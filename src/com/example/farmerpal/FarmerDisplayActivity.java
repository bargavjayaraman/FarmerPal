package com.example.farmerpal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class FarmerDisplayActivity extends ListActivity {

	ListView lv;
	ArrayAdapter<String> arrayAdapter;
	String type;
	final static String EXTRA_MESSAGE = "topMarkets";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_farmer_display);
		lv = (ListView) findViewById(android.R.id.list);

		type = "1";
		ArrayList<String> sellItems = new ArrayList<String>();
			sellItems.add("Rice");
			sellItems.add("Wheat");
			sellItems.add("LinSeed");
			sellItems.add("Milk");
			sellItems.add("Eggs");
			sellItems.add("Tomato");
			sellItems.add("Sugarcane");
			sellItems.add("Maize");
			sellItems.add("Mustard");
			sellItems.add("Jute");

		arrayAdapter = new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_list_item_1,
				sellItems );

		lv.setAdapter(arrayAdapter);
	}

	public void populateSellList(View view)
	{
		type = "1";
		ArrayList<String> sellItems = new ArrayList<String>();
		sellItems.add("Rice");
		sellItems.add("Wheat");
		sellItems.add("LinSeed");
		sellItems.add("Milk");
		sellItems.add("Eggs");
		sellItems.add("Tomato");
		sellItems.add("Sugarcane");
		sellItems.add("Maize");
		sellItems.add("Mustard");
		sellItems.add("Jute");

		arrayAdapter = new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_list_item_1,
				sellItems );

		lv.setAdapter(arrayAdapter);
	}

	public void populateBuyList(View view)
	{
		type = "0";
		ArrayList<String> buyItems = new ArrayList<String>();
		buyItems.add("Rice Seeds");
		buyItems.add("Wheat Seeds");
		buyItems.add("Maize Seeds");
		buyItems.add("Ammonia");
		buyItems.add("Hacksaw Tools");
		buyItems.add("Barley Seeds");
		buyItems.add("LinSeed Seeds");
		buyItems.add("Maize Seeds");
		buyItems.add("Potash Fertilizer");
		buyItems.add("Ploughing Tools");

		arrayAdapter = new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_list_item_1,
				buyItems );

		lv.setAdapter(arrayAdapter);
	}

	public boolean isConn() {
		ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity.getActiveNetworkInfo() != null) {
			if (connectivity.getActiveNetworkInfo().isConnected())
				return true;
		}
		return false;
	}
	
	public void sync(View view)
	{
		if(isConn())
		{
			File marketdata = new File(Environment.getExternalStorageDirectory(),"marketdata.txt");
			BufferedWriter bw1;
			HttpClient httpclient = new DefaultHttpClient();
		    HttpGet httpget = new HttpGet("http://10.2.40.55:5000/getItems/");
		    ResponseHandler<String> res = new BasicResponseHandler();
		    String response = "";
		    JSONObject json;

		    try {
		// reponse string contains all the json data
		         response = httpclient.execute(httpget,res);
		         json = new JSONObject(response);
		         JSONArray arr = json.getJSONArray("items");
		         bw1 = new BufferedWriter(new FileWriter(marketdata));
		         for(int i =1; i<arr.length()-1;i++){
		        	 String tmp = arr.getJSONObject(i).get("item").toString() + ","+ arr.getJSONObject(i).get("lat")+ "," + arr.getJSONObject(i).get("long")+ "," + arr.getJSONObject(i).get("price")+ "," + arr.getJSONObject(i).get("desc")+ "," + arr.getJSONObject(i).get("bs")+ "," + arr.getJSONObject(i).get("id") + "\n";
		        	 bw1.append(tmp);
		         }
//		         System.out.println(arr.getJSONObject(0));
		         
		         bw1.flush();
		         bw1.close();
		    } catch (ClientProtocolException e) {
		        // TODO Auto-generated catch block
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		    } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			Toast.makeText(getApplicationContext(), "No Internet Access!", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);
		Object obj = l.getAdapter().getItem(position);
		String value = obj.toString();

		ArrayList<String> topMarkets;
		topMarkets = calculateCost(value, type);
		String temp ="";
		for(String i :topMarkets)
		{
			temp = temp.concat(i);
			temp = temp.concat(",");
		}
		Intent intent = new Intent(this, TopMarketDisplayActivity.class);
		intent.putExtra(EXTRA_MESSAGE, temp);
		startActivity(intent);
	}

	public ArrayList<String> calculateCost(String item, String type){
		File f = new File(Environment.getExternalStorageDirectory(),"marketdata.txt");
		File farmerdata = new File(Environment.getExternalStorageDirectory(),"mydata.txt");

		try {
			//Farmer Data:lat/nlang/n
			BufferedReader br1 = new BufferedReader(new FileReader(farmerdata));
			Double lat = Double.parseDouble(br1.readLine());
			Double lang = Double.parseDouble(br1.readLine());
			br1.close();

			Double costperunit = 10.0;
			//Market Data:Item,Lat,Lang,Priceperkg,toShow,buy/sell 1=buy 0=sell
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = "";
			TreeMap<Double, String> markets = new TreeMap<Double, String>();
			while((line = br.readLine()) != null){
				Log.d("PARSE", line);
				String items[] = line.split(",");
				if(items[0].equalsIgnoreCase(item) && type.equals(items[5])){
					Double dist = (lat-Double.parseDouble(items[1]))*(lat-Double.parseDouble(items[1]))+(lang-Double.parseDouble(items[2]))*(lang-Double.parseDouble(items[2]));
					Double cost = 0.0;
					if(type.equals("1"))
						cost = Double.parseDouble(items[3])  - Math.sqrt(dist)*costperunit;
					else
						cost = Double.parseDouble(items[3])  + Math.sqrt(dist)*costperunit;
					System.out.println(cost);
					markets.put(cost, items[4]+";"+items[3]);
				}
			}
			ArrayList<String> al = new ArrayList<String>();
			for(Double i:markets.keySet()){
				al.add(markets.get(i));
			}
			if(type.equals("1")){
				Collections.reverse(al);
			}
			System.out.println(al);
			br.close();
			return al;
		} catch (FileNotFoundException e) {
			System.out.println("No Internet Connection");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("No Internet Connection");
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.farmer_display, menu);
		return true;
	}

}
