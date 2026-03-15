# Getting Started

Use the [`DoclingServeApi`](docling-serve/serve-api.md) to convert a document by pointing at a running [Docling Serve](https://github.com/docling-project/docling-serve) instance.

```java
import ai.docling.serve.api.DoclingServeApi;
import ai.docling.serve.api.convert.request.ConvertDocumentRequest;
import ai.docling.serve.api.convert.request.source.HttpSource;
import ai.docling.serve.api.convert.response.InBodyConvertDocumentResponse;

DoclingServeApi doclingServeApi = DoclingServeApi.builder()
    .baseUrl("<location of docling serve instance>")
    .build();

ConvertDocumentRequest request = ConvertDocumentRequest.builder()
    .source(
        HttpSource.builder()
            .url(URI.create("https://arxiv.org/pdf/2408.09869"))
            .build()
    )
    .build();

InBodyConvertDocumentResponse response = (InBodyConvertDocumentResponse) doclingServeApi.convertSource(request);
System.out.println(response.getDocument().getMarkdownContent());
```

For more examples and options, explore the modules listed above and the repository README.

## Links

- Source repository: https://github.com/docling-project/docling-java
- Docling (core project): https://github.com/docling-project
- Supported formats: https://docling-project.github.io/docling/usage/supported_formats/
- DoclingDocument concept: https://docling-project.github.io/docling/concepts/docling_document/
- Community Discord: https://docling.ai/discord
