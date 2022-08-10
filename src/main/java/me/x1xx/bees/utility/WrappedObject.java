package me.x1xx.bees.utility;

public class WrappedObject<F, S> {
    private final F first;
    private final S second;

    public WrappedObject(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }
}
