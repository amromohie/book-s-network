package com.codex.booksnetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookSNetworkApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookSNetworkApplication.class, args);
  }
}
