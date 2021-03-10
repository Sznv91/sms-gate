package ru.softvilage.smsgate.ami.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DecomposeMessageTest {

    @Test
    void isValidSender() {
        String dataValid = UriDecoder.getDecode("Sender: +79525669245");
        String dataInvalid = UriDecoder.getDecode("Sender: Tele2 Tema");
        DecomposeMessage deComposer = new DecomposeMessage();
        assertTrue(deComposer.isValidData(dataValid));
        assertFalse(deComposer.isValidData(dataInvalid));
    }

    @Test
    void isValidContent() {
        String dataValid = UriDecoder.getDecode("Content: SV-42432");
        String dataInvalid = UriDecoder.getDecode("Content: 42432");
        DecomposeMessage deComposer = new DecomposeMessage();
        assertTrue(deComposer.isValidData(dataValid));
        assertFalse(deComposer.isValidData(dataInvalid));
    }
}