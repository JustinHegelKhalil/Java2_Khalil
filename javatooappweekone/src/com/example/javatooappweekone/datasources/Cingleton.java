package com.example.javatooappweekone.datasources;

import com.example.javatooappweekone.datasources.Cingleton;

public class Cingleton {
	
	private static Cingleton instance;
	public static String customVar="Hello";

	public static void initInstance() {
		if (instance == null) {
  // Create the instance
			instance = new Cingleton();
		}
	}

	public static Cingleton getInstance() {
		// Return the instance
		return instance;
	}

	private Cingleton() {
		// Constructor hidden because this is a singleton
	}

	public void customSingletonMethod(){
		// Custom method
	}
}