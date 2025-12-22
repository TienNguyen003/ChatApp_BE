package com.livestream.Service.event;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.livestream.DTO.request.event.EventCompleteRequest;
import com.livestream.DTO.request.event.EventCreateRequest;
import com.livestream.DTO.request.event.EventUpdateRequest;
import com.livestream.DTO.response.event.EventParticipationResponse;
import com.livestream.DTO.response.event.EventResponse;
import com.livestream.Entity.event.Event;
import com.livestream.Entity.event.EventParticipation;
import com.livestream.Entity.user.Users;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.event.EventMapper;
import com.livestream.Repository.event.EventParticipationRepository;
import com.livestream.Repository.event.EventRepository;
import com.livestream.Repository.user.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventService {
    EventRepository eventRepository;
    EventParticipationRepository participationRepository;
    UserRepository userRepository;
    EventMapper eventMapper;

    public Page<EventResponse> listEvents(Pageable pageable) {
        return eventRepository.findAll(pageable).map(eventMapper::toEventResponse);
    }

    public EventResponse createEvent(EventCreateRequest request) {
        validateTime(request.getStartAt(), request.getEndAt());
        Event event = Event.builder()
                .name(request.getName())
                .description(request.getDescription())
                .bannerUrl(request.getBannerUrl())
                .startAt(request.getStartAt())
                .endAt(request.getEndAt())
                .rules(request.getRules())
                .prizeSummary(request.getPrizeSummary())
                .build();
        return eventMapper.toEventResponse(eventRepository.save(event));
    }

    public EventResponse updateEvent(int id, EventUpdateRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND));

        if (request.getName() != null)
            event.setName(request.getName());
        if (request.getDescription() != null)
            event.setDescription(request.getDescription());
        if (request.getBannerUrl() != null)
            event.setBannerUrl(request.getBannerUrl());
        if (request.getStartAt() != null)
            event.setStartAt(request.getStartAt());
        if (request.getEndAt() != null)
            event.setEndAt(request.getEndAt());
        if (request.getRules() != null)
            event.setRules(request.getRules());
        if (request.getPrizeSummary() != null)
            event.setPrizeSummary(request.getPrizeSummary());

        validateTime(event.getStartAt(), event.getEndAt());
        return eventMapper.toEventResponse(eventRepository.save(event));
    }

    public void deleteEvent(int id) {
        if (!eventRepository.existsById(id)) {
            throw new AppException(ErrorCode.EVENT_NOT_FOUND);
        }
        eventRepository.deleteById(id);
    }

    public EventParticipationResponse joinEvent(int eventId) {
        Users user = getCurrentUser();
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND));

        var existing = participationRepository.findByEventIdAndUserId(event.getId(), user.getId());
        if (existing.isPresent()) {
            throw new AppException(ErrorCode.ALREADY_JOINED_EVENT);
        }

        EventParticipation part = participationRepository.save(EventParticipation.builder()
                .event(event)
                .user(user)
                .status("JOINED")
                .joinedAt(LocalDateTime.now())
                .build());
        return eventMapper.toParticipationResponse(part);
    }

    public EventParticipationResponse completeEvent(int eventId, EventCompleteRequest request) {
        Users user = getCurrentUser();
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND));

        EventParticipation part = participationRepository.findByEventIdAndUserId(event.getId(), user.getId())
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND));

        part.setStatus("COMPLETED");
        part.setScore(request.getScore());
        part.setResult(request.getResult());
        part.setCompletedAt(LocalDateTime.now());
        return eventMapper.toParticipationResponse(participationRepository.save(part));
    }

    private Users getCurrentUser() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    private void validateTime(LocalDateTime start, LocalDateTime end) {
        if (start != null && end != null && end.isBefore(start)) {
            throw new AppException(ErrorCode.INVALID);
        }
    }
}
