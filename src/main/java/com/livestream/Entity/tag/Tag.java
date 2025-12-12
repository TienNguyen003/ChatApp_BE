package com.livestream.Entity.tag;

import com.livestream.Entity.livestream.Livestream;
import com.livestream.Entity.video.Video;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(unique = true)
    String name;

    String description;

    int usageCount;

    LocalDateTime createdAt;

    @ManyToMany(mappedBy = "tags")
    Set<Livestream> livestreams;

    @ManyToMany(mappedBy = "tags")
    Set<Video> videos;
}
