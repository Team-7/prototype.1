package com.example.hwwv2;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.hwwv2.MainActivity.PlaceholderFragment;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class Bmi_Activity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		disable();
		calculate();		
		
	}
	
	
	public void disable()// this method disable the Android keyBoard on a editText
	 {
		 
		 final EditText  currentBmi=(EditText) findViewById(R.id.editText3);
		 final EditText  name=(EditText) findViewById(R.id.editText1);
		 final EditText  age=(EditText) findViewById(R.id.ageValue);
		 final EditText  pBmi=(EditText) findViewById(R.id.editText4);
		 currentBmi.setOnTouchListener(new OnTouchListener() {

		        @Override
		        public boolean onTouch(View v, MotionEvent event) {
		            v.onTouchEvent(event);
		            InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		            if (imm != null) {
		                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		            }                
		            return true;
		        }				
		    });
		    name.setOnTouchListener(new OnTouchListener() {

		        @Override
		        public boolean onTouch(View v, MotionEvent event) {
		            v.onTouchEvent(event);
		            InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		            if (imm != null) {
		                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		            }                
		            return true;
		        }				
		    });
		    age.setOnTouchListener(new OnTouchListener() {

		        @Override
		        public boolean onTouch(View v, MotionEvent event) {
		            v.onTouchEvent(event);
		            InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		            if (imm != null) {
		                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		            }                
		            return true;
		        }				
		    });
		    pBmi.setOnTouchListener(new OnTouchListener() {

		        @Override
		        public boolean onTouch(View v, MotionEvent event) {
		            v.onTouchEvent(event);
		            InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		            if (imm != null) {
		                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		            }                
		            return true;
		        }				
		    });
	 }//end disable
	public  void calculate(){	
		 
		 final EditText height=(EditText) findViewById(R.id.heightvalue);
	        
		 final EditText weight=(EditText) findViewById(R.id.editText2);
      
		 final EditText  currentBmi=(EditText) findViewById(R.id.editText3);
       
		 final Button button1=(Button) findViewById (R.id.button1); 
       
		 final TextView comment=(TextView) findViewById(R.id.comment);

			button1.setOnClickListener(new Button.OnClickListener() {      
			
				@Override
				public void onClick(View v) {
					
					//retrieve the values of weight and height and assign it to an variables a and b
					String a=height.getText().toString();
					String b=weight.getText().toString();

					if(a.equals("") && b.equals("")) // check if both field are empty if true pop up a toast
					{						
							comment.setText(""); // set the comment in the textView to an empty string 
							currentBmi.setText(""); // set current BMI to an empty String							
							Toast.makeText(getApplicationContext(),"Empty Height or Weight", Toast.LENGTH_SHORT).show();// make a toast 
							return;
					}
					else
					if(a.equals("") || b.equals(""))// check if either of the field is empty
					{
						comment.setText(""); // set the comment in the textView to null
						currentBmi.setText(""); // set current BMI to an empty String						
						Toast.makeText(getApplicationContext(),"Empty Height or Weight", Toast.LENGTH_SHORT).show();
						return;						
					}
					else
					if(a.equals("0"))// check if either of the field is equals to zero
					{
						comment.setText(""); // set the comment in the textView to null
						currentBmi.setText(""); // set current BMI to an empty String						
						Toast.makeText(getApplicationContext(),"Sorry you can not divide by zero", Toast.LENGTH_SHORT).show();
						return;						
					}
					else
					{						
						double childHeight=Double.valueOf(height.getText().toString()); // get the child height and convert it into a double 						
						double childWeight= Double.valueOf(weight.getText().toString());// get the child weight and convert it into a double						
						double result= (childWeight/ (childHeight*childHeight)); // get the result 								
						currentBmi.setText(Double.toString(result)); // set the current BMI EditText to the actual result				
						
						if(result > 0 && result < 19)
						{  					
							comment.setText("Under Weight");						
						}
						if(result >=19 && result < 24)
						{ 			
							comment.setText("Normal");						
						}
						
						if(result >=24 && result < 30)
						{  				
							comment.setText("Over Weight");						
						}
						
						if(result >=30 && result <=40)
						{  				
							comment.setText("Obese");						
						}
						
						if(result > 40)
						{  				
							comment.setText("Morbidly Obese");
						}			
				    } //end else 
						
				}//end onClick method
					
				});				 
	 } // end calculate
	 

}
