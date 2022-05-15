package com.yevgen.messenger.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yevgen.messenger.model.Message;
import com.yevgen.messenger.model.MessageBySender;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetMessagesBySendersResponse {

    private Integer count;
    private Long lastMessageId;
    private List<MessageBySender> messagesBySenders;

    public GetMessagesBySendersResponse(List<Message> messages) {
        if (messages != null) {
            this.count = messages.size();
            this.lastMessageId = messages.size() > 0 ? messages.get(messages.size() - 1).getId() : null;

            Map<Long, List<Message>> groupedBySender = messages.stream()
                    .collect(groupingBy(Message::getSenderId));
            this.messagesBySenders = groupedBySender.keySet().stream()
                    .map(senderId -> new MessageBySender(senderId, groupedBySender.get(senderId)))
                    .collect(Collectors.toList());
        }
    }

    public Integer getCount() {
        return count;
    }

    public Long getLastMessageId() {
        return lastMessageId;
    }

    public List<MessageBySender> getMessagesBySenders() {
        return messagesBySenders;
    }

    @Override
    public String toString() {
        return "GetMessagesBySendersResponse{" +
                "count=" + count +
                ", lastMessageId=" + lastMessageId +
                ", messagesBySenders=" + messagesBySenders +
                '}';
    }
}
