import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;
import java.sql.*;

public class DaftarMotorBaru implements HttpHandler {
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    try {
      Connection connection = null;
      Map<String,String> post_query = Utility.parsePostQuery(exchange);
      String plat = post_query.get("plat");
      String merk = post_query.get("merk");
      String tipe = post_query.get("tipe");
      int tahun = Integer.parseInt(post_query.get("tahun"));
      int modal = Integer.parseInt(post_query.get("modal"));
      String sql = String.format(
            "INSERT INTO daftar_motor(tanggal_beli, plat, merk, tipe, tahun, modal, status) VALUES (now(), '%s', '%s', '%s', %d, %d, 0)",
            plat, merk, tipe, tahun, modal);
      connection = DriverManager.getConnection("jdbc:postgresql://localhost/gaya_system_db","gaya_system_admin","bajingan12345");
      Statement stmt = connection.createStatement();
      stmt.execute(sql);
      String response = "OK";
      exchange.sendResponseHeaders(200, response.length());
      OutputStream os = exchange.getResponseBody();
      os.write(response.getBytes());
      os.close();
    } catch (Exception e) {
      System.out.println(e.toString());
      e.printStackTrace();
    }
  }
}
