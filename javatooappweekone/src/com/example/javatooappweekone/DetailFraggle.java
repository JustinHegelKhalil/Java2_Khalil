package com.example.javatooappweekone;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailFraggle extends Fragment implements Layer_one.TriggerListener {

	public final static String WEBVIEW_KEY = "WEBVIEWKEY";
	TextView tv1;
	TextView tv2;
	TextView tv3;
	ImageView ivp;
	Button butt;
	Bitmap bitmap = null;
    InputStream in = null;
    BufferedOutputStream out = null;
    View view;
    Context context;
    // private TriggerListener triggerListener;
    
    @Override
    public void onItemSelect(String one, String two, String three){
    	tv1 = (TextView)view.findViewById(R.id.titleText);
    	tv2 = (TextView)view.findViewById(R.id.detailText);
    	tv3 = (TextView)view.findViewById(R.id.urlText);
    	tv1.setText(one);
    	tv2.setText(two);
    	tv3.setText(three);
    	tv1.setMovementMethod(new ScrollingMovementMethod());
    	tv2.setMovementMethod(new ScrollingMovementMethod());
    	tv3.setMovementMethod(new ScrollingMovementMethod());
    	butt = (Button)view.findViewById(R.id.openWebView);
    	ivp = (ImageView)view.findViewById(R.id.posterImage);
    	new LoadTask().execute();
    }
    
    
	
 	
 	
    
    public static void setValues(String one, String two, String three){
    	
    }
    
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		view = inflater.inflate(R.layout.activity_detail, container, false);
		
		butt = (Button)view.findViewById(R.id.openWebView);
		butt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = getActivity().getIntent();
				
				// Filer fl = new Filer();
				// String url = intent.getStringExtra(Layer_one.URL_KEY);
				String title = intent.getStringExtra(Layer_one.TITLE_KEY);
				// String linkURL = fl.readFromFile(getApplicationContext(), title);
				
				openNewActivity(view, title);
				// TODO Auto-generated method stub
				
			}
		});
		// TODO Auto-generated method stub
		return view;
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context = getActivity();
		// triggerListener = (TriggerListener)context;
		((Layer_one)context).triggerListener = this;
		
		
		
		
		
	}
    
	public class LoadTask extends AsyncTask<Void, Void, Void> {
	       // new asynctask for image loading
	    	
		Bitmap pic;
	       @Override
	       protected Void doInBackground(Void...arg0){
	    	   try {	    		   
	    		   // Intent intent = getActivity().getIntent();
	    		   String url = tv3.getText().toString();
	    				   // intent.getStringExtra(Layer_one.URL_KEY);
	   				URL newurl = new URL(url); 
	   				pic = BitmapFactory.decodeStream(newurl.openConnection().getInputStream()); 
	   				
	   				
	   				
	   		} catch (MalformedURLException e) {
	   			// TODO Auto-generated catch block
	   			e.printStackTrace();
	   		} catch (IOException e) {
	   			// TODO Auto-generated catch block
	   			e.printStackTrace();
	   		}
	    	   return null;
	       }
	       @Override
	       protected void onPostExecute(Void result){
	    	   ivp.setImageBitmap(pic);
	    	   
	       } 
				
	    	   
	}
	
	public void openNewActivity(View view, String url) {
		
		String p1 = "http://www.google.com/search?as_q=";
		String p2 = url.replace(" ", "+");
		String combiner = p1+p2;
		// String testingString = "http://www.google.com";
        // Do something in response to button
    	Intent intent = new Intent(getActivity(), WebViewActivity.class);
    	// Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(combiner));
    	intent.putExtra(WEBVIEW_KEY, combiner);
    	startActivity(intent);
    }
	
}
