package com.yevgen.messenger.service.impl;

import com.yevgen.messenger.model.Message;
import com.yevgen.messenger.repository.MessageRepository;
import com.yevgen.messenger.service.IMessengerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessengerService implements IMessengerService {

    private static Logger logger = LoggerFactory.getLogger(MessengerService.class);

    @Autowired
    private MessageRepository messageRepository;

    @Value("${messenger.service.message.request.days:30}")
    private Integer defaultDays;

    @Value("${messenger.service.message.request.limit:100}")
    private Integer defaultLimit;

    @Override
    public Long sendMessage(Long senderId, Long recipientId, String textMessage) {
        Message message = messageRepository.save(new Message(LocalDateTime.now(), senderId, recipientId, textMessage));
        return message.getId();
    }

    @Override
    public List<Message> getMessages(Long recipientId, Long senderId, Integer requestedDays, Integer requestedLimit, Long from) {

        Integer days = requestedDays == null ? defaultDays : requestedDays;
        Integer limit = requestedLimit == null ? defaultLimit : requestedLimit;

        LocalDateTime fromDate = LocalDateTime.now().minusDays(days);
        if (from != null) {
            Optional<Message> fromMessage = messageRepository.findById(from);
            if (fromMessage.isPresent()) {
                fromDate = fromMessage.get().getDateTime().minusDays(days);
            }
        }

        logger.info("MessengerService.getMessages: days {}, limit {}, fromDate {}", days, limit, fromDate);

        return senderId == null
                ? findAll(recipientId, fromDate, limit, from)
                : findBySender(recipientId, senderId, fromDate, limit, from);
    }

    private List<Message> findBySender(Long recipientId, Long senderId, LocalDateTime fromDate, Integer limit, Long from) {
        return from == null
                ? messageRepository.findBySender(recipientId, senderId, fromDate, limit)
                : messageRepository.findBySender(recipientId, senderId, fromDate, from, limit);
    }

    private List<Message> findAll(Long recipientId, LocalDateTime fromDate, Integer limit, Long from) {
        return from == null
                ? messageRepository.findAll(recipientId, fromDate, limit)
                : messageRepository.findAll(recipientId, fromDate, from, limit);
    }
}
