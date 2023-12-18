package eu.uflow.generator.docgen.application;

import eu.uflow.generator.docgen.application.exception.ApplicationException;
import eu.uflow.generator.docgen.domain.Document;
import eu.uflow.generator.docgen.domain.DocumentType;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.openpackaging.packages.OpcPackage;
import org.docx4j.openpackaging.packages.SpreadsheetMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.Parts;
import org.docx4j.openpackaging.parts.SpreadsheetML.JaxbSmlPart;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GenerateXlsxFromTemplateUseCase {

    private final static String OUTPUT_FILE_NAME = "output.xlsx";

    public Document execute(final URL templateURL) throws ApplicationException {
        final SpreadsheetMLPackage spreadsheetMLPackage = this.getPackage(templateURL);
        replacePlaceholders(spreadsheetMLPackage.getParts());
        return saveDocument(spreadsheetMLPackage);
    }

    private SpreadsheetMLPackage getPackage(final URL url) throws ApplicationException {
        try {
            final InputStream templateInputStream = url.openStream();
            return SpreadsheetMLPackage.load(templateInputStream);
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

    private void replacePlaceholders(final Parts parts) throws ApplicationException {
        try {
            final var jaxbPart = (JaxbSmlPart) parts.getParts().get(new PartName("/xl/sharedStrings.xml"));
            jaxbPart.variableReplace(getPlaceholders());
        } catch (Exception e) {
            throw new ApplicationException("E_PROCESS_100", "Couldn't process the template file", e.getMessage());
        }
    }

    private Document saveDocument(final OpcPackage ocpPackage) throws ApplicationException {
        try {
            final var outputFile = new File(OUTPUT_FILE_NAME);
            ocpPackage.save(outputFile); // can be written to byte[]
            return new Document(OUTPUT_FILE_NAME, DocumentType.XLS, outputFile.getCanonicalPath());
        } catch (Exception e) {
            throw new ApplicationException("E_WRITE_100", "Couldn't write the xlsx file", e.getMessage());
        }
    }


}
