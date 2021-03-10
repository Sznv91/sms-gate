package ru.softvilage.smsgate.ami;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.softvilage.smsgate.ami.utils.MessageDecompositor;
import ru.softvilage.smsgate.ami.utils.MessageDecompositor.DecomposedEntity;
import ru.softvilage.smsgate.ami.utils.UriDecoder;

@Service
public class AmiService {
    private final Logger logger = LogManager.getLogger(AmiService.class);
    private final MessageDecompositor deCompositor;
    private final NetworkHelper networkHelper;
    private static final MessageHolder holder = new MessageHolder();

    public AmiService(MessageDecompositor deCompositor, NetworkHelper networkHelper) {
        this.deCompositor = deCompositor;
        this.networkHelper = networkHelper;
    }

    /**
     * prefix t - Temporary
     * Выполняем проверку
     *
     * @param readString Принимает строку из класса AmiConnect, когда в Сокет приходит сообщение
     *                   от GSM-шлюза
     */
    public void receiveFromAmi(String readString) {
        logger.debug("receiveFromAmi-> Входящая переадача от AmiConnect -> " + readString);
        String tReceivedString = UriDecoder.getDecode(readString);
        DecomposedEntity tEntity = deCompositor.isValidData(tReceivedString);
        if (tEntity != null) {
            switch (tEntity.getTypeData()) {
                case SENDER:
                    holder.setSender(tEntity);
                    break;
                case CONTENT:
                    holder.setContent(tEntity);
                    networkHelper.sendToServer(holder);
                    break;
            }
        }

    }

    @Data
    public static class MessageHolder {
        private DecomposedEntity sender;
        private DecomposedEntity content;
    }

}
