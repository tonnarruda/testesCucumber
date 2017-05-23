package com.fortes.rh.test.dao.hibernate.geral;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.MotivoDemissaoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.MotivoDemissao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class MotivoDemissaoDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<MotivoDemissao>
{
	@Autowired
	private MotivoDemissaoDao motivoDemissaoDao;
	@Autowired
	private EmpresaDao empresaDao;

	public MotivoDemissao getEntity()
	{
		MotivoDemissao motDemissao = new MotivoDemissao();

		motDemissao.setId(1L);
		motDemissao.setMotivo("teste");


		return motDemissao;
	}

	public GenericDao<MotivoDemissao> getGenericDao()
	{
		return motivoDemissaoDao;
	}

	@Test
	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		MotivoDemissao motivoDemissao = new MotivoDemissao();
		motivoDemissao.setEmpresa(empresa);
		motivoDemissao.setMotivo("teste");
		motivoDemissaoDao.save(motivoDemissao);

		assertEquals(1, motivoDemissaoDao.findAllSelect(empresa.getId()).size());
	}
	
	@Test
	public void testFindMotivoDemissao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		MotivoDemissao motivoDemissao1 = new MotivoDemissao();
		motivoDemissao1.setEmpresa(empresa);
		motivoDemissao1.setMotivo("teste");
		motivoDemissaoDao.save(motivoDemissao1);
		
		MotivoDemissao motivoDemissao2 = new MotivoDemissao();
		motivoDemissao2.setEmpresa(empresa);
		motivoDemissao2.setMotivo("teste2");
		motivoDemissao2.setAtivo(false);
		motivoDemissaoDao.save(motivoDemissao2);
		
		assertEquals(1, motivoDemissaoDao.findMotivoDemissao(0, null, empresa.getId(), "teste", true).size());
	}
}




