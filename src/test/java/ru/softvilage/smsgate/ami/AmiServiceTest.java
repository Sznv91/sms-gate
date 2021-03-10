package ru.softvilage.smsgate.ami;

import org.junit.jupiter.api.Test;
import ru.softvilage.smsgate.ami.utils.MessageDecompositor;

class AmiServiceTest {

    @Test
    void receiveFromAmi() {
        AmiService service = new AmiService(new MessageDecompositor(), new NetworkHelper());
        service.receiveFromAmi("Sender: +79525669245");
        service.receiveFromAmi("Content: SV-42432");
    }
}