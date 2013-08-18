package com.example.texttospeach.service;

import com.example.texttospeach.util.ServiceHelper;

public class SMSSenderService {

	private static SMSSenderService instace;

	public static final SMSSenderService  getInstance(){
		if (instace == null)
			instace = new SMSSenderService();
		return instace;
	}
	
	public static void sendSMS(String sender, String message){
		ServiceHelper.sendPost(sender, message);
	}
	
}
