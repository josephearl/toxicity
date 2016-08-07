package uk.co.josephearl.toxicity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.io.File;
import java.util.HashMap;

public class FileToxicitiesTest {
  @Test
  public void file_isCorrect() {
    File file = new File("Test.checkstyle");
    Toxicities toxicities = Toxicities.of(new HashMap<String, Toxicity>() {});

    FileToxicities fileToxicities = FileToxicities.of(file, toxicities);

    assertThat(fileToxicities.file()).isEqualTo(file);
  }

  @Test
  public void metricToxicities_isCorrect() {
    File file = new File("Test.checkstyle");
    Toxicities toxicities = Toxicities.of(new HashMap<String, Toxicity>() {{
      put("MethodLength", Toxicity.of(2.5d));
      put("FileLength", Toxicity.of(1.2d));
    }});

    FileToxicities fileToxicities = FileToxicities.of(file, toxicities);

    assertThat(fileToxicities.metricToxicities()).isEqualTo(toxicities);
  }

  @Test
  public void totalToxicity_sumsToxicities() {
    File file = new File("Test.checkstyle");
    Toxicities toxicities = Toxicities.of(new HashMap<String, Toxicity>() {{
      put("CyclomaticComplexity", Toxicity.of(1.1d));
      put("FileLength", Toxicity.of(1.2d));
      put("MethodLength", Toxicity.of(2.5d));
    }});

    FileToxicities fileToxicities = FileToxicities.of(file, toxicities);

    assertThat(fileToxicities.totalToxicity()).isEqualTo(Toxicity.of(4.8d));
  }

  @Test
  public void equals_fileMetricToxicities_areEqual() {
    File file1 = new File("Test.checkstyle");
    Toxicities toxicities1 = Toxicities.of(new HashMap<String, Toxicity>() {{
      put("MethodLength", Toxicity.of(2.5d));
      put("FileLength", Toxicity.of(1.2d));
    }});
    File file2 = new File("Test.checkstyle");
    Toxicities toxicities2 = Toxicities.of(new HashMap<String, Toxicity>() {{
      put("FileLength", Toxicity.of(1.2d));
      put("MethodLength", Toxicity.of(2.5d));
    }});

    FileToxicities fileToxicities1 = FileToxicities.of(file1, toxicities1);
    FileToxicities fileToxicities2 = FileToxicities.of(file2, toxicities2);

    assertThat(fileToxicities1).isEqualTo(fileToxicities2);
  }
}
