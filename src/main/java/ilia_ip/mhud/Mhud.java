package ilia_ip.mhud;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.fog.FogRenderer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Objects;
import java.util.Properties;

public class Mhud implements ModInitializer {
	public static final String MOD_ID = "mhud";
	public static final Properties CONFIG = new Properties();

	public static void readCfg() {
		CONFIG.setProperty("armor_hud", "false");
		CONFIG.setProperty("crosshair_indicator", "false");
		CONFIG.setProperty("third_person_crosshair", "false");
		CONFIG.setProperty("small_utils", "false");
		CONFIG.setProperty("small_items", "false");
		CONFIG.setProperty("side_shield", "false");
		CONFIG.setProperty("third_person_nameplate", "false");
		CONFIG.setProperty("no_fog", "false");
		CONFIG.setProperty("low_fire", "false");
		CONFIG.setProperty("full_bright", "false");
		CONFIG.setProperty("potion_hud", "false");

		try (FileReader cfg_file = new FileReader("config/mhud.properties")) {
			CONFIG.load(cfg_file);
			LogUtils.getLogger().info("!!!!!");
		} catch (Exception e) {
			// e
		}
	}

	public static void saveCfg() {
		try (FileWriter cfg_file = new FileWriter("config/mhud.properties")) {
			CONFIG.store(cfg_file, "Mhud Configuration");
		} catch (Exception e) {
			// e
		}
	}

	public static boolean enabled(String key) {
		return Objects.equals(CONFIG.getOrDefault(key, "false"), "true");
	}

	public static String invert(String value) {
		return Objects.equals(value, "true") ? "false" : "true";
	}

	@Override
	public void onInitialize() {
		readCfg();

		KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.mhud.config_screen",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_MINUS,
				"category.mhud.keys"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (keyBinding.wasPressed()) {
				MinecraftClient.getInstance().setScreen(new ConfigScreen());
			}
		});
	}
}