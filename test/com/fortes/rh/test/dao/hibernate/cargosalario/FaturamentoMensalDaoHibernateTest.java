package com.fortes.rh.test.dao.hibernate.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.FaturamentoMensalDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.cargosalario.FaturamentoMensal;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaturamentoMensalFactory;

public class FaturamentoMensalDaoHibernateTest extends GenericDaoHibernateTest<FaturamentoMensal>
{
	private FaturamentoMensalDao faturamentoMensalDao;
	private EmpresaDao empresaDao;

	@Override
	public FaturamentoMensal getEntity()
	{
		return FaturamentoMensalFactory.getEntity();
	}

	@Override
	public GenericDao<FaturamentoMensal> getGenericDao()
	{
		return faturamentoMensalDao;
	}

	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		FaturamentoMensal janeiro = new FaturamentoMensal();
		janeiro.setMesAno(new Date());
		janeiro.setValor(200.0);
		janeiro.setEmpresa(empresa);
		faturamentoMensalDao.save(janeiro);
		
		Collection<FaturamentoMensal> faturamentos = faturamentoMensalDao.findAllSelect(empresa.getId());
		assertEquals(1, faturamentos.size());
	}
	
	public void setFaturamentoMensalDao(FaturamentoMensalDao faturamentoMensalDao)
	{
		this.faturamentoMensalDao = faturamentoMensalDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
}
