package com.example.javatooappweekone.datasources;

public class ItemThing {

	private static String firstString = null;
	private static String secondString = null;
	private static String thirdString = null;
	private static String fourthString = null;
	private static String fifthString = null;
	
	public static String getString(String keyString, int keyInt){
		String returner = "";
		String[] listArray = {firstString, secondString, thirdString, fourthString, fifthString};
		if (keyInt != 0){
			returner = listArray[keyInt];
		}
		if (keyString != null){
			int i = 0;
			for (i = 0; i <= listArray.length; i++){
				String currentString = listArray[i];
				if (keyString == currentString){
					returner = listArray[i];
				}
			}
		}
	return returner;	
	}
	
	public static void setStrings(String one, String two, String three, String four, String five){
		firstString = one;
		secondString = two;
		thirdString = three;
		fourthString = four;
		fifthString = five;
	}
	
	public static String[] getStringsArray(){
		String[] returner = {firstString, secondString, thirdString, fourthString, fifthString};
		return returner;
	}
}
