package com.yevgen.messenger.model;

import java.util.List;

public class MessageBySender {

    private Long senderId;
    private List<Message> messages;

    public MessageBySender(Long senderId, List<Message> messages) {
        this.senderId = senderId;
        this.messages = messages;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "MessageBySender{" +
                "senderId=" + senderId +
                ", messages=" + messages +
                '}';
    }
}
