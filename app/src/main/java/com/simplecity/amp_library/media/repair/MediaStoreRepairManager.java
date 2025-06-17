package com.simplecity.amp_library.media.repair;

import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.simplecity.amp_library.data.Repository;
import com.simplecity.amp_library.model.Song;
import com.simplecity.amp_library.sql.SqlUtils;
import com.simplecity.amp_library.utils.LogUtils;
import com.simplecity.amp_library.utils.StringUtils;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class MediaStoreRepairManager {

    private static final String TAG = "MediaStoreRepairManager";

    public static Completable repairYears(Context context, Repository.SongsRepository songsRepository) {
        return songsRepository.getSongs(song -> song.year < 1)
                .first(Collections.emptyList())
                .flatMapObservable(Observable::fromIterable)
                .concatMap(song -> Observable.just(song).delay(50, TimeUnit.MILLISECONDS))
                .flatMap(song -> {
                    if (!TextUtils.isEmpty(song.path)) {
                        File file = new File(song.path);
                        if (file.exists() && file.length() < 100 * 1024 * 1024) {
                            try {
                                AudioFile audioFile = AudioFileIO.read(file);
                                Tag tag = audioFile.getTag();
                                if (tag != null) {
                                    String year = tag.getFirst(FieldKey.YEAR);
                                    int yearInt = StringUtils.parseInt(year);
                                    if (yearInt > 0) {
                                        song.year = yearInt;
                                        ContentValues values = new ContentValues();
                                        values.put(MediaStore.Audio.Media.YEAR, yearInt);
                                        return Observable.just(ContentProviderOperation
                                                .newUpdate(ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, song.id))
                                                .withValues(values)
                                                .build());
                                    }
                                }
                            } catch (CannotReadException | IOException | TagException |
                                     ReadOnlyFileException | InvalidAudioFrameException | OutOfMemoryError e) {
                                LogUtils.logException(TAG, "Error updating year from tag", e);
                            }
                        }
                    }
                    return Observable.empty();
                })
                .toList()
                .doOnSuccess(ops -> context.getContentResolver().applyBatch(MediaStore.AUTHORITY, new ArrayList<>(ops)))
                .flatMapCompletable(ignored -> Completable.complete());
    }
}
