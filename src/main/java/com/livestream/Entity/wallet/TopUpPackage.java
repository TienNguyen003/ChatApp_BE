package com.livestream.Entity.wallet;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "top_up_packages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopUpPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String description;

    @Column(nullable = false, precision = 19, scale = 2)
    BigDecimal amount;

    @Column(nullable = false, precision = 19, scale = 2)
    BigDecimal bonusAmount;

    @Column(nullable = false)
    String currency;

    @Column(nullable = false)
    Boolean isActive;

    @Column(nullable = false)
    Integer displayOrder;

    @Column(nullable = false)
    Boolean isPopular;

    String icon;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isActive == null) {
            isActive = true;
        }
        if (isPopular == null) {
            isPopular = false;
        }
        if (displayOrder == null) {
            displayOrder = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
