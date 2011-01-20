package com.fortes.rh.test.web.action.thumb;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.thumb.ThumbnailManager;
import com.fortes.rh.web.action.thumb.ThumbnailAction;

public class ThumbnailActionTest extends MockObjectTestCase
{
	
	ThumbnailAction action;
	
	private Mock thumbnailManagerMock;

	protected void setUp() throws Exception {
		super.setUp();
		action = new ThumbnailAction();
		action.setThumbnailManager(mockaThumbnailManager());

	}

	private ThumbnailManager mockaThumbnailManager() {
		thumbnailManagerMock = new Mock(ThumbnailManager.class);
		return (ThumbnailManager) thumbnailManagerMock.proxy();
	}
	
	public void testForm() {
		assertEquals("success", action.form());
	}

	public void testProcessa() throws Exception {
		
		dadoQueExistemImagensASeremProcessadas();
		
		String outcome = action.processa();
		
		assertEquals("success", outcome);
		thumbnailManagerMock.verify();
	}
	
	public void testDeveriaExibirErroAoUsuarioSeHouverErroAoProcessarImagens() {
		
		dadoQueOcorraUmErroAoProcessarImagens();
		
		String outcome = action.processa();

		assertEquals("success", outcome);
		assertTrue("Mensagem de erro exibida ao usuário", action.hasActionErrors());
		thumbnailManagerMock.verify();
	}

	private void dadoQueOcorraUmErroAoProcessarImagens() {
		thumbnailManagerMock
		.expects(once()).method("processa").withNoArguments()
			.will(throwException(new RuntimeException("Erro interno.")));
	}

	private void dadoQueExistemImagensASeremProcessadas() {
		thumbnailManagerMock
			.expects(once()).method("processa")
				.withNoArguments()
				.isVoid();
	}

}