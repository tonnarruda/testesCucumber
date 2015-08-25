package com.fortes.rh.test.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.OpcaoImportacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.model.sesmt.Epi;
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
	
	public void testGetAfastamentosFromCSV() throws Exception
	{
		String path = getPath();
		
		File file = new File(path + "testeImportacaoAfastamentos.csv");
		
		importacaoCSVUtil.importarCSV(file, OpcaoImportacao.AFASTAMENTOS_TRU);
		
		Collection<ColaboradorAfastamento> result = importacaoCSVUtil.getAfastamentos();
		
		assertEquals(10,result.size());
	}
	
	public void testGetAfastamentosFromCSVErroNoArquivo() throws Exception
	{
		String path = getPath();
		
		File file = new File(path + "arquivoCSVInvalido.csv");
		
		Exception exception = null;
		try {
			importacaoCSVUtil.importarCSV(file, OpcaoImportacao.AFASTAMENTOS_TRU);
		} catch (Exception e) {
			exception =e;
		}
		
		assertNotNull(exception);
		assertEquals("Não foram encontradas linhas válidas. Verifique o arquivo.", exception.getMessage());
	}
	
	public void testImportarCSVColaboradorDadosPessoais() throws Exception
	{
		String path = getPath();
		
		File file = new File(path + "testeImportacaoColaboradorDadosPessoais.csv");
		
		importacaoCSVUtil.importarCSV(file, OpcaoImportacao.COLABORADORES_DADOS_PESSOAIS);
		
		Collection<Colaborador> colaboradores = importacaoCSVUtil.getColaboradores();
		
		assertEquals(3,colaboradores.size());
		
		Colaborador colaborador3 = (Colaborador) colaboradores.toArray()[2];
		
		//3484;81439296391;RUA DA CONSOLAÇÃO;650;;660;1;NOVO PABUSSU;61600500;;87600570;87600570;7
		assertEquals("3484", colaborador3.getMatricula());
		assertEquals("81439296391", colaborador3.getPessoal().getCpf());
		assertEquals("RUA DA CONSOLAÇÃO", colaborador3.getEndereco().getLogradouro());
		assertEquals("07", colaborador3.getPessoal().getEscolaridade());
	}
	
	public void testImportarCSVColaboradorDadosPessoaisErroNoArquivo() throws Exception
	{
		String path = getPath();
		
		File file = new File(path + "arquivoCSVInvalido.csv");
		
		Exception exception = null;
		try {
			importacaoCSVUtil.importarCSV(file, OpcaoImportacao.COLABORADORES_DADOS_PESSOAIS);
		} catch (Exception e) {
			exception = e;
		}
	
		assertNotNull(exception);
		assertEquals("Não foram encontradas linhas válidas. Verifique o arquivo.", exception.getMessage());
	}
	
	public void testImportarCSVEPIs() throws Exception
	{
		String path = getPath();
		
		File file = new File(path + "testeImportacaoEPIs.csv");
		
		importacaoCSVUtil.importarCSV(file, OpcaoImportacao.EPIS);
		
		Collection<Epi> epis = importacaoCSVUtil.getEpis();
		
		assertEquals(3,epis.size());
		
		Epi epi = (Epi) epis.toArray()[2];
		
		assertEquals("256", epi.getCodigo());
		assertEquals("Me acuda", epi.getFabricante());
	}
	
	public void testImportarCSVEPIsErroNoArquivo() throws Exception
	{
		String path = getPath();
		
		File file = new File(path + "arquivoCSVInvalido.csv");
		
		Exception exception = null;
		try {
			importacaoCSVUtil.importarCSV(file, OpcaoImportacao.EPIS);
		} catch (Exception e) {
			exception = e;
		}
	
		assertNotNull(exception);
		assertEquals("Não foram encontradas linhas com dados de EPI válidos. Verifique o arquivo.", exception.getMessage());
	}

	private String getPath() {
		String resourcePath = "com/fortes/rh/test/util/arquivosCSVParaTestes/";
		
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
