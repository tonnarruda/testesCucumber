package com.fortes.rh.test.dao.hibernate.avaliacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.dao.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaColaboradorDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;

public class AvaliacaoDesempenhoDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<AvaliacaoDesempenho>
{
	@Autowired private ConfiguracaoCompetenciaAvaliacaoDesempenhoDao configuracaoCompetenciaAvaliacaoDesempenhoDao;
	@Autowired private ConfiguracaoNivelCompetenciaFaixaSalarialDao configuracaoNivelCompetenciaFaixaSalarialDao;
	@Autowired private ConfiguracaoNivelCompetenciaColaboradorDao configuracaoNivelCompetenciaColaboradorDao;
	@Autowired private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
	@Autowired private AvaliacaoDesempenhoDao avaliacaoDesempenhoDao;
	@Autowired private FaixaSalarialDao faixaSalarialDao;
	@Autowired private ColaboradorDao colaboradorDao;
	@Autowired private AvaliacaoDao avaliacaoDao;
	@Autowired private EmpresaDao empresaDao;
	@Autowired private ConhecimentoDao conhecimentoDao;
	@Autowired private ConfiguracaoCompetenciaAvaliacaoDesempenhoDao competenciaAvaliacaoDesempenhoDao;

	@Override
	public AvaliacaoDesempenho getEntity()
	{
		return AvaliacaoDesempenhoFactory.getEntity();
	}

	@Override
	public GenericDao<AvaliacaoDesempenho> getGenericDao()
	{
		return avaliacaoDesempenhoDao;
	}

	public void setAvaliacaoDesempenhoDao(AvaliacaoDesempenhoDao avaliacaoDesempenhoDao)
	{
		this.avaliacaoDesempenhoDao = avaliacaoDesempenhoDao;
	}
	
	@Test
	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacao.setEmpresa(empresa);
		avaliacaoDao.save(avaliacao);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho.setAvaliacao(avaliacao);
		avaliacaoDesempenho.setEmpresa(empresa);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		assertEquals(1,avaliacaoDesempenhoDao.findAllSelect(empresa.getId(), null, null).size());
		
	}
	
	@Test
	public void testFindByAvaliador()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setNome("empresa");
		empresaDao.save(empresa);
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacao.setEmpresa(empresa);
		avaliacaoDao.save(avaliacao);
		
		AvaliacaoDesempenho avaliacaoDesempenho1 = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho1.setLiberada(true);
		avaliacaoDesempenho1.setEmpresa(empresa);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho1);
		
		AvaliacaoDesempenho avaliacaoDesempenho2 = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho2.setLiberada(false);
		avaliacaoDesempenho2.setEmpresa(empresa);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho2);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario1.setAvaliacaoDesempenho(avaliacaoDesempenho1);
		colaboradorQuestionario1.setAvaliacao(avaliacao);
		colaboradorQuestionario1.setAvaliador(colaborador);
		colaboradorQuestionarioDao.save(colaboradorQuestionario1);
		
		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setAvaliacaoDesempenho(avaliacaoDesempenho1);
		colaboradorQuestionario2.setAvaliacao(avaliacao);
		colaboradorQuestionario2.setAvaliador(colaborador);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);
		
		ColaboradorQuestionario colaboradorQuestionario3 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario3.setAvaliacaoDesempenho(avaliacaoDesempenho2);
		colaboradorQuestionario3.setAvaliacao(avaliacao);
		colaboradorQuestionario3.setAvaliador(colaborador);
		colaboradorQuestionarioDao.save(colaboradorQuestionario3);
		
		assertEquals(1, avaliacaoDesempenhoDao.findByAvaliador(colaborador.getId(), true, empresa.getId()).size());
	}
	
	@Test
	public void testLiberarOrBloquear()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacao.setEmpresa(empresa);
		avaliacaoDao.save(avaliacao);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho.setAvaliacao(avaliacao);
		avaliacaoDesempenho.setEmpresa(empresa);
		avaliacaoDesempenho.setLiberada(false);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		avaliacaoDesempenhoDao.liberarOrBloquear(avaliacaoDesempenho.getId(), true);
		AvaliacaoDesempenho retorno = avaliacaoDesempenhoDao.findByIdProjection(avaliacaoDesempenho.getId());
		assertTrue(retorno.isLiberada());
	}
	
	@Test
	public void testFindTituloModeloAvaliacao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacao.setEmpresa(empresa);
		avaliacaoDao.save(avaliacao);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho.setTitulo("avaliação desempenho de 90 dias");
		avaliacaoDesempenho.setAvaliacao(avaliacao);
		avaliacaoDesempenho.setEmpresa(empresa);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);

		AvaliacaoDesempenho avaliacaoDesempenho2 = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho2.setTitulo("avaliação desempenho de 30 dias");
		avaliacaoDesempenho2.setAvaliacao(avaliacao);
		avaliacaoDesempenho2.setEmpresa(empresa);
		avaliacaoDesempenho2.setLiberada(true);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho2);

		AvaliacaoDesempenho avaliacaoDesempenho3 = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho3.setTitulo("avaliação desempenho de 30 dias");
		avaliacaoDesempenho3.setAvaliacao(avaliacao);
		avaliacaoDesempenho3.setEmpresa(empresa);
		avaliacaoDesempenho3.setLiberada(false);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho3);
		
		assertEquals(3, avaliacaoDesempenhoDao.findTituloModeloAvaliacao(1, 15, null, null, empresa.getId(), "ação", avaliacao.getId(), null).size());
		assertEquals(2, avaliacaoDesempenhoDao.findTituloModeloAvaliacao(1, 15, null, null, empresa.getId(), "ação", avaliacao.getId(), false).size());
		assertEquals(1, avaliacaoDesempenhoDao.findTituloModeloAvaliacao(1, 15, null, null, empresa.getId(), "ação", avaliacao.getId(), true).size());
		
		assertEquals(new Integer(3), avaliacaoDesempenhoDao.findCountTituloModeloAvaliacao(null, null, null, null, empresa.getId(), "ação", avaliacao.getId(), null));
		assertEquals(new Integer(2), avaliacaoDesempenhoDao.findCountTituloModeloAvaliacao(null, null, null, null, empresa.getId(), "ação", avaliacao.getId(), false));
		assertEquals(new Integer(1), avaliacaoDesempenhoDao.findCountTituloModeloAvaliacao(null, null, null, null, empresa.getId(), "ação", avaliacao.getId(), true));
	}
	
	@Test
	public void testFindIdsAvaliacaoDesempenha()
	{
		Avaliacao avaliacao1 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao1);
		
		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao2);
		
		AvaliacaoDesempenho avaliacaoDesempenho1 = AvaliacaoDesempenhoFactory.getEntity(1L);
		avaliacaoDesempenho1.setAvaliacao(avaliacao1);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho1);

		AvaliacaoDesempenho avaliacaoDesempenho2 = AvaliacaoDesempenhoFactory.getEntity(2L);
		avaliacaoDesempenho2.setAvaliacao(avaliacao1);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho2);

		AvaliacaoDesempenho avaliacaoDesempenhoFora = AvaliacaoDesempenhoFactory.getEntity(3L);
		avaliacaoDesempenhoFora.setAvaliacao(avaliacao2);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenhoFora);
		
		assertEquals(2, avaliacaoDesempenhoDao.findIdsAvaliacaoDesempenho(avaliacao1.getId()).size());
	}
	
	@Test
	public void testFindComCompetencia()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		AvaliacaoDesempenho avaliacaoDesempenho1 = AvaliacaoDesempenhoFactory.getEntity(1L);
		avaliacaoDesempenho1.setEmpresa(empresa1);
		avaliacaoDesempenho1.setLiberada(true);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho1);
		
		AvaliacaoDesempenho avaliacaoDesempenho2 = AvaliacaoDesempenhoFactory.getEntity(2L);
		avaliacaoDesempenho2.setEmpresa(empresa2);
		avaliacaoDesempenho2.setLiberada(true);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho2);

		ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario1.setAvaliacaoDesempenho(avaliacaoDesempenho1);
		colaboradorQuestionarioDao.save(colaboradorQuestionario1);
		
		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setAvaliacaoDesempenho(avaliacaoDesempenho2);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador1 = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador1.setColaboradorQuestionario(colaboradorQuestionario1);
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador1);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador2 = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaborador2.setColaboradorQuestionario(colaboradorQuestionario2);
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador2);
		
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador3 = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity();
		configuracaoNivelCompetenciaColaboradorDao.save(configuracaoNivelCompetenciaColaborador3);
		
		assertEquals(1, avaliacaoDesempenhoDao.findComCompetencia(empresa1.getId()).size());
	}
	
	@Test
	public void testIsExibiNivelCompetenciaExigido()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		avaliacaoDesempenho.setEmpresa(empresa);
		avaliacaoDesempenho.setExibirNivelCompetenciaExigido(true);
		avaliacaoDesempenho.setLiberada(true);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		assertTrue(avaliacaoDesempenhoDao.isExibiNivelCompetenciaExigido(avaliacaoDesempenho.getId()));
	}
	
	@Test
	public void testFindByCncfId() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador avaliador = ColaboradorFactory.getEntity(null, empresa);
		colaboradorDao.save(avaliador);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(null, "Avalação", false, null, empresa);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(faixaSalarial, new Date());
		configuracaoNivelCompetenciaFaixaSalarialDao.save(configuracaoNivelCompetenciaFaixaSalarial);
		
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento(null, "Conhecimento", empresa);
		conhecimentoDao.save(conhecimento);
		
		ConfiguracaoCompetenciaAvaliacaoDesempenho competenciaAvaliacaoDesempenho = ConfiguracaoCompetenciaAvaliacaoDesempenhoFactory.getEntity
					(null, avaliador, avaliacaoDesempenho, configuracaoNivelCompetenciaFaixaSalarial, TipoCompetencia.CONHECIMENTO, conhecimento.getId());
		configuracaoCompetenciaAvaliacaoDesempenhoDao.save(competenciaAvaliacaoDesempenho);
		
		Collection<AvaliacaoDesempenho> avaliacoesDesempenho = avaliacaoDesempenhoDao.findByCncfId(configuracaoNivelCompetenciaFaixaSalarial.getId());
		assertEquals(avaliacaoDesempenho.getId(), avaliacoesDesempenho.iterator().next().getId());
	}
}