package com.codex.booksnetwork.repository;

import com.codex.booksnetwork.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {

}
