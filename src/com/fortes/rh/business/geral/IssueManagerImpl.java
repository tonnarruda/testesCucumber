package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Transient;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import com.fortes.rh.model.geral.Issue;


public class IssueManagerImpl implements IssueManager
{
	private static final String URL = "https://api.github.com/repos/fortesinformatica/FortesRH/issues";
	private static final String USERPWD = "suporterh:s1234rh";
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public Collection<Issue> getIssues() {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod( URL );
		method.setQueryString("");

        method.addRequestHeader("Content-Type", "application/json; charset=utf-8");  
        method.addRequestHeader("Authorization", "Basic " + new sun.misc.BASE64Encoder().encode(USERPWD.getBytes()));  
        
		String issuesJson = "";
		try {
			client.executeMethod( method );
			issuesJson = method.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		
		Collection<Issue> issues = new ArrayList<Issue>();
		ArrayList<Issue> cus = new ArrayList<Issue>();
		
		try {
			JsonConfig jsonConfig = new JsonConfig();
			JSONArray arr = JSONArray.fromObject(issuesJson, jsonConfig);
			cus = (ArrayList<Issue>) JSONArray.toList(arr, Issue.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		issues.addAll(cus);
		
		return null;
	}
}
