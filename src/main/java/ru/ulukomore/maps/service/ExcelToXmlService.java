package ru.ulukomore.maps.service;

import lombok.RequiredArgsConstructor;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ExcelToXmlService {

    public void convertExcelToXml(Attachment file) {
        InputStream inputStream = file.getObject(InputStream.class);
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterable<Row> rows = sheet::rowIterator;
            StreamSupport.stream(rows.spliterator(), false)
                    .skip(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
