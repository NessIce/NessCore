package br.com.ness.core.bukkit.utils;

import java.text.DecimalFormat;

public class TimeUtils {
    
    public static String getCowndown(long entry, String text){
        String message = text;
        
        DecimalFormat df = new DecimalFormat("###.#");
        
        if (entry > System.currentTimeMillis()) {
            double time = (entry - System.currentTimeMillis()) / 1000.0;
            if (time > 0.1) {
                String timeString = df.format(time).replace(",", ".");
                if (timeString.endsWith("0")) {
                    timeString = timeString.substring(0, timeString.lastIndexOf("."));
                }
                message = message.replace("{time}", timeString);
            }
        }
        return message;
    }
}
