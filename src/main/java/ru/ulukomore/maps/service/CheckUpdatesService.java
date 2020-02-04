package ru.ulukomore.maps.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.ulukomore.maps.client.SshClient;

import java.io.File;
import java.time.LocalDateTime;

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

    @Scheduled(fixedDelay = 60000)
    public void check() {
        if (!excelToXmlService.updateFound()) {
            return;
        }
        File newXml = excelToXmlService.convertExcelToXml();
        new SshClient(remoteHost, username, password)
                .put(newXml.getName(), "/var/www/u0801958/data/www/ulukomore.ru/maps/testik");
    }
}
