package net.flectone.pulse.util.checker;
import net.flectone.pulse.config.setting.PermissionSetting;
import net.flectone.pulse.model.entity.FEntity;
public interface PermissionChecker {
    boolean check(FEntity entity, String permission);
    boolean check(FEntity entity, PermissionSetting permission);
}