package br.com.ness.core.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

public class DataTable {

    private final String name;
    private final Connection connection;

    public DataTable(String name, Connection connection){
        this.name = name;
        this.connection = connection;
    }

    public void insert(String[] keys, Object[] values){
        StringJoiner columnKeys = new StringJoiner(", ");
        StringJoiner columnValues = new StringJoiner(", ");

        for(String s : keys){
            columnKeys.add(s);
            columnValues.add("?");
        }

        try {
            PreparedStatement stm = connection.prepareStatement("INSERT INTO `"+name+"`("+columnKeys.toString()+") VALUES ("+columnValues.toString()+")");

            int i = 1;
            for(Object obj : values){
                stm.setObject(i, obj);
                i++;
            }
            stm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public Object get(String key, String value, String column) {
        Object object = null;
        try {
            if (contains(key,value)) {
                PreparedStatement stm = connection.prepareStatement("SELECT * FROM `"+name+"` WHERE `"+key+"` = ?");
                stm.setString(1, value);
                ResultSet rs = stm.executeQuery();
                if (rs.next()) {
                    object = rs.getBytes(column);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void set(String key, String value, String column, Object object) {
        try {
            PreparedStatement stm = connection.prepareStatement("UPDATE `"+name+"` SET `"+column+"` = ? WHERE `"+key+"` = ?");
            stm.setObject(1, object);
            stm.setString(2, value);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean contains(String key, String value) {
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM `"+name+"` WHERE `"+key+"` = ?");
            stm.setString(1, value);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
