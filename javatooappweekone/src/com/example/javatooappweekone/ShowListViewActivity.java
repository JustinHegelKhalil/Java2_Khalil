package com.example.javatooappweekone;

// import java.util.ArrayList;
// import java.util.HashMap;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
// import android.widget.TextView;

import com.example.methodical.Filer;

public class ShowListViewActivity extends Activity {

	ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_list_view);
		// Show the Up button in the action bar.
		setupActionBar();
		
		lv = (ListView)findViewById(R.id.listId);
		View listHeader = this.getLayoutInflater().inflate(R.layout.list_header, null);
		lv.addHeaderView(listHeader);
		populateList();
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
		getMenuInflater().inflate(R.menu.show_list_view, menu);
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
	public void populateList(){
		Filer fl = new Filer();
		String jsonResults = fl.readFromFile(getApplicationContext(), "searchresults");
		ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
		
		try {
			JSONObject jsobject = new JSONObject(jsonResults);
			Log.e("json", "first object done");
			JSONObject jso = jsobject.getJSONObject("responseData");
			Log.e("json", "second object done");
			JSONArray arrayObject = jso.getJSONArray("entries");
			Log.e("json", "third object done");
			int arraySize = arrayObject.length();
			// TextView tv = (TextView)findViewById(R.id.textable);
			// tv.setText(String.valueOf(arraySize));
			int i;
			for (i = 0; i < arraySize; i++){
				JSONObject current = arrayObject.getJSONObject(i);
				String title = current.getString("title");
				Spanned convert = Html.fromHtml(title);
				String contentSnip = current.getString("contentSnippet");
				Spanned convertCon = Html.fromHtml(contentSnip);
				String url = current.getString("url");
				// String divider = "\n";
				//tv.append(convert.toString());
				//tv.append(divider);
				//tv.append(convertCon.toString());
				//tv.append(divider);
				//tv.append(url);
				//tv.append(divider);
				HashMap<String, String> stuffMap = new HashMap<String, String>();
				stuffMap.put("title", convert.toString());
				stuffMap.put("contentSnippet", convertCon.toString());
				stuffMap.put("url", url);
				myList.add(stuffMap);
				
				
			}
			SimpleAdapter sa = new SimpleAdapter(this, myList, R.layout.list_row, new String[] { "title", "contentSnippet", "url"}, new int[] { R.id.titleText, R.id.contentSnippetText, R.id.urlText});
			lv.setAdapter(sa);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
