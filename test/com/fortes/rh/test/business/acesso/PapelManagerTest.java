package com.fortes.rh.test.business.acesso;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import com.fortes.rh.business.acesso.PapelManagerImpl;
import com.fortes.rh.dao.acesso.PapelDao;
import com.fortes.rh.exception.NotConectAutenticationException;
import com.fortes.rh.exception.NotRegistredException;
import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.test.util.mockObjects.MockAutenticador;
import com.fortes.rh.util.Autenticador;

public class PapelManagerTest extends MockObjectTestCase
{
	private PapelManagerImpl papelManager;
	private Mock papelDao;

	protected void setUp(){

		papelManager = new PapelManagerImpl();

		papelDao = new Mock(PapelDao.class);
		papelManager.setDao((PapelDao) papelDao.proxy());
		
		Mockit.redefineMethods(Autenticador.class, MockAutenticador.class);
	}

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

		papelDao.expects(once()).method("findNotIn").will(returnValue(papeis));
		
		String[] permissoes = new String[]{"1","2","3"};

		assertNotNull(papelManager.getPerfilOrganizado(permissoes, null, null));
	}
	
	public void testGetPapeisPermitidos()
	{
		String[] permissoes = new String[]{"1","2","3"};
		
		Collection<Long> papeisPermitidosIds = new ArrayList<Long>();
		papeisPermitidosIds.add(1L);
		papeisPermitidosIds.add(2L);
		papeisPermitidosIds.add(3L);
		
		papelDao.expects(once()).method("findAll").will(returnValue(papeisPermitidosIds));
		
		try {
			assertEquals(papeisPermitidosIds, papelManager.getPapeisPermitidos());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
