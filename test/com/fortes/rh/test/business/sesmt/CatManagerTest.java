package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.sesmt.CatManagerImpl;
import com.fortes.rh.dao.sesmt.CatDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.model.sesmt.relatorio.CatRelatorioAnual;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;

public class CatManagerTest
{
	private CatManagerImpl catManager = new CatManagerImpl();
	private CatDao catDao;
	private AreaOrganizacionalManager areaOrganizacionalManager;

	@Before
	public void setUp() throws Exception
    {
        catDao = mock(CatDao.class);
        catManager.setDao(catDao);
        
        areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
        catManager.setAreaOrganizacionalManager(areaOrganizacionalManager);
    }

	@Test
	public void testFindCatsColaboradorByDate()throws Exception
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);

		Cat cat = new Cat();
		cat.setId(1L);

		Collection<Cat> c1 = new ArrayList<Cat>();
		c1.add(cat);

		Date hoje = new Date();
		
		when(catDao.findCatsColaboradorByDate(colaborador, hoje)).thenReturn(c1);
		
		Collection<Cat> catsRetorno = catManager.findCatsColaboradorByDate(colaborador,hoje);

		assertEquals(c1, catsRetorno);
	}

	@Test
	public void testFindByColaborador() throws Exception
	{
		Collection<Cat> catsList = new ArrayList<Cat>();

		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);
		when(catDao.findByColaborador(colaborador)).thenReturn(catsList);
		Collection<Cat> cats = catManager.findByColaborador(colaborador);

		assertEquals(catsList, cats);
	}
	
	@Test
	public void testFindAllSelect()
	{
		Date hoje=new Date();
		String[] estabelecimentosCheck = new String[]{"1","2"};
		Long[] estabelecimentoIds = LongUtil.arrayStringToArrayLong(estabelecimentosCheck);
		
		when(catDao.findAllSelect(1L, hoje, hoje, estabelecimentoIds, null, new Long[]{})).thenReturn(new ArrayList<Cat>());
		
		assertNotNull(catManager.findAllSelect(1L, hoje, hoje, estabelecimentosCheck, null, null));
	}

	@Test
	public void testFindRelatorioCats() throws Exception
	{
		Date hoje=new Date();
		String[] estabelecimentosCheck = new String[]{"1","2"};
		Long[] estabelecimentoIds = LongUtil.arrayStringToArrayLong(estabelecimentosCheck);
		
		Collection<Cat> colecao = new ArrayList<Cat>();
		Cat cat = new Cat();
		Colaborador colaborador = new Colaborador();
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		colaborador.setAreaOrganizacional(areaOrganizacional );
		cat.setColaborador(colaborador);
		colecao.add(cat);
		Collection<AreaOrganizacional> areaOrganizacionais = new ArrayList<AreaOrganizacional>();
		
		when(catDao.findAllSelect(1L, hoje, hoje, estabelecimentoIds, null, new Long[]{})).thenReturn(colecao);
		when(areaOrganizacionalManager.findAllListAndInativas(true, null, 1L)).thenReturn(areaOrganizacionais);
		when(areaOrganizacionalManager.montaFamilia(areaOrganizacionais)).thenReturn(areaOrganizacionais);
		when(areaOrganizacionalManager.getAreaOrganizacional(areaOrganizacionais, areaOrganizacional.getId())).thenReturn(areaOrganizacional);
		
		assertNotNull(catManager.findRelatorioCats(1L, hoje, hoje, estabelecimentosCheck, null));
	}
	
	@Test
	public void testGetRelatorioAnual()
	{
		Date dataFim = new Date();
		Date dataIni = DateUtil.incrementaAno(dataFim, -1);
		Date dataMeio = DateUtil.incrementaMes(dataIni, 1);
		
		Collection<Object[]> lista = new ArrayList<Object[]>();

		// Data Cat , Gerou Afastamento
		Object[] retornoConsulta1 = new Object[]{dataIni, true};
		lista.add(retornoConsulta1);
		
		Object[] retornoConsulta2 = new Object[]{dataMeio, true};
		lista.add(retornoConsulta2);
		
		Object[] retornoConsulta3 = new Object[]{dataMeio, false};
		lista.add(retornoConsulta3);
		
		when(catDao.getCatsRelatorio(2L, dataIni, dataFim)).thenReturn(lista);
		
		Collection<CatRelatorioAnual> resultado = catManager.getRelatorioCat(2L, dataIni, dataFim);
		
		assertEquals(2, resultado.size());
		
		CatRelatorioAnual cat1 = (CatRelatorioAnual) resultado.toArray()[0];
		assertEquals(1, cat1.getTotal().intValue());
		assertEquals(1, cat1.getTotalComAfastamento().intValue());
		assertEquals(0, cat1.getTotalSemAfastamento().intValue());
		
		CatRelatorioAnual cat2 = (CatRelatorioAnual) resultado.toArray()[1];
		assertEquals(2, cat2.getTotal().intValue());
		assertEquals(1, cat2.getTotalComAfastamento().intValue());
		assertEquals(1, cat2.getTotalSemAfastamento().intValue());
	}
	
	@Test
	public void testFindQtdDiasSemAcidentes()
	{
		Date data = DateUtil.criarDataMesAno(1, 1, 2011);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Cat cat = new Cat();
		cat.setData(data);
		
		when(catDao.findUltimoCat(empresa.getId())).thenReturn(cat);

		int qtdDiasSemAcidentes = DateUtil.diferencaEntreDatas(data, new Date(), false);
		int qtdDiasSemAcidentesTeste = catManager.findQtdDiasSemAcidentes(empresa.getId());
		
		assertEquals(qtdDiasSemAcidentes, qtdDiasSemAcidentesTeste);
	}
}