package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.dao.sesmt.EpcDao;
import com.fortes.rh.dao.sesmt.HistoricoAmbienteDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;

public class EpcDaoHibernateTest extends GenericDaoHibernateTest<Epc>
{
	EpcDao epcDao = null;
	EmpresaDao empresaDao;
	AmbienteDao ambienteDao;
	private HistoricoAmbienteDao historicoAmbienteDao;

	@Override
	public Epc getEntity()
	{
		return new Epc();
	}

	@Override
	public GenericDao<Epc> getGenericDao()
	{
		return epcDao;
	}

	public void setEpcDao(EpcDao epcDao)
	{
		this.epcDao = epcDao;
	}

	public void testFindByIdProjection()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Epc epc = new Epc();
		epc.setEmpresa(empresa);
		epcDao.save(epc);

		Epc epcRetorno = epcDao.findByIdProjection(epc.getId());

		assertEquals(epc.getId(), epcRetorno.getId());
	}
	
	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Epc epc = new Epc();
		epc.setEmpresa(empresa);
		epcDao.save(epc);
		
		Epc epcFora = new Epc();
		epcDao.save(epcFora);
		
		assertEquals(1, epcDao.findAllSelect(empresa.getId()).size());
	}
	
	public void testFindEpcsDoAmbiente()
	{
			Epc epc1 = new Epc();
			epcDao.save(epc1);
			
			Epc epc2 = new Epc();
			epcDao.save(epc2);
			
			Ambiente ambiente = AmbienteFactory.getEntity();
			ambienteDao.save(ambiente);
			
			Collection<Epc> epcs = new ArrayList<Epc>();
			epcs.add(epc1);
			
			HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
			historicoAmbiente.setData(new Date());
			historicoAmbiente.setAmbiente(ambiente);
			historicoAmbiente.setEpcs(epcs);
			historicoAmbienteDao.save(historicoAmbiente);
			
			assertEquals(1, epcDao.findEpcsDoAmbiente(ambiente.getId(), new Date()).size());
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setAmbienteDao(AmbienteDao ambienteDao) {
		this.ambienteDao = ambienteDao;
	}

	public void setHistoricoAmbienteDao(HistoricoAmbienteDao historicoAmbienteDao) {
		this.historicoAmbienteDao = historicoAmbienteDao;
	}
}