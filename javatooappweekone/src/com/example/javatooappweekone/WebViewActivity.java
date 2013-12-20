package com.example.javatooappweekone;
//Justin Khalil formerly of OCD, latterly of FSU
//Java-II Week Three
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class WebViewActivity extends Activity {

	TextView tv;
	// private WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		
		Intent intent = getIntent();
		String title = intent.getStringExtra(DetailActivity.WEBVIEW_KEY);
		// Show the Up button in the action bar.
		tv = (TextView)findViewById(R.id.webURL);
		tv.setText(title);
		setupActionBar();
		String p1 = "http://www.google.com/search?as_q=";
		String p2 = title.replace(" ", "+");
		// webView = (WebView) findViewById(R.id.webViewThing);
		//webView.setWebViewClient(new newWebViewClient());
		//WebSettings webSettings = webView.getSettings();
		
		//webSettings.setAllowContentAccess(true);
		//webSettings.setAllowUniversalAccessFromFileURLs(true);
		// webView.getSettings().setLoadWithOverviewMode(true);
	    // webView.getSettings().setUseWideViewPort(true); 
		//webView = new WebView(getApplicationContext());
		
		WebView webView = new WebView(this);
		// WebSettings webSettings = webView.getSettings();
		// webSettings.setJavaScriptEnabled(true);
		// webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		webView.setBackgroundColor(0x00000000);
		webView.setWebViewClient(new newWebViewClient());
		
		webView.loadUrl(p1+p2);

		setContentView(webView);
		// System.out.println(title);
		
		
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
		getMenuInflater().inflate(R.menu.web_view, menu);
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
