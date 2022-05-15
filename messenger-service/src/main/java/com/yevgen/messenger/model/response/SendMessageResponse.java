package com.yevgen.messenger.model.response;

public class SendMessageResponse {

    private Long messageId;

    public SendMessageResponse(Long messageId) {
        this.messageId = messageId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "SendMessageResponse{" +
                "messageId=" + messageId +
                '}';
    }
}
