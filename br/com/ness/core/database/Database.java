package br.com.ness.core.database;

import br.com.ness.core.logger.CoreLogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Connection connection;
    private static AutoBase autoBase;

    private static final String username = "root";
    private static final String password = "";
    private static final String database = "fryzzen";
    private static final String host = "127.0.0.1";
    private static final String port = "3306";

    public static void open(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database, username, password);
            autoBase = new AutoBase(connection);
            CoreLogger.SUCESS.sendMessage("Conexão com o MYSQL aberta com sucesso!");
        }catch(Exception e){
            e.printStackTrace();
            CoreLogger.SEVERE.sendMessage("Falha ao abrir a conexão com o MYSQL!");
        }
    }


    public static void close(){
        if(connection!=null){
            try {
                connection.close();
                CoreLogger.SEVERE.sendMessage("Conexão com o MYSQL fechada com sucesso!");
            }catch (SQLException e){
                e.printStackTrace();
                CoreLogger.SEVERE.sendMessage("Falha ao fechar a conexão com o MYSQL!");
            }
        }
    }

    public static Connection getCon(){
        return connection;
    }
    public static AutoBase getAutoBase(){ return autoBase; }
}
