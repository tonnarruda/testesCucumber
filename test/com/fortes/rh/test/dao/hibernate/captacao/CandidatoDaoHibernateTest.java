package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.CandidatoIdiomaDao;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.captacao.EtapaSeletivaDao;
import com.fortes.rh.dao.captacao.ExperienciaDao;
import com.fortes.rh.dao.captacao.HistoricoCandidatoDao;
import com.fortes.rh.dao.captacao.IdiomaDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.geral.AreaInteresseDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.BairroDao;
import com.fortes.rh.dao.geral.CidadeDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.geral.EstadoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Idioma;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.AvaliacaoCandidatosRelatorio;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.dicionario.Apto;
import com.fortes.rh.model.dicionario.OrigemCandidato;
import com.fortes.rh.model.dicionario.StatusSolicitacao;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.geral.SocioEconomica;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.EtapaSeletivaFactory;
import com.fortes.rh.test.factory.captacao.ExperienciaFactory;
import com.fortes.rh.test.factory.captacao.HistoricoCandidatoFactory;
import com.fortes.rh.test.factory.captacao.IdiomaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.geral.AreaInteresseFactory;
import com.fortes.rh.test.factory.geral.CandidatoIdiomaFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;
@SuppressWarnings("unchecked")
public class CandidatoDaoHibernateTest extends GenericDaoHibernateTest<Candidato>
{
	private CandidatoDao candidatoDao;
	private EmpresaDao empresaDao;
	private AreaInteresseDao areaInteresseDao;
	private CargoDao cargoDao;
	private ConhecimentoDao conhecimentoDao;
	private CandidatoIdiomaDao candidatoIdiomaDao;
	private IdiomaDao idiomaDao;
	private CidadeDao cidadeDao;
	private EstadoDao estadoDao;
	private BairroDao bairroDao;
	private ExperienciaDao experienciaDao;
	private SolicitacaoDao solicitacaoDao;
	private EstabelecimentoDao estabelecimentoDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private HistoricoCandidatoDao historicoCandidatoDao;
	private CandidatoSolicitacaoDao candidatoSolicitacaoDao;
	private EtapaSeletivaDao etapaSeletivaDao;
	private ColaboradorDao colaboradorDao;

	public void setHistoricoCandidatoDao(HistoricoCandidatoDao historicoCandidatoDao)
	{
		this.historicoCandidatoDao = historicoCandidatoDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
	{
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao)
	{
		this.estabelecimentoDao = estabelecimentoDao;
	}

	public void setCandidatoIdiomaDao(CandidatoIdiomaDao candidatoIdiomaDao)
	{
		this.candidatoIdiomaDao = candidatoIdiomaDao;
	}

	public void setIdiomaDao(IdiomaDao idiomaDao)
	{
		this.idiomaDao = idiomaDao;
	}

	public Candidato getEntity()
	{
		return CandidatoFactory.getCandidato();
	}

	public GenericDao<Candidato> getGenericDao()
	{
		return candidatoDao;
	}

	public void testFindByCPF()
	{
		Candidato c3 = getCandidato();
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("0000000000");

		c3 = candidatoDao.save(c3);

		Candidato candidato = candidatoDao.findByCPF("0000000000", null, null, null);
		assertEquals(c3.getId(), candidato.getId());

		candidato = candidatoDao.findByCPF("00000", null, null, null);
		assertNull(candidato);
	}
	
	public void testFindQtdCadastradosByOrigem()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Candidato c1 = getCandidato();
		c1.setDataCadastro(DateUtil.criarDataMesAno(03, 02, 1984));
		c1.setOrigem(OrigemCandidato.CADASTRADO);
		c1.setEmpresa(empresa);
		candidatoDao.save(c1);
		
		Candidato c2 = getCandidato();
		c2.setDataCadastro(DateUtil.criarDataMesAno(02, 03, 1984));
		c2.setOrigem(OrigemCandidato.F2RH);
		c2.setEmpresa(empresa);
		candidatoDao.save(c2);
		
		Candidato c3 = getCandidato();
		c3.setDataCadastro(DateUtil.criarDataMesAno(02, 02, 1984));
		c3.setOrigem(OrigemCandidato.F2RH);
		c3.setEmpresa(empresa);
		candidatoDao.save(c3);
		
		Collection<Candidato> candidatos = candidatoDao.findQtdCadastradosByOrigem(DateUtil.criarDataMesAno(01, 01, 1984), DateUtil.criarDataMesAno(03, 04, 1984));
		assertEquals(2, candidatos.size());
		assertEquals(2, ((Candidato)candidatos.toArray()[0]).getQtdCurriculosCadastrados());//qtd de cadastrados no F2RH
		assertEquals(1, ((Candidato)candidatos.toArray()[1]).getQtdCurriculosCadastrados());//qtd de cadastrados no sistema(CADASTRADO)
	}

	public void testFindByCandidatoId()
	{
		Candidato c3 = getCandidato();
		c3.setNome("bobinho2");
		c3.getPessoal().setCpf("23423");

		c3 = candidatoDao.save(c3);

		Candidato candidato = candidatoDao.findByCandidatoId(c3.getId());
		assertEquals(c3.getId(), candidato.getId());
	}

	public void testFind()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresa2 = empresaDao.save(empresa2);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("11111111111");

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222222");

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa2);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		Collection<Candidato> candidatos = candidatoDao.find(1, 10, "", "", empresa.getId(), "", 'T', null, null,null, false, false);

		assertFalse(candidatos.isEmpty());
		assertEquals(2, candidatos.size());

		candidatos = candidatoDao.find(1, 10, "chi", "", empresa.getId(), "", 'T', null, null,null, false, false);
		assertEquals(1, candidatos.size());
		assertEquals(c1.getId(), ((Candidato)(candidatos.toArray()[0])).getId(), 'T');

		candidatos = candidatoDao.find(1, 10, "chi", "11111111111", empresa.getId(), "", 'T', null, null,null, false, false);
		assertEquals(1, candidatos.size());
		assertEquals(c1.getId(), ((Candidato)(candidatos.toArray()[0])).getId(), 'T');

		candidatos = candidatoDao.find(1, 10, "chi", "22222", empresa.getId(), "", 'T', null, null,null, false, false);
		assertTrue(candidatos.isEmpty());

		candidatos = candidatoDao.find(1, 10, "bob", "", empresa.getId(), "", 'T', null, null,null, false, false);
		assertEquals(1, candidatos.size());

		assertEquals(c2.getId(), ((Candidato)(candidatos.toArray()[0])).getId(), 'T');
	}


	public void testFindVisualizar()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.setDisponivel(true);
		c1.getPessoal().setCpf("11111111111");

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.setDisponivel(false);
		c2.getPessoal().setCpf("22222222221");

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.setDisponivel(false);
		c3.getPessoal().setCpf("3333333333");

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		Collection<Candidato> candidatos = candidatoDao.find(1, 10, "", "", empresa.getId(), "", 'T', null, null,null, false, false);
		assertEquals(3, candidatos.size());

		candidatos = candidatoDao.find(1, 10, "", "", empresa.getId(), "", 'D', null, null,null, false, false);
		assertEquals(1, candidatos.size());

		candidatos = candidatoDao.find(1, 10, "", "", empresa.getId(), "", 'I', null, null,null, false, false);
		assertEquals(2, candidatos.size());
	}

	public void testUpdateBlackList()
	{
		Candidato c1 = getCandidato();
		c1.setId(24532454235423L);
		candidatoDao.save(c1);

		String observacao = "teste";

		candidatoDao.updateBlackList(observacao, true, c1.getId());

		@SuppressWarnings("unused")
		Collection<Candidato> candidatos = candidatoDao.findById(new Long[]{c1.getId()});
//		assertEquals(true, ((Candidato)candidatos.toArray()[0]).isBlackList());
//		assertEquals(observacao, ((Candidato)candidatos.toArray()[0]).getObservacaoBlackList());
	}

	private Candidato getCandidato()
	{
		Candidato candidato = new Candidato();
		candidato.setId(null);
		candidato.setNome("nome candidato");
		candidato.setObservacao("observacao");

		SocioEconomica socioEconomica = new SocioEconomica();
		candidato.setSocioEconomica(socioEconomica);

		Endereco endereco = new Endereco();
		endereco.setLogradouro("logradouro");
		endereco.setNumero("00");
		endereco.setComplemento("complemento");
		endereco.setBairro("bairro");
		endereco.setCidade(null);
		endereco.setUf(null);
		endereco.setCep("0000000");
		candidato.setEndereco(endereco);

		Contato contato = new Contato();
		contato.setEmail("mail.com");
		contato.setFoneFixo("00000000");
		contato.setFoneCelular("00000000");
		candidato.setContato(contato);

		Pessoal pessoal	= new Pessoal();
		pessoal.setDataNascimento(new Date());
		pessoal.setEstadoCivil("e");
		pessoal.setEscolaridade("e");
		pessoal.setSexo('m');
		pessoal.setConjugeTrabalha(false);
		pessoal.setCpf("00000000000");
		candidato.setPessoal(pessoal);

		return candidato;
	}

	public void testGetCount()
	{
		String nomeBusca = "teste";
		String cpfBusca = "123";

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setNome(nomeBusca);
		c1.setCpf(cpfBusca);
		c1.setEmpresa(empresa);
		c1.setDisponivel(true);
		c1 = candidatoDao.save(c1);

		Integer count = candidatoDao.getCount(nomeBusca,cpfBusca,empresa.getId(), "", 'T', null, null,null, false, false);

		assertEquals(1, (int)count);
	}

	public void testGetCountDisponivel()
	{
		String nomeBusca = "teste";
		String cpfBusca = "123";

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setNome(nomeBusca);
		c1.setCpf(cpfBusca);
		c1.setEmpresa(empresa);
		c1.setDisponivel(true);
		c1 = candidatoDao.save(c1);

		Integer count = candidatoDao.getCount(nomeBusca,cpfBusca,empresa.getId(), "", 'D', null, null,null, false, false);

		assertEquals(1, (int)count);
	}

	public void testGetCountIndisponivel()
	{
		String nomeBusca = "teste";
		String cpfBusca = "123";

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setNome(nomeBusca);
		c1.setCpf(cpfBusca);
		c1.setEmpresa(empresa);
		c1.setDisponivel(false);
		c1 = candidatoDao.save(c1);

		Integer count = candidatoDao.getCount(nomeBusca,cpfBusca,empresa.getId(), "", 'I', null, null,null, false, false);

		assertEquals(1, (int)count);
	}

	public void testGetCountPeriodo()
	{
		Date dataIni = DateUtil.criarDataMesAno(01, 02, 2000);
		Date dataFim = DateUtil.criarDataMesAno(30, 03, 2000);

		String nomeBusca = "teste";
		String cpfBusca = "123";

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setNome(nomeBusca);
		c1.setDataAtualizacao(DateUtil.criarDataMesAno(15, 03, 2000));
		c1.setCpf(cpfBusca);
		c1.setEmpresa(empresa);
		c1.setDisponivel(false);
		c1 = candidatoDao.save(c1);

		Integer count = candidatoDao.getCount(nomeBusca,cpfBusca,empresa.getId(), "", 'I', dataIni, dataFim,null, false, false);

		assertEquals(1, (int)count);
	}

	public void testGetCountBusca()
	{
		String nomeBusca = "teste";
		String cpfBusca = "123";

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(123L);
		empresa = empresaDao.save(empresa);

		Map parametros = new HashMap();

		Candidato c1 = getCandidato();
		c1.setNome(nomeBusca);
		c1.setCpf(cpfBusca);
		c1.setEmpresa(empresa);
		c1.setDisponivel(true);
		c1 = candidatoDao.save(c1);

		Integer count = candidatoDao.getCount(parametros, empresa.getId());

		assertEquals(1, (int)count);
	}

	public void testGetCountBuscaIndicadoPor()
	{
		String nomeBusca = "teste";
		String cpfBusca = "123";

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(123L);
		empresa = empresaDao.save(empresa);

		Map parametros = new HashMap();
		parametros.put("indicadoPor", "maria");

		Pessoal pessoal = new Pessoal();
		pessoal.setIndicadoPor("maria");

		Candidato c1 = getCandidato();
		c1.setPessoal(pessoal);
		c1.setNome(nomeBusca);
		c1.setCpf(cpfBusca);
		c1.setEmpresa(empresa);
		c1.setDisponivel(true);
		c1 = candidatoDao.save(c1);

		Integer count = candidatoDao.getCount(parametros, empresa.getId());

		assertEquals(1, (int)count);
	}

	public void testFindBuscaCPF() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("1111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("22222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Map parametros = new HashMap();
		parametros.put("cpfBusca", c3.getPessoal().getCpf());
		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertFalse(candidatos.isEmpty());
		assertEquals(1, candidatos.size());
	}

	public void testFindBuscaAreas() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		AreaInteresse areaInteresse = AreaInteresseFactory.getAreaInteresse();
		areaInteresse = areaInteresseDao.save(areaInteresse);

		Collection<AreaInteresse> areaInteresses = new ArrayList<AreaInteresse>();
		areaInteresses.add(areaInteresse);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("1111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.setAreasInteresse(areaInteresses);

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);
		c3.setAreasInteresse(areaInteresses);

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Map parametros = new HashMap();
		Long[] areaIds = {areaInteresse.getId()};
		parametros.put("areasIds", areaIds);
		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertFalse(candidatos.isEmpty());
		assertEquals(2, candidatos.size());
	}

	public void testFindBuscaBairros() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Bairro bairro = new Bairro();
		bairro.setNome("bairro");
		bairro = bairroDao.save(bairro);

		Bairro bairro2 = new Bairro();
		bairro2.setNome("bairro2");
		bairro2 = bairroDao.save(bairro2);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.getEndereco().setBairro(bairro.getNome());

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);
		c2.getEndereco().setBairro(bairro.getNome());

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);
		c3.getEndereco().setBairro(bairro2.getNome());

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Map parametros = new HashMap();
		String[] bairros = {bairro.getNome()};
		parametros.put("bairros", bairros);
		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertFalse(candidatos.isEmpty());
		assertEquals(2, candidatos.size());
	}

	// 1:todas
	// 2:qualquer
	// 3:frase exata
	public void testFindBuscaPalavrasChaves() throws Exception
	{
		String palavra = "teste palavra";
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setOcrTexto(palavra);

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setOcrTexto(palavra);

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setOcrTexto("erro");

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Map parametros = new HashMap();
		parametros.put("palavrasChave", palavra);
		parametros.put("formas", "1");
		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertFalse(candidatos.isEmpty());
		assertEquals(2, candidatos.size());

		candidatos.clear();
		parametros.put("formas", "2");
		candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertFalse(candidatos.isEmpty());
		assertEquals(2, candidatos.size());

		candidatos.clear();
		parametros.put("formas", "3");
		candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertFalse(candidatos.isEmpty());
		assertEquals(2, candidatos.size());
	}

	public void testFindBuscaCargos() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("carpinteiro");
		cargo = cargoDao.save(cargo);

		Collection<Cargo> cargos = new ArrayList<Cargo>();
		cargos.add(cargo);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.setCargos(cargos);

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Map parametros = new HashMap();
		Long[] cargosIds = {cargo.getId()};
		parametros.put("cargosIds", cargosIds);
		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertFalse(candidatos.isEmpty());
		assertEquals(1, candidatos.size());

		parametros = new HashMap();
		String[] cargosNomes = {cargo.getNome()};
		parametros.put("cargosNomes", cargosNomes);
		candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);
		
		assertFalse(candidatos.isEmpty());
		assertEquals(1, candidatos.size());
	}

	public void testFindBuscaConhecimentos() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento = conhecimentoDao.save(conhecimento);

		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();
		conhecimentos.add(conhecimento);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("1111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.setConhecimentos(conhecimentos);

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);
		c2.setConhecimentos(conhecimentos);

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);
		c3.setConhecimentos(conhecimentos);

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Map parametros = new HashMap();
		Long[] conhecimentosIds = {conhecimento.getId()};
		parametros.put("conhecimentosIds", conhecimentosIds);
		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertFalse(candidatos.isEmpty());
		assertEquals(3, candidatos.size());
	}

	public void testFindBuscaIdiomaComNivel() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Idioma idioma = IdiomaFactory.getIdioma();
		idioma = idiomaDao.save(idioma);

		CandidatoIdioma candidatoIdioma = CandidatoIdiomaFactory.getCandidatoIdioma();
		candidatoIdioma.setIdioma(idioma);
		candidatoIdioma.setNivel('A');
		candidatoIdioma = candidatoIdiomaDao.save(candidatoIdioma);

		CandidatoIdioma candidatoIdioma2 = CandidatoIdiomaFactory.getCandidatoIdioma();
		candidatoIdioma2.setIdioma(idioma);
		candidatoIdioma2.setNivel('A');
		candidatoIdioma2 = candidatoIdiomaDao.save(candidatoIdioma2);

		CandidatoIdioma candidatoIdioma3 = CandidatoIdiomaFactory.getCandidatoIdioma();
		candidatoIdioma3.setIdioma(idioma);
		candidatoIdioma3.setNivel('B');
		candidatoIdioma3 = candidatoIdiomaDao.save(candidatoIdioma3);

		Collection<CandidatoIdioma> candidatoIdiomas1 = new ArrayList<CandidatoIdioma>();
		candidatoIdiomas1.add(candidatoIdioma);

		Collection<CandidatoIdioma> candidatoIdiomas2 = new ArrayList<CandidatoIdioma>();
		candidatoIdiomas2.add(candidatoIdioma2);

		Collection<CandidatoIdioma> candidatoIdiomas3 = new ArrayList<CandidatoIdioma>();
		candidatoIdiomas3.add(candidatoIdioma3);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.setCandidatoIdiomas(candidatoIdiomas1);

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);
		c2.setCandidatoIdiomas(candidatoIdiomas1);

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);
		c3.setCandidatoIdiomas(candidatoIdiomas2);

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		candidatoIdioma.setCandidato(c1);
		candidatoIdioma2.setCandidato(c2);
		candidatoIdioma3.setCandidato(c3);

		candidatoIdiomaDao.update(candidatoIdioma);
		candidatoIdiomaDao.update(candidatoIdioma2);
		candidatoIdiomaDao.update(candidatoIdioma3);

		Map parametros = new HashMap();
		parametros.put("idioma", idioma.getId());
		parametros.put("nivel", candidatoIdioma.getNivel());

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertEquals(2, candidatos.size());
	}

	public void testFindBuscaIdiomaSemNivel() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Idioma idioma = IdiomaFactory.getIdioma();
		idioma = idiomaDao.save(idioma);

		CandidatoIdioma candidatoIdioma = CandidatoIdiomaFactory.getCandidatoIdioma();
		candidatoIdioma.setIdioma(idioma);
		candidatoIdioma.setNivel('A');
		candidatoIdioma = candidatoIdiomaDao.save(candidatoIdioma);

		CandidatoIdioma candidatoIdioma2 = CandidatoIdiomaFactory.getCandidatoIdioma();
		candidatoIdioma2.setIdioma(idioma);
		candidatoIdioma2.setNivel('A');
		candidatoIdioma2 = candidatoIdiomaDao.save(candidatoIdioma2);

		CandidatoIdioma candidatoIdioma3 = CandidatoIdiomaFactory.getCandidatoIdioma();
		candidatoIdioma3.setIdioma(idioma);
		candidatoIdioma3.setNivel('B');
		candidatoIdioma3 = candidatoIdiomaDao.save(candidatoIdioma3);

		Collection<CandidatoIdioma> candidatoIdiomas1 = new ArrayList<CandidatoIdioma>();
		candidatoIdiomas1.add(candidatoIdioma);

		Collection<CandidatoIdioma> candidatoIdiomas2 = new ArrayList<CandidatoIdioma>();
		candidatoIdiomas2.add(candidatoIdioma2);

		Collection<CandidatoIdioma> candidatoIdiomas3 = new ArrayList<CandidatoIdioma>();
		candidatoIdiomas3.add(candidatoIdioma3);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.setCandidatoIdiomas(candidatoIdiomas1);

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);
		c2.setCandidatoIdiomas(candidatoIdiomas1);

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);
		c3.setCandidatoIdiomas(candidatoIdiomas2);

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		candidatoIdioma.setCandidato(c1);
		candidatoIdioma2.setCandidato(c2);
		candidatoIdioma3.setCandidato(c3);

		candidatoIdiomaDao.update(candidatoIdioma);
		candidatoIdiomaDao.update(candidatoIdioma2);
		candidatoIdiomaDao.update(candidatoIdioma3);

		Map parametros = new HashMap();
		parametros.put("idioma", idioma.getId());
		parametros.put("nivel", null);

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertEquals(3, candidatos.size());
	}


	public void testFindBuscaNome() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		Map parametros = new HashMap();
		parametros.put("nomeBusca", c1.getNome());

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertEquals(1, candidatos.size());
	}

	public void testFindBuscaDataCadIni() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.setDataAtualizacao(DateUtil.criarDataMesAno(02, 01, 2008));

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);
		c2.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2007));

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);
		c3.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2006));

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		Map parametros = new HashMap();
		parametros.put("dataCadIni", DateUtil.criarDataMesAno(01, 01, 2008));

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertEquals(1, candidatos.size());
	}

	public void testFindBuscaDataCadFim() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2008));

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);
		c2.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2007));

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);
		c3.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2006));

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		Map parametros = new HashMap();
		parametros.put("dataCadFim", DateUtil.criarDataMesAno(01, 02, 2008));

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertEquals(3, candidatos.size());
	}

	public void testFindBuscaSexo() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.getPessoal().setSexo('M');

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);
		c2.getPessoal().setSexo('M');

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);
		c3.getPessoal().setSexo('F');

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		Map parametros = new HashMap();
		parametros.put("sexo", String.valueOf(c1.getPessoal().getSexo()));

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertEquals(2, candidatos.size());
	}

	public void testFindBuscaIdadeMin() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(07, 03, 2009));

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);
		c2.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(07, 03, 1982));

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);
		c3.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(07, 03, 1981));

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		Map parametros = new HashMap();
		parametros.put("idadeMin", "26");

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertEquals(2, candidatos.size());
	}

	public void testFindBuscaIdadeMax() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(07, 03, 2009));

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);
		c2.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(07, 03, 1982));

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);
		c3.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(07, 03, 1981));

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		Map parametros = new HashMap();
		parametros.put("idadeMax", "26");

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertEquals(1, candidatos.size());
	}

	public void testFindBuscaCidade() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Estado estado = EstadoFactory.getEntity();
		estado = estadoDao.save(estado);

		Cidade cidade = CidadeFactory.getEntity();
		cidade.setUf(estado);
		cidade = cidadeDao.save(cidade);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.getEndereco().setCidade(cidade);

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);
		c2.getEndereco().setCidade(cidade);

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);
		c3.getEndereco().setCidade(cidade);

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		Map parametros = new HashMap();
		parametros.put("cidade", cidade.getId());

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertEquals(3, candidatos.size());
	}

	public void testFindBuscaUf() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Estado estado = EstadoFactory.getEntity();
		estado = estadoDao.save(estado);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.getEndereco().setUf(estado);

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);
		c3.getEndereco().setUf(estado);

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		Map parametros = new HashMap();
		parametros.put("uf", estado.getId());

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertEquals(2, candidatos.size());
	}

	public void testFindBuscaEscolaridade() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);


		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.getPessoal().setEscolaridade("852");

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);
		c2.getPessoal().setEscolaridade("987");

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);
		c3.getPessoal().setEscolaridade("654");

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		Map parametros = new HashMap();
		parametros.put("escolaridade", "987");

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertEquals(1, candidatos.size());
	}

	public void testFindBuscaVeiculo() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.getSocioEconomica().setPossuiVeiculo(true);

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);
		c2.getSocioEconomica().setPossuiVeiculo(true);

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);
		c3.getSocioEconomica().setPossuiVeiculo(false);

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Map parametros = new HashMap();
		parametros.put("veiculo", 'S');

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertEquals(2, candidatos.size());
	}

	public void testFindBuscaCandidatosComExperiencia() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.getSocioEconomica().setPossuiVeiculo(true);

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);
		c2.getSocioEconomica().setPossuiVeiculo(true);

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);
		c3.getSocioEconomica().setPossuiVeiculo(false);

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		Map parametros = new HashMap();
		parametros.put("candidatosComExperiencia", new Long[]{c1.getId(),c2.getId()});

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertEquals(2, candidatos.size());
	}

	public void testFindBuscaCandidatosComSolicitacao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.getSocioEconomica().setPossuiVeiculo(true);

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);
		c2.getSocioEconomica().setPossuiVeiculo(true);

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);
		c3.getSocioEconomica().setPossuiVeiculo(false);

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		CandidatoSolicitacao candidadatoSolicitacao = new CandidatoSolicitacao();
		candidadatoSolicitacao.setCandidato(c1);
		candidadatoSolicitacao.setSolicitacaoId(1L);

		Map parametros = new HashMap();
		parametros.put("candidatosComExperiencia", new Long[]{c1.getId(),c2.getId()});

		Collection<Long> idsCandidatos = new ArrayList<Long>();
		idsCandidatos.add(c1.getId());

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, empresa.getId(), idsCandidatos, false, null, null);

		assertEquals(1, candidatos.size());
	}

	//TODO: Pau na paginação - corrigir depois
//	public void testGetCountParametros()
//	{
//		Empresa empresa = EmpresaFactory.getEmpresa();
//		empresa = empresaDao.save(empresa);
//
//		Estado estado = EstadoFactory.getEntity();
//		estado = estadoDao.save(estado);
//
//		Cidade cidade = CidadeFactory.getEntity();
//		cidade.setUf(estado);
//		cidade = cidadeDao.save(cidade);
//
//		Candidato c1 = getCandidato();
//		c1.setEmpresa(empresa);
//		c1.setNome("chico");
//		c1.getPessoal().setCpf("11111111111");
//		c1.setDisponivel(true);
//		c1.setContratado(false);
//		c1.setBlackList(false);
//		c1.getEndereco().setCidade(cidade);
//
//		Candidato c2 = getCandidato();
//		c2.setEmpresa(empresa);
//		c2.setNome("bob");
//		c2.getPessoal().setCpf("222222222221");
//		c2.setDisponivel(true);
//		c2.setContratado(false);
//		c2.setBlackList(false);
//		c2.getEndereco().setCidade(cidade);
//
//		Candidato c3 = getCandidato();
//		c3.setEmpresa(empresa);
//		c3.setNome("bobinho");
//		c3.getPessoal().setCpf("3333333333333");
//		c3.setDisponivel(true);
//		c3.setContratado(false);
//		c3.setBlackList(false);
//		c3.getEndereco().setCidade(cidade);
//
//		c1 = candidatoDao.save(c1);
//		c2 = candidatoDao.save(c2);
//		c3 = candidatoDao.save(c3);
//
//		Map parametros = new HashMap();
//		parametros.put("cidade", cidade.getId());
//
//		int countCandidatos = candidatoDao.getCount(parametros, empresa.getId());
//
//		assertEquals(3, countCandidatos);
//	}

	public void testFindCandidatosById()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Estado estado = EstadoFactory.getEntity();
		estado = estadoDao.save(estado);

		Cidade cidade = CidadeFactory.getEntity();
		cidade.setUf(estado);
		cidade = cidadeDao.save(cidade);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.getEndereco().setCidade(cidade);

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);
		c2.getEndereco().setCidade(cidade);

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);
		c3.getEndereco().setCidade(cidade);

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		Long[] ids = {c1.getId(),c2.getId(),c3.getId()};

		Collection<Candidato> candidatos = candidatoDao.findCandidatosById(ids);

		assertEquals(3, candidatos.size());
	}

	public void testGetConhecimentosByCandidatoId()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento = conhecimentoDao.save(conhecimento);

		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();
		conhecimentos.add(conhecimento);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.setConhecimentos(conhecimentos);

		c1 = candidatoDao.save(c1);

		List conhecimentosRetorno  = candidatoDao.getConhecimentosByCandidatoId(c1.getId());

		assertEquals(conhecimento.getNome(), conhecimentosRetorno.get(0));
	}

	public void testGetCandidatosByCpf()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Estado estado = EstadoFactory.getEntity();
		estado = estadoDao.save(estado);

		Cidade cidade = CidadeFactory.getEntity();
		cidade.setUf(estado);
		cidade = cidadeDao.save(cidade);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.getEndereco().setCidade(cidade);

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.getPessoal().setCpf("2222222221");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);
		c2.getEndereco().setCidade(cidade);

		Candidato c3 = getCandidato();
		c3.setEmpresa(empresa);
		c3.setNome("bobinho");
		c3.getPessoal().setCpf("33333333333");
		c3.setDisponivel(true);
		c3.setContratado(false);
		c3.setBlackList(false);
		c3.getEndereco().setCidade(cidade);

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);
		c3 = candidatoDao.save(c3);

		String[] cpfs = {c1.getPessoal().getCpf(),c2.getPessoal().getCpf(),c3.getPessoal().getCpf()};

		Collection<Candidato> candidatos = candidatoDao.getCandidatosByCpf(cpfs,empresa.getId());

		assertEquals(3, candidatos.size());
	}

	public void testFindCandidatoCpf()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);

		c1 = candidatoDao.save(c1);

		Candidato candidatoRetorno = candidatoDao.findCandidatoCpf(c1.getPessoal().getCpf(), empresa.getId());

		assertEquals(c1.getId(), candidatoRetorno.getId());
	}

	public void testUpdateSetContratado()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);

		c1 = candidatoDao.save(c1);

		candidatoDao.updateSetContratado(c1.getId());

		@SuppressWarnings("unused")
		Candidato retorno = candidatoDao.findByCandidatoId(c1.getId());

//		assertTrue(retorno.isContratado());
	}

	public void testFindByIdProjection()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setExamePalografico("?????--%%%á#%**&¨%$#@!");

		c1 = candidatoDao.save(c1);

		Candidato candidatoRetorno = candidatoDao.findByIdProjection(c1.getId());

		assertEquals(c1.getId(), candidatoRetorno.getId());
		assertEquals(c1.getExamePalografico(), candidatoRetorno.getExamePalografico());
	}


	public void testAtualizaSenha()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);

		c1 = candidatoDao.save(c1);

		candidatoDao.atualizaSenha(c1.getId(),"novaSenha");

		Candidato retorno = candidatoDao.findByIdProjection(c1.getId());

		assertEquals("novaSenha", retorno.getSenha());
	}

	public void testAtualizaTextoOcr()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.getPessoal().setCpf("111111111");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.setOcrTexto("texto ocr");

		c1 = candidatoDao.save(c1);

		candidatoDao.atualizaTextoOcr(c1);

		Collection<Candidato> retorno = candidatoDao.findToList(new String[]{"ocrTexto"}, new String[]{"ocrTexto"}, new String[]{"id"}, new Object[]{c1.getId()});
		assertEquals("texto ocr", ((Candidato) retorno.toArray()[0]).getOcrTexto());
	}

	public void testFindConhecimentosByCandidatoId()
	{
		Conhecimento conhecimento1 = ConhecimentoFactory.getConhecimento();
		conhecimento1 = conhecimentoDao.save(conhecimento1);

		Conhecimento conhecimento2 = ConhecimentoFactory.getConhecimento();
		conhecimento2 = conhecimentoDao.save(conhecimento2);

		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();
		conhecimentos.add(conhecimento1);
		conhecimentos.add(conhecimento2);

		Candidato candidato = getCandidato();
		candidato.setConhecimentos(conhecimentos);

		candidato = candidatoDao.save(candidato);

		List lista = candidatoDao.findConhecimentosByCandidatoId(candidato.getId());

		assertEquals(2, lista.size());
	}

	public void testFindCargosByCandidatoId()
	{
		Cargo cargo1 = CargoFactory.getEntity();
		cargo1 = cargoDao.save(cargo1);

		Cargo cargo2 = CargoFactory.getEntity();
		cargo2 = cargoDao.save(cargo2);

		Collection<Cargo> cargos = new ArrayList<Cargo>();
		cargos.add(cargo1);
		cargos.add(cargo2);

		Candidato candidato = getCandidato();
		candidato.setCargos(cargos);

		candidato = candidatoDao.save(candidato);

		List lista = candidatoDao.findCargosByCandidatoId(candidato.getId());

		assertEquals(2, lista.size());
	}

	public void testFindAreasInteressesByCandidatoId()
	{
		AreaInteresse areaInteresse1 = AreaInteresseFactory.getAreaInteresse();
		areaInteresse1 = areaInteresseDao.save(areaInteresse1);

		AreaInteresse areaInteresse2 = AreaInteresseFactory.getAreaInteresse();
		areaInteresse2 = areaInteresseDao.save(areaInteresse2);

		Collection<AreaInteresse> areaInteresses = new ArrayList<AreaInteresse>();
		areaInteresses.add(areaInteresse1);
		areaInteresses.add(areaInteresse2);

		Candidato candidato = getCandidato();
		candidato.setAreasInteresse(areaInteresses);

		candidato = candidatoDao.save(candidato);

		List lista = candidatoDao.findAreaInteressesByCandidatoId(candidato.getId());

		assertEquals(2, lista.size());

	}

	public void testGetCandidatoByNome()
	{
		Candidato candidato = getCandidato();
		candidato.setNome("Hubster Haas Silva");
		candidato = candidatoDao.save(candidato);

		Candidato candidato2 = getCandidato();
		candidato2.setNome("Antonio Haas");
		candidato2 = candidatoDao.save(candidato2);

		Collection<Candidato> candidatos = candidatoDao.getCandidatosByNome("Hubster Haas");

		assertEquals(1, candidatos.size());
	}

	public void testUpdateSenha()
	{
		Candidato candidato = getCandidato();
		candidato.setSenha(StringUtil.encodeString("1234"));
		candidato.setNome("Hubster Haas Silva");
		candidato = candidatoDao.save(candidato);

		candidatoDao.updateSenha(candidato.getId(), candidato.getSenha(), StringUtil.encodeString("123456"));
//TODO erro no teste findById não funciona no teste
//		Candidato candidatoRetorno = candidatoDao.findById(candidato.getId());
//
//		assertEquals(StringUtil.encodeString("123456"), candidatoRetorno.getSenha());
	}

	public void testGetCandidatosByExperiencia()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Estado estado = EstadoFactory.getEntity();
		estado = estadoDao.save(estado);

		Cidade cidade = CidadeFactory.getEntity();
		cidade.setUf(estado);
		cidade = cidadeDao.save(cidade);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setNome("chico");
		c1.setDisponivel(true);
		c1.setContratado(false);
		c1.setBlackList(false);
		c1.getEndereco().setCidade(cidade);

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setNome("bob");
		c2.setDisponivel(true);
		c2.setContratado(false);
		c2.setBlackList(false);
		c2.getEndereco().setCidade(cidade);

		c1 = candidatoDao.save(c1);
		c2 = candidatoDao.save(c2);

		Cargo ca1 = CargoFactory.getEntity();
		ca1 = cargoDao.save(ca1);

		Cargo ca2 = CargoFactory.getEntity();
		ca2 = cargoDao.save(ca2);

		Experiencia exp1 = new Experiencia();
		exp1.setCandidato(c1);
		exp1.setCargo(ca1);
		exp1.setEmpresa("e1");
		exp1.setDataAdmissao(new Date());
		exp1 = experienciaDao.save(exp1);

		Experiencia exp2 = new Experiencia();
		exp2.setCandidato(c1);
		exp2.setCargo(ca1);
		exp2.setEmpresa("e2");
		exp2.setDataAdmissao(new Date());
		exp2 = experienciaDao.save(exp2);

		Experiencia exp3 = new Experiencia();
		exp3.setCandidato(c2);
		exp3.setCargo(ca2);
		exp3.setEmpresa("e3");
		exp3.setDataAdmissao(new Date());
		exp3 = experienciaDao.save(exp3);

		Long[] experienciasIds = {ca1.getId()};

		Map param = new HashMap();
		param.put("experiencias", experienciasIds);

		Collection<Candidato> candidatos = candidatoDao.getCandidatosByExperiencia(param, empresa.getId());

		assertEquals(1, candidatos.size());
	}

	public void testFindRelatorioAvaliacaoCandidatos()
	{
		Date hoje = new Date();
		Calendar dataDoisMesesAtras = Calendar.getInstance();
    	dataDoisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar dataTresMesesAtras = Calendar.getInstance();
    	dataTresMesesAtras.add(Calendar.MONTH, -3);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);

		Cargo cargo1 = CargoFactory.getEntity();
		cargoDao.save(cargo1);
		Collection<Cargo> cargos = new ArrayList<Cargo>();
		cargos.add(cargo1);

		Cargo cargo2 = CargoFactory.getEntity();
		cargoDao.save(cargo2);
		Collection<Cargo> cargos2 = new ArrayList<Cargo>();
		cargos2.add(cargo2);

		Candidato candidato1 = getCandidato();
		candidato1.setEmpresa(empresa);
		candidato1.setNome("chico");
		candidato1.setCargos(cargos);
		candidatoDao.save(candidato1);

		Candidato candidatoForaDaConsultaCargoDiferente = getCandidato();
		candidatoForaDaConsultaCargoDiferente.setEmpresa(empresa);
		candidatoForaDaConsultaCargoDiferente.setNome("Zé Mané");
		candidatoForaDaConsultaCargoDiferente.setCargos(cargos2);
		candidatoDao.save(candidatoForaDaConsultaCargoDiferente);

		Solicitacao solicitacaoEncerrada = new Solicitacao();
		solicitacaoEncerrada.setEstabelecimento(estabelecimento);
		solicitacaoEncerrada.setAreaOrganizacional(areaOrganizacional);
		solicitacaoEncerrada.setDataEncerramento(dataDoisMesesAtras.getTime());
		solicitacaoDao.save(solicitacaoEncerrada);
		
		Solicitacao solicitacaoAberta = new Solicitacao();
		solicitacaoAberta.setEstabelecimento(estabelecimento);
		solicitacaoAberta.setAreaOrganizacional(areaOrganizacional);
		solicitacaoAberta.setDataEncerramento(DateUtil.criarDataMesAno(01, 01, 2030));
		solicitacaoDao.save(solicitacaoAberta);

		Solicitacao solicitacaoFora = new Solicitacao();
		solicitacaoFora.setEstabelecimento(estabelecimento);
		solicitacaoFora.setAreaOrganizacional(areaOrganizacional);
		solicitacaoDao.save(solicitacaoFora);

		CandidatoSolicitacao candidatoSolicitacaoEncerrada = new CandidatoSolicitacao();
		candidatoSolicitacaoEncerrada.setCandidato(candidato1);
		candidatoSolicitacaoEncerrada.setSolicitacao(solicitacaoEncerrada);
		candidatoSolicitacaoDao.save(candidatoSolicitacaoEncerrada);
		
		CandidatoSolicitacao candidatoSolicitacaoAberta = new CandidatoSolicitacao();
		candidatoSolicitacaoAberta.setCandidato(candidatoForaDaConsultaCargoDiferente);
		candidatoSolicitacaoAberta.setSolicitacao(solicitacaoAberta);
		candidatoSolicitacaoDao.save(candidatoSolicitacaoAberta);

		CandidatoSolicitacao candidatoSolicitacaoFora = new CandidatoSolicitacao();
		candidatoSolicitacaoFora.setCandidato(candidatoForaDaConsultaCargoDiferente);
		candidatoSolicitacaoFora.setSolicitacao(solicitacaoFora);
		candidatoSolicitacaoDao.save(candidatoSolicitacaoFora);

		EtapaSeletiva etapaSeletiva1 = EtapaSeletivaFactory.getEntity();
		etapaSeletiva1.setNome("Entrevista ");
		etapaSeletivaDao.save(etapaSeletiva1);

		EtapaSeletiva etapaSeletiva2 = EtapaSeletivaFactory.getEntity();
		etapaSeletiva2.setNome("Dinâmica de Grupo ");
		etapaSeletivaDao.save(etapaSeletiva2);

		HistoricoCandidato historicoCandidato1 = HistoricoCandidatoFactory.getEntity();
		historicoCandidato1.setCandidatoSolicitacao(candidatoSolicitacaoEncerrada);
		historicoCandidato1.setData(dataDoisMesesAtras.getTime());
		historicoCandidato1.setEtapaSeletiva(etapaSeletiva1);
		historicoCandidato1.setApto(Apto.SIM);
		historicoCandidatoDao.save(historicoCandidato1);

		HistoricoCandidato historicoCandidato1_ = HistoricoCandidatoFactory.getEntity();
		historicoCandidato1_.setCandidatoSolicitacao(candidatoSolicitacaoAberta);
		historicoCandidato1_.setData(dataDoisMesesAtras.getTime());
		historicoCandidato1_.setEtapaSeletiva(etapaSeletiva2);
		historicoCandidato1_.setApto(Apto.NAO);
		historicoCandidatoDao.save(historicoCandidato1_);

		HistoricoCandidato historicoCandidatoForaDaConsulta = HistoricoCandidatoFactory.getEntity();
		historicoCandidatoForaDaConsulta.setCandidatoSolicitacao(candidatoSolicitacaoFora);
		historicoCandidatoForaDaConsulta.setData(dataDoisMesesAtras.getTime());
		historicoCandidatoForaDaConsulta.setEtapaSeletiva(etapaSeletiva2);
		historicoCandidatoForaDaConsulta.setApto(Apto.NAO);
		historicoCandidatoDao.save(historicoCandidatoForaDaConsulta);

		Collection<AvaliacaoCandidatosRelatorio> resultadosComStatusSolicitacaoTodas = candidatoDao.findRelatorioAvaliacaoCandidatos(dataDoisMesesAtras.getTime(), hoje, empresa.getId(), new Long[]{estabelecimento.getId()}, new Long[]{areaOrganizacional.getId()}, new Long[]{cargo1.getId()}, StatusSolicitacao.TODAS);
		Collection<AvaliacaoCandidatosRelatorio> resultadosComStatusSolicitacaoEncerradas = candidatoDao.findRelatorioAvaliacaoCandidatos(dataDoisMesesAtras.getTime(), hoje, empresa.getId(), new Long[]{estabelecimento.getId()}, new Long[]{areaOrganizacional.getId()}, new Long[]{cargo1.getId()}, StatusSolicitacao.ENCERRADA);

		assertEquals(1, resultadosComStatusSolicitacaoTodas.size());
		assertEquals(1, resultadosComStatusSolicitacaoEncerradas.size());
		assertEquals(Integer.valueOf(1), ((AvaliacaoCandidatosRelatorio)resultadosComStatusSolicitacaoTodas.toArray()[0]).getTotal());
	}

	public void testFindByNomeCpf()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);

		Candidato candidato1 = getCandidato();
		candidato1.setEmpresa(empresa);
		candidato1.setNome("Chico");
		candidato1.setCpf("4544565634");
		candidatoDao.save(candidato1);

		Candidato candidato2 = getCandidato();
		candidato2.setEmpresa(empresa);
		candidato2.setNome("Cesar");
		candidato2.setCpf("123456789");
		candidatoDao.save(candidato2);

		Candidato candidato2OutraEmpresa = getCandidato();
		candidato2OutraEmpresa.setEmpresa(outraEmpresa);
		candidato2OutraEmpresa.setNome("Cesar");
		candidato2OutraEmpresa.setCpf("123456789");
		candidatoDao.save(candidato2OutraEmpresa);

		Candidato candidatoBusca = new Candidato();
		candidatoBusca.setNome("Cesar");
		candidatoBusca.setCpf("123456789");

		Collection<Candidato> resultados = candidatoDao.findByNomeCpf(candidatoBusca, empresa.getId());
		assertNotNull(resultados);
		assertEquals(1, resultados.size());
	}
	
	public void testUpdateDisponivelAndContratadoByColaborador()
	{
		Candidato candidato = getCandidato();
		candidato.setDisponivel(false);
		candidato.setContratado(true);
		candidatoDao.save(candidato);

		Colaborador colaborador = new Colaborador();
		colaborador.setCandidato(candidato);
		colaboradorDao.save(colaborador);
		
		candidatoDao.updateDisponivelAndContratadoByColaborador(true, false, colaborador.getId());
		
		Candidato retorno = candidatoDao.findByCandidatoId(candidato.getId());
		assertEquals(true, retorno.isDisponivel());

		//caso não exista candidato para o colaborador
		candidato = getCandidato();
		candidato.setDisponivel(false);
		candidato.setContratado(true);
		candidatoDao.save(candidato);
		
		colaborador = new Colaborador();
		colaboradorDao.save(colaborador);
		
		candidatoDao.updateDisponivelAndContratadoByColaborador(true, false, colaborador.getId());
		
		retorno = candidatoDao.findByCandidatoId(candidato.getId());
		assertEquals(false, retorno.isDisponivel());
		assertEquals(true, retorno.isContratado());
	}

	public void testFindCandidatosForSolicitacaoAllEmpresas()
	{
		String indicadoPor = "júre";
		String nomeBusca = "chicó";
		String cpfBusca = "4544565634";
		Estado estado = EstadoFactory.getEntity(1L);
		Cidade cidade = CidadeFactory.getEntity(1L);
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		Cargo cargo = CargoFactory.getEntity();

		montaBuscaTest(null, estado, cidade, conhecimento, cargo);

		String[] cargosCheck = new String[]{cargo.getNomeMercado()};
		String[] conhecimentosCheck = new String[]{conhecimento.getNome()};

		Collection<Candidato> resultados = candidatoDao.findCandidatosForSolicitacaoAllEmpresas(indicadoPor, nomeBusca, cpfBusca, estado.getId(), cidade.getId(), cargosCheck, conhecimentosCheck, null, false, null, null);
		assertEquals(1, resultados.size());
	}

	public void testFindCandidatosForSolicitacaoByEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		String indicadoPor = "júre";
		String nomeBusca = "chicó";
		String cpfBusca = "4544565634";
		Estado estado = EstadoFactory.getEntity(1L);
		Cidade cidade = CidadeFactory.getEntity(1L);
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		Cargo cargo = CargoFactory.getEntity();

		montaBuscaTest(empresa, estado, cidade, conhecimento, cargo);

		Long[] cargosIds = new Long[]{cargo.getId()};
		Long[] conhecimentosIds = new Long[]{conhecimento.getId()};

		Collection<Candidato> resultados = candidatoDao.findCandidatosForSolicitacaoByEmpresa(empresa.getId(),indicadoPor, nomeBusca, cpfBusca, estado.getId(), cidade.getId(), cargosIds, conhecimentosIds, null, false, null, null);
		assertEquals(1, resultados.size());
	}
	
	public void testFindCandidatosForSolicitacaoByEmpresaSomenteCandidatosSemSolicitacao()
	{
		boolean somenteCandidatosSemSolicitacao = true;
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Estado estado = EstadoFactory.getEntity(1L);
		Cidade cidade = CidadeFactory.getEntity(1L);
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		Cargo cargo = CargoFactory.getEntity();

		montaBuscaTest(empresa, estado, cidade, conhecimento, cargo);
		
		Candidato candidatoComSolicitacao = getCandidato();
		candidatoComSolicitacao.setEmpresa(empresa);
		candidatoComSolicitacao.setNome("Jim");
		candidatoComSolicitacao.setCpf("123456780");
		candidatoDao.save(candidatoComSolicitacao);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao);
		
		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao.setCandidato(candidatoComSolicitacao);
		candidatoSolicitacao.setSolicitacao(solicitacao);
		candidatoSolicitacaoDao.save(candidatoSolicitacao);

		Collection<Candidato> resultados = candidatoDao.findCandidatosForSolicitacaoByEmpresa(empresa.getId(), null, null, null, null, null, null, null, null, somenteCandidatosSemSolicitacao, null, null); 
		assertEquals(2, resultados.size());
		
	}

	public void testFindCandidatosForSolicitacaoByEmpresaCandidatoJaSelecionado()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		String nomeBusca = "chicó";
		String cpfBusca = "4544565634";

		Candidato candidato1 = getCandidato();
		candidato1.setNome("Chico");
		candidato1.setCpf("4544565634");
		candidato1.setEmpresa(empresa);
		candidatoDao.save(candidato1);

		Candidato candidato2 = getCandidato();
		candidato2.setNome("Chico");
		candidato2.setCpf("4544565634");
		candidato2.setEmpresa(empresa);
		candidatoDao.save(candidato2);

		Collection<Long> candidatosJaSelecionados = new ArrayList<Long>();
		candidatosJaSelecionados.add(candidato2.getId());

		Collection<Candidato> resultados = candidatoDao.findCandidatosForSolicitacaoByEmpresa(empresa.getId(), "", nomeBusca, cpfBusca, null, null, null, null, candidatosJaSelecionados, false, null, null);
		assertEquals(1, resultados.size());
	}

	private void montaBuscaTest(Empresa empresa, Estado estado, Cidade cidade, Conhecimento conhecimento, Cargo cargo)
	{
		conhecimento.setNome("conhecimento");
		conhecimentoDao.save(conhecimento);
		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();
		conhecimentos.add(conhecimento);

		cargo.setNomeMercado("desenvolvedor");
		cargoDao.save(cargo);
		Collection<Cargo> cargos = new ArrayList<Cargo>();
		cargos.add(cargo);

		estadoDao.save(estado);

		cidade.setUf(estado);
		cidadeDao.save(cidade);

		Candidato candidato1 = getCandidato();
		candidato1.setNome("Chico");
		candidato1.setCpf("4544565634");
		candidato1.getPessoal().setIndicadoPor("Jurema");
		candidato1.getEndereco().setCidade(cidade);
		candidato1.getEndereco().setUf(estado);
		candidato1.setConhecimentos(conhecimentos);
		candidato1.setCargos(cargos);
		candidato1.setEmpresa(empresa);
		candidatoDao.save(candidato1);

		Candidato candidato2 = getCandidato();
		candidato2.setNome("Chico");
		candidato2.setCpf("999999999");
		candidato2.setEmpresa(empresa);
		candidatoDao.save(candidato2);
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

		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setEmpresa(empresa);
		candidato.setEndereco(endereco);
		candidatoDao.save(candidato);

		candidatoDao.migrarBairro(bairro.getNome(), bairroDestino.getNome());
		Candidato candidatoTmp = candidatoDao.findByIdProjection(candidato.getId());
		assertEquals(candidatoTmp.getEndereco().getBairro(), bairroDestino.getNome());
	}
	
	public void testTriagemAutomatica()
	{
		Map<String, Integer> pesos = new HashMap<String, Integer>();
		pesos.put("escolaridade", 2);
		pesos.put("cidade", 1);
		pesos.put("sexo", 1);
		pesos.put("idade", 1);
		pesos.put("cargo", 3);
		pesos.put("tempoExperiencia", 2);
		pesos.put("pretensaoSalarial", 2);
		
		Cargo motorista = CargoFactory.getEntity(2L);
		motorista.setNome("motorista");
		cargoDao.save(motorista);

		Cargo cobrador = CargoFactory.getEntity(3L);
		cobrador.setNome("cobrador");
		cargoDao.save(cobrador);
		
		Estado ceara = EstadoFactory.getEntity();
		estadoDao.save(ceara);
		
		Cidade caucaia = CidadeFactory.getEntity();
		caucaia.setUf(ceara);
		cidadeDao.save(caucaia);
		
		Candidato joao = CandidatoFactory.getCandidato();
		joao.setNome("joao");
		joao.setPessoalEscolaridade("05");
		joao.setPessoalSexo('M');
		joao.setPessoalDataNascimento(DateUtil.criarDataMesAno(1, 1, 1980));
		joao.setPretencaoSalarial(1000.0);
		joao.setEnderecoCidadeId(caucaia.getId());
		candidatoDao.save(joao);
		
		Experiencia expJoao1 = ExperienciaFactory.getEntity();
		expJoao1.setCandidato(joao);
		expJoao1.setCargo(motorista);
		expJoao1.setDataAdmissao(DateUtil.criarDataMesAno(1, 1, 2009));
		expJoao1.setDataDesligamento(DateUtil.criarDataMesAno(1, 1, 2010));
		experienciaDao.save(expJoao1);

		Candidato maria = CandidatoFactory.getCandidato();
		maria.setNome("maria");
		maria.setPessoalEscolaridade("07");
		maria.setPessoalSexo('F');
		maria.setPessoalDataNascimento(DateUtil.criarDataMesAno(1, 1, 1983));
		maria.setEnderecoCidadeId(caucaia.getId());
		maria.setPretencaoSalarial(1200.0);
		candidatoDao.save(maria);
		
		Experiencia expMaria1 = ExperienciaFactory.getEntity();
		expMaria1.setCandidato(maria);
		expMaria1.setCargo(cobrador);
		expMaria1.setDataAdmissao(DateUtil.criarDataMesAno(1, 1, 2009));
		experienciaDao.save(expMaria1);
		
		Candidato candBusca = CandidatoFactory.getCandidato();
		candBusca.setPessoalEscolaridade("02");
		candBusca.setPessoalSexo('F');
		candBusca.setEnderecoCidadeId(caucaia.getId());
		candBusca.setPretencaoSalarial(1500.0);
		candidatoDao.save(candBusca);
		
		candidatoDao.findByCandidatoId(joao.getId());
		Collection<Candidato> candidatos = candidatoDao.triagemAutomatica(candBusca, new Long[]{2L,3L}, 20, 30, 1, pesos, 10, null);
		assertTrue(candidatos.size() >= 2);
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setCandidatoDao(CandidatoDao candidatoDao)
	{
		this.candidatoDao = candidatoDao;
	}

	public void setAreaInteresseDao(AreaInteresseDao areaInteresseDao)
	{
		this.areaInteresseDao = areaInteresseDao;
	}

	public void setCargoDao(CargoDao cargoDao)
	{
		this.cargoDao = cargoDao;
	}

	public void setConhecimentoDao(ConhecimentoDao conhecimentoDao)
	{
		this.conhecimentoDao = conhecimentoDao;
	}

	public void setCidadeDao(CidadeDao cidadeDao)
	{
		this.cidadeDao = cidadeDao;
	}

	public void setEstadoDao(EstadoDao estadoDao)
	{
		this.estadoDao = estadoDao;
	}

	public void setBairroDao(BairroDao bairroDao)
	{
		this.bairroDao = bairroDao;
	}

	public void setExperienciaDao(ExperienciaDao experienciaDao)
	{
		this.experienciaDao = experienciaDao;
	}

	public void setSolicitacaoDao(SolicitacaoDao solicitacaoDao)
	{
		this.solicitacaoDao = solicitacaoDao;
	}

	public void setCandidatoSolicitacaoDao(CandidatoSolicitacaoDao candidatoSolicitacaoDao)
	{
		this.candidatoSolicitacaoDao = candidatoSolicitacaoDao;
	}

	public void setEtapaSeletivaDao(EtapaSeletivaDao etapaSeletivaDao)
	{
		this.etapaSeletivaDao = etapaSeletivaDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}

}