package com.fortes.rh.test.dao.hibernate.cargosalario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.cargosalario.ReajusteColaboradorDao;
import com.fortes.rh.dao.cargosalario.TabelaReajusteColaboradorDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.cargosalario.relatorio.RelatorioPromocoes;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.ReajusteColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.DateUtil;

public class HistoricoColaboradorDaoHibernateTest_Junit4 extends GenericDaoHibernateTest_JUnit4<HistoricoColaborador>
{
	@Autowired private HistoricoColaboradorDao historicoColaboradorDao;
	@Autowired private ColaboradorDao colaboradorDao;
	@Autowired private EmpresaDao empresaDao;
	@Autowired private EstabelecimentoDao estabelecimentoDao;
	@Autowired private AreaOrganizacionalDao areaOrganizacionalDao;
	@Autowired private CargoDao cargoDao;
	@Autowired private FaixaSalarialDao faixaSalarialDao;
	@Autowired private TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao;
	@Autowired private ReajusteColaboradorDao reajusteColaboradorDao;
	@Autowired private FuncaoDao funcaoDao;
	@Autowired private AmbienteDao ambienteDao;
	
	Empresa empresa;
	Colaborador colaborador;
	HistoricoColaborador historicoColaborador;

	public HistoricoColaborador getEntity() {
		HistoricoColaborador historicoColaborador = new HistoricoColaborador();
		historicoColaborador.setId(null);
		historicoColaborador.setData(new Date());
		historicoColaborador.setMotivo("p");
		historicoColaborador.setSalario(1D);
		historicoColaborador.setColaborador(null);

		return historicoColaborador;
	}

	public GenericDao<HistoricoColaborador> getGenericDao() {
		return historicoColaboradorDao;
	}
	
	@Test
	public void testSetMotivo() {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);
		
		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		historico.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		historico.setColaborador(colaborador);
		historicoColaboradorDao.save(historico);

		historicoColaboradorDao.setMotivo(new Long[]{historico.getId()}, MotivoHistoricoColaborador.DISSIDIO);	
		
		assertEquals(MotivoHistoricoColaborador.DISSIDIO ,historicoColaboradorDao.findByIdProjectionHistorico(historico.getId()).getMotivo());
	}
	
	@Test
	public void testSetaContratadoNoPrimeiroHistorico() {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);
		
		HistoricoColaborador primeiro = HistoricoColaboradorFactory.getEntity();
		primeiro.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		primeiro.setColaborador(colaborador);
		primeiro.setData(DateUtil.criarDataMesAno(01, 02, 1999));
		historicoColaboradorDao.save(primeiro);
		
		HistoricoColaborador segundo = HistoricoColaboradorFactory.getEntity();
		segundo.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		segundo.setColaborador(colaborador);
		segundo.setData(DateUtil.criarDataMesAno(02, 05, 2001));
		historicoColaboradorDao.save(segundo);
		
		HistoricoColaborador terceiro = HistoricoColaboradorFactory.getEntity();
		terceiro.setMotivo(MotivoHistoricoColaborador.DISSIDIO);
		terceiro.setColaborador(colaborador);
		terceiro.setData(DateUtil.criarDataMesAno(02, 05, 2005));
		historicoColaboradorDao.save(terceiro);
		
		historicoColaboradorDao.setaContratadoNoPrimeiroHistorico(colaborador.getId());	
		
		assertEquals(MotivoHistoricoColaborador.CONTRATADO ,historicoColaboradorDao.findByIdProjectionHistorico(primeiro.getId()).getMotivo());
		assertEquals(MotivoHistoricoColaborador.PROMOCAO ,historicoColaboradorDao.findByIdProjectionHistorico(segundo.getId()).getMotivo());
		assertEquals(MotivoHistoricoColaborador.DISSIDIO ,historicoColaboradorDao.findByIdProjectionHistorico(terceiro.getId()).getMotivo());
	}
	
	@Test
	public void testAjustaMotivoContratado() {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoContratado = HistoricoColaboradorFactory.getEntity();
		historicoContratado.setMotivo(MotivoHistoricoColaborador.CONTRATADO);
		historicoContratado.setColaborador(colaborador);
		historicoColaboradorDao.save(historicoContratado);
		
		HistoricoColaborador historicoDissidio = HistoricoColaboradorFactory.getEntity();
		historicoDissidio.setMotivo(MotivoHistoricoColaborador.DISSIDIO);
		historicoDissidio.setColaborador(colaborador);
		historicoColaboradorDao.save(historicoDissidio);
		
		historicoColaboradorDao.ajustaMotivoContratado(colaborador.getId());	
		
		assertEquals(MotivoHistoricoColaborador.PROMOCAO ,historicoColaboradorDao.findByIdProjectionHistorico(historicoContratado.getId()).getMotivo());
		assertEquals(MotivoHistoricoColaborador.DISSIDIO ,historicoColaboradorDao.findByIdProjectionHistorico(historicoDissidio.getId()).getMotivo());
	}
	
	@Test
	public void testGetPrimeiroHistorico()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador primeiroHistorico = saveHistoricoColaborador(colaborador, DateUtil.criarDataMesAno(01, 01, 2000));
		saveHistoricoColaborador(colaborador, DateUtil.criarDataMesAno(02, 02, 2009));

		assertEquals(primeiroHistorico, historicoColaboradorDao.getPrimeiroHistorico(colaborador.getId()));
	}

	@Test
	public void testFindByIdProjection()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historico1 = saveHistoricoColaborador(colaborador, DateUtil.incrementaDias(new Date(), -5));
		saveHistoricoColaborador(colaborador, new Date());
		assertEquals(historico1, historicoColaboradorDao.findByIdProjection(historico1.getId()));
	}

	@Test
	public void testFindByIdProjectionHistorico()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		historico.setColaborador(colaborador);
		historico = historicoColaboradorDao.save(historico);

		assertEquals(historico, historicoColaboradorDao.findByIdProjectionHistorico(historico.getId()));
	}

	@Test
	public void testGetRelatorioPromocoes()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Estabelecimento parajana = saveEstabelecimento(empresa);
		
		AreaOrganizacional garagem = saveAreaOrganizacional("GARAGEM");
		AreaOrganizacional lavajato = saveAreaOrganizacional("LAVAJATO");
		
		Cargo cobrador = saveCargo(empresa, "COBRADOR");
		
		FaixaSalarial faixaUmCobrador = saveFaixaSalarial(cobrador, "I");
		FaixaSalarial faixaDoisCobrador = saveFaixaSalarial(cobrador, "II");
		
		Cargo motorista = saveCargo(empresa, "MOTORISTA");
		
		FaixaSalarial faixaAMotorista = saveFaixaSalarial(motorista, "I");
		
		Colaborador jose = saveColaborador(empresa);
		jose.setDataDesligamento(DateUtil.criarDataMesAno(01, 02, 2011));
		Colaborador joao = saveColaborador(empresa);
		Colaborador maria = saveColaborador(empresa);
		
		/*
		 *  Nesta situação o colaborador foi desligado antes que pudesse ser gerado o 1o histórico HORIZONTAL ou VERTICAL. 
		 *  O 2o e 3o histórico simulam históricos fictícios que podem ser gerados a partir da criação de um novo histórico 
		 *  de faixa salarial ou índice.
		 */
		saveHistoricoColaborador(jose, parajana, garagem, faixaUmCobrador, DateUtil.criarDataMesAno(01, 02, 2010), 500.0, MotivoHistoricoColaborador.CONTRATADO);
		saveHistoricoColaborador(jose, parajana, garagem, faixaUmCobrador, DateUtil.criarDataMesAno(02, 02, 2011), 600.0, MotivoHistoricoColaborador.PROMOCAO);
		saveHistoricoColaborador(jose, parajana, garagem, faixaDoisCobrador, DateUtil.criarDataMesAno(01, 04, 2011), 500.0, MotivoHistoricoColaborador.PROMOCAO);

		saveHistoricoColaborador(joao, parajana, garagem, faixaUmCobrador, DateUtil.criarDataMesAno(01, 02, 2010), 500.0, MotivoHistoricoColaborador.CONTRATADO);
		saveHistoricoColaborador(joao, parajana, garagem, faixaUmCobrador, DateUtil.criarDataMesAno(02, 02, 2011), 600.0, MotivoHistoricoColaborador.PROMOCAO);
		saveHistoricoColaborador(joao, parajana, garagem, faixaDoisCobrador, DateUtil.criarDataMesAno(01, 04, 2011), 500.0, MotivoHistoricoColaborador.PROMOCAO);
		saveHistoricoColaborador(joao, parajana, garagem, faixaDoisCobrador, DateUtil.criarDataMesAno(02, 04, 2011), 650.0, MotivoHistoricoColaborador.PROMOCAO);
		saveHistoricoColaborador(joao, parajana, lavajato, faixaDoisCobrador, DateUtil.criarDataMesAno(01, 05, 2011), 650.0, MotivoHistoricoColaborador.PROMOCAO);
		saveHistoricoColaborador(joao, parajana, lavajato, faixaAMotorista, DateUtil.criarDataMesAno(06, 06, 2012), 650.0, MotivoHistoricoColaborador.PROMOCAO);
		
		saveHistoricoColaborador(maria, parajana, garagem, faixaUmCobrador, DateUtil.criarDataMesAno(01, 02, 2011), 500.0, MotivoHistoricoColaborador.CONTRATADO);
		saveHistoricoColaborador(maria, parajana, garagem, faixaAMotorista, DateUtil.criarDataMesAno(02, 02, 2011), 550.0, MotivoHistoricoColaborador.PROMOCAO);
		saveHistoricoColaborador(maria, parajana, garagem, faixaAMotorista, DateUtil.criarDataMesAno(05, 05, 2011), 600.0, MotivoHistoricoColaborador.PROMOCAO);
		
		Date dataIni = DateUtil.criarDataMesAno(1, 1, 2010);
		Date dataFim = DateUtil.criarDataMesAno(1, 1, 2013);
		
		colaboradorDao.getHibernateTemplateByGenericDao().flush();
		
		List<RelatorioPromocoes> relatorioPromocoes = historicoColaboradorDao.getRelatorioPromocoes(null, null, dataIni, dataFim, empresa.getId());
		assertEquals(2, relatorioPromocoes.size());
		
		assertEquals(4, relatorioPromocoes.get(0).getQtdHorizontal());
		assertEquals(0, relatorioPromocoes.get(1).getQtdHorizontal());

		assertEquals(1, relatorioPromocoes.get(0).getQtdVertical());
		assertEquals(1, relatorioPromocoes.get(1).getQtdVertical());
	}

	@Test
    public void testExisteHistoricoConfirmadoByTabelaReajusteColaborador() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Cargo cargo = saveCargo(empresa, "Cargo");
				
		AreaOrganizacional areaOrganizacionalAtual = saveAreaOrganizacional("Area Atual");
		AreaOrganizacional areaOrganizacionalProposta = saveAreaOrganizacional("Area Proposta");
		
		FaixaSalarial faixaSalarialAtual = saveFaixaSalarial(cargo, "I");
		FaixaSalarial faixaSalarialProposta = saveFaixaSalarial(cargo, "II"); 

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		saveHistoricoColaborador(colaborador, areaOrganizacionalAtual, faixaSalarialAtual, DateUtil.incrementaDias(new Date(), -1), 1000.0, MotivoHistoricoColaborador.CONTRATADO, StatusRetornoAC.CONFIRMADO, null);
		
		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
    	tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);
		
		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador(colaborador, areaOrganizacionalAtual, areaOrganizacionalProposta, faixaSalarialAtual, faixaSalarialProposta,
				TipoAplicacaoIndice.VALOR, TipoAplicacaoIndice.VALOR, 1000.0, 1200.0);
		reajusteColaborador.setTabelaReajusteColaborador(tabelaReajusteColaborador);
		reajusteColaboradorDao.save(reajusteColaborador);
		HistoricoColaborador historicoColaborador = saveHistoricoColaborador(colaborador, areaOrganizacionalProposta, faixaSalarialProposta, new Date(), 1200.0, MotivoHistoricoColaborador.SEM_MOTIVO, StatusRetornoAC.AGUARDANDO, reajusteColaborador);
		assertFalse(historicoColaboradorDao.existeHistoricoConfirmadoByTabelaReajusteColaborador(tabelaReajusteColaborador.getId()));
		
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.update(historicoColaborador);
		
		assertTrue(historicoColaboradorDao.existeHistoricoConfirmadoByTabelaReajusteColaborador(tabelaReajusteColaborador.getId()));
    }
	
	@Test
	public void testUpdateAmbienteEFuncao()
	{
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambienteDao.save(ambiente);
		
		Funcao funcao = FuncaoFactory.getEntity();
		funcaoDao.save(funcao);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Cargo cargo = saveCargo(empresa, "Teste");
		FaixaSalarial faixaSalarial = saveFaixaSalarial(cargo, "I");
		Colaborador colaborador = saveColaborador(empresa);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, faixaSalarial, new Date(), StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);
		
		HistoricoColaborador retorno = historicoColaboradorDao.findById(historicoColaborador.getId());
		assertNull(retorno.getAmbiente());
		assertNull(retorno.getFuncao());
		
		historicoColaborador.setAmbiente(ambiente);
		historicoColaborador.setFuncao(funcao);
		
		assertTrue(historicoColaboradorDao.updateAmbienteEFuncao(historicoColaborador));
		retorno = historicoColaboradorDao.findById(historicoColaborador.getId());
		assertEquals(ambiente.getId(), retorno.getAmbiente().getId());
		assertEquals(funcao.getId(), retorno.getFuncao().getId());
	}
	
	@Test
	public void testIsUltimoHistoricoOrPosteriorAoUltimoTrue()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L, "Empresa", "9999", "001");
		empresaDao.save(empresa);
		
		Cargo cargo = saveCargo(empresa, "Teste");
		FaixaSalarial faixaSalarial = saveFaixaSalarial(cargo, "I");
		
		Colaborador colaborador = ColaboradorFactory.getEntity("999999", 2L);
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(colaborador, faixaSalarial, DateUtil.criarDataMesAno(1, 2, 2017), StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);
		
		historicoColaboradorDao.getHibernateTemplateByGenericDao().flush();
		
		assertTrue(historicoColaboradorDao.isUltimoHistoricoOrPosteriorAoUltimo(historicoColaborador.getData(), colaborador.getCodigoAC(), empresa.getCodigoAC(), empresa.getGrupoAC()));
	}
	
	@Test
	public void testIsUltimoHistoricoOrPosteriorAoUltimoFalse()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L, "Empresa", "9999", "001");
		empresaDao.save(empresa);
		
		Cargo cargo = saveCargo(empresa, "Teste");
		FaixaSalarial faixaSalarial = saveFaixaSalarial(cargo, "I");
		
		Colaborador colaborador = ColaboradorFactory.getEntity("999999", 2L);
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity(colaborador, faixaSalarial, DateUtil.criarDataMesAno(1, 2, 2017), StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador1);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity(colaborador, faixaSalarial, DateUtil.criarDataMesAno(1, 3, 2017), StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador2);
		
		historicoColaboradorDao.getHibernateTemplateByGenericDao().flush();
		
		assertFalse(historicoColaboradorDao.isUltimoHistoricoOrPosteriorAoUltimo(historicoColaborador1.getData(), colaborador.getCodigoAC(), empresa.getCodigoAC(), empresa.getGrupoAC()));
	}
	
	private HistoricoColaborador saveHistoricoColaborador(Colaborador joao, AreaOrganizacional garagem, FaixaSalarial faixaUmCobrador, Date data, Double valorSalario, String motivo, Integer status, ReajusteColaborador reajusteColaborador) {
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(joao, data, faixaUmCobrador, null, garagem, null, null, status);
		historicoColaborador.setMotivo(motivo);
		historicoColaborador.setSalario(valorSalario);
		historicoColaborador.setReajusteColaborador(reajusteColaborador);
		historicoColaboradorDao.save(historicoColaborador);
		return historicoColaborador;
	}
	
	private void saveHistoricoColaborador(Colaborador joao, Estabelecimento parajana, AreaOrganizacional garagem, FaixaSalarial faixaUmCobrador, Date data, Double valorSalario, String motivo) {
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity(joao, data, faixaUmCobrador, parajana, garagem, null, null, StatusRetornoAC.CONFIRMADO);
		historicoColaborador.setMotivo(motivo);
		historicoColaborador.setSalario(valorSalario);
		historicoColaboradorDao.save(historicoColaborador);
	}

	private Colaborador saveColaborador(Empresa empresa) {
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		return colaborador;
	}

	private FaixaSalarial saveFaixaSalarial(Cargo cargo, String nome) {
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setNome(nome);
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		return faixaSalarial;
	}

	private Cargo saveCargo(Empresa empresa, String nome) {
		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome(nome);
		cargo.setEmpresa(empresa);
		cargoDao.save(cargo);
		return cargo;
	}

	private AreaOrganizacional saveAreaOrganizacional(String nome) {
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setNome(nome);
		areaOrganizacionalDao.save(areaOrganizacional);
		return areaOrganizacional;
	}

	private Estabelecimento saveEstabelecimento(Empresa empresa) {
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setEmpresa(empresa);
		estabelecimentoDao.save(estabelecimento);
		return estabelecimento;
	}

	private HistoricoColaborador saveHistoricoColaborador(Colaborador colaborador, Date data) {
		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		historico.setColaborador(colaborador);
		historico.setData(data);
		historico = historicoColaboradorDao.save(historico);
		return historico;
	}
}