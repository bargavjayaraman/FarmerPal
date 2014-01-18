package com.example.farmerpal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
			try {
				File f = new File(Environment.getExternalStorageDirectory(),"marketdata.txt");
				BufferedReader br = new BufferedReader(new FileReader(f));
				String line = "";
				while((line = br.readLine())!=null){
					String[] items = line.split(",");

					HttpClient client = new DefaultHttpClient();
					HttpPost post = new HttpPost("https://docs.google.com/forms/d/18yM0nrhyd0bXu4usrO6fvtrSVDrA7njoTm5NF7aYAg8/formResponse");

					List<BasicNameValuePair> MarketData = new ArrayList<BasicNameValuePair>();
					MarketData.add(new BasicNameValuePair("entry.1124819832", items[0]));
					MarketData.add(new BasicNameValuePair("entry.909167110", items[1]));
					MarketData.add(new BasicNameValuePair("entry.1747531464", items[2]));
					MarketData.add(new BasicNameValuePair("entry.2041995474", items[3]));
					MarketData.add(new BasicNameValuePair("entry.688218270", items[4]));
					MarketData.add(new BasicNameValuePair("entry.1921593393", items[5]));
					MarketData.add(new BasicNameValuePair("entry.1938252992", items[6]));

					try {
						post.setEntity(new UrlEncodedFormEntity(MarketData));
					} catch (UnsupportedEncodingException e) {
						// Auto-generated catch block
						//Log.e("Intrnet", "Not Connected", e);
						e.printStackTrace();
					}
					try {
						client.execute(post);
					} catch (ClientProtocolException e) {
						// Auto-generated catch block
						//Log.e("YOUR_TAG", "client protocol exception", e);
						e.printStackTrace();
					} catch (IOException e) {
						// Auto-generated catch block
						//Log.e("YOUR_TAG", "io exception", e);
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			Toast.makeText(getApplicationContext(), "No Internet Access!", Toast.LENGTH_SHORT).show();
		}
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
