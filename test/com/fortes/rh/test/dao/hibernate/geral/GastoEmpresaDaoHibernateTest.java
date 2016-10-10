package com.fortes.rh.test.dao.hibernate.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.GastoDao;
import com.fortes.rh.dao.geral.GastoEmpresaDao;
import com.fortes.rh.dao.geral.GastoEmpresaItemDao;
import com.fortes.rh.dao.geral.GrupoGastoDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Gasto;
import com.fortes.rh.model.geral.GastoEmpresa;
import com.fortes.rh.model.geral.GastoEmpresaItem;
import com.fortes.rh.model.geral.GrupoGasto;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.GastoEmpresaFactory;
import com.fortes.rh.test.model.geral.EnderecoFactory;
import com.fortes.rh.util.DateUtil;

public class GastoEmpresaDaoHibernateTest extends GenericDaoHibernateTest<GastoEmpresa>
{
	private GastoEmpresaDao gastoEmpresaDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private ColaboradorDao colaboradorDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private GrupoGastoDao grupoGastoDao;
	private GastoDao gastoDao;
	private GastoEmpresaItemDao gastoEmpresaItemDao;
	private EmpresaDao empresaDao;

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao)
	{
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public GastoEmpresa getEntity()
	{
		GastoEmpresa gastoEmpresa = new GastoEmpresa();

		gastoEmpresa.setId(null);
		gastoEmpresa.setColaborador(null);
		gastoEmpresa.setGastoEmpresaItems(null);

		return gastoEmpresa;
	}

	public GenericDao<GastoEmpresa> getGenericDao()
	{
		return gastoEmpresaDao;
	}

	public void setGastoEmpresaDao(GastoEmpresaDao gastoEmpresaDao)
	{
		this.gastoEmpresaDao = gastoEmpresaDao;
	}

	public void testGetCount()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		GastoEmpresa gastoEmpresa = GastoEmpresaFactory.getEntity();
		gastoEmpresa.setColaborador(colaborador);
		gastoEmpresa.setEmpresa(empresa);
		gastoEmpresa = gastoEmpresaDao.save(gastoEmpresa);

		Integer retorno = gastoEmpresaDao.getCount(empresa.getId());

		assertEquals(1, retorno.intValue());
	}

	public void testFindAllList()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		GastoEmpresa gastoEmpresa = GastoEmpresaFactory.getEntity();
		gastoEmpresa.setColaborador(colaborador);
		gastoEmpresa.setEmpresa(empresa);
		gastoEmpresa = gastoEmpresaDao.save(gastoEmpresa);

		Collection<GastoEmpresa> retorno = gastoEmpresaDao.findAllList(1, 15, empresa.getId());

		assertEquals(1, retorno.size());
	}

	@SuppressWarnings("unchecked")
	public void testFiltroRelatorio()
	{
		Empresa empresa = new Empresa();
		empresa.setNome("fortes");
		empresa.setCnpj("65465");
		empresa.setRazaoSocial("fortes");

		empresa = empresaDao.save(empresa);

		AreaOrganizacional areaOrganizacional = new AreaOrganizacional();
		areaOrganizacional.setId(null);
		areaOrganizacional.setNome("area");
		areaOrganizacional.setEmpresa(empresa);

		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		Colaborador colaborador = new Colaborador();
		colaborador.setId(null);
		colaborador.setNome("nome colaborador");
		colaborador.setNomeComercial("nome  colaborador");
		colaborador.setDesligado(false);
		colaborador.setDataDesligamento(new Date());
		colaborador.setObservacao("observação");
		colaborador.setDataAdmissao(new Date());
		colaborador.setEndereco(EnderecoFactory.getEntity());

		Contato contato = new Contato();
		contato.setEmail("mail@mail.com");
		contato.setFoneFixo("00000000");
		contato.setFoneCelular("00000000");
		colaborador.setContato(contato);

		Pessoal pessoal	= new Pessoal();
		pessoal.setDataNascimento(new Date());
		pessoal.setEstadoCivil("e");
		pessoal.setEscolaridade("e");
		pessoal.setSexo('m');
		pessoal.setConjugeTrabalha(false);
		pessoal.setCpf("00000000000");
		colaborador.setPessoal(pessoal);

		colaborador.setDependentes(null);

		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador h0 = HistoricoColaboradorFactory.getEntity();
		h0.setData(DateUtil.criarAnoMesDia(2007, 3, 1));
		h0.setMotivo("p");
		h0.setSalario(1D);
		h0.setColaborador(colaborador);
		h0.setAreaOrganizacional(areaOrganizacional);

		h0 = historicoColaboradorDao.save(h0);

		Colaborador colaborador2 = new Colaborador();
		colaborador2.setId(null);
		colaborador2.setNome("nome colaborador 2");
		colaborador2.setNomeComercial("nome colaborador");
		colaborador2.setDesligado(false);
		colaborador2.setDataDesligamento(new Date());
		colaborador2.setObservacao("observação");
		colaborador.setDataAdmissao(new Date());
		colaborador2.setEndereco(EnderecoFactory.getEntity());
		colaborador2.setContato(contato);
		colaborador2.setPessoal(pessoal);

		colaborador2 = colaboradorDao.save(colaborador2);

		HistoricoColaborador h1 = HistoricoColaboradorFactory.getEntity();
		h1.setData(DateUtil.criarAnoMesDia(2007, 3, 1));
		h1.setMotivo("p");
		h1.setSalario(1D);
		h1.setColaborador(colaborador2);
		h1.setAreaOrganizacional(areaOrganizacional);

		historicoColaboradorDao.save(h1);

		GrupoGasto grupoGasto = new GrupoGasto();
		grupoGasto.setId(null);
		grupoGasto.setNome("grupo gasto");
		grupoGasto.setEmpresa(empresa);

		grupoGasto = grupoGastoDao.save(grupoGasto);

		Gasto gasto = new Gasto();
		gasto.setId(null);
		gasto.setNome("gasto");
		gasto.setGrupoGasto(grupoGasto);
		gasto.setEmpresa(empresa);

		gasto = gastoDao.save(gasto);

		Gasto gasto2 = new Gasto();
		gasto2.setId(null);
		gasto2.setNome("gasto2");
		gasto2.setGrupoGasto(grupoGasto);
		gasto2.setEmpresa(empresa);

		gasto2 = gastoDao.save(gasto2);

		GastoEmpresa gastoEmpresa = new GastoEmpresa();
		gastoEmpresa.setId(null);
		gastoEmpresa.setColaborador(colaborador);
		gastoEmpresa.setMesAno(DateUtil.criarAnoMesDia(2008, 2, 1));
		gastoEmpresa.setEmpresa(empresa);

		gastoEmpresa = gastoEmpresaDao.save(gastoEmpresa);

		GastoEmpresa gastoEmpresa2 = new GastoEmpresa();
		gastoEmpresa2.setId(null);
		gastoEmpresa2.setColaborador(colaborador2);
		gastoEmpresa2.setMesAno(DateUtil.criarAnoMesDia(2008, 2, 1));
		gastoEmpresa2.setEmpresa(empresa);

		gastoEmpresa2 = gastoEmpresaDao.save(gastoEmpresa2);

		GastoEmpresaItem gastoEmpresaItem = new GastoEmpresaItem();
		gastoEmpresaItem.setId(null);
		gastoEmpresaItem.setGasto(gasto);
		gastoEmpresaItem.setValor(500D);
		gastoEmpresaItem.setGastoEmpresa(gastoEmpresa);

		gastoEmpresaItem = gastoEmpresaItemDao.save(gastoEmpresaItem);

		GastoEmpresaItem gastoEmpresaItem2 = new GastoEmpresaItem();
		gastoEmpresaItem2.setId(null);
		gastoEmpresaItem2.setGasto(gasto2);
		gastoEmpresaItem2.setValor(500D);
		gastoEmpresaItem2.setGastoEmpresa(gastoEmpresa2);

		gastoEmpresaItem2 = gastoEmpresaItemDao.save(gastoEmpresaItem2);

		LinkedHashMap parametros = new LinkedHashMap();

		parametros.put("dataIni", DateUtil.criarAnoMesDia(2008, 1, 1));
		parametros.put("dataFim", DateUtil.criarAnoMesDia(2008, 3, 1));
		parametros.put("colaborador", colaborador);
		parametros.put("empresaId", empresa.getId());

		Collection<GastoEmpresa> gastoEmpresas = gastoEmpresaDao.filtroRelatorioByColaborador(parametros);

		assertEquals(1, gastoEmpresas.size());

		Collection<Long> areasId = new ArrayList<Long>();
		areasId.add(areaOrganizacional.getId());

		parametros.put("areas", areasId);
		parametros.put("colaborador", null);

		gastoEmpresas = gastoEmpresaDao.filtroRelatorioByAreas(parametros);

		assertEquals(2, gastoEmpresas.size());

	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
	{
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setGrupoGastoDao(GrupoGastoDao grupoGastoDao)
	{
		this.grupoGastoDao = grupoGastoDao;
	}

	public void setGastoDao(GastoDao gastoDao)
	{
		this.gastoDao = gastoDao;
	}

	public void setGastoEmpresaItemDao(GastoEmpresaItemDao gastoEmpresaItemDao)
	{
		this.gastoEmpresaItemDao = gastoEmpresaItemDao;
	}

}