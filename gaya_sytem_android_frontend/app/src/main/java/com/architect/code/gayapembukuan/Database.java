package com.architect.code.gayapembukuan;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Database {
	private static final String SERVER_URL = "http://192.168.0.109:8000";
	
	public static String getDaftarMotor(String st) throws Exception {
		String request_url = SERVER_URL + "/tarikdatamotor";
		HttpURLConnection connection = (HttpURLConnection) new URL(request_url).openConnection();
		connection.setRequestMethod("POST");
		String query = "status=" + st;
		OutputStream os = connection.getOutputStream();
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(os, "UTF-8"));
		writer.write(query);
		writer.flush();
		writer.close();
		os.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String result = "";
		String line;
		while ((line = reader.readLine()) != null) {
			result += line + "\n";
		}
		return result;
	}

	public static String getDaftarMotorByPlat(String plat) throws Exception {
		String url = SERVER_URL + "/tarikdatamotorbyplat";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		String query = "plat=" + plat;
		OutputStream os = con.getOutputStream();
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(os, "UTF-8"));
		writer.write(query);
		writer.flush();
		writer.close();
		os.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String result = "";
		String line;
		while ((line = reader.readLine()) != null) {
			result += line + "\n";
		}
		return result;
	}

	public static String setStatusMotor(String plat) throws Exception {
		String url = SERVER_URL + "/setstatusmotor";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		String query = "plat=" + plat;
		OutputStream os = con.getOutputStream();
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(os, "UTF-8"));
		writer.write(query);
		writer.flush();
		writer.close();
		os.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String result = reader.readLine();
		return result;
	}

	public static String getDataPembukuan() throws Exception {
		String url = SERVER_URL + "/tarikpembukuan";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String result = "";
		String line;
		while ((line = reader.readLine()) != null) {
			result += line + "\n";
		}
		return result;
	}

	public static String getDataPembukuanById(String id) throws Exception {
		String url = SERVER_URL + "/tarikpembukuanbyid";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		String query = "pid=" + id;
		OutputStream os = con.getOutputStream();
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(os, "UTF-8"));
		writer.write(query);
		writer.flush();
		writer.close();
		os.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String result = reader.readLine();
		return result;
	}

	public static String addDataPembukuan(String tanggal, String keterangan, String debit, String kredit, String saldo) throws Exception {
		String url = SERVER_URL+"/insertdatapembukuan";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		String query = "tanggal="+tanggal+"&keterangan="+keterangan+"&debit="+debit+"&kredit="+kredit+"&saldo="+saldo;
		OutputStream os = con.getOutputStream();
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(os, "UTF-8"));
		writer.write(query);
		writer.flush();
		writer.close();
		os.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String result = reader.readLine();
		if (result == "") {result = "Add Failed";}
		return result;
	}

	public static String getLastBalance() throws Exception {
		String url = SERVER_URL + "/getlastbalance";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String result = reader.readLine();
		return result;
	}

	public static void insertMotorBaru(String plat, String merk, String tipe, int tahun, int modal) throws Exception {
		String request_url = SERVER_URL + "/insertmotorbaru";
		HttpURLConnection connection = (HttpURLConnection) new URL(request_url).openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		String query = URLEncoder.encode(String.format(
			"plat=%s&merk=%s&tipe=%s&tahun=%d&modal=%d",
			plat, merk, tipe, tahun, modal), "utf-8");
		connection.setRequestProperty("Content-Length", Integer.toString(query.length()));
		DataOutputStream os = new DataOutputStream(connection.getOutputStream());
		os.write(query.getBytes());
		os.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String response = reader.readLine();
		Log.d("prajogotio", "Insert Motor Baru: " + response);
	}

	public static String getDataPembukuanByDate(String tanggal) throws Exception {
		String url = SERVER_URL + "/tarikpembukuanbydate";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		String query = "tanggal=" + tanggal;
		OutputStream os = con.getOutputStream();
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(os, "UTF-8"));
		writer.write(query);
		writer.flush();
		writer.close();
		os.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String result = "";
		String line;
		while ((line = reader.readLine()) != null) {
			result += line + "\n";
		}
		return result;
	}
}
