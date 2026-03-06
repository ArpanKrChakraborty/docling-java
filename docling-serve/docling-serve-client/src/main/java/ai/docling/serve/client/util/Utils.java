package ai.docling.serve.client.util;

import java.util.Optional;

import ai.docling.serve.client.operations.HttpOperations;
import ai.docling.serve.client.operations.StreamResponse;

public class Utils {

  public static Optional<String> getFileName(StreamResponse.ResponseHeaders headers) {
    return headers
        .getFirstValue("Content-Disposition")
        .filter(ai.docling.serve.api.util.Utils::isNotNullOrBlank)
        .map(contentDisposition -> {
          int filenameIndex = contentDisposition.indexOf("filename=");
          if (filenameIndex==-1) {
            return null;
          }

          String fileName = contentDisposition.substring(filenameIndex + "filename=".length());
          return fileName.replaceAll("^\"|\"$", "").trim();
        })
        .filter(ai.docling.serve.api.util.Utils::isNotNullOrBlank);
  }

  public static Optional<String> getContentType(StreamResponse.ResponseHeaders headers) {
    return headers.getFirstValue(HttpOperations.CONTENT_TYPE_HEADER)
        .filter(ai.docling.serve.api.util.Utils::isNotNullOrBlank);
  }
}
