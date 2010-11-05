package com.fortes.rh.test.dao.hibernate.pesquisa;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.dao.pesquisa.QuestionarioDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("deprecation")
public class QuestionarioDaoHibernateTest extends GenericDaoHibernateTest<Questionario>
{
	private QuestionarioDao questionarioDao;
	private EmpresaDao empresaDao;
	private UsuarioDao usuarioDao;
	private ColaboradorDao colaboradorDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;

	public Questionario getEntity()
	{
		Questionario questionario = new Questionario();

		questionario.setId(null);
		questionario.setCabecalho("cabecalho");

		return questionario;
	}

	public GenericDao<Questionario> getGenericDao()
	{
		return questionarioDao;
	}

	public void setQuestionarioDao(QuestionarioDao questionarioDao)
	{
		this.questionarioDao = questionarioDao;
	}

	private Questionario preparaQuestionario()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Questionario questionario = QuestionarioFactory.getEntity();
		questionario.setEmpresa(empresa);
		return questionario;
	}

	public void testFindByIdProjection()
	{
		Questionario questionario = preparaQuestionario();
		questionario = questionarioDao.save(questionario);

		Questionario questionarioRetorno = questionarioDao.findByIdProjection(questionario.getId());

		assertEquals(questionario, questionarioRetorno);
	}

	public void testChecarQuestionarioLiberado()
	{
		Questionario questionario = preparaQuestionario();
		questionario.setLiberado(true);
		questionario = questionarioDao.save(questionario);

		assertTrue(questionarioDao.checarQuestionarioLiberado(questionario));
	}

	public void testChecarQuestionarioLiberadoFalse()
	{
		Questionario questionario = preparaQuestionario();
		questionario.setLiberado(false);
		questionario = questionarioDao.save(questionario);

		assertFalse(questionarioDao.checarQuestionarioLiberado(questionario));
	}

	public void testAplicarPorAspecto()
	{
		Questionario questionario = preparaQuestionario();
		questionario.setAplicarPorAspecto(false);
		questionario = questionarioDao.save(questionario);

		questionarioDao.aplicarPorAspecto(questionario.getId(), true);

		Questionario questionarioRetorno = questionarioDao.findByIdProjection(questionario.getId());
		assertTrue(questionarioRetorno.isAplicarPorAspecto());
	}

	public void testLiberarQuestionario()
	{
		Questionario questionario = preparaQuestionario();
		questionario.setLiberado(false);
		questionario = questionarioDao.save(questionario);

		questionarioDao.liberarQuestionario(questionario.getId());

		Questionario questionarioRetorno = questionarioDao.findByIdProjection(questionario.getId());
		assertTrue(questionarioRetorno.isLiberado());
	}

	public void testFindQuestionarioNaoLiberados()
	{
		Date dataInicio = DateUtil.criarAnoMesDia(1900, 01, 02);

		Questionario questionario = preparaQuestionario();
		questionario.setLiberado(false);
		questionario.setDataInicio(dataInicio);
		questionario = questionarioDao.save(questionario);

		Collection<Questionario> questionarios = questionarioDao.findQuestionarioNaoLiberados(dataInicio);
		assertEquals(questionario, questionarios.toArray()[0]);
	}

	public void testFindQuestionarioPorUsuario()
	{
		Date dataAnterior = DateUtil.criarAnoMesDia(1900, 01, 01);
		Date dataPosterior = new Date();

		Questionario questionario = preparaQuestionario();
		questionario.setLiberado(true);
		questionario.setDataInicio(dataAnterior);
		questionario.setDataFim(dataPosterior);
		questionario = questionarioDao.save(questionario);

		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setUsuario(usuario);
		colaborador = colaboradorDao.save(colaborador);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionario.setRespondida(false);
		colaboradorQuestionario = colaboradorQuestionarioDao.save(colaboradorQuestionario);

		Collection<Questionario> questionarios = questionarioDao.findQuestionarioPorUsuario(usuario.getId());

		assertEquals(questionario, questionarios.toArray()[0]);
	}
	
	public void testFindQuestionario()
	{
		Date dataAnterior = DateUtil.criarAnoMesDia(1900, 01, 01);
		Date dataPosterior = new Date();
		
		Questionario questionario = preparaQuestionario();
		questionario.setLiberado(true);
		questionario.setDataInicio(dataAnterior);
		questionario.setDataFim(dataPosterior);
		questionario = questionarioDao.save(questionario);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);
		
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setQuestionario(questionario);
		colaboradorQuestionario.setRespondida(false);
		colaboradorQuestionario = colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		Collection<Questionario> questionarios = questionarioDao.findQuestionario(colaborador.getId());
		
		assertEquals(questionario, questionarios.toArray()[0]);
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setColaboradorQuestionarioDao(ColaboradorQuestionarioDao colaboradorQuestionarioDao)
	{
		this.colaboradorQuestionarioDao = colaboradorQuestionarioDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao)
	{
		this.usuarioDao = usuarioDao;
	}

}