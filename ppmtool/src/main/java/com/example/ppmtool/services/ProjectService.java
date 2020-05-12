package com.example.ppmtool.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ppmtool.entities.Backlog;
import com.example.ppmtool.entities.Project;
import com.example.ppmtool.entities.User;
import com.example.ppmtool.exceptions.ProjectException;
import com.example.ppmtool.exceptions.ProjectNotFoundException;
import com.example.ppmtool.repositories.BacklogRepository;
import com.example.ppmtool.repositories.ProjectRepository;
import com.example.ppmtool.repositories.UserRepository;

@Service
public class ProjectService {

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	BacklogRepository backlogRepository;

	@Autowired
	UserRepository userRepository;
	public Project saveOrUpdateProject(Project project, String username) {

//		try {
//			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
//
//			if (project.getId() == null) {
//				Backlog backlog = new Backlog();
//				project.setBacklog(backlog);
//				backlog.setProject(project);
//				backlog.setProjectIdentifier(project.getProjectIdentifier());
//			} else {
//				Backlog backlog = backlogRepository.findByProjectIdentifier(project.getProjectIdentifier());
//				
//				project.setBacklog(backlog);
//			}
//
//			return projectRepository.save(project);
//		} catch (Exception ex) {
//			throw new ProjectException(
//					"Project Identifier " + project.getProjectIdentifier().toUpperCase() + " already exists");
//		}
		
		
		
		if(project.getId() != null){
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
            if(existingProject !=null &&(!existingProject.getProjectLeader().equals(username))){
                throw new ProjectNotFoundException("Project not found in your account");
            }else if(existingProject == null){
                throw new ProjectNotFoundException("Project with ID: '"+project.getProjectIdentifier()+"' cannot be updated because it doesn't exist");
            }
        }

        try{

            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(project.getId()==null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId()!=null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);

        }catch (Exception e){
            throw new ProjectException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
        }
	}

	 public Project findProjectByIdentifier(String projectId, String username){

	        //Only want to return the project if the user looking for it is the owner

	        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

	        if(project == null){
	            throw new ProjectException("Project ID '"+projectId+"' does not exist");

	        }

	        if(!project.getProjectLeader().equals(username)){
	            throw new ProjectNotFoundException("Project not found in your account");
	        }



	        return project;
	    }
	 
	 
	 
	 
	public List<Project> findAllProjects(String username) {
		return projectRepository.findAllByProjectLeader(username);
	}

	public void deleteProject(String proId, String username) {

		Project project = this.findProjectByIdentifier(proId.toUpperCase(),username );

		projectRepository.delete(project);
	}
}
