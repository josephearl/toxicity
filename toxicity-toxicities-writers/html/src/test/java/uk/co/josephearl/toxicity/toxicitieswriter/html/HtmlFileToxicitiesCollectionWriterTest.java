package uk.co.josephearl.toxicity.toxicitieswriter.html;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

import org.junit.Test;
import uk.co.josephearl.toxicity.FileToxicities;
import uk.co.josephearl.toxicity.FileToxicitiesCollection;
import uk.co.josephearl.toxicity.Toxicities;
import uk.co.josephearl.toxicity.Toxicity;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.HashMap;

public class HtmlFileToxicitiesCollectionWriterTest {
  @Test
  public void write_isCorrect() throws Exception {
    File expectedToxicitiesJsFile = new File(getClass().getResource("html_file_metric_toxicities_collection_writer_test_toxicities.js").getFile());
    File expectedThresholdsJsFile = new File(getClass().getResource("html_file_metric_toxicities_collection_writer_test_thresholds.js").getFile());
    FileToxicities fileToxicities1 = FileToxicities.of(new File("A.java"),
        Toxicities.of(new HashMap<String, Toxicity>() {{
          put("MethodLength", Toxicity.of(2.5d));
          put("FileLength", Toxicity.of(1.2d));
          put("CyclomaticComplexity", Toxicity.of(1.1d));
        }})
    );
    FileToxicities fileToxicities2 = FileToxicities.of(new File("B.java"),
        Toxicities.of(new HashMap<String, Toxicity>() {{
          put("MethodLength", Toxicity.of(1.5d));
          put("CyclomaticComplexity", Toxicity.of(1.1d));
        }})
    );
    FileToxicities fileToxicities3 = FileToxicities.of(new File("C.java"),
        Toxicities.of(new HashMap<String, Toxicity>() {{
          put("AnonInnerLength", Toxicity.of(1.0d));
        }})
    );
    FileToxicitiesCollection fileToxicitiesCollection = FileToxicitiesCollection.of(Arrays.asList(fileToxicities1, fileToxicities2, fileToxicities3));
    File outputDirectory = Files.createTempDirectory("html_file_toxicities_collection_file_writer_test").toFile();
    File toxicitiesJs = new File(outputDirectory, "toxicities.js");
    File thresholdsJs = new File(outputDirectory, "thresholds.js");

    try {
      try (HtmlFileToxicitiesCollectionWriter htmlFileToxicitiesCollectionWriter = HtmlFileToxicitiesCollectionWriter.create(outputDirectory)) {
        htmlFileToxicitiesCollectionWriter.write(fileToxicitiesCollection);
      }

      assertThat(contentOf(toxicitiesJs)).isEqualTo(contentOf(expectedToxicitiesJsFile));
      assertThat(contentOf(thresholdsJs)).isEqualTo(contentOf(expectedThresholdsJsFile));
    } finally {
      deleteDirectory(outputDirectory);
    }
  }

  private static void deleteDirectory(File directory) throws IOException {
    Files.walkFileTree(directory.toPath(), new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Files.delete(file);
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        Files.delete(dir);
        return FileVisitResult.CONTINUE;
      }
    });
  }
}