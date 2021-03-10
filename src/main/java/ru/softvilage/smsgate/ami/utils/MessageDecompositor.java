package ru.softvilage.smsgate.ami.utils;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class MessageDecompositor {

    /**
     * Разделяем строку пришедшую из GSM шлюза на слова. Формат сообщений шлюза всегда
     * первым словом обозначает тип данных (Н-р: "Sender:" / "Content:" и т.д).
     * splintedData[0] - является типом данных после разделения. Остальное содержание
     * массива - соответснно содержимое сообщения.
     *
     * @param receivedString Строка перекодированная в UTF-8 из GSM - шлюза
     * @return Выполняется возврат сущности содержащей в себе инф. о типе данных, и
     * соответтсвенно самих данных.
     * Возврат !=null будет только в случае если пришёл тип данных
     * Sender or Content.
     * :Sender должен состять только из одного слова/номера тел.
     * Вслучае если отправитель будет формата "Tele2 top" - номер будем счиать невалидным.
     * :Content должен содержать константу "SV-" - в таком виде приходят к нам сообщения
     * авторизации с Android SMS Server.
     */
    public DecomposedEntity isValidData(String receivedString) {
        String[] splintedData = receivedString.split("\\s"); // regex: \s == " "
        switch (splintedData[0]) {
            case "Sender:": // Приходит в виде: "Sender: +79525669245"
                return splintedData.length == 2 ?
                        new DecomposedEntity(TypeData.SENDER, splintedData[1]) : null;
            case "Content:": // Приходит в виде: "Content: SV-42432"
                return splintedData.length == 2 && splintedData[1].startsWith("SV-")
                        && splintedData[1].length() == 8 ?
                        new DecomposedEntity(TypeData.CONTENT, splintedData[1].substring(3)) : null;
            default:
                return null;
        }
    }

    @RequiredArgsConstructor
    @Data
    public static class DecomposedEntity {
        final TypeData typeData;
        final String content;
    }
}
