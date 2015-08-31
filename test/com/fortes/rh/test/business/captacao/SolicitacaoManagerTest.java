package com.fortes.rh.test.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.captacao.AnuncioManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.PausaPreenchimentoVagasManager;
import com.fortes.rh.business.captacao.SolicitacaoAvaliacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManagerImpl;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Anuncio;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.MotivoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

@SuppressWarnings("deprecation")
public class SolicitacaoManagerTest extends MockObjectTestCase
{
	private SolicitacaoManagerImpl solicitacaoManager = new SolicitacaoManagerImpl();
	private Mock solicitacaoDao = null;
	private Mock usuarioManager = null;
	private Mock candidatoSolicitacaoManager = null;
	private Mock anuncioManager = null;
	//private Mock mail = null;
	private Mock gerenciadorComunicacaoManager;
	private Mock solicitacaoAvaliacaoManager;
	private Mock colaboradorQuestionarioManager;
	private Mock pausaPreenchimentoVagasManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		
//		mail = mock(Mail.class);
//		solicitacaoManager.setMail((Mail) mail.proxy());

		solicitacaoDao = new Mock(SolicitacaoDao.class);
		solicitacaoManager.setDao((SolicitacaoDao) solicitacaoDao.proxy());

		gerenciadorComunicacaoManager = new Mock(GerenciadorComunicacaoManager.class);
		solicitacaoManager.setGerenciadorComunicacaoManager((GerenciadorComunicacaoManager) gerenciadorComunicacaoManager.proxy());

		solicitacaoAvaliacaoManager = new Mock(SolicitacaoAvaliacaoManager.class);
		solicitacaoManager.setSolicitacaoAvaliacaoManager((SolicitacaoAvaliacaoManager) solicitacaoAvaliacaoManager.proxy());

		usuarioManager = new Mock(UsuarioManager.class);

		candidatoSolicitacaoManager = new Mock(CandidatoSolicitacaoManager.class);
		solicitacaoManager.setCandidatoSolicitacaoManager((CandidatoSolicitacaoManager) candidatoSolicitacaoManager.proxy());

		anuncioManager = new Mock(AnuncioManager.class);
		solicitacaoManager.setAnuncioManager((AnuncioManager) anuncioManager.proxy());
		
		colaboradorQuestionarioManager = new Mock(ColaboradorQuestionarioManager.class);
		
		pausaPreenchimentoVagasManager = new Mock(PausaPreenchimentoVagasManager.class);
		solicitacaoManager.setPausaPreenchimentoVagasManager((PausaPreenchimentoVagasManager) pausaPreenchimentoVagasManager.proxy());
		
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
	}

	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();
	}

	public void testSetBlackList() throws Exception
	{
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setId(1L);

		solicitacaoDao.expects(once()).method("findByIdProjection").with(eq(solicitacao.getId())).will(returnValue(solicitacao));

		Solicitacao s = solicitacaoManager.findByIdProjection(solicitacao.getId());

		assertEquals(solicitacao, s);
	}

	public void testfindByIdProjectionForUpdate() throws Exception
	{
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setId(1L);

		solicitacaoDao.expects(once()).method("findByIdProjectionForUpdate").with(eq(solicitacao.getId())).will(returnValue(solicitacao));

		Solicitacao s = solicitacaoManager.findByIdProjectionForUpdate(solicitacao.getId());

		assertEquals(solicitacao, s);
	}

	public void testFindByIdProjectionAreaFaixaSalarial() throws Exception
	{
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setId(1L);

		solicitacaoDao.expects(once()).method("findByIdProjectionAreaFaixaSalarial").with(eq(solicitacao.getId())).will(returnValue(solicitacao));

		Solicitacao s = solicitacaoManager.findByIdProjectionAreaFaixaSalarial(solicitacao.getId());

		assertEquals(solicitacao, s);
	}

	public void testRemoveWithObject()
	{

		NoSuchMethodError exp = null;
		try
		{
			solicitacaoManager.remove(new Solicitacao());
		}
		catch (NoSuchMethodError e)
		{
			exp = e;
		}

		assertNotNull(exp);
	}

	public void testRemoveWithLong()
	{

		NoSuchMethodError exp = null;
		try
		{
			solicitacaoManager.remove(1L);
		}
		catch (NoSuchMethodError e)
		{
			exp = e;
		}

		assertNotNull(exp);
	}

	public void testRemoveWithLongArray()
	{

		NoSuchMethodError exp = null;
		try
		{
			solicitacaoManager.remove(new Long[] { 1L });
		}
		catch (NoSuchMethodError e)
		{
			exp = e;
		}

		assertNotNull(exp);
	}

	public void testRemoveCascade()
	{
		Collection<CandidatoSolicitacao> candidatos = new ArrayList<CandidatoSolicitacao>();

		Collection<Anuncio> anuncios = new ArrayList<Anuncio>();

		Anuncio anuncio = new Anuncio();
		anuncio.setId(1L);

		anuncios.add(anuncio);

		candidatoSolicitacaoManager.expects(once()).method("verifyExists").with(ANYTHING, ANYTHING).will(returnValue(false));
		pausaPreenchimentoVagasManager.expects(once()).method("removeBySolicitacaoId").with(ANYTHING);
		anuncioManager.expects(once()).method("removeBySolicitacao").with(ANYTHING);
		solicitacaoDao.expects(once()).method("remove").with(ANYTHING);
		MockSpringUtil.mocks.put("colaboradorQuestionarioManager", colaboradorQuestionarioManager);
		colaboradorQuestionarioManager.expects(once()).method("removeBySolicitacaoId").with(ANYTHING).isVoid();
		solicitacaoAvaliacaoManager.expects(once()).method("removeBySolicitacaoId").with(ANYTHING).isVoid();
		
		
		solicitacaoManager.removeCascade(1L);

		candidatoSolicitacaoManager.expects(once()).method("verifyExists").with(ANYTHING, ANYTHING).will(returnValue(true));
		CandidatoSolicitacao candidato = new CandidatoSolicitacao();
		candidatos.add(candidato);

		solicitacaoManager.removeCascade(1L);
	}

	public void testFindSolicitacaoList()
	{

		Collection<Solicitacao> coll = new ArrayList<Solicitacao>();
		Long empresaId = 1L;
		Boolean encerrada = true;
		char status = StatusAprovacaoSolicitacao.APROVADO;
		Boolean suspensa = true;

		solicitacaoDao.expects(once()).method("findSolicitacaoList").with(eq(empresaId), eq(encerrada), eq(status), eq(suspensa)).will(returnValue(coll));

		assertEquals(coll, solicitacaoManager.findSolicitacaoList(empresaId, encerrada, status, suspensa));

	}

	public void testGetValor()
	{

		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setId(1L);
		Long id = 1L;

		solicitacaoDao.expects(once()).method("getValor").with(eq(id)).will(returnValue(solicitacao));

		assertEquals(solicitacao.getId(), solicitacaoManager.getValor(id).getId());

	}

	
	public void testEncerraSolicitacao() throws Exception
	{

		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setId(1L);
		solicitacao.setDataEncerramento(DateUtil.criarAnoMesDia(2008, 1, 1));

		Empresa empresa = new Empresa();
		empresa.setId(1L);

		gerenciadorComunicacaoManager.expects(once()).method("enviaEmailCandidatosNaoAptos").with(eq(empresa), eq(solicitacao.getId()));
		solicitacaoDao.expects(once()).method("updateEncerraSolicitacao").with(eq(true), eq(solicitacao.getDataEncerramento()), eq(solicitacao.getId()), ANYTHING);

		solicitacaoManager.encerraSolicitacao(solicitacao, empresa);

	}

	public void testUpdateEncerraSolicitacao() throws Exception
	{

		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setId(1L);
		solicitacao.setDataEncerramento(DateUtil.criarAnoMesDia(2008, 1, 1));

		solicitacaoDao.expects(once()).method("updateEncerraSolicitacao").with(eq(true), eq(solicitacao.getDataEncerramento()), eq(solicitacao.getId()), ANYTHING);

		solicitacaoManager.updateEncerraSolicitacao(true, solicitacao.getDataEncerramento(), solicitacao.getId());

	}

	public void testUpdateSuspendeSolicitacao() throws Exception
	{

		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setId(1L);

		solicitacaoDao.expects(once()).method("updateSuspendeSolicitacao").with(eq(true), eq("suspender"), eq(solicitacao.getId()));
		solicitacaoDao.expects(once()).method("findById").with(eq(1L)).will(returnValue(solicitacao));
		pausaPreenchimentoVagasManager.expects(once()).method("save").with(ANYTHING);

		solicitacaoManager.updateSuspendeSolicitacao(true, "suspender", solicitacao.getId());
	}
	
	public void testUpdateSolicitacao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		solicitacao.setCidade(CidadeFactory.getEntity());
		solicitacao.setStatus(StatusAprovacaoSolicitacao.REPROVADO);
		
		Solicitacao solicitacaoAux = SolicitacaoFactory.getSolicitacao();
		solicitacaoAux.setStatus(StatusAprovacaoSolicitacao.APROVADO);
		MockSecurityUtil.verifyRole = false;
		solicitacaoDao.expects(once()).method("findByIdProjectionForUpdate").with(eq(solicitacao.getId())).will(returnValue(solicitacaoAux));
		solicitacaoDao.expects(once()).method("update").with(eq(solicitacao));
		solicitacaoAvaliacaoManager.expects(once()).method("saveAvaliacoesSolicitacao").with(eq(solicitacao.getId()), ANYTHING);
		
		Exception exception = null;
		try
		{
			solicitacaoManager.updateSolicitacao(solicitacao, null, empresa, UsuarioFactory.getEntity());
		}
		catch (Exception e)
		{
			exception = e;
		}
		assertNull(exception);
	}

	public void testGetCountComUsuarioValido()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setId(1L);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);

		Cargo cargo = CargoFactory.getEntity(-1L);
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(-1L);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(-1L);
		MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();

		Solicitacao solicitacao1 = SolicitacaoFactory.getSolicitacao();
		solicitacao1.setId(1L);
		solicitacao1.setData(new Date());
		Solicitacao solicitacao2 = SolicitacaoFactory.getSolicitacao();
		solicitacao2.setId(2L);
		solicitacao2.setData(new Date());

		Collection<Solicitacao> solicitacaos = new ArrayList<Solicitacao>();
		solicitacaos.add(solicitacao1);
		solicitacaos.add(solicitacao2);

		char visualizar = 'E';
		char status = 'T';

		solicitacaoDao.expects(once()).method("getCount").with(new Constraint[] { eq(visualizar), eq(empresa.getId()), eq(usuario), eq(estabelecimento.getId()), eq(areaOrganizacional.getId()), eq(cargo.getId()), eq(motivoSolicitacao.getId()), ANYTHING, eq(status), ANYTHING, eq(null), ANYTHING, eq(null) })
				.will(returnValue(solicitacaos.size()));

		int resutado = solicitacaoManager.getCount(visualizar, empresa.getId(), usuario.getId(), estabelecimento.getId(), areaOrganizacional.getId(), cargo.getId(), motivoSolicitacao.getId(), "desc", status, null, null, new Date(), null);

		assertEquals(solicitacaos.size(), resutado);
	}

	public void testFindAllByVisualizacaoComUsuarioValido()
	{
		Usuario usuario = UsuarioFactory.getEntity();
		usuario.setId(1L);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);

		Cargo cargo = CargoFactory.getEntity(-1L);
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(-1L);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		MotivoSolicitacao motivo = MotivoSolicitacaoFactory.getEntity();

		Solicitacao solicitacao1 = SolicitacaoFactory.getSolicitacao();
		solicitacao1.setId(1L);
		Solicitacao solicitacao2 = SolicitacaoFactory.getSolicitacao();
		solicitacao2.setId(2L);
		Solicitacao solicitacao3 = SolicitacaoFactory.getSolicitacao();
		solicitacao3.setId(3L);
		solicitacao3.setData(new Date());

		Collection<Solicitacao> solicitacaos = new ArrayList<Solicitacao>();
		solicitacaos.add(solicitacao3);

		char visualizar = 'E';
		char status = 'T';

		solicitacaoDao.expects(once()).method("findAllByVisualizacao").with(
				new Constraint[] { eq(1), eq(15), eq(visualizar), eq(empresa.getId()), eq(usuario), eq(estabelecimento.getId()), eq(areaOrganizacional.getId()), eq(cargo.getId()), eq(motivo.getId()), eq(null), eq(status), ANYTHING, eq("3"), eq(null), ANYTHING }).will(returnValue(solicitacaos));

		Collection<Solicitacao> resultado = solicitacaoManager.findAllByVisualizacao(1, 15, visualizar, empresa.getId(), usuario.getId(), estabelecimento.getId(), areaOrganizacional.getId(), cargo.getId(), motivo.getId(), null, status, null, "3", null, new Date() );

		assertEquals(solicitacaos.size(), resultado.size());
	}

	public void testGetCountComUsuarioNulo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);

		MockSpringUtil.mocks.put("usuarioManager", usuarioManager);

		Cargo cargo = CargoFactory.getEntity(-1L);
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(-1L);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		MotivoSolicitacao motivo = MotivoSolicitacaoFactory.getEntity();

		Solicitacao solicitacao1 = SolicitacaoFactory.getSolicitacao();
		solicitacao1.setId(1L);
		solicitacao1.setData(DateUtil.criarAnoMesDia(2015, 01, 01));
		Solicitacao solicitacao2 = SolicitacaoFactory.getSolicitacao();
		solicitacao2.setId(2L);
		solicitacao2.setData(DateUtil.criarAnoMesDia(2015, 01, 31));

		Collection<Solicitacao> solicitacaos = new ArrayList<Solicitacao>();
		solicitacaos.add(solicitacao1);
		solicitacaos.add(solicitacao2);

		char visualizar = 'E';
		char status = 'T';

		solicitacaoDao.expects(once()).method("getCount").with(new Constraint[] { eq(visualizar), eq(empresa.getId()), ANYTHING, eq(estabelecimento.getId()), eq(areaOrganizacional.getId()), eq(cargo.getId()), eq(motivo.getId()), ANYTHING, eq(status), ANYTHING, eq(null), eq(DateUtil.criarAnoMesDia(2015, 01, 01)), eq(DateUtil.criarAnoMesDia(2015, 01, 31)) }).will(
				returnValue(solicitacaos.size()));

		int resultado = solicitacaoManager.getCount(visualizar, empresa.getId(), estabelecimento.getId(), areaOrganizacional.getId(), cargo.getId(), motivo.getId(), null, status, null, DateUtil.criarAnoMesDia(2015, 01, 01), DateUtil.criarAnoMesDia(2015, 01, 31));

		assertEquals(solicitacaos.size(), resultado);
	}

	public void testFindAllByVisualizacaoComUsuarioNulo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);

		MockSpringUtil.mocks.put("usuarioManager", usuarioManager);

		Cargo cargo = CargoFactory.getEntity(-1L);
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(-1L);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		MotivoSolicitacao motivo = MotivoSolicitacaoFactory.getEntity();

		Solicitacao solicitacao1 = SolicitacaoFactory.getSolicitacao();
		solicitacao1.setId(1L);
		Solicitacao solicitacao2 = SolicitacaoFactory.getSolicitacao();
		solicitacao2.setId(2L);
		Solicitacao solicitacao3 = SolicitacaoFactory.getSolicitacao();
		solicitacao3.setId(3L);

		Collection<Solicitacao> solicitacaos = new ArrayList<Solicitacao>();
		solicitacaos.add(solicitacao1);
		solicitacaos.add(solicitacao2);
		solicitacaos.add(solicitacao3);

		char visualizar = 'E';
		char status = 'T';

		solicitacaoDao.expects(once()).method("findAllByVisualizacao").with(
				new Constraint[] { eq(1), eq(15), eq(visualizar), eq(empresa.getId()), eq(null), eq(estabelecimento.getId()), eq(areaOrganizacional.getId()), eq(cargo.getId()), eq(motivo.getId()), eq(null), eq(status), eq(null), eq(""), eq(null), eq(null) }).will(returnValue(solicitacaos));

		Collection<Solicitacao> resultado = solicitacaoManager.findAllByVisualizacao(1, 15, visualizar, empresa.getId(), estabelecimento.getId(), areaOrganizacional.getId(), cargo.getId(), motivo.getId(), null, status, "", null, null);

		assertEquals(solicitacaos.size(), resultado.size());
	}
	
	public void testGetIndicadorMediaDiasPreenchimentoVagas(){
		
		List<IndicadorDuracaoPreenchimentoVaga> list = new ArrayList<IndicadorDuracaoPreenchimentoVaga>();

    	solicitacaoDao.expects(once()).method("getIndicadorMediaDiasPreenchimentoVagas").will(returnValue(list));

    	assertEquals(list, solicitacaoManager.getIndicadorMediaDiasPreenchimentoVagas(new Date(), new Date(), null,null, null, null));
	}
	
	public void testGetIndicadorQtdVagas(){
		
		List<IndicadorDuracaoPreenchimentoVaga> list = new ArrayList<IndicadorDuracaoPreenchimentoVaga>();
		Collection<Long> areasOrganizacionais = new ArrayList<Long>();
		Collection<Long> estabelecimentos = new ArrayList<Long>();
		Long[] solicitacaoIds = null;
		
		Date dataAte = DateUtil.criarAnoMesDia(2008, 05, 8);
		Date dataDe = DateUtil.criarAnoMesDia(2005, 05, 8);
		
		solicitacaoDao.expects(once()).method("getIndicadorQtdVagas").with(new Constraint[] { eq(dataDe), eq(dataAte), eq(areasOrganizacionais),eq(estabelecimentos),eq(solicitacaoIds) }).will(returnValue(list));
		
		assertEquals(list, solicitacaoManager.getIndicadorQtdVagas(dataDe, dataAte, areasOrganizacionais,estabelecimentos, solicitacaoIds));
	}
	
	public void testGetIndicadorQtdCandidatos(){

    	List<IndicadorDuracaoPreenchimentoVaga> list = new ArrayList<IndicadorDuracaoPreenchimentoVaga>();
    	Collection<Long> areasOrganizacionais = new ArrayList<Long>();
    	Collection<Long> estabelecimentos = new ArrayList<Long>();
    	Long[] solicitacaoIds = null;
    	
    	Date dataAte = DateUtil.criarAnoMesDia(2008, 05, 8);
    	Date dataDe = DateUtil.criarAnoMesDia(2005, 05, 8);

    	solicitacaoDao.expects(once()).method("getIndicadorQtdCandidatos").with(new Constraint[] { eq(dataDe), eq(dataAte), eq(areasOrganizacionais),eq(estabelecimentos),eq(solicitacaoIds) }).will(returnValue(list));

    	assertEquals(list, solicitacaoManager.getIndicadorQtdCandidatos(dataDe, dataAte, areasOrganizacionais,estabelecimentos, solicitacaoIds));
    }

	public void testFindQtdContratadosPorFaixa(){
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Long[] solicitacaoIds = null;
		Long[] estabelecimentoIds = null;
		Long[] areaIds = null;
		
		Date dataDe = DateUtil.criarDataMesAno(1, 11, 2010);
		Date dataAte = DateUtil.criarDataMesAno(1, 11, 2011);
		
		Collection<DataGrafico> faixas = new ArrayList<DataGrafico>();
		
		solicitacaoDao.expects(once()).method("findQtdContratadosFaixa").with(new Constraint[] {eq(empresa.getId()), eq(estabelecimentoIds), eq(areaIds), eq(solicitacaoIds), eq(dataDe), eq(dataAte)}).will(returnValue(faixas));
		
		assertEquals(faixas, solicitacaoManager.findQtdContratadosPorFaixa(empresa.getId(), estabelecimentoIds, areaIds, solicitacaoIds, dataDe, dataAte));
	}
	
	public void testFindQtdContratadosPorArea(){
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Long[] solicitacaoIds = null;
		Long[] estabelecimentoIds = null;
		Long[] areaIds = null;
		
		Date dataDe = DateUtil.criarDataMesAno(1, 11, 2010);
		Date dataAte = DateUtil.criarDataMesAno(1, 11, 2011);
		
		Collection<DataGrafico> areas = new ArrayList<DataGrafico>();
		
		solicitacaoDao.expects(once()).method("findQtdContratadosArea").with(new Constraint[] {eq(empresa.getId()), eq(estabelecimentoIds), eq(areaIds), eq(solicitacaoIds), eq(dataDe), eq(dataAte)}).will(returnValue(areas));
		
		assertEquals(areas, solicitacaoManager.findQtdContratadosPorArea(empresa.getId(), estabelecimentoIds, areaIds, solicitacaoIds, dataDe, dataAte));
	}

	public void testFindQtdContratadosPorMotivo(){
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Long[] solicitacaoIds = null;
		Long[] estabelecimentoIds = null;
		Long[] areaIds = null;
		
		Date dataDe = DateUtil.criarDataMesAno(1, 11, 2010);
		Date dataAte = DateUtil.criarDataMesAno(1, 11, 2011);
		
		Collection<DataGrafico> areas = new ArrayList<DataGrafico>();
		
		solicitacaoDao.expects(once()).method("findQtdContratadosMotivo").with(new Constraint[] {eq(empresa.getId()), eq(estabelecimentoIds), eq(areaIds), eq(solicitacaoIds), eq(dataDe), eq(dataAte)}).will(returnValue(areas));
		
		assertEquals(areas, solicitacaoManager.findQtdContratadosPorMotivo(empresa.getId(), estabelecimentoIds, areaIds, solicitacaoIds, dataDe, dataAte));
	}

	public void testGetNomesColabSubstituidosSolicitacaoEncerrada()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Solicitacao solicitacao1 = SolicitacaoFactory.getSolicitacao(1L);
		solicitacao1.setColaboradorSubstituido("Adão");

		Solicitacao solicitacao2 = SolicitacaoFactory.getSolicitacao(1L);
		solicitacao2.setColaboradorSubstituido("Adão, Eva");
		
		Collection<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
		solicitacoes.add(solicitacao1);
		solicitacoes.add(solicitacao2);
		
		solicitacaoDao.expects(once()).method("getNomesColabSubstituidosSolicitacaoEncerrada").with(eq(empresa.getId())).will(returnValue(solicitacoes));
		
		assertEquals(3, solicitacaoManager.getNomesColabSubstituidosSolicitacaoEncerrada(empresa.getId()).size());
	}
	
	// TODO . Por que ta dando pau pra mockar o Mail? 
	// Ver em CandidatoManagerTest funciona.
	
//	public void testEnviarEmailParaLiberadorSolicitacao() throws Exception
//	{
//		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(100L);
//		Empresa empresa = EmpresaFactory.getEmpresa(1L);
//		Long empresaId = 1L;
//		
//		Collection<String> emails = new ArrayList<String>();
//		emails.add("tiagolopes@grupofortes.com.br");
//		emails.add("franciscobarroso@grupofortes.com.br");
//		emails.add("robertson@grupofortes.com.br");
//		
//		Collection<ParametrosDoSistema> parametrosDoSistemas = new ArrayList<ParametrosDoSistema>();
//		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
//		parametrosDoSistemas.add(parametrosDoSistema);
//		
//		MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
//		motivoSolicitacao.setDescricao("Aumento de Quadro");
//		solicitacao.setMotivoSolicitacao(motivoSolicitacao);
//		Usuario solicitante = UsuarioFactory.getEntity(100L);
//		solicitacao.setSolicitante(solicitante);
//		
//		parametrosDoSistemaManager.expects(once()).method("findAll").will(returnValue(parametrosDoSistemas));
//		
//		perfilManager.expects(once()).method("getEmailsByRoleLiberaSolicitacao").with(eq(empresaId)).will(returnValue(emails));
//		
//		solicitacaoDao.expects(once()).method("findByIdProjectionForUpdate").with(ANYTHING).will(returnValue(solicitacao));
//		
//		mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
//		mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
//		mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
//		
//		solicitacaoManager.enviarEmailParaLiberadorSolicitacao(solicitacao, empresa);
//	}
//	
//	public void testEmailSolicitante() throws Exception
//	{
//		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
//		parametrosDoSistema.setAppUrl("url");
//		
//		Empresa empresa = EmpresaFactory.getEmpresa();
//		empresa.setEmailRespRH("chico@teste.com");
//
//		Usuario solicitante = UsuarioFactory.getEntity(2L);
//		Usuario liberador = UsuarioFactory.getEntity(3L);
//		
//		Contato contato = new Contato();
//		contato.setEmail("arnaldo@teste.com");
//		
//		Colaborador arnaldo = ColaboradorFactory.getEntity(5L);
//		arnaldo.setUsuario(solicitante);
//		arnaldo.setNome("Arnaldo Escóssio");
//		arnaldo.setNomeComercial("Arnaldo");
//		arnaldo.setContato(contato);
//		
//		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(12L);
//		solicitacao.setSolicitante(solicitante);
//		solicitacao.setLiberador(liberador);
//		
//		MockSpringUtil.mocks.put("colaboradorManager", colaboradorManager);
//		
//		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
//		solicitacaoDao.expects(once()).method("findByIdProjectionForUpdate").with(eq(solicitacao.getId())).will(returnValue(parametrosDoSistema));
//		colaboradorManager.expects(once()).method("findByUsuarioProjection").with(eq(solicitacao.getSolicitante().getId())).will(returnValue(arnaldo));
//		colaboradorManager.expects(once()).method("findByUsuarioProjection").with(eq(solicitacao.getLiberador().getId())).will(returnValue(arnaldo));
//		mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});
//		//mail.expects(once()).method("send").with(new Constraint[]{eq(empresa),eq(parametrosDoSistema),ANYTHING,ANYTHING,ANYTHING}).isVoid();
//		
//		Exception exc = null;
//		try {
//			solicitacaoManager.emailSolicitante(solicitacao, empresa, solicitante);
//		} catch (Exception e) {
//			exc = e;
//		}
//		assertNull(exc);
//	}
}