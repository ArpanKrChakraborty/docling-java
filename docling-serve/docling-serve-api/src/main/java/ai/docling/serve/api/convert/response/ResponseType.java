package ai.docling.serve.api.convert.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ResponseType {
  @JsonProperty("in_body") InBodyConvertDocumentResponse,
  @JsonProperty("zip_archive") ZipArchiveConvertDocumentResponse,
  @JsonProperty("presigned_url") PreSignedUrlConvertDocumentResponse
}
