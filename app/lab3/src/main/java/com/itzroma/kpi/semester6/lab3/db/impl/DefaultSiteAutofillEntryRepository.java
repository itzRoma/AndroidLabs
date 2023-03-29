package com.itzroma.kpi.semester6.lab3.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.itzroma.kpi.semester6.lab3.db.DBConstants;
import com.itzroma.kpi.semester6.lab3.db.SiteAutofillEntryRepository;
import com.itzroma.kpi.semester6.lab3.model.SiteAutofillEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefaultSiteAutofillEntryRepository
        extends SQLiteOpenHelper
        implements SiteAutofillEntryRepository {

    private static DefaultSiteAutofillEntryRepository INSTANCE;

    public static synchronized DefaultSiteAutofillEntryRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DefaultSiteAutofillEntryRepository(context);
        }
        return INSTANCE;
    }

    private DefaultSiteAutofillEntryRepository(@Nullable Context context) {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBConstants.SiteAutofillEntry.CREATE_SITE_AUTOFILL_ENTRY_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(DBConstants.SiteAutofillEntry.DROP_SITE_AUTOFILL_ENTRY_TABLE_QUERY);
            onCreate(db);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void createSiteAutofillEntry(SiteAutofillEntry siteAutofillEntry) {
        SQLiteDatabase writableDatabase = INSTANCE.getWritableDatabase();

        writableDatabase.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBConstants.SiteAutofillEntry.SITE_KEY, siteAutofillEntry.getSite());
            contentValues.put(DBConstants.SiteAutofillEntry.USERNAME_KEY, siteAutofillEntry.getUsername());
            contentValues.put(DBConstants.SiteAutofillEntry.PASSWORD_KEY, siteAutofillEntry.getPassword());
            contentValues.put(DBConstants.SiteAutofillEntry.DATE_KEY, siteAutofillEntry.getDate().getTime());

            writableDatabase.insertOrThrow(
                    DBConstants.SiteAutofillEntry.SITE_AUTOFILL_ENTRY_TABLE,
                    null,
                    contentValues
            );
            writableDatabase.setTransactionSuccessful();

            System.out.println("New entry inserted successfully");
        } catch (Exception ex) {
            System.err.println("Cannot insert new entry to the database");
        } finally {
            writableDatabase.endTransaction();
        }
    }

    @Override
    public List<SiteAutofillEntry> getAllSiteAutofillEntries() {
        List<SiteAutofillEntry> res = new ArrayList<>();

        SQLiteDatabase readableDatabase = INSTANCE.getReadableDatabase();
        Cursor cursor = readableDatabase.query(
                DBConstants.SiteAutofillEntry.SITE_AUTOFILL_ENTRY_TABLE,
                DBConstants.SiteAutofillEntry.SELECT_SITE_AUTOFILL_ENTRIES_PROJECTION,
                null, null, null, null, null, null
        );

        while (cursor.moveToNext()) {
            SiteAutofillEntry entry = retrieveSiteAutofillEntry(cursor);
            res.add(entry);
        }
        cursor.close();

        return res;
    }

    private SiteAutofillEntry retrieveSiteAutofillEntry(Cursor cursor) {
        return new SiteAutofillEntry(
                cursor.getLong(cursor.getColumnIndexOrThrow(DBConstants.SiteAutofillEntry.ID_KEY)),
                cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.SiteAutofillEntry.SITE_KEY)),
                cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.SiteAutofillEntry.USERNAME_KEY)),
                cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.SiteAutofillEntry.PASSWORD_KEY)),
                new Date(cursor.getLong(cursor.getColumnIndexOrThrow(DBConstants.SiteAutofillEntry.DATE_KEY)))
        );
    }

    @Override
    public void deleteAllSiteAutofillEntries() {
        SQLiteDatabase writableDatabase = INSTANCE.getWritableDatabase();

        writableDatabase.beginTransaction();
        try {
            writableDatabase.delete(DBConstants.SiteAutofillEntry.SITE_AUTOFILL_ENTRY_TABLE, null, null);
            writableDatabase.setTransactionSuccessful();

            System.out.println("All entries deleted successfully");
        } catch (Exception ex) {
            System.err.println("Cannot delete all entries from the database");
        } finally {
            writableDatabase.endTransaction();
        }
    }

    @Override
    public boolean existsBySiteAndUsername(String site, String username) {
        SQLiteDatabase readableDatabase = INSTANCE.getReadableDatabase();

        String[] selectionArgs = {site, username};
        Cursor cursor = readableDatabase.query(
                DBConstants.SiteAutofillEntry.SITE_AUTOFILL_ENTRY_TABLE,
                DBConstants.SiteAutofillEntry.EXISTS_SITE_AUTOFILL_ENTRY_BY_SITE_AND_USERNAME_PROJECTION,
                DBConstants.SiteAutofillEntry.EXISTS_SITE_AUTOFILL_ENTRY_BY_SITE_AND_USERNAME_SELECTION,
                selectionArgs, null, null, null, null
        );

        boolean exists = cursor.moveToFirst();
        cursor.close();

        return exists;
    }
}
