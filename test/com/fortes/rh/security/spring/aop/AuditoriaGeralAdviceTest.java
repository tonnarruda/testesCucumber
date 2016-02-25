package com.fortes.rh.security.spring.aop;

import org.aopalliance.aop.Advice;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.aop.framework.ProxyFactory;

import com.fortes.rh.business.security.AuditoriaManager;
import com.fortes.rh.business.security.SecurityManager;

public class AuditoriaGeralAdviceTest extends MockObjectTestCase {

	SomeManager someManager;
	AuditoriaPointcut pointcut;
	Mock auditoriaManagerMock;
	
	private static final String DADOS_ESPERADOS = "[DADOS ANTERIORES]\n"
													+ "{\n"
//													+ "  \"dependenciasDesconsideradasNaRemocao\": [],\n" 
													+ "  \"id\": 29,\n"
													+ "  \"nome\": \"Minha Festa\"\n"
													+ "}";
	
	protected void setUp() throws Exception {
		super.setUp();
		pointcut = new AuditoriaPointcut();
		someManager = new SomeManagerImpl();
	}
	
	public void testDeveriaAuditarMetodoRemoveQuandoChamado() throws Throwable {
		SomeManager manager = this.criaUmProxyDoSpringParaAuditoria();
		manager.remove(29L);
	}
	
	/**
	 * Cria um proxy do Spring para Auditoria. Desta forma qualquer método do Manager 
	 * será interceptado pelo AuditoriaPointcut e se anotado com @Audita o mesmo será
	 * auditado.
	 */
	private SomeManager criaUmProxyDoSpringParaAuditoria() {
		// define advisor
		pointcut.setAdvice(this.mockaAdviceDeAuditoria()); // TODO: deveria mockar
		// cria proxy do manager
		ProxyFactory factory = new ProxyFactory();
		factory.setTarget(someManager);
		factory.addAdvisor(pointcut);
		factory.addInterface(SomeManager.class);
		// gera proxy
		SomeManager proxy = (SomeManager) factory.getProxy();
		return proxy;
	}
	/**
	 * Mocka Advice de Auditoria
	 */
	private Advice mockaAdviceDeAuditoria() {
		
		auditoriaManagerMock = new Mock(AuditoriaManager.class);
		auditoriaManagerMock.expects(once()).method("audita").with(eq("Cadastro de Bugigangas"), eq("Remoçao Customizada"), eq("Minha Festa"), eq(DADOS_ESPERADOS)).isVoid();
		
		AuditoriaGeralAdvice auditoriaGeralAdvice = new AuditoriaGeralAdvice();
		auditoriaGeralAdvice.setAuditoriaManager((AuditoriaManager) auditoriaManagerMock.proxy());
		auditoriaGeralAdvice.setSecurityManager(new SecurityManager() {
														public boolean hasLoggedUser() {
															return true;
														}}); // usuario sempre logado
		
		return auditoriaGeralAdvice;
	}
}
