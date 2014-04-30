package com.example.hwwv2;

import java.sql.Date;

import com.example.hwwv2.Message;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;


public class HopeDbAdapter {

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
		
		public long insertChild(int id, String name, String od, String dateOfBirth, char gender, int BMI_code){
		
			db = hopeHelper.getWritableDatabase();
			ContentValues myValues = new ContentValues();
			myValues.put(HopeHelper.KEY_ID, id);
			myValues.put(HopeHelper.C_NAME, name);
			myValues.put(HopeHelper.OD, od);
			myValues.put(HopeHelper.DATE_OF_BIRTH, dateOfBirth);
			myValues.put(HopeHelper.BMI_ID, BMI_code);
			long myId = db.insert(HopeHelper.TABLE1, null, myValues);
			
			return myId;
		}
		
		public long insertCommunityWorker(String username, String password, String name, int centerID){
			
			db = hopeHelper.getWritableDatabase();
			ContentValues myValues = new ContentValues();
			myValues.put(HopeHelper.USERNAME, username);
			myValues.put(HopeHelper.PASSWORD, password);
			myValues.put(HopeHelper.W_NAME, name);
			myValues.put(HopeHelper.CENTRE_ID, centerID);
			long myId = db.insert(HopeHelper.TABLE2, null, myValues);
			
			return myId;
		}
		
public long insertECD(int centerId, String cName, String location){
			
			db = hopeHelper.getWritableDatabase();
			ContentValues myValues = new ContentValues();
			myValues.put(HopeHelper.CENTRE_ID, centerId);
			myValues.put(HopeHelper.CENTRE_NAME, cName);
			myValues.put(HopeHelper.LOCATION, location);
			long myId = db.insert(HopeHelper.TABLE3, null, myValues);
			
			return myId;
		}

public long insertBMI(int id, String date, int height, double weight, double score){
	
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


public String displayChildDetails(String name){
	
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

public String displayChildName(String name){
	
	db = hopeHelper.getWritableDatabase();
	String [] columns = {HopeHelper.C_NAME};
	Cursor myCursor = db.query(HopeHelper.TABLE1, columns, HopeHelper.C_NAME+" = '"+name+"'", null, null, null, null);
	//StringBuffer buffer = new StringBuffer();
	
	String theName="";	
	while(myCursor.moveToNext()){
		int index1 = myCursor.getColumnIndex(HopeHelper.C_NAME);
		
		theName = myCursor.getString(index1);		
		
	}
	return theName;
}

public Cursor getWordMatches(String query,String [] columns){
	
	String selection= HopeHelper.C_NAME+"MATCH?";
	String [] selectionArgs= new String [] {query+ "*"};
	return query (selection, selectionArgs,columns);
}

private Cursor query (String selection, String [] selectionArgs, String [] columns){
	
	SQLiteQueryBuilder builder=new SQLiteQueryBuilder();
	
	builder.setTables(HopeHelper.TABLE1);
	db=hopeHelper.getReadableDatabase();
	Cursor cursor= builder.query(db, columns, selection, selectionArgs, null, null, null);
	
	if(cursor==null){
		return null;
	}
	else
	if(!cursor.moveToFirst()){
		cursor.close();
		return null;
	}
	return cursor;
}
		
		
		
	
	static class HopeHelper extends SQLiteOpenHelper{
		
		//DATABASE NAME
		private static final String DATABASE_NAME = "myDb";
		//DATABASE VERSION
		private static final int DATABASE_VERSION = 2;
		//TABLE NAMES
		private static final String TABLE1 = "CHILD";
		private static final String TABLE2 = "COMMUNITY_WORKER";
		private static final String TABLE3 = "ECD";
		private static final String TABLE4 = "BMI";
		
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
				OD+" CHAR(1), "+DATE_OF_BIRTH+" DATE, "+GENDER+" CHAR(1), "+BMI_ID+" INTEGER (2), FOREIGN KEY ("+ BMI_ID+") REFERENCES "+ TABLE4+" ("+ BMI_ID+" ));";
		
		private static final String CREATE_TABLE2 = "CREATE TABLE "+ TABLE2+" ("+USERNAME+" VARCHAR (10) PRIMARY KEY, "+ PASSWORD + " VARCHAR (10), "+
				 W_NAME + " VARCHAR (20), "+ CENTRE_ID + " INTEGER (1), FOREIGN KEY (" +CENTRE_ID+") REFERENCES "+ TABLE3+" ("+ CENTRE_ID+"));";
		
		private static final String CREATE_TABLE3 = "CREATE TABLE "+ TABLE3+" ("+CENTRE_ID + " INTEGER (1) PRIMARY KEY, "+ CENTRE_NAME + " VARCHAR (20), "+
				LOCATION + " VARCHAR (20));";
		
		private static final String CREATE_TABLE4 = "CREATE TABLE "+ TABLE4+" ("+ BMI_ID + " INTEGER (2) PRIMARY KEY, "+ DATE + " TIMESTAMP, "+
				HEIGHT + " INTEGER (3), "+WEIGHT+" DOUBLE (5), "+ SCORE+ " DOUBLE (5));";
		
		private Context context;
		
		public HopeHelper (Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.context = context;
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			Message.message(context, "Constructor called");
			try {
				db.execSQL(CREATE_TABLE1);
				db.execSQL(CREATE_TABLE2);
				db.execSQL(CREATE_TABLE3);
				db.execSQL(CREATE_TABLE4);
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        // create new tables
	        onCreate(db);
		}
	}

	
	
}

