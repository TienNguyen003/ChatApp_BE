package com.livestream.Entity.support;

import com.livestream.Entity.user.Users;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "support_tickets")
public class SupportTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    Users user;

    String category; // ACCOUNT, PAYMENT, REPORT, OTHER

    String title;

    @Column(columnDefinition = "TEXT")
    String description;

    // OPEN, IN_PROGRESS, RESOLVED, CLOSED
    String status;

    String priority; // LOW, MEDIUM, HIGH, URGENT

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    Users assignee; // optional staff member

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    LocalDateTime closedAt;
}
