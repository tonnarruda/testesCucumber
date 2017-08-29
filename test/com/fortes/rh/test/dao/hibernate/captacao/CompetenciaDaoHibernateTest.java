package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.dao.captacao.AtitudeDao;
import com.fortes.rh.dao.captacao.CompetenciaDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaColaboradorDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.captacao.HabilidadeDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.dicionario.CompetenciasConsideradas;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.dao.DaoHibernateAnnotationTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;

public class CompetenciaDaoHibernateTest extends DaoHibernateAnnotationTest
{
	@Autowired
	private CompetenciaDao competenciaDao;
	@Autowired
	private ConhecimentoDao conhecimentoDao;
	@Autowired
	private HabilidadeDao habilidadeDao;
	@Autowired
	private AtitudeDao atitudeDao;
	@Autowired
	private EmpresaDao empresaDao;
	@Autowired
	private AvaliacaoDesempenhoDao avaliacaoDesempenhoDao;
	@Autowired
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao; 
	@Autowired
	private ConfiguracaoNivelCompetenciaColaboradorDao configuracaoNivelCompetenciaColaboradorDao; 
	@Autowired
	private ConfiguracaoNivelCompetenciaDao configuracaoNivelCompetenciaDao; 
	@Autowired
	private NivelCompetenciaDao nivelCompetenciaDao; 

	@Test
	public void testExisteNome()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		Empresa empresa2 = EmpresaFactory.getEmpresa();

		empresaDao.save(empresa1);
		empresaDao.save(empresa2);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento(null, "conhecimento", empresa1);
		conhecimentoDao.save(conhecimento);

		Habilidade habilidade = HabilidadeFactory.getEntity(null, "habilidade", empresa1);
		habilidadeDao.save(habilidade);

		Atitude atitude = AtitudeFactory.getEntity(null, "atitude", empresa1);
		atitudeDao.save(atitude);

		Conhecimento conhecimentoNomeHabilidade = ConhecimentoFactory.getConhecimento(null, "habilidade", empresa1);
		conhecimentoDao.save(conhecimentoNomeHabilidade);

		Assert.assertTrue(competenciaDao.existeNome("conhecimento", null, null, empresa1.getId()));
		Assert.assertFalse(competenciaDao.existeNome("conhecimento", conhecimento.getId(), Competencia.CONHECIMENTO, empresa1.getId()));
		
		Assert.assertTrue(competenciaDao.existeNome("habilidade", null, null, empresa1.getId()));
		Assert.assertTrue(competenciaDao.existeNome("habilidade", habilidade.getId(), Competencia.HABILIDADE, empresa1.getId()));
	}
	
	@Test
	public void testFindByAvaliacoesDesempenho(){
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		boolean respondidaParcialmente = false;
		ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity(avaliacaoDesempenho, respondidaParcialmente );
		colaboradorQuestionarioDao.save(colaboradorQuestionario1);

		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador1 = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity(null, null, null, colaboradorQuestionario1);
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador1);
		
		Atitude atitude = AtitudeFactory.getEntity(null, "Atitude", empresa);
		atitudeDao.save(atitude);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento(null, "Conhecimento", empresa);
		conhecimentoDao.save(conhecimento);

		Habilidade habilidade = HabilidadeFactory.getEntity(null, "Habilidade", empresa);
		habilidadeDao.save(habilidade);

		NivelCompetencia nivelPessimo = NivelCompetenciaFactory.getEntity(null, "Péssimo", empresa);
		nivelCompetenciaDao.save(nivelPessimo);
		
		NivelCompetencia nivelRegular = NivelCompetenciaFactory.getEntity(null, "Regular", empresa);
		nivelCompetenciaDao.save(nivelRegular);
		
		NivelCompetencia nivelBom = NivelCompetenciaFactory.getEntity(null, "Bom", empresa);
		nivelCompetenciaDao.save(nivelBom);

		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia1 = ConfiguracaoNivelCompetenciaFactory.getEntityColaborador(configuracaoNivelCompetenciaColaborador1, nivelPessimo, atitude.getId(), TipoCompetencia.ATITUDE);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetencia1);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia2 = ConfiguracaoNivelCompetenciaFactory.getEntityColaborador(configuracaoNivelCompetenciaColaborador1, nivelRegular, conhecimento.getId(), TipoCompetencia.CONHECIMENTO);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetencia2);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia3 = ConfiguracaoNivelCompetenciaFactory.getEntityColaborador(configuracaoNivelCompetenciaColaborador1, nivelBom, habilidade.getId(), TipoCompetencia.HABILIDADE);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetencia3);
		
		competenciaDao.getHibernateTemplateByGenericDao().flush(); //Necessário para que nos testes a view Competencia enxergue os dados inseridos via hibernate 

		Collection<Competencia> competencias = competenciaDao.findByAvaliacoesDesempenho(empresa.getId(), new Long[]{avaliacaoDesempenho.getId()}, CompetenciasConsideradas.TODAS);
		
		Assert.assertEquals(3, competencias.size());
		Assert.assertEquals(atitude.getNome(), competencias.toArray(new Competencia[]{})[0].getNome());
		Assert.assertEquals(conhecimento.getNome(), competencias.toArray(new Competencia[]{})[1].getNome());
		Assert.assertEquals(habilidade.getNome(), competencias.toArray(new Competencia[]{})[2].getNome());
	}
	
}
