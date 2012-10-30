package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.dwr.SolicitacaoDWR;

public class SolicitacaoDWRTest extends MockObjectTestCase
{
	private SolicitacaoDWR solicitacoaDWR;
	private Mock solicitacaoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		solicitacoaDWR = new SolicitacaoDWR();

		solicitacaoManager = new Mock(SolicitacaoManager.class);
		solicitacoaDWR.setSolicitacaoManager((SolicitacaoManager) solicitacaoManager.proxy());
		
	}
	
	
	public void testGetByEmpresaEstabelecimentosAreas()
	{
		
		Date dataSol1 = DateUtil.criarAnoMesDia(2000, 12, 01);
		Date dataSol2 = new Date();
		Date dataSol3 = DateUtil.criarAnoMesDia(2001, 05, 01);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setNome("Área 1");
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		
		Solicitacao sol1 = SolicitacaoFactory.getSolicitacao();
		sol1.setId(1L);
		sol1.setDescricao("Um");
		sol1.setData(dataSol1);
		sol1.setAreaOrganizacional(areaOrganizacional);
		sol1.setEstabelecimento(estabelecimento);
		sol1.setEmpresa(empresa);

		Solicitacao sol2 = SolicitacaoFactory.getSolicitacao();
		sol2.setId(2L);
		sol2.setDescricao("Dois");
		sol2.setData(dataSol2);
		sol2.setAreaOrganizacional(areaOrganizacional);
		sol2.setEstabelecimento(estabelecimento);
		sol2.setEmpresa(empresa);
		
		Solicitacao sol3 = SolicitacaoFactory.getSolicitacao();
		sol3.setId(3L);
		sol3.setDescricao("Três");
		sol3.setData(dataSol3);
		sol3.setAreaOrganizacional(areaOrganizacional);
		sol3.setEstabelecimento(estabelecimento);
		sol3.setEmpresa(empresa);

		Collection<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
		solicitacoes.add(sol1);
		solicitacoes.add(sol2);
		solicitacoes.add(sol3);

		solicitacaoManager.expects(once()).method("findByEmpresaEstabelecimentosAreas").with(eq(empresa.getId()), ANYTHING, ANYTHING).will(returnValue(solicitacoes));
		
		Map<Long, String> result = solicitacoaDWR.getByEmpresaEstabelecimentosAreas(empresa.getId(), new Long[]{estabelecimento.getId()}, new Long[]{areaOrganizacional.getId()});
		
		assertEquals("1 - Um -  - "+ DateUtil.formataDiaMesAno(dataSol1)+" - "+areaOrganizacional.getNome(), result.get(1L));	
		assertEquals("2 - Dois -  - "+ DateUtil.formataDiaMesAno(dataSol2)+" - "+areaOrganizacional.getNome(), result.get(2L));	
		assertEquals("3 - Três -  - "+ DateUtil.formataDiaMesAno(dataSol3)+" - "+areaOrganizacional.getNome(), result.get(3L));	
	}
	
	public void testGetSolicitacoes()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Date dataSol1 = DateUtil.criarAnoMesDia(2000, 12, 01);
		Date dataSol2 = new Date();
		Date dataSol3 = DateUtil.criarAnoMesDia(2001, 05, 01);
		
		Solicitacao sol1 = SolicitacaoFactory.getSolicitacao();
		sol1.setId(1L);
		sol1.setDescricao("Um");
		sol1.setData(dataSol1);
		sol1.setEmpresa(empresa);
		
		Solicitacao sol2 = SolicitacaoFactory.getSolicitacao();
		sol2.setId(2L);
		sol2.setDescricao("Dois");
		sol2.setData(dataSol2);
		sol2.setEmpresa(empresa);
		
		Solicitacao sol3 = SolicitacaoFactory.getSolicitacao();
		sol3.setId(3L);
		sol3.setDescricao("Três");
		sol3.setData(dataSol3);
		sol3.setEmpresa(empresa);
		
		Collection<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
		solicitacoes.add(sol1);
		solicitacoes.add(sol2);
		solicitacoes.add(sol3);
		
		solicitacaoManager.expects(once()).method("findSolicitacaoList").with(eq(empresa.getId()), ANYTHING, ANYTHING, ANYTHING).will(returnValue(solicitacoes));
		
		Map<Long, String> result = solicitacoaDWR.getSolicitacoes(empresa.getId());
		
		assertEquals("1 - Um -  - "+ DateUtil.formataDiaMesAno(dataSol1)+" - ", result.get(1L));	
		assertEquals("2 - Dois -  - "+ DateUtil.formataDiaMesAno(dataSol2)+" - ", result.get(2L));	
		assertEquals("3 - Três -  - "+ DateUtil.formataDiaMesAno(dataSol3)+" - ", result.get(3L));	
	}
}
	