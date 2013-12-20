package com.example.methodical;
//Justin Khalil formerly of OCD, latterly of FSU
//Java-II Week Two
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.util.Log;

public class Filer {

	public void deleteFile(Context context, String filename) {
		// pretty sure I'm not using this method right now, but it's good to have one of these lying around just in case I 
		// decide to clear the proverbial slate.
		File dir = context.getFilesDir();
		File file = new File(dir, filename);
		file.delete();
	}
	
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

	            while ((receiveString = bufferedReader.readLine()) != null) {
	            	// dump string contents into stringbuilder.
	                stringBuilder.append(receiveString);
	            }

	            inputStream.close();
	            returner = stringBuilder.toString();
	        }
	    }
	    catch (FileNotFoundException e) {
	        Log.e("login activity", "Not found: " + e.toString());
	    } catch (IOException e) {
	        Log.e("login activity", "Can't read file: " + e.toString());
	    }

	    return returner;
	}
}
