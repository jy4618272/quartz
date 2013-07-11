package com.nojava.quartz.model;

public enum TaskGrop {
	CMS("cms"),SOLR("solr");
	private final String value;
	TaskGrop(String value){
		this.value=value;
	}
	public String getValue(){
		return value;
	}

}
