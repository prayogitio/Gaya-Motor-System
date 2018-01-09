package com.architect.code.gayapembukuan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static android.view.View.GONE;

public class PembukuanFragmentChildView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembukuan_fragment_child_view);
        Intent intent  = getIntent();
        final String message = intent.getStringExtra(PembukuanFragment.EXTRA_MESSAGE);
        Thread yogi_view = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String data = Database.getDataPembukuanById(message);
                    final String[] response_data = data.split(" \\| ");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            EditText etTanggal = (EditText) findViewById(R.id.etTanggal);
                            EditText etKeterangan = (EditText) findViewById(R.id.etKeterangan);
                            EditText etDebit = (EditText) findViewById(R.id.etDebit);
                            EditText etKredit = (EditText) findViewById(R.id.etKredit);
                            EditText etSaldo = (EditText) findViewById(R.id.etSaldo);
                            etTanggal.setText(response_data[1]);
                            etKeterangan.setText(response_data[2]);
                            etDebit.setText(NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(response_data[3])).toString());
                            etKredit.setText(NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(response_data[4])).toString());
                            etSaldo.setText(NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(response_data[5])).toString());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        yogi_view.start();
    }
}
