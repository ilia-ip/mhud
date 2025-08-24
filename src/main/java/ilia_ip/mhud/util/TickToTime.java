package ilia_ip.mhud.util;

import net.minecraft.entity.effect.StatusEffectInstance;

public class TickToTime {
    public static String convert(int ticks) {
        String time;
        if (ticks/20 >= 60) {
            if (ticks/20/60 >= 60) {
                time = ticks/20/60/60 + "h";
            } else {
                time = ticks/20/60 + "m";
            }
        } else {
            time = String.valueOf(ticks/20);
        }
        return time;
    }
}
