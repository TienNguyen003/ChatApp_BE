package com.livestream.Mapper.event;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.livestream.DTO.request.event.EventUpdateRequest;
import com.livestream.DTO.response.event.EventParticipationResponse;
import com.livestream.DTO.response.event.EventResponse;
import com.livestream.Entity.event.Event;
import com.livestream.Entity.event.EventParticipation;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventResponse toEventResponse(Event event);

    EventParticipationResponse toParticipationResponse(EventParticipation participation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEventFromRequest(@MappingTarget Event event, EventUpdateRequest request);
}
