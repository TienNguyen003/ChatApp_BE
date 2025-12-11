package com.livestream.Entity.channel;

import com.livestream.Entity.user.Users;
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
@Table(name = "channels")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    Users user;

    String name;

    @Column(columnDefinition = "TEXT")
    String description;

    String avatarUrl;

    String bannerUrl;

    int followersCount;
}
