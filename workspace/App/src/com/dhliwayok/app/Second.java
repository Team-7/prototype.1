package com.dhliwayok.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.DigitalClock;
import android.widget.TextView;

public class Second extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		
		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText("Look at the time sucker!");
		DigitalClock dc = (DigitalClock) findViewById(R.id.digitalClock1);
		//dc.notifyAll();
		
	}
}
