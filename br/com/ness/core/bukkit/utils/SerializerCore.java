package br.com.ness.core.bukkit.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SerializerCore {

    public String getLocationString(Location location){
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        String world = location.getWorld().getName();
        return x + ";" + y + ";" + z + ";" + yaw + ";" + pitch + ";" + world;
    }

    public Location getLocation(String data){
        String[] s = data.split(";");
        double x = Double.parseDouble(s[0]);
        double y = Double.parseDouble(s[1]);
        double z = Double.parseDouble(s[2]);
        float yaw = Float.parseFloat(s[3]);
        float pitch = Float.parseFloat(s[4]);
        World world = Bukkit.getWorld(s[5]);
        Location location = new Location(world, x, y, z);
        location.setYaw(yaw);
        location.setPitch(pitch);
        return location;
    }

    public static String getDate(Date date){
        DateFormat day = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat hour = new SimpleDateFormat("hh:mm");
        return day.format(date)+" ás "+hour.format(date);
    }

    public static String getTime(Long longTime){
        String s = (longTime/1000%60)+"s";
        String m = (longTime/60000%60)+"m";
        String h = (longTime/3600000%24)+"h";
        String d = (longTime/86400000)+"d";
        return d+" "+h+" "+m+" "+s;
    }

    public static List<String> replaceAll(List<String> list, String oldString, String newString){
        List<String> stringList = new ArrayList<>();
        for (String s : list){
            stringList.add(s.replace(oldString, newString));
        }
        return stringList;
    }
}
