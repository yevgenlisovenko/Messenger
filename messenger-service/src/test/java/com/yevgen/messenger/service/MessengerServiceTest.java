package com.yevgen.messenger.service;

import com.yevgen.messenger.model.Message;
import com.yevgen.messenger.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MessengerServiceTest {

    @Autowired
    private IMessengerService messengerService;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    void init() {
        messageRepository.deleteAll();
    }

    @Test
    void testSendMessage() {
        Long messageId;

        messageId = messengerService.sendMessage(1L, 2L, "Test message 1");
        assertEquals(1L, messageId);

        messageId = messengerService.sendMessage(1L, 2L, "Test message 2");
        assertEquals(2L, messageId);

        messageId = messengerService.sendMessage(3L, 2L, "Test message 3");
        assertEquals(3L, messageId);
    }

    @Test
    void testGetMessages_NoMessages() {
        List<Message> messages;
        messages = messengerService.getMessages(1L, 2L, null, null, null);

        assertNotNull(messages);
        assertEquals(0, messages.size());
    }

    @Test
    void testGetMessages() {
        messengerService.sendMessage(1L, 2L, "Another test message");
        messengerService.sendMessage(1L, 2L, "One more test message");
        messengerService.sendMessage(3L, 2L, "Message from another sender");

        List<Message> messages;

        messages = messengerService.getMessages(2L, 1L, null, null, null);

        assertNotNull(messages);
        assertEquals(2, messages.size());
        assertEquals("One more test message", messages.get(0).getMessage());
        assertEquals("Another test message", messages.get(1).getMessage());

        messages = messengerService.getMessages(2L, 3L, null, null, null);

        assertNotNull(messages);
        assertEquals(1, messages.size());
        assertEquals("Message from another sender", messages.get(0).getMessage());

        messages = messengerService.getMessages(2L, null, null, null, null);
        assertNotNull(messages);
        assertEquals(3, messages.size());
        assertEquals("Message from another sender", messages.get(0).getMessage());
        assertEquals("One more test message", messages.get(1).getMessage());
        assertEquals("Another test message", messages.get(2).getMessage());
    }

}
