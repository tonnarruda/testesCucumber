package com.fortes.rh.test.business.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.sesmt.CatManager;
import com.fortes.rh.business.sesmt.EngenheiroResponsavelManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.HistoricoAmbienteManager;
import com.fortes.rh.business.sesmt.HistoricoFuncaoManager;
import com.fortes.rh.business.sesmt.MedicoCoordenadorManager;
import com.fortes.rh.business.sesmt.PppRelatorioManagerImpl;
import com.fortes.rh.business.sesmt.RiscoMedicaoRiscoManager;
import com.fortes.rh.exception.PppRelatorioException;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.TipoRisco;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.relatorio.PppFatorRisco;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.model.sesmt.RiscoFuncao;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;
import com.fortes.rh.model.sesmt.relatorio.DadosAmbienteOuFuncaoRisco;
import com.fortes.rh.model.sesmt.relatorio.PppRelatorio;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.test.factory.sesmt.MedicaoRiscoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoAmbienteFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFuncaoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoMedicaoRiscoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.DateUtil;

public class PppRelatorioManagerTest
{
	private PppRelatorioManagerImpl pppRelatorioManagerImpl = new PppRelatorioManagerImpl();
	private ColaboradorManager colaboradorManager;
	private CatManager catManager;
	private RiscoMedicaoRiscoManager riscoMedicaoRiscoManager;
	private EpiManager epiManager;
	private HistoricoAmbienteManager historicoAmbienteManager;
	private EngenheiroResponsavelManager engenheiroResponsavelManager;
	private MedicoCoordenadorManager medicoCoordenadorManager;
	private HistoricoFuncaoManager historicoFuncaoManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private EmpresaManager empresaManager;
	
	private static boolean AMBIENTE = true;
	private static boolean FUNCAO = false;

	@Before
    public void setUp() throws Exception
    {
		colaboradorManager = mock(ColaboradorManager.class);
		pppRelatorioManagerImpl.setColaboradorManager(colaboradorManager);
		
		catManager = mock(CatManager.class);
		pppRelatorioManagerImpl.setCatManager(catManager);
		
		riscoMedicaoRiscoManager = mock(RiscoMedicaoRiscoManager.class);
		pppRelatorioManagerImpl.setRiscoMedicaoRiscoManager(riscoMedicaoRiscoManager);
		
		epiManager = mock(EpiManager.class);
		pppRelatorioManagerImpl.setEpiManager(epiManager);

		historicoAmbienteManager = mock(HistoricoAmbienteManager.class);
        pppRelatorioManagerImpl.setHistoricoAmbienteManager(historicoAmbienteManager);
        
        engenheiroResponsavelManager = mock(EngenheiroResponsavelManager.class);
        pppRelatorioManagerImpl.setEngenheiroResponsavelManager(engenheiroResponsavelManager);
        
        medicoCoordenadorManager = mock(MedicoCoordenadorManager.class);
        pppRelatorioManagerImpl.setMedicoCoordenadorManager(medicoCoordenadorManager);
        
        historicoFuncaoManager = mock(HistoricoFuncaoManager.class);
        pppRelatorioManagerImpl.setHistoricoFuncaoManager(historicoFuncaoManager);

        historicoColaboradorManager = mock(HistoricoColaboradorManager.class);
        pppRelatorioManagerImpl.setHistoricoColaboradorManager(historicoColaboradorManager);
        
        empresaManager = mock(EmpresaManager.class);
        pppRelatorioManagerImpl.setEmpresaManager(empresaManager);
        
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }
	
	@Test
	public void testPopulaRelatorioPpp() throws Exception
	{
		Date hoje = new Date();
		Date dataH1 = DateUtil.criarDataMesAno(1, 9, 2005);
		Date dataH2 = DateUtil.criarDataMesAno(27, 9, 2005);
		Date dataH3 = DateUtil.criarDataMesAno(30, 1, 2006);
		Date dataAmbienteFuncao = DateUtil.criarDataMesAno(19, 4, 2004);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setControlaRiscoPor('A');
		
		Ambiente ambiente = AmbienteFactory.getEntity(10L);
		Funcao funcao = FuncaoFactory.getEntity(3L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1000L);
		
		Cargo cargo = CargoFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(3L);
		historicoColaborador1.setData(dataH1);
		historicoColaborador1.setFuncao(funcao);
		historicoColaborador1.setAmbiente(ambiente);
		historicoColaborador1.setColaborador(colaborador);
		historicoColaborador1.setFaixaSalarial(faixaSalarial);
		
		HistoricoColaborador historicoColaborador2 = new HistoricoColaborador();
		historicoColaborador2.setData(dataH2);
		historicoColaborador2.setColaborador(colaborador);
		historicoColaborador2.setFaixaSalarial(faixaSalarial);
		historicoColaborador2.setFuncao(funcao);
		historicoColaborador2.setAmbiente(ambiente);
		
		HistoricoColaborador historicoColaborador3 = HistoricoColaboradorFactory.getEntity(10L);
		historicoColaborador3.setData(dataH3);
		historicoColaborador3.setFuncao(funcao);
		historicoColaborador3.setAmbiente(ambiente);
		historicoColaborador3.setColaborador(colaborador);
		historicoColaborador3.setFaixaSalarial(faixaSalarial);
		
		Risco riscoFisico = new Risco(1L, "Risco Fisico", TipoRisco.FISICO);
		Risco riscoBiologico = new Risco(2L, "Risco Biologico", TipoRisco.BIOLOGICO);
		
		RiscoAmbiente riscoAmbienteFisico = RiscoAmbienteFactory.getEntity();
		riscoAmbienteFisico.setRisco(riscoFisico);
		RiscoAmbiente riscoAmbienteBiologico = RiscoAmbienteFactory.getEntity();
		riscoAmbienteBiologico.setRisco(riscoBiologico);
		
		RiscoFuncao riscoFuncaoFisico = RiscoFuncaoFactory.getEntity();
		riscoFuncaoFisico.setRisco(riscoFisico);
		RiscoFuncao riscoFuncaoBiologico = RiscoFuncaoFactory.getEntity();
		riscoFuncaoBiologico.setRisco(riscoBiologico);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setData(dataAmbienteFuncao);
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbiente.setRiscoAmbientes(Arrays.asList(riscoAmbienteFisico, riscoAmbienteBiologico));
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setData(dataAmbienteFuncao);
		historicoFuncao.setFuncao(funcao);
		historicoFuncao.setRiscoFuncaos(Arrays.asList(riscoFuncaoFisico, riscoFuncaoBiologico));
		
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(historicoColaborador1);
		historicoColaboradors.add(historicoColaborador2);
		historicoColaboradors.add(historicoColaborador3);
		
		Collection<EngenheiroResponsavel> engenheirosResponsaveis = Arrays.asList(new EngenheiroResponsavel());
		Collection<MedicoCoordenador> medicosCoordenadores = Arrays.asList(new MedicoCoordenador());
		
		DadosAmbienteOuFuncaoRisco dadosAmbienteRisco1 = new DadosAmbienteOuFuncaoRisco(ambiente.getId(), riscoFisico.getId(), riscoFisico.getDescricao(), riscoFisico.getGrupoRisco(), false, historicoAmbiente.getData());
		DadosAmbienteOuFuncaoRisco dadosAmbienteRisco2 = new DadosAmbienteOuFuncaoRisco(ambiente.getId(), riscoBiologico.getId(), riscoBiologico.getDescricao(), riscoBiologico.getGrupoRisco(), false, historicoAmbiente.getData());
		List<DadosAmbienteOuFuncaoRisco> dadosAmbientesRiscos = Arrays.asList(dadosAmbienteRisco1,dadosAmbienteRisco2);
		
		DadosAmbienteOuFuncaoRisco dadosFuncaoRisco1 = new DadosAmbienteOuFuncaoRisco(funcao.getId(), riscoFisico.getId(), riscoFisico.getDescricao(), riscoFisico.getGrupoRisco(), false, historicoFuncao.getData());
		DadosAmbienteOuFuncaoRisco dadosFuncaoRisco2 = new DadosAmbienteOuFuncaoRisco(funcao.getId(), riscoBiologico.getId(), riscoBiologico.getDescricao(), riscoBiologico.getGrupoRisco(), false, historicoFuncao.getData());
		List<DadosAmbienteOuFuncaoRisco> dadosFuncaoRiscos = Arrays.asList(dadosFuncaoRisco1,dadosFuncaoRisco2);
		
		Collection<Epi> epis = Arrays.asList(EpiFactory.getEntity());
		
		MedicaoRisco medicaoRisco = MedicaoRiscoFactory.getEntity(1L);
		medicaoRisco.setData(DateUtil.criarDataMesAno(1, 1, 2000));
		
		RiscoMedicaoRisco riscoMedicaoRisco =  RiscoMedicaoRiscoFactory.getEntity();
		riscoMedicaoRisco.setMedicaoRisco(medicaoRisco);
		
		List<RiscoMedicaoRisco> riscoMedicaoRiscos = Arrays.asList(riscoMedicaoRisco);
		
		when(historicoColaboradorManager.findByColaboradorData(colaborador.getId(), hoje)).thenReturn(historicoColaboradors);
		when(historicoAmbienteManager.findUltimoHistoricoAteData(historicoColaborador1.getAmbiente().getId(), historicoColaborador1.getData())).thenReturn(historicoAmbiente);
		when(historicoAmbienteManager.findUltimoHistoricoAteData(historicoColaborador2.getAmbiente().getId(), historicoColaborador2.getData())).thenReturn(historicoAmbiente);
		when(historicoAmbienteManager.findUltimoHistoricoAteData(historicoColaborador3.getAmbiente().getId(), historicoColaborador3.getData())).thenReturn(historicoAmbiente);
		
		when(historicoColaboradorManager.filtraHistoricoColaboradorParaPPP(historicoColaboradors)).thenReturn(historicoColaboradors);
		when(historicoFuncaoManager.findUltimoHistoricoAteData(funcao.getId(), historicoColaborador1.getData())).thenReturn(historicoFuncao);
		when(historicoFuncaoManager.findUltimoHistoricoAteData(funcao.getId(), historicoColaborador2.getData())).thenReturn(historicoFuncao);
		when(historicoFuncaoManager.findUltimoHistoricoAteData(funcao.getId(), historicoColaborador3.getData())).thenReturn(historicoFuncao);
		
		when(colaboradorManager.findById(colaborador.getId())).thenReturn(colaborador);
		when(historicoColaboradorManager.inserirPeriodos(historicoColaboradors)).thenReturn(historicoColaboradors);
		
		when(historicoColaboradorManager.findDistinctFuncao(historicoColaboradors)).thenReturn(historicoColaboradors);
		when(historicoFuncaoManager.findHistoricoFuncaoColaborador(historicoColaboradors, hoje, colaborador.getDataDesligamento())).thenReturn(Arrays.asList(historicoFuncao));
		when(engenheiroResponsavelManager.getEngenheirosAteData(colaborador, hoje)).thenReturn(engenheirosResponsaveis);
		when(medicoCoordenadorManager.getMedicosAteData(hoje, colaborador)).thenReturn(medicosCoordenadores);
		
		when(historicoAmbienteManager.findDadosNoPeriodo(ambiente.getId(), historicoColaborador1.getData(), historicoColaborador2.getData())).thenReturn(dadosAmbientesRiscos);
		when(historicoAmbienteManager.findDadosNoPeriodo(ambiente.getId(), historicoColaborador2.getData(), historicoColaborador3.getData())).thenReturn(dadosAmbientesRiscos);
		when(historicoAmbienteManager.findDadosNoPeriodo(ambiente.getId(), historicoColaborador3.getData(), hoje)).thenReturn(dadosAmbientesRiscos);
		
		when(historicoFuncaoManager.findDadosNoPeriodo(funcao.getId(), historicoColaborador1.getData(), historicoColaborador2.getData())).thenReturn(dadosFuncaoRiscos);
		when(historicoFuncaoManager.findDadosNoPeriodo(funcao.getId(), historicoColaborador2.getData(), historicoColaborador3.getData())).thenReturn(dadosFuncaoRiscos);
		when(historicoFuncaoManager.findDadosNoPeriodo(funcao.getId(), historicoColaborador3.getData(), hoje)).thenReturn(dadosFuncaoRiscos);
		
		when(epiManager.findByRiscoAmbienteOuFuncao(riscoFisico.getId(), ambiente.getId(), historicoAmbiente.getData(), AMBIENTE)).thenReturn(epis);
		when(epiManager.findByRiscoAmbienteOuFuncao(riscoBiologico.getId(), ambiente.getId(), historicoAmbiente.getData(), AMBIENTE)).thenReturn(epis);
		when(epiManager.findByRiscoAmbienteOuFuncao(riscoFisico.getId(), funcao.getId(), historicoFuncao.getData(), FUNCAO)).thenReturn(epis);
		when(epiManager.findByRiscoAmbienteOuFuncao(riscoBiologico.getId(), funcao.getId(), historicoFuncao.getData(), FUNCAO)).thenReturn(epis);
		
		when(riscoMedicaoRiscoManager.getByRiscoPeriodo(riscoFisico.getId(), ambiente.getId(), historicoAmbiente.getData(), historicoColaborador2.getData(), AMBIENTE)).thenReturn(riscoMedicaoRiscos);
		when(riscoMedicaoRiscoManager.getByRiscoPeriodo(riscoBiologico.getId(), ambiente.getId(), historicoAmbiente.getData(), historicoColaborador2.getData(), AMBIENTE)).thenReturn(riscoMedicaoRiscos);
		when(riscoMedicaoRiscoManager.getByRiscoPeriodo(riscoBiologico.getId(), ambiente.getId(), historicoAmbiente.getData(), hoje, AMBIENTE)).thenReturn(riscoMedicaoRiscos);
		when(riscoMedicaoRiscoManager.getByRiscoPeriodo(riscoFisico.getId(), funcao.getId(), historicoFuncao.getData(), historicoColaborador2.getData(), FUNCAO)).thenReturn(riscoMedicaoRiscos);
		when(riscoMedicaoRiscoManager.getByRiscoPeriodo(riscoBiologico.getId(), funcao.getId(), historicoFuncao.getData(), historicoColaborador2.getData(), FUNCAO)).thenReturn(riscoMedicaoRiscos);
		when(riscoMedicaoRiscoManager.getByRiscoPeriodo(riscoBiologico.getId(), funcao.getId(), historicoFuncao.getData(), hoje, FUNCAO)).thenReturn(riscoMedicaoRiscos);
		
		when(riscoMedicaoRiscoManager.findUltimaAteData(ambiente.getId(), historicoColaborador1.getData(), AMBIENTE)).thenReturn(medicaoRisco);
		when(riscoMedicaoRiscoManager.findUltimaAteData(ambiente.getId(), historicoColaborador2.getData(), AMBIENTE)).thenReturn(medicaoRisco);
		when(riscoMedicaoRiscoManager.findUltimaAteData(ambiente.getId(), historicoColaborador3.getData(), AMBIENTE)).thenReturn(medicaoRisco);
		
		when(riscoMedicaoRiscoManager.findUltimaAteData(funcao.getId(), historicoColaborador1.getData(), FUNCAO)).thenReturn(medicaoRisco);
		when(riscoMedicaoRiscoManager.findUltimaAteData(funcao.getId(), historicoColaborador2.getData(), FUNCAO)).thenReturn(medicaoRisco);
		when(riscoMedicaoRiscoManager.findUltimaAteData(funcao.getId(), historicoColaborador3.getData(), FUNCAO)).thenReturn(medicaoRisco);
		
		when(empresaManager.isControlaRiscoPorAmbiente(empresa.getId())).thenReturn(true);
		
		Collection<PppRelatorio> pppRelatoriosRetorno = pppRelatorioManagerImpl.populaRelatorioPpp(colaborador , empresa, hoje, "111", null, "Resp.", "obs", new String[5], empresa.getId());
		assertEquals(1, pppRelatoriosRetorno.size());
		
		Collection<PppFatorRisco> PppFatorRisco = ((PppRelatorio) pppRelatoriosRetorno.toArray()[0]).getFatoresRiscos();
		assertEquals(2, PppFatorRisco.size());
		
		assertEquals(dataH1, ((PppFatorRisco)PppFatorRisco.toArray()[0]).getDataInicio());
		assertNull(((PppFatorRisco)PppFatorRisco.toArray()[0]).getDataFim());
		assertEquals(riscoBiologico.getId(), ((PppFatorRisco)PppFatorRisco.toArray()[0]).getRisco().getId());

		assertEquals(dataH1, ((PppFatorRisco)PppFatorRisco.toArray()[1]).getDataInicio());
		assertEquals(dataH2, ((PppFatorRisco)PppFatorRisco.toArray()[1]).getDataFim());
		assertEquals(riscoFisico.getId(), ((PppFatorRisco)PppFatorRisco.toArray()[1]).getRisco().getId());
		
		when(empresaManager.isControlaRiscoPorAmbiente(empresa.getId())).thenReturn(false);
		
		pppRelatoriosRetorno = pppRelatorioManagerImpl.populaRelatorioPpp(colaborador , empresa, hoje, "111", null, "Resp.", "obs", new String[5], empresa.getId());
		assertEquals(1, pppRelatoriosRetorno.size());
		
		PppFatorRisco = ((PppRelatorio) pppRelatoriosRetorno.toArray()[0]).getFatoresRiscos();
		assertEquals(2, PppFatorRisco.size());
		
		assertEquals(dataH1, ((PppFatorRisco)PppFatorRisco.toArray()[0]).getDataInicio());
		assertNull(((PppFatorRisco)PppFatorRisco.toArray()[0]).getDataFim());
		assertEquals(riscoBiologico.getId(), ((PppFatorRisco)PppFatorRisco.toArray()[0]).getRisco().getId());

		assertEquals(dataH1, ((PppFatorRisco)PppFatorRisco.toArray()[1]).getDataInicio());
		assertEquals(dataH2, ((PppFatorRisco)PppFatorRisco.toArray()[1]).getDataFim());
		assertEquals(riscoFisico.getId(), ((PppFatorRisco)PppFatorRisco.toArray()[1]).getRisco().getId());
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void testPopulaRelatorioPppExcecao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setControlaRiscoPor('A');
		
		Ambiente ambiente = AmbienteFactory.getEntity(10L);
		Ambiente ambiente2 = AmbienteFactory.getEntity(12L);
		Funcao funcao = FuncaoFactory.getEntity(3L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1000L);
		
		Cargo cargo = CargoFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		
		HistoricoColaborador historicoColaboradorSemAmbiente = HistoricoColaboradorFactory.getEntity(3L);
		historicoColaboradorSemAmbiente.setData(DateUtil.criarAnoMesDia(2005, 9, 1));
		historicoColaboradorSemAmbiente.setFuncao(funcao);
		historicoColaboradorSemAmbiente.setColaborador(colaborador);
		historicoColaboradorSemAmbiente.setFaixaSalarial(faixaSalarial);
		
		HistoricoColaborador historicoColaboradorSemAmbienteEFuncao = new HistoricoColaborador();
		historicoColaboradorSemAmbienteEFuncao.setData(DateUtil.criarAnoMesDia(2005, 9, 27));
		historicoColaboradorSemAmbienteEFuncao.setColaborador(colaborador);
		historicoColaboradorSemAmbienteEFuncao.setFaixaSalarial(faixaSalarial);
		
		HistoricoColaborador historicoColaboradorComAmbienteSemHistorico = HistoricoColaboradorFactory.getEntity(10L);
		historicoColaboradorComAmbienteSemHistorico.setData(DateUtil.criarAnoMesDia(2006, 1, 30));
		historicoColaboradorComAmbienteSemHistorico.setFuncao(funcao);
		historicoColaboradorComAmbienteSemHistorico.setAmbiente(ambiente);
		historicoColaboradorComAmbienteSemHistorico.setColaborador(colaborador);
		historicoColaboradorComAmbienteSemHistorico.setFaixaSalarial(faixaSalarial);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setData(DateUtil.criarAnoMesDia(2009, 4, 19));
		historicoAmbiente.setAmbiente(ambiente2);
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setData(DateUtil.criarAnoMesDia(2005, 4, 19));
		historicoFuncao.setFuncao(funcao);
		
		HistoricoColaborador historicoColaboradorComHistoricoAmbienteSemMedicao = HistoricoColaboradorFactory.getEntity(15L);
		historicoColaboradorComHistoricoAmbienteSemMedicao.setData(DateUtil.criarAnoMesDia(2009, 4, 20));
		historicoColaboradorComHistoricoAmbienteSemMedicao.setAmbiente(ambiente2);
		historicoColaboradorComHistoricoAmbienteSemMedicao.setFuncao(funcao);
		historicoColaboradorComHistoricoAmbienteSemMedicao.setColaborador(colaborador);
		historicoColaboradorComHistoricoAmbienteSemMedicao.setFaixaSalarial(faixaSalarial);
		
		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(historicoColaboradorSemAmbiente);
		historicoColaboradors.add(historicoColaboradorSemAmbienteEFuncao);
		historicoColaboradors.add(historicoColaboradorComAmbienteSemHistorico);
		historicoColaboradors.add(historicoColaboradorComHistoricoAmbienteSemMedicao);
		
		Date hoje = new Date();
		
		when(empresaManager.isControlaRiscoPorAmbiente(empresa.getId())).thenReturn(true);
		when(historicoColaboradorManager.findByColaboradorData(colaborador.getId(), hoje)).thenReturn(historicoColaboradors);
		when(historicoAmbienteManager.findUltimoHistoricoAteData(historicoColaboradorComAmbienteSemHistorico.getAmbiente().getId(), historicoColaboradorComAmbienteSemHistorico.getData())).thenReturn(null);
		when(historicoAmbienteManager.findUltimoHistoricoAteData(historicoColaboradorComHistoricoAmbienteSemMedicao.getAmbiente().getId(), historicoColaboradorComHistoricoAmbienteSemMedicao.getData())).thenReturn(historicoAmbiente);
		
		when(historicoColaboradorManager.filtraHistoricoColaboradorParaPPP(historicoColaboradors)).thenReturn(historicoColaboradors);
		when(historicoFuncaoManager.findUltimoHistoricoAteData(funcao.getId(), historicoColaboradorSemAmbiente.getData())).thenReturn(null);
		when(historicoFuncaoManager.findUltimoHistoricoAteData(funcao.getId(), historicoColaboradorComAmbienteSemHistorico.getData())).thenReturn(historicoFuncao);
		when(historicoFuncaoManager.findUltimoHistoricoAteData(funcao.getId(), historicoColaboradorComHistoricoAmbienteSemMedicao.getData())).thenReturn(historicoFuncao);
		PppRelatorioException pppRelatorioException = null;
		Exception exception = null;
		
		MockSecurityUtil.verifyRole = true;
		
		try 
		{
			pppRelatorioManagerImpl.populaRelatorioPpp(colaborador , empresa, hoje, "111", null, "Resp.", "obs", new String[5], empresa.getId());
		}
		catch (PppRelatorioException e)
		{
			pppRelatorioException = e;
		} 
		catch (Exception e) 
		{
			exception = e;
			e.printStackTrace();
		}
		
		assertNull(exception);
		assertNotNull(pppRelatorioException);
		
		String mensagemFormatada = pppRelatorioException.getMensagemDeInformacao();
		
		String result = "Existem pendências para a geração desse relatório. Verifique as informações abaixo antes de prosseguir: <br>" +
		"<a href='../../cargosalario/historicoColaborador/prepareUpdateAmbientesEFuncoes.action?colaborador.id=1000'>" +
		"01/09/2005 - Situação do colaborador não possui ambiente definido.</a><br />" +
		"<a href='../../cargosalario/historicoColaborador/prepareUpdateAmbientesEFuncoes.action?colaborador.id=1000'>" +
		"27/09/2005 - Situação do colaborador não possui ambiente definido.</a><br />" + 
		"<a href='../../cargosalario/historicoColaborador/prepareUpdateAmbientesEFuncoes.action?colaborador.id=1000'>" + 
		"27/09/2005 - Situação do colaborador não possui função definida.</a><br />" + 
		"<a href='../ambiente/prepareUpdate.action?ambiente.id=10'>" +
		"30/01/2006 - Ambiente do colaborador não possui histórico nesta data.</a><br />";

		assertEquals(result, mensagemFormatada);
	}
}