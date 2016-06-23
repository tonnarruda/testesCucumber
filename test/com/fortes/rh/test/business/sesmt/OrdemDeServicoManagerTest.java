package com.fortes.rh.test.business.sesmt;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.OrdemDeServicoManagerImpl;
import com.fortes.rh.business.sesmt.RiscoFuncaoManager;
import com.fortes.rh.dao.sesmt.OrdemDeServicoDao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.OrdemDeServico;
import com.fortes.rh.model.sesmt.RiscoFuncao;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.OrdemDeServicoFactory;
import com.fortes.rh.util.DateUtil;

public class OrdemDeServicoManagerTest
{
	private OrdemDeServicoManagerImpl ordemDeServicoManager;
	private ColaboradorManager colaboradorManager;
	private RiscoFuncaoManager riscoFuncaoManager;
	private OrdemDeServicoDao ordemDeServicoDao;
	private CursoManager cursoManager;
	private EpiManager epiManager;
	private OrdemDeServico ordemDeServico;
	private Colaborador colaborador;
	private Empresa empresa;
	
	@Before
	public void setUp() throws Exception {
		ordemDeServicoManager = new OrdemDeServicoManagerImpl();
		
		colaboradorManager = mock(ColaboradorManager.class);
		ordemDeServicoManager.setColaboradorManager(colaboradorManager);
		
		riscoFuncaoManager = mock(RiscoFuncaoManager.class);
		ordemDeServicoManager.setRiscoFuncaoManager(riscoFuncaoManager);
		
		ordemDeServicoDao = mock(OrdemDeServicoDao.class);
		ordemDeServicoManager.setDao(ordemDeServicoDao);
		
		cursoManager = mock(CursoManager.class);
		ordemDeServicoManager.setCursoManager(cursoManager);
		
		epiManager = mock(EpiManager.class);
		ordemDeServicoManager.setEpiManager(epiManager);
	}
	@Before
	public void inicializaOrdemDeServicoColaboradorEEmpresa(){
		this.ordemDeServico = OrdemDeServicoFactory.getEntity(); 
		
		this.colaborador = ColaboradorFactory.getEntity(1L, "Colaborador");
		colaborador.setDataAdmissao(new Date());
		colaborador.setFuncaoNome("Função");
		colaborador.setCargoCodigoCBO("012345");
		colaborador.setFuncaoHistoricoFuncaoAtualId(1L);
		colaborador.setFuncaoHistoricoFuncaoAtualDescricao("Descricao");
		
		this.empresa = EmpresaFactory.getEmpresa();
		empresa.setNormasInternas("normasInternas");
		empresa.setProcedimentoEmCasoDeAcidente("procedimentoEmCasoDeAcidente");
		empresa.setTermoDeResponsabilidade("termoDeResponsabilidade");
	}
	
	@Test
	public void montaOrdemDeServicoComIdNulo() {
		Date dataOS = DateUtil.criarDataMesAno(1, 1, 2016);
		
		when(colaboradorManager.findComDadosBasicosParaOrdemDeServico(colaborador, dataOS)).thenReturn(colaborador);
		when(riscoFuncaoManager.riscosByHistoricoFuncao(colaborador.getFuncao().getHistoricoAtual())).thenReturn(new ArrayList<RiscoFuncao>());
		when(cursoManager.findByHistoricoFuncaoId(colaborador.getFuncao().getHistoricoAtual().getId())).thenReturn(new ArrayList<Curso>());
		when(epiManager.findByHistoricoFuncao(colaborador.getFuncao().getHistoricoAtual().getId())).thenReturn(new ArrayList<Epi>());
		
		assertNull(ordemDeServicoManager.montaOrdemDeServico(colaborador, empresa, dataOS).getId());
	}
	
	@Test
	public void findOrdemServicoProjection(){
		ordemDeServico.setId(1L);
		when(ordemDeServicoDao.findOrdemServicoProjection(ordemDeServico.getId())).thenReturn(ordemDeServico);
		assertEquals(ordemDeServico.getId(), ordemDeServicoManager.findOrdemServicoProjection(ordemDeServico.getId()).getId());
	}
	
	@Test
	public void ordemDeServicoAtual() {
		ordemDeServico.setId(1L);
		when(ordemDeServicoDao.ultimaOrdemDeServico(colaborador.getId())).thenReturn(ordemDeServico);
		assertEquals(ordemDeServico.getId(), ordemDeServicoManager.findUltimaOrdemDeServico(colaborador.getId()).getId());
	}
}
