package com.example.methodical;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

public class ContentProviderThing extends ContentProvider {
	static final String TAG = "ContentProviderThing";
	public static final String AUTHORITY = "com.example.methodical.contentproviderthing";
	
	public static class DisplayData implements BaseColumns {
		// defining URIs.
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/items");
		// public static final Uri CONTENT_URI = Uri.parse(AUTHORITY);
		
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.example.methodical.item";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.example.methodical.item";
		// defining constants
		public static final String OBJECT_COLUMN = "object";
		public static final String TITLE_COLUMN = "title";
		public static final String YEAR_COLUMN = "year";
		
		public static final String[] PROJECTION = {"_Id", OBJECT_COLUMN, TITLE_COLUMN, YEAR_COLUMN };
		
		private DisplayData() {};
	}
	
	public static final int ITEMS = 1;
	public static final int ITEMS_ID = 2;
	
	public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	public static class InterfaceConsts {
	       public static String Id = "_id";
	       public static String Name = "name";
	   }
	
	static {
		uriMatcher.addURI(AUTHORITY, "items/", ITEMS);
		uriMatcher.addURI(AUTHORITY, "items/#", ITEMS_ID);
	}
	
	
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public String getType(Uri uri) {
		System.out.println("onCreate");
		switch (uriMatcher.match(uri)) {
		case ITEMS:
			return DisplayData.CONTENT_TYPE;
		case ITEMS_ID:
			return DisplayData.CONTENT_ITEM_TYPE;
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	ContentProviderThing contDB;
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		contDB = new ContentProviderThing();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		
		
		System.out.println("Query running");
		System.out.println(uri.toString());
		MatrixCursor result = new MatrixCursor(DisplayData.PROJECTION);
		Filer fl = new Filer();
		String JSONString = fl.readFromFile(getContext(), "searchresults");
		JSONObject job = null;
		JSONArray recordArray = null;
		
		try {
			System.out.println("Query still running: 1");
			job = new JSONObject(JSONString);
			recordArray = job.getJSONArray("movies");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (recordArray == null){
			System.out.println("result == null");
			return result;
			
		}
		System.out.println("Query still running: 2");
		switch (uriMatcher.match(uri)) {
		case ITEMS:
			System.out.println("case ITEMS: running");
			int i = 0;
			for (i = 0; i < recordArray.length(); i++){
				try {
					System.out.println("is this thing working?");
					JSONObject current = recordArray.getJSONObject(i);
					String title = current.getString("title");
					title = "Title: " + title;
					Spanned convert = Html.fromHtml(title);
					String contentSnip = current.getString("synopsis");
					Spanned convertCon = Html.fromHtml(contentSnip);
						
					String url = current.getString("year");
					url = "year:" + url + " ";
					// some returned items lack certain elements. 
					// so I check for items that are not in every return
					if (current.has("critics_consensus")){
						url = current.getString("critics_consensus");
					}
					if (current.has("release_dates")){
						JSONObject rd = current.getJSONObject("release_dates");
						if (rd.has("theater")){
							// if there is a listed theatrical release date, put that into the returned listview
							url = url + "\n" + "released: " + rd.getString("theater");
							// I'm really getting tired of the incredibly small variable names.
							// rd, pl, osw, pl, rl, it's just bizarrely cryptic.
						}
					}
					HashMap<String, String> stuffMap = new HashMap<String, String>();
					stuffMap.put("title", convert.toString());
					stuffMap.put("synopsis", convertCon.toString());
					stuffMap.put("year", url);
					result.addRow(new Object[] {i + 1, convert, convertCon, url});
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// return DisplayData.CONTENT_TYPE;
		case ITEMS_ID:
			System.out.println("Query still running: 2");
			String itemID = uri.getLastPathSegment();
			Log.e("QueryId", itemID);
			
			/*
			int index;
			index = Integer.parseInt(itemID);
			System.out.println("Query still running: 3");
			if (index <= 0 || index > recordArray.length()){
				Log.e("query", "index out of the array's range");
			}
			
			try {
				JSONObject current = recordArray.getJSONObject(index - 1);
				String title = current.getString("title");
				title = "Title: " + title;
				Spanned convert = Html.fromHtml(title);
				String url = current.getString("year");
				url = "year:" + url + " ";
				result.addRow(new Object[] {index, convert, convert, url});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			// return DisplayData.CONTENT_ITEM_TYPE;
		}
		
		// TODO Auto-generated method stub
		System.out.println("Query running at end of query, right before result is returned: ");
		return result;
		
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	
}
