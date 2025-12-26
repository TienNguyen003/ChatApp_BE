package com.livestream.Entity.event;

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
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;

    @Enumerated(EnumType.STRING)
    EventType type;

    @Column(columnDefinition = "TEXT")
    String description;

    String bannerUrl;

    LocalDateTime startAt;

    LocalDateTime endAt;

    Integer maxParticipants; // max participants allowed, null = unlimited

    Integer currentParticipants; // current count, updated when user joins

    LocalDateTime publishedAt; // when event was published/registered

    @Column(columnDefinition = "TEXT")
    String rules; // optional JSON/text rules

    @Column(columnDefinition = "TEXT")
    String prizeSummary; // short text about prizes
}
