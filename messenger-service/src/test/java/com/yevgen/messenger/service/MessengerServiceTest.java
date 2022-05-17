package com.yevgen.messenger.service;

import com.yevgen.messenger.model.Message;
import com.yevgen.messenger.repository.MessageRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @Order(1)
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
    @Order(2)
    void testGetMessages_NoMessages() {
        List<Message> messages;
        messages = messengerService.getMessages(1L, 2L, null, null, null);

        assertNotNull(messages);
        assertEquals(0, messages.size());
    }

    @Test
    @Order(3)
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

    @Test
    @Order(4)
    void testGetMessages_DaysBack() {
        messageRepository.save(new Message(LocalDateTime.now().minusDays(100), 2L, 1L, "Test message, 100 days ago"));
        messageRepository.save(new Message(LocalDateTime.now().minusDays(50), 2L, 1L, "Test message, 50 days ago"));
        messageRepository.save(new Message(LocalDateTime.now().minusDays(31), 2L, 1L, "Test message, 31 days ago"));
        messageRepository.save(new Message(LocalDateTime.now().minusDays(30), 2L, 1L, "Test message, 30 days ago"));
        messageRepository.save(new Message(LocalDateTime.now().minusDays(29), 2L, 1L, "Test message, 29 days ago"));
        messageRepository.save(new Message(LocalDateTime.now().minusDays(28), 2L, 1L, "Test message, 28 days ago"));
        messageRepository.save(new Message(LocalDateTime.now().minusDays(14), 2L, 1L, "Test message, 14 days ago"));
        messageRepository.save(new Message(LocalDateTime.now().minusDays(1), 2L, 1L, "Test message, 1 day ago"));
        messageRepository.save(new Message(LocalDateTime.now(), 2L, 1L, "Test message, now"));
        messageRepository.save(new Message(LocalDateTime.now(), 3L, 1L, "Test message, different sender"));

        List<Message> messages;

        messages = messengerService.getMessages(1L, 2L, null, null, null);

        assertNotNull(messages);
        assertEquals(5, messages.size());
        assertEquals("Test message, now", messages.get(0).getMessage());
        assertEquals("Test message, 1 day ago", messages.get(1).getMessage());
        assertEquals("Test message, 14 days ago", messages.get(2).getMessage());
        assertEquals("Test message, 28 days ago", messages.get(3).getMessage());
        assertEquals("Test message, 29 days ago", messages.get(4).getMessage());

        messages = messengerService.getMessages(1L, 2L, 15, null, null);

        assertNotNull(messages);
        assertEquals(3, messages.size());
        assertEquals("Test message, now", messages.get(0).getMessage());
        assertEquals("Test message, 1 day ago", messages.get(1).getMessage());
        assertEquals("Test message, 14 days ago", messages.get(2).getMessage());

        messages = messengerService.getMessages(1L, 2L, 365, null, null);

        assertNotNull(messages);
        assertEquals(9, messages.size());
        assertEquals("Test message, now", messages.get(0).getMessage());
        assertEquals("Test message, 1 day ago", messages.get(1).getMessage());
        assertEquals("Test message, 14 days ago", messages.get(2).getMessage());
        assertEquals("Test message, 28 days ago", messages.get(3).getMessage());
        assertEquals("Test message, 29 days ago", messages.get(4).getMessage());
        assertEquals("Test message, 30 days ago", messages.get(5).getMessage());
        assertEquals("Test message, 31 days ago", messages.get(6).getMessage());
        assertEquals("Test message, 50 days ago", messages.get(7).getMessage());
        assertEquals("Test message, 100 days ago", messages.get(8).getMessage());
    }

    @Test
    @Order(5)
    void testGetMessages_Limit() {
        messageRepository.save(new Message(LocalDateTime.now().minusDays(100), 2L, 1L, "Test message, 100 days ago"));
        messageRepository.save(new Message(LocalDateTime.now().minusDays(50), 2L, 1L, "Test message, 50 days ago"));
        messageRepository.save(new Message(LocalDateTime.now().minusDays(31), 2L, 1L, "Test message, 31 days ago"));
        messageRepository.save(new Message(LocalDateTime.now().minusDays(30), 2L, 1L, "Test message, 30 days ago"));
        messageRepository.save(new Message(LocalDateTime.now().minusDays(29), 2L, 1L, "Test message, 29 days ago"));
        messageRepository.save(new Message(LocalDateTime.now().minusDays(28), 2L, 1L, "Test message, 28 days ago"));
        messageRepository.save(new Message(LocalDateTime.now().minusDays(14), 2L, 1L, "Test message, 14 days ago"));
        messageRepository.save(new Message(LocalDateTime.now().minusDays(1), 2L, 1L, "Test message, 1 day ago"));
        messageRepository.save(new Message(LocalDateTime.now(), 2L, 1L, "Test message, now"));
        messageRepository.save(new Message(LocalDateTime.now(), 3L, 1L, "Test message, different sender"));

        List<Message> messages;

        messages = messengerService.getMessages(1L, 2L, 200, 3, null);

        assertNotNull(messages);
        assertEquals(3, messages.size());
        assertEquals("Test message, now", messages.get(0).getMessage());
        assertEquals("Test message, 1 day ago", messages.get(1).getMessage());
        assertEquals("Test message, 14 days ago", messages.get(2).getMessage());

        messages = messengerService.getMessages(1L, 2L, 200, 5, null);

        assertNotNull(messages);
        assertEquals(5, messages.size());
        assertEquals("Test message, now", messages.get(0).getMessage());
        assertEquals("Test message, 1 day ago", messages.get(1).getMessage());
        assertEquals("Test message, 14 days ago", messages.get(2).getMessage());
        assertEquals("Test message, 28 days ago", messages.get(3).getMessage());
        assertEquals("Test message, 29 days ago", messages.get(4).getMessage());
    }

}
