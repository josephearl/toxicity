package uk.co.josephearl.toxicity;

import java.util.*;
import java.util.stream.Collectors;

public final class FileToxicitiesCollection implements List<FileToxicities> {
  private final List<FileToxicities> fileToxicitiesList;

  private FileToxicitiesCollection(Collection<FileToxicities> fileToxicitiesCollection) {
    List<FileToxicities> fileToxicitiesList = new ArrayList<>(fileToxicitiesCollection);
    fileToxicitiesList.sort((f1, f2) -> f2.totalToxicity().compareTo(f1.totalToxicity()));
    this.fileToxicitiesList = Collections.unmodifiableList(fileToxicitiesList);
  }

  public static FileToxicitiesCollection of(Collection<FileToxicities> fileToxicitiesCollection) {
    return new FileToxicitiesCollection(fileToxicitiesCollection);
  }

  public Set<String> metricTypes() {
    return fileToxicitiesList.stream()
        .flatMap(f -> f.metricToxicities().keySet().stream())
        .collect(Collectors.toSet());
  }

  @Override
  public int size() {
    return fileToxicitiesList.size();
  }

  @Override
  public boolean isEmpty() {
    return fileToxicitiesList.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return fileToxicitiesList.contains(o);
  }

  @Override
  public Iterator<FileToxicities> iterator() {
    return fileToxicitiesList.iterator();
  }

  @Override
  public Object[] toArray() {
    return fileToxicitiesList.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return fileToxicitiesList.toArray(a);
  }

  @Override
  public boolean add(FileToxicities fileToxicities) {
    return fileToxicitiesList.add(fileToxicities);
  }

  @Override
  public boolean remove(Object o) {
    return fileToxicitiesList.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return fileToxicitiesList.containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends FileToxicities> c) {
    return fileToxicitiesList.addAll(c);
  }

  @Override
  public boolean addAll(int index, Collection<? extends FileToxicities> c) {
    return fileToxicitiesList.addAll(index, c);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return fileToxicitiesList.removeAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return fileToxicitiesList.retainAll(c);
  }

  @Override
  public void clear() {
    fileToxicitiesList.clear();
  }

  @Override
  public FileToxicities get(int index) {
    return fileToxicitiesList.get(index);
  }

  @Override
  public FileToxicities set(int index, FileToxicities element) {
    return fileToxicitiesList.set(index, element);
  }

  @Override
  public void add(int index, FileToxicities element) {
    fileToxicitiesList.add(index, element);
  }

  @Override
  public FileToxicities remove(int index) {
    return fileToxicitiesList.remove(index);
  }

  @Override
  public int indexOf(Object o) {
    return fileToxicitiesList.indexOf(o);
  }

  @Override
  public int lastIndexOf(Object o) {
    return fileToxicitiesList.lastIndexOf(o);
  }

  @Override
  public ListIterator<FileToxicities> listIterator() {
    return fileToxicitiesList.listIterator();
  }

  @Override
  public ListIterator<FileToxicities> listIterator(int index) {
    return fileToxicitiesList.listIterator(index);
  }

  @Override
  public List<FileToxicities> subList(int fromIndex, int toIndex) {
    return fileToxicitiesList.subList(fromIndex, toIndex);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FileToxicitiesCollection that = (FileToxicitiesCollection) o;
    return fileToxicitiesList.equals(that.fileToxicitiesList);
  }

  @Override
  public int hashCode() {
    return fileToxicitiesList.hashCode();
  }

  @Override
  public String toString() {
    return "FileToxicitiesCollection" + fileToxicitiesList.toString();
  }
}
