package ru.ulukomore.maps.client;


import lombok.AllArgsConstructor;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
public class SshClient {

    private String remoteHost;
    private String username;
    private String password;

    public void put(Map<String, String> pathByName) {
        try {
            SSHClient sshClient = setupSshj();
            SFTPClient sftpClient = sshClient.newSFTPClient();
            for (Map.Entry<String, String> entry : pathByName.entrySet()) {
                String name = entry.getKey();
                String path = entry.getValue();
                sftpClient.put(name, path);
            }
            sftpClient.close();
            sshClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SSHClient setupSshj() throws IOException {
        SSHClient client = new SSHClient();
        client.addHostKeyVerifier(new PromiscuousVerifier());
        client.connect(remoteHost);
        client.authPassword(username, password);
        return client;
    }
}
