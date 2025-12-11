package com.livestream.Entity.follower;

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
@Table(name = "followers")
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    Users follower;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    Channel channel;

    LocalDateTime createdAt;
}
