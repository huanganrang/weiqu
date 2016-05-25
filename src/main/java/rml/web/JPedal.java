package rml.web;

/**
 * Created by edward on 2015/11/4 0004.
 */
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import java.util.List;
import javax.imageio.ImageIO;

import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.SimpleRenderer;
import org.jpedal.PdfDecoderFX;
import org.jpedal.exception.PdfException;
import org.jpedal.fonts.FontMappings;

public class JPedal {

    public static void main(String[] args) throws Exception{
        PDFDocument document = new PDFDocument();
        document.load(new File("D:\\111.pdf"));
        SimpleRenderer renderer = new SimpleRenderer();
        renderer.setResolution(300);
        List<Image> images = renderer.render(document);
        for (int i = 0; i < images.size(); i++) {
            ImageIO.write((RenderedImage) images.get(i), "png", new File((i + 1) + ".png"));
        }
    }
}