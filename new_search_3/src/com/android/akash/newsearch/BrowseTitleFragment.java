package com.android.akash.newsearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class BrowseTitleFragment extends ListFragment {

	
	
	public static final String ENDPOINT = "http://10.0.2.2:3000";
	public static final String TAG_INFO= "BrowseTitleFragment";
	public static final String TAG_TITLE = "title";
	public static final String TAG_AUTHOR ="author";

	
	ArrayList<HashMap<String,String>> titleList = new ArrayList<HashMap<String,String>>();
	//HashSet<String> hs = new HashSet<String>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActivity();
		
		String myUrl = Uri.parse(ENDPOINT).buildUpon()
						.appendPath("books")
						.build()
						.toString();
		
		new FetchItemsTask().execute(myUrl);
		
		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		String cat = (String) getListAdapter().getItem(position);
		
		Intent i = new Intent(getActivity(),MainActivity.class);
		i.putExtra(MainActivity.EXTRA_SEARCH_QUERY, cat);
		startActivity(i);
		
	}
	
public class FetchItemsTask extends AsyncTask<String, Void, Void>{
		
		protected Void doInBackground(String...params){
			
			String url = params[0] ; 
			
			try{
				String result = new BookFetchr().getURL(url);
				Log.i(TAG_INFO, "Fetch contents of URL:"+result);
				
				if (result != null){
					
					try{
							JSONArray books = new JSONArray(result);
							for(int i=0 ; i < books.length() ; i++) {
								JSONObject c = books.getJSONObject(i);
								String title = c.getString(TAG_TITLE);
								String author = c.getString(TAG_AUTHOR);
								
								HashMap<String,String> book = new HashMap<String,String>();
								book.put(TAG_TITLE,title);
								book.put(TAG_AUTHOR,author);
								
								titleList.add(book);
							
							}
							
							
					
					}
					catch(JSONException e){
					e.printStackTrace();
				}
			}
			else{
				Log.e("Service Handler"	,"Couldn't get any data");
			}
			
		}
		catch(IOException ioe){
			Log.e(TAG_INFO,"Failed to fetch URL:"+ioe);
		}
		
			
			
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			ListAdapter  adapter = new SimpleAdapter(getActivity(), titleList, R.layout.fragment_book_list,
					new String[] {TAG_TITLE,TAG_AUTHOR}, new int[] {R.id.book_title,R.id.book_author});
		
			setListAdapter(adapter);
		}

}
}
