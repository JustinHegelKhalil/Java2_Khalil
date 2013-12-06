package com.example.javatooappweekone;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.example.methodical.Filer;

public class Preppie extends IntentService {

	String searchResults;
	String searchTerm;
	public static final String MESSENGER_KEY = "messenger_key";
	public static final String MESSAGE_SEARCH = "searchterm_key";
	
	public Preppie(){
		super("Preppie");
	}
	
	@Override
	protected void onHandleIntent(Intent intent){
		searchResults = null;
		System.out.println("searching");
		Bundle extras = intent.getExtras();
		Messenger msgr = (Messenger) extras.get(MESSENGER_KEY);
		
		searchTerm = extras.getString(MESSAGE_SEARCH);
		System.out.println("from preppie" + searchTerm);
		Filer fl = new Filer();
		
		
        String searchbj = fl.readFromFile(getApplicationContext(), "searchresults");
		
		
		Message message = Message.obtain();
		message.arg1 = Activity.RESULT_OK;
		message.obj = searchbj;
		// 
		try {
			msgr.send(message);
			System.out.println("trying this" + searchbj);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
