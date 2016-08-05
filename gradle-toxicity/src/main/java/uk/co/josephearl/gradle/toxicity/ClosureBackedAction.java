package uk.co.josephearl.gradle.toxicity;

import java.util.Objects;
import groovy.lang.Closure;
import org.gradle.api.Action;
import org.gradle.api.InvalidActionClosureException;
import org.gradle.util.Configurable;

public class ClosureBackedAction<T> implements Action<T> {
  private final Closure closure;
  private final boolean configureableAware;
  private final int resolveStrategy;

  public static <T> ClosureBackedAction<T> of(Closure<?> closure) {
    return new ClosureBackedAction<T>(closure);
  }

  public ClosureBackedAction(Closure closure) {
    this(closure, Closure.DELEGATE_FIRST, true);
  }

  public ClosureBackedAction(Closure closure, int resolveStrategy) {
    this(closure, resolveStrategy, false);
  }

  public ClosureBackedAction(Closure closure, int resolveStrategy, boolean configureableAware) {
    this.closure = closure;
    this.configureableAware = configureableAware;
    this.resolveStrategy = resolveStrategy;
  }

  public static <T> void execute(T delegate, Closure<?> closure) {
    new ClosureBackedAction<T>(closure).execute(delegate);
  }

  public void execute(T delegate) {
    if (closure == null) {
      return;
    }

    try {
      if (configureableAware && delegate instanceof Configurable) {
        ((Configurable) delegate).configure(closure);
      } else {
        Closure copy = (Closure) closure.clone();
        copy.setResolveStrategy(resolveStrategy);
        copy.setDelegate(delegate);
        if (copy.getMaximumNumberOfParameters() == 0) {
          copy.call();
        } else {
          copy.call(delegate);
        }
      }
    } catch (groovy.lang.MissingMethodException e) {
      if (Objects.equals(e.getType(), closure.getClass()) && Objects.equals(e.getMethod(), "doCall")) {
        throw new InvalidActionClosureException(closure, delegate);
      }
      throw e;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ClosureBackedAction that = (ClosureBackedAction) o;

    if (configureableAware != that.configureableAware) {
      return false;
    }
    if (resolveStrategy != that.resolveStrategy) {
      return false;
    }
    if (!closure.equals(that.closure)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = closure.hashCode();
    result = 31 * result + (configureableAware ? 1 : 0);
    result = 31 * result + resolveStrategy;
    return result;
  }
}
