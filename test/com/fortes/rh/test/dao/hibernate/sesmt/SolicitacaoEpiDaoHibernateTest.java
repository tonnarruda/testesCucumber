package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.EpiDao;
import com.fortes.rh.dao.sesmt.EpiHistoricoDao;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiDao;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDao;
import com.fortes.rh.dao.sesmt.TipoEPIDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.model.sesmt.TipoEPI;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiFactory;
import com.fortes.rh.util.DateUtil;

public class SolicitacaoEpiDaoHibernateTest extends GenericDaoHibernateTest<SolicitacaoEpi>
{
	SolicitacaoEpiDao solicitacaoEpiDao;
	SolicitacaoEpiItemDao solicitacaoEpiItemDao;
	EpiDao epiDao;
	EpiHistoricoDao epiHistoricoDao;
	ColaboradorDao colaboradorDao;
	AreaOrganizacionalDao areaOrganizacionalDao;
	CargoDao cargoDao;
	EmpresaDao empresaDao;
	TipoEPIDao tipoEPIDao;
	EstabelecimentoDao estabelecimentoDao;
	HistoricoColaboradorDao historicoColaboradorDao;

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	@Override
	public SolicitacaoEpi getEntity()
	{
		return SolicitacaoEpiFactory.getEntity();
	}

	@Override
	public GenericDao<SolicitacaoEpi> getGenericDao()
	{
		return solicitacaoEpiDao;
	}

	public void setSolicitacaoEpiDao(SolicitacaoEpiDao solicitacaoEpiDao)
	{
		this.solicitacaoEpiDao = solicitacaoEpiDao;
	}

	public void testFindByIdProjection()
	{
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpiDao.save(solicitacaoEpi);

		SolicitacaoEpi resultado = solicitacaoEpiDao.findByIdProjection(solicitacaoEpi.getId());
		assertNotNull(resultado);
		assertEquals(resultado.getId(), solicitacaoEpi.getId());
	}

	public void testFindVencimentoEpi()
	{
		Date hoje = new Date();

		Calendar dataSeisMesesAtras = Calendar.getInstance();
    	dataSeisMesesAtras.add(Calendar.MONTH, -6);

    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);

		TipoEPI tipoEPI = new TipoEPI();
		tipoEPI.setId(1L);
		tipoEPI.setEmpresa(empresa);
		tipoEPI.setNome("teste");
		tipoEPIDao.save(tipoEPI);
    	
    	Epi epi = EpiFactory.getEntity();
    	epi.setEmpresa(empresa);
    	epi.setTipoEPI(tipoEPI);
    	epiDao.save(epi);

		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setValidadeUso(6);
		epiHistorico.setEpi(epi);
		epiHistorico.setData(dataSeisMesesAtras.getTime());
		epiHistoricoDao.save(epiHistorico);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setColaborador(colaborador);
		historicoColaboradorDao.save(historicoColaborador);

		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);
		solicitacaoEpi.setData(dataSeisMesesAtras.getTime());
		solicitacaoEpi.setColaborador(colaborador);
		solicitacaoEpi.setCargo(cargo);
		solicitacaoEpi.setEntregue(true);
		solicitacaoEpiDao.save(solicitacaoEpi);

		SolicitacaoEpiItem solicitacaoEpiItem = new SolicitacaoEpiItem();
		solicitacaoEpiItem.setEpi(epi);
		solicitacaoEpiItem.setSolicitacaoEpi(solicitacaoEpi);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem);

		Long[] tipoEPIIds = {tipoEPI.getId()};
		Long[] areasIds = {areaOrganizacional.getId()};
		Long[] estabelecimentoIds = {estabelecimento.getId()};
		
		Collection<SolicitacaoEpi> colecao = solicitacaoEpiDao.findVencimentoEpi(empresa.getId(), hoje, false, tipoEPIIds, areasIds, estabelecimentoIds);

		assertEquals(1,colecao.size());
	}

	public void testFindEntregaEpi()
	{
		Date hoje = new Date();
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setDesligado(false);
		colaborador.setNome("nometeste");
		colaboradorDao.save(colaborador);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("cargo");
		cargoDao.save(cargo);
		
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi.setData(hoje);
		solicitacaoEpi.setColaborador(colaborador);
		solicitacaoEpi.setCargo(cargo);
		solicitacaoEpi.setEntregue(true);
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setValidadeUso(6);
		epiHistorico.setData(hoje);
		epiHistoricoDao.save(epiHistorico);

		Collection<EpiHistorico> epiHistoricos = new ArrayList<EpiHistorico>();
		epiHistoricos.add(epiHistorico);
		
		Epi epi = EpiFactory.getEntity();
		epi.setEmpresa(empresa);
		epi.setNome("teste");
		epi.setEpiHistoricos(epiHistoricos);
		epiDao.save(epi);
		
		SolicitacaoEpiItem solicitacaoEpiItem = new SolicitacaoEpiItem();
		solicitacaoEpiItem.setDataEntrega(hoje);
		solicitacaoEpiItem.setSolicitacaoEpi(solicitacaoEpi);
		solicitacaoEpiItem.setEpi(epi);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem);
		
		Long[] epiCheck = {epi.getId()};
		char agruparPor = 'E';//agrupar por EPI
		
		Collection<SolicitacaoEpi> colecao = solicitacaoEpiDao.findEntregaEpi(empresa.getId(), DateUtil.criarDataMesAno(01, 01, 2011), DateUtil.criarDataMesAno(01, 03, 2020), epiCheck, null, agruparPor);
		
		assertEquals(1, colecao.size());
	}
	
	
	public void testFindEntregaEpiComColaborador()
	{
		Date hoje = new Date();
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setDesligado(false);
		colaborador.setNome("nometeste");
		colaboradorDao.save(colaborador);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("cargo");
		cargoDao.save(cargo);
		
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi.setData(hoje);
		solicitacaoEpi.setColaborador(colaborador);
		solicitacaoEpi.setCargo(cargo);
		solicitacaoEpi.setEntregue(true);
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setValidadeUso(6);
		epiHistorico.setData(hoje);
		epiHistoricoDao.save(epiHistorico);
		
		Collection<EpiHistorico> epiHistoricos = new ArrayList<EpiHistorico>();
		epiHistoricos.add(epiHistorico);
		
		Epi epi = EpiFactory.getEntity();
		epi.setEmpresa(empresa);
		epi.setNome("teste");
		epi.setEpiHistoricos(epiHistoricos);
		epiDao.save(epi);
		
		SolicitacaoEpiItem solicitacaoEpiItem = new SolicitacaoEpiItem();
		solicitacaoEpiItem.setDataEntrega(hoje);
		solicitacaoEpiItem.setSolicitacaoEpi(solicitacaoEpi);
		solicitacaoEpiItem.setEpi(epi);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem);
		
		Long[] colaboradorCheck = {colaborador.getId()};
		char agruparPor = 'C';//agrupar por colaborador
		
		Collection<SolicitacaoEpi> colecao = solicitacaoEpiDao.findEntregaEpi(empresa.getId(), DateUtil.criarDataMesAno(01, 01, 2011), DateUtil.criarDataMesAno(01, 03, 2020), null, colaboradorCheck, agruparPor);
		
		assertEquals(1, colecao.size());
	}
	
	public void testGetCount()
	{
		Date dataIni=DateUtil.criarDataMesAno(01, 02, 2010);
		Date dataFim=DateUtil.criarDataMesAno(01, 02, 2010);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setMatricula("132");
		colaborador.setNome("José");
		colaboradorDao.save(colaborador);

		Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);
		
		Epi epi = EpiFactory.getEntity();
    	epi.setEmpresa(empresa);
    	epiDao.save(epi);

		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setValidadeUso(6);
		epiHistorico.setEpi(epi);
		epiHistorico.setData(dataIni);
		epiHistoricoDao.save(epiHistorico);
		
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi.setEmpresa(empresa);
		solicitacaoEpi.setData(dataIni);
		solicitacaoEpi.setColaborador(colaborador);
		solicitacaoEpi.setEntregue(true);
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		assertEquals(1, solicitacaoEpiDao.getCount(empresa.getId(), dataIni, dataFim, colaborador, true).intValue());
	}
	
	public void testFindAllSelect()
	{
		Date dataIni=DateUtil.criarDataMesAno(01, 02, 2010);
		Date dataMeio=DateUtil.criarDataMesAno(13, 03, 2010);
		Date dataFim=DateUtil.criarDataMesAno(01, 04, 2010);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("José");
		colaboradorDao.save(colaborador);

		Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);
		
		Epi epi = EpiFactory.getEntity();
    	epi.setEmpresa(empresa);
    	epiDao.save(epi);
    	
    	SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi.setEmpresa(empresa);
		solicitacaoEpi.setData(dataIni);
		solicitacaoEpi.setColaborador(colaborador);
		solicitacaoEpi.setEntregue(true);
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		SolicitacaoEpi solicitacaoEpi2 = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi2.setEmpresa(empresa);
		solicitacaoEpi2.setData(dataMeio);
		solicitacaoEpi2.setColaborador(colaborador);
		solicitacaoEpi2.setEntregue(true);
		solicitacaoEpiDao.save(solicitacaoEpi2);
		
		assertEquals(2, solicitacaoEpiDao.findAllSelect(1, 2, empresa.getId(), dataIni, dataFim, colaborador, true).size());
	}

	public void setEpiDao(EpiDao epiDao)
	{
		this.epiDao = epiDao;
	}

	public void setEpiHistoricoDao(EpiHistoricoDao epiHistoricoDao)
	{
		this.epiHistoricoDao = epiHistoricoDao;
	}

	public void setCargoDao(CargoDao cargoDao)
	{
		this.cargoDao = cargoDao;
	}

	public void setSolicitacaoEpiItemDao(SolicitacaoEpiItemDao solicitacaoEpiItemDao)
	{
		this.solicitacaoEpiItemDao = solicitacaoEpiItemDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao) {
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setTipoEPIDao(TipoEPIDao tipoEPIDao) {
		this.tipoEPIDao = tipoEPIDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao) {
		this.estabelecimentoDao = estabelecimentoDao;
	}

	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao) {
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

}