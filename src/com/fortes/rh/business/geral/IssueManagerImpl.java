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
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.github.Issue;
import com.fortes.rh.util.StringUtil;

@Component
public class IssueManagerImpl implements IssueManager
{
	private static final String URL_ISSUES = "https://api.github.com/repos/fortesinformatica/FortesRH/issues";
	private static final String URL_LABELS = "https://api.github.com/repos/fortesinformatica/FortesRH/labels";
	private static final String USERPWD = "suporterh:s1234rh";
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public Collection<Issue> getIssues(String label, boolean closed, int pageInicial, int pageFinal) {
		HttpClient client = new HttpClient();
		String url = URL_ISSUES + "?sort=created&direction=desc&page="+ pageInicial +"&per_page=" + pageFinal;

		if(closed)
			url += "&state=closed";
		if(StringUtils.isNotEmpty(label))
			url += "&labels="+label.replace(" ", "%20");
			
		GetMethod method = new GetMethod( url );
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
		
		try {
			JsonConfig jsonConfig = new JsonConfig();
			JSONArray arr = JSONArray.fromObject(issuesJson, jsonConfig);
			issues = (ArrayList<Issue>) JSONArray.toList(arr, Issue.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return issues;
	}
	
	
	public Issue findByNumber(String number) 
	{
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod( URL_ISSUES + "/" + number );
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
		String url = URL_ISSUES;
		
		if (isUpdate)
			url += "/" + issue.getNumber();
		
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		
        method.addRequestHeader("Content-Type", "application/json; charset=utf-8");  
        method.addRequestHeader("Authorization", "Basic " + StringUtil.encodeString(USERPWD));
        
        Map<Object, Object> params = new HashMap<Object, Object>();
        params.put("title", issue.getTitle());
        params.put("body", issue.getBody());
        if (issue.getLabelNames() == null)
        	issue.setLabelNames(new String[]{});
        params.put("labels", issue.getLabelNames());
        
        method.setRequestBody(StringUtil.toJSON(params, null));
        
        try {
        	int status = client.executeMethod(method);
        	
        	if (status != (isUpdate ? HttpStatus.SC_OK : HttpStatus.SC_CREATED))
        		throw new FortesException("Falha no envio: " + status + " - " + HttpStatus.getStatusText(status));
		
        } finally {
			method.releaseConnection();
		}
	}

	public String getLabels() 
	{
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod( URL_LABELS );
		method.setQueryString("");

        method.addRequestHeader("Content-Type", "application/json; charset=utf-8");  
        method.addRequestHeader("Authorization", "Basic " + StringUtil.encodeString(USERPWD)); 
        
		String labelsJson = "";
		try {
			client.executeMethod( method );
			labelsJson = method.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		
		return labelsJson;
	}
}
