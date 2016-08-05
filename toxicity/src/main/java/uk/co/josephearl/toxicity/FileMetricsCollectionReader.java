package uk.co.josephearl.toxicity;

import java.io.Closeable;
import java.io.IOException;

public interface FileMetricsCollectionReader extends Closeable {
  FileMetricsCollection read() throws IOException;
}
