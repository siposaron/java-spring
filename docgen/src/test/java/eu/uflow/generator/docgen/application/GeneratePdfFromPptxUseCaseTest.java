package eu.uflow.generator.docgen.application;

import eu.uflow.generator.docgen.domain.DocumentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GeneratePdfFromPptxUseCaseTest {

    private final GeneratePdfFromPptxUseCase generatePdfUseCase = new GeneratePdfFromPptxUseCase();

    @Test
    public void testExecute() throws Exception {
        final var url = this.getClass().getClassLoader().getResource("pptx_template.pptx");
        final var document = generatePdfUseCase.execute(url);
        assertEquals("output_from_pptx.pdf", document.name());
        assertEquals(DocumentType.PDF, document.documentType());
        assertNotNull(document.path());
    }
}
