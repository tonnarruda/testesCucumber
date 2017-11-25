package com.fortes.rh.test.dao.hibernate.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.dao.sesmt.EpcDao;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.dao.sesmt.HistoricoAmbienteDao;
import com.fortes.rh.dao.sesmt.MedicaoRiscoDao;
import com.fortes.rh.dao.sesmt.RiscoMedicaoRiscoDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.LocalAmbiente;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoAmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.DateUtil;

public class AmbienteDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<Ambiente>
{
	@Autowired private AmbienteDao ambienteDao;
	@Autowired private EmpresaDao empresaDao;
	@Autowired private EstabelecimentoDao estabelecimentoDao;
	@Autowired private HistoricoAmbienteDao historicoAmbienteDao;
	
	@Autowired private ColaboradorDao colaboradorDao;
	@Autowired private HistoricoColaboradorDao historicoColaboradorDao;
	@Autowired private FuncaoDao funcaoDao;
	@Autowired private MedicaoRiscoDao medicaoRiscoDao;
	@Autowired private RiscoMedicaoRiscoDao riscoMedicaoRiscoDao;
	@Autowired private EpcDao epcDao;

	public Ambiente getEntity()
	{
		return AmbienteFactory.getEntity();
	}
	
	public GenericDao<Ambiente> getGenericDao()
	{
		return ambienteDao;
	}

	@Test
	public void testGetCount()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Estabelecimento estabelecimento = estabelecimentoDao.save(EstabelecimentoFactory.getEntity("nome", empresa));

		Ambiente a1 = ambienteDao.save(AmbienteFactory.getEntity("a", empresa));
		Ambiente a2 = ambienteDao.save(AmbienteFactory.getEntity("b", empresa));

		HistoricoAmbiente historicoAmbiente = HistoricoAmbienteFactory.getEntity(a1.getNome(), estabelecimento, "descricao", a1, new Date(), "");
		historicoAmbiente.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente);
		
		HistoricoAmbiente historicoAmbiente2 = HistoricoAmbienteFactory.getEntity(a2.getNome(), null, "descricao", a2, new Date(), "");
		historicoAmbiente2.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente2);
	

		Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
		ambientes.add(a1);
		ambientes.add(a2);

		ambienteDao.save(a1);
		ambienteDao.save(a2);

		int retorno = ambienteDao.getCount(empresa.getId(), null);

		assertEquals(ambientes.size(), retorno);
	}

	@Test
	public void testFindAmbientesTodosOsAmbienteDeTerceiroEDoEmpregador()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = estabelecimentoDao.save(EstabelecimentoFactory.getEntity("nome", empresa));

		Ambiente a1 = ambienteDao.save(AmbienteFactory.getEntity("a", empresa));
		Ambiente a2 = ambienteDao.save(AmbienteFactory.getEntity("b", empresa));

		HistoricoAmbiente historicoAmbiente = HistoricoAmbienteFactory.getEntity(a1.getNome(), estabelecimento, "descricao", a1, new Date(), "");
		historicoAmbiente.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente);
		
		HistoricoAmbiente historicoAmbiente2 = HistoricoAmbienteFactory.getEntity(a2.getNome(), null, "descricao", a2, new Date(), "");
		historicoAmbiente2.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente2);
		
		Collection<Ambiente> ambientes = Arrays.asList(a1, a2);
		HistoricoAmbiente historicoAmbienteParaConsulta = new HistoricoAmbiente();
		historicoAmbienteParaConsulta.setLocalAmbiente(null);

		Collection<Ambiente> ambientesRetorno = ambienteDao.findAmbientes(0, 0, empresa.getId(), historicoAmbienteParaConsulta);

		assertEquals("findAmbientes sem paginação",ambientes, ambientesRetorno);

		ambientesRetorno = ambienteDao.findAmbientes(1, 15, empresa.getId(), null);

		assertEquals("findAmbientes com paginação",ambientes, ambientesRetorno);
	}
	
	
	@Test
	public void testFindAmbientesComFiltroLocalAmbienteIgualATodosEEstabelecimentoSelecionado()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = estabelecimentoDao.save(EstabelecimentoFactory.getEntity("nome", empresa));

		Ambiente a1 = ambienteDao.save(AmbienteFactory.getEntity("a", empresa));
		Ambiente a2 = ambienteDao.save(AmbienteFactory.getEntity("b", empresa));

		HistoricoAmbiente historicoAmbiente = HistoricoAmbienteFactory.getEntity(a1.getNome(), estabelecimento, "descricao", a1, new Date(), "");
		historicoAmbiente.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente);
		
		HistoricoAmbiente historicoAmbiente2 = HistoricoAmbienteFactory.getEntity(a2.getNome(), null, "descricao", a2, new Date(), "");
		historicoAmbiente2.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente2);
		
		Collection<Ambiente> ambientes = Arrays.asList(a1);
		HistoricoAmbiente historicoAmbienteParaConsulta = new HistoricoAmbiente();
		historicoAmbienteParaConsulta.setLocalAmbiente(null);
		historicoAmbienteParaConsulta.setEstabelecimento(estabelecimento);

		Collection<Ambiente> ambientesRetorno = ambienteDao.findAmbientes(0, 0, empresa.getId(), historicoAmbienteParaConsulta);

		assertEquals("findAmbientes selecionando somente o filtro de estabelecimento",ambientes, ambientesRetorno);
	}
	
	@Test
	public void testFindAmbientesComFiltroEstabelecimentosDeterceiros()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = estabelecimentoDao.save(EstabelecimentoFactory.getEntity("nome", empresa));

		Ambiente a1 = ambienteDao.save(AmbienteFactory.getEntity("a", empresa));
		Ambiente a2 = ambienteDao.save(AmbienteFactory.getEntity("b", empresa));

		HistoricoAmbiente historicoAmbiente = HistoricoAmbienteFactory.getEntity(a1.getNome(), estabelecimento, "descricao", a1, new Date(), "");
		historicoAmbiente.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente);
		
		HistoricoAmbiente historicoAmbiente2 = HistoricoAmbienteFactory.getEntity(a2.getNome(), null, "descricao", a2, new Date(), "");
		historicoAmbiente2.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente2);
		
		Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
		ambientes.add(a2);

		historicoAmbiente2.setNomeAmbiente("");
		Collection<Ambiente> ambientesRetorno = ambienteDao.findAmbientes(0, 0, empresa.getId(), historicoAmbiente2);
		assertEquals("findAmbientes de estabelecimentos de terceiros",ambientes, ambientesRetorno);
	}
	
	@Test
	public void testFindAmbientesVisualizarTodosOSAmbienteDeUmEstabelecimento()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = estabelecimentoDao.save(EstabelecimentoFactory.getEntity("nome", empresa));

		Ambiente a1 = ambienteDao.save(AmbienteFactory.getEntity("a", empresa));
		Ambiente a2 = ambienteDao.save(AmbienteFactory.getEntity("b", empresa));

		HistoricoAmbiente historicoAmbiente = HistoricoAmbienteFactory.getEntity(a1.getNome(), estabelecimento, "descricao", a1, new Date(), "");
		historicoAmbiente.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente);
		
		HistoricoAmbiente historicoAmbiente2 = HistoricoAmbienteFactory.getEntity(a2.getNome(), null, "descricao", a2, new Date(), "");
		historicoAmbiente2.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente2);
		
		Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
		ambientes.add(a1);

		Collection<Ambiente> ambientesRetorno = ambienteDao.findAmbientes(0, 0, empresa.getId(), historicoAmbiente);
		assertEquals("findAmbientes visualizar todos os ambientes do empregado que estão relacionados a um estabelecimento",ambientes, ambientesRetorno);
	}
	
	@Test
	public void testFindAmbientesComFiltroEstabelecimentosDoProprioEmpregadorEscolhendoVisualizarTodososEstabelecimentos()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = estabelecimentoDao.save(EstabelecimentoFactory.getEntity("nome", empresa));

		Ambiente a1 = ambienteDao.save(AmbienteFactory.getEntity("a", empresa));
		Ambiente a2 = ambienteDao.save(AmbienteFactory.getEntity("b", empresa));

		HistoricoAmbiente historicoAmbiente = HistoricoAmbienteFactory.getEntity(a1.getNome(), estabelecimento, "descricao", a1, new Date(), "");
		historicoAmbiente.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente);
		
		HistoricoAmbiente historicoAmbiente2 = HistoricoAmbienteFactory.getEntity(a2.getNome(), null, "descricao", a2, new Date(), "");
		historicoAmbiente2.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente2);
		
		Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
		ambientes.add(a1);

		HistoricoAmbiente historicoAmbienteParametro = HistoricoAmbienteFactory.getEntity(a1.getNome(), null, "descricao", a1, new Date(), "");
		historicoAmbienteParametro.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		Collection<Ambiente> ambientesRetorno = ambienteDao.findAmbientes(0, 0, empresa.getId(), historicoAmbienteParametro);
		assertEquals("findAmbientes todos os estabalecimento do empregador de todos os estabelecimentos",ambientes, ambientesRetorno);
	}
	
	@Test
	public void testFindAmbientesComFiltroLocalAmbienteComValorInvalido()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = estabelecimentoDao.save(EstabelecimentoFactory.getEntity("nome", empresa));

		Ambiente a1 = ambienteDao.save(AmbienteFactory.getEntity("a", empresa));
		Ambiente a2 = ambienteDao.save(AmbienteFactory.getEntity("b", empresa));

		HistoricoAmbiente historicoAmbiente = HistoricoAmbienteFactory.getEntity(a1.getNome(), estabelecimento, "descricao", a1, new Date(), "");
		historicoAmbiente.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente);
		
		HistoricoAmbiente historicoAmbiente2 = HistoricoAmbienteFactory.getEntity(a2.getNome(), null, "descricao", a2, new Date(), "");
		historicoAmbiente2.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente2);
		
		Collection<Ambiente> ambientes = Arrays.asList(a1, a2);

		HistoricoAmbiente historicoAmbienteParaConsulta = new HistoricoAmbiente();
		historicoAmbienteParaConsulta.setLocalAmbiente(0);
		
		Collection<Ambiente> ambientesRetorno = ambienteDao.findAmbientes(0, 0, empresa.getId(), historicoAmbienteParaConsulta);
		assertEquals("findAmbientes com local do ambiente com valor inválido",ambientes, ambientesRetorno);
	}
	
	@Test
	public void testFindByIdProjection()
	{
		Empresa empresa = empresaDao.save(new Empresa());
		
		Ambiente ambiente = ambienteDao.save(AmbienteFactory.getEntity("Ambiente", empresa));
		
		assertEquals(ambiente, ambienteDao.findByIdProjection(ambiente.getId()));
	}

	
	@Test
	public void testFindByIds()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Date hoje = Calendar.getInstance().getTime();
		Calendar doisMesesAntes = Calendar.getInstance();
		doisMesesAntes.add(Calendar.MONTH, -2);
		Calendar tresMesesAntes = Calendar.getInstance();
		tresMesesAntes.add(Calendar.MONTH, -3);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("Estabelecimento");
		estabelecimentoDao.save(estabelecimento);
		
		Ambiente ambiente1 = ambienteDao.save(AmbienteFactory.getEntity("Ambiente 1", empresa));
		
		HistoricoAmbiente historicoAmbiente1 = HistoricoAmbienteFactory.getEntity(doisMesesAntes.getTime(), ambiente1.getNome(), ambiente1, estabelecimento, "Descrição do Ambiente 1", LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente1);
		
		HistoricoAmbiente historicoAmbiente1Atual = HistoricoAmbienteFactory.getEntity(hoje, ambiente1.getNome(), ambiente1, estabelecimento, "Descrição do Ambiente 1", LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente1Atual);
		
		Ambiente ambiente2 = ambienteDao.save(AmbienteFactory.getEntity("Ambiente 2", empresa));
		
		HistoricoAmbiente historicoAmbiente2 = HistoricoAmbienteFactory.getEntity(doisMesesAntes.getTime(), ambiente2.getNome(), ambiente2, estabelecimento, "Descrição do Ambiente 2", LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente2);
		
		Ambiente ambiente3 = ambienteDao.save(AmbienteFactory.getEntity("Ambiente 3", empresa));
		
		HistoricoAmbiente historicoAmbiente3 = HistoricoAmbienteFactory.getEntity(doisMesesAntes.getTime(), ambiente3.getNome(), ambiente3, estabelecimento, "Descrição do Ambiente 3", LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente3);
		
		HistoricoAmbiente historicoAmbiente3Fora = HistoricoAmbienteFactory.getEntity(tresMesesAntes.getTime(), ambiente3.getNome(), ambiente3, estabelecimento, "XCXCXCXCX", LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente3Fora);
		
		Date data = hoje;
		Collection<Long> ambienteIds = new ArrayList<Long>();
		ambienteIds.add(ambiente1.getId());
		ambienteIds.add(ambiente2.getId());
		ambienteIds.add(ambiente3.getId());
		
		Collection<Ambiente> colecao = ambienteDao.findByIds(empresa.getId(), ambienteIds, data, estabelecimento.getId(), LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		Object[] array = colecao.toArray();
		
		assertEquals("Descrição do Ambiente 1",((Ambiente)array[0]).getHistoricoAtual().getDescricao());
		assertEquals("Descrição do Ambiente 2",((Ambiente)array[1]).getHistoricoAtual().getDescricao());
		assertEquals("Descrição do Ambiente 3",((Ambiente)array[2]).getHistoricoAtual().getDescricao());
		
		assertEquals(new Integer(3).intValue(), colecao.size());
	}
	
	@Test
	public void testFindByIdsComArrayDeAmbientesVazio()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Date hoje = Calendar.getInstance().getTime();
		Calendar doisMesesAntes = Calendar.getInstance();
		doisMesesAntes.add(Calendar.MONTH, -2);
		Calendar tresMesesAntes = Calendar.getInstance();
		tresMesesAntes.add(Calendar.MONTH, -3);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("Estabelecimento");
		estabelecimentoDao.save(estabelecimento);
		
		Ambiente ambiente1 = ambienteDao.save(AmbienteFactory.getEntity("Ambiente 1", empresa));
		
		HistoricoAmbiente historicoAmbiente1 = HistoricoAmbienteFactory.getEntity(doisMesesAntes.getTime(), ambiente1.getNome(), ambiente1, estabelecimento, "Descrição do Ambiente 1", LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente1);
		
		HistoricoAmbiente historicoAmbiente1Atual = HistoricoAmbienteFactory.getEntity(hoje, ambiente1.getNome(), ambiente1, estabelecimento, "Descrição do Ambiente 1", LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente1Atual);
		
		Ambiente ambiente2 = ambienteDao.save(AmbienteFactory.getEntity("Ambiente 2", empresa));
		
		HistoricoAmbiente historicoAmbiente2 = HistoricoAmbienteFactory.getEntity(doisMesesAntes.getTime(), ambiente2.getNome(), ambiente2, estabelecimento, "Descrição do Ambiente 2", LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente2);
		
		Ambiente ambiente3 = ambienteDao.save(AmbienteFactory.getEntity("Ambiente 3", empresa));
		
		HistoricoAmbiente historicoAmbiente3 = HistoricoAmbienteFactory.getEntity(doisMesesAntes.getTime(), ambiente3.getNome(), ambiente3, estabelecimento, "Descrição do Ambiente 3", LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente3);
		
		HistoricoAmbiente historicoAmbiente3Fora = HistoricoAmbienteFactory.getEntity(tresMesesAntes.getTime(), ambiente3.getNome(), ambiente3, estabelecimento, "XCXCXCXCX", LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente3Fora);
		
		Date data = hoje;
		Collection<Long> ambienteIds = new ArrayList<Long>();
		
		Collection<Ambiente> colecao = ambienteDao.findByIds(empresa.getId(), ambienteIds, data, null, LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		Object[] array = colecao.toArray();
		
		assertEquals("Descrição do Ambiente 1",((Ambiente)array[0]).getHistoricoAtual().getDescricao());
		assertEquals("Descrição do Ambiente 2",((Ambiente)array[1]).getHistoricoAtual().getDescricao());
		assertEquals("Descrição do Ambiente 3",((Ambiente)array[2]).getHistoricoAtual().getDescricao());
		
		assertEquals(new Integer(3).intValue(), colecao.size());
	}
	
	@Test
	public void testFindByIdsComArrayDeAmbientesNulo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Date hoje = Calendar.getInstance().getTime();
		Calendar doisMesesAntes = Calendar.getInstance();
		doisMesesAntes.add(Calendar.MONTH, -2);
		Calendar tresMesesAntes = Calendar.getInstance();
		tresMesesAntes.add(Calendar.MONTH, -3);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("Estabelecimento");
		estabelecimentoDao.save(estabelecimento);
		
		Ambiente ambiente1 = ambienteDao.save(AmbienteFactory.getEntity("Ambiente 1", empresa));
		
		HistoricoAmbiente historicoAmbiente1 = HistoricoAmbienteFactory.getEntity(doisMesesAntes.getTime(), ambiente1.getNome(), ambiente1, estabelecimento, "Descrição do Ambiente 1", LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente1);
		
		HistoricoAmbiente historicoAmbiente1Atual = HistoricoAmbienteFactory.getEntity(hoje, ambiente1.getNome(), ambiente1, estabelecimento, "Descrição do Ambiente 1", LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente1Atual);
		
		Ambiente ambiente2 = ambienteDao.save(AmbienteFactory.getEntity("Ambiente 2", empresa));
		
		HistoricoAmbiente historicoAmbiente2 = HistoricoAmbienteFactory.getEntity(doisMesesAntes.getTime(), ambiente2.getNome(), ambiente2, estabelecimento, "Descrição do Ambiente 2", LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente2);
		
		Ambiente ambiente3 = ambienteDao.save(AmbienteFactory.getEntity("Ambiente 3", empresa));
		
		HistoricoAmbiente historicoAmbiente3 = HistoricoAmbienteFactory.getEntity(doisMesesAntes.getTime(), ambiente3.getNome(), ambiente3, estabelecimento, "Descrição do Ambiente 3", LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente3);
		
		HistoricoAmbiente historicoAmbiente3Fora = HistoricoAmbienteFactory.getEntity(tresMesesAntes.getTime(), ambiente3.getNome(), ambiente3, estabelecimento, "XCXCXCXCX", LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente3Fora);
		
		Date data = hoje;
		Collection<Ambiente> colecao = ambienteDao.findByIds(empresa.getId(), null, data, estabelecimento.getId(), LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao());
		
		assertTrue(colecao.isEmpty());
	}
	
	@Test
	public void testGetQtdColaboradorByAmbiente()
	{
		Date hoje = Calendar.getInstance().getTime();
		Calendar doisMesesAntes = Calendar.getInstance();
		doisMesesAntes.add(Calendar.MONTH, -2);
		Calendar tresMesesAntes = Calendar.getInstance();
		tresMesesAntes.add(Calendar.MONTH, -3);
		
		Funcao funcao1 = funcaoDao.save(FuncaoFactory.getEntity(null, "F1"));
		Funcao funcao2 = funcaoDao.save(FuncaoFactory.getEntity(null, "F2"));
		Funcao funcao3 = funcaoDao.save(FuncaoFactory.getEntity(null, "F3"));
		funcaoDao.save(FuncaoFactory.getEntity(null, "F4"));
		
		Ambiente ambiente = ambienteDao.save(AmbienteFactory.getEntity());
		
		Colaborador colaborador1 = saveColaborador('F', null);
		
		HistoricoColaborador historicoColaborador1Fora = HistoricoColaboradorFactory.getEntity(colaborador1, tresMesesAntes.getTime(), null, null, null, funcao1, ambiente, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador1Fora);
		
		HistoricoColaborador historicoColaborador1Atual = HistoricoColaboradorFactory.getEntity(colaborador1, doisMesesAntes.getTime(), null, null, null, funcao2, ambiente, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador1Atual);
		
		Colaborador colaborador2 = saveColaborador('M', null);
		
		HistoricoColaborador historicoColaborador2Atual = HistoricoColaboradorFactory.getEntity(colaborador2, hoje, null, null, null, funcao3, ambiente, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador2Atual);
		
		Colaborador colaborador3 = saveColaborador('F', null);
		
		HistoricoColaborador historicoColaborador3 = HistoricoColaboradorFactory.getEntity(colaborador3, hoje, null, null, null, funcao3, ambiente, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador3);
		
		Colaborador colaboradorDesligadoDepois = saveColaborador('F', DateUtil.incrementaDias(hoje, 1));
		
		HistoricoColaborador historicoColaboradorDesligadoDepois = HistoricoColaboradorFactory.getEntity(colaboradorDesligadoDepois, doisMesesAntes.getTime(), null, null, null, funcao2, ambiente, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaboradorDesligadoDepois);

		Colaborador colaboradorDesligadoAntes = saveColaborador('F', DateUtil.incrementaDias(hoje, -1));

		HistoricoColaborador historicoColaboradorDesligadoAntes = HistoricoColaboradorFactory.getEntity(colaboradorDesligadoAntes, doisMesesAntes.getTime(), null, null, null, funcao2, ambiente, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaboradorDesligadoAntes);
		
		assertEquals("colaborador1 e colaboradorDesligadoDepois", 2, ambienteDao.getQtdColaboradorByAmbiente(ambiente.getId(), hoje, Sexo.FEMININO, funcao2.getId()));
		assertEquals("colaborador1, colaborador3 e colaboradorDesligadoDepois", 3, ambienteDao.getQtdColaboradorByAmbiente(ambiente.getId(), hoje, Sexo.FEMININO, null));
	}

	private Colaborador saveColaborador(char sexo, Date dataDeslisgamento) {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.getPessoal().setSexo(sexo);
		colaborador.setDataDesligamento(dataDeslisgamento);
		colaboradorDao.save(colaborador);
		return colaborador;
	}
	
	
	
	@Test
	public void testDeleteAmbienteSemHistorico() {
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Ambiente ambiente = new Ambiente("nome", empresa);
		ambienteDao.save(ambiente);
		
		Exception exception = null;
		try {
			ambienteDao.deleteAmbienteSemHistorico();
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}
	
	@Test
	public void testFindAllByEmpresa() {
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setEmpresa(empresa);
		estabelecimentoDao.save(estabelecimento);
		
		Ambiente ambiente = AmbienteFactory.getEntity("Ambiente 1", empresa);
		ambienteDao.save(ambiente);
		
		HistoricoAmbiente historicoAmbiente1 = HistoricoAmbienteFactory.getEntity("Descrição Ambiente 1", ambiente, DateUtil.criarDataMesAno(1, 1, 2011),"tempoExposicao Ambiente 1");
		historicoAmbienteDao.save(historicoAmbiente1);
		
		Ambiente ambiente2 = AmbienteFactory.getEntity("Ambiente 2", empresa);
		ambienteDao.save(ambiente2);
		
		HistoricoAmbiente historicoAmbiente2 = HistoricoAmbienteFactory.getEntity("Descrição Ambiente 2 Fora",ambiente2,DateUtil.criarDataMesAno(1, 1, 2015),"tempoExposicao Ambiente 2 Fora");
		historicoAmbienteDao.save(historicoAmbiente2);
		
		HistoricoAmbiente historicoAmbiente3 = HistoricoAmbienteFactory.getEntity("Descrição Ambiente 2",ambiente2,DateUtil.criarDataMesAno(1, 1, 2016),"tempoExposicao Ambiente 2");
		historicoAmbienteDao.save(historicoAmbiente3);
		
		Collection<Ambiente> ambientes = ambienteDao.findAllByEmpresa(empresa.getId());
		
		assertEquals(2, ambientes.size());
		assertEquals("Ambiente 1", ((Ambiente) ambientes.toArray()[0]).getNome());
		assertEquals("Descrição Ambiente 1", ((Ambiente) ambientes.toArray()[0]).getHistoricoAtual().getDescricao());
		assertEquals("tempoExposicao Ambiente 2", ((Ambiente) ambientes.toArray()[1]).getHistoricoAtual().getTempoExposicao());
	}
	
	@Test
	public void testAtualizaDadosParaUltimoHistorico(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setEmpresa(empresa);
		estabelecimentoDao.save(estabelecimento);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimento2.setEmpresa(empresa);
		estabelecimentoDao.save(estabelecimento2);
		
		Ambiente ambiente = AmbienteFactory.getEntity("Ambiente 1", empresa);
		ambienteDao.save(ambiente);
		
		HistoricoAmbiente historicoAmbiente1 = HistoricoAmbienteFactory.getEntity(ambiente.getNome(), estabelecimento, "Descrição Ambiente 1", ambiente, DateUtil.criarDataMesAno(1, 1, 2011),"tempoExposicao Ambiente 1");
		historicoAmbienteDao.save(historicoAmbiente1);
		
		HistoricoAmbiente historicoAmbiente2 = HistoricoAmbienteFactory.getEntity("Ambiente modificado", estabelecimento2, "Descrição Ambiente 1", ambiente, DateUtil.criarDataMesAno(1, 1, 2012),"tempoExposicao Ambiente 1");
		historicoAmbienteDao.save(historicoAmbiente2);
		
		ambienteDao.atualizaDadosParaUltimoHistorico(ambiente.getId());
		Ambiente ambienteDoBanco = ambienteDao.findByIdProjection(ambiente.getId());
		
		assertEquals("Ambiente modificado", ambienteDoBanco.getNome());
	} 

	@Test
	public void testFindAmbientesDeEstabelecimentosDoProprioEmpregador(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = estabelecimentoDao.save(EstabelecimentoFactory.getEntity("Estalecimento 1", empresa));
		Estabelecimento estabelecimento2 = estabelecimentoDao.save(EstabelecimentoFactory.getEntity("Estalecimento 2", empresa));
		
		Ambiente ambienteEstabelecimento1 = ambienteDao.save(AmbienteFactory.getEntity("Ambiente do estabelecimento 1", empresa));
		Ambiente ambienteEstabelecimento2 = ambienteDao.save(AmbienteFactory.getEntity("Ambiente do estabelecimento 2", empresa));
		Ambiente ambienteDeTerceiro = ambienteDao.save(AmbienteFactory.getEntity("Ambiente de terceiros", empresa));
		
		HistoricoAmbiente historicoAmbienteDeTerceiro = HistoricoAmbienteFactory.getEntity(ambienteDeTerceiro.getNome(), null, "Descrição Ambiente de terceiros", ambienteDeTerceiro, DateUtil.criarDataMesAno(1, 1, 2011),"tempoExposicao Ambiente 1");
		historicoAmbienteDeTerceiro.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao());
		historicoAmbienteDao.save(historicoAmbienteDeTerceiro);
		
		HistoricoAmbiente historicoAmbienteEstabelecimento1 = HistoricoAmbienteFactory.getEntity(ambienteEstabelecimento1.getNome(), estabelecimento, "Descrição Ambiente do estabelecimento 1", ambienteEstabelecimento1, DateUtil.criarDataMesAno(1, 1, 2011),"tempoExposicao Amb. estabelecimento 1");
		historicoAmbienteEstabelecimento1.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbienteEstabelecimento1);

		HistoricoAmbiente historicoAmbienteEstabelecimento2 = HistoricoAmbienteFactory.getEntity(ambienteEstabelecimento2.getNome(), estabelecimento2, "Descrição Ambiente do estabelecimento 2", ambienteEstabelecimento1, DateUtil.criarDataMesAno(1, 1, 2011),"tempoExposicao Amb. estabelecimento 2");
		historicoAmbienteEstabelecimento2.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbienteEstabelecimento2);

		Collection<Ambiente> ambientes = ambienteDao.findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(empresa.getId(), estabelecimento.getId(), LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao(), new Date());
		assertEquals(1, ambientes.size());
		assertEquals(ambienteEstabelecimento1.getId(), ambientes.iterator().next().getId());
	}
	
	@Test
	public void testFindAmbienteDeEstabelecimentosDeTerceiros(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity("Estalecimento", empresa);
		estabelecimentoDao.save(estabelecimento);
		
		Ambiente ambienteDeTerceiro = AmbienteFactory.getEntity("Ambiente de terceiros", empresa);
		ambienteDao.save(ambienteDeTerceiro);
		
		HistoricoAmbiente historicoAmbiente1 = HistoricoAmbienteFactory.getEntity(ambienteDeTerceiro.getNome(), null, "Descrição Ambiente de terceiros", ambienteDeTerceiro, DateUtil.criarDataMesAno(1, 1, 2011),"tempoExposicao Ambiente 1");
		historicoAmbiente1.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente1);
		
		Ambiente ambienteDoProprioEmpregador = AmbienteFactory.getEntity("Ambiente do próprio empregador", empresa);
		ambienteDao.save(ambienteDoProprioEmpregador);
		
		HistoricoAmbiente historicoAmbienteDoProprioEmpregador = HistoricoAmbienteFactory.getEntity(ambienteDoProprioEmpregador.getNome(), estabelecimento, "Descrição Ambiente do próprio empregador", ambienteDoProprioEmpregador, DateUtil.criarDataMesAno(1, 1, 2011),"tempoExposicao Ambiente 1");
		historicoAmbienteDoProprioEmpregador.setLocalAmbiente(LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbienteDoProprioEmpregador);
		
		Collection<Ambiente> ambientes = ambienteDao.findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(empresa.getId(), null, LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao(), new Date());
		assertEquals(1, ambientes.size());
		assertEquals(ambienteDeTerceiro.getId(), ambientes.iterator().next().getId());
	}
	
	@Test
	public void testFindAmbientesPorEstabelecimento() {
		Empresa empresa = empresaDao.save(EmpresaFactory.getEmpresa());
		Estabelecimento estabelecimento = estabelecimentoDao.save(EstabelecimentoFactory.getEntity("Estalecimento", empresa));
		
		Ambiente ambiente1 = ambienteDao.save(AmbienteFactory.getEntity("Ambiente do empregador", empresa));
		HistoricoAmbiente historicoAmbiente1 = HistoricoAmbienteFactory.getEntity(DateUtil.criarDataMesAno(1, 1, 2011), ambiente1.getNome(), ambiente1, estabelecimento, "Descrição Ambiente do empregador", LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente1);

		Ambiente ambiente2 = ambienteDao.save(AmbienteFactory.getEntity("Ambiente de terceiros", empresa));
		HistoricoAmbiente historicoAmbiente2 = HistoricoAmbienteFactory.getEntity(DateUtil.criarDataMesAno(1, 1, 2011), ambiente2.getNome(), ambiente2, null, "Descrição Ambiente de terceiros", LocalAmbiente.ESTABELECIMENTO_DE_TERCEIROS.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente2);

		Collection<Ambiente> ambientes = ambienteDao.findAmbientesPorEstabelecimento(new Long[]{estabelecimento.getId()}, new Date());
		assertEquals(1, ambientes.size());
		assertEquals(ambiente1.getId(), ambientes.iterator().next().getId());
	}
	
	@Test
	public void testFindAmbientesPorEstabelecimentoComAmbienteTentoMudadoDeEstabelecimento() {
		Empresa empresa = empresaDao.save(EmpresaFactory.getEmpresa());
		Estabelecimento estabelecimento1 = estabelecimentoDao.save(EstabelecimentoFactory.getEntity("Estalecimento 1", empresa));
		Estabelecimento estabelecimento2 = estabelecimentoDao.save(EstabelecimentoFactory.getEntity("Estalecimento 2", empresa));
		
		Ambiente ambiente1 = ambienteDao.save(AmbienteFactory.getEntity("Ambiente Estalecimento 1", empresa));
		HistoricoAmbiente historicoAmbiente1 = HistoricoAmbienteFactory.getEntity(DateUtil.criarDataMesAno(1, 1, 2017), ambiente1.getNome(), ambiente1, estabelecimento1, "Descrição Ambiente do empregador", LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente1);
		
		HistoricoAmbiente historicoAmbiente2 = HistoricoAmbienteFactory.getEntity(DateUtil.criarDataMesAno(2, 10, 2017), ambiente1.getNome(), ambiente1, estabelecimento2, "Descrição Ambiente de terceiros", LocalAmbiente.ESTABELECIMENTO_DO_PROPRIO_EMPREGADOR.getOpcao());
		historicoAmbienteDao.save(historicoAmbiente2);

		Collection<Ambiente> ambientes = ambienteDao.findAmbientesPorEstabelecimento(new Long[]{estabelecimento1.getId()}, DateUtil.criarDataMesAno(1, 10, 2017));
		assertEquals(1, ambientes.size());
		assertEquals(ambiente1.getId(), ambientes.iterator().next().getId());
		
		ambientes = ambienteDao.findAmbientesPorEstabelecimento(new Long[]{estabelecimento1.getId()}, DateUtil.criarDataMesAno(2, 10, 2017));
		assertTrue(ambientes.isEmpty());
		
		ambientes = ambienteDao.findAmbientesPorEstabelecimento(new Long[]{estabelecimento1.getId()}, DateUtil.criarDataMesAno(1, 1, 2016));
		assertTrue(ambientes.isEmpty());
		
		ambientes = ambienteDao.findAmbientesPorEstabelecimento(new Long[]{estabelecimento2.getId()}, DateUtil.criarDataMesAno(1, 10, 2017));
		assertTrue(ambientes.isEmpty());
		
		ambientes = ambienteDao.findAmbientesPorEstabelecimento(new Long[]{estabelecimento2.getId()}, DateUtil.criarDataMesAno(2, 10, 2017));
		assertEquals(1, ambientes.size());
		assertEquals(ambiente1.getId(), ambientes.iterator().next().getId());
		
		ambientes = ambienteDao.findAmbientesPorEstabelecimento(new Long[]{estabelecimento2.getId()}, DateUtil.criarDataMesAno(2, 11, 2017));
		assertEquals(1, ambientes.size());
		assertEquals(ambiente1.getId(), ambientes.iterator().next().getId());
	}
}