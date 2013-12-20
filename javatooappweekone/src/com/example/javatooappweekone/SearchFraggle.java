package com.example.javatooappweekone;
//Justin Khalil formerly of OCD, latterly of FSU
//Java-II Week Three
import java.io.Serializable;
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
import com.example.methodical.Filer;
import com.example.methodical.Grabber;

public class SearchFraggle extends Fragment {
	
	static ArrayList<HashMap<String, String>> thisList;
	Button sendButton;
	EditText et;
	String obj;
	String searchTerm;
	String searchResults;
	String searchString;
	static ArrayList<HashMap<String, String>> theListViewArray;
	public final static String DETAIL_KEY = "DETAILKEY";
	public final static String URL_KEY = "URLKEY";
	public final static String TITLE_KEY = "TITLEKEY";
	public final static String MY_LIST = "LISTKEY";
	public final static String PUBLIC_KEY = "KEYFORSEARCH";
	ListView lv;
	View view;
	Context context;
	Cursor cre;
	Filer fl;
	private ResponseReceiver receiver;
	private DetailListener detailListener;
	public interface DetailListener{
		public void onListItemClick(String title, String detail, String url);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		Log.e("TestLog", "onViewCreated running");
		super.onViewCreated(view, savedInstanceState);
		
		IntentFilter filter = new IntentFilter(ResponseReceiver.RESPONSE_EVENT);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        getActivity().registerReceiver(receiver, filter);
        if (savedInstanceState != null){
        	Log.e("TestLog", "found a saved instance state");
        	System.out.println("found a saved instance state");
    		thisList = (ArrayList<HashMap<String, String>>) savedInstanceState.getSerializable(MY_LIST);
    		searchString = (String) savedInstanceState.getString(PUBLIC_KEY);
    		et = (EditText) getActivity().findViewById(R.id.textField);
    		// okay, it seems that somehow android remembers the contents of an EditText field on restoreInstanceState.
    		// that's just weird.
    		sendButton = (Button)getActivity().findViewById(R.id.sendButton);
    		refreshList(thisList);
        } else {
        	fl = new Filer();
        	Log.e("TestLog", "no saved instance state");
        	System.out.println("no saved instance state");
        	// get string containing saveddata file contents.
            // if there are contents, it means the app has been run before.
        	try {
				String newstring = fl.readFromFile(getActivity().getApplicationContext(), "searchresults");
				System.out.println(newstring);
				if (newstring.length() > 1){
					populateTheList();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			// again, set these things.
			detailListener = (DetailListener) activity;
		} catch (ClassCastException e) {
			e.printStackTrace();
			Log.e("error", e.toString());
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// Almost utterly irrelevant, but present for archaeological purposes.
		setRetainInstance(true);
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
	}

	@Override
	public void setRetainInstance(boolean retain) {
		super.setRetainInstance(retain);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (thisList != null && !thisList.isEmpty()){
			// The saveinstancestate bundle is more sophisticated than I thought. I was under the impression that I needed to 
			// explicitly place each and every item I wanted to retrieve into the bundle with a key. The dynamics of its function
			// are still somewhat mysterious.
    		outState.putSerializable(MY_LIST, (Serializable) thisList);
    		et = (EditText)getActivity().findViewById(R.id.textField);
    		String st = et.getText().toString();
    		Log.e("outputting test", st);
    		outState.putString(PUBLIC_KEY, st);
    	}
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
				Intent messageIntent = new Intent(getActivity(), CycleService.class);
		    	messageIntent.putExtra(CycleService.IN_MESSAGE, searchTerm);
		    	getActivity().startService(messageIntent);
			}
		});
    	lv = (ListView)view.findViewById(R.id.listview_in_fragment);
		lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	// get cursor and contentresolver...
            	if (cre == null){
            		ContentResolver cr = getActivity().getContentResolver();
            		Cursor cur = cr.query(ContentProviderThing.DisplayData.CONTENT_URI, ContentProviderThing.DisplayData.PROJECTION, null, null, null);
            		cre = cur;
            	}
            	String item;
            	String item2;
            	String item3;
            	cre.moveToFirst();
            	// position cursor at correct index.
            	if (cre.moveToPosition(position)){
            		// get values...
            		item = cre.getString(1);
            		item2 = cre.getString(2);
            		item3 = cre.getString(3);
            		System.out.println(item);
            		System.out.println(item2);
            		System.out.println(item3);
            		// send values to new activity method.
            		openNewActivity(view, item, item2, item3);
            	}	
            }
        });
		return view;
	}

	public class LoadTask extends AsyncTask<Void, Void, Void> {
		@Override
	    protected Void doInBackground(Void...arg0){
			// get data from remote source using grabber's grabData method.
	    	Grabber gr = new Grabber();
	    	obj = gr.grabData(getActivity().getApplicationContext(), searchTerm);
	    	return null;
	    }
	    @Override
	    protected void onPostExecute(Void result){
	    	   
	    }
	}
	
	private void refreshList(ArrayList<HashMap<String, String>> savedList){
		thisList = savedList;
		lv = (ListView)getActivity().findViewById(R.id.listview_in_fragment);
		SimpleAdapter sa = new SimpleAdapter(getActivity().getApplicationContext(), thisList, R.layout.list_row, new String[] { "title", "synopsis", "year"}, new int[] { R.id.titleText, R.id.synopsisText, R.id.yearText});
		lv.setAdapter(sa);
	}
		
	
	private void populateTheList(){
		// boilerplate listbuilding, with some fragment embellishments.
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
		thisList = myList;
	}
	
	public void openNewActivity(View view, String title, String detail, String url) {
        // Do something in response to button
		DetailFraggle fragment = (DetailFraggle) getFragmentManager().findFragmentById(R.id.detail_frag_id);
			if (fragment == null || ! fragment.isInLayout()) {
				// open activity
				Intent intent = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
			    intent.putExtra(TITLE_KEY, title);
			    intent.putExtra(DETAIL_KEY, detail);
			    intent.putExtra(URL_KEY, url);
			    startActivity(intent);
			} else {
				detailListener.onListItemClick(title, detail, url);
			} 
	}
	
	public class ResponseReceiver extends BroadcastReceiver {
		public static final String RESPONSE_EVENT = "com.example.javatooappweekone.MESSAGE_PROCESSED";
 	    @Override
 	    public void onReceive(Context context, Intent intent) {
 	    	
 	    	searchResults = intent.getStringExtra(CycleService.OUT_MESSAGE);
 	    	populateTheList();
 	    }
 	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null){
        	thisList = (ArrayList<HashMap<String, String>>) savedInstanceState.getSerializable(MY_LIST);
    		searchString = (String) savedInstanceState.getString(PUBLIC_KEY);
    		et = (EditText) getActivity().findViewById(R.id.textField);
    		// okay, it seems that somehow android remembers the contents of an EditText field on restoreInstanceState.
    		// that's just weird.
    		sendButton = (Button)getActivity().findViewById(R.id.sendButton);
    		refreshList(thisList);
        } else {
        	fl = new Filer();
        	// get string containing saveddata file contents.
            // if there are contents, it means the app has been run before.
        	try {
				String newstring = fl.readFromFile(getActivity().getApplicationContext(), "searchresults");
				if (newstring.length() > 1){
					populateTheList();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		getActivity().unregisterReceiver(receiver);
	}
	
	
}
