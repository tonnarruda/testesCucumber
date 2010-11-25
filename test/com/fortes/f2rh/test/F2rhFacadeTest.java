package com.fortes.f2rh.test;

import java.util.Collection;

import mockit.Mockit;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
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
	
	String jsonList1 = "[{\"escolaridade_rh\":\"Superior Incompleto\",\"updated_at\":\"2010-10-25T16:24:27Z\",\"nome\":\"Henrique de Albuquerque Vasconcelos Soares\",\"cidade_rh\":\"Fortaleza\",\"id\":15,\"estado\":\"CE\"},{\"escolaridade_rh\":null,\"updated_at\":\"2009-10-27T13:06:24Z\",\"nome\":\"Ana Cristina Soares Silva\",\"cidade_rh\":null,\"id\":112,\"estado\":null}]";
	String jsonList2 = "[{\"escolaridade_rh\":null,\"updated_at\":\"2009-10-29T14:25:51Z\",\"nome\":\"Laysa Lourenco Feitosa \",\"cidade_rh\":null,\"id\":1560,\"estado\":null},{\"escolaridade_rh\":\"Superior Completo\",\"updated_at\":\"2010-10-28T10:54:52Z\",\"nome\":\"marcio elvis oliveira da silva\",\"cidade_rh\":null,\"id\":5382,\"estado\":\"\"}]";
	
	protected void setUp() {
		expected = new Curriculo();
		expected.setId(15);
		expected.setNome("Henrique de Albuquerque Vasconcelos Soares");
		expected.setEscolaridade_rh("Superior Incompleto");
		expected.setCidade_rh("Fortaleza");
		expected.setEstado("CE");
		expected.setUpdated_at("2010-10-25T16:24:27Z");
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
		config.setUrl("http://10.1.2.9:3000/rh_curriculos.json");
		config.setJson(jsonList1);
		String curriculos = f2rhFacade.find_f2rh(config);
		assertEquals(curriculos, config.getJson());
	}
	
	public void testFindF2RHAvancado() {
		
		Mockit.redefineMethods(HttpClient.class, MockHttpClient.class);
		Mockit.redefineMethods(HttpMethodBase.class, MockHttpMethod2.class);
		
		//id 5382
		String[] consulta_avancada = new String[]{"escolaridade=\"Superior Completo\""};
		config.setConsulta(consulta_avancada);
		config.setUrl("http://10.1.2.9:3000/rh_curriculos.json");
		config.setJson(jsonList2);
		String curriculos = f2rhFacade.find_f2rh(config);
		assertEquals(curriculos, config.getJson());
	}
	
	public void testObterCurriculos() {
		config.setJson(jsonList1);
		Collection<Curriculo> curriculos = f2rhFacade.obterCurriculos(config);
		assertEquals("Curriculo encontrados", curriculos.size(), 2);
	}
	
	public void testObterCurriculo() {
		//Mockit.redefineMethods(HttpClient.class, MockHttpClient.class);
		//Mockit.redefineMethods(HttpMethodBase.class, MockHttpMethod2.class);
		config.setUrl("http://10.1.2.9:3000/rh_curriculos.json");
		String[] consulta = new String[]{"curriculo[id][]=15",  "curriculo[id][]=1560"};
		config.setConsulta(consulta);
		String curriculos_s = f2rhFacade.find_f2rh(config);
		config.setJson(curriculos_s);
		
		Collection<Curriculo> curriculos = f2rhFacade.obterCurriculos(config);
		actual = encontrar(curriculos, expected);
		assertEquals("", expected, actual);
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
}
