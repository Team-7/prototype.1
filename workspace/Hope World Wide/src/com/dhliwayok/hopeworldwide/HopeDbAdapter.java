package com.dhliwayok.hopeworldwide;


import java.sql.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class HopeDbAdapter {//Adapter Class to help use database

		private HopeHelper hopeHelper;
		private SQLiteDatabase db;
		
		public HopeDbAdapter(Context context){
			
			hopeHelper = new HopeHelper(context);
			
		}
		
		//Login method, compares username and password
		public boolean Login(String username, String password) throws SQLException {
		db = hopeHelper.getWritableDatabase();// returns a writable db 
		String [] columns = {HopeHelper.USERNAME, HopeHelper.PASSWORD};// Columns that we actually need to log in  
		Cursor myCursor = db.query(HopeHelper.TABLE2, columns, HopeHelper.USERNAME+" = '"+username+"'", null, null, null, null);
		// query that looks up the username that is in the db 		
		String pass= "";
		if (myCursor != null) {
			
			while(myCursor.moveToNext()){ // move the cursor to the next row 
			int index = myCursor.getColumnIndex(HopeHelper.PASSWORD);
			pass = myCursor.getString(index);
			}
			
			if(pass.equals(password))	
				return true;
				
			}
		return false;
		}
		
		public String LoginName(String username, String password) throws SQLException {
			db = hopeHelper.getWritableDatabase();// returns a writable db 
			String [] columns = {HopeHelper.USERNAME, HopeHelper.PASSWORD, HopeHelper.W_NAME};// Columns that we actually need to log in  
			Cursor myCursor = db.query(HopeHelper.TABLE2, columns, HopeHelper.USERNAME+" = '"+username+"'", null, null, null, null);
			// query that looks up the username that is in the db 		
			String pass= "";
			String name = "";
			if (myCursor != null) {
				
				while(myCursor.moveToNext()){ // move the cursor to the next row 
				int index = myCursor.getColumnIndex(HopeHelper.PASSWORD);
				int index2 = myCursor.getColumnIndex(HopeHelper.W_NAME);
				pass = myCursor.getString(index);
				name = myCursor.getString(index2);
				}
				
				if(pass.equals(password))	
					return name;
					
				}
			return name;
			}
		
		public long insertChild(int id, String name, String od, String dateOfBirth, char gender){//inserts a child record in the database
		
			db = hopeHelper.getWritableDatabase();
			ContentValues myValues = new ContentValues();//raw queries can have problems 
			myValues.put(HopeHelper.KEY_ID, id);
			myValues.put(HopeHelper.C_NAME, name);
			myValues.put(HopeHelper.OD, od);
			myValues.put(HopeHelper.DATE_OF_BIRTH, dateOfBirth);
			long myId = db.insert(HopeHelper.TABLE1, null, myValues);
			
			return myId;
		}
		
		public long insertCommunityWorker(String username, String password, String name, int centerID){//insert a community worker in the database
			
			db = hopeHelper.getWritableDatabase();
			ContentValues myValues = new ContentValues();
			myValues.put(HopeHelper.USERNAME, username);
			myValues.put(HopeHelper.PASSWORD, password);
			myValues.put(HopeHelper.W_NAME, name);
			myValues.put(HopeHelper.CENTRE_ID, centerID);
			long myId = db.insert(HopeHelper.TABLE2, null, myValues);
			
			return myId;
		}
		
public long insertECD(int centerId, String cName, String location){//inserts community center 
			
			db = hopeHelper.getWritableDatabase();
			ContentValues myValues = new ContentValues();
			myValues.put(HopeHelper.CENTRE_ID, centerId);
			myValues.put(HopeHelper.CENTRE_NAME, cName);
			myValues.put(HopeHelper.LOCATION, location);
			long myId = db.insert(HopeHelper.TABLE3, null, myValues);
			
			return myId;
		}

public long insertBMI(int id, String date, int height, double weight, double score){//inserts a BMI record in database
	
	db = hopeHelper.getWritableDatabase();
	ContentValues myValues = new ContentValues();
	myValues.put(HopeHelper.BMI_ID, id);
	myValues.put(HopeHelper.DATE, date);
	myValues.put(HopeHelper.HEIGHT, height);
	myValues.put(HopeHelper.WEIGHT, weight);
	myValues.put(HopeHelper.SCORE, score);
	long myId = db.insert(HopeHelper.TABLE4, null, myValues);
	
	return myId;
}

public long insertChildBMI(int child_id, int BMI_id){//links child to a bmi record (association)
	
	db = hopeHelper.getWritableDatabase();
	ContentValues myValues = new ContentValues();
	myValues.put(HopeHelper.BMI_ID, BMI_id);
	myValues.put(HopeHelper.KEY_ID, child_id);
	long myId = db.insert(HopeHelper.TABLE5, null, myValues);
	
	return myId;
}

public int checkBMIrow(){//checks the number of rows in the BMI Table
	
	db = hopeHelper.getWritableDatabase();
	Cursor c = db.rawQuery("SELECT "+HopeHelper.BMI_ID+" FROM "+HopeHelper.TABLE4+";",null);

	int count = c.getCount();
	return count;
	
}

public boolean updateChild(String name,int id){//Updates a child by adding an id
	
	db = hopeHelper.getWritableDatabase();
	ContentValues myValues = new ContentValues();
	myValues.put(HopeHelper.BMI_ID, id);
	long myId = db.update(HopeHelper.TABLE1, myValues,HopeHelper.C_NAME+" = '"+name+"'", null);
	
	if(myId>0)
		return true;
	else			
		return false;
	
}


public String displayChildDetails(String name){//displays all child details (excluding BMI information)
	
	db = hopeHelper.getWritableDatabase();
	String [] columns = {HopeHelper.C_NAME, HopeHelper.OD, HopeHelper.DATE_OF_BIRTH, HopeHelper.GENDER};
	Cursor myCursor = db.query(HopeHelper.TABLE1, columns, HopeHelper.C_NAME+" = '"+name+"'", null, null, null, null);
	StringBuffer buffer = new StringBuffer();
	
	while(myCursor.moveToNext()){
		int index1 = myCursor.getColumnIndex(HopeHelper.C_NAME);
		int index2 = myCursor.getColumnIndex(HopeHelper.OD);
		int index3 = myCursor.getColumnIndex(HopeHelper.DATE_OF_BIRTH);
		int index4 = myCursor.getColumnIndex(HopeHelper.GENDER);
		
		String theName = myCursor.getString(index1);
		String od = myCursor.getString(index2);
		String date = myCursor.getString(index3);
		String sex = myCursor.getString(index4);
		buffer.append(theName+" "+od+" "+date+" "+sex+" \n");
	}
	return buffer.toString();
}

public String displayChildName(String name){//checks if a name exists and if t does it returns the name
	
	db = hopeHelper.getWritableDatabase();
	String [] columns = {HopeHelper.C_NAME, HopeHelper.OD, HopeHelper.DATE_OF_BIRTH, HopeHelper.GENDER};
	Cursor myCursor = db.query(HopeHelper.TABLE1, columns, HopeHelper.C_NAME+" = '"+name+"'", null, null, null, null);
	StringBuffer buffer = new StringBuffer();
	
	while(myCursor.moveToNext()){
		int index1 = myCursor.getColumnIndex(HopeHelper.C_NAME);
		
		String theName = myCursor.getString(index1);

		buffer.append(theName);
	}
	return buffer.toString();
}

public String displayChildAge(String name){//calculates child age from date of birth
	
	db = hopeHelper.getWritableDatabase();
	String [] columns = {HopeHelper.C_NAME, HopeHelper.OD, HopeHelper.DATE_OF_BIRTH, HopeHelper.GENDER};
	Cursor myCursor = db.query(HopeHelper.TABLE1, columns, HopeHelper.C_NAME+" = '"+name+"'", null, null, null, null);
	StringBuffer buffer = new StringBuffer();
	String date = "";
	while(myCursor.moveToNext()){
		int index3 = myCursor.getColumnIndex(HopeHelper.DATE_OF_BIRTH);
		
		date = myCursor.getString(index3);
		
	}
	Cursor miCursor = db.rawQuery("SELECT "+HopeHelper.DATE_OF_BIRTH+", round((SELECT julianday('now') - julianday('"+date+"'))/365, 0)FROM "+HopeHelper.TABLE1+";",null);
	String age = "";
	while(miCursor.moveToNext()){
		age = miCursor.getString(1);
		//buffer.append(age);
	}
	return age + " years";//buffer.toString();
}

public String getChildId(String name){// searches child by name and returns the child id
	db = hopeHelper.getWritableDatabase();
	String [] columns = {HopeHelper.C_NAME, HopeHelper.KEY_ID};
	Cursor myCursor = db.query(HopeHelper.TABLE1, columns, HopeHelper.C_NAME+" = '"+name+"'", null, null, null, null);
	
	String ID = "";
	
	while(myCursor.moveToNext()){
		int index5 = myCursor.getColumnIndex(HopeHelper.KEY_ID);
		ID = myCursor.getString(index5);
		//buffer.append(bmi);
	}
	return ID;
}

public String displayChildBMI(String name){//searches child by name and returns bmi information
	
	db = hopeHelper.getWritableDatabase();
	StringBuffer buffer = new StringBuffer();
	String ID = getChildId(name);
	String a ="";
	if(!ID.equals("")){
		
		String [] c = {HopeHelper.BMI_ID};
		Cursor b= db.query(HopeHelper.TABLE5, c, HopeHelper.KEY_ID+" = '"+ID+"'", null, null, null, null);

		while(b.moveToNext()){
			int index5 = b.getColumnIndex(HopeHelper.BMI_ID);
			a = b.getString(index5);
			//buffer.append(bmi);
		}
	}
	
	String result = "";
	if(a.equals("0"))
		result = "";
	else{
		String [] bmiColumns = {HopeHelper.SCORE, HopeHelper.BMI_ID};
		Cursor miCursor = db.query(HopeHelper.TABLE4, bmiColumns, HopeHelper.BMI_ID+" = '"+a+"'", null, null, null, null);
	
		while(miCursor.moveToNext()){
			int index = miCursor.getColumnIndex(HopeHelper.SCORE);
			result = miCursor.getString(index);
			buffer.append(result);
		}
	}
	
	return buffer.toString();
}		
		
		
	
	static class HopeHelper extends SQLiteOpenHelper{//Database class (Schema)
		
		//DATABASE NAME
		private static final String DATABASE_NAME = "myDb";
		//DATABASE VERSION
		private static final int DATABASE_VERSION = 3;
		//TABLE NAMES
		private static final String TABLE1 = "CHILD";
		private static final String TABLE2 = "COMMUNITY_WORKER";
		private static final String TABLE3 = "ECD";
		private static final String TABLE4 = "BMI";
		private static final String TABLE5 = "CHILD_BMI";
		
		//TABLE CHILD COLUMN NAMES
		private static final String KEY_ID = "_id";
		private static final String C_NAME = "Name";
		private static final String OD = "Odeama";
		private static final String DATE_OF_BIRTH = "Date_of_birth";
		private static final String GENDER = "Gender";
		
		//TABLE COMMUNITY WORKER COLUMN NAMES
		private static final String USERNAME = "_Username";
		private static final String PASSWORD = "Password";
		private static final String W_NAME = "Name";
		
		//TABLE COMMUNITY CENTRE COLUMN NAMES
		private static final String CENTRE_ID = "_Center_id";
		private static final String CENTRE_NAME = "Centre_name";
		private static final String LOCATION = "Location";
		
		//TABLE BMI COLUMN NAMES
		private static final String BMI_ID = "_BMI_id";
		private static final String DATE = "Date";
		private static final String HEIGHT = "Height";
		private static final String WEIGHT = "Weight";
		private static final String SCORE = "BMI_Score";
		
		//CREATE TABLE STATEMENTS
		//CREATE TABLE TABLE1 (_id INTEGER PRIMARY KEY,NAME VARCHAR(255));
		private static final String CREATE_TABLE1 = "CREATE TABLE "+ TABLE1+" ("+KEY_ID+" INTEGER (8) PRIMARY KEY, "+C_NAME+" VARCHAR (20), "+
				OD+" CHAR(1), "+DATE_OF_BIRTH+" DATE, "+GENDER+" CHAR(1));";
		
		private static final String CREATE_TABLE2 = "CREATE TABLE "+ TABLE2+" ("+USERNAME+" VARCHAR (10) PRIMARY KEY, "+ PASSWORD + " VARCHAR (10), "+
				 W_NAME + " VARCHAR (20), "+ CENTRE_ID + " INTEGER (3), FOREIGN KEY (" +CENTRE_ID+") REFERENCES "+ TABLE3+" ("+ CENTRE_ID+"));";
		
		private static final String CREATE_TABLE3 = "CREATE TABLE "+ TABLE3+" ("+CENTRE_ID + " INTEGER (3) PRIMARY KEY, "+ CENTRE_NAME + " VARCHAR (20), "+
				LOCATION + " VARCHAR (20));";
		
		private static final String CREATE_TABLE4 = "CREATE TABLE "+ TABLE4+" ("+ BMI_ID + " INTEGER (4) PRIMARY KEY, "+ DATE + " TIMESTAMP, "+
				HEIGHT + " INTEGER (3), "+WEIGHT+" DOUBLE (5), "+ SCORE+ " DOUBLE (5));";
		
		private static final String CREATE_TABLE5 = "CREATE TABLE "+ TABLE5+" ("+ BMI_ID + " INTEGER (4) NOT NULL REFERENCES "+ TABLE4+"("+ BMI_ID+"), "
		+ KEY_ID +" INTEGER (8) NOT NULL REFERENCES "+ TABLE1+"("+ KEY_ID+"), PRIMARY KEY ("+KEY_ID+","+BMI_ID+"));";
		
		private Context context;
		
		public HopeHelper (Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.context = context;
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
			try {
				db.execSQL(CREATE_TABLE1);
				db.execSQL(CREATE_TABLE2);
				db.execSQL(CREATE_TABLE3);
				db.execSQL(CREATE_TABLE4);
				db.execSQL(CREATE_TABLE5);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Message.message(context, ""+e);
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			// on upgrade drop older tables
	        try {
				db.execSQL("DROP TABLE IF EXISTS " + TABLE1);
				db.execSQL("DROP TABLE IF EXISTS " + TABLE2);
				db.execSQL("DROP TABLE IF EXISTS " + TABLE3);
				db.execSQL("DROP TABLE IF EXISTS " + TABLE4);
				db.execSQL("DROP TABLE IF EXISTS " + TABLE5);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        // create new tables
	        onCreate(db);
		}

		/* (non-Javadoc)
		 * @see android.database.sqlite.SQLiteOpenHelper#onOpen(android.database.sqlite.SQLiteDatabase)
		 */
		@Override
		public void onOpen(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			super.onOpen(db);
			if (!db.isReadOnly()) {
		        // Enable foreign key constraints
		        db.execSQL("PRAGMA foreign_keys=ON;");
		    }
		}
		
		
	}

	
	
}
