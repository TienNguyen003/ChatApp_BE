package com.livestream.Mapper.support;

import com.livestream.DTO.response.support.SupportTicketResponse;
import com.livestream.Entity.support.SupportTicket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupportTicketMapper {
    SupportTicketResponse toSupportTicketResponse(SupportTicket ticket);
}
