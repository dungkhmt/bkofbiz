package org.ofbiz.bkeuniv.model.sciencesection;

public enum ScienceSection {
	
	AppliedProject("applied-project-declaration"),
	
	ScientificService("scientific-service"),
	
	RecentPublications("recent-publications"),
	
	RecentProjects("recent-projects");
	
	private String value;
	
	ScienceSection(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
