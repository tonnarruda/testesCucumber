package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.CartaoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.dicionario.TipoCartao;
import com.fortes.rh.model.geral.Cartao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.CartaoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class CartaoDaoHibernateTest extends GenericDaoHibernateTest<Cartao>
{
	private EmpresaDao empresaDao;
	private CartaoDao cartaoDao;
	
	public Cartao getEntity()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Cartao cartao = CartaoFactory.getEntity(empresa);
		return cartao;
	}

	public GenericDao<Cartao> getGenericDao()
	{
		return cartaoDao;
	}

	public void testFindByEmpresaIdAndTipo() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Cartao cartaoDeAniversario = CartaoFactory.getEntity(empresa, TipoCartao.ANIVERSARIO);
		cartaoDao.save(cartaoDeAniversario);
		
		Cartao cartaoAnoDeEmpresa = CartaoFactory.getEntity(empresa, TipoCartao.ANO_DE_EMPRESA);
		cartaoDao.save(cartaoAnoDeEmpresa);
		
		Cartao cartao = cartaoDao.findByEmpresaIdAndTipo(empresa.getId(), TipoCartao.ANIVERSARIO);
		
		assertEquals(cartaoDeAniversario.getId(), cartao.getId());
	}
	
	public void testFindByEmpresaId() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Cartao cartaoDeAniversario = CartaoFactory.getEntity(empresa, TipoCartao.ANIVERSARIO);
		cartaoDao.save(cartaoDeAniversario);
		
		Cartao cartaoAnoDeEmpresa = CartaoFactory.getEntity(empresa, TipoCartao.ANO_DE_EMPRESA);
		cartaoDao.save(cartaoAnoDeEmpresa);
		
		assertEquals(2, cartaoDao.findByEmpresaId(empresa.getId()).size());
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
	
	public void setCartaoDao(CartaoDao cartaoDao) {
		this.cartaoDao = cartaoDao;
	}
	
	
}