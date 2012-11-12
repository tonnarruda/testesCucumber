package com.fortes.rh.test.business.captacao;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.captacao.AnuncioManagerImpl;
import com.fortes.rh.business.captacao.EmpresaBdsManager;
import com.fortes.rh.dao.captacao.AnuncioDao;
import com.fortes.rh.model.captacao.Anuncio;
import com.fortes.rh.model.captacao.EmpresaBds;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;

public class AnuncioManagerTest extends MockObjectTestCase
{
	Mock empresaBdsManager;
	Mock anuncioDao;
	AnuncioManagerImpl anuncioManager;

	protected void setUp() throws Exception
	{
		anuncioManager = new AnuncioManagerImpl();

		empresaBdsManager = new Mock(EmpresaBdsManager.class);
		anuncioManager.setEmpresaBdsManager((EmpresaBdsManager) empresaBdsManager.proxy());

		anuncioDao = new Mock(AnuncioDao.class);
		anuncioManager.setDao((AnuncioDao) anuncioDao.proxy());
	}

	public void testGetEmpresasCheck() throws Exception
	{

		Empresa empresa = new Empresa();
		empresa.setId(1L);

		empresaBdsManager.expects(once()).method("findToList").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(new ArrayList<EmpresaBds>()));

		anuncioManager.getEmpresasCheck(empresa.getId());
	}

	public void testRemoveBySolicitacao()
	{
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setId(1L);

		anuncioDao.expects(once()).method("removeBySolicitacao").with(eq(solicitacao.getId()));

		anuncioManager.removeBySolicitacao(solicitacao.getId());
	}

	public void testMontaEmails() throws Exception
	{

		Collection<EmpresaBds> emps = new ArrayList<EmpresaBds>();

		EmpresaBds eb1 = new EmpresaBds();
		eb1.setEmail("1@2.com");

		EmpresaBds eb2 = new EmpresaBds();
		eb2.setEmail("2@2.com");

		emps.add(eb1);
		emps.add(eb2);

		empresaBdsManager.expects(once()).method("findAllProjection").with(ANYTHING).will(returnValue(emps));

		String[] mails = anuncioManager.montaEmails("emailAvulso", new String[] { "1", "2" });

		assertEquals("Test 1", "1@2.com", mails[0]);
		assertEquals("Test 2", "2@2.com", mails[1]);
	}

	public void testFindAnunciosModuloExterno() throws Exception
	{

		Collection<Anuncio> anuncios = new ArrayList<Anuncio>();

		anuncioDao.expects(once()).method("findAnunciosModuloExterno").with(eq(1L), eq(null)).will(returnValue(anuncios));

		assertEquals(anuncios, anuncioManager.findAnunciosModuloExterno(1L, null));
	}
	
	public void testFindByIdProjection() throws Exception
	{
		Anuncio anuncio = new Anuncio();
		anuncio.setId(1L);
		
		anuncioDao.expects(once()).method("findByIdProjection").with(eq(anuncio.getId())).will(returnValue(anuncio));
		
		assertEquals(anuncio, anuncioManager.findByIdProjection(anuncio.getId()));
	}
	
	public void testFindBySolicitacao() throws Exception
	{
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setId(1L);
			
		Anuncio anuncio = new Anuncio();
		anuncio.setSolicitacao(solicitacao);
		anuncio.setId(1L);
		
		anuncioDao.expects(once()).method("findBySolicitacao").with(eq(solicitacao.getId())).will(returnValue(anuncio));
		
		assertEquals(anuncio, anuncioManager.findBySolicitacao(solicitacao.getId()));
	}
}
