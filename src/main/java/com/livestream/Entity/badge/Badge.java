package com.livestream.Entity.badge;

import com.livestream.Entity.gift.Gift;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "badges")
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;

    String iconUrl;

    @Column(columnDefinition = "TEXT")
    String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    BadgeCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    BadgeRarity rarity;

    @Column(name = "target_value")
    Integer targetValue;

    String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gift_id")
    Gift gift;
}
