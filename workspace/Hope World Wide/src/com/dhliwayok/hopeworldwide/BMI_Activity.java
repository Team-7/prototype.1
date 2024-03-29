/**
 * 
 */
package com.dhliwayok.hopeworldwide;

import java.math.BigDecimal;

import com.dhliwayok.hopeworldwide.R;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author KudaD
 *
 */
public class BMI_Activity extends Activity {
	
	private EditText searchText;
	private EditText height;
	private EditText weight;
	private Button search;
	private Button calcBMI;
	private HopeDbAdapter myDb;
	private TextView childView;
	private TextView ageView;
	private TextView bmiView;
	private TextView comment;
	private TextView bmiComment;
	private TextView currBMI;
	String txt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bmi);
		

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("CLEAR_STACK");
		BroadcastReceiver r;
		r = new ActivitiesBroadcastReceiver();
		registerReceiver(r, intentFilter);
		
		myDb = new HopeDbAdapter(this);
		
		searchText = (EditText) findViewById(R.id.searchView1);
		search = (Button) findViewById(R.id.button2);
		childView = (TextView) findViewById(R.id.textView5);
		ageView = (TextView) findViewById(R.id.textView6);
		bmiView = (TextView) findViewById(R.id.textView7);
		calcBMI = (Button) findViewById(R.id.button1);
		height = (EditText) findViewById(R.id.heightvalue);
		weight = (EditText) findViewById(R.id.editText2);
		comment = (TextView) findViewById(R.id.errordisp);
		bmiComment = (TextView) findViewById(R.id.comFlag);
		currBMI = (TextView) findViewById(R.id.myCurrBMI);
		
calcBMI.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		currBMI.setText("");
		bmiComment.setText("");
		comment.setText("");
		
		txt = searchText.getText().toString();
		if (!myDb.displayChildName(txt).equals("")) {
			if (!height.getText().toString().equals("")
					&& !weight.getText().toString().equals("")) {//values entered

				if (!height.getText().toString().equals("0")) {//check if height not equals zero

					if (!weight.getText().toString().equals("0")) {//check if height not equals zero

						int h = Integer.parseInt(height.getText().toString());
						double w = Double.parseDouble(weight.getText().toString());

						if (w < 100 && h < 250) {
							BMI myBmi = new BMI(h, w);
							double score = myBmi.calculateBMI();
							currBMI.setText("" + score);
							checkScore(score);
							int id = myDb.checkBMIrow() + 1;//checks the highest bmi row number in the database 
							int childId = Integer.parseInt(myDb.getChildId(txt));//gets searched child's id number
							
							if (!myDb.displayChildName(txt).equals("")) {

								if (myDb.insertBMI(id, myBmi.getDate(), h, w,
										score) > 0) //stores bmi score, height, weight 
									Message.message(BMI_Activity.this,
											"BMI added successfully");
								else
									Message.message(BMI_Activity.this,
											"BMI insert error");

								if (myDb.insertChildBMI(childId, id) > 0)//links child to calculated bmi
									Message.message(BMI_Activity.this,
											"Child updated successfully");
								else
									Message.message(BMI_Activity.this,
											"Child update error");

							}
						} else {
							comment.setTextColor(Color.RED);
							comment.setText("Please check your height or weight values");
						}

					} else {
						comment.setTextColor(Color.RED);
						comment.setText("Weight cannot be 0");
					}
				} else {
					comment.setTextColor(Color.RED);
					comment.setText("Height cannot be 0");
				}
			} else if (height.getText().toString().equals("")) {//check if height field is empty
				comment.setTextColor(Color.RED);
				comment.setText("Enter height value");
			} else if (weight.getText().toString().equals("")) {//check if weight field is empty
				comment.setTextColor(Color.RED);
				comment.setText("Enter weight value");
			}
		}
		else{
			if (!height.getText().toString().equals("")
					&& !weight.getText().toString().equals("")) {//values entered

				if (!height.getText().toString().equals("0")) {//check if height not equals zero

					if (!weight.getText().toString().equals("0")) {//check if height not equals zero

						int h = Integer.parseInt(height.getText().toString());
						double w = Double.parseDouble(weight.getText().toString());

						if (w < 100 && h < 180) {
							double myH = (h*h);
							
							double score= (w/myH)*10000;
							currBMI.setText("" + BMI.round(score, 2, BigDecimal.ROUND_HALF_UP));
							checkScore(score);
						} else {
							comment.setTextColor(Color.RED);
							comment.setText("Please check your height or weight values");
						}

					} else {
						comment.setTextColor(Color.RED);
						comment.setText("Weight cannot be 0");
					}
				} else {
					comment.setTextColor(Color.RED);
					comment.setText("Height cannot be 0");
				}
			} else if (height.getText().toString().equals("")) {//check if height field is empty
				comment.setTextColor(Color.RED);
				comment.setText("Enter height value");
			} else if (weight.getText().toString().equals("")) {//check if weight field is empty
				comment.setTextColor(Color.RED);
				comment.setText("Enter weight value");
			}
			
		}

		
		
		
	}
});		

search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				height.setText("");
				weight.setText("");//set all the fields and views to blank
				currBMI.setText("");
				bmiComment.setText("");
				comment.setText("");
				
				txt = searchText.getText().toString();
				if(!myDb.displayChildName(txt).equals("")){	//check name typed is in database
					childView.setText(txt);
					
					if(!myDb.displayChildAge(txt).equals("")){//check for child's age
						
						String age = myDb.displayChildAge(txt);
							ageView.setText(age);
						}
						else
							ageView.setText("child has no age");
					
					if(!myDb.displayChildBMI(txt).equals("")){
						String bmi = myDb.displayChildBMI(txt);//get child bmi information
						bmiView.setText(bmi);
					}
					else
						bmiView.setText("No previous BMI");
				}
				else{
					childView.setText("No child found");
					ageView.setText("");
					bmiView.setText("");
					currBMI.setText("");
					}
			
					
			}
		});
		
	}
		
	
private void checkScore(double score){//checks which category bmi score belongs to 
	
	if(score > 0 && score < 19)
	{  					
		bmiComment.setTextColor(Color.RED);
		bmiComment.setText("Under Weight");						
	}
	if(score >=19 && score < 24)
	{ 			
		bmiComment.setTextColor(Color.BLUE);
		bmiComment.setText("Normal");						
	}
	
	if(score >=24 && score < 30)
	{  				
		bmiComment.setTextColor(Color.RED);
		bmiComment.setText("Over Weight");						
	}
	
	if(score >=30 && score <=40)
	{  				
		bmiComment.setTextColor(Color.RED);
		bmiComment.setText("Obese");						
	}
	
	if(score > 40)
	{  				
		bmiComment.setTextColor(Color.RED);
		bmiComment.setText("Morbidly Obese");
	}	
}
	

class ActivitiesBroadcastReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		finish();
	}
}

	

}


