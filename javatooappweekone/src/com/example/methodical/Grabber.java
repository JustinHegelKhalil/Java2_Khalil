package com.example.methodical;

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

public class Grabber {
	
	public String grabData(Context context, String searchTerm) {
		searchTerm = searchTerm.replace(" ", "%20");
		String returnString = "";
		Thread one = new Thread();
		one.start();
		while (one.isAlive()) {
            try {
				one.join(2000);
				if (returnString.length() < 1) {
					String url = "https://ajax.googleapis.com/ajax/services/feed/find?v=1.0&q=" + searchTerm;
					// String url = "http://pipes.yahoo.com/pipes/pipe.run?_id=MjmAJWS13RGC6TDRpgt1Yg&_render=json&numberinput1=40&textinput1=" + searchTerm;
					HttpClient httpClient = new DefaultHttpClient();
					// sending data to destination for horoscope response.
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
							// System.out.println(result);
							Filer fl = new Filer();
							fl.writeToFile(context, result, "searchresults");
							String finito = "";
							try {
								JSONObject json = new JSONObject(result);
								JSONObject jso = json.getJSONObject("responseData");
								JSONArray countOne = jso.getJSONArray("entries");
								// JSONObject jso = json.getJSONObject("value");
								// JSONArray countOne = jso.getJSONArray("items");
								fl.writeToFile(context, countOne.toString(), "arraycontents");
							} catch (Exception e) {
								// TODO Auto-generated catch block
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
			if (returnString.length() < 2){
				
			}
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
