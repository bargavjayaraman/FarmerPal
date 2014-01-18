package com.example.farmerpal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class AddItemActivity extends Activity {

	Spinner dropDown;
	ArrayAdapter<String> arrayAdapter;
	String selectedItem = "Rice";
	Double price = 0.0;
	EditText et;
	final static String MSG = "MSG";
	String sell;
	Intent parentIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);

		parentIntent = getIntent();
		sell = parentIntent.getStringExtra("msg");
		dropDown = (Spinner) findViewById(R.id.spinner1);
		et = (EditText) findViewById(R.id.editText1);
		ArrayList<String> items = new ArrayList<String>();
		items.add("Rice");
		items.add("Wheat");
		items.add("LinSeed");
		items.add("Milk");
		items.add("Eggs");
		items.add("Tomato");
		items.add("Sugarcane");
		items.add("Maize");
		items.add("Mustard");
		items.add("Jute");
		items.add("Rice Seeds");
		items.add("Wheat Seeds");
		items.add("Maize Seeds");
		items.add("Ammonia");
		items.add("Hacksaw Tools");
		items.add("Barley Seeds");
		items.add("LinSeed Seeds");
		items.add("Maize Seeds");
		items.add("Potash Fertilizer");
		items.add("Ploughing Tools");
		

		arrayAdapter = new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_spinner_item,
				items );
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dropDown.setAdapter(arrayAdapter);
	}

	public void send(View view)
	{
		String item = String.valueOf(dropDown.getSelectedItem());
		String val = et.getText().toString();
		String msg = item + "," + val;
		File f = new File(Environment.getExternalStorageDirectory(),"marketdata.txt");

		File mydata = new File(Environment.getExternalStorageDirectory(),"mydata.txt");
		String lat="",lang="",id="",addr="";
		ArrayList<String> lines = new ArrayList<String>();
		try {
			BufferedReader br;
			br = new BufferedReader(new FileReader(mydata));
			lat = br.readLine();
			lang = br.readLine();
			id = br.readLine();
			addr = br.readLine();
			br.close();
			
			mydata = new File(Environment.getExternalStorageDirectory(),"marketdata.txt");
			br = new BufferedReader(new FileReader(mydata));
			String line = "";
			while((line = br.readLine())!=null){
				lines.add(line);
			}
			br.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));

			bw.append(item+","+lat+","+lang+","+val+","+addr+","+sell+","+id+"\n");
			for(String i:lines){
				String[] items = i.split(",");
				if(items[0].equalsIgnoreCase(item) && items[6].equalsIgnoreCase(id))
					continue;
				bw.append(i);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Intent intent = new Intent(this, MarketDisplayActivity.class);
		intent.putExtra(MSG, msg);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_item, menu);
		return true;
	}
	
}