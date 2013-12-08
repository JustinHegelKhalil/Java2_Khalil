package com.example.methodical;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Handler;

public class SpecHandler extends Handler {
	
	static ArrayList<HashMap<String, String>> thisList;
	
	public final static int RESULT_COOL = 100;
	
	/*
	
	@SuppressWarnings("unchecked")
	@Override
	public void handleMessage(Message msg){
		ArrayList<HashMap<String, String>> response = null;
		if (msg.arg1 == RESULT_COOL && msg.obj != null){
			
			try {
				response = (ArrayList<HashMap<String, String>>) msg.obj;
				System.out.println("list not refreshing");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("list not refreshing");
				e.printStackTrace();
			}
			
			// Layer_one.setContent(response);
		}
		
	}
	*/
	
}
