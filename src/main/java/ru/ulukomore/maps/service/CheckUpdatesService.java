package ru.ulukomore.maps.service;

import lombok.RequiredArgsConstructor;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CheckUpdatesService {

    private final ExcelToXmlService excelToXmlService;
    private final SSHClient client;

    @Scheduled(fixedDelay = 60000)
    public void check() {
        if (!excelToXmlService.wasUpdates()) {
            return;
        }
        try {
            File newXml = excelToXmlService.convertExcelToXml();
            SFTPClient sftpClient = client.newSFTPClient();
            sftpClient.put(newXml.getName(), "/var/www/u0801958/data/www/ulukomore.ru/maps/testik");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
