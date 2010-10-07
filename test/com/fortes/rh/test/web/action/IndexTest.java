package com.fortes.rh.test.web.action;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.business.pesquisa.QuestionarioManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.model.geral.PendenciaAC;
import com.fortes.rh.model.geral.UsuarioMensagem;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.MensagemFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.PesquisaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.web.action.Index;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;

public class IndexTest extends MockObjectTestCase
{
	Index index;
	Mock questionarioManager;
	Mock colaboradorManager;
	Mock usuarioMensagemManager;
	Mock historicoColaboradorManager;
	Mock faixaSalarialHistoricoManager;

	protected void setUp() throws Exception
	{
		index = new Index();

		questionarioManager = new Mock(QuestionarioManager.class);
		index.setQuestionarioManager((QuestionarioManager) questionarioManager.proxy());

		colaboradorManager = new Mock(ColaboradorManager.class);
		index.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());

		usuarioMensagemManager = new Mock(UsuarioMensagemManager.class);
		index.setUsuarioMensagemManager((UsuarioMensagemManager)usuarioMensagemManager.proxy());

		historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);
		index.setHistoricoColaboradorManager((HistoricoColaboradorManager) historicoColaboradorManager.proxy());

		faixaSalarialHistoricoManager = new Mock(FaixaSalarialHistoricoManager.class);
		index.setFaixaSalarialHistoricoManager((FaixaSalarialHistoricoManager) faixaSalarialHistoricoManager.proxy());

		Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	public void testIndex(){

		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setId(1L);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setUsuario(usuario);

		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(false);

		Questionario questionario = QuestionarioFactory.getEntity(1L);
		questionario.setEmpresa(empresa);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity(1L);
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);

		Pesquisa pesquisa1 = PesquisaFactory.getEntity(1L);
		pesquisa1.setQuestionario(questionario);

		Pesquisa pesquisa2 = PesquisaFactory.getEntity(1L);
		pesquisa2.setQuestionario(questionario);

		Collection<Pesquisa> pesquisas = new ArrayList<Pesquisa>();
		pesquisas.add(pesquisa1);
		pesquisas.add(pesquisa2);

		questionarioManager.expects(once()).method("findQuestionarioPorUsuario").with(ANYTHING).will(returnValue(pesquisas));

		colaboradorManager.expects(once()).method("findByUsuario").with(ANYTHING, ANYTHING).will(returnValue(colaborador));

		Mensagem mensagem = MensagemFactory.getEntity(1L);

		UsuarioMensagem usuarioMensagem = new UsuarioMensagem();
		usuarioMensagem.setId(1L);
		usuarioMensagem.setEmpresa(empresa);
		usuarioMensagem.setUsuario(usuario);
		usuarioMensagem.setLida(false);
		usuarioMensagem.setMensagem(mensagem);

		Collection<UsuarioMensagem> usuarioMensagems = new ArrayList<UsuarioMensagem>();
		usuarioMensagems.add(usuarioMensagem);

		index.setActionMsg("Teste");

		usuarioMensagemManager.expects(once()).method("listaUsuarioMensagem").with(ANYTHING, ANYTHING).will(returnValue(usuarioMensagems));

		usuarioMensagemManager.expects(once()).method("possuiMensagemNaoLida").with(ANYTHING,ANYTHING).will(returnValue(true));

		Collection<PendenciaAC> pendenciaACs = new ArrayList<PendenciaAC>();

		historicoColaboradorManager.expects(once()).method("findPendenciasByHistoricoColaborador").will(returnValue(pendenciaACs));

		faixaSalarialHistoricoManager.expects(once()).method("findPendenciasByFaixaSalarialHistorico").will(returnValue(pendenciaACs));

		assertEquals("success", index.index());

	}

	public void testGetSet(){
		index.setColaborador(null);
		index.getColaborador();
		index.setQuestionarios(null);
		index.getQuestionarios();
		index.setUsuarioId(null);
		index.getUsuarioId();
		index.setPesquisasAtrasadas(null);
		index.getPesquisasAtrasadas();
		index.setPesquisasColaborador(null);
		index.getPesquisasColaborador();
	}

	public void setColaboradorManager(Mock colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setPesquisaManager(Mock pesquisaManager)
	{
		this.questionarioManager = pesquisaManager;
	}

}