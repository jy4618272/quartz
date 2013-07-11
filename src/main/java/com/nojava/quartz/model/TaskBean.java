package com.nojava.quartz.model;

import java.util.Map;

public class TaskBean {

    private String name;
    private String group;
    private String taskClass;
    private String describe;
    private String cronExpress;
    private Map<String, String> context;
    private FireTime fireTime;
    private Integer triggerState;

    public String getCronExpress() {
        return cronExpress;
    }

    public void setCronExpress(String cronExpress) {
        this.cronExpress = cronExpress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTaskClass() {
        return taskClass;
    }

    public void setTaskClass(String taskClass) {
        this.taskClass = taskClass;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public FireTime getFireTime() {
        return fireTime;
    }

    public void setFireTime(FireTime fireTime) {
        this.fireTime = fireTime;
    }

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> context) {
        this.context = context;
    }

    public Integer getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(Integer triggerState) {
        this.triggerState = triggerState;
    }
    
}
