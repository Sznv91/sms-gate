package ru.softvilage.smsgate.ami.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MessageDecompositorTest {

    @Test
    void isValidSender() {
        String dataValid = UriDecoder.getDecode("Sender: +79525669245");
        String dataInvalid = UriDecoder.getDecode("Sender: Tele2 Tema");
        MessageDecompositor deComposer = new MessageDecompositor();
        MessageDecompositor.DecomposedEntity answerOk =
                new MessageDecompositor.DecomposedEntity(
                        TypeData.SENDER, "+79525669245");

        Assertions.assertEquals(deComposer.isValidData(dataValid), answerOk);
        Assertions.assertNull(deComposer.isValidData(dataInvalid));
    }

    @Test
    void isValidContent() {
        String dataValid = UriDecoder.getDecode("Content: SV-42432");
        String dataInvalid = UriDecoder.getDecode("Content: 42432");
        String dataInvalidLength = UriDecoder.getDecode("Content: SV-123456");
        MessageDecompositor deComposer = new MessageDecompositor();
        MessageDecompositor.DecomposedEntity answerOk =
                new MessageDecompositor.DecomposedEntity(
                        TypeData.CONTENT, "42432");

        Assertions.assertEquals(deComposer.isValidData(dataValid), answerOk);
        Assertions.assertNull(deComposer.isValidData(dataInvalid));
        Assertions.assertNull(deComposer.isValidData(dataInvalidLength));
    }
}