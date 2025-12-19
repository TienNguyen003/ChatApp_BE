package com.livestream.Service.support;

import com.livestream.DTO.request.support.SupportTicketRequest;
import com.livestream.DTO.request.support.SupportTicketUpdateRequest;
import com.livestream.DTO.response.support.SupportTicketResponse;
import com.livestream.Entity.support.SupportTicket;
import com.livestream.Entity.user.Users;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.support.SupportTicketMapper;
import com.livestream.Repository.support.SupportTicketRepository;
import com.livestream.Repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SupportService {
    SupportTicketRepository ticketRepository;
    UserRepository userRepository;
    SupportTicketMapper ticketMapper;

    public SupportTicketResponse createTicket(SupportTicketRequest request) {
        Users user = getCurrentUser();
        SupportTicket ticket = ticketRepository.save(SupportTicket.builder()
                .user(user)
                .category(request.getCategory())
                .title(request.getTitle())
                .description(request.getDescription())
                .status("OPEN")
                .priority(request.getPriority())
                .createdAt(LocalDateTime.now())
                .build());
        return ticketMapper.toSupportTicketResponse(ticket);
    }

    public Page<SupportTicketResponse> myTickets(Pageable pageable) {
        Users user = getCurrentUser();
        return ticketRepository.findByUserId(user.getId(), pageable)
                .map(ticketMapper::toSupportTicketResponse);
    }

    // Admin CRUD Operations
    public SupportTicketResponse updateTicket(Long id, SupportTicketUpdateRequest request) {
        SupportTicket ticket = ticketRepository.findById(id.intValue())
                .orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_FOUND));

        if (request.getStatus() != null) {
            ticket.setStatus(request.getStatus());
        }
        if (request.getPriority() != null) {
            ticket.setPriority(request.getPriority());
        }
        if (request.getAssigneeId() != null) {
            Users assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            ticket.setAssignee(assignee);
        }

        ticket.setUpdatedAt(LocalDateTime.now());
        return ticketMapper.toSupportTicketResponse(ticketRepository.save(ticket));
    }

    public void deleteTicket(Long id) {
        SupportTicket ticket = ticketRepository.findById(id.intValue())
                .orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_FOUND));
        ticketRepository.delete(ticket);
    }

    private Users getCurrentUser() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
}
