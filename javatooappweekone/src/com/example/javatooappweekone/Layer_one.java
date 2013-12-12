package com.example.javatooappweekone;
//Justin Khalil formerly of OCD, latterly of FSU
//Java-II Week One
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.methodical.ContentProviderThing;
import com.example.methodical.Filer;
import com.example.methodical.Grabber;
import com.example.methodical.SpecHandler;
// import android.database.MatrixCursor;

public class Layer_one extends Activity implements OnClickListener {
	public final static String PUBLIC_KEY = "KEYFORSEARCH";
	public final static String MY_LIST = "LISTKEY";
	EditText et;
	TextView tv;
	Filer fl;
	String obj;
	ListView lv;
	Handler AHandler;
	String searchString;
	static ArrayList<HashMap<String, String>> thisList;
	boolean savedInstance;
	Bundle state;
	SpecHandler sh;
	String searchResults;
	private Button sendButton;
	
	
	
	
	
	
	
    // There's a variable definition a few lines down that Eclipse doesn't like.
    // Research says that there's no solid way around it other than suppressing it, so that's what I do here.
    @SuppressWarnings("unchecked")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	setContentView(R.layout.activity_layer_one);
    	super.onCreate(savedInstanceState);
    	
    	sendButton = (Button)findViewById(R.id.sendButton);
    	sendButton.setOnClickListener(this);
    	et = (EditText) findViewById(R.id.textField);
		// okay, it seems that somehow android remembers the contents of an EditText field on restoreInstanceState.
		// that's just weird.
		// et.setText(ContentProviderThing.DisplayData.CONTENT_URI.toString());
		ContentResolver cr = getContentResolver();
		
		
		// Uri path = new Uri.Builder().scheme( ContentResolver.SCHEME_CONTENT ).authority(ContentProviderThing.AUTHORITY ).appendPath( "items/" ).build();
		Cursor cur = cr.query(ContentProviderThing.DisplayData.CONTENT_URI, ContentProviderThing.DisplayData.PROJECTION, null, null, null);
		// Cursor cur = cr.query(path, ContentProviderThing.DisplayData.PROJECTION, null, null, null);
		// System.out.println(ContentProviderThing.DisplayData.CONTENT_TYPE);
		// System.out.println(ContentProviderThing.DisplayData.CONTENT_URI);
		// Uri nowURI = ContentProviderThing.DisplayData.CONTENT_URI;
		
		// Uri uri = Uri.withAppendedPath(ContentProviderThing.DisplayData.CONTENT_URI, ContentProviderThing.DisplayData.TITLE_COLUMN);
				
				// h(ContentProviderThing.DisplayData.CONTENT_URI, ContentProviderThing.ITEMS);
		
		// String uriString = "content://com.example.methodical.contentproviderthing/items/1";
		// Uri uriStringInUriForm = Uri.parse(uriString);
		Uri uriNew = Uri.parse(ContentProviderThing.DisplayData.CONTENT_URI +"/items/");
		System.out.println(uriNew.toString());
		// Cursor cur = cr.query(uriNew, ContentProviderThing.DisplayData.PROJECTION, null, null, null);
		// Cursor cur = cr.query(ContentProviderThing.DisplayData.CONTENT_URI, ContentProviderThing.DisplayData.PROJECTION, null, null, null);
		// cur.moveToFirst();
		// String outputString = cur.getColumnName(0);
		String[] outputString = cur.getColumnNames();
		int quant = cur.getCount();
		System.out.println("# of rows: " + quant);
		// String secondOutput = cur.getString(cur.getColumnIndex("title"));
		// System.out.println(secondOutput);
		System.out.println(outputString[0] + outputString[1] + outputString[2] + outputString[3]);
		// Cursor cur = cr.query(uri, ContentProviderThing.DisplayData.PROJECTION, null, null, null);
		// 
		// String output = cur.getString(1);
		// System.out.println(output);
		
		// String data = cur.getString(cur.getCount());
		System.out.println(cur.getColumnCount());
		// cur.moveToPosition(1);
		if (cur.moveToFirst()) {
			String text = cur.getInt(0) + " " + cur.getString(1);
			System.out.println(text);
		}
		// String text = cur.getInt(0)
		// System.out.println(cur.getString(0));
	    //Cursor c = managedQuery(ContentProviderThing.ITEMS_ID, null, null, null, "name");
		
    	if (savedInstanceState != null){

    		thisList = (ArrayList<HashMap<String, String>>) savedInstanceState.getSerializable(MY_LIST);
    		searchString = (String) savedInstanceState.getString(PUBLIC_KEY);
    		et = (EditText) findViewById(R.id.textField);
    		// okay, it seems that somehow android remembers the contents of an EditText field on restoreInstanceState.
    		// that's just weird.
    		
    		
    		sendButton = (Button)findViewById(R.id.sendButton);
    		populateList();
        } else {
        	fl = new Filer();
        	// get string containing saveddata file contents.
            // if there are contents, it means the app has been run before.
        	String newstring = fl.readFromFile(getApplicationContext(), "saveddata");
        	
        	if (newstring.length() > 1){
        		
        		populateList();
        	}
        	
        	// activatable lines to delete files for testing purposes.
            // fl.deleteFile(getApplicationContext(), "saveddata");
            // fl.deleteFile(getApplicationContext(), "searchresults");
            // fl.deleteFile(getApplicationContext(), "arraycontents");
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.layer_one, menu);
        return true;
    }
    
    public void openSecondView(View view) {
        // open second activity
    	// not implemented in final version, used for testing.
    	// Intent intent = new Intent(this, ShowListViewActivity.class);
    	// String st = et.getText().toString();
    	// intent.putExtra(PUBLIC_KEY, st);
        // startActivity(intent);
    }
    
    public void populateList(){
    	
    	
    	
		ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
		
		if (thisList != null && !thisList.isEmpty()){
			myList = thisList;
			lv = (ListView)findViewById(R.id.listId1);
			// construct hashmap for returns.
			SimpleAdapter sa = new SimpleAdapter(this, myList, R.layout.list_row, new String[] { "title", "synopsis", "year"}, new int[] { R.id.titleText, R.id.synopsisText, R.id.yearText});
			lv.setAdapter(sa);
				
		} else {
			try {
				Filer fl = new Filer();
				String jsonResults = fl.readFromFile(getApplicationContext(), "searchresults");
				// System.out.println(jsonResults);
		    	
				
				// create object from JSON string
				// break it down into individual items.
				JSONObject jsobject = new JSONObject(jsonResults);
				// Log.e("json", "first object done");
				JSONArray jso = jsobject.getJSONArray("movies");
				// alternate parsing code here.
				// JSONObject jso = jsobject.getJSONObject("responseData");
				// Log.e("json", "second object done");
				// JSONArray arrayObject = jso.getJSONArray("entries");
				// Log.e("json", "third object done");
				int arraySize = jso.length();
				// TextView tv = (TextView)findViewById(R.id.textable);
				// tv.setText(String.valueOf(arraySize));
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
				
				lv = (ListView)findViewById(R.id.listId1);
				// construct hashmap for returns.
				SimpleAdapter sa = new SimpleAdapter(this, myList, R.layout.list_row, new String[] { "title", "synopsis", "year"}, new int[] { R.id.titleText, R.id.synopsisText, R.id.yearText});
				lv.setAdapter(sa);
					
				// View v = (View)findViewById(R.layout.activity_layer_one);
					
					
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	sendButton = (Button)findViewById(R.id.sendButton);
	sendButton.setText(R.string.search_prompt);
	}
    
    
    private void buildList(){
    	lv = (ListView)findViewById(R.id.listId1);
		// construct hashmap for returns.
		SimpleAdapter sa = new SimpleAdapter(this, thisList, R.layout.list_row, new String[] { "title", "synopsis", "year"}, new int[] { R.id.titleText, R.id.synopsisText, R.id.yearText});
		lv.setAdapter(sa);
		sendButton = (Button)findViewById(R.id.sendButton);
		sendButton.setText(R.string.search_prompt);
		
		
	}
    
    
    @Override
    protected void onSaveInstanceState(Bundle outState){
    	
    	if (thisList != null && !thisList.isEmpty()){
    		outState.putSerializable(MY_LIST, (Serializable) thisList);
    		String st = et.getText().toString();
    		outState.putString(PUBLIC_KEY, st);
    	}
    	super.onSaveInstanceState(outState);
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
    	super.onRestoreInstanceState(savedInstanceState);
    }

    static public void setContent(ArrayList<HashMap<String, String>> contentList){
    	thisList = contentList;
    	System.out.println("thislist updated");
    }
    
    @Override
	public void onClick(View arg0) {
		sendButton = (Button)findViewById(R.id.sendButton);
		sendButton.setText(R.string.loading);
		
		fl = new Filer();
		fl.writeToFile(getApplicationContext(), "previously run", "saveddata");
		
		
		
		populateList();
		
		
		
		
		new Thread(new Runnable() { 
            public void run(){
            	//et = (EditText)findViewById(R.id.textField);
          	  	//String st = et.getText().toString();
          	  	//Grabber gr = new Grabber();
          	  	//obj = gr.grabData(getApplicationContext(), st);
           }
        }).start();
		
		
		
		sh = new SpecHandler(){
			
    		@SuppressWarnings("unchecked")
			@Override
    		public void handleMessage(Message msg){
    			ArrayList<HashMap<String, String>> response = null;
    			if (msg.arg1 == RESULT_OK && msg.obj != null){
    				
    				try {
    					response = (ArrayList<HashMap<String, String>>) msg.obj;
    					thisList = response;
    					System.out.println("list refreshing");
    					// populateList();
    				} catch (Exception e) {
    					System.out.println("list not refreshing");
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    				
    				// setContent(response);
    				
    			}
    			
    		
    		}
    	};
    	
		
		
		Messenger cycleMessenger = new Messenger(sh);
		Intent cycleIntent = new Intent(this, CycleService.class);
		cycleIntent.putExtra(CycleService.RESULT_COOL, cycleMessenger);
		startService(cycleIntent);
		
		// populateList();
		
		// sh.post(setLoadingText);
		// sh.post(doListThing);
		// sh.post(setSearchText);
		new LoadTask().execute();
		}


    public class LoadTask extends AsyncTask<Void, Void, Void> {
       // oh wow, I cannot believe how convoluted this all got. 
    	
       
       @Override
       protected Void doInBackground(Void...arg0){
    	   et = (EditText)findViewById(R.id.textField);
    	   String st = et.getText().toString();
    	   Grabber gr = new Grabber();
    	   obj = gr.grabData(getApplicationContext(), st);
    	   return null;
       }
       @Override
       protected void onPostExecute(Void result){
    	   // try {
    		   
    		   // ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
    		   ArrayList<HashMap<String, String>> myListToo = new ArrayList<HashMap<String, String>>();
    		   // Filer fl = new Filer();
    		   // String jsonResults = fl.readFromFile(getApplicationContext(), "searchresults");
    		   // System.out.println(jsonResults);
				
    		   ContentResolver cr = getContentResolver();
    		   Cursor cur = cr.query(ContentProviderThing.DisplayData.CONTENT_URI, ContentProviderThing.DisplayData.PROJECTION, null, null, null);
    		   int quant = cur.getCount();
    		   int x = 0;
    		   for (x = 0; x < quant; x++){
    			   String title = cur.getInt(x) + " " + cur.getString(1);
    			   String synop = cur.getInt(x) + " " + cur.getString(2);
    			   String url = cur.getInt(x) + " " + cur.getString(3);
					
    			   HashMap<String, String> tempHash = new HashMap<String, String>();
    			   tempHash.put("title", title.toString());
    			   tempHash.put("synopsis", synop.toString());
    			   tempHash.put("year", url);
					
    			   myListToo.add(tempHash);
				}
    		   thisList = myListToo;
				/*
				// create object from JSON string
				// break it down into individual items.
				JSONObject jsobject = new JSONObject(jsonResults);
				// Log.e("json", "first object done");
				JSONArray jso = jsobject.getJSONArray("movies");
				// alternate parsing code here.
				// JSONObject jso = jsobject.getJSONObject("responseData");
				// Log.e("json", "second object done");
				// JSONArray arrayObject = jso.getJSONArray("entries");
				// Log.e("json", "third object done");
				int arraySize = jso.length();
				// TextView tv = (TextView)findViewById(R.id.textable);
				// tv.setText(String.valueOf(arraySize));
				// MatrixCursor cursorResult = new MatrixCursor(new String[] {"_id","Column1","Column2","Column3"});
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
					
					// cursorResult.addRow(new Object[] {i, convert, convertCon, url});
					myList.add(stuffMap);
					
				}
				*/
				
    		   buildList();		
			} 
			
    	   
       }
    

}