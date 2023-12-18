package eu.uflow.generator.docgen.application;

import eu.uflow.generator.docgen.application.exception.ApplicationException;
import eu.uflow.generator.docgen.domain.Document;
import eu.uflow.generator.docgen.domain.DocumentType;
import org.docx4j.Docx4J;
import org.docx4j.fonts.BestMatchingMapper;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class GeneratePdfFromDocxUseCase {

    private final static String OUTPUT_FILE_NAME = "output_from_docx.pdf";

    public Document execute(final URL fileURL) throws ApplicationException {
        try (final var outputStream = new FileOutputStream(OUTPUT_FILE_NAME)) {
            final WordprocessingMLPackage wordMLPackage = this.getPackage(fileURL);
            Docx4J.toPDF(wordMLPackage, outputStream);
            outputStream.flush();
            return new Document(OUTPUT_FILE_NAME, DocumentType.PDF, "");
        } catch (Exception e) {
            throw new ApplicationException("E_GENERATE_100", "Could not generate PDF from DocX.", e.getMessage());
        }
    }

    private WordprocessingMLPackage getPackage(final URL url) throws ApplicationException {
        try {
            final InputStream templateInputStream = url.openStream();
            final WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);

            String fontRegex = ".*(Courier New|Arial|Times New Roman|Comic Sans|Georgia|Impact|Lucida Console|Lucida Sans Unicode|Palatino Linotype|Tahoma|Trebuchet|Verdana|Symbol|Webdings|Wingdings|MS Sans Serif|MS Serif).*";

            if (System.getProperty("os.name").startsWith("Windows")) {
                fontRegex = ".*(calibri|cour|arial|times|comic|georgia|impact|LSANS|pala|tahoma|trebuc|verdana|symbol|webdings|wingding).*";
            }

            PhysicalFonts.setRegex(fontRegex);
            wordMLPackage.setFontMapper(new BestMatchingMapper());

            return wordMLPackage;
        } catch (Exception e) {
            throw new ApplicationException("E_READ_100", "Couldn't read DocX file", e.getMessage());
        }
    }
}
