package com.yevgen.messenger.repository;

import com.yevgen.messenger.model.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    Message save(Message message);

    Optional<Message> findById(Long id);

    @Query(value =
            "SELECT * FROM Messages WHERE recipient_id = ?1 AND sender_id = ?2 AND date_time >= ?3 ORDER BY id DESC LIMIT ?4",
            nativeQuery = true)
    List<Message> findBySender(Long recipientId, Long senderId, LocalDateTime dateTime, Integer limit);

    @Query(value =
            "SELECT * FROM Messages WHERE recipient_id = ?1 AND sender_id = ?2 AND date_time >= ?3 AND id < ?4 ORDER BY id DESC LIMIT ?5",
            nativeQuery = true)
    List<Message> findBySender(Long recipientId, Long senderId, LocalDateTime dateTime, Long fromId, Integer limit);

    @Query(value =
            "SELECT * FROM Messages WHERE recipient_id = ?1 AND date_time >= ?2 ORDER BY id DESC LIMIT ?3",
            nativeQuery = true)
    List<Message> findAll(Long recipientId, LocalDateTime dateTime, Integer limit);

    @Query(value =
            "SELECT * FROM Messages WHERE recipient_id = ?1 AND date_time >= ?2 AND id < ?3 ORDER BY id DESC LIMIT ?4",
            nativeQuery = true)
    List<Message> findAll(Long recipientId, LocalDateTime dateTime, Long fromId, Integer limit);

}
