package org.pdfOperator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.pdfOperator.bean.WordCoordinate;
import org.pdfOperator.font.PDFontCustom;
import org.pdfOperator.util.CoordinateFetcher;
import org.pdfOperator.writer.PDFWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        try {

            PDDocument document = PDDocument.load(new File("D:\\入网协议.pdf"));

            PDFWriter writer = new PDFWriter();

            PDFont font = PDFontCustom.getFont(document, PDFontCustom.MISANS_BOLD);

            CoordinateFetcher fetcher = new CoordinateFetcher();
            List<WordCoordinate> wordCoordinates = fetcher.fetchCoordinate(document, "${transferName}");

            for (WordCoordinate coordinate : wordCoordinates) {
                writer.insertText(document, "张一凡", font, 15, coordinate, -coordinate.getWidth() * "${transferName}".length(), 0);
            }

            CoordinateFetcher acceptNameFetcher = new CoordinateFetcher();
            List<WordCoordinate> acceptNameWordCoordinates = acceptNameFetcher.fetchCoordinate(document, "${acceptName}");

            for (WordCoordinate coordinate : acceptNameWordCoordinates) {
                writer.insertText(document, "张二凡", font, 15, coordinate, -coordinate.getWidth() * "${acceptName}".length(), 0);
            }

            CoordinateFetcher transferNumFetcher = new CoordinateFetcher();
            List<WordCoordinate> transferNumWordCoordinates = transferNumFetcher.fetchCoordinate(document, "${transferNum}");

            for (WordCoordinate coordinate : transferNumWordCoordinates) {
                writer.insertText(document, "13500000000", font, 15, coordinate, -coordinate.getWidth() * "${transferNum}".length(), 0);
            }

            CoordinateFetcher dateFetcher = new CoordinateFetcher();
            List<WordCoordinate> dateWordCoordinates = dateFetcher.fetchCoordinate(document, "${date}");

            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
            String dateStr = format.format(date);

            for (WordCoordinate coordinate : dateWordCoordinates) {
                writer.insertText(document, dateStr, font, 15, coordinate, -coordinate.getWidth() * "${date}".length(), 0);
            }


            List<WordCoordinate> imgCoordinates = fetcher.fetchCoordinate(document, "${pic}");

            File file = new File("D:\\touxiang.jpg");
            FileInputStream inputStream = new FileInputStream(file);

            for (WordCoordinate coordinate : imgCoordinates) {
                System.out.println(coordinate);
                writer.insertImage(document, inputStream, 100, 150, coordinate, -coordinate.getWidth() * "${pic}".length(), 0);
            }


            document.save("D:\\入网协议1.pdf");

            document.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
