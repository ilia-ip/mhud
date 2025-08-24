package ilia_ip.mhud.config;

import ilia_ip.mhud.Mhud;

import java.io.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


public class Config implements Serializable {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface ConfigOption {
        String category();

        String name();
    }

    @ConfigOption(category = "hud", name = "armor_hud")
    public boolean ARMOR_HUD = true;

    @ConfigOption(category = "hud", name = "potion_hud")
    public boolean POTION_HUD = true;

    @ConfigOption(category = "hud", name = "better_subtitles")
    public boolean BETTER_SUBTITLES = true;

    @ConfigOption(category = "hud", name = "small_totem_pop")
    public boolean SMALL_TOTEM_POP = true;

    @ConfigOption(category = "world", name = "low_fire")
    public boolean LOW_FIRE = true;

    @ConfigOption(category = "world", name = "no_fog")
    public boolean NO_FOG = true;

    @ConfigOption(category = "world", name = "hitbox_indicator")
    public boolean HITBOX_INDICATOR = true;

    @ConfigOption(category = "world", name = "full_bright")
    public boolean FULL_BRIGHT = true;

    @ConfigOption(category = "world", name = "less_particles")
    public boolean LESS_PARTICLES = true;

    @ConfigOption(category = "item", name = "small_utils")
    public boolean SMALL_UTILS = true;

    @ConfigOption(category = "item", name = "side_shield")
    public boolean SIDE_SHIELD = true;

    @ConfigOption(category = "item", name = "big_important_items")
    public boolean BIG_IMPORTANT_ITEMS = true;

    @ConfigOption(category = "item", name = "despawn_timer")
    public boolean DESPAWN_TIMER = true;

    public void save() {
        try (FileOutputStream fileOut = new FileOutputStream("mhud.config"); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this);
        } catch (Exception e) {
            Mhud.LOGGER.error("Error saving configuration file");
        }
    }

    public static Config load() {
        try (FileInputStream fileIn = new FileInputStream("mhud.config"); ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (Config) in.readObject();
        } catch (Exception e) {
            Mhud.LOGGER.error("Error loading configuration file, reverting to default values");
            return new Config();
        }
    }

    public static boolean enabled(String a) {
        return true;
    }
}
