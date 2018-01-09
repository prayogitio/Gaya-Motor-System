import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;
import java.sql.*;

public class Server{
	public static void main(String[] args) throws Exception {
		Class.forName("org.postgresql.Driver");
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/tarikpembukuan", new TarikPembukuan());
		server.createContext("/getlastbalance", new GetLastBalance());
		server.createContext("/tarikpembukuanbyid", new TarikPembukuanById());
		server.createContext("/tarikpembukuanbydate", new TarikPembukuanByDate());
		server.createContext("/setdatapembukuan", new SetDataPembukuan());
		server.createContext("/tarikdatamotor", new TarikDataMotor());
		server.createContext("/tarikdatamotorbyplat", new TarikDataMotorByPlat());
		server.createContext("/insertdatapembukuan", new InsertData());
		server.createContext("/insertmotorbaru", new DaftarMotorBaru());
		server.createContext("/setstatusmotor", new SetStatusMotor());
		server.setExecutor(null);
		server.start();
	}
}
