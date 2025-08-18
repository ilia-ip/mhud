package ilia_ip.mhud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.world.gen.feature.BlueIceFeature;

import java.util.concurrent.atomic.AtomicInteger;

public class ConfigScreen extends Screen {

    protected ConfigScreen() {
        super(Text.translatable("conf.mhud.title"));
    }

    @Override
    protected void init() {
        AtomicInteger x = new AtomicInteger(50);
        AtomicInteger y = new AtomicInteger(50);

        Mhud.CONFIG.forEach((keyObject, value) -> {
            if (keyObject instanceof String key) {
                addDrawableChild(
                        ButtonWidget.builder(
                                keyText(key),
                                (widget) -> {
                                    Mhud.invertProperty(key);
                                    widget.setMessage(keyText(key));
                                    Mhud.saveCfg();
                                }
                        ).dimensions(x.get(), y.get(), 200, 20).build()
                );

                if (y.get() >= height - 70) {
                    y.set(50);
                    x.addAndGet(250);
                } else {
                    y.addAndGet(30);
                }
            }
        });
    }

    private Text keyText(String key) {
        return ScreenTexts.composeToggleText(Text.translatable("conf.mhud." + key), Mhud.enabled(key));
    }
}
