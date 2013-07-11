package com.nojava.service.quartz;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.ee.servlet.QuartzInitializerServlet;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.nojava.quartz.model.FireTime;
import com.nojava.quartz.model.TaskBean;

@Service
@Lazy
public class TaskService {

    private static final Logger log= Logger.getLogger(TaskService.class);
    private Scheduler scheduler;

    @PostConstruct
    private void init() throws SchedulerException {
        StdSchedulerFactory factory = (StdSchedulerFactory) ServletActionContext.getServletContext()
                .getAttribute(QuartzInitializerServlet.QUARTZ_FACTORY_KEY);
        scheduler = factory.getScheduler();
    }

    public void save(TaskBean taskBean) throws SchedulerException, ParseException, ClassNotFoundException {
        Class jobClass=Class.forName(taskBean.getTaskClass());
        JobDetail jobDetail = new JobDetail(taskBean.getName(), taskBean.getGroup(), jobClass);
        jobDetail.setDescription(taskBean.getDescribe());
        CronTrigger trigger = new CronTrigger(taskBean.getName(), taskBean.getGroup(),
                taskBean.getCronExpress());
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public TaskBean find(String name, String group) {
        TaskBean taskBean = new TaskBean();
        try{
        JobDetail jobDetail = scheduler.getJobDetail(name, group);
        taskBean.setName(jobDetail.getName());
        taskBean.setGroup(jobDetail.getGroup());
        taskBean.setTaskClass(jobDetail.getJobClass().getName());
        // 目前只支持1对1方式
        Trigger[] triggers = scheduler.getTriggersOfJob(name, group);
        CronTrigger cTrigger = (CronTrigger) triggers[0];
        scheduler.getTriggerState(name, group);
        taskBean.setCronExpress(cTrigger.getCronExpression());
        taskBean.setTriggerState(scheduler.getTriggerState(cTrigger.getName(), cTrigger.getGroup()));
        FireTime fireTime = new FireTime();
        fireTime.setNextFireTime(cTrigger.getNextFireTime());
        fireTime.setPrevFireTime(cTrigger.getPreviousFireTime());
        taskBean.setFireTime(fireTime);
        } catch(Exception e){
            log.error("name:"+name,e);
        }
        return taskBean;
    }

    public boolean delete(String name, String group) throws SchedulerException {
        return scheduler.deleteJob(name, group);
    }

    public List<TaskBean> getList(String group) throws SchedulerException {
        List<TaskBean> taskBeans = new ArrayList<TaskBean>();
        String[] names = null;
        if (StringUtils.isBlank(group)) {
            group = getGropName()[0];
        }
        names = scheduler.getJobNames(group);
        for (String name : names) {
            taskBeans.add(find(name, group));
        }
        return taskBeans;
    }

    public String[] getGropName() throws SchedulerException {
        return scheduler.getJobGroupNames();
    }

    public void run(String name, String group) throws SchedulerException {
        scheduler.triggerJob(name, group);
    }

    public void pause(String name, String group) throws SchedulerException {
        scheduler.pauseJob(name, group);
    }

    public void resume(String name, String group) throws SchedulerException {
        scheduler.resumeJob(name, group);
    }
}
