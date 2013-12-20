package com.example.javatooappweekone;
//Justin Khalil formerly of OCD, latterly of FSU
//Java-II Week Three
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
    
    @Override
    public void onItemSelect(String one, String two, String three){
    	// Get ids for elements.
    	tv1 = (TextView)view.findViewById(R.id.titleText);
    	tv2 = (TextView)view.findViewById(R.id.detailText);
    	tv3 = (TextView)view.findViewById(R.id.urlText);
    	butt = (Button)view.findViewById(R.id.openWebView);
    	ivp = (ImageView)view.findViewById(R.id.posterImage);
    	// set those elements values.
    	tv1.setText(one);
    	tv2.setText(two);
    	tv3.setText(three);
    	// Just for layout purposes, give them the ability to scroll.
    	tv1.setMovementMethod(new ScrollingMovementMethod());
    	tv2.setMovementMethod(new ScrollingMovementMethod());
    	tv3.setMovementMethod(new ScrollingMovementMethod());
    	// execute tiny AsyncTask for loading poster image.
    	new LoadTask().execute();
    }
    
    // I'm keeping this around because when I initially wrote this method out, everything else starting snapping into place very quickly,
    // so it remains because it is a good luck charm.
    // public static void setValues(String one, String two, String three){
    // }
    
    
    // A few of these methods don't do anything special. They are around mainly as signposts for my process.
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.activity_detail, container, false);
		// basic boilerplate here... elements, and clickListeners...
		butt = (Button)view.findViewById(R.id.openWebView);
		butt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Looking back on this, I did this in a really weird way. But it seems to work fine.
				// So maybe I'll try doing everything in this weird way to see if it is better, worse, or qualitatively indistinct
				// from the usual way.
				//Intent intent = getActivity().getIntent();
				// String title = intent.getStringExtra(Layer_one.TITLE_KEY);
				tv1 = (TextView)view.findViewById(R.id.titleText);
				String title = tv1.getText().toString();
				openNewActivity(view, title);
			}
		});
		return view;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = getActivity();
		// The syntax of the following line is INSANE. That said, it seems to work just fine.
		// ... took me a long time to figure out how to do this particular thing, though.
		// Really made the whole "custom service" thing seem much more useful than was initially apparent.
		((Layer_one)context).triggerListener = this;
		
		
		
		
		
	}
    
	public class LoadTask extends AsyncTask<Void, Void, Void> {
		Bitmap pic;
		@Override
		protected Void doInBackground(Void...arg0){
			try {	    		   
				String url = tv3.getText().toString();
	    		URL newurl = new URL(url); 
	   			pic = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
	   		} catch (MalformedURLException e) {
	   			e.printStackTrace();
	   		} catch (IOException e) {
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
		String combiner;
		try {
			String p1 = "http://www.google.com/search?as_q=";
			String p2 = url.replace("@ @", "+");
			combiner = p1+p2;
		} catch (Exception e) {
			try {
				String p1 = "http://www.google.com/search?as_q=";
				String p2 = url.replace(" ", "+");
				combiner = p1+p2;
			} catch (Exception e1) {
				String p1 = "http://www.google.com/search?as_q=";
				String partTwo = "part two";
				// String partTwo = url;
				//int point = url.indexOf(" ");
				//String p2 = url.substring(0, point);
				combiner = p1+partTwo;
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// this is in a try/catch block because every once in a while, my "replace" call crashes the entire app like a 
			// direct-hit from a proton torpedo entering a small thermal exhauset port right beneath the main port. And 
			// naturally, it didn't present itself until the last minute.
			e.printStackTrace();
		}
		
			// String testingString = "http://www.google.com";
			// Do something in response to button
			context = getActivity();
			Intent intent = new Intent(context, WebViewActivity.class);
			// Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(combiner));
			intent.putExtra(WEBVIEW_KEY, combiner);
			startActivity(intent);
		
    }
}
