package ilia_ip.mhud.util;

public interface CameraOverriddenEntity {
    float mhud$getCameraPitch();

    float mhud$getCameraYaw();

    void mhud$setCameraPitch(float pitch);

    void mhud$setCameraYaw(float yaw);
}
