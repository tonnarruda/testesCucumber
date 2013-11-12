package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.ObraDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Obra;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.ObraFactory;

public class ObraDaoHibernateTest extends GenericDaoHibernateTest<Obra>
{
	private ObraDao obraDao;
	private EmpresaDao empresaDao;

	@Override
	public Obra getEntity()
	{
		return ObraFactory.getEntity();
	}
	
	public void testFindAllSelect()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		Empresa empresa2 = EmpresaFactory.getEmpresa();

		empresaDao.save(empresa1);
		empresaDao.save(empresa2);

		Obra obra1 = ObraFactory.getEntity();
		obra1.setNome("Obra A");
		obra1.setEmpresa(empresa1);
		obraDao.save(obra1);

		Obra obra2 = ObraFactory.getEntity();
		obra2.setNome("Obra B");
		obra2.setEmpresa(empresa1);
		obraDao.save(obra2);

		Obra obra3 = ObraFactory.getEntity();
		obra3.setNome("Obra C");
		obra3.setEmpresa(empresa2);
		obraDao.save(obra3);

		Collection<Obra> retorno = obraDao.findAllSelect(null, empresa1.getId());
		assertEquals("empresa1", 2, retorno.size());

		retorno = obraDao.findAllSelect(null, empresa2.getId());
		assertEquals("empresa2", 1, retorno.size());
		
		retorno = obraDao.findAllSelect("rá B", empresa1.getId());
		assertEquals("nome", obra2.getId(), retorno.toArray(new Obra[]{})[0].getId());
	}

	@Override
	public GenericDao<Obra> getGenericDao()
	{
		return obraDao;
	}

	public void setObraDao(ObraDao obraDao)
	{
		this.obraDao = obraDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
}
