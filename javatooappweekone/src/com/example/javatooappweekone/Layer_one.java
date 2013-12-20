package com.example.javatooappweekone;
//Justin Khalil formerly of OCD, latterly of FSU
//Java-II Week Two
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.database.Cursor;
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

import com.example.methodical.Filer;
import com.example.methodical.SpecHandler;

public class Layer_one extends Activity implements OnClickListener, SearchFraggle.DetailListener {
	
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
	@Override
	public void onListItemClick(String title, String detail, String url){
		System.out.println(title+detail+url);
		triggerListener.onItemSelect(title, detail, url);
	}
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.search_frag);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.layer_one, menu);
        return true;
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
    	// the logic of fragment/activity savedinstancestates is really weird. For some reason, this is necessary... 
    	// It might just be becase of a typo somewhere deep in one of these classes.
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
    @Override
	public void onClick(View arg0) {
		sendButton = (Button)findViewById(R.id.sendButton);
		sendButton.setText(R.string.loading);
		fl = new Filer();
		fl.writeToFile(getApplicationContext(), "previously run", "saveddata");
		sh = new SpecHandler(){		
    	@SuppressWarnings("unchecked")
		@Override
    	public void handleMessage(Message msg){
    		ArrayList<HashMap<String, String>> response = null;
    		if (msg.arg1 == RESULT_OK && msg.obj != null){
    			try {
    				response = (ArrayList<HashMap<String, String>>) msg.obj;
    				thisList = response;
    			} catch (Exception e) {
    				e.printStackTrace();
    			}	
    		}
    	}
		};
	
	}
}