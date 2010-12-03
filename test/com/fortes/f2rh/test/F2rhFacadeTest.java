package com.fortes.f2rh.test;

import java.util.Collection;

import mockit.Mockit;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.jmock.MockObjectTestCase;

import com.fortes.f2rh.ConfigF2RH;
import com.fortes.f2rh.Curriculo;
import com.fortes.f2rh.F2rhFacade;
import com.fortes.f2rh.F2rhFacadeImpl;
import com.fortes.f2rh.User;

public class F2rhFacadeTest extends MockObjectTestCase {

	private Curriculo expected;
	private Curriculo actual;
	F2rhFacade f2rhFacade;
	ConfigF2RH config;
	
	String jsonList1 = "[{\"endereco\":\"Rua Coronel Linhares 115/202\",\"data_nascimento_rh\":\"27/12/1984\",\"cidade_rh\":\"Alta Floresta D`Oeste\",\"nome\":\"Henrique de Albuquerque Vasconcelos Soares\",\"cep\":\"60170-240\",\"escolaridade_rh\":\"Superior Incompleto\",\"user\":{\"name\":null,\"login\":\"010.178.063-06\",\"email\":\"henriquesoares@grupofortes.com.br\"},\"id\":15,\"created_rh\":\"23/10/2009\",\"nacionalidade\":\"Brasileiro\",\"bairro\":\"Meireles\",\"curriculo_telefones\":[{\"ddd\":\"85\",\"numero\":\"8747-2023\"}],\"updated_rh\":\"25/10/2010\",\"estado_civil\":\"1\",\"estado\":\"RO\",\"curso\":\"Letras\",\"sexo\":\"M\",\"observacoes_complementares\":\"\",\"salario\":0.0,\"area_formacao\":\"2\"}]";
	
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
	
	public void testFindF2RHBasico() {
		
		Mockit.redefineMethods(HttpClient.class, MockHttpClient.class);
		Mockit.redefineMethods(HttpMethodBase.class, MockHttpMethod.class);
		
		String[] consulta_basica = new String[]{"escolaridade=\"Superior Completo\""};
		config.setConsulta(consulta_basica);
		config.setJson(jsonList1);
		String curriculos = f2rhFacade.find_f2rh(config);
		assertEquals(curriculos, config.getJson());
	}
	
	public void testFindF2RHAvancado() {
		
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
	
	public void testObterCurriculo() {
		Mockit.redefineMethods(HttpMethodBase.class, MockHttpMethod.class);
		Mockit.redefineMethods(HttpClient.class, MockHttpClient.class);
		String[] consulta = new String[]{"curriculo[id][]=15"};
		config.setConsulta(consulta);
		String curriculos_s = f2rhFacade.find_f2rh(config);
		config.setJson(curriculos_s);
		
		Collection<Curriculo> curriculos = f2rhFacade.obterCurriculos(config);
		actual = encontrar(curriculos, expected);
		assertEquals(expected, actual);
	}
	
	public void testBuscarCurriculos()
	{
		Mockit.redefineMethods(HttpMethodBase.class, MockHttpMethod.class);
		Mockit.redefineMethods(HttpClient.class, MockHttpClient.class);
		String[] consulta = new String[]{"curriculo[id][]=15"};
		Collection<Curriculo> curriculos = f2rhFacade.buscarCurriculos(consulta);
		actual = encontrar(curriculos, expected);
		assertEquals(expected.getCpf(), actual.getCpf());
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
