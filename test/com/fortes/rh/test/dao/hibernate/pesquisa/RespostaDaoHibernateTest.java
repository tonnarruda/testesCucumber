package com.fortes.rh.test.dao.hibernate.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.pesquisa.PerguntaDao;
import com.fortes.rh.dao.pesquisa.RespostaDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.RespostaFactory;

public class RespostaDaoHibernateTest extends GenericDaoHibernateTest<Resposta>
{
	private RespostaDao respostaDao;
	private PerguntaDao perguntaDao;
	private AvaliacaoDao avaliacaoDao;
	private EmpresaDao empresaDao;
//	private QuestionarioDao questionarioDao;

	public Resposta getEntity()
	{
		Resposta resposta = new Resposta();

		resposta.setId(null);
		resposta.setOrdem(1);
		resposta.setPergunta(null);
		resposta.setTexto("resposta");

		return resposta;
	}

	public GenericDao<Resposta> getGenericDao()
	{
		return respostaDao;
	}

	public void testFindInPerguntaIds()
	{
		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1 = perguntaDao.save(pergunta1);

		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2 = perguntaDao.save(pergunta2);

		Resposta resposta1 = RespostaFactory.getEntity();
		resposta1.setPergunta(pergunta1);
		resposta1 = respostaDao.save(resposta1);

		Resposta resposta2 = RespostaFactory.getEntity();
		resposta2.setPergunta(pergunta1);
		resposta2 = respostaDao.save(resposta2);

		Resposta resposta3 = RespostaFactory.getEntity();
		resposta3.setPergunta(pergunta2);
		resposta3 = respostaDao.save(resposta3);

		Collection<Resposta> retorno = respostaDao.findInPerguntaIds(new Long[]{pergunta1.getId()});

		assertEquals(2, retorno.size());
	}

	public void testFindByPergunta()
	{
		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1 = perguntaDao.save(pergunta1);

		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2 = perguntaDao.save(pergunta2);

		Resposta resposta1 = RespostaFactory.getEntity();
		resposta1.setPergunta(pergunta1);
		resposta1 = respostaDao.save(resposta1);

		Resposta resposta2 = RespostaFactory.getEntity();
		resposta2.setPergunta(pergunta1);
		resposta2 = respostaDao.save(resposta2);

		Resposta resposta3 = RespostaFactory.getEntity();
		resposta3.setPergunta(pergunta2);
		resposta3 = respostaDao.save(resposta3);

		Collection<Resposta> retorno = respostaDao.findByPergunta(pergunta1.getId());

		assertEquals(2, retorno.size());
	}

	public void testRemoverRespostasDasPerguntas()
	{
		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1 = perguntaDao.save(pergunta1);
		
		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2 = perguntaDao.save(pergunta2);
		
		Resposta resposta1 = RespostaFactory.getEntity();
		resposta1.setPergunta(pergunta1);
		resposta1 = respostaDao.save(resposta1);
		
		Resposta resposta2 = RespostaFactory.getEntity();
		resposta2.setPergunta(pergunta1);
		resposta2 = respostaDao.save(resposta2);
		
		Resposta resposta3 = RespostaFactory.getEntity();
		resposta3.setPergunta(pergunta2);
		resposta3 = respostaDao.save(resposta3);
		
		Collection<Long> perguntaIds = new ArrayList<Long>();
		perguntaIds.add(pergunta1.getId());
		perguntaIds.add(pergunta2.getId());
		
		assertEquals(3, respostaDao.findInPerguntaIds(new Long[]{pergunta1.getId(), pergunta2.getId()}).size());
		respostaDao.removerRespostasDasPerguntas(perguntaIds);		
		assertEquals(0, respostaDao.findInPerguntaIds(new Long[]{pergunta1.getId(), pergunta2.getId()}).size());
	}
	
	public void testRemoverRespostasDasPerguntasDaAvaliacao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Avaliacao avaliacao1 = AvaliacaoFactory.getEntity();
		avaliacao1.setAtivo(true);
		avaliacao1.setEmpresa(empresa);
		avaliacao1.setTipoModeloAvaliacao(TipoModeloAvaliacao.AVALIACAO_DESEMPENHO);
		avaliacao1.setTitulo("Avaliacao 1");
		avaliacaoDao.save(avaliacao1);
		avaliacao1 = avaliacaoDao.save(avaliacao1);
		
		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity();
		avaliacao2.setAtivo(true);
		avaliacao2.setEmpresa(empresa);
		avaliacao2.setTipoModeloAvaliacao(TipoModeloAvaliacao.AVALIACAO_DESEMPENHO);
		avaliacao2.setTitulo("Avaliacao 2");
		avaliacaoDao.save(avaliacao2);
		avaliacao2 = avaliacaoDao.save(avaliacao2);
		
		Pergunta pergunta1 = PerguntaFactory.getEntity();
		pergunta1.setAvaliacao(avaliacao1);
		pergunta1 = perguntaDao.save(pergunta1);
		
		Pergunta pergunta2 = PerguntaFactory.getEntity();
		pergunta2.setAvaliacao(avaliacao1);
		pergunta2 = perguntaDao.save(pergunta2);
		
		Pergunta pergunta3 = PerguntaFactory.getEntity();
		pergunta3.setAvaliacao(avaliacao2);
		pergunta3 = perguntaDao.save(pergunta3);
		
		Resposta resposta1 = RespostaFactory.getEntity();
		resposta1.setPergunta(pergunta1);
		resposta1 = respostaDao.save(resposta1);
		
		Resposta resposta2 = RespostaFactory.getEntity();
		resposta2.setPergunta(pergunta1);
		resposta2 = respostaDao.save(resposta2);
		
		Resposta resposta3 = RespostaFactory.getEntity();
		resposta3.setPergunta(pergunta2);
		resposta3 = respostaDao.save(resposta3);
		
		Resposta resposta4 = RespostaFactory.getEntity();
		resposta4.setPergunta(pergunta3);
		resposta3 = respostaDao.save(resposta4);

		assertEquals(3, respostaDao.findInPerguntaIds(new Long[]{pergunta1.getId(), pergunta2.getId()}).size());
		assertEquals(4, respostaDao.findInPerguntaIds(new Long[]{pergunta1.getId(), pergunta2.getId(), pergunta3.getId()}).size());
		respostaDao.removerRespostasDasPerguntasDaAvaliacao(avaliacao1.getId());
		assertEquals(0, respostaDao.findInPerguntaIds(new Long[]{pergunta1.getId(), pergunta2.getId()}).size());
		assertEquals(1, respostaDao.findInPerguntaIds(new Long[]{pergunta1.getId(), pergunta2.getId(), pergunta3.getId()}).size());
	}

	public void setRespostaDao(RespostaDao respostaDao)
	{
		this.respostaDao = respostaDao;
	}

	public void setPerguntaDao(PerguntaDao perguntaDao)
	{
		this.perguntaDao = perguntaDao;
	}

	public void setAvaliacaoDao(AvaliacaoDao avaliacaoDao) {
		this.avaliacaoDao = avaliacaoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

}