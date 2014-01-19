package com.example.farmerpal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
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
		String id="";
		if(isConn())
		{
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

					HttpClient client = new DefaultHttpClient();
					HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
					HttpResponse response;
					JSONObject json = new JSONObject();

					try {
						HttpPost post = new HttpPost("http://10.2.40.55:5000/insert/");
						json.put("item", items[0]);
						json.put("lat", items[1]);
						json.put("long", items[2]);
						json.put("price", items[3]);
						json.put("desc", items[4]);
						json.put("bs", items[5]);
						json.put("id", items[6]);
						StringEntity se = new StringEntity( json.toString());  
						se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
						post.setEntity(se);
						response = client.execute(post);

						if(response!=null){
							InputStream in = response.getEntity().getContent(); //Get the data in the entity
						}

					} catch(Exception e) {
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
