package com.example.farmerpal;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TopMarketDisplayActivity extends Activity {

	ListView lv;
	ArrayAdapter<String> arrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_top_market_display);
		
		lv = (ListView) findViewById(android.R.id.list);
		Intent intent = getIntent();
		String temp = intent.getStringExtra(FarmerDisplayActivity.EXTRA_MESSAGE);
	    ArrayList<String> topMarkets = new ArrayList<String>();
	    
	    for(String i:temp.split(","))
	    	topMarkets.add(i);
	    
	    arrayAdapter = new ArrayAdapter<String>(
                this, 
                android.R.layout.simple_list_item_1,
                topMarkets );

        lv.setAdapter(arrayAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.top_market_display, menu);
		return true;
	}

}
