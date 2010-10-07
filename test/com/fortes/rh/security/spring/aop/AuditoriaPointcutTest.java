package com.fortes.rh.security.spring.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.jmock.MockObjectTestCase;
import org.springframework.aop.framework.ProxyFactory;

import com.fortes.rh.model.sesmt.Evento;

public class AuditoriaPointcutTest extends MockObjectTestCase {

	SomeManager managerImpl;
	AuditoriaPointcut pointcut;
	boolean metodoInterceptado;
	
	protected void setUp() throws Exception {
		super.setUp();
		managerImpl = new SomeManagerImpl();
		pointcut = new AuditoriaPointcut();
		metodoInterceptado = false;
	}

	public void testDeveriaSerInterceptado() throws SecurityException, NoSuchMethodException {
		
		Method saveMethod = managerImpl.getClass().getMethod("save", new Class[]{Evento.class}); // com Object.class não funciona
		boolean interceptado = pointcut.matches(saveMethod, SomeManager.class);
		
		assertTrue(interceptado);
	}
	
	public void testDeveriaNaoSerInterceptado() throws SecurityException, NoSuchMethodException {
		
		Method findAllMethod = managerImpl.getClass().getMethod("findAll"); // findAll nao possui @Audita
		boolean interceptado = pointcut.matches(findAllMethod, SomeManager.class);
		
		assertFalse(interceptado);
	}
	
	public void testDeveriaSerInterceptadoQuandoForProxyDoSpring() throws SecurityException, NoSuchMethodException {
		
		SomeManager proxy = this.criaUmProxyDoSpring();
		
		Method saveMethod = proxy.getClass().getMethod("save", new Class[]{Evento.class});
		boolean interceptado = pointcut.matches(saveMethod, SomeManager.class);
		
		assertTrue(interceptado);
	}
	
	public void testDeveriaNaoSerInterceptadoQuandoForProxyDoSpring() throws SecurityException, NoSuchMethodException {
		
		SomeManager proxy = this.criaUmProxyDoSpring();
		
		Method findAllMethod = proxy.getClass().getMethod("findAll"); // findAll nao possui @Audita
		boolean interceptado = pointcut.matches(findAllMethod, SomeManager.class);
		
		assertFalse(interceptado);
	}
	
	public void testDeveriaInterceptarMetodoSave() {
		SomeManager proxy = this.criaUmProxyDoSpringParaAuditoria(); // XXX: se usar SomeManagerImpl funciona!
		proxy.save(new Evento()); // executa metodo
		assertTrue(metodoInterceptado);
	}
	
	public void testDeveriaInterceptarMetodoUpdate() {
		SomeManager proxy = this.criaUmProxyDoSpringParaAuditoria();
		proxy.update(new Evento()); // executa metodo
		assertTrue(metodoInterceptado);
	}
	
	public void testDeveriaInterceptarMetodoRemove() {
		SomeManager proxy = this.criaUmProxyDoSpringParaAuditoria();
		proxy.remove(3L); // executa metodo
		assertTrue(metodoInterceptado);
	}
	
	public void testDeveriaNaoInterceptarMetodoFindAll() {
		SomeManager proxy = this.criaUmProxyDoSpringParaAuditoria();
		proxy.findAll(); // executa metodo
		assertFalse(metodoInterceptado);
	}
	
	/**
	 * Cria um proxy do Spring
	 */
	private SomeManager criaUmProxyDoSpring() {
		
		ProxyFactory factory = new ProxyFactory();
		factory.setTarget(managerImpl);
		factory.addInterface(SomeManager.class);
		
		SomeManager proxy = (SomeManager) factory.getProxy();
		return proxy;
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
		factory.setTarget(managerImpl);
		factory.addAdvisor(pointcut);
		factory.addInterface(SomeManager.class);
		// gera proxy
		SomeManager proxy = (SomeManager) factory.getProxy();
		return proxy;
	}
	/**
	 * Mocka Advice de Auditoria.
	 */
	private AuditoriaGeralAdvice mockaAdviceDeAuditoria() {
		return new AuditoriaGeralAdvice() {
			@Override
			public Object invoke(MethodInvocation invocation) throws Throwable {
				metodoInterceptado = true;
				System.out.println("Metodo Interceptado: " + invocation.getMethod());
				return invocation.proceed();
			}
		};
	}
	
}
