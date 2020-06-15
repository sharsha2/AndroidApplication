package com.example.finalabnormaldrivingdetection;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class view extends Activity {
	private TextView t1,t2,t3,t4,t5,t6;
	private SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view);
		createDatabase();
		
		t1=(TextView)findViewById(R.id.textView1);
		t2=(TextView)findViewById(R.id.textView2);
		t3=(TextView)findViewById(R.id.textView3);
		t4=(TextView)findViewById(R.id.textView4);
		t5=(TextView)findViewById(R.id.textView5);
		t6=(TextView)findViewById(R.id.textView6);
		
	/**************************************/
		Cursor c=db.rawQuery("SELECT count(cno) FROM drives where cno ='WAVE'", null);
	  	int count=0;
	   	if(c.moveToNext())
	   	{
	   		String d=t1.getText().toString()+c.getString(0);
	   		t1.setText(d);
	   	}
	   	c=db.rawQuery("SELECT count(cno) FROM drives where cno ='Sudden Left Turn'", null);
	  	if(c.moveToNext())
	   	{
	   		String d=t2.getText().toString()+c.getString(0);
	   		t2.setText(d);
	   	}
	  	c=db.rawQuery("SELECT count(cno) FROM drives where cno ='Sudden Right Turn'", null);
	  	if(c.moveToNext())
	   	{
	   		String d=t3.getText().toString()+c.getString(0);
	   		t3.setText(d);
	   	}
	  	c=db.rawQuery("SELECT count(cno) FROM drives where cno ='Side Slipping'", null);
	  	if(c.moveToNext())
	   	{
	   		String d=t4.getText().toString()+c.getString(0);
	   		t4.setText(d);
	   	}
	  	c=db.rawQuery("SELECT count(cno) FROM drives where cno ='Sudden U Turn'", null);
	  	if(c.moveToNext())
	   	{
	   		String d=t5.getText().toString()+c.getString(0);
	   		t5.setText(d);
	   	}
	  	c=db.rawQuery("SELECT count(cno) FROM drives where cno ='Sudden Breaking'", null);
	  	if(c.moveToNext())
	   	{
	   		String d=t6.getText().toString()+c.getString(0);
	   		t6.setText(d);
	   	}
	}

	protected void createDatabase(){
        db=openOrCreateDatabase("drive", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS drives(cno VARCHAR);");
    }

}
