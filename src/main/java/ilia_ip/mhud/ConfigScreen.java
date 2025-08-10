package ilia_ip.mhud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

import java.util.concurrent.atomic.AtomicInteger;

public class ConfigScreen extends Screen {

    public static final Text ENABLED = Text.translatable("conf.mhud.enabled");
    public static final Text DISABLED = Text.translatable("conf.mhud.disabled");

    protected ConfigScreen() {
        super(Text.of("Mhud Configuration Screen"));
    }

    @Override
    protected void init() {
        AtomicInteger x = new AtomicInteger(50);
        AtomicInteger y = new AtomicInteger(50);

        Mhud.CONFIG.forEach((keyObject, value) -> {
            if (keyObject instanceof String key) {

                addDrawableChild(
                        new TextWidget(x.get(), y.get() - 20, 200, 20, Text.translatable("conf.mhud." + key), textRenderer)
                );
                addDrawableChild(
                        ButtonWidget.builder(
                                Mhud.enabled(key) ? ENABLED : DISABLED,
                                (widget) -> {
                                    Mhud.CONFIG.setProperty(key, Mhud.invert(Mhud.CONFIG.getProperty(key)));

                                    widget.setMessage(Mhud.enabled(key) ? ENABLED : DISABLED);

                                    Mhud.saveCfg();
                                }
                        ).dimensions(x.get(), y.get(), 200, 20).build()
                );

                if (y.get() >= MinecraftClient.getInstance().getWindow().getScaledHeight() - 70) {
                    y.set(50);
                    x.addAndGet(250);
                } else {
                    y.addAndGet(50);
                }
            }
        });
    }
}
