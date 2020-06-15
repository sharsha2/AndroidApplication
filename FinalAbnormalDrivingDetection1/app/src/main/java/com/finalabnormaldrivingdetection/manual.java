package com.finalabnormaldrivingdetection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class manual extends Activity implements SensorEventListener{
	private float lastX, lastY, lastZ;
	private SQLiteDatabase db;
	private Button b1;
	private SensorManager sensorManager;
	private Sensor accelerometer;

	private float deltaXMax = 0;
	private float deltaYMax = 0;
	private float deltaZMax = 0;

	private float deltaX = 0;
	private float deltaY = 0;
	private float deltaZ = 0;

	private float vibrateThreshold = 0;

	private TextView currentX, currentY, currentZ, maxX, maxY, maxZ;

	public Vibrator v;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manual);
		createDatabase();
		
		initializeViews();

		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
			// success! we have an accelerometer

			accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
			vibrateThreshold = accelerometer.getMaximumRange() / 2;
		} else {
			// fai! we dont have an accelerometer!
		}
		
		//initialize vibration
		v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
/*************************/
		b1=(Button)findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "jhghj",Toast.LENGTH_SHORT).show();		
				Intent login=new Intent(getApplicationContext(),view.class);
				startActivity(login);	
			
			}
		});
	}

	public void initializeViews() {
		currentX = (TextView) findViewById(R.id.currentX);
		currentY = (TextView) findViewById(R.id.currentY);
		currentZ = (TextView) findViewById(R.id.currentZ);

		maxX = (TextView) findViewById(R.id.maxX);
		maxY = (TextView) findViewById(R.id.maxY);
		maxZ = (TextView) findViewById(R.id.maxZ);
	}

	//onResume() register the accelerometer for listening the events
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}

	//onPause() unregister the accelerometer for stop listening the events
	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	public void onSensorChanged(SensorEvent event) {

		// clean current values
		displayCleanValues();
		// display the current x,y,z accelerometer values
		displayCurrentValues();
		// display the max x,y,z accelerometer values
		displayMaxValues();

		// get the change of the x,y,z values of the accelerometer
		deltaX = Math.abs(lastX - event.values[0]);
		deltaY = Math.abs(lastY - event.values[1]);
		deltaZ = Math.abs(lastZ - event.values[2]);

		// if the change is below 2, it is just plain noise
		if (deltaX < 2)
			deltaX = 0;
		if (deltaY < 2)
			deltaY = 0;
		if ((deltaZ > vibrateThreshold) || (deltaY > vibrateThreshold) || (deltaZ > vibrateThreshold)) {
			v.vibrate(50);
		}
	}

	public void displayCleanValues() {
		currentX.setText("0.0");
		currentY.setText("0.0");
		currentZ.setText("0.0");
		/**********************/
		maxX.setText("0.0");
		maxY.setText("0.0");
		maxZ.setText("0.0");
		/*****************/
	}

	// display the current x,y,z accelerometer values
	public void displayCurrentValues() {
		currentX.setText(Float.toString(deltaX));
		currentY.setText(Float.toString(deltaY));
		currentZ.setText(Float.toString(deltaZ));
	}

	// display the max x,y,z accelerometer values
	public void displayMaxValues() {
		if (deltaX > deltaXMax) {
			deltaXMax = deltaX;
			maxX.setText(Float.toString(deltaXMax));
		}
		if (deltaY > deltaYMax) {
			deltaYMax = deltaY;
			maxY.setText(Float.toString(deltaYMax));
		}
		if (deltaZ > deltaZMax) {
			deltaZMax = deltaZ;
			maxZ.setText(Float.toString(deltaZMax));
		}
	   /*********************/
		double avg=(deltaXMax+deltaYMax+deltaZMax)/3;
		if(avg>=10.43067&&avg<=13.35)
		{
			//Toast.makeText(this, "Sudden Right Turn",Toast.LENGTH_SHORT).show();
			String query = "INSERT INTO drives values('Sudden Right Turn');";
	        db.execSQL(query);
	    
		}
		else if(avg>=5.821667&&avg<=10.91133)
		{
			//Toast.makeText(this, "Side Slipping",Toast.LENGTH_SHORT).show();
			String query = "INSERT INTO drives values('Side Slipping');";
	        db.execSQL(query);
	    
		}
		else if(avg>=8.966667&&avg<=17.45267)
		{
			//Toast.makeText(this, "WAVE detected",Toast.LENGTH_SHORT).show();
			String query = "INSERT INTO drives values('WAVE');";
	        db.execSQL(query);
	    
		}
		else if(avg>=5.75&&avg<=8.969667)
		{
			//Toast.makeText(this, "Sudden Left Turn",Toast.LENGTH_SHORT).show();
			String query = "INSERT INTO drives values('Sudden Left Turn');";
	        db.execSQL(query);
	    
		}
		else if(avg>=9.208333&&avg<=16.06367)
		{
			//Toast.makeText(this, "Sudden U Turn",Toast.LENGTH_SHORT).show();
			String query = "INSERT INTO drives values('Sudden U Turn');";
	        db.execSQL(query);
	    
		}
		else if(avg>=5.815&&avg<=11.00633)
		{
			//Toast.makeText(this, "Sudden Breaking",Toast.LENGTH_SHORT).show();
			String query = "INSERT INTO drives values('Sudden Breaking');";
	        db.execSQL(query);
	    
		}
		/*else
		{
			//Toast.makeText(this, "Normal Driving",Toast.LENGTH_SHORT).show();
			//Toast.makeText(this, "Processing",Toast.LENGTH_SHORT).show();
			
		}	*/
			
	
	}

	protected void createDatabase(){
        db=openOrCreateDatabase("drive", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS drives(cno VARCHAR);");
    }

}
