package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.ClinicaAutorizadaManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.MedicoCoordenadorManager;
import com.fortes.rh.business.sesmt.SolicitacaoExameManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.model.sesmt.relatorio.AsoRelatorio;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoExameFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockStringUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.sesmt.ExameListAction;
import com.fortes.web.tags.CheckBox;

public class ExameListActionTest extends MockObjectTestCase
{

	private ExameListAction action;
	private Mock exameManager;
	private Mock areaOrganizacionalManager;
	private Mock estabelecimentoManager;
	private Mock colaboradorManager;
	private Mock medicoCoordenadorManager;
	private Mock candidatoManager;
	private Mock clinicaAutorizadaManager;
	private Mock solicitacaoExameManager;


	protected void setUp() throws Exception
	{
		action = new ExameListAction();

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
		Mockit.redefineMethods(StringUtil.class, MockStringUtil.class);

		exameManager = new Mock(ExameManager.class);
		action.setExameManager((ExameManager) exameManager.proxy());

		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		action.setAreaOrganizacionalManager((AreaOrganizacionalManager)areaOrganizacionalManager.proxy());

		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		action.setEstabelecimentoManager((EstabelecimentoManager)estabelecimentoManager.proxy());

		colaboradorManager = new Mock(ColaboradorManager.class);
		action.setColaboradorManager((ColaboradorManager)colaboradorManager.proxy());

		medicoCoordenadorManager = new Mock(MedicoCoordenadorManager.class);
		action.setMedicoCoordenadorManager((MedicoCoordenadorManager)medicoCoordenadorManager.proxy());

		candidatoManager = new Mock(CandidatoManager.class);
		action.setCandidatoManager((CandidatoManager)candidatoManager.proxy());
		
		clinicaAutorizadaManager = mock(ClinicaAutorizadaManager.class);
		action.setClinicaAutorizadaManager((ClinicaAutorizadaManager) clinicaAutorizadaManager.proxy());
		
		solicitacaoExameManager = mock(SolicitacaoExameManager.class);
		action.setSolicitacaoExameManager((SolicitacaoExameManager) solicitacaoExameManager.proxy());
	}

	@Override
	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();
		action = null;
		exameManager = null;
        MockSecurityUtil.verifyRole = false;

		super.tearDown();
	}

	public void testList() throws Exception
	{
		listComplete();

		assertEquals("success", action.list());
	}

	private void listComplete()
	{
		Collection<Exame> epcs = new ArrayList<Exame>();

		exameManager.expects(once()).method("count").with(ANYTHING,ANYTHING).will(returnValue(epcs.size()));
		exameManager.expects(once()).method("find").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(epcs));
	}

	public void testDelete() throws Exception
	{
		Exame exame = new Exame();
		exame.setId(1L);
		action.setExame(exame);

		listComplete();

		Empresa empresa = new Empresa();
		empresa.setId(1L);

		Exame exame2 = new Exame();
		exame2.setId(2L);
		exame2.setEmpresa(empresa);

		exameManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(exame2));
		exameManager.expects(once()).method("remove").with(ANYTHING);

		assertEquals("success",action.delete());
	}

	public void testDeleteException() throws Exception
	{
		Exame exame = new Exame();
		exame.setId(1L);
		action.setExame(exame);

		Exception exception = null;

		action.setExameManager(null);

		try
		{
			action.delete();
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testDeleteNaoExisteNaEmpresa() throws Exception
	{
		Exame exame = new Exame();
		exame.setId(1L);
		action.setExame(exame);

		Collection<Exame> exames = new ArrayList<Exame>();
		action.setExames(exames);

		listComplete();

		exameManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(null));

		assertEquals("success",action.delete());

	}
	public void testDeleteSemExame() throws Exception
	{
		Exame exame = new Exame();
		action.setExame(exame);

		listComplete();

		assertEquals("success",action.delete());
	}

	public void testPrepareImprimirAso()
	{
		assertEquals("success", action.prepareImprimirAso());
	}

	public void testFiltroImprimirAso()
	{
		Long empresaId = 1L;
		String cpf = "123.456.789-10";

		//Colaborador
		medicoCoordenadorManager.expects(once()).method("findByEmpresa").with(eq(empresaId)).will(returnValue(new ArrayList<MedicoCoordenador>()));
		StringUtil.removeMascara(cpf);
		colaboradorManager.expects(once()).method("findByNomeCpfMatricula").with(new Constraint[]{ANYTHING, ANYTHING, eq(null), eq(null), eq(new Long[]{empresaId})}).will(returnValue(new ArrayList<Colaborador>()));

		action.setEmitirPara('C');
		assertEquals("success", action.filtroImprimirAso());

		//Candidato
		medicoCoordenadorManager.expects(once()).method("findByEmpresa").with(eq(empresaId)).will(returnValue(new ArrayList<MedicoCoordenador>()));
		StringUtil.removeMascara(cpf);
		candidatoManager.expects(once()).method("findByNomeCpf").with(ANYTHING, eq(empresaId)).will(returnValue(new ArrayList<Candidato>()));

		action.setEmitirPara('A');
		assertEquals("success", action.filtroImprimirAso());
	}
	
/*
	public void testImprimirAso()
	{
		Long colaboradorId = 1L, candidatoId = 1L, medicoCoordenadorId = 1L;
		MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
		medicoCoordenador.setId(medicoCoordenadorId);
		Empresa empresaSistema = EmpresaFactory.getEmpresa(1L);
		empresaSistema.setCidade(new Cidade());

		//Colaborador
		medicoCoordenadorManager.expects(once()).method("findByIdProjection").with(eq(medicoCoordenadorId)).will(returnValue(new MedicoCoordenador()));
		colaboradorManager.expects(once()).method("findColaboradorByIdProjection").with(eq(colaboradorId)).will(returnValue(new Colaborador()));
		RelatorioUtil.getParametrosRelatorio("SERVIÇO DE MEDICINA OCUPACIONAL", new Empresa(), "ATESTADO DE SAÚDE OCUPACIONAL - ASO");

		action.setEmpresaSistema(empresaSistema);
		action.getColaborador().setId(colaboradorId);
		action.getCandidato().setId(null);
		assertEquals("success", action.imprimirAso());

		//Candidato
		medicoCoordenadorManager.expects(once()).method("findByIdProjection").with(eq(medicoCoordenadorId)).will(returnValue(new MedicoCoordenador()));
		candidatoManager.expects(once()).method("findByCandidatoId").with(eq(candidatoId)).will(returnValue(new Candidato()));
		RelatorioUtil.getParametrosRelatorio("SERVIÇO DE MEDICINA OCUPACIONAL", new Empresa(), "ATESTADO DE SAÚDE OCUPACIONAL - ASO");

		action.setEmpresaSistema(empresaSistema);
		action.getColaborador().setId(null);
		action.getCandidato().setId(candidatoId);
		assertEquals("success", action.imprimirAso());
	}
*/
	
	public void testImprimirAso()
	{
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(100L);
		solicitacaoExame.setColaborador(ColaboradorFactory.getEntity(1000L));
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		AsoRelatorio asoRelatorio = new AsoRelatorio(solicitacaoExame, empresa);
		solicitacaoExameManager.expects(once()).method("montaRelatorioAso").will(returnValue(asoRelatorio ));
		assertEquals("success", action.imprimirAso());
	}
	
	public void testImprimirAsoColecaoVazia()
	{
		solicitacaoExameManager.expects(once()).method("montaRelatorioAso").will(throwException(new ColecaoVaziaException("")));
		assertEquals("input", action.imprimirAso());
	}

	public void testPrepareRelatorioExamesPrevistos()
	{
		Long empresaId = 1L;
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(eq(empresaId)).will(returnValue(new ArrayList<CheckBox>()));
		estabelecimentoManager.expects(once()).method("populaCheckBox").with(eq(empresaId)).will(returnValue(new ArrayList<CheckBox>()));
		colaboradorManager.expects(once()).method("populaCheckBox").with(eq(empresaId)).will(returnValue(new ArrayList<CheckBox>()));
		exameManager.expects(once()).method("populaCheckBox").with(eq(empresaId)).will(returnValue(new ArrayList<CheckBox>()));

		assertEquals("success", action.prepareRelatorioExamesPrevistos());
	}

	
	public void testDeveriaGerarRelatorioDeExamesPrevitos() throws Exception {
		
		Long empresaId = 1L;
		
		ExamesPrevistosRelatorio examesPrevistosRelatorio = new ExamesPrevistosRelatorio();
		Collection<ExamesPrevistosRelatorio> colecaoRelatorio = new ArrayList<ExamesPrevistosRelatorio>();
		colecaoRelatorio.add(examesPrevistosRelatorio);

		exameManager.expects(atLeastOnce())
			.method("findRelatorioExamesPrevistos")
			.with(new Constraint[]{eq(empresaId), ANYTHING, ANYTHING, 
					eq(null),eq(null),eq(null),eq(null),ANYTHING,eq(false),eq(false)})
			.will(returnValue(colecaoRelatorio));
		
		estabelecimentoManager.expects(never())
			.method("nomeEstabelecimentos")
			.with(ANYTHING);
		
		assertEquals("success", action.relatorioExamesPrevistos());
	}
	
	public void testDeveriaGerarRelatorioDeExamesPrevitosQuandoTemParametros() throws Exception
	{
		Long empresaId = 1L;
		Collection<ExamesPrevistosRelatorio> colecaoRelatorio = new ArrayList<ExamesPrevistosRelatorio>();
		ExamesPrevistosRelatorio examesPrevistosRelatorio = new ExamesPrevistosRelatorio();
		colecaoRelatorio.add(examesPrevistosRelatorio);

		Long[] estabelecimentoIds = {1L};
		
		// Teste com os arrays de ids
		String[] areasCheck={"1"}, estabelecimentosCheck={"1"}, colaboradoresCheck={"1"}, examesCheck = {"1"};
		action.setAreasCheck(areasCheck);
		action.setEstabelecimentosCheck(estabelecimentosCheck);
		action.setExamesCheck(examesCheck);
		action.setColaboradoresCheck(colaboradoresCheck);

		exameManager.expects(atLeastOnce())
			.method("findRelatorioExamesPrevistos")
			.with(new Constraint[]{eq(empresaId), eq(null), eq(null), 
					eq(new Long[]{1L}), eq(new Long[]{1L}), 
					eq(new Long[]{1L}), eq(new Long[]{1L}), ANYTHING, ANYTHING,eq(false)})
			.will(returnValue(colecaoRelatorio));
		
		estabelecimentoManager.expects(atLeastOnce())
			.method("nomeEstabelecimentos")
			.with(eq(estabelecimentoIds), eq(null))
			.will(returnValue("Paizinho"));

		assertEquals("success", action.relatorioExamesPrevistos());
	}
	
	public void testRelatorioExamesPrevistosColecaoVaziaException() throws Exception
	{
		Long empresaId = 1L;
		
		exameManager.expects(once()).method("findRelatorioExamesPrevistos").with(new Constraint[]{eq(empresaId), ANYTHING, ANYTHING, eq(null),eq(null),eq(null),eq(null),ANYTHING,eq(false),eq(false)}).will(throwException(new ColecaoVaziaException("")));
		
		//prepare
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(eq(empresaId)).will(returnValue(new ArrayList<CheckBox>()));
		estabelecimentoManager.expects(once()).method("populaCheckBox").with(eq(empresaId)).will(returnValue(new ArrayList<CheckBox>()));
		colaboradorManager.expects(once()).method("populaCheckBox").with(eq(empresaId)).will(returnValue(new ArrayList<CheckBox>()));
		exameManager.expects(once()).method("populaCheckBox").with(eq(empresaId)).will(returnValue(new ArrayList<CheckBox>()));
		
		assertEquals("input", action.relatorioExamesPrevistos());
	}
	
	public void testPrepareRelatorioExamesRealizados()
	{
		Long empresaId = 1L;
		estabelecimentoManager.expects(once()).method("populaCheckBox").with(eq(empresaId)).will(returnValue(new ArrayList<CheckBox>()));
		clinicaAutorizadaManager.expects(once()).method("findByDataEmpresa").with(eq(empresaId), ANYTHING, eq(null)).will(returnValue(new ArrayList<ClinicaAutorizada>()));
		exameManager.expects(once()).method("populaCheckBox").with(eq(empresaId)).will(returnValue(new ArrayList<CheckBox>()));

		assertEquals("success", action.prepareRelatorioExamesRealizados());
	}

	public void testRelatorioExamesRealizados()
	{
		Date inicio = new Date();
		Date fim = new Date();
		Long empresaId = 1L;
		Collection<ExamesRealizadosRelatorio> colecaoRelatorio = new ArrayList<ExamesRealizadosRelatorio>();
		ExamesRealizadosRelatorio examesRealizadosRelatorio = new ExamesRealizadosRelatorio();
		colecaoRelatorio.add(examesRealizadosRelatorio);
		
		String[] estabelecimentosCheck={"1"}, examesCheck = {"1"};
		action.setEstabelecimentosCheck(estabelecimentosCheck);
		action.setExamesCheck(examesCheck);
		action.setMotivo(MotivoSolicitacaoExame.ADMISSIONAL);
		action.setResultado(ResultadoExame.ANORMAL.toString());
		action.setInicio(inicio);
		action.setFim(fim);
		
		Long[] estabelecimentoIds = {1L};
		
		exameManager.expects(once()).method("findRelatorioExamesRealizados").with(new Constraint[]{eq(empresaId), ANYTHING, eq(inicio), eq(fim), eq(MotivoSolicitacaoExame.ADMISSIONAL), eq(ResultadoExame.ANORMAL.toString()), eq(null), ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colecaoRelatorio));
		
		estabelecimentoManager.expects(atLeastOnce())
			.method("nomeEstabelecimentos")
			.with(eq(estabelecimentoIds), eq(null))
			.will(returnValue("Paizinho"));
		
		assertEquals("success", action.relatorioExamesRealizados());
	}

	public void testRelatorioExamesRealizadosColecaoVaziaException()
	{
		Long empresaId = 1L;

		exameManager.expects(once()).method("findRelatorioExamesRealizados").will(throwException(new ColecaoVaziaException("")));

		//prepare
		estabelecimentoManager.expects(once()).method("populaCheckBox").with(eq(empresaId)).will(returnValue(new ArrayList<CheckBox>()));
		clinicaAutorizadaManager.expects(once()).method("findByDataEmpresa").will(returnValue(new ArrayList<ClinicaAutorizada>()));
		exameManager.expects(once()).method("populaCheckBox").with(eq(empresaId)).will(returnValue(new ArrayList<CheckBox>()));

		assertEquals("input", action.relatorioExamesRealizados());
	}
	
	public void testGetSet(){
		action.getExame();
		action.getExames();

		action.getAreasCheck();
		action.getAreasCheckList();
		action.getEstabelecimentosCheck();
		action.getEstabelecimentosCheckList();
		action.getExamesCheck();
		action.getExamesCheckList();
		action.getColaboradoresCheck();
		action.getColaboradoresCheckList();
		
		action.setCandidato(null);
		action.setColaborador(null);
		
		action.getEmitirPara();
		action.getCandidatos();
		action.getColaboradors();
		action.getMedicoCoordenadors();
		action.getClinicas();
		action.getAsoRelatorio();
		
		action.getColecaoExamesPrevistos();
		action.getInicio();
		action.getFim();
		action.getParametros();
		action.getMotivo();
		action.getResultado();
		action.getMotivos();
		action.getClinicaAutorizada();
		action.setClinicaAutorizada(null);
		action.getExamesRealizados();
		action.setSolicitacaoExame(null);
		
		action.setAgruparPor('A');
		action.getAgruparPor();
	}
}
