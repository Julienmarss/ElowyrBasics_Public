package fr.elowyr.basics.interfaces;

@FunctionalInterface
public interface ThrowableBiConsumer<A, B>
{
    void accept(final A p0, final B p1) throws Throwable;
}
