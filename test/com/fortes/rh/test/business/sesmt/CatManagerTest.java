package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

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

public class CatManagerTest extends MockObjectTestCase
{
	private CatManagerImpl catManager = new CatManagerImpl();
	private Mock catDao = null;
	private Mock areaOrganizacionalManager;

	protected void setUp() throws Exception
    {
        super.setUp();
        catDao = new Mock(CatDao.class);
        catManager.setDao((CatDao) catDao.proxy());
        
        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        catManager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
    }

	public void testFindCatsColaboradorByDate()throws Exception
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);

		Cat cat = new Cat();
		cat.setId(1L);

		Collection<Cat> c1 = new ArrayList<Cat>();
		c1.add(cat);

		catDao.expects(once()).method("findCatsColaboradorByDate").with(new Constraint[]{ANYTHING,ANYTHING}).will(returnValue(c1));
		Collection<Cat> catsRetorno = catManager.findCatsColaboradorByDate(colaborador,new Date());

		assertEquals(c1, catsRetorno);
	}

	public void testFindByColaborador() throws Exception
	{
		Collection<Cat> catsList = new ArrayList<Cat>();

		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);
		catDao.expects(once()).method("findByColaborador").with(eq(colaborador)).will(returnValue(catsList));
		Collection<Cat> cats = catManager.findByColaborador(colaborador);

		assertEquals(catsList, cats);
	}
	
	public void testFindAllSelect()
	{
		Date hoje=new Date();
		String[] estabelecimentosCheck = new String[]{"1","2"};
		Long[] estabelecimentoIds = LongUtil.arrayStringToArrayLong(estabelecimentosCheck);
		
		catDao.expects(once()).method("findAllSelect").with(new Constraint[]{eq(1L),eq(hoje),eq(hoje),eq(estabelecimentoIds), ANYTHING, ANYTHING}).will(returnValue(new ArrayList<Cat>()));
		
		assertNotNull(catManager.findAllSelect(1L, hoje, hoje, estabelecimentosCheck, null, null));
	}

	public void testFindRelatorioCats() throws ColecaoVaziaException
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
		
		catDao.expects(once()).method("findAllSelect").with(new Constraint[]{eq(1L),eq(hoje),eq(hoje),eq(estabelecimentoIds), ANYTHING, ANYTHING}).will(returnValue(colecao));
		areaOrganizacionalManager.expects(once()).method("findAllListAndInativa").will(returnValue(areaOrganizacionais ));
		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(areaOrganizacionais));
		areaOrganizacionalManager.expects(once()).method("getAreaOrganizacional").will(returnValue(areaOrganizacional));
		
		assertNotNull(catManager.findRelatorioCats(1L, hoje, hoje, estabelecimentosCheck, null));
	}
	
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
		
		catDao.expects(once()).method("getCatsRelatorio").with(ANYTHING, eq(dataIni), eq(dataFim)).will(returnValue(lista));
		
		Collection<CatRelatorioAnual> resultado = catManager.getRelatorioAnual(2L, dataFim);
		
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
	
	public void testFindQtdDiasSemAcidentes()
	{
		Date data = DateUtil.criarDataMesAno(1, 1, 2011);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Cat cat = new Cat();
		cat.setData(data);
		
		catDao.expects(once()).method("findUltimoCat").with(ANYTHING).will(returnValue(cat));

		int qtdDiasSemAcidentes = DateUtil.diferencaEntreDatas(data, new Date());
		int qtdDiasSemAcidentesTeste = catManager.findQtdDiasSemAcidentes(empresa.getId());
		
		assertEquals(qtdDiasSemAcidentes, qtdDiasSemAcidentesTeste);
	}
}