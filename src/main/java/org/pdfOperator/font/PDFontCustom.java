package org.pdfOperator.font;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class PDFontCustom {

    public static final String MISANS_BOLD = "MiSans-Bold.ttf";

    public static final String MISANS_DEMIBOLD = "MiSans-Demibold.ttf";

    public static final String MISANS_EXTRALIGHT = "MiSans-ExtraLight.ttf";

    public static final String MISANS_HEAVY = "MiSans-Heavy.ttf";

    public static final String MISANS_LIGHT = "MiSans-Light.ttf";

    public static final String MISANS_MEDIUM = "MiSans-Medium.ttf";

    public static final String MISANS_NORMAL = "MiSans-Normal.ttf";

    public static final String MISANS_REGULAR = "MiSans-Regular.ttf";

    public static final String MISANS_SEMIBOLD = "MiSans-Semibold.ttf";
    
    public static final String MISANS_THIN = "MiSans-Thin.ttf";

    public static PDFont getFont(PDDocument pdDocument, String font) throws IOException {
        ClassLoader loader = PDFontCustom.class.getClassLoader();
        URL resource = loader.getResource(font);
        InputStream inputStream = resource.openStream();
        return PDType0Font.load(pdDocument, inputStream);
    }


}
