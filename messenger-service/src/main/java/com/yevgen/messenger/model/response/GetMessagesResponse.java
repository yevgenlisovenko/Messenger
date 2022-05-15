package com.yevgen.messenger.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yevgen.messenger.model.Message;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetMessagesResponse {

    private Integer count;
    private Long lastMessageId;
    private List<Message> messages;

    public GetMessagesResponse(List<Message> messages) {
        if (messages != null) {
            this.count = messages.size();
            this.lastMessageId = messages.size() > 0 ? messages.get(messages.size() - 1).getId() : null;
            this.messages = messages;
        }
    }

    public Integer getCount() {
        return count;
    }

    public Long getLastMessageId() {
        return lastMessageId;
    }

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "GetMessagesResponse{" +
                "count=" + count +
                ", lastMessageId=" + lastMessageId +
                ", messages=" + messages +
                '}';
    }
}
