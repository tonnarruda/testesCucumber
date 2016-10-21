package com.fortes.rh.test.business.captacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoManager;
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
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCriterio;
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
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfigHistoricoNivelFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaCandidatoFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaCriterioFactory;
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
import com.fortes.rh.test.util.mockObjects.MockSpringUtilJUnit4;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

public class ConfiguracaoNivelCompetenciaManagerTest
{
	private ConfiguracaoNivelCompetenciaManagerImpl manager = new ConfiguracaoNivelCompetenciaManagerImpl();
	private NivelCompetenciaManager nivelCompetenciaManager;
	private ColaboradorRespostaManager colaboradorRespostaManager;
	private ConfigHistoricoNivelManager configHistoricoNivelManager;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
	private ConfiguracaoNivelCompetenciaDao configuracaoNivelCompetenciaDao;
	private CriterioAvaliacaoCompetenciaManager criterioAvaliacaoCompetenciaManager;
	private ConfiguracaoNivelCompetenciaColaboradorManager configuracaoNivelCompetenciaColaboradorManager;
	private ConfiguracaoNivelCompetenciaCriterioManager configuracaoNivelCompetenciaCriterioManager;
	private ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager;
	private ConfiguracaoNivelCompetenciaCandidatoManager configuracaoNivelCompetenciaCandidatoManager;
	private ConfiguracaoCompetenciaAvaliacaoDesempenhoManager configuracaoCompetenciaAvaliacaoDesempenhoManager;
	
	@Before
	public void setUp() throws Exception
    {
        configuracaoNivelCompetenciaDao = mock(ConfiguracaoNivelCompetenciaDao.class);
        manager.setDao(configuracaoNivelCompetenciaDao);

        nivelCompetenciaManager = mock(NivelCompetenciaManager.class);
        manager.setNivelCompetenciaManager(nivelCompetenciaManager);

        candidatoSolicitacaoManager = mock(CandidatoSolicitacaoManager.class);
        manager.setCandidatoSolicitacaoManager(candidatoSolicitacaoManager);
        
        configuracaoNivelCompetenciaColaboradorManager = mock(ConfiguracaoNivelCompetenciaColaboradorManager.class);
        manager.setConfiguracaoNivelCompetenciaColaboradorManager(configuracaoNivelCompetenciaColaboradorManager);
        
        configuracaoNivelCompetenciaCriterioManager = mock(ConfiguracaoNivelCompetenciaCriterioManager.class);
        manager.setConfiguracaoNivelCompetenciaCriterioManager(configuracaoNivelCompetenciaCriterioManager);
        
        criterioAvaliacaoCompetenciaManager = mock(CriterioAvaliacaoCompetenciaManager.class);
        manager.setCriterioAvaliacaoCompetenciaManager(criterioAvaliacaoCompetenciaManager);
        
        configHistoricoNivelManager = mock(ConfigHistoricoNivelManager.class);
        manager.setConfigHistoricoNivelManager(configHistoricoNivelManager);
        
        configuracaoNivelCompetenciaFaixaSalarialManager = mock(ConfiguracaoNivelCompetenciaFaixaSalarialManager.class);
        manager.setConfiguracaoNivelCompetenciaFaixaSalarialManager(configuracaoNivelCompetenciaFaixaSalarialManager);

        configuracaoNivelCompetenciaCandidatoManager = mock(ConfiguracaoNivelCompetenciaCandidatoManager.class);
        manager.setConfiguracaoNivelCompetenciaCandidatoManager(configuracaoNivelCompetenciaCandidatoManager);
        
        colaboradorRespostaManager = mock(ColaboradorRespostaManager.class);
        MockSpringUtilJUnit4.mocks.put("colaboradorRespostaManager", colaboradorRespostaManager);
        
        colaboradorQuestionarioManager = mock(ColaboradorQuestionarioManager.class);
        MockSpringUtilJUnit4.mocks.put("colaboradorQuestionarioManager", colaboradorQuestionarioManager);
        
        configuracaoCompetenciaAvaliacaoDesempenhoManager = mock(ConfiguracaoCompetenciaAvaliacaoDesempenhoManager.class);
        MockSpringUtilJUnit4.mocks.put("configuracaoCompetenciaAvaliacaoDesempenhoManager", configuracaoCompetenciaAvaliacaoDesempenhoManager);
        

        Mockit.redefineMethods(SpringUtil.class, MockSpringUtilJUnit4.class);
    }

	@Test
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
		
		when(configuracaoNivelCompetenciaFaixaSalarialManager.findByFaixaSalarialIdAndData(faixaSalarial.getId(), solicitacao.getData())).thenReturn(new ConfiguracaoNivelCompetenciaFaixaSalarial());
	
		manager.saveCompetenciasCandidato(niveisCompetenciaFaixaSalariais, faixaSalarial.getId(), candidato.getId(), solicitacao);
	}
	
	@Test
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
		
		manager.saveCompetenciasColaborador(niveisCompetenciaFaixaSalariais, configuracaoNivelCompetenciaColaborador);
	}
	
	@Test
	public void testSaveCompetenciasColaboradorInsertComColaboradorQuestionarioEAvaliadorComIdNuloSemConfiguracaoNivelCompetencia()
	{
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador.setColaboradorQuestionario(ColaboradorQuestionarioFactory.getEntity());
		configuracaoNivelCompetenciaColaborador.setAvaliador(ColaboradorFactory.getEntity());
		
		manager.saveCompetenciasColaborador(null, configuracaoNivelCompetenciaColaborador);
	}
	
	@Test
	public void testSaveCompetenciasColaboradorUpdate()
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity();
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity(1L);
		configuracaoNivelCompetenciaColaborador.setColaboradorQuestionario(null);
		configuracaoNivelCompetenciaColaborador.setAvaliador(null);
		
		ConfiguracaoNivelCompetenciaCriterio configuracaoNivelCompetenciaCriterio = ConfiguracaoNivelCompetenciaCriterioFactory.getEntity(1L);
		configuracaoNivelCompetenciaCriterio.setCriterioId(2L);
		Collection<ConfiguracaoNivelCompetenciaCriterio> criterios = new ArrayList<ConfiguracaoNivelCompetenciaCriterio>();
		criterios.add(configuracaoNivelCompetenciaCriterio);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = ConfiguracaoNivelCompetenciaFactory.getEntity(1L);
		configuracaoNivelCompetencia1.setConfiguracaoNivelCompetenciaCriterios(criterios);
		configuracaoNivelCompetencia1.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetencia1.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetencia1.setCompetenciaId(atitude.getId());
		configuracaoNivelCompetencia1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		
		ConfiguracaoNivelCompetencia nivelCompetenciaSemCompetenciaId = ConfiguracaoNivelCompetenciaFactory.getEntity(2L);
		nivelCompetenciaSemCompetenciaId.setFaixaSalarial(faixaSalarial);
		nivelCompetenciaSemCompetenciaId.setNivelCompetencia(nivelCompetencia);
		nivelCompetenciaSemCompetenciaId.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		
		Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais = Arrays.asList(configuracaoNivelCompetencia1, nivelCompetenciaSemCompetenciaId);
		
		manager.saveCompetenciasColaborador(niveisCompetenciaFaixaSalariais, configuracaoNivelCompetenciaColaborador);
	}
	
	@Test
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
		
		manager.saveCompetenciasColaborador(niveisCompetenciaFaixaSalariais, configuracaoNivelCompetenciaColaborador);
	}
	
	@Test
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
		
		when(colaboradorQuestionarioManager.findById(colaboradorQuestionario.getId())).thenReturn(configuracaoNivelCompetenciaColaborador.getColaboradorQuestionario());
		
		manager.saveCompetenciasColaboradorAndRecalculaPerformance(1L, niveisCompetenciaFaixaSalariais, configuracaoNivelCompetenciaColaborador);
	}
	
	@Test
	public void testMontaRelatorioConfiguracaoNivelCompetencia()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(999999999999L);

		Colaborador joao = ColaboradorFactory.getEntity(1L, "João");
		Colaborador avaliador = ColaboradorFactory.getEntity(3L, "Avaliador");

		Atitude atitude = AtitudeFactory.getEntity(1L, "Liderança", null);
		Atitude atitude2 = AtitudeFactory.getEntity(2L, "Comunicação", null);
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento(3L, "Delphi", null);
		
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
		
		when(configuracaoNivelCompetenciaColaboradorManager.findByDataAndFaixaSalarial(dataIni, dataFim, faixaSalarial.getId())).thenReturn(configuracaoNivelCompetenciasColaborador);
		when(configuracaoNivelCompetenciaDao.findCompetenciasConfiguracaoNivelCompetenciaFaixaSalarial(competenciaIds,configuracaoNivelCompetenciaFaixaSalarial.getId())).thenReturn(configuracaoNivelCompetenciaFaixas);
		when(nivelCompetenciaManager.findAllSelect(empresa.getId(), configuracaoNivelCompetenciaFaixaSalarial.getNivelCompetenciaHistorico().getId(), configuracaoNivelCompetenciaFaixaSalarial.getData())).thenReturn(nivelCompetencias);
		when(configuracaoNivelCompetenciaDao.findByConfiguracaoNivelCompetenciaColaborador(competenciaIds, configuracaoNivelCompetenciaJoao.getId(), configuracaoNivelCompetenciaFaixaSalarial.getId())).thenReturn(configuracaoNivelCompetencias);
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
	
	@Test
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
		
		when(configuracaoNivelCompetenciaDao.findCompetenciaByFaixaSalarial(faixaSalarial.getId(), configuracaoNivelCompetenciaFaixaSalarial.getData(), configuracaoNivelCompetenciaFaixaSalarial.getId(), null, null)).thenReturn(configuracaoNivelCompetencias);
		when(nivelCompetenciaManager.findAllSelect(empresa.getId(), configuracaoNivelCompetenciaFaixaSalarial.getId(), configuracaoNivelCompetenciaFaixaSalarial.getData())).thenReturn(nivelCompetencias);
		when(criterioAvaliacaoCompetenciaManager.findByCompetenciaAndCNCFId(null, 1L, 'A')).thenReturn(new ArrayList<CriterioAvaliacaoCompetencia>());
		
		Collection<MatrizCompetenciaNivelConfiguracao> result = manager.montaConfiguracaoNivelCompetenciaByFaixa(empresa.getId(), faixaSalarial.getId(), configuracaoNivelCompetenciaFaixaSalarial);
		
		assertEquals(9, result.size());
	}

	@Test
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
		
		when(configuracaoNivelCompetenciaColaboradorManager.findByData(colaboradorQuestionario.getRespondidaEm(), colaboradorQuestionario.getColaborador().getId(), colaboradorQuestionario.getAvaliador().getId(), colaboradorQuestionario.getId())).thenReturn(configuracaoNivelCompetenciaColaborador);
		when(nivelCompetenciaManager.findAllSelect(empresa.getId(), 1L, configuracaoNivelCompetenciaFaixaSalarial.getData())).thenReturn(Arrays.asList(nivelBom, nivelRuim, nivelPessimo));
		when(configuracaoNivelCompetenciaFaixaSalarialManager.findByProjection(1L)).thenReturn(configuracaoNivelCompetenciaFaixaSalarial);
		when(configuracaoNivelCompetenciaDao.findCompetenciaByFaixaSalarial(configuracaoNivelCompetenciaColaborador.getId(), configuracaoNivelCompetenciaFaixaSalarial.getData(), configuracaoNivelCompetenciaFaixaSalarial.getId(), null, null)).thenReturn(configuracaoNivelCompetenciasExigidasPelaFaixa);
		when(configuracaoNivelCompetenciaDao.findByConfiguracaoNivelCompetenciaColaborador(null,configuracaoNivelCompetenciaColaborador.getId(),configuracaoNivelCompetenciaFaixaSalarial.getId())).thenReturn(configuracaoNivelCompetenciasDoColaborador);
		
		assertEquals(6, manager.montaMatrizCNCByQuestionario(colaboradorQuestionario, empresa.getId()).size());
	}	

	@Test
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
		
		when(candidatoSolicitacaoManager.getCandidatosBySolicitacao(solicitacao.getId())).thenReturn(Arrays.asList(joao.getId()));
		when(configuracaoNivelCompetenciaCandidatoManager.findByCandidatoAndSolicitacao(joao.getId(), solicitacao.getId())).thenReturn(configuracaoNivelCompetenciaCandidato);
		when(configuracaoNivelCompetenciaDao.findByConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaCandidato.getConfiguracaoNivelCompetenciaFaixaSalarial().getId())).thenReturn(configuracaoNivelCompetenciasFaixaSalarial);
		when(configuracaoNivelCompetenciaDao.findConfiguracaoNivelCompetenciaCandidato(configuracaoNivelCompetenciaCandidato.getId())).thenReturn(configuracaoNivelCompetenciasCandidato);
		when(configHistoricoNivelManager.findByNivelCompetenciaHistoricoId(configuracaoNivelCompetenciaCandidato.getConfiguracaoNivelCompetenciaFaixaSalarial().getNivelCompetenciaHistorico().getId())).thenReturn(configHistoricoNiveis);	
		
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
	
	@Test
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
		
		when(configuracaoNivelCompetenciaDao.findByConfiguracaoNivelCompetenciaFaixaSalarial(1L)).thenReturn(niveisCompetenciaEmpresa);
		when(configuracaoNivelCompetenciaDao.findByCandidato(1L)).thenReturn(niveisCompetenciaCandidato);
		when(nivelCompetenciaManager.findByCargoOrEmpresa(null, 1L)).thenReturn(niveisCompetenciaEmpresa);
		when(nivelCompetenciaManager.findAllSelect(null)).thenReturn(new ArrayList<NivelCompetencia>());
		
		Collection<Solicitacao> solicitacaoCoimConhecimento = manager.getCompetenciasCandidato(1L, 1L); 
		assertEquals(1, solicitacaoCoimConhecimento.size());
		
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = ((Solicitacao ) solicitacaoCoimConhecimento.toArray()[0]).getConfiguracaoNivelCompetencias();
		assertEquals(2, configuracaoNivelCompetencias.size());
		assertEquals("java", ((ConfiguracaoNivelCompetencia)configuracaoNivelCompetencias.toArray()[0]).getCompetenciaDescricao());
		assertEquals("proatividade", ((ConfiguracaoNivelCompetencia)configuracaoNivelCompetencias.toArray()[1]).getCompetenciaDescricao());
	}
	
	@Test
	public void testFindByConfiguracaoNivelCompetenciaColaborador(){
		Long configuracaoNivelCompetenciaColaboradorId = 1L;
		Long configuracaoNivelCompetenciaFaixaSalarialId = 2L;
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1DoColaborador = ConfiguracaoNivelCompetenciaFactory.getEntity(3L);
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia2DoColaborador = ConfiguracaoNivelCompetenciaFactory.getEntity(4L); 
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetenciasDoColaborador = Arrays.asList(configuracaoNivelCompetencia1DoColaborador, configuracaoNivelCompetencia2DoColaborador);
		
		ConfiguracaoNivelCompetenciaCriterio ConfiguracaoNivelCompetenciaCriterio1 = ConfiguracaoNivelCompetenciaCriterioFactory.getEntity(5L);
		Collection<ConfiguracaoNivelCompetenciaCriterio> ConfiguracaoNivelCompetenciaCriterios1 = new ArrayList<ConfiguracaoNivelCompetenciaCriterio>();
		ConfiguracaoNivelCompetenciaCriterios1.add(ConfiguracaoNivelCompetenciaCriterio1);
		
		when(configuracaoNivelCompetenciaDao.findByConfiguracaoNivelCompetenciaColaborador(null,configuracaoNivelCompetenciaColaboradorId,configuracaoNivelCompetenciaFaixaSalarialId)).thenReturn(configuracaoNivelCompetenciasDoColaborador);
		when(configuracaoNivelCompetenciaCriterioManager.findByConfiguracaoNivelCompetencia(configuracaoNivelCompetencia1DoColaborador.getId(), configuracaoNivelCompetenciaFaixaSalarialId)).thenReturn(ConfiguracaoNivelCompetenciaCriterios1);
		when(configuracaoNivelCompetenciaCriterioManager.findByConfiguracaoNivelCompetencia(configuracaoNivelCompetencia2DoColaborador.getId(), configuracaoNivelCompetenciaFaixaSalarialId)).thenReturn(new ArrayList<ConfiguracaoNivelCompetenciaCriterio>());
		
		Collection<ConfiguracaoNivelCompetencia> retorno = manager.findByConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaboradorId, configuracaoNivelCompetenciaFaixaSalarialId);
		
		assertEquals(2, retorno.size());
		assertEquals(1, ((ConfiguracaoNivelCompetencia)retorno.toArray()[0]).getConfiguracaoNivelCompetenciaCriterios().size());
		assertEquals(0, ((ConfiguracaoNivelCompetencia)retorno.toArray()[1]).getConfiguracaoNivelCompetenciaCriterios().size());
	}	
		
	
	@Test
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
		
		Long[] competrenciasIds = new Long[]{1L, 2L};
		
		when(configuracaoNivelCompetenciaDao.findCompetenciaColaborador(null,null,competrenciasIds,faixaSalarial.getId(),false)).thenReturn(configuracaoNivelCompetencias);
		when(configuracaoNivelCompetenciaDao.findCompetenciaByFaixaSalarial(null, null, configuracaoNivelCompetenciaFaixaSalarial.getId(), null, null)).thenReturn(configuracaoNivelCompetenciaFaixas);
		assertEquals(1, manager.findColaboradorAbaixoNivel(competrenciasIds, faixaSalarial.getId(), null).size());
	}
	
	@Test
	public void testCriaCNCColaboradorByCNCCnadidato(){
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		Candidato candidato = CandidatoFactory.getCandidato(2L);
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(3L);
		Colaborador colaborador = ColaboradorFactory.getEntity(22L);
		
		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity(1L, colaborador, faixaSalarial, DateUtil.criarDataDiaMesAno("01/06/2015"));
		
		Atitude atitude = AtitudeFactory.getEntity(1L, "Prestativo", null);
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento(1L, "Java", null);
		Habilidade habilidade= HabilidadeFactory.getEntity(6L, "Magica", null);
		
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
		
		when(configuracaoNivelCompetenciaCandidatoManager.findByCandidatoAndSolicitacao(candidato.getId(), solicitacao.getId())).thenReturn(configuracaoNivelCompetenciaCandidato);
		when(configuracaoNivelCompetenciaDao.findByCandidatoAndSolicitacao(candidato.getId(), solicitacao.getId())).thenReturn(cncscandidato);
		when(configuracaoNivelCompetenciaDao.findByConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaCandidato.getConfiguracaoNivelCompetenciaFaixaSalarial().getId())).thenReturn(cncsFaixaSalarial);
		Exception exception = null;
		try {
			manager.criaCNCColaboradorByCNCCnadidato(colaborador, candidato.getId(), solicitacao, historico);
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}
	
	@Test
	public void testRemoveByCandidatoAndSolicitacao() 
	{
		Long candidatoId = 1L;
		Long solicitacaoId = 2L;
		Exception exception = null;
		try {
			manager.removeByCandidatoAndSolicitacao(candidatoId, solicitacaoId);
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}

	@Test
	public void testRemoveByCandidato() 
	{
		Long candidatoId = 1L;
		Exception exception = null;
		try {
			manager.removeByCandidato(candidatoId);
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
	
	private Colaborador prepareRelatorioAnaliseCompetencia(boolean agruparPorCargo) {
		Competencia competencia = new Competencia();
		competencia.setId(1L);
		competencia.setNome("Competência 1");
		
		Cargo cargo1 = CargoFactory.getEntity(1L);
		cargo1.setNome("Cargo 1");
		
		Cargo cargo2 = CargoFactory.getEntity(2L);
		cargo2.setNome("Cargo 2");
		
		Cargo cargo3 = CargoFactory.getEntity(3L);
		cargo3.setNome("Cargo 3");
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L, "Colaborador 1");
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L, "Colaborador 2");
		Colaborador colaborador3 = ColaboradorFactory.getEntity(3L, "Colaborador 3");
		Colaborador colaborador4 = ColaboradorFactory.getEntity(4L, "Colaborador 4");

		LinkedList<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = new LinkedList<ConfiguracaoNivelCompetencia>();
		configuracaoNivelCompetencias.add(ConfiguracaoNivelCompetenciaFactory.getEntity(competencia, colaborador, cargo1, 2));
		configuracaoNivelCompetencias.add(ConfiguracaoNivelCompetenciaFactory.getEntity(competencia, colaborador2, cargo2, 3));
		configuracaoNivelCompetencias.add(ConfiguracaoNivelCompetenciaFactory.getEntity(competencia, colaborador3, cargo3, 6));
		configuracaoNivelCompetencias.add(ConfiguracaoNivelCompetenciaFactory.getEntity(competencia, colaborador4, cargo2, 5));
		
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity();
		Collection<NivelCompetencia> niveisCompetencia = Arrays.asList(nivelCompetencia);
		
		when(configuracaoNivelCompetenciaDao.findByAvaliacaoDesempenhoAndAvaliado(1L, colaborador.getId(), agruparPorCargo)).thenReturn(configuracaoNivelCompetencias);
		when(nivelCompetenciaManager.getOrdemMaximaByAavaliacaoDesempenhoAndAvaliado(1L, colaborador.getId())).thenReturn(new Integer(5));
		when(nivelCompetenciaManager.findNiveisCompetenciaByAvDesempenho(1L)).thenReturn(niveisCompetencia);
		
		return colaborador;
	}
	
	@Test
	public void testMontaRelatorioResultadoCompetencia(){
		Colaborador colaborador = prepareRelatorioAnaliseCompetencia(true);
		Collection<Long> colaboradorIds = new ArrayList<Long>();
		colaboradorIds.add(1L);
		colaboradorIds.add(2L);
		
		RelatorioAnaliseDesempenhoColaborador relatorioAnaliseDesempenhoColaborador = manager.montaRelatorioAnaliseDesempenhoColaborador(1L, colaborador.getId(), colaboradorIds, 0, true);
		LinkedList<ResultadoCompetenciaColaborador> resultadoCompetenciaColaboradores = (LinkedList<ResultadoCompetenciaColaborador>) relatorioAnaliseDesempenhoColaborador.getResultadosCompetenciaColaborador(); 
		assertEquals(1, resultadoCompetenciaColaboradores.size());
		
		ResultadoCompetenciaColaborador resultadoCompetenciaColaborador1 = (ResultadoCompetenciaColaborador) resultadoCompetenciaColaboradores.toArray()[0];
		assertEquals("Competência 1", resultadoCompetenciaColaborador1.getCompetenciaNome());
		assertEquals(4, resultadoCompetenciaColaborador1.getResultadoCompetencias().size());
		
		assertEquals(RelatorioAnaliseDesempenhoColaborador.AUTOAVALIACAO, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[0]).getNome());
		assertEquals("Cargo 2", ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[1]).getNome());
		assertEquals(RelatorioAnaliseDesempenhoColaborador.OUTROSAVALIADORES, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[2]).getNome());
		assertEquals(RelatorioAnaliseDesempenhoColaborador.MEDIA, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[3]).getNome());
		
		assertEquals(new Double(2.0), ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[0]).getOrdem());
		assertEquals(new Double(3.0), ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[1]).getOrdem());
		assertEquals(new Double(5.5), ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[2]).getOrdem());
		assertEquals(new Double(4.0), ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[3]).getOrdem());
	}
	
	@Test
	public void testMontaRelatorioResultadoCompetenciaComColabNoMesmoCargo(){
		Colaborador colaborador = prepareRelatorioAnaliseCompetencia(true);
		
		Collection<Long> colaboradorIds = new ArrayList<Long>();
		colaboradorIds.add(1L);
		colaboradorIds.add(2L);

		RelatorioAnaliseDesempenhoColaborador relatorioAnaliseDesempenhoColaborador = manager.montaRelatorioAnaliseDesempenhoColaborador(1L, colaborador.getId(), colaboradorIds, 0, true);
		LinkedList<ResultadoCompetenciaColaborador> resultadoCompetenciaColaboradores = (LinkedList<ResultadoCompetenciaColaborador>) relatorioAnaliseDesempenhoColaborador.getResultadosCompetenciaColaborador();
		assertEquals(1, resultadoCompetenciaColaboradores.size());
		
		ResultadoCompetenciaColaborador resultadoCompetenciaColaborador1 = (ResultadoCompetenciaColaborador) resultadoCompetenciaColaboradores.toArray()[0];
		assertEquals("Competência 1", resultadoCompetenciaColaborador1.getCompetenciaNome());
		assertEquals(4, resultadoCompetenciaColaborador1.getResultadoCompetencias().size());
		
		assertEquals(RelatorioAnaliseDesempenhoColaborador.AUTOAVALIACAO, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[0]).getNome());
		assertEquals("Cargo 2", ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[1]).getNome());
		assertEquals(RelatorioAnaliseDesempenhoColaborador.OUTROSAVALIADORES, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[2]).getNome());
		assertEquals(RelatorioAnaliseDesempenhoColaborador.MEDIA, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[3]).getNome());
		
		assertEquals(new Double(2.0), ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[0]).getOrdem());
		assertEquals(new Double(3.0), ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[1]).getOrdem());
		assertEquals(new Double(5.5), ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[2]).getOrdem());
		assertEquals(new Double(4.0), ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[3]).getOrdem());
	}
	
	@Test
	public void testMontaRelatorioResultadoCompetenciaPorAvaliado(){
		Colaborador colaborador = prepareRelatorioAnaliseCompetencia(false);
		Collection<Long> colaboradorIds = new ArrayList<Long>();
		colaboradorIds.add(2L);
		colaboradorIds.add(4L);
		
		RelatorioAnaliseDesempenhoColaborador relatorioAnaliseDesempenhoColaborador = manager.montaRelatorioAnaliseDesempenhoColaborador(1L, colaborador.getId(), colaboradorIds, 0, false);
		LinkedList<ResultadoCompetenciaColaborador> resultadoCompetenciaColaboradores = (LinkedList<ResultadoCompetenciaColaborador>) relatorioAnaliseDesempenhoColaborador.getResultadosCompetenciaColaborador();
		assertEquals(1, resultadoCompetenciaColaboradores.size());
		
		ResultadoCompetenciaColaborador resultadoCompetenciaColaborador1 = (ResultadoCompetenciaColaborador) resultadoCompetenciaColaboradores.toArray()[0];
		assertEquals("Competência 1", resultadoCompetenciaColaborador1.getCompetenciaNome());
		assertEquals(5, resultadoCompetenciaColaborador1.getResultadoCompetencias().size());
		
		assertEquals(RelatorioAnaliseDesempenhoColaborador.AUTOAVALIACAO, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[0]).getNome());
		assertEquals("Colaborador 2", ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[1]).getNome());
		assertEquals("Colaborador 4", ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[2]).getNome());
		assertEquals(RelatorioAnaliseDesempenhoColaborador.OUTROSAVALIADORES, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[3]).getNome());
		assertEquals(RelatorioAnaliseDesempenhoColaborador.MEDIA, ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[4]).getNome());
		
		assertEquals(new Double(2.0), ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[0]).getOrdem());
		assertEquals(new Double(3.0), ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[1]).getOrdem());
		assertEquals(new Double(5.0), ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[2]).getOrdem());
		assertEquals(new Double(6.0), ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[3]).getOrdem());
		assertEquals(new Double(4.0), ((ResultadoCompetencia)resultadoCompetenciaColaborador1.getResultadoCompetencias().toArray()[4]).getOrdem());
	}
	
	@Test
	@SuppressWarnings("static-access")
	public void testSaveCompetenciasFaixaSalarial(){
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia.setCompetenciaId(1L);
		
		Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaFaixaSalariais = new ArrayList<ConfiguracaoNivelCompetencia>();
		niveisCompetenciaFaixaSalariais.add(configuracaoNivelCompetencia);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = new ConfiguracaoNivelCompetenciaFaixaSalarialFactory().getEntity(1L); 
		
		Exception ex = null;
		try {
			manager.saveCompetenciasFaixaSalarial(niveisCompetenciaFaixaSalariais, configuracaoNivelCompetenciaFaixaSalarial);
		} catch (Exception e) {
			ex = e;
		}
		
		assertNull(ex);
	}
	
	@Test
	public void testFindCompetenciaByFaixaSalarial(){
		Long configuracaoNivelCompetenciaFaixaSalarialId = 1L;
		Long avaliacaoDesempenhoId = 2L;
		Long avaliadorId = 4L;
		Long faixaId = 3L;
		Date data = new DateUtil().criarDataMesAno(1, 1, 2016);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = ConfiguracaoNivelCompetenciaFactory.getEntity('A');
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = new ArrayList<ConfiguracaoNivelCompetencia>();
		configuracaoNivelCompetencias.add(configuracaoNivelCompetencia);

		CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia = new CriterioAvaliacaoCompetencia();
		Collection<CriterioAvaliacaoCompetencia> criterioAvaliacaoCompetencias = new ArrayList<CriterioAvaliacaoCompetencia>();
		criterioAvaliacaoCompetencias.add(criterioAvaliacaoCompetencia);
		
		when(configuracaoCompetenciaAvaliacaoDesempenhoManager.existe(configuracaoNivelCompetenciaFaixaSalarialId,avaliacaoDesempenhoId)).thenReturn(true);
		when(configuracaoNivelCompetenciaDao.findCompetenciaByFaixaSalarial(faixaId, data, configuracaoNivelCompetenciaFaixaSalarialId, avaliadorId, avaliacaoDesempenhoId)).thenReturn(configuracaoNivelCompetencias);
		when(criterioAvaliacaoCompetenciaManager.findByCompetenciaAndCNCFId(null, configuracaoNivelCompetenciaFaixaSalarialId, configuracaoNivelCompetencia.getTipoCompetencia())).thenReturn(criterioAvaliacaoCompetencias);
		
		Collection<ConfiguracaoNivelCompetencia>  retorno = manager.findCompetenciaByFaixaSalarial(faixaId, data, configuracaoNivelCompetenciaFaixaSalarialId, avaliadorId, avaliacaoDesempenhoId);
		
		assertEquals(1, retorno.size());
		assertEquals(1, ((ConfiguracaoNivelCompetencia)retorno.toArray()[0]).getCriteriosAvaliacaoCompetencia().size());
	}
	
	@Test
	public void testFindCompetenciaByFaixaSalarialSemConfAvDesempenho(){
		Long configuracaoNivelCompetenciaFaixaSalarialId = 1L;
		Long avaliacaoDesempenhoId = 2L;
		Long avaliadorId = 4L;
		Long faixaId = 3L;
		Date data = new DateUtil().criarDataMesAno(1, 1, 2016);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = ConfiguracaoNivelCompetenciaFactory.getEntity('A');
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = new ArrayList<ConfiguracaoNivelCompetencia>();
		configuracaoNivelCompetencias.add(configuracaoNivelCompetencia);

		CriterioAvaliacaoCompetencia criterioAvaliacaoCompetencia = new CriterioAvaliacaoCompetencia();
		Collection<CriterioAvaliacaoCompetencia> criterioAvaliacaoCompetencias = new ArrayList<CriterioAvaliacaoCompetencia>();
		criterioAvaliacaoCompetencias.add(criterioAvaliacaoCompetencia);
		
		when(configuracaoCompetenciaAvaliacaoDesempenhoManager.existe(configuracaoNivelCompetenciaFaixaSalarialId,avaliacaoDesempenhoId)).thenReturn(false);
		when(configuracaoNivelCompetenciaDao.findCompetenciaByFaixaSalarial(faixaId, data, configuracaoNivelCompetenciaFaixaSalarialId, null, null)).thenReturn(configuracaoNivelCompetencias);
		when(criterioAvaliacaoCompetenciaManager.findByCompetenciaAndCNCFId(null, configuracaoNivelCompetenciaFaixaSalarialId, configuracaoNivelCompetencia.getTipoCompetencia())).thenReturn(criterioAvaliacaoCompetencias);
		
		Collection<ConfiguracaoNivelCompetencia>  retorno = manager.findCompetenciaByFaixaSalarial(faixaId, data, configuracaoNivelCompetenciaFaixaSalarialId, avaliadorId, avaliacaoDesempenhoId);
		
		assertEquals(1, retorno.size());
		assertEquals(1, ((ConfiguracaoNivelCompetencia)retorno.toArray()[0]).getCriteriosAvaliacaoCompetencia().size());
	}
	
	@Test
	public void testRemoveConfiguracaoNivelCompetenciaColaborador(){
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity(1L);
		when(configuracaoNivelCompetenciaColaboradorManager.findByIdProjection(configuracaoNivelCompetenciaColaborador.getId())).thenReturn(configuracaoNivelCompetenciaColaborador);

		Exception ex = null;
		try {
			manager.removeConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador.getId());
		} catch (Exception e) {
			ex = e;
		}

		assertNull(ex);
	}
	
	@Test
	public void testRemoveConfiguracaoNivelCompetenciaColaboradorException(){
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity(1L);
		configuracaoNivelCompetenciaColaborador.setColaboradorQuestionario(ColaboradorQuestionarioFactory.getEntity(1L));
		
		when(configuracaoNivelCompetenciaColaboradorManager.findByIdProjection(configuracaoNivelCompetenciaColaborador.getId())).thenReturn(configuracaoNivelCompetenciaColaborador);

		Exception ex = null;
		try {
			manager.removeConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaColaborador.getId());
		} catch (Exception e) {
			ex = e;
		}
		assertEquals("Esta configuração de competência não pode ser excluída, pois existe dependência com avaliação de desempenho.", ex.getMessage());
	}
	
	@Test
	public void removeConfiguracaoNivelCompetenciaFaixaSalarial(){
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(2L);
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(faixaSalarial, "01/01/2016");
		configuracaoNivelFaixaSalarial.setId(1L);
		
		Collection<ConfiguracaoNivelCompetenciaFaixaSalarial> configuracaoNivelCompetenciaFaixaSalariais = new ArrayList<ConfiguracaoNivelCompetenciaFaixaSalarial>();
		configuracaoNivelCompetenciaFaixaSalariais.add(configuracaoNivelFaixaSalarial);
		
		Date dataDaProximaConfiguracaodaFaixaSalarial = configuracaoNivelFaixaSalarial.getData();
		
		when(configuracaoNivelCompetenciaFaixaSalarialManager.findProximasConfiguracoesAposData(configuracaoNivelFaixaSalarial.getFaixaSalarial().getId(), configuracaoNivelFaixaSalarial.getData())).thenReturn(configuracaoNivelCompetenciaFaixaSalariais);
		when(configuracaoNivelCompetenciaFaixaSalarialManager.findById(configuracaoNivelFaixaSalarial.getId())).thenReturn(configuracaoNivelFaixaSalarial);
		when(configuracaoNivelCompetenciaColaboradorManager.existeDependenciaComCompetenciasDaFaixaSalarial(configuracaoNivelFaixaSalarial.getFaixaSalarial().getId(), configuracaoNivelFaixaSalarial.getData(), dataDaProximaConfiguracaodaFaixaSalarial)).thenReturn(false);
		when(configuracaoNivelCompetenciaDao.existeDependenciaComCompetenciasDoCandidato(configuracaoNivelFaixaSalarial.getFaixaSalarial().getId(), configuracaoNivelFaixaSalarial.getData(), dataDaProximaConfiguracaodaFaixaSalarial)).thenReturn(false);

		Exception ex = null;
		try {
			manager.removeConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelFaixaSalarial.getId());
		} catch (Exception e) {
			ex = e;
		}
		
		assertNull(ex);
	}
	
	@Test
	public void removeConfiguracaoNivelCompetenciaFaixaSalarialDependenciaComCompetenciasDaFaixaSalarial(){
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(2L);
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(faixaSalarial, "01/01/2016");
		configuracaoNivelFaixaSalarial.setId(1L);
		
		Collection<ConfiguracaoNivelCompetenciaFaixaSalarial> configuracaoNivelCompetenciaFaixaSalariais = new ArrayList<ConfiguracaoNivelCompetenciaFaixaSalarial>();
		configuracaoNivelCompetenciaFaixaSalariais.add(configuracaoNivelFaixaSalarial);
		
		Date dataDaProximaConfiguracaodaFaixaSalarial = configuracaoNivelFaixaSalarial.getData();
		
		when(configuracaoNivelCompetenciaFaixaSalarialManager.findProximasConfiguracoesAposData(configuracaoNivelFaixaSalarial.getFaixaSalarial().getId(), configuracaoNivelFaixaSalarial.getData())).thenReturn(configuracaoNivelCompetenciaFaixaSalariais);
		when(configuracaoNivelCompetenciaFaixaSalarialManager.findById(configuracaoNivelFaixaSalarial.getId())).thenReturn(configuracaoNivelFaixaSalarial);
		when(configuracaoNivelCompetenciaColaboradorManager.existeDependenciaComCompetenciasDaFaixaSalarial(configuracaoNivelFaixaSalarial.getFaixaSalarial().getId(), configuracaoNivelFaixaSalarial.getData(), dataDaProximaConfiguracaodaFaixaSalarial)).thenReturn(true);

		Exception ex = null;
		try {
			manager.removeConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelFaixaSalarial.getId());
		} catch (Exception e) {
			ex = e;
		}
		
		assertEquals("Esta configuração de competência não pode ser excluída, pois existe dependência com competências do colaborador.", ex.getMessage());
	}
	
	@Test
	public void removeConfiguracaoNivelCompetenciaFaixaSalarialDependenciaComCompetenciasDoCandidato(){
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(2L);
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(faixaSalarial, "01/01/2016");
		configuracaoNivelFaixaSalarial.setId(1L);
		
		Collection<ConfiguracaoNivelCompetenciaFaixaSalarial> configuracaoNivelCompetenciaFaixaSalariais = new ArrayList<ConfiguracaoNivelCompetenciaFaixaSalarial>();
		configuracaoNivelCompetenciaFaixaSalariais.add(configuracaoNivelFaixaSalarial);
		
		Date dataDaProximaConfiguracaodaFaixaSalarial = configuracaoNivelFaixaSalarial.getData();
		
		when(configuracaoNivelCompetenciaFaixaSalarialManager.findProximasConfiguracoesAposData(configuracaoNivelFaixaSalarial.getFaixaSalarial().getId(), configuracaoNivelFaixaSalarial.getData())).thenReturn(configuracaoNivelCompetenciaFaixaSalariais);
		when(configuracaoNivelCompetenciaFaixaSalarialManager.findById(configuracaoNivelFaixaSalarial.getId())).thenReturn(configuracaoNivelFaixaSalarial);
		when(configuracaoNivelCompetenciaColaboradorManager.existeDependenciaComCompetenciasDaFaixaSalarial(configuracaoNivelFaixaSalarial.getFaixaSalarial().getId(), configuracaoNivelFaixaSalarial.getData(), dataDaProximaConfiguracaodaFaixaSalarial)).thenReturn(false);
		when(configuracaoNivelCompetenciaDao.existeDependenciaComCompetenciasDoCandidato(configuracaoNivelFaixaSalarial.getFaixaSalarial().getId(), configuracaoNivelFaixaSalarial.getData(), dataDaProximaConfiguracaodaFaixaSalarial)).thenReturn(true);

		Exception ex = null;
		try {
			manager.removeConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelFaixaSalarial.getId());
		} catch (Exception e) {
			ex = e;
		}
		
		assertEquals("Esta configuração de competência não pode ser excluída, pois existe dependência com competências do candidato.", ex.getMessage());
	}
	
	@Test
	public void TestGetDao(){
		Exception ex = null;
		try {
			manager.removeByFaixas(null);
			manager.removeColaborador(null);
			manager.findCompetenciasIdsConfiguradasByFaixaSolicitacao(null);
			manager.somaConfiguracoesByFaixa(null);
			manager.findColaboradoresCompetenciasAbaixoDoNivel(null, null, null, null, 'A');
			manager.findCompetenciasColaboradorByFaixaSalarialAndPeriodo(null, null, null);
			manager.removeDependenciasComConfiguracaoNivelCompetenciaColaboradorByFaixaSalarial(null);
			manager.removeDependenciasComConfiguracaoNivelCompetenciaFaixaSalarialByFaixaSalarial(null);
			manager.findDependenciaComColaborador(null, null);
			manager.findDependenciaComCandidato(null, null);
			manager.existeConfiguracaoNivelCompetencia(null, 'T');
			manager.removeBySolicitacaoId(null);
			manager.findCompetenciasAndPesos(null, null);
			manager.getCriterioAvaliacaoCompetenciaManager();
			manager.getConfiguracaoNivelCompetenciaFaixaSalarialManager();
			manager.findByFaixa(null, null);
			manager.findByConfiguracaoNivelCompetenciaFaixaSalarial(null);
		} catch (Exception e) {
			ex = e;
		}
		assertNull(ex);
	}
}