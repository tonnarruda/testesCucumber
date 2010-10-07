package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

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
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;

public class AmbienteDaoHibernateTest extends GenericDaoHibernateTest<Ambiente>
{
	private AmbienteDao ambienteDao;
	private EmpresaDao empresaDao;
	private EstabelecimentoDao estabelecimentoDao;
	private HistoricoAmbienteDao historicoAmbienteDao;
	
	ColaboradorDao colaboradorDao;
	HistoricoColaboradorDao historicoColaboradorDao;
	FuncaoDao funcaoDao;
	MedicaoRiscoDao medicaoRiscoDao;
	RiscoMedicaoRiscoDao riscoMedicaoRiscoDao;
	EpcDao epcDao;

	public Ambiente getEntity()
	{
		return AmbienteFactory.getEntity();
	}

	public void testGetCount()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Ambiente a1 = new Ambiente();
		a1.setId(1L);
		a1.setEmpresa(empresa);

		Ambiente a2 = new Ambiente();
		a2.setId(2L);
		a2.setEmpresa(empresa);

		Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
		ambientes.add(a1);
		ambientes.add(a2);

		ambienteDao.save(a1);
		ambienteDao.save(a2);

		int retorno = ambienteDao.getCount(empresa.getId());

		assertEquals(ambientes.size(), retorno);
	}

	public void testFindAmbientes()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Ambiente a1 = new Ambiente();
		a1.setNome("a");
		a1.setEmpresa(empresa);

		Ambiente a2 = new Ambiente();
		a2.setNome("b");
		a2.setEmpresa(empresa);

		a1 = ambienteDao.save(a1);
		a2 = ambienteDao.save(a2);

		Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
		ambientes.add(a1);
		ambientes.add(a2);

		Collection<Ambiente> ambientesRetorno = ambienteDao.findAmbientes(0, 0, empresa.getId());

		assertEquals("findAmbientes sem paginação",ambientes, ambientesRetorno);

		ambientesRetorno = ambienteDao.findAmbientes(1, 15, empresa.getId());

		assertEquals("findAmbientes com paginação",ambientes, ambientesRetorno);
	}
	
	public void testFindByIdProjection()
	{
		Empresa empresa = new Empresa();
		empresa = empresaDao.save(empresa);
		
		Ambiente ambiente = new Ambiente();
		ambiente.setEmpresa(empresa);
		
		ambienteDao.save(ambiente);
		
		assertEquals(ambiente, ambienteDao.findByIdProjection(ambiente.getId()));
	}

	public void testFindByEstabelecimento()
	{
		Empresa empresa = new Empresa();
		empresa.setNome("fortes");
		empresa.setCnpj("65465");
		empresa.setRazaoSocial("fortes");
		empresa = empresaDao.save(empresa);

		Estabelecimento estabelecimento1 = new Estabelecimento();
		estabelecimento1.setNome("teste");
		estabelecimento1.setEmpresa(empresa);

		Estabelecimento estabelecimento2 = new Estabelecimento();
		estabelecimento2.setNome("teste");
		estabelecimento2.setEmpresa(empresa);

		estabelecimento1 = estabelecimentoDao.save(estabelecimento1);
		estabelecimento2 = estabelecimentoDao.save(estabelecimento2);

		Ambiente amb1 = new Ambiente();
		amb1.setNome("ambiente1");
		amb1.setEstabelecimento(estabelecimento1);
		amb1.setEmpresa(empresa);
		ambienteDao.save(amb1);

		Ambiente amb2 = new Ambiente();
		amb2.setNome("ambiente2");
		amb2.setEstabelecimento(estabelecimento1);
		amb2.setEmpresa(empresa);
		ambienteDao.save(amb2);

		Collection<Ambiente> ambientes = ambienteDao.findByEstabelecimento(estabelecimento1.getId());

		assertEquals(2, ambientes.size());

		ambientes = ambienteDao.findByEstabelecimento(estabelecimento2.getId());

		assertTrue(ambientes.isEmpty());
	}
	
	public void testFindByIds()
	{
		Date hoje = Calendar.getInstance().getTime();
		Calendar doisMesesAntes = Calendar.getInstance();
		doisMesesAntes.add(Calendar.MONTH, -2);
		Calendar tresMesesAntes = Calendar.getInstance();
		tresMesesAntes.add(Calendar.MONTH, -3);
		
		Ambiente ambiente1 = new Ambiente();
		ambiente1.setNome("ZZZZ");
		ambienteDao.save(ambiente1);
		
		HistoricoAmbiente historicoAmbiente1 = new HistoricoAmbiente();
		historicoAmbiente1.setAmbiente(ambiente1);
		historicoAmbiente1.setData(doisMesesAntes.getTime());
		historicoAmbienteDao.save(historicoAmbiente1);
		
		HistoricoAmbiente historicoAmbiente1Atual = new HistoricoAmbiente();
		historicoAmbiente1Atual.setDescricao("Descrição do Ambiente1");
		historicoAmbiente1Atual.setAmbiente(ambiente1);
		historicoAmbiente1Atual.setData(hoje);
		historicoAmbienteDao.save(historicoAmbiente1Atual);
		
		Ambiente ambiente2 = new Ambiente();
		ambienteDao.save(ambiente2);
		
		HistoricoAmbiente historicoAmbiente2 = new HistoricoAmbiente();
		historicoAmbiente2.setDescricao("Descrição do Ambiente2");
		historicoAmbiente2.setAmbiente(ambiente2);
		historicoAmbiente2.setData(doisMesesAntes.getTime());
		historicoAmbienteDao.save(historicoAmbiente2);
		
		Ambiente ambiente3 = new Ambiente();
		ambienteDao.save(ambiente3);
		
		HistoricoAmbiente historicoAmbiente3 = new HistoricoAmbiente();
		historicoAmbiente3.setDescricao("Descrição do Ambiente3");
		historicoAmbiente3.setAmbiente(ambiente3);
		historicoAmbiente3.setData(doisMesesAntes.getTime());
		historicoAmbienteDao.save(historicoAmbiente3);
		
		HistoricoAmbiente historicoAmbiente3Fora = new HistoricoAmbiente();
		historicoAmbiente3Fora.setDescricao("XCXCXCXCX");
		historicoAmbiente3Fora.setAmbiente(ambiente3);
		historicoAmbiente3Fora.setData(tresMesesAntes.getTime());
		historicoAmbienteDao.save(historicoAmbiente3Fora);
		
		Date data = hoje;
		Collection<Long> ambienteIds = new ArrayList<Long>();
		ambienteIds.add(ambiente1.getId());
		ambienteIds.add(ambiente2.getId());
		ambienteIds.add(ambiente3.getId());
		
		Collection<Ambiente> colecao = ambienteDao.findByIds(ambienteIds, data);
		Object[] array = colecao.toArray();
		
		assertEquals("Descrição do Ambiente1",((Ambiente)array[0]).getHistoricoAtual().getDescricao());
		assertEquals("Descrição do Ambiente2",((Ambiente)array[1]).getHistoricoAtual().getDescricao());
		assertEquals("Descrição do Ambiente3",((Ambiente)array[2]).getHistoricoAtual().getDescricao());
		
		assertEquals(new Integer(3).intValue(), colecao.size());
	}
	
	public void testGetQtdColaboradorByAmbiente()
	{
		Date hoje = Calendar.getInstance().getTime();
		Calendar doisMesesAntes = Calendar.getInstance();
		doisMesesAntes.add(Calendar.MONTH, -2);
		Calendar tresMesesAntes = Calendar.getInstance();
		tresMesesAntes.add(Calendar.MONTH, -3);
		
		Funcao funcao1 = FuncaoFactory.getEntity();
		funcao1.setNome("F1");
		funcaoDao.save(funcao1);
		Funcao funcao2 = FuncaoFactory.getEntity();
		funcao2.setNome("F2");
		funcaoDao.save(funcao2);
		Funcao funcao3 = FuncaoFactory.getEntity();
		funcao3.setNome("F3");
		funcaoDao.save(funcao3);
		Funcao funcao4 = FuncaoFactory.getEntity();
		funcao4.setNome("F4");
		funcaoDao.save(funcao4);
		
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
		historicoColaboradorDao.save(historicoColaborador1Fora);
		
		HistoricoColaborador historicoColaborador1Atual = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1Atual.setData(doisMesesAntes.getTime());
		historicoColaborador1Atual.setColaborador(colaborador1);
		historicoColaborador1Atual.setAmbiente(ambiente);
		historicoColaborador1Atual.setFuncao(funcao2);
		historicoColaboradorDao.save(historicoColaborador1Atual);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.getPessoal().setSexo('M');
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2Atual = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2Atual.setData(hoje);
		historicoColaborador2Atual.setColaborador(colaborador2);
		historicoColaborador2Atual.setAmbiente(ambiente);
		historicoColaborador2Atual.setFuncao(funcao3);
		historicoColaboradorDao.save(historicoColaborador2Atual);
		
		Colaborador colaborador3 = ColaboradorFactory.getEntity();
		colaborador3.getPessoal().setSexo('F');
		colaboradorDao.save(colaborador3);
		
		HistoricoColaborador historicoColaborador3 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador3.setData(hoje);
		historicoColaborador3.setColaborador(colaborador3);
		historicoColaborador3.setAmbiente(ambiente);
		historicoColaborador3.setFuncao(funcao3);
		historicoColaboradorDao.save(historicoColaborador3);
		
		assertEquals(2, ambienteDao.getQtdColaboradorByAmbiente(ambiente.getId(), hoje, Sexo.FEMININO));
	}
	
	public GenericDao<Ambiente> getGenericDao()
	{
		return ambienteDao;
	}

	public void setAmbienteDao(AmbienteDao ambienteDao)
	{
		this.ambienteDao = ambienteDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao) {
		this.estabelecimentoDao = estabelecimentoDao;
	}

	public void setHistoricoAmbienteDao(HistoricoAmbienteDao historicoAmbienteDao) {
		this.historicoAmbienteDao = historicoAmbienteDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}

	public void setHistoricoColaboradorDao(
			HistoricoColaboradorDao historicoColaboradorDao) {
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public void setFuncaoDao(FuncaoDao funcaoDao) {
		this.funcaoDao = funcaoDao;
	}

	public void setMedicaoRiscoDao(MedicaoRiscoDao medicaoRiscoDao) {
		this.medicaoRiscoDao = medicaoRiscoDao;
	}

	public void setRiscoMedicaoRiscoDao(RiscoMedicaoRiscoDao riscoMedicaoRiscoDao) {
		this.riscoMedicaoRiscoDao = riscoMedicaoRiscoDao;
	}

	public void setEpcDao(EpcDao epcDao) {
		this.epcDao = epcDao;
	}
}