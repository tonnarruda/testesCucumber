package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.MedicoCoordenadorDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class MedicoCoordenadorDaoHibernateTest extends GenericDaoHibernateTest<MedicoCoordenador>
{
	MedicoCoordenadorDao medicoCoordenadorDao = null;
	private EmpresaDao empresaDao;

	@Override
	public MedicoCoordenador getEntity()
	{
		MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
		medicoCoordenador.setNome("medicoCoordenador");
		return medicoCoordenador;
	}

	@Override
	public GenericDao<MedicoCoordenador> getGenericDao()
	{
		return medicoCoordenadorDao;
	}

	public void setMedicoCoordenadorDao(MedicoCoordenadorDao medicoCoordenadorDao)
	{
		this.medicoCoordenadorDao = medicoCoordenadorDao;
	}

	@SuppressWarnings("deprecation")
	public void testFindByDataEmpresa()
	{
		Empresa empresa1 = new Empresa();
		empresa1.setId(123434214L);
		empresa1.setNome("fortes");
		empresa1.setCnpj("65465");
		empresa1.setRazaoSocial("fortes");

		Empresa empresa2 = new Empresa();
		empresa1.setId(12345454L);
		empresa2.setNome("fortes");
		empresa2.setCnpj("65465");
		empresa2.setRazaoSocial("fortes");

		empresa1 = empresaDao.save(empresa1);
		empresa2 = empresaDao.save(empresa2);

		MedicoCoordenador medico1 = new MedicoCoordenador();
		medico1.setEmpresa(empresa1);
		medico1.setInicio(new Date("2000/01/01"));

		MedicoCoordenador medico2 = new MedicoCoordenador();
		medico2.setEmpresa(empresa1);
		medico2.setInicio(new Date("2007/01/01"));

		MedicoCoordenador medico3 = new MedicoCoordenador();
		medico3.setEmpresa(empresa2);
		medico3.setInicio(new Date("2008/01/01"));

		medico1 = medicoCoordenadorDao.save(medico1);
		medico2 = medicoCoordenadorDao.save(medico2);
		medico3 = medicoCoordenadorDao.save(medico3);

		MedicoCoordenador medicoRetorno = medicoCoordenadorDao.findByDataEmpresa(empresa1.getId(), new Date("2008/01/01"));

		assertEquals(medico2, medicoRetorno);
	}

	public void testFindByIdProjection()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		MedicoCoordenador medico = new MedicoCoordenador();
		medico.setEmpresa(empresa);
		medico = medicoCoordenadorDao.save(medico);

		MedicoCoordenador medicoRetorno = medicoCoordenadorDao.findByIdProjection(medico.getId());

		assertEquals(medico, medicoRetorno);
	}

	public void testFindByEmpresa()
	{

		Calendar dataDoisMesesAtras = Calendar.getInstance();
    	dataDoisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar dataTresMesesAtras = Calendar.getInstance();
    	dataTresMesesAtras.add(Calendar.MONTH, -3);
    	Calendar dataFutura = Calendar.getInstance();
    	dataFutura.add(Calendar.MONTH, +3);

		Empresa empresa1 = new Empresa();
		empresa1.setId(123434214L);
		empresa1.setNome("fortes");
		empresa1.setCnpj("65465");
		empresa1.setRazaoSocial("fortes");

		Empresa empresa2 = new Empresa();
		empresa1.setId(12345454L);
		empresa2.setNome("fortes");
		empresa2.setCnpj("65465");
		empresa2.setRazaoSocial("fortes");

		empresa1 = empresaDao.save(empresa1);
		empresa2 = empresaDao.save(empresa2);

		MedicoCoordenador medico1 = new MedicoCoordenador();
		medico1.setEmpresa(empresa1);
		medico1.setInicio(dataTresMesesAtras.getTime());

		MedicoCoordenador medico2 = new MedicoCoordenador();
		medico2.setEmpresa(empresa1);
		medico2.setInicio(dataDoisMesesAtras.getTime());

		MedicoCoordenador medico3 = new MedicoCoordenador();
		medico3.setEmpresa(empresa2);
		medico3.setInicio(dataFutura.getTime());

		medico1 = medicoCoordenadorDao.save(medico1);
		medico2 = medicoCoordenadorDao.save(medico2);
		medico3 = medicoCoordenadorDao.save(medico3);

		Collection<MedicoCoordenador> collection = medicoCoordenadorDao.findByEmpresa(empresa1.getId(), false);
		collection = medicoCoordenadorDao.findByEmpresa(empresa1.getId(), false);

		assertEquals(2, collection.size());
		assertTrue(collection.contains(medico2));
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
}