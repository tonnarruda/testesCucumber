package com.fortes.rh.web.action;

import java.util.ArrayList;
import java.util.List;

import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.fortes.rh.util.CollectionUtil;
import com.opensymphony.xwork.Action;

public class JobsAction extends MyActionSupport 
{
	private static final long serialVersionUID = 1L;
	
	private String jobName;
	private List<Trigger> triggers;
	
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
	
	public String executarJob()
	{
		return Action.SUCCESS;
	}

	public String getJobName() 
	{
		return jobName;
	}

	public void setJobName(String jobName) 
	{
		this.jobName = jobName;
	}
	
	public List<Trigger> getTriggers() 
	{
		return triggers;
	}
}
