package us.blav.commons;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public final class Throwings {

  private Throwings () {
  }

  @FunctionalInterface
  public interface Function<IN, OUT> {

    OUT apply (IN in) throws Exception;

  }

  @FunctionalInterface
  public interface Supplier<T> {

    T get () throws Exception;

  }

  @FunctionalInterface
  public interface Consumer<T> {

    void accept (T in) throws Exception;

  }

  @FunctionalInterface
  public interface BiConsumer<T, U> {

    void accept (T t, U u) throws Exception;

  }

  @FunctionalInterface
  public interface IntConsumer {

    void accept (int in) throws Exception;

  }

  @FunctionalInterface
  public interface Predicate<T> {

    boolean test (T in) throws Exception;

  }

  @FunctionalInterface
  public interface BiFunction<IN1, IN2, OUT> {

    OUT apply (IN1 in1, IN2 in2) throws Exception;

  }

  @FunctionalInterface
  public interface Runnable {

    void run () throws Exception;

  }

  public static <T> java.util.function.Consumer<T> wrapConsumer (Consumer<T> throwing) {
    return t -> {
      try {
        throwing.accept (t);
      } catch (RuntimeException e) {
        throw e;
      } catch (Exception e) {
        throw new RuntimeException (e);
      }
    };
  }

  public static <T, U> java.util.function.BiConsumer<T, U> wrapBiConsumer (BiConsumer<T, U> throwing) {
    return (t, u) -> {
      try {
        throwing.accept (t, u);
      } catch (RuntimeException e) {
        throw e;
      } catch (Exception e) {
        throw new RuntimeException (e);
     }
    };
  }

  public static <T> java.util.function.IntConsumer wrapIntConsumer (IntConsumer throwing) {
    return t -> {
      try {
        throwing.accept (t);
      } catch (RuntimeException e) {
        throw e;
      } catch (Exception e) {
        throw new RuntimeException (e);
      }
    };
  }

  public static <T> java.util.function.Supplier<T> wrapSupplier (Supplier<T> throwing) {
    return () -> {
      try {
        return throwing.get ();
      } catch (RuntimeException e) {
        throw e;
      } catch (Exception e) {
        throw new RuntimeException (e);
      }
    };
  }

  public static <T> java.util.function.Predicate<T> wrapPredicate (Predicate<T> throwing) {
    return t -> {
      try {
        return throwing.test (t);
      } catch (RuntimeException e) {
        throw e;
      } catch (Exception e) {
        throw new RuntimeException (e);
      }
    };
  }

  public static <IN, OUT> java.util.function.Function<IN, OUT> wrap (Function<IN, OUT> throwing) {
    return in -> {
      try {
        return throwing.apply (in);
      } catch (RuntimeException e) {
        throw e;
      } catch (Exception e) {
        throw new RuntimeException (e);
      }
    };
  }

  public static <IN, OUT> java.util.function.Function<IN, OUT> wrap (Function<IN, OUT> throwing, java.util.function.Function<Exception, OUT> returnOnException) {
    requireNonNull (throwing, "throwing");
    requireNonNull (returnOnException, "returnOnException");
    return in -> {
      try {
        return throwing.apply (in);
      } catch (Exception e) {
        return returnOnException.apply (e);
      }
    };
  }

  public static <IN1, IN2, OUT> java.util.function.BiFunction<IN1, IN2, OUT> wrap (BiFunction<IN1, IN2, OUT> throwing) {
    return (in1, in2) -> {
      try {
        return throwing.apply (in1, in2);
      } catch (RuntimeException e) {
        throw e;
      } catch (Exception e) {
        throw new RuntimeException (e);
      }
    };
  }

  public static java.lang.Runnable wrap (Runnable throwing) {
    return () -> {
      try {
        throwing.run ();
      } catch (RuntimeException e) {
        throw e;
      } catch (Exception e) {
        throw new RuntimeException (e);
      }
    };
  }

  public static Optional<Exception> runQuietly (Runnable runnable) {
    try {
      if (runnable != null)
        runnable.run ();
      return Optional.empty ();
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      return Optional.of (e);
    }
  }

  public static java.lang.Runnable quiet (Runnable runnable) {
    return () -> runQuietly (runnable);
  }
}
