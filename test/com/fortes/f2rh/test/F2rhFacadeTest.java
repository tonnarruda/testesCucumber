package com.fortes.f2rh.test;

import java.util.Collection;

import mockit.Mockit;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.jmock.MockObjectTestCase;

import com.fortes.f2rh.ConfigF2RH;
import com.fortes.f2rh.Curriculo;
import com.fortes.f2rh.F2rhDownloadFacade;
import com.fortes.f2rh.F2rhFacade;
import com.fortes.f2rh.F2rhFacadeImpl;
import com.fortes.f2rh.User;

public class F2rhFacadeTest extends MockObjectTestCase {

	private Curriculo expected;
	private Curriculo actual;
	F2rhFacade f2rhFacade;
	ConfigF2RH config;
	
//	String jsonList1 = "[{\"ddd_rh\":\"85\",\"endereco\":\"Rua Coronel Linhares 115/202\",\"cidade_rh\":\"Fortaleza\",\"nome\":\"Henrique de Albuquerque Vasconcelos Soares\",\"cep\":\"60170-240\",\"escolaridade_rh\":\"Superior Incompleto\",\"id\":15,\"user\":{\"name\":null,\"login\":\"010.178.063-06\",\"email\":\"henriquesoares@grupofortes.com.br\"},\"nacionalidade\":\"Brasileiro\",\"data_nascimento_rh\":\"27/12/1984\",\"bairro\":\"Meireles\",\"estado_civil\":\"1\",\"estado\":\"CE\",\"curso\":\"Letras\",\"telefone_rh\":\"87472023\",\"sexo\":\"M\",\"observacoes_complementares\":\"\",\"salario\":0.0,\"area_formacao\":\"2\"}]";
	String jsonList1 = "[{\"ddd_rh\":\"85\",\"endereco\":\"Rua Coronel Linhares 115/202\",\"cidade_rh\":\"Fortaleza\",\"nome\":\"Henrique de Albuquerque Vasconcelos Soares\",\"cep\":\"60170-240\",\"escolaridade_rh\":\"Superior Incompleto\",\"id\":15,\"user\":{\"name\":null,\"login\":\"010.178.063-06\",\"email\":\"henriquesoares@grupofortes.com.br\"},\"nacionalidade\":\"Brasileiro\",\"data_nascimento_rh\":\"27/12/1984\",\"bairro\":\"Meireles\",\"estado_civil\":\"1\",\"estado\":\"CE\",\"curso\":\"Letras\",\"telefone_rh\":\"87472023\",\"sexo\":\"M\",\"observacoes_complementares\":\"\",\"salario\":0.0,\"area_formacao\":\"2\",\"foto_file_name\":\"rponte.jpg\"}]";
	
	protected void setUp() {
		expected = new Curriculo();
		expected.setId(15);
		expected.setNome("Henrique de Albuquerque Vasconcelos Soares");
		expected.setEscolaridade_rh("Superior Incompleto");
		expected.setCidade_rh("Fortaleza");
		expected.setEstado("CE");
		expected.setUpdated_rh("25/10/2010");
		User user = new User();
		user.setLogin("01017806306");
		expected.setUser(user);
		
		f2rhFacade = new F2rhFacadeImpl();
		config = new ConfigF2RH();

	}
	
	public void testFindF2RHBasico() throws Exception {
		
		Mockit.redefineMethods(HttpClient.class, MockHttpClient.class);
		Mockit.redefineMethods(HttpMethodBase.class, MockHttpMethod.class);
		
		String[] consulta_basica = new String[]{"escolaridade=Superior Completo"};
		config.setConsulta(consulta_basica);
		config.setJson(jsonList1);
		String curriculos = f2rhFacade.find_f2rh(config);
		assertEquals(curriculos, config.getJson());
	}
	
	public void testFindF2RHAvancado() throws Exception {
		
		Mockit.redefineMethods(HttpClient.class, MockHttpClient.class);
		Mockit.redefineMethods(HttpMethodBase.class, MockHttpMethod.class);
		
		//id 5382
		String[] consulta_avancada = new String[]{"escolaridade=\"Superior Completo\""};
		config.setConsulta(consulta_avancada);
		config.setJson(jsonList1);
		String curriculos = f2rhFacade.find_f2rh(config);
		assertEquals(curriculos, config.getJson());
	}
	
	public void testObterCurriculos() {
		Mockit.redefineMethods(HttpMethodBase.class, MockHttpMethod.class);
		config.setJson(jsonList1);
		Collection<Curriculo> curriculos = f2rhFacade.obterCurriculos(config);
		assertEquals("Curriculo encontrados", curriculos.size(), 1);
	}
	
	public void testObterCurriculo() throws Exception {
		Mockit.redefineMethods(HttpMethodBase.class, MockHttpMethod.class);
		Mockit.redefineMethods(HttpClient.class, MockHttpClient.class);
		String[] consulta = new String[]{"curriculo[id][]=15"};
		config.setConsulta(consulta);
		String curriculos_s = f2rhFacade.find_f2rh(config);
		config.setJson(curriculos_s);
		
		Collection<Curriculo> curriculos = f2rhFacade.obterCurriculos(config);
		actual = encontrar(curriculos, expected);
		assertEquals(expected.getNome(), actual.getNome());
	}
	
	public void testBuscarCurriculos() throws Exception
	{
		Mockit.redefineMethods(HttpMethodBase.class, MockHttpMethod.class);
		Mockit.redefineMethods(HttpClient.class, MockHttpClient.class);
		String[] consulta = new String[]{"curriculo[id][]=15"};
		Collection<Curriculo> curriculos = f2rhFacade.buscarCurriculos(consulta);
		actual = encontrar(curriculos, expected);
		assertEquals(expected.getCpf(), actual.getCpf());
	}
	
	public void testBuscarCurriculosComFoto() throws Exception
	{
		Mockit.redefineMethods(HttpMethodBase.class, MockHttpMethod.class);
		Mockit.redefineMethods(HttpClient.class, MockHttpClient.class);
		String[] consulta = new String[]{"curriculo[id][]=15"};
		
		// mocka F2rhDownloadFacade
		F2rhDownloadFacade downloader = new MockF2rhDownloadFacade();
		f2rhFacade = new F2rhFacadeImpl(downloader);
		
		Collection<Curriculo> curriculos = f2rhFacade.buscarCurriculosComFoto(consulta);
		actual = encontrar(curriculos, expected);
		assertNotNull("deveria carregar foto", actual.getFoto_file_name());
		assertEquals("rponte.jpg", actual.getFoto_file_name());
		assertNotNull("foto", actual.getFoto());
	}

	public void testMontaIds() throws Exception
	{
		Mockit.redefineMethods(HttpMethodBase.class, MockHttpMethod.class);
		String[] curriculoIds = new String[]{"15", "156"};
		
		String[] retorno = f2rhFacade.montaIds(curriculoIds);

		//Ex.: new String[]{"curriculo[id][]=15",  "curriculo[id][]=1560"}
		assertEquals(2, retorno.length);
		assertEquals("curriculo[id][]=15", retorno[0]);
	}
	
	private Curriculo encontrar(Collection<Curriculo> curriculos, Curriculo expected) {
		actual = new Curriculo();
		for (Curriculo curriculo : curriculos) {
			if(curriculo.getCpf().equalsIgnoreCase(expected.getCpf())) {
				actual = curriculo;
			}
		}
		return actual;
	}
	
	public void testPrepareParams() {
		Mockit.redefineMethods(HttpMethodBase.class, MockHttpMethod.class);
		String[] consulta = new String[]{"curriculo[id][]=15"};
		NameValuePair[] pairs = f2rhFacade.prepareParams(consulta);
		NameValuePair[] pairsExpected = new NameValuePair[] {
				new NameValuePair("curriculo[id][]", "15"),
		};
		assertEquals(pairsExpected.length, pairs.length);
	}
	
	public void testPrepareParamsVazio() {
		Mockit.redefineMethods(HttpMethodBase.class, MockHttpMethod.class);
		String[] consulta = new String[]{"",  "curriculo[id][]=1560"};
		NameValuePair[] pairs = f2rhFacade.prepareParams(consulta);
		NameValuePair[] pairsExpected = new NameValuePair[] {
				new NameValuePair("curriculo[id][]", "1560")
		};
		assertEquals(pairsExpected.length, pairs.length);
	}
	
	public void testPrepareParamsVazioMasComIgual() {
		Mockit.redefineMethods(HttpMethodBase.class, MockHttpMethod.class);
		String[] consulta = new String[]{"=",  "curriculo[id][]=1560"};
		NameValuePair[] pairs = f2rhFacade.prepareParams(consulta);
		NameValuePair[] pairsExpected = new NameValuePair[] {
				new NameValuePair("curriculo[id][]", "1560")
		};
		assertEquals(pairsExpected.length, pairs.length);
	}
	
}
