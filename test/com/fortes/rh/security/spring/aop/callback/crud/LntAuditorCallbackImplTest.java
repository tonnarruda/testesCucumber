package com.fortes.rh.security.spring.aop.callback.crud;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoLntManager;
import com.fortes.rh.business.desenvolvimento.LntManager;
import com.fortes.rh.business.desenvolvimento.LntManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.dao.desenvolvimento.LntDao;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.security.spring.aop.MetodoInterceptadoImpl;
import com.fortes.rh.security.spring.aop.callback.LntAuditorCallbackImpl;
import com.fortes.rh.security.spring.aop.callback.crud.helper.MethodInvocationDefault;
import com.fortes.rh.test.factory.desenvolvimento.LntFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;

public class LntAuditorCallbackImplTest {

	private AuditorCallback callback;
	private LntManagerImpl lntManager = new LntManagerImpl();
	private LntDao lntDao;
	private EmpresaManager empresaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private ColaboradorTurmaManager colaboradorTurmaManager; 
	private CursoLntManager cursoLntManager; 
	private Lnt lntAtual;
	
	@Before
	public void setUp() {
		lntDao = mock(LntDao.class);
		lntManager.setDao(lntDao);
		
		empresaManager = mock(EmpresaManager.class);
		lntManager.setEmpresaManager(empresaManager);
		
		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		lntManager.setAreaOrganizacionalManager(areaOrganizacionalManager);
		
		colaboradorTurmaManager = mock(ColaboradorTurmaManager.class);
		cursoLntManager = mock(CursoLntManager.class);
		
		callback = new LntAuditorCallbackImpl();
		lntAtual = new Lnt();
	}
	
	@Test
	public void testSave() throws Throwable {
		lntAtual = LntFactory.getEntity(1L,"Lnt 01",DateUtil.criarDataMesAno(DateUtil.criarDataDiaMesAno("08/12/2016")),DateUtil.criarDataMesAno(DateUtil.criarDataDiaMesAno("08/12/2016")), null);
		
		MethodInvocation metodoSave = new MethodInvocationDefault<LntManager>("save", LntManager.class, new Object[]{lntAtual}, lntManager, null);
		Auditavel auditavel = callback.processa(new MetodoInterceptadoImpl(metodoSave));

		String dados = "[DADOS ATUALIZADOS]\n{\n  \"areasOrganizacionais\": [],\n  \"cursoLnts\": [],\n  \"dataFim\": \"08/12/2016 00:00:00\",\n  \"dataFinalizada\": \"\","
				+ "\n  \"dataInicio\": \"08/12/2016 00:00:00\",\n  \"descricao\": \"Lnt 01\",\n  \"empresas\": [],\n  \"finalizada\": false,\n  \"id\": 1\n}";
		
		assertEquals("Lnt", auditavel.getModulo());
		assertEquals("Inserção", auditavel.getOperacao());
		assertEquals("Lnt 01", auditavel.getChave());
		assertEquals(dados, auditavel.getDados());
	}	
	
	@Test
	public void testUpdate() throws Throwable {
		Lnt lntAnterior = LntFactory.getEntity(1L,"Lnt 01",DateUtil.criarDataMesAno(DateUtil.criarDataDiaMesAno("08/12/2016")),DateUtil.criarDataMesAno(DateUtil.criarDataDiaMesAno("08/12/2016")), null);
		lntAtual = LntFactory.getEntity(1L,"Lnt 01",DateUtil.criarDataMesAno(DateUtil.criarDataDiaMesAno("09/12/2016")),DateUtil.criarDataMesAno(DateUtil.criarDataDiaMesAno("10/12/2016")), null);
		
		when(lntManager.findEntidadeComAtributosSimplesById(lntAnterior.getId())).thenReturn(lntAnterior);
		when(areaOrganizacionalManager.findByLntIdComEmpresa(lntAnterior.getId(),new Long[]{})).thenReturn(null);
		when(empresaManager.findByLntId(lntAnterior.getId())).thenReturn(null);

		MethodInvocation metodoUpdate = new MethodInvocationDefault<LntManager>("update", LntManager.class, new Object[]{lntAtual}, lntManager, null);
		Auditavel auditavel = callback.processa(new MetodoInterceptadoImpl(metodoUpdate));
		
		String dados = "[DADOS ANTERIORES]\n{\n  \"areasOrganizacionais\": [],\n  \"cursoLnts\": [],\n  \"dataFim\": \"08/12/2016 00:00:00\",\n  \"dataFinalizada\": \"\","
				+ "\n  \"dataInicio\": \"08/12/2016 00:00:00\",\n  \"descricao\": \"Lnt 01\",\n  \"empresas\": [],\n  \"finalizada\": false,\n  \"id\": 1\n}\n\n"
				+ "[DADOS ATUALIZADOS]\n{\n  \"areasOrganizacionais\": [],\n  \"cursoLnts\": [],\n  \"dataFim\": \"10/12/2016 00:00:00\",\n  \"dataFinalizada\": \"\","
				+ "\n  \"dataInicio\": \"09/12/2016 00:00:00\",\n  \"descricao\": \"Lnt 01\",\n  \"empresas\": [],\n  \"finalizada\": false,\n  \"id\": 1\n}";

		assertEquals("Lnt", auditavel.getModulo());
		assertEquals("Atualização", auditavel.getOperacao());
		assertEquals("Lnt 01", auditavel.getChave());
		assertEquals(dados, auditavel.getDados());
	}
	
	@Test
	public void testRemoveComDependencias() throws Throwable {
		lntAtual = LntFactory.getEntity(1L,"Lnt 01",DateUtil.criarDataMesAno(DateUtil.criarDataDiaMesAno("08/12/2016")),DateUtil.criarDataMesAno(DateUtil.criarDataDiaMesAno("08/12/2016")), null);
		
		when(lntManager.findEntidadeComAtributosSimplesById(lntAtual.getId())).thenReturn(lntAtual);
		when(areaOrganizacionalManager.findByLntIdComEmpresa(lntAtual.getId(),new Long[]{})).thenReturn(null);
		when(empresaManager.findByLntId(lntAtual.getId())).thenReturn(null);
		
		MethodInvocation removeComDependencias = new MethodInvocationDefault<LntManager>("removeComDependencias", LntManager.class, 
				new Object[]{lntAtual.getId(), colaboradorTurmaManager, cursoLntManager}, lntManager, null);
		Auditavel auditavel = callback.processa(new MetodoInterceptadoImpl(removeComDependencias));

		String dados = "[DADOS ANTERIORES]\n{\n  \"areasOrganizacionais\": [],\n  \"cursoLnts\": [],\n  \"dataFim\": \"08/12/2016 00:00:00\",\n  \"dataFinalizada\": \"\","
				+ "\n  \"dataInicio\": \"08/12/2016 00:00:00\",\n  \"descricao\": \"Lnt 01\",\n  \"empresas\": [],\n  \"finalizada\": false,\n  \"id\": 1\n}";
		
		assertEquals("Lnt", auditavel.getModulo());
		assertEquals("Remoção", auditavel.getOperacao());
		assertEquals("Lnt 01", auditavel.getChave());
		assertEquals(dados, auditavel.getDados());
	}
	
	@Test
	public void testFinalizar() throws Throwable {
		lntAtual = LntFactory.getEntity(1L,"Lnt 01",DateUtil.criarDataMesAno(DateUtil.criarDataDiaMesAno("08/12/2016")),DateUtil.criarDataMesAno(DateUtil.criarDataDiaMesAno("10/12/2016")), DateUtil.criarDataMesAno(DateUtil.criarDataDiaMesAno("12/12/2016")));
		
		when(lntManager.findEntidadeComAtributosSimplesById(lntAtual.getId())).thenReturn(lntAtual);
		
		MethodInvocation finalizar = new MethodInvocationDefault<LntManager>("finalizar", LntManager.class, new Object[]{lntAtual.getId()}, lntManager, null);
		Auditavel auditavel = callback.processa(new MetodoInterceptadoImpl(finalizar));
		
		StringBuilder dados = new StringBuilder();
		dados.append("\nId: 1");
		dados.append("\nDescrição: Lnt 01");
		dados.append("\nData início: 08/12/2016");
		dados.append("\nData fim: 08/12/2016");
		dados.append("\nData em que foi finalizada: 12/12/2016");
		
		assertEquals("Lnt", auditavel.getModulo());
		assertEquals("Finalizar", auditavel.getOperacao());
		assertEquals("Finalizar LNT - Lnt 01", auditavel.getChave());
		assertEquals(dados.toString(), auditavel.getDados());
	}
	
	@Test
	public void testReabrir() throws Throwable {
		lntAtual = LntFactory.getEntity(1L,"Lnt 01",DateUtil.criarDataMesAno(DateUtil.criarDataDiaMesAno("08/12/2016")),DateUtil.criarDataMesAno(DateUtil.criarDataDiaMesAno("10/12/2016")), DateUtil.criarDataMesAno(DateUtil.criarDataDiaMesAno("12/12/2016")));
		
		when(lntManager.findEntidadeComAtributosSimplesById(lntAtual.getId())).thenReturn(lntAtual);
		
		MethodInvocation reabrir = new MethodInvocationDefault<LntManager>("reabrir", LntManager.class, new Object[]{lntAtual.getId()}, lntManager, null);
		Auditavel auditavel = callback.processa(new MetodoInterceptadoImpl(reabrir));
		
		StringBuilder dados = new StringBuilder();
		dados.append("[DADOS ANTERIORES]");
		dados.append("\n[");
		dados.append("\n  Descrição: Lnt 01");
		dados.append("\n  Data início: 08/12/2016");
		dados.append("\n  Data fim: 08/12/2016");
		dados.append("\n  Data em que foi finalizada: 12/12/2016");
		dados.append("\n]");
		dados.append("\n\n\n[DADOS ATUALIZADOS]");
		dados.append("\n[");
		dados.append("\n  Descrição: Lnt 01");
		dados.append("\n  Data início: 08/12/2016");
		dados.append("\n  Data fim: 08/12/2016");
		dados.append("\n\n  Esta LNT foi reaberta.");
		dados.append("\n]");
						
		assertEquals("Lnt", auditavel.getModulo());
		assertEquals("Reabrir", auditavel.getOperacao());
		assertEquals("Reabrir LNT - Lnt 01", auditavel.getChave());
		assertEquals(dados.toString(), auditavel.getDados());
	}
}
