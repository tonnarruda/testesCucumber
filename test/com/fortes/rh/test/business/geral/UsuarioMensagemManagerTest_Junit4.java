package com.fortes.rh.test.business.geral;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.business.geral.UsuarioMensagemManagerImpl;
import com.fortes.rh.dao.geral.UsuarioMensagemDao;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.dicionario.TipoMensagem;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;

public class UsuarioMensagemManagerTest_Junit4
{
	private UsuarioMensagemManagerImpl usuarioMensagemManager = new UsuarioMensagemManagerImpl();
	private MensagemManager mensagemManager;
	private UsuarioMensagemDao usuarioMensagemDao;
	private UsuarioEmpresaManager usuarioEmpresaManager;

	@Before
    public void setUp() throws Exception
    {
    	mensagemManager = mock(MensagemManager.class);
        usuarioMensagemDao = mock(UsuarioMensagemDao.class);
        usuarioEmpresaManager = mock(UsuarioEmpresaManager.class);

        usuarioMensagemManager.setDao(usuarioMensagemDao);
        usuarioMensagemManager.setMensagemManager(mensagemManager);
        usuarioMensagemManager.setUsuarioEmpresaManager(usuarioEmpresaManager);

	}

	@Test
	public void testSaveMensagemResponderAcompPeriodoExperiencia() {
		Collection<Long> areasIds = new ArrayList<Long>();
		Colaborador colaborador = ColaboradorFactory.getEntity(2L);
		int tipoResponsavel = AreaOrganizacional.RESPONSAVEL;
		
		Collection<UsuarioEmpresa> usuariosEmpresa = new ArrayList<UsuarioEmpresa>();
		when(usuarioEmpresaManager.findUsuarioResponsavelOuCoResponsavelPorAreaOrganizacional(areasIds, colaborador.getId(), tipoResponsavel)).thenReturn(usuariosEmpresa);
		Exception exception = null;
		try {
			usuarioMensagemManager.saveMensagemResponderAcompPeriodoExperiencia("", "", "", areasIds, TipoMensagem.AVALIACAO_DESEMPENHO, null, colaborador, AreaOrganizacional.RESPONSAVEL);
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
}