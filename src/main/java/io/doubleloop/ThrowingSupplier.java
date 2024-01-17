package io.doubleloop;

public interface ThrowingSupplier<A, E extends Throwable> {
  A get() throws E;
}
