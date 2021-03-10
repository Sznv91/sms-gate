package ru.softvilage.smsgate.ami;

import lombok.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
@Data
@Setter(AccessLevel.NONE)
@ToString(exclude = "logger")
public class AmiConfig {
    @Getter(AccessLevel.NONE)
    private Logger logger = LogManager.getLogger(AmiConfig.class);

    private String host;// = "192.168.14.111";
    private int port;// = 5038;
    private String userName;// = "admadm";
    private String secret;// = "pwdpwd";

    public AmiConfig() {
        Properties properties = new Properties();
        InputStream stream = AmiConnect.class.getClassLoader().getResourceAsStream("gate.properties");

        try {
            properties.load(stream);
            host = properties.getProperty("host");
            port = Integer.parseInt(properties.getProperty("port"));
            userName = properties.getProperty("userName");
            secret = properties.getProperty("secret");
            logger.info("Выполнили чтение креденшинолов " + this.toString());
            if (stream != null) stream.close();
        } catch (IOException e) {
            logger.error("Не смогли прочитать файл с креденшиналами", e);
            e.printStackTrace();
        }
    }

}
