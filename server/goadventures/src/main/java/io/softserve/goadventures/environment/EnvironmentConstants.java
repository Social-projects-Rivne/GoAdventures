//package io.softserve.goadventures.environment;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//
//public class EnvironmentConstants {
//    private Logger logger = LoggerFactory.getLogger(io.softserve.goadventures.environment.EnvironmentConstants.class);
//
//    @Autowired
//    Environment env;
//
//    public static String apiHostAdress;
//    public  static String apiHostName;
//    public  static String apiPort;
//
//    EnvironmentConstants() {
//        try {
//            apiHostAdress = InetAddress.getLocalHost().getHostAddress();
//            apiHostName = InetAddress.getLocalHost().getHostName();
//            apiPort = env.getProperty("server.port");
//        } catch (UnknownHostException exp) {
//            logger.error("UnknownHostException occurred");
//            exp.printStackTrace();
//            apiHostAdress = "127.0.0.1";
//            apiHostName = "localhost";
//            apiPort = "8080";
//        }
//    }
//}
