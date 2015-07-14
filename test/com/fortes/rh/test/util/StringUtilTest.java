package com.fortes.rh.test.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import junit.framework.TestCase;

import com.fortes.rh.model.geral.ConfiguracaoPerformance;
import com.fortes.rh.model.geral.TurmaTipoDespesa;
import com.fortes.rh.util.StringUtil;

public class StringUtilTest extends TestCase
{

	/**
	 * O teste do método testGetHTML está no arquivo TestsNoIncludeAllUnitTest.java
	 */

	protected void setUp()
	{
	}

	public void testReplaceXml()
	{
		assertEquals("teste&amp;a&gt;b&lt;c&quot;d&apos;teste", StringUtil.replaceXml("teste&a>b<c\"d'teste"));
	}
	
	public void testRemoveBreak()
	{

		String texto = "Mas quando a branca estrela matutina.\n" + "Surgiu do espaço e as brisas forasteiras.\n" + "No verde leque das 'gentis' palmeiras.\r"
				+ "Foram cantar os hinos do arrebol.";

		String esperado = "Mas quando a branca estrela matutina.Surgiu do espaço e as brisas forasteiras.No verde leque das `gentis` palmeiras.Foram cantar os hinos do arrebol.";

		assertEquals(esperado, StringUtil.removeBreak(texto));
	}
	
	public void testJsonToJava() throws Exception
	{
		String json = "[{tipoDespesaId:2, despesa:545.56}, {tipoDespesaId:3, despesa:1044.56}]";
		Collection<TurmaTipoDespesa> tipoDespesas = (Collection<TurmaTipoDespesa>) StringUtil.simpleJSONtoArrayJava(json, TurmaTipoDespesa.class);

		assertEquals(2, tipoDespesas.size());
		
		TurmaTipoDespesa t1 = (TurmaTipoDespesa)tipoDespesas.toArray()[0];
		assertEquals(545.56, t1.getDespesa());
		assertEquals(new Long(2), t1.getTipoDespesaId());

		TurmaTipoDespesa t2 = (TurmaTipoDespesa)tipoDespesas.toArray()[1];
		assertEquals(1044.56, t2.getDespesa());
		assertEquals(new Long(3), t2.getTipoDespesaId());
		
		tipoDespesas = (Collection<TurmaTipoDespesa>) StringUtil.simpleJSONtoArrayJava("[]", TurmaTipoDespesa.class);
		assertEquals(0, tipoDespesas.size());

		Exception exception = null;
		try {
			tipoDespesas = (Collection<TurmaTipoDespesa>) StringUtil.simpleJSONtoArrayJava("", TurmaTipoDespesa.class);			
		} catch (Exception e) {
			exception = e;
		}
		assertNotNull(exception);
	}
	
	public void testJsonToJavaException() throws Exception
	{
		
		Exception exception = null;
		try {
			Collection<TurmaTipoDespesa> tipoDespesas = (Collection<TurmaTipoDespesa>) StringUtil.simpleJSONtoArrayJava("", TurmaTipoDespesa.class);			
		} catch (Exception e) {
			exception = e;
		}
		
		assertNotNull(exception);
	}
	
	public void testToJson() 
	{
		ConfiguracaoPerformance config1 = new ConfiguracaoPerformance(1L, "1", 2, true);
		ConfiguracaoPerformance config2 = new ConfiguracaoPerformance(2L, "2", 1, false);
		
		Collection<ConfiguracaoPerformance> configuracaoPerformances = new ArrayList<ConfiguracaoPerformance>();
		configuracaoPerformances.add(config1);
		configuracaoPerformances.add(config2);
		
		assertEquals("[{\"aberta\":true,\"caixa\":\"1\",\"dependenciasDesconsideradasNaRemocao\":[],\"id\":0,\"ordem\":2},{\"aberta\":false,\"caixa\":\"2\",\"dependenciasDesconsideradasNaRemocao\":[],\"id\":0,\"ordem\":1}]", StringUtil.toJSON(configuracaoPerformances, new String[]{"usuario"}));
	}
	
	public void testRemoveAspas()
	{
		
		String texto = "Mas quando a 'branca '";
		
		String esperado = "Mas quando a `branca `";
		
		assertEquals(esperado, StringUtil.removeAspas(texto));
	}

	public void testCamelCaseToSnakeCase()
	{
		assertEquals("faixa Salarial CArgo", StringUtil.camelCaseToSnakeCase("faixaSalarialCArgo"));
		assertEquals("faixa Salarial CArgo", StringUtil.camelCaseToSnakeCase("faixaSalarial CArgo"));
		assertEquals("", StringUtil.camelCaseToSnakeCase(""));
		assertEquals(null, StringUtil.camelCaseToSnakeCase(null));
	}
	
	public void testSubStr()
	{
		String texto = "Mas quando tamanho";//length = 20
		assertEquals("Mas quando tamanho", StringUtil.subStr(texto, 20));
		assertEquals(null, StringUtil.subStr(null, 20));
		assertEquals("Mas q", StringUtil.subStr(texto, 5));
	}

	public void testSubStrSuffix()
	{
		String texto = "Mas quando tamanho";//length = 20
		assertEquals("Mas quando tamanho", StringUtil.subStr(texto, 20, "..."));
		assertEquals(null, StringUtil.subStr(null, 20, "..."));
		assertEquals("Mas q...", StringUtil.subStr(texto, 5, "..."));
	}
	
	public void test() {
		
	}
	
	public void testMontaTokenF2rh()
	{
		Calendar calendar = new GregorianCalendar();
		assertEquals("42H"+ (calendar.get(GregorianCalendar.MONTH) + 1) +"0t3" + calendar.get(GregorianCalendar.DAY_OF_MONTH), StringUtil.montaTokenF2rh("Henriqué de Albuquerque Vasconcelos Soares"));
	}
	
	public void testStringToLong()
	{

		String[] strs = new String[] { "1", "2", "3", "4", "5" };
		Long[] longs = StringUtil.stringToLong(strs);

		assertTrue("Test 1", longs[0].equals(1L));
		assertTrue("Test 2", longs[1].equals(2L));
		assertTrue("Test 3", longs[2].equals(3L));
		assertTrue("Test 4", longs[3].equals(4L));
		assertTrue("Test 5", longs[4].equals(5L));

		strs = new String[0];

		assertTrue("Test 6", (StringUtil.stringToLong(strs)).length == 0);
	}

	public void testConverteArrayToString()
	{
		String[] array = new String[] { "1", "2" };

		assertEquals("1,2", StringUtil.converteArrayToString(array));

		assertEquals("", StringUtil.converteArrayToString(null));
	}
	
	public void testConverteCollectionToArrayString()
	{
		Collection<String> valores = new ArrayList<String>();
		valores.add("1");
		valores.add("2");
		
		assertNull(StringUtil.converteCollectionToArrayString(null));

		String[] retorno = StringUtil.converteCollectionToArrayString(valores);
		assertEquals(2, retorno.length);
		assertEquals("1", retorno[0]);
		assertEquals("2", retorno[1]);
		
	}
	public void testConverteCollectionLongToArrayString()
	{
		Collection<Long> valores = new ArrayList<Long>();
		valores.add(1L);
		valores.add(2L);
		
		String retorno = StringUtil.converteCollectionLongToString(valores);
		assertEquals("1,2", retorno);
		
	}
	
	public void testCriarMascaraTelefone()
	{
		assertEquals("teste 1", "3241-5566", StringUtil.criarMascaraTelefone("32415566"));
		assertEquals("teste 2", "324-1556", StringUtil.criarMascaraTelefone("3241556"));
		assertEquals("teste 3", null, StringUtil.criarMascaraTelefone(null));
	}
	
	public void testConverteCollectionToString()
	{
		Collection<String> colecao = new ArrayList<String>();
		colecao.add("1");
		colecao.add("2");
		assertEquals("1,2", StringUtil.converteCollectionToString(colecao));
		
		assertEquals("", StringUtil.converteCollectionToString(null));
	}
	
	public void testConverteCollectionToStringComAspas()
	{
		Collection<String> colecao = new ArrayList<String>();
		colecao.add("1");
		colecao.add("2");
		
		assertEquals("\"1\",\"2\"", StringUtil.converteCollectionToStringComAspas(colecao));
		
		assertEquals("", StringUtil.converteCollectionToStringComAspas(null));
	}

	public void testGetSenhaRandom()
	{

		String str1 = StringUtil.getSenhaRandom(8);
		assertTrue("Test 1", Character.isDigit(str1.charAt(0)));
		assertTrue("Test 2", Character.isLetter(str1.charAt(1)));
		assertTrue("Test 3", Character.isDigit(str1.charAt(2)));
		assertTrue("Test 4", Character.isLetter(str1.charAt(3)));
		assertTrue("Test 5", Character.isDigit(str1.charAt(4)));
		assertTrue("Test 6", Character.isLetter(str1.charAt(5)));
		assertTrue("Test 7", Character.isDigit(str1.charAt(6)));
		assertTrue("Test 8", Character.isLetter(str1.charAt(7)));
	}

	public void testLetraByNumero()
	{
		assertEquals("Test 1", "a", StringUtil.letraByNumero(1));
		assertEquals("Test 2", "z", StringUtil.letraByNumero(26));
		assertEquals("Test 3", "-", StringUtil.letraByNumero(0));
		assertEquals("Test 4", "-", StringUtil.letraByNumero(27));
	}

	public void testMudaExtensaoFile()
	{
		assertEquals("Test 1", "test.pdf", StringUtil.mudaExtensaoFile("test.txt", ".pdf"));
	}

	public void testConverteStringToInt()
	{
		assertEquals("Test 1", 1000, StringUtil.converteStringToInt("1000"));
		assertEquals("Test 2", 0, StringUtil.converteStringToInt("1000A"));
	}

	public void testRemoveMascara()
	{
		assertEquals("Test 1", "10121978", StringUtil.removeMascara("10/12/1978"));
		assertEquals("Test 2", "10121978", StringUtil.removeMascara("10-12-1978"));
		assertEquals("Test 3", "10121978", StringUtil.removeMascara("10.12.1978"));
		assertEquals("Test 4", "", StringUtil.removeMascara(null));
		assertEquals("Test 4", "", StringUtil.removeMascara(""));
	}

	public void testRemoveApostrofo()
	{
		assertEquals("Joana D\\'arc", StringUtil.removeApostrofo("Joana D'arc"));
	}

	public void testCriarMascaraCpf()
	{
		assertEquals("340.438.643-17",StringUtil.criarMascaraCpf("34043864317"));
		assertEquals("608.215.086-44",StringUtil.criarMascaraCpf("60821508644"));
		assertEquals("",StringUtil.criarMascaraCpf(""));
	}

	public void testDestacarExpressoesApresentacao()
	{
		assertEquals("A <span class='xz'>palavra</span> chave", StringUtil.destacarExpressoesApresentacao("A palavra chave", new String[]{"palavra"}));
	}

	public void testEncodeString()
	{
		StringUtil.encodeString("teste");
	}

	public void testDecodeString()
	{
		StringUtil.decodeString("teste");
	}

	public void testDecodeStringException()
	{
		Exception excep = null;
		try
		{
			StringUtil.decodeString(null);
		}
		catch (Exception e)
		{
			excep = e;
		}

		assertNotNull(excep);

	}

    public void testRetiraAcento()
    {
    	assertEquals("AcaO E", StringUtil.retiraAcento("AçãO É"));
    	assertEquals("", StringUtil.retiraAcento(null));
    	assertEquals("", StringUtil.retiraAcento(""));
    }

    public void testGetNumeroExtensoData()
    {
    	assertEquals("vinte e três",StringUtil.getNumeroExtensoData(23));
    	assertEquals("trinta e um",StringUtil.getNumeroExtensoData(31));
    }
    
    public void testFormataCnpj()
    {
    	String cnpj = "04583292";
		String complementoCnpj = "0001";
		
		String cnpjFormatado = StringUtil.formataCnpj(cnpj, complementoCnpj);
		
		assertEquals("04.583.292/0001-93", cnpjFormatado);
    }
    
    public void testFormataCnpjIncompleto()
    {
    	String cnpj = "04583292";
    	String complementoCnpj = null;
    	
    	assertEquals("04583292", StringUtil.formataCnpj(cnpj, complementoCnpj));
    	assertEquals(null, StringUtil.formataCnpj(null, complementoCnpj));
    }
    
    public void testFormataCep()
    {
    	String cep = "60181225";
    	
    	assertEquals("60.181-225", StringUtil.formataCep(cep));
    }
    
    public void testFormataCepIncompleto()
    {
    	String cep = "60000";
    	
    	assertEquals("60.000-000", StringUtil.formataCep(cep));
    }
    public void testFormataCepBranco()
    {
    	assertEquals("", StringUtil.formataCep(""));
    }
    
    public void testFormataCodigoAC()
    {
    	assertEquals("000001", StringUtil.formataCodigoAC("1"));
    	assertEquals("000333", StringUtil.formataCodigoAC("333"));
    	assertEquals("001403", StringUtil.formataCodigoAC("1403"));
    	assertEquals("", StringUtil.formataCodigoAC(null));
    	assertEquals("", StringUtil.formataCodigoAC("1nv4l1d c0d3"));
    }
    
    public void testEqualsIgnoreCaseAcento()
    {
    	assertTrue(StringUtil.equalsIgnoreCaseAcento("João", "Joao"));
    	assertTrue(StringUtil.equalsIgnoreCaseAcento("Cação D'Avila", "cacao d'avila"));
    	assertTrue(StringUtil.equalsIgnoreCaseAcento("José Mané", "Jósê Mane"));
    	assertTrue(StringUtil.equalsIgnoreCaseAcento("Escobar", "ESCOBAR"));
    	assertFalse(StringUtil.equalsIgnoreCaseAcento("Escobar", "ESCOBARr"));
    }
    
    public void testGetSomenteNumero()
    {
    	Collection<String> retorno = StringUtil.getSomenteNumero("There are more than -2 and less than 12 numbers here");
    	
    	assertEquals(2, retorno.size());
    	assertEquals("-2", (String) retorno.toArray()[0]);
    	assertEquals("12", (String) retorno.toArray()[1]);
    }
}
