package org.pdfOperator.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.pdfOperator.bean.WordCoordinate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

// 文件坐标获取器
public class CoordinateFetcher {

    public List<WordCoordinate> fetchCoordinate(PDDocument document, String keyWord) throws IOException {

        List<WordCoordinate> coordinateArray = new ArrayList<WordCoordinate>();

        PDPageTree pages = document.getPages();

        int pageCount = pages.getCount();

        Writer writer = new OutputStreamWriter(new ByteArrayOutputStream());

        for (int i = 0; i < pageCount; i++) {

            int startPage = i + 1;
            int endPage = i + 1;

            PDFGetWordLocationAndSize stripper = new PDFGetWordLocationAndSize();
            stripper.setSortByPosition(true);
            stripper.setKeyWord(keyWord);
            stripper.setPage(i);

            stripper.setStartPage(startPage);
            stripper.setEndPage(endPage);

            stripper.writeText(document, writer);

            if (stripper.canFind()) {
                coordinateArray.addAll(stripper.getCoordinates());
            }

        }

        return coordinateArray;

    }


}
