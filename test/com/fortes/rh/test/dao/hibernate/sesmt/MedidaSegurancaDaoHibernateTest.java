package com.fortes.rh.test.dao.hibernate.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.MedidaSegurancaDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.MedidaSeguranca;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.MedidaSegurancaFactory;

public class MedidaSegurancaDaoHibernateTest extends GenericDaoHibernateTest<MedidaSeguranca>
{
	private MedidaSegurancaDao medidaSegurancaDao;
	private EmpresaDao empresaDao;

	@Override
	public MedidaSeguranca getEntity()
	{
		return MedidaSegurancaFactory.getEntity();
	}

	@Override
	public GenericDao<MedidaSeguranca> getGenericDao()
	{
		return medidaSegurancaDao;
	}

	public void testFindAllSelect()
	{
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		Empresa empresa2 = EmpresaFactory.getEmpresa();

		empresaDao.save(empresa1);
		empresaDao.save(empresa2);

		MedidaSeguranca medida1 = MedidaSegurancaFactory.getEntity();
		medida1.setDescricao("Amarrar as almas");
		medida1.setEmpresa(empresa1);
		medidaSegurancaDao.save(medida1);
		
		MedidaSeguranca medida2 = MedidaSegurancaFactory.getEntity();
		medida2.setDescricao("Isolar as pontas");
		medida2.setEmpresa(empresa2);
		medidaSegurancaDao.save(medida2);
		
		assertEquals("Medida 1", 1, medidaSegurancaDao.findAllSelect(null, empresa1.getId()).size());
		assertEquals("Medida 1", 1, medidaSegurancaDao.findAllSelect("amar", empresa1.getId()).size());
		assertEquals("Descricao inexistente", 0, medidaSegurancaDao.findAllSelect("ammar", empresa1.getId()).size());
	}
	
	public void setMedidaSegurancaDao(MedidaSegurancaDao medidaSegurancaDao)
	{
		this.medidaSegurancaDao = medidaSegurancaDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
}
