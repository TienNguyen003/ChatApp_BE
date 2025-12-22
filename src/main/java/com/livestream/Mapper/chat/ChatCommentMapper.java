package com.livestream.Mapper.chat;

import com.livestream.DTO.request.chat.ChatMessageRequest;
import com.livestream.DTO.response.chat.ChatCommentResponse;
import com.livestream.Entity.chat.ChatComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatCommentMapper {
    ChatComment toChatComment(ChatMessageRequest request);

    ChatCommentResponse toChatCommentResponse(ChatComment chatComment);
}
