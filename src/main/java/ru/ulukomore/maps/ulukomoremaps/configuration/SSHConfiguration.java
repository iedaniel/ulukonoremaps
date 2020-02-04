package ru.ulukomore.maps.ulukomoremaps.configuration;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class SSHConfiguration {

    @Value("${app.ftp.server}")
    private String remoteHost;

    @Value("${app.ftp.user}")
    private String username;

    @Value("${app.ftp.password}")
    private String password;

    @Bean
    public SSHClient setupSshj() throws IOException {
        SSHClient client = new SSHClient();
        client.addHostKeyVerifier(new PromiscuousVerifier());
        client.connect(remoteHost);
        client.authPassword(username, password);
        return client;
    }
}
