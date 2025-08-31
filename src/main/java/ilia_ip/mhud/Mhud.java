package ilia_ip.mhud;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.logging.LogUtils;
import ilia_ip.mhud.config.Config;
import ilia_ip.mhud.config.ConfigScreen;
import ilia_ip.mhud.util.Waypoint;
import ilia_ip.mhud.util.Waypoints;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.command.argument.ColorArgumentType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
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

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager
					.literal("wp")
					.then(ClientCommandManager
							.literal("new")
							.then(ClientCommandManager
									.argument("name", StringArgumentType.string())
									.then(ClientCommandManager
											.argument("color", ColorArgumentType.color())
											.executes(context -> {
												Waypoints.WAYPOINTS.add(new Waypoint(
														context.getSource().getPosition(),
														Text.of(StringArgumentType.getString(context, "name")),
														context.getArgument("color", Formatting.class).getColorValue().intValue()
												));
												return 1;
											})
									)
									.executes(context -> {
										Waypoints.WAYPOINTS.add(new Waypoint(
												context.getSource().getPosition(),
												Text.of(StringArgumentType.getString(context, "name")),
												context.getSource().getPosition().hashCode()
										));
										return 1;
									})
							)
					));
		});
	}
}