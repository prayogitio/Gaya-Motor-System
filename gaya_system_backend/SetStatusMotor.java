import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;
import java.sql.*;
import org.json.*;

public class SetStatusMotor implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try {
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:postgresql://localhost/gaya_system_db","gaya_system_admin","bajingan12345");
			Statement stmt = connection.createStatement();
			InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			Map<String, String> q = new HashMap<>();
			String[] key_value_pair = query.split("=");	
			q.put(key_value_pair[0], key_value_pair[1]);
			String sql = "update daftar_motor set status = '1' where plat = '"+ q.get("plat") +"'";
			stmt.executeUpdate(sql);
			String response = "Status motor terjual";
			br.close();
			exchange.sendResponseHeaders(200, response.length());
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}