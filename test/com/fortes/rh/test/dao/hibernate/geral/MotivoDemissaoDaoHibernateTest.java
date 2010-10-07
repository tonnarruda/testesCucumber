package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.MotivoDemissaoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.MotivoDemissao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class MotivoDemissaoDaoHibernateTest extends GenericDaoHibernateTest<MotivoDemissao>
{
	private MotivoDemissaoDao motivoDemissaoDao;
	private EmpresaDao empresaDao;

	public MotivoDemissao getEntity()
	{
		MotivoDemissao motDemissao = new MotivoDemissao();

		motDemissao.setId(1L);
		motDemissao.setMotivo("teste");


		return motDemissao;
	}


	public void setMotivoDemissaoDao(MotivoDemissaoDao motivoDemissao)
	{
		this.motivoDemissaoDao = motivoDemissao;
	}


	public GenericDao<MotivoDemissao> getGenericDao()
	{
		return motivoDemissaoDao;
	}

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


	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
}




