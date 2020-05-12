package com.example.ppmtool.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ppmtool.entities.Project;
import com.example.ppmtool.services.ProjectService;
import com.example.ppmtool.utilities.UtilityService;

@RestController
@CrossOrigin
@RequestMapping("api/project")
public class ProjectController {

	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	UtilityService utilityService;
	
	@PostMapping("")
	public ResponseEntity<?> addProject(@Valid @RequestBody Project project, BindingResult result, Principal principal) {
		
		ResponseEntity<Map<String,String>> errorMap = utilityService.checkerrors(result);
			
		if(errorMap != null)
			return errorMap;
		
		Project savedProject = projectService.saveOrUpdateProject(project, principal.getName());
		
		return new ResponseEntity<Project>(savedProject, HttpStatus.CREATED);
	}
	
	
	@GetMapping("/{projectId}")
	 public ResponseEntity<?> getProjectByProjectIdentifier(@PathVariable String projectId, Principal principal) {
		
			return new ResponseEntity<Project>(projectService.findProjectByIdentifier(projectId, principal.getName()), HttpStatus.OK);
					
		}
	
	
	@GetMapping("/all")
	public List<Project> getProjectsAll(Principal principal) {
		return projectService.findAllProjects(principal.getName() );
	}
	
	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProject(@PathVariable String projectId, Principal principal) {
			projectService.deleteProject(projectId, principal.getName());
			
			return new ResponseEntity<String>(projectId.toUpperCase() +" deleted successfully!", HttpStatus.OK);
	}
	
}
