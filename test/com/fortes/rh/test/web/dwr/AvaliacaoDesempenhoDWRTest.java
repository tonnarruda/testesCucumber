package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.web.dwr.AvaliacaoDesempenhoDWR;

public class AvaliacaoDesempenhoDWRTest extends MockObjectTestCase
{
	private AvaliacaoDesempenhoDWR avaliacaoDesempenhoDWR;
	private Mock avaliacaoDesempenhoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		avaliacaoDesempenhoDWR = new AvaliacaoDesempenhoDWR();

		avaliacaoDesempenhoManager = new Mock(AvaliacaoDesempenhoManager.class);
		avaliacaoDesempenhoDWR.setAvaliacaoDesempenhoManager((AvaliacaoDesempenhoManager) avaliacaoDesempenhoManager.proxy());
	}

	public void testGetByEmpresa()
	{
		Long empresaId = 1L;
		avaliacaoDesempenhoManager.expects(once()).method("findAllSelect").with(eq(empresaId), ANYTHING, ANYTHING).will(returnValue(new ArrayList<AvaliacaoDesempenho>()));
		
		assertEquals(0, avaliacaoDesempenhoDWR.getAvaliacoesByEmpresa(empresaId).size());
	}
	
	public void testGetAvaliacoesNaoLiberadasByTitulo()
	{
		Long empresaId = 1L;
		String titulo = "teste";
		avaliacaoDesempenhoManager.expects(once()).method("findTituloModeloAvaliacao").with(new Constraint[]{eq(null), eq(null), eq(empresaId), eq(titulo), eq(null), eq(false)}).will(returnValue(new ArrayList<AvaliacaoDesempenho>()));
		
		assertEquals(0, avaliacaoDesempenhoDWR.getAvaliacoesNaoLiberadasByTitulo(empresaId, titulo).size());
	}
}
