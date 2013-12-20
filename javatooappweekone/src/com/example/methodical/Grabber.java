package com.example.methodical;
//Justin Khalil formerly of OCD, latterly of FSU
//Java-II Week Two
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;

public class Grabber {
	
	public String grabData(Context context, String searchTerm) {
		Spanned sp = Html.fromHtml(searchTerm);
		searchTerm = sp.toString();
		searchTerm = searchTerm.replace(" ", "%20");
		searchTerm = searchTerm.replaceAll("\"", "");
		// resolving a bunch of problems with string formatting.
		String returnString = "";
		Thread one = new Thread();
		one.start();
		while (one.isAlive()) {
            try {
				one.join(2000);
				if (returnString.length() < 1) {
					// construct url from components.
					// superfluous elements retained for future embellishments.
					String urlP1 = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=";
					String urlP2 = "9q98mr6xrq5j9hz99ms9kecv";
					String urlP3 = "&q=";
					searchTerm = searchTerm.replace(" ", "+");
					String urlP5 = "&page_limit=20";
					String url = urlP1 + urlP2 + urlP3 + searchTerm + urlP5;
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(url); 
					httpGet.setHeader("Content-type", "application/json");
					HttpResponse response;
					response = httpClient.execute(httpGet);
					if (response.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = response.getEntity();
						if (entity != null) {
							InputStream herein;
							herein = entity.getContent();
							String result = getStringFromStream(herein);
							// put search results in 'search-results' file.
							Filer fl = new Filer();
							fl.writeToFile(context, result, "searchresults");
							String finito = "";
							try {
								JSONObject json = new JSONObject(result);
								JSONArray countOne = json.getJSONArray("movies");
								fl.writeToFile(context, countOne.toString(), "arraycontents");
							} catch (Exception e) {
								e.printStackTrace();
							}
							returnString = finito;
						}
					}
				}
			} catch (ClientProtocolException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} 
		try {
			one.join();
			return returnString;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnString;
	}

	private static String getStringFromStream(InputStream streamer){
		BufferedReader Breeder = new BufferedReader(new InputStreamReader(streamer));
		StringBuilder stringBuilderThing = new StringBuilder();
		String line = null;
		try {
			// grabbing data from source, placing it into a string.
			while ((line = Breeder.readLine()) != null) {
				// here: appending to full string.
				stringBuilderThing.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				streamer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stringBuilderThing.toString();
	}
}
