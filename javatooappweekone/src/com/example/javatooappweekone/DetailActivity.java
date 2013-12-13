package com.example.javatooappweekone;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.methodical.Filer;

public class DetailActivity extends Activity {

	public final static String WEBVIEW_KEY = "WEBVIEWKEY";
	TextView tv1;
	TextView tv2;
	TextView tv3;
	ImageView ivp;
	Button butt;
	Bitmap bitmap = null;
    InputStream in = null;
    BufferedOutputStream out = null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		tv1 = (TextView) findViewById(R.id.titleText);
		tv2 = (TextView) findViewById(R.id.detailText);
		tv3 = (TextView) findViewById(R.id.urlText);
		butt = (Button) findViewById(R.id.openWebView);
		
		Intent intent = getIntent();
		String title = intent.getStringExtra(Layer_one.TITLE_KEY);
		String detail = intent.getStringExtra(Layer_one.DETAIL_KEY);
		String url = intent.getStringExtra(Layer_one.URL_KEY);
		tv1.setText(title);
		tv2.setText(detail);
		tv3.setText(url);
		ivp = (ImageView)findViewById(R.id.posterImage);
		
		
		// ivp.setImageURI(uri);
		new LoadTask().execute();
		// set imageview's image
		butt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = getIntent();
				View thisone = (View)findViewById(R.id.secondView);
				Filer fl = new Filer();
				String title = intent.getStringExtra(Layer_one.TITLE_KEY);
				String linkURL = fl.readFromFile(getApplicationContext(), title);
				
				openNewActivity(thisone, linkURL);
				// TODO Auto-generated method stub
				
			}
		});
		
        
       
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public class LoadTask extends AsyncTask<Void, Void, Void> {
	       // new asynctask for image loading
	    	
		Bitmap pic;
	       @Override
	       protected Void doInBackground(Void...arg0){
	    	   try {	    		   
	    		   Intent intent = getIntent();
	    		   String url = intent.getStringExtra(Layer_one.URL_KEY);
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
        // Do something in response to button
    	Intent intent = new Intent(this, WebViewActivity.class);
    	intent.putExtra(WEBVIEW_KEY, url);
    	startActivity(intent);
    }

}
