import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;
import java.sql.*;

public class InsertData implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try {
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:postgresql://localhost/gaya_system_db","gaya_system_admin","bajingan12345");
			Map<String,String> m = new HashMap<>();
			m = Utility.parsePostQuery(exchange);
			Statement stmt = connection.createStatement();
			String sql = "INSERT INTO pembukuan (tanggal, keterangan, debit, kredit, saldo) values('"+ m.get("tanggal") +"','"+ m.get("keterangan") +"','"+ Integer.parseInt(m.get("debit")) +"','"+ Integer.parseInt(m.get("kredit")) +"','"+ Integer.parseInt(m.get("saldo")) +"')";
			stmt.executeUpdate(sql);
			String response = "Data Inserted";
			exchange.sendResponseHeaders(200, response.length());
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}