package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.rh.model.geral.Issue;


public interface IssueManager
{
	Collection<Issue> getIssues();
	void save(Issue issue) throws Exception;
	Issue findByNumber(String number);
}
