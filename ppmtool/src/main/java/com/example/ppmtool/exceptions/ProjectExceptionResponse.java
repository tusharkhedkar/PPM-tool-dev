package com.example.ppmtool.exceptions;

public class ProjectExceptionResponse {

	private String projectIdentifier;

	ProjectExceptionResponse(String exceptionResponse) {
		this.projectIdentifier = exceptionResponse;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

}
