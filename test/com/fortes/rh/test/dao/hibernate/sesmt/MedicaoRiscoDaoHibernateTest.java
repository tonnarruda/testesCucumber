package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.dao.sesmt.HistoricoAmbienteDao;
import com.fortes.rh.dao.sesmt.MedicaoRiscoDao;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.dao.sesmt.RiscoMedicaoRiscoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.sesmt.MedicaoRiscoFactory;

public class MedicaoRiscoDaoHibernateTest extends GenericDaoHibernateTest<MedicaoRisco>
{
	private MedicaoRiscoDao medicaoRiscoDao;
	private RiscoMedicaoRiscoDao riscoMedicaoRiscoDao;
	private AmbienteDao ambienteDao;
	private EmpresaDao empresaDao;
	
	private Empresa empresa = null;
	private MedicaoRisco medicaoRisco = null;
	private Ambiente ambiente = null;
	
	RiscoDao riscoDao;
	HistoricoAmbienteDao historicoAmbienteDao;
	
	@Override
	public MedicaoRisco getEntity()
	{
		return MedicaoRiscoFactory.getEntity();
	}

	@Override
	public GenericDao<MedicaoRisco> getGenericDao()
	{
		return medicaoRiscoDao;
	}

	public void setMedicaoRiscoDao(MedicaoRiscoDao medicaoRiscoDao)
	{
		this.medicaoRiscoDao = medicaoRiscoDao;
	}
	
	public void testFindByIdProjection() 
	{
		saveDadosMedicao();
		assertEquals(medicaoRisco, medicaoRiscoDao.findByIdProjection(medicaoRisco.getId()));
	}
	
	public void testFindAllSelect()
	{
		empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		saveDadosMedicao();
		saveDadosMedicao();
		saveDadosMedicao();
		
		Collection<MedicaoRisco> colecao = medicaoRiscoDao.findAllSelect(empresa.getId(), null);
		
		assertEquals(3, colecao.size());
	}
	public void testFindAllSelectPorAmbiente()
	{
		empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		saveDadosMedicao();
		saveDadosMedicao();
		saveDadosMedicao();
		
		Collection<MedicaoRisco> colecao = medicaoRiscoDao.findAllSelect(empresa.getId(), ambiente.getId());
		
		assertEquals(1, colecao.size());
	}
	
	public void testFindTecnicasUtilizadasDistinct()
	{
		empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		saveDadosMedicao();
		saveDadosMedicao();
		
		assertEquals(1, medicaoRiscoDao.findTecnicasUtilizadasDistinct(empresa.getId()).size());
	}

	private void saveDadosMedicao() {
		ambiente = AmbienteFactory.getEntity();
		ambiente.setEmpresa(empresa);
		ambienteDao.save(ambiente);
		
		medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setAmbiente(ambiente);
		medicaoRiscoDao.save(medicaoRisco);
		
		RiscoMedicaoRisco riscoMedicaoRisco = new RiscoMedicaoRisco();
		riscoMedicaoRisco.setMedicaoRisco(medicaoRisco);
		riscoMedicaoRisco.setTecnicaUtilizada("Técnica 1");
		riscoMedicaoRiscoDao.save(riscoMedicaoRisco);
	}
	
	public void setAmbienteDao(AmbienteDao ambienteDao) {
		this.ambienteDao = ambienteDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public void setRiscoMedicaoRiscoDao(RiscoMedicaoRiscoDao riscoMedicaoRiscoDao) {
		this.riscoMedicaoRiscoDao = riscoMedicaoRiscoDao;
	}

	public void setRiscoDao(RiscoDao riscoDao) {
		this.riscoDao = riscoDao;
	}

	public void setHistoricoAmbienteDao(HistoricoAmbienteDao historicoAmbienteDao) {
		this.historicoAmbienteDao = historicoAmbienteDao;
	}

}