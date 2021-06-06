package com.acpreda.lab.echo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Echo service unit tests")
class EchoTest {

    @ParameterizedTest
    @DisplayName("Should say whatever it's sent")
    @ValueSource(strings = {"Hello", "Something special", "Nothing at all"})
    void should_say_whatever_its_sent(String text) {
        // Given
        EchoService echoService = new EchoService();

        // When
        String answer = echoService.say(text);

        // Then
        assertEquals(text, answer);
    }

}
