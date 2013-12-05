package com.example.javatooappweekone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.methodical.Filer;
import com.example.methodical.Grabber;

public class Layer_one extends Activity {
	public final static String PUBLIC_KEY = "KEYFORSEARCH";
	EditText et;
	Button sendButton;
	TextView tv;
	Filer fl;
	String obj;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fl = new Filer();
        sendButton = new Button(this);
        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(llp);
        sendButton.setText(R.string.send_button_text);
        
        String newstring = fl.readFromFile(getApplicationContext(), "saveddata");
        tv = new TextView(this);
        
        String searchbj = fl.readFromFile(getApplicationContext(), "searchresults");
        System.out.println(searchbj);
        try {
			JSONObject json = new JSONObject(searchbj);
			JSONObject jso = json.getJSONObject("value");
			JSONArray resultsArray = jso.getJSONArray("items");
			int i;
			for (i = 0; i <= resultsArray.length(); i++){
				JSONObject currentObject = resultsArray.getJSONObject(i);
				String title = currentObject.getString("title");
				tv.setText(title);
				System.out.println(title);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (newstring.length() > 0){
        	tv.setText(newstring);
        } else {
        	tv.setText("unused");
        }
		Button save = new Button(this);
		ll.addView(save);
        ll.addView(tv);
        et = new EditText(this);
        ll.addView(et);
        ll.addView(sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
    		
			@Override
			public void onClick(View arg0) {
				fl = new Filer();
				fl.writeToFile(getApplicationContext(), "previously run", "saveddata");
				new Thread(new Runnable() { 
		              public void run(){
		            	  String st = et.getText().toString();
		            	  System.out.println(st);
		            	  Grabber gr = new Grabber();
		            	  obj = gr.grabData(getApplicationContext(), st);
		            	  System.out.println(obj);
		            	  runOnUiThread(new Runnable() {
		            		  public void run() {
		            			  openSecondView(getCurrentFocus());
		            		  
		            		  }
		            	  });
		              }}).start();
				}
		});
        
        save.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View arg0) {
				fl = new Filer();
				fl.writeToFile(getApplicationContext(), "previously run", "saveddata");
				new Thread(new Runnable() { 
		              public void run(){
		            	  Grabber gr = new Grabber();
		            	  obj = gr.grabData(getApplicationContext(), "interview");
		            	  runOnUiThread(new Runnable() {
		            		  public void run() {
		            			  
		            		  }
		            	  });
		              }}).start();
				}
		});
        
        setContentView(ll);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.layer_one, menu);
        return true;
    }
    public void loadAndSetTextView(){
    	new Thread(new Runnable() { 
            public void run(){
          	  runOnUiThread(new Runnable() {
          		  public void run() {
          			// ultra temporary usage to eliminate warning
          			openListView("fkjhfs");
          			// to be deleted...
          			  
          		  }
          	  });
            }}).start();
    }
    public void openSecondView(View view) {
        // open second activity
    	Intent intent = new Intent(this, ShowListViewActivity.class);
    	String st = et.getText().toString();
    	intent.putExtra(PUBLIC_KEY, st);
        startActivity(intent);
    }
    private void openListView(String searchTerm){
    	
    }
}