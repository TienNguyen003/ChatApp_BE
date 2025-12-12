package com.livestream.Controller;

import com.livestream.DTO.websocket.ChatMessage;
import com.livestream.DTO.websocket.LivestreamStatus;
import com.livestream.DTO.websocket.ReactionMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebSocketController {
    SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/{livestreamId}")
    @SendTo("/receive/chat/{livestreamId}")
    public ChatMessage sendChatMessage(@DestinationVariable int livestreamId, ChatMessage message) {
        message.setLivestreamId(livestreamId);
        message.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return message;
    }

    @MessageMapping("/reaction/{livestreamId}")
    @SendTo("/receive/reaction/{livestreamId}")
    public ReactionMessage sendReaction(@DestinationVariable int livestreamId, ReactionMessage reaction) {
        reaction.setLivestreamId(livestreamId);
        reaction.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return reaction;
    }

    // Method to broadcast livestream status updates
    public void broadcastLivestreamStatus(LivestreamStatus status) {
        messagingTemplate.convertAndSend("/receive/livestream/status", status);
    }

    // Method to update viewer count
    public void updateViewerCount(int livestreamId, int viewersCount) {
        messagingTemplate.convertAndSend(
                "/receive/livestream/" + livestreamId + "/viewers",
                viewersCount);
    }
}
