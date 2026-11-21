package net.flectone.pulse.model;
public record FColor(int number, String name) {
    public enum Type {
        SEE,
        OUT
    }
}