package com.livestream.Entity.video;

import com.livestream.Entity.channel.Channel;
import com.livestream.Entity.category.Category;
import com.livestream.Entity.tag.Tag;
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
@Table(name = "videos")
public class Video {
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

    @Column(columnDefinition = "TEXT")
    String description;

    int views;

    int likes;

    int dislikes;

    String duration;

    String videoUrl;

    String thumbnailUrl;

    LocalDateTime uploadedAt;

    @ManyToMany
    @JoinTable(name = "video_tags", joinColumns = @JoinColumn(name = "video_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    Set<Tag> tags;
}
