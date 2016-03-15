package com.fortes.rh.test.web.action.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.pesquisa.PesquisaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.PesquisaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.RespostaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.pesquisa.PesquisaListAction;

public class PesquisaListActionTest extends MockObjectTestCase
{
	private PesquisaListAction pesquisaListAction;
	private Mock pesquisaManager;

	protected void setUp() throws Exception
	{
		super.setUp();

		pesquisaListAction = new PesquisaListAction();

		pesquisaManager = new Mock(PesquisaManager.class);
		pesquisaListAction.setPesquisaManager(( PesquisaManager ) pesquisaManager.proxy());

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	protected void tearDown() throws Exception
	{
		pesquisaListAction = null;
		pesquisaManager = null;
        MockSecurityUtil.verifyRole = false;

		super.tearDown();
	}

	public void testExecute() throws Exception
	{
		assertEquals("Teste de retorno da actio com sucesso", "success", pesquisaListAction.execute());
	}

	public void testClonarPesquisa() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Questionario questionario = QuestionarioFactory.getEntity(1L);
		questionario.setEmpresa(empresa);

		Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
		pergunta1.setQuestionario(questionario);

		Resposta resposta1 = RespostaFactory.getEntity(1L);
		resposta1.setPergunta(pergunta1);

		Resposta resposta2 = RespostaFactory.getEntity(2L);
		resposta2.setPergunta(pergunta1);

		Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
		pergunta2.setQuestionario(questionario);

		Resposta resposta3 = RespostaFactory.getEntity(3L);
		resposta3.setPergunta(pergunta2);

		Resposta resposta4 = RespostaFactory.getEntity(4L);
		resposta4.setPergunta(pergunta2);

		Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
		pesquisa.setQuestionario(questionario);

		Collection<Pesquisa> pesquisas = new ArrayList<Pesquisa>();
		pesquisas.add(pesquisa);

		pesquisaListAction.setPesquisa(pesquisa);

		pesquisaManager.expects(once()).method("clonarPesquisa").with(ANYTHING, ANYTHING).will(returnValue(pesquisa));

		assertEquals("Teste de sucesso do clonar pesquisa", "success", pesquisaListAction.clonarPesquisa());
	}

	public void testDelete() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Questionario questionario = QuestionarioFactory.getEntity(1L);
		questionario.setEmpresa(empresa);

		Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
		pesquisa.setQuestionario(questionario);

		Collection<Pesquisa> pesquisas = new ArrayList<Pesquisa>();
		pesquisas.add(pesquisa);

		pesquisaListAction.setPesquisa(pesquisa);
		pesquisaListAction.setEmpresaSistema(empresa);
		pesquisaListAction.setPage(1);
		pesquisaListAction.setTotalSize(1);

		pesquisaManager.expects(once()).method("delete").with(eq(pesquisa.getId()), eq(empresa.getId()));

		assertEquals("success", pesquisaListAction.delete());
	}

	public void testGetSet()
	{
		pesquisaListAction.getPesquisa();

		pesquisaListAction.getPesquisas();

		pesquisaListAction.getTipoPergunta();
	}

}
