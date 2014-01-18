package com.example.farmerpal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private String username, password;
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void authenticate(View view)
	{
		EditText etUsername = (EditText) findViewById(R.id.etUsernamePrompt);
		username = etUsername.getText().toString();
		
		EditText etPassword = (EditText) findViewById(R.id.etPasswordPrompt);
		password = etPassword.getText().toString();
		
		if(username.equals("f") && password.equals("f"))
		{
			intent = new Intent(this, FarmerDisplayActivity.class);
			startActivity(intent);
		}
		if(username.equals("m") && password.equals("m"))
		{
			intent = new Intent(this, MarketDisplayActivity.class);
			startActivity(intent);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu){
		Toast.makeText(getApplicationContext(), "Creating Data!", Toast.LENGTH_SHORT).show();
		createData();
		return true;
	}
	
	public void createData(){
		File marketdata = new File(Environment.getExternalStorageDirectory(),"marketdata.txt");
		File farmerdata = new File(Environment.getExternalStorageDirectory(),"mydata.txt");

		try {
			BufferedWriter bw1 = new BufferedWriter(new FileWriter(marketdata));
			BufferedWriter bw2 = new BufferedWriter(new FileWriter(farmerdata));
			bw2.append("0.00\n0.00\n01\n");

			bw1.append("Rice,10.0,10.0,50,Shop1-IndiraNagar,1,05\n");
			bw1.append("Tea,10.0,10.0,55,Shop2-IndiraNagar,1,06\n");
			bw1.append("Wheat,8.0,6.0,55,Shop3-Gachibowli,1,07\n");
			bw1.append("Linseed,10.0,10.0,80,Shop1-IndiraNagar,1,05\n");
			bw1.append("Mustard,8.0,6.0,100,Shop3-Gachibowli,1,07\n");
			bw1.append("Groundnut,8.0,6.0,100,Shop8-Gachibowli,1,17\n");
			bw1.append("CastorOil,8.0,9.0,100,Shop2-Gachibowli,1,11\n");
			bw1.append("Oilseeds,3.0,3.0,100,Shop5-Gachibowli,1,08\n");
			bw1.append("Rubber,8.0,6.0,100,Shop3-Gachibowli,1,07\n");
			bw1.append("Coffee,8.0,6.0,100,Shop8-Gachibowli,1,17\n");
			bw1.append("Coconut,8.0,6.0,100,Shop8-Gachibowli,1,17\n");
			bw1.append("FiberTypeCrops,8.0,9.0,100,Shop2-Gachibowli,1,11\n");
			bw1.append("Eggs,3.0,3.0,100,Shop5-Gachibowli,1,08\n");
			bw1.append("Jute,8.0,6.0,100,Shop3-Gachibowli,1,07\n");
			bw1.append("Sugarcane,8.0,6.0,100,Shop8-Gachibowli,1,17\n");

			bw1.append("Barley Seeds,10.0,10.0,5,Shop1-IndiraNagar,0,05\n");
			bw1.append("Mustard Seeds,8.0,9.0,6,Shop2-IndiraNagar,0,11\n");
			bw1.append("Jute Seeds,9.0,10.0,8,Shop3-Gachibowli,0,12\n");
			bw1.append("Tomato Seeds,5.0,4.0,5,Shop1-IndiraNagar,0,09\n");
			bw1.append("Ammonia,3.0,3.0,8,Shop5-Gachibowli,0,08\n");
			bw1.append("Maize Seeds,10.0,10.0,5,Shop1-IndiraNagar,0,05\n");
			bw1.append("Cotton Seeds,8.0,9.0,6,Shop2-IndiraNagar,0,11\n");
			bw1.append("Apricot Seeds,9.0,10.0,8,Shop3-Gachibowli,0,12\n");
			bw1.append("Marijuana Seeds,5.0,4.0,5,Shop1-IndiraNagar,0,09\n");
			bw1.append("Grain Seeds,3.0,3.0,8,Shop5-Gachibowli,0,08\n");
			bw1.append("Potash Fertilizer,10.0,8.0,100,Shop1-IndiraNagar,0,10\n");
			bw1.append("Iron Fertilizer,8.0,6.0,120,Shop3-Gachibowli,0,07\n");
			bw1.append("Fertilizer,8.0,8.0,110,Shop4-Gachibowli,0,13\n");
			bw1.append("Plouhing Tools,8.0,6.0,150,Shop2-IndiraNagar,0,07\n");
			bw1.append("Frame saw,10.0,8.0,130,Shop3-Gachibowli,0,10\n");
			bw1.append("Hayricks,11.0,9.0,100,Shop5-Gachibowli,0,14\n");

			bw1.flush();
			bw1.close();
			bw2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}