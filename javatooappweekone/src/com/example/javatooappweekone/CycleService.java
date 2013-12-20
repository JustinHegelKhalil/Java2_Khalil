package com.example.javatooappweekone;
//Justin Khalil formerly of OCD, latterly of FSU
//Java-II Week Three
import java.util.ArrayList;
import java.util.HashMap;

import android.app.IntentService;
import android.content.Intent;

import com.example.javatooappweekone.SearchFraggle.ResponseReceiver;
import com.example.methodical.Grabber;

public class CycleService extends IntentService {
	static String searchTerm;
	static ArrayList<HashMap<String, String>> thisList;
	
	public final static String RESULT_COOL = "loader";
	public final static String OUT_MESSAGE = "output_message";
	public final static String IN_MESSAGE = "input_message";
	
	public CycleService(){
		super("CycleService");
	}
	
	public static void setSearchTerm(String st){
		searchTerm = st;
	}
	
	@Override
	protected void onHandleIntent(Intent intent){
		// receive search term for get operation.
		String msg = intent.getStringExtra(IN_MESSAGE);
		// get grabber ready.
		Grabber gr = new Grabber();
		// get remote data.
		String objectString = gr.grabData(getApplicationContext(), msg);
		// Filer fl = new Filer();
		// fl.writeToFile(getApplicationContext(), objectString, "searchresults");
		// make intent for returning string.
		Intent broadcastIntent = new Intent();
		// set intent target parameters.
		broadcastIntent.setAction(ResponseReceiver.RESPONSE_EVENT);
		// I don't know why, but categories 
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		// set output content.
		broadcastIntent.putExtra(OUT_MESSAGE, objectString);
		// send message.
		sendBroadcast(broadcastIntent);
	}
	
}
