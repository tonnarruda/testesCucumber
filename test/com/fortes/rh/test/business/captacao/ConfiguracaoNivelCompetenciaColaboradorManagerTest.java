package com.fortes.rh.test.business.captacao;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaColaboradorManagerImpl;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaColaboradorDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.util.DateUtil;

public class ConfiguracaoNivelCompetenciaColaboradorManagerTest extends MockObjectTestCase
{
	private ConfiguracaoNivelCompetenciaColaboradorManagerImpl configuracaoNivelCompetenciaColaboradorManager = new ConfiguracaoNivelCompetenciaColaboradorManagerImpl();
	private Mock configuracaoNivelCompetenciaColaboradorDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        configuracaoNivelCompetenciaColaboradorDao = new Mock(ConfiguracaoNivelCompetenciaColaboradorDao.class);
        configuracaoNivelCompetenciaColaboradorManager.setDao((ConfiguracaoNivelCompetenciaColaboradorDao) configuracaoNivelCompetenciaColaboradorDao.proxy());
    }

	public void testFindByIdProjection()
	{
		ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador = new ConfiguracaoNivelCompetenciaColaborador();

		configuracaoNivelCompetenciaColaboradorDao.expects(once()).method("findByIdProjection").with(eq(configuracaoNivelCompetenciaColaborador.getId())).will(returnValue(configuracaoNivelCompetenciaColaborador));
		
		assertEquals(configuracaoNivelCompetenciaColaborador, configuracaoNivelCompetenciaColaboradorManager.findByIdProjection(configuracaoNivelCompetenciaColaborador.getId()));
	}
	
	public void testFindByColaborador()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
				
		ConfiguracaoNivelCompetenciaColaborador config1 = new ConfiguracaoNivelCompetenciaColaborador();
		config1.setColaborador(colaborador);

		ConfiguracaoNivelCompetenciaColaborador config2 = new ConfiguracaoNivelCompetenciaColaborador();
		config2.setColaborador(colaborador);
		
		Collection<ConfiguracaoNivelCompetenciaColaborador> configuracoes = Arrays.asList(config1, config2);
		
		configuracaoNivelCompetenciaColaboradorDao.expects(once()).method("findByColaborador").with(eq(colaborador.getId())).will(returnValue(configuracoes));
		
		assertEquals(2, configuracaoNivelCompetenciaColaboradorManager.findByColaborador(colaborador.getId()).size());
	}
}
