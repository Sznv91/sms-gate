package ru.softvilage.smsgate.ami;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.softvilage.smsgate.ami.utils.DecomposeMessage;
import ru.softvilage.smsgate.ami.utils.UriDecoder;

@Service
public class AmiService {
    private final Logger logger = LogManager.getLogger(AmiService.class);
    private final DecomposeMessage deCompositor;

    public AmiService(DecomposeMessage deCompositor) {
        this.deCompositor = deCompositor;
    }

    /**
     * prefix t - Temporary
     * Выполняем проверку
     * @param readString Принимает строку из класса AmiConnect, когда в Сокет приходит сообщение
     *                   от GSM-шлюза
     */
    public void receiveFromAmi(String readString) {
        String tReceivedString = UriDecoder.getDecode(readString);
        if (deCompositor.isValidData(tReceivedString)){

        }


            logger.debug("receiveFromAmi-> Входящая переадача от AmiConnect -> " + tReceivedString);
    }

}
