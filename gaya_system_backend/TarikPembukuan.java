import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TarikPembukuan implements HttpHandler {
	@Override
	public void handle(HttpExchange t) throws IOException {
		try {
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:postgresql://localhost/gaya_system_db","gaya_system_admin","bajingan12345");
			Statement stmt = connection.createStatement();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDate localDate = LocalDate.now();
			String sql = "select * from pembukuan where tanggal = '"+ dtf.format(localDate) +"' order by pid asc";
			ResultSet rs = stmt.executeQuery(sql);
			String response = "";
			while (rs.next()) {
				int id = rs.getInt("pid");
				String tanggal = rs.getDate("tanggal").toString();
				String keterangan = rs.getString("keterangan");
				int debit = rs.getInt("debit");
				int kredit = rs.getInt("kredit");
				int saldo = rs.getInt("saldo");
				response = response + id + " | " + tanggal + " | " + keterangan + " | " + debit + " | " + kredit + " | " + saldo + " | "+ System.lineSeparator();
			}
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}