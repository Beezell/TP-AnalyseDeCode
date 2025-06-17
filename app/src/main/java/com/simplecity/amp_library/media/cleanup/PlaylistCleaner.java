package com.simplecity.amp_library.media.cleanup;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;

import com.simplecity.amp_library.model.Genre;
import com.simplecity.amp_library.sql.sqlbrite.SqlBriteUtils;
import com.simplecity.amp_library.utils.extensions.GenreExtKt;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class GenreCleaner {

    public static Completable cleanGenres(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            return Completable.complete();
        }

        return SqlBriteUtils.createSingleList(context, Genre::new, Genre.getQuery())
                .flatMapObservable(Observable::fromIterable)
                .concatMap(genre -> Observable.just(genre).delay(50, TimeUnit.MILLISECONDS))
                .flatMapSingle(genre -> GenreExtKt.getSongsObservable(genre, context)
                        .doOnSuccess(songs -> {
                            if (songs.isEmpty()) {
                                context.getContentResolver().delete(
                                        MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI,
                                        MediaStore.Audio.Genres._ID + " == " + genre.id,
                                        null);
                            }
                        }))
                .ignoreElements();
    }
}
