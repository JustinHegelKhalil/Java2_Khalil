package com.example.javatooappweekone;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ListFraggle extends Fragment {

	
	/// implements Layer_one.TriggerListener
	
	static ArrayList<HashMap<String, String>> theListViewArray;
	public final static String DETAIL_KEY = "DETAILKEY";
	public final static String URL_KEY = "URLKEY";
	public final static String TITLE_KEY = "TITLEKEY";
	ListView lv;
	View view;
	Context context;
	Cursor cre;
	
	/*
	private RelayListenerOne relayListenerOne;
	
	public interface RelayListenerOne{
		public void relayToActivity(String itemOne, String itemTwo, String itemThree);
	}
	*/
	// private TriggerListener triggerListener;
	
	//@Override
    //public void triggerListRefresh(Activity activity){
    //	populateTheList();
    //}
	
	@Override
	public void onAttach(Activity activity){
	  super.onAttach(activity);
	  // relayListenerOne = (RelayListenerOne) activity;
	  context = getActivity();
	  // triggerListener = (TriggerListener)context;
	  // ((Layer_one)context).triggerListener = this;
	  
	}
	
	
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		
		view = inflater.inflate(R.layout.frag_layout_portrait_two, container, false);
		/*
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
		*/
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewStateRestored(savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	
	/*
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
	*/
	
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
					// relayListenerOne.relayToActivity(title, detail, url);
				  } 
		
    	
    }
	

}
