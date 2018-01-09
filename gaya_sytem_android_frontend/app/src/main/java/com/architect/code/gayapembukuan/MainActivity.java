package com.architect.code.gayapembukuan;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	DemoCollectionPagerAdapter m_demo_collection_pager_adapter;
	ViewPager m_view_pager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		m_demo_collection_pager_adapter = new DemoCollectionPagerAdapter(getSupportFragmentManager());
		m_view_pager = (ViewPager) findViewById(R.id.pager);
		m_view_pager.setAdapter(m_demo_collection_pager_adapter);
	}
}

class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
	public DemoCollectionPagerAdapter(FragmentManager fragment_manager) {
		super(fragment_manager);
	}
	
	@Override
	public Fragment getItem(int position) {
		switch (position) {
			case 0: {
				return new PembukuanFragment();
			}
			case 1: {
				return new DaftarMotorFragment();
			}
			default:
				throw new RuntimeException("CIPAI");
		}
	}

	@Override
	public int getCount() {
		return 2;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		String title = "";
		if (position == 0) {
			title = "Pembukuan";
		}
		if (position == 1) {
			title = "Motor";
		}
		return title;
	}

	static public class DemoObjectFragment extends Fragment {
		public static final String ARG_OBJECT = "object";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View root_view = inflater.inflate(R.layout.fragment_collection_object, container, false);
			((TextView) root_view.findViewById(R.id.textaja)).setText("TODO");
			return root_view;
		}
	}
}
