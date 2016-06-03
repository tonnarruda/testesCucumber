package com.fortes.rh.test.web.action.sesmt;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorCertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockCheckListBoxUtil;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.desenvolvimento.CertificacaoEditAction;
import com.fortes.web.tags.CheckBox;

public class CertificacaoEditActionTest
{
	private CertificacaoEditAction action;
	private CertificacaoManager certificacaoManager;
	private CursoManager cursoManager;
	private AvaliacaoPraticaManager avaliacaoPraticaManager;
	private ColaboradorCertificacaoManager colaboradorCertificacaoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private EmpresaManager empresaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	@Before
	public void setUp() throws Exception
	{
		action = new CertificacaoEditAction();

		certificacaoManager = mock(CertificacaoManager.class);
        action.setCertificacaoManager(certificacaoManager);
        
        cursoManager = mock(CursoManager.class);
        action.setCursoManager(cursoManager);

        avaliacaoPraticaManager = mock(AvaliacaoPraticaManager.class);
        action.setAvaliacaoPraticaManager(avaliacaoPraticaManager);

        colaboradorCertificacaoManager = mock(ColaboradorCertificacaoManager.class);
        action.setColaboradorCertificacaoManager(colaboradorCertificacaoManager);
        
        areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
        action.setAreaOrganizacionalManager(areaOrganizacionalManager);

        estabelecimentoManager = mock(EstabelecimentoManager.class);
        action.setEstabelecimentoManager(estabelecimentoManager);

        empresaManager = mock(EmpresaManager.class);
        action.setEmpresaManager(empresaManager);

        parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
        action.setParametrosDoSistemaManager(parametrosDoSistemaManager);
        
        Mockit.redefineMethods(CheckListBoxUtil.class, MockCheckListBoxUtil.class);
        Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
	}
	
	@Test
	public void testPrepareInsert() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		certificacao.setNome("certifica");
		action.setCertificacao(certificacao);
		
		when(certificacaoManager.findById(certificacao.getId())).thenReturn(certificacao);
		when(cursoManager.findAllByEmpresasParticipantes(new Long[] {empresa.getId()})).thenReturn(new ArrayList<Curso>());
		when(avaliacaoPraticaManager.find(null)).thenReturn(new ArrayList<AvaliacaoPratica>());
		
		assertEquals("success", action.prepareInsert());
	}

	@Test
	public void testPrepareUpdate() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setControlarVencimentoCertificacaoPor(2);
		action.setEmpresaSistema(empresa);
		
		Curso trepa = CursoFactory.getEntity(2L);
		trepa.setNome("trepa");

		Curso come = CursoFactory.getEntity(2L);
		trepa.setNome("come");
		
		Collection<Curso> cursos = new ArrayList<Curso>();
		cursos.add(trepa);
		cursos.add(come);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		certificacao.setNome("certifica");
		certificacao.setCursos(cursos);
		action.setCertificacao(certificacao);
		
		when(certificacaoManager.verificaEmpresa(certificacao.getId(), empresa.getId())).thenReturn(true);
		when(certificacaoManager.findById(certificacao.getId())).thenReturn(certificacao);
		when(cursoManager.findAllByEmpresasParticipantes(new Long[] {empresa.getId()})).thenReturn(new ArrayList<Curso>());
		when(certificacaoManager.findAllSelectNotCertificacaoIdAndCertificacaoPreRequisito(empresa.getId(), certificacao.getId())).thenReturn(new ArrayList<Certificacao>());
		when(avaliacaoPraticaManager.find(null)).thenReturn(new ArrayList<AvaliacaoPratica>());
		when(colaboradorCertificacaoManager.findByColaboradorIdAndCertificacaoId(null, certificacao.getId())).thenReturn(new ArrayList<ColaboradorCertificacao>());
		
		assertEquals("success", action.prepareUpdate());
	}

	@Test
	public void testPrepareUpdateSemCertificacao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		assertEquals("success", action.prepareUpdate());
	}
	
	@Test
	public void testInsert() throws Exception
	{
		String[] cursosCheck = new String[]{"1", "2"};
		action.setCursosCheck(cursosCheck);
				
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		certificacao.setNome("certifica");
		action.setCertificacao(certificacao);
		
		assertEquals("success", action.insert());
	}
	
	@Test
	public void testUpdate() throws Exception
	{
		String[] cursosCheck = new String[]{"1", "2"};
		action.setCursosCheck(cursosCheck);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		certificacao.setNome("certifica");
		action.setCertificacao(certificacao);
		
		assertEquals("success", action.update());
	}
	
	@Test
	public void testPrepareImprimirCertificadosVencidosAVencer() throws Exception
	{
		mocksPrepareImprimir();
		assertEquals("success", action.prepareImprimirCertificadosVencidosAVencer());
	}

	private void mocksPrepareImprimir() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
		parametrosDoSistema.setCompartilharColaboradores(true);
		
		when(certificacaoManager.populaCheckBox(empresa.getId())).thenReturn(new ArrayList<CheckBox>());
		when(areaOrganizacionalManager.populaCheckOrderDescricao(empresa.getId())).thenReturn(new ArrayList<CheckBox>());
		when(estabelecimentoManager.populaCheckBox(empresa.getId())).thenReturn(new ArrayList<CheckBox>());
		when(empresaManager.ajustaCombo(empresa.getId(), empresa.getId())).thenReturn(empresa.getId());
		when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
		when(empresaManager.findEmpresasPermitidas(true, empresa.getId(), 1L, "")).thenReturn(new ArrayList<Empresa>());
	}
	
	@Test
	public void testImprimirCertificadosVencidosAVencer() throws Exception
	{
		action.setColaboradorNaoCertificado(true);
		action.setColaboradorCertificado(true);
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		ColaboradorCertificacao colaboradorCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacaos.add(colaboradorCertificacao);
		
		when(colaboradorCertificacaoManager.montaRelatorioColaboradoresNasCertificacoes(null, null, null, null, new Long[]{}, new Long[]{}, new Long[]{}, new Long[]{}, null)).thenReturn(colaboradorCertificacaos);
		
		assertEquals("success", action.imprimirCertificadosVencidosAVencer());
	}
	
	@Test
	public void testImprimirCertificadosVencidosAVencerVazio() throws Exception
	{
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = new ArrayList<ColaboradorCertificacao>();
		
		when(colaboradorCertificacaoManager.montaRelatorioColaboradoresNasCertificacoes(null, null, null, null, new Long[]{}, new Long[]{}, new Long[]{}, new Long[]{}, null)).thenReturn(colaboradorCertificacaos);
		mocksPrepareImprimir();
		
		assertEquals("input", action.imprimirCertificadosVencidosAVencer());
	}
	
	@Test
	public void testImprimirCertificadosVencidosAVencerException() throws Exception
	{
		when(colaboradorCertificacaoManager.montaRelatorioColaboradoresNasCertificacoes(null, null, null, true, new Long[]{}, new Long[]{}, new Long[]{}, new Long[]{}, null)).thenReturn(null);
		mocksPrepareImprimir();
		
		assertEquals("input", action.imprimirCertificadosVencidosAVencer());
	}
	
	@Test
	public void testImprimirCertificadosVencidosAVencerAgrupadoPorCertificacao() throws Exception
	{
		action.setColaboradorNaoCertificado(true);
		action.setColaboradorCertificado(true);
		action.setAgruparPor('T');;
		
		Colaborador colaboradorAprovado = ColaboradorFactory.getEntity(1L);
		Colaborador colaboradorNaoAprovado = ColaboradorFactory.getEntity(2L);
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		
		ColaboradorCertificacao colaboradorAprovadoCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);
		colaboradorAprovadoCertificacao.setCertificacao(certificacao);
		colaboradorAprovadoCertificacao.setColaborador(colaboradorAprovado);
		
		ColaboradorCertificacao colaboradorNaoAprovadoCertificacao = ColaboradorCertificacaoFactory.getEntity(1L);
		colaboradorNaoAprovadoCertificacao.setCertificacao(certificacao);
		colaboradorNaoAprovadoCertificacao.setColaborador(colaboradorNaoAprovado);
		
		Collection<ColaboradorCertificacao> colaboradorCertificacaos = new ArrayList<ColaboradorCertificacao>();
		colaboradorCertificacaos.add(colaboradorAprovadoCertificacao);
		colaboradorCertificacaos.add(colaboradorNaoAprovadoCertificacao);
		
		when(colaboradorCertificacaoManager.montaRelatorioColaboradoresNasCertificacoes(null, null, null, null, new Long[]{}, new Long[]{}, new Long[]{}, new Long[]{}, null)).thenReturn(colaboradorCertificacaos);
		
		assertEquals("sucessoAgrupadoPorCertificacao", action.imprimirCertificadosVencidosAVencer());
	}
}
