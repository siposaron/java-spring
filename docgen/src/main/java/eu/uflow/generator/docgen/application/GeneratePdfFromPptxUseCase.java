package eu.uflow.generator.docgen.application;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import eu.uflow.generator.docgen.application.exception.ApplicationException;
import eu.uflow.generator.docgen.domain.Document;
import eu.uflow.generator.docgen.domain.DocumentType;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class GeneratePdfFromPptxUseCase {

    private final static String OUTPUT_FILE_NAME = "output_from_pptx.pdf";
    private final static int ZOOM = 2;

    public Document execute(final URL fileURL) throws ApplicationException {
        try {
            final InputStream inputStream = fileURL.openStream();
            final XMLSlideShow ppt = new XMLSlideShow(inputStream);
            return generatePdf(ppt);
        } catch (Exception e) {
            throw new ApplicationException("E_GENERATE_100", "Could not generate PDF from PptX.", e.getMessage());
        }
    }

    private int getNumSlides(final List<XSLFSlide> slides){
        return slides.size();
    }

    private void drawOntoGraphic(final List<XSLFSlide> slides, final int index, final Graphics2D graphics) {
        slides.get(index).draw(graphics);
    }

    private Color getSlideBackgroundColor(final List<XSLFSlide> slides, final int index) {
        return slides.get(index).getBackground().getFillColor();
    }

    private Document generatePdf(final XMLSlideShow ppt) throws Exception {
        try (final var outputStream = new FileOutputStream(OUTPUT_FILE_NAME)) {

            final var pageSize = ppt.getPageSize();
            final var slides = ppt.getSlides();
            final AffineTransform at = getAffineTransform();

            final com.itextpdf.layout.Document doc = getDocument(outputStream);

            for (int i = 0; i < getNumSlides(slides); i++) {
                final ImageData imageData = getImageDataFromSlide(pageSize, slides, at, i);

                doc.getPdfDocument().setDefaultPageSize(
                        new PageSize(
                                new com.itextpdf.kernel.geom.Rectangle(
                                        imageData.getWidth(),
                                        imageData.getHeight())));
                doc.getPdfDocument().addNewPage();
                doc.add(new com.itextpdf.layout.element.Image(imageData));
                doc.flush();
            }

            doc.close();
            return new Document(OUTPUT_FILE_NAME, DocumentType.PDF, "");
        } catch (Exception e) {
            throw new Exception("Could not write PDF file!", e.getCause());
        }
    }

    private ImageData getImageDataFromSlide(Dimension pageSize, List<XSLFSlide> slides, AffineTransform at, int i) throws IOException {
        final var bufImg = new BufferedImage(
                (int) Math.ceil(pageSize.width * ZOOM),
                (int) Math.ceil(pageSize.height * ZOOM),
                BufferedImage.TYPE_INT_RGB);
        final var graphics = bufImg.createGraphics();
        graphics.setTransform(at);

        //clear the drawing area
        graphics.setPaint(getSlideBackgroundColor(slides, i));
        graphics.fill(new Rectangle2D.Float(0, 0, pageSize.width, pageSize.height));
        drawOntoGraphic(slides, i, graphics);

        return ImageDataFactory.create(bufImg, null);
    }

    private com.itextpdf.layout.Document getDocument(FileOutputStream outputStream) {
        final var pdfDocument = new PdfDocument(new PdfWriter(outputStream));
        final var doc = new com.itextpdf.layout.Document(pdfDocument);
        doc.setMargins(0,0,0,0);
        return doc;
    }

    private AffineTransform getAffineTransform() {
        final AffineTransform at = new AffineTransform();
        at.setToScale(ZOOM, ZOOM);
        return at;
    }

}
