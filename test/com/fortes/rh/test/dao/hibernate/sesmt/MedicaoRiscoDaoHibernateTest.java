package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.dao.sesmt.HistoricoAmbienteDao;
import com.fortes.rh.dao.sesmt.MedicaoRiscoDao;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.dao.sesmt.RiscoMedicaoRiscoDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.MedicaoRiscoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;

public class MedicaoRiscoDaoHibernateTest extends GenericDaoHibernateTest<MedicaoRisco>
{
	private MedicaoRiscoDao medicaoRiscoDao;
	private RiscoMedicaoRiscoDao riscoMedicaoRiscoDao;
	private AmbienteDao ambienteDao;
	private FuncaoDao funcaoDao;
	private EmpresaDao empresaDao;
	private EstabelecimentoDao estabelecimentoDao;
	private CargoDao cargoDao;
	
	private Empresa empresa = null;
	private MedicaoRisco medicaoRisco = null;
	private Ambiente ambiente = null;
	private Funcao funcao = null;
	
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
		
		Collection<MedicaoRisco> colecao = medicaoRiscoDao.findAllSelectByAmbiente(empresa.getId(), null);
		
		assertEquals(3, colecao.size());
	}
	public void testFindAllSelectPorAmbiente()
	{
		empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		saveDadosMedicao();
		saveDadosMedicao();
		saveDadosMedicao();
		
		Collection<MedicaoRisco> colecao = medicaoRiscoDao.findAllSelectByAmbiente(empresa.getId(), ambiente.getId());
		
		assertEquals(1, colecao.size());
	}

	public void testFindAllSelectPorFuncao()
	{
		empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		saveDadosMedicaoFuncao(empresa);
		saveDadosMedicaoFuncao(empresa);
		saveDadosMedicaoFuncao(empresa);
		
		Collection<MedicaoRisco> colecao = medicaoRiscoDao.findAllSelectByFuncao(empresa.getId(), funcao.getId());
		
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
	
	public void testGetMedicaoRiscoMedicaoPorFuncao() 
	{
		funcao = new Funcao();
		funcaoDao.save(funcao);
		
		medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setFuncao(funcao);
		medicaoRiscoDao.save(medicaoRisco);
		
		assertEquals(medicaoRisco.getFuncao().getId(), ((MedicaoRisco) medicaoRiscoDao.getMedicaoRiscoMedicaoPorFuncao(medicaoRisco.getId())).getFuncao().getId());
	}
	
	public void testFindRiscoMedicaoRiscos() 
	{
		Risco risco = RiscoFactory.getEntity();
		riscoDao.save(risco);
		
		funcao = new Funcao();
		funcaoDao.save(funcao);
		
		medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setFuncao(funcao);
		medicaoRiscoDao.save(medicaoRisco);
		
		RiscoMedicaoRisco riscoMedicaoRisco = new RiscoMedicaoRisco();
		riscoMedicaoRisco.setRisco(risco);
		riscoMedicaoRisco.setMedicaoRisco(medicaoRisco);
		riscoMedicaoRisco.setTecnicaUtilizada("Técnica 1");
		riscoMedicaoRiscoDao.save(riscoMedicaoRisco);

		RiscoMedicaoRisco riscoMedicaoRisco2 = new RiscoMedicaoRisco();
		riscoMedicaoRisco2.setRisco(risco);
		riscoMedicaoRisco2.setMedicaoRisco(medicaoRisco);
		riscoMedicaoRisco2.setTecnicaUtilizada("Técnica 2");
		riscoMedicaoRiscoDao.save(riscoMedicaoRisco2);
		
		assertEquals(2, medicaoRiscoDao.findRiscoMedicaoRiscos(medicaoRisco.getId()).size());
	}

	private void saveDadosMedicao() {
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("Estabelecimento");
		estabelecimentoDao.save(estabelecimento);
		
		ambiente = AmbienteFactory.getEntity();
		ambiente.setEmpresa(empresa);
		ambiente.setEstabelecimento(estabelecimento);
		ambienteDao.save(ambiente);
		
		medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setAmbiente(ambiente);
		medicaoRiscoDao.save(medicaoRisco);
		
		RiscoMedicaoRisco riscoMedicaoRisco = new RiscoMedicaoRisco();
		riscoMedicaoRisco.setMedicaoRisco(medicaoRisco);
		riscoMedicaoRisco.setTecnicaUtilizada("Técnica 1");
		riscoMedicaoRiscoDao.save(riscoMedicaoRisco);
	}
	
	private void saveDadosMedicaoFuncao(Empresa empresa) {
		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("Cargo");
		cargo.setEmpresa(empresa);
		cargoDao.save(cargo);
		
		funcao = new Funcao();
		funcao.setEmpresa(empresa);
		funcaoDao.save(funcao);
		
		medicaoRisco = MedicaoRiscoFactory.getEntity();
		medicaoRisco.setFuncao(funcao);
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

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao) {
		this.estabelecimentoDao = estabelecimentoDao;
	}

	public void setCargoDao(CargoDao cargoDao) {
		this.cargoDao = cargoDao;
	}

	public void setFuncaoDao(FuncaoDao funcaoDao) {
		this.funcaoDao = funcaoDao;
	}

}