package com.example.javatooappweekone;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.methodical.ContentProviderThing;
import com.example.methodical.Grabber;

public class SearchFraggle extends Fragment {
	
	
	static ArrayList<HashMap<String, String>> thisList;
	Button sendButton;
	EditText et;
	String obj;
	String searchTerm;
	String searchResults;
	static ArrayList<HashMap<String, String>> theListViewArray;
	public final static String DETAIL_KEY = "DETAILKEY";
	public final static String URL_KEY = "URLKEY";
	public final static String TITLE_KEY = "TITLEKEY";
	ListView lv;
	View view;
	Context context;
	Cursor cre;
	private ResponseReceiver receiver;
	
	
	private DetailListener detailListener;
	
	public interface DetailListener{
		public void onListItemClick(String title, String detail, String url);
	}
	
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			detailListener = (DetailListener) activity;
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("error", e.toString());
		}
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		IntentFilter filter = new IntentFilter(ResponseReceiver.RESPONSE_EVENT);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        getActivity().registerReceiver(receiver, filter);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		
		
		view = inflater.inflate(R.layout.frag_layout_portrait_one, container, false);
		sendButton = (Button)view.findViewById(R.id.sendButton);
    	sendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				et = (EditText) getActivity().findViewById(R.id.textField);
				searchTerm = et.getText().toString();
				// searchListener.onSearchClick(searchTerm);
				// searchString = searchKey;
		    	
		    	Intent messageIntent = new Intent(getActivity(), CycleService.class);
		    	messageIntent.putExtra(CycleService.IN_MESSAGE, searchTerm);
		    	getActivity().startService(messageIntent);
			}
		});
    	lv = (ListView)view.findViewById(R.id.listview_in_fragment);
		lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	// String item = ((TextView)view).getText().toString();
            	if (cre == null){
            		ContentResolver cr = getActivity().getContentResolver();
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
            		// View thisone = (View)findViewById(R.id.thisView);
            		openNewActivity(view, item, item2, item3);
            	}	
            }
        });
		return view;
	}
/*
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		System.out.println("/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
		
		
		
		
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
    	
    	//ItemThing firstItem = new ItemThing();
    	//firstItem.setStrings("the first string", "the second string", "the third string", "the fourth string", "the fifth string");
    	// ItemThing secondItem = new ItemThing(ItemThing.setStrings("the first string", "the second string", "the third string", "the fourth string", "the fifth string"));
    	
    	
    	
    	
	}
	*/
	
	public class LoadTask extends AsyncTask<Void, Void, Void> {
	       @Override
	       protected Void doInBackground(Void...arg0){
	    	   // get data from remote source using grabber's grabData method.
	    	   System.out.println("in fragment, doInBackground functioning, searchKey is: " + searchTerm);
	    	   Grabber gr = new Grabber();
	    	   obj = gr.grabData(getActivity().getApplicationContext(), searchTerm);
	    	   return null;
	       }
	       @Override
	       protected void onPostExecute(Void result){
	    	   System.out.println("in fragment, onPostExecute functioning, searchKey is: " + searchTerm);
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
	    	   System.out.println(obj);
	       }
	       
	    }

	private void populateTheList(){
		
		view = getView();
		ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
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
   				System.out.println(title + " " + synop + " " + " " + url);
   				myList.add(tempHash);
   			}
		}
   			
	   	lv = (ListView)getActivity().findViewById(R.id.listview_in_fragment);
		// construct hashmap for returns.
		SimpleAdapter sa = new SimpleAdapter(getActivity().getApplicationContext(), myList, R.layout.list_row, new String[] { "title", "synopsis", "year"}, new int[] { R.id.titleText, R.id.synopsisText, R.id.yearText});
		lv.setAdapter(sa);
		
	}
	
	public void openNewActivity(View view, String title, String detail, String url) {
        // Do something in response to button
		
		DetailFraggle fragment = (DetailFraggle) getFragmentManager().
				   findFragmentById(R.id.detail_frag_id);
				if (fragment == null || ! fragment.isInLayout()) {
					
				  // open activity
					Intent intent = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
			    	
			    	intent.putExtra(TITLE_KEY, title);
			    	intent.putExtra(DETAIL_KEY, detail);
			    	intent.putExtra(URL_KEY, url);
			    	startActivity(intent);
				  } else {
					  
					  //DetailFraggle.setValues(title, detail, url);
					  //fragment.tv1.setText(title, null);
					  //fragment.tv1.setText(title);
					// relayListenerOne.relayToActivity(title, detail, url);
					  detailListener.onListItemClick(title, detail, url);
				  } 
		
    	
    }
	public class ResponseReceiver extends BroadcastReceiver {
 	   public static final String RESPONSE_EVENT =
 	      "com.example.javatooappweekone.MESSAGE_PROCESSED";
 	   @Override
 	   public void onReceive(Context context, Intent intent) {
 	       searchResults = intent.getStringExtra(CycleService.OUT_MESSAGE);
 	       // buildList();
 	       populateTheList();
 	       // result.setText(text);
 	    }
 	}
	
	/*private void buildList(){
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
	
	/*
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		getActivity().unregisterReceiver(receiver);
	}
	*/
	
}
