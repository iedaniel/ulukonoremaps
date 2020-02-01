package ru.ulukomore.maps.service;

import lombok.RequiredArgsConstructor;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ru.ulukomore.maps.model.xml.Village;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ExcelToXmlService {

    public void convertExcelToXml(Attachment file) {
        InputStream inputStream = file.getObject(InputStream.class);
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterable<Row> rowIterator = sheet::rowIterator;
            List<Row> rows = StreamSupport.stream(rowIterator.spliterator(), false)
                    .skip(1)
                    .collect(Collectors.toList());
            Village village = new Village(rows);
            try {
                JAXBContext context = JAXBContext.newInstance(Village.class);
                Marshaller jaxbMarshaller = context.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                jaxbMarshaller.marshal(village, System.out);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
