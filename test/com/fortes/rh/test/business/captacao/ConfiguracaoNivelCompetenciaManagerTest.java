package com.fortes.rh.test.business.captacao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.core.Constraint;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.ConfigHistoricoNivelManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaCandidatoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaColaboradorManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaCriterioManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManagerImpl;
import com.fortes.rh.business.captacao.CriterioAvaliacaoCompetenciaManager;
import com.fortes.rh.business.captacao.NivelCompetenciaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.ColaboradorRespostaManager;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.RelatorioAnaliseDesempenhoColaborador;
import com.fortes.rh.model.avaliacao.ResultadoCompetencia;
import com.fortes.rh.model.avaliacao.ResultadoCompetenciaColaborador;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCandidato;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaVO;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.CriterioAvaliacaoCompetencia;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.captacao.MatrizCompetenciaNivelConfiguracao;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.business.MockObjectTestCaseManager;
import com.fortes.rh.test.business.TesteAutomaticoManager;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfigHistoricoNivelFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaCandidatoFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaHistoricoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

public class ConfiguracaoNivelCompetenciaManagerTest extends MockObjectTestCaseManager<ConfiguracaoNivelCompetenciaManagerImpl> implements TesteAutomaticoManager
{
	private Mock nivelCompetenciaManager;
	private Mock colaboradorRespostaManager;
	private Mock configHistoricoNivelManager;
	private Mock candidatoSolicitacaoManager;
	private Mock colaboradorQuestionarioManager;
	private Mock configuracaoNivelCompetenciaDao;
	private Mock criterioAvaliacaoCompetenciaManager;
	private Mock configuracaoNivelCompetenciaColaboradorManager;
	private Mock configuracaoNivelCompetenciaCriterioManager;
	private Mock configuracaoNivelCompetenciaFaixaSalarialManager;
	private Mock configuracaoNivelCompetenciaCandidatoManager;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        
        manager = new ConfiguracaoNivelCompetenciaManagerImpl();
        configuracaoNivelCompetenciaDao = new Mock(ConfiguracaoNivelCompetenciaDao.class);
        manager.setDao((ConfiguracaoNivelCompetenciaDao) configuracaoNivelCompetenciaDao.proxy());

        nivelCompetenciaManager = new Mock(NivelCompetenciaManager.class);
        manager.setNivelCompetenciaManager((NivelCompetenciaManager) nivelCompetenciaManager.proxy());

        candidatoSolicitacaoManager = new Mock(CandidatoSolicitacaoManager.class);
        manager.setCandidatoSolicitacaoManager((CandidatoSolicitacaoManager) candidatoSolicitacaoManager.proxy());
        
        configuracaoNivelCompetenciaColaboradorManager = new Mock(ConfiguracaoNivelCompetenciaColaboradorManager.class);
        manager.setConfiguracaoNivelCompetenciaColaboradorManager((ConfiguracaoNivelCompetenciaColaboradorManager) configuracaoNivelCompetenciaColaboradorManager.proxy());
        
        configuracaoNivelCompetenciaCriterioManager = new Mock(ConfiguracaoNivelCompetenciaCriterioManager.class);
        manager.setConfiguracaoNivelCompetenciaCriterioManager((ConfiguracaoNivelCompetenciaCriterioManager) configuracaoNivelCompetenciaCriterioManager.proxy());
        
        criterioAvaliacaoCompetenciaManager = new Mock(CriterioAvaliacaoCompetenciaManager.class);
        manager.setCriterioAvaliacaoCompetenciaManager((CriterioAvaliacaoCompetenciaManager) criterioAvaliacaoCompetenciaManager.proxy());
        
        colaboradorRespostaManager = new Mock(ColaboradorRespostaManager.class);
        MockSpringUtil.mocks.put("colaboradorRespostaManager", colaboradorRespostaManager);
        
        colaboradorQuestionarioManager = new Mock(ColaboradorQuestionarioManager.class);
        MockSpringUtil.mocks.put("colaboradorQuestionarioManager", colaboradorQuestionarioManager);
        
        configHistoricoNivelManager = new Mock(ConfigHistoricoNivelManager.class);
        manager.setConfigHistoricoNivelManager((ConfigHistoricoNivelManager) configHistoricoNivelManager.proxy());
        
        configuracaoNivelCompetenciaFaixaSalarialManager = new Mock(ConfiguracaoNivelCompetenciaFaixaSalarialManager.class);
        manager.setConfiguracaoNivelCompetenciaFaixaSalarialManager( (ConfiguracaoNivelCompetenciaFaixaSalarialManager) configuracaoNivelCompetenciaFaixaSalarialManager.proxy());

        configuracaoNivelCompetenciaCandidatoManager = new Mock(ConfiguracaoNivelCompetenciaCandidatoManager.class);
        manager.setConfiguracaoNivelCompetenciaCandidatoManager((ConfiguracaoNivelCompetenciaCandidatoManager) configuracaoNivelCompetenciaCandidatoManager.proxy());
        
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
    }

	public void testSaveCompetenciasCandidato()
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity(2L);
		Candidato candidato = CandidatoFactory.getCandidato(3L);
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia1.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetencia1.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetencia1.setCompetenciaId(atitude.getId());
		configuracaoNivelCompetencia1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		
		ConfiguracaoNivelCompetencia nivelCompetenciaSemCompetenciaId = new ConfiguracaoNivelCompetencia();
		nivelCompetenciaSemCompetenciaId.setFaixaSalarial(faixaSalarial);
		nivelCompetenciaSemCompetenciaId.setNivelCompetencia(nivelCompetencia);
		nivelCompetenciaSemCompetenciaId.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		
		Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais = Arrays.asList(configuracaoNivelCompetencia1, nivelCompetenciaSemCompetenciaId);
		
		configuracaoNivelCompetenciaDao.expects(once()).method("deleteConfiguracaoByCandidatoFaixa").with(eq(candidato.getId()), eq(solicitacao.getId())).isVoid();
		configuracaoNivelCompetenciaCandidatoManager.expects(once()).method("removeByCandidatoAndSolicitacao").with(eq(candidato.getId()), eq(solicitacao.getId())).isVoid();
		configuracaoNivelCompetenciaDao.expects(once()).method("save").with(ANYTHING).isVoid();
		configuracaoNivelCompetenciaFaixaSalarialManager.expects(once()).method("findByFaixaSalarialIdAndData").with(eq(faixaSalarial.getId()), eq(solicitacao.getData())).will(returnValue(new ConfiguracaoNivelCompetenciaFaixaSalarial()));
		configuracaoNivelCompetenciaCandidatoManager.expects(once()).method("save").with(ANYTHING);
	
		manager.saveCompetenciasCandidato(niveisCompetenciaFaixaSalariais, faixaSalarial.getId(), candidato.getId(), solicitacao);
	}
	
	public void testSaveCompetenciasColaboradorInsertSemColaboradorQuestionario()
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity();
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador.setColaboradorQuestionario(null);
		configuracaoNivelCompetenciaColaborador.setAvaliador(null);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = ConfiguracaoNivelCompetenciaFactory.getEntity(1L);
		configuracaoNivelCompetencia1.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetencia1.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetencia1.setCompetenciaId(atitude.getId());
		configuracaoNivelCompetencia1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		
		ConfiguracaoNivelCompetencia nivelCompetenciaSemCompetenciaId = ConfiguracaoNivelCompetenciaFactory.getEntity(2L);
		nivelCompetenciaSemCompetenciaId.setFaixaSalarial(faixaSalarial);
		nivelCompetenciaSemCompetenciaId.setNivelCompetencia(nivelCompetencia);
		nivelCompetenciaSemCompetenciaId.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		
		Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais = Arrays.asList(configuracaoNivelCompetencia1, nivelCompetenciaSemCompetenciaId);
		
		configuracaoNivelCompetenciaColaboradorManager.expects(once()).method("save").with(eq(configuracaoNivelCompetenciaColaborador)).isVoid();
		configuracaoNivelCompetenciaDao.expects(once()).method("save").with(ANYTHING).isVoid();
		
		manager.saveCompetenciasColaborador(niveisCompetenciaFaixaSalariais, configuracaoNivelCompetenciaColaborador);
	}
	
	public void testSaveCompetenciasColaboradorInsertComColaboradorQuestionarioEAvaliadorComIdNuloSemConfiguracaoNivelCompetencia()
	{
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador.setColaboradorQuestionario(ColaboradorQuestionarioFactory.getEntity());
		configuracaoNivelCompetenciaColaborador.setAvaliador(ColaboradorFactory.getEntity());
		
		configuracaoNivelCompetenciaColaboradorManager.expects(once()).method("save").with(eq(configuracaoNivelCompetenciaColaborador)).isVoid();
		
		manager.saveCompetenciasColaborador(null, configuracaoNivelCompetenciaColaborador);
	}
	
	public void testSaveCompetenciasColaboradorUpdate()
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity();
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity(1L);
		configuracaoNivelCompetenciaColaborador.setColaboradorQuestionario(null);
		configuracaoNivelCompetenciaColaborador.setAvaliador(null);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = ConfiguracaoNivelCompetenciaFactory.getEntity(1L);
		configuracaoNivelCompetencia1.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetencia1.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetencia1.setCompetenciaId(atitude.getId());
		configuracaoNivelCompetencia1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		
		ConfiguracaoNivelCompetencia nivelCompetenciaSemCompetenciaId = ConfiguracaoNivelCompetenciaFactory.getEntity(2L);
		nivelCompetenciaSemCompetenciaId.setFaixaSalarial(faixaSalarial);
		nivelCompetenciaSemCompetenciaId.setNivelCompetencia(nivelCompetencia);
		nivelCompetenciaSemCompetenciaId.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		
		Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais = Arrays.asList(configuracaoNivelCompetencia1, nivelCompetenciaSemCompetenciaId);
		
		configuracaoNivelCompetenciaColaboradorManager.expects(once()).method("update").with(eq(configuracaoNivelCompetenciaColaborador)).isVoid();
		configuracaoNivelCompetenciaCriterioManager.expects(once()).method("removeByConfiguracaoNivelCompetenciaColaborador").with(eq(configuracaoNivelCompetenciaColaborador.getId())).isVoid();
		configuracaoNivelCompetenciaDao.expects(once()).method("deleteByConfiguracaoNivelCompetenciaColaborador").with(eq(configuracaoNivelCompetenciaColaborador.getId())).isVoid();
		configuracaoNivelCompetenciaDao.expects(once()).method("save").with(ANYTHING).isVoid();
		
		manager.saveCompetenciasColaborador(niveisCompetenciaFaixaSalariais, configuracaoNivelCompetenciaColaborador);
	}
	
	public void testSaveCompetenciasColaboradorUpdateComColaboradorQuestionario()
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity();
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
		colaboradorQuestionario.setAvaliacaoDesempenho(AvaliacaoDesempenhoFactory.getEntity());
		colaboradorQuestionario.setAvaliador(ColaboradorFactory.getEntity());
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity(1L);
		configuracaoNivelCompetenciaColaborador.setColaboradorQuestionario(colaboradorQuestionario);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = ConfiguracaoNivelCompetenciaFactory.getEntity(1L);
		configuracaoNivelCompetencia1.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetencia1.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetencia1.setCompetenciaId(atitude.getId());
		configuracaoNivelCompetencia1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		
		ConfiguracaoNivelCompetencia nivelCompetenciaSemCompetenciaId = ConfiguracaoNivelCompetenciaFactory.getEntity(2L);
		nivelCompetenciaSemCompetenciaId.setFaixaSalarial(faixaSalarial);
		nivelCompetenciaSemCompetenciaId.setNivelCompetencia(nivelCompetencia);
		nivelCompetenciaSemCompetenciaId.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		
		Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais = Arrays.asList(configuracaoNivelCompetencia1, nivelCompetenciaSemCompetenciaId);
		
		configuracaoNivelCompetenciaColaboradorManager.expects(once()).method("update").with(eq(configuracaoNivelCompetenciaColaborador)).isVoid();
		configuracaoNivelCompetenciaCriterioManager.expects(once()).method("removeByConfiguracaoNivelCompetenciaColaborador").with(eq(configuracaoNivelCompetenciaColaborador.getId())).isVoid();
		configuracaoNivelCompetenciaDao.expects(once()).method("deleteByConfiguracaoNivelCompetenciaColaborador").with(eq(configuracaoNivelCompetenciaColaborador.getId())).isVoid();
		configuracaoNivelCompetenciaDao.expects(once()).method("save").with(ANYTHING).isVoid();
		
		manager.saveCompetenciasColaborador(niveisCompetenciaFaixaSalariais, configuracaoNivelCompetenciaColaborador);
	}
	
	public void testSaveCompetenciasColaboradorAndRecalculaPerformance()
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity();
		Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
		colaboradorQuestionario.setAvaliacaoDesempenho(AvaliacaoDesempenhoFactory.getEntity(1L));
		colaboradorQuestionario.setAvaliador(ColaboradorFactory.getEntity());
		colaboradorQuestionario.setAvaliacao(avaliacao);

		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity(1L);
		configuracaoNivelCompetenciaColaborador.setColaboradorQuestionario(colaboradorQuestionario);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = ConfiguracaoNivelCompetenciaFactory.getEntity(1L);
		configuracaoNivelCompetencia1.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetencia1.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetencia1.setCompetenciaId(atitude.getId());
		configuracaoNivelCompetencia1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		
		ConfiguracaoNivelCompetencia nivelCompetenciaSemCompetenciaId = ConfiguracaoNivelCompetenciaFactory.getEntity(2L);
		nivelCompetenciaSemCompetenciaId.setFaixaSalarial(faixaSalarial);
		nivelCompetenciaSemCompetenciaId.setNivelCompetencia(nivelCompetencia);
		nivelCompetenciaSemCompetenciaId.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		
		Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais = Arrays.asList(configuracaoNivelCompetencia1, nivelCompetenciaSemCompetenciaId);
		
		configuracaoNivelCompetenciaColaboradorManager.expects(once()).method("update").with(eq(configuracaoNivelCompetenciaColaborador)).isVoid();
		configuracaoNivelCompetenciaCriterioManager.expects(once()).method("removeByConfiguracaoNivelCompetenciaColaborador").with(eq(configuracaoNivelCompetenciaColaborador.getId())).isVoid();
		configuracaoNivelCompetenciaDao.expects(once()).method("deleteByConfiguracaoNivelCompetenciaColaborador").with(eq(configuracaoNivelCompetenciaColaborador.getId())).isVoid();
		configuracaoNivelCompetenciaDao.expects(once()).method("save").with(ANYTHING).isVoid();
		colaboradorQuestionarioManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(configuracaoNivelCompetenciaColaborador.getColaboradorQuestionario()));
		colaboradorRespostaManager.expects(once()).method("calculaPerformance").withAnyArguments().isVoid();
		colaboradorQuestionarioManager.expects(once()).method("save").with(ANYTHING).isVoid();
		
		manager.saveCompetenciasColaboradorAndRecalculaPerformance(1L, niveisCompetenciaFaixaSalariais, configuracaoNivelCompetenciaColaborador);
	}
	
	public void testMontaRelatorioConfiguracaoNivelCompetencia()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(999999999999L);

		Colaborador joao = ColaboradorFactory.getEntity(1L, "João");
		Colaborador avaliador = ColaboradorFactory.getEntity(3L, "Avaliador");

		Atitude atitude = AtitudeFactory.getEntity(1L, "Liderança");
		Atitude atitude2 = AtitudeFactory.getEntity(2L, "Comunicação");
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento(3L, "Delphi");
		
		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity(DateUtil.criarDataMesAno(1, 1, 2015), empresa);
		NivelCompetencia nivelPessimo = NivelCompetenciaFactory.getEntity("Pessimo", 1);
		NivelCompetencia nivelRuim = NivelCompetenciaFactory.getEntity("Ruim", 2);
		NivelCompetencia nivelBom = NivelCompetenciaFactory.getEntity("Bom", 3);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(faixaSalarial, DateUtil.criarDataMesAno(1, 1, 2000), nivelCompetenciaHistorico);
		ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaFaixa1 = ConfiguracaoNivelCompetenciaFactory.getEntityFaixaSalarial(1L, configuracaoNivelCompetenciaFaixaSalarial, nivelBom, atitude.getNome(), atitude.getId(), TipoCompetencia.ATITUDE);
		ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaFaixa2 = ConfiguracaoNivelCompetenciaFactory.getEntityFaixaSalarial(2L, configuracaoNivelCompetenciaFaixaSalarial, nivelRuim, conhecimento.getNome(), conhecimento.getId(), TipoCompetencia.CONHECIMENTO);
		ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaFaixa3 = ConfiguracaoNivelCompetenciaFactory.getEntityFaixaSalarial(3L, configuracaoNivelCompetenciaFaixaSalarial, nivelBom, atitude2.getNome(), atitude2.getId(), TipoCompetencia.ATITUDE);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaJoao = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity(1L, joao, faixaSalarial, DateUtil.criarDataMesAno(1, 1, 2010), avaliador, configuracaoNivelCompetenciaFaixaSalarial);
		ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaJoao1 = ConfiguracaoNivelCompetenciaFactory.getEntityColaborador(4L, configuracaoNivelCompetenciaJoao, nivelBom, atitude.getId(), atitude.getNome(), TipoCompetencia.ATITUDE);
		ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaJoao2 = ConfiguracaoNivelCompetenciaFactory.getEntityColaborador(5L, configuracaoNivelCompetenciaJoao, nivelPessimo, conhecimento.getId(), conhecimento.getNome(), TipoCompetencia.CONHECIMENTO);

		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = Arrays.asList(configuracaoNivelCompetenciaJoao1,configuracaoNivelCompetenciaJoao2);
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciaFaixas = Arrays.asList(configuracaoNivelCompetenciaFaixa1,configuracaoNivelCompetenciaFaixa2,configuracaoNivelCompetenciaFaixa3);
		
		Collection<ConfiguracaoNivelCompetenciaColaborador> configuracaoNivelCompetenciasColaborador = Arrays.asList(configuracaoNivelCompetenciaJoao);
		
		CollectionUtil<ConfiguracaoNivelCompetencia> clu = new CollectionUtil<ConfiguracaoNivelCompetencia>();
		Long[] competenciaIds = clu.convertCollectionToArrayIds(configuracaoNivelCompetencias);

		Collection<NivelCompetencia> nivelCompetencias = Arrays.asList(nivelPessimo,nivelRuim,nivelBom);
		Date dataIni = DateUtil.criarDataMesAno(1, 1, 2010);
		Date dataFim = DateUtil.criarDataMesAno(1, 1, 2015);
		
		configuracaoNivelCompetenciaColaboradorManager.expects(once()).method("findByDataAndFaixaSalarial").with(eq(dataIni), eq(dataFim), eq(faixaSalarial.getId())).will(returnValue(configuracaoNivelCompetenciasColaborador));
		configuracaoNivelCompetenciaDao.expects(atLeastOnce()).method("findCompetenciasConfiguracaoNivelCompetenciaFaixaSalarial").with(eq(competenciaIds),eq(configuracaoNivelCompetenciaFaixaSalarial.getId())).will(returnValue(configuracaoNivelCompetenciaFaixas));
		nivelCompetenciaManager.expects(atLeastOnce()).method("findAllSelect").with(eq(empresa.getId()), eq(configuracaoNivelCompetenciaFaixaSalarial.getNivelCompetenciaHistorico().getId()), eq(configuracaoNivelCompetenciaFaixaSalarial.getData())).will(returnValue(nivelCompetencias));
		configuracaoNivelCompetenciaDao.expects(atLeastOnce()).method("findByConfiguracaoNivelCompetenciaColaborador").with(eq(competenciaIds),ANYTHING, eq(configuracaoNivelCompetenciaFaixaSalarial.getId())).will(returnValue(configuracaoNivelCompetencias));
		configuracaoNivelCompetenciaColaboradorManager.expects(atLeastOnce()).method("verificaAvaliadorAnonimo").with(ANYTHING).isVoid();
		Collection<ConfiguracaoNivelCompetenciaVO> result = manager.montaRelatorioConfiguracaoNivelCompetencia(dataIni,dataFim, empresa.getId(), faixaSalarial.getId(), competenciaIds);
		
		assertEquals(1, result.size());

		Collection<MatrizCompetenciaNivelConfiguracao> matrizJoao = ((ConfiguracaoNivelCompetenciaVO)result.toArray()[0]).getMatrizes();
		assertEquals("Matriz padrão (12)", 12, matrizJoao.size());
		assertFalse(((MatrizCompetenciaNivelConfiguracao)matrizJoao.toArray()[0]).getConfiguracao());
		assertFalse(((MatrizCompetenciaNivelConfiguracao)matrizJoao.toArray()[7]).getConfiguracao());

		assertEquals(new Integer(0), ((MatrizCompetenciaNivelConfiguracao)matrizJoao.toArray()[3]).getGap());
		assertEquals(new Integer(-1), ((MatrizCompetenciaNivelConfiguracao)matrizJoao.toArray()[7]).getGap());
		assertEquals(new Integer(-1), ((MatrizCompetenciaNivelConfiguracao)matrizJoao.toArray()[11]).getGap());
	}
	
	public void testMontaConfiguracaoNivelCompetenciaByFaixa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(999999999999L);
		
		NivelCompetencia nivelPessimo = NivelCompetenciaFactory.getEntity();
		nivelPessimo.setDescricao("pessimo");
		nivelPessimo.setOrdem(1);

		NivelCompetencia nivelRuim = NivelCompetenciaFactory.getEntity();
		nivelRuim.setDescricao("ruim");
		nivelRuim.setOrdem(2);
		
		NivelCompetencia nivelBom = NivelCompetenciaFactory.getEntity();
		nivelBom.setDescricao("bom");
		nivelBom.setOrdem(3);

		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity(DateUtil.criarDataMesAno(1, 2, 2015), empresa);
		nivelCompetenciaHistorico.setId(1L);
		
		ConfigHistoricoNivel configHistoricoNivelPessimo = ConfigHistoricoNivelFactory.getEntity(1L);
		configHistoricoNivelPessimo.setNivelCompetencia(nivelPessimo);
		configHistoricoNivelPessimo.setOrdem(1);
		configHistoricoNivelPessimo.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);

		ConfigHistoricoNivel configHistoricoNivelRuim = ConfigHistoricoNivelFactory.getEntity(1L);
		configHistoricoNivelRuim.setNivelCompetencia(nivelRuim);
		configHistoricoNivelRuim.setOrdem(2);
		configHistoricoNivelRuim.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		
		ConfigHistoricoNivel configHistoricoNivelBom = ConfigHistoricoNivelFactory.getEntity(1L);
		configHistoricoNivelBom.setNivelCompetencia(nivelBom);
		configHistoricoNivelBom.setOrdem(3);
		configHistoricoNivelBom.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);

		nivelCompetenciaHistorico.setConfigHistoricoNiveis(Arrays.asList(configHistoricoNivelPessimo, configHistoricoNivelRuim, configHistoricoNivelBom));
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetencia = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity(999999999998L);
		configuracaoNivelCompetencia.setFaixaSalarial(faixaSalarial);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia1.setId(1L);
		configuracaoNivelCompetencia1.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetencia);
		configuracaoNivelCompetencia1.setCompetenciaDescricao("Java");
		configuracaoNivelCompetencia1.setNivelCompetencia(nivelBom);
		configuracaoNivelCompetencia1.setNivelCompetenciaColaborador(nivelPessimo);
		configuracaoNivelCompetencia1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia2 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia2.setId(2L);
		configuracaoNivelCompetencia2.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetencia);
		configuracaoNivelCompetencia2.setCompetenciaDescricao("Delphi");
		configuracaoNivelCompetencia2.setNivelCompetencia(nivelRuim);
		configuracaoNivelCompetencia2.setNivelCompetenciaColaborador(nivelRuim);
		configuracaoNivelCompetencia2.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia3 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia3.setId(3L);
		configuracaoNivelCompetencia3.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetencia);
		configuracaoNivelCompetencia3.setCompetenciaDescricao("C#");
		configuracaoNivelCompetencia3.setNivelCompetencia(nivelPessimo);
		configuracaoNivelCompetencia3.setNivelCompetenciaColaborador(nivelRuim);
		configuracaoNivelCompetencia3.setTipoCompetencia(TipoCompetencia.ATITUDE);
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(faixaSalarial, DateUtil.criarDataMesAno(1, 2, 2015));
		configuracaoNivelCompetenciaFaixaSalarial.setId(1L);
		configuracaoNivelCompetenciaFaixaSalarial.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = Arrays.asList(configuracaoNivelCompetencia1,configuracaoNivelCompetencia2,configuracaoNivelCompetencia3);
		
		Collection<NivelCompetencia> nivelCompetencias = Arrays.asList(nivelPessimo,nivelRuim,nivelBom);
		
		configuracaoNivelCompetenciaDao.expects(once()).method("findCompetenciaByFaixaSalarial").with(new Constraint[]{eq(faixaSalarial.getId()), eq(configuracaoNivelCompetenciaFaixaSalarial.getData()), eq(configuracaoNivelCompetenciaFaixaSalarial.getId()), ANYTHING, ANYTHING}).will(returnValue(configuracaoNivelCompetencias));
		nivelCompetenciaManager.expects(once()).method("findAllSelect").with(eq(empresa.getId()), ANYTHING, eq(configuracaoNivelCompetenciaFaixaSalarial.getData())).will(returnValue(nivelCompetencias));
		criterioAvaliacaoCompetenciaManager.expects(atLeastOnce()).method("findByCompetenciaAndCNCFId").withAnyArguments().will(returnValue(new ArrayList<CriterioAvaliacaoCompetencia>()));
		
		Collection<MatrizCompetenciaNivelConfiguracao> result = manager.montaConfiguracaoNivelCompetenciaByFaixa(empresa.getId(), faixaSalarial.getId(), configuracaoNivelCompetenciaFaixaSalarial);
		
		assertEquals(9, result.size());
	}

	public void testMontaMatrizCNCByQuestionario(){
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		
		Colaborador avaliado = ColaboradorFactory.getEntity(1L, "Avaliado");
		Colaborador avaliador = ColaboradorFactory.getEntity(1L, "Avaliador");
		
		NivelCompetencia nivelRuim = NivelCompetenciaFactory.getEntity("Ruim", 1);
		NivelCompetencia nivelPessimo = NivelCompetenciaFactory.getEntity("Péssimo", 2);
		NivelCompetencia nivelBom = NivelCompetenciaFactory.getEntity("Bom", 3);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
		colaboradorQuestionario.setRespondidaEm(new Date());
		colaboradorQuestionario.setColaborador(avaliado);
		colaboradorQuestionario.setAvaliador(avaliador);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(1L,faixaSalarial, DateUtil.criarDataMesAno(1, 2, 2015), NivelCompetenciaHistoricoFactory.getEntity(1L));
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1ExigidoPelaFaixa = ConfiguracaoNivelCompetenciaFactory.getEntityFaixaSalarial(1L, configuracaoNivelCompetenciaFaixaSalarial, nivelRuim, "Comunicação", 1L, TipoCompetencia.ATITUDE);
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia2ExigidoPelaFaixa = ConfiguracaoNivelCompetenciaFactory.getEntityFaixaSalarial(2L, configuracaoNivelCompetenciaFaixaSalarial, nivelBom, "Conhecimento Técnico", 1L, TipoCompetencia.CONHECIMENTO); 

		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity(1L);
		configuracaoNivelCompetenciaColaborador.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetenciaColaborador.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1DoColaborador = ConfiguracaoNivelCompetenciaFactory.getEntityColaborador(3L, configuracaoNivelCompetenciaColaborador, nivelPessimo, 1L, "Comunicação", TipoCompetencia.ATITUDE);
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia2DoColaborador = ConfiguracaoNivelCompetenciaFactory.getEntityColaborador(4L, configuracaoNivelCompetenciaColaborador, nivelPessimo, 2L, "Conhecimento Técnico", TipoCompetencia.CONHECIMENTO); 
		
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciasDoColaborador = Arrays.asList(configuracaoNivelCompetencia1DoColaborador, configuracaoNivelCompetencia2DoColaborador);
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciasExigidasPelaFaixa = Arrays.asList(configuracaoNivelCompetencia1ExigidoPelaFaixa,configuracaoNivelCompetencia2ExigidoPelaFaixa);
		configuracaoNivelCompetenciaColaboradorManager.expects(once()).method("findByData").with(eq(colaboradorQuestionario.getRespondidaEm()), eq(colaboradorQuestionario.getColaborador().getId()), eq(colaboradorQuestionario.getAvaliador().getId()), eq(colaboradorQuestionario.getId())).will(returnValue(configuracaoNivelCompetenciaColaborador));
		
		nivelCompetenciaManager.expects(once()).method("findAllSelect").with(eq(empresa.getId()), ANYTHING, eq(configuracaoNivelCompetenciaFaixaSalarial.getData())).will(returnValue(Arrays.asList(nivelBom, nivelRuim, nivelPessimo)));
		configuracaoNivelCompetenciaDao.expects(once()).method("findCompetenciaByFaixaSalarial").with(new Constraint[]{eq(configuracaoNivelCompetenciaColaborador.getId()), eq(configuracaoNivelCompetenciaFaixaSalarial.getData()), eq(configuracaoNivelCompetenciaFaixaSalarial.getId()), ANYTHING, ANYTHING}).will(returnValue(configuracaoNivelCompetenciasExigidasPelaFaixa));
		configuracaoNivelCompetenciaDao.expects(once()).method("findByConfiguracaoNivelCompetenciaColaborador").with(eq(null),eq(configuracaoNivelCompetenciaColaborador.getId()),eq(configuracaoNivelCompetenciaFaixaSalarial.getId())).will(returnValue(configuracaoNivelCompetenciasDoColaborador));
		criterioAvaliacaoCompetenciaManager.expects(atLeastOnce()).method("findByCompetenciaAndCNCFId").withAnyArguments().will(returnValue(new ArrayList<CriterioAvaliacaoCompetencia>()));
		configuracaoNivelCompetenciaCriterioManager.expects(atLeastOnce()).method("findByConfiguracaoNivelCompetencia").withAnyArguments().will(returnValue(new ArrayList<CriterioAvaliacaoCompetencia>()));
		configuracaoNivelCompetenciaFaixaSalarialManager.expects(once()).method("findByProjection").withAnyArguments().will(returnValue(configuracaoNivelCompetenciaFaixaSalarial));
		
		assertEquals(6, manager.montaMatrizCNCByQuestionario(colaboradorQuestionario, empresa.getId()).size());
	}	

	public void testMontaMatrizCompetenciaCandidato(){
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(999999999999L);

		Date data = DateUtil.criarDataDiaMesAno("24/11/2015");
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L,faixaSalarial, data);
		Candidato joao = CandidatoFactory.getCandidato(1L,  "João");
		
		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity(data, empresa);
		NivelCompetencia nivelPessimo = NivelCompetenciaFactory.getEntity("Pessimo", 1);
		ConfigHistoricoNivel configHistoricoNivelPessimo = ConfigHistoricoNivelFactory.getEntity(1L, 1, nivelCompetenciaHistorico, nivelPessimo, null);
		
		NivelCompetencia nivelRuim = NivelCompetenciaFactory.getEntity("Ruim", 2);
		ConfigHistoricoNivel configHistoricoNivelRuim = ConfigHistoricoNivelFactory.getEntity(2L, 2, nivelCompetenciaHistorico, nivelRuim, null);
		
		NivelCompetencia nivelBom = NivelCompetenciaFactory.getEntity("Bom", 3);
		ConfigHistoricoNivel configHistoricoNivelBom = ConfigHistoricoNivelFactory.getEntity(3L, 3, nivelCompetenciaHistorico, nivelBom, null);

		Collection<ConfigHistoricoNivel> configHistoricoNiveis = Arrays.asList(configHistoricoNivelPessimo, configHistoricoNivelRuim, configHistoricoNivelBom);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(faixaSalarial, data, nivelCompetenciaHistorico);
		ConfiguracaoNivelCompetenciaCandidato configuracaoNivelCompetenciaCandidato = ConfiguracaoNivelCompetenciaCandidatoFactory.getEntity(1L, joao, solicitacao, configuracaoNivelCompetenciaFaixaSalarial, data);
		
		ConfiguracaoNivelCompetencia configuracaoNivelFaixaCompetencia1 = criaConfigCompetencia("Comunicação", null, nivelBom, TipoCompetencia.ATITUDE, configuracaoNivelCompetenciaFaixaSalarial, null);
		ConfiguracaoNivelCompetencia configuracaoNivelFaixaCompetencia2 = criaConfigCompetencia("Liderança", null, nivelBom, TipoCompetencia.CONHECIMENTO, configuracaoNivelCompetenciaFaixaSalarial, null);

		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1CandidatoJoao = criaConfigCompetencia("Comunicação", faixaSalarial, nivelBom, TipoCompetencia.CONHECIMENTO, null, configuracaoNivelCompetenciaCandidato);
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia2CandidatoJoao = criaConfigCompetencia("Liderança", faixaSalarial, nivelBom, TipoCompetencia.ATITUDE, null, configuracaoNivelCompetenciaCandidato);

		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciasFaixaSalarial = Arrays.asList(configuracaoNivelFaixaCompetencia1, configuracaoNivelFaixaCompetencia2);
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciasCandidato = Arrays.asList(configuracaoNivelCompetencia1CandidatoJoao, configuracaoNivelCompetencia2CandidatoJoao);

		candidatoSolicitacaoManager.expects(once()).method("getCandidatosBySolicitacao").with(eq(solicitacao.getId())).will(returnValue(Arrays.asList(joao.getId())));
		configuracaoNivelCompetenciaCandidatoManager.expects(once()).method("findByCandidatoAndSolicitacao").with(eq(joao.getId()), eq(solicitacao.getId())).will(returnValue(configuracaoNivelCompetenciaCandidato));
		configuracaoNivelCompetenciaDao.expects(once()).method("findByConfiguracaoNivelCompetenciaFaixaSalarial").with(eq(configuracaoNivelCompetenciaCandidato.getConfiguracaoNivelCompetenciaFaixaSalarial().getId())).will(returnValue(configuracaoNivelCompetenciasFaixaSalarial));
		configuracaoNivelCompetenciaDao.expects(once()).method("findConfiguracaoNivelCompetenciaCandidato").with(eq(configuracaoNivelCompetenciaCandidato.getId())).will(returnValue(configuracaoNivelCompetenciasCandidato));
		configHistoricoNivelManager.expects(once()).method("findByNivelCompetenciaHistoricoId").with(eq(configuracaoNivelCompetenciaCandidato.getConfiguracaoNivelCompetenciaFaixaSalarial().getNivelCompetenciaHistorico().getId())).will(returnValue(configHistoricoNiveis));	
		
		Collection<ConfiguracaoNivelCompetenciaVO> matrizes = manager.montaMatrizCompetenciaCandidato(empresa.getId(), faixaSalarial.getId(), solicitacao);
		assertEquals(1, matrizes.size());
		
		ConfiguracaoNivelCompetenciaVO matrizJoao = (ConfiguracaoNivelCompetenciaVO) matrizes.iterator().next();
		assertEquals(joao.getNome(), matrizJoao.getNome());
		assertEquals("matriz Joao", 8, matrizJoao.getMatrizes().size());

		List<MatrizCompetenciaNivelConfiguracao> matrizConfiguradaJoao = (List<MatrizCompetenciaNivelConfiguracao>) matrizJoao.getMatrizes();
		assertEquals("configuracao candidato", Boolean.TRUE, matrizConfiguradaJoao.get(2).getConfiguracao());
		assertEquals("configuracao faixa", Boolean.TRUE, matrizConfiguradaJoao.get(2).getConfiguracaoFaixa());
		assertEquals("gap", new Integer(0), matrizConfiguradaJoao.get(3).getGap());
	}

	private ConfiguracaoNivelCompetencia criaConfigCompetencia(String descricao, FaixaSalarial faixaSalarial, NivelCompetencia nivel, char tipo, ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial, ConfiguracaoNivelCompetenciaCandidato configuracaoNivelCompetenciaCandidato)
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia.setCompetenciaDescricao(descricao);
		configuracaoNivelCompetencia.setNivelCompetencia(nivel);
		configuracaoNivelCompetencia.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaCandidato(configuracaoNivelCompetenciaCandidato);
		configuracaoNivelCompetencia.setTipoCompetencia(tipo);
		configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		
		return configuracaoNivelCompetencia;
	}
	
	public void testGetCompetenciasCandidato()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(1L,faixaSalarial, DateUtil.criarDataMesAno(1, 2, 2015), NivelCompetenciaHistoricoFactory.getEntity(1L));
		ConfiguracaoNivelCompetenciaCandidato configuracaoNivelCompetenciaCandidato = ConfiguracaoNivelCompetenciaCandidatoFactory.getEntity(null, solicitacao, configuracaoNivelCompetenciaFaixaSalarial, new Date());
		
		ConfiguracaoNivelCompetencia nivelConhecimento = new ConfiguracaoNivelCompetencia(TipoCompetencia.CONHECIMENTO, 1L, "", null, 1);
		nivelConhecimento.setFaixaSalarial(faixaSalarial);
		nivelConhecimento.setConfiguracaoNivelCompetenciaCandidato(configuracaoNivelCompetenciaCandidato);

		ConfiguracaoNivelCompetencia nivelAtitude = new ConfiguracaoNivelCompetencia(TipoCompetencia.ATITUDE, 3L, "", null, 1);
		nivelAtitude.setFaixaSalarial(faixaSalarial);
		nivelAtitude.setConfiguracaoNivelCompetenciaCandidato(configuracaoNivelCompetenciaCandidato);

		Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaCandidato = Arrays.asList(nivelConhecimento, nivelAtitude);

		nivelConhecimento.setCompetenciaDescricao("java");
		nivelAtitude.setCompetenciaDescricao("proatividade");
		ConfiguracaoNivelCompetencia nivelHabilidade = new ConfiguracaoNivelCompetencia(TipoCompetencia.HABILIDADE, 2L, "comunicacao", null, 1);
		
		Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaEmpresa = Arrays.asList(nivelConhecimento, nivelHabilidade, nivelAtitude);

		configuracaoNivelCompetenciaDao.expects(once()).method("findByConfiguracaoNivelCompetenciaFaixaSalarial").with(eq(1L)).will(returnValue(new ArrayList<ConfiguracaoNivelCompetencia>()));
		configuracaoNivelCompetenciaDao.expects(once()).method("findByCandidato").with(eq(1L)).will(returnValue(niveisCompetenciaCandidato));
		nivelCompetenciaManager.expects(atLeastOnce()).method("findByCargoOrEmpresa").with(ANYTHING,ANYTHING).will(returnValue(niveisCompetenciaEmpresa));
		nivelCompetenciaManager.expects(once()).method("findAllSelect").withAnyArguments().will(returnValue(new ArrayList<NivelCompetencia>()));
		
		Collection<Solicitacao> solicitacaoCoimConhecimento = manager.getCompetenciasCandidato(1L, 1L); 
		assertEquals(1, solicitacaoCoimConhecimento.size());
		
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = ((Solicitacao ) solicitacaoCoimConhecimento.toArray()[0]).getConfiguracaoNivelCompetencias();
		assertEquals(2, configuracaoNivelCompetencias.size());
		assertEquals("java", ((ConfiguracaoNivelCompetencia)configuracaoNivelCompetencias.toArray()[0]).getCompetenciaDescricao());
		assertEquals("proatividade", ((ConfiguracaoNivelCompetencia)configuracaoNivelCompetencias.toArray()[1]).getCompetenciaDescricao());
	}
	
	public void testFindColaboradorAbaixoNivel()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(1L);
		configuracaoNivelCompetenciaFaixaSalarial.setData(DateUtil.criarDataMesAno(1, 1, 2015));
		configuracaoNivelCompetenciaFaixaSalarial.setNivelCompetenciaHistoricoId(1L);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador.setData(DateUtil.criarDataMesAno(1, 2, 2015));
		
		NivelCompetencia nivelCompetencia1 = NivelCompetenciaFactory.getEntity(1L);
		nivelCompetencia1.setDescricao("novel1");
		
		NivelCompetencia nivelCompetencia2 = NivelCompetenciaFactory.getEntity(2L);
		nivelCompetencia2.setDescricao("novel2");
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaFaixa1 = new ConfiguracaoNivelCompetencia(TipoCompetencia.CONHECIMENTO, 1L, nivelCompetencia1.getId(), 3);
		configuracaoNivelCompetenciaFaixa1.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaFaixa2 = new ConfiguracaoNivelCompetencia(TipoCompetencia.ATITUDE, 2L, nivelCompetencia2.getId(), 5);
		configuracaoNivelCompetenciaFaixa2.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = new ConfiguracaoNivelCompetencia(new BigInteger("1"), "faixa1", "Joao", null, "Ruim", 2, null,
				configuracaoNivelCompetenciaColaborador.getData(), "avaliadorNome", false, configuracaoNivelCompetenciaFaixaSalarial.getId(), 'C', configuracaoNivelCompetenciaFaixaSalarial.getNivelCompetenciaHistorico().getId() );
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia2 = new ConfiguracaoNivelCompetencia(new BigInteger("2"), "faixa2", "Pedro", null, "otimo", 5, null, 
				configuracaoNivelCompetenciaColaborador.getData(), "avaliadorNome", false, configuracaoNivelCompetenciaFaixaSalarial.getId(), 'C', configuracaoNivelCompetenciaFaixaSalarial.getNivelCompetenciaHistorico().getId() );

		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias =  Arrays.asList(configuracaoNivelCompetencia1, configuracaoNivelCompetencia2);
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciaFaixas =  Arrays.asList(configuracaoNivelCompetenciaFaixa1, configuracaoNivelCompetenciaFaixa2);
		
		configuracaoNivelCompetenciaDao.expects(once()).method("findCompetenciaByFaixaSalarial").withAnyArguments().will(returnValue(configuracaoNivelCompetenciaFaixas));
		configuracaoNivelCompetenciaDao.expects(once()).method("findCompetenciaColaborador").with(new Constraint[]{eq(null), eq(null),ANYTHING, eq(faixaSalarial.getId()),ANYTHING}).will(returnValue(configuracaoNivelCompetencias));
		assertEquals(1, manager.findColaboradorAbaixoNivel(new Long[]{1L, 2L}, faixaSalarial.getId(), null).size());
	}
	
	public void testCriaCNCColaboradorByCNCCnadidato(){
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		Candidato candidato = CandidatoFactory.getCandidato(2L);
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(3L);
		Colaborador colaborador = ColaboradorFactory.getEntity(22L);
		
		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity(1L, colaborador, faixaSalarial, DateUtil.criarDataDiaMesAno("01/06/2015"));
		
		Atitude atitude = AtitudeFactory.getEntity(1L, "Prestativo");
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento(1L, "Java");
		Habilidade habilidade= HabilidadeFactory.getEntity(6L, "Magica");
		
		NivelCompetencia nivelBom = NivelCompetenciaFactory.getEntity("Pessimo", 1);
		NivelCompetencia nivelRuim = NivelCompetenciaFactory.getEntity("Ruim", 2);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(1L);
		ConfiguracaoNivelCompetenciaCandidato configuracaoNivelCompetenciaCandidato = ConfiguracaoNivelCompetenciaCandidatoFactory.getEntity(1L, candidato, solicitacao, configuracaoNivelCompetenciaFaixaSalarial, new Date());

		ConfiguracaoNivelCompetencia cncCandidato1 = ConfiguracaoNivelCompetenciaFactory.getEntityCandidato(configuracaoNivelCompetenciaCandidato, faixaSalarial, nivelBom, conhecimento.getId(), TipoCompetencia.CONHECIMENTO);
		ConfiguracaoNivelCompetencia cncCandidato2 = ConfiguracaoNivelCompetenciaFactory.getEntityCandidato(configuracaoNivelCompetenciaCandidato, faixaSalarial, nivelBom, atitude.getId(), TipoCompetencia.ATITUDE);
		Collection<ConfiguracaoNivelCompetencia> cncscandidato = Arrays.asList(cncCandidato1, cncCandidato2);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial cncFaixa = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(10L); 
		ConfiguracaoNivelCompetencia cncFaixaSalarial1 = ConfiguracaoNivelCompetenciaFactory.getEntityFaixaSalarial(cncFaixa, nivelRuim, atitude.getId(), TipoCompetencia.ATITUDE);
		ConfiguracaoNivelCompetencia cncFaixaSalarial2 = ConfiguracaoNivelCompetenciaFactory.getEntityFaixaSalarial(cncFaixa, nivelRuim, habilidade.getId(), TipoCompetencia.HABILIDADE);
		Collection<ConfiguracaoNivelCompetencia> cncsFaixaSalarial = Arrays.asList(cncFaixaSalarial1, cncFaixaSalarial2);
		
		configuracaoNivelCompetenciaCandidatoManager.expects(once()).method("findByCandidatoAndSolicitacao").with(eq(candidato.getId()), eq(solicitacao.getId())).will(returnValue(configuracaoNivelCompetenciaCandidato));
		configuracaoNivelCompetenciaDao.expects(once()).method("findByCandidatoAndSolicitacao").with(eq(candidato.getId()), eq(solicitacao.getId())).will(returnValue(cncscandidato));
		configuracaoNivelCompetenciaDao.expects(once()).method("findByConfiguracaoNivelCompetenciaFaixaSalarial").with(eq(configuracaoNivelCompetenciaCandidato.getConfiguracaoNivelCompetenciaFaixaSalarial().getId())).will(returnValue(cncsFaixaSalarial));
		configuracaoNivelCompetenciaColaboradorManager.expects(once()).method("save").withAnyArguments().isVoid();
		configuracaoNivelCompetenciaDao.expects(once()).method("save").with(ANYTHING).isVoid();
		Exception exception = null;
		try {
			manager.criaCNCColaboradorByCNCCnadidato(colaborador, candidato.getId(), solicitacao, historico);
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}
	
	public void testRemoveByCandidatoAndSolicitacao() 
	{
		Long candidatoId = 1L;
		Long solicitacaoId = 2L;
		Exception exception = null;
		try {
			configuracaoNivelCompetenciaDao.expects(once()).method("removeByCandidatoAndSolicitacao").with(eq(candidatoId), eq(solicitacaoId)).isVoid();
			configuracaoNivelCompetenciaCandidatoManager.expects(once()).method("removeByCandidatoAndSolicitacao").with(eq(candidatoId), eq(solicitacaoId)).isVoid();
			manager.removeByCandidatoAndSolicitacao(candidatoId, solicitacaoId);
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}

	public void testRemoveByCandidato() 
	{
		Long candidatoId = 1L;
		Exception exception = null;
		try {
			configuracaoNivelCompetenciaDao.expects(once()).method("removeByCandidato").with(eq(candidatoId)).isVoid();
			configuracaoNivelCompetenciaCandidatoManager.expects(once()).method("removeByCandidato").with(eq(candidatoId)).isVoid();
			manager.removeByCandidato(candidatoId);
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
	public void testExecutaTesteAutomaticoDoManager() {
		testeAutomatico(configuracaoNivelCompetenciaDao);
	}
	
	public void testMontaRelatorioResultadoCompetencia(){
		Competencia competencia1 = new Competencia();
		competencia1.setId(1L);
		competencia1.setNome("Competência 1");
		
		Cargo cargo1 = CargoFactory.getEntity(1L);
		cargo1.setNome("Cargo 1");
		
		Cargo cargo2 = CargoFactory.getEntity(2L);
		cargo2.setNome("Cargo 2");
		
		Cargo cargo3 = CargoFactory.getEntity(3L);
		cargo3.setNome("Cargo 3");
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L, "Colaborador 1");
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L, "Colaborador 2");
		Colaborador colaborador3 = ColaboradorFactory.getEntity(3L, "Colaborador 3");
		Colaborador colaborador4 = ColaboradorFactory.getEntity(4L, "Colaborador 4");

		LinkedList<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = new LinkedList<ConfiguracaoNivelCompetencia>();
		configuracaoNivelCompetencias.add(ConfiguracaoNivelCompetenciaFactory.getEntity(competencia1, colaborador1, cargo1, 2));
		configuracaoNivelCompetencias.add(ConfiguracaoNivelCompetenciaFactory.getEntity(competencia1, colaborador2, cargo2, 3));
		configuracaoNivelCompetencias.add(ConfiguracaoNivelCompetenciaFactory.getEntity(competencia1, colaborador3, cargo3, 6));
		configuracaoNivelCompetencias.add(ConfiguracaoNivelCompetenciaFactory.getEntity(competencia1, colaborador4, cargo2, 5));
		
		Collection<Long> colaboradorIds = new ArrayList<Long>();
		colaboradorIds.add(colaborador1.getId());
		colaboradorIds.add(colaborador2.getId());
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity();
		Collection<NivelCompetencia> niveisCompetencia = Arrays.asList(nivelCompetencia);
		
		configuracaoNivelCompetenciaDao.expects(once()).method("findByAvaliacaaDesempenhoAndAvaliado").withAnyArguments().will(returnValue(configuracaoNivelCompetencias));
		nivelCompetenciaManager.expects(once()).method("getOrdemMaximaByAavaliacaoDesempenhoAndAvaliado").withAnyArguments().will(returnValue(5));
		nivelCompetenciaManager.expects(once()).method("findNiveisCompetenciaByAvDesempenho").withAnyArguments().will(returnValue(niveisCompetencia));
		
		RelatorioAnaliseDesempenhoColaborador relatorioAnaliseDesempenhoColaborador = manager.montaRelatorioAnaliseDesempenhoColaborador(1L, colaborador1.getId(), colaboradorIds, 0, true);
		LinkedList<ResultadoCompetenciaColaborador> resultadoCompetenciaColaboradores = (LinkedList<ResultadoCompetenciaColaborador>) relatorioAnaliseDesempenhoColaborador.getResultadosCompetenciaColaborador(); 
		assertEquals(1, resultadoCompetenciaColaboradores.size());
		
		ResultadoCompetenciaColaborador resultadoCompetenciaColaborador1 = (ResultadoCompetenciaColaborador) resultadoCompetenciaColaboradores.toArray()[0];
		assertEquals("Competência 1", resultadoCompetenciaColaborador1.getCompetenciaNome());
		assertEquals(4, resultadoCompetenciaColaborador1.getResultadoCompetencias().size());
		
		assertEquals("Auto-Avaliação", ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[0]).getNome());
		assertEquals("Cargo 2", ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[1]).getNome());
		assertEquals("Outros Avaliadores", ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[2]).getNome());
		assertEquals("Média", ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[3]).getNome());
		
		assertEquals(2.0, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[0]).getOrdem());
		assertEquals(3.0, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[1]).getOrdem());
		assertEquals(5.5, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[2]).getOrdem());
		assertEquals(4.0, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[3]).getOrdem());
	}
	
	public void testMontaRelatorioResultadoCompetenciaComColabNoMesmoCargo(){
		Competencia competencia1 = new Competencia();
		competencia1.setId(1L);
		competencia1.setNome("Competência 1");

		Cargo cargo1 = CargoFactory.getEntity(1L);
		cargo1.setNome("Cargo 1");
		
		Cargo cargo2 = CargoFactory.getEntity(2L);
		cargo2.setNome("Cargo 2");
		
		Cargo cargo3 = CargoFactory.getEntity(3L);
		cargo3.setNome("Cargo 3");
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L, "Colaborador 1");
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L, "Colaborador 2");
		Colaborador colaborador3 = ColaboradorFactory.getEntity(3L, "Colaborador 3");
		Colaborador colaborador4 = ColaboradorFactory.getEntity(4L, "Colaborador 4");

		LinkedList<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = new LinkedList<ConfiguracaoNivelCompetencia>();
		configuracaoNivelCompetencias.add(ConfiguracaoNivelCompetenciaFactory.getEntity(competencia1, colaborador1, cargo1, 2));
		configuracaoNivelCompetencias.add(ConfiguracaoNivelCompetenciaFactory.getEntity(competencia1, colaborador2, cargo2, 3));
		configuracaoNivelCompetencias.add(ConfiguracaoNivelCompetenciaFactory.getEntity(competencia1, colaborador3, cargo3, 7));
		configuracaoNivelCompetencias.add(ConfiguracaoNivelCompetenciaFactory.getEntity(competencia1, colaborador4, cargo2, 9));
		
		Collection<Long> colaboradorIds = new ArrayList<Long>();
		colaboradorIds.add(colaborador2.getId());
		colaboradorIds.add(colaborador4.getId());
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity();
		Collection<NivelCompetencia> niveisCompetencia = Arrays.asList(nivelCompetencia);
		
		configuracaoNivelCompetenciaDao.expects(once()).method("findByAvaliacaaDesempenhoAndAvaliado").withAnyArguments().will(returnValue(configuracaoNivelCompetencias));
		nivelCompetenciaManager.expects(once()).method("getOrdemMaximaByAavaliacaoDesempenhoAndAvaliado").withAnyArguments().will(returnValue(5));
		nivelCompetenciaManager.expects(once()).method("findNiveisCompetenciaByAvDesempenho").withAnyArguments().will(returnValue(niveisCompetencia));
		
		RelatorioAnaliseDesempenhoColaborador relatorioAnaliseDesempenhoColaborador = manager.montaRelatorioAnaliseDesempenhoColaborador(1L, colaborador1.getId(), colaboradorIds, 0, true);
		LinkedList<ResultadoCompetenciaColaborador> resultadoCompetenciaColaboradores = (LinkedList<ResultadoCompetenciaColaborador>) relatorioAnaliseDesempenhoColaborador.getResultadosCompetenciaColaborador();
		assertEquals(1, resultadoCompetenciaColaboradores.size());
		
		ResultadoCompetenciaColaborador resultadoCompetenciaColaborador1 = (ResultadoCompetenciaColaborador) resultadoCompetenciaColaboradores.toArray()[0];
		assertEquals("Competência 1", resultadoCompetenciaColaborador1.getCompetenciaNome());
		assertEquals(4, resultadoCompetenciaColaborador1.getResultadoCompetencias().size());
		
		assertEquals("Auto-Avaliação", ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[0]).getNome());
		assertEquals("Cargo 2", ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[1]).getNome());
		assertEquals("Outros Avaliadores", ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[2]).getNome());
		assertEquals("Média", ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[3]).getNome());
		
		assertEquals(2.0, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[0]).getOrdem());
		assertEquals(6.0, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[1]).getOrdem());
		assertEquals(7.0, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[2]).getOrdem());
		assertEquals(5.25, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[3]).getOrdem());
	}
	
	public void testMontaRelatorioResultadoCompetenciaPorAvaliado(){
		Competencia competencia1 = new Competencia();
		competencia1.setId(1L);
		competencia1.setNome("Competência 1");

		Cargo cargo1 = CargoFactory.getEntity(1L);
		cargo1.setNome("Cargo 1");
		
		Cargo cargo2 = CargoFactory.getEntity(2L);
		cargo2.setNome("Cargo 2");
		
		Cargo cargo3 = CargoFactory.getEntity(3L);
		cargo3.setNome("Cargo 3");
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L, "Colaborador 1");
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L, "Colaborador 2");
		Colaborador colaborador3 = ColaboradorFactory.getEntity(3L, "Colaborador 3");
		Colaborador colaborador4 = ColaboradorFactory.getEntity(4L, "Colaborador 4");

		LinkedList<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = new LinkedList<ConfiguracaoNivelCompetencia>();
		configuracaoNivelCompetencias.add(ConfiguracaoNivelCompetenciaFactory.getEntity(competencia1, colaborador1, cargo1, 2));
		configuracaoNivelCompetencias.add(ConfiguracaoNivelCompetenciaFactory.getEntity(competencia1, colaborador2, cargo2, 3));
		configuracaoNivelCompetencias.add(ConfiguracaoNivelCompetenciaFactory.getEntity(competencia1, colaborador3, cargo3, 7));
		configuracaoNivelCompetencias.add(ConfiguracaoNivelCompetenciaFactory.getEntity(competencia1, colaborador4, cargo2, 9));
		
		Collection<Long> colaboradorIds = new ArrayList<Long>();
		colaboradorIds.add(colaborador2.getId());
		colaboradorIds.add(colaborador4.getId());
		
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity();
		Collection<NivelCompetencia> niveisCompetencia = Arrays.asList(nivelCompetencia);
		
		configuracaoNivelCompetenciaDao.expects(once()).method("findByAvaliacaaDesempenhoAndAvaliado").withAnyArguments().will(returnValue(configuracaoNivelCompetencias));
		nivelCompetenciaManager.expects(once()).method("getOrdemMaximaByAavaliacaoDesempenhoAndAvaliado").withAnyArguments().will(returnValue(5));
		nivelCompetenciaManager.expects(once()).method("findNiveisCompetenciaByAvDesempenho").withAnyArguments().will(returnValue(niveisCompetencia));
		
		RelatorioAnaliseDesempenhoColaborador relatorioAnaliseDesempenhoColaborador = manager.montaRelatorioAnaliseDesempenhoColaborador(1L, colaborador1.getId(), colaboradorIds, 0, false);
		LinkedList<ResultadoCompetenciaColaborador> resultadoCompetenciaColaboradores = (LinkedList<ResultadoCompetenciaColaborador>) relatorioAnaliseDesempenhoColaborador.getResultadosCompetenciaColaborador();
		assertEquals(1, resultadoCompetenciaColaboradores.size());
		
		ResultadoCompetenciaColaborador resultadoCompetenciaColaborador1 = (ResultadoCompetenciaColaborador) resultadoCompetenciaColaboradores.toArray()[0];
		assertEquals("Competência 1", resultadoCompetenciaColaborador1.getCompetenciaNome());
		assertEquals(5, resultadoCompetenciaColaborador1.getResultadoCompetencias().size());
		
		assertEquals("Auto-Avaliação", ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[0]).getNome());
		assertEquals("Colaborador 2", ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[1]).getNome());
		assertEquals("Colaborador 4", ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[2]).getNome());
		assertEquals("Outros Avaliadores", ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[3]).getNome());
		assertEquals("Média", ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[4]).getNome());
		
		assertEquals(2.0, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[0]).getOrdem());
		assertEquals(3.0, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[1]).getOrdem());
		assertEquals(9.0, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[2]).getOrdem());
		assertEquals(7.0, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[3]).getOrdem());
		assertEquals(5.25, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[4]).getOrdem());
	}
}