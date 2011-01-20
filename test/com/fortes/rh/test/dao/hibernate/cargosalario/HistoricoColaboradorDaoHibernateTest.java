package com.fortes.rh.test.dao.hibernate.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.model.AbstractModel;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialHistoricoDao;
import com.fortes.rh.dao.cargosalario.GrupoOcupacionalDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.cargosalario.IndiceDao;
import com.fortes.rh.dao.cargosalario.IndiceHistoricoDao;
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
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.TipoBuscaHistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.GrupoOcupacionalFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.ReajusteColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;

@SuppressWarnings("deprecation")
public class HistoricoColaboradorDaoHibernateTest extends GenericDaoHibernateTest<HistoricoColaborador>
{
	private HistoricoColaboradorDao historicoColaboradorDao;
	private ColaboradorDao colaboradorDao;
	private EstabelecimentoDao estabelecimentoDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private EmpresaDao empresaDao;
	private FaixaSalarialDao faixaSalarialDao;
	private CargoDao cargoDao;
	private GrupoOcupacionalDao grupoOcupacionalDao;
	private ReajusteColaboradorDao reajusteColaboradorDao;
	private TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao;
	private AmbienteDao ambienteDao;
	private FuncaoDao funcaoDao;
	private IndiceDao indiceDao;
	private IndiceHistoricoDao indiceHistoricoDao;
	
	private FaixaSalarialHistoricoDao faixaSalarialHistoricoDao;

	public HistoricoColaborador getEntity()
	{
		HistoricoColaborador historicoColaborador = new HistoricoColaborador();

		historicoColaborador.setId(null);
		historicoColaborador.setData(new Date());
		historicoColaborador.setMotivo("p");
		historicoColaborador.setSalario(1D);
		historicoColaborador.setColaborador(null);

		return historicoColaborador;
	}

	public GenericDao<HistoricoColaborador> getGenericDao()
	{
		return historicoColaboradorDao;
	}

	public void testGetHistoricoAnterior()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador h0 = HistoricoColaboradorFactory.getEntity();
		h0.setData(DateUtil.criarAnoMesDia(2008, 03, 1));
		h0.setColaborador(colaborador);
		h0 = historicoColaboradorDao.save(h0);

		HistoricoColaborador h1 = HistoricoColaboradorFactory.getEntity();
		h1.setData(DateUtil.criarAnoMesDia(2008, 04, 1));
		h1.setColaborador(colaborador);
		h1 = historicoColaboradorDao.save(h1);

		HistoricoColaborador h2 = HistoricoColaboradorFactory.getEntity();
		h2.setData(DateUtil.criarAnoMesDia(2008, 06, 1));
		h2.setColaborador(colaborador);
		h2 = historicoColaboradorDao.save(h2);

		HistoricoColaborador h3 = HistoricoColaboradorFactory.getEntity();
		h3.setData(DateUtil.criarAnoMesDia(2008, 07, 1));
		h3.setColaborador(colaborador);
		h3 = historicoColaboradorDao.save(h3);

		HistoricoColaborador hRetorno = historicoColaboradorDao.getHistoricoAnterior(h1);
		assertEquals(h0.getId(), hRetorno.getId());

		HistoricoColaborador hRetorno2 = historicoColaboradorDao.getHistoricoAnterior(h0);
		assertEquals(null, hRetorno2);
	}

	public void testFindByColaboradorProjection()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		historico.setColaborador(colaborador);
		historico = historicoColaboradorDao.save(historico);

		assertEquals(1, historicoColaboradorDao.findByColaboradorProjection(colaborador.getId()).size());
	}
	
	public void testDeleteSituacaoByMovimentoSalarial()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("as32d1");
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		historico.setMovimentoSalarialId(2);
		historico.setColaborador(colaborador);
		historico = historicoColaboradorDao.save(historico);
		
		historicoColaboradorDao.deleteSituacaoByMovimentoSalarial(2L, empresa.getId());
		assertEquals(null, historicoColaboradorDao.findByIdProjectionMinimo(historico.getId()));
	}

	public void testGetPrimeiroHistorico()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador primeiroHistorico = HistoricoColaboradorFactory.getEntity();
		primeiroHistorico.setColaborador(colaborador);
		primeiroHistorico.setData(DateUtil.criarDataMesAno(01, 01, 2000));
		primeiroHistorico = historicoColaboradorDao.save(primeiroHistorico);

		HistoricoColaborador historicoAtual = HistoricoColaboradorFactory.getEntity();
		historicoAtual.setColaborador(colaborador);
		historicoAtual.setData(DateUtil.criarDataMesAno(02, 02, 2009));
		historicoAtual = historicoColaboradorDao.save(historicoAtual);

		assertEquals(primeiroHistorico, historicoColaboradorDao.getPrimeiroHistorico(colaborador.getId()));
	}

	public void testFindByIdHQL()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		historico.setColaborador(colaborador);
		historico = historicoColaboradorDao.save(historico);

		assertEquals(historico, historicoColaboradorDao.findByIdHQL(historico.getId()));
	}

	public void testFindByIdProjection()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		historico.setColaborador(colaborador);
		historico = historicoColaboradorDao.save(historico);

		assertEquals(historico, historicoColaboradorDao.findByIdProjection(historico.getId()));
	}

	public void testFindByIdProjectionHistorico()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		historico.setColaborador(colaborador);
		historico = historicoColaboradorDao.save(historico);

		assertEquals(historico, historicoColaboradorDao.findByIdProjectionHistorico(historico.getId()));
	}

	public void testFindPromocaoByColaborador()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional1 = areaOrganizacionalDao.save(areaOrganizacional1);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimento1 = estabelecimentoDao.save(estabelecimento1);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setData(DateUtil.criarAnoMesDia(2008, 10, 12));
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setValor(500.0);

		Indice indice = IndiceFactory.getEntity();

		FaixaSalarialHistorico faixaSalarialHistorico2 = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico2.setData(DateUtil.criarAnoMesDia(2008, 12, 12));
		faixaSalarialHistorico2.setTipo(TipoAplicacaoIndice.INDICE);
		faixaSalarialHistorico2.setIndice(indice);

		montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2007, 03, 1), colaborador, estabelecimento1, areaOrganizacional1, faixaSalarial, TipoAplicacaoIndice.VALOR);
		montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2008, 03, 1), colaborador, estabelecimento1, areaOrganizacional1, faixaSalarial, TipoAplicacaoIndice.VALOR);

		Collection<HistoricoColaborador> historicoColaboradores = historicoColaboradorDao.findPromocaoByColaborador(colaborador.getId());

		assertEquals(2, historicoColaboradores.size());
	}

	public void testGetPromocoes()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional1 = areaOrganizacionalDao.save(areaOrganizacional1);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimento1 = estabelecimentoDao.save(estabelecimento1);

		HistoricoColaborador historicoColaborador = montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2007, 03, 1), colaborador, estabelecimento1, areaOrganizacional1, null, TipoAplicacaoIndice.VALOR);
		HistoricoColaborador historicoColaboradorAnterior = montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2006, 03, 1), colaborador, estabelecimento1, areaOrganizacional1, null, TipoAplicacaoIndice.VALOR);

		historicoColaborador.setHistoricoAnterior(historicoColaboradorAnterior);
		historicoColaborador.setMotivo(MotivoHistoricoColaborador.PROMOCAO_HORIZONTAL);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		Collection<HistoricoColaborador> historicoColaboradores = historicoColaboradorDao.getPromocoes(new Long[]{areaOrganizacional1.getId()}, new Long[]{estabelecimento1.getId()}, DateUtil.criarDataMesAno(01, 02, 2007), DateUtil.criarDataMesAno(01, 02, 2008));

		assertEquals(1, historicoColaboradores.size());
	}

	public void testGetHistoricoAtual()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional1 = areaOrganizacionalDao.save(areaOrganizacional1);

		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional2 = areaOrganizacionalDao.save(areaOrganizacional2);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimento1 = estabelecimentoDao.save(estabelecimento1);

		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimento2 = estabelecimentoDao.save(estabelecimento2);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2007, 03, 1), colaborador, estabelecimento1, areaOrganizacional1, faixaSalarial, TipoAplicacaoIndice.VALOR);
		HistoricoColaborador historicoColaborador2 = montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2008, 03, 1), colaborador, estabelecimento1, areaOrganizacional1, faixaSalarial, TipoAplicacaoIndice.VALOR);
		montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2020, 03, 1), colaborador, estabelecimento1, areaOrganizacional1, faixaSalarial, TipoAplicacaoIndice.VALOR);

		HistoricoColaborador historicoColaboradore = historicoColaboradorDao.getHistoricoAtual(colaborador.getId(), TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);

		assertEquals(historicoColaborador2, historicoColaboradore);
	}

	public void testFindByCargosIdsComEmpresa()
	{
		Cargo cargo	= CargoFactory.getEntity();
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setNome("Pedro Paulo");
		colaborador.setMatricula("112Ae3456");
		colaborador.setDesligado(false);
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		Collection<Long> cargoIds = LongUtil.arrayStringToCollectionLong(new String[] {cargo.getId().toString()});

		Colaborador colaboradorBusca = ColaboradorFactory.getEntity();
		colaboradorBusca.setNome("ro pA");
		colaboradorBusca.setMatricula("2a");

		Collection<HistoricoColaborador> retorno1 = historicoColaboradorDao.findByCargosIds(1, 15, cargoIds, empresa.getId(), colaboradorBusca);
		Collection<HistoricoColaborador> retorno2 = historicoColaboradorDao.findByCargosIds(1, 15, new ArrayList<Long>(), empresa.getId(), colaboradorBusca);
		Collection<HistoricoColaborador> retorno3 = historicoColaboradorDao.findByCargosIds(1, 15, null, empresa.getId(), colaboradorBusca);

		assertEquals("cargoIds preenchido", 1, retorno1.size());
		assertEquals("cargoIds vazio", 0, retorno2.size());
		assertEquals("cargoIds nulo", 0, retorno3.size());
	}

	public void testFindByGrupoOcupacionalIds()
	{
		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo	= CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setDesligado(false);
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		Collection<Long> grupoOcupacionalIds = LongUtil.arrayStringToCollectionLong(new String[] {grupoOcupacional.getId().toString()});

		Collection<HistoricoColaborador> retorno1 = historicoColaboradorDao.findByGrupoOcupacionalIds(1, 15, grupoOcupacionalIds, empresa.getId());
		Collection<HistoricoColaborador> retorno2 = historicoColaboradorDao.findByGrupoOcupacionalIds(1, 15, new ArrayList<Long>(), empresa.getId());
		Collection<HistoricoColaborador> retorno3 = historicoColaboradorDao.findByGrupoOcupacionalIds(1, 15, null, empresa.getId());

		assertEquals("grupoOcupacionalIds preenchido", 1, retorno1.size());
		assertEquals("grupoOcupacionalIds vazio", 0, retorno2.size());
		assertEquals("grupoOcupacionalIds nulo", 0, retorno3.size());
	}

	@SuppressWarnings("unused")
	public void testGetHistoricosAtuaisByEstabelecimentoArea()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa);
		colaborador1 = colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaborador2 = colaboradorDao.save(colaborador2);

		Colaborador colaborador3 = ColaboradorFactory.getEntity();
		colaborador3.setEmpresa(empresa);
		colaborador3.setNaoIntegraAc(true);
		colaborador3 = colaboradorDao.save(colaborador3);

		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional1.setId(null);
		areaOrganizacional1 = areaOrganizacionalDao.save(areaOrganizacional1);

		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional2.setId(null);
		areaOrganizacional2 = areaOrganizacionalDao.save(areaOrganizacional2);

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
		estabelecimento1 = estabelecimentoDao.save(estabelecimento1);

		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimento2 = estabelecimentoDao.save(estabelecimento2);

		Date data = DateUtil.criarAnoMesDia(2008, 03, 1);
		//Não apagar linhas com avisos
		HistoricoColaborador historicoColaborador6 = montaSaveHistoricoColaborador(data, colaborador3, estabelecimento1, areaOrganizacional1, null, TipoAplicacaoIndice.VALOR);
		//Salva os HistoricoColaborador com Colaborador 1
		HistoricoColaborador historicoColaborador1 = montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2009, 03, 1), colaborador1, estabelecimento1, areaOrganizacional1, null, TipoAplicacaoIndice.VALOR);
		HistoricoColaborador historicoColaborador2 = montaSaveHistoricoColaborador(data, colaborador1, estabelecimento1, areaOrganizacional1, null, TipoAplicacaoIndice.VALOR);
		//Não apagar linhas com avisos
		HistoricoColaborador historicoColaborador3 = montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2006, 03, 1), colaborador1, estabelecimento1, areaOrganizacional1, null, TipoAplicacaoIndice.VALOR);

		//Salva os HistoricoColaborador com Colaborador 2
		HistoricoColaborador historicoColaborador4 = montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2009, 03, 1), colaborador2, estabelecimento1, areaOrganizacional1, null, TipoAplicacaoIndice.VALOR);
		HistoricoColaborador historicoColaborador5 = montaSaveHistoricoColaborador(data, colaborador2, estabelecimento1, areaOrganizacional1, null, TipoAplicacaoIndice.VALOR);

		Collection<HistoricoColaborador> historicoColaboradores = historicoColaboradorDao.getHistoricosAtuaisByEstabelecimentoAreaGrupo(new Long[]{estabelecimento1.getId(), estabelecimento2.getId()}, '1', new Long[]{areaOrganizacional1.getId(), areaOrganizacional2.getId()}, null, empresa.getId(), data);

		assertEquals(2, historicoColaboradores.size());
		for (HistoricoColaborador historicoColaborador : historicoColaboradores)
		{
			assertTrue(historicoColaborador.getId().equals(historicoColaborador2.getId()) || historicoColaborador.getId().equals(historicoColaborador5.getId()));
		}
	}

	@SuppressWarnings("unused")
	public void testGetHistoricosAtuaisByEstabelecimentoGrupo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa);
		colaborador1 = colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaborador2 = colaboradorDao.save(colaborador2);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		Date data = DateUtil.criarAnoMesDia(2008, 03, 1);
		//Salva os HistoricoColaborador com Colaborador 1
		HistoricoColaborador historicoColaborador1 = montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2009, 03, 1), colaborador1, estabelecimento, null, faixaSalarial, TipoAplicacaoIndice.VALOR);
		HistoricoColaborador historicoColaborador2 = montaSaveHistoricoColaborador(data, colaborador1, estabelecimento, null, faixaSalarial, TipoAplicacaoIndice.VALOR);
		HistoricoColaborador historicoColaborador3 = montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2006, 03, 1), colaborador1, estabelecimento, null, faixaSalarial, TipoAplicacaoIndice.VALOR);

		//Salva os HistoricoColaborador com Colaborador 2
		HistoricoColaborador historicoColaborador4 = montaSaveHistoricoColaborador(DateUtil.criarAnoMesDia(2009, 03, 1), colaborador2, estabelecimento, null, faixaSalarial, TipoAplicacaoIndice.VALOR);
		HistoricoColaborador historicoColaborador5 = montaSaveHistoricoColaborador(data, colaborador2, estabelecimento, null, faixaSalarial, TipoAplicacaoIndice.INDICE);

		Collection<HistoricoColaborador> historicoColaboradores = historicoColaboradorDao.getHistoricosAtuaisByEstabelecimentoAreaGrupo(new Long[]{estabelecimento.getId()}, '2', null, new Long[]{grupoOcupacional.getId()}, empresa.getId(), data);

		assertEquals(1, historicoColaboradores.size());
		assertTrue(((AbstractModel) historicoColaboradores.toArray()[0]).getId().equals(historicoColaborador2.getId()));
	}

	private HistoricoColaborador montaSaveHistoricoColaborador(Date data, Colaborador colaborador, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional, FaixaSalarial faixaSalarial, int tipoSalario)
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(data);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setTipoSalario(tipoSalario);

		return historicoColaboradorDao.save(historicoColaborador);
	}

	public void testGetHistoricoProximo()
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(null);
		colaborador.setNome("nome colaborador");
		colaborador.setNomeComercial("nome comercial");
		colaborador.setDesligado(false);
		colaborador.setDataDesligamento(new Date());
		colaborador.setObservacao("observação");
		colaborador.setDataAdmissao(new Date());

		Endereco endereco = new Endereco();
		endereco.setLogradouro("logradouro");
		endereco.setNumero("00");
		endereco.setComplemento("complemento");
		endereco.setBairro("bairro");
		endereco.setCidade(null);
		endereco.setUf(null);
		endereco.setCep("0000000");
		colaborador.setEndereco(endereco);

		Contato contato = new Contato();
		contato.setEmail("mail@mail.com");
		contato.setFoneFixo("00000000");
		contato.setFoneCelular("00000000");
		colaborador.setContato(contato);

		Pessoal pessoal	= new Pessoal();
		pessoal.setDataNascimento(new Date());
		pessoal.setEstadoCivil("e");
		pessoal.setEscolaridade("e");
		pessoal.setSexo('m');
		pessoal.setConjugeTrabalha(false);
		pessoal.setCpf("00000000000");
		colaborador.setPessoal(pessoal);

		colaborador.setAreaOrganizacional(null);
		colaborador.setDependentes(null);

		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador h0 = new HistoricoColaborador();
		h0.setId(null);
		h0.setData(DateUtil.criarAnoMesDia(2008, 03, 1));
		h0.setMotivo("p");
		h0.setSalario(1D);
		h0.setColaborador(colaborador);

		h0 = historicoColaboradorDao.save(h0);

		HistoricoColaborador h1 = new HistoricoColaborador();
		h1.setId(null);
		h1.setData(DateUtil.criarAnoMesDia(2008, 04, 1));
		h1.setMotivo("p");
		h1.setSalario(1D);
		h1.setColaborador(colaborador);

		h1 = historicoColaboradorDao.save(h1);


		HistoricoColaborador h2 = new HistoricoColaborador();
		h2.setId(null);
		h2.setData(DateUtil.criarAnoMesDia(2008, 06, 1));
		h2.setMotivo("p");
		h2.setSalario(1D);
		h2.setColaborador(colaborador);

		h2 = historicoColaboradorDao.save(h2);

		HistoricoColaborador h3 = new HistoricoColaborador();
		h3.setId(null);
		h3.setData(DateUtil.criarAnoMesDia(2008, 07, 1));
		h3.setMotivo("p");
		h3.setSalario(1D);
		h3.setColaborador(colaborador);

		historicoColaboradorDao.save(h3);

		HistoricoColaborador hRetorno = historicoColaboradorDao.getHistoricoProximo(h1);

		assertEquals(h2.getId(), hRetorno.getId());

		hRetorno = historicoColaboradorDao.getHistoricoProximo(h3);

		assertEquals(null, hRetorno);
	}

	public void testAtualizarHistoricoAnterior()
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(null);
		colaborador.setNome("nome colaborador");
		colaborador.setNomeComercial("nome colaborador");
		colaborador.setDesligado(false);
		colaborador.setDataDesligamento(new Date());
		colaborador.setObservacao("observação");
		colaborador.setDataAdmissao(new Date());

		Endereco endereco = new Endereco();
		endereco.setLogradouro("logradouro");
		endereco.setNumero("00");
		endereco.setComplemento("complemento");
		endereco.setBairro("bairro");
		endereco.setCidade(null);
		endereco.setUf(null);
		endereco.setCep("0000000");
		colaborador.setEndereco(endereco);

		Contato contato = new Contato();
		contato.setEmail("mail@mail.com");
		contato.setFoneFixo("00000000");
		contato.setFoneCelular("00000000");
		colaborador.setContato(contato);

		Pessoal pessoal	= new Pessoal();
		pessoal.setDataNascimento(new Date());
		pessoal.setEstadoCivil("e");
		pessoal.setEscolaridade("e");
		pessoal.setSexo('m');
		pessoal.setConjugeTrabalha(false);
		pessoal.setCpf("00000000000");
		colaborador.setPessoal(pessoal);

		colaborador.setAreaOrganizacional(null);
		colaborador.setDependentes(null);

		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador h0 = new HistoricoColaborador();
		h0.setId(null);
		h0.setData(DateUtil.criarAnoMesDia(2008, 03, 1));
		h0.setMotivo("p");
		h0.setSalario(1D);
		h0.setColaborador(colaborador);

		h0 = historicoColaboradorDao.save(h0);

		HistoricoColaborador h1 = new HistoricoColaborador();
		h1.setId(null);
		h1.setData(DateUtil.criarAnoMesDia(2008, 04, 1));
		h1.setMotivo("p");
		h1.setSalario(1D);
		h1.setColaborador(colaborador);

		h1 = historicoColaboradorDao.save(h1);

		historicoColaboradorDao.atualizarHistoricoAnterior(h1);

		historicoColaboradorDao.findById(h1.getId());

//		assertEquals(h0,h3.getHistoricoAnterior());
	}

	public void testSetStatus()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		assertEquals(true, historicoColaboradorDao.setStatus(historicoColaborador.getId(), true));

		HistoricoColaborador historicoColaboradorRetorno = historicoColaboradorDao.findByIdProjectionMinimo(historicoColaborador.getId());
		assertEquals(StatusRetornoAC.CONFIRMADO, historicoColaboradorRetorno.getStatus());
	}

	public void testFindByIdProjectionMinimo()
	{
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		HistoricoColaborador historicoColaboradorRetorno = historicoColaboradorDao.findByIdProjectionMinimo(historicoColaborador.getId());
		assertEquals(historicoColaborador, historicoColaboradorRetorno);
	}

	public void testFindByAC()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("333AA11");
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setCodigoAC("1233FF55");
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		Date data = new Date();

		HistoricoColaborador hc = HistoricoColaboradorFactory.getEntity();
		hc.setColaborador(colaborador);
		hc.setData(data);
		historicoColaboradorDao.save(hc);

		assertEquals(hc, historicoColaboradorDao.findByAC(data, colaborador.getCodigoAC(), empresa.getCodigoAC()));
	}

	public void testFindAtualByAC()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("333AA11");
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setCodigoAC("1233FF55");
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador hcAntigo = HistoricoColaboradorFactory.getEntity();
		hcAntigo.setColaborador(colaborador);
		hcAntigo.setData(DateUtil.criarDataMesAno(01, 01, 1998));
		historicoColaboradorDao.save(hcAntigo);

		HistoricoColaborador hcAtual = HistoricoColaboradorFactory.getEntity();
		hcAtual.setColaborador(colaborador);
		hcAtual.setData(DateUtil.criarDataMesAno(01, 01, 2000));
		historicoColaboradorDao.save(hcAtual);

		TSituacao situacao = new TSituacao();
		situacao.setData("01/02/2000");
		situacao.setEmpregadoCodigoAC(colaborador.getCodigoAC());
		situacao.setEmpresaCodigoAC(empresa.getCodigoAC());

		assertEquals(hcAtual, historicoColaboradorDao.findAtualByAC(situacao.getDataFormatada(), situacao.getEmpregadoCodigoAC(), situacao.getEmpresaCodigoAC()));
	}

	public void testFindColaboradorCodigoAC()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setCodigoAC("12345");
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		assertEquals("12345", historicoColaboradorDao.findColaboradorCodigoAC(historicoColaborador.getId()));
		assertNull(historicoColaboradorDao.findColaboradorCodigoAC(21546532L));
	}

	public void testFindByColaboradorData()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		assertEquals(0, historicoColaboradorDao.findByColaboradorData(colaborador.getId(), DateUtil.criarDataMesAno(01, 01, 2007)).size());
		assertEquals(1, historicoColaboradorDao.findByColaboradorData(colaborador.getId(), DateUtil.criarDataMesAno(01, 01, 2009)).size());
	}

	public void testFindReajusteByHistoricoColaborador()
	{
		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador();
		reajusteColaborador = reajusteColaboradorDao.save(reajusteColaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setReajusteColaborador(reajusteColaborador);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		assertEquals(reajusteColaborador.getId(), historicoColaboradorDao.findReajusteByHistoricoColaborador(historicoColaborador.getId()));
	}
	
	public void testFindColaboradoresByTabelaReajusteData()
	{
		Colaborador colaboradorNaTabela = ColaboradorFactory.getEntity();
		colaboradorNaTabela.setNome("colaboradorNaTabela");
		colaboradorDao.save(colaboradorNaTabela);
		
		Colaborador colaboradorForaDaTabela = ColaboradorFactory.getEntity();
		colaboradorForaDaTabela.setNome("colaboradorForaDaTabela");
		colaboradorDao.save(colaboradorForaDaTabela);
		
		TabelaReajusteColaborador tabelaReajusteColaborador1 = new TabelaReajusteColaborador();
		tabelaReajusteColaborador1.setData(DateUtil.criarDataMesAno(01, 02, 2000));
		tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador1);
		
		TabelaReajusteColaborador tabelaReajusteColaborador2 = new TabelaReajusteColaborador();
		tabelaReajusteColaborador2.setData(DateUtil.criarDataMesAno(01, 02, 2000));
		tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador2);
		
		ReajusteColaborador reajusteColaborador1 = ReajusteColaboradorFactory.getReajusteColaborador();
		reajusteColaborador1.setColaborador(colaboradorNaTabela);
		reajusteColaborador1.setTabelaReajusteColaborador(tabelaReajusteColaborador1);
		reajusteColaboradorDao.save(reajusteColaborador1);

		ReajusteColaborador reajusteColaborador2 = ReajusteColaboradorFactory.getReajusteColaborador();
		reajusteColaborador2.setTabelaReajusteColaborador(tabelaReajusteColaborador2);
		reajusteColaboradorDao.save(reajusteColaborador2);
		
		HistoricoColaborador historicoColaboradorDaTabela = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorDaTabela.setColaborador(colaboradorNaTabela);
		historicoColaboradorDaTabela.setData(DateUtil.criarDataMesAno(01, 02, 2000));
		historicoColaboradorDao.save(historicoColaboradorDaTabela);
		
		HistoricoColaborador historicoColaboradorForaDaTabela = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorForaDaTabela.setReajusteColaborador(reajusteColaborador2);
		historicoColaboradorForaDaTabela.setColaborador(colaboradorForaDaTabela);
		historicoColaboradorForaDaTabela.setData(DateUtil.criarDataMesAno(01, 02, 2000));
		historicoColaboradorDao.save(historicoColaboradorForaDaTabela);
		
		Collection<HistoricoColaborador> retorno = historicoColaboradorDao.findColaboradoresByTabelaReajusteData(tabelaReajusteColaborador1.getId(), tabelaReajusteColaborador1.getData());
		assertEquals(1, retorno.size());
		assertEquals("colaboradorNaTabela", ((HistoricoColaborador)retorno.toArray()[0]).getColaborador().getNome());
	}

	public void testFindHistoricosByTabelaReajuste()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		TabelaReajusteColaborador tabelaReajusteColaborador = new TabelaReajusteColaborador();
		tabelaReajusteColaborador = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);

		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador();
		reajusteColaborador.setTabelaReajusteColaborador(tabelaReajusteColaborador);
		reajusteColaborador = reajusteColaboradorDao.save(reajusteColaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setReajusteColaborador(reajusteColaborador);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);
		historicoColaborador.setColaborador(colaborador);

		assertEquals(1, historicoColaboradorDao.findHistoricosByTabelaReajuste(tabelaReajusteColaborador.getId()).size());
	}

	public void testFindHistoricosByTabelaReajusteColaboradorNaoIntegrado()
	{
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setNaoIntegraAc(false);
		colaborador1 = colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setNaoIntegraAc(true);
		colaborador2 = colaboradorDao.save(colaborador2);

		TabelaReajusteColaborador tabelaReajusteColaborador = new TabelaReajusteColaborador();
		tabelaReajusteColaborador = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);

		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador();
		reajusteColaborador.setTabelaReajusteColaborador(tabelaReajusteColaborador);
		reajusteColaborador = reajusteColaboradorDao.save(reajusteColaborador);

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setReajusteColaborador(reajusteColaborador);
		historicoColaborador1 = historicoColaboradorDao.save(historicoColaborador1);
		historicoColaborador1.setColaborador(colaborador1);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setReajusteColaborador(reajusteColaborador);
		historicoColaborador2 = historicoColaboradorDao.save(historicoColaborador2);
		historicoColaborador2.setColaborador(colaborador2);

		assertEquals(2, historicoColaboradorDao.findHistoricosByTabelaReajuste(tabelaReajusteColaborador.getId()).size());
	}

	public void testUpdateHistoricoAnterior()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setColaborador(colaborador);
		historicoColaborador1.setData(DateUtil.criarDataMesAno(01, 02, 2009));
		historicoColaborador1 = historicoColaboradorDao.save(historicoColaborador1);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setHistoricoAnterior(historicoColaborador1);
		historicoColaborador2.setData(DateUtil.criarDataMesAno(02, 02, 2009));
		historicoColaborador2.setColaborador(colaborador);
		historicoColaborador2 = historicoColaboradorDao.save(historicoColaborador2);

		HistoricoColaborador historicoColaborador3 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador3.setHistoricoAnterior(historicoColaborador2);
		historicoColaborador3.setData(DateUtil.criarDataMesAno(03, 02, 2009));
		historicoColaborador3.setColaborador(colaborador);
		historicoColaborador3 = historicoColaboradorDao.save(historicoColaborador3);

		historicoColaboradorDao.updateHistoricoAnterior(historicoColaborador2.getId());

		HistoricoColaborador resultado = historicoColaboradorDao.findByIdProjection(historicoColaborador3.getId());

		assertEquals(historicoColaborador1.getId(), resultado.getHistoricoAnterior().getId());
	}

	public void testUpdateHistoricoAnteriorSemHistoricoAnterior()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setData(DateUtil.criarDataMesAno(03, 02, 2009));
		historicoColaborador1.setColaborador(colaborador);
		historicoColaborador1 = historicoColaboradorDao.save(historicoColaborador1);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setHistoricoAnterior(historicoColaborador1);
		historicoColaborador2.setData(DateUtil.criarDataMesAno(04, 02, 2009));
		historicoColaborador2.setColaborador(colaborador);
		historicoColaborador2 = historicoColaboradorDao.save(historicoColaborador2);

		historicoColaboradorDao.updateHistoricoAnterior(historicoColaborador1.getId());

		HistoricoColaborador resultado = historicoColaboradorDao.findByIdProjection(historicoColaborador2.getId());

		assertEquals(null, resultado.getHistoricoAnterior().getId());
	}

	public void testFindPendenciasByHistoricoColaborador()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setDataAdmissao(DateUtil.criarDataMesAno(20,2,2010));
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		Collection<HistoricoColaborador> historicoColaboradors = historicoColaboradorDao.findPendenciasByHistoricoColaborador(empresa.getId());
		
		assertEquals(1, historicoColaboradors.size());
		
		HistoricoColaborador resultado1 = (HistoricoColaborador) historicoColaboradors.toArray()[0];
		
		assertEquals(colaborador.getDataAdmissao(), resultado1.getColaborador().getDataAdmissao());
	}
	
	public void testFindByCargoEstabelecimento()
	{
		// dado uma empresa
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setAcIntegra(true);
		empresaDao.save(empresa);
		
		// dado um estabelecimento
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		// dado um cargo
		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("Desenvolvedor");
		cargo.setEmpresa(empresa);
		cargoDao.save(cargo);
		
		Indice indice = IndiceFactory.getEntity();
		indice.setNome("Indice");
		indiceDao.save(indice);
		
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity();
		indiceHistorico.setIndice(indice);
		indiceHistorico.setData(DateUtil.criarDataMesAno(1, 2, 2010));
		indiceHistorico.setValor(12.00);
		indiceHistoricoDao.save(indiceHistorico);
		
		// dado uma faixa com seu historico
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setNome("Junior");
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);

		FaixaSalarial faixaSalarialColab = FaixaSalarialFactory.getEntity();
		faixaSalarialColab.setNome("Tadeu");
		faixaSalarialColab.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarialColab);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarialColab);
		faixaSalarialHistorico.setData(DateUtil.criarDataMesAno(1, 2, 2010));
		faixaSalarialHistorico.setValor(875.99);
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setStatus(StatusRetornoAC.CONFIRMADO);
		faixaSalarialHistoricoDao.save(faixaSalarialHistorico);
		
		// dado uma area organizacional
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		// dado um colaborador
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Maria");
		colaborador.setEmpresa(empresa);
		colaborador.setDataAtualizacao(DateUtil.criarDataMesAno(1, 6, 2010));
		colaborador.setDesligado(false);
		colaboradorDao.save(colaborador);

		Colaborador colaboradorFaixaSalarial = ColaboradorFactory.getEntity();
		colaboradorFaixaSalarial.setNome("colaboradorFaixaSalarial");
		colaboradorFaixaSalarial.setEmpresa(empresa);
		colaboradorFaixaSalarial.setDataAtualizacao(DateUtil.criarDataMesAno(1, 6, 2010));
		colaboradorFaixaSalarial.setDesligado(false);
		colaboradorDao.save(colaboradorFaixaSalarial);

		Colaborador colaboradorIndice = ColaboradorFactory.getEntity();
		colaboradorIndice.setNome("colaboradorIndice");
		colaboradorIndice.setEmpresa(empresa);
		colaboradorIndice.setDataAtualizacao(DateUtil.criarDataMesAno(1, 6, 2010));
		colaboradorIndice.setDesligado(false);
		colaboradorDao.save(colaboradorIndice);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setData(DateUtil.criarDataMesAno(1, 2, 2010));
		historicoColaborador1.setColaborador(colaborador);
		historicoColaborador1.setFaixaSalarial(faixaSalarial);
		historicoColaborador1.setEstabelecimento(estabelecimento);
		historicoColaborador1.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador1.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaborador1.setSalario(1000.00);
		historicoColaboradorDao.save(historicoColaborador1);

		HistoricoColaborador historicoColaboradorFaixasalarial = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorFaixasalarial.setData(DateUtil.criarDataMesAno(1, 2, 2010));
		historicoColaboradorFaixasalarial.setTipoSalario(TipoAplicacaoIndice.CARGO);
		historicoColaboradorFaixasalarial.setColaborador(colaboradorFaixaSalarial);
		historicoColaboradorFaixasalarial.setFaixaSalarial(faixaSalarialColab);
		historicoColaboradorFaixasalarial.setEstabelecimento(estabelecimento);
		historicoColaboradorFaixasalarial.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorFaixasalarial.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaboradorFaixasalarial);

		HistoricoColaborador historicoColaboradorIndice = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorIndice.setData(DateUtil.criarDataMesAno(1, 2, 2010));
		historicoColaboradorIndice.setTipoSalario(TipoAplicacaoIndice.INDICE);
		historicoColaboradorIndice.setColaborador(colaboradorIndice);
		historicoColaboradorIndice.setFaixaSalarial(faixaSalarial);
		historicoColaboradorIndice.setEstabelecimento(estabelecimento);
		historicoColaboradorIndice.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorIndice.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorIndice.setIndice(indice);
		historicoColaboradorIndice.setQuantidadeIndice(2.0);
		historicoColaboradorDao.save(historicoColaboradorIndice);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setData(DateUtil.criarDataMesAno(10, 2, 2010));//esse é o atual mas ta com status = AGUARDANDO
		historicoColaborador2.setColaborador(colaborador);
		historicoColaborador2.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaboradorDao.save(historicoColaborador2);
		
		// quando
		Long[] cargoIds = new Long[]{cargo.getId()};
		Long[] estabelecimentoIds = new Long[]{estabelecimento.getId()};
		Long[] areaOrganizacionalIds = new Long[]{areaOrganizacional.getId()};
		Date dataConsulta = new Date();
		
		Collection<HistoricoColaborador> historicoColaboradors = historicoColaboradorDao.findByCargoEstabelecimento(DateUtil.criarDataMesAno(20, 2, 2010), cargoIds, estabelecimentoIds,  dataConsulta, areaOrganizacionalIds, null, empresa.getId());		
		assertEquals(3, historicoColaboradors.size());
		HistoricoColaborador resultado1 = (HistoricoColaborador) historicoColaboradors.toArray()[0];
		assertEquals("Desenvolvedor Junior", resultado1.getFaixaSalarial().getDescricao());
		assertEquals("Maria", resultado1.getColaborador().getNome());
		assertEquals(1000.00, resultado1.getSalarioCalculado());

		HistoricoColaborador resultadoColabIndice = (HistoricoColaborador) historicoColaboradors.toArray()[1];
		assertEquals("colaboradorIndice", resultadoColabIndice.getColaborador().getNome());
		assertEquals(24.00, resultadoColabIndice.getSalarioCalculado());
		
		HistoricoColaborador resultadoColabFaixaSalarial = (HistoricoColaborador) historicoColaboradors.toArray()[2];
		assertEquals("colaboradorFaixaSalarial", resultadoColabFaixaSalarial.getColaborador().getNome());
		assertEquals(875.99, resultadoColabFaixaSalarial.getSalarioCalculado());

		assertTrue(historicoColaboradorDao.findByCargoEstabelecimento(DateUtil.criarDataMesAno(20, 2, 1900), null, null, dataConsulta, null, null, null).isEmpty());

		//desatualizados a partir de 01/07/2010 para tras
		Date dataAtualizacao = DateUtil.criarDataMesAno(1, 7, 2010);
		Collection<HistoricoColaborador> historicoColaboradorsDatAtaualizacao = historicoColaboradorDao.findByCargoEstabelecimento(DateUtil.criarDataMesAno(20, 2, 2010), cargoIds, estabelecimentoIds,  dataConsulta, areaOrganizacionalIds, dataAtualizacao, null);
		
		assertEquals(3, historicoColaboradorsDatAtaualizacao.size());
	}

	public void testFindHistoricoAprovado()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setColaborador(colaborador);
		historicoColaborador1.setStatus(StatusRetornoAC.AGUARDANDO);
		historicoColaborador1 = historicoColaboradorDao.save(historicoColaborador1);

		Collection<HistoricoColaborador> retorno = historicoColaboradorDao.findHistoricoAprovado(historicoColaborador1.getId(), colaborador.getId());

		assertEquals(0, retorno.size());
	}
	
	public void testFindByData()
	{
		Date hoje = new Date();
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setColaborador(colaborador);
		historicoColaborador1.setData(hoje);
		historicoColaboradorDao.save(historicoColaborador1);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setColaborador(colaborador);
		historicoColaborador2.setData(DateUtil.criarDataMesAno(01, 01, 2000));
		historicoColaboradorDao.save(historicoColaborador2);
		
		Collection<HistoricoColaborador> retorno = historicoColaboradorDao.findByData(colaborador.getId(), hoje);
		
		assertEquals(1, retorno.size());
	}

	public void testRemoveColaborador()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaboradorDao.save(historicoColaborador);

		historicoColaboradorDao.removeColaborador(colaborador.getId());
		assertTrue(historicoColaboradorDao.findByColaboradorProjection(colaborador.getId()).isEmpty());
	}
	
	public void testFindHistoricoAdmissao()
	{
		Date dataAdmissao = DateUtil.criarDataMesAno(18, 06, 2007);
		Date dataPrimeiroHistorico = DateUtil.criarDataMesAno(18, 06, 2009);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setDataAdmissao(dataAdmissao);
		colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(dataPrimeiroHistorico);
		historicoColaboradorDao.save(historicoColaborador);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setColaborador(colaborador);
		historicoColaborador2.setData(new Date());
		historicoColaboradorDao.save(historicoColaborador2);
		
		HistoricoColaborador resultado = historicoColaboradorDao.findHistoricoAdmissao(colaborador.getId());
		
		assertTrue(DateUtil.equals(dataAdmissao, resultado.getColaborador().getDataAdmissao()));
		assertTrue(DateUtil.equals(dataPrimeiroHistorico, resultado.getData()));
	}
	
	public void testFindByPeriodo()
	{
		Date dataIni = DateUtil.criarDataMesAno(01, 06, 2009);
		Date dataFim = DateUtil.criarDataMesAno(01, 07, 2009);
		Date dataHistorico = DateUtil.criarDataMesAno(18, 06, 2009);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento = estabelecimentoDao.save(estabelecimento); 
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(dataHistorico);
		historicoColaborador.setMotivo(MotivoHistoricoColaborador.IMPORTADO);
		historicoColaboradorDao.save(historicoColaborador);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setEstabelecimento(estabelecimento);
		historicoColaborador2.setColaborador(colaborador);
		historicoColaborador2.setData(dataIni);
		historicoColaborador2.setMotivo(MotivoHistoricoColaborador.CONTRATADO);
		historicoColaboradorDao.save(historicoColaborador2);
		
		Long[] estabelecimentosIds = new Long[]{estabelecimento.getId()};
		Long[] areasIds = new Long[0];
		String origemSituacao = "T"; // Qualquer origem (RH ou AC)
		
		Collection<HistoricoColaborador> resultado1 = historicoColaboradorDao.findByPeriodo(empresa.getId(), dataIni, dataFim, estabelecimentosIds, areasIds, origemSituacao);
		
		assertEquals(2, resultado1.size());
		
		origemSituacao = "AC"; // origem  AC
		Collection<HistoricoColaborador> resultado2 = historicoColaboradorDao.findByPeriodo(empresa.getId(), dataIni, dataFim, estabelecimentosIds, areasIds, origemSituacao);
		assertEquals(1, resultado2.size());
	}
	
	public void testFindAllByColaboradorBuscandoAmbienteEFuncao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambienteDao.save(ambiente);
		
		Funcao funcao = FuncaoFactory.getEntity();
		funcaoDao.save(funcao);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 5, 2003));
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(historicoColaborador);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setColaborador(colaborador);
		historicoColaborador2.setAmbiente(ambiente);
		historicoColaborador2.setData(DateUtil.criarDataMesAno(10, 8, 2005));
		historicoColaborador2.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(historicoColaborador2);
		
		HistoricoColaborador historicoColaborador3 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador3.setColaborador(colaborador);
		historicoColaborador3.setFuncao(funcao);
		historicoColaborador3.setAmbiente(ambiente);
		historicoColaborador3.setData(DateUtil.criarDataMesAno(22, 3, 2010));
		historicoColaborador3.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(historicoColaborador3);
		
		Collection<HistoricoColaborador> historicoColaboradors = historicoColaboradorDao.findAllByColaborador(colaborador.getId());
		assertEquals(3, historicoColaboradors.size());
	}
	
	public void testUpdateAmbienteEFuncao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(new Date());
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(historicoColaborador);
		
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambienteDao.save(ambiente);
		
		Funcao funcao = FuncaoFactory.getEntity();
		funcaoDao.save(funcao);
		
		historicoColaborador.setAmbiente(ambiente);
		historicoColaborador.setFuncao(funcao);
		
		assertTrue(historicoColaboradorDao.updateAmbienteEFuncao(historicoColaborador));
	}
	
	public void testFindHistoricoAdmitidos()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		
		Date hoje = new Date();
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaborador.setData(hoje);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(historicoColaborador);
		
		assertEquals(1, historicoColaboradorDao.findHistoricoAdmitidos(empresa.getId(), hoje).size());
	}

	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao)
	{
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
	{
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao)
	{
		this.estabelecimentoDao = estabelecimentoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setCargoDao(CargoDao cargoDao)
	{
		this.cargoDao = cargoDao;
	}

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao)
	{
		this.faixaSalarialDao = faixaSalarialDao;
	}

	public void setGrupoOcupacionalDao(GrupoOcupacionalDao grupoOcupacionalDao)
	{
		this.grupoOcupacionalDao = grupoOcupacionalDao;
	}

	public void setReajusteColaboradorDao(ReajusteColaboradorDao reajusteColaboradorDao)
	{
		this.reajusteColaboradorDao = reajusteColaboradorDao;
	}

	public void setTabelaReajusteColaboradorDao(TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao)
	{
		this.tabelaReajusteColaboradorDao = tabelaReajusteColaboradorDao;
	}

	public void setAmbienteDao(AmbienteDao ambienteDao) {
		this.ambienteDao = ambienteDao;
	}

	public void setFuncaoDao(FuncaoDao funcaoDao) {
		this.funcaoDao = funcaoDao;
	}

	public void setFaixaSalarialHistoricoDao(FaixaSalarialHistoricoDao faixaSalarialHistoricoDao) {
		this.faixaSalarialHistoricoDao = faixaSalarialHistoricoDao;
	}

	public void setIndiceDao(IndiceDao indiceDao) {
		this.indiceDao = indiceDao;
	}

	public void setIndiceHistoricoDao(IndiceHistoricoDao indiceHistoricoDao) {
		this.indiceHistoricoDao = indiceHistoricoDao;
	}
}