package com.fortes.rh.test.dao.hibernate.captacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.config.JDBCConnection;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.captacao.EtapaSeletivaDao;
import com.fortes.rh.dao.captacao.ExperienciaDao;
import com.fortes.rh.dao.captacao.HistoricoCandidatoDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.geral.AreaInteresseDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.BairroDao;
import com.fortes.rh.dao.geral.CamposExtrasDao;
import com.fortes.rh.dao.geral.CidadeDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.ComoFicouSabendoVagaDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.geral.EstadoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.AvaliacaoCandidatosRelatorio;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.Apto;
import com.fortes.rh.model.dicionario.Escolaridade;
import com.fortes.rh.model.dicionario.OrigemCandidato;
import com.fortes.rh.model.dicionario.PesosTriagemAutomatica;
import com.fortes.rh.model.dicionario.StatusSolicitacao;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.EtapaSeletivaFactory;
import com.fortes.rh.test.factory.captacao.ExperienciaFactory;
import com.fortes.rh.test.factory.captacao.HistoricoCandidatoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.geral.AreaInteresseFactory;
import com.fortes.rh.test.factory.geral.CamposExtrasFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.test.util.mockObjects.MockCandidato;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;
@SuppressWarnings({"unchecked","rawtypes"})
public class CandidatoDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<Candidato>
{
	@Autowired private CandidatoDao candidatoDao;
	@Autowired private EmpresaDao empresaDao;
	@Autowired private AreaInteresseDao areaInteresseDao;
	@Autowired private CargoDao cargoDao;
	@Autowired private ConhecimentoDao conhecimentoDao;
	@Autowired private CidadeDao cidadeDao;
	@Autowired private EstadoDao estadoDao;
	@Autowired private BairroDao bairroDao;
	@Autowired private ExperienciaDao experienciaDao;
	@Autowired private SolicitacaoDao solicitacaoDao;
	@Autowired private EstabelecimentoDao estabelecimentoDao;
	@Autowired private AreaOrganizacionalDao areaOrganizacionalDao;
	@Autowired private HistoricoCandidatoDao historicoCandidatoDao;
	@Autowired private CandidatoSolicitacaoDao candidatoSolicitacaoDao;
	@Autowired private EtapaSeletivaDao etapaSeletivaDao;
	@Autowired private ColaboradorDao colaboradorDao;
	@Autowired private FaixaSalarialDao faixaSalarialDao;
	@Autowired private ComoFicouSabendoVagaDao comoFicouSabendoVagaDao;
	@Autowired private CamposExtrasDao camposExtrasDao;

	public Candidato getEntity()
	{
		return CandidatoFactory.getCandidato();
	}

	public GenericDao<Candidato> getGenericDao()
	{
		return candidatoDao;
	}

	@Test
	public void testFindByCPFCollection()
	{
		Empresa e1 = EmpresaFactory.getEmpresa();
		empresaDao.save(e1);

		Empresa e2 = EmpresaFactory.getEmpresa();
		empresaDao.save(e2);
		
		Candidato c = CandidatoFactory.getCandidatoDiponivel("chico", "00000000", e1);
		candidatoDao.save(c);
		
		Candidato c2 = CandidatoFactory.getCandidatoDiponivel("bob", "0000000000", e1);
		candidatoDao.save(c2);
		
		Candidato c3 = CandidatoFactory.getCandidatoDiponivel("bobinho", "0000000000", e2);
		candidatoDao.save(c3);
		
		Candidato c4 = CandidatoFactory.getCandidatoDiponivel("bobinho2", "0000000000", e2);
		c4.setContratado(true);
		candidatoDao.save(c4);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setCandidato(c3);
		colaboradorDao.save(colaborador);
		
		assertEquals("Verifica contratados", 2, candidatoDao.findByCPF("0000000000", null, null, false).size());
		assertEquals("Verifica exclusao de si", 1, candidatoDao.findByCPF("0000000000", null, c2.getId(), false).size());
		assertEquals("Verifica empresa", 1, candidatoDao.findByCPF("0000000000", e1.getId(), null, false).size());
	}
	
	@Test
	public void testFindQtdCadastradosByOrigem()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Candidato cand1 = getCandidato();
		cand1.setDataCadastro(DateUtil.criarDataMesAno(03, 02, 1984));
		cand1.setOrigem(OrigemCandidato.CADASTRADO);
		cand1.setEmpresa(empresa);
		candidatoDao.save(cand1);
		
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
	
	@Test
	public void testCountComoFicouSabendoVagas()
	{
		Date hoje = new Date();

		ComoFicouSabendoVaga revista = new ComoFicouSabendoVaga();
		revista.setNome("revista");
		comoFicouSabendoVagaDao.save(revista);
		
		ComoFicouSabendoVaga jornal = new ComoFicouSabendoVaga();
		jornal.setNome("jornal");
		comoFicouSabendoVagaDao.save(jornal);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Candidato pedro = getCandidato();
		pedro.setEmpresa(empresa);
		pedro.setDataAtualizacao(hoje);
		pedro.setComoFicouSabendoVaga(jornal);
		candidatoDao.save(pedro);
		
		Candidato maria = getCandidato();
		maria.setEmpresa(empresa);
		maria.setDataAtualizacao(hoje);
		maria.setComoFicouSabendoVaga(jornal);
		candidatoDao.save(maria);
		
		Candidato joao = getCandidato();
		joao.setEmpresa(empresa);
		joao.setDataAtualizacao(hoje);
		joao.setComoFicouSabendoVaga(revista);
		candidatoDao.save(joao);
		
		Collection<ComoFicouSabendoVaga> vagas = candidatoDao.countComoFicouSabendoVagas(empresa.getId(), hoje, hoje);
		
		assertEquals(2, vagas.size());
		assertEquals("jornal", ((ComoFicouSabendoVaga)vagas.toArray()[0]).getNome());
		assertEquals("revista", ((ComoFicouSabendoVaga)vagas.toArray()[1]).getNome());
	}

	@Test
	public void testFindByCandidatoId()
	{
		Candidato c3 = getCandidato();
		c3.setNome("bobinho2");
		c3.getPessoal().setCpf("23423");

		candidatoDao.save(c3);

		Candidato candidato = candidatoDao.findByCandidatoId(c3.getId());
		assertEquals(c3.getId(), candidato.getId());
	}

	@Test
	public void testFind()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresa2 = empresaDao.save(empresa2);

		Candidato c1 = CandidatoFactory.getCandidatoDiponivel("chico", "11111111111", empresa);
		candidatoDao.save(c1);
		Candidato c2 = CandidatoFactory.getCandidatoDiponivel("bob", "22226222221", empresa);
		candidatoDao.save(c2);
		Candidato c3 = CandidatoFactory.getCandidatoDiponivel("bobinho", "73333333333", empresa2);
		candidatoDao.save(c3);

		candidatoDao.getHibernateTemplateByGenericDao().flush();
		Collection<Candidato> candidatos = candidatoDao.find(1, 10, "", "", null, null, null, "", 'T',null, null, null, false, false, empresa.getId());

		assertFalse(candidatos.isEmpty());
		assertEquals(2, candidatos.size());

		candidatos = candidatoDao.find(1, 10, "chi", "", null, null, null, "", 'T',null, null, null, false, false, empresa.getId());
		assertEquals(1, candidatos.size());
		assertEquals(c1.getId(), ((Candidato)(candidatos.toArray()[0])).getId(), 'T');

		candidatos = candidatoDao.find(1, 10, "chi", "11111111111", null, null, null, "", 'T',null, null, null, false, false, empresa.getId());
		assertEquals(1, candidatos.size());
		assertEquals(c1.getId(), ((Candidato)(candidatos.toArray()[0])).getId(), 'T');

		candidatos = candidatoDao.find(1, 10, "chi", "22222", null, null, null, "", 'T',null, null, null, false, false, empresa.getId());
		assertTrue(candidatos.isEmpty());

		candidatos = candidatoDao.find(1, 10, "bob", "", null, null, null, "", 'T',null, null, null, false, false, empresa.getId());
		assertEquals(1, candidatos.size());

		assertEquals(c2.getId(), ((Candidato)(candidatos.toArray()[0])).getId(), 'T');
	}

	@Test
	public void testFindVisualizar()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = CandidatoFactory.getCandidatoDiponivel("chico", "11111111111", empresa);
		candidatoDao.save(c1);
		Candidato c2 = CandidatoFactory.getCandidatoDiponivel("bob", "22226222221", empresa);
		c2.setDisponivel(false);
		candidatoDao.save(c2);
		Candidato c3 = CandidatoFactory.getCandidatoDiponivel("bobinho", "73333333333", empresa);
		c3.setDisponivel(false);
		candidatoDao.save(c3);

		candidatoDao.getHibernateTemplateByGenericDao().flush();
		Collection<Candidato> candidatos = candidatoDao.find(1, 10, "", "", null, null, null, "", 'T',null, null, null, false, false, empresa.getId());
		assertEquals(3, candidatos.size());

		candidatos = candidatoDao.find(1, 10, "", "", null, null, null, "", 'D',null, null, null, false, false, empresa.getId());
		assertEquals(1, candidatos.size());

		candidatos = candidatoDao.find(1, 10, "", "", null, null, null, "", 'I',null, null, null, false, false, empresa.getId());
		assertEquals(2, candidatos.size());
	}
	
	@Test
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
		return MockCandidato.getCandidato();
	}

	@Test
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
		c1.setContatoDdd("85");
		c1.setContatoFoneFixo("33334444");
		c1.setContatoFoneCelular("88889999");
		candidatoDao.save(c1);

		candidatoDao.getHibernateTemplateByGenericDao().flush();

		Integer count = candidatoDao.getCount(nomeBusca, cpfBusca, "85", "344", "8889", "", 'T',null, null, null, false, false, empresa.getId());

		assertEquals(1, (int)count);
	}

	@Test
	public void testGetCountDisponivelIndisponivel()
	{
		String nomeBusca = "teste";
		String cpfBusca = "123";

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setNome(nomeBusca);
		c1.setCpf(cpfBusca);
		c1.setContatoDdd("85");
		c1.setContatoFoneFixo("33334444");
		c1.setContatoFoneCelular("88889999");
		c1.setEmpresa(empresa);
		c1.setDisponivel(true);
		candidatoDao.save(c1);

		candidatoDao.getHibernateTemplateByGenericDao().flush();
		assertEquals("disponivel", 1, (int)candidatoDao.getCount(nomeBusca, cpfBusca, "85", "344", "8889", "", 'D',null, null, null, false, false, empresa.getId()));
		
		c1.setDisponivel(false);
		candidatoDao.save(c1);
		candidatoDao.getHibernateTemplateByGenericDao().flush();
		
		assertEquals("indisponivel", 1, (int)candidatoDao.getCount(nomeBusca, cpfBusca, "85", "344", "8889", "", 'I',null, null, null, false, false, empresa.getId()));
	}

	@Test
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
		candidatoDao.save(c1);

		candidatoDao.getHibernateTemplateByGenericDao().flush();
		Integer count = candidatoDao.getCount(nomeBusca, cpfBusca, "", "", "", "", 'I',dataIni, dataFim, null, false, false, empresa.getId());

		assertEquals(1, (int)count);
	}

	@Test
	public void testFindCandidatosById()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Estado estado = EstadoFactory.getEntity();
		estado = estadoDao.save(estado);

		Cidade cidade = CidadeFactory.getEntity();
		cidade.setUf(estado);
		cidade = cidadeDao.save(cidade);

		Candidato c1 = CandidatoFactory.getCandidatoDiponivel("chico", "1111211111", empresa);
		c1.getEndereco().setCidade(cidade);
		candidatoDao.save(c1);
		Candidato c2 = CandidatoFactory.getCandidatoDiponivel("bob", "22222232221", empresa);
		c2.getEndereco().setCidade(cidade);
		candidatoDao.save(c2);
		Candidato c3 = CandidatoFactory.getCandidatoDiponivel("bobinho", "33433333333", empresa);
		c3.getEndereco().setCidade(cidade);
		candidatoDao.save(c3);

		Long[] ids = {c1.getId(),c2.getId(),c3.getId()};
		Collection<Candidato> candidatos = candidatoDao.findCandidatosById(ids);

		assertEquals(3, candidatos.size());
	}

	@Test
	public void testGetConhecimentosByCandidatoId()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento = conhecimentoDao.save(conhecimento);

		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();
		conhecimentos.add(conhecimento);

		Candidato c1 = CandidatoFactory.getCandidatoDiponivel("chico", "1111211111", empresa);
		c1.setConhecimentos(conhecimentos);
		candidatoDao.save(c1);

		List conhecimentosRetorno  = candidatoDao.getConhecimentosByCandidatoId(c1.getId());
		assertEquals(conhecimento.getNome(), conhecimentosRetorno.get(0));
	}

	@Test
	public void testGetCandidatosByCpf()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Estado estado = EstadoFactory.getEntity();
		estado = estadoDao.save(estado);

		Cidade cidade = CidadeFactory.getEntity();
		cidade.setUf(estado);
		cidade = cidadeDao.save(cidade);

		Candidato c1 = CandidatoFactory.getCandidatoDiponivel("chico", "1111241111", empresa);
		c1.getEndereco().setCidade(cidade);
		candidatoDao.save(c1);
		Candidato c2 = CandidatoFactory.getCandidatoDiponivel("bob", "22222232521", empresa);
		c2.getEndereco().setCidade(cidade);
		candidatoDao.save(c2);
		Candidato c3 = CandidatoFactory.getCandidatoDiponivel("bobinho", "33433633333", empresa);
		c3.getEndereco().setCidade(cidade);
		candidatoDao.save(c3);

		String[] cpfs = {c1.getPessoal().getCpf(),c2.getPessoal().getCpf(),c3.getPessoal().getCpf()};

		Collection<Candidato> candidatos = candidatoDao.getCandidatosByCpf(cpfs,empresa.getId());

		assertEquals(3, candidatos.size());
	}

	@Test
	public void testFindCandidatoCpf()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = CandidatoFactory.getCandidatoDiponivel("chico", "1111241111", empresa);
		candidatoDao.save(c1);
		
		Candidato candidatoRetorno = candidatoDao.findCandidatoCpf(c1.getPessoal().getCpf(), empresa.getId());

		assertEquals(c1.getId(), candidatoRetorno.getId());
	}

	@Test
	public void testUpdateSetContratado()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);

		Candidato c1 = CandidatoFactory.getCandidatoDiponivel("chico", "1111241111", empresa);
		candidatoDao.save(c1);
		
		candidatoDao.updateSetContratado(c1.getId(), empresa2.getId());
		Candidato retorno = candidatoDao.findByIdProjection(c1.getId());

		assertEquals(empresa2.getId(), retorno.getEmpresa().getId());
		assertTrue(retorno.isContratado());
	}

	@Test
	public void testFindByIdProjection()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setExamePalografico("?????--%%%á#%**&¨%$#@!");

		candidatoDao.save(c1);

		Candidato candidatoRetorno = candidatoDao.findByIdProjection(c1.getId());

		assertEquals(c1.getId(), candidatoRetorno.getId());
		assertEquals(c1.getExamePalografico(), candidatoRetorno.getExamePalografico());
	}

	@Test
	public void testAtualizaSenha()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = CandidatoFactory.getCandidatoDiponivel("chico", "1111241111", empresa);
		candidatoDao.save(c1);

		candidatoDao.atualizaSenha(c1.getId(),"novaSenha");

		Candidato retorno = candidatoDao.findByIdProjection(c1.getId());

		assertEquals("novaSenha", retorno.getSenha());
	}

	@Test
	public void testAtualizaTextoOcr()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = CandidatoFactory.getCandidatoDiponivel("chico", "1111241111", empresa);
		c1.setOcrTexto("texto ocr");
		candidatoDao.save(c1);

		candidatoDao.atualizaTextoOcr(c1);

		Collection<Candidato> retorno = candidatoDao.findToList(new String[]{"ocrTexto"}, new String[]{"ocrTexto"}, new String[]{"id"}, new Object[]{c1.getId()});
		assertEquals("texto ocr", ((Candidato) retorno.toArray()[0]).getOcrTexto());
	}

	@Test
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

	@Test
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

	@Test
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

	@Test
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

	@Test
	public void testUpdateSenha()
	{
		Candidato candidato = getCandidato();
		candidato.setSenha(StringUtil.encodeString("1234"));
		candidato.setNome("Hubster Haas Silva");
		candidato = candidatoDao.save(candidato);

		candidatoDao.updateSenha(candidato.getId(), candidato.getSenha(), StringUtil.encodeString("123456"));
	}

	@Test
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

		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setCargo(cargo1);
		faixaSalarialDao.save(faixaSalarial1);
		
		Cargo cargo2 = CargoFactory.getEntity();
		cargoDao.save(cargo2);

		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarial2.setCargo(cargo2);
		faixaSalarialDao.save(faixaSalarial2);
		
		Candidato candidato1 = getCandidato();
		candidato1.setEmpresa(empresa);
		candidato1.setNome("chico");
		candidatoDao.save(candidato1);

		Candidato candidato2 = getCandidato();
		candidato2.setEmpresa(empresa);
		candidato2.setNome("Zé Mané");
		candidatoDao.save(candidato2);

		Solicitacao solicitacaoEncerrada = new Solicitacao();
		solicitacaoEncerrada.setFaixaSalarial(faixaSalarial1);
		solicitacaoEncerrada.setEstabelecimento(estabelecimento);
		solicitacaoEncerrada.setAreaOrganizacional(areaOrganizacional);
		solicitacaoEncerrada.setDataEncerramento(dataDoisMesesAtras.getTime());
		solicitacaoDao.save(solicitacaoEncerrada);
		
		Solicitacao solicitacaoAberta = new Solicitacao();
		solicitacaoAberta.setFaixaSalarial(faixaSalarial1);
		solicitacaoAberta.setEstabelecimento(estabelecimento);
		solicitacaoAberta.setAreaOrganizacional(areaOrganizacional);
		solicitacaoAberta.setDataEncerramento(DateUtil.criarDataMesAno(01, 01, 2030));
		solicitacaoDao.save(solicitacaoAberta);

		Solicitacao solicitacaoFora = new Solicitacao();
		solicitacaoFora.setFaixaSalarial(faixaSalarial2);
		solicitacaoFora.setEstabelecimento(estabelecimento);
		solicitacaoFora.setAreaOrganizacional(areaOrganizacional);
		solicitacaoDao.save(solicitacaoFora);

		CandidatoSolicitacao candidatoSolicitacaoEncerrada = new CandidatoSolicitacao();
		candidatoSolicitacaoEncerrada.setCandidato(candidato1);
		candidatoSolicitacaoEncerrada.setSolicitacao(solicitacaoEncerrada);
		candidatoSolicitacaoDao.save(candidatoSolicitacaoEncerrada);
		
		CandidatoSolicitacao candidatoSolicitacaoAberta = new CandidatoSolicitacao();
		candidatoSolicitacaoAberta.setCandidato(candidato2);
		candidatoSolicitacaoAberta.setSolicitacao(solicitacaoAberta);
		candidatoSolicitacaoDao.save(candidatoSolicitacaoAberta);

		CandidatoSolicitacao candidatoSolicitacaoFora = new CandidatoSolicitacao();
		candidatoSolicitacaoFora.setCandidato(candidato2);
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

		assertEquals(2, resultadosComStatusSolicitacaoTodas.size());
		assertEquals(1, resultadosComStatusSolicitacaoEncerradas.size());
		assertEquals(Integer.valueOf(1), ((AvaliacaoCandidatosRelatorio)resultadosComStatusSolicitacaoTodas.toArray()[0]).getTotal());
	}

	@Test
	public void testFindByNomeCpf()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa outraEmpresa = EmpresaFactory.getEmpresa();
		empresaDao.save(outraEmpresa);
		
		Candidato candidato1 = CandidatoFactory.getCandidatoDiponivel("Cesar", "123456789", empresa);
		candidatoDao.save(candidato1);
		Candidato candidato2 = CandidatoFactory.getCandidatoDiponivel("bob", "22222232521", empresa);
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
	
	@Test
	public void testUpdateDisponivelAndContratadoByColaborador()
	{
		Candidato candidato = getCandidato();
		candidato.setDisponivel(false);
		candidato.setContratado(true);
		candidatoDao.save(candidato);

		Colaborador colaborador = ColaboradorFactory.getEntity();
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
		
		colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		candidatoDao.updateDisponivelAndContratadoByColaborador(true, false, colaborador.getId());
		
		retorno = candidatoDao.findByCandidatoId(candidato.getId());
		assertEquals(false, retorno.isDisponivel());
		assertEquals(true, retorno.isContratado());
	}
	
	@Test
	public void testUpdateDisponivel()
	{
		Candidato candidato = getCandidato();
		candidato.setDisponivel(false);
		candidato.setContratado(true);
		candidatoDao.save(candidato);
		
		candidatoDao.updateDisponivel(true, candidato.getId());
		
		Candidato retorno = candidatoDao.findByCandidatoId(candidato.getId());
		assertEquals(true, retorno.isDisponivel());
		
		candidatoDao.updateDisponivel(false, candidato.getId());
		
		retorno = candidatoDao.findByCandidatoId(candidato.getId());
		assertEquals(false, retorno.isDisponivel());
	}

	@Test
	public void testFindCandidatosForSolicitacaoAllEmpresasPermitidas()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		String indicadoPor = "júre";
		String nomeBusca = "chicó";
		String cpfBusca = "4544565634";
		String escolaridade = "02";
		
		Estado estado = EstadoFactory.getEntity(1L);
		Cidade cidade = CidadeFactory.getEntity(1L);
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		Cargo cargo = CargoFactory.getEntity();

		montaBuscaTest(empresa, estado, cidade, conhecimento, cargo);

		String[] cargosCheck = new String[]{cargo.getNomeMercadoComStatus()};
		String[] conhecimentosCheck = new String[]{conhecimento.getNome()};
		Long[] cidadesIds = new Long[]{cidade.getId()};

		Collection<Candidato> resultados = candidatoDao.findCandidatosForSolicitacao(indicadoPor, nomeBusca, cpfBusca, escolaridade, estado.getId(), cidadesIds, cargosCheck, conhecimentosCheck, null, false, null, null, new Long[]{empresa.getId()}, true);
		assertEquals(1, resultados.size());
	}

	@Test
	public void testFindCandidatosForSolicitacaoByEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		String indicadoPor = "júre";
		String nomeBusca = "chicó";
		String cpfBusca = "4544565634";
		String escolaridade = "02";
		
		Estado estado = EstadoFactory.getEntity(1L);
		Cidade cidade = CidadeFactory.getEntity(1L);
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		Cargo cargo = CargoFactory.getEntity();

		montaBuscaTest(empresa, estado, cidade, conhecimento, cargo);

		String[] cargosIds = new String[]{cargo.getId().toString()};
		String[] conhecimentosIds = new String[]{conhecimento.getId().toString()};
		Long[] cidadesIds = new Long[]{cidade.getId()};

		Collection<Candidato> resultados = candidatoDao.findCandidatosForSolicitacao(indicadoPor, nomeBusca, cpfBusca, escolaridade, 
				estado.getId(), cidadesIds, cargosIds, conhecimentosIds, null, false, null, null, new Long[]{empresa.getId()}, false);
		
		assertEquals(1, resultados.size());
	}
	
	@Test
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
		candidatoComSolicitacao.getPessoal().setEscolaridade(Escolaridade.COLEGIAL_COMPLETO);
		candidatoDao.save(candidatoComSolicitacao);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao);
		
		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao.setCandidato(candidatoComSolicitacao);
		candidatoSolicitacao.setSolicitacao(solicitacao);
		candidatoSolicitacaoDao.save(candidatoSolicitacao);
		
		Collection<Candidato> resultados = candidatoDao.findCandidatosForSolicitacao(null, null, null, null, null, null, null, null, null, somenteCandidatosSemSolicitacao, null, null, new Long[]{empresa.getId()}, true); 
		assertEquals(2, resultados.size());
		
	}
	
	@Test
	public void testFindCandidatosForSolicitacaoByEmpresaCandidatoJaSelecionado()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		String nomeBusca = "chicó";
		String cpfBusca = "4544565634";
		String escolaridade = null;
		
		Candidato candidato1 = CandidatoFactory.getCandidatoDiponivel("Chico", "4544565634", empresa);
		candidatoDao.save(candidato1);
		Candidato candidato2 = CandidatoFactory.getCandidatoDiponivel("Chico", "4544565634", empresa);
		candidatoDao.save(candidato2);

		Collection<Long> candidatosJaSelecionados = new ArrayList<Long>();
		candidatosJaSelecionados.add(candidato2.getId());

		Collection<Candidato> resultados = candidatoDao.findCandidatosForSolicitacao("", nomeBusca, cpfBusca, escolaridade, null, null, null, null, candidatosJaSelecionados, false, null, null, new Long[]{empresa.getId()}, true);
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
		candidato1.getPessoal().setEscolaridade("02");
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

	@Test
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
	
	@Test
	public void testTriagemAutomatica()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		PesosTriagemAutomatica pesos = new PesosTriagemAutomatica();
		
		Cargo cobrador = CargoFactory.getEntity();
		cobrador.setNome("cobrador");
		cargoDao.save(cobrador);
		
		Cargo motorista = CargoFactory.getEntity();
		motorista.setNome("motorista");
		cargoDao.save(motorista);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(motorista);
		faixaSalarial.setNome("I");
		faixaSalarialDao.save(faixaSalarial);
		
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
		joao.setEmpresa(empresa);
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
		maria.setEmpresa(empresa);
		candidatoDao.save(maria);
		
		Experiencia expMaria1 = ExperienciaFactory.getEntity();
		expMaria1.setCandidato(maria);
		expMaria1.setCargo(cobrador);
		expMaria1.setDataAdmissao(DateUtil.criarDataMesAno(1, 1, 2009));
		experienciaDao.save(expMaria1);
		
		Solicitacao solicitacaoBusca = SolicitacaoFactory.getSolicitacao();
		solicitacaoBusca.setEscolaridade("02");
		solicitacaoBusca.setSexo("F");
		solicitacaoBusca.setProjectionCidadeId(caucaia.getId());
		solicitacaoBusca.setRemuneracao(1500.0);
		solicitacaoBusca.setEmpresa(empresa);
		solicitacaoBusca.setFaixaSalarial(faixaSalarial);
		
		candidatoDao.findByCandidatoId(joao.getId());
		Collection<Candidato> candidatos = candidatoDao.triagemAutomatica(solicitacaoBusca, 1, pesos, 10);
		assertTrue(candidatos.size() >= 2);
	}

	@Test
	public void testFindQtdCadastrados() {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Date hoje = new Date();
		
		Candidato c1 = getCandidato();
		c1.setEmpresa(empresa);
		c1.setDataCadastro(hoje);
		candidatoDao.save(c1);

		Candidato c2 = getCandidato();
		c2.setEmpresa(empresa);
		c2.setDataCadastro(hoje);
		candidatoDao.save(c2);

		assertEquals(2, candidatoDao.findQtdCadastrados(empresa.getId(), hoje, hoje));

		c2.setDataCadastro(DateUtil.incrementaDias(hoje, 5));
		candidatoDao.save(c2);
		
		assertEquals(1, candidatoDao.findQtdCadastrados(empresa.getId(), hoje, hoje));

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		assertEquals(0, candidatoDao.findQtdCadastrados(empresa2.getId(), hoje, hoje));
		
	}
	
	@Test
	public void testRemoveAreaInteresseConhecimentoCargo() 
	{
	
		Exception ex = null;
		try {
			candidatoDao.removeAreaInteresseConhecimentoCargo(99999999999L);
			
		} catch (Exception e) {
			ex = e;
		}
		assertNull(ex);
		
		String qtdTabelasComCandidatos = JDBCConnection.executeQuery("select count(table_name) from information_schema.columns as col " +
																		"where col.column_name = 'candidato_id' " +
																		"and col.table_schema = 'public' " +
																		"and col.table_name <> 'candidatoeleicao';"); // candidatoeleicao é na realidade um colaborador
		//se aumentar atualizar removercandidato no manager
		assertEquals("12", qtdTabelasComCandidatos);
	}
	
	@Test
	public void testFindColaboradoresMesmoCpf()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colab1 = ColaboradorFactory.getEntity();
		colab1.setPessoalCpf("65617222222");
		colab1.setDataDesligamento(DateUtil.criarDataMesAno(1, 2, 2013));
		colab1.setEmpresa(empresa);
		colaboradorDao.save(colab1);

		Colaborador colab2 = ColaboradorFactory.getEntity();
		colab2.setPessoalCpf("36548111111");
		colab2.setEmpresa(empresa);
		colaboradorDao.save(colab2);
		
		assertEquals(2, candidatoDao.findColaboradoresMesmoCpf(new String[] { "", colab1.getPessoal().getCpf(), colab2.getPessoal().getCpf() }).size() );
	}
	
	@Test
	public void testDeleteCargosPretendidos() 
	{
		Exception ex = null;
		try {
			candidatoDao.deleteCargosPretendidos(99999999999L);
		} catch (Exception e) {
			ex = e;
		}
		assertNull(ex);
	}
	
	@Test
	public void testExisteCamposExtras()
	{
		CamposExtras camposExtras = CamposExtrasFactory.getEntity(1L);
		camposExtrasDao.save(camposExtras);
		
		assertFalse(candidatoDao.existeCamposExtras(camposExtras.getId()));
		
		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setCamposExtras(camposExtras);
		candidatoDao.save(candidato);
		
		assertTrue(candidatoDao.existeCamposExtras(camposExtras.getId()));
	}
	
	@Test
	public void testGetAutoComplete(){
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Candidato candidato1 = CandidatoFactory.getCandidato(1L, "Francisco Bernardo");
		candidato1.setEmpresa(empresa);
		candidatoDao.save(candidato1);
		
		Candidato candidato2 = CandidatoFactory.getCandidato(1L, "Juliana Almeida");
		candidato2.setEmpresa(empresa);
		candidatoDao.save(candidato2);
		
		assertEquals(1, candidatoDao.getAutoComplete("JULIANA", empresa.getId()).size());
	}
	
	@Test
	public void testFindCandidatosIndicadosPor()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Candidato candidato1 = CandidatoFactory.getCandidato();
		candidato1.setDataCadastro(DateUtil.criarDataMesAno(1, 1, 2015));
		candidato1.setPessoalIndicadoPor("Francisco");
		candidato1.setEmpresa(empresa);
		candidatoDao.save(candidato1);
		
		Candidato candidato2 = CandidatoFactory.getCandidato();
		candidato2.setDataCadastro(DateUtil.criarDataMesAno(1, 1, 2016));
		candidato2.setPessoalIndicadoPor("Carlos");
		candidato2.setEmpresa(empresa);
		candidatoDao.save(candidato2);
		
		Long[] empresasIds = new Long[]{empresa.getId()};
		
		Collection<Candidato> candidatos = candidatoDao.findCandidatosIndicadosPor(DateUtil.criarDataMesAno(1, 1, 2016), new Date(), empresasIds);
		
		assertEquals(1, candidatos.size());
		assertEquals(candidato2.getPessoal().getIndicadoPor(), candidatos.iterator().next().getPessoal().getIndicadoPor());
	}
	
	@Test
	public void testGetCandidatosByEtapaSeletiva()
	{
		EtapaSeletiva etapaSeletiva1 = inicializaCandidatoSolicitacaoComEtapaSeletiva();
		Collection<Candidato> candidatos = candidatoDao.getCandidatosByEtapaSeletiva(etapaSeletiva1.getId());
		
		assertEquals(1, candidatos.size());
		assertEquals("Cand1", ((Candidato)candidatos.toArray()[0]).getNome());
	}
	
	@Test
	public void testGetFuncoesPretendidasByEtapaSeletiva()
	{
		EtapaSeletiva etapaSeletiva1 = inicializaCandidatoSolicitacaoComEtapaSeletiva();
		candidatoDao.getHibernateTemplateByGenericDao().flush();
		Map<Long, Collection<String>> funcoesPretendidas = candidatoDao.getFuncoesPretendidasByEtapaSeletiva(etapaSeletiva1.getId());
		
		assertEquals(1, funcoesPretendidas.size());
		assertEquals("Develop", ((Collection<String>) funcoesPretendidas.values().toArray()[0]).toArray()[0]);
	}

	@Test
	public void testGetCandidatosByEtapaSeletivaCandidatoAptoPoremContratado()
	{
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao);
		
		Cargo funcaoPretendida1 = CargoFactory.getEntity("Develop");
		cargoDao.save(funcaoPretendida1);
		
		Cargo funcaoPretendida2 = CargoFactory.getEntity("Analista");
		cargoDao.save(funcaoPretendida2);
		
		EtapaSeletiva etapaSeletiva1 = EtapaSeletivaFactory.getEntity();
		etapaSeletivaDao.save(etapaSeletiva1);
		
		Candidato candidato1 = saveCandidato("Cand1", false, funcaoPretendida1);
		Candidato candidato2 = saveCandidato("Cand2", true, funcaoPretendida2);
		
		saveCandidatoSolicitacaoComHistorico(candidato1, solicitacao, etapaSeletiva1, DateUtil.criarDataMesAno(1, 1, 2016), Apto.SIM);
		saveCandidatoSolicitacaoComHistorico(candidato2, solicitacao, etapaSeletiva1, DateUtil.criarDataMesAno(1, 1, 2016), Apto.SIM);
		
		Collection<Candidato> candidatos = candidatoDao.getCandidatosByEtapaSeletiva(etapaSeletiva1.getId());
		
		assertEquals(1, candidatos.size());
		assertEquals("Cand1", ((Candidato)candidatos.toArray()[0]).getNome());
	}
	
	private Candidato saveCandidato(String nome, boolean contratado, Cargo funcaoPretendida){
		Candidato candidato = CandidatoFactory.getCandidato(nome);
		candidato.setContratado(contratado);
		candidato.setCargos(java.util.Arrays.asList(funcaoPretendida));
		candidatoDao.save(candidato);
		return candidato;
	}
	
	private void saveCandidatoSolicitacaoComHistorico(Candidato candidato, Solicitacao solicitacao, EtapaSeletiva etapaSeletiva, Date data, char apto){
		CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
		candidatoSolicitacao.setCandidato(candidato);
		candidatoSolicitacao.setSolicitacao(solicitacao);
		candidatoSolicitacaoDao.save(candidatoSolicitacao);
		
		HistoricoCandidato historicoCandidato1 = HistoricoCandidatoFactory.getEntity(etapaSeletiva, data, candidatoSolicitacao);
		historicoCandidato1.setApto(apto);
		historicoCandidatoDao.save(historicoCandidato1);
	}
	
	private EtapaSeletiva inicializaCandidatoSolicitacaoComEtapaSeletiva() {
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao);
		
		Cargo funcaoPretendida1 = CargoFactory.getEntity("Develop");
		cargoDao.save(funcaoPretendida1);
		
		Cargo funcaoPretendida2 = CargoFactory.getEntity("Analista");
		cargoDao.save(funcaoPretendida2);
		
		EtapaSeletiva etapaSeletiva1 = EtapaSeletivaFactory.getEntity();
		etapaSeletivaDao.save(etapaSeletiva1);
		
		EtapaSeletiva etapaSeletiva2 = EtapaSeletivaFactory.getEntity();
		etapaSeletivaDao.save(etapaSeletiva2);
		
		Candidato candidato1 = saveCandidato("Cand1", false, funcaoPretendida1);
		Candidato candidato2 = saveCandidato("Cand2", false, funcaoPretendida2);
		
		saveCandidatoSolicitacaoComHistorico(candidato1, solicitacao, etapaSeletiva1, DateUtil.criarDataMesAno(1, 1, 2016), Apto.SIM);
		saveCandidatoSolicitacaoComHistorico(candidato2, solicitacao, etapaSeletiva2, DateUtil.criarDataMesAno(1, 1, 2016), Apto.NAO);
		
		return etapaSeletiva1;
	}
	
	@Test
	public void testFindPorEmpresaByCpfSenha(){
        Empresa empresa = EmpresaFactory.getEmpresa();
        empresaDao.save(empresa);
        
        Candidato candidato1 = CandidatoFactory.getCandidatoDiponivel("Candidato 1", "01234567890", empresa);
        candidato1.setSenha("1234");
        candidatoDao.save(candidato1);
        
        Candidato candidato2 = CandidatoFactory.getCandidatoDiponivel("Candidato 1", "01234567890", empresa);
        candidatoDao.save(candidato2);
        
        Collection<Candidato> retorno = candidatoDao.findPorEmpresaByCpfSenha("01234567890", "1234", empresa.getId());
        assertEquals(1, retorno.size());
        assertEquals(candidato1.getId(), retorno.iterator().next().getId());
	}
}