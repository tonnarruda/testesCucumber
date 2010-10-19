package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.dao.sesmt.EpiDao;
import com.fortes.rh.dao.sesmt.EpiHistoricoDao;
import com.fortes.rh.dao.sesmt.HistoricoAmbienteDao;
import com.fortes.rh.dao.sesmt.HistoricoFuncaoDao;
import com.fortes.rh.dao.sesmt.RiscoAmbienteDao;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiDao;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDao;
import com.fortes.rh.dao.sesmt.TipoEPIDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.model.sesmt.TipoEPI;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.test.factory.sesmt.RiscoAmbienteFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;

public class EpiDaoHibernateTest extends GenericDaoHibernateTest<Epi>
{
	private EpiDao epiDao;
	SolicitacaoEpiDao solicitacaoEpiDao;
	SolicitacaoEpiItemDao solicitacaoEpiItemDao;
	EpiHistoricoDao epiHistoricoDao;
	private EmpresaDao empresaDao;
	private AmbienteDao ambienteDao;
	private HistoricoAmbienteDao historicoAmbienteDao;
	private RiscoDao riscoDao;
	private RiscoAmbienteDao riscoAmbienteDao;
	private HistoricoFuncaoDao historicoFuncaoDao;
	private TipoEPIDao tipoEPIDao;

	private Empresa empresa;

	public Epi getEntity()
	{
		setEmpresa();

		Epi epi = new Epi();

		epi.setId(null);
		epi.setNome("nome da epi");
		epi.setFabricante("TecToy");
		epi.setEmpresa(empresa);
		epi.setTipoEPI(null);

		return epi;
	}

	public void testFindByIdProjection()
	{
		setEmpresa();

		Epi epi = new Epi();
		epi.setEmpresa(empresa);
		epi = epiDao.save(epi);

		Epi epiRetorno = epiDao.findByIdProjection(epi.getId());

		assertEquals(epi.getId(), epiRetorno.getId());
	}

	public void testFindByVencimentoCa()
	{
		Date data = new Date();

		TipoEPI tipoEPI = new TipoEPI();
		tipoEPIDao.save(tipoEPI);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Epi epi = EpiFactory.getEntity();
		epi.setTipoEPI(tipoEPI);
		epi.setEmpresa(empresa);
		epiDao.save(epi);
		
		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setData(data);
		epiHistorico.setVencimentoCA(data);
		epiHistorico.setEpi(epi);
		epiHistoricoDao.save(epiHistorico);

		Collection<Epi> colecao = epiDao.findByVencimentoCa(data, empresa.getId(), new Long[]{tipoEPI.getId()});
		assertEquals(1, colecao.size());
	}
	
	public void testFindEpisDoAmbiente()
	{
		Epi epi1 = new Epi();
		epiDao.save(epi1);
		
		EpiHistorico epiHistorico1 = new EpiHistorico();
		epiHistorico1.setData(new Date());
		epiHistorico1.setEpi(epi1);
		epiHistoricoDao.save(epiHistorico1);
		
		Epi epi2 = new Epi();
		epiDao.save(epi2);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambienteDao.save(ambiente);
		
		Collection<Epi> epis = new ArrayList<Epi>();
		epis.add(epi1);
		
		Risco risco = RiscoFactory.getEntity();
		risco.setEpis(epis);
		riscoDao.save(risco);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setData(new Date());
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbienteDao.save(historicoAmbiente);
		
		RiscoAmbiente riscoAmbiente = RiscoAmbienteFactory.getEntity();
		riscoAmbiente.setRisco(risco);
		riscoAmbiente.setHistoricoAmbiente(historicoAmbiente);
		riscoAmbienteDao.save(riscoAmbiente);
		
		assertEquals(1, epiDao.findEpisDoAmbiente(ambiente.getId(), new Date()).size());
	}

	public void testFindByRiscoAmbiente()
	{
		Epi epi1 = new Epi();
		epiDao.save(epi1);

		EpiHistorico epiHistorico1 = new EpiHistorico();
		epiHistorico1.setData(new Date());
		epiHistorico1.setEpi(epi1);
		epiHistoricoDao.save(epiHistorico1);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambienteDao.save(ambiente);
		
		Collection<Epi> epis = new ArrayList<Epi>();
		epis.add(epi1);
		
		Risco risco = RiscoFactory.getEntity();
		risco.setEpis(epis);
		riscoDao.save(risco);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setData(new Date());
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbienteDao.save(historicoAmbiente);
		
		RiscoAmbiente riscoAmbiente = RiscoAmbienteFactory.getEntity();
		riscoAmbiente.setRisco(risco);
		riscoAmbiente.setHistoricoAmbiente(historicoAmbiente);
		riscoAmbienteDao.save(riscoAmbiente);
		
		assertEquals(epis, epiDao.findByRiscoAmbiente(risco.getId(), ambiente.getId(), new Date()));
	}

	public void testFindByRisco()
	{
		Epi epi1 = new Epi();
		epiDao.save(epi1);
		
		EpiHistorico epiHistorico1 = new EpiHistorico();
		epiHistorico1.setData(new Date());
		epiHistorico1.setEpi(epi1);
		epiHistoricoDao.save(epiHistorico1);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambienteDao.save(ambiente);
		
		Collection<Epi> epis = new ArrayList<Epi>();
		epis.add(epi1);
		
		Risco risco = RiscoFactory.getEntity();
		risco.setEpis(epis);
		riscoDao.save(risco);
		
		RiscoAmbiente riscoAmbiente = RiscoAmbienteFactory.getEntity();
		riscoAmbiente.setRisco(risco);
		riscoAmbienteDao.save(riscoAmbiente);
		
		assertEquals(epis, epiDao.findByRisco(risco.getId()));
	}


	public void testFindByIdHistoricoFuncao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Epi epi1 = new Epi();
		epi1.setEmpresa(empresa);
		epi1 = epiDao.save(epi1);

		Epi epi2 = new Epi();
		epi2.setEmpresa(empresa);
		epi2 = epiDao.save(epi2);

		Collection<Epi> epis = new ArrayList<Epi>();
		epis.add(epi1);
		epis.add(epi2);

		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setEpis(epis);
		historicoFuncao = historicoFuncaoDao.save(historicoFuncao);

		Collection<Epi> episRetorno = epiDao.findByHistoricoFuncao(historicoFuncao.getId());

		assertEquals(2, episRetorno.size());
	}	
	
	public void testFindSincronizarEpiInteresse() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa = empresaDao.save(empresa);
		
		Epi epi = EpiFactory.getEntity();
		epi.setEmpresa(empresa);
		epiDao.save(epi);
		
		Collection<Epi> episRetorno = epiDao.findSincronizarEpiInteresse(empresa.getId());

		assertEquals(1, episRetorno.size());
	}
	
	private void setEmpresa()
	{
		empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
	}

	public GenericDao<Epi> getGenericDao()
	{
		return epiDao;
	}

	public void setEpiDao(EpiDao epiDao)
	{
		this.epiDao = epiDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setSolicitacaoEpiDao(SolicitacaoEpiDao solicitacaoEpiDao)
	{
		this.solicitacaoEpiDao = solicitacaoEpiDao;
	}

	public void setSolicitacaoEpiItemDao(SolicitacaoEpiItemDao solicitacaoEpiItemDao)
	{
		this.solicitacaoEpiItemDao = solicitacaoEpiItemDao;
	}

	public void setEpiHistoricoDao(EpiHistoricoDao epiHistoricoDao)
	{
		this.epiHistoricoDao = epiHistoricoDao;
	}

	public void setAmbienteDao(AmbienteDao ambienteDao) {
		this.ambienteDao = ambienteDao;
	}

	public void setHistoricoAmbienteDao(HistoricoAmbienteDao historicoAmbienteDao) {
		this.historicoAmbienteDao = historicoAmbienteDao;
	}

	public void setRiscoDao(RiscoDao riscoDao) {
		this.riscoDao = riscoDao;
	}

	public void setRiscoAmbienteDao(RiscoAmbienteDao riscoAmbienteDao) {
		this.riscoAmbienteDao = riscoAmbienteDao;
	}

	public void setHistoricoFuncaoDao(HistoricoFuncaoDao historicoFuncaoDao)
	{
		this.historicoFuncaoDao = historicoFuncaoDao;
	}

	public void setTipoEPIDao(TipoEPIDao tipoEPIDao) {
		this.tipoEPIDao = tipoEPIDao;
	}
}