package eu.uflow.generator.docgen.application;

import eu.uflow.generator.docgen.application.exception.ApplicationException;
import eu.uflow.generator.docgen.domain.Document;
import eu.uflow.generator.docgen.domain.DocumentType;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.openpackaging.packages.OpcPackage;
import org.docx4j.openpackaging.packages.PresentationMLPackage;
import org.docx4j.openpackaging.parts.PresentationML.MainPresentationPart;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GeneratePptxFromTemplateUseCase {

    private final static String OUTPUT_FILE_NAME = "output.pptx";

    public Document execute(final URL templateURL) throws ApplicationException {
        final PresentationMLPackage presentationMLPackagePackage = getPackage(templateURL);
        final MainPresentationPart template = presentationMLPackagePackage.getMainPresentationPart();
        replacePlaceholders(template);
        return saveDocument(presentationMLPackagePackage);
    }

    private PresentationMLPackage getPackage(final URL url) throws ApplicationException {
        try {
            final InputStream templateInputStream = url.openStream();
            return PresentationMLPackage.load(templateInputStream);
        } catch (Exception e) {
            throw new ApplicationException("E_READ_100", "Couldn't read template file", e.getMessage());
        }
    }

    private Map<String, String> getPlaceholders() {
        var placeholdersWithValues = new HashMap<String, String>();
        placeholdersWithValues.put("firstName", "Aron");
        placeholdersWithValues.put("lastName", "Sipos");
        placeholdersWithValues.put("LAST", "Last message");
        return placeholdersWithValues;
    }

    private void replacePlaceholders(final MainPresentationPart template) throws ApplicationException {
        try {
            final var placeholders = getPlaceholders();

            template.getSlideParts().forEach(sp -> {
                try {
                    sp.variableReplace(placeholders);
                } catch (Exception e) {
                    log.error("Couldn't process the slide {}" + sp.getPartName().getName());
                }
            });

        } catch (Exception e) {
            throw new ApplicationException("E_PROCESS_100", "Couldn't process the template file", e.getMessage());
        }
    }

    private Document saveDocument(final OpcPackage ocpPackage) throws ApplicationException {
        try {
            final var outputFile = new File(OUTPUT_FILE_NAME);
            ocpPackage.save(outputFile); // can be written to byte[]
            return new Document(OUTPUT_FILE_NAME, DocumentType.PPT, outputFile.getCanonicalPath());
        } catch (Exception e) {
            throw new ApplicationException("E_WRITE_100", "Couldn't write the pptx file", e.getMessage());
        }
    }

}
