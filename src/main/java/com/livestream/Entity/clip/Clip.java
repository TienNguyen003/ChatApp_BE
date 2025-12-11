package com.livestream.Entity.clip;

import com.livestream.Entity.channel.Channel;
import com.livestream.Entity.livestream.Livestream;
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
@Table(name = "clips")
public class Clip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "livestream_id")
    Livestream livestream;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    Channel channel;

    String title;

    String startTime;

    String endTime;

    String videoUrl;

    String thumbnailUrl;
}
