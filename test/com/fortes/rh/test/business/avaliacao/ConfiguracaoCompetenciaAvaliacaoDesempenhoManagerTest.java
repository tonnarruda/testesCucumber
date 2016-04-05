package com.fortes.rh.test.business.avaliacao;

import java.util.Arrays;
import java.util.Collection;

import org.jmock.Mock;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.business.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoManagerImpl;
import com.fortes.rh.business.captacao.CompetenciaManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.dao.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.business.MockObjectTestCaseManager;
import com.fortes.rh.test.business.TesteAutomaticoManager;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;

public class ConfiguracaoCompetenciaAvaliacaoDesempenhoManagerTest extends MockObjectTestCaseManager<ConfiguracaoCompetenciaAvaliacaoDesempenhoManagerImpl> implements TesteAutomaticoManager
{
	private Mock configuracaoCompetenciaAvaliacaoDesempenhoDao;
	private Mock configuracaoNivelCompetenciaManager;
	private Mock gerenciadorComunicacaoManager;
	private Mock avaliacaoDesempenhoManager;
	private Mock faixaSalarialManager;
	private Mock competenciaManager;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        manager = new ConfiguracaoCompetenciaAvaliacaoDesempenhoManagerImpl();
        configuracaoCompetenciaAvaliacaoDesempenhoDao = new Mock(ConfiguracaoCompetenciaAvaliacaoDesempenhoDao.class);
        manager.setDao((ConfiguracaoCompetenciaAvaliacaoDesempenhoDao) configuracaoCompetenciaAvaliacaoDesempenhoDao.proxy());
        
        gerenciadorComunicacaoManager = new Mock(GerenciadorComunicacaoManager.class);
        manager.setGerenciadorComunicacaoManager((GerenciadorComunicacaoManager) gerenciadorComunicacaoManager.proxy());
        
        configuracaoNivelCompetenciaManager = new Mock(ConfiguracaoNivelCompetenciaManager.class);
        manager.setConfiguracaoNivelCompetenciaManager((ConfiguracaoNivelCompetenciaManager) configuracaoNivelCompetenciaManager.proxy());
        
        avaliacaoDesempenhoManager = new Mock(AvaliacaoDesempenhoManager.class);
        manager.setAvaliacaoDesempenhoManager((AvaliacaoDesempenhoManager) avaliacaoDesempenhoManager.proxy());
        
        faixaSalarialManager = new Mock(FaixaSalarialManager.class);
        manager.setFaixaSalarialManager((FaixaSalarialManager) faixaSalarialManager.proxy());
        
        competenciaManager = new Mock(CompetenciaManager.class);
        manager.setCompetenciaManager((CompetenciaManager) competenciaManager.proxy());
    }

	public void testSave() 
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		configuracaoCompetenciaAvaliacaoDesempenhoDao.expects(once()).method("findByAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId())).will(returnValue(Arrays.asList(new ConfiguracaoCompetenciaAvaliacaoDesempenho())));
		configuracaoCompetenciaAvaliacaoDesempenhoDao.expects(once()).method("saveOrUpdate").with(ANYTHING).isVoid();

		Exception exception = null;
		try
		{
			manager.save(Arrays.asList(new ConfiguracaoCompetenciaAvaliacaoDesempenho()), avaliacaoDesempenho);
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
			manager.removeNotIn(Arrays.asList(new ConfiguracaoCompetenciaAvaliacaoDesempenho()), avaliacaoDesempenho.getId());
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
		assertNotNull(manager.findByAvaliador(avaliador.getId(), faixaSalarial.getId(), avaliacaoDesempenho.getId()));
	}
	
	public void testfindFaixasSalariaisByCompetenciasConfiguradasParaAvaliacaoDesempenho() {
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		Collection<FaixaSalarial> faixaSalarials = Arrays.asList(faixaSalarial);
		configuracaoCompetenciaAvaliacaoDesempenhoDao.expects(atLeastOnce()).method("findFaixasSalariaisByCompetenciasConfiguradasParaAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId())).will(returnValue(faixaSalarials));
		configuracaoNivelCompetenciaManager.expects(atLeastOnce()).method("findByConfiguracaoNivelCompetenciaFaixaSalarial").with(ANYTHING);
		
		assertNotNull(manager.findFaixasSalariaisByCompetenciasConfiguradasParaAvaliacaoDesempenho(avaliacaoDesempenho.getId()));
	}
	
	public void testExisteNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado() {
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		configuracaoCompetenciaAvaliacaoDesempenhoDao.expects(once()).method("existeNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado").with(eq(avaliacaoDesempenho.getId())).will(returnValue(true));
		assertTrue(manager.existeNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado(avaliacaoDesempenho.getId()));
	}
	
	public void testRemoveByAvaliacaoDesempenho() {
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		configuracaoCompetenciaAvaliacaoDesempenhoDao.expects(once()).method("removeByAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId())).isVoid();
		Exception exception = null;
		try
		{
			manager.removeByAvaliacaoDesempenho(avaliacaoDesempenho.getId());
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
		
		assertTrue(manager.existe(configuracaoNivelCompetenciaFaixaSalarial.getId(), avaliacaoDesempenho.getId()));
	}

	public void testExecutaTesteAutomaticoDoManager() {
		testeAutomatico(configuracaoCompetenciaAvaliacaoDesempenhoDao);
	}
}
