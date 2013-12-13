package com.example.thewebviewactivity;

import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class TheWebViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_the_web_view);
		
		
		Intent intent = getIntent();
		Uri data = intent.getData();
      	URL url = null;
      	
      	try {
       	 	url = new URL(data.getScheme(), data.getHost(),
                               data.getPath());
      	  } catch (Exception e) {
       	 	e.printStackTrace();
       	}
      	
      	
      	WebView webView = (WebView) findViewById(R.id.webView1); 
      	WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
       	webView.setWebViewClient(new newWebViewClient());
       	webView.loadUrl(url.toString());
	}

	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.the_web_view, menu);
		return true;
	}

	private class newWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        
	    	view.loadUrl(url);
	        // view.loadUrl(url);
	        //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	        //startActivity(intent);
	        return false;
	    }
	    
	    
	
	}
	
}
