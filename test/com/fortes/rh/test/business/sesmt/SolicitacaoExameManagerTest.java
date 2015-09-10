package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.sesmt.ExameSolicitacaoExameManager;
import com.fortes.rh.business.sesmt.RealizacaoExameManager;
import com.fortes.rh.business.sesmt.RiscoAmbienteManager;
import com.fortes.rh.business.sesmt.RiscoFuncaoManager;
import com.fortes.rh.business.sesmt.SolicitacaoExameManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoExameDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.GrupoRisco;
import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.model.dicionario.TipoPessoa;
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
import com.fortes.rh.test.factory.sesmt.RiscoFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoExameFactory;
import com.fortes.rh.test.util.mockObjects.MockTransactionStatus;
import com.fortes.rh.util.DateUtil;

public class SolicitacaoExameManagerTest extends MockObjectTestCase
{
	private SolicitacaoExameManagerImpl solicitacaoExameManager = new SolicitacaoExameManagerImpl();
	private Mock exameSolicitacaoExameManager;
	private Mock realizacaoExameManager;
	private Mock transactionManager;
	private Mock historicoColaboradorManager;
	private Mock solicitacaoExameDao;
	private Mock riscoAmbienteManager;
	private Mock riscoFuncaoManager;

	protected void setUp() throws Exception
    {
        exameSolicitacaoExameManager = new Mock(ExameSolicitacaoExameManager.class);
        solicitacaoExameManager.setExameSolicitacaoExameManager((ExameSolicitacaoExameManager)exameSolicitacaoExameManager.proxy());
        
        realizacaoExameManager = new Mock(RealizacaoExameManager.class);
        solicitacaoExameManager.setRealizacaoExameManager((RealizacaoExameManager)realizacaoExameManager.proxy());
        
        solicitacaoExameDao = new Mock(SolicitacaoExameDao.class);
        solicitacaoExameManager.setDao((SolicitacaoExameDao) solicitacaoExameDao.proxy());
        
        historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);
        solicitacaoExameManager.setHistoricoColaboradorManager((HistoricoColaboradorManager) historicoColaboradorManager.proxy());

        riscoAmbienteManager = new Mock(RiscoAmbienteManager.class);
        solicitacaoExameManager.setRiscoAmbienteManager((RiscoAmbienteManager) riscoAmbienteManager.proxy());
        
        riscoFuncaoManager = new Mock(RiscoFuncaoManager.class);
        solicitacaoExameManager.setRiscoFuncaoManager((RiscoFuncaoManager) riscoFuncaoManager.proxy());
        
        transactionManager = new Mock(PlatformTransactionManager.class);
        solicitacaoExameManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
    }
	
	public void testGetCount()
	{
		Collection<SolicitacaoExame> colecao = new ArrayList<SolicitacaoExame>();
		colecao.add(new SolicitacaoExame());
		solicitacaoExameDao.expects(once()).method("getCount").will(returnValue(1));

		assertEquals(1, solicitacaoExameManager.getCount(1L, new Date(), new Date(), TipoPessoa.COLABORADOR, null, null, null, null, null).intValue());
	}

	public void testFindAllSelect()
	{
		Collection<SolicitacaoExame> colecao = new ArrayList<SolicitacaoExame>();
		colecao.add(new SolicitacaoExame());
		solicitacaoExameDao.expects(once()).method("findAllSelect").will(returnValue(colecao));

		assertEquals(colecao, solicitacaoExameManager.findAllSelect(0, 20, 4L, new Date(), new Date(), TipoPessoa.CANDIDATO, null, null, null, null, null));
	}
	
	public void testFindByCandidatoOuColaborador()
	{
		TipoPessoa vinculo = TipoPessoa.TODOS;
		Long candidatoOuColaboradorId=99L;
		
		solicitacaoExameDao.expects(once()).method("findByCandidatoOuColaborador").with(eq(TipoPessoa.TODOS),eq(99L),eq(MotivoSolicitacaoExame.ADMISSIONAL));
		
		solicitacaoExameManager.findByCandidatoOuColaborador(vinculo, candidatoOuColaboradorId, MotivoSolicitacaoExame.ADMISSIONAL);
	}

	public void testSave()
	{
		String[] exameIds = new String[]{"1","2"};
		String[] selectClinicas = new String[]{"",""};
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(1L);
		Integer[] periodicidades = new Integer[]{12,6};

		solicitacaoExameDao.expects(once()).method("save").with(eq(solicitacaoExame)).isVoid();
		exameSolicitacaoExameManager.expects(once()).method("save").with(eq(solicitacaoExame), eq(exameIds), eq(selectClinicas),eq(periodicidades)).isVoid();

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

	public void testSaveException()
	{
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(1L);

		solicitacaoExameDao.expects(once()).method("save").with(eq(solicitacaoExame)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(solicitacaoExame.getId(),""))));

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

	public void testUpdate()
	{
		String[] exameIds = new String[]{"1","2"};
		String[] selectClinicas = new String[]{"",""};
		Integer[] periodicidades = new Integer[]{12,6};

		Collection<Long> colecaoRealizacaoExameIds = new ArrayList<Long>();
		Long[] realizacaoExameIds = new Long[0];

		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(1L);

		solicitacaoExameDao.expects(once()).method("update").with(eq(solicitacaoExame)).isVoid();
		realizacaoExameManager.expects(once()).method("findIdsBySolicitacaoExame").with(eq(solicitacaoExame.getId())).will(returnValue(colecaoRealizacaoExameIds));
		exameSolicitacaoExameManager.expects(once()).method("removeAllBySolicitacaoExame").with(eq(solicitacaoExame.getId())).isVoid();
		realizacaoExameManager.expects(once()).method("remove").with(eq(realizacaoExameIds)).isVoid();
		exameSolicitacaoExameManager.expects(once()).method("save").with(eq(solicitacaoExame), eq(exameIds), eq(selectClinicas),eq(periodicidades)).isVoid();

		solicitacaoExameManager.update(solicitacaoExame, exameIds, selectClinicas, periodicidades);
	}

	public void testRemove()
	{
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(1L);
		transactionManager.expects(atLeastOnce()).method("getTransaction").will(returnValue(new MockTransactionStatus()));

		Collection<Long> colecaoRealizacaoExameIds = new ArrayList<Long>();
		Long[] realizacaoExameIds = new Long[0];

		realizacaoExameManager.expects(once()).method("findIdsBySolicitacaoExame").with(eq(solicitacaoExame.getId())).will(returnValue(colecaoRealizacaoExameIds));

		exameSolicitacaoExameManager.expects(once()).method("removeAllBySolicitacaoExame").with(eq(solicitacaoExame.getId())).isVoid();

		realizacaoExameManager.expects(once()).method("remove").with(eq(realizacaoExameIds)).isVoid();
		solicitacaoExameDao.expects(once()).method("remove").with(eq(solicitacaoExame.getId())).isVoid();

		transactionManager.expects(atLeastOnce()).method("commit");

		solicitacaoExameManager.remove(solicitacaoExame.getId());
	}
	
	public void testRemoveException()
	{
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(1L);
		transactionManager.expects(atLeastOnce()).method("getTransaction").will(returnValue(new MockTransactionStatus()));
		realizacaoExameManager.expects(once()).method("findIdsBySolicitacaoExame").with(eq(solicitacaoExame.getId())).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(solicitacaoExame.getId(),""))));
		transactionManager.expects(once()).method("rollback");
		
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
	
	public void testGetRelatorioAtendimentos() throws Exception
	{
		Date inicio = DateUtil.criarDataMesAno(01, 01, 2010);
		Date fim = DateUtil.criarDataMesAno(01, 05, 2010);
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity();
		solicitacaoExame.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		
		Collection<SolicitacaoExame> solicitacaoExames = new ArrayList<SolicitacaoExame>();
		solicitacaoExames.add(solicitacaoExame);
		
		solicitacaoExameDao.expects(once()).method("findAtendimentosMedicos").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(solicitacaoExames));
		
		boolean agruparPorMotivo=false;
		boolean ordenarPorNome=false;
		assertEquals(1,solicitacaoExameManager.getRelatorioAtendimentos(inicio, fim, solicitacaoExame, empresa, agruparPorMotivo, ordenarPorNome, new String[]{}, 'T').size());
	}
	
	public void testGetRelatorioAtendimentosColecaoVazia()
	{
		Date inicio = DateUtil.criarDataMesAno(01, 01, 2010);
		Date fim = DateUtil.criarDataMesAno(01, 05, 2010);
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity();
		solicitacaoExame.setMotivo(MotivoSolicitacaoExame.PERIODICO);
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		
		solicitacaoExameDao.expects(once()).method("findAtendimentosMedicos").will(returnValue(new ArrayList<SolicitacaoExame>()));
		
		Exception exception=null;
		try {
			solicitacaoExameManager.getRelatorioAtendimentos(inicio, fim, solicitacaoExame, empresa, false, false, new String[]{}, 'T');
		} catch (ColecaoVaziaException e) {
			exception=e;
		}
		
		assertNotNull(exception);
	}
	
	public void testTransferir()
	{
		Long empresaId=1L, candidatoId=100L, colaboradorId=1302L;
		
		solicitacaoExameDao.expects(once()).method("transferirCandidatoToColaborador").isVoid();
		
		solicitacaoExameManager.transferirCandidatoToColaborador(empresaId, candidatoId, colaboradorId);
	}
	
	public void testMontaRelatorioAsoSemSolicitacaoExame() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Exception exception = null;
		try {
			solicitacaoExameManager.montaRelatorioAso(empresa , null, "N");
		} catch (ColecaoVaziaException e) {
			exception=e;
		}
		
		assertNotNull(exception);
	}
	
	public void testMontaRelatorioAsoConsiderandoRiscoPorAmbiente() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		Funcao funcao = FuncaoFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1000L);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, null, null, null, ambiente, funcao);
		
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(2L);
		solicitacaoExame.setColaborador(colaborador);
		
		Risco risco1 = RiscoFactory.getEntity(1L, "Risco 1", GrupoRisco.FISICO);
		Risco risco2 = RiscoFactory.getEntity(2L, "Risco 2", GrupoRisco.ACIDENTE);
		
		solicitacaoExameDao.expects(once()).method("findById").will(returnValue(solicitacaoExame));
		exameSolicitacaoExameManager.expects(once()).method("findBySolicitacaoExame").with(eq(solicitacaoExame.getId()), eq(true)).will(returnValue(null));
		exameSolicitacaoExameManager.expects(once()).method("findBySolicitacaoExame").with(eq(solicitacaoExame.getId()), eq(false)).will(returnValue(null));
		historicoColaboradorManager.expects(once()).method("getHistoricoAtual").will(returnValue(null));
		historicoColaboradorManager.expects(once()).method("getHistoricoAtualOuFuturo").will(returnValue(historicoColaborador));
		riscoAmbienteManager.expects(once()).method("findRiscosByAmbienteData").with(eq(historicoColaborador.getAmbiente().getId()), eq(solicitacaoExame.getData())).will(returnValue(Arrays.asList(risco1, risco2)));
		
		AsoRelatorio asoRelatorio = solicitacaoExameManager.montaRelatorioAso(empresa , solicitacaoExame, "A");
		
		assertEquals(solicitacaoExame.getColaborador(), asoRelatorio.getPessoa());
		assertEquals(asoRelatorio.getGrpRiscoFisico(), "Físicos: "+risco1.getDescricao());
		assertEquals(asoRelatorio.getGrpRiscoAcidente(), "Acidentes: "+risco2.getDescricao());
		assertEquals(asoRelatorio.getGrpRiscoBiologico()+asoRelatorio.getGrpRiscoErgonomico()+asoRelatorio.getGrpRiscoQuimico(), StringUtils.EMPTY);
	}
	
	
	public void testMontaRelatorioAsoConsiderandoRiscoPorFuncao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Funcao funcao = FuncaoFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1000L);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, null, null, null, null, funcao);
		
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(2L);
		solicitacaoExame.setColaborador(colaborador);
		
		Risco risco1 = RiscoFactory.getEntity(1L, "Risco 1", GrupoRisco.ERGONOMICO);
		Risco risco2 = RiscoFactory.getEntity(2L, "Risco 2", GrupoRisco.ACIDENTE);
		
		solicitacaoExameDao.expects(once()).method("findById").will(returnValue(solicitacaoExame));
		exameSolicitacaoExameManager.expects(once()).method("findBySolicitacaoExame").with(eq(solicitacaoExame.getId()), eq(true)).will(returnValue(null));
		exameSolicitacaoExameManager.expects(once()).method("findBySolicitacaoExame").with(eq(solicitacaoExame.getId()), eq(false)).will(returnValue(null));
		historicoColaboradorManager.expects(once()).method("getHistoricoAtual").will(returnValue(null));
		historicoColaboradorManager.expects(once()).method("getHistoricoAtualOuFuturo").will(returnValue(historicoColaborador));
		riscoFuncaoManager.expects(once()).method("findRiscosByFuncaoData").with(eq(historicoColaborador.getFuncao().getId()), eq(solicitacaoExame.getData())).will(returnValue(Arrays.asList(risco1, risco2)));
		
		AsoRelatorio asoRelatorio = solicitacaoExameManager.montaRelatorioAso(empresa , solicitacaoExame, "F");
		
		assertEquals(solicitacaoExame.getColaborador(), asoRelatorio.getPessoa());
		assertEquals(asoRelatorio.getGrpRiscoErgonomico(), "Ergonômicos: "+risco1.getDescricao());
		assertEquals(asoRelatorio.getGrpRiscoAcidente(), "Acidentes: "+risco2.getDescricao());
		assertEquals(asoRelatorio.getGrpRiscoBiologico()+asoRelatorio.getGrpRiscoFisico()+asoRelatorio.getGrpRiscoQuimico(), StringUtils.EMPTY);
	}
	
	public void testMontaRelatorioAsoConsiderandoRiscoPorAmbienteFuncao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		Funcao funcao = FuncaoFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1000L);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, null, null, null, ambiente, funcao);
		
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(2L);
		solicitacaoExame.setColaborador(colaborador);
		
		Risco risco1 = RiscoFactory.getEntity(1L, "Risco 1", GrupoRisco.ERGONOMICO);
		Risco risco2 = RiscoFactory.getEntity(2L, "Risco 2", GrupoRisco.ACIDENTE);
		Risco risco3 = RiscoFactory.getEntity(3L, "Risco 3", GrupoRisco.QUIMICO);
		Risco risco4 = RiscoFactory.getEntity(4L, "Risco 4", GrupoRisco.FISICO);
		
		solicitacaoExameDao.expects(once()).method("findById").will(returnValue(solicitacaoExame));
		exameSolicitacaoExameManager.expects(once()).method("findBySolicitacaoExame").with(eq(solicitacaoExame.getId()), eq(true)).will(returnValue(null));
		exameSolicitacaoExameManager.expects(once()).method("findBySolicitacaoExame").with(eq(solicitacaoExame.getId()), eq(false)).will(returnValue(null));
		historicoColaboradorManager.expects(once()).method("getHistoricoAtual").will(returnValue(null));
		historicoColaboradorManager.expects(once()).method("getHistoricoAtualOuFuturo").will(returnValue(historicoColaborador));
		riscoAmbienteManager.expects(once()).method("findRiscosByAmbienteData").with(eq(historicoColaborador.getAmbiente().getId()), eq(solicitacaoExame.getData())).will(returnValue(Arrays.asList(risco1, risco2, risco3)));
		riscoFuncaoManager.expects(once()).method("findRiscosByFuncaoData").with(eq(historicoColaborador.getFuncao().getId()), eq(solicitacaoExame.getData())).will(returnValue(Arrays.asList(risco1, risco2, risco4)));
		
		AsoRelatorio asoRelatorio = solicitacaoExameManager.montaRelatorioAso(empresa , solicitacaoExame, "AF");
		
		assertEquals(solicitacaoExame.getColaborador(), asoRelatorio.getPessoa());
		assertEquals(asoRelatorio.getGrpRiscoErgonomico(), "Ergonômicos: "+risco1.getDescricao());
		assertEquals(asoRelatorio.getGrpRiscoAcidente(), "Acidentes: "+risco2.getDescricao());
		assertEquals(asoRelatorio.getGrpRiscoQuimico(), "Químicos: "+risco3.getDescricao());
		assertEquals(asoRelatorio.getGrpRiscoFisico(), "Físicos: "+risco4.getDescricao());
		assertEquals(asoRelatorio.getGrpRiscoBiologico(), StringUtils.EMPTY);
	}
	
	public void testMontaRelatorioAsodesconsiderandoRisco() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Ambiente ambiente = AmbienteFactory.getEntity(1L);
		Funcao funcao = FuncaoFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1000L);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, null, null, null, ambiente, funcao);
		
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(2L);
		solicitacaoExame.setColaborador(colaborador);
		
		solicitacaoExameDao.expects(once()).method("findById").will(returnValue(solicitacaoExame));
		exameSolicitacaoExameManager.expects(once()).method("findBySolicitacaoExame").with(eq(solicitacaoExame.getId()), eq(true)).will(returnValue(null));
		exameSolicitacaoExameManager.expects(once()).method("findBySolicitacaoExame").with(eq(solicitacaoExame.getId()), eq(false)).will(returnValue(null));
		historicoColaboradorManager.expects(once()).method("getHistoricoAtual").will(returnValue(null));
		historicoColaboradorManager.expects(once()).method("getHistoricoAtualOuFuturo").will(returnValue(historicoColaborador));
		
		AsoRelatorio asoRelatorio = solicitacaoExameManager.montaRelatorioAso(empresa , solicitacaoExame, "N");
		
		assertEquals(solicitacaoExame.getColaborador(), asoRelatorio.getPessoa());
		assertEquals(asoRelatorio.getGrpRiscoAcidente()+asoRelatorio.getGrpRiscoBiologico()+asoRelatorio.getGrpRiscoErgonomico()+asoRelatorio.getGrpRiscoFisico()+asoRelatorio.getGrpRiscoQuimico(), StringUtils.EMPTY);
	}
}