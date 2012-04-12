package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.Issue;
import com.fortes.rh.util.StringUtil;


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
        method.addRequestHeader("Authorization", "Basic " + StringUtil.encodeString(USERPWD)); 
        
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
			jsonConfig.setExcludes( new String[]{"pull_request", "user", "html_url", "url", "comments", "assignee", "milestone"} );
			JSONArray arr = JSONArray.fromObject(issuesJson, jsonConfig);
			cus = (ArrayList<Issue>) JSONArray.toList(arr, Issue.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		issues.addAll(cus);
		for (Issue issue : issues) {
			System.out.println(issue.getNumber());
			System.out.println(issue.getLabels());
		}
		return issues;
	}
	
	public Issue findByNumber(String number) 
	{
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod( URL + "/" + number );
		method.setQueryString("");

        method.addRequestHeader("Content-Type", "application/json; charset=utf-8");  
        method.addRequestHeader("Authorization", "Basic " + StringUtil.encodeString(USERPWD)); 
        
        
		String issueJson = "";
		try {
			client.executeMethod( method );
			issueJson = method.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		
		Issue issue = new Issue();
		try {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes( new String[]{"pull_request", "user", "html_url", "url", "comments", "assignee", "milestone"} );
			
			JSONObject obj = JSONObject.fromObject(issueJson, jsonConfig);
			issue = (Issue) JSONObject.toBean(obj, Issue.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return issue;
	}

	@SuppressWarnings("deprecation")
	public void save(Issue issue) throws Exception 
	{
		boolean isUpdate = !StringUtil.isBlank(issue.getNumber());
		String url = URL;
		
		if (isUpdate)
			url += "/" + issue.getNumber();
		
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		
        method.addRequestHeader("Content-Type", "application/json; charset=utf-8");  
        method.addRequestHeader("Authorization", "Basic " + StringUtil.encodeString(USERPWD));
        
        Map<Object, Object> params = new HashMap<Object, Object>();
        params.put("title", issue.getTitle());
        params.put("body", issue.getBody());
        
        method.setRequestBody(StringUtil.toJSON(params, null));
        
        try {
        	int status = client.executeMethod(method);
        	
        	if (status != (isUpdate ? HttpStatus.SC_OK : HttpStatus.SC_CREATED))
        		throw new FortesException("Falha no envio: " + status + " - " + HttpStatus.getStatusText(status));
		
        } finally {
			method.releaseConnection();
		}
	}
}
