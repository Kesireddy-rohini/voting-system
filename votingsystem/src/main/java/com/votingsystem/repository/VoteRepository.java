package com.votingsystem.repository;

import com.votingsystem.entity.Vote;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
	 Optional<Vote> findByEmail(String email);
}
