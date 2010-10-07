package com.fortes.rh.business.geral;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.fortes.rh.web.action.geral.DocumentoVersaoListAction;

public class LimpaDirTemp implements Job
{
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		try
		{
			DocumentoVersaoListAction.limpaDirTemp();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}