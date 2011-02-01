package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.ClinicaAutorizadaDao;
import com.fortes.rh.dao.sesmt.ExameDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.ExameFactory;
import com.fortes.rh.util.DateUtil;

public class ClinicaAutorizadaDaoHibernateTest extends GenericDaoHibernateTest<ClinicaAutorizada>
{
	ClinicaAutorizadaDao clinicaAutorizadaDao = null;
	ExameDao exameDao = null;
	EmpresaDao empresaDao = null;

	@Override
	public ClinicaAutorizada getEntity()
	{
		ClinicaAutorizada clinicaAutorizada = new ClinicaAutorizada();
		clinicaAutorizada.setNome("ClinicaAutorizada");
		return clinicaAutorizada;
	}

	@Override
	public GenericDao<ClinicaAutorizada> getGenericDao()
	{
		return clinicaAutorizadaDao;
	}

	public void setClinicaAutorizadaDao(ClinicaAutorizadaDao clinicaAutorizadaDao)
	{
		this.clinicaAutorizadaDao = clinicaAutorizadaDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	@SuppressWarnings("deprecation")
	public void testFindClinicasAtivasByDataEmpresa()
	{
		Empresa empresa1 = new Empresa();
		empresa1.setNome("fortes");
		empresa1.setCnpj("65465");
		empresa1.setRazaoSocial("fortes");

		Empresa empresa2 = new Empresa();
		empresa2.setNome("fortes");
		empresa2.setCnpj("65465");
		empresa2.setRazaoSocial("fortes");

		empresa1 = empresaDao.save(empresa1);
		empresa2 = empresaDao.save(empresa2);

		ClinicaAutorizada clinica1 = new ClinicaAutorizada();
		clinica1.setEmpresa(empresa1);
		clinica1.setData(new Date("2007/01/01"));

		ClinicaAutorizada clinica2 = new ClinicaAutorizada();
		clinica2.setDataInativa(new Date("2007/10/01"));

		ClinicaAutorizada clinica3 = new ClinicaAutorizada();
		clinica3.setEmpresa(empresa1);
		clinica3.setData(new Date("2007/01/01"));

		ClinicaAutorizada clinica4 = new ClinicaAutorizada();
		clinica4.setEmpresa(empresa1);
		clinica4.setData(new Date("2008/01/01"));

		ClinicaAutorizada clinica5 = new ClinicaAutorizada();
		clinica5.setEmpresa(empresa1);
		clinica5.setData(new Date("2009/01/01"));

		ClinicaAutorizada clinica6 = new ClinicaAutorizada();
		clinica6.setEmpresa(empresa2);
		clinica6.setData(new Date("2007/01/01"));

		clinicaAutorizadaDao.save(clinica1);
		clinicaAutorizadaDao.save(clinica2);
		clinicaAutorizadaDao.save(clinica3);
		clinicaAutorizadaDao.save(clinica4);
		clinicaAutorizadaDao.save(clinica5);
		clinicaAutorizadaDao.save(clinica6);

		Collection<ClinicaAutorizada> clinicaAutorizadas = clinicaAutorizadaDao.findClinicasAtivasByDataEmpresa(empresa1.getId(), new Date("2008/01/01"));

		assertEquals(3, clinicaAutorizadas.size());
	}

	public void testFindByExame()
	{
		Empresa empresa1 = new Empresa();
		empresaDao.save(empresa1);

		Exame exame = ExameFactory.getEntity();
		exameDao.save(exame);
		
		Exame exame2 = ExameFactory.getEntity();
		exameDao.save(exame2);
		
		Collection<Exame> exames = new ArrayList<Exame>();
		exames.add(exame);
		exames.add(exame2);

		Date hoje = new Date();
		ClinicaAutorizada clinica1 = new ClinicaAutorizada();
		clinica1.setEmpresa(empresa1);
		clinica1.setExames(exames);
		clinica1.setData(hoje);
		clinicaAutorizadaDao.save(clinica1);

		ClinicaAutorizada clinica2 = new ClinicaAutorizada();
		clinica2.setEmpresa(empresa1);
		clinica2.setExames(exames);
		clinica2.setData(DateUtil.montaDataByString("01/02/1990"));
		clinicaAutorizadaDao.save(clinica2);
		
		ClinicaAutorizada clinicaAtivaAte2090 = new ClinicaAutorizada();
		clinicaAtivaAte2090.setEmpresa(empresa1);
		clinicaAtivaAte2090.setData(hoje);
		clinicaAtivaAte2090.setExames(exames);
		clinicaAtivaAte2090.setDataInativa(DateUtil.montaDataByString("01/02/2090"));
		clinicaAutorizadaDao.save(clinicaAtivaAte2090);

		ClinicaAutorizada clinicaInativa = new ClinicaAutorizada();
		clinicaInativa.setEmpresa(empresa1);
		clinica2.setExames(exames);
		clinicaInativa.setDataInativa(DateUtil.montaDataByString("01/02/2000"));
		clinicaAutorizadaDao.save(clinicaInativa);

		Collection<ClinicaAutorizada> clinicas = clinicaAutorizadaDao.findByExame(empresa1.getId(), exame.getId(), hoje);

		assertEquals(3, clinicas.size());
	}

	public void setExameDao(ExameDao exameDao)
	{
		this.exameDao = exameDao;
	}
}