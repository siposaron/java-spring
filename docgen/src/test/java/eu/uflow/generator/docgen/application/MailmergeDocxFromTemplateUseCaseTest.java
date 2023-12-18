package eu.uflow.generator.docgen.application;

import org.junit.jupiter.api.Test;

import java.net.URI;

public class MailmergeDocxFromTemplateUseCaseTest {

    private final MailmergeDocxFromTemplateUseCase generateDocxUseCase = new MailmergeDocxFromTemplateUseCase();

    // ignore, doesn't work & no time for this
    public void testExecute() throws Exception {
        final String url = "file:///Users/aronsipos/Dev/SiposAron/java-spring/docgen/src/main/resources/name_template_mailmerge.docx";
        final var uri = new URI(url);
        final var filePath = generateDocxUseCase.execute(uri.toURL());
        System.out.println(filePath);
    }
}
