package com.fortes.rh.test.business.sesmt;

import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;

import com.fortes.rh.business.sesmt.EngenheiroResponsavelManagerImpl;
import com.fortes.rh.dao.sesmt.EngenheiroResponsavelDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.util.DateUtil;

public class EngenheiroResponsavelManagerTest extends MockObjectTestCase
{
	private EngenheiroResponsavelManagerImpl engenheiroResponsavelManager = new EngenheiroResponsavelManagerImpl();
	private Mock engenheiroResponsavelDao = null;
	
	// Utilizado em testGetEngenheirosAteData
	private int contador = 0;

	protected void setUp() throws Exception
    {
        super.setUp();
        engenheiroResponsavelDao = new Mock(EngenheiroResponsavelDao.class);
        engenheiroResponsavelManager.setDao((EngenheiroResponsavelDao) engenheiroResponsavelDao.proxy());
    }

	public void testFindByIdProjection() throws Exception
	{
		EngenheiroResponsavel engenheiroResponsavel = new EngenheiroResponsavel();
		engenheiroResponsavel.setId(1L);

		engenheiroResponsavelDao.expects(once()).method("findByIdProjection").with(eq(engenheiroResponsavel.getId())).will(returnValue(engenheiroResponsavel));

		assertEquals(engenheiroResponsavel, engenheiroResponsavelManager.findByIdProjection(engenheiroResponsavel.getId()));
	}
	
	public void testGetEngenheirosAteData()
	{
		Date data_1 = DateUtil.criarDataMesAno(01, 01, 2013);
		Date data_2 = DateUtil.criarDataMesAno(01, 02, 2013);
		Date data_3 = DateUtil.criarDataMesAno(01, 03, 2013);
		Date data_4 = DateUtil.criarDataMesAno(01, 04, 2013);
		Date data_5 = DateUtil.criarDataMesAno(01, 04, 2013);

		boolean estaNaRelacao = true;
		boolean naoEstaNaRelacao = false;
		
		// Teste com desligamento e fim não nulos
		testaSeEngenheiroFazParteDoRelatorioPPP(data_1, data_1, data_1, data_1, data_1, estaNaRelacao);
		
		testaSeEngenheiroFazParteDoRelatorioPPP(data_3, data_1, data_5, data_2, data_4, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_3, data_1, data_4, data_2, data_5, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_4, data_1, data_3, data_2, data_5, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_5, data_1, data_3, data_2, data_4, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_4, data_1, data_5, data_2, data_3, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_5, data_1, data_4, data_2, data_3, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_2, data_1, data_5, data_3, data_4, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_2, data_1, data_4, data_3, data_5, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_2, data_1, data_3, data_4, data_5, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_5, data_1, data_2, data_3, data_4, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_4, data_1, data_2, data_3, data_5, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_5, data_1, data_2, data_3, data_4, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_3, data_2, data_4, data_1, data_5, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_3, data_2, data_5, data_1, data_4, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_4, data_2, data_3, data_1, data_5, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_5, data_2, data_3, data_1, data_4, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_4, data_2, data_5, data_1, data_3, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_5, data_2, data_4, data_1, data_3, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_2, data_3, data_4, data_1, data_5, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_2, data_3, data_5, data_1, data_4, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_2, data_4, data_5, data_1, data_3, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_5, data_3, data_4, data_1, data_2, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_4, data_3, data_5, data_1, data_2, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_3, data_4, data_5, data_1, data_2, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_1, data_2, data_5, data_3, data_4, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_1, data_2, data_4, data_3, data_5, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_1, data_2, data_3, data_4, data_5, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_1, data_3, data_5, data_2, data_4, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_1, data_3, data_4, data_2, data_5, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_1, data_4, data_5, data_2, data_3, naoEstaNaRelacao);

		// Teste com desligamento e fim nulos
		testaSeEngenheiroFazParteDoRelatorioPPP(data_1, data_1, null, data_1, null, estaNaRelacao);
		
		testaSeEngenheiroFazParteDoRelatorioPPP(data_3, data_1, null, data_2, null, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_2, data_1, null, data_3, null, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_3, data_2, null, data_1, null, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_2, data_3, null, data_1, null, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_1, data_2, null, data_3, null, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_1, data_3, null, data_2, null, naoEstaNaRelacao);
		
		// Teste com desligamento nulo
		testaSeEngenheiroFazParteDoRelatorioPPP(data_1, data_1, null, data_1, data_1, estaNaRelacao);
		
		testaSeEngenheiroFazParteDoRelatorioPPP(data_3, data_1, null, data_2, data_4, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_4, data_1, null, data_2, data_3, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_2, data_1, null, data_3, data_4, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_3, data_2, null, data_1, data_4, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_4, data_2, null, data_1, data_3, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_2, data_3, null, data_1, data_4, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_2, data_4, null, data_1, data_3, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_1, data_2, null, data_3, data_4, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_1, data_3, null, data_2, data_4, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_1, data_4, null, data_2, data_3, naoEstaNaRelacao);
		
		// Teste com fim nulo
		testaSeEngenheiroFazParteDoRelatorioPPP(data_1, data_1, data_1, data_1, null, estaNaRelacao);
		
		testaSeEngenheiroFazParteDoRelatorioPPP(data_3, data_1, data_4, data_2, null, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_4, data_1, data_3, data_2, null, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_2, data_1, data_4, data_3, null, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_2, data_1, data_3, data_4, null, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_3, data_1, data_2, data_4, null, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_4, data_1, data_2, data_3, null, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_3, data_2, data_4, data_1, null, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_4, data_2, data_3, data_1, null, estaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_2, data_3, data_4, data_1, null, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_1, data_2, data_4, data_3, null, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_1, data_2, data_3, data_4, null, naoEstaNaRelacao);
		testaSeEngenheiroFazParteDoRelatorioPPP(data_1, data_3, data_4, data_2, null, naoEstaNaRelacao);
	}

	private void testaSeEngenheiroFazParteDoRelatorioPPP(Date ppp, Date admissao, Date desligamento, Date inicio, Date fim, boolean estaNaRelacao)
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Colaborador colaboradorDoPPP = ColaboradorFactory.getEntity();
		colaboradorDoPPP.setEmpresa(empresa);
		
		EngenheiroResponsavel engenheiroResponsavel1 = new EngenheiroResponsavel();
		engenheiroResponsavel1.setInicio(inicio);
		engenheiroResponsavel1.setFim(fim);
		
		engenheiroResponsavelDao.expects(once()).method("findAllByEmpresa").with(eq(1L)).will(returnValue(Arrays.asList(new EngenheiroResponsavel[]{engenheiroResponsavel1})));
		
		colaboradorDoPPP.setDataAdmissao(admissao);
		colaboradorDoPPP.setDataDesligamento(desligamento);
		Collection<EngenheiroResponsavel> resultado = engenheiroResponsavelManager.getEngenheirosAteData(colaboradorDoPPP, ppp);
		
		assertEquals(++contador+"o teste.",estaNaRelacao, resultado.size() == 1);
	}
}