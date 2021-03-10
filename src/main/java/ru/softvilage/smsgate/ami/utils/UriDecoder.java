package ru.softvilage.smsgate.ami.utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class UriDecoder {

    /**
     * Во время работы метода производится замена знака '+' на '_' и обратно
     * для сохранения знака в ответе. При декодировании в UTF-8 знак заменятся на " ".
     *
     * @param uriEncode Строка пришедшая с GSM шлюза.
     * @return Строка переведеннная в формат UTF-8.
     */
    public static String getDecode(String uriEncode) {
        String tTransform = uriEncode.replace('+', '_');
        tTransform = URLDecoder.decode(tTransform, StandardCharsets.UTF_8);
        tTransform = uriEncode.replace('_', '+');
        return new String(tTransform);
    }
}
