package eu.uflow.generator.docgen.application;

import eu.uflow.generator.docgen.domain.DocumentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GenerateDocxFromTemplateUseCaseTest {

    private final GenerateDocxFromTemplateUseCase generateDocxUseCase = new GenerateDocxFromTemplateUseCase();

    @Test
    public void testExecute() throws Exception {
        final var url = this.getClass().getClassLoader().getResource("docx_template.docx");
        final var document = generateDocxUseCase.execute(url);
        assertEquals("output.docx", document.name());
        assertEquals(DocumentType.DOC, document.documentType());
        assertNotNull(document.path());
    }
}
