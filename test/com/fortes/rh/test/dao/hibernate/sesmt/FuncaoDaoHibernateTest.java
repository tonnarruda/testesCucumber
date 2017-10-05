package com.fortes.rh.test.dao.hibernate.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.kohsuke.rngom.digested.DUnaryPattern;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.dao.sesmt.EpcDao;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.dao.sesmt.HistoricoFuncaoDao;
import com.fortes.rh.dao.sesmt.MedicaoRiscoDao;
import com.fortes.rh.dao.sesmt.RiscoMedicaoRiscoDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("unused")
public class FuncaoDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<Funcao>
{
	@Autowired private FuncaoDao funcaoDao;
	@Autowired private EmpresaDao empresaDao;
	@Autowired private FaixaSalarialDao faixaSalarialDao;
	@Autowired private EstabelecimentoDao estabelecimentoDao;
	
	@Autowired private ColaboradorDao colaboradorDao;
	@Autowired private HistoricoColaboradorDao historicoColaboradorDao;
	@Autowired private AmbienteDao ambienteDao;
	@Autowired private HistoricoFuncaoDao historicoFuncaoDao;
	@Autowired private MedicaoRiscoDao medicaoRiscoDao;
	@Autowired private RiscoMedicaoRiscoDao riscoMedicaoRiscoDao;
	@Autowired private EpcDao epcDao;

	public Funcao getEntity()
	{
		Funcao funcao = new Funcao();

		funcao.setId(1L);
		funcao.setNome("funcao");

		return funcao;
	}

	@Test
	public void testFindByEmpresaComPaginacao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Funcao f1 = saveFuncao("a", empresa);
		Funcao f2 = saveFuncao("b", empresa);

		Collection<Funcao> funcoesTest = new ArrayList<Funcao>();
		funcoesTest.add(f1);
		funcoesTest.add(f2);


		Collection<Funcao> funcaos = funcaoDao.findByEmpresa(1, 15, empresa.getId(), null);
		assertEquals(funcoesTest, funcaos);
		
		funcaos = funcaoDao.findByEmpresa(1, 15, empresa.getId(), "");
		assertEquals(funcoesTest, funcaos);
		
		funcaos = funcaoDao.findByEmpresa(1, 15, empresa.getId(), f1.getNome());
		assertEquals(1, funcaos.size());
		assertEquals(f1.getNome(), funcaos.iterator().next().getNome());
		
		int count = funcaoDao.getCount(empresa.getId(), null); 
		assertEquals(2, count);
	}

	@Test
	public void testFindByEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa("Empresa 1", "21212121212", "empresa");
		empresaDao.save(empresa);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa("Empresa 2", "21212121212", "empresa2");
		empresaDao.save(empresa2);

		Funcao f1 = saveFuncao("F1", empresa);
		Funcao f2 = saveFuncao("F2", empresa);
		Funcao f3 = saveFuncao("F3", empresa2);
		
		Collection<Funcao> funcaos = funcaoDao.findByEmpresa(empresa.getId());

		assertEquals("Test 1", 2, funcaos.size());
		assertTrue("Test 2", funcaos.contains(f1));
		assertTrue("Test 3", funcaos.contains(f2));
		assertTrue("Test 4", !funcaos.contains(f3));

		funcaos = funcaoDao.findByEmpresa(empresa2.getId());

		assertEquals("Test 5", 1, funcaos.size());
		assertTrue("Test 6", funcaos.contains(f3));

	}
	
	@Test
	public void testFindByEmpresaAndCodigoFPIsNull()
	{
		Empresa empresa = EmpresaFactory.getEmpresa("Empresa 1", "21212121212", "empresa");
		empresaDao.save(empresa);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa("Empresa 2", "21212121212", "empresa2");
		empresaDao.save(empresa2);

		Funcao f1 = FuncaoFactory.getEntity("F1", empresa);
		f1.setCodigoFP("123456");
		funcaoDao.save(f1);

		Funcao f2 = saveFuncao("F2", empresa);
		Funcao f3 = saveFuncao("F3", empresa2);
		
		Collection<Funcao> funcaos = funcaoDao.findByEmpresaAndCodigoFPIsNull(empresa.getId());

		assertEquals(1, funcaos.size());
		assertTrue(funcaos.contains(f2));
	}
	
	@Test
	public void testFindByIdProjection()
	{
		Funcao funcao = new Funcao();
		funcao = funcaoDao.save(funcao);
		
		assertEquals(funcao, funcaoDao.findByIdProjection(funcao.getId()));
	}
	
	@Test
	public void testFindColaboradoresSemFuncao()
	{
		Date hoje = new Date();
		Date doisMesesAntes = DateUtil.incrementaMes(hoje, -2);
		Date tresMesesAntes = DateUtil.incrementaMes(hoje, -3);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Funcao funcao1 = FuncaoFactory.getEntity();
		funcao1.setNome("F1");
		funcaoDao.save(funcao1);
		
		HistoricoFuncao historicoFuncao1 = new HistoricoFuncao();
		historicoFuncao1.setFuncao(funcao1);
		historicoFuncao1.setData(hoje);
		historicoFuncao1.setDescricao("Realizar a limpeza dos principais acessos.");
		historicoFuncaoDao.save(historicoFuncao1);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador1);
		
		//Não precisa apagar historicoColaborador1Fora, ..., serve para entender melhor o teste "Fora, Atual"
		HistoricoColaborador historicoColaborador1Fora = criaHistoricoColaborador(colaborador1, tresMesesAntes, null, funcao1, estabelecimento1);
		
		HistoricoColaborador historicoColaborador1Atual = criaHistoricoColaborador(colaborador1, doisMesesAntes, null, funcao1, estabelecimento1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity(null, "Colab2");
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2Fora = criaHistoricoColaborador(colaborador2, tresMesesAntes, null, null, estabelecimento1);

		Colaborador colaborador3 = ColaboradorFactory.getEntity(null, "Colab3", null, tresMesesAntes, hoje);
		colaboradorDao.save(colaborador3);
		
		HistoricoColaborador historicoColaborador3DentroComDataDesligamentoAnterior = criaHistoricoColaborador(colaborador3, tresMesesAntes, null, null, estabelecimento1);

		Colaborador colaborador4 = ColaboradorFactory.getEntity(null, "Colab4", null, tresMesesAntes, tresMesesAntes);
		colaboradorDao.save(colaborador4);
		
		HistoricoColaborador historicoColaborador4ForaComDataDesligamentoAnterior = criaHistoricoColaborador(colaborador4, tresMesesAntes, null, null, estabelecimento1);
		
		Collection<String> nomes = funcaoDao.findColaboradoresSemFuncao(hoje, estabelecimento1.getId());
		
		assertEquals(2, nomes.size());
	}
	
	@Test
	public void testFindFuncaoAtualDosColaboradores()
	{
		Date hoje = Calendar.getInstance().getTime();
		Calendar doisMesesAntes = Calendar.getInstance();
		doisMesesAntes.add(Calendar.MONTH, -2);
		Calendar tresMesesAntes = Calendar.getInstance();
		tresMesesAntes.add(Calendar.MONTH, -3);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Funcao funcao1 = FuncaoFactory.getEntity();
		funcao1.setNome("F1");
		funcaoDao.save(funcao1);
		
		HistoricoFuncao historicoFuncao1 = new HistoricoFuncao();
		historicoFuncao1.setFuncao(funcao1);
		historicoFuncao1.setData(hoje);
		historicoFuncao1.setDescricao("Realizar a limpeza dos principais acessos.");
		historicoFuncaoDao.save(historicoFuncao1);
		
		Funcao funcao2 = FuncaoFactory.getEntity();
		funcao2.setNome("F2");
		funcaoDao.save(funcao2);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador1);
		
		//Não precisa apagar historicoColaborador1Fora, ..., serve para entender melhor o teste "Fora, Atual"
		HistoricoColaborador historicoColaborador1Fora = criaHistoricoColaborador(colaborador1, tresMesesAntes.getTime(), null, funcao1, estabelecimento1);
		
		HistoricoColaborador historicoColaborador1Atual = criaHistoricoColaborador(colaborador1, doisMesesAntes.getTime(), null, funcao2, estabelecimento1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2Fora = criaHistoricoColaborador(colaborador2, tresMesesAntes.getTime(), null, funcao2, estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		Colaborador colaborador3 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador3);
		
		HistoricoColaborador historicoColaborador3Fora = criaHistoricoColaborador(colaborador2, hoje, null, funcao2, estabelecimento2);
		
		Collection<Long> ids = funcaoDao.findFuncaoAtualDosColaboradores(hoje, estabelecimento1.getId());
		
		assertEquals(1, ids.size());
		assertEquals(funcao2.getId(), ids.toArray()[0]);
	}
	
	private HistoricoColaborador criaHistoricoColaborador(Colaborador colaborador, Date data, Ambiente ambiente, Funcao funcao, Estabelecimento estabelecimento)
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(data);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setAmbiente(ambiente);		
		historicoColaborador.setFuncao(funcao);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(historicoColaborador);
		
		return historicoColaborador;
	}

	@Test
	public void testGetFuncoesDoAmbiente()
	{
		Date hoje = Calendar.getInstance().getTime();
		Calendar doisMesesAntes = Calendar.getInstance();
		doisMesesAntes.add(Calendar.MONTH, -2);
		Calendar tresMesesAntes = Calendar.getInstance();
		tresMesesAntes.add(Calendar.MONTH, -3);
		
		Funcao funcao1 = FuncaoFactory.getEntity();
		funcao1.setNome("F1");
		funcaoDao.save(funcao1);
		HistoricoFuncao historicoFuncao1 = new HistoricoFuncao();
		historicoFuncao1.setFuncao(funcao1);
		historicoFuncao1.setData(hoje);
		historicoFuncao1.setDescricao("Realizar a limpeza dos principais acessos.");
		historicoFuncaoDao.save(historicoFuncao1);
		
		Funcao funcao2 = FuncaoFactory.getEntity();
		funcao2.setNome("F2");
		funcaoDao.save(funcao2);
		
		HistoricoFuncao historicoFuncao2 = new HistoricoFuncao();
		historicoFuncao2.setFuncao(funcao2);
		historicoFuncao2.setData(doisMesesAntes.getTime());
		historicoFuncao2.setDescricao("Porteiro.");
		historicoFuncaoDao.save(historicoFuncao2);
		
		HistoricoFuncao historicoFuncao2Atual = new HistoricoFuncao();
		historicoFuncao2Atual.setFuncao(funcao2);
		historicoFuncao2Atual.setData(hoje);
		historicoFuncao2Atual.setDescricao("Zelar pela guarda do patrimônio.");
		historicoFuncaoDao.save(historicoFuncao2Atual);
		
		Funcao funcao3 = FuncaoFactory.getEntity();
		funcao3.setNome("F3");
		funcaoDao.save(funcao3);
		
		HistoricoFuncao historicoFuncao3 = new HistoricoFuncao();
		historicoFuncao3.setFuncao(funcao3);
		historicoFuncao3.setData(hoje);
		historicoFuncao3.setDescricao("Recepcionar os clientes com cortesia, atenção e eficiência.");
		historicoFuncaoDao.save(historicoFuncao3);
		
		Funcao funcao4 = FuncaoFactory.getEntity();
		funcao4.setNome("F4");
		funcaoDao.save(funcao4);
		
		HistoricoFuncao historicoFuncao4 = new HistoricoFuncao();
		historicoFuncao4.setFuncao(funcao4);
		historicoFuncao4.setData(hoje);
		historicoFuncao4.setDescricao("Desenvolver software.");
		historicoFuncaoDao.save(historicoFuncao4);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambienteDao.save(ambiente);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador1);
		
		//Não precisa apagar historicoColaborador1Fora, ..., serve para entender melhor o teste "Fora, Atual"
		HistoricoColaborador historicoColaborador1Fora = criaHistoricoColaborador(colaborador1, tresMesesAntes.getTime(), ambiente, funcao1, null);
		
		HistoricoColaborador historicoColaborador1Atual = criaHistoricoColaborador(colaborador1, doisMesesAntes.getTime(), ambiente, funcao2, null);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2Fora = criaHistoricoColaborador(colaborador2, tresMesesAntes.getTime(), ambiente, funcao2, null);
		
		HistoricoColaborador historicoColaborador2Atual = criaHistoricoColaborador(colaborador2, hoje, ambiente, funcao3, null);
		
		Collection<Funcao> colecao = funcaoDao.findFuncoesDoAmbiente(ambiente.getId(), hoje);
		
		assertEquals(2, colecao.size());
		assertTrue(colecao.contains(funcao2));
		assertEquals("Zelar pela guarda do patrimônio.",
				((Funcao)colecao.toArray()[0]).getHistoricoAtual().getDescricao());
	}
	
	@Test
	public void testGetQtdColaboradorByFuncao()
	{
		Date hoje = Calendar.getInstance().getTime();
		Calendar doisMesesAntes = Calendar.getInstance();
		doisMesesAntes.add(Calendar.MONTH, -2);
		Calendar tresMesesAntes = Calendar.getInstance();
		tresMesesAntes.add(Calendar.MONTH, -3);
		
		Estabelecimento estabelecimento  = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Funcao funcao1 = saveFuncao("F1", empresa1);
		Funcao funcao2 = saveFuncao("F2", empresa2);
		Funcao funcao3 = saveFuncao("F3", empresa2);
		Funcao funcao4 = saveFuncao("F4", empresa1);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambienteDao.save(ambiente);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.getPessoal().setSexo('F');
		colaboradorDao.save(colaborador1);
		
		HistoricoColaborador historicoColaborador1Fora = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1Fora.setData(tresMesesAntes.getTime());
		historicoColaborador1Fora.setColaborador(colaborador1);
		historicoColaborador1Fora.setAmbiente(ambiente);
		historicoColaborador1Fora.setFuncao(funcao1);
		historicoColaborador1Fora.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(historicoColaborador1Fora);
		
		HistoricoColaborador historicoColaborador1Atual = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1Atual.setData(doisMesesAntes.getTime());
		historicoColaborador1Atual.setColaborador(colaborador1);
		historicoColaborador1Atual.setAmbiente(ambiente);
		historicoColaborador1Atual.setFuncao(funcao2);
		historicoColaborador1Atual.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(historicoColaborador1Atual);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.getPessoal().setSexo('M');
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2Atual = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2Atual.setData(hoje);
		historicoColaborador2Atual.setColaborador(colaborador2);
		historicoColaborador2Atual.setAmbiente(ambiente);
		historicoColaborador2Atual.setFuncao(funcao3);
		historicoColaborador2Atual.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(historicoColaborador2Atual);
		
		Colaborador colaborador3 = ColaboradorFactory.getEntity();
		colaborador3.getPessoal().setSexo('F');
		colaboradorDao.save(colaborador3);
		
		HistoricoColaborador historicoColaborador3 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador3.setData(hoje);
		historicoColaborador3.setColaborador(colaborador3);
		historicoColaborador3.setAmbiente(ambiente);
		historicoColaborador3.setFuncao(funcao2);
		historicoColaborador3.setEstabelecimento(estabelecimento);
		historicoColaboradorDao.save(historicoColaborador3);
		
		Long[] funcoesIds = {funcao1.getId(), funcao2.getId(),funcao3.getId(),funcao4.getId()};
		assertEquals(2, funcaoDao.getQtdColaboradorByFuncao(empresa2.getId(), estabelecimento  .getId(), hoje, 'T').size());
	}

	public GenericDao<Funcao> getGenericDao()
	{
		return funcaoDao;
	}
	
	private Funcao saveFuncao(String nome, Empresa empresa){
		Funcao funcao = FuncaoFactory.getEntity(nome, empresa);
		funcaoDao.save(funcao);
		return funcao;
	}
}