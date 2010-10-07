package com.fortes.rh.config.backup;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.listener.Log4jListener;

/**
 * http://www.ibm.com/developerworks/websphere/library/techarticles/0502_gawor/0502_gawor.html
 */
public class RunAntScript {

	private String scriptPath;
	private String targetName;
	
	private Project project;
	

	public RunAntScript() {}
	
	public RunAntScript(String scriptName) {
		this.scriptPath = scriptName;
		project = new Project();
	}
	
	public RunAntScript(String scriptPath, String targetName) {
		this(scriptPath);
		this.targetName = targetName;
	}
	
	/**
	 * Executa script Ant. Se um target é passado como parametro então ele será
	 * executado, caso contrario o target default do script será executado.
	 */
	public void launch() {
		// FIXME: Isso está amarrado a um script, e não deveria
		if(this.scriptPath == null)
			this.scriptPath = this.getClass().getResource("backup_script.xml").getFile().replace("\\", "/").replace("%20", " ");
		
		try {
			configure();
			execute();
		} catch (BuildException e) {
			project.fireBuildFinished(e);
			throw new RuntimeException("Erro ao tentar rodar script ant: " + e.getMessage(), e);
		}
	}

	/**
	 * Executa script ant.
	 */
	private void execute() {
		project.executeTarget(this.getTarget());
		project.fireBuildFinished(null);
	}
	/**
	 * Configura projeto baseado no build file.
	 */
	private void configure() {
		// FIXME: Deve ter um jeito melhor :-)
		boolean isNotFirstInvocation = (project.getReference("ant.projectHelper") != null);
		if (isNotFirstInvocation)
			return;
		
		File buildFile = new File(scriptPath);
		
		project.setUserProperty("ant.file", buildFile.getAbsolutePath());
		addLogger(project);
		project.fireBuildStarted();
		project.init();
		ProjectHelper helper = ProjectHelper.getProjectHelper();
		project.addReference("ant.projectHelper", helper);
		helper.parse(project, buildFile);
	}
	/**
	 * Adiciona uma propriedade ao script.
	 */
	public void addProperty(String name, String value) {
		project.setProperty(name, value);
	}
	/**
	 * Retorna propriedade definida no script pelo nome;
	 */
	public String getProperty(String name) {
		return project.getProperty(name);
	}
	/**
	 * Retorna o target correto.
	 */
	private String getTarget() {
		return this.targetName != null ? this.targetName : project.getDefaultTarget();
	}
	/**
	 * Habilita logging integrado ao Log4J.
	 */
	private void addLogger(Project p) {
		p.addBuildListener(new Log4jListener());
	}

	public String getScriptPath() {
		return this.scriptPath;
	}
	public String getTargetName() {
		return this.targetName;
	}
	
}
