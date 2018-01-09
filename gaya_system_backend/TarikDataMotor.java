import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;
import java.sql.*;
import org.json.*;

public class TarikDataMotor implements HttpHandler {
	@Override
	public void handle(HttpExchange t) throws IOException {
		try {
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:postgresql://localhost/gaya_system_db","gaya_system_admin","bajingan12345");
			Statement stmt = connection.createStatement();
			InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			Map<String, String> q = new HashMap<>();
			String[] key_value_pair = query.split("=");	
			q.put(key_value_pair[0], key_value_pair[1]);
			String sql = "select * from daftar_motor where status = '"+ q.get("status") +"'";
			ResultSet rs = stmt.executeQuery(sql);
			JSONArray motor_list = new JSONArray();
			while (rs.next()) {
				int id = rs.getInt("did");
				String tanggal_beli = rs.getDate("tanggal_beli").toString();
				String plat = rs.getString("plat");
				String merk = rs.getString("merk");
				String tipe = rs.getString("tipe");
				int tahun = rs.getInt("tahun");
				int modal = rs.getInt("modal");
				int status = rs.getInt("status");
				JSONObject info_motor = new JSONObject();
				info_motor.put("id", new Integer(id));
				info_motor.put("tanggal_beli", tanggal_beli);
				info_motor.put("plat", plat);
				info_motor.put("merk", merk);
				info_motor.put("tipe", tipe);
				info_motor.put("tahun", new Integer(tahun));
				info_motor.put("modal", new Integer(modal));
				info_motor.put("status", new Integer(status));
				motor_list.put(info_motor);
			}
			JSONObject result = new JSONObject();
			result.put("daftar_motor", motor_list);
			String response = result.toString();
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
