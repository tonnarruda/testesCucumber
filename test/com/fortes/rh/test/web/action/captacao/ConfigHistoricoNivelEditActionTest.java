package com.fortes.rh.test.web.action.captacao;

import java.util.Arrays;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.captacao.ConfigHistoricoNivelManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialManager;
import com.fortes.rh.business.captacao.CriterioAvaliacaoCompetenciaManager;
import com.fortes.rh.business.captacao.NivelCompetenciaHistoricoManager;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.ConfigHistoricoNivelFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaHistoricoFactory;
import com.fortes.rh.web.action.captacao.ConfigHistoricoNivelEditAction;

public class ConfigHistoricoNivelEditActionTest extends MockObjectTestCase
{
	private ConfigHistoricoNivelEditAction action;
	private Mock configuracaoNivelCompetenciaFaixaSalarialManager;
	private Mock criterioAvaliacaoCompetenciaManager;
	private Mock nivelCompetenciaHistoricoManager;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(ConfigHistoricoNivelManager.class);
		nivelCompetenciaHistoricoManager = new Mock(NivelCompetenciaHistoricoManager.class);
		configuracaoNivelCompetenciaFaixaSalarialManager = new Mock(ConfiguracaoNivelCompetenciaFaixaSalarialManager.class);
		criterioAvaliacaoCompetenciaManager = new Mock(CriterioAvaliacaoCompetenciaManager.class);
		
		action = new ConfigHistoricoNivelEditAction();
		action.setConfigHistoricoNivelManager((ConfigHistoricoNivelManager) manager.proxy());
		action.setNivelCompetenciaHistoricoManager((NivelCompetenciaHistoricoManager) nivelCompetenciaHistoricoManager.proxy());
		action.setConfiguracaoNivelCompetenciaFaixaSalarialManager((ConfiguracaoNivelCompetenciaFaixaSalarialManager) configuracaoNivelCompetenciaFaixaSalarialManager.proxy());
		action.setCriterioAvaliacaoCompetenciaManager((CriterioAvaliacaoCompetenciaManager) criterioAvaliacaoCompetenciaManager.proxy());

		action.setConfigHistoricoNivel(new ConfigHistoricoNivel());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testInsert() throws Exception
	{
		ConfigHistoricoNivel configHistoricoNivel = ConfigHistoricoNivelFactory.getEntity(1L);
		action.setConfigHistoricoNivel(configHistoricoNivel);
		action.setConfigHistoricoNivels(Arrays.asList(configHistoricoNivel));
		
		nivelCompetenciaHistoricoManager.expects(once()).method("save").with(ANYTHING);

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		ConfigHistoricoNivel configHistoricoNivel = ConfigHistoricoNivelFactory.getEntity(1L);
		action.setConfigHistoricoNivel(configHistoricoNivel);
		action.setConfigHistoricoNivels(Arrays.asList(configHistoricoNivel));
		
		nivelCompetenciaHistoricoManager.expects(once()).method("save").will(throwException(new DataIntegrityViolationException("")));
		
		manager.expects(once()).method("findNiveisCompetenciaByEmpresa").with(eq(empresa.getId()));
		criterioAvaliacaoCompetenciaManager.expects(once()).method("existeCriterioAvaliacaoCompetencia").with(eq(empresa.getId())).will(returnValue(false));
		
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		ConfigHistoricoNivel configHistoricoNivel = ConfigHistoricoNivelFactory.getEntity(1L);
		action.setConfigHistoricoNivels(Arrays.asList(configHistoricoNivel));
		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity(1L);
		action.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);

		nivelCompetenciaHistoricoManager.expects(once()).method("updateNivelConfiguracaoHistorico").with(eq(nivelCompetenciaHistorico));
		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		ConfigHistoricoNivel configHistoricoNivel = ConfigHistoricoNivelFactory.getEntity(1L);
		action.setConfigHistoricoNivels(Arrays.asList(configHistoricoNivel));
		
		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity(1L);
		action.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);

		nivelCompetenciaHistoricoManager.expects(once()).method("updateNivelConfiguracaoHistorico").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		nivelCompetenciaHistoricoManager.expects(once()).method("findById").with(eq(nivelCompetenciaHistorico.getId())).will(returnValue(nivelCompetenciaHistorico));
		manager.expects(once()).method("findByNivelCompetenciaHistoricoId").with(eq(nivelCompetenciaHistorico.getId()));

		configuracaoNivelCompetenciaFaixaSalarialManager.expects(once()).method("existByNivelCompetenciaHistoricoId").with(eq(nivelCompetenciaHistorico.getId())).will(returnValue(true));
		
		criterioAvaliacaoCompetenciaManager.expects(once()).method("existeCriterioAvaliacaoCompetencia").with(eq(empresa.getId())).will(returnValue(true));
		
		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setConfigHistoricoNivel(null);

		assertNotNull(action.getConfigHistoricoNivel());
		assertTrue(action.getConfigHistoricoNivel() instanceof ConfigHistoricoNivel);
	}
}
