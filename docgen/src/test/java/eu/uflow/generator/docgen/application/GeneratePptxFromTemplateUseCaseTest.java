package eu.uflow.generator.docgen.application;

import eu.uflow.generator.docgen.domain.DocumentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GeneratePptxFromTemplateUseCaseTest {

    private final GeneratePptxFromTemplateUseCase generatePptxUseCase = new GeneratePptxFromTemplateUseCase();

    @Test
    public void testExecute() throws Exception {
        final var url = this.getClass().getClassLoader().getResource("pptx_template.pptx");
        final var document = generatePptxUseCase.execute(url);
        assertEquals("output.pptx", document.name());
        assertEquals(DocumentType.PPT, document.documentType());
        assertNotNull(document.path());
    }
}
