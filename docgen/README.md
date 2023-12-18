# DOCGEN

Sample app snippets for generating docx, pptx, xlsx from templates of same format.

Trial for PDF generation out of docx and pptx. Please note, for pptx the itext library is used, which has AGPL license.

## PRECONDITIONS

Java 21

## RUN

```
./mvnw clean install

./mvnw clean test

./mvnw test
```

The tests will generate the binary files from the templates (docx, pptx, xlsx) found under `src/main/resources`.
The resulting binaries will be saved under the project root folder.