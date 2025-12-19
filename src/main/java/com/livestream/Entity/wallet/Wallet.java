package com.livestream.Entity.wallet;

import com.livestream.Entity.user.Users;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    Users user;

    @Builder.Default
    @Column(nullable = false)
    BigDecimal balance = BigDecimal.ZERO;

    @Builder.Default
    @Column(nullable = false)
    BigDecimal lockedBalance = BigDecimal.ZERO;

    @Builder.Default
    @Column(nullable = false)
    String currency = "Xu";

    LocalDateTime updatedAt;
}
