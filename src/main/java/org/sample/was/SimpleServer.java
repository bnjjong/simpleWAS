package org.sample.was;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleServer {
  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleServer.class);
  private static JSONObject config;

  static {
    try {
      JSONParser parser = new JSONParser();

      InputStream is = SimpleServer.class.getClassLoader().getResourceAsStream("server.config.json");
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String str;
      StringBuffer sb = new StringBuffer();
      while ((str = br.readLine()) != null) {
        sb.append(str);
      }
      config = (JSONObject) parser.parse(sb.toString());

    } catch (ParseException | IOException e) {
      e.printStackTrace();
    }
  }
  public static void main(String[] args) throws IOException {
    if (config == null) {
      throw new IllegalStateException("please check config file >>>> server.config.json.");
    }
    if(!config.containsKey("port") && config.containsKey("contextRoot")) {
      throw new IllegalStateException("it must contain port and contextRoot key on the config file");
    }
    int port = (int)(long)config.get("port");
    String contextRoot = (String) config.get("contextRoot");
    int maxThread = (int)(long)config.get("maxThread");

    try (ServerSocket listenSocker = new ServerSocket(port)) { // 블록킹 메소드
      LOGGER.info("start server!");
      Socket connection;
      ExecutorService pool = Executors.newFixedThreadPool(maxThread);
      while ((connection = listenSocker.accept()) != null) {

      }
    }

  }
}
