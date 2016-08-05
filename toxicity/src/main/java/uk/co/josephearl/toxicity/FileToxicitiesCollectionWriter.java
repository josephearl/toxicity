package uk.co.josephearl.toxicity;

import java.io.Closeable;
import java.io.IOException;

public interface FileToxicitiesCollectionWriter extends Closeable {
  void write(FileToxicitiesCollection fileToxicitiesCollection) throws IOException;
}
