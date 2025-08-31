package ilia_ip.mhud.util;

import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public record Waypoint(Vec3d pos, Text name, int color) {
}
