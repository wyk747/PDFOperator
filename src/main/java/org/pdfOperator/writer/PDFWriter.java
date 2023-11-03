package org.pdfOperator.writer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.pdfOperator.bean.WordCoordinate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PDFWriter {

    /**
     * PDF文档插入文字
     *
     * @param document
     * @param page       插入页
     * @param text       插入文本
     * @param font       文本字体
     * @param fontSize   字体大小
     * @param coordinate 插入坐标
     * @param offsetX    坐标X轴偏移量
     * @param offsetY    坐标Y轴偏移量
     */
    public void insertText(PDDocument document, PDPage page, String text, PDFont font, int fontSize, WordCoordinate coordinate, float offsetX, float offsetY) throws IOException {

        PDRectangle mediaBox = page.getMediaBox();

        PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, false, false);
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.newLineAtOffset(coordinate.getX() + offsetX, mediaBox.getUpperRightY() - coordinate.getY() - offsetY);
        contentStream.showText(text);
        contentStream.endText();

        contentStream.close();

    }

    public void insertText(PDDocument document, PDPage page, String text, PDFont font, int fontSize, WordCoordinate coordinate) throws IOException {

        this.insertText(document, page, text, font, fontSize, coordinate, 0, 0);

    }

    public void insertText(PDDocument document, String text, PDFont font, int fontSize, WordCoordinate coordinate, float offsetX, float offsetY) throws IOException {

        PDPage pdPage = document.getPage(coordinate.getPage());

        this.insertText(document, pdPage, text, font, fontSize, coordinate, offsetX, offsetY);

    }

    /**
     * PDF文档插入图片
     *
     * @param document
     * @param page
     * @param inputStream 图片流
     * @param width       图片宽度
     * @param height      图片高度
     * @param coordinate  插入坐标
     * @param offsetX     坐标X轴偏移量
     * @param offsetY     坐标Y轴偏移量
     * @throws IOException
     */
    public void insertImage(PDDocument document, PDPage page, InputStream inputStream, int width, int height, WordCoordinate coordinate, float offsetX, float offsetY) throws IOException {

        PDRectangle mediaBox = page.getMediaBox();

        PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, false, false);

        byte[] imageByte = inputStreamToByteArray(inputStream);

        PDImageXObject imageXObject = PDImageXObject.createFromByteArray(document, imageByte, "");

        contentStream.drawImage(imageXObject, coordinate.getX() + offsetX, mediaBox.getUpperRightY() - coordinate.getY() - height - offsetY, width, height);

        contentStream.close();

    }

    public void insertImage(PDDocument document, PDPage page, InputStream inputStream, int width, int height, WordCoordinate coordinate) throws IOException {
        this.insertImage(document, page, inputStream, width, height, coordinate, 0, 0);
    }

    public void insertImage(PDDocument document, InputStream inputStream, int width, int height, WordCoordinate coordinate, float offsetX, float offsetY) throws IOException {

        PDPage page = document.getPage(coordinate.getPage());

        this.insertImage(document, page, inputStream, width, height, coordinate, offsetX, offsetY);

    }

    private byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {

        byte[] buffer = new byte[1024];
        int len = 0;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, len);
        }
        byte[] imageByte = outputStream.toByteArray();

        outputStream.close();
        inputStream.close();

        return imageByte;
    }

}
