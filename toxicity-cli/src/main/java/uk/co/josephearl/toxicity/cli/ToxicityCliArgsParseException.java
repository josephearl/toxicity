package uk.co.josephearl.toxicity.cli;

public class ToxicityCliArgsParseException extends Exception {
  public ToxicityCliArgsParseException() {
  }

  public ToxicityCliArgsParseException(String s) {
    super(s);
  }

  public ToxicityCliArgsParseException(String message, Throwable cause) {
    super(message, cause);
  }

  public ToxicityCliArgsParseException(Throwable cause) {
    super(cause);
  }
}
