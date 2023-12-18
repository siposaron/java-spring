package eu.uflow.generator.docgen.application;

import eu.uflow.generator.docgen.application.exception.ApplicationException;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.model.fields.merge.DataFieldName;
import org.docx4j.model.fields.merge.MailMerger;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MailmergeDocxFromTemplateUseCase {

    public String execute(final URL templateURL) throws ApplicationException {
        final WordprocessingMLPackage wordMLPackage = this.getPackage(templateURL);
        replacePlaceholders(wordMLPackage);
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

    private Map<DataFieldName, String> getPlaceholders() {
        var placeholdersWithValues = new HashMap<DataFieldName, String>();
        placeholdersWithValues.put(new DataFieldName("@firstName"), "Aron");
        placeholdersWithValues.put(new DataFieldName("@lastName"), "Sipos");
        placeholdersWithValues.put(new DataFieldName("firstName"), "Aron");
        placeholdersWithValues.put(new DataFieldName("lastName"), "Sipos");
        placeholdersWithValues.put(new DataFieldName("first"), "fst");
        placeholdersWithValues.put(new DataFieldName("@first"), "fst");
        placeholdersWithValues.put(new DataFieldName("$first"), "fst");
        return placeholdersWithValues;
    }

    private void replacePlaceholders(final WordprocessingMLPackage wordMLPackage) throws ApplicationException {
        try {
            MailMerger.setMERGEFIELDInOutput(MailMerger.OutputField.REMOVED);
            MailMerger.performMerge(wordMLPackage, getPlaceholders(), true);
        } catch (Exception e) {
            throw new ApplicationException("E_PROCESS_100", "Couldn't process the template file", e.getMessage());
        }
    }

    private String saveDocument(final WordprocessingMLPackage wordMLPackage) throws ApplicationException {
        try {
            final var outputFile = new File("./output_merge.docx");
            wordMLPackage.save(outputFile); // can be written to byte[]
            return outputFile.getCanonicalPath();
        } catch (Exception e) {
            throw new ApplicationException("E_WRITE_100", "Couldn't write the docx file", e.getMessage());
        }
    }


}
