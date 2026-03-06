package ai.docling.serve.client.operations;

import java.io.InputStream;
import java.util.Optional;

/**
 * Wrapper for HTTP responses containing binary stream data.
 * Provides an abstraction layer to decouple from specific HTTP client implementations.
 */
public class StreamResponse {
  private final InputStream body;
  private final ResponseHeaders headers;

  private StreamResponse(Builder builder) {
    this.body = builder.body;
    this.headers = builder.headers;
  }

  public InputStream getBody() { return body; }


  public ResponseHeaders getHeaders() { return headers; }

  public static Builder builder() { return new Builder(); }

  public Builder toBuilder() { return new Builder(this); }

  public static class Builder {
    private InputStream body;
    private ResponseHeaders headers;

    public Builder() {}

    public Builder(StreamResponse streamResponse) {
      this.body = streamResponse.body;
      this.headers = streamResponse.headers;
    }

    public Builder body(InputStream body) {
      this.body = body;
      return this;
    }

    public Builder headers(ResponseHeaders headers) {
      this.headers = headers;
      return this;
    }

    public StreamResponse build() {
      return new StreamResponse(this);
    }
  }

  /**
   * Abstraction for HTTP response headers.
   */
  @FunctionalInterface
  public interface ResponseHeaders {
    /**
     * Gets the first value of the specified header.
     *
     * @param headerName the name of the header
     * @return an Optional containing the first header value, or empty if not found
     */
    Optional<String> getFirstValue(String headerName);
  }
}
