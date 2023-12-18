package eu.uflow.generator.docgen.application;

import eu.uflow.generator.docgen.domain.DocumentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GenerateXlsxFromTemplateUseCaseTest {

    private final GenerateXlsxFromTemplateUseCase generateXlsxUseCase = new GenerateXlsxFromTemplateUseCase();

    @Test
    public void testExecute() throws Exception {
        final var url = this.getClass().getClassLoader().getResource("xlsx_template.xlsx");
        final var document = generateXlsxUseCase.execute(url);
        assertEquals("output.xlsx", document.name());
        assertEquals(DocumentType.XLS, document.documentType());
        assertNotNull(document.path());
    }
}
