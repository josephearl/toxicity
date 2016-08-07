package uk.co.josephearl.toxicity.toxicitieswriter.csv;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

import org.junit.Test;
import uk.co.josephearl.toxicity.FileToxicities;
import uk.co.josephearl.toxicity.FileToxicitiesCollection;
import uk.co.josephearl.toxicity.Toxicities;
import uk.co.josephearl.toxicity.Toxicity;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;

public class CsvFileToxicitiesCollectionWriterTest {
  @Test
  public void write_isCorrect() throws Exception {
    File expected = new File(getClass().getResource("csv_file_metric_toxicities_collection_writer_test.csv").getFile());
    FileToxicities fileToxicities = FileToxicities.of(new File("Test.java"),
        Toxicities.of(new HashMap<String, Toxicity>() {{
          put("MethodLength", Toxicity.of(2.5d));
          put("FileLength", Toxicity.of(1.2d));
          put("CyclomaticComplexity", Toxicity.of(1.1d));
        }})
    );
    FileToxicitiesCollection fileToxicitiesCollection = FileToxicitiesCollection.of(Collections.singletonList(fileToxicities));
    File outputFile = File.createTempFile("csv_file_metric_toxicities_collection_writer_test", ".csv");
    assertThat(outputFile.delete()).isTrue();

    try {
      try (CsvFileToxicitiesCollectionWriter csvFileToxicitiesCollectionWriter = CsvFileToxicitiesCollectionWriter.create(outputFile)) {
        csvFileToxicitiesCollectionWriter.write(fileToxicitiesCollection);
      }

      assertThat(contentOf(outputFile)).isEqualTo(contentOf(expected));
    } finally {
      assertThat(outputFile.delete()).isTrue();
    }
  }
}
