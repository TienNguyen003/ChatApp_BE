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

    @Column(columnDefinition = "TEXT")
    String description;

    String bannerUrl;

    LocalDateTime startAt;

    LocalDateTime endAt;

    @Column(columnDefinition = "TEXT")
    String rules; // optional JSON/text rules

    String prizeSummary; // short text about prizes
}
