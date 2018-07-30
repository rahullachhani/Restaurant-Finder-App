package com.codingc.team20.restofinder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NoNetworkFragment extends Fragment{

	View rootView;
	static FragmentManager fg;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		fg=getFragmentManager();
         rootView= inflater.inflate(R.layout.nonetworkfragment, container, false);
         return rootView;
}
}
