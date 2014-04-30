package com.example.hwwv2;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.Toast;

public class search_Activity extends Activity{

	HopeDbAdapter ourDb= new HopeDbAdapter(this);
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		handleIntent(getIntent());
	}

	@Override
	public void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		
		handleIntent(intent);
	}
	
	public void handleIntent(Intent intent){
		
		if(intent.ACTION_SEARCH.equals(intent.getAction())){
			
			String query= intent.getStringExtra(SearchManager.QUERY);
					
			Cursor c=ourDb.getWordMatches(query, null);
			
			Toast.makeText(getApplicationContext(),"ggggggggggggg", Toast.LENGTH_SHORT).show();// make a toast 
			return;
			
			
		}
	}
	
	
	
}
