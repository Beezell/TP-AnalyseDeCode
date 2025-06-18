package com.simplecity.amp_library.sql.providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import com.simplecity.amp_library.BuildConfig;

public class PlayCountTable extends SQLiteOpenHelper {

    private Context applicationContext;

    private static final String DATABASE_NAME = "play_count.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_PLAY_COUNT = "play_count";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PLAY_COUNT = "play_count";
    public static final String COLUMN_TIME_PLAYED = "time_played";

    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE_PLAY_COUNT
            + "("
            + COLUMN_ID + " LONG NOT NULL UNIQUE ON CONFLICT REPLACE, "
            + COLUMN_PLAY_COUNT + " INTEGER DEFAULT 0, "
            + COLUMN_TIME_PLAYED + " LONG DEFAULT 0"
            + ");";

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".play_count.contentprovider";

    public static final Uri URI = Uri.parse("content://" + AUTHORITY + "/" + "play_count");

    public PlayCountTable(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.applicationContext = context.getApplicationContext();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {

            String tableBackUp = "BACKUP";

            //Rename existing table to backup
            db.execSQL("ALTER TABLE " + TABLE_PLAY_COUNT + " RENAME TO " + tableBackUp + ";");

            //Create new table
            db.execSQL("CREATE TABLE " + TABLE_PLAY_COUNT + "("
                    + COLUMN_ID + " LONG NOT NULL UNIQUE ON CONFLICT REPLACE, "
                    + COLUMN_PLAY_COUNT + " INTEGER DEFAULT 0, "
                    + COLUMN_TIME_PLAYED + " LONG DEFAULT 0);");

            //Copy backup into new
            db.execSQL("INSERT OR REPLACE INTO " + TABLE_PLAY_COUNT + "(" + COLUMN_ID + ", " + COLUMN_PLAY_COUNT + ") "
                    + "SELECT " + COLUMN_ID + "," + COLUMN_PLAY_COUNT + " FROM " + tableBackUp + "; ");

            //Drop backup
            db.execSQL("DROP TABLE " + tableBackUp + "; ");

            //We have to end this transaction so we can attach the count info table below
            db.setTransactionSuccessful();
            db.endTransaction();

            //Add rows from count info table
            try {
                String countInfoDb = "count_info.db";
                String pahtCountInfo = applicationContext.getDatabasePath(countInfoDb).toString();
                String tableCountInfo = "COUNT_INFO";
                String countInfoColumnId = "_id";
                String countInfoColumnTimePlayed = "time_played";

                db.execSQL("ATTACH '" + pahtCountInfo + "' AS " + tableCountInfo + "; ");

                //Now we have to begin a new transaction
                db.beginTransaction();
                db.execSQL("INSERT OR REPLACE INTO "
                        + TABLE_PLAY_COUNT
                        + "("
                        + COLUMN_ID
                        + ", "
                        + COLUMN_PLAY_COUNT
                        + ", "
                        + COLUMN_TIME_PLAYED
                        + ") "
                        + "SELECT "
                        + countInfoColumnId
                        + ","
                        + "(SELECT "
                        + COLUMN_PLAY_COUNT
                        + " FROM "
                        + TABLE_PLAY_COUNT
                        + " WHERE _id = "
                        + COLUMN_ID
                        + ")"
                        + ","
                        + countInfoColumnTimePlayed
                        + " FROM "
                        + tableCountInfo
                        + ";");
            } catch (SQLiteException ignored) {
                // The count info table probably doesn't exist (it wasn't created in the previous version of the app)
                //  Nothing to do
            }
            //SQLiteOpenHelper will automatically setTransactionSuccessful & endTransaction for us.
        }
    }
}