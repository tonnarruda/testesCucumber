package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.GastoEmpresaItemManager;
import com.fortes.rh.business.geral.GastoEmpresaManagerImpl;
import com.fortes.rh.business.geral.HistoricoColaboradorBeneficioManager;
import com.fortes.rh.dao.geral.GastoEmpresaDao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Gasto;
import com.fortes.rh.model.geral.GastoEmpresa;
import com.fortes.rh.model.geral.GastoEmpresaItem;
import com.fortes.rh.model.geral.relatorio.GastoRelatorio;
import com.fortes.rh.model.geral.relatorio.GastoRelatorioItem;
import com.fortes.rh.model.geral.relatorio.TotalGastoRelatorio;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.geral.GastoEmpresaFactory;
import com.fortes.rh.test.factory.geral.GastoEmpresaItemFactory;
import com.fortes.rh.test.factory.geral.GastoFactory;
import com.fortes.rh.util.DateUtil;

public class GastoEmpresaManagerTest extends MockObjectTestCase
{
	private GastoEmpresaManagerImpl manager = new GastoEmpresaManagerImpl();
	private Mock historicoColaboradorBeneficioManager = null;
	private Mock turmaManager = null;
	private Mock areaOrganizacionalManager = null;
	private Mock gastoEmpresaDao = null;
	private Mock gastoEmpresaItemManager = null;

	protected void setUp() throws Exception
    {
        super.setUp();
        gastoEmpresaDao = new Mock(GastoEmpresaDao.class);
        historicoColaboradorBeneficioManager = new Mock(HistoricoColaboradorBeneficioManager.class);
        turmaManager = new Mock(TurmaManager.class);
        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        gastoEmpresaItemManager = new Mock(GastoEmpresaItemManager.class);
        
        manager.setDao((GastoEmpresaDao) gastoEmpresaDao.proxy());
        manager.setHistoricoColaboradorBeneficioManager((HistoricoColaboradorBeneficioManager) historicoColaboradorBeneficioManager.proxy());
        manager.setTurmaManager((TurmaManager) turmaManager.proxy());
        manager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
        manager.setGastoEmpresaItemManager((GastoEmpresaItemManager) gastoEmpresaItemManager.proxy());
    }

	@SuppressWarnings("unchecked")
	public void testFiltroRelatorioByArea() throws Exception
	{
		LinkedHashMap parametros = new LinkedHashMap();
		parametros.put("colaborador", null);
		parametros.put("empresaId", 1L);

		Object[] g1 = new Object[6];
		g1[0] = "A1";
		g1[1] = "Gasto1";
		g1[2] = "Grupo";
		g1[3] = DateUtil.criarDataMesAno(01, 02, 2008);
		g1[4] = 110D;
		g1[5] = 1L;

		Object[] g2 = new Object[6];
		g2[0] = "A1";
		g2[1] = "Gasto2";
		g2[2] = "Grupo";
		g2[3] = DateUtil.criarDataMesAno(01, 02, 2008);
		g2[4] = 100D;
		g2[5] = 1L;

		Object[] g11 = new Object[6];
		g11[0] = "A2";
		g11[1] = "Gasto1";
		g11[2] = "Grupo";
		g11[3] = DateUtil.criarDataMesAno(01, 02, 2008);
		g11[4] = 110D;
		g11[5] = 2L;

		Object[] g21 = new Object[6];
		g21[0] = "A2";
		g21[1] = "Gasto2";
		g21[2] = "Grupo";
		g21[3] = DateUtil.criarDataMesAno(01, 02, 2008);
		g21[4] = 100D;
		g21[5] = 2L;

		Object[] b1 = new Object[8];
		b1[0] = 1L;
		b1[1] = "Beneficio1";
		b1[2] = DateUtil.criarDataMesAno(01, 02, 2008);
		b1[3] = null;
		b1[4] = "1";
		b1[5] = null;
		b1[6] = 220D;
		b1[7] = "A1";

		Object[] b2 = new Object[8];
		b2[0] = 1L;
		b2[1] = "Beneficio2";
		b2[2] = DateUtil.criarDataMesAno(01, 02, 2008);
		b2[3] = null;
		b2[4] = "3";
		b2[5] = null;
		b2[6] = 250D;
		b2[7] = "A1";

		Object[] b11 = new Object[8];
		b11[0] = 2L;
		b11[1] = "Beneficio1";
		b11[2] = DateUtil.criarDataMesAno(01, 02, 2008);
		b11[3] = null;
		b11[4] = "2";
		b11[5] = null;
		b11[6] = 220D;
		b11[7] = "A2";

		Object[] b21 = new Object[8];
		b21[0] = 2L;
		b21[1] = "Beneficio2";
		b21[2] = DateUtil.criarDataMesAno(01, 02, 2008);
		b21[3] = null;
		b21[4] = "4";
		b21[5] = null;
		b21[6] = 220D;
		b21[7] = "A2";

		Object[] t1 = new Object[6];
		t1[0] = "A1";
		t1[1] = "Curso1";
		t1[2] = DateUtil.criarDataMesAno(01, 02, 2008);
		t1[3] = 400D;
		t1[4] = 2;
		t1[5] = 1L;

		Object[] t2 = new Object[6];
		t2[0] = "A1";
		t2[1] = "Curso2";
		t2[2] = DateUtil.criarDataMesAno(01, 03, 2008);
		t2[3] = 300D;
		t2[4] = 2;
		t2[5] = 1L;

		Object[] t11 = new Object[6];
		t11[0] = "A2";
		t11[1] = "Curso1";
		t11[2] = DateUtil.criarDataMesAno(01, 02, 2008);
		t11[3] = 400D;
		t11[4] = 2;
		t11[5] = 2L;

		Object[] t21 = new Object[6];
		t21[0] = "A2";
		t21[1] = "Curso2";
		t21[2] = DateUtil.criarDataMesAno(01, 03, 2008);
		t21[3] = 300D;
		t21[4] = 2;
		t21[5] = 2L;

		List listaGastos = new LinkedList();
		listaGastos.add(g1);
		listaGastos.add(g2);
		listaGastos.add(g11);
		listaGastos.add(g21);

		List listaBeneficios = new LinkedList();
		listaBeneficios.add(b1);
		listaBeneficios.add(b2);
		listaBeneficios.add(b11);
		listaBeneficios.add(b21);

		List listaTreinamentos = new LinkedList();
		listaTreinamentos.add(t1);
		listaTreinamentos.add(t2);
		listaTreinamentos.add(t11);
		listaTreinamentos.add(t21);

		gastoEmpresaDao.expects(once()).method("filtroRelatorioByAreas").with(new Constraint[]{ANYTHING}).will(returnValue(listaGastos));
		historicoColaboradorBeneficioManager.expects(once()).method("filtroRelatorioByAreas").with(new Constraint[]{ANYTHING}).will(returnValue(listaBeneficios));
		turmaManager.expects(once()).method("filtroRelatorioByAreas").with(new Constraint[]{ANYTHING}).will(returnValue(listaTreinamentos));
		areaOrganizacionalManager.expects(once()).method("findAllListAndInativa").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").with(ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));
		areaOrganizacionalManager.expects(once()).method("getDistinctAreaMae").with(ANYTHING,ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));

		Collection<GastoRelatorio> retorno = manager.filtroRelatorio(parametros);

		assertEquals(2, retorno.size());

	}

	@SuppressWarnings("unchecked")
	public void testFiltroRelatorioByColaborador() throws Exception
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setId(1L);
		areaOrganizacional.setEmpresaId(1L);

		Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
		areaOrganizacionals.add(areaOrganizacional);

		LinkedHashMap parametros = new LinkedHashMap();
		parametros.put("colaborador", colaborador);
		parametros.put("empresaId", 1L);

		Object[] g1 = new Object[6];
		g1[0] = areaOrganizacional.getNome();
		g1[1] = "Gasto1";
		g1[2] = "Grupo";
		g1[3] = DateUtil.criarDataMesAno(01, 02, 2008);
		g1[4] = 110D;
		g1[5] = areaOrganizacional.getId();

		Object[] g2 = new Object[6];
		g2[0] = areaOrganizacional.getNome();
		g2[1] = "Gasto2";
		g2[2] = "Grupo";
		g2[3] = DateUtil.criarDataMesAno(01, 02, 2008);
		g2[4] = 100D;
		g2[5] = areaOrganizacional.getId();

		Object[] b1 = new Object[8];
		b1[0] = areaOrganizacional.getId();
		b1[1] = "Beneficio1";
		b1[2] = DateUtil.criarDataMesAno(01, 02, 2008);
		b1[3] = null;
		b1[4] = "1";
		b1[5] = null;
		b1[6] = 220D;
		b1[7] = areaOrganizacional.getNome();

		Object[] t1 = new Object[6];
		t1[0] = areaOrganizacional.getNome();
		t1[1] = "Curso1";
		t1[2] = DateUtil.criarDataMesAno(01, 02, 2008);
		t1[3] = 400D;
		t1[4] = 2;
		t1[5] = areaOrganizacional.getId();

		List listaGastos = new LinkedList();
		listaGastos.add(g1);
		listaGastos.add(g2);

		List listaBeneficios = new LinkedList();
		listaBeneficios.add(b1);

		List listaTreinamentos = new LinkedList();
		listaTreinamentos.add(t1);

		gastoEmpresaDao.expects(once()).method("filtroRelatorioByColaborador").with(new Constraint[]{ANYTHING}).will(returnValue(listaGastos));
		historicoColaboradorBeneficioManager.expects(once()).method("filtroRelatorioByColaborador").with(new Constraint[]{ANYTHING}).will(returnValue(listaBeneficios));
		turmaManager.expects(once()).method("filtroRelatorioByColaborador").with(new Constraint[]{ANYTHING}).will(returnValue(listaTreinamentos));
		areaOrganizacionalManager.expects(atLeastOnce()).method("findAllListAndInativa").with(eq(1L),eq(AreaOrganizacional.TODAS), ANYTHING).will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(atLeastOnce()).method("montaFamilia").with(ANYTHING).will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(atLeastOnce()).method("getDistinctAreaMae").with(ANYTHING,ANYTHING).will(returnValue(areaOrganizacionals));

		Collection<GastoRelatorio> retorno = manager.filtroRelatorio(parametros);

		assertEquals(1, retorno.size());

		gastoEmpresaDao.expects(once()).method("filtroRelatorioByColaborador").with(new Constraint[]{ANYTHING}).will(returnValue(new LinkedList()));
		historicoColaboradorBeneficioManager.expects(once()).method("filtroRelatorioByColaborador").with(new Constraint[]{ANYTHING}).will(returnValue(listaBeneficios));
		turmaManager.expects(once()).method("filtroRelatorioByColaborador").with(new Constraint[]{ANYTHING}).will(returnValue(listaTreinamentos));
		areaOrganizacionalManager.expects(atLeastOnce()).method("findAllListAndInativa").with(eq(1L),eq(AreaOrganizacional.TODAS), ANYTHING).will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(atLeastOnce()).method("montaFamilia").with(ANYTHING).will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(atLeastOnce()).method("getDistinctAreaMae").with(ANYTHING,ANYTHING).will(returnValue(areaOrganizacionals));

		retorno = manager.filtroRelatorio(parametros);

		assertEquals(1, retorno.size());

		gastoEmpresaDao.expects(once()).method("filtroRelatorioByColaborador").with(new Constraint[]{ANYTHING}).will(returnValue(listaGastos));
		historicoColaboradorBeneficioManager.expects(once()).method("filtroRelatorioByColaborador").with(new Constraint[]{ANYTHING}).will(returnValue(new LinkedList()));
		turmaManager.expects(once()).method("filtroRelatorioByColaborador").with(new Constraint[]{ANYTHING}).will(returnValue(listaTreinamentos));
		areaOrganizacionalManager.expects(atLeastOnce()).method("findAllListAndInativa").with(eq(1L),eq(AreaOrganizacional.TODAS), ANYTHING).will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(atLeastOnce()).method("montaFamilia").with(ANYTHING).will(returnValue(areaOrganizacionals));
		areaOrganizacionalManager.expects(atLeastOnce()).method("getDistinctAreaMae").with(ANYTHING,ANYTHING).will(returnValue(areaOrganizacionals));

		retorno = manager.filtroRelatorio(parametros);

		assertEquals(1, retorno.size());
	}

	public void testMapearGastos() throws Exception
	{
		AreaOrganizacional ao1 = AreaOrganizacionalFactory.getEntity();
		ao1.setId(1L);
		ao1.setNome("a1");
		AreaOrganizacional ao2 = AreaOrganizacionalFactory.getEntity();
		ao2.setId(2L);
		ao2.setNome("a2");
		AreaOrganizacional ao3 = AreaOrganizacionalFactory.getEntity();
		ao3.setId(3L);
		ao3.setNome("a3");

		GastoRelatorioItem gri1 = new GastoRelatorioItem();
		GastoRelatorioItem gri2 = new GastoRelatorioItem();
		GastoRelatorioItem gri3 = new GastoRelatorioItem();

		Collection<GastoRelatorioItem> gastos1 = new ArrayList<GastoRelatorioItem>();
		gastos1.add(gri1);
		gastos1.add(gri2);

		GastoRelatorio gastoRelatorio1 = new GastoRelatorio();
		gastoRelatorio1.setAreaOrganizacional(ao1);
		gastoRelatorio1.setGastoRelatorioItems(gastos1);

		Collection<GastoRelatorioItem> gastos2 = new ArrayList<GastoRelatorioItem>();
		gastos2.add(gri2);
		gastos2.add(gri3);

		GastoRelatorio gastoRelatorio2 = new GastoRelatorio();
		gastoRelatorio2.setAreaOrganizacional(ao2);
		gastoRelatorio2.setGastoRelatorioItems(gastos2);

		Collection<GastoRelatorioItem> gastos3 = new ArrayList<GastoRelatorioItem>();
		gastos3.add(gri1);
		gastos3.add(gri3);

		GastoRelatorio gastoRelatorio3 = new GastoRelatorio();
		gastoRelatorio3.setAreaOrganizacional(ao3);
		gastoRelatorio3.setGastoRelatorioItems(gastos3);

		Collection<GastoRelatorio> gastoRelatorios = new ArrayList<GastoRelatorio>();
		gastoRelatorios.add(gastoRelatorio1);
		gastoRelatorios.add(gastoRelatorio2);
		gastoRelatorios.add(gastoRelatorio3);

		Map<AreaOrganizacional, Collection<GastoRelatorioItem>> mesclado = new HashMap<AreaOrganizacional, Collection<GastoRelatorioItem>>();

		Map<AreaOrganizacional, Collection<GastoRelatorioItem>> retorno = manager.mapearGastos(gastoRelatorios, mesclado);

		assertEquals(3, retorno.size());

		Collection<AreaOrganizacional> keys = retorno.keySet();
		int cont = 0;
		for (AreaOrganizacional key : keys)
		{
			cont += retorno.get(key).size();
		}

		assertEquals(6, cont);

		Map<AreaOrganizacional, Collection<GastoRelatorioItem>> retorno2 = manager.mapearGastos(gastoRelatorios, retorno);
		assertEquals(3, retorno2.size());

		keys = retorno.keySet();
		cont = 0;
		for (AreaOrganizacional key : keys)
		{
			cont += retorno.get(key).size();
		}

		assertEquals(12, cont);
	}

	public void testMultiplicarGastoRelatorioItem()
	{
		Gasto gasto = new Gasto();
		gasto.setNome("gasto");

		GastoRelatorioItem gastoRelatorioItem = new GastoRelatorioItem();
		gastoRelatorioItem.setGasto(gasto);
		gastoRelatorioItem.setMesAno(DateUtil.criarDataMesAno(01, 03, 2008));
		gastoRelatorioItem.setTotal(100D);

		Date dataIni = DateUtil.criarDataMesAno(01, 01, 2008);
		Date dataFim = DateUtil.criarDataMesAno(01, 06, 2008);

		Collection<GastoRelatorioItem> retorno = manager.multiplicarGastoRelatorioItem(gastoRelatorioItem, dataIni, dataFim);

		assertEquals(7, retorno.size());

		Double total = 0D;

		for (GastoRelatorioItem item : retorno)
		{
			total += item.getTotal();
		}

		assertEquals(100D, total);
	}

	public void testTotalizarInvestimentos()
	{
		GastoRelatorioItem gri1 = new GastoRelatorioItem();
		gri1.setMesAno(DateUtil.criarDataMesAno(01, 01, 2008));
		gri1.setTotal(100D);

		GastoRelatorioItem gri2 = new GastoRelatorioItem();
		gri2.setMesAno(DateUtil.criarDataMesAno(01, 02, 2008));
		gri2.setTotal(100D);

		GastoRelatorioItem gri3 = new GastoRelatorioItem();
		gri3.setMesAno(DateUtil.criarDataMesAno(01, 03, 2008));
		gri3.setTotal(100D);

		GastoRelatorioItem gri4 = new GastoRelatorioItem();
		gri4.setMesAno(DateUtil.criarDataMesAno(01, 02, 2008));
		gri4.setTotal(50D);

		GastoRelatorioItem gri5 = new GastoRelatorioItem();
		gri5.setMesAno(DateUtil.criarDataMesAno(01, 03, 2008));
		gri5.setTotal(50D);

		Collection<GastoRelatorioItem> gastoRelatorioItems = new ArrayList<GastoRelatorioItem>();
		gastoRelatorioItems.add(gri1);
		gastoRelatorioItems.add(gri2);
		gastoRelatorioItems.add(gri3);
		gastoRelatorioItems.add(gri4);
		gastoRelatorioItems.add(gri5);

		GastoRelatorio gastoRelatorio = new GastoRelatorio();
		gastoRelatorio.setGastoRelatorioItems(gastoRelatorioItems);

		Collection<GastoRelatorio> gastoRelatorios = new ArrayList<GastoRelatorio>();
		gastoRelatorios.add(gastoRelatorio);

		Map<Date,Double> retorno = manager.totalizarInvestimentos(gastoRelatorios);

		assertEquals(3, retorno.size());
		assertEquals(100D, retorno.get(DateUtil.criarDataMesAno(01, 01, 2008)));
		assertEquals(150D, retorno.get(DateUtil.criarDataMesAno(01, 02, 2008)));
	}

	public void testGetTotalInvestimentos()
	{
		GastoRelatorioItem gri1 = new GastoRelatorioItem();
		gri1.setMesAno(DateUtil.criarDataMesAno(01, 01, 2008));
		gri1.setTotal(100D);

		GastoRelatorioItem gri2 = new GastoRelatorioItem();
		gri2.setMesAno(DateUtil.criarDataMesAno(01, 02, 2008));
		gri2.setTotal(100D);

		GastoRelatorioItem gri3 = new GastoRelatorioItem();
		gri3.setMesAno(DateUtil.criarDataMesAno(01, 02, 2008));
		gri3.setTotal(50D);

		Collection<GastoRelatorioItem> gastoRelatorioItems = new ArrayList<GastoRelatorioItem>();
		gastoRelatorioItems.add(gri1);
		gastoRelatorioItems.add(gri2);
		gastoRelatorioItems.add(gri3);

		GastoRelatorio gastoRelatorio = new GastoRelatorio();
		gastoRelatorio.setGastoRelatorioItems(gastoRelatorioItems);

		Collection<GastoRelatorio> gastoRelatorios = new ArrayList<GastoRelatorio>();
		gastoRelatorios.add(gastoRelatorio);

		Collection<TotalGastoRelatorio> retorno = manager.getTotalInvestimentos(gastoRelatorios);

		assertEquals(3, retorno.size());
	}

	public void testClonarGastosPorColaborador()
	{
		Gasto gasto = GastoFactory.getEntity(1L);
		gasto.setNaoImportar(false);
		
		Collection<GastoEmpresaItem> itens = new ArrayList<GastoEmpresaItem>();
		GastoEmpresaItem gastoEmpresaItem = GastoEmpresaItemFactory.getEntity(1L);
		gastoEmpresaItem.setGasto(gasto);
		itens.add(gastoEmpresaItem);

		GastoEmpresa gastoEmpresa = GastoEmpresaFactory.getEntity(1L);
		gastoEmpresa.setGastoEmpresaItems(itens);
		
		gastoEmpresaDao.expects(once()).method("findById").with(ANYTHING).will(returnValue(gastoEmpresa));
		gastoEmpresaDao.expects(once()).method("save").with(ANYTHING).will(returnValue(gastoEmpresa));
		gastoEmpresaItemManager.expects(once()).method("save").with(ANYTHING);
		
		manager.clonarGastosPorColaborador("2009-08-08", gastoEmpresa);
	}
}
