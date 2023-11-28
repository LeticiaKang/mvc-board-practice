package com.fastcampus.boardpractice.repository;

import com.fastcampus.boardpractice.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}