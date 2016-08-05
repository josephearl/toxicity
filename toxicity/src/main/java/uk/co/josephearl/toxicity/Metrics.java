package uk.co.josephearl.toxicity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public final class Metrics implements Collection<Metric> {
  private final Collection<Metric> metrics;

  private Metrics(Collection<Metric> metrics) {
    this.metrics = Collections.unmodifiableList(new ArrayList<>(metrics));
  }

  public static Metrics of(final Collection<Metric> metrics) {
    return new Metrics(metrics);
  }

  @Override
  public int size() {
    return metrics.size();
  }

  @Override
  public boolean isEmpty() {
    return metrics.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return metrics.contains(o);
  }

  @Override
  public Iterator<Metric> iterator() {
    return metrics.iterator();
  }

  @Override
  public Object[] toArray() {
    return metrics.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return metrics.toArray(a);
  }

  @Override
  public boolean add(Metric metric) {
    return metrics.add(metric);
  }

  @Override
  public boolean remove(Object o) {
    return metrics.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return metrics.containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends Metric> c) {
    return metrics.addAll(c);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return metrics.removeAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return metrics.retainAll(c);
  }

  @Override
  public void clear() {
    metrics.clear();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Metrics that = (Metrics) o;
    return metrics.equals(that.metrics);
  }

  @Override
  public int hashCode() {
    return metrics.hashCode();
  }

  @Override
  public String toString() {
    return "Metrics" + metrics.toString();
  }
}
