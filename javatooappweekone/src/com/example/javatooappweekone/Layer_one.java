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
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.methodical.Filer;
import com.example.methodical.Grabber;

public class Layer_one extends Activity {
	public final static String PUBLIC_KEY = "KEYFORSEARCH";
	public final static String MY_LIST = "LISTKEY";
	EditText et;
	Button sendButton;
	TextView tv;
	Filer fl;
	String obj;
	ListView lv;
	Handler AHandler;
	String searchString;
	ArrayList<HashMap<String, String>> thisList;
	boolean savedInstance;
	Bundle state;
	
     private Runnable doListThing = new Runnable() {
         public void run() {
        	 savedInstance = false;
             populateList();
         }
      };
	
    // There's a variable definition a few lines down that Eclipse doesn't like.
    // Research says that there's no solid way around it other than suppressing it, so that's what I do here.
    @SuppressWarnings("unchecked")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	setContentView(R.layout.activity_layer_one);
    	super.onCreate(savedInstanceState);
    	
    	if (savedInstanceState != null){

    		savedInstance = true;
    		thisList = (ArrayList<HashMap<String, String>>) savedInstanceState.getSerializable(MY_LIST);
    		searchString = (String) savedInstanceState.getString(PUBLIC_KEY);
    		et = (EditText) findViewById(R.id.textField);
    		// okay, it seems that somehow android remembers the contents of an EditText field on restoreInstanceState.
    		// that's just weird.
    		
    		sendButton = (Button)findViewById(R.id.sendButton);
    		
    		populateList();
    		AHandler = new Handler();
        	fl = new Filer();
    		sendButton.setOnClickListener(new View.OnClickListener() {
        		
    			@Override
    			public void onClick(View arg0) {
    				sendButton = (Button)findViewById(R.id.sendButton);
    				sendButton.setText(R.string.loading);
    				fl = new Filer();
    				fl.writeToFile(getApplicationContext(), "previously run", "saveddata");
    				new Thread(new Runnable() { 
    		              public void run(){
    		            	  String st = et.getText().toString();
    		            	  Grabber gr = new Grabber();
    		            	  obj = gr.grabData(getApplicationContext(), st);
    		            	  System.out.println(obj);
    		            	  runOnUiThread(new Runnable() {
    		            		  public void run() {
    		            			  // start handler's runnable for adding elements to listview
    		            			  
    		            			  AHandler.post(doListThing);
    		            		  }
    		            	  });
    		              }}).start();
    				}
    		});
    		
    	} else {
    		AHandler = new Handler();
        	fl = new Filer();
        	// get string containing saveddata file contents.
            // if there are contents, it means the app has been run before.
        	String newstring = fl.readFromFile(getApplicationContext(), "saveddata");
        	if (newstring.length() > 1){
        		populateList();
        	}
        	
        	String searchbj = fl.readFromFile(getApplicationContext(), "searchresults");
            try {
    			JSONObject json = new JSONObject(searchbj);
    			JSONObject jso = json.getJSONObject("value");
    			JSONArray resultsArray = jso.getJSONArray("items");
    			int i;
    			for (i = 0; i <= resultsArray.length(); i++){
    				JSONObject currentObject = resultsArray.getJSONObject(i);
    				String title = currentObject.getString("title");
    				tv.setText(title);
    			}
    		} catch (JSONException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            
            et = (EditText)findViewById(R.id.textField);
            sendButton = (Button)findViewById(R.id.sendButton);
            sendButton.setOnClickListener(new View.OnClickListener() {
        		
    			@Override
    			public void onClick(View arg0) {
    				sendButton = (Button)findViewById(R.id.sendButton);
    				sendButton.setText(R.string.loading);
    				fl = new Filer();
    				fl.writeToFile(getApplicationContext(), "previously run", "saveddata");
    				new Thread(new Runnable() { 
    		              public void run(){
    		            	  String st = et.getText().toString();
    		            	  Grabber gr = new Grabber();
    		            	  obj = gr.grabData(getApplicationContext(), st);
    		            	  System.out.println(obj);
    		            	  runOnUiThread(new Runnable() {
    		            		  public void run() {
    		            			  
    		            			  // start handler's runnable for adding elements to listview
    		            			  AHandler.post(doListThing);
    		            			  
    		            		  }
    		            	  });
    		              }}).start();
    				}
    		});
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
    	
		Filer fl = new Filer();
		String jsonResults = fl.readFromFile(getApplicationContext(), "searchresults");
		// System.out.println(jsonResults);
		
		ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
		System.out.println("checking if savedInstance == true");
		System.out.println("savedInstance == true");
		if (thisList != null && !thisList.isEmpty()){
			myList = thisList;
			lv = (ListView)findViewById(R.id.listId1);
			// construct hashmap for returns.
			SimpleAdapter sa = new SimpleAdapter(this, myList, R.layout.list_row, new String[] { "title", "synopsis", "year"}, new int[] { R.id.titleText, R.id.synopsisText, R.id.yearText});
			lv.setAdapter(sa);
				
		} else {
			System.out.println("savedInstance == false");
			try {
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
	savedInstance = false;
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







}