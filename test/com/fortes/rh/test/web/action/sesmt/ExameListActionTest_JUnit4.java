package com.fortes.rh.test.web.action.sesmt;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.ClinicaAutorizadaManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.MedicoCoordenadorManager;
import com.fortes.rh.business.sesmt.SolicitacaoExameManager;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.sesmt.ExameListAction;

public class ExameListActionTest_JUnit4
{
	private ExameListAction action;
	private ExameManager exameManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private ColaboradorManager colaboradorManager;
	private MedicoCoordenadorManager medicoCoordenadorManager;
	private CandidatoManager candidatoManager;
	private ClinicaAutorizadaManager clinicaAutorizadaManager;
	private SolicitacaoExameManager solicitacaoExameManager;

	@Before
	public void setUp() throws Exception
	{
		action = new ExameListAction();
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);

		exameManager = mock(ExameManager.class);
		action.setExameManager(exameManager);

		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		action.setAreaOrganizacionalManager(areaOrganizacionalManager);

		estabelecimentoManager = mock(EstabelecimentoManager.class);
		action.setEstabelecimentoManager(estabelecimentoManager);

		colaboradorManager = mock(ColaboradorManager.class);
		action.setColaboradorManager(colaboradorManager);

		medicoCoordenadorManager = mock(MedicoCoordenadorManager.class);
		action.setMedicoCoordenadorManager(medicoCoordenadorManager);

		candidatoManager = mock(CandidatoManager.class);
		action.setCandidatoManager(candidatoManager);
		
		clinicaAutorizadaManager = mock(ClinicaAutorizadaManager.class);
		action.setClinicaAutorizadaManager(clinicaAutorizadaManager);
		
		solicitacaoExameManager = mock(SolicitacaoExameManager.class);
		action.setSolicitacaoExameManager(solicitacaoExameManager);
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
	}

	@Test
	public void testDeveriaGerarRelatorioDeExamesPrevitosQuandoTemParametros() throws Exception
	{
		Collection<ExamesPrevistosRelatorio> colecaoRelatorio = new ArrayList<ExamesPrevistosRelatorio>();
		ExamesPrevistosRelatorio examesPrevistosRelatorio = new ExamesPrevistosRelatorio();
		colecaoRelatorio.add(examesPrevistosRelatorio);

		Long[] estabelecimentoIds = {1L};
		
		// Teste com os arrays de ids
		String[] areasCheck={"1"}, estabelecimentosCheck={"1"}, colaboradoresCheck={"1"}, examesCheck = {"1"};
		action.setAreasCheck(areasCheck);
		action.setEstabelecimentosCheck(estabelecimentosCheck);
		action.setExamesCheck(examesCheck);
		action.setColaboradoresCheck(colaboradoresCheck);
		action.setAgruparPor('C');
		action.setInicio(DateUtil.criarDataMesAno(1, 1, 2015));
		action.setFim(DateUtil.criarDataMesAno(1, 2, 2015));
		
		when(exameManager.findRelatorioExamesPrevistos(action.getEmpresaSistema().getId(), action.getInicio(), action.getFim(), new Long[]{1L}, new Long[]{1L}, new Long[]{1L}, new Long[]{1L}, 'C', false, false, false)).thenReturn(colecaoRelatorio);
		when(estabelecimentoManager.nomeEstabelecimentos(estabelecimentoIds, action.getEmpresaSistema().getId())).thenReturn("Paizinho");

		assertEquals("success", action.relatorioExamesPrevistos());
	}
	
	@Test
	public void testRelatorioExamesPrevistosColecaoVaziaException() throws Exception
	{
		when(exameManager.findRelatorioExamesPrevistos(1L, null, null, new Long[]{1L}, new Long[]{1L}, new Long[]{1L}, new Long[]{1L}, 'C', false, false, false)).thenReturn(new ArrayList<ExamesPrevistosRelatorio>());
		assertEquals("input", action.relatorioExamesPrevistos());
	}
	
	@Test
	public void testDeveriaGerarRelatorioDeExamesPrevitos() throws Exception {
		
		ExamesPrevistosRelatorio examesPrevistosRelatorio = new ExamesPrevistosRelatorio();
		Collection<ExamesPrevistosRelatorio> colecaoRelatorio = new ArrayList<ExamesPrevistosRelatorio>();
		colecaoRelatorio.add(examesPrevistosRelatorio);
		action.setAgruparPor('C');
		action.setInicio(DateUtil.criarDataMesAno(1, 1, 2015));
		action.setFim(DateUtil.criarDataMesAno(1, 2, 2015));

		when(exameManager.findRelatorioExamesPrevistos(action.getEmpresaSistema().getId(), action.getInicio(), action.getFim(), null, null, null, null, 'C', false, false, false)).thenReturn(colecaoRelatorio);
		
		assertEquals("success", action.relatorioExamesPrevistos());
	}
}
