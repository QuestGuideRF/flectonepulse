package net.flectone.pulse.exception;
public class FileLoadException extends RuntimeException {
    public FileLoadException(String file, Throwable cause) {
        super("Не удалось прочитать " + file + "\n" + cause.getMessage());
    }
}