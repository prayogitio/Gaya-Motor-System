package com.architect.code.gayapembukuan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.GONE;

public class PembukuanFragmentChildAdd extends AppCompatActivity {
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembukuan_fragment_child_add);
        Intent intent  = getIntent();
        final String message = intent.getStringExtra(PembukuanFragment.EXTRA_MESSAGE);
        final EditText etTanggal = (EditText) findViewById(R.id.etTanggal);
        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(PembukuanFragmentChildAdd.this, android.R.style.Theme_Holo_Dialog, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "/" + month + "/" + day;
                etTanggal.setText(date);
            }
        };
        final EditText etKeterangan = (EditText)findViewById(R.id.etKeterangan);
        final EditText etDebit = (EditText)findViewById(R.id.etDebit);
        final EditText etKredit = (EditText)findViewById(R.id.etKredit);
        if (message.equals("0")) {
            etKredit.setText("0");
            etKredit.setEnabled(false);
        } else if (message.equals("1")) {
            etDebit.setText("0");
            etDebit.setEnabled(false);
        }
        Button btnAdd = (Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread yogi_edit = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String last_saldo = Database.getLastBalance();
                            System.out.println(last_saldo);
                            final String result = Database.addDataPembukuan(etTanggal.getText().toString(),etKeterangan.getText().toString(), etDebit.getText().toString(), etKredit.getText().toString(), Integer.toString(Integer.parseInt(last_saldo)+Integer.parseInt(etDebit.getText().toString())-Integer.parseInt(etKredit.getText().toString())));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),""+result, Toast.LENGTH_SHORT).show();
                                    setResult(PembukuanFragment.INSERT_PEMBUKUAN_RESULT, null);
                                    finish();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                yogi_edit.start();
            }
        });
    }
}
