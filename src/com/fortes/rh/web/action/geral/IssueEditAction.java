package com.fortes.rh.web.action.geral;


import java.util.Collection;

import com.fortes.rh.business.geral.IssueManager;
import com.fortes.rh.model.geral.Issue;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class IssueEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private IssueManager issueManager;
	private Issue issue;
	private Collection<Issue> issues;

	private void prepare() throws Exception
	{
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		issues = issueManager.getIssues();
		return Action.SUCCESS;
	}
	
	public Issue getIssue()
	{
		if(issue == null)
			issue = new Issue();
		return issue;
	}

	public void setIssue(Issue issue)
	{
		this.issue = issue;
	}

	public void setIssueManager(IssueManager issueManager)
	{
		this.issueManager = issueManager;
	}
	
	public Collection<Issue> getIssues()
	{
		return issues;
	}
}
