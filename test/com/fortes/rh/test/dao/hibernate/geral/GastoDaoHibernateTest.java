package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.GastoDao;
import com.fortes.rh.dao.geral.GrupoGastoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Gasto;
import com.fortes.rh.model.geral.GrupoGasto;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class GastoDaoHibernateTest extends GenericDaoHibernateTest<Gasto>
{
	private GastoDao gastoDao;
	private EmpresaDao empresaDao;
	private GrupoGastoDao grupoGastoDao;

	public void setGrupoGastoDao(GrupoGastoDao grupoGastoDao)
	{
		this.grupoGastoDao = grupoGastoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public Gasto getEntity()
	{
		Gasto gasto = new Gasto();

		gasto.setId(null);
		gasto.setNome("gasto");
		gasto.setGrupoGasto(null);
		gasto.setEmpresa(null);

		return gasto;
	}

	public GenericDao<Gasto> getGenericDao()
	{
		return gastoDao;
	}

	public void setGastoDao(GastoDao gastoDao)
	{
		this.gastoDao = gastoDao;
	}

	public void testFindByEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Gasto gasto = getEntity();
		gasto.setEmpresa(empresa);
		gasto.setCodigoAc(null);
		gasto = gastoDao.save(gasto);

		Gasto gasto2 = getEntity();
		gasto2.setEmpresa(empresa);
		gasto2.setCodigoAc("65");
		gasto2 = gastoDao.save(gasto2);

		Collection<Gasto> retorno = gastoDao.findByEmpresa(empresa.getId());

		assertEquals(1, retorno.size());
	}

	public void testFindByIdProjection()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Gasto gasto = new Gasto();
		gasto.setEmpresa(empresa);
		gasto = gastoDao.save(gasto);

		Gasto gastoRetorno = gastoDao.findByIdProjection(gasto.getId());

		assertEquals(gasto, gastoRetorno);
	}

	public void testUpdateGrupoGastoByGastos()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		GrupoGasto grupoGasto = new GrupoGasto();
		grupoGasto.setEmpresa(empresa);
		grupoGasto = grupoGastoDao.save(grupoGasto);

		Gasto gasto1 = getEntity();
		gasto1.setEmpresa(empresa);
		gasto1 = gastoDao.save(gasto1);

		Gasto gasto2 = getEntity();
		gasto2.setEmpresa(empresa);
		gasto2 = gastoDao.save(gasto2);

		gastoDao.updateGrupoGastoByGastos(grupoGasto.getId(), new Long[]{gasto1.getId(), gasto2.getId()});
	}
	
	public void testFindGastosDoGrupo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		GrupoGasto grupoGasto = new GrupoGasto();
		grupoGasto.setEmpresa(empresa);
		grupoGasto = grupoGastoDao.save(grupoGasto);
		
		Gasto gasto1 = getEntity();
		gasto1.setEmpresa(empresa);
		gasto1.setGrupoGasto(grupoGasto);
		gasto1 = gastoDao.save(gasto1);
		
		Gasto gasto2 = getEntity();
		gasto2.setEmpresa(empresa);
		gasto2.setGrupoGasto(grupoGasto);
		gasto2 = gastoDao.save(gasto2);
		
		assertEquals(2, gastoDao.findGastosDoGrupo(grupoGasto.getId()).size());
	}
	
	public void testGetGastosSemGrupo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Gasto gasto = getEntity();
		gasto.setEmpresa(empresa);
		gasto = gastoDao.save(gasto);
		
		assertEquals(1, gastoDao.getGastosSemGrupo(empresa.getId()).size());
	}
	
	public void testFindSemCodigoAC() {
		
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresa1 = empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresa2 = empresaDao.save(empresa2);
		
		Gasto gasto1 = getEntity();
		gasto1.setEmpresa(empresa1);
		gasto1.setCodigoAc("");
		gastoDao.save(gasto1);
		
		Gasto gasto2 = getEntity();
		gasto2.setEmpresa(empresa1);
		gasto2.setCodigoAc("1");
		gastoDao.save(gasto2);
		
		Gasto gasto3 = getEntity();
		gasto3.setEmpresa(empresa1);
		gasto3.setCodigoAc(null);
		gastoDao.save(gasto3);
		
		Gasto gasto4 = getEntity();
		gasto4.setEmpresa(empresa2);
		gasto4.setCodigoAc(null);
		gastoDao.save(gasto4);
		
		Gasto gasto5 = getEntity();
		gasto5.setEmpresa(empresa2);
		gasto5.setCodigoAc("5");
		gastoDao.save(gasto5);
		
		assertEquals(2, gastoDao.findSemCodigoAC(empresa1.getId()).size());
		assertEquals(1, gastoDao.findSemCodigoAC(empresa2.getId()).size());
		
	}
}