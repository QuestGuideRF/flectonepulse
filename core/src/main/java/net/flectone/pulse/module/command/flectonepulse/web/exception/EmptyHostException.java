package net.flectone.pulse.module.command.flectonepulse.web.exception;
public class EmptyHostException extends IllegalArgumentException {
    public EmptyHostException() {
        super("Параметр хоста не может быть пустым и должен быть настроен в config.yml.");
    }
}