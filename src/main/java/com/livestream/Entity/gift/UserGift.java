package com.livestream.Entity.gift;

import com.livestream.Entity.channel.Channel;
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
@Table(name = "user_gifts")
public class UserGift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    Users user;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    Channel channel;

    @ManyToOne
    @JoinColumn(name = "gift_id")
    Gift gift;

    int quantity;

    LocalDateTime sentAt;
}
