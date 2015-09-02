package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
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
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemEntregaDao;
import com.fortes.rh.dao.sesmt.TipoEPIDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.SituacaoSolicitacaoEpi;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
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
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiItemEntregaFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiItemFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;

public class SolicitacaoEpiDaoHibernateTest extends GenericDaoHibernateTest<SolicitacaoEpi>
{
	SolicitacaoEpiDao solicitacaoEpiDao;
	SolicitacaoEpiItemDao solicitacaoEpiItemDao;
	SolicitacaoEpiItemEntregaDao solicitacaoEpiItemEntregaDao;
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
		Date hoje = DateUtil.criarDataMesAno(20, 3, 2012);
		Date dataSeisMesesAtras = DateUtil.criarDataMesAno(20, 9, 2011);

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
		epiHistorico.setData(dataSeisMesesAtras);
		epiHistorico.setVencimentoCA(dataSeisMesesAtras);
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
		solicitacaoEpi.setData(dataSeisMesesAtras);
		solicitacaoEpi.setColaborador(colaborador);
		solicitacaoEpi.setCargo(cargo);
		solicitacaoEpiDao.save(solicitacaoEpi);

		SolicitacaoEpiItem solicitacaoEpiItem = new SolicitacaoEpiItem();
		solicitacaoEpiItem.setEpi(epi);
		solicitacaoEpiItem.setSolicitacaoEpi(solicitacaoEpi);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem);
		
		SolicitacaoEpiItemEntrega entrega = SolicitacaoEpiItemEntregaFactory.getEntity();
		entrega.setEpiHistorico(epiHistorico);
		entrega.setSolicitacaoEpiItem(solicitacaoEpiItem);
		entrega.setDataEntrega(dataSeisMesesAtras);
		entrega.setQtdEntregue(3);
		solicitacaoEpiItemEntregaDao.save(entrega);

		SolicitacaoEpiItemEntrega entrega2 = SolicitacaoEpiItemEntregaFactory.getEntity();
		entrega2.setEpiHistorico(epiHistorico);
		entrega2.setSolicitacaoEpiItem(solicitacaoEpiItem);
		entrega2.setDataEntrega(dataSeisMesesAtras);
		entrega2.setQtdEntregue(2);
		solicitacaoEpiItemEntregaDao.save(entrega2);

		Long[] tipoEPIIds = {tipoEPI.getId()};
		Long[] areasIds = {areaOrganizacional.getId()};
		Long[] estabelecimentoIds = {estabelecimento.getId()};
		
		Collection<SolicitacaoEpi> colecao = solicitacaoEpiDao.findVencimentoEpi(empresa.getId(), hoje, false, tipoEPIIds, areasIds, estabelecimentoIds, 'C');

		assertEquals(2, colecao.size());
		assertEquals(dataSeisMesesAtras, ((SolicitacaoEpi)colecao.toArray()[0]).getVencimentoCA());
	}

	public void testFindEntregaEpi()
	{
		Date hoje = DateUtil.criarDataMesAno(2, 2, 2012);
		Date vencCA = DateUtil.criarDataMesAno(2, 5, 2012);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setDesligado(false);
		colaborador.setNome("nometeste");
		colaboradorDao.save(colaborador);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(hoje);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorDao.save(historicoColaborador);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("cargo");
		cargoDao.save(cargo);
		
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi.setData(hoje);
		solicitacaoEpi.setColaborador(colaborador);
		solicitacaoEpi.setCargo(cargo);
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		Epi epi = EpiFactory.getEntity();
		epi.setEmpresa(empresa);
		epi.setNome("teste");
		epiDao.save(epi);
		
		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setEpi(epi);
		epiHistorico.setVencimentoCA(vencCA);
		epiHistoricoDao.save(epiHistorico);
		
		SolicitacaoEpiItem solicitacaoEpiItem = new SolicitacaoEpiItem();
		solicitacaoEpiItem.setSolicitacaoEpi(solicitacaoEpi);
		solicitacaoEpiItem.setEpi(epi);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem);
		
		SolicitacaoEpiItemEntrega entrega = SolicitacaoEpiItemEntregaFactory.getEntity();
		entrega.setEpiHistorico(epiHistorico);
		entrega.setSolicitacaoEpiItem(solicitacaoEpiItem);
		entrega.setDataEntrega(hoje);
		entrega.setQtdEntregue(3);
		solicitacaoEpiItemEntregaDao.save(entrega);
		
		Long[] epiCheck = {epi.getId()};
		char agruparPor = 'E';//agrupar por EPI
		
		Collection<SolicitacaoEpiItemEntrega> colecao = solicitacaoEpiDao.findEntregaEpi(empresa.getId(), DateUtil.criarDataMesAno(01, 01, 2011), DateUtil.criarDataMesAno(01, 03, 2020), epiCheck, null, null, agruparPor, false);
		
		assertEquals(1, colecao.size());
		assertEquals(vencCA, ((SolicitacaoEpiItemEntrega)colecao.toArray()[0]).getEpiHistorico().getVencimentoCA());
	}
	
	public void montaSaveSolicitacaoEpiEntregue(Empresa empresa, Colaborador colaborador, AreaOrganizacional areaOrganizacional, Cargo cargo, Date hoje, Date vencCA) {
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi.setData(hoje);
		solicitacaoEpi.setColaborador(colaborador);
		solicitacaoEpi.setCargo(cargo);
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		Epi epi = EpiFactory.getEntity();
		epi.setEmpresa(empresa);
		epi.setNome("teste");
		epiDao.save(epi);
		
		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setEpi(epi);
		epiHistorico.setVencimentoCA(vencCA);
		epiHistoricoDao.save(epiHistorico);
		
		SolicitacaoEpiItem solicitacaoEpiItem = new SolicitacaoEpiItem();
		solicitacaoEpiItem.setSolicitacaoEpi(solicitacaoEpi);
		solicitacaoEpiItem.setEpi(epi);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem);
		
		SolicitacaoEpiItemEntrega entrega = SolicitacaoEpiItemEntregaFactory.getEntity();
		entrega.setEpiHistorico(epiHistorico);
		entrega.setSolicitacaoEpiItem(solicitacaoEpiItem);
		entrega.setDataEntrega(hoje);
		entrega.setQtdEntregue(3);
		solicitacaoEpiItemEntregaDao.save(entrega);
	}
	
	public void testFindEntregaEpiComArea()
	{
		Date hoje = DateUtil.criarDataMesAno(2, 2, 2012);
		Date vencCA = DateUtil.criarDataMesAno(2, 5, 2012);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setDesligado(false);
		colaborador.setNome("nometeste");
		colaboradorDao.save(colaborador);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(hoje);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorDao.save(historicoColaborador);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("cargo");
		cargoDao.save(cargo);
		
		montaSaveSolicitacaoEpiEntregue(empresa, colaborador, areaOrganizacional, cargo, hoje, vencCA);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setDesligado(false);
		colaborador2.setNome("nometeste");
		colaboradorDao.save(colaborador2);
		
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional2);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setData(hoje);
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional2);
		historicoColaboradorDao.save(historicoColaborador2);
		
		Cargo cargo2 = CargoFactory.getEntity();
		cargo2.setNome("cargo");
		cargoDao.save(cargo2);
		
		montaSaveSolicitacaoEpiEntregue(empresa, colaborador2, areaOrganizacional2, cargo2, hoje, vencCA);
		
		Long[] areaCheck = {areaOrganizacional.getId()};
		char agruparPor = 'E';//agrupar por EPI
		
		Collection<SolicitacaoEpiItemEntrega> colecao = solicitacaoEpiDao.findEntregaEpi(empresa.getId(), DateUtil.criarDataMesAno(01, 01, 2011), DateUtil.criarDataMesAno(01, 03, 2020), null, areaCheck, null, agruparPor, false);
		
		assertEquals(1, colecao.size());
		assertEquals(vencCA, ((SolicitacaoEpiItemEntrega)colecao.toArray()[0]).getEpiHistorico().getVencimentoCA());
	}
	
	public void testFindEntregaEpiComColaborador()
	{
		Date hoje = DateUtil.criarDataMesAno(28, 2, 2012);
		Date data1 = DateUtil.criarDataMesAno(1, 3, 2012);
		Date data2 = DateUtil.criarDataMesAno(2, 3, 2012);
		Date data3 = DateUtil.criarDataMesAno(3, 3, 2012);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setDesligado(false);
		colaborador.setNome("nometeste");
		colaboradorDao.save(colaborador);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("cargo");
		cargoDao.save(cargo);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(hoje);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorDao.save(historicoColaborador);
		
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi.setData(hoje);
		solicitacaoEpi.setColaborador(colaborador);
		solicitacaoEpi.setCargo(cargo);
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setValidadeUso(6);
		epiHistorico.setData(hoje);
		epiHistorico.setVencimentoCA(hoje);
		epiHistoricoDao.save(epiHistorico);
		
		Collection<EpiHistorico> epiHistoricos = new ArrayList<EpiHistorico>();
		epiHistoricos.add(epiHistorico);
		
		Epi epi = EpiFactory.getEntity();
		epi.setEmpresa(empresa);
		epi.setNome("teste");
		epi.setEpiHistoricos(epiHistoricos);
		epiDao.save(epi);
		
		SolicitacaoEpiItem item1 = new SolicitacaoEpiItem();
		item1.setSolicitacaoEpi(solicitacaoEpi);
		item1.setQtdSolicitado(6);
		item1.setEpi(epi);
		solicitacaoEpiItemDao.save(item1);

		SolicitacaoEpiItem item2 = new SolicitacaoEpiItem();
		item2.setSolicitacaoEpi(solicitacaoEpi);
		item2.setQtdSolicitado(5);
		item2.setEpi(epi);
		solicitacaoEpiItemDao.save(item2);
		
		SolicitacaoEpiItemEntrega entrega1 = SolicitacaoEpiItemEntregaFactory.getEntity();
		entrega1.setEpiHistorico(epiHistorico);
		entrega1.setSolicitacaoEpiItem(item1);
		entrega1.setDataEntrega(data1);
		entrega1.setQtdEntregue(3);
		solicitacaoEpiItemEntregaDao.save(entrega1);

		SolicitacaoEpiItemEntrega entrega2 = SolicitacaoEpiItemEntregaFactory.getEntity();
		entrega2.setEpiHistorico(epiHistorico);
		entrega2.setSolicitacaoEpiItem(item1);
		entrega2.setDataEntrega(data2);
		entrega2.setQtdEntregue(4);
		solicitacaoEpiItemEntregaDao.save(entrega2);

		SolicitacaoEpiItemEntrega entrega3 = SolicitacaoEpiItemEntregaFactory.getEntity();
		entrega3.setEpiHistorico(epiHistorico);
		entrega3.setSolicitacaoEpiItem(item2);
		entrega3.setDataEntrega(data3);
		entrega3.setQtdEntregue(5);
		solicitacaoEpiItemEntregaDao.save(entrega3);
		
		Long[] colaboradorCheck = {colaborador.getId()};
		Long[] areaCheck = {areaOrganizacional.getId()};
		char agruparPor = 'C';//agrupar por colaborador
		
		Collection<SolicitacaoEpiItemEntrega> entregasParciais = solicitacaoEpiDao.findEntregaEpi(empresa.getId(), hoje, data2, null, areaCheck, colaboradorCheck, agruparPor, false);
		Collection<SolicitacaoEpiItemEntrega> entregasTotais = solicitacaoEpiDao.findEntregaEpi(empresa.getId(), hoje, null, null, null, colaboradorCheck, agruparPor, false);
		
		assertEquals(2, entregasParciais.size());
		assertEquals(3, entregasTotais.size());
		assertEquals(hoje, ((SolicitacaoEpiItemEntrega)entregasParciais.toArray()[0]).getEpiHistorico().getVencimentoCA());
	}
	
	public void testGetCount()
	{
		Date dataIni=DateUtil.criarDataMesAno(01, 02, 2010);
		Date dataFim=DateUtil.criarDataMesAno(01, 02, 2010);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setMatricula("132");
		colaborador.setNome("José");
		colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2010));
		historicoColaboradorDao.save(historicoColaborador);

		Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);
		
    	TipoEPI tipoEPI = new TipoEPI();
    	tipoEPI.setNome("tipo Epi");
    	tipoEPIDao.save(tipoEPI);

    	TipoEPI tipoEPI2 = new TipoEPI();
    	tipoEPI2.setNome("tipo Epi 2");
    	tipoEPIDao.save(tipoEPI2);
    	
		Epi epi = EpiFactory.getEntity();
		epi.setTipoEPI(tipoEPI);
    	epi.setEmpresa(empresa);
    	epiDao.save(epi);

    	Epi epi2 = EpiFactory.getEntity();
    	epi2.setTipoEPI(tipoEPI2);
    	epi2.setEmpresa(empresa);
    	epiDao.save(epi2);

		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setValidadeUso(6);
		epiHistorico.setEpi(epi);
		epiHistorico.setData(dataIni);
		epiHistoricoDao.save(epiHistorico);
		
		EpiHistorico epiHistorico2 = new EpiHistorico();
		epiHistorico2.setValidadeUso(6);
		epiHistorico2.setEpi(epi2);
		epiHistorico2.setData(dataIni);
		epiHistoricoDao.save(epiHistorico2);
		
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi.setEmpresa(empresa);
		solicitacaoEpi.setData(dataIni);
		solicitacaoEpi.setColaborador(colaborador);
		solicitacaoEpi.setEstabelecimento(estabelecimento1);
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		SolicitacaoEpi solicitacaoEpi2 = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi2.setEmpresa(empresa);
		solicitacaoEpi2.setData(dataIni);
		solicitacaoEpi2.setColaborador(colaborador);
		solicitacaoEpi2.setEstabelecimento(estabelecimento2);
		solicitacaoEpiDao.save(solicitacaoEpi2);
		
		SolicitacaoEpiItem item = SolicitacaoEpiItemFactory.getEntity();
		item.setSolicitacaoEpi(solicitacaoEpi);
		item.setQtdSolicitado(3);
		item.setEpi(epi);
		solicitacaoEpiItemDao.save(item);
		
		SolicitacaoEpiItem item2 = SolicitacaoEpiItemFactory.getEntity();
		item2.setSolicitacaoEpi(solicitacaoEpi2);
		item2.setQtdSolicitado(3);
		item2.setEpi(epi2);
		solicitacaoEpiItemDao.save(item2);
		
		SolicitacaoEpiItemEntrega entrega = SolicitacaoEpiItemEntregaFactory.getEntity();
		entrega.setEpiHistorico(epiHistorico);
		entrega.setSolicitacaoEpiItem(item);
		entrega.setDataEntrega(dataIni);
		entrega.setQtdEntregue(3);
		solicitacaoEpiItemEntregaDao.save(entrega);
		
		SolicitacaoEpiItemEntrega entrega2 = SolicitacaoEpiItemEntregaFactory.getEntity();
		entrega2.setEpiHistorico(epiHistorico2);
		entrega2.setSolicitacaoEpiItem(item2);
		entrega2.setDataEntrega(dataIni);
		entrega2.setQtdEntregue(3);
		solicitacaoEpiItemEntregaDao.save(entrega2);
		
		solicitacaoEpiDao.findByIdProjection(solicitacaoEpi.getId());
		
		String[] estabelecimentos = null;
		assertEquals(1, solicitacaoEpiDao.getCount(empresa.getId(), dataIni, dataFim, colaborador, SituacaoSolicitacaoEpi.ENTREGUE, tipoEPI.getId(), SituacaoColaborador.TODOS, LongUtil.arrayStringToArrayLong(estabelecimentos), 'D').intValue());
		assertEquals(1, solicitacaoEpiDao.getCount(empresa.getId(), dataIni, dataFim, colaborador, SituacaoSolicitacaoEpi.ENTREGUE, tipoEPI.getId(), SituacaoColaborador.TODOS, LongUtil.arrayStringToArrayLong(estabelecimentos), 'N').intValue());
		
		estabelecimentos = new String[]{estabelecimento1.getId().toString()};
		assertEquals(1, solicitacaoEpiDao.getCount(empresa.getId(), dataIni, dataFim, colaborador, SituacaoSolicitacaoEpi.ENTREGUE, tipoEPI.getId(), SituacaoColaborador.TODOS, LongUtil.arrayStringToArrayLong(estabelecimentos), 'D').intValue());
		assertEquals(1, solicitacaoEpiDao.getCount(empresa.getId(), dataIni, dataFim, colaborador, SituacaoSolicitacaoEpi.ENTREGUE, tipoEPI.getId(), SituacaoColaborador.TODOS, LongUtil.arrayStringToArrayLong(estabelecimentos), 'N').intValue());
	}
	
	public void testFindAllSelect()
	{
		Date dataIni=DateUtil.criarDataMesAno(01, 02, 2010);
		Date dataMeio=DateUtil.criarDataMesAno(13, 03, 2010);
		Date dataFim=DateUtil.criarDataMesAno(01, 04, 2010);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		Estabelecimento estabelecimento3 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento3);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("José");
		colaborador.setDesligado(true);
		colaboradorDao.save(colaborador);

		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setNome("");
		colaborador1.setMatricula("");
		colaborador1.setDesligado(false);
		colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setNome("Chico");
		colaborador2.setDesligado(false);
		colaboradorDao.save(colaborador2);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2010));
		historicoColaboradorDao.save(historicoColaborador);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaborador2.setMotivo(null);
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setData(DateUtil.criarDataMesAno(01, 01, 2010));
		historicoColaboradorDao.save(historicoColaborador2);

		Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);
		
		Epi epi = EpiFactory.getEntity();
    	epi.setEmpresa(empresa);
    	epiDao.save(epi);

    	Cargo cargo = CargoFactory.getEntity();
    	cargo.setNome("motorista");
    	cargoDao.save(cargo);
    	
    	SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi.setEmpresa(empresa);
		solicitacaoEpi.setData(dataIni);
		solicitacaoEpi.setColaborador(colaborador);
		solicitacaoEpi.setCargo(cargo);
		solicitacaoEpi.setEstabelecimento(estabelecimento1);
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		SolicitacaoEpiItem item = SolicitacaoEpiItemFactory.getEntity();
		item.setSolicitacaoEpi(solicitacaoEpi);
		item.setQtdSolicitado(3);
		solicitacaoEpiItemDao.save(item);
		
		SolicitacaoEpiItemEntrega entrega = SolicitacaoEpiItemEntregaFactory.getEntity();
		entrega.setSolicitacaoEpiItem(item);
		entrega.setDataEntrega(dataIni);
		entrega.setQtdEntregue(3);
		solicitacaoEpiItemEntregaDao.save(entrega);
		
		SolicitacaoEpi solicitacaoEpi2 = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi2.setEmpresa(empresa);
		solicitacaoEpi2.setData(dataMeio);
		solicitacaoEpi2.setColaborador(colaborador);
		solicitacaoEpi2.setCargo(cargo);
		solicitacaoEpi2.setEstabelecimento(estabelecimento2);
		solicitacaoEpiDao.save(solicitacaoEpi2);
		
		SolicitacaoEpiItem item2 = SolicitacaoEpiItemFactory.getEntity();
		item2.setSolicitacaoEpi(solicitacaoEpi2);
		item2.setQtdSolicitado(2);
		solicitacaoEpiItemDao.save(item2);
		
		SolicitacaoEpiItemEntrega entrega2 = SolicitacaoEpiItemEntregaFactory.getEntity();
		entrega2.setSolicitacaoEpiItem(item2);
		entrega2.setDataEntrega(dataIni);
		entrega2.setQtdEntregue(2);
		solicitacaoEpiItemEntregaDao.save(entrega2);

		SolicitacaoEpi solicitacaoEpi3 = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi3.setEmpresa(empresa);
		solicitacaoEpi3.setData(dataIni);
		solicitacaoEpi3.setColaborador(colaborador2);
		solicitacaoEpi3.setCargo(cargo);
		solicitacaoEpi3.setEstabelecimento(estabelecimento3);
		solicitacaoEpiDao.save(solicitacaoEpi3);
		
		SolicitacaoEpiItem item3 = SolicitacaoEpiItemFactory.getEntity();
		item3.setSolicitacaoEpi(solicitacaoEpi3);
		item3.setQtdSolicitado(3);
		solicitacaoEpiItemDao.save(item3);
		
		solicitacaoEpiDao.findByIdProjection(solicitacaoEpi.getId());
		
		String[] estabelecimentos = new String[]{};
		assertEquals(2, solicitacaoEpiDao.findAllSelect(1, 2, empresa.getId(), dataIni, dataFim, colaborador1, SituacaoSolicitacaoEpi.ENTREGUE, null, SituacaoColaborador.TODOS, LongUtil.arrayStringToArrayLong(estabelecimentos ), 'D').size());
		assertEquals(0, solicitacaoEpiDao.findAllSelect(1, 2, empresa.getId(), dataIni, dataFim, colaborador2, SituacaoSolicitacaoEpi.ENTREGUE, null, SituacaoColaborador.ATIVO, LongUtil.arrayStringToArrayLong(estabelecimentos), 'D').size());
		assertEquals(2, solicitacaoEpiDao.findAllSelect(1, 2, empresa.getId(), dataIni, dataFim, colaborador, SituacaoSolicitacaoEpi.ENTREGUE, null, SituacaoColaborador.DESLIGADO, LongUtil.arrayStringToArrayLong(estabelecimentos), 'D').size());
		assertEquals(1, solicitacaoEpiDao.findAllSelect(1, 2, empresa.getId(), dataIni, dataFim, colaborador1, SituacaoSolicitacaoEpi.ABERTA, null, SituacaoColaborador.ATIVO, LongUtil.arrayStringToArrayLong(estabelecimentos), 'D').size());
		
		assertEquals(2, solicitacaoEpiDao.findAllSelect(1, 2, empresa.getId(), dataIni, dataFim, colaborador1, SituacaoSolicitacaoEpi.ENTREGUE, null, SituacaoColaborador.TODOS, LongUtil.arrayStringToArrayLong(estabelecimentos ), 'N').size());
		assertEquals(0, solicitacaoEpiDao.findAllSelect(1, 2, empresa.getId(), dataIni, dataFim, colaborador2, SituacaoSolicitacaoEpi.ENTREGUE, null, SituacaoColaborador.ATIVO, LongUtil.arrayStringToArrayLong(estabelecimentos), 'N').size());
		assertEquals(2, solicitacaoEpiDao.findAllSelect(1, 2, empresa.getId(), dataIni, dataFim, colaborador, SituacaoSolicitacaoEpi.ENTREGUE, null, SituacaoColaborador.DESLIGADO, LongUtil.arrayStringToArrayLong(estabelecimentos), 'N').size());
		assertEquals(1, solicitacaoEpiDao.findAllSelect(1, 2, empresa.getId(), dataIni, dataFim, colaborador1, SituacaoSolicitacaoEpi.ABERTA, null, SituacaoColaborador.ATIVO, LongUtil.arrayStringToArrayLong(estabelecimentos), 'N').size());
		
		estabelecimentos = new String[]{estabelecimento1.getId().toString(), estabelecimento2.getId().toString()};
		assertEquals(2, solicitacaoEpiDao.findAllSelect(1, 2, empresa.getId(), dataIni, dataFim, colaborador, SituacaoSolicitacaoEpi.ENTREGUE, null, SituacaoColaborador.DESLIGADO, LongUtil.arrayStringToArrayLong(estabelecimentos), 'D').size());
		assertEquals(2, solicitacaoEpiDao.findAllSelect(1, 2, empresa.getId(), dataIni, dataFim, colaborador, SituacaoSolicitacaoEpi.ENTREGUE, null, SituacaoColaborador.DESLIGADO, LongUtil.arrayStringToArrayLong(estabelecimentos), 'N').size());
		
		estabelecimentos = new String[]{estabelecimento3.getId().toString()};
		assertEquals(1, solicitacaoEpiDao.findAllSelect(1, 2, empresa.getId(), dataIni, dataFim, colaborador2, SituacaoSolicitacaoEpi.ABERTA, null, SituacaoColaborador.ATIVO, LongUtil.arrayStringToArrayLong(estabelecimentos), 'D').size());
		assertEquals(1, solicitacaoEpiDao.findAllSelect(1, 2, empresa.getId(), dataIni, dataFim, colaborador2, SituacaoSolicitacaoEpi.ABERTA, null, SituacaoColaborador.ATIVO, LongUtil.arrayStringToArrayLong(estabelecimentos), 'N').size());
	}
	
	public void testFindEpisWithItens() 
	{
		Date dataIni=DateUtil.criarDataMesAno(01, 02, 2010);
		Date dataMeio=DateUtil.criarDataMesAno(13, 03, 2010);
		Date dataFim=DateUtil.criarDataMesAno(01, 04, 2010);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("José");
		colaborador.setDesligado(false);
		colaboradorDao.save(colaborador);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2010));
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(historicoColaborador);

		Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);
		
		Epi epi1 = EpiFactory.getEntity();
		epi1.setNome("Epi Nome 1");
    	epi1.setEmpresa(empresa);
    	epiDao.save(epi1);

    	Epi epi2 = EpiFactory.getEntity();
    	epi2.setNome("Epi Nome 2");
    	epi2.setEmpresa(empresa);
    	epiDao.save(epi2);
    	
    	Epi epi3 = EpiFactory.getEntity();
    	epi3.setNome("Epi Nome 3");
    	epi3.setEmpresa(empresa);
    	epiDao.save(epi3);
    	
    	Cargo cargo = CargoFactory.getEntity();
    	cargo.setNome("motorista");
    	cargoDao.save(cargo);
    	
    	SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi.setEmpresa(empresa);
		solicitacaoEpi.setData(dataIni);
		solicitacaoEpi.setColaborador(colaborador);
		solicitacaoEpi.setCargo(cargo);
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		SolicitacaoEpiItem item = SolicitacaoEpiItemFactory.getEntity();
		item.setSolicitacaoEpi(solicitacaoEpi);
		item.setEpi(epi1);
		item.setQtdSolicitado(3);
		solicitacaoEpiItemDao.save(item);
		
		SolicitacaoEpi solicitacaoEpi2 = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi2.setEmpresa(empresa);
		solicitacaoEpi2.setData(dataMeio);
		solicitacaoEpi2.setColaborador(colaborador);
		solicitacaoEpi2.setCargo(cargo);
		solicitacaoEpiDao.save(solicitacaoEpi2);
		
		SolicitacaoEpiItem item2 = SolicitacaoEpiItemFactory.getEntity();
		item2.setSolicitacaoEpi(solicitacaoEpi2);
		item2.setEpi(epi2);
		item2.setQtdSolicitado(2);
		solicitacaoEpiItemDao.save(item2);
		
		SolicitacaoEpiItem item3 = SolicitacaoEpiItemFactory.getEntity();
		item3.setSolicitacaoEpi(solicitacaoEpi2);
		item3.setEpi(epi3);
		item3.setQtdSolicitado(3);
		solicitacaoEpiItemDao.save(item3);
//		
//		Collection<SolicitacaoEpiItemVO> lista = solicitacaoEpiDao.findEpisWithItens(empresa.getId(), dataIni, dataFim, 'A', null, null, SituacaoColaborador.TODOS);
//		SolicitacaoEpiItemVO vo1 = (SolicitacaoEpiItemVO) lista.toArray()[0];		
//		SolicitacaoEpiItemVO vo2 = (SolicitacaoEpiItemVO) lista.toArray()[1];		
//		SolicitacaoEpiItemVO vo3 = (SolicitacaoEpiItemVO) lista.toArray()[2];		
//		
//		assertEquals(3, lista.size());
//		assertEquals(item2.getQtdSolicitado(), vo1.getQtdSolicitadoItem());
//		assertEquals(item3.getQtdSolicitado(), vo2.getQtdSolicitadoItem());
//		assertEquals(item.getQtdSolicitado(), vo3.getQtdSolicitadoItem());
//		assertEquals(new Integer(5), vo1.getQtdSolicitadoTotal());
//		assertEquals(new Integer(5), vo2.getQtdSolicitadoTotal());
//		assertEquals(new Integer(3), vo3.getQtdSolicitadoTotal());
	}

	
	public void testFindByColaboradorId()
	{
		Date data1=DateUtil.criarDataMesAno(01, 02, 2010);
		Date data2=DateUtil.criarDataMesAno(13, 03, 2010);

		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setNome("João");
		colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setNome("Chico");
		colaboradorDao.save(colaborador2);

    	SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi.setData(data1);
		solicitacaoEpi.setColaborador(colaborador1);
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		SolicitacaoEpi solicitacaoEpi2 = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi2.setData(data2);
		solicitacaoEpi2.setColaborador(colaborador1);
		solicitacaoEpiDao.save(solicitacaoEpi2);
		
		SolicitacaoEpi solicitacaoEpi3 = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi3.setData(data1);
		solicitacaoEpi3.setColaborador(colaborador2);
		solicitacaoEpiDao.save(solicitacaoEpi3);
		
		Collection<SolicitacaoEpi> solicitacaoEpis = solicitacaoEpiDao.findByColaboradorId(colaborador1.getId());
		assertEquals(2, solicitacaoEpis.size());
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

	public void setSolicitacaoEpiItemEntregaDao(SolicitacaoEpiItemEntregaDao solicitacaoEpiItemEntregaDao) {
		this.solicitacaoEpiItemEntregaDao = solicitacaoEpiItemEntregaDao;
	}

}