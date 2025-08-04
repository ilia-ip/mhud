package ilia_ip.mhud;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mhud implements ModInitializer {
	public static final String MOD_ID = "mhud";
	public static final Config CONFIG = new Config();

	@Override
	public void onInitialize() {
		CONFIG.read();

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

		HudElementRegistry.addFirst(Identifier.of(MOD_ID, "armor_hud"), new ArmorHud());
	}
}