package com.architect.code.gayapembukuan;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MasukMotorBaru extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_masuk_motor_baru);

		Button ok_btn = (Button) findViewById(R.id.ok_btn);
		ok_btn.setOnClickListener(new OkButtonHandler(this));
	}


	private class OkButtonHandler implements View.OnClickListener {
		private Activity m_root;
		
		public OkButtonHandler(Activity root) {
			m_root = root;
		}
		
		@Override
		public void onClick(View view) {
			EditText plat_et = m_root.findViewById(R.id.plat_et);
			final String plat = plat_et.getText().toString();
			EditText merk_et = m_root.findViewById(R.id.merk_et);
			final String merk = merk_et.getText().toString();
			EditText tahun_et = m_root.findViewById(R.id.tahun_et);
			final int tahun = parseStringAsInt(tahun_et.getText().toString());
			EditText tipe_et = m_root.findViewById(R.id.tipe_et);
			final String tipe = tipe_et.getText().toString();
			EditText modal_et = m_root.findViewById(R.id.modal_et);
			final int modal = parseStringAsInt(modal_et.getText().toString());
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Database.insertMotorBaru(plat, merk, tipe, tahun, modal);
						// TODO: Currently assumed success. Handle failure
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast toast = Toast.makeText(m_root, "Motor baru dengan plat " + plat + " berhasil dimasukkan.", Toast.LENGTH_LONG);
								toast.show();
								setResult(DaftarMotorFragment.INSERT_MOTOR_RESULT, null);
								finish();
							}
						});
					} catch (Exception e) {
						Log.d("prajogotio", e.toString());
						throw new RuntimeException("ERROR INSERTING DATA");
					}
				}
			});
			thread.start();
		}
		
		private int parseStringAsInt(String value) {
			if (value.trim().isEmpty()) {
				return -1;
			}
			return Integer.parseInt(value);
		}
	}
}
