package uk.co.josephearl.toxicity.thresholdsreader.checkstyle;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import uk.co.josephearl.toxicity.Metric;
import uk.co.josephearl.toxicity.Threshold;
import uk.co.josephearl.toxicity.Thresholds;
import uk.co.josephearl.toxicity.ThresholdsReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public final class CheckstyleThresholdsReader implements ThresholdsReader {
  private static final List<String> IGNORED_NAMES = Arrays.asList("TreeWalker", "Checker", "FileContentsHolder");
  private static final String MODULE_NODE_TAG_NAME = "module";
  private static final String MODULE_NODE_NAME_ATTRIBUTE = "name";
  private static final String PROPERTY_NODE_TAG_NAME = "property";
  private static final String PROPERTY_NODE_NAME_ATTRIBUTE = "name";
  private static final String PROPERTY_NODE_VALUE_ATTRIBUTE = "value";
  private static final String MAX_PROPERTY_NODE_NAME_ATTRIBUTE_VALUE = "max";
  private final InputStream inputStream;

  private CheckstyleThresholdsReader(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  public static CheckstyleThresholdsReader create(File file) throws IOException {
    return new CheckstyleThresholdsReader(new FileInputStream(file));
  }

  @Override
  public Thresholds read() throws IOException {
    Document document = parseDocument(inputStream);
    NodeList moduleNodes = document.getElementsByTagName(MODULE_NODE_TAG_NAME);
    return parseMetricThresholds(moduleNodes);
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

  private Thresholds parseMetricThresholds(NodeList modules) {
    Set<Threshold> thresholds = new HashSet<>();
    for (int i = 0; i < modules.getLength(); i++) {
      Node moduleNode = modules.item(i);
      getAttributeValue(moduleNode, MODULE_NODE_NAME_ATTRIBUTE)
          .filter(name -> !ignoreModule(name))
          .map(metricType -> Threshold.of(Metric.of(metricType, parseMax(moduleNode))))
          .ifPresent(thresholds::add);
    }
    return Thresholds.of(thresholds);
  }

  private boolean ignoreModule(String moduleName) {
    return IGNORED_NAMES.contains(moduleName);
  }

  private int parseMax(Node moduleNode) {
    return getMaxPropertyNode(moduleNode)
        .flatMap(maxPropertyNode -> getAttributeValue(maxPropertyNode, PROPERTY_NODE_VALUE_ATTRIBUTE))
        .map(Integer::parseInt)
        .orElse(1);
  }

  private Optional<Node> getMaxPropertyNode(Node moduleNode) {
    return getChildNodesWithTagName(moduleNode, PROPERTY_NODE_TAG_NAME).stream()
        .filter(propertyNode ->
            getAttributeValue(propertyNode, PROPERTY_NODE_NAME_ATTRIBUTE)
                .filter(MAX_PROPERTY_NODE_NAME_ATTRIBUTE_VALUE::equals)
                .isPresent())
        .findFirst();
  }

  private Optional<String> getAttributeValue(Node node, String attributeName) {
    Node attribute = node.getAttributes().getNamedItem(attributeName);
    return attribute != null ? Optional.ofNullable(attribute.getTextContent()) : Optional.empty();
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
}
