package com.fortes.rh.config;

import java.io.File;

import junit.framework.TestCase;

import org.junit.Assert;

public class ScriptReaderTest extends TestCase {

	private static final String SCRIPT_UPDATE_TEST_SQL = "script_update_test.sql";
	
	ScriptReader reader;
	private File arquivoUpdateSql;
	
	String[] comandosEsperados = {
			"alter table candidato add column deficiencia character(1); ",
			"alter table candidato alter column deficiencia set not null; ",
			"update parametrosdosistema set appversao = '1.0.2.0'; ",
			"CREATE TABLE colaboradorresposta (    id bigint NOT NULL,    comentario text,    " +
					"valor integer NOT NULL,    pergunta_id bigint,    resposta_id bigint,    " +
					"colaboradorquestionario_id bigint,    areaorganizacional_id bigint); ",
			"update parametrosdosistema set appversao = '1.0.7.1'; "
								 }; 
	
	public void setUp() {
		arquivoUpdateSql = getSqlScript();
	}
	
	private File getSqlScript() {
		String path = this.getClass()
					.getResource(SCRIPT_UPDATE_TEST_SQL).getFile()
						.replace("\\", "/").replace("%20", " ");
		return new File(path);
	}

	public void testDeveriaLerComandosDoScriptSqlAposVersaoAtual() {
		
		String[] comandos = ScriptReader.getComandos(arquivoUpdateSql, "1.0.1.4");
		
		assertEquals("quantidade de comandos encontrados", 5, comandos.length);
		Assert.assertArrayEquals("comandos", comandosEsperados, comandos);
	}
	
	public void testDeveriaLancarExcecaoSeNaoForPossivelLerArquivoSql() {
		try {
			ScriptReader.getComandos(null, "1.0.1.4");
			fail("Deveria ter lançado RuntimeException.");
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
		}
	}
	
}
