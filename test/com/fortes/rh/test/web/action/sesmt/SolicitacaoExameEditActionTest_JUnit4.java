package com.fortes.rh.test.web.action.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.ClinicaAutorizadaManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.ExameSolicitacaoExameManager;
import com.fortes.rh.business.sesmt.MedicoCoordenadorManager;
import com.fortes.rh.business.sesmt.SolicitacaoExameManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.TipoPessoa;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoExameFactory;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.action.sesmt.SolicitacaoExameEditAction;
import com.opensymphony.xwork.ActionContext;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SpringUtil.class, SecurityUtil.class, ActionContext.class})
public class SolicitacaoExameEditActionTest_JUnit4 
{
	private SolicitacaoExameEditAction action = new SolicitacaoExameEditAction();
	
	private SolicitacaoExameManager solicitacaoExameManager;
	private ExameManager exameManager;
	private ExameSolicitacaoExameManager exameSolicitacaoExameManager;
	private MedicoCoordenadorManager medicoCoordenadorManager;
	private ClinicaAutorizadaManager clinicaAutorizadaManager;
	private ColaboradorManager colaboradorManager;
	private FaixaSalarialManager faixaSalarialManager;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	
	@Before
	public void setUp () throws Exception
	{
		solicitacaoExameManager = mock(SolicitacaoExameManager.class) ;
		exameManager = mock(ExameManager.class) ;
		exameSolicitacaoExameManager = mock(ExameSolicitacaoExameManager.class) ;
		medicoCoordenadorManager = mock(MedicoCoordenadorManager.class) ;
		clinicaAutorizadaManager = mock(ClinicaAutorizadaManager.class) ;
		colaboradorManager=mock(ColaboradorManager.class);
		faixaSalarialManager=mock(FaixaSalarialManager.class);
		candidatoSolicitacaoManager=mock(CandidatoSolicitacaoManager.class);
		
		action.setSolicitacaoExameManager(solicitacaoExameManager);
		action.setExameManager(exameManager);
		action.setExameSolicitacaoExameManager(exameSolicitacaoExameManager);
		action.setMedicoCoordenadorManager(medicoCoordenadorManager);
		action.setClinicaAutorizadaManager(clinicaAutorizadaManager);
		action.setColaboradorManager(colaboradorManager);
		action.setFaixaSalarialManager(faixaSalarialManager);
		action.setCandidatoSolicitacaoManager(candidatoSolicitacaoManager);
	}

	@Test
	public void testPrepare() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		when(medicoCoordenadorManager.findByEmpresa(empresa.getId())).thenReturn(new ArrayList<MedicoCoordenador>());
		when(clinicaAutorizadaManager.findByDataEmpresa(empresa.getId(),new Date(),true)).thenReturn(new ArrayList<ClinicaAutorizada>());
		
		assertEquals("success",action.prepareInsert());
	}
	
	@Test
	public void testPrepareInsertColaborador() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		action.setColaborador(ColaboradorFactory.getEntity(1L));
		
		when(medicoCoordenadorManager.findByEmpresa(1L)).thenReturn(new ArrayList<MedicoCoordenador>());
		when(clinicaAutorizadaManager.findByDataEmpresa(1L,new Date(),true)).thenReturn(new ArrayList<ClinicaAutorizada>());
		when(exameManager.findPriorizandoExameRelacionado(1L,1l)).thenReturn(new ArrayList<Exame>());
		when(colaboradorManager.findByData(any(Long.class),any(Date.class))).thenReturn(new Colaborador());
		when(faixaSalarialManager.findFaixas(eq(empresa),eq(Cargo.ATIVO),any(Long.class))).thenReturn(new ArrayList<FaixaSalarial>());
		
		assertEquals("success",action.prepareInsert());
	}

	@Test
	public void testPrepareInsertCandidato() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Candidato candidato = CandidatoFactory.getCandidato(1L);

		action.setEmpresaSistema(empresa);
		action.setCandidato(candidato);
		
		when(medicoCoordenadorManager.findByEmpresa(1L)).thenReturn(new ArrayList<MedicoCoordenador>());
		when(clinicaAutorizadaManager.findByDataEmpresa(1L,new Date(),true)).thenReturn(new ArrayList<ClinicaAutorizada>());
		when(exameManager.findByEmpresaComAsoPadrao(1L)).thenReturn(new ArrayList<Exame>());
		when(faixaSalarialManager.findFaixas(any(Empresa.class),eq(Cargo.ATIVO),any(Long.class))).thenReturn(new ArrayList<FaixaSalarial>());
		when(candidatoSolicitacaoManager.listarSolicitacoesEmAbertoCandidatoOuColaborador(eq(TipoPessoa.CANDIDATO),eq(candidato.getId()),any(Date.class))).thenReturn(new ArrayList<CandidatoSolicitacao>());
		
		assertEquals("success",action.prepareInsert());
	} 

	@Test
	public void testPrepareInsertColaboradorComSolicitacaoExame() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		
		Candidato candidato = CandidatoFactory.getCandidato(1l);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setCandidato(candidato);
		colaborador.setFaixaSalarial(faixaSalarial);
		
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(new Date(), empresa, null);
		solicitacaoExame.setId(1l);
		solicitacaoExame.setColaborador(colaborador);
		
		action.setEmpresaSistema(empresa);
		action.setColaborador(colaborador);
		action.setSolicitacaoExame(solicitacaoExame);
		
		when(solicitacaoExameManager.findByIdProjection(solicitacaoExame.getId())).thenReturn(solicitacaoExame);
		when(medicoCoordenadorManager.findByEmpresa(empresa.getId())).thenReturn(new ArrayList<MedicoCoordenador>());
		when(clinicaAutorizadaManager.findByDataEmpresa(empresa.getId(),new Date(),true)).thenReturn(new ArrayList<ClinicaAutorizada>());
		when(exameManager.findPriorizandoExameRelacionado(empresa.getId(),colaborador.getId())).thenReturn(new ArrayList<Exame>());
		when(colaboradorManager.findByData(eq(colaborador.getId()),any(Date.class))).thenReturn(colaborador);
		when(faixaSalarialManager.findFaixas(eq(empresa),eq(Cargo.ATIVO),any(Long.class))).thenReturn(new ArrayList<FaixaSalarial>());
		
		assertEquals("success",action.prepareInsert());
	}
	
	@Test
	public void testPrepareInsertColaboradorComSolicitacaoExameSemData() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		
		Candidato candidato = CandidatoFactory.getCandidato(1l);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setCandidato(candidato);
		colaborador.setFaixaSalarial(faixaSalarial);
		
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(1l);
		solicitacaoExame.setId(1l);
		solicitacaoExame.setData(null);
		solicitacaoExame.setColaborador(colaborador);
		
		action.setEmpresaSistema(empresa);
		action.setColaborador(colaborador);
		action.setSolicitacaoExame(solicitacaoExame);
		
		when(solicitacaoExameManager.findByIdProjection(solicitacaoExame.getId())).thenReturn(solicitacaoExame);
		when(medicoCoordenadorManager.findByEmpresa(empresa.getId())).thenReturn(new ArrayList<MedicoCoordenador>());
		when(clinicaAutorizadaManager.findByDataEmpresa(empresa.getId(),new Date(),true)).thenReturn(new ArrayList<ClinicaAutorizada>());
		when(exameManager.findPriorizandoExameRelacionado(empresa.getId(),colaborador.getId())).thenReturn(new ArrayList<Exame>());
		when(colaboradorManager.findByData(eq(colaborador.getId()),any(Date.class))).thenReturn(colaborador);
		when(faixaSalarialManager.findFaixas(eq(empresa),eq(Cargo.ATIVO),any(Long.class))).thenReturn(new ArrayList<FaixaSalarial>());
		
		assertEquals("success",action.prepareInsert());
	}
	
	@Test
	public void testPrepareInsertColaboradorDesligadoComSolicitacaoExame() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1l);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setDesligado(Boolean.TRUE);
		colaborador.setFaixaSalarial(faixaSalarial);
		
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(new Date(), empresa, null);
		solicitacaoExame.setId(1l);
		solicitacaoExame.setColaborador(colaborador);
		
		action.setEmpresaSistema(empresa);
		action.setColaborador(colaborador);
		action.setSolicitacaoExame(solicitacaoExame);
		
		when(solicitacaoExameManager.findByIdProjection(solicitacaoExame.getId())).thenReturn(solicitacaoExame);
		when(medicoCoordenadorManager.findByEmpresa(empresa.getId())).thenReturn(new ArrayList<MedicoCoordenador>());
		when(clinicaAutorizadaManager.findByDataEmpresa(empresa.getId(),new Date(),true)).thenReturn(new ArrayList<ClinicaAutorizada>());
		when(exameManager.findPriorizandoExameRelacionado(empresa.getId(),colaborador.getId())).thenReturn(new ArrayList<Exame>());
		when(colaboradorManager.findByData(eq(colaborador.getId()),any(Date.class))).thenReturn(colaborador);
		
		Colaborador colaboradorAction = action.getColaborador();
		
		assertEquals("success",action.prepareInsert());
		assertTrue(colaboradorAction.isDesligado());
		assertNotNull(colaboradorAction.getFaixaSalarial().getId());
	}
}