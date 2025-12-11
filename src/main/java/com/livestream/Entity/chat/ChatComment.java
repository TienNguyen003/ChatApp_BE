package com.livestream.Entity.chat;

import com.livestream.Entity.livestream.Livestream;
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
@Table(name = "chat_comment")
public class ChatComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "livestream_id")
    Livestream livestream;

    @ManyToOne
    @JoinColumn(name = "user_id")
    Users user;

    @Column(columnDefinition = "TEXT")
    String message;

    LocalDateTime createdAt;
}
