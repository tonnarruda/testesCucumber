package com.fortes.rh.test.business.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceHistoricoManager;
import com.fortes.rh.business.cargosalario.ReajusteColaboradorManager;
import com.fortes.rh.business.cargosalario.ReajusteFaixaSalarialManager;
import com.fortes.rh.business.cargosalario.ReajusteIndiceManager;
import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManager;
import com.fortes.rh.dao.cargosalario.TabelaReajusteColaboradorDao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.TipoReajuste;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.ReajusteColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;
import com.fortes.rh.test.util.mockObjects.MockHibernateTemplate;
import com.fortes.rh.test.util.mockObjects.MockHistoricoColaboradorUtil;
import com.fortes.rh.util.HistoricoColaboradorUtil;
import com.fortes.rh.web.ws.AcPessoalClientTabelaReajusteInterface;

public class TabelaReajusteColaboradorManagerTest extends MockObjectTestCase
{
	TabelaReajusteColaboradorManagerImpl tabelaReajusteColaboradorManager = new TabelaReajusteColaboradorManagerImpl();

	Mock tabelaReajusteColaboradorDao;
	Mock reajusteColaboradorManager;
	Mock historicoColaboradorManager;
	Mock acPessoalClientTabelaReajuste;
	Mock reajusteFaixaSalarialManager;
	Mock reajusteIndiceManager;
	Mock colaboradorManager;
	Mock quantidadeLimiteColaboradoresPorCargoManager;
	Mock faixaSalarialHistoricoManager;
	Mock indiceHistoricoManager;

	protected void setUp() throws Exception
	{
		reajusteColaboradorManager  = new Mock(ReajusteColaboradorManager.class);
		historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);
		acPessoalClientTabelaReajuste = new Mock(AcPessoalClientTabelaReajusteInterface.class);
		quantidadeLimiteColaboradoresPorCargoManager = new Mock(QuantidadeLimiteColaboradoresPorCargoManager.class);
		reajusteIndiceManager = new Mock(ReajusteIndiceManager.class);
		reajusteFaixaSalarialManager = new Mock(ReajusteFaixaSalarialManager.class);
		faixaSalarialHistoricoManager = new Mock(FaixaSalarialHistoricoManager.class);
		indiceHistoricoManager = new Mock(IndiceHistoricoManager.class);

		tabelaReajusteColaboradorDao = new Mock(TabelaReajusteColaboradorDao.class);
		tabelaReajusteColaboradorManager.setDao((TabelaReajusteColaboradorDao) tabelaReajusteColaboradorDao.proxy());
		tabelaReajusteColaboradorManager.setReajusteColaboradorManager((ReajusteColaboradorManager) reajusteColaboradorManager.proxy());
		tabelaReajusteColaboradorManager.setHistoricoColaboradorManager((HistoricoColaboradorManager)historicoColaboradorManager.proxy());
		tabelaReajusteColaboradorManager.setAcPessoalClientTabelaReajuste((AcPessoalClientTabelaReajusteInterface)acPessoalClientTabelaReajuste.proxy());
		tabelaReajusteColaboradorManager.setQuantidadeLimiteColaboradoresPorCargoManager((QuantidadeLimiteColaboradoresPorCargoManager) quantidadeLimiteColaboradoresPorCargoManager.proxy());
		tabelaReajusteColaboradorManager.setReajusteIndiceManager((ReajusteIndiceManager) reajusteIndiceManager.proxy());
		tabelaReajusteColaboradorManager.setReajusteFaixaSalarialManager((ReajusteFaixaSalarialManager) reajusteFaixaSalarialManager.proxy());
		tabelaReajusteColaboradorManager.setFaixaSalarialHistoricoManager((FaixaSalarialHistoricoManager) faixaSalarialHistoricoManager.proxy());
		tabelaReajusteColaboradorManager.setIndiceHistoricoManager((IndiceHistoricoManager) indiceHistoricoManager.proxy());
		
		colaboradorManager = mock(ColaboradorManager.class);
		tabelaReajusteColaboradorManager.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());

		Mockit.redefineMethods(HibernateTemplate.class, MockHibernateTemplate.class);
		Mockit.redefineMethods(HistoricoColaboradorUtil.class, MockHistoricoColaboradorUtil.class);
	}

    protected void tearDown() throws Exception
    {
    	Mockit.restoreAllOriginalDefinitions();
    }

	public void testRemove()
	{
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaborador.setId(1L);

		reajusteFaixaSalarialManager.expects(once()).method("removeByTabelaReajusteColaborador").with(eq(tabelaReajusteColaborador.getId()));
		reajusteIndiceManager.expects(once()).method("removeByTabelaReajusteColaborador").with(eq(tabelaReajusteColaborador.getId()));
		reajusteColaboradorManager.expects(once()).method("deleteByColaboradoresTabelaReajuste").with(ANYTHING,ANYTHING);
		tabelaReajusteColaboradorDao.expects(once()).method("remove").with(ANYTHING);

		tabelaReajusteColaboradorManager.remove(tabelaReajusteColaborador);
	}

	public void testFindAllSelect()
	{
		Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors = new ArrayList<TabelaReajusteColaborador>();

		tabelaReajusteColaboradorDao.expects(once()).method("findAllSelect").withAnyArguments().will(returnValue(tabelaReajusteColaboradors));

		Collection<TabelaReajusteColaborador> retorno = tabelaReajusteColaboradorManager.findAllSelect(1L, null, null);

		assertEquals(tabelaReajusteColaboradors.size(), retorno.size());
	}

	public void testFindAllSelectByNaoAprovada()
	{
		Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors = new ArrayList<TabelaReajusteColaborador>();

		tabelaReajusteColaboradorDao.expects(once()).method("findAllSelect").withAnyArguments().will(returnValue(tabelaReajusteColaboradors));

		Collection<TabelaReajusteColaborador> retorno = tabelaReajusteColaboradorManager.findAllSelectByNaoAprovada(1L, TipoReajuste.COLABORADOR);

		assertEquals(tabelaReajusteColaboradors.size(), retorno.size());
	}

	public void testMarcaUltima()
	{
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaborador.setAprovada(true);

		Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors = new ArrayList<TabelaReajusteColaborador>();
		tabelaReajusteColaboradors.add(tabelaReajusteColaborador);

		tabelaReajusteColaboradorManager.marcaUltima(tabelaReajusteColaboradors);
	}

	public void testGetCount()
	{
		Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors = new ArrayList<TabelaReajusteColaborador>();

		tabelaReajusteColaboradorDao.expects(once()).method("getCount").with(ANYTHING).will(returnValue(tabelaReajusteColaboradors.size()));

		int retorno = tabelaReajusteColaboradorManager.getCount(1L);

		assertEquals(retorno, tabelaReajusteColaboradors.size());
	}

	public void testFindAllList()
	{
		Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors = new ArrayList<TabelaReajusteColaborador>();

		tabelaReajusteColaboradorDao.expects(once()).method("findAllList").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(tabelaReajusteColaboradors));

		Collection<TabelaReajusteColaborador> retorno = tabelaReajusteColaboradorManager.findAllList(0, 0, 1L);

		assertEquals(tabelaReajusteColaboradors.size(), retorno.size());
	}

	public void testFindByIdProjection()
	{
		TabelaReajusteColaborador tabelaReajusteColaborador = new TabelaReajusteColaborador();
		tabelaReajusteColaborador.setId(1L);

		tabelaReajusteColaboradorDao.expects(once()).method("findByIdProjection").with(eq(tabelaReajusteColaborador.getId())).will(returnValue(tabelaReajusteColaborador));

		TabelaReajusteColaborador retorno = tabelaReajusteColaboradorManager.findByIdProjection(tabelaReajusteColaborador.getId());

		assertEquals(tabelaReajusteColaborador.getId(), retorno.getId());
	}

	public void testGetsSets()
	{
		tabelaReajusteColaboradorManager.setHistoricoColaboradorManager(null);
		tabelaReajusteColaboradorManager.setAcPessoalClientTabelaReajuste(null);
	}


	public void testaAplicarEmTabelaSemColaborador(){

		TabelaReajusteColaborador tabelaReajusteColaborador = new TabelaReajusteColaborador();

		Exception exception = null;
		try
		{
			tabelaReajusteColaboradorManager.aplicarPorColaborador(tabelaReajusteColaborador, null, null);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testaAplicarComReajusteVazio(){

		TabelaReajusteColaborador tabelaReajusteColaborador = new TabelaReajusteColaborador();
		tabelaReajusteColaborador.setReajusteColaboradors(new ArrayList<ReajusteColaborador>(0));

		Exception exception = null;
		try
		{
			tabelaReajusteColaboradorManager.aplicarPorColaborador(tabelaReajusteColaborador, null, null);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	// TODO Integração com AC
//	public void testaAplicarEmEmpresaQueIntegraComACmasTemColaboradoresSemCodigoAC(){
//
//		Colaborador mario, abreu;
//		mario = new Colaborador();
//		mario.setNomeComercial("Mário");
//		mario.setCodigoAC("");
//		abreu = new Colaborador();
//		abreu.setNomeComercial("Abreu");
//		abreu.setCodigoAC("");
//
//		ReajusteColaborador reajusteDoMario = new ReajusteColaborador();
//		reajusteDoMario.setColaborador(mario);
//		ReajusteColaborador reajusteDoAbreu = new ReajusteColaborador();
//		reajusteDoAbreu.setColaborador(abreu);
//		Collection<ReajusteColaborador>reajustes = new ArrayList<ReajusteColaborador>(2);
//		reajustes.add(reajusteDoAbreu);
//		reajustes.add(reajusteDoMario);
//		TabelaReajusteColaborador tabelaReajusteColaborador = new TabelaReajusteColaborador();
//		tabelaReajusteColaborador.setReajusteColaboradors(reajustes);
//
//		Empresa empresa = new Empresa();
//		empresa.setAcIntegra(true);
//
//		tabelaReajusteColaboradorDao.expects(once()).method("findById").with(ANYTHING).will(returnValue(tabelaReajusteColaborador));
//
//		Exception exception = null;
//		try
//		{
//			tabelaReajusteColaboradorManager.aplicar(tabelaReajusteColaborador, empresa, reajustes);
//		}
//		catch (Exception e)
//		{
//			exception = e;
//		}
//
//		assertNotNull(exception);
//	}
//
	public void testaAplicarEmEmpresaQueIntegraComAC(){

		Colaborador mario, abreu;
		Date data = new Date();

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);

		AreaOrganizacional areaOrganizacionalProposta = new AreaOrganizacional();
		Estabelecimento estabelecimentoProposto = new Estabelecimento();


		// Abreu
		abreu = new Colaborador();
		abreu.setId(22L);
		abreu.setNomeComercial("Abreu");
		abreu.setCodigoAC("22");
		TabelaReajusteColaborador tabelaDoAbreu = new TabelaReajusteColaborador();
		tabelaDoAbreu.setData(data);
		ReajusteColaborador reajusteDoAbreu = new ReajusteColaborador();
		reajusteDoAbreu.setColaborador(abreu);
		reajusteDoAbreu.setTabelaReajusteColaborador(tabelaDoAbreu);
		reajusteDoAbreu.setFaixaSalarialProposta(faixaSalarial);
		reajusteDoAbreu.setTipoSalarioProposto(TipoAplicacaoIndice.VALOR);
		reajusteDoAbreu.setSalarioProposto(1000d);
		reajusteDoAbreu.setAreaOrganizacionalProposta(areaOrganizacionalProposta);
		reajusteDoAbreu.setEstabelecimentoProposto(estabelecimentoProposto);
		
		HistoricoColaborador historicoAtualDoAbreu = new HistoricoColaborador();
		historicoAtualDoAbreu.setAreaOrganizacional(areaOrganizacionalProposta);
		historicoAtualDoAbreu.setFaixaSalarial(faixaSalarial);
		historicoAtualDoAbreu.setColaborador(abreu);
		

		// Mario
		mario = new Colaborador();
		mario.setId(11L);
		mario.setNomeComercial("Mário");
		mario.setCodigoAC("11");
		TabelaReajusteColaborador tabelaDoMario = new TabelaReajusteColaborador();
		tabelaDoMario.setData(data);
		ReajusteColaborador reajusteDoMario = new ReajusteColaborador();
		reajusteDoMario.setColaborador(mario);
		reajusteDoMario.setTabelaReajusteColaborador(tabelaDoMario);
		reajusteDoMario.setFaixaSalarialProposta(faixaSalarial);
		reajusteDoMario.setTipoSalarioProposto(TipoAplicacaoIndice.VALOR);
		reajusteDoMario.setSalarioProposto(2000d);
		reajusteDoMario.setAreaOrganizacionalProposta(areaOrganizacionalProposta);
		reajusteDoMario.setEstabelecimentoProposto(estabelecimentoProposto);
		
		HistoricoColaborador historicoAtualDoMario = new HistoricoColaborador();
		historicoAtualDoMario.setAreaOrganizacional(areaOrganizacionalProposta);
		historicoAtualDoMario.setFaixaSalarial(faixaSalarial);
		historicoAtualDoMario.setColaborador(mario);

		Collection<ReajusteColaborador>reajustes = new ArrayList<ReajusteColaborador>(2);
		reajustes.add(reajusteDoAbreu);
		reajustes.add(reajusteDoMario);
		TabelaReajusteColaborador tabelaReajusteColaborador = new TabelaReajusteColaborador();
		tabelaReajusteColaborador.setReajusteColaboradors(reajustes);

		Empresa empresa = new Empresa();
		empresa.setAcIntegra(true);
		
		colaboradorManager.expects(once()).method("verificaColaboradoresSemCodigoAC").isVoid();
		colaboradorManager.expects(once()).method("verificaColaboradoresDesligados").isVoid();

		historicoColaboradorManager.expects(atLeastOnce()).method("getHistoricoAtual").with(eq(22L)).will(returnValue(historicoAtualDoAbreu));
		historicoColaboradorManager.expects(atLeastOnce()).method("getHistoricoAtual").with(eq(11L)).will(returnValue(historicoAtualDoMario));
		
		historicoColaboradorManager.expects(once()).method("ajustaTipoSalario").with(new Constraint[]{ANYTHING, eq(TipoAplicacaoIndice.VALOR), ANYTHING, ANYTHING, eq(1000d)}).will(returnValue(historicoAtualDoAbreu));
		historicoColaboradorManager.expects(once()).method("ajustaTipoSalario").with(new Constraint[]{ANYTHING, eq(TipoAplicacaoIndice.VALOR), ANYTHING, ANYTHING, eq(2000d)}).will(returnValue(historicoAtualDoMario));
		
		quantidadeLimiteColaboradoresPorCargoManager.expects(atLeastOnce()).method("validaLimite").withAnyArguments();
		
		historicoColaboradorManager.expects(atLeastOnce()).method("save").withAnyArguments();

		tabelaReajusteColaboradorDao.expects(once()).method("updateSetAprovada").withAnyArguments();
		
		tabelaReajusteColaboradorDao.expects(atLeastOnce()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));
		
		acPessoalClientTabelaReajuste.expects(once()).method("aplicaReajuste").withAnyArguments();

		Exception exception = null;
		try
		{
			tabelaReajusteColaboradorManager.aplicarPorColaborador(tabelaReajusteColaborador, empresa, reajustes);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}
	
	public void testPrepareDeleteSituacao() throws Exception
	{
		Collection<TSituacao> situacaosTmp = new ArrayList<TSituacao>();
		String dataSituacao = "01/01/2000";
		
		TSituacao situacao1 = new TSituacao();
		situacao1.setEmpregadoCodigoAC("11");
		situacao1.setData(dataSituacao);
		
		TSituacao situacao2 = new TSituacao();
		situacao2.setEmpregadoCodigoAC("22");
		situacao2.setData(dataSituacao);
		
		situacaosTmp.add(situacao1);
		situacaosTmp.add(situacao2);
		
		TSituacao[] situacaos = tabelaReajusteColaboradorManager.prepareDeleteSituacao(situacaosTmp);
		
		assertEquals(2, situacaos.length);
		assertEquals("11", situacaos[0].getEmpregadoCodigoAC());
		assertEquals(dataSituacao, situacaos[0].getData());

		assertEquals("22", situacaos[1].getEmpregadoCodigoAC());
		assertEquals(dataSituacao, situacaos[1].getData());
	}
	
	public void testVerificaDataHistoricoColaborador() throws Exception	
	{
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(historicoColaborador);
		
		historicoColaboradorManager.expects(once()).method("findColaboradoresByTabelaReajusteData").with(eq(tabelaReajusteColaborador.getId()), eq(tabelaReajusteColaborador.getData())).will(returnValue(historicoColaboradors));
		
		Exception exception = null;
		try
		{
			tabelaReajusteColaboradorManager.verificaDataHistoricoColaborador(tabelaReajusteColaborador.getId(), tabelaReajusteColaborador.getData());
		}
		catch (Exception e)
		{
			exception = e;
		}
		
		assertNotNull(exception);
	}
	
	public void testVerificaDataHistoricoColaboradorVazio() throws Exception	
	{		
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);
		
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradorManager.expects(once()).method("findColaboradoresByTabelaReajusteData").with(eq(tabelaReajusteColaborador.getId()), eq(tabelaReajusteColaborador.getData())).will(returnValue(historicoColaboradors));
		
		Exception exception = null;
		try
		{
			tabelaReajusteColaboradorManager.verificaDataHistoricoColaborador(tabelaReajusteColaborador.getId(), tabelaReajusteColaborador.getData());
		}
		catch (Exception e)
		{
			exception = e;
		}
		
		assertNull(exception);
	}

	public void testVerificaDataHistoricoFaixaSalarial() throws Exception	
	{
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);
		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		
		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricos.add(faixaSalarialHistorico);
		
		faixaSalarialHistoricoManager.expects(once()).method("findByTabelaReajusteIdData").with(eq(tabelaReajusteColaborador.getId()), eq(tabelaReajusteColaborador.getData())).will(returnValue(faixaSalarialHistoricos));
		
		Exception exception = null;
		try
		{
			tabelaReajusteColaboradorManager.verificaDataHistoricoFaixaSalarial(tabelaReajusteColaborador.getId(), tabelaReajusteColaborador.getData());
		}
		catch (Exception e)
		{
			exception = e;
		}
		
		assertNotNull(exception);
	}
	
	public void testVerificaDataHistoricoFaixaSalarialVazio() throws Exception	
	{		
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);
		
		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricoManager.expects(once()).method("findByTabelaReajusteIdData").with(eq(tabelaReajusteColaborador.getId()), eq(tabelaReajusteColaborador.getData())).will(returnValue(faixaSalarialHistoricos));
		
		Exception exception = null;
		try
		{
			tabelaReajusteColaboradorManager.verificaDataHistoricoFaixaSalarial(tabelaReajusteColaborador.getId(), tabelaReajusteColaborador.getData());
		}
		catch (Exception e)
		{
			exception = e;
		}
		
		assertNull(exception);
	}
	
	public void testVerificaDataHistoricoIndice() throws Exception	
	{
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity();
		
		Collection<IndiceHistorico> indiceHistoricos = new ArrayList<IndiceHistorico>();
		indiceHistoricos.add(indiceHistorico);
		
		indiceHistoricoManager.expects(once()).method("findByTabelaReajusteIdData").with(eq(tabelaReajusteColaborador.getId()), eq(tabelaReajusteColaborador.getData())).will(returnValue(indiceHistoricos));
		
		Exception exception = null;
		try
		{
			tabelaReajusteColaboradorManager.verificaDataHistoricoIndice(tabelaReajusteColaborador.getId(), tabelaReajusteColaborador.getData());
		}
		catch (Exception e)
		{
			exception = e;
		}
		
		assertNotNull(exception);
	}
	
	public void testVerificaDataHistoricoIndiceVazio() throws Exception	
	{		
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);
		
		Collection<IndiceHistorico> indiceHistoricos = new ArrayList<IndiceHistorico>();
		
		indiceHistoricoManager.expects(once()).method("findByTabelaReajusteIdData").with(eq(tabelaReajusteColaborador.getId()), eq(tabelaReajusteColaborador.getData())).will(returnValue(indiceHistoricos));
		
		Exception exception = null;
		try
		{
			tabelaReajusteColaboradorManager.verificaDataHistoricoIndice(tabelaReajusteColaborador.getId(), tabelaReajusteColaborador.getData());
		}
		catch (Exception e)
		{
			exception = e;
		}
		
		assertNull(exception);
	}

	public void testCancelar() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);
		tabelaReajusteColaborador.setEmpresa(empresa);
		tabelaReajusteColaborador.setAprovada(false);

		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador(1L);
		reajusteColaborador.setTabelaReajusteColaborador(tabelaReajusteColaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setReajusteColaborador(reajusteColaborador);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setHistoricoColaborador(historicoColaborador);
		colaborador.setNaoIntegraAc(false);

		Collection<TSituacao> situacaoIntegrados = new ArrayList<TSituacao>();
		TSituacao situacao = new TSituacao();
		situacao.setId(1);
		situacao.setEmpregadoCodigoAC("1");
		situacaoIntegrados.add(situacao);

		tabelaReajusteColaboradorDao.expects(once()).method("updateSetAprovada").with(ANYTHING, ANYTHING);
		historicoColaboradorManager.expects(once()).method("findHistoricosByTabelaReajuste").with(new Constraint[]{eq(tabelaReajusteColaborador.getId()), eq(empresa)}).will(returnValue(situacaoIntegrados));
		historicoColaboradorManager.expects(once()).method("remove").with(ANYTHING).isVoid();
		tabelaReajusteColaboradorDao.expects(atLeastOnce()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));
		acPessoalClientTabelaReajuste.expects(once()).method("deleteHistoricoColaboradorAC").with(ANYTHING, ANYTHING);
		
		Exception exception = null;
		try
		{
			tabelaReajusteColaboradorManager.cancelar(TipoReajuste.COLABORADOR, tabelaReajusteColaborador.getId(), empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testCancelarComColaboradorNaoIntegadoAC() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);

		Collection<TSituacao> situacaoIntegrados = new ArrayList<TSituacao>();
		TSituacao situacao = new TSituacao();
		situacao.setId(1);
		situacao.setEmpregadoCodigoAC("1");
		situacaoIntegrados.add(situacao);
		
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);
		tabelaReajusteColaborador.setEmpresa(empresa);
		tabelaReajusteColaborador.setAprovada(false);

		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador(1L);
		reajusteColaborador.setTabelaReajusteColaborador(tabelaReajusteColaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(1L);
		historicoColaborador.setReajusteColaborador(reajusteColaborador);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setHistoricoColaborador(historicoColaborador);

		tabelaReajusteColaboradorDao.expects(once()).method("updateSetAprovada").with(ANYTHING, ANYTHING);
		historicoColaboradorManager.expects(once()).method("findHistoricosByTabelaReajuste").with(new Constraint[]{eq(tabelaReajusteColaborador.getId()), eq(empresa)}).will(returnValue(situacaoIntegrados));
		historicoColaboradorManager.expects(once()).method("remove").with(ANYTHING).isVoid();
		tabelaReajusteColaboradorDao.expects(atLeastOnce()).method("getHibernateTemplateByGenericDao").will(returnValue(new HibernateTemplate()));
		acPessoalClientTabelaReajuste.expects(once()).method("deleteHistoricoColaboradorAC").with(ANYTHING, ANYTHING).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));;
		
		Exception exception = null;
		try
		{
			tabelaReajusteColaboradorManager.cancelar(TipoReajuste.COLABORADOR, tabelaReajusteColaborador.getId(), empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}
}
