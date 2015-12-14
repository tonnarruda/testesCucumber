package com.fortes.rh.test.business.captacao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.ConfigHistoricoNivelManager;
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
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
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
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfigHistoricoNivelFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaHistoricoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

public class ConfiguracaoNivelCompetenciaManagerTest extends MockObjectTestCase
{
	private ConfiguracaoNivelCompetenciaManagerImpl configuracaoNivelCompetenciaManager = new ConfiguracaoNivelCompetenciaManagerImpl();
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
	
	protected void setUp() throws Exception
    {
        super.setUp();
        configuracaoNivelCompetenciaDao = new Mock(ConfiguracaoNivelCompetenciaDao.class);
        configuracaoNivelCompetenciaManager.setDao((ConfiguracaoNivelCompetenciaDao) configuracaoNivelCompetenciaDao.proxy());

        nivelCompetenciaManager = new Mock(NivelCompetenciaManager.class);
        configuracaoNivelCompetenciaManager.setNivelCompetenciaManager((NivelCompetenciaManager) nivelCompetenciaManager.proxy());

        candidatoSolicitacaoManager = new Mock(CandidatoSolicitacaoManager.class);
        configuracaoNivelCompetenciaManager.setCandidatoSolicitacaoManager((CandidatoSolicitacaoManager) candidatoSolicitacaoManager.proxy());
        
        configuracaoNivelCompetenciaColaboradorManager = new Mock(ConfiguracaoNivelCompetenciaColaboradorManager.class);
        configuracaoNivelCompetenciaManager.setConfiguracaoNivelCompetenciaColaboradorManager((ConfiguracaoNivelCompetenciaColaboradorManager) configuracaoNivelCompetenciaColaboradorManager.proxy());
        
        configuracaoNivelCompetenciaCriterioManager = new Mock(ConfiguracaoNivelCompetenciaCriterioManager.class);
        configuracaoNivelCompetenciaManager.setConfiguracaoNivelCompetenciaCriterioManager((ConfiguracaoNivelCompetenciaCriterioManager) configuracaoNivelCompetenciaCriterioManager.proxy());
        
        criterioAvaliacaoCompetenciaManager = new Mock(CriterioAvaliacaoCompetenciaManager.class);
        configuracaoNivelCompetenciaManager.setCriterioAvaliacaoCompetenciaManager((CriterioAvaliacaoCompetenciaManager) criterioAvaliacaoCompetenciaManager.proxy());
        
        colaboradorRespostaManager = new Mock(ColaboradorRespostaManager.class);
        MockSpringUtil.mocks.put("colaboradorRespostaManager", colaboradorRespostaManager);
        
        colaboradorQuestionarioManager = new Mock(ColaboradorQuestionarioManager.class);
        MockSpringUtil.mocks.put("colaboradorQuestionarioManager", colaboradorQuestionarioManager);
        
        configHistoricoNivelManager = new Mock(ConfigHistoricoNivelManager.class);
        configuracaoNivelCompetenciaManager.setConfigHistoricoNivelManager((ConfigHistoricoNivelManager) configHistoricoNivelManager.proxy());
        
        configuracaoNivelCompetenciaFaixaSalarialManager = new Mock(ConfiguracaoNivelCompetenciaFaixaSalarialManager.class);
        configuracaoNivelCompetenciaManager.setConfiguracaoNivelCompetenciaFaixaSalarialManager( (ConfiguracaoNivelCompetenciaFaixaSalarialManager) configuracaoNivelCompetenciaFaixaSalarialManager.proxy());

        
        
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
		
		configuracaoNivelCompetenciaDao.expects(once()).method("deleteConfiguracaoByCandidatoFaixa").with(eq(candidato.getId()), eq(faixaSalarial.getId()), eq(solicitacao.getId())).isVoid();
		configuracaoNivelCompetenciaDao.expects(once()).method("save").with(ANYTHING).isVoid();
	
		configuracaoNivelCompetenciaManager.saveCompetenciasCandidato(niveisCompetenciaFaixaSalariais, faixaSalarial.getId(), candidato.getId(), solicitacao.getId());
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
		
		configuracaoNivelCompetenciaManager.saveCompetenciasColaborador(niveisCompetenciaFaixaSalariais, configuracaoNivelCompetenciaColaborador);
	}
	
	public void testSaveCompetenciasColaboradorInsertComColaboradorQuestionarioEAvaliadorComIdNuloSemConfiguracaoNivelCompetencia()
	{
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador.setColaboradorQuestionario(ColaboradorQuestionarioFactory.getEntity());
		configuracaoNivelCompetenciaColaborador.setAvaliador(ColaboradorFactory.getEntity());
		
		configuracaoNivelCompetenciaColaboradorManager.expects(once()).method("save").with(eq(configuracaoNivelCompetenciaColaborador)).isVoid();
		
		configuracaoNivelCompetenciaManager.saveCompetenciasColaborador(null, configuracaoNivelCompetenciaColaborador);
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
		
		configuracaoNivelCompetenciaManager.saveCompetenciasColaborador(niveisCompetenciaFaixaSalariais, configuracaoNivelCompetenciaColaborador);
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
		
		configuracaoNivelCompetenciaManager.saveCompetenciasColaborador(niveisCompetenciaFaixaSalariais, configuracaoNivelCompetenciaColaborador);
	}
	
	public void testSaveCompetenciasColaboradorAndRecalculaPerformance()
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity();
		Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
		colaboradorQuestionario.setAvaliacaoDesempenho(AvaliacaoDesempenhoFactory.getEntity());
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
		
		configuracaoNivelCompetenciaManager.saveCompetenciasColaboradorAndRecalculaPerformance(1L, niveisCompetenciaFaixaSalariais, configuracaoNivelCompetenciaColaborador);
	}
	
	public void testMontaRelatorioConfiguracaoNivelCompetencia()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Colaborador joao = ColaboradorFactory.getEntity(1L);
		joao.setNome("joao");

		Colaborador maria = ColaboradorFactory.getEntity(2L);
		maria.setNome("maria");

		Colaborador avaliador = ColaboradorFactory.getEntity(3L);
		maria.setNome("avaliador");

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(999999999999L);
		
		Atitude atitude = AtitudeFactory.getEntity(1L);
		atitude.setNome("Java");
		
		Atitude atitude2 = AtitudeFactory.getEntity(2L);
		atitude2.setNome("C#");
		
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento(3L);
		conhecimento.setNome("Delphi");
		
		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity(1L);
		nivelCompetenciaHistorico.setEmpresa(empresa);
		nivelCompetenciaHistorico.setData(DateUtil.criarDataMesAno(1, 1, 2015));
		
		NivelCompetencia nivelPessimo = NivelCompetenciaFactory.getEntity();
		nivelPessimo.setDescricao("pessimo");
		nivelPessimo.setOrdem(1);

		ConfigHistoricoNivel configHistoricoNivelPessimo = new ConfigHistoricoNivel();
		configHistoricoNivelPessimo.setOrdem(1);
		configHistoricoNivelPessimo.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		configHistoricoNivelPessimo.setNivelCompetencia(nivelPessimo);

		NivelCompetencia nivelRuim = NivelCompetenciaFactory.getEntity();
		nivelRuim.setDescricao("ruim");
		nivelRuim.setOrdem(2);

		ConfigHistoricoNivel configHistoricoNivelRuim = new ConfigHistoricoNivel();
		configHistoricoNivelRuim.setOrdem(2);
		configHistoricoNivelRuim.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		configHistoricoNivelRuim.setNivelCompetencia(nivelRuim);

		NivelCompetencia nivelBom = NivelCompetenciaFactory.getEntity();
		nivelBom.setDescricao("bom");
		nivelBom.setOrdem(3);

		ConfigHistoricoNivel configHistoricoNivelBom = new ConfigHistoricoNivel();
		configHistoricoNivelBom.setOrdem(3);
		configHistoricoNivelBom.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		configHistoricoNivelBom.setNivelCompetencia(nivelBom);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(1L);
		configuracaoNivelCompetenciaFaixaSalarial.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		configuracaoNivelCompetenciaFaixaSalarial.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetenciaFaixaSalarial.setData(DateUtil.criarDataMesAno(1, 1, 2000));
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaJoao = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity(999999999998L);
		configuracaoNivelCompetenciaJoao.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		configuracaoNivelCompetenciaJoao.setData(DateUtil.criarDataMesAno(1, 1, 2010));
		configuracaoNivelCompetenciaJoao.setColaborador(joao);
		configuracaoNivelCompetenciaJoao.setAvaliador(avaliador);
		configuracaoNivelCompetenciaJoao.setFaixaSalarial(faixaSalarial);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaMaria = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity(999999999999L);
		configuracaoNivelCompetenciaMaria.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		configuracaoNivelCompetenciaMaria.setData(DateUtil.criarDataMesAno(1, 1, 2010));
		configuracaoNivelCompetenciaMaria.setColaborador(maria);
		configuracaoNivelCompetenciaMaria.setAvaliador(avaliador);
		configuracaoNivelCompetenciaMaria.setFaixaSalarial(faixaSalarial);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia1.setId(1L);
		configuracaoNivelCompetencia1.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaJoao);
		configuracaoNivelCompetencia1.setCompetenciaId(atitude.getId());
		configuracaoNivelCompetencia1.setCompetenciaDescricao(atitude.getNome());
		configuracaoNivelCompetencia1.setNivelCompetencia(nivelBom);
		configuracaoNivelCompetencia1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia2 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia2.setId(2L);
		configuracaoNivelCompetencia2.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaJoao);
		configuracaoNivelCompetencia2.setCompetenciaId(conhecimento.getId());
		configuracaoNivelCompetencia2.setCompetenciaDescricao(conhecimento.getNome());
		configuracaoNivelCompetencia2.setNivelCompetencia(nivelRuim);
		configuracaoNivelCompetencia2.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia3 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia3.setId(3L);
		configuracaoNivelCompetencia3.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaMaria);
		configuracaoNivelCompetencia3.setCompetenciaId(atitude2.getId());
		configuracaoNivelCompetencia3.setCompetenciaDescricao(atitude2.getNome());
		configuracaoNivelCompetencia3.setNivelCompetencia(nivelPessimo);
		configuracaoNivelCompetencia3.setTipoCompetencia(TipoCompetencia.ATITUDE);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaFaixa1 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetenciaFaixa1.setId(1L);
		configuracaoNivelCompetenciaFaixa1.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		configuracaoNivelCompetenciaFaixa1.setCompetenciaId(atitude.getId());
		configuracaoNivelCompetenciaFaixa1.setCompetenciaDescricao(atitude.getNome());
		configuracaoNivelCompetenciaFaixa1.setNivelCompetencia(nivelBom);
		configuracaoNivelCompetenciaFaixa1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaFaixa2 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetenciaFaixa2.setId(2L);
		configuracaoNivelCompetenciaFaixa2.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		configuracaoNivelCompetenciaFaixa2.setCompetenciaId(conhecimento.getId());
		configuracaoNivelCompetenciaFaixa2.setCompetenciaDescricao(conhecimento.getNome());
		configuracaoNivelCompetenciaFaixa2.setNivelCompetencia(nivelRuim);
		configuracaoNivelCompetenciaFaixa2.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetenciaFaixa3 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetenciaFaixa3.setId(3L);
		configuracaoNivelCompetenciaFaixa3.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		configuracaoNivelCompetenciaFaixa3.setCompetenciaId(atitude2.getId());
		configuracaoNivelCompetenciaFaixa3.setCompetenciaDescricao(atitude2.getNome());
		configuracaoNivelCompetenciaFaixa3.setNivelCompetencia(nivelBom);
		configuracaoNivelCompetenciaFaixa3.setTipoCompetencia(TipoCompetencia.ATITUDE);
		
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = Arrays.asList(configuracaoNivelCompetencia1,configuracaoNivelCompetencia2,configuracaoNivelCompetencia3);
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciaFaixas = Arrays.asList(configuracaoNivelCompetenciaFaixa1,configuracaoNivelCompetenciaFaixa2,configuracaoNivelCompetenciaFaixa3);
		
		
		Collection<ConfiguracaoNivelCompetenciaColaborador> configuracaoNivelCompetenciasColaborador = Arrays.asList(configuracaoNivelCompetenciaJoao, configuracaoNivelCompetenciaMaria);
		
		CollectionUtil<ConfiguracaoNivelCompetencia> clu = new CollectionUtil<ConfiguracaoNivelCompetencia>();
		Long[] competenciaIds = clu.convertCollectionToArrayIds(configuracaoNivelCompetencias);

		Collection<NivelCompetencia> nivelCompetencias = Arrays.asList(nivelPessimo,nivelRuim,nivelBom);
		Date dataIni = DateUtil.criarDataMesAno(1, 1, 2010);
		Date dataFim = DateUtil.criarDataMesAno(1, 1, 2015);
		
		configuracaoNivelCompetenciaColaboradorManager.expects(once()).method("findByDataAndFaixaSalarial").with(eq(dataIni), eq(dataFim), eq(faixaSalarial.getId())).will(returnValue(configuracaoNivelCompetenciasColaborador));
		configuracaoNivelCompetenciaDao.expects(atLeastOnce()).method("findCompetenciasConfiguracaoNivelCompetenciaFaixaSalarial").with(eq(competenciaIds),eq(configuracaoNivelCompetenciaFaixaSalarial.getId())).will(returnValue(configuracaoNivelCompetenciaFaixas));
		nivelCompetenciaManager.expects(atLeastOnce()).method("findAllSelect").with(eq(empresa.getId()), eq(configuracaoNivelCompetenciaFaixaSalarial.getNivelCompetenciaHistorico().getId()), eq(configuracaoNivelCompetenciaFaixaSalarial.getData())).will(returnValue(nivelCompetencias));
		configuracaoNivelCompetenciaDao.expects(atLeastOnce()).method("findByConfiguracaoNivelCompetenciaColaborador").with(eq(competenciaIds),ANYTHING, eq(configuracaoNivelCompetenciaFaixaSalarial.getData())).will(returnValue(configuracaoNivelCompetencias));
		configuracaoNivelCompetenciaColaboradorManager.expects(atLeastOnce()).method("verificaAvaliadorAnonimo").with(ANYTHING).isVoid();
		Collection<ConfiguracaoNivelCompetenciaVO> result = configuracaoNivelCompetenciaManager.montaRelatorioConfiguracaoNivelCompetencia(dataIni,dataFim, empresa.getId(), faixaSalarial.getId(), competenciaIds);
		
		assertEquals(2, result.size());

		Collection<MatrizCompetenciaNivelConfiguracao> matrizJoao = ((ConfiguracaoNivelCompetenciaVO)result.toArray()[0]).getMatrizes();
		Collection<MatrizCompetenciaNivelConfiguracao> matrizMaria = ((ConfiguracaoNivelCompetenciaVO)result.toArray()[1]).getMatrizes();
		
		assertEquals("Matriz padrão (12)", 12, matrizJoao.size());
		assertEquals("Matriz padrão (12)", 12, matrizMaria.size());

		assertFalse(((MatrizCompetenciaNivelConfiguracao)matrizJoao.toArray()[0]).getConfiguracao());
		assertFalse(((MatrizCompetenciaNivelConfiguracao)matrizJoao.toArray()[7]).getConfiguracao());

		assertEquals(new Integer(0), ((MatrizCompetenciaNivelConfiguracao)matrizJoao.toArray()[3]).getGap());
		assertEquals(new Integer(0), ((MatrizCompetenciaNivelConfiguracao)matrizJoao.toArray()[7]).getGap());
		assertEquals(new Integer(-2), ((MatrizCompetenciaNivelConfiguracao)matrizJoao.toArray()[11]).getGap());

		assertEquals(new Integer(0), ((MatrizCompetenciaNivelConfiguracao)matrizMaria.toArray()[3]).getGap());
		assertEquals(new Integer(0), ((MatrizCompetenciaNivelConfiguracao)matrizMaria.toArray()[7]).getGap());
		assertEquals(new Integer(-2), ((MatrizCompetenciaNivelConfiguracao)matrizMaria.toArray()[11]).getGap());
		
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
		
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = Arrays.asList(configuracaoNivelCompetencia1,configuracaoNivelCompetencia2,configuracaoNivelCompetencia3);
		
		Collection<NivelCompetencia> nivelCompetencias = Arrays.asList(nivelPessimo,nivelRuim,nivelBom);
		
		configuracaoNivelCompetenciaDao.expects(once()).method("findCompetenciaByFaixaSalarial").with(new Constraint[]{eq(faixaSalarial.getId()), eq(configuracaoNivelCompetenciaFaixaSalarial.getData()), eq(configuracaoNivelCompetenciaFaixaSalarial.getId()), ANYTHING, ANYTHING}).will(returnValue(configuracaoNivelCompetencias));
		nivelCompetenciaManager.expects(once()).method("findAllSelect").with(eq(empresa.getId()), ANYTHING, eq(configuracaoNivelCompetenciaFaixaSalarial.getData())).will(returnValue(nivelCompetencias));
		criterioAvaliacaoCompetenciaManager.expects(atLeastOnce()).method("findByCompetenciaAndCNCFId").withAnyArguments().will(returnValue(new ArrayList<CriterioAvaliacaoCompetencia>()));
		configuracaoNivelCompetenciaFaixaSalarialManager.expects(atLeastOnce()).method("findByFaixaSalarialIdAndData").with(eq(faixaSalarial.getId()), ANYTHING).will(returnValue(configuracaoNivelCompetenciaFaixaSalarial));
		
		Collection<MatrizCompetenciaNivelConfiguracao> result = configuracaoNivelCompetenciaManager.montaConfiguracaoNivelCompetenciaByFaixa(empresa.getId(), faixaSalarial.getId(), null);
		
		assertEquals(9, result.size());
	}

	public void testMontaMatrizCNCByQuestionario(){
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		
		Colaborador avaliado = ColaboradorFactory.getEntity(1L);
		avaliado.setNome("Avaliado");
		
		Colaborador avaliador = ColaboradorFactory.getEntity(1L);
		avaliador.setNome("Avaliador");

		NivelCompetencia nivelRuim = NivelCompetenciaFactory.getEntity();
		nivelRuim.setDescricao("ruim");
		nivelRuim.setOrdem(1);
		
		NivelCompetencia nivelPessimo = NivelCompetenciaFactory.getEntity();
		nivelPessimo.setDescricao("Péssimo");
		nivelPessimo.setOrdem(2);
		
		NivelCompetencia nivelBom = NivelCompetenciaFactory.getEntity();
		nivelBom.setDescricao("bom");
		nivelBom.setOrdem(3);

		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity(1L);
		configuracaoNivelCompetenciaColaborador.setFaixaSalarial(faixaSalarial);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
		colaboradorQuestionario.setRespondidaEm(new Date());
		colaboradorQuestionario.setColaborador(avaliado);
		colaboradorQuestionario.setAvaliador(avaliador);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1ExigidoPelaFaixa = criaConfigCompetencia("Comunicação", faixaSalarial, nivelRuim, TipoCompetencia.ATITUDE, null, null, null);
		configuracaoNivelCompetencia1ExigidoPelaFaixa.setId(1L);

		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia2ExigidoPelaFaixa = criaConfigCompetencia("Conhecimento Técnico", faixaSalarial, nivelBom, TipoCompetencia.CONHECIMENTO, null, null, null);
		configuracaoNivelCompetencia2ExigidoPelaFaixa.setId(2L);

		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1DoColaborador = criaConfigCompetencia("Comunicação", faixaSalarial, nivelPessimo, TipoCompetencia.ATITUDE, null, null, null);
		configuracaoNivelCompetencia1DoColaborador.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador);
		configuracaoNivelCompetencia1DoColaborador.setId(3L);

		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia2DoColaborador = criaConfigCompetencia("Conhecimento Técnico", faixaSalarial, nivelBom, TipoCompetencia.CONHECIMENTO, null, null, null);
		configuracaoNivelCompetencia2DoColaborador.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador);
		configuracaoNivelCompetencia2DoColaborador.setId(4L);
		
		Collection<NivelCompetencia> niveisCompetencia = Arrays.asList(nivelBom, nivelRuim, nivelPessimo);
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciasDoColaborador = Arrays.asList(configuracaoNivelCompetencia1DoColaborador, configuracaoNivelCompetencia2DoColaborador);
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciasExigidasPelaFaixa = Arrays.asList(configuracaoNivelCompetencia1ExigidoPelaFaixa,configuracaoNivelCompetencia2ExigidoPelaFaixa);
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(1L,faixaSalarial, DateUtil.criarDataMesAno(1, 2, 2015));
		
		
		configuracaoNivelCompetenciaColaboradorManager.expects(once()).method("findByData").with(eq(colaboradorQuestionario.getRespondidaEm()), eq(colaboradorQuestionario.getColaborador().getId()), eq(colaboradorQuestionario.getAvaliador().getId()), eq(colaboradorQuestionario.getId())).will(returnValue(configuracaoNivelCompetenciaColaborador));
		
		configuracaoNivelCompetenciaFaixaSalarialManager.expects(atLeastOnce()).method("findByFaixaSalarialIdAndData").with(eq(faixaSalarial.getId()), ANYTHING).will(returnValue(configuracaoNivelCompetenciaFaixaSalarial));
		nivelCompetenciaManager.expects(once()).method("findAllSelect").with(eq(empresa.getId()), ANYTHING, eq(configuracaoNivelCompetenciaFaixaSalarial.getData())).will(returnValue(niveisCompetencia));
		configuracaoNivelCompetenciaDao.expects(once()).method("findCompetenciaByFaixaSalarial").with(new Constraint[]{eq(configuracaoNivelCompetenciaColaborador.getId()), eq(configuracaoNivelCompetenciaFaixaSalarial.getData()), eq(configuracaoNivelCompetenciaFaixaSalarial.getId()), ANYTHING, ANYTHING}).will(returnValue(configuracaoNivelCompetenciasExigidasPelaFaixa));
		configuracaoNivelCompetenciaDao.expects(once()).method("findByConfiguracaoNivelCompetenciaColaborador").with(eq(null),eq(configuracaoNivelCompetenciaColaborador.getId()),eq(configuracaoNivelCompetenciaFaixaSalarial.getData())).will(returnValue(configuracaoNivelCompetenciasDoColaborador));
		criterioAvaliacaoCompetenciaManager.expects(atLeastOnce()).method("findByCompetenciaAndCNCFId").withAnyArguments().will(returnValue(new ArrayList<CriterioAvaliacaoCompetencia>()));
		configuracaoNivelCompetenciaCriterioManager.expects(atLeastOnce()).method("findByConfiguracaoNivelCompetencia").withAnyArguments().will(returnValue(new ArrayList<CriterioAvaliacaoCompetencia>()));
		
		Collection<MatrizCompetenciaNivelConfiguracao> matrizCompetencias = configuracaoNivelCompetenciaManager.montaMatrizCNCByQuestionario(colaboradorQuestionario, empresa.getId()); 
		
		assertEquals(6, matrizCompetencias.size());
	}	

	public void testMontaMatrizCompetenciaCandidato()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(999999999999L);

		Date data = DateUtil.criarDataDiaMesAno("24/11/2015");
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		solicitacao.setData(data);
		solicitacao.setFaixaSalarial(faixaSalarial);
		
		Candidato joao = CandidatoFactory.getCandidato(1L);
		joao.setNome("joao");

		Collection<Long> candidatosIds= Arrays.asList(joao.getId());

		candidatoSolicitacaoManager.expects(once()).method("getCandidatosBySolicitacao").with(eq(solicitacao.getId())).will(returnValue(candidatosIds));

		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity();
		nivelCompetenciaHistorico.setData(data);
		nivelCompetenciaHistorico.setEmpresa(empresa);
		
		NivelCompetencia nivelPessimo = NivelCompetenciaFactory.getEntity();
		nivelPessimo.setDescricao("pessimo");
		nivelPessimo.setOrdem(1);
		
		ConfigHistoricoNivel configHistoricoNivelPessimo = ConfigHistoricoNivelFactory.getEntity(1L);
		configHistoricoNivelPessimo.setOrdem(1);
		configHistoricoNivelPessimo.setNivelCompetencia(nivelPessimo);
		configHistoricoNivelPessimo.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		nivelPessimo.setConfigHistoricoNiveis(Arrays.asList(configHistoricoNivelPessimo));
		
		NivelCompetencia nivelRuim = NivelCompetenciaFactory.getEntity();
		nivelRuim.setDescricao("ruim");
		nivelRuim.setOrdem(2);
		
		ConfigHistoricoNivel configHistoricoNivelRuim = ConfigHistoricoNivelFactory.getEntity(1L);
		configHistoricoNivelRuim.setOrdem(2);
		configHistoricoNivelRuim.setNivelCompetencia(nivelRuim);
		configHistoricoNivelRuim.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		nivelRuim.setConfigHistoricoNiveis(Arrays.asList(configHistoricoNivelRuim));
		
		NivelCompetencia nivelBom = NivelCompetenciaFactory.getEntity();
		nivelBom.setDescricao("bom");
		nivelBom.setOrdem(3);

		ConfigHistoricoNivel configHistoricoNivelBom = ConfigHistoricoNivelFactory.getEntity(1L);
		configHistoricoNivelBom.setOrdem(3);
		configHistoricoNivelBom.setNivelCompetencia(nivelBom);
		configHistoricoNivelBom.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		
		nivelBom.setConfigHistoricoNiveis(Arrays.asList(configHistoricoNivelBom));

		Collection<ConfigHistoricoNivel> configHistoricoNiveis = Arrays.asList(configHistoricoNivelPessimo, configHistoricoNivelRuim, configHistoricoNivelBom);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity();
		configuracaoNivelCompetenciaFaixaSalarial.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetenciaFaixaSalarial.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		configuracaoNivelCompetenciaFaixaSalarial.setData(data);
		
		
		ConfiguracaoNivelCompetencia configuracaoNivelFaixaCompetencia1 = criaConfigCompetencia("Comunicação", null, nivelBom, TipoCompetencia.ATITUDE, null, configuracaoNivelCompetenciaFaixaSalarial, null);
		ConfiguracaoNivelCompetencia configuracaoNivelFaixaCompetencia2 = criaConfigCompetencia("Liderança", null, nivelBom, TipoCompetencia.CONHECIMENTO, null, configuracaoNivelCompetenciaFaixaSalarial, null);

		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1CandidatoJoao = criaConfigCompetencia("Comunicação", faixaSalarial, nivelBom, TipoCompetencia.CONHECIMENTO, joao, null, solicitacao);
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia2CandidatoJoao = criaConfigCompetencia("Liderança", faixaSalarial, nivelBom, TipoCompetencia.ATITUDE, joao, null, solicitacao);

		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciasFaixaSalarial = Arrays.asList(configuracaoNivelFaixaCompetencia1, configuracaoNivelFaixaCompetencia2);
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciasCandidato = Arrays.asList(configuracaoNivelCompetencia1CandidatoJoao, configuracaoNivelCompetencia2CandidatoJoao);

		configuracaoNivelCompetenciaDao.expects(once()).method("findCompetenciaByFaixaSalarial").with(new Constraint[]{eq(faixaSalarial.getId()), eq(solicitacao.getData()), eq(null), ANYTHING, ANYTHING}).will(returnValue(configuracaoNivelCompetenciasFaixaSalarial));
		configHistoricoNivelManager.expects(once()).method("findByEmpresaAndDataNivelCompetenciaHistorico").with(eq(empresa.getId()), eq(solicitacao.getData())).will(returnValue(configHistoricoNiveis));	
		configuracaoNivelCompetenciaDao.expects(once()).method("findBySolicitacaoIdCandidatoIdAndDataNivelCompetenciaHistorico").with(eq(solicitacao.getId()), eq(joao.getId()), eq(solicitacao.getData())).will(returnValue(configuracaoNivelCompetenciasCandidato));

		Collection<ConfiguracaoNivelCompetenciaVO> matrizes = configuracaoNivelCompetenciaManager.montaMatrizCompetenciaCandidato(empresa.getId(), faixaSalarial.getId(), solicitacao);
		assertEquals(1, matrizes.size());
		
		ConfiguracaoNivelCompetenciaVO matrizJoao = (ConfiguracaoNivelCompetenciaVO) matrizes.iterator().next();
		
		assertEquals("joao", matrizJoao.getNome());
		
		assertEquals("matriz Joao", 8, matrizJoao.getMatrizes().size());

		List<MatrizCompetenciaNivelConfiguracao> matrizConfiguradaJoao = (List<MatrizCompetenciaNivelConfiguracao>) matrizJoao.getMatrizes();
		assertEquals("configuracao candidato", Boolean.TRUE, matrizConfiguradaJoao.get(2).getConfiguracao());
		assertEquals("configuracao faixa", Boolean.TRUE, matrizConfiguradaJoao.get(2).getConfiguracaoFaixa());

		assertEquals("gap", new Integer(0), matrizConfiguradaJoao.get(3).getGap());
	}

	private ConfiguracaoNivelCompetencia criaConfigCompetencia(String descricao, FaixaSalarial faixaSalarial, NivelCompetencia nivel, char tipo, Candidato candidato, ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial, Solicitacao solicitacao)
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia.setCompetenciaDescricao(descricao);
		configuracaoNivelCompetencia.setNivelCompetencia(nivel);
		configuracaoNivelCompetencia.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetencia.setCandidato(candidato);
		configuracaoNivelCompetencia.setTipoCompetencia(tipo);
		configuracaoNivelCompetencia.setConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		
		return configuracaoNivelCompetencia;
	}
	
	public void testGetCompetenciasCandidato()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		
		ConfiguracaoNivelCompetencia nivelConhecimento = new ConfiguracaoNivelCompetencia(TipoCompetencia.CONHECIMENTO, 1L, "", null, 1);
		nivelConhecimento.setFaixaSalarial(faixaSalarial);
		nivelConhecimento.setSolicitacao(solicitacao);

		ConfiguracaoNivelCompetencia nivelAtitude = new ConfiguracaoNivelCompetencia(TipoCompetencia.ATITUDE, 3L, "", null, 1);
		nivelAtitude.setFaixaSalarial(faixaSalarial);
		nivelAtitude.setSolicitacao(solicitacao);

		Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaCandidato = Arrays.asList(nivelConhecimento, nivelAtitude);

		nivelConhecimento.setCompetenciaDescricao("java");
		nivelAtitude.setCompetenciaDescricao("proatividade");
		ConfiguracaoNivelCompetencia nivelHabilidade = new ConfiguracaoNivelCompetencia(TipoCompetencia.HABILIDADE, 2L, "comunicacao", null, 1);
		
		Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaEmpresa = Arrays.asList(nivelConhecimento, nivelHabilidade, nivelAtitude);

		configuracaoNivelCompetenciaDao.expects(once()).method("findByCandidatoAndSolicitacao").withAnyArguments().will(returnValue(niveisCompetenciaCandidato));
		configuracaoNivelCompetenciaDao.expects(once()).method("findByFaixa").withAnyArguments().will(returnValue(new ArrayList<ConfiguracaoNivelCompetencia>()));
		nivelCompetenciaManager.expects(once()).method("findByCargoOrEmpresa").with(ANYTHING,ANYTHING).will(returnValue(niveisCompetenciaEmpresa));
		nivelCompetenciaManager.expects(once()).method("findAllSelect").withAnyArguments().will(returnValue(new ArrayList<NivelCompetencia>()));
		
		Collection<Solicitacao> solicitacaoCoimConhecimento = configuracaoNivelCompetenciaManager.getCompetenciasCandidato(1L, 1L); 
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
		assertEquals(1, configuracaoNivelCompetenciaManager.findColaboradorAbaixoNivel(new Long[]{1L, 2L}, faixaSalarial.getId(), null).size());
	}
	
	public void testCriaCNCColaboradorByCNCCnadidato()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		Candidato candidato = CandidatoFactory.getCandidato(2L);
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(3L);
		Colaborador colaborador = ColaboradorFactory.getEntity(22L);
		
		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity(33L);
		historico.setFaixaSalarial(faixaSalarial);
		historico.setData(DateUtil.criarDataDiaMesAno("01/06/2015"));
		
		Atitude atitude = AtitudeFactory.getEntity(1L);
		atitude.setNome("Prestativo");
		
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento(1L);
		conhecimento.setNome("Java");
		
		Habilidade habilidade= HabilidadeFactory.getEntity(6L);
		habilidade.setNome("Magica");
		
		NivelCompetencia nivelBom = NivelCompetenciaFactory.getEntity(4L);
		nivelBom.setDescricao("pessimo");
		nivelBom.setOrdem(1);
		
		NivelCompetencia nivelRuim = NivelCompetenciaFactory.getEntity(5L);
		nivelRuim.setDescricao("ruim");
		nivelRuim.setOrdem(2);
		
		ConfiguracaoNivelCompetencia cncCandidato1 = ConfiguracaoNivelCompetenciaFactory.getEntityCandidato(candidato, solicitacao, faixaSalarial, nivelBom, conhecimento.getId(), TipoCompetencia.CONHECIMENTO);
		ConfiguracaoNivelCompetencia cncCandidato2 = ConfiguracaoNivelCompetenciaFactory.getEntityCandidato(candidato, solicitacao, faixaSalarial, nivelBom, atitude.getId(), TipoCompetencia.ATITUDE);
		Collection<ConfiguracaoNivelCompetencia> cncscandidato = Arrays.asList(cncCandidato1, cncCandidato2);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial cncFaixa = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(10L); 
		ConfiguracaoNivelCompetencia cncFaixaSalarial1 = ConfiguracaoNivelCompetenciaFactory.getEntityFaixaSalarial(cncFaixa, nivelRuim, atitude.getId(), TipoCompetencia.ATITUDE);
		ConfiguracaoNivelCompetencia cncFaixaSalarial2 = ConfiguracaoNivelCompetenciaFactory.getEntityFaixaSalarial(cncFaixa, nivelRuim, habilidade.getId(), TipoCompetencia.HABILIDADE);
		Collection<ConfiguracaoNivelCompetencia> cncsFaixaSalarial = Arrays.asList(cncFaixaSalarial1, cncFaixaSalarial2);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity(1L);
		configuracaoNivelCompetenciaColaborador.setColaboradorQuestionario(null);
		configuracaoNivelCompetenciaColaborador.setAvaliador(null);
		
		configuracaoNivelCompetenciaColaboradorManager.expects(once()).method("save").withAnyArguments().isVoid();
		configuracaoNivelCompetenciaDao.expects(once()).method("save").with(ANYTHING).isVoid();
		configuracaoNivelCompetenciaDao.expects(once()).method("findByCandidatoAndSolicitacao").with(eq(candidato.getId()), eq(solicitacao.getId())).will(returnValue(cncscandidato));
		configuracaoNivelCompetenciaDao.expects(once()).method("findByFaixa").with(eq(faixaSalarial.getId()), ANYTHING).will(returnValue(cncsFaixaSalarial));
		
		Exception ex = null;
		try {
			configuracaoNivelCompetenciaManager.criaCNCColaboradorByCNCCnadidato(colaborador, candidato.getId(), solicitacao, historico);
		} catch (Exception e) {
			ex = e;
		}
		
		assertNull(ex);
	}
}
