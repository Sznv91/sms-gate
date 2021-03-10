package ru.softvilage.smsgate.ami;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

@Component
public class AmiConnect {
    private final Logger logger = LogManager.getLogger(AmiConnect.class);
    private final AmiConfig amiConfig;
    @Getter
    private boolean isAlive;
    final AmiService service;

    public AmiConnect(AmiConfig amiConfig, AmiService service) {
        this.amiConfig = amiConfig;
        this.service = service;
    }

    @Bean
    private void astConn() {
        try {
            logger.info("astConn() -> try connect to GSM Gate");
            Socket client = new Socket();
            client.connect(new InetSocketAddress(amiConfig.getHost(), amiConfig.getPort()), 0); //2000
            String actionLogin = "Action: Login\r\nUsername: " + amiConfig.getUserName() + "\r\nSecret: " + amiConfig.getSecret() + "\r\n\r\n";
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            out.write(actionLogin);
            out.flush();
            isAlive = true;

            InputStream inputStream = client.getInputStream();
            BufferedReader readLine = new BufferedReader(new InputStreamReader(inputStream));

            while (true) {
                String readString;
                if (readLine.ready()) {
                    readString = readLine.readLine();
                    service.receiveFromAmi(readString);
                }
            }

        } catch (IOException e) {
            isAlive = false;
            logger.error("astConn() -> Не смогли выполнить подключение к GSM шлюзу" + e);
            e.printStackTrace();
        }
    }
}
