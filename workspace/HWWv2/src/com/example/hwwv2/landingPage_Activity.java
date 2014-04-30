package com.example.hwwv2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class landingPage_Activity  extends Activity{
	
	Button bt1;
	Button bt2;
	Button bt3;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.landing_page);
		
		bt1=(Button) findViewById(R.id.calculateBmi); // bmi button 
		bt2=(Button) findViewById(R.id.addChild); // add child button 
		bt3=(Button) findViewById(R.id.syncData);// sync button
		
		bt1.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				
				Intent intent = new Intent(landingPage_Activity.this, Bmi_Activity.class);
				startActivity(intent);
				
				}			
			
		});

	}
}


