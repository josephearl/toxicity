package uk.co.josephearl.toxicity;

import java.util.*;
import java.util.stream.Collectors;

public final class Thresholds implements Set<Threshold> {
  private final Map<String, Threshold> metricThresholdMap;
  private final Set<Threshold> thresholds;

  private Thresholds(Map<String, Threshold> metricThresholdMap) {
    this.metricThresholdMap = Collections.unmodifiableMap(new HashMap<>(metricThresholdMap));
    thresholds = Collections.unmodifiableSet(new HashSet<>(metricThresholdMap.values()));
  }

  public static Thresholds of(Set<Threshold> thresholds) {
    return thresholds.stream()
        .collect(Collectors.collectingAndThen(Collectors.toMap(Threshold::type, t -> t), Thresholds::new));
  }

  public boolean containsMetricType(String metricType) {
    return metricThresholdMap.containsKey(metricType);
  }

  public Threshold get(String metricType) {
    return metricThresholdMap.get(metricType);
  }

  public Set<String> metricTypes() {
    return metricThresholdMap.keySet();
  }

  @Override
  public int size() {
    return metricThresholdMap.size();
  }

  @Override
  public boolean isEmpty() {
    return metricThresholdMap.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return thresholds.contains(o);
  }

  @Override
  public Iterator<Threshold> iterator() {
    return thresholds.iterator();
  }

  @Override
  public Object[] toArray() {
    return thresholds.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return thresholds.toArray(a);
  }

  @Override
  public boolean add(Threshold threshold) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean remove(Object metricThreshold) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return thresholds.containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends Threshold> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Thresholds that = (Thresholds) o;
    return metricThresholdMap.equals(that.metricThresholdMap);
  }

  @Override
  public int hashCode() {
    return metricThresholdMap.hashCode();
  }

  @Override
  public String toString() {
    return "Thresholds" + metricThresholdMap.toString();
  }
}