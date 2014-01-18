package com.example.farmerpal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MarketDisplayActivity extends ListActivity {

	ListView lv;
	ArrayAdapter<String> arrayAdapter;
	String value;
	String sell = "1";
	ArrayList<String> allitems = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		createData();
		
		setContentView(R.layout.activity_market_display);

		lv = (ListView) findViewById(android.R.id.list);
		String id="";
		allitems.clear();
		File f = new File(Environment.getExternalStorageDirectory(),"mydata.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			br.readLine();
			br.readLine();
			id = br.readLine();
			br.close();

			System.out.println(id);
			f = new File(Environment.getExternalStorageDirectory(),"marketdata.txt");
			br = new BufferedReader(new FileReader(f));
			String line = "";
			while((line = br.readLine())!=null){
				String[] items = line.split(",");
				if(items[5].equalsIgnoreCase(sell) && items[6].equalsIgnoreCase(id)){
					String item = items[0]+","+items[3];
					allitems.add(item);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		arrayAdapter = new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_list_item_1,
				allitems );

		lv.setAdapter(arrayAdapter);
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

		    @Override
		    public boolean onItemLongClick(AdapterView<?> parent, View view,
		            int position, long arg3) {
		    	allitems.remove(position);//where arg2 is position of item you click
		    	arrayAdapter.notifyDataSetChanged();

		        return false;
		    }

		});
	}

	public void populateBuyList(View view)
	{
		sell="1";
		String id="";
		allitems.clear();
		File f = new File(Environment.getExternalStorageDirectory(),"mydata.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			br.readLine();
			br.readLine();
			id = br.readLine();
			br.close();

			f = new File(Environment.getExternalStorageDirectory(),"marketdata.txt");
			br = new BufferedReader(new FileReader(f));
			String line = "";
			while((line = br.readLine())!=null){
				String[] items = line.split(",");
				if(items[5].equalsIgnoreCase(sell) && items[6].equalsIgnoreCase(id)){
					String item = items[0]+","+items[3];
					allitems.add(item);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		arrayAdapter = new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_list_item_1,
				allitems );

		lv.setAdapter(arrayAdapter);

	}

	public void populateSellList(View view)
	{
		sell="0";
		String id="";
		allitems.clear();
		File f = new File(Environment.getExternalStorageDirectory(),"mydata.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			br.readLine();
			br.readLine();
			id = br.readLine();
			br.close();

			f = new File(Environment.getExternalStorageDirectory(),"marketdata.txt");
			br = new BufferedReader(new FileReader(f));
			String line = "";
			while((line = br.readLine())!=null){
				String[] items = line.split(",");
				if(items[5].equalsIgnoreCase(sell) && items[6].equalsIgnoreCase(id)){
					String item = items[0]+","+items[3];
					allitems.add(item);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		arrayAdapter = new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_list_item_1,
				allitems );

		lv.setAdapter(arrayAdapter);

	}

	public void add(View view)
	{
		Intent intent = new Intent(this, AddItemActivity.class);
		intent.putExtra("msg", sell);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.market_display, menu);
		return true;
	}

	public void createData(){
		File mydata = new File(Environment.getExternalStorageDirectory(),"mydata.txt");

		try {
			BufferedWriter bw2 = new BufferedWriter(new FileWriter(mydata));
			bw2.append("8.0\n6.0\n07\nShop3-Gachibowli\n");
			bw2.close();
		}
		catch(Exception e){

		}
	}

}
