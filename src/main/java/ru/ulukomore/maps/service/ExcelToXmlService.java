package ru.ulukomore.maps.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.ulukomore.maps.model.xml.Village;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ExcelToXmlService {

    @Value("${app.xsls.path}")
    private String pathToXslx;

    public File convertExcelToXml() {
        try {
            FileInputStream file = new FileInputStream(new File(pathToXslx));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterable<Row> rowIterator = sheet::rowIterator;
            List<Row> rows = StreamSupport.stream(rowIterator.spliterator(), false)
                    .skip(1)
                    .collect(Collectors.toList());
            Village village = new Village(rows);
            JAXBContext context = JAXBContext.newInstance(Village.class);
            Marshaller jaxbMarshaller = context.createMarshaller();
            File output = new File("out.xml");
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(village, output);
            return output;
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
