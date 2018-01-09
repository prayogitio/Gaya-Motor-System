package com.architect.code.gayapembukuan;

/**
 * Created by USER on 10/12/2017.
 */

public class Pembukuan {
    private String m_id_pembukuan;
    private String m_tanggal;
    private String m_keterangan;
    private String m_debit;
    private String m_kredit;
    private String m_saldo;

    public Pembukuan(String m_id_pembukuan, String m_tanggal, String m_keterangan, String m_debit, String m_kredit, String m_saldo) {
        this.m_id_pembukuan = m_id_pembukuan;
        this.m_tanggal = m_tanggal;
        this.m_keterangan = m_keterangan;
        this.m_debit = m_debit;
        this.m_kredit = m_kredit;
        this.m_saldo = m_saldo;
    }

    public String getM_id_pembukuan() {
        return m_id_pembukuan;
    }

    public void setM_id_pembukuan(String m_id_pembukuan) {
        this.m_id_pembukuan = m_id_pembukuan;
    }

    public String getM_tanggal() {
        return m_tanggal;
    }

    public void setM_tanggal(String m_tanggal) {
        this.m_tanggal = m_tanggal;
    }

    public String getM_keterangan() {
        return m_keterangan;
    }

    public void setM_keterangan(String m_keterangan) {
        this.m_keterangan = m_keterangan;
    }

    public String getM_debit() {
        return m_debit;
    }

    public void setM_debit(String m_debit) {
        this.m_debit = m_debit;
    }

    public String getM_kredit() {
        return m_kredit;
    }

    public void setM_kredit(String m_kredit) {
        this.m_kredit = m_kredit;
    }

    public String getM_saldo() {
        return m_saldo;
    }

    public void setM_saldo(String m_saldo) {
        this.m_saldo = m_saldo;
    }

}
