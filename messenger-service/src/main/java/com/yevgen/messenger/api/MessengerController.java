package com.yevgen.messenger.api;

import com.yevgen.messenger.model.Message;
import com.yevgen.messenger.model.request.SendMessageRequest;
import com.yevgen.messenger.model.response.GetMessagesBySendersResponse;
import com.yevgen.messenger.model.response.GetMessagesResponse;
import com.yevgen.messenger.model.response.SendMessageResponse;
import com.yevgen.messenger.service.IMessengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Messenger Controller")
@RestController
@RequestMapping("/v1/message")
public class MessengerController {

    private static Logger logger = LoggerFactory.getLogger(MessengerController.class);

    @Autowired
    private IMessengerService messengerService;

    @Operation(summary = "Send message from one user to another")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message successfully sent",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SendMessageResponse.class)) })
    })
    @PostMapping
    public ResponseEntity<SendMessageResponse> sendMessage(
            @Valid @RequestBody SendMessageRequest request) {

        logger.info("{}", request);

        Long messageId = messengerService.sendMessage(
                request.getSenderId(), request.getRecipientId(), request.getMessage());
        SendMessageResponse response = new SendMessageResponse(messageId);

        logger.info("{}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Returns messages from given sender",
    parameters = {
        @Parameter(name = "recipient-id", description = "Recipient id"),
        @Parameter(name = "sender-id", description = "Sender id"),
        @Parameter(name = "days", required = false,
                description = "Optional parameter. Specifies for how many days messages needs to be returned. " +
                        "If not specified, this parameter controlled by service's property \'messenger.service.message.request.days\' " +
                        "and by default equals to 30"),
        @Parameter(name = "limit", required = false,
                description = "Optional parameter. Limits count of messages returned by response. " +
                        "If not specified, this parameter controlled by service's property and by default equals to 100"),
        @Parameter(name = "from", required = false,
                description = "Optional parameter. Id of the message from which start search. Used for pagination of messages")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request executed successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetMessagesResponse.class)) })
    })
    @GetMapping( "/{recipient-id}/{sender-id}" )
    public ResponseEntity<GetMessagesResponse> getMessagesBySender(
            @PathVariable(value = "recipient-id") Long recipientId,
            @PathVariable(value = "sender-id") Long senderId,
            @RequestParam(value = "days", required = false) Integer days,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "from", required = false) Long from
            ) {

        logger.info("MessengerController.getMessagesBySender: recipientId {}, senderId {}, days {}, limit {}, from {}",
                recipientId, senderId, days, limit, from);

        List<Message> messages = messengerService.getMessages(recipientId, senderId, days, limit, from);
        GetMessagesResponse response = new GetMessagesResponse(messages);

        logger.info("{}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Returns messages from all senders",
            parameters = {
                    @Parameter(name = "recipient-id", description = "Recipient id"),
                    @Parameter(name = "days", required = false,
                            description = "Optional parameter. Specifies for how many days messages needs to be returned. " +
                                    "If not specified, this parameter controlled by service's property \'messenger.service.message.request.days\' " +
                                    "and by default equals to 30"),
                    @Parameter(name = "limit", required = false,
                            description = "Optional parameter. Limits count of messages returned by response. " +
                                    "If not specified, this parameter controlled by service's property and by default equals to 100"),
                    @Parameter(name = "from", required = false,
                            description = "Optional parameter. Id of the message from which start search. Used for pagination of messages")
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request executed successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetMessagesBySendersResponse.class)) })
    })
    @GetMapping( "/{recipient-id}" )
    public ResponseEntity<GetMessagesBySendersResponse> getMessages(
            @PathVariable(value = "recipient-id") Long recipientId,
            @RequestParam(value = "days", required = false) Integer days,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "from", required = false) Long from
    ) {

        logger.info("MessengerController.getMessages: recipientId {}, days {}, limit {}, from {}",
                recipientId, days, limit, from);

        List<Message> messages = messengerService.getMessages(recipientId, null, days, limit, from);
        GetMessagesBySendersResponse response = new GetMessagesBySendersResponse(messages);

        logger.info("{}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
