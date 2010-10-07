package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.ExtintorInspecaoManager;
import com.fortes.rh.business.sesmt.ExtintorManagerImpl;
import com.fortes.rh.business.sesmt.ExtintorManutencaoManager;
import com.fortes.rh.dao.sesmt.ExtintorDao;
import com.fortes.rh.model.dicionario.MotivoExtintorManutencao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.ExtintorInspecao;
import com.fortes.rh.model.sesmt.ExtintorManutencao;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorFactory;
import com.fortes.rh.util.DateUtil;

public class ExtintorManagerTest extends MockObjectTestCase
{
	private ExtintorManagerImpl extintorManager = new ExtintorManagerImpl();
	private Mock extintorDao;
	private Mock extintorInspecaoManager;
	private Mock extintorManutencaoManager;
	private Mock estabelecimentoManager;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        extintorDao = new Mock(ExtintorDao.class);
        extintorManager.setDao((ExtintorDao) extintorDao.proxy());
        
        extintorInspecaoManager = new Mock(ExtintorInspecaoManager.class);
        extintorManager.setExtintorInspecaoManager((ExtintorInspecaoManager) extintorInspecaoManager.proxy());
        
        extintorManutencaoManager = new Mock(ExtintorManutencaoManager.class);
        extintorManager.setExtintorManutencaoManager((ExtintorManutencaoManager) extintorManutencaoManager.proxy());
    
        estabelecimentoManager = new Mock(EstabelecimentoManager.class);
        extintorManager.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
    }

	Long empresaId = 5L;
	String tipoBusca = "T";
	Integer numeroBusca = 123;
	char ativo = 'S';

	public void testGetCount()
	{
		extintorDao.expects(once()).method("getCount").with(eq(empresaId), eq(null), eq(numeroBusca), eq(true)).will(returnValue(1));
		assertEquals(Integer.valueOf(1),
				extintorManager.getCount(empresaId, tipoBusca, numeroBusca, ativo));

		ativo='N';
		extintorDao.expects(once()).method("getCount").with(eq(empresaId), eq(null), eq(numeroBusca), eq(false)).will(returnValue(1));
		assertEquals(Integer.valueOf(1),
				extintorManager.getCount(empresaId, tipoBusca, numeroBusca, ativo));
	}

	public void testFindAllSelect()
	{
		Collection<Extintor> colecao = new ArrayList<Extintor>();
		colecao.add(ExtintorFactory.getEntity(1L));

		extintorDao.expects(once()).method("findAllSelect").with(new Constraint[]{eq(0),eq(0),eq(empresaId), eq(null), eq(numeroBusca), eq(true)}).will(returnValue(colecao ));
		assertEquals(colecao, extintorManager.findAllSelect(0, 0, empresaId, tipoBusca, numeroBusca, ativo));

		ativo='N';
		extintorDao.expects(once()).method("findAllSelect").with(new Constraint[]{eq(0),eq(0),eq(empresaId), eq(null), eq(numeroBusca), eq(false)}).will(returnValue(colecao ));
		assertEquals(colecao, extintorManager.findAllSelect(0, 0, empresaId, tipoBusca, numeroBusca, ativo));
	}

	public void testFindByEstabelecimento()
	{
		Long estabelecimentoId = 2L;
		extintorDao.expects(once()).method("findByEstabelecimento").with(eq(estabelecimentoId),eq(null)).will(returnValue(new ArrayList<Extintor>()));
		assertEquals(0, extintorManager.findByEstabelecimento(estabelecimentoId, null).size());
	}
	
	public void testGetFabricantes()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Collection<String> fabricantes = new ArrayList<String>();
		fabricantes.add("Gas");
		fabricantes.add("Extint");
		
		extintorDao.expects(once()).method("findFabricantesDistinctByEmpresa").with(eq(empresa.getId())).will(returnValue(fabricantes));
		assertEquals("\"Gas\",\"Extint\"", extintorManager.getFabricantes(empresa.getId()));
	}

	public void testGetFabricantesVazio()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		extintorDao.expects(once()).method("findFabricantesDistinctByEmpresa").with(eq(empresa.getId())).will(returnValue(new ArrayList<String>()));
		assertEquals("", extintorManager.getFabricantes(empresa.getId()));
	}
	
	public void testMontaLabelFiltro()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		estabelecimento.setNome("Palmacia");
		
		Date dataVencimento = DateUtil.criarDataMesAno(1, 10, 2000);
		estabelecimentoManager.expects(once()).method("findEstabelecimentoCodigoAc").with(eq(estabelecimento.getId())).will(returnValue(estabelecimento));
		assertEquals("Estabelecimento: Palmacia\nVencimentos até: 01/10/2000", extintorManager.montaLabelFiltro(estabelecimento.getId(), dataVencimento));
	}
	
	public void testGetLocalizacoes()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Collection<String> localizacoes = new ArrayList<String>();
		localizacoes.add("Gas");
		localizacoes.add("Extint");
		
		extintorDao.expects(once()).method("findLocalizacoesDistinctByEmpresa").with(eq(empresa.getId())).will(returnValue(localizacoes));
		assertEquals("\"Gas\",\"Extint\"", extintorManager.getLocalizacoes(empresa.getId()));
	}
	
	public void testGetLocalizacoesVazio()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		extintorDao.expects(once()).method("findLocalizacoesDistinctByEmpresa").with(eq(empresa.getId())).will(returnValue(new ArrayList<String>()));
		assertEquals("", extintorManager.getLocalizacoes(empresa.getId()));
	}

	public void testRelatorioManutencaoAndInspecao() throws Exception
	{
		Long estabelecimentoId = 1L;
		Date dataVencimento = new Date();
		boolean inspecaoVencida = true;
		boolean cargaVencida = true;
		boolean testeHidrostaticoVencido = true;
		
		Collection<ExtintorInspecao> extintorInspecaoVencidas = new ArrayList<ExtintorInspecao>();
		extintorInspecaoVencidas.add(new ExtintorInspecao());
		
		extintorInspecaoManager.expects(once()).method("findInspecoesVencidas").with(eq(estabelecimentoId), eq(dataVencimento)).will(returnValue(extintorInspecaoVencidas));
		extintorManutencaoManager.expects(once()).method("findManutencaoVencida").with(eq(estabelecimentoId), eq(dataVencimento), eq(MotivoExtintorManutencao.PRAZO_RECARGA)).will(returnValue(new ArrayList<ExtintorManutencao>()));
		extintorManutencaoManager.expects(once()).method("findManutencaoVencida").with(eq(estabelecimentoId), eq(dataVencimento), eq(MotivoExtintorManutencao.PRAZO_HIDROSTATICO)).will(returnValue(new ArrayList<ExtintorManutencao>()));
				
		assertNotNull(extintorManager.relatorioManutencaoAndInspecao(estabelecimentoId, dataVencimento, inspecaoVencida, cargaVencida, testeHidrostaticoVencido));
	}
	
	public void testRelatorioManutencaoAndInspecaoVazio()
	{
		Long estabelecimentoId = 1L;
		Date dataVencimento = new Date();
		boolean inspecaoVencida = true;
		boolean cargaVencida = true;
		boolean testeHidrostaticoVencido = true;
		
		extintorInspecaoManager.expects(once()).method("findInspecoesVencidas").with(eq(estabelecimentoId), eq(dataVencimento)).will(returnValue(null));
		extintorManutencaoManager.expects(once()).method("findManutencaoVencida").with(eq(estabelecimentoId), eq(dataVencimento), eq(MotivoExtintorManutencao.PRAZO_RECARGA)).will(returnValue(null));
		extintorManutencaoManager.expects(once()).method("findManutencaoVencida").with(eq(estabelecimentoId), eq(dataVencimento), eq(MotivoExtintorManutencao.PRAZO_HIDROSTATICO)).will(returnValue(null));

		Exception exception = null;
		try
		{
			extintorManager.relatorioManutencaoAndInspecao(estabelecimentoId, dataVencimento, inspecaoVencida, cargaVencida, testeHidrostaticoVencido);
		}
		catch (Exception e)
		{
			exception = e;
		}
		
		assertNotNull(exception);
	}
}