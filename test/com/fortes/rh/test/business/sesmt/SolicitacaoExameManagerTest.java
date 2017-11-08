package com.fortes.rh.test.business.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.transaction.SystemException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.ObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.sesmt.ExameSolicitacaoExameManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.business.sesmt.RealizacaoExameManager;
import com.fortes.rh.business.sesmt.RiscoAmbienteManager;
import com.fortes.rh.business.sesmt.RiscoFuncaoManager;
import com.fortes.rh.business.sesmt.SolicitacaoExameManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoExameDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.GrupoRisco;
import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoPessoa;
import com.fortes.rh.model.dicionario.TipoRiscoSistema;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.model.sesmt.relatorio.AsoRelatorio;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoFuncaoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoExameFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringUtil.class)
public class SolicitacaoExameManagerTest
{
	private SolicitacaoExameManagerImpl solicitacaoExameManager = new SolicitacaoExameManagerImpl();
	private ExameSolicitacaoExameManager exameSolicitacaoExameManager;
	private RealizacaoExameManager realizacaoExameManager;
	private PlatformTransactionManager transactionManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private SolicitacaoExameDao solicitacaoExameDao;
	private RiscoAmbienteManager riscoAmbienteManager;
	private RiscoFuncaoManager riscoFuncaoManager;
	private HistoricoFuncaoManager historicoFuncaoManager;

	@Before
	public void setUp() throws Exception
    {
        exameSolicitacaoExameManager = mock(ExameSolicitacaoExameManager.class);
        solicitacaoExameManager.setExameSolicitacaoExameManager(exameSolicitacaoExameManager);
        
        realizacaoExameManager = mock(RealizacaoExameManager.class);
        solicitacaoExameManager.setRealizacaoExameManager(realizacaoExameManager);
        
        solicitacaoExameDao = mock(SolicitacaoExameDao.class);
        solicitacaoExameManager.setDao(solicitacaoExameDao);
        
        historicoColaboradorManager = mock(HistoricoColaboradorManager.class);
        solicitacaoExameManager.setHistoricoColaboradorManager(historicoColaboradorManager);

        riscoAmbienteManager = mock(RiscoAmbienteManager.class);
        solicitacaoExameManager.setRiscoAmbienteManager(riscoAmbienteManager);
        
        riscoFuncaoManager = mock(RiscoFuncaoManager.class);
        solicitacaoExameManager.setRiscoFuncaoManager(riscoFuncaoManager);
        
        transactionManager = mock(PlatformTransactionManager.class);
        solicitacaoExameManager.setTransactionManager(transactionManager);
        
        historicoFuncaoManager = mock(HistoricoFuncaoManager.class);
        
		PowerMockito.mockStatic(SpringUtil.class);
		BDDMockito.given(SpringUtil.getBean("historicoFuncaoManager")).willReturn(historicoFuncaoManager);
    }
	
	@Test
	public void testGetCount()
	{
		Date ini = new Date();
		Date fim = new Date();
		
		when(solicitacaoExameDao.getCount(1L, ini, fim, TipoPessoa.COLABORADOR, null, null, null, new Long[]{}, null, false)).thenReturn(1);
		assertEquals(1, solicitacaoExameManager.getCount(1L, ini, fim, TipoPessoa.COLABORADOR, null, null, null, null, null, false).intValue());
	}

	@Test
	public void testFindAllSelect()
	{
		Collection<SolicitacaoExame> colecao = new ArrayList<SolicitacaoExame>();
		colecao.add(new SolicitacaoExame());
		
		Date ini = new Date();
		Date fim = new Date();
		
		when(solicitacaoExameDao.findAllSelect(0, 20, 4L, ini, fim, TipoPessoa.CANDIDATO, null, null, null, new Long[]{}, null, false)).thenReturn(colecao);

		assertEquals(colecao, solicitacaoExameManager.findAllSelect(0, 20, 4L, ini, fim, TipoPessoa.CANDIDATO, null, null, null, null, null, false));
	}
	
	@Test
	public void testFindByCandidatoOuColaborador()
	{
		TipoPessoa vinculo = TipoPessoa.TODOS;
		Long candidatoOuColaboradorId=99L;
		
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity();
		
		Collection<SolicitacaoExame> solicitacaoExames = new ArrayList<SolicitacaoExame>();
		solicitacaoExames.add(solicitacaoExame);
		
		when(solicitacaoExameDao.findByCandidatoOuColaborador(TipoPessoa.TODOS, 99L, MotivoSolicitacaoExame.ADMISSIONAL)).thenReturn(solicitacaoExames);
		
		assertEquals(1, solicitacaoExameManager.findByCandidatoOuColaborador(vinculo, candidatoOuColaboradorId, MotivoSolicitacaoExame.ADMISSIONAL).size());
	}
	
	@Test
	public void testSave()
	{
		String[] exameIds = new String[]{"1","2"};
		String[] selectClinicas = new String[]{"",""};
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(1L);
		Integer[] periodicidades = new Integer[]{12,6};

		Exception exception = null;
		try
		{
			solicitacaoExameManager.save(solicitacaoExame, exameIds, selectClinicas, periodicidades);
		}
		catch (Exception e)
		{
			exception = e;
		}
		assertNull(exception);
	}

	@Test
	public void testSaveException()
	{
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(1L);

		when(solicitacaoExameDao.save(solicitacaoExame)).thenThrow(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(solicitacaoExame.getId(),"")));

		Exception exception = null;
		try
		{
			solicitacaoExameManager.save(solicitacaoExame, null, null, null);
		}
		catch (Exception e)
		{
			exception = e;
		}
		assertNotNull(exception);
	}

	@Test
	public void testUpdate()
	{
		String[] exameIds = new String[]{"1","2"};
		String[] selectClinicas = new String[]{"",""};
		Integer[] periodicidades = new Integer[]{12,6};

		Collection<Long> colecaoRealizacaoExameIds = new ArrayList<Long>();
		Long[] realizacaoExameIds = new Long[0];

		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(1L);

		when(realizacaoExameManager.findIdsBySolicitacaoExame(solicitacaoExame.getId())).thenReturn(colecaoRealizacaoExameIds);

		solicitacaoExameManager.update(solicitacaoExame, exameIds, selectClinicas, periodicidades);

		verify(exameSolicitacaoExameManager).removeAllBySolicitacaoExame(solicitacaoExame.getId());
		verify(realizacaoExameManager).remove(realizacaoExameIds);
		verify(exameSolicitacaoExameManager).save(solicitacaoExame,exameIds,selectClinicas,periodicidades);

	}

	@Test
	public void testRemove() throws SystemException
	{
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(1L);

		Collection<Long> colecaoRealizacaoExameIds = new ArrayList<Long>();
		Long[] realizacaoExameIds = new Long[0];

		when(realizacaoExameManager.findIdsBySolicitacaoExame(solicitacaoExame.getId())).thenReturn(colecaoRealizacaoExameIds);

		solicitacaoExameManager.remove(solicitacaoExame.getId());

		verify(solicitacaoExameDao).remove(solicitacaoExame.getId());
		verify(realizacaoExameManager).remove(realizacaoExameIds);
		verify(exameSolicitacaoExameManager).removeAllBySolicitacaoExame(solicitacaoExame.getId());
	}
	
	@Test
	public void testRemoveException() throws SystemException
	{
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(1L);
		when(realizacaoExameManager.findIdsBySolicitacaoExame(solicitacaoExame.getId())).thenThrow(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(solicitacaoExame.getId(),"")));
		
		Exception exception = null;
		
		try
		{
			solicitacaoExameManager.remove(solicitacaoExame.getId());
		}catch(Exception e)
		{
			exception = e;	
		}
		
		assertNotNull(exception);
	}
	
	@Test
	public void testGetRelatorioAtendimentos() throws Exception
	{
		Date inicio = DateUtil.criarDataMesAno(01, 01, 2010);
		Date fim = DateUtil.criarDataMesAno(01, 05, 2010);
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity();
		solicitacaoExame.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		
		Collection<SolicitacaoExame> solicitacaoExames = new ArrayList<SolicitacaoExame>();
		solicitacaoExames.add(solicitacaoExame);
		boolean agruparPorMotivo=false;
		boolean ordenarPorNome=false;
		
		when(solicitacaoExameDao.findAtendimentosMedicos(inicio, fim, new String[]{}, solicitacaoExame.getMedicoCoordenador(), empresa.getId(), agruparPorMotivo, ordenarPorNome, 'T')).thenReturn(solicitacaoExames);
		
		assertEquals(1,solicitacaoExameManager.getRelatorioAtendimentos(inicio, fim, solicitacaoExame, empresa, agruparPorMotivo, ordenarPorNome, new String[]{}, 'T').size());
	}
	
	@Test
	public void testGetRelatorioAtendimentosColecaoVazia()
	{
		Date inicio = DateUtil.criarDataMesAno(01, 01, 2010);
		Date fim = DateUtil.criarDataMesAno(01, 05, 2010);
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity();
		solicitacaoExame.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		
		when(solicitacaoExameDao.findAtendimentosMedicos(inicio, fim, new String[]{}, solicitacaoExame.getMedicoCoordenador(), empresa.getId(), false, false, 'T')).thenReturn(new ArrayList<SolicitacaoExame>());
		
		Exception exception=null;
		try {
			solicitacaoExameManager.getRelatorioAtendimentos(inicio, fim, solicitacaoExame, empresa, false, false, new String[]{}, 'T');
		} catch (ColecaoVaziaException e) {
			exception=e;
		}
		
		assertNotNull(exception);
	}
	
	@Test
	public void testMontaRelatorioAsoSemSolicitacaoExame() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Exception exception = null;
		try {
			solicitacaoExameManager.montaRelatorioAso(empresa , null, TipoRiscoSistema.NEHUM);
		} catch (ColecaoVaziaException e) {
			exception=e;
		}
		
		assertNotNull(exception);
	}
	
	@Test
	public void testMontaRelatorioAsoConsiderandoRiscoPorAmbiente() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		Funcao funcao = FuncaoFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1000L);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, null, null, null, null, funcao, ambiente, StatusRetornoAC.CONFIRMADO);
		
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(2L);
		solicitacaoExame.setColaborador(colaborador);
		
		Risco risco1 = RiscoFactory.getEntity(1L, "Risco 1", GrupoRisco.FISICO);
		Risco risco2 = RiscoFactory.getEntity(2L, "Risco 2", GrupoRisco.ACIDENTE);
		
		when(solicitacaoExameDao.findById(solicitacaoExame.getId())).thenReturn(solicitacaoExame);
		when(exameSolicitacaoExameManager.findBySolicitacaoExame(solicitacaoExame.getId(), true)).thenReturn(null);
		when(exameSolicitacaoExameManager.findBySolicitacaoExame(solicitacaoExame.getId(), false)).thenReturn(null);
		when(historicoColaboradorManager.getHistoricoAtual(colaborador.getId())).thenReturn(null);
		when(historicoColaboradorManager.getHistoricoAtualOuFuturo(colaborador.getId())).thenReturn(historicoColaborador);
		when(riscoAmbienteManager.findRiscosByAmbienteData(historicoColaborador.getAmbiente().getId(), solicitacaoExame.getData())).thenReturn(Arrays.asList(risco1, risco2));
		
		Date dataHistFuncao = DateUtil.criarDataMesAno(1, 1, 2000);
		when(historicoFuncaoManager.findByFuncaoAndData(historicoColaborador.getFuncao().getId(), historicoColaborador.getData())).thenReturn(HistoricoFuncaoFactory.getEntity(funcao, dataHistFuncao, null));
		
		AsoRelatorio asoRelatorio = solicitacaoExameManager.montaRelatorioAso(empresa , solicitacaoExame, TipoRiscoSistema.AMBIENTE);
		
		assertEquals(solicitacaoExame.getColaborador(), asoRelatorio.getPessoa());
		assertEquals(asoRelatorio.getGrpRiscoFisico(), "Físicos: "+risco1.getDescricao());
		assertEquals(asoRelatorio.getGrpRiscoAcidente(), "Acidentes: "+risco2.getDescricao());
		assertEquals(asoRelatorio.getGrpRiscoBiologico()+asoRelatorio.getGrpRiscoErgonomico()+asoRelatorio.getGrpRiscoQuimico(), StringUtils.EMPTY);
	}
	
	@Test
	public void testMontaRelatorioAsoConsiderandoRiscoPorFuncao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Funcao funcao = FuncaoFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1000L);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, null, null, null, null, funcao, null, StatusRetornoAC.CONFIRMADO);
		
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(2L);
		solicitacaoExame.setColaborador(colaborador);
		
		Risco risco1 = RiscoFactory.getEntity(1L, "Risco 1", GrupoRisco.ERGONOMICO);
		Risco risco2 = RiscoFactory.getEntity(2L, "Risco 2", GrupoRisco.ACIDENTE);
		
		when(solicitacaoExameDao.findById(solicitacaoExame.getId())).thenReturn(solicitacaoExame);
		when(exameSolicitacaoExameManager.findBySolicitacaoExame(solicitacaoExame.getId(), true)).thenReturn(null);
		when(exameSolicitacaoExameManager.findBySolicitacaoExame(solicitacaoExame.getId(), false)).thenReturn(null);
		when(historicoColaboradorManager.getHistoricoAtual(colaborador.getId())).thenReturn(null);
		when(historicoColaboradorManager.getHistoricoAtualOuFuturo(colaborador.getId())).thenReturn(historicoColaborador);
		when(riscoFuncaoManager.findRiscosByFuncaoData(historicoColaborador.getFuncao().getId(), solicitacaoExame.getData())).thenReturn(Arrays.asList(risco1, risco2));
		
		Date dataHistFuncao = DateUtil.criarDataMesAno(1, 1, 2000);
		when(historicoFuncaoManager.findByFuncaoAndData(historicoColaborador.getFuncao().getId(), historicoColaborador.getData())).thenReturn(HistoricoFuncaoFactory.getEntity(funcao, dataHistFuncao, null));
		
		AsoRelatorio asoRelatorio = solicitacaoExameManager.montaRelatorioAso(empresa , solicitacaoExame, TipoRiscoSistema.FUNCAO);
		
		assertEquals(solicitacaoExame.getColaborador(), asoRelatorio.getPessoa());
		assertEquals(asoRelatorio.getGrpRiscoErgonomico(), "Ergonômicos: "+risco1.getDescricao());
		assertEquals(asoRelatorio.getGrpRiscoAcidente(), "Acidentes: "+risco2.getDescricao());
		assertEquals(asoRelatorio.getGrpRiscoBiologico()+asoRelatorio.getGrpRiscoFisico()+asoRelatorio.getGrpRiscoQuimico(), StringUtils.EMPTY);
	}
	
	@Test
	public void testMontaRelatorioAsoConsiderandoRiscoPorAmbienteFuncao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		Funcao funcao = FuncaoFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1000L);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, null, null, null, null, funcao, ambiente, StatusRetornoAC.CONFIRMADO);
		
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(2L);
		solicitacaoExame.setColaborador(colaborador);
		
		Risco risco1 = RiscoFactory.getEntity(1L, "Risco 1", GrupoRisco.ERGONOMICO);
		Risco risco2 = RiscoFactory.getEntity(2L, "Risco 2", GrupoRisco.ACIDENTE);
		Risco risco3 = RiscoFactory.getEntity(3L, "Risco 3", GrupoRisco.QUIMICO);
		Risco risco4 = RiscoFactory.getEntity(4L, "Risco 4", GrupoRisco.FISICO);
		
		when(solicitacaoExameDao.findById(solicitacaoExame.getId())).thenReturn(solicitacaoExame);
		when(exameSolicitacaoExameManager.findBySolicitacaoExame(solicitacaoExame.getId(), true)).thenReturn(null);
		when(exameSolicitacaoExameManager.findBySolicitacaoExame(solicitacaoExame.getId(), false)).thenReturn(null);
		when(historicoColaboradorManager.getHistoricoAtual(colaborador.getId())).thenReturn(null);
		when(historicoColaboradorManager.getHistoricoAtualOuFuturo(colaborador.getId())).thenReturn(historicoColaborador);
		when(riscoAmbienteManager.findRiscosByAmbienteData(historicoColaborador.getAmbiente().getId(), solicitacaoExame.getData())).thenReturn(Arrays.asList(risco1, risco2, risco3));
		when(riscoFuncaoManager.findRiscosByFuncaoData(historicoColaborador.getFuncao().getId(), solicitacaoExame.getData())).thenReturn(Arrays.asList(risco1, risco2, risco4));
		
		Date dataHistFuncao = DateUtil.criarDataMesAno(1, 1, 2000);
		when(historicoFuncaoManager.findByFuncaoAndData(historicoColaborador.getFuncao().getId(), historicoColaborador.getData())).thenReturn(HistoricoFuncaoFactory.getEntity(funcao, dataHistFuncao, null));
		
		AsoRelatorio asoRelatorio = solicitacaoExameManager.montaRelatorioAso(empresa , solicitacaoExame, TipoRiscoSistema.AMBIENTE_FUNCAO);
		
		assertEquals(solicitacaoExame.getColaborador(), asoRelatorio.getPessoa());
		assertEquals(asoRelatorio.getGrpRiscoErgonomico(), "Ergonômicos: "+risco1.getDescricao());
		assertEquals(asoRelatorio.getGrpRiscoAcidente(), "Acidentes: "+risco2.getDescricao());
		assertEquals(asoRelatorio.getGrpRiscoQuimico(), "Químicos: "+risco3.getDescricao());
		assertEquals(asoRelatorio.getGrpRiscoFisico(), "Físicos: "+risco4.getDescricao());
		assertEquals(asoRelatorio.getGrpRiscoBiologico(), StringUtils.EMPTY);
	}

	@Test
	public void testMontaRelatorioAsodesconsiderandoRisco() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		Funcao funcao = FuncaoFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1000L);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, null, null, null, null, funcao, ambiente, StatusRetornoAC.CONFIRMADO);
		
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(2L);
		solicitacaoExame.setColaborador(colaborador);
		
		when(solicitacaoExameDao.findById(solicitacaoExame.getId())).thenReturn(solicitacaoExame);
		when(exameSolicitacaoExameManager.findBySolicitacaoExame(solicitacaoExame.getId(), true)).thenReturn(null);
		when(exameSolicitacaoExameManager.findBySolicitacaoExame(solicitacaoExame.getId(), false)).thenReturn(null);
		when(historicoColaboradorManager.getHistoricoAtual(colaborador.getId())).thenReturn(null);
		when(historicoColaboradorManager.getHistoricoAtualOuFuturo(colaborador.getId())).thenReturn(historicoColaborador);
		
		Date dataHistFuncao = DateUtil.criarDataMesAno(1, 1, 2000);
		when(historicoFuncaoManager.findByFuncaoAndData(historicoColaborador.getFuncao().getId(), historicoColaborador.getData())).thenReturn(HistoricoFuncaoFactory.getEntity(funcao, dataHistFuncao, null));
		
		AsoRelatorio asoRelatorio = solicitacaoExameManager.montaRelatorioAso(empresa , solicitacaoExame, TipoRiscoSistema.NEHUM);
		
		assertEquals(solicitacaoExame.getColaborador(), asoRelatorio.getPessoa());
		assertEquals(asoRelatorio.getGrpRiscoAcidente()+asoRelatorio.getGrpRiscoBiologico()+asoRelatorio.getGrpRiscoErgonomico()+asoRelatorio.getGrpRiscoFisico()+asoRelatorio.getGrpRiscoQuimico(), StringUtils.EMPTY);
	}
	
	@Test
	public void transferirSolicitacaoExamesCandidatoColaborador(){
		assertFalse(true);
	}
}