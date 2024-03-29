package com.dhliwayok.hopeworldwide;

import com.dhliwayok.hopeworldwide.HopeDbAdapter.HopeHelper;
import com.dhliwayok.hopeworldwide.HopeDbAdapter;
import com.dhliwayok.hopeworldwide.LoginActivity;
import com.dhliwayok.hopeworldwide.MainActivity;
import com.dhliwayok.hopeworldwide.Message;
import com.dhliwayok.hopeworldwide.R;
import com.dhliwayok.hopeworldwide.MainActivity.ActivitiesBroadcastReceiver;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	HopeDbAdapter mydb;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("CLEAR_STACK");
		BroadcastReceiver r;
		r = new ActivitiesBroadcastReceiver();
		registerReceiver(r, intentFilter);
		
		 mydb= new HopeDbAdapter(this);
		 
		 //insert fake child records (for testing)
		 if(mydb.insertChild(147741, "Tanaka Tsepo", "N", "2005-05-23", 'M') >0)
			 Message.message(MainActivity.this, "Child added successfully");	
		 
		 if(mydb.insertChild(254741, "Kalamba Timba", "Y", "2007-12-05", 'M') >0)
			 Message.message(MainActivity.this, "Child added successfully");
		 
		 //insert fake Community center (for testing)
		 if(mydb.insertECD(1, "Zanspruit", "Roodeport")>0)
			 Message.message(MainActivity.this, "ECD added successfully"); 
		 
		 if(mydb.insertECD(2, "Boxburg", "Roodeport")>0)
			 Message.message(MainActivity.this, "ECD added successfully"); 
		 
		 if(mydb.insertECD(3, "Midrand", "Roodeport")>0)
			 Message.message(MainActivity.this, "ECD added successfully"); 
		 
		 //insert fake community worker
		 if(mydb.insertCommunityWorker("ktdhl1", "kuda5", "Kuda Dhliwayo", 1)> 0)
			 Message.message(MainActivity.this, "CW added successfully");
		 
		 if(mydb.insertCommunityWorker("mdbuk1", "12345", "Djo Bukata", 2)> 0)
			 Message.message(MainActivity.this, "CW added successfully");
		 
		 if(mydb.insertCommunityWorker("ktmup1", "54321", "Kuda Mupeni", 3)> 0)
			 Message.message(MainActivity.this, "CW added successfully");
			
		 
		
		 
try {
	new Handler().postDelayed(new Runnable() {
	            @Override
	            public void run() {
	                final Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
	                MainActivity.this.startActivity(mainIntent);
	                MainActivity.this.finish();
	            }
	        }, 7000);//langing page with picture runs for 7seconds
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

		 
	
	}
	
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	class ActivitiesBroadcastReceiver extends BroadcastReceiver {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			finish();
		}
	}

	
	}


