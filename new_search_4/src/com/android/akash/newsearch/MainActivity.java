package com.android.akash.newsearch;

import java.util.ArrayList;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

//public class MainActivity extends ActionBarActivity {

public class MainActivity extends FragmentActivity implements SearchView.OnQueryTextListener{

	private SearchView mSearchView ; //added now 
	//private EditText mUsername;
	//private EditText mPassword;
//	private TextView mStatusView ; //added now 
	public static final String EXTRA_SEARCH_QUERY = " com.android.akash.newsearch.search_query";
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] navMenuTitles;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	//private LinearLayout linearLayout;
	FragmentManager fm = getSupportFragmentManager(); 
	
	Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		
		setContentView(R.layout.activity_fragment);
	
	//	mStatusView = (TextView)findViewById(R.id.status);
		String query = getIntent().getStringExtra(EXTRA_SEARCH_QUERY);
		Log.d("Intent from search","Got query");
		if(query != null){
			
		
		if(fragment == null){
			fragment = SearchResultsFragment.newInstance(query);
			fm.beginTransaction()
			  .add(R.id.fragmentContainer,fragment)
			  .commit(); 
			Log.d("Create Fragment","Creating new Fragment with Query:"+query);
		}
		
			//mUsername = (EditText)findViewById(R.id.username);
			
		
		}
		
		String id =(String)getIntent().getStringExtra(BookFragment.EXTRA_BOOK_ID);
		if(id != null){
			
			if(fragment == null){
				fragment = BookFragment.newInstance(id);
				fm.beginTransaction()
				  .add(R.id.fragmentContainer,fragment)
				  .commit(); 
				Log.d("Create Fragment","Creating new Fragment with Query:"+query);
			}
		}
		//Log.d("handle Intent",query);
		//linearLayout = (LinearLayout)findViewById(R.id.frame_container);
		mTitle = mDrawerTitle = getTitle();
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		
		mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
		mDrawerList = (ListView)findViewById(R.id.list_slidermenu);
		
		navDrawerItems = new ArrayList<NavDrawerItem>();
		
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0]));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1]));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2]));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3]));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4]));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5]));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6]));
		
		adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItems);
		mDrawerList.setAdapter(adapter);
		
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.drawable.ic_drawer, 
												R.string.app_name, R.string.app_name){
			
			public void onDrawerClosed(View view){
				super.onDrawerClosed(view);
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}
			
			public void onDrawerOpened(View view){
				super.onDrawerOpened(view);
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		mDrawerList.setOnItemClickListener(new SlideMenuCLickListener());
		
		
	
			
		
	}
	
	private class SlideMenuCLickListener implements ListView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			displayView(position);
		}
		
	}
	
	private void displayView(int position){
		Fragment fragment = null;
		switch(position){
		
			case 1:
				fragment = new BrowseFragment();
				break;
				
		}
		
		if(fragment != null){
			fm.beginTransaction()
				.replace(R.id.fragmentContainer, fragment)
				.commit();
			mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
		}
		else{
			Log.e("MainActivity","Error in creating fragment");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		 
	   // SearchManager searchManager =
		 //          (SearchManager)getSystemService(Context.SEARCH_SERVICE);
		 
		
		// SearchView searchView =
		  //          (SearchView) menu.findItem(R.id.search).getActionView();
		   
		 //searchView.setSearchableInfo(
		   //         searchManager.getSearchableInfo(new ComponentName(this,SearchableActivity.class)));
		    
		    //searchView.setIconifiedByDefault(false);
		 
		MenuItem  searchItem = menu.findItem(R.id.search) ;
		mSearchView  = (SearchView)searchItem.getActionView();
		setupSearchView(searchItem); 
		return true;
	}
	
	 private void setupSearchView(MenuItem searchItem) {

	        if (isAlwaysExpanded()) {
	            mSearchView.setIconifiedByDefault(false);
	        } else {
	            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
	                    | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
	        }

	        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	        if (searchManager != null) {
	           //List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();
	        
	           SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
	           
	           /*for (SearchableInfo inf : searchables) {
	                if (inf.getSuggestAuthority() != null
	                        && inf.getSuggestAuthority().startsWith("applications")) {
	                    info = inf;
	                }
	            }*/
	            mSearchView.setSearchableInfo(info);
	        }

	        mSearchView.setOnQueryTextListener(this);
	    }

	    public boolean onQueryTextChange(String newText) {
	     //   mStatusView.setText("Query = " + newText);
	        return false;
	    }

	    public boolean onQueryTextSubmit(String query) {
	        //mStatusView.setText("Query = " + query + " : submitted");
	        return false;
	    }

	    public boolean onClose() {
	      //  mStatusView.setText("Closed!");
	        return false;
	    }

	    protected boolean isAlwaysExpanded() {
	        return false;
	    }
	


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		
		  if (mDrawerToggle.onOptionsItemSelected(item)) {
	            return true;
	        }
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu){
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		 
		boolean drawerOpen  = mDrawerLayout.isDrawerOpen(mDrawerList);
		/*if(drawerOpen == true){
			Log.d("Drawer Open", "True");
		}
		else if(drawerOpen == false){
			Log.d("Drawer Open","False");
		}
		else{
			Log.d("Drawer Open","ND");
		}*/
			
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
		
		
	}
	
	@Override
	public void setTitle(CharSequence title){
		mTitle  = title ;
		getActionBar().setTitle(mTitle);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState){
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	
}
