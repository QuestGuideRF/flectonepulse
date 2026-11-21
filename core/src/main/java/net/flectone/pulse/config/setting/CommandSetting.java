package net.flectone.pulse.config.setting;
import java.util.List;
public interface CommandSetting extends CooldownConfigSetting, SoundConfigSetting, EnableSetting {
    List<String> aliases();
}