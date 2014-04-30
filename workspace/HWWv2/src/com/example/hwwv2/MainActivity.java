package com.example.hwwv2;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	
	HopeDbAdapter ourDb;
	Login_Activity login;
	Bmi_Activity bmi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);	
		
		login=new Login_Activity();		
		
		add();
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	 public void add() // this method is used to add new worker and child 
	 {
		
		 ourDb= new HopeDbAdapter(this);
		 
		 if(ourDb.insertCommunityWorker("mdbuk1", "12345", "Djo", 1) >0)
				Message.message(MainActivity.this, "Community Worker added successfully");
			else
				Message.message(MainActivity.this, "Community Worker insert error");
		//Message.message(MainActivity.this, ourDb.displayChildDetails("Djo"));
		// try {
		//		startActivity(new Intent(MainActivity.this, LoginActivity.class));
		//	} catch (Exception e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
		//	}
		 if(ourDb.insertChild(12345678, "herve", "Y","29-07-1989", 'M',0) >0)
			 Message.message(MainActivity.this, "child added successfully");
		 else
				Message.message(MainActivity.this, "child insert error");		 
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
