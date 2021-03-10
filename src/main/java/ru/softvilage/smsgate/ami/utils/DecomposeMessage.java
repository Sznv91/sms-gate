package ru.softvilage.smsgate.ami.utils;

import org.springframework.stereotype.Component;

@Component
public class DecomposeMessage {

    /**
     * Разделяем строку пришедшую из GSM шлюза на слова. Формат сообщений шлюза всегда
     * первым словом обозначает тип данных (Н-р: "Sender:" / "Content:" и т.д).
     * splintedData[0] - является типом данных после разделения. Остальное содержание
     * массива - соответснно содержимое сообщения.
     *
     * @param receivedString Строка перекодированная в UTF-8 из GSM - шлюза
     * @return Возврат True будет только в случае если пришёл тип данных
     * Sender or Content.
     * :Sender должен состять только из одного слова/номера тел.
     * Вслучае если отправитель будет формата "Tele2 top" - номер будем счиать невалидным.
     * :Content должен содержать константу "SV-" - в таком виде приходят к нам сообщения
     * авторизации с Android SMS Server.
     */
    public boolean isValidData(String receivedString) {
        String[] splintedData = receivedString.split("\\s"); // regex: \s == " "
        switch (splintedData[0]) {
            case "Sender:": // Приходит в виде: "Sender: +79525669245"
                return splintedData.length == 2;
            case "Content:": // Приходит в виде: "Content: SV-42432"
                return splintedData.length == 2 && splintedData[1].startsWith("SV-");
            default:
                return false;
        }
    }
}
