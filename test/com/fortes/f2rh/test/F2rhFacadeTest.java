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
		
		f2rhFacade = new F2rhFacadeImpl();
		config = new ConfigF2RH();
		config.setJson(jsonList1);
		
	
		//Mockit.redefineMethods(GetMethod.class, MockHttpMethod.class);
		
		
		
	}
	
	public void testFindF2RHBasico() {
		
		Mockit.redefineMethods(HttpClient.class, MockHttpClient.class);
		
		Mockit.redefineMethods(HttpMethodBase.class, MockHttpMethod.class);
		
		String[] consulta_basica = new String[]{"escolaridade=\"Superior Completo\""};
		config.setConsulta(consulta_basica);
		String curriculos = f2rhFacade.find_f2rh(config);
		assertEquals(curriculos, config.getJson());
	}
	
	public void testFindF2RHAvancado() {
		
/*		Mockit.redefineMethods(GetMethod.class, new Object() {
			
			public String getResponseBodyAsString() throws IOException {
				String json = "[{\"escolaridade_rh\":null,\"updated_at\":\"2009-10-29T14:25:51Z\",\"nome\":\"Laysa Lourenco Feitosa \",\"cidade_rh\":null,\"id\":1560,\"estado\":null},{\"escolaridade_rh\":\"Superior Completo\",\"updated_at\":\"2010-10-28T10:54:52Z\",\"nome\":\"marcio elvis oliveira da silva\",\"cidade_rh\":null,\"id\":5382,\"estado\":\"\"}]";
				return json;
			}
			
		});*/
		
		//id 5382
		String[] consulta_avancada = new String[]{"escolaridade=\"Superior Completo\""};
		config.setConsulta(consulta_avancada);
		
		config.setJson(jsonList2);
		String curriculos = f2rhFacade.find_f2rh(config);
		assertEquals(curriculos, config.getJson());
	}
	
	public void testObterCurriculos() {
		Collection<Curriculo> curriculos = f2rhFacade.obterCurriculos(config);
		assertEquals("Curriculo encontrados", curriculos.size(), 2);
	}
	
	public void testObterCurriculo() {
		Collection<Curriculo> curriculos = f2rhFacade.obterCurriculos(config);
		actual = encontrar(curriculos, expected);
		assertEquals("", expected, actual);
	}

	private Curriculo encontrar(Collection<Curriculo> curriculos, Curriculo expected) {
		actual = null;
		for (Curriculo curriculo : curriculos) {
			if(curriculo.getCpf() == expected.getCpf() ) {
				actual = curriculo;
			}
		}
		return actual;
	}
}
