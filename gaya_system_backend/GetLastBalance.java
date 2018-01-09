import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;
import java.sql.*;

public class GetLastBalance implements HttpHandler {
	@Override
	public void handle(HttpExchange t) throws IOException {
		try {
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:postgresql://localhost/gaya_system_db","gaya_system_admin","bajingan12345");
			Statement stmt = connection.createStatement();
			String sql = "SELECT saldo FROM pembukuan ORDER BY pid DESC";
			ResultSet rs = stmt.executeQuery(sql);
			String response = "";
			rs.next();
			int saldo = rs.getInt("saldo");
			response = Integer.toString(saldo);
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}