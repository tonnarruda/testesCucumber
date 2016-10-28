package com.fortes.rh.test.web.action.captacao;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.business.captacao.CriterioAvaliacaoCompetenciaManager;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.web.action.captacao.ConhecimentoListAction;

public class ConhecimentoListActionTest extends MockObjectTestCase

{
	private ConhecimentoListAction action;
	private Mock configuracaoNivelCompetenciaManager;
	private Mock conhecimentoManager;
	private Mock criterioAvaliacaoCompetenciaManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		conhecimentoManager = new Mock(ConhecimentoManager.class);
		action = new ConhecimentoListAction();
		configuracaoNivelCompetenciaManager = new Mock(ConfiguracaoNivelCompetenciaManager.class);
		criterioAvaliacaoCompetenciaManager = new Mock(CriterioAvaliacaoCompetenciaManager.class);
		
		action.setConhecimento(new Conhecimento());
		action.setConhecimentoManager((ConhecimentoManager) conhecimentoManager.proxy());
		action.setConfiguracaoNivelCompetenciaManager((ConfiguracaoNivelCompetenciaManager) configuracaoNivelCompetenciaManager.proxy());
		action.setCriterioAvaliacaoCompetenciaManager((CriterioAvaliacaoCompetenciaManager) criterioAvaliacaoCompetenciaManager.proxy());
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
	}

	protected void tearDown() throws Exception
	{
		conhecimentoManager = null;
		action = null;
		super.tearDown();
	}
	
	public void testDelete() throws Exception
	{
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento(1L);
		action.setConhecimento(conhecimento);

		configuracaoNivelCompetenciaManager.expects(once()).method("existeConfiguracaoNivelCompetencia").with(eq(conhecimento.getId()), eq(TipoCompetencia.CONHECIMENTO)).will(returnValue(false));
		criterioAvaliacaoCompetenciaManager.expects(once()).method("removeByCompetencia");
		conhecimentoManager.expects(once()).method("remove");
		assertEquals("success", action.delete());
	}
	
	public void testDeleteComDependenciaConfiguracaoNivelCompetencia() throws Exception
	{
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento(1L);
		action.setConhecimento(conhecimento);

		configuracaoNivelCompetenciaManager.expects(once()).method("existeConfiguracaoNivelCompetencia").with(eq(conhecimento.getId()), eq(TipoCompetencia.CONHECIMENTO)).will(returnValue(true));
		assertEquals("input", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento(1L);
		action.setConhecimento(conhecimento);
		
		configuracaoNivelCompetenciaManager.expects(once()).method("existeConfiguracaoNivelCompetencia").with(eq(conhecimento.getId()), eq(TipoCompetencia.CONHECIMENTO)).will(returnValue(false));
		criterioAvaliacaoCompetenciaManager.expects(once()).method("removeByCompetencia");
		conhecimentoManager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		
		try {
			action.delete();
		} catch (Exception e) {
			fail("Não deveria ter lançado uma exceçao");
		}
	}
}
