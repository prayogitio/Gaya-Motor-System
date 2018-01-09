package com.architect.code.gayapembukuan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.view.View.GONE;

public class  DaftarMotorFragment extends Fragment {
	public static final String EXTRA_MESSAGE = "com.architect.code.gayapembukuan.MESSAGE";
	private class AddButtonHandler implements View.OnClickListener {
		private Fragment m_fragment;

		public AddButtonHandler(Fragment fragment) {
			m_fragment = fragment;
		}

		@Override
		public void onClick(View view) {
			Intent start_intent = new Intent(m_fragment.getActivity(), MasukMotorBaru.class);
			m_fragment.startActivityForResult(start_intent, INSERT_MOTOR_RESULT);
		}
	}

	private class AbsenMotorButtonHandler implements View.OnClickListener {
		private Fragment m_fragment;

		public AbsenMotorButtonHandler(Fragment fragment) {
			m_fragment = fragment;
		}

		@Override
		public void onClick(View view) {
			Intent start_intent = new Intent(m_fragment.getActivity(), AbsenMotor.class);
			m_fragment.startActivity(start_intent);
		}
	}
	
	public final static int INSERT_MOTOR_RESULT = 4123;
	View m_root_view;

	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		m_root_view = inflater.inflate(R.layout.fragment_daftar_motor, container, false);
		final SwipeRefreshLayout srl = m_root_view.findViewById(R.id.swiperefresh);
		srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable() {
					@Override public void run() {
						refresh();
						srl.setRefreshing(false);
					}
				}, 2000);
			}
		});
		FloatingActionButton fapAdd = m_root_view.findViewById(R.id.fabAdd);
		fapAdd.setOnClickListener(new AddButtonHandler(this));
		FloatingActionButton fabAbsen = m_root_view.findViewById(R.id.fabAbsen);
		fabAbsen.setOnClickListener(new AbsenMotorButtonHandler(this));
		TableLayout table_layout = m_root_view.findViewById(R.id.table);
		CheckBox chkStatus = m_root_view.findViewById(R.id.chkStatus);
		chkStatus.setChecked(true);
		chkStatus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				refresh();
			}
		});
		Thread load_data_task = new Thread(new DaftarMotorLoader(getActivity(), table_layout, inflater));
		load_data_task.start();
		return m_root_view;
	}
	
	public void refresh() {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		TableLayout table_layout = m_root_view.findViewById(R.id.table);
		Thread load_data_task = new Thread(new DaftarMotorLoader(getActivity(), table_layout, inflater));
		load_data_task.start();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == INSERT_MOTOR_RESULT) {
			// Trigger update.
			refresh();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}

class InfoMotor {
	public String plat = "";
	public String tahun = "";
	public String tipe = "";
	public String modal = "";
	public String tanggal = "";
}


class DaftarMotorLoader implements Runnable {
	private Activity m_root_activity;
	private TableLayout m_table_layout;
	private LayoutInflater m_layout_inflater;
	
	public DaftarMotorLoader(
		Activity root_activity, TableLayout table_layout, LayoutInflater layout_inflater) {
		m_root_activity = root_activity;
		m_table_layout = table_layout;
		m_layout_inflater = layout_inflater;
	}
	
	@Override
	public void run() {
		try {
			String filter = "";
			CheckBox chk = m_root_activity.findViewById(R.id.chkStatus);
			if (chk.isChecked()) {
				filter = "0";
			} else filter = "1";
			String response = Database.getDaftarMotor(filter);
			JSONObject json = new JSONObject(response);
			List<InfoMotor> rows = new ArrayList<>();
			JSONArray daftar_motor = json.getJSONArray("daftar_motor");
			for (int i = 0; i < daftar_motor.length(); ++i) {
				JSONObject info_motor_json = daftar_motor.getJSONObject(i);
				InfoMotor info_motor = new InfoMotor();
				info_motor.tipe = info_motor_json.getString("tipe");
				info_motor.plat = info_motor_json.getString("plat");
				info_motor.tahun = info_motor_json.getString("tahun");
				info_motor.modal = info_motor_json.getString("modal");
				info_motor.tanggal = info_motor_json.getString("tanggal_beli");
				rows.add(info_motor);
			}
			updateTableLayout(rows);
		} catch (Exception e) {
			Log.d("prajogotio", e.toString());
			throw new RuntimeException("Error Bung");
		}
	}
	
	private void updateTableLayout(final List<InfoMotor> rows) {
		m_root_activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				final Handler handler = new Handler();
				m_table_layout.removeAllViews();
				for (InfoMotor motor : rows) {
					TableRow row = (TableRow) m_layout_inflater.inflate(R.layout.daftar_motor_table_item, m_table_layout, false);
					final Button plat_btn = row.findViewById(R.id.plat);
					TextView tipe_tahun_tv = row.findViewById(R.id.tipe_tahun);
					TextView modal_tv = row.findViewById(R.id.modal);
					plat_btn.setText(motor.plat);
					plat_btn.setTag(motor.plat);
					tipe_tahun_tv.setText(motor.tipe + "\n" + motor.tahun + "\n" + "Tgl. Beli : "+ "\n"+ motor.tanggal);
					modal_tv.setText(NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(motor.modal)).toString());
					m_table_layout.addView(row);
					final String EXTRA_MESSAGE = "com.architect.code.gayapembukuan.MESSAGE";
					plat_btn.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							final Intent intent = new Intent(m_root_activity, ViewMotor.class);
							String message = v.getTag().toString();
							intent.putExtra(EXTRA_MESSAGE, message);
							m_root_activity.startActivity(intent);
						}
					});
				}
				m_root_activity.findViewById(R.id.progress_bar).setVisibility(GONE);
			}
		});
	}
}