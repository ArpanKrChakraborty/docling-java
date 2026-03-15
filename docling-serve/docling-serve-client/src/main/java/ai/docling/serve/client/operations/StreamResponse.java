package ai.docling.serve.client.operations;

import java.io.InputStream;
import java.util.Optional;

import ai.docling.serve.api.util.Utils;

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

  public static Builder builder() {
    return new Builder();
  }

  public InputStream getBody() {
    return body;
  }

  public ResponseHeaders getHeaders() {
    return headers;
  }

  public Builder toBuilder() {
    return new Builder(this);
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

    /**
     * Gets the file name from the Content-Disposition header
     *
     * @return an Optional containing the file name if its present in the header, empty otherwise
     */
    default Optional<String> getFileName() {
      return getFirstValue("Content-Disposition")
        .filter(Utils::isNotNullOrBlank)
        .map(contentDisposition -> {
          int filenameIndex = contentDisposition.indexOf("filename=");
          if (filenameIndex == -1) {
            return null;
          }

          String fileName = contentDisposition.substring(filenameIndex + "filename=".length());
          return fileName.replaceAll("^\"|\"$", "")
                         .trim();
        })
        .filter(Utils::isNotNullOrBlank);
    }

    /**
     * Gets the Content-Type header value in lower-case.
     *
     * @return an Optional containing the Content-Type header value if its present in the header, empty otherwise
     */
    default Optional<String> getContentType() {
      return getFirstValue(HttpOperations.CONTENT_TYPE_HEADER)
        .filter(Utils::isNotNullOrBlank)
        .map(contentType -> {
          int firstSemicolon = contentType.indexOf(';');
          return firstSemicolon != -1 ?
                 contentType.substring(0, firstSemicolon):
                 contentType;
        })
        .map(String::trim)
        .map(String::toLowerCase);
    }
  }

  public static class Builder {
    private InputStream body;
    private ResponseHeaders headers;

    private Builder() {
    }

    private Builder(StreamResponse streamResponse) {
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
}
