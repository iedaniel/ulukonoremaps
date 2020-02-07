package ru.ulukomore.maps.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static ru.ulukomore.maps.Constants.PATH_TO_XLSX;

@Service
@RequiredArgsConstructor
public class ExcelToXmlService {

    @Autowired
    private LocalDateTime lastUpdate;


    public File convertExcelToXml(String resultName) {
        try {
            FileInputStream file = new FileInputStream(new File(PATH_TO_XLSX));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet(resultName);
            Iterable<Row> rowIterator = sheet::rowIterator;
            List<Row> rows = StreamSupport.stream(rowIterator.spliterator(), false)
                    .skip(1)
                    .collect(Collectors.toList());
            Village village = new Village(rows);
            JAXBContext context = JAXBContext.newInstance(Village.class);
            Marshaller jaxbMarshaller = context.createMarshaller();
            File output = new File(String.format("%s.xml", resultName));
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(village, output);
            file.close();
            return output;
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateFound() {
        try {
            LocalDateTime lastUpdateFile = LocalDateTime.ofInstant(
                    Files.getLastModifiedTime(Paths.get(PATH_TO_XLSX)).toInstant(),
                    ZoneId.systemDefault()
            );
            if (lastUpdateFile.isAfter(lastUpdate)) {
                lastUpdate = lastUpdateFile;
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
