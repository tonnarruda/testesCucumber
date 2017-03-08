package com.fortes.rh.test.dao.hibernate.captacao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.dao.captacao.AtitudeDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaCandidatoDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCandidato;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.test.dao.DaoHibernateAnnotationTest;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaCandidatoFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.util.DateUtil;

public class ConfiguracaoNivelCompetenciaDaoHibernateTest extends DaoHibernateAnnotationTest
{
	@Autowired
	private ConfiguracaoNivelCompetenciaDao configuracaoNivelCompetenciaDao;
	@Autowired
	private SolicitacaoDao solicitacaoDao;
	@Autowired
	private CandidatoDao candidatoDao;
	@Autowired
	private CandidatoSolicitacaoDao candidatoSolicitacaoDao;
	@Autowired
	private NivelCompetenciaDao nivelCompetenciaDao;
	@Autowired
	private ConfiguracaoNivelCompetenciaCandidatoDao configuracaoNivelCompetenciaCandidatoDao;
	@Autowired
	private FaixaSalarialDao faixaSalarialDao;
	@Autowired
	private AtitudeDao atitudeDao;
	@Autowired
	private ConhecimentoDao conhecimentoDao;
	@Autowired
	private ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao;

	@Test
	public void testExisteDependenciaComCompetenciasDaCandidato()
	{
		assertTrue("Sem data final e data da solicitação igual à data início)", setUpTestExisteDependenciaComCompetenciasDoCandidato(DateUtil.criarDataDiaMesAno("01/01/2019"), DateUtil.criarDataDiaMesAno("01/01/2019"), null));
		assertFalse("Sem data final e data da solicitação menor que data início)", setUpTestExisteDependenciaComCompetenciasDoCandidato(DateUtil.criarDataDiaMesAno("01/01/2019"), DateUtil.criarDataDiaMesAno("02/01/2019"), null));
		assertTrue("Sem data final e data da solicitação maior que data início)", setUpTestExisteDependenciaComCompetenciasDoCandidato(DateUtil.criarDataDiaMesAno("03/01/2019"), DateUtil.criarDataDiaMesAno("02/01/2019"), null));
		
		assertFalse("Com data final e data da solicitação menor que data início)", setUpTestExisteDependenciaComCompetenciasDoCandidato(DateUtil.criarDataDiaMesAno("01/02/2019"), DateUtil.criarDataDiaMesAno("02/02/2019"), DateUtil.criarDataDiaMesAno("01/03/2019")));
		assertTrue("Com data final e data da solicitação igual à data início)", setUpTestExisteDependenciaComCompetenciasDoCandidato(DateUtil.criarDataDiaMesAno("01/01/2019"), DateUtil.criarDataDiaMesAno("01/01/2019"), DateUtil.criarDataDiaMesAno("01/02/2019")));
		assertTrue("Com data final e data da solicitação entre início e fim)", setUpTestExisteDependenciaComCompetenciasDoCandidato(DateUtil.criarDataDiaMesAno("01/02/2019"), DateUtil.criarDataDiaMesAno("01/01/2019"), DateUtil.criarDataDiaMesAno("01/03/2019")));
		assertFalse("Com data final e data da solicitação igual à data final)", setUpTestExisteDependenciaComCompetenciasDoCandidato(DateUtil.criarDataDiaMesAno("01/02/2019"), DateUtil.criarDataDiaMesAno("01/01/2019"), DateUtil.criarDataDiaMesAno("01/02/2019")));
		assertFalse("Com data final e data da solicitação maior que data final)", setUpTestExisteDependenciaComCompetenciasDoCandidato(DateUtil.criarDataDiaMesAno("02/02/2019"), DateUtil.criarDataDiaMesAno("01/01/2019"), DateUtil.criarDataDiaMesAno("01/02/2019")));
	}
	
	private boolean setUpTestExisteDependenciaComCompetenciasDoCandidato(Date dataSolicitacao, Date dataInicio, Date dataFinal)
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacao.setData(dataSolicitacao);
		solicitacaoDao.save(solicitacao);

		Atitude atitude = criaAtitude();
		
		Candidato candidato = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato);
		
		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao.setCandidato(candidato);
		candidatoSolicitacao.setSolicitacao(solicitacao);
		candidatoSolicitacao.setTriagem(false);
		candidatoSolicitacaoDao.save(candidatoSolicitacao);
		
		candidato.setCandidatoSolicitacaos(Arrays.asList(candidatoSolicitacao));
		candidatoDao.save(candidato);
		
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity();
		nivelCompetenciaDao.save(nivelCompetencia);
		
		ConfiguracaoNivelCompetenciaCandidato configuracaoNivelCompetenciaCandidato = ConfiguracaoNivelCompetenciaCandidatoFactory.getEntity(candidato, solicitacao, null, new Date());
		configuracaoNivelCompetenciaCandidatoDao.save(configuracaoNivelCompetenciaCandidato);
		
		ConfiguracaoNivelCompetencia cncFaixa = ConfiguracaoNivelCompetenciaFactory.getEntityCandidato(configuracaoNivelCompetenciaCandidato, faixaSalarial, nivelCompetencia, atitude.getId(), TipoCompetencia.ATITUDE);
		configuracaoNivelCompetenciaDao.save(cncFaixa);

		return configuracaoNivelCompetenciaDao.candsComDependenciaComCompetenciasDoCandidato(faixaSalarial.getId(), dataInicio, dataFinal).size() > 0;
		
	}

	private Atitude criaAtitude()
	{
		Atitude atitude = AtitudeFactory.getEntity();
		atitude.setNome("atividade");
		atitudeDao.save(atitude);
		return atitude;
	}
}
