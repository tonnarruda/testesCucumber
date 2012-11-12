package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.captacao.AnuncioDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.dao.captacao.SolicitacaoAvaliacaoDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.captacao.Anuncio;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class AnuncioDaoHibernateTest extends GenericDaoHibernateTest<Anuncio>
{
	private AnuncioDao anuncioDao;
	private SolicitacaoDao solicitacaoDao;
	private EmpresaDao empresaDao;
	private CandidatoDao candidatoDao;
	private AvaliacaoDao avaliacaoDao;
	private SolicitacaoAvaliacaoDao solicitacaoAvaliacaoDao;
	private CandidatoSolicitacaoDao candidatoSolicitacaoDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;

	public Anuncio getEntity()
	{
		Anuncio anuncio = new Anuncio();

		return anuncio;
	}

	public GenericDao<Anuncio> getGenericDao()
	{
		return anuncioDao;
	}

	public void testFindAnunciosSolicitacaoAberta()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Solicitacao solicitacao = new Solicitacao();

		solicitacao.setIdadeMaxima(50);
		solicitacao.setIdadeMinima(15);
		solicitacao.setEncerrada(false);
		solicitacao.setStatus(StatusAprovacaoSolicitacao.APROVADO);
		solicitacao.setEmpresa(empresa);
		solicitacao = solicitacaoDao.save(solicitacao);

		Anuncio anuncio = new Anuncio();
		anuncio.setTitulo("titulo");
		anuncio.setSolicitacao(solicitacao);
		anuncio.setExibirModuloExterno(true);
		anuncio = anuncioDao.save(anuncio);

		assertEquals(anuncio, anuncioDao.findAnunciosSolicitacaoAberta(empresa.getId()).toArray()[0]);
	}
	
	public void testFindAnunciosModuloExterno()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato candidato1 = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato1);
		
		Candidato candidato2 = CandidatoFactory.getCandidato();
		candidatoDao.save(candidato2);

		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setIdadeMaxima(50);
		solicitacao.setIdadeMinima(15);
		solicitacao.setEncerrada(false);
		solicitacao.setStatus(StatusAprovacaoSolicitacao.APROVADO);
		solicitacao.setEmpresa(empresa);
		solicitacao = solicitacaoDao.save(solicitacao);
		
		CandidatoSolicitacao candidatoSolicitacao1 = new CandidatoSolicitacao();
		candidatoSolicitacao1.setCandidato(candidato1);
		candidatoSolicitacao1.setSolicitacao(solicitacao);
		candidatoSolicitacaoDao.save(candidatoSolicitacao1);

		CandidatoSolicitacao candidatoSolicitacao2 = new CandidatoSolicitacao();
		candidatoSolicitacao2.setCandidato(candidato2);
		candidatoSolicitacao2.setSolicitacao(solicitacao);
		candidatoSolicitacaoDao.save(candidatoSolicitacao2);
		
		Avaliacao avaliacao1 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao1);
		
		Avaliacao avaliacao2 = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao2);
		
		SolicitacaoAvaliacao solicitacaoAvaliacao1 = new SolicitacaoAvaliacao();
		solicitacaoAvaliacao1.setSolicitacao(solicitacao);
		solicitacaoAvaliacao1.setAvaliacao(avaliacao1);
		solicitacaoAvaliacao1.setResponderModuloExterno(true);
		solicitacaoAvaliacaoDao.save(solicitacaoAvaliacao1);
		
		SolicitacaoAvaliacao solicitacaoAvaliacao2 = new SolicitacaoAvaliacao();
		solicitacaoAvaliacao2.setSolicitacao(solicitacao);
		solicitacaoAvaliacao2.setAvaliacao(avaliacao2);
		solicitacaoAvaliacao2.setResponderModuloExterno(true);
		solicitacaoAvaliacaoDao.save(solicitacaoAvaliacao2);
		
		ColaboradorQuestionario colaboradorQuestionario1 = new ColaboradorQuestionario();
		colaboradorQuestionario1.setSolicitacao(solicitacao);
		colaboradorQuestionario1.setCandidato(candidato1);
		colaboradorQuestionario1.setAvaliacao(avaliacao1);
		colaboradorQuestionario1.setRespondida(true);
		colaboradorQuestionarioDao.save(colaboradorQuestionario1);
		
		Anuncio anuncio = new Anuncio();
		anuncio.setTitulo("titulo");
		anuncio.setSolicitacao(solicitacao);
		anuncio.setExibirModuloExterno(true);
		anuncioDao.save(anuncio);
		
		Collection<Anuncio> anuncios = anuncioDao.findAnunciosModuloExterno(empresa.getId(), candidato1.getId());
		assertEquals(1, anuncios.size());
		
		Anuncio anuncioProCandidato1 = anuncios.toArray(new Anuncio[]{})[0];
		assertEquals(new Integer(2), anuncioProCandidato1.getQtdAvaliacoes());
		assertEquals(new Integer(1), anuncioProCandidato1.getQtdAvaliacoesRespondidas());

		anuncios = anuncioDao.findAnunciosModuloExterno(empresa.getId(), candidato2.getId());
		
		Anuncio anuncioProCandidato2 = anuncios.toArray(new Anuncio[]{})[0];
		assertEquals(new Integer(2), anuncioProCandidato2.getQtdAvaliacoes());
		assertEquals(new Integer(0), anuncioProCandidato2.getQtdAvaliacoesRespondidas());
	}

	public void testRemoveBySolicitacao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setEmpresa(empresa);
		solicitacao = solicitacaoDao.save(solicitacao);

		Anuncio anuncio = new Anuncio();
		anuncio.setSolicitacao(solicitacao);
		anuncio = anuncioDao.save(anuncio);

		anuncioDao.removeBySolicitacao(solicitacao.getId());

		assertNull(anuncioDao.findById(anuncio.getId(), null));
	}
	
	public void testFindByIdProjection()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setEmpresa(empresa);
		solicitacao = solicitacaoDao.save(solicitacao);
		
		Anuncio anuncio = new Anuncio();
		anuncio.setSolicitacao(solicitacao);
		anuncio = anuncioDao.save(anuncio);
		
		assertEquals(anuncio, anuncioDao.findByIdProjection(anuncio.getId()));
	}

	public void testFindBySolicitacao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setEmpresa(empresa);
		solicitacao = solicitacaoDao.save(solicitacao);
		
		Anuncio anuncio = new Anuncio();
		anuncio.setSolicitacao(solicitacao);
		anuncio = anuncioDao.save(anuncio);
		
		assertEquals(anuncio, anuncioDao.findBySolicitacao(solicitacao.getId()));
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setAnuncioDao(AnuncioDao anuncioDao)
	{
		this.anuncioDao = anuncioDao;
	}

	public void setSolicitacaoDao(SolicitacaoDao solicitacaoDao)
	{
		this.solicitacaoDao = solicitacaoDao;
	}

	public void setCandidatoDao(CandidatoDao candidatoDao) {
		this.candidatoDao = candidatoDao;
	}

	public void setAvaliacaoDao(AvaliacaoDao avaliacaoDao) {
		this.avaliacaoDao = avaliacaoDao;
	}

	public void setSolicitacaoAvaliacaoDao(
			SolicitacaoAvaliacaoDao solicitacaoAvaliacaoDao) {
		this.solicitacaoAvaliacaoDao = solicitacaoAvaliacaoDao;
	}

	public void setCandidatoSolicitacaoDao(
			CandidatoSolicitacaoDao candidatoSolicitacaoDao) {
		this.candidatoSolicitacaoDao = candidatoSolicitacaoDao;
	}

	public void setColaboradorQuestionarioDao(
			ColaboradorQuestionarioDao colaboradorQuestionarioDao) {
		this.colaboradorQuestionarioDao = colaboradorQuestionarioDao;
	}

}
