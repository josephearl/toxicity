package uk.co.josephearl.toxicity;

import java.util.*;
import java.util.stream.Collectors;

public final class Toxicities implements SortedMap<String, Toxicity> {
  private final SortedMap<String, Toxicity> sortedMetricToxicities;

  private Toxicities(Map<String, Toxicity> metricToxicities) {
    Map<String, Toxicity> unsortedMetricToxicities = new HashMap<>(metricToxicities);
    SortedMap<String, Toxicity> sortedMetricToxicities = new TreeMap<>(new ValueComparator<>(unsortedMetricToxicities, (t1, t2) -> Double.compare(t2.value(), t1.value())));
    sortedMetricToxicities.putAll(unsortedMetricToxicities);
    this.sortedMetricToxicities = Collections.unmodifiableSortedMap(sortedMetricToxicities);
  }

  public static Toxicities of(Map<String, Toxicity> metricToxicities) {
    return new Toxicities(metricToxicities);
  }

  public Toxicity totalToxicity() {
    return sortedMetricToxicities.values().stream()
        .collect(Collectors.collectingAndThen(Collectors.summingDouble(Toxicity::value), Toxicity::of));
  }

  @Override
  public Comparator<? super String> comparator() {
    return sortedMetricToxicities.comparator();
  }

  @Override
  public SortedMap<String, Toxicity> subMap(String fromKey, String toKey) {
    return sortedMetricToxicities.subMap(fromKey, toKey);
  }

  @Override
  public SortedMap<String, Toxicity> headMap(String toKey) {
    return sortedMetricToxicities.headMap(toKey);
  }

  @Override
  public SortedMap<String, Toxicity> tailMap(String fromKey) {
    return sortedMetricToxicities.tailMap(fromKey);
  }

  @Override
  public String firstKey() {
    return sortedMetricToxicities.firstKey();
  }

  @Override
  public String lastKey() {
    return sortedMetricToxicities.lastKey();
  }

  @Override
  public int size() {
    return sortedMetricToxicities.size();
  }

  @Override
  public boolean isEmpty() {
    return sortedMetricToxicities.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return sortedMetricToxicities.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return sortedMetricToxicities.containsValue(value);
  }

  @Override
  public Toxicity get(Object key) {
    return sortedMetricToxicities.get(key);
  }

  @Override
  public Toxicity put(String key, Toxicity value) {
    return sortedMetricToxicities.put(key, value);
  }

  @Override
  public Toxicity remove(Object key) {
    return sortedMetricToxicities.remove(key);
  }

  @Override
  public void putAll(Map<? extends String, ? extends Toxicity> m) {
    sortedMetricToxicities.putAll(m);
  }

  @Override
  public void clear() {
    sortedMetricToxicities.clear();
  }

  @Override
  public Set<String> keySet() {
    return sortedMetricToxicities.keySet();
  }

  @Override
  public Collection<Toxicity> values() {
    return sortedMetricToxicities.values();
  }

  @Override
  public Set<Entry<String, Toxicity>> entrySet() {
    return sortedMetricToxicities.entrySet();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Toxicities that = (Toxicities) o;
    return sortedMetricToxicities.equals(that.sortedMetricToxicities);
  }

  @Override
  public int hashCode() {
    return sortedMetricToxicities.hashCode();
  }

  @Override
  public String toString() {
    return "Toxicities" + sortedMetricToxicities.toString();
  }
}
