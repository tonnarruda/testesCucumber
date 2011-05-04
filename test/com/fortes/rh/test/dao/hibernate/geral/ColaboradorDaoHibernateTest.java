package com.fortes.rh.test.dao.hibernate.geral;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.PerfilDao;
import com.fortes.rh.dao.acesso.UsuarioDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDao;
import com.fortes.rh.dao.avaliacao.AvaliacaoDesempenhoDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialHistoricoDao;
import com.fortes.rh.dao.cargosalario.GrupoOcupacionalDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.cargosalario.ReajusteColaboradorDao;
import com.fortes.rh.dao.cargosalario.TabelaReajusteColaboradorDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.BairroDao;
import com.fortes.rh.dao.geral.CamposExtrasDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.dao.geral.MotivoDemissaoDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Habilitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.Deficiencia;
import com.fortes.rh.model.dicionario.EstadoCivil;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.TipoBuscaHistoricoColaborador;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.geral.MotivoDemissao;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.geral.SocioEconomica;
import com.fortes.rh.model.geral.relatorio.TurnOver;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
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
import com.fortes.rh.test.factory.cargosalario.ReajusteColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.CamposExtrasFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.util.DateUtil;

public class ColaboradorDaoHibernateTest extends GenericDaoHibernateTest<Colaborador>
{
	private final int AREA_ORGANIZACIONAL = 1;
	private final int CARGO = 3;
	private final int NO_MES = 0;
	private final int ADMITIDOS = 1;
	private final int DEMITIDOS = 2;

	private ColaboradorDao colaboradorDao;
	private TurmaDao turmaDao;
	private ColaboradorTurmaDao colaboradorTurmaDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private EmpresaDao empresaDao;
	private UsuarioDao usuarioDao;
	private CandidatoDao candidatoDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private AvaliacaoDao avaliacaoDao;
	private EstabelecimentoDao estabelecimentoDao;
	private FaixaSalarialDao faixaSalarialDao;
	private FaixaSalarialHistoricoDao faixaSalarialHistoricoDao;
	private CargoDao cargoDao;
	private GrupoOcupacionalDao grupoOcupacionalDao;
	private MotivoDemissaoDao motivoDemissaoDao;
	private TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao;
	private ReajusteColaboradorDao reajusteColaboradorDao;
	private AmbienteDao ambienteDao;
	private FuncaoDao funcaoDao;
	private BairroDao bairroDao;
	private PerfilDao perfilDao;
	private AvaliacaoDesempenhoDao avaliacaoDesempenhoDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
	private CamposExtrasDao camposExtrasDao;
	private GrupoACDao grupoACDao;

	private Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity();
	private Cargo cargo1 = CargoFactory.getEntity();
	private AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();

	public Colaborador getEntity()
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(null);
		colaborador.setNome("nome colaborador");
		colaborador.setNomeComercial("nome comercial");
		colaborador.setDesligado(false);
		colaborador.setDataDesligamento(new Date());
		colaborador.setObservacao("observação");
		colaborador.setDataAdmissao(new Date());
		colaborador.setMatricula("00001");

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

		return colaborador;
	}

	@Override
	public void testFindById() throws Exception
	{
		Colaborador colaborador = getEntity();
		colaborador = colaboradorDao.save(colaborador);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico.setData(new Date());
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricos.add(faixaSalarialHistorico);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setSalario(1000D);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		historicoColaborador.setMotivo("m");
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		Colaborador colaboradorRetorno = colaboradorDao.findById(colaborador.getId());

		assertNotNull(colaborador);
		assertEquals(colaboradorRetorno.getId(), colaborador.getId());
	}

	@Override
	public void testUpdate() throws Exception
	{
		Colaborador colaborador = getEntity();
		colaborador.setNome("nome");
		colaborador = colaboradorDao.save(colaborador);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico.setData(new Date());
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricos.add(faixaSalarialHistorico);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setSalario(1000D);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		historicoColaborador.setMotivo("m");
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		colaborador.setNome("outro nome");

		Colaborador colaboradorRetorno = colaboradorDao.findById(colaborador.getId());

		assertEquals(colaborador.getNome(), colaboradorRetorno.getNome());
	}

	public void testUpdateDataDesligamentoByCodigo() throws Exception
	{
		Empresa empresa = new Empresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = getEntity();
		colaborador.setCodigoAC("010203");
		colaborador.setEmpresa(empresa);

		colaboradorDao.save(colaborador);

		assertTrue(colaboradorDao.updateDataDesligamentoByCodigo("010203", empresa, new Date()));
		
		Colaborador colDesligado = colaboradorDao.findByCodigoAC("010203", empresa);
		assertNotNull(colDesligado.getDataDesligamento());
		assertTrue(colDesligado.isDesligado());
		
		assertTrue(colaboradorDao.updateDataDesligamentoByCodigo("010203", empresa, null));
		
		Colaborador colReligado = colaboradorDao.findByCodigoAC("010203", empresa);
		assertNull(colReligado.getDataDesligamento());
		assertFalse(colReligado.isDesligado());
		
	}

	public void testUpdateUsuarioColaborador() throws Exception
	{
		Colaborador colaborador = getEntity();
		colaborador.setNome("nome");
		colaborador = colaboradorDao.save(colaborador);

		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		colaborador.setUsuario(usuario);

		colaboradorDao.atualizarUsuario(colaborador.getId(), usuario.getId());
		Colaborador colaboradorRetorno = colaboradorDao.findByIdProjectionUsuario(colaborador.getId());
		assertEquals(usuario.getId(), colaboradorRetorno.getUsuario().getId());

		colaboradorDao.atualizarUsuario(colaborador.getId(), null);
		colaboradorRetorno = colaboradorDao.findByIdProjectionUsuario(colaborador.getId());
		assertNull(colaboradorRetorno.getUsuario().getId());
	}

	public void testFindByUsuarioProjection() throws Exception
	{		
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setNome("João Batista");
		usuarioDao.save(usuario);
	
		Colaborador colaborador = getEntity();
		colaborador.setNome("João");
		colaborador.setUsuario(usuario);
		colaboradorDao.save(colaborador);

		Colaborador retorno = colaboradorDao.findByUsuarioProjection(usuario.getId());
		assertEquals("João", retorno.getNome());
		assertEquals("João Batista", retorno.getUsuario().getNome());
	}
	
	@Override
	public void testRemove() throws Exception
	{
		Colaborador colaborador = getEntity();
		colaborador = colaboradorDao.save(colaborador);

		colaboradorDao.remove(colaborador);

		colaborador = getGenericDao().findById(colaborador.getId());

		assertNull(colaborador);
	}

	public void testFindSemUsuarios()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		Empresa empresa2 = new Empresa();
		empresa2.setNome("empresa");
		empresa2.setCnpj("21212121212");
		empresa2.setRazaoSocial("empresa");
		empresa2 = empresaDao.save(empresa2);

		Colaborador c1 = getColaborador();
		c1.setEmpresa(empresa);
		c1 = colaboradorDao.save(c1);

		Colaborador c2 = getColaborador();
		c2.setEmpresa(empresa);
		c2.setUsuario(usuario);
		c2 = colaboradorDao.save(c2);

		Colaborador c3 = getColaborador();
		c3.setEmpresa(empresa2);
		c3 = colaboradorDao.save(c3);

		Collection<Colaborador> colaboradores = colaboradorDao.findSemUsuarios(empresa.getId(), null);

		assertFalse(colaboradores.isEmpty());
		assertEquals(c1.getId(),((Colaborador)colaboradores.toArray()[0]).getId());
	}

	public void testFindSemUsuariosComUsuario()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		Empresa empresa2 = new Empresa();
		empresa2.setNome("empresa");
		empresa2.setCnpj("21212121212");
		empresa2.setRazaoSocial("empresa");
		empresa2 = empresaDao.save(empresa2);

		Colaborador c1 = getColaborador();
		c1.setEmpresa(empresa);
		c1 = colaboradorDao.save(c1);

		Colaborador c2 = getColaborador();
		c2.setEmpresa(empresa);
		c2.setUsuario(usuario);
		c2 = colaboradorDao.save(c2);

		Colaborador c3 = getColaborador();
		c3.setEmpresa(empresa2);
		c3 = colaboradorDao.save(c3);

		Collection<Colaborador> colaboradores = colaboradorDao.findSemUsuarios(empresa.getId(), usuario);

		assertEquals(2, colaboradores.size());
	}

//	@SuppressWarnings("unchecked")
//	public void testGetCountSQL()
//	{
//		Empresa empresa = new Empresa();
//		empresa.setNome("empresa");
//		empresa.setCnpj("21212121212");
//		empresa.setRazaoSocial("empresa");
//		empresaDao.save(empresa);
//		
//		insereAmbiente(empresa);
//		
//		Map parametros = new HashMap();
//		parametros.put("empresaId", 4L);
//		parametros.put("areaId", null);
//		
//		Collection<Object> colaboradors = colaboradorDao.findComHistoricoFuturoSQL(parametros, 5, 10);
//		assertEquals(27, colaboradors.size());
//		
//		parametros.put("nomeBusca", "");
//		parametros.put("cpfBusca", "1111");
//		
//		result = colaboradorDao.getCount(parametros, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
//		assertEquals(1, result);
//		
//		parametros.put("nomeBusca", "Chi");
//		parametros.put("cpfBusca", "1111");
//		
//		result = colaboradorDao.getCount(parametros, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
//		assertEquals(1, result);
//	
//	}

	public void testGetCount()
	{
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		insereAmbiente(empresa);

		Map parametros = new HashMap();
		parametros.put("nomeBusca", "chi");
		parametros.put("cpfBusca", "");
		parametros.put("empresaId", empresa.getId());
		parametros.put("areaId", null);

		int result = colaboradorDao.getCount(parametros, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
		assertEquals(2, result);

		parametros.put("nomeBusca", "");
		parametros.put("cpfBusca", "1111");

		result = colaboradorDao.getCount(parametros, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
		assertEquals(1, result);

		parametros.put("nomeBusca", "Chi");
		parametros.put("cpfBusca", "1111");

		result = colaboradorDao.getCount(parametros, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
		assertEquals(1, result);
	}

	@SuppressWarnings("unchecked")
	public void testFindList()
	{
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		insereAmbiente(empresa);

		Map parametros = new HashMap();
		parametros.put("nomeBusca", "chi");
		parametros.put("cpfBusca", "");
		parametros.put("empresaId", empresa.getId());
		parametros.put("areaId", null);

		Collection<Colaborador> colaboradores = colaboradorDao.findList(1, 10, parametros, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
		assertEquals("só chi",2, colaboradores.size());

		parametros.put("nomeBusca", "");
		parametros.put("cpfBusca", "1111");

		colaboradores = colaboradorDao.findList(1, 10, parametros, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
		assertEquals("só 1111",1, colaboradores.size());

		parametros.put("nomeBusca", "Chi");
		parametros.put("cpfBusca", "1111");

		colaboradores = colaboradorDao.findList(1, 10, parametros, TipoBuscaHistoricoColaborador.SEM_HISTORICO_FUTURO);
		assertEquals("os 2",1, colaboradores.size());

		colaboradores = colaboradorDao.findList(1, 10, parametros, TipoBuscaHistoricoColaborador.COM_HISTORICO_FUTURO);

		parametros.clear();

		assertEquals("Histórico futuro",1, colaboradores.size());
	}

	private void insereAmbiente(Empresa empresa)
	{
		Empresa empresa2 = new Empresa();
		empresa2.setNome("empresa");
		empresa2.setCnpj("21212121212");
		empresa2.setRazaoSocial("empresa");
		empresa2 = empresaDao.save(empresa2);

		AreaOrganizacional ao = AreaOrganizacionalFactory.getEntity();
		ao = areaOrganizacionalDao.save(ao);

		Colaborador c1 = getColaborador();
		c1.setEmpresa(empresa);
	    c1.setNome("Chico");
	    c1.setNomeComercial("Chico");
	    c1.getPessoal().setCpf("11111111111");
		c1 = colaboradorDao.save(c1);

		HistoricoColaborador hc1 = HistoricoColaboradorFactory.getEntity();
		hc1.setColaborador(c1);
		hc1.setAreaOrganizacional(ao);
		hc1.setSalario(1000D);
		hc1.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		hc1.setMotivo("m");
		hc1 = historicoColaboradorDao.save(hc1);

		Colaborador c2 = getColaborador();
		c2.setEmpresa(empresa);
	    c2.setNome("Chiquinho");
	    c2.setNomeComercial("Chiquinho");
	    c2.getPessoal().setCpf("22222222222");
		c2 = colaboradorDao.save(c2);

		HistoricoColaborador hc2 = HistoricoColaboradorFactory.getEntity();
		hc2.setColaborador(c2);
		hc2.setAreaOrganizacional(ao);
		hc2.setSalario(1000D);
		hc2.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		hc2.setMotivo("m");
		hc2 = historicoColaboradorDao.save(hc2);

		Colaborador c3 = getColaborador();
		c3.setEmpresa(empresa);
	    c3.setNome("Mazelento");
	    c3.setNomeComercial("Mazelento");
	    c3.getPessoal().setCpf("33333333333");
		c3 = colaboradorDao.save(c3);

		HistoricoColaborador hc3 = HistoricoColaboradorFactory.getEntity();
		hc3.setColaborador(c3);
		hc3.setAreaOrganizacional(ao);
		hc3.setSalario(1000D);
		hc3.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		hc3.setMotivo("m");
		hc3 = historicoColaboradorDao.save(hc3);

		Colaborador c4 = getColaborador();
		c4.setEmpresa(empresa2);
	    c4.setNome("Chiquitita");
	    c4.setNomeComercial("Chiquitita");
	    c4.getPessoal().setCpf("11111111111");
		c4 = colaboradorDao.save(c4);

		HistoricoColaborador hc4 = HistoricoColaboradorFactory.getEntity();
		hc4.setColaborador(c4);
		hc4.setAreaOrganizacional(ao);
		hc4.setSalario(1000D);
		hc4.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		hc4.setMotivo("m");
		hc4 = historicoColaboradorDao.save(hc4);

		Colaborador c5 = getColaborador();
		c5.setEmpresa(empresa2);
		c5.setNome("Robertita");
		c5.setNomeComercial("Robertita");
		c5.getPessoal().setCpf("11111111111");
		c5 = colaboradorDao.save(c5);

		HistoricoColaborador hc5 = HistoricoColaboradorFactory.getEntity();
		hc5.setColaborador(c5);
		hc5.setAreaOrganizacional(ao);
		hc5.setSalario(1000D);
		hc5.setData(DateUtil.criarDataMesAno(01, 01, 3000));
		hc5.setMotivo("m");
		hc5 = historicoColaboradorDao.save(hc5);

	}

	public void testFindColaboradorPesquisa()
	{
		AreaOrganizacional area1 = new AreaOrganizacional();
		area1.setNome("area 1");
		area1 = areaOrganizacionalDao.save(area1);

		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		Empresa empresa2 = new Empresa();
		empresa2.setNome("empresa");
		empresa2.setCnpj("21212121212");
		empresa2.setRazaoSocial("empresa");
		empresa2 = empresaDao.save(empresa2);

		Colaborador c1 = getColaborador();
		c1.setEmpresa(empresa);
	    c1.setNome("Chico");
	    c1.setNomeComercial("Chico");
	    c1.getPessoal().setCpf("11111111111");
		c1 = colaboradorDao.save(c1);

		HistoricoColaborador hc1 = HistoricoColaboradorFactory.getEntity();
		hc1.setColaborador(c1);
		hc1.setAreaOrganizacional(area1);
		hc1.setSalario(1000D);
		hc1.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		hc1.setMotivo("m");
		hc1 = historicoColaboradorDao.save(hc1);

		Colaborador c2 = getColaborador();
		c2.setEmpresa(empresa2);
	    c2.setNome("Chiquinho");
	    c2.setNomeComercial("Chiquinho");
	    c2.getPessoal().setCpf("22222222222");
		c2 = colaboradorDao.save(c2);

		HistoricoColaborador hc2 = HistoricoColaboradorFactory.getEntity();
		hc2.setColaborador(c2);
		hc2.setAreaOrganizacional(area1);
		hc2.setSalario(1000D);
		hc2.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		hc2.setMotivo("m");
		hc2 = historicoColaboradorDao.save(hc2);

		Colaborador colaborador = colaboradorDao.findColaboradorPesquisa(c1.getId(), empresa.getId());

		assertEquals("equals",c1.getId(), colaborador.getId());

		colaborador = colaboradorDao.findColaboradorPesquisa(c1.getId(), empresa2.getId());

		assertNull("null",colaborador.getId());
	}

	public void testFindByUsuario()
	{
		AreaOrganizacional area1 = new AreaOrganizacional();
		area1.setNome("area 1");
		area1 = areaOrganizacionalDao.save(area1);

		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		Empresa empresa2 = new Empresa();
		empresa2.setNome("empresa");
		empresa2.setCnpj("21212121212");
		empresa2.setRazaoSocial("empresa");
		empresa2 = empresaDao.save(empresa2);

		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		Usuario usuario1 = UsuarioFactory.getEntity();
		usuario1 = usuarioDao.save(usuario1);

		Colaborador c1 = getColaborador();
		c1.setEmpresa(empresa);
	    c1.setNome("Chico");
	    c1.setNomeComercial("Chico");
	    c1.getPessoal().setCpf("11111111111");
	    c1.setUsuario(usuario);
		c1 = colaboradorDao.save(c1);

		HistoricoColaborador hc1 = HistoricoColaboradorFactory.getEntity();
		hc1.setColaborador(c1);
		hc1.setAreaOrganizacional(area1);
		hc1.setSalario(1000D);
		hc1.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		hc1.setMotivo("m");
		hc1 = historicoColaboradorDao.save(hc1);

		Colaborador c2 = getColaborador();
		c2.setEmpresa(empresa2);
	    c2.setNome("Chiquinho");
	    c2.setNomeComercial("Chiquinho");
	    c2.getPessoal().setCpf("22222222222");
	    c2.setUsuario(usuario1);
		c2 = colaboradorDao.save(c2);

		HistoricoColaborador hc2 = HistoricoColaboradorFactory.getEntity();
		hc2.setColaborador(c2);
		hc2.setAreaOrganizacional(area1);
		hc2.setSalario(1000D);
		hc2.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		hc2.setMotivo("m");
		hc2 = historicoColaboradorDao.save(hc2);

		Colaborador colaborador = colaboradorDao.findByUsuario(usuario, empresa.getId());
		assertEquals("equals", c1.getId(), colaborador.getId());

		colaborador = colaboradorDao.findByUsuario(usuario, empresa2.getId());
		assertNull("null", colaborador.getId());
	}

	public void testFindColaboradorUsuarioByCpf()
	{

		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		Empresa empresa2 = new Empresa();
		empresa2.setNome("empresa");
		empresa2.setCnpj("21212121212");
		empresa2.setRazaoSocial("empresa");
		empresa2 = empresaDao.save(empresa2);

		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setAcessoSistema(true);
		usuario = usuarioDao.save(usuario);

		Usuario usuario1 = new Usuario();
		usuario1.setLogin("usuario 1");
		usuario1 = usuarioDao.save(usuario1);

		Usuario usuario2 = new Usuario();
		usuario2.setLogin("usuario 2");
		usuario2.setAcessoSistema(true);
		usuario2 = usuarioDao.save(usuario2);

		Colaborador c1 = getColaborador();
		c1.setEmpresa(empresa);
		c1.setUsuario(usuario);
		c1.getPessoal().setCpf("11111111111");

		Colaborador c2 = getColaborador();
		c2.setEmpresa(empresa);
		c2.setUsuario(usuario1);
		c2.getPessoal().setCpf("11111111112");

		Colaborador c3 = getColaborador();
		c3.setEmpresa(empresa2);
		c3.setUsuario(usuario2);
		c3.getPessoal().setCpf("11111111111");


		c1 = colaboradorDao.save(c1);
		c2 = colaboradorDao.save(c2);
		c3 = colaboradorDao.save(c3);

		Colaborador colaborador = colaboradorDao.findColaboradorUsuarioByCpf("11111111111", empresa2.getId());
		assertEquals("equals", c3.getId(), colaborador.getId());

		colaborador = colaboradorDao.findColaboradorUsuarioByCpf("11111111111", empresa.getId());
		assertEquals("equals2", c1.getId(), colaborador.getId());

	}

	public void testeFindbyCandidato()
	{
		Empresa empresa = new Empresa();
		empresa.setNome("empresa1");
		empresa.setCnpj("1111212215225");
		empresa.setRazaoSocial("razaoSocial1");
		empresa = empresaDao.save(empresa);

		Candidato candidato = getCandidato();
		candidato.setEmpresa(empresa);
		candidato = candidatoDao.save(candidato);

		Candidato candidato2 = getCandidato();
		candidato2.setEmpresa(empresa);
		candidato2 = candidatoDao.save(candidato2);


		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador.setCandidato(candidato);
		colaborador = colaboradorDao.save(colaborador);

		Colaborador colaborador2 = getColaborador();
		colaborador2.setEmpresa(empresa);
		colaborador2.setCandidato(candidato2);
		colaborador2 = colaboradorDao.save(colaborador2);

		Collection<Colaborador> colaboradors = colaboradorDao.findbyCandidato(candidato.getId(),empresa.getId());

		assertEquals(colaborador.getId(), ((Colaborador) colaboradors.toArray()[0]).getId());
	}
	
	public void testSetCandidatoNull()
	{
		Empresa empresa = new Empresa();
		empresa.setNome("empresa1");
		empresaDao.save(empresa);
		
		Candidato candidato = getCandidato();
		candidato.setEmpresa(empresa);
		candidatoDao.save(candidato);
		
		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador.setCandidato(candidato);
		colaboradorDao.save(colaborador);
		
		colaboradorDao.setCandidatoNull(candidato.getId());
		
		assertEquals(0, colaboradorDao.findbyCandidato(candidato.getId(),empresa.getId()).size());
	}

	private Candidato getCandidato()
	{
		Candidato candidato = new Candidato();
		candidato.setId(null);
		candidato.setAreasInteresse(null);
		candidato.setColocacao('1');
		candidato.setConhecimentos(null);

		Contato contato = new Contato();
		contato.setEmail("mail@mail.com");
		contato.setFoneFixo("00000000");
		contato.setFoneCelular("00000000");
		candidato.setContato(contato);

		candidato.setContratado(false);
		candidato.setCursos(null);
		candidato.setDisponivel(true);

		Endereco endereco = new Endereco();
		endereco.setLogradouro("logradouro");
		endereco.setNumero("00");
		endereco.setComplemento("complemento");
		endereco.setBairro("bairro");
		endereco.setCidade(null);
		endereco.setUf(null);
		endereco.setCep("0000000");
		candidato.setEndereco(endereco);

		candidato.setExperiencias(null);
		candidato.setFormacao(null);
		candidato.setNome("colaborador teste");
		candidato.setObservacao("obs");

		Pessoal pessoal	= new Pessoal();
		pessoal.setDataNascimento(new Date());
		pessoal.setEstadoCivil("e");
		pessoal.setEscolaridade("e");
		pessoal.setSexo('m');
		pessoal.setConjugeTrabalha(false);
		pessoal.setCpf("00000000000");
		pessoal.setConjuge("Maria");
		pessoal.setNaturalidade("Palmácia");
		pessoal.setMae("Joana");
		pessoal.setPai("Roberto");
		pessoal.setQtdFilhos(0);
		candidato.setPessoal(pessoal);

		SocioEconomica socioEconomica = new SocioEconomica();
		socioEconomica.setPagaPensao(true);
		socioEconomica.setPossuiVeiculo(true);
		socioEconomica.setQuantidade(10);
		socioEconomica.setValor(1500D);
		candidato.setSocioEconomica(socioEconomica);

		Habilitacao habilitacao = new Habilitacao();
		habilitacao.setCategoria("ABC");
		habilitacao.setEmissao(null);
		habilitacao.setVencimento(null);
		habilitacao.setRegistro("607583");
		habilitacao.setNumeroHab("123324235");
		candidato.setHabilitacao(habilitacao);

		candidato.setPretencaoSalarial(1500.00);

		return candidato;
	}

	private Colaborador getColaborador()
	{
		Colaborador colaborador = new Colaborador();
		colaborador.setId(null);
		colaborador.setNome("nome colaborador");
		colaborador.setNomeComercial("nome comercial colaborador");
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
		pessoal.setCpf("000000");
		colaborador.setPessoal(pessoal);

		colaborador.setDependentes(null);
		return colaborador;
	}
	
	public void testCountSexo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('M', false, DateUtil.criarDataMesAno(02, 02, 2010), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, null);//data superior
		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('F', false, DateUtil.criarDataMesAno(04, 04, 2000), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('F', true, DateUtil.criarDataMesAno(01, 02, 2005), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, null);//desligado
		saveColaborador('F', false, DateUtil.criarDataMesAno(02, 03, 2005), outraEmpresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, null);//outra empresa
		
		Collection<DataGrafico> data = colaboradorDao.countSexo(DateUtil.criarDataMesAno(01, 02, 2008), empresa.getId());
		assertEquals(2, data.size());
		
		DataGrafico fem = (DataGrafico) data.toArray()[0];
		DataGrafico mas = (DataGrafico) data.toArray()[1];
		
		assertEquals("Feminino", fem.getLabel());
		assertEquals(2.0, fem.getData());

		assertEquals("Masculino", mas.getLabel());
		assertEquals(1.0, mas.getData());
	}
	
	public void testCountVinculo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO);
		saveColaborador('M', false, DateUtil.criarDataMesAno(02, 02, 2010), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.EMPREGO);//data superior
		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.APRENDIZ);
		saveColaborador('F', false, DateUtil.criarDataMesAno(04, 04, 2000), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, Vinculo.ESTAGIO);
		saveColaborador('F', true, DateUtil.criarDataMesAno(01, 02, 2005), empresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, null);//desligado
		saveColaborador('F', false, DateUtil.criarDataMesAno(02, 03, 2005), outraEmpresa, "01", null, Deficiencia.SEM_DEFICIENCIA, null, null, null);//outra empresa
		
		Collection<DataGrafico> data = colaboradorDao.countColocacao(DateUtil.criarDataMesAno(01, 02, 2008), empresa.getId());
		assertEquals(3, data.size());
		
		DataGrafico empregado = (DataGrafico) data.toArray()[0];
		DataGrafico aprendiz = (DataGrafico) data.toArray()[1];
		
		assertEquals(2.0, empregado.getData());
		assertEquals(1.0, aprendiz.getData());
	}

	public void testCountEstadoCivil()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_PARCIAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_UNIVERSAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2004), empresa, EstadoCivil.CASADO_REGIME_MISTO_ESPECIAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2006), empresa, EstadoCivil.CASADO_REGIME_TOTAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.CASADO_SEPARACAO_DE_BENS, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('M', false, DateUtil.criarDataMesAno(02, 02, 2007), empresa, EstadoCivil.SOLTEIRO, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.UNIAO_ESTAVEL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('F', false, DateUtil.criarDataMesAno(04, 04, 2000), empresa, EstadoCivil.DIVORCIADO, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.SEPARADO_JUDIALMENTE, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('F', false, DateUtil.criarDataMesAno(02, 03, 2005), empresa, EstadoCivil.VIUVO, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		
		Collection<DataGrafico> data = colaboradorDao.countEstadoCivil(DateUtil.criarDataMesAno(01, 02, 2008), empresa.getId());
		assertEquals(4, data.size());
		
		DataGrafico casado= (DataGrafico) data.toArray()[0];
		DataGrafico divorciado = (DataGrafico) data.toArray()[1];
		DataGrafico solteiro = (DataGrafico) data.toArray()[2];
		DataGrafico viuvo = (DataGrafico) data.toArray()[3];
		
		assertEquals("Casado", casado.getLabel());
		assertEquals(5.0, casado.getData());
		
		assertEquals("Divorciado", divorciado.getLabel());
		assertEquals(2.0, divorciado.getData());

		assertEquals("Solteiro", solteiro.getLabel());
		assertEquals(2.0, solteiro.getData());
		
		assertEquals("Viúvo", viuvo.getLabel());
		assertEquals(1.0, viuvo.getData());
	}

	public void testCountDeficiencia()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_PARCIAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_UNIVERSAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2004), empresa, EstadoCivil.CASADO_REGIME_MISTO_ESPECIAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2006), empresa, EstadoCivil.CASADO_REGIME_TOTAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.CASADO_SEPARACAO_DE_BENS, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('M', false, DateUtil.criarDataMesAno(02, 02, 2007), empresa, EstadoCivil.SOLTEIRO, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.UNIAO_ESTAVEL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('F', false, DateUtil.criarDataMesAno(04, 04, 2000), empresa, EstadoCivil.DIVORCIADO, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.SEPARADO_JUDIALMENTE, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('F', false, DateUtil.criarDataMesAno(02, 03, 2005), empresa, EstadoCivil.VIUVO, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		
		Collection<DataGrafico> data = colaboradorDao.countEstadoCivil(DateUtil.criarDataMesAno(01, 02, 2008), empresa.getId());
		assertEquals(4, data.size());
		
		DataGrafico casado= (DataGrafico) data.toArray()[0];
		DataGrafico divorciado = (DataGrafico) data.toArray()[1];
		DataGrafico solteiro = (DataGrafico) data.toArray()[2];
		DataGrafico viuvo = (DataGrafico) data.toArray()[3];
		
		assertEquals("Casado", casado.getLabel());
		assertEquals(5.0, casado.getData());
		
		assertEquals("Divorciado", divorciado.getLabel());
		assertEquals(2.0, divorciado.getData());
		
		assertEquals("Solteiro", solteiro.getLabel());
		assertEquals(2.0, solteiro.getData());
		
		assertEquals("Viúvo", viuvo.getLabel());
		assertEquals(1.0, viuvo.getData());
	}
	
	public void testCountFaixaEtaria()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_PARCIAL, DateUtil.criarDataMesAno(31, 01, 1989), Deficiencia.SEM_DEFICIENCIA, null, null, null);
		
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_UNIVERSAL, DateUtil.criarDataMesAno(31, 01, 1988), Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2004), empresa, EstadoCivil.CASADO_REGIME_MISTO_ESPECIAL, DateUtil.criarDataMesAno(31, 01, 1986), Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('F', false, DateUtil.criarDataMesAno(02, 03, 2005), empresa, EstadoCivil.VIUVO, DateUtil.criarDataMesAno(23, 02, 1978), Deficiencia.SEM_DEFICIENCIA, null, null, null);
		
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2006), empresa, EstadoCivil.CASADO_REGIME_TOTAL, DateUtil.criarDataMesAno(18, 12, 1977), Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.CASADO_SEPARACAO_DE_BENS, DateUtil.criarDataMesAno(18, 12, 1975), Deficiencia.SEM_DEFICIENCIA, null, null, null);
		
		saveColaborador('M', false, DateUtil.criarDataMesAno(02, 02, 2007), empresa, EstadoCivil.SOLTEIRO, DateUtil.criarDataMesAno(01, 02, 1968), Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.UNIAO_ESTAVEL, DateUtil.criarDataMesAno(31, 12, 1960), Deficiencia.SEM_DEFICIENCIA, null, null, null);
		
		saveColaborador('F', false, DateUtil.criarDataMesAno(04, 04, 2000), empresa, EstadoCivil.DIVORCIADO, DateUtil.criarDataMesAno(31, 01, 1950), Deficiencia.SEM_DEFICIENCIA, null, null, null);
		
		saveColaborador('F', false, DateUtil.criarDataMesAno(01, 02, 2005), empresa, EstadoCivil.SEPARADO_JUDIALMENTE, DateUtil.criarDataMesAno(18, 12, 1947), Deficiencia.SEM_DEFICIENCIA, null, null, null);
		
		Collection<DataGrafico> data = colaboradorDao.countFaixaEtaria(DateUtil.criarDataMesAno(01, 02, 2008), empresa.getId());

		DataGrafico faixa1 = (DataGrafico) data.toArray()[0];
		DataGrafico faixa2 = (DataGrafico) data.toArray()[1];
		DataGrafico faixa3 = (DataGrafico) data.toArray()[2];
		DataGrafico faixa4 = (DataGrafico) data.toArray()[3];
		DataGrafico faixa5 = (DataGrafico) data.toArray()[4];
		DataGrafico faixa6 = (DataGrafico) data.toArray()[5];
		
		assertEquals("Até 19", faixa1.getLabel());
		assertEquals(1.0, faixa1.getData());
		
		assertEquals("20 a 29", faixa2.getLabel());
		assertEquals(3.0, faixa2.getData());

		assertEquals("30 a 39", faixa3.getLabel());
		assertEquals(2.0, faixa3.getData());
		
		assertEquals("40 a 49", faixa4.getLabel());
		assertEquals(2.0, faixa4.getData());
		
		assertEquals("50 a 59", faixa5.getLabel());
		assertEquals(1.0, faixa5.getData());
		
		assertEquals("Acima de 60", faixa6.getLabel());
		assertEquals(1.0, faixa6.getData());
	}
	
	public void testCountMotivoDemissao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		
		MotivoDemissao motivoFalta = new MotivoDemissao();
		motivoFalta.setMotivo("Falta");
		motivoDemissaoDao.save(motivoFalta);
		
		MotivoDemissao motivoPediuPraSair = new MotivoDemissao();
		motivoPediuPraSair.setMotivo("Pediu pra Sair");
		motivoDemissaoDao.save(motivoPediuPraSair);
		
		saveColaborador('F', true, null, empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2005), motivoFalta, null);
		saveColaborador('M', true, null, empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2006), motivoFalta, null);
		saveColaborador('M', true, null, empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2006), motivoPediuPraSair, null);
		
		saveColaborador('M', true, null, outraEmpresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2006), motivoPediuPraSair, null);
		saveColaborador('M', true, null, empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2003), motivoPediuPraSair, null);
		saveColaborador('M', true, null, empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2010), motivoPediuPraSair, null);
		
		Collection<DataGrafico> motivos = colaboradorDao.countMotivoDesligamento(DateUtil.criarDataMesAno(01, 02, 2004), DateUtil.criarDataMesAno(01, 02, 2009), empresa.getId(), 2);
		assertEquals(2, motivos.size());
		
		DataGrafico motivo1 = (DataGrafico) motivos.toArray()[0];
		DataGrafico motivo2 = (DataGrafico) motivos.toArray()[1];
		
		assertEquals("Falta", motivo1.getLabel());
		assertEquals(2.0, motivo1.getData());

		assertEquals("Pediu pra Sair", motivo2.getLabel());
		assertEquals(1.0, motivo2.getData());
	}
	
	public void testCountAdmitidos()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		saveColaborador('F', true, DateUtil.criarDataMesAno(02, 03, 2005), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('M', true, DateUtil.criarDataMesAno(02, 03, 2003), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('F', true, DateUtil.criarDataMesAno(02, 03, 2011), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('F', true, DateUtil.criarDataMesAno(01, 02, 2004), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('F', true, DateUtil.criarDataMesAno(01, 02, 2009), empresa, null, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		
		int count = colaboradorDao.countAdmitidos(DateUtil.criarDataMesAno(01, 02, 2004), DateUtil.criarDataMesAno(01, 02, 2009), empresa.getId());
		assertEquals(3, count);		
	}
	
	public void testCountDemitidos()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		saveColaborador('F', true, null, empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2005), null, null);
		saveColaborador('M', true, null, empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2003), null, null);
		saveColaborador('F', true, null, empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(02, 03, 2011), null, null);
		saveColaborador('F', true, null, empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(01, 02, 2004), null, null);
		saveColaborador('F', true, null, empresa, null, null, Deficiencia.SEM_DEFICIENCIA, DateUtil.criarDataMesAno(01, 02, 2009), null, null);
		
		int count = colaboradorDao.countDemitidos(DateUtil.criarDataMesAno(01, 02, 2004), DateUtil.criarDataMesAno(01, 02, 2009), empresa.getId());
		assertEquals(3, count);		
	}
	
	private void saveColaborador(char sexo, boolean desligado, Date dataAdmissao, Empresa empresa, String estadoCivil, Date dataNascimento, char deficiencia, Date dataDesligamento, MotivoDemissao motivoDemissao, String vinculo)
	{
		Pessoal pessoal	= new Pessoal();
		pessoal.setEstadoCivil(estadoCivil);
		pessoal.setSexo(sexo);
		pessoal.setDataNascimento(dataNascimento);
		pessoal.setDeficiencia(deficiencia);

		Colaborador colab = new Colaborador();
		colab.setDesligado(desligado);
		colab.setDataAdmissao(dataAdmissao);
		colab.setEmpresa(empresa);
		colab.setPessoal(pessoal);
		colab.setDataDesligamento(dataDesligamento);
		colab.setMotivoDemissao(motivoDemissao);
		colab.setVinculo(vinculo);
		
		colaboradorDao.save(colab);
	}

	public void testFindByIdSobrescrito()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico.setData(new Date());
		faixaSalarialHistorico.setStatus(StatusRetornoAC.CONFIRMADO);
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricos.add(faixaSalarialHistorico);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(new Date());
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		Colaborador colaboradorRetorno = colaboradorDao.findById(colaborador.getId());

		assertEquals(colaborador.getId(), colaboradorRetorno.getId());
		assertEquals(areaOrganizacional.getId(), colaboradorRetorno.getAreaOrganizacional().getId());
		assertEquals(areaOrganizacional.getNome(), colaboradorRetorno.getAreaOrganizacional().getNome());
	}

	public void testFindColaboradorByIdProjection()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		Colaborador colaboradorRetorno = colaboradorDao.findColaboradorByIdProjection(colaborador.getId());

		assertEquals(colaborador.getId(), colaboradorRetorno.getId());
	}

	public void testDesligaColaborador()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setDataDesligamento(null);
		colaborador.setObservacaoDemissao("");
		colaborador.setDesligado(false);
		colaborador.setMotivoDemissao(null);
		colaborador = colaboradorDao.save(colaborador);

		MotivoDemissao motivo = new MotivoDemissao();
		motivo.setMotivo("motivo");
		motivo = motivoDemissaoDao.save(motivo);

		Date dataDesligamento = new Date();
		colaboradorDao.desligaColaborador(true, dataDesligamento, "desligado", motivo.getId(), colaborador.getId());

		Colaborador colaboradorRetorno = colaboradorDao.findColaboradorById(colaborador.getId());

		assertEquals(true, colaboradorRetorno.isDesligado());
		assertEquals("desligado", colaboradorRetorno.getObservacaoDemissao());
		assertEquals(DateUtil.formataDiaMesAno(dataDesligamento), DateUtil.formataDiaMesAno(colaboradorRetorno.getDataDesligamento()));
		assertEquals(motivo.getId(), colaboradorRetorno.getMotivoDemissao().getId());
	}

	public void testFindByIdProjectionEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		Colaborador colaboradorRetorno = colaboradorDao.findByIdProjectionEmpresa(colaborador.getId());

		assertEquals(colaborador.getId(), colaboradorRetorno.getId());
	}

	public void testFindByNomeCpfMatricula()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa);
		colaborador1.setNome("teste");
		colaborador1.setMatricula("1234");
		colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaboradorDao.save(colaborador2);

		assertEquals(1, colaboradorDao.findByNomeCpfMatricula(colaborador1, empresa.getId(), false).size());
		assertEquals(2, colaboradorDao.findByNomeCpfMatricula(null, empresa.getId(), false).size());
	}
	
	public void testFindByNomeCpfMatriculaAndResponsavelArea()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(empresa);
		colaborador1.setNome("teste");
		colaborador1.setMatricula("1234");
		colaboradorDao.save(colaborador1);
		
		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional1.setResponsavel(colaborador1);
		areaOrganizacionalDao.save(areaOrganizacional1);

		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional2);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		historicoColaborador1.setColaborador(colaborador1);
		historicoColaborador1.setAreaOrganizacional(areaOrganizacional1);
		historicoColaboradorDao.save(historicoColaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(empresa);
		colaborador2.setNome("teste");
		colaborador2.setMatricula("1234");
		colaboradorDao.save(colaborador2);
		
		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setData(DateUtil.criarDataMesAno(01, 01, 2008));
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional2);
		historicoColaboradorDao.save(historicoColaborador2);

		assertEquals(1, colaboradorDao.findByNomeCpfMatriculaAndResponsavelArea(colaborador1, empresa.getId(), colaborador1.getId()).size());
	}

	public void testSetCodigoColaboradorAC()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setCodigoAC("");
		colaborador = colaboradorDao.save(colaborador);

		assertEquals(true, colaboradorDao.setCodigoColaboradorAC("codigo", colaborador.getId()));
	}

	public void testFindByCodigoAC()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setCodigoAC("12345678");
		colaborador = colaboradorDao.save(colaborador);

		assertEquals(colaborador, colaboradorDao.findByCodigoAC(colaborador.getCodigoAC(), empresa));
	}
	
	public void testFindByCodigoACUsuario()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuarioDao.save(usuario);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setCodigoAC("12345678");
		colaborador.setUsuario(usuario);
		colaborador = colaboradorDao.save(colaborador);
		
		assertEquals(colaborador, colaboradorDao.findByCodigoAC(colaborador.getCodigoAC(), empresa));
	}

	public void testFindByCodigoACEmpresaString()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("112233");
		empresa.setGrupoAC(grupoAC.getCodigo());
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setCodigoAC("12345678");
		colaborador = colaboradorDao.save(colaborador);

		assertEquals(colaborador, colaboradorDao.findByCodigoAC(colaborador.getCodigoAC(), empresa.getCodigoAC(), "XXX"));
	}

	public void testFindByFuncaoAmbiente()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setNomeComercial("nomeTeste");
		colaborador = colaboradorDao.save(colaborador);

		Ambiente ambiente = AmbienteFactory.getEntity();
		ambiente = ambienteDao.save(ambiente);

		Funcao funcao = FuncaoFactory.getEntity();
		funcao = funcaoDao.save(funcao);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setAmbiente(ambiente);
		historicoColaborador.setFuncao(funcao);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		Collection<Colaborador> colaboradores = colaboradorDao.findByFuncaoAmbiente(funcao.getId(), ambiente.getId());
		assertEquals(1, colaboradores.size());

		Colaborador colaboradorTmp = (Colaborador) colaboradores.toArray()[0];
		assertEquals(colaborador.getId(), colaboradorTmp.getId());
		assertEquals(colaborador.getNomeComercial(), colaboradorTmp.getNomeComercial());
	}

	public void testFindByFuncaoAmbienteNull()
	{
		Ambiente ambiente = AmbienteFactory.getEntity();
		ambiente = ambienteDao.save(ambiente);

		Funcao funcao = FuncaoFactory.getEntity();
		funcao.setId(null);

		Collection<Colaborador> colaboradores = colaboradorDao.findByFuncaoAmbiente(funcao.getId(), ambiente.getId());
		assertEquals(null, colaboradores);
	}

	public void testFindByAreaOrganizacionalIds()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador1 = getColaborador();
		colaborador1.setEmpresa(empresa);
		colaborador1.setNome("Pedro Jose");
		colaborador1.setMatricula("12e3456789");
		colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = getColaborador();
		colaborador2.setEmpresa(empresa);
		colaboradorDao.save(colaborador2);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setEmpresa(empresa);
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		AreaOrganizacional areaOrganizacionalAntiga = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalAntiga.setEmpresa(empresa);
		areaOrganizacionalAntiga = areaOrganizacionalDao.save(areaOrganizacionalAntiga);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaborador1.setColaborador(colaborador1);
		historicoColaborador1.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador1.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(historicoColaborador1);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setData(DateUtil.setaMesPosterior(new Date()));
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador2.setFaixaSalarial(faixaSalarial);
		historicoColaboradorDao.save(historicoColaborador2);

		Collection<Long> areaIds = new ArrayList<Long>();
		areaIds.add(areaOrganizacional.getId());

		Colaborador colaboradorBusca = ColaboradorFactory.getEntity(1L);
		colaboradorBusca.setNome("dro j");
		colaboradorBusca.setMatricula("2E3");
		Collection<Colaborador> colaboradores = colaboradorDao.findByAreaOrganizacionalIds(areaIds, 1, 10, colaboradorBusca, empresa.getId());

		assertEquals(1, colaboradores.size());
		Colaborador retorno = (Colaborador) colaboradores.toArray()[0];
		assertEquals(faixaSalarial, retorno.getFaixaSalarial());
		assertEquals(cargo, retorno.getFaixaSalarial().getCargo());
		assertEquals(grupoOcupacional, retorno.getFaixaSalarial().getCargo().getGrupoOcupacional());
		assertEquals(areaOrganizacional, retorno.getAreaOrganizacional());
		assertEquals("Pedro Jose", retorno.getNome());

		//Teste findByAreaOrganizacionalIds
		Long[] areasIds = new Long[]{areaOrganizacional.getId()};
		colaboradores = colaboradorDao.findByAreaOrganizacionalIds(1, 10, areasIds, null, areaOrganizacional.getEmpresa().getId());
		assertEquals(1, colaboradores.size());

		//Teste findByAreaOrganizacionalIds
		colaboradores = colaboradorDao.findByAreaOrganizacionalIds(areasIds);
		assertEquals(1, colaboradores.size());

		//Teste findByArea
		colaboradores = colaboradorDao.findByArea(areaOrganizacional);
		assertEquals(1, colaboradores.size());
	}
	
	public void testFindByAreaOrganizacional()
	{
		Empresa empresa = new Empresa();
		empresa = empresaDao.save(empresa);
		
		Colaborador colaborador1 = getColaborador();
		colaborador1.setEmpresa(empresa);
		colaborador1.setNome("Pedro Jose");
		colaboradorDao.save(colaborador1);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador1.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaborador1.setColaborador(colaborador1);
		historicoColaborador1.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorDao.save(historicoColaborador1);
		
		Collection<Long> areaIds = new ArrayList<Long>();
		areaIds.add(areaOrganizacional.getId());

		Collection<Colaborador> colaboradores = colaboradorDao.findByAreaOrganizacional(areaIds);
		assertEquals(1, colaboradores.size());
	}

	public void testFindByAreaEstabelecimento()
	{
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		AreaOrganizacional areaOrganizacionalAtual = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalAtual = areaOrganizacionalDao.save(areaOrganizacionalAtual);

		AreaOrganizacional areaOrganizacionalAntiga = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalAntiga = areaOrganizacionalDao.save(areaOrganizacionalAntiga);

		Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity();
		estabelecimentoAtual.setEmpresa(empresa);
		estabelecimentoAtual.setNome("A");
		estabelecimentoAtual = estabelecimentoDao.save(estabelecimentoAtual);

		Estabelecimento estabelecimentoAntigo = EstabelecimentoFactory.getEntity();
		estabelecimentoAntigo.setEmpresa(empresa);
		estabelecimentoAntigo.setNome("B");
		estabelecimentoAntigo = estabelecimentoDao.save(estabelecimentoAntigo);

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setAreaOrganizacional(areaOrganizacionalAtual);
		historicoColaboradorAtual.setEstabelecimento(estabelecimentoAtual);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setAreaOrganizacional(areaOrganizacionalAntiga);
		historicoColaboradorAntigo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);

		Collection<Colaborador> colaboradores = colaboradorDao.findByAreaEstabelecimento(areaOrganizacionalAtual.getId(), estabelecimentoAtual.getId());

		assertEquals(1, colaboradores.size());

		Colaborador colaboradorDoBanco = (Colaborador) colaboradores.toArray()[0];
		assertEquals(areaOrganizacionalAtual, colaboradorDoBanco.getAreaOrganizacional());
		assertEquals(estabelecimentoAtual, colaboradorDoBanco.getEstabelecimento());
	}

	public void testFindByEstabelecimento()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity();
		estabelecimentoAtual.setEmpresa(empresa);
		estabelecimentoAtual.setNome("A");
		estabelecimentoAtual = estabelecimentoDao.save(estabelecimentoAtual);

		Estabelecimento estabelecimentoAntigo = EstabelecimentoFactory.getEntity();
		estabelecimentoAntigo.setEmpresa(empresa);
		estabelecimentoAntigo.setNome("B");
		estabelecimentoAntigo = estabelecimentoDao.save(estabelecimentoAntigo);

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setEstabelecimento(estabelecimentoAtual);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);

		Collection<Colaborador> colaboradores = colaboradorDao.findByEstabelecimento(new Long[]{estabelecimentoAtual.getId()});

		assertEquals(1, colaboradores.size());

		Colaborador colaboradorDoBanco = (Colaborador) colaboradores.toArray()[0];
		assertEquals(estabelecimentoAtual, colaboradorDoBanco.getEstabelecimento());
	}

	public void testGetCountAtivosByEstabelecimento()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity();
		estabelecimentoAtual.setEmpresa(empresa);
		estabelecimentoAtual.setNome("A");
		estabelecimentoAtual = estabelecimentoDao.save(estabelecimentoAtual);

		Estabelecimento estabelecimentoAntigo = EstabelecimentoFactory.getEntity();
		estabelecimentoAntigo.setEmpresa(empresa);
		estabelecimentoAntigo.setNome("B");
		estabelecimentoAntigo = estabelecimentoDao.save(estabelecimentoAntigo);

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setEstabelecimento(estabelecimentoAtual);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);

		Integer count = colaboradorDao.getCountAtivosByEstabelecimento(estabelecimentoAtual.getId());
		assertEquals((Integer)1, count);
	}

	public void testFindByIdHistoricoProjection()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador.setMatricula("123456");
		colaboradorDao.save(colaborador);

		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setNome("Teste");
		cargoDao.save(cargo1);

		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setNome("I");
		faixaSalarial1.setCargo(cargo1);
		faixaSalarialDao.save(faixaSalarial1);

		Cargo cargo2 = CargoFactory.getEntity();
		cargoDao.save(cargo2);

		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarial2.setCargo(cargo2);
		faixaSalarialDao.save(faixaSalarial2);

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setFaixaSalarial(faixaSalarial1);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setFaixaSalarial(faixaSalarial2);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);

		Colaborador colaboradorTmp = colaboradorDao.findByIdHistoricoProjection(colaborador.getId());

		assertEquals(colaborador, colaboradorTmp);
		assertEquals(colaborador.getMatricula(), colaboradorTmp.getMatricula());
		assertEquals(faixaSalarial1.getDescricao(), colaboradorTmp.getHistoricoColaborador().getFaixaSalarial().getDescricao());
	}

	public void testFindByIdHistoricoAtual()
	{
		Colaborador colaborador = getColaborador();
		colaborador.setNome("TESTE");
		colaboradorDao.save(colaborador);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);

		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setNome("Teste");
		cargoDao.save(cargo1);

		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setNome("I");
		faixaSalarial1.setCargo(cargo1);
		faixaSalarialDao.save(faixaSalarial1);

		Cargo cargo2 = CargoFactory.getEntity();
		cargoDao.save(cargo2);

		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarial2.setCargo(cargo2);
		faixaSalarialDao.save(faixaSalarial2);

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setFaixaSalarial(faixaSalarial1);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setFaixaSalarial(faixaSalarial2);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);

		Collection<Long> ids = new ArrayList<Long>();
		ids.add(colaborador.getId());

		Collection<Colaborador> colaboradors = colaboradorDao.findByIdHistoricoAtual(ids);
		assertEquals(1, colaboradors.size());

		Colaborador colaboradorTmp = (Colaborador) colaboradors.toArray()[0];
		assertEquals(colaborador.getNome(), colaboradorTmp.getNome());
		assertEquals("Teste I", colaboradorTmp.getFaixaSalarial().getDescricao());
		assertEquals(areaOrganizacional, colaboradorTmp.getAreaOrganizacional());
	}

	public void testFindByAreasOrganizacionaisEstabelecimentos()
	{
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = getColaborador();
		colaborador.setNome("francisco");
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		Colaborador colaborador2 = getColaborador();
		colaborador2.setNome("francisco");
		colaborador2.setEmpresa(empresa);
		colaborador2.setDesligado(true);
		colaborador2 = colaboradorDao.save(colaborador2);

		AreaOrganizacional areaOrganizacionalAtual = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalAtual = areaOrganizacionalDao.save(areaOrganizacionalAtual);

		AreaOrganizacional areaOrganizacionalAntiga = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalAntiga = areaOrganizacionalDao.save(areaOrganizacionalAntiga);

    	Collection<Long> areasIds = new ArrayList<Long>();
    	areasIds.add(areaOrganizacionalAtual.getId());

		Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity();
		estabelecimentoAtual.setEmpresa(empresa);
		estabelecimentoAtual.setNome("A");
		estabelecimentoAtual = estabelecimentoDao.save(estabelecimentoAtual);

		Estabelecimento estabelecimentoAntigo = EstabelecimentoFactory.getEntity();
		estabelecimentoAntigo.setEmpresa(empresa);
		estabelecimentoAntigo.setNome("B");
		estabelecimentoAntigo = estabelecimentoDao.save(estabelecimentoAntigo);

		Collection<Long> estabelecimentosIds = new ArrayList<Long>();
		estabelecimentosIds.add(estabelecimentoAtual.getId());

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setAreaOrganizacional(areaOrganizacionalAtual);
		historicoColaboradorAtual.setEstabelecimento(estabelecimentoAtual);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setAreaOrganizacional(areaOrganizacionalAntiga);
		historicoColaboradorAntigo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setAreaOrganizacional(areaOrganizacionalAtual);
		historicoColaborador2.setEstabelecimento(estabelecimentoAtual);
		historicoColaborador2 = historicoColaboradorDao.save(historicoColaborador2);

		Collection<Colaborador> colaboradores = colaboradorDao.findByAreasOrganizacionaisEstabelecimentos(areasIds, estabelecimentosIds, "francisco");

		assertEquals(1, colaboradores.size());

		Colaborador colaboradorDoBanco = (Colaborador) colaboradores.toArray()[0];
		assertEquals(areaOrganizacionalAtual, colaboradorDoBanco.getAreaOrganizacional());
		assertEquals(estabelecimentoAtual, colaboradorDoBanco.getEstabelecimento());
	}

	public void testFindByCargoIdsEstabelecimentoIds()
	{
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador.setNome("francisco");
		
		colaborador = colaboradorDao.save(colaborador);

		Cargo cargoAtual = CargoFactory.getEntity();
		cargoAtual = cargoDao.save(cargoAtual);

		FaixaSalarial faixaSalarialAtual = FaixaSalarialFactory.getEntity();
		faixaSalarialAtual.setCargo(cargoAtual);
		faixaSalarialAtual = faixaSalarialDao.save(faixaSalarialAtual);

		Collection<Long> cargosIds = new ArrayList<Long>();
		cargosIds.add(cargoAtual.getId());

		Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity();
		estabelecimentoAtual.setEmpresa(empresa);
		estabelecimentoAtual.setNome("A");
		estabelecimentoAtual = estabelecimentoDao.save(estabelecimentoAtual);

		Estabelecimento estabelecimentoAntigo = EstabelecimentoFactory.getEntity();
		estabelecimentoAntigo.setEmpresa(empresa);
		estabelecimentoAntigo.setNome("B");
		estabelecimentoAntigo = estabelecimentoDao.save(estabelecimentoAntigo);

		Collection<Long> estabelecimentosIds = new ArrayList<Long>();
		estabelecimentosIds.add(estabelecimentoAtual.getId());

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setEstabelecimento(estabelecimentoAtual);
		historicoColaboradorAtual.setFaixaSalarial(faixaSalarialAtual);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);

		HistoricoColaborador historicoColaboradorFuturo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorFuturo.setData(DateUtil.criarDataMesAno(1, 1, 2015));
		historicoColaboradorFuturo.setColaborador(colaborador);
		historicoColaboradorFuturo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorFuturo = historicoColaboradorDao.save(historicoColaboradorFuturo);

		Collection<Colaborador> colaboradores = colaboradorDao.findByCargoIdsEstabelecimentoIds(cargosIds, estabelecimentosIds, "franci");

		assertEquals(1, colaboradores.size());

		Colaborador colaboradorDoBanco = (Colaborador) colaboradores.toArray()[0];
		assertEquals(estabelecimentoAtual, colaboradorDoBanco.getEstabelecimento());
	}

	public void testFindByGrupoOcupacionalIdsEstabelecimentoIds()
	{
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = getColaborador();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional = grupoOcupacionalDao.save(grupoOcupacional);

		Collection<Long> grupoOcupacionalIds = new ArrayList<Long>();
		grupoOcupacionalIds.add(grupoOcupacional.getId());

		Cargo cargoAtual = CargoFactory.getEntity();
		cargoAtual.setGrupoOcupacional(grupoOcupacional);
		cargoAtual = cargoDao.save(cargoAtual);

		FaixaSalarial faixaSalarialAtual = FaixaSalarialFactory.getEntity();
		faixaSalarialAtual.setCargo(cargoAtual);
		faixaSalarialAtual = faixaSalarialDao.save(faixaSalarialAtual);

		Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity();
		estabelecimentoAtual.setEmpresa(empresa);
		estabelecimentoAtual.setNome("A");
		estabelecimentoAtual = estabelecimentoDao.save(estabelecimentoAtual);

		Estabelecimento estabelecimentoAntigo = EstabelecimentoFactory.getEntity();
		estabelecimentoAntigo.setEmpresa(empresa);
		estabelecimentoAntigo.setNome("B");
		estabelecimentoAntigo = estabelecimentoDao.save(estabelecimentoAntigo);

		Collection<Long> estabelecimentosIds = new ArrayList<Long>();
		estabelecimentosIds.add(estabelecimentoAtual.getId());

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setEstabelecimento(estabelecimentoAtual);
		historicoColaboradorAtual.setFaixaSalarial(faixaSalarialAtual);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);

		HistoricoColaborador historicoColaboradorFuturo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorFuturo.setData(DateUtil.criarDataMesAno(1, 1, 2015));
		historicoColaboradorFuturo.setColaborador(colaborador);
		historicoColaboradorFuturo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorFuturo = historicoColaboradorDao.save(historicoColaboradorFuturo);

		Collection<Colaborador> colaboradores = colaboradorDao.findByGrupoOcupacionalIdsEstabelecimentoIds(grupoOcupacionalIds, estabelecimentosIds);

		assertEquals(1, colaboradores.size());

		Colaborador colaboradorDoBanco = (Colaborador) colaboradores.toArray()[0];
		assertEquals(estabelecimentoAtual, colaboradorDoBanco.getEstabelecimento());
	}

	public void testFindByIdProjectionUsuario() throws Exception
	{
		Colaborador colaborador = getEntity();
		colaborador = colaboradorDao.save(colaborador);

		Colaborador colaboradorRetorno = colaboradorDao.findByIdProjectionUsuario(colaborador.getId());

		assertNotNull(colaborador);
		assertEquals(colaboradorRetorno.getId(), colaborador.getId());
	}

	public void testFindAreaOrganizacionalByGruposOrAreas()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("estabelecimento");
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		Colaborador responsavel = getEntity();
		responsavel = colaboradorDao.save(responsavel);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setResponsavel(responsavel);
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional =  grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		Colaborador colaborador = getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);

		//colaborador desligado
		Colaborador colaborador2 = getEntity();
		colaborador2.setDesligado(true);
		colaborador2 = colaboradorDao.save(colaborador2);

		HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
		historicoColaborador2.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador2.setEstabelecimento(estabelecimento);
		historicoColaborador2.setColaborador(colaborador2);
		historicoColaborador2.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador2);

		// Parâmetros do find
		Collection<Long> estabelecimentoIds = new ArrayList<Long>();
		Collection<Long> areaOrganizacionalIds = new ArrayList<Long>();
		Collection<Long> grupoOcupacionalIds = new ArrayList<Long>();

		estabelecimentoIds.add(estabelecimento.getId());
		areaOrganizacionalIds.add(areaOrganizacional.getId());
		grupoOcupacionalIds.add(grupoOcupacional.getId());

		Collection<Colaborador> colaboradors = colaboradorDao.findAreaOrganizacionalByAreas(false, estabelecimentoIds, areaOrganizacionalIds, null, null, null);

		assertEquals(1, colaboradors.size());
	}
	
	public void testFindAreaOrganizacionalByGruposOrAreasCamposExtras()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("estabelecimento");
		estabelecimento = estabelecimentoDao.save(estabelecimento);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);
		
		CamposExtras camposExtras = CamposExtrasFactory.getEntity();
		camposExtras.setTexto1("texto1");
		camposExtras.setTexto2("texto2");
		camposExtras.setTexto3("texto3");
		camposExtras.setTexto4("texto4");
		camposExtras.setTexto5("texto5");
		camposExtras.setTexto6("texto6");
		camposExtras.setTexto7("texto7");
		camposExtras.setTexto8("texto8");
		camposExtras.setTexto9("texto9");
		camposExtras.setTexto10("texto10");
		
		Date date1 = DateUtil.criarDataMesAno(1, 1, 1000);
		Date date2 = DateUtil.criarDataMesAno(2, 2, 2000);
		Date date3 = DateUtil.criarDataMesAno(3, 3, 3000);
		camposExtras.setData1(date1);
		camposExtras.setData2(date2);
		camposExtras.setData3(date3);
		
		camposExtras.setValor1(10.0);
		camposExtras.setValor2(20.0);
		
		camposExtras.setNumero1(55);
		camposExtrasDao.save(camposExtras);
		
		Colaborador colaborador = getEntity();
		colaborador.setCamposExtras(camposExtras);
		colaborador = colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);
				
		// Parâmetros do find
		Collection<Long> estabelecimentoIds = new ArrayList<Long>();
		Collection<Long> areaOrganizacionalIds = new ArrayList<Long>();
		
		estabelecimentoIds.add(estabelecimento.getId());
		areaOrganizacionalIds.add(areaOrganizacional.getId());
		
		Collection<Colaborador> colaboradors = colaboradorDao.findAreaOrganizacionalByAreas(true, estabelecimentoIds, areaOrganizacionalIds, camposExtras, null, null);
		assertEquals(1, colaboradors.size());
		
		CamposExtras camposExtrasBusca = new CamposExtras();
		
		camposExtrasBusca = CamposExtrasFactory.getEntity();
		camposExtrasBusca.setData1(DateUtil.criarDataMesAno(1, 1, 1000));
		camposExtrasBusca.setData2(DateUtil.criarDataMesAno(2, 2, 2000));
		camposExtrasBusca.setData3(DateUtil.criarDataMesAno(3, 3, 3000));
		
		camposExtrasBusca.setData1Fim(DateUtil.criarDataMesAno(3, 3, 1000));
		camposExtrasBusca.setData2Fim(DateUtil.criarDataMesAno(4, 4, 2000));
		camposExtrasBusca.setData3Fim(DateUtil.criarDataMesAno(5, 5, 3000));
		
		camposExtrasBusca.setValor1(5.0);
		camposExtrasBusca.setValor2(10.0);

		camposExtrasBusca.setValor1Fim(20.0);
		camposExtrasBusca.setValor2Fim(30.0);
		
		camposExtrasBusca.setNumero1(40);
		camposExtrasBusca.setNumero1Fim(60);
		
		colaboradors = colaboradorDao.findAreaOrganizacionalByAreas(true, estabelecimentoIds, areaOrganizacionalIds, camposExtrasBusca, null, null);
		assertEquals(1, colaboradors.size());
		
		// Parâmetros do find
		camposExtrasBusca = CamposExtrasFactory.getEntity();
		
		camposExtrasBusca.setData1Fim(DateUtil.criarDataMesAno(3, 3, 1000));
		camposExtrasBusca.setData2Fim(DateUtil.criarDataMesAno(4, 4, 2000));
		camposExtrasBusca.setData3Fim(DateUtil.criarDataMesAno(5, 5, 3000));
		
		camposExtrasBusca.setValor1Fim(20.0);
		camposExtrasBusca.setValor2Fim(30.0);
		
		camposExtrasBusca.setNumero1Fim(60);
		
		colaboradors = colaboradorDao.findAreaOrganizacionalByAreas(true, estabelecimentoIds, areaOrganizacionalIds, camposExtrasBusca, null, null);
		assertEquals(1, colaboradors.size());
	}

	public void testFindColaboradoresMotivoDemissao()
	{
		prepareFindColaboradoresMotivo();

		Date dataIni = DateUtil.criarDataMesAno(01, 01, 1945);
		Date dataFim = DateUtil.criarDataMesAno(30, 12, 2007);

		Long[] estabelecimentoIds = new Long[]{estabelecimento1.getId()};
		Long[] areaIds = new Long[]{areaOrganizacional1.getId()};
		Long[] cargoIds = new Long[]{cargo1.getId()};

		Collection<Colaborador> retorno = colaboradorDao.findColaboradoresMotivoDemissao(estabelecimentoIds, areaIds, cargoIds, dataIni, dataFim);

		assertEquals(1, retorno.size());
	}

	public void testFindColaboradoresMotivoDemissaoQuantidade()
	{
		prepareFindColaboradoresMotivo();

		Date dataIni = DateUtil.criarDataMesAno(01, 01, 1945);
		Date dataFim = DateUtil.criarDataMesAno(30, 12, 2007);

		Long[] estabelecimentoIds = new Long[]{estabelecimento1.getId()};
		Long[] areaIds = new Long[]{areaOrganizacional1.getId()};
		Long[] cargoIds = new Long[]{cargo1.getId()};

		List<Object[]> retorno = colaboradorDao.findColaboradoresMotivoDemissaoQuantidade(estabelecimentoIds, areaIds, cargoIds, dataIni, dataFim);

		assertEquals(1, retorno.size());
	}

	private void prepareFindColaboradoresMotivo()
	{
		estabelecimento1.setNome("estabelecimento");
		estabelecimento1 = estabelecimentoDao.save(estabelecimento1);

		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity();
		estabelecimento2.setNome("estabelecimento");
		estabelecimento2 = estabelecimentoDao.save(estabelecimento2);

		Colaborador responsavel = getEntity();
		responsavel = colaboradorDao.save(responsavel);

		areaOrganizacional1.setResponsavel(responsavel);
		areaOrganizacional1 = areaOrganizacionalDao.save(areaOrganizacional1);

		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional2.setResponsavel(responsavel);
		areaOrganizacional2 = areaOrganizacionalDao.save(areaOrganizacional2);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional =  grupoOcupacionalDao.save(grupoOcupacional);

		cargo1.setGrupoOcupacional(grupoOcupacional);
		cargo1 = cargoDao.save(cargo1);

		Cargo cargo2 = CargoFactory.getEntity();
		cargo2.setGrupoOcupacional(grupoOcupacional);
		cargo2 = cargoDao.save(cargo2);

		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setCargo(cargo1);
		faixaSalarial1 = faixaSalarialDao.save(faixaSalarial1);

		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarial2.setCargo(cargo2);
		faixaSalarial2 = faixaSalarialDao.save(faixaSalarial2);

		MotivoDemissao motivo = new MotivoDemissao();
		motivo.setMotivo("motivo");
		motivo = motivoDemissaoDao.save(motivo);

		//vem na consulta
		Colaborador c1 = ColaboradorFactory.getEntity();
		c1.setMotivoDemissao(motivo);
		c1.setDataAdmissao(DateUtil.criarDataMesAno(01, 02, 1945));
		c1.setDataDesligamento(DateUtil.criarDataMesAno(01, 06, 1945));
		c1 = colaboradorDao.save(c1);

		HistoricoColaborador hc1 = HistoricoColaboradorFactory.getEntity();
		hc1.setColaborador(c1);
		hc1.setAreaOrganizacional(areaOrganizacional1);
		hc1.setEstabelecimento(estabelecimento1);
		hc1.setFaixaSalarial(faixaSalarial1);
		hc1 = historicoColaboradorDao.save(hc1);

		//não vem na consulta pois não tem data desligamento
		Colaborador c2 = ColaboradorFactory.getEntity();
		c2.setDataAdmissao(DateUtil.criarDataMesAno(01, 02, 1945));
		c2.setDataDesligamento(null);
		c2 = colaboradorDao.save(c2);

		HistoricoColaborador hc2 = HistoricoColaboradorFactory.getEntity();
		hc2.setColaborador(c2);
		hc2.setAreaOrganizacional(areaOrganizacional1);
		hc2.setEstabelecimento(estabelecimento1);
		hc2.setFaixaSalarial(faixaSalarial2);
		hc2 = historicoColaboradorDao.save(hc2);

		//não vem na consulta pois a data de desligamento é depois do parametro passado na consulta
		Colaborador c3 = ColaboradorFactory.getEntity();
		c3.setDataAdmissao(DateUtil.criarDataMesAno(01, 01, 1945));
		c3.setDataDesligamento(DateUtil.criarDataMesAno(20, 02, 2008));
		c3 = colaboradorDao.save(c3);

		HistoricoColaborador hc3 = HistoricoColaboradorFactory.getEntity();
		hc3.setColaborador(c3);
		hc3.setAreaOrganizacional(areaOrganizacional1);
		hc3.setEstabelecimento(estabelecimento1);
		hc3.setFaixaSalarial(faixaSalarial2);
		hc3 = historicoColaboradorDao.save(hc3);

		//não vem na consulta pois tem um estabelecimento diferente
		Colaborador c4 = ColaboradorFactory.getEntity();
		c4.setDataAdmissao(DateUtil.criarDataMesAno(01, 01, 1945));
		c4.setDataDesligamento(DateUtil.criarDataMesAno(20, 02, 2007));
		c4 = colaboradorDao.save(c4);

		HistoricoColaborador hc4 = HistoricoColaboradorFactory.getEntity();
		hc4.setColaborador(c4);
		hc4.setAreaOrganizacional(areaOrganizacional1);
		hc4.setEstabelecimento(estabelecimento2);
		hc4.setFaixaSalarial(faixaSalarial2);
		hc4 = historicoColaboradorDao.save(hc4);

		//não vem na consulta pois tem uma area diferente
		Colaborador c5 = ColaboradorFactory.getEntity();
		c5.setDataAdmissao(DateUtil.criarDataMesAno(01, 01, 1945));
		c5.setDataDesligamento(DateUtil.criarDataMesAno(20, 02, 2007));
		c5 = colaboradorDao.save(c5);

		HistoricoColaborador hc5 = HistoricoColaboradorFactory.getEntity();
		hc5.setColaborador(c5);
		hc5.setAreaOrganizacional(areaOrganizacional2);
		hc5.setEstabelecimento(estabelecimento1);
		hc5.setFaixaSalarial(faixaSalarial2);
		hc5 = historicoColaboradorDao.save(hc5);

		//não vem na consulta pois tem um cargo diferente
		Colaborador c6 = ColaboradorFactory.getEntity();
		c6.setDataAdmissao(DateUtil.criarDataMesAno(01, 01, 1945));
		c6.setDataDesligamento(DateUtil.criarDataMesAno(20, 02, 2007));
		c6 = colaboradorDao.save(c6);

		HistoricoColaborador hc6 = HistoricoColaboradorFactory.getEntity();
		hc6.setColaborador(c6);
		hc6.setAreaOrganizacional(areaOrganizacional1);
		hc6.setEstabelecimento(estabelecimento1);
		hc6.setFaixaSalarial(faixaSalarial2);
		hc6 = historicoColaboradorDao.save(hc6);
	}

	public void testFindProjecaoSalarialByHistoricoColaborador()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("estabelecimento");
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional =  grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setData(new Date());
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setFaixaSalarialHistoricoAtual(faixaSalarialHistorico);
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		Collection<Long> estabelecimentoIds = new ArrayList<Long>();
		estabelecimentoIds.add(estabelecimento.getId());

		Collection<Long> grupoIds = new ArrayList<Long>();
		grupoIds.add(grupoOcupacional.getId());

		Collection<Long> cargoIds = new ArrayList<Long>();
		cargoIds.add(cargo.getId());

		Collection<Colaborador> retorno = colaboradorDao.findProjecaoSalarialByHistoricoColaborador(new Date(), estabelecimentoIds, null, grupoIds, cargoIds, "2", empresa.getId());

		assertEquals(colaboradors.size(), retorno.size());
	}

	public void testFindProjecaoSalarialByTabelaReajusteColaborador()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaborador = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador = colaboradorDao.save(colaborador);

		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("estabelecimento");
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setNome("nome");
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		GrupoOcupacional grupoOcupacional = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional =  grupoOcupacionalDao.save(grupoOcupacional);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setGrupoOcupacional(grupoOcupacional);
		cargo = cargoDao.save(cargo);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setData(new Date());
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setFaixaSalarialHistoricoAtual(faixaSalarialHistorico);
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);

		ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador();
		reajusteColaborador.setTipoSalarioProposto(TipoAplicacaoIndice.VALOR);
		reajusteColaborador.setAreaOrganizacionalProposta(areaOrganizacional);
		reajusteColaborador.setEstabelecimentoProposto(estabelecimento);
		reajusteColaborador.setColaborador(colaborador);
		reajusteColaborador.setFaixaSalarialProposta(faixaSalarial);
		reajusteColaborador.setTabelaReajusteColaborador(tabelaReajusteColaborador);
		reajusteColaborador = reajusteColaboradorDao.save(reajusteColaborador);

		Collection<Long> estabelecimentoIds = new ArrayList<Long>();
		estabelecimentoIds.add(estabelecimento.getId());

		Collection<Long> areaIds = new ArrayList<Long>();
		areaIds.add(areaOrganizacional.getId());

		Collection<Colaborador> retorno = colaboradorDao.findProjecaoSalarialByTabelaReajusteColaborador(tabelaReajusteColaborador.getId(), new Date(), estabelecimentoIds, areaIds, null, null, "1", empresa.getId());

		assertEquals(colaboradors.size(), retorno.size());
	}

	public void testSetRespondeuEntrevista()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();

		colaborador = colaboradorDao.save(colaborador);

		colaboradorDao.setRespondeuEntrevista(colaborador.getId());
	}

	public void testFindAllSelect()
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
		colaborador3.setDesligado(true);
		colaborador3 = colaboradorDao.save(colaborador3);

		assertEquals(2, colaboradorDao.findAllSelect(empresa.getId(), "nomeComercial").size());
	}
	
	public void testFindAllSelects()
	{
		Empresa vega = EmpresaFactory.getEmpresa();
		empresaDao.save(vega);

		Empresa urbana = EmpresaFactory.getEmpresa();
		empresaDao.save(urbana);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setEmpresa(vega);
		colaborador1 = colaboradorDao.save(colaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setEmpresa(urbana);
		colaborador2 = colaboradorDao.save(colaborador2);
		
		Colaborador colaborador3 = ColaboradorFactory.getEntity();
		colaborador3.setEmpresa(vega);
		colaborador3.setDesligado(true);
		colaborador3 = colaboradorDao.save(colaborador3);
		
		Long[] empresaIds = new Long[]{vega.getId(), urbana.getId()};
		
		assertEquals(2, colaboradorDao.findAllSelect(empresaIds).size());
	}

	public void testSetMatriculaColaborador()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setCodigoAC("1234567888");
		colaborador.setMatricula("");
		colaborador = colaboradorDao.save(colaborador);

		String matricula = "123456789";
		colaboradorDao.setMatriculaColaborador(empresa.getId(), colaborador.getCodigoAC(), matricula);

		Colaborador colaboradorTmp = colaboradorDao.findByIdProjectionEmpresa(colaborador.getId());
		assertEquals(matricula, colaboradorTmp.getMatricula());
	}

	public void testSetMatriculaColaboradorPorId()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setMatricula("");
		colaborador = colaboradorDao.save(colaborador);

		String matricula = "123456789";
		colaboradorDao.setMatriculaColaborador(colaborador.getId(), matricula);

		Colaborador colaboradorTmp = colaboradorDao.findByIdProjectionEmpresa(colaborador.getId());
		assertEquals(matricula, colaboradorTmp.getMatricula());
	}

	public void testFindByCodigoACEmpresaCodigoAC()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("fs444ADSF54");
		empresa.setGrupoAC(grupoAC.getCodigo());
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setCodigoAC("123ASDF55");
		colaborador = colaboradorDao.save(colaborador);

		assertEquals(colaborador, colaboradorDao.findByCodigoACEmpresaCodigoAC(colaborador.getCodigoAC(), colaborador.getEmpresa().getCodigoAC(), "XXX"));
	}

	public void testFindColaboradorById()
	{
		Estado estado = EstadoFactory.getEntity(1L);
		estado.setSigla("CE");

		Habilitacao habilitacao = new Habilitacao();
		habilitacao.setNumeroHab("123");

		Endereco endereco = new Endereco();
		endereco.setCep("1234");

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.getPessoal().getCtps().setCtpsUf(estado);
		colaborador.setHabilitacao(habilitacao);
		colaborador.setEndereco(endereco);
		colaboradorDao.save(colaborador);

		Colaborador colaboradorRetorno = colaboradorDao.findColaboradorById(colaborador.getId());
		assertEquals(colaborador, colaboradorRetorno);
		assertEquals(habilitacao.getNumeroHab(), colaboradorRetorno.getHabilitacao().getNumeroHab());
		assertEquals(endereco.getCep(), colaboradorRetorno.getEndereco().getCep());
	}

	public void testUpdateInfoPessoais()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.getEndereco().setLogradouro("logradouro");
		colaborador = colaboradorDao.save(colaborador);

		Colaborador colaboradorUpdate = ColaboradorFactory.getEntity();
		colaboradorUpdate.setId(colaborador.getId());
		colaboradorUpdate.getEndereco().setLogradouro("teste");

		colaboradorDao.updateInfoPessoais(colaboradorUpdate);

		Colaborador colaboradorTmp = colaboradorDao.findColaboradorById(colaborador.getId());

		assertEquals("teste", colaboradorTmp.getEndereco().getLogradouro());
	}

	public void testFindByUsuarioColaborador()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario = usuarioDao.save(usuario);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setUsuario(usuario);
		colaborador = colaboradorDao.save(colaborador);

		assertEquals(colaborador.getId(), colaboradorDao.findByUsuario(usuario.getId()));
	}

	public void testFindByUsuarioColaboradorNull()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		assertEquals(null, colaboradorDao.findByUsuario(15984441L));
	}

	public void testFindByIdComHistorico()
	{
		Colaborador colaborador = getEntity();
		colaborador = colaboradorDao.save(colaborador);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico.setData(new Date());
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricos.add(faixaSalarialHistorico);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setSalario(1000D);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 3000));
		historicoColaborador.setMotivo("m");
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		Colaborador colaboradorRetorno = colaboradorDao.findByIdComHistorico(colaborador.getId(), null);

		assertEquals(colaboradorRetorno.getId(), colaborador.getId());
	}

	public void testGetCountAtivos()
	{
		Date hoje = new Date();
		Calendar dataDoisMesesAtras = Calendar.getInstance();
    	dataDoisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar dataTresMesesAtras = Calendar.getInstance();
    	dataTresMesesAtras.add(Calendar.MONTH, -3);

		Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);

    	Colaborador colaboradorAtivoDentroDaConsulta = ColaboradorFactory.getEntity();
    	colaboradorAtivoDentroDaConsulta.setEmpresa(empresa);
    	colaboradorAtivoDentroDaConsulta.setDataAdmissao(dataTresMesesAtras.getTime());
    	colaboradorAtivoDentroDaConsulta.setDesligado(false);
    	colaboradorDao.save(colaboradorAtivoDentroDaConsulta);

    	Colaborador colaboradorDesligadoDentroDaConsulta = ColaboradorFactory.getEntity();
    	colaboradorDesligadoDentroDaConsulta.setEmpresa(empresa);
    	colaboradorDesligadoDentroDaConsulta.setDataAdmissao(dataTresMesesAtras.getTime());
    	colaboradorDesligadoDentroDaConsulta.setDesligado(true);
    	colaboradorDesligadoDentroDaConsulta.setDataDesligamento(hoje);
    	colaboradorDao.save(colaboradorDesligadoDentroDaConsulta);

    	Colaborador colaboradorDesligadoForaDaConsulta = ColaboradorFactory.getEntity();
    	colaboradorDesligadoForaDaConsulta.setEmpresa(empresa);
    	colaboradorDesligadoForaDaConsulta.setDataAdmissao(dataTresMesesAtras.getTime());
    	colaboradorDesligadoForaDaConsulta.setDesligado(true);
    	colaboradorDesligadoForaDaConsulta.setDataDesligamento(dataTresMesesAtras.getTime());
    	colaboradorDao.save(colaboradorDesligadoForaDaConsulta);

    	assertEquals(new Integer(2), colaboradorDao.getCountAtivos(dataTresMesesAtras.getTime(), dataDoisMesesAtras.getTime(), empresa.getId()));

	}
	
	public void testGetCountAtivosDataBase()
	{
		Date dataBase = DateUtil.criarDataMesAno(01, 02, 2011); 
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_PARCIAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('F', true, DateUtil.criarDataMesAno(01, 02, 2000), empresa, EstadoCivil.CASADO_COMUNHAO_PARCIAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2012), empresa, EstadoCivil.CASADO_COMUNHAO_PARCIAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		saveColaborador('M', false, DateUtil.criarDataMesAno(01, 02, 2012), empresa2, EstadoCivil.CASADO_COMUNHAO_PARCIAL, null, Deficiencia.SEM_DEFICIENCIA, null, null, null);
		
		assertEquals(1, colaboradorDao.getCountAtivos(dataBase, empresa.getId()));
	}

	public void testFindAniversariantes()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresaDao.save(empresa);
    	
    	int mes = 2;
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.setEmpresa(empresa);
    	colaborador.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(01, 01, 2000));
    	colaboradorDao.save(colaborador);
    	
    	Colaborador colaboradorAniversariante = ColaboradorFactory.getEntity();
    	colaboradorAniversariante.setEmpresa(empresa);
    	colaboradorAniversariante.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(01, 02, 2000));
    	colaboradorDao.save(colaboradorAniversariante);

    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
    	estabelecimento = estabelecimentoDao.save(estabelecimento);
    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
    	areaOrganizacionalDao.save(areaOrganizacional);
    	
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2000));
		historicoColaboradorDao.save(historicoColaborador);
		
		HistoricoColaborador historicoColaboradorAniversariante = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAniversariante.setColaborador(colaboradorAniversariante);
		historicoColaboradorAniversariante.setEstabelecimento(estabelecimento);
		historicoColaboradorAniversariante.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorAniversariante.setData(DateUtil.criarDataMesAno(01, 01, 2001));
		historicoColaboradorDao.save(historicoColaboradorAniversariante);
		
		Collection<Colaborador> aniversariantes = colaboradorDao.findAniversariantes(new Long[]{empresa.getId()}, mes, null, new Long[]{estabelecimento.getId()});
		assertEquals(1, aniversariantes.size());
	}
	
	public void testCountSemMotivos()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setDesligado(true);
		colaborador.setMotivoDemissao(null);
		colaborador.setDataDesligamento(DateUtil.criarDataMesAno(2, 1, 1980));
		colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 1977));
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		assertEquals(new Integer(1), colaboradorDao.countSemMotivos(null, null, null, DateUtil.criarDataMesAno(1, 1, 1980), DateUtil.criarDataMesAno(3, 1, 1980)));
	}

	public void testMigrarBairro()
	{
		Bairro bairro = new Bairro();
		bairro.setNome("bairro novo");
		bairroDao.save(bairro);

		Bairro bairroDestino = new Bairro();
		bairroDestino.setNome("novo bairro");
		bairroDao.save(bairroDestino);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Endereco endereco = new Endereco();
		endereco.setBairro(bairro.getNome());

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setEndereco(endereco);
		colaboradorDao.save(colaborador);

		colaboradorDao.migrarBairro(bairro.getNome(), bairroDestino.getNome());
		Colaborador colaboradorTmp = colaboradorDao.findColaboradorById(colaborador.getId());
		assertEquals(colaboradorTmp.getEndereco().getBairro(), bairroDestino.getNome());
	}

//
//    public void testFindSemCurso()
//    {
//    	Empresa empresa = EmpresaFactory.getEmpresa();
//    	empresa = empresaDao.save(empresa);
//    	Colaborador colaboradorSemCurso = ColaboradorFactory.getEntity();
//    	colaboradorSemCurso.setEmpresa(empresa);
//    	colaboradorSemCurso = colaboradorDao.save(colaboradorSemCurso);
//
//    	HistoricoColaborador historicoColaborador1 = HistoricoColaboradorFactory.getEntity();
//    	historicoColaborador1.setColaborador(colaboradorSemCurso);
//    	HistoricoColaborador historicoColaborador2 = HistoricoColaboradorFactory.getEntity();
//    	historicoColaborador2.setColaborador(colaboradorSemCurso);
//
//    	AreaOrganizacional areaOrganizacionalAntiga = AreaOrganizacionalFactory.getEntity();
//    	areaOrganizacionalAntiga = areaOrganizacionalDao.save(areaOrganizacionalAntiga);
//    	AreaOrganizacional areaOrganizacionalAtual = AreaOrganizacionalFactory.getEntity();
//    	areaOrganizacionalAtual = areaOrganizacionalDao.save(areaOrganizacionalAtual);
//
//    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
//    	estabelecimento = estabelecimentoDao.save(estabelecimento);
//
//    	historicoColaborador1.setAreaOrganizacional(areaOrganizacionalAntiga);
//    	historicoColaborador1.setEstabelecimento(estabelecimento);
//    	historicoColaborador1.setData(DateUtil.criarDataMesAno(03, 02, 2005));
//
//    	historicoColaborador2.setAreaOrganizacional(areaOrganizacionalAtual);
//    	historicoColaborador2.setEstabelecimento(estabelecimento);
//    	historicoColaborador2.setData(DateUtil.criarDataMesAno(05, 02, 2009));
//
//    	historicoColaboradorDao.save(historicoColaborador1);
//    	historicoColaboradorDao.save(historicoColaborador2);
//
//    	Collection<Colaborador> colaboradores = colaboradorDao.findSemCurso();
//    }
//	select ct.id, c.id, hc.areaorganizacional_id, hc.estabelecimento_id, c.empresa_id, colab.nome, colab.matricula, t.descricao, t.dataprevini , t.dataprevfim, ct.aprovado
//	 from colaboradorturma as ct
//	join turma as t on t.id = ct.turma_id
//	join curso as c on c.id = ct.curso_id
//	join colaborador as colab on colab.id = ct.colaborador_id
//	join historicocolaborador as hc on ct.colaborador_id=hc.colaborador_id
//		where hc.data = (select max(hc2.data) from historicocolaborador hc2 where hc2.colaborador_id = ct.colaborador_id and hc2.data <= '2009-05-05')
//		and hc.areaorganizacional_id = 41
//		and hc.estabelecimento_id = 2
//		and c.empresa_id = 4
//	order by colab.nome
//
//
//	select colab.id, colab.nome from colaborador as colab
//	 join historicocolaborador as hc on colab.id=hc.colaborador_id
//		where hc.data = (select max(hc2.data) from historicocolaborador hc2 where hc2.colaborador_id = colab.id and hc2.data <= '2009-05-05')
//		and hc.areaorganizacional_id = 41
//		and hc.estabelecimento_id = 2
//		and colab.empresa_id = 4
//	order by colab.nome
//
//
//	select colab.id, colab.nome, colab.matricula, hc.areaorganizacional_id from colaborador as colab
//	join historicocolaborador as hc on colab.id=hc.colaborador_id
//	left join colaboradorturma as ct on colab.id=ct.colaborador_id
//	left join curso as c on c.id = ct.curso_id
//		where hc.data = (select max(hc2.data) from historicocolaborador hc2 where hc2.colaborador_id = colab.id and hc2.data <= '2009-05-05')
//		and hc.areaorganizacional_id = 41
//		and hc.estabelecimento_id = 2
//		and colab.empresa_id = 4
//		and (c.id != 15 or ct.id is null)
//	order by colab.nome

	
	public void testFindEmailsDeColaboradoresByPerfis()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.setEmpresa(empresa);
    	colaborador.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(01, 01, 2000));
    	colaborador.getContato().setEmail("miguel@ze.com.br");
    	colaboradorDao.save(colaborador);
    	
    	Perfil perfil = new Perfil();
    	perfilDao.save(perfil);
    	
    	assertEquals(0,colaboradorDao.findEmailsDeColaboradoresByPerfis(new Long[]{perfil.getId()}, empresa.getId()).size());
	}
	
	public void testFindAdmitidosHaDias()
	{
		Calendar quarentaDiasAtras = Calendar.getInstance();
		quarentaDiasAtras.add(Calendar.DAY_OF_YEAR, -40);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.setEmpresa(empresa);
    	colaborador.setDataAdmissao(quarentaDiasAtras.getTime());
    	colaboradorDao.save(colaborador);
    	
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(DateUtil.criarDataMesAno(01, 01, 2005));
		historicoColaboradorDao.save(historicoColaborador);
    	
    	assertEquals(1, colaboradorDao.findAdmitidosHaDias(40, empresa).size());
	}
	
	public void testFindAdmitidosNoPeriodo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Date data = DateUtil.criarDataMesAno(01, 05, 2010);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setDataAdmissao(data);
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(data);
		historicoColaboradorDao.save(historicoColaborador);

		Collection<Colaborador> colaboradores = colaboradorDao.findAdmitidosNoPeriodo(DateUtil.criarDataMesAno(01, 06, 2010), empresa, null, null, 200, 0);
		assertEquals(1, colaboradores.size());
		
		Colaborador colaboradorTmp = (Colaborador) colaboradores.toArray()[0];
		assertEquals(new Integer(31), colaboradorTmp.getDiasDeEmpresa());
	}
	
	public void testCountDesligados()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Collection<Long> longs = new ArrayList<Long>();
		longs.add(1L);
		
		Collection<TurnOver> colaboradores = colaboradorDao.countDemitidosPeriodo(DateUtil.criarDataMesAno(01, 01, 2010), DateUtil.criarDataMesAno(30, 12, 2010), empresa.getId(), longs, longs, longs);
		assertEquals(0, colaboradores.size());
	}
	
	public void testCountAdmitidosPeriodo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Collection<Long> longs = new ArrayList<Long>();
		longs.add(1L);
		Collection<TurnOver> colaboradores = colaboradorDao.countAdmitidosPeriodo(DateUtil.criarDataMesAno(01, 01, 2010), DateUtil.criarDataMesAno(30, 12, 2010), empresa.getId(), longs, longs, longs);
		assertEquals(0, colaboradores.size());
	}
	
	public void testFindComAvaliacoesExperiencias()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Date data = DateUtil.criarDataMesAno(01, 05, 2010);
		Date respondidaEm = DateUtil.criarDataMesAno(16, 05, 2010);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setDataAdmissao(data);
		colaboradorDao.save(colaborador);
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(data);
		historicoColaboradorDao.save(historicoColaborador);
	
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacaoDao.save(avaliacao);
		
		ColaboradorQuestionario colaboradorQuestionarioRespondeu = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioRespondeu.setColaborador(colaborador);
		colaboradorQuestionarioRespondeu.setRespondidaEm(respondidaEm);
		colaboradorQuestionarioRespondeu.setAvaliacao(avaliacao);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioRespondeu);

		ColaboradorQuestionario colaboradorQuestionarioPesquisa = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionarioPesquisa.setColaborador(colaborador);
		colaboradorQuestionarioPesquisa.setRespondidaEm(respondidaEm);
		colaboradorQuestionarioDao.save(colaboradorQuestionarioPesquisa);
		
		Collection<Colaborador> colaboradores = colaboradorDao.findComAvaliacoesExperiencias(DateUtil.criarDataMesAno(01, 06, 2010), empresa, null, null, 200, 0);
		assertEquals(1, colaboradores.size());
		
		Colaborador colaboradorTmp = (Colaborador) colaboradores.toArray()[0];
		assertEquals(new Integer(15), colaboradorTmp.getQtdDiasRespondeuAvExperiencia());
	}
	
	public void testFindAdmitidos()
	{
		Date dataAdmissao = DateUtil.montaDataByString("20/01/2010");
		Date dataAdmissaoFora = DateUtil.montaDataByString("13/03/2010");
		
		Empresa empresa = EmpresaFactory.getEmpresa();
    	empresaDao.save(empresa);
    	
    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
    	estabelecimento.setEmpresa(empresa);
    	estabelecimentoDao.save(estabelecimento);
    	
    	Estabelecimento estabelecimentoFora = EstabelecimentoFactory.getEntity();
		estabelecimentoFora.setEmpresa(empresa);
		estabelecimentoFora.setNome("B");
		estabelecimentoFora = estabelecimentoDao.save(estabelecimentoFora);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.setEmpresa(empresa);
    	colaborador.setDataAdmissao(dataAdmissao);
    	colaboradorDao.save(colaborador);
    	
    	Colaborador colaboradorFora = ColaboradorFactory.getEntity();
    	colaboradorFora.setEmpresa(empresa);
    	colaboradorFora.setDataAdmissao(dataAdmissaoFora);
    	colaboradorDao.save(colaboradorFora);
    	
    	HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setEstabelecimento(estabelecimento);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);
		
		HistoricoColaborador historicoColaboradorFora = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorFora.setData(DateUtil.criarDataMesAno(1, 1, 2009));
		historicoColaboradorFora.setColaborador(colaboradorFora);
		historicoColaboradorFora.setEstabelecimento(estabelecimentoFora);
		historicoColaboradorFora = historicoColaboradorDao.save(historicoColaboradorFora);
		
    	Date dataIni = DateUtil.montaDataByString("19/05/2009");
		Date dataFim = DateUtil.montaDataByString("20/01/2010");;
		Long[] areasIds = null;
		Long[] estabelecimentosIds = new Long[]{estabelecimento.getId()};
		
		assertEquals(1, colaboradorDao.findAdmitidos(dataIni, dataFim, areasIds, estabelecimentosIds, false).size());
	}
	

	public void testFindHistoricoByColaboradors()
	{
		Colaborador colaborador1 = getColaborador();
		colaborador1.setNome("A TESTE");
		colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = getColaborador();
		colaborador2.setNome("X Maria");
		colaboradorDao.save(colaborador2);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);

		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setNome("Teste");
		cargoDao.save(cargo1);

		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setNome("I");
		faixaSalarial1.setCargo(cargo1);
		faixaSalarialDao.save(faixaSalarial1);

		HistoricoColaborador historicoColaboradorAtual1 = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual1.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorAtual1.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual1.setColaborador(colaborador1);
		historicoColaboradorAtual1.setFaixaSalarial(faixaSalarial1);
		historicoColaboradorDao.save(historicoColaboradorAtual1);
		
		HistoricoColaborador historicoColaboradorAtual2 = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual2.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorAtual2.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual2.setColaborador(colaborador2);
		historicoColaboradorAtual2.setFaixaSalarial(faixaSalarial1);
		historicoColaboradorDao.save(historicoColaboradorAtual2);

		HistoricoColaborador historicoColaboradorAntigo1 = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo1.setData(DateUtil.criarDataMesAno(1, 1, 2007));
		historicoColaboradorAntigo1.setColaborador(colaborador1);
		historicoColaboradorDao.save(historicoColaboradorAntigo1);

		Collection<Long> ids = new ArrayList<Long>();
		ids.add(colaborador1.getId());
		ids.add(colaborador2.getId());

		Collection<Colaborador> colaboradors = colaboradorDao.findByIdHistoricoAtual(ids);
		assertEquals(2, colaboradors.size());

		Colaborador colaboradorTmp = (Colaborador) colaboradors.toArray()[0];
		assertEquals(colaborador1.getNome(), colaboradorTmp.getNome());
		assertEquals("Teste I", colaboradorTmp.getFaixaSalarial().getDescricao());
		assertEquals(areaOrganizacional.getNome(), colaboradorTmp.getAreaOrganizacional().getNome());
	}
	
	public void testFindByCpf()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador1 = getColaborador();
		colaborador1.setNome("Xica ");
		colaborador1.setPessoalCpf("26745534304");
		colaborador1.setEmpresa(empresa);
		
		colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = getColaborador();
		colaborador2.setNome("Xica");
		colaborador2.setPessoalCpf("26745534304");
		colaborador2.setEmpresa(empresa);
		colaboradorDao.save(colaborador2);
		
		assertEquals(2, colaboradorDao.findByCpf("26745534304", empresa.getId()).size());
	}
	
	public void testUpdateInfoPessoaisByCpf()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = getColaborador();
		colaborador.setNome("Xica ");
		colaborador.setPessoalCpf("26745534304");
		colaborador.setEmpresa(empresa);
		
		colaboradorDao.updateInfoPessoaisByCpf(colaborador, empresa.getId());
	}
	
	public void testFindParticipantesByAvaliacaoDesempenho()
    {
    	Colaborador avaliador = ColaboradorFactory.getEntity();
    	avaliador.setNome("Avaliador Carrasco");
    	colaboradorDao.save(avaliador);
    	
    	Colaborador avaliado = ColaboradorFactory.getEntity();
    	avaliado.setNome("Pobre Coitado Que Será Demitido");
    	colaboradorDao.save(avaliado);
    	
    	Colaborador avaliado2 = ColaboradorFactory.getEntity();
    	avaliado2.setNome("Pobre Coitado 2");
    	colaboradorDao.save(avaliado2);
    	
    	AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
    	avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
    	
    	ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
    	colaboradorQuestionario.setAvaliador(avaliador);
    	colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
    	colaboradorQuestionarioDao.save(colaboradorQuestionario);
    	
    	ColaboradorQuestionario colaboradorQuestionarioAvaliado = ColaboradorQuestionarioFactory.getEntity();
    	colaboradorQuestionarioAvaliado.setColaborador(avaliado);
    	colaboradorQuestionarioAvaliado.setAvaliacaoDesempenho(avaliacaoDesempenho);
    	colaboradorQuestionarioDao.save(colaboradorQuestionarioAvaliado);
    	
    	ColaboradorQuestionario colaboradorQuestionarioAvaliado2 = ColaboradorQuestionarioFactory.getEntity();
    	colaboradorQuestionarioAvaliado2.setColaborador(avaliado2);
    	colaboradorQuestionarioAvaliado2.setAvaliacaoDesempenho(avaliacaoDesempenho);
    	colaboradorQuestionarioDao.save(colaboradorQuestionarioAvaliado2);
    	
    	// avaliados
    	assertEquals(2, colaboradorDao.findParticipantesDistinctByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), true, null).size());
    	// avaliadores
    	assertEquals(1, colaboradorDao.findParticipantesDistinctByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), false, null).size());
    }
	
	public void testFindParticipantesDistinctByAvaliacaoDesempenho()
	{
		Colaborador avaliado = ColaboradorFactory.getEntity();
		avaliado.setNome("Avaliado");
		colaboradorDao.save(avaliado);

		HistoricoColaborador historicoColaboradorAtual1 = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual1.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAtual1.setColaborador(avaliado);
		historicoColaboradorDao.save(historicoColaboradorAtual1);

		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);

		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setColaborador(avaliado);
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
		ColaboradorQuestionario colaboradorQuestionario2 = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario2.setColaborador(avaliado);
		colaboradorQuestionario2.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario2);

		assertEquals(1, colaboradorDao.findParticipantesDistinctComHistoricoByAvaliacaoDesempenho(avaliacaoDesempenho.getId(), true).size());
	}
	
	public void testGetColaboradoresByTurmas()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaborador1.setNome("Xica ");
		colaborador1.setEmpresa(empresa);
		colaboradorDao.save(colaborador1);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setNome("Xica");
		colaborador2.setEmpresa(empresa);
		colaboradorDao.save(colaborador2);
		
		Turma turma = TurmaFactory.getEntity(1L);
		turma.setDescricao("teste");
		turma.setEmpresa(empresa);
		turmaDao.save(turma);
		
		Collection<Long> turmaIds = new ArrayList<Long>();
		turmaIds.add(turma.getId());
		
		ColaboradorTurma colaboradorTurma1 = ColaboradorTurmaFactory.getEntity(1L);
		colaboradorTurma1.setTurma(turma);
		colaboradorTurma1.setColaborador(colaborador1);
		colaboradorTurmaDao.save(colaboradorTurma1);

		ColaboradorTurma colaboradorTurma2 = ColaboradorTurmaFactory.getEntity(2L);
		colaboradorTurma2.setTurma(turma);
		colaboradorTurma2.setColaborador(colaborador2);
		colaboradorTurmaDao.save(colaboradorTurma2);
		
		assertEquals(new Integer(2), colaboradorDao.qtdColaboradoresByTurmas(turmaIds));
	}	

	public void testFindColabPeriodoExperiencia()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Xica ");
		colaborador.setDesligado(false);
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);

		Colaborador avaliador = ColaboradorFactory.getEntity();
		avaliador.setNome("Augusto ");
		avaliador.setDesligado(false);
		avaliador.setEmpresa(empresa);
		colaboradorDao.save(avaliador);
    	
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
    	estabelecimentoDao.save(estabelecimento);
		
    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
    	areaOrganizacional.setNome("Area 1");
    	areaOrganizacionalDao.save(areaOrganizacional);
				
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(DateUtil.montaDataByString("14/01/2010"));
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoColaborador);
		
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setEmpresa(empresa);
		avaliacao.setTitulo("avaliacao");
		avaliacaoDao.save(avaliacao);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho.setTitulo("titulo");
		avaliacaoDesempenhoDao.save(avaliacaoDesempenho);
				
		ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
		colaboradorQuestionario.setPerformance(0.35);
		colaboradorQuestionario.setColaborador(colaborador);
		colaboradorQuestionario.setAvaliacao(avaliacao);
		colaboradorQuestionario.setRespondida(true);
		colaboradorQuestionario.setRespondidaEm(DateUtil.montaDataByString("15/10/2010"));
		colaboradorQuestionario.setAvaliador(avaliador);
		colaboradorQuestionario.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorQuestionarioDao.save(colaboradorQuestionario);
		
	   	Long[] estabelecimentoIds = new Long[]{estabelecimento.getId()};
    	Long[] areaIds = new Long[]{areaOrganizacional.getId()};
		
		Date periodoIni = DateUtil.montaDataByString("14/08/2010");
		Date periodoFim = DateUtil.montaDataByString("16/12/2010");
		
		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors = colaboradorDao.findColabPeriodoExperiencia(empresa.getId(), periodoIni, periodoFim, avaliacaoDesempenho.getId(), areaIds, estabelecimentoIds);

		assertEquals(1, colaboradors.size());
	}
	
//	@SuppressWarnings("unchecked")
//	public void testFindComHistoricoFuturoSQL()
//	{
//		Cargo cargo = CargoFactory.getEntity();
//		cargo.setNome("Teste");
//		cargoDao.save(cargo);
//
//		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
//		faixaSalarial.setNome("I");
//		faixaSalarial.setCargo(cargo);
//		faixaSalarialDao.save(faixaSalarial);
//		
//		Empresa empresa = EmpresaFactory.getEmpresa(4L);
//		empresaDao.save(empresa);
//		
//		Colaborador joao = ColaboradorFactory.getEntity();
//		joao.setNome("Joao ");
//		joao.setEmpresa(empresa);
//		joao.getPessoal().setCpf("99999888888");
//		colaboradorDao.save(joao);
//		
//		Colaborador maria = ColaboradorFactory.getEntity();
//		maria.setNome("Maria");
//		maria.setEmpresa(empresa);
//		maria.getPessoal().setCpf("12321362391");
//		colaboradorDao.save(maria);
//
//		Colaborador pedro = ColaboradorFactory.getEntity();
//		pedro.setNome("Pedro");
//		pedro.setEmpresa(empresa);
//		pedro.getPessoal().setCpf("12321362391");
//		colaboradorDao.save(pedro);
//		
//		HistoricoColaborador historicoColaboradorJoao = HistoricoColaboradorFactory.getEntity();
//		historicoColaboradorJoao.setData(DateUtil.criarDataMesAno(1, 1, 2008));
//		historicoColaboradorJoao.setColaborador(joao);
//		historicoColaboradorJoao.setFaixaSalarial(faixaSalarial);
//		historicoColaboradorDao.save(historicoColaboradorJoao);
//
//		HistoricoColaborador historicoColaboradorMaria = HistoricoColaboradorFactory.getEntity();
//		historicoColaboradorMaria.setData(DateUtil.criarDataMesAno(1, 1, 2020));
//		historicoColaboradorMaria.setColaborador(maria);
//		historicoColaboradorMaria.setFaixaSalarial(faixaSalarial);
//		historicoColaboradorDao.save(historicoColaboradorMaria);
//
//		HistoricoColaborador historicoColaboradorPedroAntigo = HistoricoColaboradorFactory.getEntity();
//		historicoColaboradorPedroAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2000));
//		historicoColaboradorPedroAntigo.setColaborador(pedro);
//		historicoColaboradorPedroAntigo.setFaixaSalarial(faixaSalarial);
//		historicoColaboradorDao.save(historicoColaboradorPedroAntigo);
//
//		HistoricoColaborador historicoColaboradorPedroAtual = HistoricoColaboradorFactory.getEntity();
//		historicoColaboradorPedroAtual.setData(DateUtil.criarDataMesAno(1, 1, 2010));
//		historicoColaboradorPedroAtual.setColaborador(pedro);
//		historicoColaboradorPedroAtual.setFaixaSalarial(faixaSalarial);
//		historicoColaboradorDao.save(historicoColaboradorPedroAtual);
//
//		Map parametros = new HashMap<String, Object>();
//		parametros.put("cpfBusca", "12321362391");
//		parametros.put("empresaId", empresa.getId());
//		
//		assertEquals(2, colaboradorDao.findComHistoricoFuturoSQL(parametros, 0, 0).size());
//	}	
	
	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
	{
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao)
	{
		this.usuarioDao = usuarioDao;
	}

	public GenericDao<Colaborador> getGenericDao()
	{
		return colaboradorDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setCandidatoDao(CandidatoDao candidatoDao)
	{
		this.candidatoDao = candidatoDao;
	}

	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao)
	{
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao)
	{
		this.estabelecimentoDao = estabelecimentoDao;
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

	public void setMotivoDemissaoDao(MotivoDemissaoDao motivoDemissaoDao)
	{
		this.motivoDemissaoDao = motivoDemissaoDao;
	}

	public void setFaixaSalarialHistoricoDao(FaixaSalarialHistoricoDao faixaSalarialHistoricoDao)
	{
		this.faixaSalarialHistoricoDao = faixaSalarialHistoricoDao;
	}

	public void setTabelaReajusteColaboradorDao(TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao)
	{
		this.tabelaReajusteColaboradorDao = tabelaReajusteColaboradorDao;
	}

	public void setReajusteColaboradorDao(ReajusteColaboradorDao reajusteColaboradorDao)
	{
		this.reajusteColaboradorDao = reajusteColaboradorDao;
	}

	public void setAmbienteDao(AmbienteDao ambienteDao)
	{
		this.ambienteDao = ambienteDao;
	}

	public void setFuncaoDao(FuncaoDao funcaoDao)
	{
		this.funcaoDao = funcaoDao;
	}

	public void setBairroDao(BairroDao bairroDao)
	{
		this.bairroDao = bairroDao;
	}

	public void setPerfilDao(PerfilDao perfilDao) {
		this.perfilDao = perfilDao;
	}

	public void setAvaliacaoDesempenhoDao(AvaliacaoDesempenhoDao avaliacaoDesempenhoDao) {
		this.avaliacaoDesempenhoDao = avaliacaoDesempenhoDao;
	}

	public void setColaboradorQuestionarioDao(ColaboradorQuestionarioDao colaboradorQuestionarioDao) {
		this.colaboradorQuestionarioDao = colaboradorQuestionarioDao;
	}

	public void setCamposExtrasDao(CamposExtrasDao camposExtrasDao)
	{
		this.camposExtrasDao = camposExtrasDao;
	}

	public void setTurmaDao(TurmaDao turmaDao) {
		this.turmaDao = turmaDao;
	}

	public void setColaboradorTurmaDao(ColaboradorTurmaDao colaboradorTurmaDao) {
		this.colaboradorTurmaDao = colaboradorTurmaDao;
	}

	public void setAvaliacaoDao(AvaliacaoDao avaliacaoDao) {
		this.avaliacaoDao = avaliacaoDao;
	}

	public void setGrupoACDao(GrupoACDao grupoACDao) {
		this.grupoACDao = grupoACDao;
	}

}