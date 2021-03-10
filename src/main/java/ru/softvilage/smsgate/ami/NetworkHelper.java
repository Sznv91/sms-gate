package ru.softvilage.smsgate.ami;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static ru.softvilage.smsgate.ami.AmiService.MessageHolder;

@Component
public class NetworkHelper {
    private final Logger logger = LogManager.getLogger(NetworkHelper.class);
    private static final String SERVER_LINK = "https://softvillage.ru/sms_server/number_identification/set_number.php";

    public CloseableHttpResponse sendToServer(MessageHolder message) {
        JSONObject json = new JSONObject();
        json.put("telephone_number", message.getSender().getContent());
        json.put("secret_code", message.getContent().getContent());


        try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httppost = new HttpPost(SERVER_LINK);
            ByteArrayEntity myEntity = new ByteArrayEntity(json.toString().getBytes(), ContentType.APPLICATION_JSON);
            httppost.setEntity(myEntity);
            httppost.addHeader("Content-type", "text/html");
            CloseableHttpResponse response = httpclient.execute(httppost);
            logger.info("sendToServer -> отправили на сервер JSON: " + json.toString());
            return response;
        } catch (IOException e) {
            logger.error("sendToServer -> отправка JSON не удалась. JSON: " + json.toString(), e);
            throw new RuntimeException(e);
        }
    }
}
