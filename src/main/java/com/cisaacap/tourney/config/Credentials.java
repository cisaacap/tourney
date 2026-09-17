package main.java.com.cisaacap.tourney.config;

public class Credentials {

    public static final String DATA_BASE = "tourney_in4bm";
    public static final String URL_DB = "jdbc:mysql://localhost:3306/" + DATA_BASE + "?useSSL=false&serverTimezone=UTC";
    public static final String PASS_DB = "$DmynM4A";
    public static final String USER_DB = "IN4BM";

}

/*    
    public static final String DATA_BASE = System.getenv("DATA_BASE");
    public static final String URL_DB = System.getenv("URL_DB");
    public static final String PASS_DB = System.getenv("PASS_DB");
    public static final String USER_DB = System.getenv("USER_DB");

*/