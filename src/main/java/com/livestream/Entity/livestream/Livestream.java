package com.livestream.Entity.livestream;

import com.livestream.Entity.channel.Channel;
import com.livestream.Entity.category.Category;
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
@Table(name = "livestreams")
public class Livestream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    Channel channel;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    String title;

    int viewersCount;

    String status;

    LocalDateTime startedAt;

    LocalDateTime endedAt;

    String streamUrl;

    String thumbnailUrl;
}
