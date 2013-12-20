package com.example.javatooappweekone;
//Justin Khalil formerly of OCD, latterly of FSU
//Java-II Week Two
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.methodical.ContentProviderThing;
import com.example.methodical.Filer;
import com.example.methodical.Grabber;
import com.example.methodical.SpecHandler;

public class Layer_one extends Activity implements OnClickListener, SearchFraggle.DetailListener {
	
	// , SearchFraggle.SearchListener 
	public final static String PUBLIC_KEY = "KEYFORSEARCH";
	public final static String MY_LIST = "LISTKEY";
	public final static String DETAIL_KEY = "DETAILKEY";
	public final static String URL_KEY = "URLKEY";
	public final static String TITLE_KEY = "TITLEKEY";
	public final static String IN_MESSAGE = "input_message";
	public final static String OUT_MESSAGE = "output_message";
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
	Cursor cre;
	
	
	
	public TriggerListener triggerListener;
	
	public interface TriggerListener{
		public void onItemSelect(String one, String two, String three);
	}
	
	// There's a variable definition a few lines down that Eclipse doesn't like.
    // Research says that there's no solid way around it other than suppressing it, so that's what I do here.
    // @SuppressWarnings("unchecked")
	@Override
	public void onListItemClick(String title, String detail, String url){
		System.out.println(title+detail+url);
		triggerListener.onItemSelect(title, detail, url);
	}
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	// setContentView(R.layout.activity_layer_one);
    	setContentView(R.layout.search_frag);
    	
    	/*
    	IntentFilter filter = new IntentFilter(ResponseReceiver.RESPONSE_EVENT);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);
    	*/
    	/*
    	sendButton = (Button)findViewById(R.id.sendButton);
    	sendButton.setOnClickListener(this);
    	et = (EditText) findViewById(R.id.textField);
    	lv = (ListView) findViewById(R.id.listId1);
    	lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	// String item = ((TextView)view).getText().toString();
            	if (cre == null){
            		ContentResolver cr = getContentResolver();
            		Cursor cur = cr.query(ContentProviderThing.DisplayData.CONTENT_URI, ContentProviderThing.DisplayData.PROJECTION, null, null, null);
            		cre = cur;
            	}
            	String item;
            	String item2;
            	String item3;
            	cre.moveToFirst();
            	if (cre.moveToPosition(position)){
            		item = cre.getString(1);
            		item2 = cre.getString(2);
            		item3 = cre.getString(3);
            		System.out.println(item);
            		System.out.println(item2);
            		System.out.println(item3);
            		//View view = (View) f
            		View thisone = (View)findViewById(R.id.thisView);
            		openNewActivity(thisone, item, item2, item3);
            	}	
            }
        });
    	
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
        	String newstring = fl.readFromFile(getApplicationContext(), "searchresults");
        	if (newstring.length() > 1){
        		populateList();
        	}
        	// activatable lines to delete files for testing purposes.
            // fl.deleteFile(getApplicationContext(), "saveddata");
            // fl.deleteFile(getApplicationContext(), "searchresults");
            // fl.deleteFile(getApplicationContext(), "arraycontents");
    	}
    	*/
    	
    	//ItemThing firstItem = new ItemThing();
    	//firstItem.setStrings("the first string", "the second string", "the third string", "the fourth string", "the fifth string");
    	// ItemThing secondItem = new ItemThing(ItemThing.setStrings("the first string", "the second string", "the third string", "the fourth string", "the fifth string"));
    	
    	
    	
    	
    	
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.layer_one, menu);
        return true;
    }
    
    /*
    public void populateList(){
    	ArrayList<HashMap<String, String>> myListToo = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
		if (thisList != null && !thisList.isEmpty()){
			myList = thisList;
			myListToo = thisList;
			// lv = (ListView)findViewById(R.id.listId1);
			// construct hashmap for returns.
			SimpleAdapter sa = new SimpleAdapter(this, myList, R.layout.list_row, new String[] { "title", "synopsis", "year"}, new int[] { R.id.titleText, R.id.synopsisText, R.id.yearText});
			lv.setAdapter(sa);	
		} else {
			// try {
    	   		ContentResolver cr = getContentResolver();
    	   		Cursor cur = cr.query(ContentProviderThing.DisplayData.CONTENT_URI, ContentProviderThing.DisplayData.PROJECTION, null, null, null);
    	   		int quant = cur.getCount();
    	   		int x = 0;
    	   		for (x = 0; x < quant; x++){
    	   			if (cur.moveToPosition(x)){
    	   				String title = cur.getString(1);
    	   				String synop = cur.getString(2);
    	   				String url = cur.getString(3);
    	   				HashMap<String, String> tempHash = new HashMap<String, String>();
    	   				tempHash.put("title", title.toString());
    	   				tempHash.put("synopsis", synop.toString());
    	   				tempHash.put("year", url);
    	   				System.out.println(title + " " + synop + " " + " " + url);
    	   				myListToo.add(tempHash);
    	   			}
    			}
    	   		cre = cur;
    		   	thisList = myListToo;
    		   	cre = cur;
       		   	buildList();	
    		   	// lv = (ListView)findViewById(R.id.listId1);
				// construct hashmap for returns.
				// SimpleAdapter sa = new SimpleAdapter(this, thisList, R.layout.list_row, new String[] { "title", "synopsis", "year"}, new int[] { R.id.titleText, R.id.synopsisText, R.id.yearText});
				// lv.setAdapter(sa);
		}
	sendButton = (Button)findViewById(R.id.sendButton);
	sendButton.setText(R.string.search_prompt);
	}
    
    */
    /*
    private void buildList(){
    	
    	ContentResolver cr = getContentResolver();
  		Cursor cur = cr.query(ContentProviderThing.DisplayData.CONTENT_URI, ContentProviderThing.DisplayData.PROJECTION, null, null, null);
  		int quant = cur.getCount();
  		int x = 0;
  		for (x = 0; x < quant; x++){
  			if (cur.moveToPosition(x)){
  				String title = cur.getString(1);
  				String synop = cur.getString(2);
  				String url = cur.getString(3);
  				HashMap<String, String> tempHash = new HashMap<String, String>();
  				tempHash.put("title", title.toString());
  				tempHash.put("synopsis", synop.toString());
  				tempHash.put("year", url);
  				System.out.println(title + " " + synop + " " + " " + url);
  				
  			}
  		}
    	// Context context = getApplicationContext();
    			
    	// triggerListener = (TriggerListener) context;
    	// triggerListener.triggerListRefresh(this);
    	
    	
    	lv = (ListView)findViewById(R.id.listId1);
		// construct hashmap for returns.
		SimpleAdapter sa = new SimpleAdapter(this, thisList, R.layout.list_row, new String[] { "title", "synopsis", "year"}, new int[] { R.id.titleText, R.id.synopsisText, R.id.yearText});
		lv.setAdapter(sa);
		sendButton = (Button)findViewById(R.id.sendButton);
		sendButton.setText(R.string.search_prompt);
		
    	
		
	}
    
    */
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
    	// thisList = contentList;
    	// System.out.println("thislist updated");
    }
    
    @Override
	public void onClick(View arg0) {
		sendButton = (Button)findViewById(R.id.sendButton);
		sendButton.setText(R.string.loading);
		
		fl = new Filer();
		fl.writeToFile(getApplicationContext(), "previously run", "saveddata");
		
		// populateList();
		
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
    		}
    	}
	};
	/*
    Messenger cycleMessenger = new Messenger(sh);
	Intent cycleIntent = new Intent(this, CycleService.class);
	cycleIntent.putExtra(CycleService.RESULT_COOL, cycleMessenger);
	startService(cycleIntent);
	*/
	// new LoadTask().execute();
	}

    /*
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
    	   ArrayList<HashMap<String, String>> myListToo = new ArrayList<HashMap<String, String>>();
    	   ContentResolver cr = getContentResolver();
    	   Cursor cur = cr.query(ContentProviderThing.DisplayData.CONTENT_URI, ContentProviderThing.DisplayData.PROJECTION, null, null, null);
    	   int quant = cur.getCount();
    	   int x = 0;
    	   for (x = 0; x < quant; x++){
    		   if (cur.moveToPosition(x)){
    			   String title = cur.getString(1);
    			   String synop = cur.getString(2);
    			   String url = cur.getString(3);
    			   HashMap<String, String> tempHash = new HashMap<String, String>();
    			   tempHash.put("title", title.toString());
    			   tempHash.put("synopsis", synop.toString());
    			   tempHash.put("year", url);
    			   myListToo.add(tempHash);
    			   }
    		   }
    	   cre = cur;
    	   thisList = myListToo;
    	   cre = cur;
    	   buildList();
    	   lv = (ListView)findViewById(R.id.listId1);
    	   // construct hashmap for returns.
    	   SimpleAdapter sa = new SimpleAdapter(getApplicationContext(), myListToo, R.layout.list_row, new String[] { "title", "synopsis", "year"}, new int[] { R.id.titleText, R.id.synopsisText, R.id.yearText});
    	   lv.setAdapter(sa);
       }
    }
    */
    
    /*
    public void openNewActivity(View view, String title, String detail, String url) {
        // Do something in response to button
    	Intent intent = new Intent(this, DetailActivity.class);
    	intent.putExtra(TITLE_KEY, title);
    	intent.putExtra(DETAIL_KEY, detail);
    	intent.putExtra(URL_KEY, url);
    	startActivity(intent);
    }
    */
    
    // Search Fragment method.
    
    /*
    @Override
    public void onSearchClick(String searchKey){
    	System.out.println("listener functioning, searchKey is: " + searchKey);
    	searchString = searchKey;
    	
    	Intent messageIntent = new Intent(this, CycleService.class);
    	messageIntent.putExtra(CycleService.IN_MESSAGE, searchKey);
    	startService(messageIntent);
    	
    	
    	
    	// new LoadTask().execute();
    	
    	
    }
    */
    
    /*
    @Override
    public void relayToActivity(String one, String two, String three){
    	
    }
    */
    
    public class LoadTask extends AsyncTask<Void, Void, Void> {
	       // oh wow, I cannot believe how convoluted this all got. 
	       @Override
	       protected Void doInBackground(Void...arg0){
	    	   System.out.println("doInBackground functioning, searchKey is: " + searchString);
	    	   // String st = et.getText().toString();
	    	   Grabber gr = new Grabber();
	    	   obj = gr.grabData(getApplicationContext(), searchString);
	    	   System.out.println(obj);
	    	   return null;
	       }
	       @Override
	       protected void onPostExecute(Void result){
	    	   
	    	   ContentResolver cr = getContentResolver();
	      		Cursor cur = cr.query(ContentProviderThing.DisplayData.CONTENT_URI, ContentProviderThing.DisplayData.PROJECTION, null, null, null);
	      		int quant = cur.getCount();
	      		int x = 0;
	      		for (x = 0; x < quant; x++){
	      			if (cur.moveToPosition(x)){
	      				String title = cur.getString(1);
	      				String synop = cur.getString(2);
	      				String url = cur.getString(3);
	      				HashMap<String, String> tempHash = new HashMap<String, String>();
	      				tempHash.put("title", title.toString());
	      				tempHash.put("synopsis", synop.toString());
	      				tempHash.put("year", url);
	      				System.out.println(title + " " + synop + " " + " " + url);
	      				
	      			}
	      		}
	      		
	      		System.out.println("onPostExecute functioning, searchKey is: " + searchString);
	    	   /*
	    	   ArrayList<HashMap<String, String>> myListToo = new ArrayList<HashMap<String, String>>();
	    	   ContentResolver cr = getActivity().getContentResolver();
	    	   Cursor cur = cr.query(ContentProviderThing.DisplayData.CONTENT_URI, ContentProviderThing.DisplayData.PROJECTION, null, null, null);
	    	   int quant = cur.getCount();
	    	   int x = 0;
	    	   for (x = 0; x < quant; x++){
	    		   if (cur.moveToPosition(x)){
	    			   String title = cur.getString(1);
	    			   String synop = cur.getString(2);
	    			   String url = cur.getString(3);
	    			   HashMap<String, String> tempHash = new HashMap<String, String>();
	    			   tempHash.put("title", title.toString());
	    			   tempHash.put("synopsis", synop.toString());
	    			   tempHash.put("year", url);
	    			   myListToo.add(tempHash);
	    			   }
	    		   }
	    	   */
	    	   // cre = cur;
	    	   // thisList = myListToo;
	    	   // cre = cur;
	    	   // notify list to update with new data.
	    	   // buildList();
	    	   // lv = (ListView)getActivity().findViewById(R.id.listId1);
	    	   // construct hashmap for returns.
	    	   // SimpleAdapter sa = new SimpleAdapter(getActivity().getApplicationContext(), myListToo, R.layout.list_row, new String[] { "title", "synopsis", "year"}, new int[] { R.id.titleText, R.id.synopsisText, R.id.yearText});
	    	   // lv.setAdapter(sa);
	    	   // buildList();
	       }
	       
	    }
    
    /*
    public class ResponseReceiver extends BroadcastReceiver {
    	   public static final String RESPONSE_EVENT =
    	      "com.example.javatooappweekone.MESSAGE_PROCESSED";
    	   @Override
    	    public void onReceive(Context context, Intent intent) {
    	       searchResults = intent.getStringExtra(CycleService.OUT_MESSAGE);
    	       buildList();
    	       // result.setText(text);
    	    }
    	}
*/
/*
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}
    */
}