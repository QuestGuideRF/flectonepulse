package net.flectone.pulse.config.setting;
import net.flectone.pulse.config.Permission;
public interface CooldownPermissionSetting {
    Permission.PermissionEntry cooldownBypass();
}