package com.example.javatooappweekone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.methodical.Filer;
import com.example.methodical.Grabber;

public class Layer_one extends Activity {

	TextView tv;
	Filer fl;
	String obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fl = new Filer();
        LinearLayout ll = new LinearLayout(this);
        String newstring = fl.readFromFile(getApplicationContext(), "saveddata");
        System.out.println(newstring);
        tv = new TextView(this);
        tv.setText("text");
        
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
        
        String these = fl.readFromFile(getApplicationContext(), "saveddata");
        
        // tv.setText(these);
        if (newstring.length() > 0){
        	// tv.setText(newstring);
           //  
        }
		// testline for startup
        // 
        Button save = new Button(this);
        ll.addView(save);
        ll.addView(tv);
        String get = fl.readFromFile(getApplicationContext(), "saveddata");
        
        save.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View arg0) {
				fl = new Filer();
				fl.writeToFile(getApplicationContext(), "Bunch of crap for testing", "saveddata");
				new Thread(new Runnable() { 
		              public void run(){
		            	  Grabber gr = new Grabber();
		            	  obj = gr.grabData(getApplicationContext(), "interview");
		            	  runOnUiThread(new Runnable() {
		            		  public void run() {
		            			 
		            			  }
		            	  });
		              }}).start();
				
				
					
				// tv.setText(obj);	
			}
			
		});
        ////////

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
          			  // requestor rq = new requestor();
          			  // String newstring = rq.horoscope(getApplicationContext(), "aries");
          			  // TextView tv = (TextView)findViewById(R.id.testingtext);
          			  // tv.setText(newstring);
          			  
       

          		  }
          	  });
            }}).start();
    }
}