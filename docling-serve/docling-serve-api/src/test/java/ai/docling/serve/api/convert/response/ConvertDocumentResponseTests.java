package ai.docling.serve.api.convert.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.docling.core.DoclingDocument;
import ai.docling.core.DoclingDocument.TitleItem;

/**
 * Unit tests for {@link ConvertDocumentResponse}.
 */
class ConvertDocumentResponseTests {

  @Test
  void createResponseWithAllFields() {
    DocumentResponse document = DocumentResponse.builder()
        .doctagsContent("doctags content")
        .filename("test-file.pdf")
        .htmlContent("<html>content</html>")
        .jsonContent(DoclingDocument.builder()
            .name("test-file.pdf")
            .text(TitleItem.builder().text("Docling Rocks!").build())
            .build())
        .markdownContent("# Markdown content")
        .textContent("Plain text content")
        .build();

    List<ErrorItem> errors = List.of(
        ErrorItem.builder().componentType("parser").errorMessage("Parse error").moduleName("pdf_module").build(),
        ErrorItem.builder().componentType("converter").errorMessage("Conversion warning").moduleName("html_module").build()
    );

    Double processingTime = 1.5;
    String status = "success";
    Map<String, Object> timings = Map.of(
        "parse_time", 0.8,
        "convert_time", 0.7
    );

    InBodyConvertDocumentResponse response = InBodyConvertDocumentResponse.builder()
        .document(document)
        .errors(errors)
        .processingTime(processingTime)
        .status(status)
        .timings(timings)
        .build();

    assertThat(response.getDocument()).isEqualTo(document);
    assertThat(response.getErrors()).containsExactlyInAnyOrderElementsOf(errors);
    assertThat(response.getProcessingTime()).isEqualTo(processingTime);
    assertThat(response.getStatus()).isEqualTo(status);
    assertThat(response.getTimings()).containsExactlyInAnyOrderEntriesOf(timings);
  }

  @Test
  void createInBodyConvertDocumentResponseWithNullFields() {
    InBodyConvertDocumentResponse response = InBodyConvertDocumentResponse.builder().build();

    assertThat(response.getDocument()).isNull();
    assertThat(response.getErrors()).isNotNull().isEmpty();
    assertThat(response.getProcessingTime()).isNull();
    assertThat(response.getStatus()).isNull();
    assertThat(response.getTimings()).isNotNull().isEmpty();
  }

  @Test
  void createZipArchiveConvertDocumentResponseWithNullFields() {
    ZipArchiveConvertDocumentResponse response = ZipArchiveConvertDocumentResponse.builder().build();

    assertThat(response.getInputStream()).isNull();
    assertThat(response.getFileName()).isNotNull().isEqualTo("converted_docs.zip");
  }

  @Test
  void createPreSignedUrlConvertDocumentResponseWithNullFields() {
    PreSignedUrlConvertDocumentResponse response = PreSignedUrlConvertDocumentResponse.builder().build();

    assertThat(response.getNumConverted()).isNull();
    assertThat(response.getNumFailed()).isNull();
    assertThat(response.getProcessingTime()).isNull();
    assertThat(response.getNumSucceeded()).isNull();
  }

  @Test
  void createResponseWithEmptyCollections() {
    DocumentResponse document = DocumentResponse.builder()
        .filename("empty-file.txt")
        .textContent("")
        .build();

    InBodyConvertDocumentResponse response = InBodyConvertDocumentResponse.builder()
        .document(document)
        .processingTime(0.1)
        .status("completed")
        .build();

    assertThat(response.getDocument()).isEqualTo(document);
    assertThat(response.getErrors()).isEmpty();
    assertThat(response.getProcessingTime()).isEqualTo(0.1);
    assertThat(response.getStatus()).isEqualTo("completed");
    assertThat(response.getTimings()).isEmpty();
  }

  @Test
  void convertDocumentResponseIsImmutable() {
    List<ErrorItem> errors = new ArrayList<>(List.of(
        ErrorItem.builder().componentType("original").errorMessage("Original error").moduleName("original_module").build()
    ));

    Map<String, Object> timings = new HashMap<>(Map.of("original_time", 1.0));

    InBodyConvertDocumentResponse response = InBodyConvertDocumentResponse.builder()
        .errors(errors)
        .timings(timings)
        .build();

    assertThat(response.getErrors()).containsExactlyInAnyOrderElementsOf(errors);
    assertThat(response.getTimings()).containsExactlyInAnyOrderEntriesOf(timings);

    errors.add(ErrorItem.builder().componentType("modified").errorMessage("Modified error").moduleName("modified_module").build());
    timings.put("modified_time", 3.0);

    assertThat(response.getErrors()).hasSize(1);
    assertThat(response.getErrors().get(0).getErrorMessage()).isEqualTo("Original error");
    assertThat(response.getTimings()).hasSize(1);
    assertThat(response.getTimings().get("original_time")).isEqualTo(1.0);
  }

}
