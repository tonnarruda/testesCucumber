package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManagerImpl;
import com.fortes.rh.business.sesmt.EpcManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.HistoricoAmbienteManager;
import com.fortes.rh.business.sesmt.RiscoMedicaoRiscoManager;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;
import com.fortes.rh.model.sesmt.relatorio.PpraLtcatCabecalho;
import com.fortes.rh.model.sesmt.relatorio.PpraLtcatRelatorio;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoAmbienteFactory;
import com.fortes.web.tags.CheckBox;
import com.ibm.icu.util.Calendar;

public class AmbienteManagerTest extends MockObjectTestCase
{
	private AmbienteManagerImpl ambienteManager = new AmbienteManagerImpl();
	private Mock ambienteDao = null;
	private Mock historicoAmbienteManager = null;
	private Mock estabelecimentoManager;
	private Mock funcaoManager;
	private Mock riscoMedicaoRiscoManager;
	private Mock epcManager;
	private Mock epiManager;

	protected void setUp() throws Exception
    {
        super.setUp();
        ambienteDao = new Mock(AmbienteDao.class);
        historicoAmbienteManager = new Mock(HistoricoAmbienteManager.class);

        ambienteManager.setDao((AmbienteDao) ambienteDao.proxy());
        ambienteManager.setHistoricoAmbienteManager((HistoricoAmbienteManager) historicoAmbienteManager.proxy());
        
        estabelecimentoManager = mock(EstabelecimentoManager.class);
        ambienteManager.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
        
        funcaoManager = mock(FuncaoManager.class);
        ambienteManager.setFuncaoManager((FuncaoManager) funcaoManager.proxy());
        
        riscoMedicaoRiscoManager = mock(RiscoMedicaoRiscoManager.class);
        ambienteManager.setRiscoMedicaoRiscoManager((RiscoMedicaoRiscoManager) riscoMedicaoRiscoManager.proxy());
        
        epcManager = mock(EpcManager.class);
        ambienteManager.setEpcManager((EpcManager) epcManager.proxy());
        
        epiManager = mock(EpiManager.class);
        ambienteManager.setEpiManager((EpiManager) epiManager.proxy());
    }

	public void testGetCount()
	{
		Collection<Ambiente> ambientes = new ArrayList<Ambiente>();

		ambienteDao.expects(once()).method("getCount").with(ANYTHING).will(returnValue(ambientes.size()));

		int retorno = ambienteManager.getCount(1L);

		assertEquals(ambientes.size(), retorno);
	}

	public void testFindAmbientes()throws Exception
	{
		Empresa empresa = new Empresa();
		empresa.setId(1L);

		Ambiente a1 = new Ambiente();
		a1.setId(1L);
		a1.setEmpresa(empresa);

		Ambiente a2 = new Ambiente();
		a2.setId(2L);
		a2.setEmpresa(empresa);

		Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
		ambientes.add(a1);
		ambientes.add(a2);

		ambienteDao.expects(once()).method("findAmbientes").with(eq(0), eq(0), eq(empresa.getId())).will(returnValue(ambientes));
		Collection<Ambiente> ambienteRetorno = ambienteManager.findAmbientes(empresa.getId());

		assertEquals("findAmbientes sem paginação",ambientes, ambienteRetorno);

		ambienteDao.expects(once()).method("findAmbientes").with(eq(1), eq(15), eq(empresa.getId())).will(returnValue(ambientes));
		ambienteRetorno = ambienteManager.findAmbientes(1, 15, empresa.getId());

		assertEquals("findAmbientes com paginação",ambientes, ambienteRetorno);

	}

	public void testSaveAmbienteHistoricoCommit()throws Exception
	{
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setId(1L);

		Ambiente ambiente = new Ambiente();
		ambiente.setId(1L);
		
		String[] riscoChecks = new String[]{"822", "823"};
		String[] epcCheck = new String[]{"100"};
		
		Collection<RiscoAmbiente> riscosAmbientes = RiscoAmbienteFactory.getCollection();

		ambienteDao.expects(atLeastOnce()).method("save").will(returnValue(ambiente));
		historicoAmbienteManager.expects(atLeastOnce()).method("save").with(eq(historicoAmbiente),eq(riscoChecks),eq(riscosAmbientes),eq(epcCheck)).isVoid();

		ambienteManager.saveAmbienteHistorico(ambiente, historicoAmbiente, riscoChecks, riscosAmbientes, epcCheck);
		
		assertEquals(Long.valueOf(1L), ambiente.getId());
	}
	
	public void testRemoveCascade() throws Exception
	{
		Ambiente ambiente = new Ambiente();
		ambiente.setId(1L);
		
		ambienteDao.expects(atLeastOnce()).method("findById").with(eq(ambiente.getId())).will(returnValue(ambiente));
		ambienteDao.expects(atLeastOnce()).method("remove").with(eq(ambiente));
		
		ambienteManager.removeCascade(ambiente.getId());
	}
	
	public void testFindByEmpresa() throws Exception
	{
		Collection<Ambiente> ambientesRetorno = new ArrayList<Ambiente>();

		Ambiente ambiente = new Ambiente();
		ambiente.setId(1L);

		ambientesRetorno.add(ambiente);

		ambienteDao.expects(once()).method("find").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(ambientesRetorno));

		assertEquals(ambientesRetorno, ambienteManager.findByEmpresa(1L));
	}
	
	public void testFindByEstabelecimento()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(2L);
		Collection<Ambiente> colecao = new ArrayList<Ambiente>();

		Ambiente ambiente = new Ambiente();
		ambiente.setId(1L);
		ambiente.setEstabelecimento(estabelecimento);
		colecao.add(ambiente);

		ambienteDao.expects(once()).method("findByEstabelecimento").with(eq(estabelecimento.getId())).will(returnValue(colecao));
		
		Collection<Ambiente> retorno = ambienteManager.findByEstabelecimento(estabelecimento.getId());
		
		assertEquals(colecao, retorno);
	}
	
	public void testFindByIdProjection()
	{
		Ambiente ambiente = new Ambiente();
		ambiente.setId(1L);
		ambienteDao.expects(once()).method("findByIdProjection").with(eq(ambiente.getId())).will(returnValue(ambiente));
		
		ambienteManager.findByIdProjection(ambiente.getId());
	}
	

	public void testGetAmbientes() throws Exception
	{

		Collection<Ambiente> ambientesRetorno = new ArrayList<Ambiente>();

		Ambiente ambiente = new Ambiente();
		ambiente.setId(1L);

		ambientesRetorno.add(ambiente);

		Empresa empresa = EmpresaFactory.getEmpresa();

		ambienteDao.expects(once()).method("find").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(ambientesRetorno));

		assertEquals(1, ambienteManager.getAmbientes(empresa).size());
	}

	public void testGetIdsAmbientes()throws Exception
	{
		Ambiente a1 = new Ambiente();
		a1.setId(1L);
		a1.setNome("A1");

		Ambiente a2 = new Ambiente();
		a2.setId(2L);
		a2.setNome("F2");

		Collection<Long> colLong = new ArrayList<Long>();
		colLong.add(a1.getId());
		colLong.add(a2.getId());

		HistoricoColaborador hc1 = new HistoricoColaborador();
		hc1.setId(3L);
		hc1.setAmbiente(a1);

		HistoricoColaborador hc2 = new HistoricoColaborador();
		hc2.setId(4L);
		hc2.setAmbiente(a2);

		HistoricoColaborador hc3 = new HistoricoColaborador();
		hc3.setId(5L);
		hc3.setAmbiente(a2);

		Collection<HistoricoColaborador> colhc = new ArrayList<HistoricoColaborador>();
		colhc.add(hc1);
		colhc.add(hc2);
		colhc.add(hc3);

		Collection<Long> idsRetorno = ambienteManager.getIdsAmbientes(colhc);
		assertEquals(colLong.size(),idsRetorno.size());
	}
	
	public void testPopulaCheckBox()
	{
		Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
		ambientes.add(AmbienteFactory.getEntity(1L));
		ambienteDao.expects(once()).method("findByEstabelecimento").will(returnValue(ambientes));
		
		assertEquals(1, ambienteManager.populaCheckBox(1L).size());
	}

	public void testPopulaCheckBox_ComException()
	{
		ambienteDao.expects(once()).method("findByEstabelecimento").will(returnValue(null));
		
		assertEquals( new ArrayList<CheckBox>(), ambienteManager.populaCheckBox(1L));
	}

	public void testGetQtdColaboradorByAmbiente()
	{
		ambienteDao.expects(once()).method("getQtdColaboradorByAmbiente").will(returnValue(new Integer(1)));
		
		assertEquals(1, ambienteManager.getQtdColaboradorByAmbiente(1L, new Date(), "masculino"));
	}
	
	public void testPopulaRelatorio() throws Exception
	{
		Date hoje = Calendar.getInstance().getTime();
		boolean exibirPpra=true;
		boolean exibirLtcat=true;
		boolean exibirComposicaoSesmt=true;
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setRazaoSocial("TESTEX S/A Testes Automatizados");
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(2L);
		
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua Margarida Flores");
		endereco.setBairro("Parque Araxá");
		endereco.setNumero("234");
		estabelecimento.setEndereco(endereco);
		
		Collection<HistoricoAmbiente> historicoAmbientes = new ArrayList<HistoricoAmbiente>();
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setDescricao("Piso de concreto, paredes de aço, teto de madeira.");
		
		historicoAmbientes.add(historicoAmbiente);
		
		Ambiente ambiente1 = AmbienteFactory.getEntity(50L);
		ambiente1.setHistoricoAtual(historicoAmbiente);
		ambiente1.setNome("Ambiente1");
		ambiente1.setHistoricoAmbientes(historicoAmbientes);
		
		Ambiente ambiente2 = AmbienteFactory.getEntity(51L);
		ambiente2.setNome("Ambiente2");
		HistoricoAmbiente historicoAmbiente2 = new HistoricoAmbiente();
		historicoAmbiente2.setDescricao("Paredes revestidas com isolamento acústico.");
		ambiente2.setHistoricoAtual(historicoAmbiente2);
		
		Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
		ambientes.add(ambiente1);
		ambientes.add(ambiente2);
		
		PpraLtcatCabecalho cabecalho = new PpraLtcatCabecalho();
		
		cabecalho.setEmpresa(empresa);
		cabecalho.setEstabelecimento(estabelecimento);
		
		estabelecimentoManager.expects(once()).method("findById").will(returnValue(estabelecimento));
		ambienteDao.expects(once()).method("findByIds").will(returnValue(ambientes));
		
		funcaoManager.expects(atLeastOnce()).method("findFuncoesDoAmbiente").will(returnValue(new ArrayList<Funcao>()));
		ambienteDao.expects(atLeastOnce()).method("getQtdColaboradorByAmbiente").with(ANYTHING,ANYTHING,eq(Sexo.MASCULINO)).will(returnValue(10));
		ambienteDao.expects(atLeastOnce()).method("getQtdColaboradorByAmbiente").with(ANYTHING,ANYTHING,eq(Sexo.FEMININO)).will(returnValue(50));
		riscoMedicaoRiscoManager.expects(atLeastOnce()).method("findMedicoesDeRiscosDoAmbiente").will(returnValue(new ArrayList<RiscoMedicaoRisco>()));
		epcManager.expects(atLeastOnce()).method("findEpcsDoAmbiente").will(returnValue(new ArrayList<Epc>()));
		epiManager.expects(atLeastOnce()).method("findEpisDoAmbiente").will(returnValue(new ArrayList<Epi>()));
		
		Date data = hoje;
		String[] ambienteCheck=new String[]{"50", "51"};
		boolean gerarPpra=exibirPpra;
		boolean gerarLtcat=exibirLtcat;
		
		Collection<PpraLtcatRelatorio> relatorios = ambienteManager.montaRelatorioPpraLtcat(empresa, estabelecimento.getId(), data, ambienteCheck, gerarPpra, gerarLtcat, exibirComposicaoSesmt);
		
		assertEquals(2, relatorios.size());
		assertEquals(60, ((PpraLtcatRelatorio) relatorios.toArray()[0]).getCabecalho().getQtdTotal().intValue());
	}
}