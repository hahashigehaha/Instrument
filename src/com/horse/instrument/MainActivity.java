package com.horse.instrument;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

public class MainActivity extends Activity {

	private InstrumentView test;
	
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				test.setProgress(2000,test);
				test.setNumber(12453);
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		test = (InstrumentView) findViewById(R.id.test);

		test.setCoord(0,300,500,1800,2400,3100,3600);
//		
//		test.setProgress(3000);
//		
//		test.setNumber(22345);
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				SystemClock.sleep(2000);
				Message message = handler.obtainMessage();
				message.what = 0 ;
				handler.sendMessage(message);
			}
		}).start();
		
	}

}
