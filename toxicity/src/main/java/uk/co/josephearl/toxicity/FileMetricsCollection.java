package uk.co.josephearl.toxicity;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

public final class FileMetricsCollection implements Collection<FileMetrics> {
  private final Collection<FileMetrics> fileMetricsCollection;

  private FileMetricsCollection(Collection<FileMetrics> fileMetricsCollection) {
    this.fileMetricsCollection = fileMetricsCollection;
  }

  public static FileMetricsCollection of(Collection<FileMetrics> fileMetricsCollection) {
    return new FileMetricsCollection(fileMetricsCollection);
  }

  public FileToxicitiesCollection toxicities(ToxicityCalculator calculator) {
    return fileMetricsCollection.stream()
        .map(f -> FileToxicities.of(f.file(), calculator.calculate(f.metricReadings())))
        .collect(Collectors.collectingAndThen(Collectors.toList(), FileToxicitiesCollection::of));
  }

  @Override
  public int size() {
    return fileMetricsCollection.size();
  }

  @Override
  public boolean isEmpty() {
    return fileMetricsCollection.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return fileMetricsCollection.contains(o);
  }

  @Override
  public Iterator<FileMetrics> iterator() {
    return fileMetricsCollection.iterator();
  }

  @Override
  public Object[] toArray() {
    return fileMetricsCollection.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return fileMetricsCollection.toArray(a);
  }

  @Override
  public boolean add(FileMetrics fileMetrics) {
    return fileMetricsCollection.add(fileMetrics);
  }

  @Override
  public boolean remove(Object o) {
    return fileMetricsCollection.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return fileMetricsCollection.containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends FileMetrics> c) {
    return fileMetricsCollection.addAll(c);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return fileMetricsCollection.retainAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return fileMetricsCollection.retainAll(c);
  }

  @Override
  public void clear() {
    fileMetricsCollection.clear();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FileMetricsCollection that = (FileMetricsCollection) o;
    return fileMetricsCollection.equals(that.fileMetricsCollection);
  }

  @Override
  public int hashCode() {
    return fileMetricsCollection.hashCode();
  }

  @Override
  public String toString() {
    return "FileMetricsCollection" + fileMetricsCollection.toString();
  }
}
