package com.fortes.rh.test.dao.hibernate.avaliacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.pesquisa.PerguntaDao;
import com.fortes.rh.dao.pesquisa.RespostaDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.TipoPergunta;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Resposta;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class AvaliacaoDaoHibernateTest extends GenericDaoHibernateTest<Avaliacao>
{
	private AvaliacaoDao avaliacaoDao;
	private AvaliacaoDesempenhoDao avaliacaoDesempenhoDao;
	private EmpresaDao empresaDao;
	private RespostaDao respostaDao;
	private PerguntaDao perguntaDao;
	private PeriodoExperienciaDao periodoExperienciaDao;
	

	@Override
	public Avaliacao getEntity()
	{
		return AvaliacaoFactory.getEntity();
	}

	@Override
	public GenericDao<Avaliacao> getGenericDao()
	{
		return avaliacaoDao;
	}

	public void setAvaliacaoDao(AvaliacaoDao avaliacaoDao)
	{
		this.avaliacaoDao = avaliacaoDao;
	}
	
	public void testGetCount()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		preparaTesteFindAllSelectEGetCount(empresa);
		
		assertEquals(new Integer(1), avaliacaoDao.getCount(empresa.getId(), true, TipoModeloAvaliacao.SOLICITACAO, null));
		assertEquals(new Integer(1), avaliacaoDao.getCount(empresa.getId(), false, TipoModeloAvaliacao.SOLICITACAO, null));
		assertEquals(new Integer(2), avaliacaoDao.getCount(empresa.getId(), null, TipoModeloAvaliacao.SOLICITACAO, null));
		assertEquals(new Integer(3), avaliacaoDao.getCount(empresa.getId(), true, TipoModeloAvaliacao.DESEMPENHO, null));
		assertEquals(new Integer(1), avaliacaoDao.getCount(empresa.getId(), null, TipoModeloAvaliacao.DESEMPENHO, "60"));
		assertEquals(new Integer(1), avaliacaoDao.getCount(empresa.getId(), true, TipoModeloAvaliacao.AVALIACAO_DESEMPENHO, null));
		assertTrue(avaliacaoDao.getCount(empresa.getId(), true, TipoModeloAvaliacao.AVALIACAO_ALUNO, null) >= 2);
		assertEquals(new Integer(2), avaliacaoDao.getCount(empresa.getId(), true, TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA, null));
	}
	
	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		preparaTesteFindAllSelectEGetCount(empresa);
		
		Collection<Avaliacao> a = avaliacaoDao.findAllSelect(null, null, empresa.getId(), true, TipoModeloAvaliacao.AVALIACAO_ALUNO, null);
		
				
		assertEquals(1, avaliacaoDao.findAllSelect(null, null, empresa.getId(), true, TipoModeloAvaliacao.SOLICITACAO, null).size());
		assertEquals(1, avaliacaoDao.findAllSelect(null, null, empresa.getId(), false, TipoModeloAvaliacao.SOLICITACAO, null).size());
		assertEquals(3, avaliacaoDao.findAllSelect(null, null, empresa.getId(), true, TipoModeloAvaliacao.DESEMPENHO, null).size());
		assertEquals(1, avaliacaoDao.findAllSelect(null, null, empresa.getId(), true, TipoModeloAvaliacao.DESEMPENHO, "60").size());
		assertEquals(1, avaliacaoDao.findAllSelect(null, null, empresa.getId(), true, TipoModeloAvaliacao.AVALIACAO_DESEMPENHO, null).size());
		assertTrue(avaliacaoDao.findAllSelect(null, null, empresa.getId(), true, TipoModeloAvaliacao.AVALIACAO_ALUNO, null).size() >= 2);
		assertEquals(2, avaliacaoDao.findAllSelect(null, null, empresa.getId(), true, TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA, null).size());
	}
	
	private void preparaTesteFindAllSelectEGetCount(Empresa empresa)
	{
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacao.setAtivo(true);
		avaliacao.setEmpresa(empresa);
		avaliacao.setTipoModeloAvaliacao(TipoModeloAvaliacao.SOLICITACAO);
		avaliacao.setTitulo("30 dias");
		avaliacaoDao.save(avaliacao);

		Avaliacao avaliacao1 = AvaliacaoFactory.getEntity();
		avaliacao1.setAtivo(false);
		avaliacao1.setEmpresa(empresa);
		avaliacao1.setTipoModeloAvaliacao(TipoModeloAvaliacao.SOLICITACAO);
		avaliacao1.setTitulo("45 dias");
		avaliacaoDao.save(avaliacao1);
		
		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity();
		avaliacao2.setAtivo(true);
		avaliacao2.setEmpresa(empresa);
		avaliacao2.setTipoModeloAvaliacao(TipoModeloAvaliacao.DESEMPENHO);
		avaliacao2.setTitulo("60 dias");
		avaliacaoDao.save(avaliacao2);

		Avaliacao avaliacao3 = AvaliacaoFactory.getEntity();
		avaliacao3.setAtivo(true);
		avaliacao3.setEmpresa(empresa);
		avaliacao3.setTipoModeloAvaliacao(TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA);
		avaliacao3.setTitulo("100 dias");
		avaliacaoDao.save(avaliacao3);

		Avaliacao avaliacao4 = AvaliacaoFactory.getEntity();
		avaliacao4.setAtivo(true);
		avaliacao4.setEmpresa(empresa);
		avaliacao4.setTipoModeloAvaliacao(TipoModeloAvaliacao.AVALIACAO_DESEMPENHO);
		avaliacao4.setTitulo("600 dias");
		avaliacaoDao.save(avaliacao4);
		
		Avaliacao avaliacao5 = AvaliacaoFactory.getEntity();
		avaliacao5.setAtivo(true);
		avaliacao5.setEmpresa(empresa);
		avaliacao5.setTipoModeloAvaliacao(TipoModeloAvaliacao.ACOMPANHAMENTO_EXPERIENCIA);
		avaliacao5.setTitulo("50 dias");
		avaliacaoDao.save(avaliacao5);
		
		Avaliacao avaliacao6 = AvaliacaoFactory.getEntity();
		avaliacao6.setAtivo(true);
		avaliacao6.setEmpresa(empresa);
		avaliacao6.setTipoModeloAvaliacao(TipoModeloAvaliacao.AVALIACAO_ALUNO);
		avaliacao6.setTitulo("Tipo aluno");
		avaliacaoDao.save(avaliacao6);
		
		Avaliacao avaliacao7 = AvaliacaoFactory.getEntity();
		avaliacao7.setAtivo(true);
		avaliacao7.setEmpresa(empresa);
		avaliacao7.setTipoModeloAvaliacao(TipoModeloAvaliacao.AVALIACAO_ALUNO);
		avaliacao7.setTitulo("Tipo aluno");
		avaliacaoDao.save(avaliacao7);	
	}

	public void testFindAllSelectComAvaliacaoDesempenho()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacao.setAtivo(true);
		avaliacao.setEmpresa(empresa);
		avaliacao.setTitulo("Modelo");
		avaliacaoDao.save(avaliacao);

		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho.setAvaliacao(avaliacao);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);

		AvaliacaoDesempenho avaliacaoDesempenho2 = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho2.setAvaliacao(avaliacao);
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho2);

		Collection<Avaliacao> avaliacaos = avaliacaoDao.findAllSelectComAvaliacaoDesempenho(empresa.getId(), true);
		assertEquals(1, avaliacaos.size());

		Avaliacao resp = (Avaliacao) avaliacaos.toArray()[0];
		assertEquals("Modelo", resp.getTitulo());
	}
	
	public void testFindPeriodoExperienciaIsNull()
	{
		PeriodoExperiencia periodoExperiencia = PeriodoExperienciaFactory.getEntity();
		periodoExperienciaDao.save(periodoExperiencia);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Avaliacao avaliacaoOutroTipo = AvaliacaoFactory.getEntity();
		avaliacaoOutroTipo.setAtivo(true);
		avaliacaoOutroTipo.setEmpresa(empresa);
		avaliacaoOutroTipo.setTipoModeloAvaliacao(TipoModeloAvaliacao.SOLICITACAO);
		avaliacaoDao.save(avaliacaoOutroTipo);
		
		Avaliacao avaliacaoAtivaSemPeriodo = AvaliacaoFactory.getEntity();
		avaliacaoAtivaSemPeriodo.setAtivo(true);
		avaliacaoAtivaSemPeriodo.setEmpresa(empresa);
		avaliacaoAtivaSemPeriodo.setTipoModeloAvaliacao(TipoModeloAvaliacao.DESEMPENHO);
		avaliacaoDao.save(avaliacaoAtivaSemPeriodo);
		
		Avaliacao avaliacaoComPeriodo = AvaliacaoFactory.getEntity();
		avaliacaoComPeriodo.setAtivo(true);
		avaliacaoComPeriodo.setEmpresa(empresa);
		avaliacaoComPeriodo.setTipoModeloAvaliacao(TipoModeloAvaliacao.DESEMPENHO);
		avaliacaoComPeriodo.setPeriodoExperiencia(periodoExperiencia);
		avaliacaoDao.save(avaliacaoComPeriodo);
		
		Avaliacao avaliacaoInativa = AvaliacaoFactory.getEntity();
		avaliacaoInativa.setAtivo(false);
		avaliacaoInativa.setEmpresa(empresa);
		avaliacaoInativa.setTipoModeloAvaliacao(TipoModeloAvaliacao.DESEMPENHO);
		avaliacaoDao.save(avaliacaoInativa);
		
		
		assertEquals(1, avaliacaoDao.findPeriodoExperienciaIsNull(TipoModeloAvaliacao.DESEMPENHO, empresa.getId()).size());
	}
	
	public void testGetPontuacaoMaximaDaPerformance()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacao.setEmpresa(empresa);
		
		avaliacaoDao.save(avaliacao);
		
		Pergunta perguntaPorNota1 = new Pergunta();
		perguntaPorNota1.setTipo(TipoPergunta.NOTA);
		perguntaPorNota1.setTexto("Relacionamento com colegas");
		perguntaPorNota1.setOrdem(1);
		perguntaPorNota1.setPeso(3);
		perguntaPorNota1.setNotaMinima(1);
		perguntaPorNota1.setNotaMaxima(5);
		perguntaPorNota1.setAvaliacao(avaliacao);
		perguntaDao.save(perguntaPorNota1);
		
		Pergunta perguntaObjetiva1 = new Pergunta();
		perguntaObjetiva1.setTipo(TipoPergunta.OBJETIVA);
		perguntaObjetiva1.setTexto("Habilidade com ferramentas de trabalho");
		perguntaObjetiva1.setOrdem(2);
		perguntaObjetiva1.setPeso(3);
		perguntaObjetiva1.setAvaliacao(avaliacao);
		perguntaDao.save(perguntaObjetiva1);
		
		Pergunta perguntaObjetiva2 = new Pergunta();
		perguntaObjetiva2.setTipo(TipoPergunta.OBJETIVA);
		perguntaObjetiva2.setTexto("Zelo pelo material de trabalho");
		perguntaObjetiva2.setOrdem(3);
		perguntaObjetiva2.setPeso(3);
		perguntaObjetiva2.setAvaliacao(avaliacao);
		perguntaDao.save(perguntaObjetiva2);
		
		Pergunta perguntaPorNota2 = new Pergunta();
		perguntaPorNota2.setTipo(TipoPergunta.NOTA);
		perguntaPorNota2.setTexto("Relacionamento com chefia");
		perguntaPorNota2.setOrdem(4);
		perguntaPorNota2.setPeso(1);
		perguntaPorNota2.setNotaMinima(1);
		perguntaPorNota2.setNotaMaxima(10);
		perguntaPorNota2.setAvaliacao(avaliacao);
		perguntaDao.save(perguntaPorNota2);
		
		Pergunta perguntaSubjetiva = new Pergunta();
		perguntaSubjetiva.setTipo(TipoPergunta.SUBJETIVA);
		perguntaSubjetiva.setTexto("Dê sua opinião.");
		perguntaSubjetiva.setOrdem(5);
		perguntaSubjetiva.setAvaliacao(avaliacao);
		perguntaDao.save(perguntaSubjetiva);
		
		Pergunta perguntaMultiplaEscolha = new Pergunta();
		perguntaMultiplaEscolha.setTipo(TipoPergunta.MULTIPLA_ESCOLHA);
		perguntaMultiplaEscolha.setTexto("Habilidades");
		perguntaMultiplaEscolha.setOrdem(6);
		perguntaMultiplaEscolha.setPeso(1);
		perguntaMultiplaEscolha.setAvaliacao(avaliacao);
		perguntaDao.save(perguntaMultiplaEscolha);
		
		Resposta resposta1PerguntaObjetiva1 = new Resposta();
		resposta1PerguntaObjetiva1.setPergunta(perguntaObjetiva1);
		resposta1PerguntaObjetiva1.setTexto("Não se aplica");
		resposta1PerguntaObjetiva1.setPeso(5);
		respostaDao.save(resposta1PerguntaObjetiva1);
		
		Resposta resposta2PerguntaObjetiva1 = new Resposta();
		resposta2PerguntaObjetiva1.setPergunta(perguntaObjetiva1);
		resposta2PerguntaObjetiva1.setTexto("bom");
		resposta2PerguntaObjetiva1.setPeso(2);
		respostaDao.save(resposta2PerguntaObjetiva1);
		
		Resposta resposta3PerguntaObjetiva1 = new Resposta();
		resposta3PerguntaObjetiva1.setPergunta(perguntaObjetiva1);
		resposta3PerguntaObjetiva1.setTexto("otimo");
		resposta3PerguntaObjetiva1.setPeso(5);
		respostaDao.save(resposta3PerguntaObjetiva1);
		
		Resposta resposta1PerguntaObjetiva2 = new Resposta();
		resposta1PerguntaObjetiva2.setPergunta(perguntaObjetiva2);
		resposta1PerguntaObjetiva2.setTexto("ruim");
		resposta1PerguntaObjetiva2.setPeso(1);
		respostaDao.save(resposta1PerguntaObjetiva2);
		
		Resposta resposta2PerguntaObjetiva2 = new Resposta();
		resposta2PerguntaObjetiva2.setPergunta(perguntaObjetiva2);
		resposta2PerguntaObjetiva2.setTexto("bom");
		resposta2PerguntaObjetiva2.setPeso(2);
		respostaDao.save(resposta2PerguntaObjetiva2);
		
		Resposta resposta3PerguntaObjetiva2 = new Resposta();
		resposta3PerguntaObjetiva2.setPergunta(perguntaObjetiva2);
		resposta3PerguntaObjetiva2.setTexto("otimo");
		resposta3PerguntaObjetiva2.setPeso(5);
		respostaDao.save(resposta3PerguntaObjetiva2);
		
		//
		
		Resposta resposta1PerguntaMultipla = new Resposta();
		resposta1PerguntaMultipla.setPergunta(perguntaMultiplaEscolha);
		resposta1PerguntaMultipla.setTexto("Gerenciais");
		resposta1PerguntaMultipla.setPeso(2);
		respostaDao.save(resposta1PerguntaMultipla);
		
		Resposta resposta2PerguntaMultipla = new Resposta();
		resposta2PerguntaMultipla.setPergunta(perguntaMultiplaEscolha);
		resposta2PerguntaMultipla.setTexto("Técnicas");
		resposta2PerguntaMultipla.setPeso(2);
		respostaDao.save(resposta2PerguntaMultipla);
		
		Resposta resposta3PerguntaMultipla = new Resposta();
		resposta3PerguntaMultipla.setPergunta(perguntaMultiplaEscolha);
		resposta3PerguntaMultipla.setTexto("Experiência");
		resposta3PerguntaMultipla.setPeso(2);
		respostaDao.save(resposta3PerguntaMultipla);
		
		//migué consulta SQL
		avaliacaoDao.find(avaliacao);
		
		assertEquals(61, avaliacaoDao.getPontuacaoMaximaDaPerformance(avaliacao.getId(), null).intValue());
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public void setRespostaDao(RespostaDao respostaDao) {
		this.respostaDao = respostaDao;
	}

	public void setPerguntaDao(PerguntaDao perguntaDao) {
		this.perguntaDao = perguntaDao;
	}

	public void setPeriodoExperienciaDao(PeriodoExperienciaDao periodoExperienciaDao) {
		this.periodoExperienciaDao = periodoExperienciaDao;
	}

	public void setAvaliacaoDesempenhoDao(AvaliacaoDesempenhoDao avaliacaoDesempenhoDao) {
		this.avaliacaoDesempenhoDao = avaliacaoDesempenhoDao;
	}
}
