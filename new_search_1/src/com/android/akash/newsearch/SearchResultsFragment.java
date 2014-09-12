package com.android.akash.newsearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
//import com.akash.android.winecellar.R;

public class SearchResultsFragment extends ListFragment {
	
	public static final String TAG_QUERY = "com.android.akash.search.quer_string";
	
	public static final String TAG_TITLE = "title";
	public static final String TAG_AUTHOR ="author";
	public static final String TAG_publication="publication";
	public static final String TAG_INFO = "SearchResultsFragment";
	public static final String ENDPOINT = "http://10.0.2.2:3000";
	ArrayList<HashMap<String,String>> BookList ; 

	
	public static SearchResultsFragment newInstance(String query){
		Bundle args = new Bundle();
		args.putCharSequence(TAG_QUERY, query);
		
		SearchResultsFragment fragment = new SearchResultsFragment();
		fragment.setArguments(args);
		
		return fragment; 
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		getActivity();
		BookList = new ArrayList<HashMap<String,String>>();
		
		
		String query = (String)getArguments().getCharSequence(TAG_QUERY);
		
		if(query != null){
			
			/*Uri.Builder builder = new  Uri.Builder();
			builder.scheme("http").authority("10.0.2.2:3000")
			.appendPath("books")
			.appendPath("search")
			.appendQueryParameter("q", query);
		*/
			String en_query = Uri.parse(query).toString();
			String myUrl = Uri.parse(ENDPOINT).buildUpon()
					.appendPath("books")
					.appendPath("search")
					.appendQueryParameter("q", en_query)
					.build().toString(); 
			
		
		
			new FetchItemsTask().execute(myUrl);
		}
		
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
						
								String title  =  c.getString(TAG_TITLE);
								String author = c.getString(TAG_AUTHOR);
								//String publ = c.getString(TAG_publication);
						
								HashMap<String,String> wine = new HashMap<String,String>();
						
								wine.put(TAG_TITLE, title);
								wine.put(TAG_AUTHOR, author);
						
								BookList.add(wine);
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
			
			ListAdapter  adapter = new SimpleAdapter(getActivity(), BookList, R.layout.fragment_book_list,
					new String[] {TAG_TITLE,TAG_AUTHOR}, new int[] {R.id.book_title,R.id.book_author});
		
			setListAdapter(adapter);
		}
	}
}
