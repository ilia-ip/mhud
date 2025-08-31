package ilia_ip.mhud.modules.waypoint;

import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    /**
     * @author author
     * @reason reason
     */
    @Overwrite
    private boolean shouldShowExperienceBar() {
        return true;
    }
}
