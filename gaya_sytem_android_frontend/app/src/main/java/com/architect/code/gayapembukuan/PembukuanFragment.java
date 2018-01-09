package com.architect.code.gayapembukuan;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.String;

import static android.view.View.GONE;

public class PembukuanFragment extends Fragment {
    public static final int INSERT_PEMBUKUAN_RESULT = 4122;
    View root_view;
    private PembukuanListAdapter adapter;
    private List<Pembukuan> mPembukuanList;
    public static final String EXTRA_MESSAGE = "com.architect.code.gayapembukuan.MESSAGE";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public PembukuanFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.fragment_pembukuan, container, false);
        final ListView listView = root_view.findViewById(R.id.listViewPembukuan);
        FloatingActionButton fab = root_view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(getActivity(), PembukuanFragmentChildAdd.class);
                AlertDialog.Builder adb2 = new AlertDialog.Builder(getActivity());
                adb2.setTitle("Transaction Type");
                adb2.setItems(new CharSequence[]{"Kas Masuk", "Kas Keluar", "Kalibrasi Saldo"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int pos) {
                        if (pos == 0) {
                            String message = "0";
                            intent.putExtra(EXTRA_MESSAGE, message);
                            startActivityForResult(intent, INSERT_PEMBUKUAN_RESULT);
                        }
                        if (pos == 1) {
                            String message = "1";
                            intent.putExtra(EXTRA_MESSAGE, message);
                            startActivityForResult(intent, INSERT_PEMBUKUAN_RESULT);
                        }
                        if (pos == 2) {
                            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                            View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                            alertDialogBuilder.setView(promptView);
                            final EditText etBalance = (EditText) promptView.findViewById(R.id.etBalance);
                            // setup a dialog window
                            alertDialogBuilder.setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Thread yogi_edit = new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        Calendar calendar = Calendar.getInstance();
                                                        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM/dd");
                                                        final String result = Database.addDataPembukuan(mdformat.format(calendar.getTime()),"Kalibrasi Saldo", "0", "0", etBalance.getText().toString());
                                                        getActivity().runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(getActivity(),""+result, Toast.LENGTH_SHORT).show();
                                                                refresh();
                                                            }
                                                        });
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                            yogi_edit.start();
                                        }
                                    })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });
                            AlertDialog alert = alertDialogBuilder.create();
                            alert.show();
                        }
                    }
                });
                AlertDialog al2 = adb2.create();
                al2.show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Action");
                builder.setItems(new CharSequence[]{"Details"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            Intent intent = new Intent(getActivity(), PembukuanFragmentChildView.class);
                            String message = view.getTag().toString();
                            intent.putExtra(EXTRA_MESSAGE, message);
                            startActivity(intent);
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });

        FloatingActionButton fabView = root_view.findViewById(R.id.fabView);
        fabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View promptView2 = layoutInflater.inflate(R.layout.input_dialog_date, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setView(promptView2);
                final EditText etDate = (EditText) promptView2.findViewById(R.id.etDate);
                etDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        android.icu.util.Calendar cal = android.icu.util.Calendar.getInstance();
                        int year = cal.get(android.icu.util.Calendar.YEAR);
                        int month = cal.get(android.icu.util.Calendar.MONTH);
                        int day = cal.get(android.icu.util.Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog, mDateSetListener, year, month, day);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    }
                });
                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = year + "/" + month + "/" + day;
                        etDate.setText(date);
                    }
                };
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Thread yogi_edit = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            final String data = Database.getDataPembukuanByDate(etDate.getText().toString());
                                            mPembukuanList = new ArrayList<>();
                                            String[] response_line = data.split("\n");
                                            for (String line : response_line) {
                                                String[] pair = line.split(" \\| ");
                                                String id = pair[0];
                                                String tanggal = pair[1];
                                                String keterangan = pair[2];
                                                String debit = pair[3];
                                                String kredit = pair[4];
                                                String saldo = pair[5];
                                                mPembukuanList.add(new Pembukuan(id, tanggal, keterangan, debit, kredit, saldo));
                                            }
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    adapter = new PembukuanListAdapter(getActivity().getApplicationContext(), mPembukuanList);
                                                    listView.setAdapter(adapter);
                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                yogi_edit.start();
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }
        });

        Thread yogi = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String data = Database.getDataPembukuan();
                    System.out.println(data);
                    mPembukuanList = new ArrayList<>();
                    String[] response_line = data.split("\n");
                    for (String line : response_line) {
                        String[] pair = line.split(" \\| ");
                        String id = pair[0];
                        String tanggal = pair[1];
                        String keterangan = pair[2];
                        String debit = pair[3];
                        String kredit = pair[4];
                        String saldo = pair[5];
                        mPembukuanList.add(new Pembukuan(id, tanggal, keterangan, debit, kredit, saldo));
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new PembukuanListAdapter(getActivity().getApplicationContext(), mPembukuanList);
                            listView.setAdapter(adapter);
                            root_view.findViewById(R.id.progressBar).setVisibility(GONE);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        yogi.start();
        return root_view;
    }

    public void refresh() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final ListView listView = root_view.findViewById(R.id.listViewPembukuan);
        Thread yogi = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String data = Database.getDataPembukuan();
                    mPembukuanList = new ArrayList<>();
                    String[] response_line = data.split("\n");
                    for (String line : response_line) {
                        String[] pair = line.split(" \\| ");
                        String id = pair[0];
                        String tanggal = pair[1];
                        String keterangan = pair[2];
                        String debit = pair[3];
                        String kredit = pair[4];
                        String saldo = pair[5];
                        mPembukuanList.add(new Pembukuan(id, tanggal, keterangan, debit, kredit, saldo));
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new PembukuanListAdapter(getActivity().getApplicationContext(), mPembukuanList);
                            listView.setAdapter(adapter);
                            root_view.findViewById(R.id.progressBar).setVisibility(GONE);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        yogi.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("prayogitio", "Called?");
        if (requestCode == INSERT_PEMBUKUAN_RESULT) {
            refresh();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
