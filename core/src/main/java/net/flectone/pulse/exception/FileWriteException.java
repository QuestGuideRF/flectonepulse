package net.flectone.pulse.exception;
public class FileWriteException extends RuntimeException {
    public FileWriteException(String file, Throwable cause) {
        super("Не удалось написать " + file + "\n" + cause.getMessage());
    }
}