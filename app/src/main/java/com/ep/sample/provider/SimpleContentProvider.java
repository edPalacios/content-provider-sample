package com.ep.sample.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class SimpleContentProvider extends ContentProvider {

	private static final String DATABASE_NAME = "Items";
	private static final int DATABASE_VERSION = 1;
	private DatabaseHelper dbHelper;

	private static final String CREATE_LOCATION_TABLE = " CREATE TABLE "
			+ ContentContract.ITEM_TABLE_NAME + " ("
			+ ContentContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ ContentContract.ITEM_BITMAP_PATH + " TEXT NOT NULL, "
			+ ContentContract.ITEM_NAME + " TEXT NOT NULL, "
			+ ContentContract.ITEM_LOCATION + " TEXT NOT NULL, "
			+ ContentContract.ITEM_LAT + " REAL NOT NULL, "
			+ ContentContract.ITEM_LON + " REAL NOT NULL);";

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_LOCATION_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS "
					+ ContentContract.ITEM_TABLE_NAME);
			onCreate(db);
		}

	}

	@Override
	public boolean onCreate() {
		dbHelper = new DatabaseHelper(getContext());
		return (dbHelper != null);
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		int rowsDeleted = dbHelper.getWritableDatabase().delete(
				ContentContract.ITEM_TABLE_NAME, null, null);
		getContext().getContentResolver().notifyChange(
				ContentContract.CONTENT_URI, null);
		return rowsDeleted;

	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long rowID = dbHelper.getWritableDatabase().insert(
				ContentContract.ITEM_TABLE_NAME, "", values);
		if (rowID > 0) {
			Uri fullUri = ContentUris.withAppendedId(
					ContentContract.CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(fullUri, null);
			return fullUri;
		}
		throw new SQLException("Failed to add record into" + uri);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(ContentContract.ITEM_TABLE_NAME);

		Cursor cursor = qb.query(dbHelper.getWritableDatabase(), projection, selection,
				selectionArgs, null, null, sortOrder);

		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;

	}

	@Override
	public String getType(Uri arg0) {
		// Not Implemented
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// unimplemented
		return 0;
	}

}
