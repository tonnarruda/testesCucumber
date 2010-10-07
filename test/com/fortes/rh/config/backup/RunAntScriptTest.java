package com.fortes.rh.config.backup;

import junit.framework.TestCase;

public class RunAntScriptTest extends TestCase {

	RunAntScript runAntScript;
	
	public void testShouldGetPropertyFromAntScript() {
		runAntScript = new RunAntScript(this.getAntScript());
		runAntScript.launch();
		String property = runAntScript.getProperty("propriedadeDeTeste");
		
		assertEquals("propriedade do script", "valorDaPropriedadeDeTeste", property);
	}
	
	public void testShouldAddPropertyToAntScript() {
		runAntScript = new RunAntScript(this.getAntScript(), "show_property_target");
		runAntScript.addProperty("propriedade", "valor");
		runAntScript.launch();
		
		String property = runAntScript.getProperty("propriedade");
		
		assertEquals("propriedade do script", "valor", property);
	}
	
	public void testConstrutoresDaClasse() {
		// construtor 1
		runAntScript = new RunAntScript("ant_script.xml");
		assertEquals("ant_script.xml", runAntScript.getScriptPath());
		assertNull(runAntScript.getTargetName());
		// construtor 2
		runAntScript = new RunAntScript("ant_script.xml", "test");
		assertEquals("ant_script.xml", runAntScript.getScriptPath());
		assertEquals("test", runAntScript.getTargetName());
	}

	public void testLaunchWhenTargetWasNotInformed() {
		runAntScript = new RunAntScript(this.getAntScript());
		runAntScript.launch();
	}
	
	public void testLaunchWhenTargetWasInformed() {
		runAntScript = new RunAntScript(this.getAntScript(), "show_timestamp_target");
		runAntScript.launch();
	}
	
	public void testLaunchWhenScriptPathIsIncorrect() {
		try {
			runAntScript = new RunAntScript("ant_script.xml__");
			runAntScript.launch();
			fail("Deveria ter lançado uma exceção.");
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
			assertTrue(e.getMessage().contains("java.io.FileNotFoundException"));
		}
	}

	private String getAntScript() {
		return this.getClass().getResource("ant_script.xml").getFile().replace("\\", "/").replace("%20", " ");
	}

}
