package uk.co.josephearl.toxicity;

import java.util.Comparator;
import java.util.Map;

final class ValueComparator<K extends Comparable<K>, V> implements Comparator<K> {
  private final Map<K, V> map;
  private final Comparator<V> comparator;

  ValueComparator(Map<K, V> map, Comparator<V> comparator) {
    this.map = map;
    this.comparator = comparator;
  }

  @Override
  public int compare(K k1, K k2) {
    V v1 = map.get(k1);
    V v2 = map.get(k2);
    if (v1 == null && v2 == null) return 0;
    if (v1 == null) return 1;
    if (v2 == null) return -1;
    int i = comparator.compare(v1, v2);
    if (i == 0) {
      return k1.compareTo(k2);
    }
    return i;
  }
}
