package com.livestream.Entity.site;

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
@Table(name = "site_info")
public class SiteInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(unique = true)
    String keyword; // ABOUT, TERMS, PRIVACY, CONTACT, etc.

    @Column(columnDefinition = "TEXT")
    String content; // markdown/html

    LocalDateTime updatedAt;

    Integer updatedBy; // user id of admin/editor
}
