package com.example.ppmtool.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ppmtool.entities.Backlog;
import com.example.ppmtool.entities.ProjectTask;
import com.example.ppmtool.exceptions.ProjectNotFoundException;
import com.example.ppmtool.repositories.BacklogRepository;
import com.example.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	BacklogRepository backlogRepository;
	@Autowired
	ProjectTaskRepository projectTaskRepository;
	@Autowired
	ProjectService projectService;
	
	
	public ProjectTask addProjectTask(String prId, ProjectTask projectTask, String username) {
		
		//Backlog backlog = backlogRepository.findByProjectIdentifier(prId);
		Backlog backlog = projectService.findProjectByIdentifier(prId, username).getBacklog();
		if(backlog == null)
			throw new ProjectNotFoundException("Project Not Found");
		
		projectTask.setBacklog(backlog);
		// To Create & Set projectSequence
		Integer backlogSequence = backlog.getPTSequence();
		backlogSequence++;
		backlog.setPTSequence(backlogSequence);
		projectTask.setProjectSequence(prId + "-" + backlogSequence);
		
		// Set Project Identifier
		projectTask.setProjectIdentifier(prId);
		
		//Set Project Task Priority if not defined
		if(projectTask.getPriority() == null || projectTask.getPriority() == 0)
			projectTask.setPriority(3);
		
		// Set Project Status if not defined
		if(projectTask.getStatus() == "" || projectTask.getStatus() == null)
			projectTask.setStatus("TO_DO");
		
		return projectTaskRepository.save(projectTask);
	}


	public List<ProjectTask> getProjectTasksOfCurrentBacklog(String backlog_id, String username) {
		// TODO Auto-generated method stub
		projectService.findProjectByIdentifier(backlog_id, username);
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
	}
	
	public ProjectTask getPTByBacklogIdAndSeqId(String backlogId, String ptSeqId, String username) {
		
		projectService.findProjectByIdentifier(backlogId, username);
		
			ProjectTask projectTask = projectTaskRepository.findByProjectSequence(ptSeqId);
			
			if(projectTask == null)
					throw new ProjectNotFoundException("Project Task doesn't exist with ID "+ ptSeqId);
			
			if(!projectTask.getBacklog().getProjectIdentifier().equals(backlogId))
				throw new ProjectNotFoundException("Project Task doesn't exist with ID "+ ptSeqId + " and id " + backlogId);
			
		return projectTask;
	}


	public ProjectTask updateBySequence(ProjectTask projectTask, String backlogId, String ptSeqId, String username) {
		ProjectTask task = this.getPTByBacklogIdAndSeqId(backlogId, ptSeqId, username);
		
		task = projectTask;
		
		return projectTaskRepository.save(task);
	}
	
	public void deletePTBySequence(String backlogId, String ptSeqId, String username) {
		ProjectTask task = this.getPTByBacklogIdAndSeqId(backlogId, ptSeqId, username);
		
//		Backlog backlog = task.getBacklog();
//		backlog.getProjectTasks().remove(task);
//		backlogRepository.save(backlog);
	
		projectTaskRepository.delete(task);
	}
}
