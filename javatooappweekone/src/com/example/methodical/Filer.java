package com.example.methodical;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.util.Log;
// import android.util.Log;

public class Filer {

	public void writeToFile(Context context, String data, String filename) {
	    try {
	        OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
	        // been using external storage for a while, only now learning that such action is frowned
	        // upon by many.
	        osw.write(data);
	        osw.close();
	    }
	    catch (IOException e) {
	        Log.e("Exception", "filewriting failure: " + e.toString());
	    } 
	}


	public String readFromFile(Context context, String filename) {
		// I know that passing the context to a method is lame, but it's the easiest way to move on
		// to trickier problems.
		String returner = "";
		try {
			// get file data
	        InputStream inputStream = context.openFileInput(filename);
	        if ( inputStream != null ) {
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String receiveString = "";
	            StringBuilder stringBuilder = new StringBuilder();

	            while ( (receiveString = bufferedReader.readLine()) != null ) {
	                stringBuilder.append(receiveString);
	            }

	            inputStream.close();
	            returner = stringBuilder.toString();
	        }
	    }
	    catch (FileNotFoundException e) {
	        Log.e("login activity", "File not found: " + e.toString());
	    } catch (IOException e) {
	        Log.e("login activity", "Can not read file: " + e.toString());
	    }

	    return returner;
	}
}
