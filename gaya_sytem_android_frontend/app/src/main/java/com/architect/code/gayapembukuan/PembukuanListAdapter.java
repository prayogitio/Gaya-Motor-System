package com.architect.code.gayapembukuan;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by USER on 10/12/2017.
 */

public class PembukuanListAdapter extends BaseAdapter{
    private Context mContext;
    private List<Pembukuan> mPembukuanList;

    public PembukuanListAdapter(Context mContext, List<Pembukuan> mPembukuanList) {
        this.mContext = mContext;
        this.mPembukuanList = mPembukuanList;
    }

    @Override
    public int getCount() {
        return mPembukuanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPembukuanList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.list_pembukuan, null);
        TextView tvTanggal = (TextView)v.findViewById(R.id.tvTanggal);
        TextView tvKeterangan = (TextView)v.findViewById(R.id.tvKeterangan);
        TextView tvDebit = (TextView)v.findViewById(R.id.tvDebit);
        TextView tvKredit = (TextView)v.findViewById(R.id.tvKredit);
        TextView tvSaldo = (TextView)v.findViewById(R.id.tvSaldo);

        tvTanggal.setText(mPembukuanList.get(position).getM_tanggal());
        tvKeterangan.setText(mPembukuanList.get(position).getM_keterangan());
        tvDebit.setText(NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(mPembukuanList.get(position).getM_debit())).toString());
        tvKredit.setText(NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(mPembukuanList.get(position).getM_kredit())).toString());
        tvSaldo.setText(NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(mPembukuanList.get(position).getM_saldo())).toString());
        v.setTag(mPembukuanList.get(position).getM_id_pembukuan());
        return v;
    }
}
