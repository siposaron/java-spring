package eu.uflow.generator.docgen.application;

import eu.uflow.generator.docgen.application.exception.ApplicationException;
import eu.uflow.generator.docgen.domain.Document;
import eu.uflow.generator.docgen.domain.DocumentType;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.OpcPackage;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class GenerateDocxFromTemplateUseCase {

    private final static String OUTPUT_FILE_NAME = "output.docx";

    public Document execute(final URL templateURL) throws ApplicationException {
        final WordprocessingMLPackage wordMLPackage = this.getPackage(templateURL);
        final MainDocumentPart template = wordMLPackage.getMainDocumentPart();
        replacePlaceholders(template);
        return saveDocument(wordMLPackage);
    }

    private WordprocessingMLPackage getPackage(final URL url) throws ApplicationException {
        try {
            final InputStream templateInputStream = url.openStream();
            final var wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
            VariablePrepare.prepare(wordMLPackage); // important for text replace
            return wordMLPackage;
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

    private void replacePlaceholders(final MainDocumentPart template) throws ApplicationException {
        try {
            template.variableReplace(getPlaceholders());
        } catch (Exception e) {
            throw new ApplicationException("E_PROCESS_100", "Couldn't process the template file", e.getMessage());
        }
    }

    private Document saveDocument(final OpcPackage ocpPackage) throws ApplicationException {
        try {
            final var outputFile = new File(OUTPUT_FILE_NAME);
            ocpPackage.save(outputFile); // can be written to byte[]
            return new Document(OUTPUT_FILE_NAME, DocumentType.DOC, outputFile.getCanonicalPath());
        } catch (Exception e) {
            throw new ApplicationException("E_WRITE_100", "Couldn't write the docx file", e.getMessage());
        }
    }


}
