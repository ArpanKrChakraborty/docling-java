package ai.docling.serve.api.convert.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines the response type for document conversion operations.
 *
 * <p>Each enumeration value corresponds to a specific response format and determines how
 * the converted document content is delivered to the client:
 *
 * <ul>
 *   <li>{@link #IN_BODY} - Content is embedded directly in the response body
 *       ({@link InBodyConvertDocumentResponse})</li>
 *   <li>{@link #ZIP_ARCHIVE} - Content is packaged and returned as a ZIP archive
 *       ({@link ZipArchiveConvertDocumentResponse})</li>
 *   <li>{@link #PRE_SIGNED_URL} - Content is packaged as a ZIP archive and uploaded to the given target URI
 *   and statistical information is returned.
 *       ({@link PreSignedUrlConvertDocumentResponse})</li>
 * </ul>
 *
 * @see ConvertDocumentResponse
 * @see InBodyConvertDocumentResponse
 * @see ZipArchiveConvertDocumentResponse
 * @see PreSignedUrlConvertDocumentResponse
 */
public enum ResponseType {
  
  /**
   * Represents response type - {@link InBodyConvertDocumentResponse}
   *
   */
  @JsonProperty("in_body")
  IN_BODY,

  /**
   * Represents response type - {@link ZipArchiveConvertDocumentResponse}
   *
   */
  @JsonProperty("zip_archive")
  ZIP_ARCHIVE,

  /**
   * Represents response type - {@link PreSignedUrlConvertDocumentResponse}
   *
   */
  @JsonProperty("presigned_url")
  PRE_SIGNED_URL
}
