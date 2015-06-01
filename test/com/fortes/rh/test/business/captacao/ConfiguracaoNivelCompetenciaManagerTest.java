package com.fortes.rh.test.business.captacao;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaColaboradorManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManagerImpl;
import com.fortes.rh.business.captacao.NivelCompetenciaManager;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaVO;
import com.fortes.rh.model.captacao.MatrizCompetenciaNivelConfiguracao;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;

public class ConfiguracaoNivelCompetenciaManagerTest extends MockObjectTestCase
{
	private ConfiguracaoNivelCompetenciaManagerImpl configuracaoNivelCompetenciaManager = new ConfiguracaoNivelCompetenciaManagerImpl();
	private Mock configuracaoNivelCompetenciaDao;
	private Mock nivelCompetenciaManager;
	private Mock candidatoSolicitacaoManager;
	private Mock configuracaoNivelCompetenciaColaboradorManager;
	
	
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
    }

	public void testSaveCompetenciasCandidato()
	{
		Atitude atitude = AtitudeFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity(2L);
		Candidato candidato = CandidatoFactory.getCandidato(3L);
		
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
		
		configuracaoNivelCompetenciaDao.expects(once()).method("deleteConfiguracaoByCandidatoFaixa").with(eq(candidato.getId()), eq(faixaSalarial.getId())).isVoid();
		configuracaoNivelCompetenciaDao.expects(once()).method("save").with(ANYTHING).isVoid();
	
		configuracaoNivelCompetenciaManager.saveCompetenciasCandidato(niveisCompetenciaFaixaSalariais, faixaSalarial.getId(), candidato.getId());
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
		configuracaoNivelCompetenciaDao.expects(once()).method("deleteByConfiguracaoNivelCompetenciaColaborador").with(eq(configuracaoNivelCompetenciaColaborador.getId())).isVoid();
		configuracaoNivelCompetenciaDao.expects(once()).method("save").with(ANYTHING).isVoid();
		
		configuracaoNivelCompetenciaManager.saveCompetenciasColaborador(niveisCompetenciaFaixaSalariais, configuracaoNivelCompetenciaColaborador);
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
		
		NivelCompetencia nivelPessimo = NivelCompetenciaFactory.getEntity();
		nivelPessimo.setDescricao("pessimo");
		nivelPessimo.setOrdem(1);

		NivelCompetencia nivelRuim = NivelCompetenciaFactory.getEntity();
		nivelRuim.setDescricao("ruim");
		nivelRuim.setOrdem(2);

		NivelCompetencia nivelBom = NivelCompetenciaFactory.getEntity();
		nivelBom.setDescricao("bom");
		nivelBom.setOrdem(3);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaJoao = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity(999999999998L);
		configuracaoNivelCompetenciaJoao.setColaborador(joao);
		configuracaoNivelCompetenciaJoao.setAvaliador(avaliador);
		configuracaoNivelCompetenciaJoao.setFaixaSalarial(faixaSalarial);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaMaria = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity(999999999999L);
		configuracaoNivelCompetenciaMaria.setColaborador(maria);
		configuracaoNivelCompetenciaMaria.setAvaliador(avaliador);
		configuracaoNivelCompetenciaMaria.setFaixaSalarial(faixaSalarial);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia1.setId(1L);
		configuracaoNivelCompetencia1.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaJoao);
		configuracaoNivelCompetencia1.setCompetenciaDescricao("Java");
		configuracaoNivelCompetencia1.setNivelCompetencia(nivelBom);
		configuracaoNivelCompetencia1.setNivelCompetenciaColaborador(nivelPessimo);
		configuracaoNivelCompetencia1.setTipoCompetencia(TipoCompetencia.ATITUDE);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia2 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia2.setId(2L);
		configuracaoNivelCompetencia2.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaJoao);
		configuracaoNivelCompetencia2.setCompetenciaDescricao("Delphi");
		configuracaoNivelCompetencia2.setNivelCompetencia(nivelRuim);
		configuracaoNivelCompetencia2.setNivelCompetenciaColaborador(nivelRuim);
		configuracaoNivelCompetencia2.setTipoCompetencia(TipoCompetencia.CONHECIMENTO);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia3 = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia3.setId(3L);
		configuracaoNivelCompetencia3.setConfiguracaoNivelCompetenciaColaborador(configuracaoNivelCompetenciaMaria);
		configuracaoNivelCompetencia3.setCompetenciaDescricao("C#");
		configuracaoNivelCompetencia3.setNivelCompetencia(nivelPessimo);
		configuracaoNivelCompetencia3.setNivelCompetenciaColaborador(nivelRuim);
		configuracaoNivelCompetencia3.setTipoCompetencia(TipoCompetencia.ATITUDE);
		
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = Arrays.asList(configuracaoNivelCompetencia1,configuracaoNivelCompetencia2,configuracaoNivelCompetencia3);
		
		CollectionUtil<ConfiguracaoNivelCompetencia> clu = new CollectionUtil<ConfiguracaoNivelCompetencia>();
		Long[] competenciaIds = clu.convertCollectionToArrayIds(configuracaoNivelCompetencias);

		Collection<NivelCompetencia> nivelCompetencias = Arrays.asList(nivelPessimo,nivelRuim,nivelBom);
		
		Date dataIni = DateUtil.criarDataMesAno(1, 1, 2010);
		Date dataFim = DateUtil.criarDataMesAno(1, 1, 2015);
		
		configuracaoNivelCompetenciaColaboradorManager.expects(atLeastOnce()).method("verificaAvaliadorAnonimo").with(ANYTHING).isVoid();
		configuracaoNivelCompetenciaDao.expects(once()).method("findCompetenciaColaborador").with(new Constraint[]{eq(dataIni), eq(dataFim),eq(competenciaIds),eq(faixaSalarial.getId()),eq(true)}).will(returnValue(configuracaoNivelCompetencias));
		nivelCompetenciaManager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(nivelCompetencias));
		
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

		assertEquals(new Integer(1), ((MatrizCompetenciaNivelConfiguracao)matrizMaria.toArray()[3]).getGap());
		assertEquals(new Integer(0), ((MatrizCompetenciaNivelConfiguracao)matrizMaria.toArray()[7]).getGap());
		assertEquals(new Integer(0), ((MatrizCompetenciaNivelConfiguracao)matrizMaria.toArray()[11]).getGap());
		
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
		
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = Arrays.asList(configuracaoNivelCompetencia1,configuracaoNivelCompetencia2,configuracaoNivelCompetencia3);
		
		Collection<NivelCompetencia> nivelCompetencias = Arrays.asList(nivelPessimo,nivelRuim,nivelBom);
		
		configuracaoNivelCompetenciaDao.expects(once()).method("findCompetenciaByFaixaSalarial").with(eq(faixaSalarial.getId()), ANYTHING).will(returnValue(configuracaoNivelCompetencias));
		nivelCompetenciaManager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(nivelCompetencias));
		
		Collection<MatrizCompetenciaNivelConfiguracao> result = configuracaoNivelCompetenciaManager.montaConfiguracaoNivelCompetenciaByFaixa(empresa.getId(), faixaSalarial.getId());
		
		assertEquals(9, result.size());
	}

	public void testMontaMatrizCompetenciaCandidato()
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
		
		Collection<NivelCompetencia> nivelCompetencias = Arrays.asList(nivelPessimo, nivelRuim, nivelBom);
		
		Candidato joao = CandidatoFactory.getCandidato(1L);
		joao.setNome("joao");

		Candidato maria = CandidatoFactory.getCandidato(2L);
		maria.setNome("maria");
		
		Collection<Long> candidatosIds= Arrays.asList(joao.getId(), maria.getId());
		
		ConfiguracaoNivelCompetencia configFaixa1Java = criaConfigCompetencia("java", faixaSalarial, nivelBom, TipoCompetencia.ATITUDE, null);
		ConfiguracaoNivelCompetencia configFaixa1Delphi = criaConfigCompetencia("delphi", faixaSalarial, nivelPessimo, TipoCompetencia.CONHECIMENTO, null);
		ConfiguracaoNivelCompetencia configFaixa1BD = criaConfigCompetencia("BD", faixaSalarial, nivelPessimo, TipoCompetencia.CONHECIMENTO, null);

		ConfiguracaoNivelCompetencia configCandidatoJoao1 = criaConfigCompetencia("BD", faixaSalarial, nivelBom, TipoCompetencia.CONHECIMENTO, joao);
		ConfiguracaoNivelCompetencia configCandidatoJoao2 = criaConfigCompetencia("java", faixaSalarial, nivelBom, TipoCompetencia.ATITUDE, joao);
		
		ConfiguracaoNivelCompetencia configCandidatoMaria1 = criaConfigCompetencia("java", faixaSalarial, nivelPessimo, TipoCompetencia.ATITUDE, maria);
		
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias = Arrays.asList(configFaixa1Java, configFaixa1Delphi, configFaixa1BD, configCandidatoJoao1, configCandidatoJoao2, configCandidatoMaria1);
		
		candidatoSolicitacaoManager.expects(once()).method("getCandidatosBySolicitacao").with(ANYTHING).will(returnValue(candidatosIds));
		configuracaoNivelCompetenciaDao.expects(once()).method("findCompetenciaCandidato").with(eq(faixaSalarial.getId()), eq(candidatosIds)).will(returnValue(configuracaoNivelCompetencias));
		nivelCompetenciaManager.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(nivelCompetencias));

		Collection<ConfiguracaoNivelCompetenciaVO> matrizes = configuracaoNivelCompetenciaManager.montaMatrizCompetenciaCandidato(empresa.getId(), faixaSalarial.getId(), null);
		assertEquals(2, matrizes.size());
		
		ConfiguracaoNivelCompetenciaVO matrizJoao = (ConfiguracaoNivelCompetenciaVO) matrizes.toArray()[0];
		ConfiguracaoNivelCompetenciaVO matrizMaria = (ConfiguracaoNivelCompetenciaVO) matrizes.toArray()[1];
		
		assertEquals("joao", matrizJoao.getNome());
		assertEquals("maria", matrizMaria.getNome());
		
		assertEquals("matriz Joao", 12, matrizJoao.getMatrizes().size());
		assertEquals("matriz Maria", 12, matrizJoao.getMatrizes().size());
	
		List<MatrizCompetenciaNivelConfiguracao> matrizConfiguradaJoao = (List<MatrizCompetenciaNivelConfiguracao>) matrizJoao.getMatrizes();
		assertEquals("java configuracao candidato", Boolean.TRUE, matrizConfiguradaJoao.get(2).getConfiguracao());
		assertEquals("java configuracao faixa", Boolean.TRUE, matrizConfiguradaJoao.get(2).getConfiguracaoFaixa());
		
		assertEquals("gap java", new Integer(0), matrizConfiguradaJoao.get(3).getGap());
		assertEquals("gap delphi", null, matrizConfiguradaJoao.get(7).getGap());
		assertEquals("gap BD", new Integer(2), matrizConfiguradaJoao.get(11).getGap());

		List<MatrizCompetenciaNivelConfiguracao> matrizConfiguradaMaria = (List<MatrizCompetenciaNivelConfiguracao>) matrizMaria.getMatrizes();
		assertEquals("java configuracao candidato", Boolean.TRUE, matrizConfiguradaMaria.get(0).getConfiguracao());
		assertEquals("java configuracao faixa", Boolean.FALSE, matrizConfiguradaMaria.get(0).getConfiguracaoFaixa());
		
		assertEquals("gap java", new Integer(-2), matrizConfiguradaMaria.get(3).getGap());
		assertEquals("gap delphi", null, matrizConfiguradaMaria.get(7).getGap());
		assertEquals("gap BD", null, matrizConfiguradaMaria.get(11).getGap());
	}

	private ConfiguracaoNivelCompetencia criaConfigCompetencia(String descricao, FaixaSalarial faixaSalarial, NivelCompetencia nivel, char tipo, Candidato candidato)
	{
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia.setCompetenciaDescricao(descricao);
		configuracaoNivelCompetencia.setNivelCompetencia(nivel);
		configuracaoNivelCompetencia.setFaixaSalarial(faixaSalarial);
		configuracaoNivelCompetencia.setCandidato(candidato);
		configuracaoNivelCompetencia.setTipoCompetencia(tipo);
		
		return configuracaoNivelCompetencia;
	}
	
	public void testGetCompetenciasCandidato()
	{
		ConfiguracaoNivelCompetencia nivelConhecimento = new ConfiguracaoNivelCompetencia(TipoCompetencia.CONHECIMENTO, 1L, "", null);
		ConfiguracaoNivelCompetencia nivelAtitude = new ConfiguracaoNivelCompetencia(TipoCompetencia.ATITUDE, 3L, "", null);

		Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaCandidato = Arrays.asList(nivelConhecimento, nivelAtitude);

		nivelConhecimento.setCompetenciaDescricao("java");
		nivelAtitude.setCompetenciaDescricao("proatividade");
		ConfiguracaoNivelCompetencia nivelHabilidade = new ConfiguracaoNivelCompetencia(TipoCompetencia.HABILIDADE, 2L, "comunicacao", null);
		
		Collection<ConfiguracaoNivelCompetencia> niveisCompetencia = Arrays.asList(nivelConhecimento, nivelHabilidade, nivelAtitude);

		configuracaoNivelCompetenciaDao.expects(once()).method("findByCandidato").with(ANYTHING).will(returnValue(niveisCompetenciaCandidato));
		nivelCompetenciaManager.expects(once()).method("findByCargoOrEmpresa").with(ANYTHING,ANYTHING).will(returnValue(niveisCompetencia));
		
		Collection<ConfiguracaoNivelCompetencia> niveisComDescricao = configuracaoNivelCompetenciaManager.getCompetenciasCandidato(1L, 1L); 
		
		assertEquals(2, niveisComDescricao.size());
		assertEquals("java", ((ConfiguracaoNivelCompetencia)niveisComDescricao.toArray()[0]).getCompetenciaDescricao());
		assertEquals("proatividade", ((ConfiguracaoNivelCompetencia)niveisComDescricao.toArray()[1]).getCompetenciaDescricao());
	}
	
	public void testFindColaboradorAbaixoNivel()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = new ConfiguracaoNivelCompetencia("faixa1", "bom", 5, "Joao", null, "Ruim", 2, null, null, "avaliadorNome", false);
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia2 = new ConfiguracaoNivelCompetencia("faixa2", "medio", 2, "Pedro", null, "otimo", 5, null, null, "avaliadorNome", false);
		Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias =  Arrays.asList(configuracaoNivelCompetencia1, configuracaoNivelCompetencia2);
		
		configuracaoNivelCompetenciaDao.expects(once()).method("findCompetenciaColaborador").with(new Constraint[]{eq(null), eq(null),ANYTHING, eq(faixaSalarial.getId()),ANYTHING}).will(returnValue(configuracaoNivelCompetencias));
		
		assertEquals(1, configuracaoNivelCompetenciaManager.findColaboradorAbaixoNivel(new Long[]{1L, 2L}, faixaSalarial.getId()).size());
	}
}
