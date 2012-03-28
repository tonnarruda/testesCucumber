package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.dao.sesmt.HistoricoAmbienteDao;
import com.fortes.rh.dao.sesmt.RiscoAmbienteDao;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoAmbiente;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoAmbienteFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;

public class RiscoAmbienteDaoHibernateTest extends GenericDaoHibernateTest<RiscoAmbiente>
{
	private RiscoAmbienteDao riscoAmbienteDao;
	private HistoricoAmbienteDao historicoAmbienteDao;
	private AmbienteDao ambienteDao;
	private EstabelecimentoDao estabelecimentoDao;
	private ColaboradorDao colaboradorDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	RiscoDao riscoDao;
	EmpresaDao empresaDao;

	@Override
	public RiscoAmbiente getEntity()
	{
		return RiscoAmbienteFactory.getEntity();
	}

	@Override
	public GenericDao<RiscoAmbiente> getGenericDao()
	{
		return riscoAmbienteDao;
	}

	public void setRiscoAmbienteDao(RiscoAmbienteDao riscoAmbienteDao)
	{
		this.riscoAmbienteDao = riscoAmbienteDao;
	}
	
	public void testRemoveByHistoricoAmbiente()
	{
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbienteDao.save(historicoAmbiente);
		
		RiscoAmbiente riscoAmbiente = RiscoAmbienteFactory.getEntity();
		riscoAmbiente.setHistoricoAmbiente(historicoAmbiente);
		riscoAmbienteDao.save(riscoAmbiente);
		
		assertTrue(riscoAmbienteDao.removeByHistoricoAmbiente(historicoAmbiente.getId()));
	}
	
	public void testFindRiscosByAmbienteData()
	{
		Date hoje = Calendar.getInstance().getTime();
		Calendar doisMesesAtras = Calendar.getInstance();
		doisMesesAtras.add(Calendar.MONTH, -2);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambiente.setEmpresa(empresa);
		ambienteDao.save(ambiente);
		
		HistoricoAmbiente historicoAmbiente = new HistoricoAmbiente();
		historicoAmbiente.setData(doisMesesAtras.getTime());
		historicoAmbiente.setAmbiente(ambiente);
		historicoAmbienteDao.save(historicoAmbiente);
		
		HistoricoAmbiente historicoAmbienteAtual = new HistoricoAmbiente();
		historicoAmbienteAtual.setData(hoje);
		historicoAmbienteAtual.setAmbiente(ambiente);
		historicoAmbienteDao.save(historicoAmbienteAtual);
		
		Risco risco1 = RiscoFactory.getEntity();
		risco1.setEmpresa(empresa);
		risco1.setDescricao("Calor");
		riscoDao.save(risco1);
		Risco risco2 = RiscoFactory.getEntity();
		risco2.setEmpresa(empresa);
		risco2.setDescricao("Ruído");
		riscoDao.save(risco2);
		
		RiscoAmbiente riscoAmbienteDoisMesesAtras = RiscoAmbienteFactory.getEntity();
		riscoAmbienteDoisMesesAtras.setRisco(risco1);
		riscoAmbienteDoisMesesAtras.setHistoricoAmbiente(historicoAmbiente);
		riscoAmbienteDao.save(riscoAmbienteDoisMesesAtras);
		
		RiscoAmbiente riscoAmbienteAtual1 = RiscoAmbienteFactory.getEntity();
		riscoAmbienteAtual1.setRisco(risco1);
		riscoAmbienteAtual1.setHistoricoAmbiente(historicoAmbienteAtual);
		riscoAmbienteDao.save(riscoAmbienteAtual1);
		RiscoAmbiente riscoAmbienteAtual2 = RiscoAmbienteFactory.getEntity();
		riscoAmbienteAtual2.setRisco(risco2);
		riscoAmbienteAtual2.setHistoricoAmbiente(historicoAmbienteAtual);
		riscoAmbienteDao.save(riscoAmbienteAtual2);
		
		Collection<Risco> riscos = riscoAmbienteDao.findRiscosByAmbienteData(ambiente.getId(), doisMesesAtras.getTime());
		
		assertEquals(1, riscos.size());
		assertEquals(risco1, (Risco)riscos.toArray()[0]);
	}
	
	
	public void testFindColaboradoresSem()
	{
		Date hoje = Calendar.getInstance().getTime();
		Calendar doisMesesAntes = Calendar.getInstance();
		doisMesesAntes.add(Calendar.MONTH, -2);
		Calendar tresMesesAntes = Calendar.getInstance();
		tresMesesAntes.add(Calendar.MONTH, -3);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambiente.setNome("recepcao");
		ambienteDao.save(ambiente);
		
		HistoricoAmbiente Historicoambiente = new HistoricoAmbiente();
		Historicoambiente.setAmbiente(ambiente);
		Historicoambiente.setData(hoje);
		Historicoambiente.setDescricao("Piso metálico");
		historicoAmbienteDao.save(Historicoambiente);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador1);
		
		//Não precisa apagar historicoColaborador1Fora, ..., serve para entender melhor o teste "Fora, Atual"
		HistoricoColaborador historicoColaborador1Fora = criaHistoricoColaborador(colaborador1, tresMesesAntes.getTime(), ambiente, null, estabelecimento1);
		
		HistoricoColaborador historicoColaborador1Atual = criaHistoricoColaborador(colaborador1, doisMesesAntes.getTime(), ambiente, null, estabelecimento1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setNome("teste");
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2Fora = criaHistoricoColaborador(colaborador2, tresMesesAntes.getTime(), null, null, estabelecimento1);

		Colaborador colaborador3 = ColaboradorFactory.getEntity();
		colaborador3.setNome("teste3");
		colaborador3.setDataDesligamento(hoje);
		colaboradorDao.save(colaborador3);
		
		HistoricoColaborador historicoColaborador3DentroComDataDesligamentoAnterior = criaHistoricoColaborador(colaborador3, tresMesesAntes.getTime(), null, null, estabelecimento1);

		Colaborador colaborador4 = ColaboradorFactory.getEntity();
		colaborador4.setNome("teste3");
		colaborador4.setDataDesligamento(tresMesesAntes.getTime());
		colaboradorDao.save(colaborador4);
		
		HistoricoColaborador historicoColaborador4ForaComDataDesligamentoAnterior = criaHistoricoColaborador(colaborador4, tresMesesAntes.getTime(), null, null, estabelecimento1);
		
		Collection<String> nomes = riscoAmbienteDao.findColaboradoresSemAmbiente(hoje, estabelecimento1.getId());
		
		assertEquals(2, nomes.size());
	}
	
	public void testFindFuncaoAtualDosColaboradores()
	{
		Date hoje = Calendar.getInstance().getTime();
		Calendar doisMesesAntes = Calendar.getInstance();
		doisMesesAntes.add(Calendar.MONTH, -2);
		Calendar tresMesesAntes = Calendar.getInstance();
		tresMesesAntes.add(Calendar.MONTH, -3);
		
		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento1);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambiente.setNome("recepcao");
		ambienteDao.save(ambiente);
		
		HistoricoAmbiente Historicoambiente = new HistoricoAmbiente();
		Historicoambiente.setAmbiente(ambiente);
		Historicoambiente.setData(hoje);
		Historicoambiente.setDescricao("Piso metálico");
		historicoAmbienteDao.save(Historicoambiente);
		
		Ambiente ambiente2 = AmbienteFactory.getEntity();
		ambiente2.setNome("recepcao");
		ambienteDao.save(ambiente2);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador1);
		
		//Não precisa apagar historicoColaborador1Fora, ..., serve para entender melhor o teste "Fora, Atual"
		HistoricoColaborador historicoColaborador1Fora = criaHistoricoColaborador(colaborador1, tresMesesAntes.getTime(), ambiente, null, estabelecimento1);
		
		HistoricoColaborador historicoColaborador1Atual = criaHistoricoColaborador(colaborador1, doisMesesAntes.getTime(), ambiente2, null, estabelecimento1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2Fora = criaHistoricoColaborador(colaborador2, tresMesesAntes.getTime(), ambiente, null, estabelecimento1);
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento2);
		
		Colaborador colaborador3 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador3);
		
		HistoricoColaborador historicoColaborador3Fora = criaHistoricoColaborador(colaborador2, hoje, ambiente2, null, estabelecimento2);
		
		Collection<Long> ids = riscoAmbienteDao.findAmbienteAtualDosColaboradores(hoje, estabelecimento1.getId());
		
		assertEquals(1, ids.size());
		assertEquals(ambiente2.getId(), ids.toArray()[0]);
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
	
	public void setHistoricoAmbienteDao(HistoricoAmbienteDao historicoAmbienteDao) {
		this.historicoAmbienteDao = historicoAmbienteDao;
	}

	public void setAmbienteDao(AmbienteDao ambienteDao) {
		this.ambienteDao = ambienteDao;
	}

	public void setRiscoDao(RiscoDao riscoDao) {
		this.riscoDao = riscoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao) {
		this.estabelecimentoDao = estabelecimentoDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}

	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao) {
		this.historicoColaboradorDao = historicoColaboradorDao;
	}
}
