import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;
import java.sql.*;
import java.net.*;

public class Utility {
	public static Map<String,String> parsePostQuery(HttpExchange exchange) throws Exception {
		InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String query = URLDecoder.decode(br.readLine(), "utf-8");
		Map<String, String> q = new HashMap<>();
		String[] key_value_pair = query.split("&");	
		for (String kv : key_value_pair) {
			String[] pair = kv.split("=");
			if (pair.length == 1) {
				q.put(pair[0], "");
			} else {
				q.put(pair[0], pair[1]);
			}
		}
		br.close();
		return q;
	}
}