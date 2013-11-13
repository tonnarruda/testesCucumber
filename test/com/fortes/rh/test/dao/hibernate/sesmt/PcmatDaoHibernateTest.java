package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.ObraDao;
import com.fortes.rh.dao.sesmt.PcmatDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Obra;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.ObraFactory;
import com.fortes.rh.test.factory.sesmt.PcmatFactory;

public class PcmatDaoHibernateTest extends GenericDaoHibernateTest<Pcmat>
{
	private PcmatDao pcmatDao;
	private EmpresaDao empresaDao;
	private ObraDao obraDao;

	@Override
	public Pcmat getEntity()
	{
		return PcmatFactory.getEntity();
	}

	@Override
	public GenericDao<Pcmat> getGenericDao()
	{
		return pcmatDao;
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
		
		Pcmat pcmat1 = PcmatFactory.getEntity();
		pcmat1.setObra(obra1);
		pcmatDao.save(pcmat1);

		Collection<Pcmat> retorno = pcmatDao.findAllSelect(null, empresa1.getId());
		assertEquals("pcmat1", 1, retorno.size());
	}

	
	public void setPcmatDao(PcmatDao pcmatDao)
	{
		this.pcmatDao = pcmatDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public void setObraDao(ObraDao obraDao) {
		this.obraDao = obraDao;
	}
}
