package com.yevgen.messenger.model.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SendMessageRequest {

    @NotNull
    private Long senderId;

    @NotNull
    private Long recipientId;

    @NotBlank
    private String message;

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SendMessageRequest{" +
                "senderId=" + senderId +
                ", recipientId=" + recipientId +
                ", message='" + message + '\'' +
                '}';
    }
}
