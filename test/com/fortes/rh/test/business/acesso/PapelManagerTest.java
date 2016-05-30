package com.fortes.rh.test.business.acesso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.acesso.PapelManagerImpl;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.dao.acesso.PapelDao;
import com.fortes.rh.exception.NotConectAutenticationException;
import com.fortes.rh.exception.NotRegistredException;
import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockAutenticador;
import com.fortes.rh.util.Autenticador;

public class PapelManagerTest
{
	private PapelManagerImpl papelManager = new PapelManagerImpl();
	private PapelDao papelDao;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	@Before
	public void setUp(){

		papelDao = mock(PapelDao.class);
		papelManager.setDao(papelDao);
		
		parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
		papelManager.setParametrosDoSistemaManager(parametrosDoSistemaManager);
		
	}

	@Test
	public void testGetPerfilOrganizado() throws NotConectAutenticationException, NotRegistredException{

		Collection<Papel> papeis = new ArrayList<Papel>();

		Papel p1 = new Papel();
		p1.setId(1L);

		Papel p2 = new Papel();
		p2.setId(2L);

		Papel p3 = new Papel();
		p3.setId(3L);
		p3.setPapelMae(p2);

		Papel p4 = new Papel();
		p4.setId(4L);
		p4.setPapelMae(p3);

		papeis.add(p1);
		papeis.add(p2);
		papeis.add(p3);
		papeis.add(p4);

		Collection<Long> ids = new ArrayList<Long>();
		
		when(papelDao.findNotIn(ids)).thenReturn(papeis);
		when(parametrosDoSistemaManager.findById(1L)).thenReturn(ParametrosDoSistemaFactory.getEntity());
		
		String[] permissoes = new String[]{"1","2","3"};

		assertNotNull(papelManager.getPerfilOrganizado(permissoes, null, null));
	}
	
	@Test
	public void testGetPapeisPermitidos()
	{
		Collection<Long> papeisPermitidosIds = Arrays.asList(357L, 361L, 353L, 365L, 373L, 382L, 75L, 37L, 495L);
		
		Papel p1 = new Papel();
		p1.setId(99999999999999999L);

		Papel p2 = new Papel();
		p2.setId(8888888888888888888L);
		p2.setPapelMae(p1);
		
		Collection<Papel> papeis = new ArrayList<Papel>();
		papeis.add(p1);
		papeis.add(p2);
		
		when(papelDao.findAll()).thenReturn(papeis);
		
		try {
			assertEquals(papeisPermitidosIds, papelManager.getPapeisPermitidos());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMontarArvore()
	{
		Collection<Papel> papeis = new ArrayList<Papel>();
		
		Papel papelVizinho = new Papel();
		papelVizinho.setNome("Vizinho");
		papelVizinho.setId(3L);
		papeis.add(papelVizinho);
		
		Papel papelAvo = new Papel(); 
		papelAvo.setNome("Avo");
		papelAvo.setId(2L);
		papeis.add(papelAvo);
		
		Papel papelMae = new Papel(); 
		papelMae.setNome("Mae");
		papelMae.setId(4L);
		papelMae.setPapelMae(papelAvo);
		papeis.add(papelMae);
		
		Papel papelTia = new Papel(); 
		papelTia.setNome("Tia");
		papelTia.setId(6L);
		papelTia.setPapelMae(papelAvo);
		papeis.add(papelTia);
		
		Papel papelFilho1 = new Papel();
		papelFilho1.setNome("Filho 1");
		papelFilho1.setId(1L);
		papelFilho1.setPapelMae(papelMae);
		papeis.add(papelFilho1);
		
		Papel papelFilho2 = new Papel();
		papelFilho2.setNome("Filho 2");
		papelFilho2.setId(5L);
		papelFilho2.setPapelMae(papelMae);
		papeis.add(papelFilho2);
		
		String arvore = papelManager.montarArvore(papeis);
		assertEquals("Vizinho\n\nAvo\n   Mae\n      Filho 1\n      Filho 2\n   Tia\n", arvore);
	}
}
