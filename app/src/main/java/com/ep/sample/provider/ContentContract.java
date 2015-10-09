package com.ep.sample.provider;

import android.net.Uri;


public final class ContentContract {

	public static final String AUTHORITY = "content://com.ep.sample.provider/";
	public static final Uri BASE_URI = Uri.parse(AUTHORITY);

	public static final String ITEM_TABLE_NAME = "item";


	public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI, ITEM_TABLE_NAME);

	public static final String _ID = "_id";
	public static final String ITEM_BITMAP_PATH = "bitmapPath";
	public static final String ITEM_NAME = "name";
	public static final String ITEM_LOCATION = "location";
	public static final String ITEM_LAT = "lat";
	public static final String ITEM_LON = "lon";

}
