package com.example.ppmtool.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ppmtool.entities.Backlog;

@Repository
public interface BacklogRepository extends JpaRepository<Backlog, Long> {

	
	Backlog findByProjectIdentifier(String pId);
}
