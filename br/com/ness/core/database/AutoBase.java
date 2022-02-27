package br.com.ness.core.database;

import br.com.ness.core.logger.CoreLogger;
import net.md_5.bungee.api.ChatColor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.StringJoiner;

public class AutoBase {

    public Connection connection;

    public AutoBase(Connection connection){
        this.connection = connection;
    }

    public void createTable(String name, String[] values){
        StringJoiner column = new StringJoiner(", ");
        for(String s : values){
            column.add(s);
        }
        if (connection != null) {
            try {
                connection.prepareStatement("CREATE TABLE IF NOT EXISTS `"+name+"`("+column+")").executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                CoreLogger.SEVERE.sendMessage(ChatColor.RED+"Falha ao criar a tabela "+name+" no MYSQL!");
            }
        }
    }

    public DataTable getTable(String name){
        return new DataTable(name, connection);
    }
}
