package com.android.akash.newsearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BrowseFragment extends Fragment {
	
	private FragmentTabHost mTabhost;
	public BrowseFragment(){}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View rootView = inflater.inflate(R.layout.fragment_browse_host, container,false);
		mTabhost = (FragmentTabHost)rootView.findViewById(R.id.tabhost);
		mTabhost.setup(getActivity(),getChildFragmentManager(),R.id.realtabcontent);
		
		mTabhost.addTab(mTabhost.newTabSpec("fragment_cat")
				.setIndicator("Categories"),BrowseResultsFragment.class,null);
		
		mTabhost.addTab(mTabhost.newTabSpec("fragment_titl").setIndicator("Titles")
				,BrowseTitleFragment.class,null);
		
		return rootView;
		
	}

}
