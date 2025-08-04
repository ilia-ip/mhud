package ilia_ip.mhud;

import com.mojang.logging.LogUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Config {
    public boolean ArmorHudEnabled = true;
    public boolean PotionHudEnabled = true;
    public boolean Crosshair3dPerson = true;
    public boolean CrosshairIndicator = true;
    public boolean SmallUtils = true;
    public boolean SideShield = true;

    public void read() {
        File cfg_file = new File("config/mhud");

        try {
            String cfg = FileUtils.readFileToString(cfg_file, "UTF-8");

            String[] parts = cfg.split("\\|");
            if (parts.length < 6) return;

            ArmorHudEnabled = Objects.equals(parts[0], "true");
            PotionHudEnabled = Objects.equals(parts[1], "true");
            Crosshair3dPerson = Objects.equals(parts[2], "true");
            CrosshairIndicator = Objects.equals(parts[3], "true");
            SmallUtils = Objects.equals(parts[4], "true");
            SideShield = Objects.equals(parts[5], "true");

        } catch (IOException e) {
            LogUtils.getLogger().error("Error reading config file");
        }
    }

    public void save() {
        String cfg = Mhud.CONFIG.ArmorHudEnabled + "|" + Mhud.CONFIG.PotionHudEnabled + "|" + Mhud.CONFIG.Crosshair3dPerson + "|" + Mhud.CONFIG.CrosshairIndicator + "|" + Mhud.CONFIG.SmallUtils + "|" + Mhud.CONFIG.SideShield;

        File cfg_file = new File("config/mhud");

        try {
            cfg_file.createNewFile();
            FileUtils.writeStringToFile(cfg_file, cfg);
        } catch (IOException e) {
            LogUtils.getLogger().error("Error creating config file");
        }
    }
}
