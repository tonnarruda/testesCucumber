package com.fortes.rh.test.business.pesquisa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManagerImpl;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.exception.AvaliacaoRespondidaException;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.TipoQuestionario;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.SpringUtil;

public class ColaboradorQuestionarioManagerTest extends MockObjectTestCase
{
	private ColaboradorQuestionarioManagerImpl colaboradorQuestionarioManager = new ColaboradorQuestionarioManagerImpl();
	private Mock colaboradorQuestionarioDao;
	private Mock colaboradorManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        colaboradorQuestionarioDao = new Mock(ColaboradorQuestionarioDao.class);
        colaboradorQuestionarioManager.setDao((ColaboradorQuestionarioDao) colaboradorQuestionarioDao.proxy());
        
        colaboradorManager = mock(ColaboradorManager.class);
        colaboradorQuestionarioManager.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());

        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
    }

    public void testFindByQuestionario()
    {
    	Collection<ColaboradorQuestionario> colaboradorQuestionarios = ColaboradorQuestionarioFactory.getCollection();
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	colaboradorQuestionarioDao.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(colaboradorQuestionarios));

    	assertEquals(colaboradorQuestionarios, colaboradorQuestionarioManager.findByQuestionario(questionario.getId()));
    }

    public void testFindByQuestionarioEmpresaRespondida()
    {
    	Collection<ColaboradorQuestionario> colaboradorQuestionarios = ColaboradorQuestionarioFactory.getCollection();
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	
    	colaboradorQuestionarioDao.expects(once()).method("findByQuestionarioEmpresaRespondida").with(eq(questionario.getId()), ANYTHING, ANYTHING, ANYTHING).will(returnValue(colaboradorQuestionarios));
    	
    	assertEquals(colaboradorQuestionarios, colaboradorQuestionarioManager.findByQuestionarioEmpresaRespondida(questionario.getId(), null, null, null));
    }

    public void testSelecionaColaboradoresPercentual()
    {
    	Collection<Colaborador> colaboradores = montaColecaoColaborador();

    	Collection<Colaborador> retorno = colaboradorQuestionarioManager.selecionaColaboradores(colaboradores, '1', 90.0, 0);

    	assertEquals(3, retorno.size());
    }

    public void testSelecionaColaboradoresQuantidade()
    {
    	Collection<Colaborador> colaboradores = montaColecaoColaborador();

    	Collection<Colaborador> retorno = colaboradorQuestionarioManager.selecionaColaboradores(colaboradores, '2', 0, 3);

    	assertEquals(3, retorno.size());
    }

    public void testSelecionaColaboradoresColaboradores()
    {
    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();

    	Collection<Colaborador> retorno = colaboradorQuestionarioManager.selecionaColaboradores(colaboradores, '2', 0, 3);

    	assertEquals(0, retorno.size());
    }

    public void testSelecionaColaboradoresQuantidadeUm()
    {
    	Collection<Colaborador> colaboradores = montaColecaoColaborador();

    	Collection<Colaborador> retorno = colaboradorQuestionarioManager.selecionaColaboradores(colaboradores, '1', 1.0, 0);

    	assertEquals(1, retorno.size());
    }

    public void testFindByPesquisa()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);

    	colaboradorQuestionarioDao.expects(once()).method("findByQuestionario").with(eq(questionario.getId()), eq(colaborador.getId()), ANYTHING).will(returnValue(colaboradorQuestionario));

    	assertEquals(colaboradorQuestionario, colaboradorQuestionarioManager.findByQuestionario(questionario.getId(), colaborador.getId(), null));
    }

    private Collection<Colaborador> montaColecaoColaborador()
    {
    	Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
    	Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
    	Colaborador colaborador3 = ColaboradorFactory.getEntity(3L);
    	Colaborador colaborador4 = ColaboradorFactory.getEntity(4L);

    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	colaboradores.add(colaborador1);
    	colaboradores.add(colaborador2);
    	colaboradores.add(colaborador3);
    	colaboradores.add(colaborador4);

    	return colaboradores;
    }

    public void testSelecionaColaboradoresQuantidadeAplicarPorParteArea()
    {
    	AreaOrganizacional area1 = AreaOrganizacionalFactory.getEntity(1L);
    	Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
    	colaborador1.setAreaOrganizacional(area1);

    	Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
    	colaborador2.setAreaOrganizacional(area1);

    	Colaborador colaborador3 = ColaboradorFactory.getEntity(3L);
    	colaborador3.setAreaOrganizacional(area1);

    	AreaOrganizacional area2 = AreaOrganizacionalFactory.getEntity(2L);
    	Colaborador colaborador4 = ColaboradorFactory.getEntity(4L);
    	colaborador4.setAreaOrganizacional(area2);

    	Colaborador colaborador5 = ColaboradorFactory.getEntity(5L);
    	colaborador5.setAreaOrganizacional(area2);

    	AreaOrganizacional area3 = AreaOrganizacionalFactory.getEntity(3L);
    	Colaborador colaborador6 = ColaboradorFactory.getEntity(6L);
    	colaborador6.setAreaOrganizacional(area3);

    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	colaboradores.add(colaborador1);
    	colaboradores.add(colaborador2);
    	colaboradores.add(colaborador3);
    	colaboradores.add(colaborador4);
    	colaboradores.add(colaborador5);
    	colaboradores.add(colaborador6);

    	Collection<Long> areasIds = new ArrayList<Long>();
    	areasIds.add(area1.getId());
    	areasIds.add(area2.getId());
    	areasIds.add(area3.getId());

    	Collection<Colaborador> retorno = colaboradorQuestionarioManager.selecionaColaboradoresPorParte(colaboradores, '1', areasIds, areasIds, '2', 0.0, 2);

    	assertEquals(5, retorno.size());
    }

    public void testSelecionaColaboradoresQuantidadeAplicarPorParteCargo()
    {
    	Cargo cargo1 = CargoFactory.getEntity(1L);
    	FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity(1L);
    	faixaSalarial1.setCargo(cargo1);
    	Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
    	colaborador1.setFaixaSalarial(faixaSalarial1);

    	Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
    	colaborador2.setFaixaSalarial(faixaSalarial1);

    	Colaborador colaborador3 = ColaboradorFactory.getEntity(3L);
    	colaborador3.setFaixaSalarial(faixaSalarial1);

    	Cargo cargo2 = CargoFactory.getEntity(2L);
    	FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity(2L);
    	faixaSalarial2.setCargo(cargo2);
    	Colaborador colaborador4 = ColaboradorFactory.getEntity(4L);
    	colaborador4.setFaixaSalarial(faixaSalarial2);

    	Colaborador colaborador5 = ColaboradorFactory.getEntity(5L);
    	colaborador5.setFaixaSalarial(faixaSalarial2);

    	Cargo cargo3 = CargoFactory.getEntity(3L);
    	FaixaSalarial faixaSalarial3 = FaixaSalarialFactory.getEntity(3L);
    	faixaSalarial3.setCargo(cargo3);
    	Colaborador colaborador6 = ColaboradorFactory.getEntity(6L);
    	colaborador6.setFaixaSalarial(faixaSalarial3);

    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	colaboradores.add(colaborador1);
    	colaboradores.add(colaborador2);
    	colaboradores.add(colaborador3);
    	colaboradores.add(colaborador4);
    	colaboradores.add(colaborador5);
    	colaboradores.add(colaborador6);

    	Collection<Long> cargosIds = new ArrayList<Long>();
    	cargosIds.add(cargo1.getId());
    	cargosIds.add(cargo2.getId());
    	cargosIds.add(cargo3.getId());

    	Collection<Colaborador> retorno = colaboradorQuestionarioManager.selecionaColaboradoresPorParte(colaboradores, '2', null, cargosIds, '2', 0.0, 2);

    	assertEquals(5, retorno.size());
    }

    public void testSaveColaboradorQuestionario()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	questionario.setDataInicio(new Date());
    	questionario.setDataFim(new Date());
    	questionario.setEmpresa(empresa);

    	Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
    	Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);

    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
    	colaboradorQuestionario.setColaborador(colaborador1);

    	Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
    	colaboradorQuestionarios.add(colaboradorQuestionario);

    	Long[] colaboradoresIds = new Long[]{colaborador1.getId(), colaborador2.getId()};


    	colaboradorQuestionarioDao.expects(atLeastOnce()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(colaboradorQuestionarios));
    	colaboradorQuestionarioDao.expects(atLeastOnce()).method("save").with(ANYTHING);

		try
		{
			colaboradorQuestionarioManager.save(questionario, colaboradoresIds, null);
		}
		catch (Exception e)
		{
		}
    }

	public void testFindColaboradorComEntrevistaDeDesligamento()
    {
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);

    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	questionario.setTipo(TipoQuestionario.getENTREVISTA());

    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
    	colaboradorQuestionario.setColaborador(colaborador);
    	colaboradorQuestionario.setQuestionario(questionario);

    	colaboradorQuestionarioDao.expects(once()).method("findColaboradorComEntrevistaDeDesligamento").with(ANYTHING).will(returnValue(colaboradorQuestionario));

    	ColaboradorQuestionario retorno = colaboradorQuestionarioManager.findColaboradorComEntrevistaDeDesligamento(colaborador.getId());

    	assertEquals(colaboradorQuestionario.getId(), retorno.getId());
    }
	
	public void testFindByAvaliacaoDesempenho()
	{
    	Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
    	colaboradorQuestionarios.add(ColaboradorQuestionarioFactory.getEntity(1L));
    	colaboradorQuestionarios.add(ColaboradorQuestionarioFactory.getEntity(2L));
		colaboradorQuestionarioDao.expects(once()).method("findByAvaliacaoDesempenho").with(eq(1L), eq(null)).will(returnValue(colaboradorQuestionarios));
		
		assertEquals(2,colaboradorQuestionarioManager.findByAvaliacaoDesempenho(1L, null).size());
	}
	
	public void testSaveComAvaliacaoDesempenhoEParticipantes()
	{
		Collection<Colaborador> participantes = new ArrayList<Colaborador>();
		participantes.add(ColaboradorFactory.getEntity(1L));
		participantes.add(ColaboradorFactory.getEntity(2L));
		
		Long[] colaboradorIds = new Long[]{1L,30L};
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		
		colaboradorManager.expects(once()).method("findParticipantesDistinctByAvaliacaoDesempenho").with(eq(1L), eq(false), eq(null)).will(returnValue(participantes));
		
		colaboradorQuestionarioDao.expects(once()).method("save").with(ANYTHING);
		
		colaboradorQuestionarioManager.save(avaliacaoDesempenho, colaboradorIds, false);
	}
	
	
	public void testAssociarParticipantes() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		
		Colaborador avaliado1 = ColaboradorFactory.getEntity(11L);
		Colaborador avaliado2 = ColaboradorFactory.getEntity(12L);
		Colaborador avaliado3JaAssociado = ColaboradorFactory.getEntity(13L);

		Collection<Colaborador> avaliados = Arrays.asList(avaliado1, avaliado2, avaliado3JaAssociado);

		Colaborador avaliador1 = ColaboradorFactory.getEntity(26L);
		Colaborador avaliador2JaAssociado = ColaboradorFactory.getEntity(27L);
		Colaborador avaliador3 = ColaboradorFactory.getEntity(28L);
		
		Collection<Colaborador> avaliadores = Arrays.asList(avaliador1, avaliador2JaAssociado, avaliador3);

		ColaboradorQuestionario colaboradorQuestionario = new ColaboradorQuestionario(avaliacaoDesempenho, avaliado3JaAssociado.getId(), avaliador2JaAssociado.getId());
		
		Collection<ColaboradorQuestionario> associados = new ArrayList<ColaboradorQuestionario>();
		associados.add(colaboradorQuestionario);
		
		colaboradorQuestionarioDao.expects(once()).method("findByAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId()), eq(true)).will(returnValue(associados));
		
		colaboradorQuestionarioDao.expects(once()).method("saveOrUpdate").with(ANYTHING);
		colaboradorQuestionarioDao.expects(once()).method("removeParticipantesSemAssociacao").with(ANYTHING);
		
		Collection<ColaboradorQuestionario> novosAssociados = colaboradorQuestionarioManager.associarParticipantes(avaliacaoDesempenho, avaliados, avaliadores);
		assertEquals(8, novosAssociados.size());
		
		for (ColaboradorQuestionario associado : novosAssociados) 
		{
			assertFalse(associado.getColaborador().equals(avaliado3JaAssociado) && associado.getAvaliador().equals(avaliador2JaAssociado));
		}
		
	}
	
	public void testAssociarParticipantesComAutoAvaliacao() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		
		Colaborador avaliado1 = ColaboradorFactory.getEntity(11L);
		Colaborador avaliado2 = ColaboradorFactory.getEntity(12L);
		
		Collection<Colaborador> avaliados = Arrays.asList(avaliado1, avaliado2);
		
		Colaborador avaliador1 = ColaboradorFactory.getEntity(11L);
		Colaborador avaliador2 = ColaboradorFactory.getEntity(28L);
		
		Collection<Colaborador> avaliadores = Arrays.asList(avaliador1, avaliador2);
		
		Collection<ColaboradorQuestionario> associados = new ArrayList<ColaboradorQuestionario>();
		
		colaboradorQuestionarioDao.expects(once()).method("findByAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId()), eq(true)).will(returnValue(associados));
		
		colaboradorQuestionarioDao.expects(once()).method("saveOrUpdate").with(ANYTHING);
		colaboradorQuestionarioDao.expects(once()).method("removeParticipantesSemAssociacao").with(ANYTHING);
		
		Collection<ColaboradorQuestionario> novosAssociados = colaboradorQuestionarioManager.associarParticipantes(avaliacaoDesempenho, avaliados, avaliadores);
		assertEquals(3, novosAssociados.size());
		
		for (ColaboradorQuestionario associado : novosAssociados) 
		{
			assertFalse(associado.getColaborador().equals(associado.getAvaliador()));
		}
	}
	
	public void testDesassociar() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		
		Colaborador avaliado1 = ColaboradorFactory.getEntity(11L);
		Colaborador avaliado2 = ColaboradorFactory.getEntity(12L);
		
		Collection<Colaborador> avaliados = Arrays.asList(avaliado1, avaliado2);
		
		Colaborador avaliador1 = ColaboradorFactory.getEntity(11L);
		Colaborador avaliador2 = ColaboradorFactory.getEntity(28L);
		
		Collection<Colaborador> avaliadores = Arrays.asList(avaliador1, avaliador2);
		
		colaboradorManager.expects(once()).method("findParticipantesDistinctByAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId()), eq(true), eq(false)).will(returnValue(avaliados));
		colaboradorManager.expects(once()).method("findParticipantesDistinctByAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId()), eq(false), eq(false)).will(returnValue(avaliadores));
		
		colaboradorQuestionarioDao.expects(once()).method("saveOrUpdate").with(ANYTHING);
		colaboradorQuestionarioDao.expects(once()).method("removeAssociadosSemResposta").with(ANYTHING);
		
		Collection<ColaboradorQuestionario> desassociados = colaboradorQuestionarioManager.desassociarParticipantes(avaliacaoDesempenho);
		
		assertEquals(4, desassociados.size());
	}
	
	public void testClonarComAvaliacaoLiberada() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		AvaliacaoDesempenho avaliacaoDesempenhoClone = AvaliacaoDesempenhoFactory.getEntity(2L);
		
		colaboradorManager.expects(once()).method("findParticipantesDistinctByAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId()), eq(true), eq(null)).will(returnValue(new ArrayList<Colaborador>()));
		colaboradorManager.expects(once()).method("findParticipantesDistinctByAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId()), eq(false), eq(null)).will(returnValue(new ArrayList<Colaborador>()));
		colaboradorQuestionarioDao.expects(once()).method("saveOrUpdate").with(ANYTHING);
		
		colaboradorQuestionarioManager.clonarParticipantes(avaliacaoDesempenho, avaliacaoDesempenhoClone);
	}

	public void testRemoveParticipanteDeAvaliacaoDesempenho() throws Exception
	{
		Long participanteId=33L;
		Long avaliacaoDesempenhoId=1L;
		
		colaboradorQuestionarioDao.expects(once()).method("findRespondidasByAvaliacaoDesempenho").with(ANYTHING, eq(avaliacaoDesempenhoId), eq(true)).will(returnValue(new ArrayList<ColaboradorQuestionario>()));
		colaboradorQuestionarioDao.expects(once()).method("removeByParticipante").with(eq(avaliacaoDesempenhoId), ANYTHING, eq(true));
		
		colaboradorQuestionarioManager.remove(new Long[]{participanteId}, avaliacaoDesempenhoId, true);
	}
	
	public void testRemoveParticipanteDeAvaliacaoDesempenhoExceptionAvaliacaoRespondida() 
	{
		Long participanteId=33L;
		Long avaliacaoDesempenhoId=1L;
		
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
		colaboradorQuestionarios.add(ColaboradorQuestionarioFactory.getEntity(10L)); 
		ColaboradorQuestionario colaboradorQuestionarioRespondida = ColaboradorQuestionarioFactory.getEntity(11L);
		colaboradorQuestionarioRespondida.setRespondida(true);
		colaboradorQuestionarios.add(colaboradorQuestionarioRespondida);
		
		colaboradorQuestionarioDao.expects(once()).method("findRespondidasByAvaliacaoDesempenho").with(ANYTHING, eq(avaliacaoDesempenhoId), eq(true)).will(returnValue(colaboradorQuestionarios));
		 
		Exception exception=null;
		
		try {
			colaboradorQuestionarioManager.remove(new Long[]{participanteId}, avaliacaoDesempenhoId, true);
		} catch (Exception e) {
			exception = e;
		}
		
		assertTrue(exception instanceof AvaliacaoRespondidaException);
	}
	
	public void testVerifyTemParticipantesAssociados()
    {
		colaboradorQuestionarioDao.expects(once()).method("getCountParticipantesAssociados").with(eq(1L)).will(returnValue(0));
    	assertFalse(colaboradorQuestionarioManager.verifyTemParticipantesAssociados(1L));
    	colaboradorQuestionarioDao.expects(once()).method("getCountParticipantesAssociados").with(eq(1L)).will(returnValue(1));
    	assertTrue(colaboradorQuestionarioManager.verifyTemParticipantesAssociados(1L));
    }
	
	public void testFindByColaboradorAndAvaliacaoDesempenho()
	{
		colaboradorQuestionarioDao.expects(once()).method("findByColaboradorAndAvaliacaoDesempenho").with(eq(1L),eq(1L),eq(true),eq(false)).will(returnValue(new ArrayList<ColaboradorQuestionario>()));
		assertNotNull(colaboradorQuestionarioManager.findByColaboradorAndAvaliacaoDesempenho(1L, 1L, true, false));
	}
	
    public void testFindAvaliadosByAvaliador()
    {
    	Collection<ColaboradorQuestionario> avaliados = new ArrayList<ColaboradorQuestionario>();
    	
    	colaboradorQuestionarioDao.expects(once()).method("findAvaliadosByAvaliador").with(eq(1L), eq(1000L), eq(false), eq(true)).will(returnValue(avaliados));
    	
    	assertNotNull(colaboradorQuestionarioManager.findAvaliadosByAvaliador(1L, 1000L, false, true));
    }
    
    public void testGetPerformance()
    {
		Long avaliacaoDesempenhoId = 1L;
		Collection<Long> avaliados = Arrays.asList(1L);
		
		colaboradorQuestionarioDao.expects(once()).method("getPerformance").with(eq(avaliados), eq(avaliacaoDesempenhoId)).will(returnValue(new ArrayList<ColaboradorQuestionario>()));
		
		assertNotNull(colaboradorQuestionarioManager.getPerformance(avaliados, avaliacaoDesempenhoId));
    }
    
    public void testExisteMesmoModeloAvaliacaoEmDesempnhoEPeriodoExperiencia()
    {
    	ColaboradorQuestionario colaboradorQuestionario1 = criaColaboradorQuestionarioParatestExisteMesmoModeloAvaliacaoEmDesempnhoEPeriodoExperiencia(0,1);
    	ColaboradorQuestionario colaboradorQuestionario2 = criaColaboradorQuestionarioParatestExisteMesmoModeloAvaliacaoEmDesempnhoEPeriodoExperiencia(0,3);
    	ColaboradorQuestionario colaboradorQuestionario3 = criaColaboradorQuestionarioParatestExisteMesmoModeloAvaliacaoEmDesempnhoEPeriodoExperiencia(2,0);
    	
    	Collection<ColaboradorQuestionario> colaboradorQuestionarios = Arrays.asList(colaboradorQuestionario1, colaboradorQuestionario2, colaboradorQuestionario3);
    	Long avaliacaoId = 1L;
    	
    	colaboradorQuestionarioDao.expects(atLeastOnce()).method("findByAvaliacaoComQtdDesempenhoEPeriodoExperiencia").with(eq(avaliacaoId)).will(returnValue(colaboradorQuestionarios));
    	
    	assertFalse("Não existe uma mesma avaliação para Desempenho e Per. Experiência",colaboradorQuestionarioManager.existeMesmoModeloAvaliacaoEmDesempenhoEPeriodoExperiencia(avaliacaoId));
    	
    	ColaboradorQuestionario colaboradorQuestionario4 = criaColaboradorQuestionarioParatestExisteMesmoModeloAvaliacaoEmDesempnhoEPeriodoExperiencia(2,1);
    	colaboradorQuestionarios = Arrays.asList(colaboradorQuestionario1, colaboradorQuestionario2, colaboradorQuestionario3, colaboradorQuestionario4);
    	colaboradorQuestionarioDao.expects(atLeastOnce()).method("findByAvaliacaoComQtdDesempenhoEPeriodoExperiencia").with(eq(avaliacaoId)).will(returnValue(colaboradorQuestionarios));

    	assertTrue("Existe uma mesma avaliação para Desempenho e Per. Experiência",colaboradorQuestionarioManager.existeMesmoModeloAvaliacaoEmDesempenhoEPeriodoExperiencia(avaliacaoId));
    }

	private ColaboradorQuestionario criaColaboradorQuestionarioParatestExisteMesmoModeloAvaliacaoEmDesempnhoEPeriodoExperiencia(Integer qtdAvaliacaoDesempenho, Integer qtdPeriodoExperiencia)
	{
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
    	colaboradorQuestionario.setQtdAvaliacaoDesempenho(qtdAvaliacaoDesempenho);
    	colaboradorQuestionario.setQtdPeriodoExperiencia(qtdPeriodoExperiencia);
    	
		return colaboradorQuestionario;
	}
}
