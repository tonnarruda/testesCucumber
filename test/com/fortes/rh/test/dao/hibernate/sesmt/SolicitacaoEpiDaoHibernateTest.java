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
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDevolucaoDao;
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
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.rh.model.sesmt.TipoEPI;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoEpiItemVO;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoEpiVO;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiItemDevolucaoFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiItemEntregaFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiItemFactory;
import com.fortes.rh.test.factory.sesmt.TipoEpiFactory;
import com.fortes.rh.util.DateUtil;

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
	SolicitacaoEpiItemDevolucaoDao solicitacaoEpiItemDevolucaoDao;

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
		
		solicitacaoEpiDao.getHibernateTemplateByGenericDao().flush();
		
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
	
	private Colaborador saveColaboradorComHistorico(String nomeDoColaborador, String matricula, boolean desligado, Date dataHistorico, Integer statusHistorico){
		Colaborador colaborador = ColaboradorFactory.getEntity(nomeDoColaborador, desligado, matricula);
		colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, dataHistorico, statusHistorico);
		historicoColaboradorDao.save(historicoColaborador);
		
		return colaborador;
	} 
	
	public void testFindAllSelectTodas(){
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Colaborador colaborador1 = saveColaboradorComHistorico("Colaborador 1", null, true,  DateUtil.criarDataMesAno(01, 01, 2010), StatusRetornoAC.CONFIRMADO);

		Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);
		
    	TipoEPI tipoEPI = TipoEpiFactory.getEntity();
    	tipoEPIDao.save(tipoEPI);
    	
    	Epi epi = EpiFactory.getEntity(tipoEPI);
    	epiDao.save(epi);

    	Cargo cargo = CargoFactory.getEntity("Motorista");
    	cargoDao.save(cargo);
    	
    	SolicitacaoEpi solicitacaoEpiEntregue = SolicitacaoEpiFactory.getEntity(DateUtil.criarDataMesAno(01, 02, 2010), colaborador1, empresa, estabelecimento1, cargo);
		solicitacaoEpiDao.save(solicitacaoEpiEntregue);
		
		SolicitacaoEpiItem solicitacaoEpiItem_solicitacaoEpiEntregue = SolicitacaoEpiItemFactory.getEntity(solicitacaoEpiEntregue, epi, 3);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem_solicitacaoEpiEntregue);
		
		SolicitacaoEpiItemEntrega entrega = SolicitacaoEpiItemEntregaFactory.getEntity(DateUtil.criarDataMesAno(01, 02, 2010), solicitacaoEpiItem_solicitacaoEpiEntregue, 3);
		solicitacaoEpiItemEntregaDao.save(entrega);
		
		SolicitacaoEpi solicitacaoEpiAberta = SolicitacaoEpiFactory.getEntity(DateUtil.criarDataMesAno(13, 03, 2010), colaborador1, empresa, estabelecimento1, cargo);
		solicitacaoEpiDao.save(solicitacaoEpiAberta);
		
		SolicitacaoEpiItem solicitacaoEpiItem_solicitacaoEpiAberta = SolicitacaoEpiItemFactory.getEntity(solicitacaoEpiAberta, epi, 3);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem_solicitacaoEpiAberta);
		
		solicitacaoEpiDao.findByIdProjection(solicitacaoEpiEntregue.getId());
		SolicitacaoEpiVO solicitacaoEpiVO = solicitacaoEpiDao.findAllSelect(1, 2, empresa.getId(), null, null, new Colaborador(), 
				SituacaoSolicitacaoEpi.TODAS, null, SituacaoColaborador.TODOS, null, 'N');
		
		assertEquals(new Integer(2), solicitacaoEpiVO.getQtdSolicitacaoEpis());
	}
	
	public void testFindAllSelectSolicitacoesAbertas() {
		Date dataIni=DateUtil.criarDataMesAno(01, 02, 2010);
		Date dataMeio=DateUtil.criarDataMesAno(13, 03, 2010);
		Date dataFim=DateUtil.criarDataMesAno(01, 04, 2010);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Colaborador colaborador1 = saveColaboradorComHistorico("Colaborador 1", null, false,  DateUtil.criarDataMesAno(01, 01, 2010), StatusRetornoAC.CONFIRMADO);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);
		
    	TipoEPI tipoEPI = TipoEpiFactory.getEntity();
    	tipoEPIDao.save(tipoEPI);
    	
		Epi epi = EpiFactory.getEntity(tipoEPI);
    	epiDao.save(epi);

    	Cargo cargo = CargoFactory.getEntity("Motorista");
    	cargoDao.save(cargo);
    	
    	SolicitacaoEpi solicitacaoEpiEntregue = saveSolicitacaoEpi(dataIni,
				empresa, colaborador1, estabelecimento1, cargo);
		
		SolicitacaoEpiItem solicitacaoEpiItem_solicitacaoEpiEntregue = SolicitacaoEpiItemFactory.getEntity(solicitacaoEpiEntregue, epi, 3);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem_solicitacaoEpiEntregue);
		
		SolicitacaoEpiItemEntrega entrega = SolicitacaoEpiItemEntregaFactory.getEntity(dataIni, solicitacaoEpiItem_solicitacaoEpiEntregue, 3);
		solicitacaoEpiItemEntregaDao.save(entrega);
		
		SolicitacaoEpi solicitacaoEpiAberta = saveSolicitacaoEpi(dataMeio,
				empresa, colaborador1, estabelecimento1, cargo);
		
		SolicitacaoEpiItem solicitacaoEpiItem_solicitacaoEpiAberta = SolicitacaoEpiItemFactory.getEntity(solicitacaoEpiAberta, epi, 3);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem_solicitacaoEpiAberta);
		
		solicitacaoEpiDao.findByIdProjection(solicitacaoEpiEntregue.getId());
		SolicitacaoEpiVO solicitacaoEpiVO = solicitacaoEpiDao.findAllSelect(1, 2, empresa.getId(), dataIni, dataFim, colaborador1, SituacaoSolicitacaoEpi.ABERTA, tipoEPI.getId(), SituacaoColaborador.ATIVO, null, 'D');
		assertEquals(solicitacaoEpiAberta.getId(), solicitacaoEpiVO.getSolicitacaoEpis().iterator().next().getId());
		assertEquals(new Integer(1), solicitacaoEpiVO.getQtdSolicitacaoEpis());
	}
	
	public void testFindAllSelectSolicitacoesEntreguesOrdenacaoPorNome(){
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Colaborador colaborador1 = saveColaboradorComHistorico("Colaborador 1", null, true,  DateUtil.criarDataMesAno(01, 01, 2010), StatusRetornoAC.CONFIRMADO);

		Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);
		
    	TipoEPI tipoEPI = TipoEpiFactory.getEntity();
    	tipoEPIDao.save(tipoEPI);
    	
    	Epi epi = EpiFactory.getEntity(tipoEPI);
    	epiDao.save(epi);

    	Cargo cargo = CargoFactory.getEntity("Motorista");
    	cargoDao.save(cargo);
    	
    	SolicitacaoEpi solicitacaoEpiEntregue = SolicitacaoEpiFactory.getEntity(DateUtil.criarDataMesAno(01, 02, 2010), colaborador1, empresa, estabelecimento1, cargo);
		solicitacaoEpiDao.save(solicitacaoEpiEntregue);
		
		SolicitacaoEpiItem solicitacaoEpiItem_solicitacaoEpiEntregue = SolicitacaoEpiItemFactory.getEntity(solicitacaoEpiEntregue, epi, 3);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem_solicitacaoEpiEntregue);
		
		SolicitacaoEpiItemEntrega entrega = SolicitacaoEpiItemEntregaFactory.getEntity(DateUtil.criarDataMesAno(01, 02, 2010), solicitacaoEpiItem_solicitacaoEpiEntregue, 3);
		solicitacaoEpiItemEntregaDao.save(entrega);
		
		SolicitacaoEpi solicitacaoEpiAberta = SolicitacaoEpiFactory.getEntity(DateUtil.criarDataMesAno(13, 03, 2010), colaborador1, empresa, estabelecimento1, cargo);
		solicitacaoEpiDao.save(solicitacaoEpiAberta);
		
		SolicitacaoEpiItem solicitacaoEpiItem_solicitacaoEpiAberta = SolicitacaoEpiItemFactory.getEntity(solicitacaoEpiAberta, epi, 3);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem_solicitacaoEpiAberta);
		
		solicitacaoEpiDao.findByIdProjection(solicitacaoEpiEntregue.getId());
		SolicitacaoEpiVO solicitacaoEpiVO = solicitacaoEpiDao.findAllSelect(1, 2, empresa.getId(), DateUtil.criarDataMesAno(01, 02, 2010), DateUtil.criarDataMesAno(01, 04, 2010), colaborador1, 
				SituacaoSolicitacaoEpi.ENTREGUE, tipoEPI.getId(), SituacaoColaborador.DESLIGADO, new Long[]{estabelecimento1.getId()}, 'N');
		
		assertEquals(solicitacaoEpiEntregue.getId(), solicitacaoEpiVO.getSolicitacaoEpis().iterator().next().getId());
		assertEquals(new Integer(1), solicitacaoEpiVO.getQtdSolicitacaoEpis());
	}
	
	public void testFindAllSelectSolicitacoesParcialmenteEntregue(){
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Colaborador colaborador1 = saveColaboradorComHistorico("Colaborador 1", "000123", true,  DateUtil.criarDataMesAno(01, 01, 2010), StatusRetornoAC.CONFIRMADO);

		Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);
		
    	TipoEPI tipoEPI = TipoEpiFactory.getEntity();
    	tipoEPIDao.save(tipoEPI);
    	
    	Epi epi = EpiFactory.getEntity(tipoEPI);
    	epiDao.save(epi);

    	Cargo cargo = CargoFactory.getEntity("Motorista");
    	cargoDao.save(cargo);
    	
    	SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(DateUtil.criarDataMesAno(01, 02, 2010), colaborador1, empresa, estabelecimento1, cargo);
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		SolicitacaoEpiItem solicitacaoEpiItem = SolicitacaoEpiItemFactory.getEntity(solicitacaoEpi, epi, 3);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem);
		
		SolicitacaoEpiItemEntrega entrega = SolicitacaoEpiItemEntregaFactory.getEntity(DateUtil.criarDataMesAno(01, 02, 2010), solicitacaoEpiItem, 2);
		solicitacaoEpiItemEntregaDao.save(entrega);
		
		solicitacaoEpiDao.findByIdProjection(solicitacaoEpi.getId());
		SolicitacaoEpiVO solicitacaoEpiVO = solicitacaoEpiDao.findAllSelect(1, 2, empresa.getId(), DateUtil.criarDataMesAno(01, 02, 2010), DateUtil.criarDataMesAno(01, 04, 2010), colaborador1, 
				SituacaoSolicitacaoEpi.ENTREGUE_PARCIALMENTE,tipoEPI.getId(), SituacaoColaborador.DESLIGADO, new Long[]{estabelecimento1.getId()}, 'N');
		
		assertEquals(solicitacaoEpi.getId(), solicitacaoEpiVO.getSolicitacaoEpis().iterator().next().getId());
		assertEquals(new Integer(1), solicitacaoEpiVO.getQtdSolicitacaoEpis());
	}
	
	public void testFindAllSelectSolicitacoesDevolvidas(){
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Colaborador colaborador1 = saveColaboradorComHistorico("Colaborador 1", null, true,  DateUtil.criarDataMesAno(01, 01, 2010), StatusRetornoAC.CONFIRMADO);

		Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);
		
    	TipoEPI tipoEPI = TipoEpiFactory.getEntity();
    	tipoEPIDao.save(tipoEPI);
    	
    	Epi epi = EpiFactory.getEntity(tipoEPI);
    	epiDao.save(epi);

    	Cargo cargo = CargoFactory.getEntity("Motorista");
    	cargoDao.save(cargo);
    	
    	SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(DateUtil.criarDataMesAno(01, 02, 2010), colaborador1, empresa, estabelecimento1, cargo);
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		SolicitacaoEpiItem solicitacaoEpiItem = SolicitacaoEpiItemFactory.getEntity(solicitacaoEpi, epi, 3);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem);
		
		SolicitacaoEpiItemEntrega entrega = SolicitacaoEpiItemEntregaFactory.getEntity(DateUtil.criarDataMesAno(01, 02, 2010), solicitacaoEpiItem, 3);
		solicitacaoEpiItemEntregaDao.save(entrega);
		
		SolicitacaoEpiItemDevolucao devolucao = SolicitacaoEpiItemDevolucaoFactory.getEntity(DateUtil.criarDataMesAno(01, 02, 2010), 3, solicitacaoEpiItem);
		solicitacaoEpiItemDevolucaoDao.save(devolucao);
		
		solicitacaoEpiDao.findByIdProjection(solicitacaoEpi.getId());
		SolicitacaoEpiVO solicitacaoEpiVO = solicitacaoEpiDao.findAllSelect(1, 2, empresa.getId(), DateUtil.criarDataMesAno(01, 02, 2010), DateUtil.criarDataMesAno(01, 04, 2010), new Colaborador(), 
				SituacaoSolicitacaoEpi.DEVOLVIDO, null, SituacaoColaborador.DESLIGADO, new Long[]{estabelecimento1.getId()}, 'N');
		
		assertEquals(solicitacaoEpi.getId(), solicitacaoEpiVO.getSolicitacaoEpis().iterator().next().getId());
		assertEquals(new Integer(1), solicitacaoEpiVO.getQtdSolicitacaoEpis());
	}
	
	public void testFindAllSelectSolicitacoesDevolvidasParcialmente(){
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Colaborador colaborador1 = saveColaboradorComHistorico("Colaborador 1", null, true,  DateUtil.criarDataMesAno(01, 01, 2010), StatusRetornoAC.CONFIRMADO);

		Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);
		
    	TipoEPI tipoEPI = TipoEpiFactory.getEntity();
    	tipoEPIDao.save(tipoEPI);
    	
    	Epi epi = EpiFactory.getEntity(tipoEPI);
    	epiDao.save(epi);

    	Cargo cargo = CargoFactory.getEntity("Motorista");
    	cargoDao.save(cargo);
    	
    	SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(DateUtil.criarDataMesAno(01, 02, 2010), colaborador1, empresa, estabelecimento1, cargo);
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		SolicitacaoEpiItem solicitacaoEpiItem = SolicitacaoEpiItemFactory.getEntity(solicitacaoEpi, epi, 3);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem);
		
		SolicitacaoEpiItemEntrega entrega = SolicitacaoEpiItemEntregaFactory.getEntity(DateUtil.criarDataMesAno(01, 02, 2010), solicitacaoEpiItem, 3);
		solicitacaoEpiItemEntregaDao.save(entrega);
		
		SolicitacaoEpiItemDevolucao devolucao = SolicitacaoEpiItemDevolucaoFactory.getEntity(DateUtil.criarDataMesAno(01, 02, 2010), 2, solicitacaoEpiItem);
		solicitacaoEpiItemDevolucaoDao.save(devolucao);
		
		solicitacaoEpiDao.findByIdProjection(solicitacaoEpi.getId());
		SolicitacaoEpiVO solicitacaoEpiVO = solicitacaoEpiDao.findAllSelect(1, 2, empresa.getId(), DateUtil.criarDataMesAno(01, 02, 2010), DateUtil.criarDataMesAno(01, 04, 2010), new Colaborador(), 
				SituacaoSolicitacaoEpi.DEVOLVIDO_PARCIALMENTE, null, SituacaoColaborador.TODOS, new Long[]{}, 'D');
		
		assertEquals(solicitacaoEpi.getId(), solicitacaoEpiVO.getSolicitacaoEpis().iterator().next().getId());
		assertEquals(new Integer(1), solicitacaoEpiVO.getQtdSolicitacaoEpis());
	}
	
	public void testFindAllSelectSolicitacoesSemDevolucao(){
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Colaborador colaborador1 = saveColaboradorComHistorico("Colaborador 1", null, true,  DateUtil.criarDataMesAno(01, 01, 2010), StatusRetornoAC.CONFIRMADO);

		Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);
		
    	TipoEPI tipoEPI = TipoEpiFactory.getEntity();
    	tipoEPIDao.save(tipoEPI);
    	
    	Epi epi = EpiFactory.getEntity(tipoEPI);
    	epiDao.save(epi);

    	Cargo cargo = CargoFactory.getEntity("Motorista");
    	cargoDao.save(cargo);
    	
    	SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(DateUtil.criarDataMesAno(01, 02, 2010), colaborador1, empresa, estabelecimento1, cargo);
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		SolicitacaoEpiItem solicitacaoEpiItem = SolicitacaoEpiItemFactory.getEntity(solicitacaoEpi, epi, 3);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem);
		
		SolicitacaoEpiItemEntrega entrega = SolicitacaoEpiItemEntregaFactory.getEntity(DateUtil.criarDataMesAno(01, 02, 2010), solicitacaoEpiItem, 3);
		solicitacaoEpiItemEntregaDao.save(entrega);
		
		solicitacaoEpiDao.findByIdProjection(solicitacaoEpi.getId());
		SolicitacaoEpiVO solicitacaoEpiVO = solicitacaoEpiDao.findAllSelect(1, 2, empresa.getId(), DateUtil.criarDataMesAno(01, 02, 2010), DateUtil.criarDataMesAno(01, 04, 2010), new Colaborador(), 
				SituacaoSolicitacaoEpi.SEM_DEVOLUCAO, null, SituacaoColaborador.TODOS, new Long[]{}, 'D');
		
		assertEquals(solicitacaoEpi.getId(), solicitacaoEpiVO.getSolicitacaoEpis().iterator().next().getId());
		assertEquals(new Integer(1), solicitacaoEpiVO.getQtdSolicitacaoEpis());
	}
	
	
	public void testFindEpisWithItens() 
	{
		Date dataIni=DateUtil.criarDataMesAno(01, 02, 2010);
		Date dataMeio=DateUtil.criarDataMesAno(13, 03, 2010);
		Date dataFim=DateUtil.criarDataMesAno(01, 04, 2010);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity("José");
		colaboradorDao.save(colaborador);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, DateUtil.criarDataMesAno(01, 01, 2010), StatusRetornoAC.CONFIRMADO);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(historicoColaborador);
		
		Epi epi1 = saveEpi(empresa, "Epi Nome 1");
    	Epi epi2 = saveEpi(empresa, "Epi Nome 2");
    	Epi epi3 = saveEpi(empresa, "Epi Nome 3");
    	Epi epi4 = saveEpi(empresa, "Epi Nome 4");
    	
    	Cargo cargo = CargoFactory.getEntity("Motorista");
    	cargoDao.save(cargo);
    	
    	SolicitacaoEpi solicitacaoEpi1 = saveSolicitacaoEpi(dataIni, empresa, colaborador, estabelecimento, cargo);
    	SolicitacaoEpi solicitacaoEpi2 =  saveSolicitacaoEpi(dataMeio, empresa, colaborador, estabelecimento, cargo);
    	SolicitacaoEpi solicitacaoEpi3 =  saveSolicitacaoEpi(dataFim, empresa, colaborador, estabelecimento, cargo);
    	
		SolicitacaoEpiItem item1 = saveSolicitacaoEpiItem(solicitacaoEpi1, epi1, 3);
		SolicitacaoEpiItem item2 = saveSolicitacaoEpiItem(solicitacaoEpi2, epi2, 2);
		SolicitacaoEpiItem item3 = saveSolicitacaoEpiItem(solicitacaoEpi2, epi3, 3);
		SolicitacaoEpiItem item4 = saveSolicitacaoEpiItem(solicitacaoEpi3, epi4, 3);
		
		saveSolicitacaoEpiItemEntrega(dataIni, item2, 2);
		saveSolicitacaoEpiItemDevolucao(DateUtil.criarDataMesAno(01, 06, 2010), item2, 2);

		saveSolicitacaoEpiItemEntrega(dataIni, item3, 3);
		saveSolicitacaoEpiItemDevolucao(DateUtil.criarDataMesAno(01, 06, 2010), item3, 3);

		saveSolicitacaoEpiItemEntrega(dataIni, item4, 2);
		
		Collection<SolicitacaoEpiItemVO> lista = solicitacaoEpiDao.findEpisWithItens(empresa.getId(), null, dataFim, SituacaoSolicitacaoEpi.TODAS, colaborador, null, SituacaoColaborador.TODOS, null, 'N');

		assertEquals(4, lista.size());
		
		for (SolicitacaoEpiItemVO solicitacaoEpiItemVO : lista) {
			if(solicitacaoEpiItemVO.getItemId().equals(item4.getId())){
				assertEquals(item4.getQtdSolicitado(), solicitacaoEpiItemVO.getQtdSolicitadoItem());
				assertEquals(new Integer(3), solicitacaoEpiItemVO.getQtdSolicitadoTotal());
				assertEquals("Sem Devolução", solicitacaoEpiItemVO.getSituacaoDescricaoDevolucao(), SituacaoSolicitacaoEpi.getSituacaoDescricaoDevolucao(solicitacaoEpiItemVO.getQtdDevolvida(), solicitacaoEpiItemVO.getQtdEntregue()));
			}else if(solicitacaoEpiItemVO.getItemId().equals(item2.getId())){
				assertEquals(item2.getQtdSolicitado(), solicitacaoEpiItemVO.getQtdSolicitadoItem());
				assertEquals(new Integer(5), solicitacaoEpiItemVO.getQtdSolicitadoTotal());
				assertEquals("Devolvido Parcialmente", solicitacaoEpiItemVO.getSituacaoDescricaoDevolucao(), SituacaoSolicitacaoEpi.getSituacaoDescricaoDevolucao(solicitacaoEpiItemVO.getQtdDevolvida(), solicitacaoEpiItemVO.getQtdEntregue()));
			}else if(solicitacaoEpiItemVO.getItemId().equals(item3.getId())){
				assertEquals(item3.getQtdSolicitado(), solicitacaoEpiItemVO.getQtdSolicitadoItem());
				assertEquals(new Integer(5), solicitacaoEpiItemVO.getQtdSolicitadoTotal());
				assertEquals("Devolvido", solicitacaoEpiItemVO.getSituacaoDescricaoDevolucao(), SituacaoSolicitacaoEpi.getSituacaoDescricaoDevolucao(solicitacaoEpiItemVO.getQtdDevolvida(), solicitacaoEpiItemVO.getQtdEntregue()));
			}else if(solicitacaoEpiItemVO.getItemId().equals(item4.getId())){
				assertEquals(item1.getQtdSolicitado(), solicitacaoEpiItemVO.getQtdSolicitadoItem());
				assertEquals(new Integer(3), solicitacaoEpiItemVO.getQtdSolicitadoTotal());
				assertEquals("Sem EPI a devolver", solicitacaoEpiItemVO.getSituacaoDescricaoDevolucao(), SituacaoSolicitacaoEpi.getSituacaoDescricaoDevolucao(solicitacaoEpiItemVO.getQtdDevolvida(), solicitacaoEpiItemVO.getQtdEntregue()));
			}
		}
	}

	private SolicitacaoEpi saveSolicitacaoEpi(Date dataIni, Empresa empresa, Colaborador colaborador, Estabelecimento estabelecimento, Cargo cargo) {
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(dataIni, colaborador, empresa, estabelecimento, cargo);
		solicitacaoEpiDao.save(solicitacaoEpi);
		return solicitacaoEpi;
	}

	private Epi saveEpi(Empresa empresa, String nome) {
		Epi epi = EpiFactory.getEntity(null, nome, empresa);
    	epiDao.save(epi);
		return epi;
	}
	
	private SolicitacaoEpiItem saveSolicitacaoEpiItem(SolicitacaoEpi solicitacaoEpi, Epi epi, Integer quantidadeSolicitada){
		SolicitacaoEpiItem item = SolicitacaoEpiItemFactory.getEntity(solicitacaoEpi, epi, quantidadeSolicitada);
		solicitacaoEpiItemDao.save(item);
		return item;
	}
	
	private SolicitacaoEpiItemEntrega saveSolicitacaoEpiItemEntrega(Date dataEntrega, SolicitacaoEpiItem item, Integer qtdEntregue){
		SolicitacaoEpiItemEntrega entregaItem = SolicitacaoEpiItemEntregaFactory.getEntity(dataEntrega, item, qtdEntregue);
		solicitacaoEpiItemEntregaDao.save(entregaItem);
		return entregaItem;
	}
	
	private SolicitacaoEpiItemDevolucao saveSolicitacaoEpiItemDevolucao(Date dataDevolucao, SolicitacaoEpiItem item, Integer qtdDevolvida){
		SolicitacaoEpiItemDevolucao devolucaoItem = SolicitacaoEpiItemDevolucaoFactory.getEntity(dataDevolucao, qtdDevolvida, item);
		solicitacaoEpiItemDevolucaoDao.save(devolucaoItem);
		return devolucaoItem;
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
	
	public void testFindDevolucaoEpi(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setDesligado(false);
		colaborador.setNome("nometeste");
		colaboradorDao.save(colaborador);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2014));
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorDao.save(historicoColaborador);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("cargo");
		cargoDao.save(cargo);
		
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity();
		solicitacaoEpi.setData(DateUtil.criarDataMesAno(01, 11, 2015));
		solicitacaoEpi.setColaborador(colaborador);
		solicitacaoEpi.setCargo(cargo);
		solicitacaoEpiDao.save(solicitacaoEpi);
		
		Epi epi = EpiFactory.getEntity();
		epi.setEmpresa(empresa);
		epi.setNome("teste");
		epiDao.save(epi);
		
		EpiHistorico epiHistorico = new EpiHistorico();
		epiHistorico.setEpi(epi);
		epiHistoricoDao.save(epiHistorico);
		
		SolicitacaoEpiItem solicitacaoEpiItem = new SolicitacaoEpiItem();
		solicitacaoEpiItem.setSolicitacaoEpi(solicitacaoEpi);
		solicitacaoEpiItem.setQtdSolicitado(3);
		solicitacaoEpiItem.setEpi(epi);
		solicitacaoEpiItemDao.save(solicitacaoEpiItem);
		
		SolicitacaoEpiItemEntrega entrega = SolicitacaoEpiItemEntregaFactory.getEntity();
		entrega.setEpiHistorico(epiHistorico);
		entrega.setSolicitacaoEpiItem(solicitacaoEpiItem);
		entrega.setDataEntrega(DateUtil.criarDataMesAno(15, 11, 2015));
		entrega.setQtdEntregue(3);
		solicitacaoEpiItemEntregaDao.save(entrega);

		SolicitacaoEpiItemDevolucao epiItemDevolucao = SolicitacaoEpiItemDevolucaoFactory.getEntity();
		epiItemDevolucao.setSolicitacaoEpiItem(solicitacaoEpiItem);
		epiItemDevolucao.setQtdDevolvida(2);
		epiItemDevolucao.setDataDevolucao(DateUtil.criarDataMesAno(01, 12, 2015));
		solicitacaoEpiItemDevolucaoDao.save(epiItemDevolucao);
		
		Long[] epiCheck = {epi.getId()};

		Collection<SolicitacaoEpiItemDevolucao> devolucoesComDataIgualADataDaDevolucao = solicitacaoEpiDao.findDevolucaoEpi(empresa.getId(), DateUtil.criarDataMesAno(01, 12, 2015), DateUtil.criarDataMesAno(01, 12, 2015), epiCheck, null, null, 'E', false);
		assertEquals(1, devolucoesComDataIgualADataDaDevolucao.size());
		assertEquals(2, ((SolicitacaoEpiItemDevolucao) devolucoesComDataIgualADataDaDevolucao.toArray()[0]).getQtdDevolvida().intValue());
		
		Collection<SolicitacaoEpiItemDevolucao> devolucoesComDataInicialAnteriorADataDaDevolucao = solicitacaoEpiDao.findDevolucaoEpi(empresa.getId(), DateUtil.criarDataMesAno(30, 11, 2015), DateUtil.criarDataMesAno(01, 12, 2015), epiCheck, null, null, 'E', false);
		assertEquals(1, devolucoesComDataInicialAnteriorADataDaDevolucao.size());
		assertEquals(2, ((SolicitacaoEpiItemDevolucao) devolucoesComDataIgualADataDaDevolucao.toArray()[0]).getQtdDevolvida().intValue());
		
		Collection<SolicitacaoEpiItemDevolucao> devolucoesComDataPosteriorADevolucao = solicitacaoEpiDao.findDevolucaoEpi(empresa.getId(), DateUtil.criarDataMesAno(02, 12, 2015), DateUtil.criarDataMesAno(31, 12, 2015), epiCheck, null, null, 'E', false);
		assertEquals(0, devolucoesComDataPosteriorADevolucao.size());
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

	public void setSolicitacaoEpiItemDevolucaoDao(SolicitacaoEpiItemDevolucaoDao solicitacaoEpiItemDevolucaoDao) {
		this.solicitacaoEpiItemDevolucaoDao = solicitacaoEpiItemDevolucaoDao;
	}
}