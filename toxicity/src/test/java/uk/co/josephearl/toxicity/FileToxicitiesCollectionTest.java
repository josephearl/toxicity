package uk.co.josephearl.toxicity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

public class FileToxicitiesCollectionTest {
  @Test
  public void size_isCorrect() {
    Toxicities toxicities = Toxicities.of(new HashMap<String, Toxicity>() {{
      put("CyclomaticComplexity", Toxicity.of(1.1d));
    }});
    FileToxicities fileToxicities1 = FileToxicities.of(new File("Test1.checkstyle"), toxicities);
    FileToxicities fileToxicities2 = FileToxicities.of(new File("Test2.checkstyle"), toxicities);
    FileToxicities fileToxicities3 = FileToxicities.of(new File("Test1.checkstyle"), toxicities);

    FileToxicitiesCollection fileToxicitiesCollection = FileToxicitiesCollection.of(Arrays.asList(
        fileToxicities1,
        fileToxicities2,
        fileToxicities3
    ));

    assertThat(fileToxicitiesCollection).hasSize(3);
  }

  @Test
  public void equals_fileMetricToxicitiesCollection_areEqual() {
    File file1 = new File("Test.checkstyle");
    Toxicities toxicities1 = Toxicities.of(new HashMap<String, Toxicity>() {{
      put("MethodLength", Toxicity.of(2.5d));
      put("FileLength", Toxicity.of(1.2d));
    }});
    FileToxicities fileToxicities1 = FileToxicities.of(file1, toxicities1);
    File file2 = new File("Test.checkstyle");
    Toxicities toxicities2 = Toxicities.of(new HashMap<String, Toxicity>() {{
      put("FileLength", Toxicity.of(1.2d));
      put("MethodLength", Toxicity.of(2.5d));
    }});
    FileToxicities fileToxicities2 = FileToxicities.of(file2, toxicities2);

    FileToxicitiesCollection fileToxicitiesCollection1 =
        FileToxicitiesCollection.of(Arrays.asList(fileToxicities1, fileToxicities2));
    FileToxicitiesCollection fileToxicitiesCollection2 =
        FileToxicitiesCollection.of(Arrays.asList(fileToxicities1, fileToxicities2));

    assertThat(fileToxicitiesCollection1).isEqualTo(fileToxicitiesCollection2);
  }
}
