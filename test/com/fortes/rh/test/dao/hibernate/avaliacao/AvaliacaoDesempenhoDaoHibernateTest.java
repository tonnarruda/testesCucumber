package com.fortes.rh.test.dao.hibernate.avaliacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.dao.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoDao;
import com.fortes.rh.dao.captacao.ConfigHistoricoNivelDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaColaboradorDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.captacao.HabilidadeDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaHistoricoDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.avaliacao.AnaliseDesempenhoOrganizacao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
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
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.util.DateUtil;

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
	@Autowired private HistoricoColaboradorDao historicoColaboradorDao;
	@Autowired private EstabelecimentoDao estabelecimentoDao;
	@Autowired private AreaOrganizacionalDao areaOrganizacionalDao ;
	@Autowired private CargoDao cargoDao ;
	@Autowired private NivelCompetenciaHistoricoDao nivelCompetenciaHistoricoDao;
	@Autowired private NivelCompetenciaDao nivelCompetenciaDao; 
	@Autowired private ConfigHistoricoNivelDao configHistoricoNivelDao;
	@Autowired private ConfiguracaoNivelCompetenciaDao configuracaoNivelCompetenciaDao;
	@Autowired private HabilidadeDao habilidadeDao;

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
	
	@Test
	public void testFindByModelo() {
		Avaliacao avaliacao1 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao1);
		
		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao2);
		
		AvaliacaoDesempenho avaliacaoDesempenho1 = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho1.setAvaliacao(avaliacao1);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho1);

		AvaliacaoDesempenho avaliacaoDesempenho2 = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho2.setAvaliacao(avaliacao2);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho2);
		
		Collection<AvaliacaoDesempenho> retorno = avaliacaoDesempenhoDao.findByModelo(avaliacao1.getId());
		
		assertEquals(1, retorno.size());
		assertEquals(avaliacaoDesempenho1.getId(), retorno.iterator().next().getId());
	}
	
	@Test
	public void testFindEstabelecimentosDosParticipantes() {
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho.setInicio(new Date());
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, new Date(), null, estabelecimento , null, null, null, StatusRetornoAC.CONFIRMADO); 
		historicoColaboradorDao.save(historicoColaborador);
		
		Collection<HistoricoColaborador> historicoColaboradors = Arrays.asList(historicoColaborador);
		colaborador.setHistoricoColaboradors(historicoColaboradors );
		
		ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario1.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionario1.setColaborador(colaborador);
		colaboradorQuestionarioDao.save(colaboradorQuestionario1);

		Long[] avaliacoesDesempenhoIds = new Long[]{avaliacaoDesempenho.getId()};
		
		Collection<Estabelecimento> estabelecimentosDosParticipantes = avaliacaoDesempenhoDao.findEstabelecimentosDosParticipantes(avaliacoesDesempenhoIds);
		
		Assert.assertEquals(1, estabelecimentosDosParticipantes.size());
		Assert.assertEquals(estabelecimento.getId(), estabelecimentosDosParticipantes.iterator().next().getId());
	}
	
	@Test
	public void testFindAreasOrganizacionaisDosParticipantes() {
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho.setInicio(new Date());
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, new Date(), null, null, areaOrganizacional, null, null, StatusRetornoAC.CONFIRMADO); 
		historicoColaboradorDao.save(historicoColaborador);
		
		Collection<HistoricoColaborador> historicoColaboradors = Arrays.asList(historicoColaborador);
		colaborador.setHistoricoColaboradors(historicoColaboradors );
		
		ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario1.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionario1.setColaborador(colaborador);
		colaboradorQuestionarioDao.save(colaboradorQuestionario1);

		Long[] avaliacoesDesempenhoIds = new Long[]{avaliacaoDesempenho.getId()};
		
		 Collection<AreaOrganizacional> areasDosParticipantes = avaliacaoDesempenhoDao.findAreasOrganizacionaisDosParticipantes(avaliacoesDesempenhoIds);
		
		Assert.assertEquals(1, areasDosParticipantes.size());
		Assert.assertEquals(areaOrganizacional.getId(), areasDosParticipantes.iterator().next().getId());
	}
	
	@Test
	public void testFindCargosDosParticipantes() {
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho.setInicio(new Date());
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity("Faixa I", cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, new Date(), faixaSalarial, null, null, null, null, StatusRetornoAC.CONFIRMADO); 
		historicoColaboradorDao.save(historicoColaborador);
		
		Collection<HistoricoColaborador> historicoColaboradors = Arrays.asList(historicoColaborador);
		colaborador.setHistoricoColaboradors(historicoColaboradors );
		
		ColaboradorQuestionario colaboradorQuestionario1 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario1.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionario1.setColaborador(colaborador);
		colaboradorQuestionarioDao.save(colaboradorQuestionario1);

		Long[] avaliacoesDesempenhoIds = new Long[]{avaliacaoDesempenho.getId()};
		
		Collection<Cargo> cargosDosParticipantes = avaliacaoDesempenhoDao.findCargosDosParticipantes(avaliacoesDesempenhoIds);
		
		Assert.assertEquals(1, cargosDosParticipantes.size());
		Assert.assertEquals(cargo.getId(), cargosDosParticipantes.iterator().next().getId());
	}
	
	@Test
	public void testFindAnaliseDesempenhoOrganizacaoPorEmpresa() {
		String nomeAgrupador = "Empresa I";
		SetUpTesteFindAnaliseDesempenhoOrganizacao setup = setupTestFindAnaliseDesempenhoOrganizacao(nomeAgrupador);
		
		Collection<AnaliseDesempenhoOrganizacao> analiseDesempenhoOrganizacao = avaliacaoDesempenhoDao.findAnaliseDesempenhoOrganizacao(setup.avaliacoesDesempenhoIds, setup.estabelecimentosIds, null, null, setup.competenciasIds, AnaliseDesempenhoOrganizacao.POR_EMPRESA, setup.empresaId);
		
		Assert.assertEquals(1, analiseDesempenhoOrganizacao.size());
		Assert.assertEquals(nomeAgrupador, analiseDesempenhoOrganizacao.iterator().next().getAgrupador());
	}

	@Test
	public void testFindAnaliseDesempenhoOrganizacaoPorArea() {
		String nomeAgrupador = "Área I";
		SetUpTesteFindAnaliseDesempenhoOrganizacao setup = setupTestFindAnaliseDesempenhoOrganizacao(nomeAgrupador);
		
		Collection<AnaliseDesempenhoOrganizacao> analiseDesempenhoOrganizacao = avaliacaoDesempenhoDao.findAnaliseDesempenhoOrganizacao(setup.avaliacoesDesempenhoIds, setup.estabelecimentosIds, null, setup.areasIds, setup.competenciasIds, AnaliseDesempenhoOrganizacao.POR_AREA, setup.empresaId);
		
		Assert.assertEquals(1, analiseDesempenhoOrganizacao.size());
		Assert.assertEquals(nomeAgrupador, analiseDesempenhoOrganizacao.iterator().next().getAgrupador());
	}
	
	@Test
	public void testFindAnaliseDesempenhoOrganizacaoPorCargo() {
		String nomeAgrupador = "Cargo I";
		SetUpTesteFindAnaliseDesempenhoOrganizacao setup = setupTestFindAnaliseDesempenhoOrganizacao(nomeAgrupador);
		
		Collection<AnaliseDesempenhoOrganizacao> analiseDesempenhoOrganizacao = avaliacaoDesempenhoDao.findAnaliseDesempenhoOrganizacao(setup.avaliacoesDesempenhoIds, setup.estabelecimentosIds,setup.cargosIds, null, setup.competenciasIds, AnaliseDesempenhoOrganizacao.POR_CARGO, setup.empresaId);
		
		Assert.assertEquals(1, analiseDesempenhoOrganizacao.size());
		Assert.assertEquals(nomeAgrupador, analiseDesempenhoOrganizacao.iterator().next().getAgrupador());
	}
	
	private SetUpTesteFindAnaliseDesempenhoOrganizacao setupTestFindAnaliseDesempenhoOrganizacao(String nomeAgrupador) {
		Date hoje = DateUtil.criarDataMesAno(new Date());
		
		Empresa empresa = EmpresaFactory.getEmpresa(null, nomeAgrupador, null, null);
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);

		Colaborador avaliador = ColaboradorFactory.getEntity();
		colaboradorDao.save(avaliador);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);

		Cargo cargo = CargoFactory.getEntity(nomeAgrupador);
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity("Faixa I", cargo);
		faixaSalarialDao.save(faixaSalarial);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(null, nomeAgrupador, Boolean.TRUE, empresa);
		areaOrganizacionalDao.save(areaOrganizacional);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, hoje, faixaSalarial, estabelecimento, areaOrganizacional, null, null, StatusRetornoAC.CONFIRMADO); 
		historicoColaboradorDao.save(historicoColaborador);

		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho.setInicio(hoje);
		avaliacaoDesempenho.setEmpresa(empresa);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(avaliacaoDesempenho, Boolean.FALSE);
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setPesoAvaliador(2);
		colaboradorQuestionario.setRespondida(true);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		Habilidade habilidade = HabilidadeFactory.getEntity(null, "Habilidade I", empresa);
		habilidadeDao.save(habilidade);

		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity(hoje, empresa);
		nivelCompetenciaHistoricoDao.save(nivelCompetenciaHistorico);
		
		NivelCompetencia nivelCompetencia = NivelCompetenciaFactory.getEntity();
		nivelCompetenciaDao.save(nivelCompetencia);
		
		ConfigHistoricoNivel configHistoricoNivel = ConfigHistoricoNivelFactory.getEntity(null, 1, nivelCompetenciaHistorico, nivelCompetencia, null);
		configHistoricoNivelDao.save(configHistoricoNivel);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial cncFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(faixaSalarial, hoje);
		cncFaixaSalarial.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		configuracaoNivelCompetenciaFaixaSalarialDao.save(cncFaixaSalarial);

		ConfiguracaoNivelCompetenciaColaborador cncColaborador = ConfiguracaoNivelCompetenciaColaboradorFactory.getEntity(hoje, colaborador, avaliador, colaboradorQuestionario);
		cncColaborador.setConfiguracaoNivelCompetenciaFaixaSalarial(cncFaixaSalarial);
		configuracaoNivelCompetenciaColaboradorDao.save(cncColaborador);

		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = ConfiguracaoNivelCompetenciaFactory.getEntity(nivelCompetencia, habilidade.getId(), cncColaborador, cncFaixaSalarial, faixaSalarial);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetencia);

		avaliacaoDesempenhoDao.getHibernateTemplateByGenericDao().flush();
		
		return new SetUpTesteFindAnaliseDesempenhoOrganizacao(new Long[]{avaliacaoDesempenho.getId()},new Long[]{estabelecimento.getId()},new Long[]{cargo.getId()},new Long[]{areaOrganizacional.getId()},new Long[]{habilidade.getId()}, empresa.getId());
	}
	
	class SetUpTesteFindAnaliseDesempenhoOrganizacao{
		Long[] avaliacoesDesempenhoIds ;
		Long[] estabelecimentosIds ;
		Long[] cargosIds ;
		Long[] areasIds ;
		Long[] competenciasIds ;
		Long empresaId;
		
		public SetUpTesteFindAnaliseDesempenhoOrganizacao(Long[] avaliacoesDesempenhoIds, Long[] estabelecimentosIds, Long[] cargosIds, Long[] areasIds, Long[] competenciasIds, Long empresaId) {
			super();
			this.avaliacoesDesempenhoIds = avaliacoesDesempenhoIds;
			this.estabelecimentosIds = estabelecimentosIds;
			this.cargosIds = cargosIds;
			this.areasIds = areasIds;
			this.competenciasIds = competenciasIds;
			this.empresaId = empresaId;
		}
	}
}