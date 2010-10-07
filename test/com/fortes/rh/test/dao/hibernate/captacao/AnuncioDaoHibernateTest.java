package com.fortes.rh.test.dao.hibernate.captacao;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.AnuncioDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.captacao.Anuncio;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class AnuncioDaoHibernateTest extends GenericDaoHibernateTest<Anuncio>
{
	private AnuncioDao anuncioDao;
	private SolicitacaoDao solicitacaoDao;
	private EmpresaDao empresaDao;

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
		solicitacao.setLiberada(true);
		solicitacao.setEmpresa(empresa);
		solicitacao = solicitacaoDao.save(solicitacao);

		Anuncio anuncio = new Anuncio();
		anuncio.setTitulo("titulo");
		anuncio.setSolicitacao(solicitacao);
		anuncio.setExibirModuloExterno(true);
		anuncio = anuncioDao.save(anuncio);

		assertEquals(anuncio, anuncioDao.findAnunciosSolicitacaoAberta(empresa.getId()).toArray()[0]);
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

}
