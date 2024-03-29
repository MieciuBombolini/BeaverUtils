package dev.bebomny.beaver.beaverutils.helpers;

import dev.bebomny.beaver.beaverutils.features.KeyOnOffFeature;
import net.minecraft.text.Text;

public class TextUtils {

    public static Text getEnabledDisabledText(String name, boolean isEnabled) {
        return Text.of(name + (isEnabled ? " Enabled" : " Disabled"));
    }

    public static Text getOnOffText(String name, boolean state) {
        return Text.of(name + (state ? " ON" : " OFF"));
    }
}
