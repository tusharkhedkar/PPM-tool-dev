package com.example.ppmtool.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ppmtool.entities.ProjectTask;
import com.example.ppmtool.exceptions.ProjectNotFoundException;
import com.example.ppmtool.services.ProjectService;
import com.example.ppmtool.services.ProjectTaskService;
import com.example.ppmtool.utilities.UtilityService;

@RestController
@CrossOrigin
@RequestMapping("/api/backlog")
public class BacklogController {

	@Autowired
	ProjectService projectService;
	@Autowired
	UtilityService utilityService;
	@Autowired
	ProjectTaskService projectTaskService;

	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addProjectTaskToBacklog(@PathVariable String backlog_id,
			@Valid @RequestBody ProjectTask projectTask, BindingResult result, 
			Principal principal) {

		ResponseEntity<?> errorMap = utilityService.checkerrors(result);
		if (errorMap != null)
			return errorMap;

		ProjectTask projectTask2 = projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName());
		return new ResponseEntity<ProjectTask>(projectTask2, HttpStatus.CREATED);

	}
	
	@GetMapping("/{backlog_id}")
	public List<ProjectTask> getProjectTasksOfCurrentBacklog(@PathVariable String backlog_id, Principal principal) {
		List<ProjectTask> projectTasks =  projectTaskService.getProjectTasksOfCurrentBacklog(backlog_id, principal.getName());
		
					if(projectTasks.isEmpty())
						throw new ProjectNotFoundException("Project Tasks doesn't exist with ID "+ backlog_id);
					return projectTasks;
		
				
	}
	
	@GetMapping("/{backlogId}/{ptSeqId}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlogId, @PathVariable String ptSeqId, Principal principal) {
		
		ProjectTask projectTask = projectTaskService.getPTByBacklogIdAndSeqId(backlogId, ptSeqId, principal.getName());
		
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
	}
	
	
	@PutMapping("/{backlogId}/{ptSeqId}")	
	public ResponseEntity<?> updateProjectTask(@Valid @ RequestBody ProjectTask projectTask,BindingResult result,
			@PathVariable String backlogId, @PathVariable String ptSeqId, Principal principal) {
		
		
		ResponseEntity<?> errorMap = utilityService.checkerrors(result);
		if (errorMap != null)
			return errorMap;

		ProjectTask updatedProjectTask = projectTaskService.updateBySequence(projectTask, backlogId, ptSeqId, principal.getName());
		
		return new ResponseEntity<ProjectTask>(updatedProjectTask,HttpStatus.OK);
	}
	
	@DeleteMapping("/{backlogId}/{ptSeqId}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlogId, @PathVariable String ptSeqId, Principal principal) {
		
		projectTaskService.deletePTBySequence(backlogId, ptSeqId, principal.getName());
		
		return new ResponseEntity<String>("Project Task With ID " + ptSeqId + " Deleted Successfully...", HttpStatus.OK);
	}
}
