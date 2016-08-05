package uk.co.josephearl.toxicity.metricsreader.checkstyle;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import uk.co.josephearl.toxicity.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public final class CheckstyleFileMetricsCollectionReader implements FileMetricsCollectionReader {
  private static final Pattern DIGITS_PATTERN = Pattern.compile("\\d+((,|.)\\d\\d\\d)*");
  private static final String FILE_NODE_TAG_NAME = "file";
  private static final String FILE_NODE_NAME_ATTRIBUTE = "name";
  private static final String ERROR_NODE_TAG_NAME = "error";
  private static final String ERROR_NODE_SOURCE_ATTRIBUTE = "source";
  private static final String ERROR_NODE_MESSAGE_ATTRIBUTE = "message";
  private final InputStream inputStream;

  private CheckstyleFileMetricsCollectionReader(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  public static CheckstyleFileMetricsCollectionReader create(File file) throws IOException {
    return new CheckstyleFileMetricsCollectionReader(new FileInputStream(file));
  }

  @Override
  public FileMetricsCollection read() throws IOException {
    Document document = parseDocument(inputStream);
    NodeList fileNodes = document.getElementsByTagName(FILE_NODE_TAG_NAME);
    return parseFileMetricReadingsCollection(fileNodes);
  }

  @Override
  public void close() throws IOException {
    inputStream.close();
  }

  private Document parseDocument(InputStream inputStream) throws IOException {
    try {
      return DocumentBuilderFactory.newInstance().newDocumentBuilder()
          .parse(inputStream);
    } catch (SAXException | ParserConfigurationException e) {
      throw new IOException(e);
    }
  }

  private FileMetricsCollection parseFileMetricReadingsCollection(NodeList fileNodes) {
    Collection<FileMetrics> fileMetricsCollection = new ArrayList<>();
    for (int i = 0; i < fileNodes.getLength(); i++) {
      Node fileNode = fileNodes.item(i);
      getAttributeValue(fileNode, FILE_NODE_NAME_ATTRIBUTE)
          .ifPresent(fileName -> fileMetricsCollection.add(parseFileMetricReadings(fileNode, fileName)));
    }
    return FileMetricsCollection.of(fileMetricsCollection);
  }

  private FileMetrics parseFileMetricReadings(Node fileNode, String fileName) {
    return getChildNodesWithTagName(fileNode, ERROR_NODE_TAG_NAME).stream()
        .map(this::readMetricReading)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.collectingAndThen(Collectors.toList(), fileMetricReadingsCollection ->
            FileMetrics.of(new File(fileName), Metrics.of(fileMetricReadingsCollection))));
  }

  private Optional<Metric> readMetricReading(Node errorNode) {
    return getAttributeValue(errorNode, ERROR_NODE_SOURCE_ATTRIBUTE)
        .map(s -> s.replaceAll("^com.puppycrawl.tools.checkstyle.checks.([a-zA-Z0-9]+.)", ""))
        .map(s -> s.replaceAll("Check$", ""))
        .map(type -> {
          int value = getAttributeValue(errorNode, ERROR_NODE_MESSAGE_ATTRIBUTE)
              .map(DIGITS_PATTERN::matcher)
              .filter(Matcher::find)
              .map(Matcher::group)
              .map(s -> s.replace(",", "").replace(".", ""))
              .map(Integer::parseInt)
              .orElse(1);
          return Metric.of(type, value);
        });
  }

  private List<Node> getChildNodesWithTagName(Node node, String childNodeTagName) {
    List<Node> childNodesWithTagName = new ArrayList<>();
    NodeList childNodes = node.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node childNode = childNodes.item(i);
      if (childNodeTagName.equals(childNode.getNodeName())) {
        childNodesWithTagName.add(childNode);
      }
    }
    return childNodesWithTagName;
  }

  private Optional<String> getAttributeValue(Node node, String attributeName) {
    Node attribute = node.getAttributes().getNamedItem(attributeName);
    return attribute != null ? Optional.ofNullable(attribute.getTextContent()) : Optional.empty();
  }
}
