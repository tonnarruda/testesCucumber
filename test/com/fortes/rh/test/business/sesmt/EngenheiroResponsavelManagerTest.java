package com.fortes.rh.test.business.sesmt;

import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;

import com.fortes.rh.business.sesmt.EngenheiroResponsavelManagerImpl;
import com.fortes.rh.dao.sesmt.EngenheiroResponsavelDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
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
	
	/*
	 * Este teste contempla apenas as regras individuais, sempre heverá 1(Um) ou 0(Zero) engenheiro
	 * que fará parte dp PPP.
	 */
	public void testGetEngenheirosAteDataComUmOuZeroEngenheiro()
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

	public void testGetEngenheirosAteDataComVariosEngenheiros()
	{
		Date admissao = DateUtil.criarDataMesAno(01, 01, 2013);
		Date desligamento = DateUtil.criarDataMesAno(01, 04, 2013);

		Date inicio_1 = DateUtil.criarDataMesAno(01, 02, 2013);
		Date inicio_2 = DateUtil.criarDataMesAno(10, 02, 2013);
		Date fim = DateUtil.criarDataMesAno(01, 03, 2013);
		
		Date dataPPP = DateUtil.criarDataMesAno(05, 02, 2013);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(null, null, estabelecimento);
		
		Colaborador colaboradorDoPPP = ColaboradorFactory.getEntity();
		colaboradorDoPPP.setEmpresa(empresa);
		colaboradorDoPPP.setDataAdmissao(admissao);
		colaboradorDoPPP.setDataDesligamento(desligamento);
		colaboradorDoPPP.setHistoricoColaborador(historicoColaborador);
		
		EngenheiroResponsavel engenheiroResponsavel1 = new EngenheiroResponsavel();
		engenheiroResponsavel1.setId(1L);
		engenheiroResponsavel1.setInicio(inicio_1);
		engenheiroResponsavel1.setFim(fim);
		
		EngenheiroResponsavel engenheiroResponsavel2 = new EngenheiroResponsavel();
		engenheiroResponsavel2.setId(2L);
		engenheiroResponsavel2.setInicio(inicio_1);
		engenheiroResponsavel2.setFim(fim);
		
		EngenheiroResponsavel engenheiroResponsavel3 = new EngenheiroResponsavel();
		engenheiroResponsavel3.setId(3L);
		engenheiroResponsavel3.setInicio(inicio_1);
		engenheiroResponsavel3.setFim(null);
		
		EngenheiroResponsavel engenheiroResponsavel4 = new EngenheiroResponsavel();
		engenheiroResponsavel4.setId(4L);
		engenheiroResponsavel4.setInicio(inicio_2);
		engenheiroResponsavel4.setFim(fim);
		
		EngenheiroResponsavel engenheiroResponsavel5 = new EngenheiroResponsavel();
		engenheiroResponsavel5.setId(5L);
		engenheiroResponsavel5.setInicio(inicio_2);
		engenheiroResponsavel5.setFim(fim);
		
		EngenheiroResponsavel[] engenheirosResponsaveis = new EngenheiroResponsavel[]{engenheiroResponsavel1, engenheiroResponsavel2, engenheiroResponsavel3, engenheiroResponsavel4, engenheiroResponsavel5};
		engenheiroResponsavelDao.expects(once()).method("findResponsaveisPorEstabelecimento").with(eq(empresa.getId()), eq(estabelecimento.getId())).will(returnValue(Arrays.asList(engenheirosResponsaveis)));
		
		Collection<EngenheiroResponsavel> resultado = engenheiroResponsavelManager.findResponsaveisPorEstabelecimento(colaboradorDoPPP, dataPPP);
		
		assertEquals(3, resultado.size());
		assertEquals(engenheiroResponsavel1.getId(), ((EngenheiroResponsavel)resultado.toArray()[0]).getId());
		assertEquals(engenheiroResponsavel2.getId(), ((EngenheiroResponsavel)resultado.toArray()[1]).getId());
		assertEquals(engenheiroResponsavel3.getId(), ((EngenheiroResponsavel)resultado.toArray()[2]).getId());
	}
	
	private void testaSeEngenheiroFazParteDoRelatorioPPP(Date ppp, Date admissao, Date desligamento, Date inicio, Date fim, boolean estaNaRelacao)
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(null, null, estabelecimento);

		Colaborador colaboradorDoPPP = ColaboradorFactory.getEntity();
		colaboradorDoPPP.setEmpresa(empresa);
		colaboradorDoPPP.setHistoricoColaborador(historicoColaborador);
		
		EngenheiroResponsavel engenheiroResponsavel1 = new EngenheiroResponsavel();
		engenheiroResponsavel1.setInicio(inicio);
		engenheiroResponsavel1.setFim(fim);
		
		engenheiroResponsavelDao.expects(once()).method("findResponsaveisPorEstabelecimento").with(eq(empresa.getId()), eq(estabelecimento.getId())).will(returnValue(Arrays.asList(new EngenheiroResponsavel[]{engenheiroResponsavel1})));
		
		colaboradorDoPPP.setDataAdmissao(admissao);
		colaboradorDoPPP.setDataDesligamento(desligamento);
		Collection<EngenheiroResponsavel> resultado = engenheiroResponsavelManager.findResponsaveisPorEstabelecimento(colaboradorDoPPP, ppp);
		
		assertEquals(++contador+"o teste.",estaNaRelacao, resultado.size() == 1);
	}
}