package com.fortes.rh.test.business.geral;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.MensagemManagerImpl;
import com.fortes.rh.dao.geral.MensagemDao;
import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.test.factory.geral.MensagemFactory;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.util.ArquivoUtil;
import com.opensymphony.webwork.ServletActionContext;

public class MensagemManagerTest extends MockObjectTestCase
{
	private MensagemManagerImpl mensagemManager = new MensagemManagerImpl();
	private Mock mensagemDao = null;

    protected void setUp() throws Exception
    {
        super.setUp();

        mensagemDao = new Mock(MensagemDao.class);
        mensagemManager.setDao((MensagemDao) mensagemDao.proxy());

        Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
		Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
	}

	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();
	}

	public void testSave() throws Exception
	{
		Mensagem mensagem = MensagemFactory.getEntity(1L);

		mensagemDao.expects(once()).method("save").with(ANYTHING).will(returnValue(mensagem));

		Mensagem retorno = mensagemManager.save(mensagem);

		assertEquals(mensagem.getId(), retorno.getId());

	}

}