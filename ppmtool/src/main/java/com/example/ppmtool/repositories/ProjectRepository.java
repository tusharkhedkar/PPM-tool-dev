package com.example.ppmtool.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ppmtool.entities.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	
	Project findByProjectIdentifier(String proIdentifierString);
	
	List<Project> findAllByProjectLeader(String username);
}
