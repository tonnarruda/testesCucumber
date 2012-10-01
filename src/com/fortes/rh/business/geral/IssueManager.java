package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.rh.model.github.Issue;


public interface IssueManager
{
	Collection<Issue> getIssues(String label, boolean closed, int pageInicial, int pageFinal);
	String getLabels();
	void save(Issue issue) throws Exception;
	Issue findByNumber(String number);
}
