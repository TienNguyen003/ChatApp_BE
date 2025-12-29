package com.livestream.Entity.subscription;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.livestream.Entity.channel.Channel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "subscription_packages")
public class SubscriptionPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "channel_id", nullable = false)
    Channel channel;

    int tierLevel; // 1, 2, 3

    String tierName; // VIP, Premium, Member

    BigDecimal price;

    @Column(columnDefinition = "TEXT")
    String benefits; // JSON: ["badge", "emoji", "early_access"]

    String description;

    @Builder.Default
    @Column(nullable = false)
    boolean isActive = true;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
