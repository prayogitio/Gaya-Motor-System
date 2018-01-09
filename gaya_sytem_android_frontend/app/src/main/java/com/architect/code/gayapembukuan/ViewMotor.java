package com.architect.code.gayapembukuan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by prayogi on 02/01/18.
 */

public class ViewMotor extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motor_view);
        Intent intent  = getIntent();
        final String message = intent.getStringExtra(DaftarMotorFragment.EXTRA_MESSAGE);
        Button btn_terjual = (Button)findViewById(R.id.btnTerjual);
        btn_terjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread ubah_status_motor = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String response = Database.setStatusMotor(message);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Hasil : " + response, Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        } catch (Exception e) {
                            Log.d("prayogitio", e.toString());
                            throw new RuntimeException("Error Bung!");
                        }
                    }
                });
                ubah_status_motor.start();
            }
        });
        Thread yogi_view = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String response = Database.getDaftarMotorByPlat(message);
                    System.out.println(response);
                    JSONObject json = new JSONObject(response);
                    List<InfoMotor> rows = new ArrayList<>();
                    JSONArray daftar_motor = json.getJSONArray("daftar_motor");
                    final String tipe, plat, merk, tahun, modal, tanggal;
                    JSONObject info_motor_json = daftar_motor.getJSONObject(0);
                    tipe = info_motor_json.getString("tipe");
                    plat = info_motor_json.getString("plat");
                    merk = info_motor_json.getString("merk");
                    tahun = info_motor_json.getString("tahun");
                    modal = info_motor_json.getString("modal");
                    tanggal = info_motor_json.getString("tanggal_beli");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            EditText etPlat = (EditText) findViewById(R.id.plat_et);
                            EditText etTahun = (EditText) findViewById(R.id.tahun_et);
                            EditText etMerk = (EditText) findViewById(R.id.merk_et);
                            EditText etTipe = (EditText) findViewById(R.id.tipe_et);
                            EditText etModal = (EditText) findViewById(R.id.modal_et);
                            EditText etTanggal = (EditText) findViewById(R.id.tanggal_et);
                            etPlat.setText(plat);
                            etTipe.setText(tipe);
                            etMerk.setText(merk);
                            etTahun.setText(tahun);
                            etModal.setText(NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(modal)).toString());
                            etTanggal.setText(tanggal);
                        }
                    });
                } catch (Exception e) {
                    Log.d("prajogotio", e.toString());
                    throw new RuntimeException("Error Bung!");
                }
            }
        });
        yogi_view.start();
    }
}
