package com.example.javatooappweekone;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.Html;
import android.text.Spanned;

import com.example.methodical.Filer;
// import com.example.javatooappweekone.Layer_one;

public class CycleService extends IntentService {
	static String searchTerm;
	static ArrayList<HashMap<String, String>> thisList;
	
	public final static String RESULT_COOL = "loader";
	
	public CycleService(){
		super("CycleService");
	}
	
	public static void setSearchTerm(String st){
		searchTerm = st;
	}
	
	@Override
	protected void onHandleIntent(Intent intent){
		
		Bundle extras = intent.getExtras();
		Messenger msgr = (Messenger) extras.get(RESULT_COOL);
		
		Filer fl = new Filer();
		String jsonResults = fl.readFromFile(getApplicationContext(), "searchresults");
		// System.out.println(jsonResults);
			
		ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
		// construct hashmap for returns.
		try {
			JSONObject jsobject = new JSONObject(jsonResults);
			JSONArray jso = jsobject.getJSONArray("movies");
			int arraySize = jso.length();
				int i;
				// iterate through JSON array, adding strings to hashmap
				for (i = 0; i < arraySize; i++){
					JSONObject current = jso.getJSONObject(i);
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
					myList.add(stuffMap);
				}	
						
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
	Message message = Message.obtain();
	message.arg1 = Activity.RESULT_OK;
	message.obj = myList;
	
	try {
		msgr.send(message);
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	// thisList = myList;
	// Layer_one.setContent(thisList);
	}
	
}
