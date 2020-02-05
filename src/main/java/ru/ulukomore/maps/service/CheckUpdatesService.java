package ru.ulukomore.maps.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.ulukomore.maps.client.SshClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CheckUpdatesService {

    private final ExcelToXmlService excelToXmlService;

    @Value("${app.ftp.server}")
    private String remoteHost;
    @Value("${app.ftp.user}")
    private String username;
    @Value("${app.ftp.password}")
    private String password;
    @Value("${app.xlsx.config}")
    private String configPath;

    @Scheduled(fixedDelay = 60000)
    public void check() {
        if (!excelToXmlService.updateFound()) {
            return;
        }
        try {
            FileInputStream routesFile = new FileInputStream(new File(configPath));
            XSSFWorkbook workbook = new XSSFWorkbook(routesFile);
            Sheet sheet = workbook.getSheetAt(0);
            Iterable<Row> rowsIter = sheet::rowIterator;
            Map<String, String> pathBySheet = new HashMap<>();
            StreamSupport.stream(rowsIter.spliterator(), false)
                    .forEach(row -> pathBySheet.put(
                            row.getCell(0).getStringCellValue(),
                            row.getCell(1).getStringCellValue()
                    ));
            Map<String, String> pathByName = new HashMap<>();
            pathBySheet.forEach((name, path) -> {
                File file = excelToXmlService.convertExcelToXml(name);
                pathByName.put(file.getName(), path);
            });
            SshClient sshClient = new SshClient(remoteHost, username, password);
            sshClient.put(pathByName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
