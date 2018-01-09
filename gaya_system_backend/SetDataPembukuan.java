import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;
import java.sql.*;

public class SetDataPembukuan implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try {
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:postgresql://localhost/gaya_system_db","gaya_system_admin","bajingan12345");
			Map<String,String> m = new HashMap<>();
			m = Utility.parsePostQuery(exchange);
			Statement stmt = connection.createStatement();
			String sql = "UPDATE pembukuan SET keterangan = '"+ m.get("keterangan") +"', debit = '"+ m.get("debit") +"', kredit = '"+ m.get("kredit") +"', saldo = '"+ m.get("saldo") +"' where pid='"+ m.get("pid") +"'";
			stmt.executeUpdate(sql);
			String response = "Edit Successfull";
			exchange.sendResponseHeaders(200, response.length());
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}