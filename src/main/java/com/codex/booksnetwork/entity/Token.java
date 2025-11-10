package com.codex.booksnetwork.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Getter
@Setter
public class Token {

  @Id
  @SequenceGenerator(name = "token_seq_id", sequenceName = "token_seq_id", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_seq_id")
  private Long id;
  private String token;
  private LocalDateTime createdAt;
  private LocalDateTime expiredAt;
  private LocalDateTime validatedAt;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

}
