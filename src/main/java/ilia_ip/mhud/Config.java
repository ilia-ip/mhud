package ilia_ip.mhud;

import com.mojang.logging.LogUtils;
import net.minecraft.client.render.fog.FogRenderer;
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
    public boolean Nameplate3dPerson = true;
    public boolean NoFog = true;
    public boolean Fullbright = true;
    public boolean LowFire = true;

    public void read() {
        File cfg_file = new File("config/mhud");

        try {
            String cfg = FileUtils.readFileToString(cfg_file, "UTF-8");

            String[] parts = cfg.split("\\|");
            if (parts.length < 9) return;

            ArmorHudEnabled = Objects.equals(parts[0], "true");
            PotionHudEnabled = Objects.equals(parts[1], "true");
            Crosshair3dPerson = Objects.equals(parts[2], "true");
            CrosshairIndicator = Objects.equals(parts[3], "true");
            SmallUtils = Objects.equals(parts[4], "true");
            SideShield = Objects.equals(parts[5], "true");
            Nameplate3dPerson = Objects.equals(parts[6], "true");
            NoFog = Objects.equals(parts[7], "true");
            Fullbright = Objects.equals(parts[8], "true");
            LowFire = Objects.equals(parts[8], "true");

        } catch (IOException e) {
            LogUtils.getLogger().error("Error reading config file");
        }
    }

    public void save() {
        String cfg = Mhud.CONFIG.ArmorHudEnabled + "|"
                + Mhud.CONFIG.PotionHudEnabled + "|"
                + Mhud.CONFIG.Crosshair3dPerson + "|"
                + Mhud.CONFIG.CrosshairIndicator + "|"
                + Mhud.CONFIG.SmallUtils + "|"
                + Mhud.CONFIG.SideShield + "|"
                + Mhud.CONFIG.Nameplate3dPerson + "|"
                + Mhud.CONFIG.NoFog + "|"
                + Mhud.CONFIG.Fullbright + "|"
                + Mhud.CONFIG.LowFire;

        File cfg_file = new File("config/mhud");

        try {
            cfg_file.createNewFile();
            FileUtils.writeStringToFile(cfg_file, cfg);
        } catch (IOException e) {
            LogUtils.getLogger().error("Error creating config file");
        }
    }
}
