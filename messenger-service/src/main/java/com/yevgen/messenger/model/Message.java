package com.yevgen.messenger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="messages_id_seq")
    @SequenceGenerator(name = "messages_id_seq", sequenceName = "messages_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "sender_id")
    @JsonIgnore
    private Long senderId;

    @Column(name = "recipient_id")
    @JsonIgnore
    private Long recipientId;

    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

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

    public Message() {
    }

    public Message(LocalDateTime dateTime, Long senderId, Long recipientId, String message) {
        this.dateTime = dateTime;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return getId().equals(message.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", senderId=" + senderId +
                ", recipientId=" + recipientId +
                ", message='" + message + '\'' +
                '}';
    }
}
