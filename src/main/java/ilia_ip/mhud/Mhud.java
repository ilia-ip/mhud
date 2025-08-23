package ilia_ip.mhud;

import com.mojang.logging.LogUtils;
import ilia_ip.mhud.config.Config;
import ilia_ip.mhud.config.ConfigScreen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

public class Mhud implements ModInitializer {

	public static boolean ZOOMING;
	public static boolean FREELOOKING;

	public static final Logger LOGGER = LogUtils.getLogger();
	public static final Config CONFIG = Config.load();

	@Override
	public void onInitialize() {

		KeyBinding zoomKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.mhud.zoom",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_MOUSE_BUTTON_5,
				"category.mhud.keys"
		));

		KeyBinding screenKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.mhud.config_screen",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_MINUS,
				"category.mhud.keys"
		));

		KeyBinding freeLookKeybinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.mhud.freelook",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_CAPS_LOCK,
				"category.mhud.keys"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (screenKeyBinding.wasPressed()) {
				MinecraftClient.getInstance().setScreen(new ConfigScreen());
			}

            ZOOMING = zoomKeyBinding.isPressed();

			FREELOOKING = freeLookKeybinding.isPressed();
		});
	}
}