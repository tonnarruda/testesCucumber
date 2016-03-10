package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.desenvolvimento.TurmaDocumentoAnexoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.business.geral.UsuarioMensagemManagerImpl;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.dao.geral.UsuarioMensagemDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.desenvolvimento.TurmaDocumentoAnexo;
import com.fortes.rh.model.dicionario.TipoMensagem;
import com.fortes.rh.model.geral.CaixaMensagem;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.model.geral.UsuarioMensagem;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.MensagemFactory;
import com.fortes.rh.test.factory.geral.UsuarioEmpresaFactory;
import com.fortes.rh.test.factory.geral.UsuarioMensagemFactory;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.SpringUtil;
import com.opensymphony.webwork.ServletActionContext;

public class UsuarioMensagemManagerTest extends MockObjectTestCase
{
	private UsuarioMensagemManagerImpl usuarioMensagemManager = new UsuarioMensagemManagerImpl();
	private Mock usuarioMensagemDao = null;
	private Mock mensagemManager = null;
	private Mock usuarioEmpresaManager = null;
	private Mock questionarioManager = null;
	private Mock colaboradorQuestionarioManager = null;
	private Mock colaboradorManager = null;
	private Mock avaliacaoDesempenhoManager = null;
	private Mock solicitacaoManager = null;
	private Mock gerenciadorComunicacaoManager = null;
	private Mock turmaDocumentoAnexoManager = null;

    protected void setUp() throws Exception
    {
        super.setUp();

        usuarioMensagemDao = new Mock(UsuarioMensagemDao.class);
        usuarioMensagemManager.setDao((UsuarioMensagemDao) usuarioMensagemDao.proxy());

        mensagemManager = new Mock(MensagemManager.class);
        usuarioMensagemManager.setMensagemManager((MensagemManager) mensagemManager.proxy());

        usuarioEmpresaManager = new Mock(UsuarioEmpresaManager.class);
        usuarioMensagemManager.setUsuarioEmpresaManager((UsuarioEmpresaManager) usuarioEmpresaManager.proxy());

        questionarioManager = new Mock(QuestionarioManager.class);
        colaboradorQuestionarioManager = new Mock(ColaboradorQuestionarioManager.class);
        colaboradorManager = new Mock(ColaboradorManager.class);
        avaliacaoDesempenhoManager = new Mock(AvaliacaoDesempenhoManager.class);
        solicitacaoManager = new Mock(SolicitacaoManager.class);
        gerenciadorComunicacaoManager = new Mock(GerenciadorComunicacaoManager.class);
        
        turmaDocumentoAnexoManager = new Mock(TurmaDocumentoAnexoManager.class);
        
        Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
		Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		
		MockSpringUtil.mocks.put("turmaDocumentoAnexoManager", turmaDocumentoAnexoManager);
	}

	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();
	}

	public void testListaUsuarioMensagem()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setId(1L);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setUsuario(usuario);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Mensagem mensagem = MensagemFactory.getEntity(1L);
		mensagem.setTipo(TipoMensagem.AVALIACAO_DESEMPENHO);

		UsuarioMensagem usuarioMensagem = new UsuarioMensagem();
		usuarioMensagem.setId(1L);
		usuarioMensagem.setUsuario(usuario);
		usuarioMensagem.setEmpresa(empresa);
		usuarioMensagem.setMensagem(mensagem);
		usuarioMensagem.setLida(false);

		Collection<UsuarioMensagem> usuarioMensagems = new ArrayList<UsuarioMensagem>();
		usuarioMensagems.add(usuarioMensagem);

		MockSpringUtil.mocks.put("questionarioManager", questionarioManager);
		MockSpringUtil.mocks.put("colaboradorQuestionarioManager", colaboradorQuestionarioManager);
		MockSpringUtil.mocks.put("colaboradorManager", colaboradorManager);
		MockSpringUtil.mocks.put("avaliacaoDesempenhoManager", avaliacaoDesempenhoManager);
		MockSpringUtil.mocks.put("solicitacaoManager", solicitacaoManager);
		MockSpringUtil.mocks.put("gerenciadorComunicacaoManager", gerenciadorComunicacaoManager);
		MockSecurityUtil.verifyRole = true;
		
		usuarioMensagemDao.expects(once()).method("listaUsuarioMensagem").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(usuarioMensagems));
		colaboradorQuestionarioManager.expects(once()).method("findQuestionarioByTurmaLiberadaPorUsuario").with(ANYTHING).will(returnValue(new ArrayList<ColaboradorQuestionario>()));
		questionarioManager.expects(once()).method("findQuestionarioPorUsuario").with(ANYTHING).will(returnValue(new ArrayList<Questionario>()));	
		avaliacaoDesempenhoManager.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<AvaliacaoDesempenho>()));
		solicitacaoManager.expects(once()).method("findSolicitacaoList").withAnyArguments().will(returnValue(new ArrayList<Solicitacao>()));
		gerenciadorComunicacaoManager.expects(once()).method("existeConfiguracaoParaCandidatosModuloExterno").withAnyArguments().will(returnValue(false));
		turmaDocumentoAnexoManager.expects(once()).method("findByColaborador").with(ANYTHING).will(returnValue(new ArrayList<TurmaDocumentoAnexo>()));
		
		Map<Character, CaixaMensagem> retorno = usuarioMensagemManager.listaMensagens(usuario.getId(), empresa.getId(), 1L);

		assertEquals(new TipoMensagem().size(), retorno.size());
	}

	public void testFindByIdProjection()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setId(1L);

		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Mensagem mensagem = MensagemFactory.getEntity(1L);

		UsuarioMensagem usuarioMensagem = new UsuarioMensagem();
		usuarioMensagem.setId(1L);
		usuarioMensagem.setUsuario(usuario);
		usuarioMensagem.setEmpresa(empresa);
		usuarioMensagem.setMensagem(mensagem);
		usuarioMensagem.setLida(false);

		usuarioMensagemDao.expects(once()).method("findByIdProjection").with(ANYTHING, ANYTHING).will(returnValue(usuarioMensagem));

		UsuarioMensagem retorno = usuarioMensagemManager.findByIdProjection(usuarioMensagem.getId(), empresa.getId());

		assertEquals(usuarioMensagem.getId(), retorno.getId());
	}

	public void testPossuiMensagemNaoLida()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setId(1L);

		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Mensagem mensagem = MensagemFactory.getEntity(1L);

		UsuarioMensagem usuarioMensagem = new UsuarioMensagem();
		usuarioMensagem.setId(1L);
		usuarioMensagem.setUsuario(usuario);
		usuarioMensagem.setEmpresa(empresa);
		usuarioMensagem.setMensagem(mensagem);
		usuarioMensagem.setLida(false);

		Collection<UsuarioMensagem> usuarioMensagems = new ArrayList<UsuarioMensagem>();
		usuarioMensagems.add(usuarioMensagem);

		usuarioMensagemDao.expects(once()).method("possuiMensagemNaoLida").with(ANYTHING,ANYTHING).will(returnValue(true));

		boolean retorno = usuarioMensagemManager.possuiMensagemNaoLida(usuario.getId(), empresa.getId());

		assertEquals(true, retorno);
	}

	public void testSalvaMensagem() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Mensagem mensagem = MensagemFactory.getEntity(1L);

		String [] usuariosCheck = {"1"};

		usuarioMensagemDao.expects(atLeastOnce()).method("save").with(ANYTHING);

		usuarioMensagemManager.salvaMensagem(empresa, mensagem, usuariosCheck);

	}

	public void testSalvaMensagemException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Mensagem mensagem = MensagemFactory.getEntity(1L);

		String [] usuariosCheck = null;

		Exception exception = null;

		try
		{
			usuarioMensagemManager.salvaMensagem(empresa, mensagem, usuariosCheck);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testSaveMensagemAndUsuarioMensagem() throws Exception
	{
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity(1L);

		Mensagem mensagem = MensagemFactory.getEntity(1L);
		mensagem.setTipo(TipoMensagem.INFO_FUNCIONAIS);

		Collection<UsuarioEmpresa> usuarioEmpresas = new ArrayList<UsuarioEmpresa>();
		usuarioEmpresas.add(usuarioEmpresa);

		mensagemManager.expects(once()).method("save").with(ANYTHING).will(returnValue(mensagem));
		usuarioMensagemDao.expects(once()).method("save").with(ANYTHING);

		usuarioMensagemManager.saveMensagemAndUsuarioMensagem("Msg", "Chico Bagulhoso", "link", usuarioEmpresas, null, TipoMensagem.INFO_FUNCIONAIS, null, null);
	}

	public void testSaveMensagemAndUsuarioMensagemRespAreaOrganizacional() throws Exception
	{
		Usuario usuario = UsuarioFactory.getEntity(1L);
		UsuarioEmpresa usuarioEmpresa = UsuarioEmpresaFactory.getEntity(1L);
		usuarioEmpresa.setUsuario(usuario);

		Usuario usuario2 = UsuarioFactory.getEntity(2L);
		UsuarioEmpresa usuarioEmpresa2 = UsuarioEmpresaFactory.getEntity(2L);
		usuarioEmpresa2.setUsuario(usuario2);
				
		Mensagem mensagem = MensagemFactory.getEntity(1L);
			
		Collection<UsuarioEmpresa> usuariosResponsaveisAreaOrganizacionais = new ArrayList<UsuarioEmpresa>();
		usuariosResponsaveisAreaOrganizacionais.add(usuarioEmpresa);
		usuariosResponsaveisAreaOrganizacionais.add(usuarioEmpresa2);
		
		usuarioEmpresaManager.expects(once()).method("findUsuarioResponsavelAreaOrganizacional").with(ANYTHING).will(returnValue(usuariosResponsaveisAreaOrganizacionais));
		mensagemManager.expects(once()).method("save").with(ANYTHING).will(returnValue(mensagem));
		usuarioMensagemDao.expects(once()).method("save").with(ANYTHING);
		usuarioMensagemDao.expects(once()).method("save").with(ANYTHING);
		
		usuarioMensagemManager.saveMensagemAndUsuarioMensagemRespAreaOrganizacional("Msg", "Chico Bagulhoso", "link", new ArrayList<Long>(), TipoMensagem.AVALIACAO_DESEMPENHO, null);
	}
	
	public void testDeleteUm() throws Exception
	{
		UsuarioMensagem usuarioMensagem = UsuarioMensagemFactory.getEntity(1L);
		usuarioMensagemDao.expects(once()).method("remove").with(ANYTHING);
		usuarioMensagemManager.delete(usuarioMensagem, null);
	}

	public void testDeleteVarios() throws Exception
	{
		Long[] usuarioMensagemIds = new Long[]{1L, 2L};
		usuarioMensagemDao.expects(once()).method("remove").with(ANYTHING);
		usuarioMensagemManager.delete(null, usuarioMensagemIds);
	}

	public void testGetAnteriorOuProximo()
	{
		usuarioMensagemDao.expects(once()).method("countMensagens").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(2));
		usuarioMensagemDao.expects(once()).method("findAnteriorOuProximo").with(new Constraint[] {ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(2L));
		assertEquals(new Long(2), usuarioMensagemManager.getAnteriorOuProximo(1L, 1L, 1L, 'P', null));
	}
}
