package com.livestream.Mapper.event;

import com.livestream.DTO.response.event.EventParticipationResponse;
import com.livestream.DTO.response.event.EventResponse;
import com.livestream.Entity.event.Event;
import com.livestream.Entity.event.EventParticipation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventResponse toEventResponse(Event event);

    EventParticipationResponse toParticipationResponse(EventParticipation participation);
}
