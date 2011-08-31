package com.fortes.rh.test.business.acesso;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import com.fortes.rh.business.acesso.PapelManagerImpl;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.dao.acesso.PapelDao;
import com.fortes.rh.model.acesso.Papel;

public class PapelManagerTest extends MockObjectTestCase
{
	private PapelManagerImpl papelManager;
	private Mock papelDao;
	private Mock parametrosDoSistemaManager;

	protected void setUp(){

		papelManager = new PapelManagerImpl();

		papelDao = new Mock(PapelDao.class);
		papelManager.setDao((PapelDao) papelDao.proxy());
		parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
		papelManager.setParametrosDoSistemaManager((ParametrosDoSistemaManager)parametrosDoSistemaManager.proxy());
	}

	public void testGetPerfilOrganizado(){

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

		papelDao.expects(once()).method("findAll").will(returnValue(papeis));
		String[] permissoes = new String[]{"1","2","3"};
		parametrosDoSistemaManager.expects(once()).method("getModulosDecodificados").will(returnValue(permissoes));

		assertNotNull(papelManager.getPerfilOrganizado(permissoes, false));
	}
	
	public void testGetPapeisPermitidos()
	{
		String[] permissoes = new String[]{"1","2","3"};
		
		Collection<Long> papeisPermitidosIds = new ArrayList<Long>();
		papeisPermitidosIds.add(1L);
		papeisPermitidosIds.add(2L);
		papeisPermitidosIds.add(3L);
		
		parametrosDoSistemaManager.expects(once()).method("getModulosDecodificados").will(returnValue(permissoes));
		
		assertEquals(papeisPermitidosIds, papelManager.getPapeisPermitidos());
	}
	
	public void testAtualizarPapeis()
	{
		Collection<Long> modulosJaExistentes = new ArrayList<Long>();
		modulosJaExistentes.add(1L);
		modulosJaExistentes.add(2L);
		
		Collection<Papel> novosPapeis = new ArrayList<Papel>();
		
		Papel papel1 = new Papel(); 
		papel1.setId(10L);
		papel1.setPapelMaeId(1L);
		novosPapeis.add(papel1);
		
		Papel papel2SemPermissao = new Papel(); 
		papel2SemPermissao.setId(11L);
		papel2SemPermissao.setPapelMaeId(33L);
		novosPapeis.add(papel2SemPermissao);
		
		Papel papel3 = new Papel(); 
		papel3.setId(12L);
		papel3.setPapelMaeId(2L);
		novosPapeis.add(papel3);
		
		parametrosDoSistemaManager.expects(once()).method("getModulosDecodificados").will(returnValue(new String[]{"1","2"}));
		
		papelDao.expects(once()).method("findPapeisAPartirDe").with(eq(10L)).will(returnValue(novosPapeis));
		
		parametrosDoSistemaManager.expects(once()).method("updateModulos").with(eq("1,2,10,12")).isVoid();
		parametrosDoSistemaManager.expects(once()).method("disablePapeisIds").isVoid();
		
		papelManager.atualizarPapeis(10L);
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
