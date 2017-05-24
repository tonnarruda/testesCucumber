package com.fortes.rh.test.web.action.geral;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.geral.AreaFormacaoManager;
import com.fortes.rh.model.geral.AreaFormacao;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.geral.AreaFormacaoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.AreaFormacaoListAction;

public class AreaFormacaoListActionTest {
	private AreaFormacaoListAction action;
	private AreaFormacaoManager manager;

	@Before
	public void setUp() throws Exception {
		action = new AreaFormacaoListAction();

		manager = mock(AreaFormacaoManager.class);
		action.setAreaFormacaoManager(manager);

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	@Test
	public void testExecute() throws Exception {
		assertEquals(action.execute(), "success");
	}

	@Test
	public void testList() throws Exception {
		AreaFormacao ai1 = AreaFormacaoFactory.getEntity();
		ai1.setNome("teste1");
		ai1.setId(1L);

		AreaFormacao ai2 = AreaFormacaoFactory.getEntity();
		ai2.setId(2L);
		ai2.setNome("teste2");

		Collection<AreaFormacao> areaFormacaos = new ArrayList<AreaFormacao>();
		areaFormacaos.add(ai1);
		areaFormacaos.add(ai2);

		AreaFormacao area1 = new AreaFormacao();
		area1.setNome("test");

		action.setAreaFormacaos(areaFormacaos);
		action.setAreaFormacao(area1);

		when(manager.getCount()).thenReturn(2);

		when(manager.findByFiltro(1, 15, area1)).thenReturn(areaFormacaos);

		assertEquals("success", action.list());
		assertEquals(2, action.getAreaFormacaos().size());
	}

	@Test
	public void testDelete() throws Exception {
		AreaFormacao ai1 = AreaFormacaoFactory.getEntity();
		ai1.setId(1L);
		action.setAreaFormacao(ai1);

		assertEquals("success", action.delete());
		assertFalse(action.getActionMessages().isEmpty());

		verify(manager, times(1)).remove(ai1.getId());
	}

	@Test
	public void testSetAreaFormacao() {
		AreaFormacao areaFormacao = AreaFormacaoFactory.getEntity();
		action.setAreaFormacao(areaFormacao);
		assertNotNull(action.getAreaFormacao());

	}

	@Test
	public void testGetAreaFormacao() {
		assertNotNull(action.getAreaFormacao());

	}

	@Test
	public void testGetCollectionAreaFormacao() {
		AreaFormacao areaFormacao = AreaFormacaoFactory.getEntity();
		Collection<AreaFormacao> areasFormacoes = Arrays.asList(areaFormacao, areaFormacao);
		action.setAreaFormacaos(areasFormacoes);
		assertEquals(2, action.getAreaFormacaos().size());

	}

}
