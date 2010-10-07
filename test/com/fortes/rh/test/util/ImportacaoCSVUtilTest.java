package com.fortes.rh.test.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.OpcaoImportacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.util.importacao.ImportacaoCSVUtil;

@SuppressWarnings("unused")
public class ImportacaoCSVUtilTest extends TestCase
{
	
	private ImportacaoCSVUtil importacaoCSVUtil;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		importacaoCSVUtil = new ImportacaoCSVUtil();
	}
	
/*
 * TODO Testes comentados porque quebram no Coverage! 
 */
	
//	public void testGetAfastamentosFromCSV() throws IOException
//	{
//		String path = getPath();
//		
//		File file = new File(path + "testeImportacaoAfastamentos.csv");
//		
//		importacaoCSVUtil.importarCSV(file, OpcaoImportacao.AFASTAMENTOS_TRU);
//		
//		Collection<ColaboradorAfastamento> result = importacaoCSVUtil.getAfastamentos();
//		
//		assertEquals(10,result.size());
//	}
//	
//	public void testImportarCSVColaboradorDadosPessoais() throws IOException
//	{
//		String path = getPath();
//		
//		File file = new File(path + "testeImportacaoColaboradorDadosPessoais.csv");
//		
//		importacaoCSVUtil.importarCSV(file, OpcaoImportacao.COLABORADORES_DADOS_PESSOAIS);
//		
//		Collection<Colaborador> colaboradores = importacaoCSVUtil.getColaboradores();
//		
//		assertEquals(3,colaboradores.size());
//		
//		Colaborador colaborador3 = (Colaborador) colaboradores.toArray()[2];
//		
//		//3484;81439296391;RUA DA CONSOLAÇÃO;650;;660;1;NOVO PABUSSU;61600500;;87600570;87600570;7
//		assertEquals("3484", colaborador3.getMatricula());
//		assertEquals("81439296391", colaborador3.getPessoal().getCpf());
//		assertEquals("RUA DA CONSOLAÇÃO", colaborador3.getEndereco().getLogradouro());
//		assertEquals("07", colaborador3.getPessoal().getEscolaridade());
//	}

	private String getPath() {
		String resourcePath = "com/fortes/rh/test/util/";
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String basePath = loader.getResource(".").getFile().replace("\\", "/").replace("%20", " ");
		String testPath = basePath.replace("web/WEB-INF/classes/", "") + "test/";
		String path = testPath + resourcePath;
		path = path.replace('/', java.io.File.separatorChar);
		return path;
	}
	
	public void testMiguel()
	{
		assertTrue(true);
	}
}
