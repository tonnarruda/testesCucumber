package com.fortes.rh.web.action.geral;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.IssueManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.github.Issue;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class IssueEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private IssueManager issueManager;
	private Issue issue;
	private String labels;
	private boolean closed;
	private String label;
	private Collection<Issue> issues;
    
	private void prepare() throws Exception
	{
		labels = issueManager.getLabels();
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		issue = issueManager.findByNumber(issue.getNumber());
		
		return Action.SUCCESS;
	}

	public String insert()
	{
		try {
			issueManager.save(issue);
			return Action.SUCCESS;
		
		} catch (FortesException e) {
			addActionError(e.getMessage());
			e.printStackTrace();
			return Action.INPUT;
			
		} catch (Exception e) {
			addActionError("Ocorreu um erro ao enviar os dados");
			e.printStackTrace();
			return Action.INPUT;
		}
	}

	public String update() throws Exception
	{
		try {
			issueManager.save(issue);
			return Action.SUCCESS;
		
		} catch (FortesException e) {
			addActionError(e.getMessage());
			e.printStackTrace();
			prepareUpdate();
			return Action.INPUT;
			
		} catch (Exception e) {
			addActionError("Ocorreu um erro ao enviar os dados");
			e.printStackTrace();
			prepareUpdate();
			return Action.INPUT;
		}
	}

	public String list() throws Exception
	{
		setTotalSize(issueManager.getIssues(label, closed, 1, 9999).size());	
		labels = issueManager.getLabels();
		issues = issueManager.getIssues(label, closed, getPage(), getPagingSize());
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
	
	public Collection<Issue> getIssues()
	{
		return issues;
	}

	public String getLabels() {
		return labels;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public boolean isClosed() {
		return closed;
	}
}