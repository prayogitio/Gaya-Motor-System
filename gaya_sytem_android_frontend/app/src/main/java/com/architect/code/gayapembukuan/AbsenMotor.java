package com.architect.code.gayapembukuan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbsenMotor extends AppCompatActivity implements TextWatcher {
	private class Absen {
		String plat;
		boolean ada = false;
		boolean show = true;
		public Absen(String plat) {
			this.plat = plat;
		}
	}
	ArrayList<Absen> m_absen = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		jumlah_motor = 0;
		setContentView(R.layout.activity_absen_motor);
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String response = Database.getDaftarMotor("0");
					JSONObject json = new JSONObject(response);
					JSONArray daftar_motor = json.getJSONArray("daftar_motor");
					for (int i = 0; i < daftar_motor.length(); ++i) {
						JSONObject info_motor = daftar_motor.getJSONObject(i);
						m_absen.add(new Absen(info_motor.getString("plat")));
					}
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							refreshTable();
						}
					});
				} catch (Exception e) {
					throw new RuntimeException("Error Bung");
				}
			}
		});
		thread.start();
		EditText search = (EditText) findViewById(R.id.search);
		search.addTextChangedListener(this);
	}

	public static int jumlah_motor = 0;

	private void refreshTable() {
		LayoutInflater inflater = getLayoutInflater();
		TableLayout table = (TableLayout) findViewById(R.id.table);
		table.removeAllViews();

		TextView tvTotal = (TextView) findViewById(R.id.tvTotal);
		tvTotal.setText("Jumlah Motor Pak Martin ada " + Integer.toString(m_absen.size()) + " buah");
		final TextView tvJumlahMotor = (TextView) findViewById(R.id.tvJumlahMotor);
		tvJumlahMotor.setText("Yang sudah dicek ada " + Integer.toString(jumlah_motor) + " buah");
		for (final Absen absen : m_absen) {
			if (!absen.show) continue;
			TableRow table_row = (TableRow) inflater.inflate(R.layout.absen_motor_table_item, table, false);
			((TextView) table_row.findViewById(R.id.plat)).setText(absen.plat);
			final CheckBox chk_status = table_row.findViewById(R.id.check);
			chk_status.setChecked(absen.ada);
			chk_status.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (chk_status.isChecked()) {
						absen.ada = true;
						absen.show = false;
						jumlah_motor++;
						tvJumlahMotor.setText("Yang sudah dicek ada " + Integer.toString(jumlah_motor) + " buah");
						if (jumlah_motor == m_absen.size()) {
							Toast.makeText(getApplicationContext(), "Motor Pak Martin lengkap!", Toast.LENGTH_SHORT).show();
							jumlah_motor = 0;
							finish();
						}
					}
					if (!chk_status.isChecked()) {
						absen.ada = false;
						absen.show = true;
						jumlah_motor--;
						tvJumlahMotor.setText("Yang sudah dicek ada " + Integer.toString(jumlah_motor) + " buah");
					}
				}
			});
			table.addView(table_row);
		}
		TextView status = (TextView) findViewById(R.id.status);

		if (table.getChildCount() == 0) {
			status.setText("Motor yang plat-nya kayak di atas ga ketemu");
		} else {
			status.setText("");
		}
	}

	@Override
	public void beforeTextChanged(CharSequence char_seq, int start, int before, int count) {
		if (char_seq.toString().length() == 1) {
			for (Absen absen : m_absen) {
				absen.show = true;
			}
			refreshTable();
			return;
		}
	}

	@Override
	public void onTextChanged(CharSequence char_seq, int start, int before, int count) {
		String str = char_seq.toString().trim();

		Pattern regex = Pattern.compile(str.trim(), Pattern.CASE_INSENSITIVE);
		for (Absen absen : m_absen) {
			Matcher matcher = regex.matcher(absen.plat);
			absen.show = matcher.find();
		}
		refreshTable();
	}

	@Override
	public void afterTextChanged(Editable editable) {

	}
}
