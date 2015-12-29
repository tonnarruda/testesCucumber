package com.fortes.rh.test.business.avaliacao;

import java.util.Arrays;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.business.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoManagerImpl;
import com.fortes.rh.business.captacao.CompetenciaManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.dao.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;

public class ConfiguracaoCompetenciaAvaliacaoDesempenhoManagerTest extends MockObjectTestCase
{
	private ConfiguracaoCompetenciaAvaliacaoDesempenhoManagerImpl configuracaoCompetenciaAvaliacaoDesempenhoManager = new ConfiguracaoCompetenciaAvaliacaoDesempenhoManagerImpl();
	private Mock configuracaoCompetenciaAvaliacaoDesempenhoDao;
	private Mock configuracaoNivelCompetenciaManager;
	private Mock gerenciadorComunicacaoManager;
	private Mock avaliacaoDesempenhoManager;
	private Mock faixaSalarialManager;
	private Mock competenciaManager;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        configuracaoCompetenciaAvaliacaoDesempenhoDao = new Mock(ConfiguracaoCompetenciaAvaliacaoDesempenhoDao.class);
        configuracaoCompetenciaAvaliacaoDesempenhoManager.setDao((ConfiguracaoCompetenciaAvaliacaoDesempenhoDao) configuracaoCompetenciaAvaliacaoDesempenhoDao.proxy());
        
        gerenciadorComunicacaoManager = new Mock(GerenciadorComunicacaoManager.class);
        configuracaoCompetenciaAvaliacaoDesempenhoManager.setGerenciadorComunicacaoManager((GerenciadorComunicacaoManager) gerenciadorComunicacaoManager.proxy());
        
        configuracaoNivelCompetenciaManager = new Mock(ConfiguracaoNivelCompetenciaManager.class);
        configuracaoCompetenciaAvaliacaoDesempenhoManager.setConfiguracaoNivelCompetenciaManager((ConfiguracaoNivelCompetenciaManager) configuracaoNivelCompetenciaManager.proxy());
        
        avaliacaoDesempenhoManager = new Mock(AvaliacaoDesempenhoManager.class);
        configuracaoCompetenciaAvaliacaoDesempenhoManager.setAvaliacaoDesempenhoManager((AvaliacaoDesempenhoManager) avaliacaoDesempenhoManager.proxy());
        
        faixaSalarialManager = new Mock(FaixaSalarialManager.class);
        configuracaoCompetenciaAvaliacaoDesempenhoManager.setFaixaSalarialManager((FaixaSalarialManager) faixaSalarialManager.proxy());
        
        competenciaManager = new Mock(CompetenciaManager.class);
        configuracaoCompetenciaAvaliacaoDesempenhoManager.setCompetenciaManager((CompetenciaManager) competenciaManager.proxy());
    }

	public void testSave() 
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		configuracaoCompetenciaAvaliacaoDesempenhoDao.expects(once()).method("findByAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId())).will(returnValue(Arrays.asList(new ConfiguracaoCompetenciaAvaliacaoDesempenho())));
		configuracaoCompetenciaAvaliacaoDesempenhoDao.expects(once()).method("saveOrUpdate").with(ANYTHING).isVoid();

		Exception exception = null;
		try
		{
			configuracaoCompetenciaAvaliacaoDesempenhoManager.save(Arrays.asList(new ConfiguracaoCompetenciaAvaliacaoDesempenho()), avaliacaoDesempenho);
		}
		catch(Exception e){
			exception = e;
		}
		assertNull(exception);
	}
	
	public void testRemoveNotIn()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		configuracaoCompetenciaAvaliacaoDesempenhoDao.expects(once()).method("findByAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId())).will(returnValue(Arrays.asList(new ConfiguracaoCompetenciaAvaliacaoDesempenho())));
		configuracaoCompetenciaAvaliacaoDesempenhoDao.expects(once()).method("remove").with(ANYTHING).isVoid();
		
		Exception exception = null;

		try
		{
			configuracaoCompetenciaAvaliacaoDesempenhoManager.removeNotIn(Arrays.asList(new ConfiguracaoCompetenciaAvaliacaoDesempenho()), avaliacaoDesempenho.getId());
		}
		catch(Exception e){
			exception = e;
		}
		assertNull(exception);
	}
	
	public void testFindByAvaliador(){
		Colaborador avaliador = ColaboradorFactory.getEntity(1L);
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		configuracaoCompetenciaAvaliacaoDesempenhoDao.expects(once()).method("findByAvaliador").with(eq(avaliador.getId()), eq(faixaSalarial.getId()), eq(avaliacaoDesempenho.getId())).will(returnValue(Arrays.asList()));
		assertNotNull(configuracaoCompetenciaAvaliacaoDesempenhoManager.findByAvaliador(avaliador.getId(), faixaSalarial.getId(), avaliacaoDesempenho.getId()));
	}
	
	public void testReajusteByConfiguracaoNivelCompetenciaFaixaSalarial() {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Competencia competencia = new Competencia();
		competencia.setId(1L);
		competencia.setNome("Conhecimento");
		competencia.setTipo(TipoCompetencia.CONHECIMENTO);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarialAnterior = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(1L);
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarialAtual = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(1L);
		configuracaoNivelCompetenciaFaixaSalarialAtual.setFaixaSalarial(faixaSalarial);
		Collection<AvaliacaoDesempenho> avaliacoesDesempenho = Arrays.asList(new AvaliacaoDesempenho()); 
		
		configuracaoCompetenciaAvaliacaoDesempenhoDao.expects(atLeastOnce()).method("removeByCompetenciasQueNaoPermaneceram").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING}).isVoid();
		competenciaManager.expects(once()).method("findCompetencia").with(eq(competencia.getId()), eq(competencia.getTipo())).will(returnValue(competencia));
		faixaSalarialManager.expects(once()).method("findByFaixaSalarialId").with(eq(configuracaoNivelCompetenciaFaixaSalarialAtual.getFaixaSalarial().getId())).will(returnValue(faixaSalarial));
		avaliacaoDesempenhoManager.expects(once()).method("findAvaliacaoDesempenhoBloqueadaComConfiguracaoCompetencia").with(eq(configuracaoNivelCompetenciaFaixaSalarialAnterior.getId())).will(returnValue(avaliacoesDesempenho));
		gerenciadorComunicacaoManager.expects(once()).method("enviaEmailAoInserirConfiguracaoCompetenciaFaixaSalarial").with(new Constraint[]{ANYTHING,ANYTHING, ANYTHING,ANYTHING,ANYTHING});
		configuracaoCompetenciaAvaliacaoDesempenhoDao.expects(once()).method("replaceConfiguracaoNivelCompetenciaFaixaSalarial").with(eq(configuracaoNivelCompetenciaFaixaSalarialAtual)).isVoid();
		
		Exception exception = null;

		try
		{
			configuracaoCompetenciaAvaliacaoDesempenhoManager.reajusteByConfiguracaoNivelCompetenciaFaixaSalarial(Arrays.asList(new Competencia()), Arrays.asList(new Competencia()), Arrays.asList(new Competencia()), Arrays.asList(competencia), configuracaoNivelCompetenciaFaixaSalarialAtual, empresa, configuracaoNivelCompetenciaFaixaSalarialAnterior);
		}
		catch(Exception e){
			exception = e;
		}
		assertNull(exception);
	}
	
	public void testfindFaixasSalariaisByCompetenciasConfiguradasParaAvaliacaoDesempenho() {
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		Collection<FaixaSalarial> faixaSalarials = Arrays.asList(faixaSalarial);
		configuracaoCompetenciaAvaliacaoDesempenhoDao.expects(atLeastOnce()).method("findFaixasSalariaisByCompetenciasConfiguradasParaAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId())).will(returnValue(faixaSalarials));
		configuracaoNivelCompetenciaManager.expects(atLeastOnce()).method("findByConfiguracaoNivelCompetenciaFaixaSalarial").with(ANYTHING);
		
		assertNotNull(configuracaoCompetenciaAvaliacaoDesempenhoManager.findFaixasSalariaisByCompetenciasConfiguradasParaAvaliacaoDesempenho(avaliacaoDesempenho.getId()));
	}
	
	public void testRemoveCompetenciasQueNaoPermaneceram() {
		configuracaoCompetenciaAvaliacaoDesempenhoDao.expects(atLeastOnce()).method("removeByCompetenciasQueNaoPermaneceram").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING}).isVoid();
		Exception exception = null;

		try
		{
			configuracaoCompetenciaAvaliacaoDesempenhoManager.removeCompetenciasQueNaoPermaneceram(Arrays.asList(new Competencia()), Arrays.asList(new Competencia()), 1L, TipoCompetencia.CONHECIMENTO);
		}
		catch(Exception e){
			exception = e;
		}
		assertNull(exception);
	}
	
	public void testExisteNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado() {
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		configuracaoCompetenciaAvaliacaoDesempenhoDao.expects(once()).method("existeNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado").with(eq(avaliacaoDesempenho.getId())).will(returnValue(true));
		assertTrue(configuracaoCompetenciaAvaliacaoDesempenhoManager.existeNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado(avaliacaoDesempenho.getId()));
	}
	
	public void testRemoveByAvaliacaoDesempenho() {
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		configuracaoCompetenciaAvaliacaoDesempenhoDao.expects(once()).method("removeByAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId())).isVoid();
		Exception exception = null;
		try
		{
			configuracaoCompetenciaAvaliacaoDesempenhoManager.removeByAvaliacaoDesempenho(avaliacaoDesempenho.getId());
		}
		catch(Exception e){
			exception = e;
		}
		assertNull(exception);
	}
	
	public void testReplaceConfiguracaoNivelCompetenciaFaixaSalarial() {
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(1L);
		configuracaoCompetenciaAvaliacaoDesempenhoDao.expects(once()).method("replaceConfiguracaoNivelCompetenciaFaixaSalarial").with(eq(configuracaoNivelCompetenciaFaixaSalarial)).isVoid();
		
		Exception exception = null;
		try
		{
			configuracaoCompetenciaAvaliacaoDesempenhoManager.replaceConfiguracaoNivelCompetenciaFaixaSalarial(configuracaoNivelCompetenciaFaixaSalarial);
		}
		catch(Exception e){
			exception = e;
		}
		assertNull(exception);
	}
	
	public void testExiste() {
		ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial = ConfiguracaoNivelCompetenciaFaixaSalarialFactory.getEntity(1L);
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		configuracaoCompetenciaAvaliacaoDesempenhoDao.expects(once()).method("existe").with(eq(configuracaoNivelCompetenciaFaixaSalarial.getId()), eq(avaliacaoDesempenho.getId())).will(returnValue(true));
		
		assertTrue(configuracaoCompetenciaAvaliacaoDesempenhoManager.existe(configuracaoNivelCompetenciaFaixaSalarial.getId(), avaliacaoDesempenho.getId()));
	}
}
