package ru.softvilage.smsgate.ami;

import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.softvilage.smsgate.ami.utils.TypeData;

import static ru.softvilage.smsgate.ami.AmiService.MessageHolder;
import static ru.softvilage.smsgate.ami.utils.MessageDecompositor.DecomposedEntity;

class NetworkHelperTest {

    @Test
    void sendToServer() {
        DecomposedEntity sender =
                new DecomposedEntity(
                        TypeData.SENDER, "+79999999999");
        DecomposedEntity content =
                new DecomposedEntity(
                        TypeData.CONTENT, "12345");
        MessageHolder message = new MessageHolder();
        message.setSender(sender);
        message.setContent(content);

        NetworkHelper helper = new NetworkHelper();
        CloseableHttpResponse response = helper.sendToServer(message);
        Assertions.assertEquals(200, response.getCode());
    }
}