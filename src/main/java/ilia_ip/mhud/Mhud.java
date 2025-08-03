package ilia_ip.mhud;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mhud implements ModInitializer {
	public static final String MOD_ID = "mhud";
	public static final Config CONFIG = new Config();

	@Override
	public void onInitialize() {

		HudElementRegistry.addFirst(Identifier.of(MOD_ID, "armor_hud"), new ArmorHud());
	}
}