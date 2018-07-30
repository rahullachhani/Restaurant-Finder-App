package com.codingc.team20.restofinder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter2 extends FragmentPagerAdapter {

	public TabsPagerAdapter2(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new Tab1AdminFragment();
		case 1:
			// Games fragment activity
			return new Tab3AdminFragment();
			
		case 2:
			// Movies fragment activity
			return new Tab2AdminFragment();
			
		
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
