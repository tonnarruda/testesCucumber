package com.fortes.rh.test.dao.hibernate.captacao;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.dao.captacao.CandidatoIdiomaDao;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.dao.captacao.ExperienciaDao;
import com.fortes.rh.dao.captacao.FormacaoDao;
import com.fortes.rh.dao.captacao.IdiomaDao;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.geral.AreaInteresseDao;
import com.fortes.rh.dao.geral.BairroDao;
import com.fortes.rh.dao.geral.CidadeDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstadoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.captacao.Idioma;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.ExperienciaFactory;
import com.fortes.rh.test.factory.captacao.FormacaoFactory;
import com.fortes.rh.test.factory.captacao.IdiomaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.geral.AreaInteresseFactory;
import com.fortes.rh.test.factory.geral.CandidatoIdiomaFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.test.util.mockObjects.MockCandidato;
import com.fortes.rh.util.DateUtil;
@SuppressWarnings({"unchecked","rawtypes"})
public class CandidatoDaoHibernateTest_Junit4 extends GenericDaoHibernateTest_JUnit4<Candidato>
{
	@Autowired
	private CandidatoDao candidatoDao;
	@Autowired
	private EmpresaDao empresaDao;
	@Autowired
	private AreaInteresseDao areaInteresseDao;
	@Autowired
	private CargoDao cargoDao;
	@Autowired
	private ConhecimentoDao conhecimentoDao;
	@Autowired
	private CandidatoIdiomaDao candidatoIdiomaDao;
	@Autowired
	private IdiomaDao idiomaDao;
	@Autowired
	private CidadeDao cidadeDao;
	@Autowired
	private EstadoDao estadoDao;
	@Autowired
	private BairroDao bairroDao;
	@Autowired
	private ExperienciaDao experienciaDao;
	@Autowired
	private SolicitacaoDao solicitacaoDao;
	@Autowired
	private CandidatoSolicitacaoDao candidatoSolicitacaoDao;
	@Autowired
	private FormacaoDao formacaoDao;

	@Override
	public Candidato getEntity()
	{
		return CandidatoFactory.getCandidato();
	}

	@Override
	public GenericDao<Candidato> getGenericDao()
	{
		return candidatoDao;
	}
	
	private Candidato getCandidato()
	{
		return MockCandidato.getCandidato();
	}
	
	@Test
	public void testFindBuscaCPF() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidatoDisponivel("chico", "1141111111", empresa);
		candidatoDao.save(c1);
		Candidato c2 = getCandidatoDisponivel("bob", "22226222221", empresa);
		candidatoDao.save(c2);
		Candidato c3 = getCandidatoDisponivel("bobinho", "73333333333", empresa);
		candidatoDao.save(c3);

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Map parametros = new HashMap();
		parametros.put("cpfBusca", c3.getPessoal().getCpf());
		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);

		assertFalse(candidatos.isEmpty());
		assertEquals(1, candidatos.size());
	}
	
	private Candidato getCandidatoDisponivel(String nome, String cpf, Empresa empresa){
		Candidato candidato = getCandidato();
		candidato.setEmpresa(empresa);
		candidato.setNome(nome);
		candidato.getPessoal().setCpf(cpf);
		candidato.setDisponivel(true);
		candidato.setContratado(false);
		candidato.setBlackList(false);
		
		return candidato;
	}
	
	private Candidato saveCandidatoDisponivel(String nome, String cpf, Empresa empresa, Collection<AreaInteresse> areaInteresses ){
		Candidato candidato = getCandidatoDisponivel(nome, cpf, empresa);
		candidato.setAreasInteresse(areaInteresses);
		candidatoDao.save(candidato);
		return candidato;
	}

	@Test
	public void testFindBuscaAreas() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		AreaInteresse areaInteresse = AreaInteresseFactory.getAreaInteresse();
		areaInteresse = areaInteresseDao.save(areaInteresse);

		Collection<AreaInteresse> areaInteresses = new ArrayList<AreaInteresse>();
		areaInteresses.add(areaInteresse);

		saveCandidatoDisponivel("chico", "1111111111", empresa, areaInteresses);
		saveCandidatoDisponivel("bob", "22222222221", empresa, null);
		saveCandidatoDisponivel("bobinho", "33333333333", empresa, areaInteresses);
		
		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Map parametros = new HashMap();
		Long[] areaIds = {areaInteresse.getId()};
		parametros.put("areasIds", areaIds);
		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);

		assertFalse(candidatos.isEmpty());
		assertEquals(2, candidatos.size());
	}
	
	@Test
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

		Candidato c1 = getCandidatoDisponivel("chico", "1111111111", empresa);
		c1.getEndereco().setBairro(bairro.getNome());
		candidatoDao.save(c1);

		Candidato c2 = getCandidatoDisponivel("bob", "22222222221", empresa);
		c2.getEndereco().setBairro(bairro.getNome());
		candidatoDao.save(c2);

		Candidato c3 = getCandidatoDisponivel("bobinho", "33333333333", empresa);
		c3.getEndereco().setBairro(bairro2.getNome());
		candidatoDao.save(c3);

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Map parametros = new HashMap();
		String[] bairros = {bairro.getNome()};
		parametros.put("bairros", bairros);
		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);

		assertFalse(candidatos.isEmpty());
		assertEquals(2, candidatos.size());
	}

	// 1:todas
	// 2:qualquer
	// 3:frase exata
	@Test
	public void testFindBuscapalavrasChaveCurriculoEscaneado() throws Exception{
		String palavra = "teste palavra";
		Empresa empresa = empresaDao.save(EmpresaFactory.getEmpresa());

		Candidato c1 = getCandidatoDisponivel("chico", "1111111111", empresa);
		c1.setOcrTexto(palavra);
		candidatoDao.save(c1);

		Candidato c2 = getCandidatoDisponivel("bla", "1111111111", empresa);
		c2.setOcrTexto(palavra);
		candidatoDao.save(c2);

		Candidato c3 = getCandidatoDisponivel("x", "1111111111", empresa);
		c3.setOcrTexto("erro");
		candidatoDao.save(c3);

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Map parametros = new HashMap();
		parametros.put("palavrasChaveCurriculoEscaneado", palavra);
		parametros.put("formas", "1");
		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);

		assertFalse(candidatos.isEmpty());
		assertEquals(2, candidatos.size());

		candidatos.clear();
		parametros.put("formas", "2");
		candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);

		assertFalse(candidatos.isEmpty());
		assertEquals(2, candidatos.size());

		candidatos.clear();
		parametros.put("formas", "3");
		candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);

		assertFalse(candidatos.isEmpty());
		assertEquals(2, candidatos.size());
	}
	@Test
	public void testFindBuscapalavrasChaveOutrosCamposComSomenteSemSolicitacao() throws Exception {
		String palavra = "java avançado";
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);
		
		Experiencia experiencia = ExperienciaFactory.getEntity("Java complementar");
		
		saveCandidatoComFormacao("Java", empresa, palavra, "");
		saveCandidatoComFormacao("Java Avançado", empresa, palavra, "");
		saveCandidatoComFormacao(null, empresa, palavra, "Java Básico, Java Intermediário");
		
		Candidato c4 = CandidatoFactory.getCandidao(empresa);
		experiencia.setCandidato(c4);
		c4.setExperiencias(Arrays.asList(new Experiencia[] {experiencia}));
		candidatoDao.save(c4);
		
		saveCandidato(empresa, "", "Curso html avançado concluído em 2010");
		saveCandidato(empresa, "erro", "");
		
		Collection<Long> idsCandidatos = new ArrayList<Long>();
		
		Map parametros = new HashMap();
		parametros.put("palavrasChaveOutrosCampos", palavra);
		
		parametros.put("formas", "1"); // Todas palavras
		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, true, null, null);
		
		assertFalse(candidatos.isEmpty());
		assertEquals(1, candidatos.size());
		
		candidatos.clear();
		parametros.put("formas", "2"); // Qualquer palavra
		candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);
		
		assertFalse(candidatos.isEmpty());
		assertEquals(5, candidatos.size());
		
		candidatos.clear();
		parametros.put("formas", "3"); // Frase exata
		candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);
		
		assertFalse(candidatos.isEmpty());
		assertEquals(1, candidatos.size());
	}
	
	private Candidato saveCandidatoComFormacao(String nomeFormacao, Empresa empresa, String ocrTexto, String cursos){
		Candidato candidato = getCandidato();
		candidato.setEmpresa(empresa);
		candidato.setOcrTexto(ocrTexto);
		candidato.setCursos(cursos);

		if (nomeFormacao != null) {
			Formacao formacao = FormacaoFactory.getEntity();
			formacao.setCurso(nomeFormacao);
			candidato.setFormacao(Arrays.asList(new Formacao[] {formacao}));
			formacao.setCandidato(candidato);
		}

		candidatoDao.save(candidato);
		return candidato;
	}
	
	private Candidato saveCandidato(Empresa empresa, String ocrTexto, String cursos){
		Candidato candidato = CandidatoFactory.getCandidao(empresa);
		candidato.setOcrTexto(ocrTexto);
		candidato.setCursos(cursos);
		candidatoDao.save(candidato);
		return candidato;
	} 
	
	@Test
	public void testFindBuscaCargos() throws Exception {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("carpinteiro");
		cargo.setNomeMercado("carpinteiro");
		cargo.setAtivo(true);
		cargo = cargoDao.save(cargo);

		Collection<Cargo> cargos = new ArrayList<Cargo>();
		cargos.add(cargo);

		Candidato c1 = getCandidatoDisponivel("chico", "1111111111", empresa);
		c1.setCargos(cargos);
		candidatoDao.save(c1);

		saveCandidatoDisponivel("bob", "22222222421", empresa, null);
		saveCandidatoDisponivel("bobinho", "33334333333", empresa, null);

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Map parametros = new HashMap();
		Long[] cargosIds = {cargo.getId()};
		parametros.put("cargosIds", cargosIds);
		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, true, null, null);

		assertFalse(candidatos.isEmpty());
		assertEquals(1, candidatos.size());

		parametros = new HashMap();
		String[] cargosNomes = {cargo.getNomeMercadoComStatus()};
		parametros.put("cargosNomeMercado", cargosNomes);
		candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);
		
		assertFalse(candidatos.isEmpty());
		assertEquals(1, candidatos.size());
	}
	@Test
	public void testFindBuscaConhecimentos() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento = conhecimentoDao.save(conhecimento);

		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();
		conhecimentos.add(conhecimento);
		
		Candidato c1 = getCandidatoDisponivel("chico", "1111131111", empresa);
		c1.setConhecimentos(conhecimentos);
		candidatoDao.save(c1);

		Candidato c2 = getCandidatoDisponivel("bob", "22222223221", empresa);
		c2.setConhecimentos(conhecimentos);
		candidatoDao.save(c2);

		Candidato c3 = getCandidatoDisponivel("bobinho", "333w3333333", empresa);
		c3.setConhecimentos(conhecimentos);
		candidatoDao.save(c3);

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Map parametros = new HashMap();
		Long[] conhecimentosIds = {conhecimento.getId()};
		parametros.put("conhecimentosIds", conhecimentosIds);
		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);

		assertFalse(candidatos.isEmpty());
		assertEquals(3, candidatos.size());
	}

	@Test
	public void testFindBuscaIdiomaComNivel() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Idioma idioma = IdiomaFactory.getIdioma();
		idioma = idiomaDao.save(idioma);
		
		saveCandidatoComIdioma("chico", "1111111111", empresa, idioma, 'A');
		saveCandidatoComIdioma("bob", "22222222221", empresa, idioma, 'A');
		saveCandidatoComIdioma("bobinho", "33333333333", empresa, idioma, 'B');
		
		Map parametros = new HashMap();
		parametros.put("idioma", idioma.getId());
		parametros.put("nivel", 'A');

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);

		assertEquals(2, candidatos.size());
	}
		
	@Test
	public void testFindBuscaIdiomaSemNivel() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Idioma idioma = IdiomaFactory.getIdioma();
		idioma = idiomaDao.save(idioma);

		saveCandidatoComIdioma("chico", "1211111111", empresa, idioma, 'A');
		saveCandidatoComIdioma("chico", "21222222221", empresa, idioma, 'A');
		saveCandidatoComIdioma("chico", "36333333333", empresa, idioma, 'B');
		
		Map parametros = new HashMap();
		parametros.put("idioma", idioma.getId());
		parametros.put("nivel", null);

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);

		assertEquals(3, candidatos.size());
	}
	
	private Candidato saveCandidatoComIdioma( String nome, String cpf, Empresa empresa, Idioma idioma, char nivel) {
		Candidato candidato = getCandidatoDisponivel("chico", "1111111111", empresa);

		CandidatoIdioma candidatoIdioma = CandidatoIdiomaFactory.getCandidatoIdioma();
		candidatoIdioma.setIdioma(idioma);
		candidatoIdioma.setNivel(nivel);
		candidatoIdioma.setCandidato(candidato);

		Collection<CandidatoIdioma> candidatoIdiomas1 = new ArrayList<CandidatoIdioma>();
		candidatoIdiomas1.add(candidatoIdioma);

		candidato.setCandidatoIdiomas(candidatoIdiomas1);
		candidatoDao.save(candidato);
		
		return candidato;
	}

	@Test
	public void testFindBuscaNome() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidatoDisponivel("chico", "1111511111", empresa);
		candidatoDao.save(c1);
		Candidato c2 = getCandidatoDisponivel("bob", "22222252221", empresa);
		candidatoDao.save(c2);
		Candidato c3 = getCandidatoDisponivel("bobinho", "33353333333", empresa);
		candidatoDao.save(c3);

		Map parametros = new HashMap();
		parametros.put("nomeBusca", c1.getNome());

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);

		assertEquals(1, candidatos.size());
	}
	
	@Test
	public void testFindBuscaDataCadIni() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidatoDisponivel("chico", "1111111111", empresa);
		c1.setDataAtualizacao(DateUtil.criarDataMesAno(02, 01, 2008));
		candidatoDao.save(c1);
		
		Candidato c2 = getCandidatoDisponivel("bob", "22222222221", empresa);
		c2.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2007));
		candidatoDao.save(c2);

		Candidato c3 = getCandidatoDisponivel("bobinho", "33333333333", empresa);
		c3.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2006));
		candidatoDao.save(c3);

		Map parametros = new HashMap();
		parametros.put("dataCadIni", DateUtil.criarDataMesAno(01, 01, 2008));

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, true, null, null);

		assertEquals(1, candidatos.size());
	}
	
	@Test
	public void testFindBuscaDataCadFim() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidatoDisponivel("chico", "1111211111", empresa);
		c1.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2008));
		candidatoDao.save(c1);
		Candidato c2 = getCandidatoDisponivel("bob", "22222232221", empresa);
		c2.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2007));
		candidatoDao.save(c2);
		Candidato c3 = getCandidatoDisponivel("bobinho", "33433333333", empresa);
		c3.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2006));
		candidatoDao.save(c3);

		Map parametros = new HashMap();
		parametros.put("dataCadFim", DateUtil.criarDataMesAno(01, 02, 2008));

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);

		assertEquals(3, candidatos.size());
	}
	
	@Test
	public void testFindBuscaSexo() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidatoDisponivel("chico", "1111111111", empresa);
		c1.getPessoal().setSexo('M');
		candidatoDao.save(c1);
		Candidato c2 = getCandidatoDisponivel("bob", "22222222221", empresa);
		c2.getPessoal().setSexo('M');
		candidatoDao.save(c2);
		Candidato c3 = getCandidatoDisponivel("bobinho", "33333333333", empresa);
		c3.getPessoal().setSexo('F');
		candidatoDao.save(c3);

		Map parametros = new HashMap();
		parametros.put("sexo", String.valueOf(c1.getPessoal().getSexo()));

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);

		assertEquals(2, candidatos.size());
	}
	@Test
	public void testFindBuscaIdadeMin() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidatoDisponivel("chico", "1111211111", empresa);
		c1.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(07, 03, 2009));
		candidatoDao.save(c1);
		Candidato c2 = getCandidatoDisponivel("bob", "22222232221", empresa);
		c2.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(07, 03, 1982));
		candidatoDao.save(c2);
		Candidato c3 = getCandidatoDisponivel("bobinho", "33433333333", empresa);
		c3.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(07, 03, 1981));
		candidatoDao.save(c3);

		Map parametros = new HashMap();
		parametros.put("idadeMin", "26");

		Collection<Long> idsCandidatos = new ArrayList<Long>();
		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);

		assertEquals(2, candidatos.size());
	}
	@Test
	public void testFindBuscaIdadeMax() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidatoDisponivel("chico", "1111211111", empresa);
		c1.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(07, 03, 2009));
		candidatoDao.save(c1);
		Candidato c2 = getCandidatoDisponivel("bob", "22222232221", empresa);
		c2.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(07, 03, 1982));
		candidatoDao.save(c2);
		Candidato c3 = getCandidatoDisponivel("bobinho", "33433333333", empresa);
		c3.getPessoal().setDataNascimento(DateUtil.criarDataMesAno(07, 03, 1981));
		candidatoDao.save(c3);

		Map parametros = new HashMap();
		parametros.put("idadeMax", "26");

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);

		assertEquals(1, candidatos.size());
	}
	@Test
	public void testFindBuscaCidade() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Estado estado = EstadoFactory.getEntity();
		estado = estadoDao.save(estado);

		Cidade cidade = CidadeFactory.getEntity();
		cidade.setUf(estado);
		cidade = cidadeDao.save(cidade);

		Candidato c1 = getCandidatoDisponivel("chico", "1111211111", empresa);
		c1.getEndereco().setCidade(cidade);
		candidatoDao.save(c1);
		Candidato c2 = getCandidatoDisponivel("bob", "22222232221", empresa);
		c2.getEndereco().setCidade(cidade);
		candidatoDao.save(c2);
		Candidato c3 = getCandidatoDisponivel("bobinho", "33433333333", empresa);
		c3.getEndereco().setCidade(cidade);
		candidatoDao.save(c3);

		Map parametros = new HashMap();
		parametros.put("cidade", cidade.getId());

		Collection<Long> idsCandidatos = new ArrayList<Long>();
		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);

		assertEquals(3, candidatos.size());
	}
	@Test
	public void testFindBuscaUf() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Estado estado = EstadoFactory.getEntity();
		estado = estadoDao.save(estado);

		Candidato c1 = getCandidatoDisponivel("chico", "1111211111", empresa);
		c1.getEndereco().setUf(estado);
		candidatoDao.save(c1);
		Candidato c2 = getCandidatoDisponivel("bob", "22222232221", empresa);
		c2.setDataAtualizacao(DateUtil.criarDataMesAno(01, 01, 2007));
		candidatoDao.save(c2);
		Candidato c3 = getCandidatoDisponivel("bobinho", "33433333333", empresa);
		c3.getEndereco().setUf(estado);
		candidatoDao.save(c3);

		Map parametros = new HashMap();
		parametros.put("uf", estado.getId());

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);

		assertEquals(2, candidatos.size());
	}
	@Test
	public void testFindBuscaEscolaridade() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidatoDisponivel("chico", "1111211111", empresa);
		c1.getPessoal().setEscolaridade("852");
		candidatoDao.save(c1);
		Candidato c2 = getCandidatoDisponivel("bob", "22222232221", empresa);
		c2.getPessoal().setEscolaridade("987");
		candidatoDao.save(c2);
		Candidato c3 = getCandidatoDisponivel("bobinho", "33433333333", empresa);
		c3.getPessoal().setEscolaridade("654");
		candidatoDao.save(c3);

		Map parametros = new HashMap();
		parametros.put("escolaridade", "987");

		Collection<Long> idsCandidatos = new ArrayList<Long>();
		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);

		assertEquals(1, candidatos.size());
	}
	@Test
	public void testFindBuscaVeiculo() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidatoDisponivel("chico", "1111211111", empresa);
		c1.getSocioEconomica().setPossuiVeiculo(true);
		candidatoDao.save(c1);
		Candidato c2 = getCandidatoDisponivel("bob", "22222232221", empresa);
		c2.getSocioEconomica().setPossuiVeiculo(true);
		candidatoDao.save(c2);
		Candidato c3 = getCandidatoDisponivel("bobinho", "33433333333", empresa);
		c3.getSocioEconomica().setPossuiVeiculo(false);
		candidatoDao.save(c3);

		Collection<Long> idsCandidatos = new ArrayList<Long>();

		Map parametros = new HashMap();
		parametros.put("veiculo", 'S');

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);

		assertEquals(2, candidatos.size());
	}
	@Test
	public void testFindBuscaCandidatosComExperiencia() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Candidato c1 = getCandidatoDisponivel("chico", "1111211111", empresa);
		c1.getSocioEconomica().setPossuiVeiculo(true);
		candidatoDao.save(c1);
		Candidato c2 = getCandidatoDisponivel("bob", "22222232221", empresa);
		c2.getSocioEconomica().setPossuiVeiculo(true);
		candidatoDao.save(c2);
		Candidato c3 = getCandidatoDisponivel("bobinho", "33433333333", empresa);
		c3.getSocioEconomica().setPossuiVeiculo(false);
		candidatoDao.save(c3);

		Map parametros = new HashMap();
		parametros.put("candidatosComExperiencia", new Long[]{c1.getId(),c2.getId()});

		Collection<Long> idsCandidatos = new ArrayList<Long>();
		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);

		assertEquals(2, candidatos.size());
	}
	@Test
	public void testFindBuscaCandidatosComSolicitacao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		CandidatoSolicitacao candidadatoSolicitacao = new CandidatoSolicitacao();
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		solicitacaoDao.save(solicitacao);
		candidadatoSolicitacao.setSolicitacaoId(solicitacao.getId());
		
		Collection<CandidatoSolicitacao> candidatoSolicitacaos = new ArrayList<CandidatoSolicitacao>();
		candidatoSolicitacaos.add(candidadatoSolicitacao);
		
		Candidato c1 = getCandidatoDisponivel("chico", "1111211111", empresa);
		c1.getSocioEconomica().setPossuiVeiculo(true);
		candidatoDao.save(c1);
		Candidato c2 = getCandidatoDisponivel("bob", "22222232221", empresa);
		c2.getSocioEconomica().setPossuiVeiculo(true);
		candidatoDao.save(c2);
		Candidato c3 = getCandidatoDisponivel("bobinho", "33433333333", empresa);
		c3.getSocioEconomica().setPossuiVeiculo(false);
		candidatoDao.save(c3);

		candidadatoSolicitacao.setCandidato(c1);
		candidatoSolicitacaoDao.save(candidadatoSolicitacao);

		Map parametros = new HashMap();
		parametros.put("candidatosComExperiencia", new Long[]{c1.getId(),c2.getId()});

		Collection<Long> idsCandidatos = new ArrayList<Long>();
		idsCandidatos.add(c1.getId());

		Collection<Candidato> candidatos = candidatoDao.findBusca(parametros, new Long[]{empresa.getId()}, idsCandidatos, false, null, null);

		assertEquals(1, candidatos.size());
	}
	
	@Test
	public void testGetCandidatosByExperiencia()
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

		Cargo ca1 = CargoFactory.getEntity();
		ca1 = cargoDao.save(ca1);

		Cargo ca2 = CargoFactory.getEntity();
		ca2 = cargoDao.save(ca2);

		Experiencia exp1 = ExperienciaFactory.getEntity();
		exp1.setCandidato(c1);
		exp1.setCargo(ca1);
		exp1.setEmpresa("e1");
		exp1 = experienciaDao.save(exp1);

		Experiencia exp2 = ExperienciaFactory.getEntity();
		exp2.setCandidato(c1);
		exp2.setCargo(ca1);
		exp2.setEmpresa("e2");
		exp2 = experienciaDao.save(exp2);

		Experiencia exp3 = ExperienciaFactory.getEntity();
		exp3.setCandidato(c2);
		exp3.setCargo(ca2);
		exp3.setEmpresa("e3");
		exp3 = experienciaDao.save(exp3);

		Long[] experienciasIds = {ca1.getId()};

		Map param = new HashMap();
		param.put("experiencias", experienciasIds);

		Collection<Candidato> candidatos = candidatoDao.getCandidatosByExperiencia(param, new Long[]{empresa.getId()});

		assertEquals(1, candidatos.size());
	}	
}