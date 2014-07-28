package com.fortes.rh.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.fortes.rh.util.CollectionUtil;
import com.opensymphony.xwork.Action;

public class JobsAction extends MyActionSupport 
{
	private static final long serialVersionUID = 1L;
	
	private List<Trigger> triggers;
	private String jobName;
	private String jobGroup;
	private String jobClass;
	
	@SuppressWarnings("unchecked")
	public String index() throws SchedulerException
	{
		if (getUsuarioLogado().getId() != 1L)
			return "index";
		
		triggers = new ArrayList<Trigger>();
		
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();

		for (String groupName : scheduler.getJobGroupNames()) 
		{
			for (String jobName : scheduler.getJobNames(groupName)) 
			{
				triggers.addAll(Arrays.asList(scheduler.getTriggersOfJob(jobName, groupName)));
			}
		}
		
		new CollectionUtil<Trigger>().sortCollectionStringIgnoreCase(triggers, "jobName");
		
		return Action.SUCCESS;
	}
	
	public String executeJob() throws SchedulerException, ClassNotFoundException
	{
		if (getUsuarioLogado().getId() != 1L)
			return "index";
		
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		JobDetail detail = new JobDetail(jobName, jobGroup, Class.forName(jobClass));
		SimpleTrigger trigger = new SimpleTrigger(jobName + "Trigger", jobGroup, new Date());
		
		scheduler.scheduleJob(detail, trigger);
		
		return Action.SUCCESS;
	}

	public List<Trigger> getTriggers() 
	{
		return triggers;
	}

	public void setJobName(String jobName) 
	{
		this.jobName = jobName;
	}

	public void setJobGroup(String jobGroup) 
	{
		this.jobGroup = jobGroup;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}
}
