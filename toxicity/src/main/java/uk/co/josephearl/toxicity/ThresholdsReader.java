package uk.co.josephearl.toxicity;

import java.io.Closeable;
import java.io.IOException;

public interface ThresholdsReader extends Closeable {
  Thresholds read() throws IOException;
}
