package com.fortes.rh.test.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.captacao.AnuncioManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.PausaPreenchimentoVagasManager;
import com.fortes.rh.business.captacao.SolicitacaoAvaliacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManagerImpl;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.dao.captacao.SolicitacaoDao;
import com.fortes.rh.model.captacao.Anuncio;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

@SuppressWarnings("deprecation")
public class SolicitacaoManagerTest extends MockObjectTestCase
{
	private SolicitacaoManagerImpl solicitacaoManager = new SolicitacaoManagerImpl();
	private Mock solicitacaoDao = null;
	private Mock candidatoSolicitacaoManager = null;
	private Mock anuncioManager = null;
	private Mock gerenciadorComunicacaoManager;
	private Mock solicitacaoAvaliacaoManager;
	private Mock colaboradorQuestionarioManager;
	private Mock pausaPreenchimentoVagasManager;
	private Mock configuracaoNivelCompetenciaManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		
		configuracaoNivelCompetenciaManager = new Mock(ConfiguracaoNivelCompetenciaManager.class);
		solicitacaoManager.setConfiguracaoNivelCompetenciaManager((ConfiguracaoNivelCompetenciaManager) configuracaoNivelCompetenciaManager.proxy());
		
		solicitacaoDao = new Mock(SolicitacaoDao.class);
		solicitacaoManager.setDao((SolicitacaoDao) solicitacaoDao.proxy());

		gerenciadorComunicacaoManager = new Mock(GerenciadorComunicacaoManager.class);
		solicitacaoManager.setGerenciadorComunicacaoManager((GerenciadorComunicacaoManager) gerenciadorComunicacaoManager.proxy());

		solicitacaoAvaliacaoManager = new Mock(SolicitacaoAvaliacaoManager.class);
		solicitacaoManager.setSolicitacaoAvaliacaoManager((SolicitacaoAvaliacaoManager) solicitacaoAvaliacaoManager.proxy());

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
		MockSecurityUtil.verifyRole = false;
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
		configuracaoNivelCompetenciaManager.expects(once()).method("removeBySolicitacaoId").with(ANYTHING).isVoid();
		
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

	public void testGetIndicadorMediaDiasPreenchimentoVagas(){
		
		List<IndicadorDuracaoPreenchimentoVaga> list = new ArrayList<IndicadorDuracaoPreenchimentoVaga>();

    	solicitacaoDao.expects(once()).method("getIndicadorMediaDiasPreenchimentoVagas").will(returnValue(list));

    	assertEquals(list, solicitacaoManager.getIndicadorMediaDiasPreenchimentoVagas(new Date(), new Date(), null,null, null, null, true));
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