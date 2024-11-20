package org.pdfOperator.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.apache.pdfbox.util.Matrix;
import org.apache.pdfbox.util.Vector;
import org.pdfOperator.bean.WordCoordinate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PDFGetWordLocationAndSize extends PDFTextStripper {

    // 关键词转换为字符数组用于匹配
    private char[] keyWordArray;

    // 记录匹配位置
    private int wordPosition;

    private int arrayLength;

    private int page;

    private final List<WordCoordinate> coordinates = new ArrayList<WordCoordinate>();

    // 能否匹配到关键词
    private boolean canFind = false;


    public PDFGetWordLocationAndSize() throws IOException {
    }

    public void setKeyWord(String keyword) {
        this.keyWordArray = keyword.toCharArray();
        arrayLength = keyWordArray.length;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public PDFGetWordLocationAndSize(String keyWord) throws IOException {
        keyWordArray = keyWord.toCharArray();
        arrayLength = keyWordArray.length;
    }

    public boolean canFind() {
        return canFind;
    }

    public List<WordCoordinate> getCoordinates() {
        return coordinates;
    }

    @Override
    protected void writeString(String text, List<TextPosition> textPositions) throws IOException {

        for (TextPosition position : textPositions) {

            String unicode = position.getUnicode();
            float x = position.getXDirAdj();
            float y = position.getYDirAdj();
            float height = position.getYScale();
            float width = position.getWidthOfSpace();

            if (unicode.equals(String.valueOf(keyWordArray[wordPosition]))) {
                wordPosition++;
                if (wordPosition == arrayLength) {
                    this.canFind = true;
                    coordinates.add(new WordCoordinate(x, y, height, width, page));
                    System.out.println(unicode + " x: " + x + " y:" + y + " height: " + height + " width: " + width + " getYScale:" + position.getYScale());
                    wordPosition = 0;
                }
            } else {
                wordPosition = 0;
            }
        }

    }

    public static void main(String[] args) {
        try {

            PDDocument document = PDDocument.load(new File("D:\\入网协议.pdf"));
            PDFGetWordLocationAndSize stripper = new PDFGetWordLocationAndSize("${transferName}");
            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(2);

            Writer writer = new OutputStreamWriter(new ByteArrayOutputStream());
            stripper.writeText(document, writer);

            boolean canFind1 = stripper.canFind();
            System.out.println(canFind1);

            List<WordCoordinate> coordinates1 = stripper.getCoordinates();
            for (WordCoordinate wordCoordinate : coordinates1) {
                System.out.println(wordCoordinate);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
