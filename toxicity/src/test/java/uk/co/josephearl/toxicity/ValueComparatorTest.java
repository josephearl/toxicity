package uk.co.josephearl.toxicity;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class ValueComparatorTest {
  @Test
  public void compare_comparesByMapValues() {
    Map<String, Integer> map = new HashMap<String, Integer>() {{
      put("Largest", 6);
      put("Smallest", 2);
      put("Middle", 3);
    }};
    ValueComparator<String, Integer> valueComparator = new ValueComparator<>(map, Integer::compare);

    SortedMap<String, Integer> sortedMap = new TreeMap<>(valueComparator);
    sortedMap.putAll(map);

    assertThat(sortedMap.firstKey()).isEqualTo("Smallest");
    assertThat(sortedMap.lastKey()).isEqualTo("Largest");
  }

  @Test
  public void compare_valueNull_isPositive() {
    Map<String, Integer> map = new HashMap<String, Integer>() {{
      put("Largest", 6);
      put("Smallest", 2);
      put("Middle", 3);
    }};
    ValueComparator<String, Integer> valueComparator = new ValueComparator<>(map, Integer::compare);

    int result = valueComparator.compare("Other", "Largest");

    assertThat(result).isGreaterThanOrEqualTo(1);
  }

  @Test
  public void compare_otherValueNull_isNegative() {
    Map<String, Integer> map = new HashMap<String, Integer>() {{
      put("Largest", 6);
      put("Smallest", 2);
      put("Middle", 3);
    }};
    ValueComparator<String, Integer> valueComparator = new ValueComparator<>(map, Integer::compare);

    int result = valueComparator.compare("Smallest", "Other");

    assertThat(result).isLessThanOrEqualTo(-1);
  }

  @Test
  public void compare_equalValues_comparesKeys() {
    Map<String, Integer> map = new HashMap<String, Integer>() {{
      put("A", 3);
      put("B", 3);
      put("C", 2);
    }};
    ValueComparator<String, Integer> valueComparator = new ValueComparator<>(map, Integer::compare);

    int result = valueComparator.compare("A", "B");

    assertThat(result).isLessThanOrEqualTo(-1);
  }
}
