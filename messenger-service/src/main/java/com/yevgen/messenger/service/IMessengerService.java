package com.yevgen.messenger.service;

import com.yevgen.messenger.model.Message;

import java.util.List;

public interface IMessengerService {

    Long sendMessage(Long senderId, Long recipientId, String textMessage);

    List<Message> getMessages(Long recipientId, Long senderId, Integer days, Integer limit, Long from);

}
