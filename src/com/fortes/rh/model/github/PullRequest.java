package com.fortes.rh.model.github;

public class PullRequest {

	private String html_url;
	private String diff_url;
	private String patch_url;
	
	public String getHtml_url() {
		return html_url;
	}
	
	public void setHtml_url(String html_url) {
		this.html_url = html_url;
	}
	
	public String getDiff_url() {
		return diff_url;
	}
	
	public void setDiff_url(String diff_url) {
		this.diff_url = diff_url;
	}
	
	public String getPatch_url() {
		return patch_url;
	}
	
	public void setPatch_url(String patch_url) {
		this.patch_url = patch_url;
	}
}
