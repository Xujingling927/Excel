package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Base64;

public class BaseDao {
    private static final String jdbc_driver = "com.mysql.cj.jdbc.Driver";
    private static final String db_url = "jdbc:mysql://localhost:3306/mydatabase?characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String db_user = "eHVqaW5nbGluZw=="; //base64 encode
    private static final String db_password = "MTIzNDU2";

    private static BaseDao instance;
    private Connection connection;
    public static BaseDao getInstance(){
        if (instance == null){
            instance = new BaseDao();
        }
        return instance;
    }

    public BaseDao(){
        try {
            Class.forName(jdbc_driver);
            byte[] decode = Base64.getDecoder().decode(db_password);  // base64 decode
            String DB_PASSWORD = new String(decode,"utf-8");
            decode = Base64.getDecoder().decode(db_user);
            String DB_USER = new String(decode,"utf-8");
            connection = DriverManager.getConnection(db_url,DB_USER,DB_PASSWORD);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public Connection getConnection(){
        return connection;
    }
}
