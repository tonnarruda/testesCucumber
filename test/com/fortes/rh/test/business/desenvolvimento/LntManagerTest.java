package com.fortes.rh.test.business.desenvolvimento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoLntManager;
import com.fortes.rh.business.desenvolvimento.LntManagerImpl;
import com.fortes.rh.business.desenvolvimento.ParticipanteCursoLntManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.dao.desenvolvimento.LntDao;
import com.fortes.rh.model.desenvolvimento.CursoLnt;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.model.dicionario.StatusLnt;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoLntFactory;
import com.fortes.rh.test.factory.desenvolvimento.LntFactory;
import com.fortes.rh.test.factory.desenvolvimento.ParticipanteCursoLntFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSpringUtilJUnit4;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.SpringUtil;

public class LntManagerTest
{
	private LntManagerImpl lntManager = new LntManagerImpl();
	private LntDao lntDao;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private EmpresaManager empresaManager;
	private ParticipanteCursoLntManager participanteCursoLntManager;
	
	@Before
	public void setUp() throws Exception
    {
        lntDao = mock(LntDao.class);
        lntManager.setDao(lntDao);
        
        gerenciadorComunicacaoManager = mock(GerenciadorComunicacaoManager.class);
        MockSpringUtilJUnit4.mocks.put("gerenciadorComunicacaoManager", gerenciadorComunicacaoManager);
        
        empresaManager = mock(EmpresaManager.class);
        lntManager.setEmpresaManager(empresaManager);
        
        participanteCursoLntManager = mock(ParticipanteCursoLntManager.class);
        lntManager.setParticipanteCursoLntManager(participanteCursoLntManager);
        
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtilJUnit4.class);
        Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
    }
	
	@Test
	public void testFindAllLnt()
	{
		Collection<Lnt> lnts = LntFactory.getCollection(1L);

		when(lntDao.findAllLnt(anyString(), anyChar(), anyInt(), anyInt())).thenReturn(lnts);
		assertEquals(lnts, lntManager.findAllLnt(null, StatusLnt.TODOS, null, 0, 15));
	}
	
	@Test
	public void testGetCountFindAllLnt()
	{
		Collection<Lnt> lnts = LntFactory.getCollection(1L);

		when(lntDao.findAllLnt(anyString(), anyChar(), eq(0), eq(0))).thenReturn(lnts);
		assertEquals(new Integer(lnts.size()), lntManager.getCountFindAllLnt(null, StatusLnt.TODOS, null));
	}
	
	@Test
	public void testFindLntEmAndamentoComDataInicioIgualAAtual()
	{
		when(lntDao.findLntsNaoFinalizadas(null)).thenReturn(null);
		assertNull(lntDao.findLntsNaoFinalizadas(null));
	}
	
	@Test
	public void testFinalizar() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		Lnt lnt = LntFactory.getEntity(1L);
		
		when(lntManager.findById(lnt.getId())).thenReturn(lnt);
		when(empresaManager.findById(empresa.getId())).thenReturn(empresa);
		
		lntManager.finalizar(lnt.getId(), empresa.getId());
		verify(lntDao).finalizar(lnt.getId());
	}
	
	@Test
	public void testRemoverComDependencia() throws Exception
	{
		Lnt lnt = LntFactory.getEntity(1L);
		
		Collection<ParticipanteCursoLnt> participantesCursoLnt = new ArrayList<ParticipanteCursoLnt>();
		participantesCursoLnt.add(ParticipanteCursoLntFactory.getEntity(1L));
		
		Collection<CursoLnt> cursosLnt = new ArrayList<CursoLnt>();
		cursosLnt.add(CursoLntFactory.getEntity(1L));
		
		ColaboradorTurmaManager colaboradorTurmaManager = mock(ColaboradorTurmaManager.class);
		CursoLntManager cursoLntManager = mock(CursoLntManager.class);
		
		when(participanteCursoLntManager.findByLnt(lnt.getId())).thenReturn(participantesCursoLnt);
		when(cursoLntManager.findByLntId(lnt.getId())).thenReturn(cursosLnt);
		
		lntManager.removeComDependencias(lnt.getId(), colaboradorTurmaManager, cursoLntManager);
		verify(lntDao).remove(lnt.getId());
	}
}
