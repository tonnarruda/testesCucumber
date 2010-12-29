package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.ExperienciaManager;
import com.fortes.rh.business.captacao.FormacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorIdiomaManager;
import com.fortes.rh.business.geral.ColaboradorManagerImpl;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.DynaRecord;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.model.geral.relatorio.TurnOver;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.ExperienciaFactory;
import com.fortes.rh.test.factory.captacao.FormacaoFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;
import com.fortes.rh.test.factory.geral.CandidatoIdiomaFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.test.util.mockObjects.MockImportacaoCSVUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.test.util.mockObjects.MockTransactionStatus;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.util.importacao.ImportacaoCSVUtil;
import com.fortes.rh.web.ws.AcPessoalClientColaborador;

@SuppressWarnings("unchecked")
public class ColaboradorManagerTest extends MockObjectTestCase
{
    private ColaboradorManagerImpl colaboradorManager = new ColaboradorManagerImpl();
    private Mock colaboradorDao = null;
    private Mock transactionManager;
    private Mock candidatoManager;
    private Mock historicoColaboradorManager;
    private Mock areaOrganizacinoalManager;
    private Mock indiceManager;
    private Mock faixaSalarialManager;
    private Mock usuarioManager;
    private Mock cidadeManager;
    private Mock estadoManager;
    private Mock formacaoManager;
    private Mock colaboradorIdiomaManager;
    private Mock experienciaManager;
    private Mock acPessoalClientColaborador;
    private Mock parametrosDoSistemaManager;
	private Colaborador colaborador;
	private List<Formacao> formacoes;
	private List<CandidatoIdioma> idiomas;
	private List<Experiencia> experiencias;


    protected void setUp() throws Exception
    {
        super.setUp();
        colaboradorDao = new Mock(ColaboradorDao.class);

        colaboradorManager.setDao((ColaboradorDao) colaboradorDao.proxy());

        candidatoManager = new Mock(CandidatoManager.class);
        colaboradorManager.setCandidatoManager((CandidatoManager) candidatoManager.proxy());

        historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);
        colaboradorManager.setHistoricoColaboradorManager((HistoricoColaboradorManager)historicoColaboradorManager.proxy());

        areaOrganizacinoalManager = new Mock(AreaOrganizacionalManager.class);
        colaboradorManager.setAreaOrganizacionalManager((AreaOrganizacionalManager)areaOrganizacinoalManager.proxy());

        indiceManager = new Mock(IndiceManager.class);
        colaboradorManager.setIndiceManager((IndiceManager)indiceManager.proxy());

        faixaSalarialManager = new Mock(FaixaSalarialManager.class);
        colaboradorManager.setFaixaSalarialManager((FaixaSalarialManager) faixaSalarialManager.proxy());

        transactionManager = new Mock(PlatformTransactionManager.class);
        colaboradorManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());

        cidadeManager = new Mock(CidadeManager.class);
        colaboradorManager.setCidadeManager((CidadeManager) cidadeManager.proxy());

        estadoManager = new Mock(EstadoManager.class);
        colaboradorManager.setEstadoManager((EstadoManager) estadoManager.proxy());

        experienciaManager = new Mock(ExperienciaManager.class);
        colaboradorManager.setExperienciaManager((ExperienciaManager) experienciaManager.proxy());

        formacaoManager = new Mock(FormacaoManager.class);
        colaboradorManager.setFormacaoManager((FormacaoManager) formacaoManager.proxy());

        colaboradorIdiomaManager = new Mock(ColaboradorIdiomaManager.class);
        colaboradorManager.setColaboradorIdiomaManager((ColaboradorIdiomaManager) colaboradorIdiomaManager.proxy());

        acPessoalClientColaborador = mock(AcPessoalClientColaborador.class);
		colaboradorManager.setAcPessoalClientColaborador((AcPessoalClientColaborador) acPessoalClientColaborador.proxy());

		parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
		colaboradorManager.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
		
        usuarioManager = new Mock(UsuarioManager.class);
        MockSpringUtil.mocks.put("usuarioManager", usuarioManager);
        
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        Mockit.redefineMethods(ImportacaoCSVUtil.class, MockImportacaoCSVUtil.class);
    }

    public void testFindByAreaEstabelecimento()
    {
        AreaOrganizacional areaOrganizacional = new AreaOrganizacional();
        areaOrganizacional.setId(1L);

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(2L);

        Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
        colaboradores.add(new Colaborador());

        colaboradorDao.expects(once()).method("findByAreaEstabelecimento").with(eq(areaOrganizacional.getId()), eq(estabelecimento.getId())).will(returnValue(colaboradores));

        assertEquals(1, colaboradorManager.findByAreaEstabelecimento(areaOrganizacional.getId(), estabelecimento.getId()).size());
    }

    public void testFindByAreasOrganizacionaisEstabelecimentos()
    {
        AreaOrganizacional areaOrganizacional = new AreaOrganizacional();
        areaOrganizacional.setId(1L);
        Collection<Long> areasIds = new ArrayList<Long>();
        areasIds.add(areaOrganizacional.getId());

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(2L);
        Collection<Long> estabelecimentosIds = new ArrayList<Long>();
        estabelecimentosIds.add(estabelecimento.getId());

        Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
        colaboradores.add(new Colaborador());

        colaboradorDao.expects(once()).method("findByAreasOrganizacionaisEstabelecimentos").with(eq(areasIds), eq(estabelecimentosIds)).will(returnValue(colaboradores));

        assertEquals(1, colaboradorManager.findByAreasOrganizacionaisEstabelecimentos(areasIds, estabelecimentosIds).size());
    }

    public void testGetColaboradoresByEstabelecimentoAreaGrupo()
    {
        AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
        Collection<Long> areasIds = new ArrayList<Long>();
        areasIds.add(areaOrganizacional.getId());

        Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(2L);
        Collection<Long> estabelecimentosIds = new ArrayList<Long>();
        estabelecimentosIds.add(estabelecimento.getId());

        Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
        colaboradores.add(new Colaborador());

        colaboradorDao.expects(once()).method("findByAreasOrganizacionaisEstabelecimentos").with(eq(areasIds), eq(estabelecimentosIds)).will(returnValue(colaboradores));
        assertEquals(colaboradores, colaboradorManager.getColaboradoresByEstabelecimentoAreaGrupo('1', estabelecimentosIds, areasIds, null));

        colaboradorDao.expects(once()).method("findByCargoIdsEstabelecimentoIds").with(ANYTHING, eq(estabelecimentosIds)).will(returnValue(colaboradores));
        assertEquals(colaboradores, colaboradorManager.getColaboradoresByEstabelecimentoAreaGrupo('2', estabelecimentosIds, null, null));
    }

    public void testFindByEstabelecimento()
    {
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(2L);

        Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
        colaboradores.add(new Colaborador());

        colaboradorDao.expects(once()).method("findByEstabelecimento").with(eq(new Long[]{estabelecimento.getId()})).will(returnValue(colaboradores));

        assertEquals(1, colaboradorManager.findByEstabelecimento(new Long[]{estabelecimento.getId()}).size());
    }

    public void testFindColaboradorByIdProjection()
    {
        Colaborador colaborador = new Colaborador();
        colaborador.setId(1L);

        colaboradorDao.expects(once()).method("findColaboradorByIdProjection").with(eq(colaborador.getId())).will(returnValue(colaborador));

        assertEquals(colaborador, colaboradorManager.findColaboradorByIdProjection(colaborador.getId()));
    }

    public void testFindByCargoIdsEstabelecimentoIds()
    {
        Cargo cargo = new Cargo();
        cargo.setId(2L);
        Collection<Long> cargosIds = new ArrayList<Long>();
        cargosIds.add(cargo.getId());

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(2L);
        Collection<Long> estabelecimentosIds = new ArrayList<Long>();
        estabelecimentosIds.add(estabelecimento.getId());

        Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
        colaboradores.add(new Colaborador());

        colaboradorDao.expects(once()).method("findByCargoIdsEstabelecimentoIds").with(eq(cargosIds), eq(estabelecimentosIds)).will(returnValue(colaboradores));

        assertEquals(1, colaboradorManager.findByCargoIdsEstabelecimentoIds(cargosIds, estabelecimentosIds).size());
    }

    public void testFindByGrupoOcupacionalIdsEstabelecimentoIds()
    {
        GrupoOcupacional grupoOcupacional = new GrupoOcupacional();
        grupoOcupacional.setId(2L);
        Collection<Long> grupoOcupacionalIds = new ArrayList<Long>();
        grupoOcupacionalIds.add(grupoOcupacional.getId());

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(2L);
        Collection<Long> estabelecimentosIds = new ArrayList<Long>();
        estabelecimentosIds.add(estabelecimento.getId());

        Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
        colaboradores.add(new Colaborador());

        colaboradorDao.expects(once()).method("findByGrupoOcupacionalIdsEstabelecimentoIds").with(eq(grupoOcupacionalIds), eq(estabelecimentosIds)).will(returnValue(colaboradores));

        assertEquals(1, colaboradorManager.findByGrupoOcupacionalIdsEstabelecimentoIds(grupoOcupacionalIds, estabelecimentosIds).size());
    }

    public void testGetColaboradoresIntegraAc()
    {
        Colaborador c1 = ColaboradorFactory.getEntity();
        c1.setNaoIntegraAc(false);

        Colaborador c2 = ColaboradorFactory.getEntity();
        c2.setNaoIntegraAc(false);

        Colaborador c3 = ColaboradorFactory.getEntity();
        c3.setNaoIntegraAc(true);

        Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
        colaboradores.add(c1);
        colaboradores.add(c2);
        colaboradores.add(c3);

        Collection<Colaborador> retorno = colaboradorManager.getColaboradoresIntegraAc(colaboradores);

        assertEquals(2, retorno.size());
    }

	public void testGetCountParametros()
    {
        Map parametros = new HashMap();

        colaboradorDao.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(2));

        Integer count = colaboradorManager.getCount(parametros);

        assertTrue(count.equals(2));
    }

    public void testFindAreaOrganizacionalByAreas()
    {
        HashMap<Object, Object> parametros = new HashMap<Object, Object>();
        parametros.put("areas", new ArrayList<String>());

        Colaborador colaborador = ColaboradorFactory.getEntity();
        colaborador.setId(1L);

        Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
        colaboradors.add(colaborador);

        colaboradorDao.expects(once()).method("findAreaOrganizacionalByAreas").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING, ANYTHING}).will(returnValue(colaboradors));

        Collection<Colaborador> retorno = colaboradorManager.findAreaOrganizacionalByAreas(false, null, null, null, null, null);

        assertEquals(1, retorno.size());
    }

    private Map getParametros()
    {
        Map parametros = new HashMap();
        parametros.put("cargos", new String[]{"1","2"});
        parametros.put("estabelecimentos", new String[]{"1","2"});

        return parametros;
    }

    public void testFindAdmitidosByPeriodo()
    {
        Date ini = DateUtil.criarDataMesAno(01, 01, 2001);
        Date fim = DateUtil.criarDataMesAno(30, 01, 2001);

        colaboradorDao.expects(once()).method("findColaborador").with(ANYTHING).will(returnValue(ColaboradorFactory.getCollection()));

        assertEquals(1, colaboradorManager.findAdmitidosByPeriodo(ini, fim, getParametros()).size());
    }

    public void testFindDemitidosByPeriodo()
    {
        Date ini = DateUtil.criarDataMesAno(01, 01, 2001);
        Date fim = DateUtil.criarDataMesAno(30, 01, 2001);

        colaboradorDao.expects(once()).method("findColaborador").with(ANYTHING).will(returnValue(ColaboradorFactory.getCollection()));

        assertEquals(1, colaboradorManager.findDemitidosByPeriodo(ini, fim, getParametros()).size());
    }

    public void testFindColaboradoresInData()
    {
        Date data = DateUtil.criarDataMesAno(30, 01, 1945);

        colaboradorDao.expects(once()).method("findColaborador").with(ANYTHING).will(returnValue(ColaboradorFactory.getCollection()));

        assertEquals(1, colaboradorManager.findColaboradorInData(data, getParametros()).size());
    }

    public void testGetTurnOverByMes()
    {
        Date data = DateUtil.criarDataMesAno("02/1945");

        Collection<Colaborador> colMesAnterior = new ArrayList<Colaborador>();
        colMesAnterior.add(ColaboradorFactory.getEntity());
        colMesAnterior.add(ColaboradorFactory.getEntity());
        colMesAnterior.add(ColaboradorFactory.getEntity());
        colMesAnterior.add(ColaboradorFactory.getEntity());
        colMesAnterior.add(ColaboradorFactory.getEntity());

        Collection<Colaborador> colAdmitidosMes = new ArrayList<Colaborador>();
        colAdmitidosMes.add(ColaboradorFactory.getEntity());
        colAdmitidosMes.add(ColaboradorFactory.getEntity());

        Collection<Colaborador> colDemitidosMes = new ArrayList<Colaborador>();
        colDemitidosMes.add(ColaboradorFactory.getEntity());

        colaboradorDao.expects(once()).method("findColaborador").with(ANYTHING).will(returnValue(colMesAnterior));
        colaboradorDao.expects(once()).method("findColaborador").with(ANYTHING).will(returnValue(colDemitidosMes));
        colaboradorDao.expects(once()).method("findColaborador").with(ANYTHING).will(returnValue(colAdmitidosMes));

        TurnOver turnOver = colaboradorManager.getTurnOverByMes(data, getParametros());
        assertEquals(30.00, turnOver.getTurnOver());
        assertEquals(2.0, turnOver.getQtdAdmitidos());
        assertEquals(1.0, turnOver.getQtdDemitidos());
        assertEquals(5.0, turnOver.getQtdAtivos());
    }

    public void testGetTurnOver() throws ColecaoVaziaException
    {
        String de = "01/1945";
        String ate = "03/1945";

        Colaborador c1 = ColaboradorFactory.getEntity();
        c1.setId(1L);
        c1.setDataAdmissao(DateUtil.criarDataMesAno("01/1940"));
        Colaborador c2 = ColaboradorFactory.getEntity();
        c2.setId(2L);
        c2.setDataAdmissao(DateUtil.criarDataMesAno("01/1940"));
        Colaborador c3 = ColaboradorFactory.getEntity();
        c3.setId(3L);
        c3.setDataAdmissao(DateUtil.criarDataMesAno("01/1940"));
        Colaborador c4 = ColaboradorFactory.getEntity();
        c4.setId(4L);
        c4.setDataAdmissao(DateUtil.criarDataMesAno("01/1940"));
        c4.setDataDesligamento(DateUtil.criarDataMesAno("02/1945"));
        Colaborador c5 = ColaboradorFactory.getEntity();
        c5.setId(5L);
        c5.setDataAdmissao(DateUtil.criarDataMesAno("01/1940"));
        c5.setDataDesligamento(DateUtil.criarDataMesAno("02/1945"));

        Collection<Colaborador> colMesAnterior = new ArrayList<Colaborador>();
        colMesAnterior.add(c1);
        colMesAnterior.add(c2);
        colMesAnterior.add(c3);
        colMesAnterior.add(c4);
        colMesAnterior.add(c5);

        Collection<Colaborador> colDemitidosMesFev = new ArrayList<Colaborador>();
        colDemitidosMesFev.add(c4);
        colDemitidosMesFev.add(c5);

        Colaborador c6 = ColaboradorFactory.getEntity();
        c6.setId(6L);
        c6.setDataAdmissao(DateUtil.criarDataMesAno("03/1945"));

        Colaborador c7 = ColaboradorFactory.getEntity();
        c7.setId(7L);
        c7.setDataAdmissao(DateUtil.criarDataMesAno("03/1945"));

        Colaborador c8 = ColaboradorFactory.getEntity();
        c8.setId(8L);
        c8.setDataAdmissao(DateUtil.criarDataMesAno("03/1945"));

        Collection<Colaborador> colAdmitidosMesMar = new ArrayList<Colaborador>();
        colAdmitidosMesMar.add(c6);
        colAdmitidosMesMar.add(c7);

        Collection<Colaborador> colDez = new ArrayList<Colaborador>();
        colDez.addAll(colMesAnterior);
        Collection<Colaborador> colJan = new ArrayList<Colaborador>();
        colJan.addAll(colDez);
        Collection<Colaborador> colFev = new ArrayList<Colaborador>();
        colFev.addAll(colJan);
        colFev.remove(c4);
        colFev.remove(c5);

        Map parametros = getParametros();

        colaboradorDao.expects(once()).method("findColaborador").with(ANYTHING).will(returnValue(colFev));
        colaboradorDao.expects(once()).method("findColaborador").with(ANYTHING).will(returnValue(new ArrayList<Colaborador>()));
        colaboradorDao.expects(once()).method("findColaborador").with(ANYTHING).will(returnValue(colAdmitidosMesMar));

        colaboradorDao.expects(once()).method("findColaborador").with(ANYTHING).will(returnValue(colJan));
        colaboradorDao.expects(once()).method("findColaborador").with(ANYTHING).will(returnValue(colDemitidosMesFev));
        colaboradorDao.expects(once()).method("findColaborador").with(ANYTHING).will(returnValue(new ArrayList<Colaborador>()));

        colaboradorDao.expects(once()).method("findColaborador").with(ANYTHING).will(returnValue(colDez));
        colaboradorDao.expects(once()).method("findColaborador").with(ANYTHING).will(returnValue(new ArrayList<Colaborador>()));
        colaboradorDao.expects(once()).method("findColaborador").with(ANYTHING).will(returnValue(new ArrayList<Colaborador>()));

        Collection<TurnOver> retorno = colaboradorManager.getTurnOver(de, ate, parametros);
        assertEquals(3, retorno.size());
        TurnOver toJan = (TurnOver) retorno.toArray()[0];
        assertEquals(0.0, toJan.getTurnOver());
        assertEquals(DateUtil.criarDataMesAno(01, 01, 1945), toJan.getMesAno());
        TurnOver toFev = (TurnOver) retorno.toArray()[1];
        assertEquals(20.0, toFev.getTurnOver());
        assertEquals(DateUtil.criarDataMesAno(01, 02, 1945), toFev.getMesAno());
        TurnOver toMar = (TurnOver) retorno.toArray()[2];
        assertEquals(33.33333333333333, toMar.getTurnOver());
        assertEquals(DateUtil.criarDataMesAno(01, 03, 1945), toMar.getMesAno());
    }

    public void testFindColaboradoresMotivoDemissao() throws Exception
    {
        colaboradorDao.expects(once()).method("findColaboradoresMotivoDemissao").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(ColaboradorFactory.getCollection()));
        assertNotNull(colaboradorManager.findColaboradoresMotivoDemissao(new Long[]{}, new Long[]{}, new Long[]{}, new Date(), new Date()));
    }

    public void testFindColaboradoresMotivoDemissaoException() throws Exception
    {
        Exception exc = null;
        try
        {
            colaboradorDao.expects(once()).method("findColaboradoresMotivoDemissao").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(null));
            colaboradorManager.findColaboradoresMotivoDemissao(new Long[]{}, new Long[]{}, new Long[]{}, new Date(), new Date());
        }
        catch (Exception e)
        {
            exc = e;
        }

        assertNotNull(exc);
    }

    public void testFindColaboradoresMotivoDemissaoQuantidade() throws Exception
    {
        List<Object[]> lista = new ArrayList<Object[]>();
        lista.add(new Object[]{"motivo",1});

        colaboradorDao.expects(once()).method("findColaboradoresMotivoDemissaoQuantidade").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(lista));
        assertNotNull(colaboradorManager.findColaboradoresMotivoDemissaoQuantidade(new Long[]{}, new Long[]{}, new Long[]{}, new Date(), new Date()));
    }

    public void testDesligaColaborador() throws Exception
    {
        Colaborador colaborador = ColaboradorFactory.getEntity(1L);

        transactionManager.expects(atLeastOnce()).method("getTransaction").with(ANYTHING).will(returnValue(new MockTransactionStatus()));
        usuarioManager.expects(once()).method("desativaAcessoSistema").with(eq(colaborador.getId()));
        colaboradorDao.expects(once()).method("desligaColaborador").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING});
        transactionManager.expects(atLeastOnce()).method("commit").with(ANYTHING);

        colaboradorManager.desligaColaborador(true, new Date(), "observacao", 1L, colaborador.getId());
    }
    public void testFindByAreasOrganizacionalIds() throws Exception
    {
    	Long[] ids = new Long[]{1L};
    	colaboradorDao.expects(once()).method("findByAreaOrganizacionalIds").with(eq(ids)).will(returnValue(new ArrayList<Colaborador>()));
    	
    	assertNotNull(colaboradorManager.findByAreasOrganizacionalIds(ids));
    }
    
    public void testSaveDetalhes() {
    	// dado que
    	dadoUmColaboradorQualquer();
    	dadoDuasFormacoes();
    	dadoDoisIdiomas();
    	dadoDuasExperiencias();
    	// quando
    	colaboradorManager.saveDetalhes(colaborador, formacoes, idiomas, experiencias);
    	// entao espera-se que
    	assertQueFormacoesForamSalvas();
    	assertQueIdiomasForamSalvos();
    	assertQueExperienciasForamSalvas();
    }
    

    private void assertQueExperienciasForamSalvas() {
    	assertQueDadosDaExperienciaForamAlterados(experiencias.get(0));
    	assertQueDadosDaExperienciaForamAlterados(experiencias.get(1));
    	experienciaManager.verify();
	}

	private void assertQueDadosDaExperienciaForamAlterados(Experiencia experiencia) {
		assertNull("id", experiencia.getId());
		assertEquals("colaborador", colaborador, experiencia.getColaborador());
		assertNull("candidato", experiencia.getCandidato());
		assertNull("cargo", experiencia.getCargo());
	}

	private void assertQueIdiomasForamSalvos() {
    	colaboradorIdiomaManager.verify();
	}

	private void assertQueFormacoesForamSalvas() {
		assertQueDadosDaFormacaoForamAlterados(formacoes.get(0));
    	assertQueDadosDaFormacaoForamAlterados(formacoes.get(1));
    	formacaoManager.verify();
	}
	
	private void assertQueDadosDaFormacaoForamAlterados(Formacao formacao) {
		assertNull("id", formacao.getId());
		assertEquals("colaborador", colaborador, formacao.getColaborador());
		assertNull("candidato", formacao.getCandidato());
	}

	private void dadoDuasExperiencias() {
    	experiencias = new ArrayList<Experiencia>();
    	experiencias.add(ExperienciaFactory.getEntity(1L));
    	experiencias.add(ExperienciaFactory.getEntity(2L));
    	// esperado
    	experienciaManager.expects(once()).method("save").with(eq(experiencias.get(0)));
    	experienciaManager.expects(once()).method("save").with(eq(experiencias.get(1)));
	}

	private void dadoDoisIdiomas() {
    	idiomas = new ArrayList<CandidatoIdioma>();
    	idiomas.add(CandidatoIdiomaFactory.getCandidatoIdioma(1L));
    	idiomas.add(CandidatoIdiomaFactory.getCandidatoIdioma(2L));
    	// esperado
    	colaboradorIdiomaManager.expects(once()).method("save").with(ANYTHING); // codigo instancia novos idiomas
    	colaboradorIdiomaManager.expects(once()).method("save").with(ANYTHING); // codigo instancia novos idiomas
	}

	private void dadoDuasFormacoes() {
    	formacoes = new ArrayList<Formacao>();
    	formacoes.add(FormacaoFactory.getEntity(1L));
    	formacoes.add(FormacaoFactory.getEntity(2L));
    	// esperado
    	formacaoManager.expects(once()).method("save").with(eq(formacoes.get(0)));
    	formacaoManager.expects(once()).method("save").with(eq(formacoes.get(1)));
	}

	private void dadoUmColaboradorQualquer() {
		colaborador = ColaboradorFactory.getEntity(14L);
	}

	public void testRemoveColaborador() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(true);
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);

    	formacaoManager.expects(once()).method("removeColaborador").with(eq(colaborador)).isVoid();
    	colaboradorIdiomaManager.expects(once()).method("removeColaborador").with(eq(colaborador)).isVoid();
    	experienciaManager.expects(once()).method("removeColaborador").with(eq(colaborador)).isVoid();
    	historicoColaboradorManager.expects(once()).method("removeColaborador").with(eq(colaborador.getId())).isVoid();
    	colaboradorDao.expects(once()).method("remove").with(eq(colaborador.getId())).isVoid();
    	colaboradorDao.expects(once()).method("findColaboradorByIdProjection").with(eq(colaborador.getId())).will(returnValue(colaborador));
    	acPessoalClientColaborador.expects(once()).method("remove").with(ANYTHING, ANYTHING).will(returnValue(true));

    	Exception exception = null;
    	try
		{
    		colaboradorManager.remove(colaborador, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
    }

    public void testRemoveColaboradorException() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(true);
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);

    	formacaoManager.expects(once()).method("removeColaborador").with(eq(colaborador)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(colaborador.getId(),""))));;

    	Exception exception = null;
    	try
    	{
    		colaboradorManager.remove(colaborador, empresa);
    	}
    	catch (Exception e)
    	{
    		exception = e;
    	}

    	assertNotNull(exception);
    }

    public void testFindColaboradoresMotivoDemissaoQuantidadeException() throws Exception
    {
        Exception exc = null;
        try
        {
            colaboradorDao.expects(once()).method("findColaboradoresMotivoDemissaoQuantidade").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(null));
            colaboradorManager.findColaboradoresMotivoDemissaoQuantidade(new Long[]{}, new Long[]{}, new Long[]{}, new Date(), new Date());
        }
        catch (Exception e)
        {
            exc = e;
        }

        assertNotNull(exc);
    }


    public void testFindProjecaoSalarial() throws Exception
    {
        AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
        Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
        colaborador1.setAreaOrganizacional(areaOrganizacional);

        Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
        colaboradors.add(colaborador1);

        Empresa empresa = EmpresaFactory.getEmpresa(1L);

        TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();

        Date data = new Date();

        Collection<Long> estabelecimentoIds = new ArrayList<Long>(1);
        estabelecimentoIds.add(1L);
        Collection<Long> cargoIds = new ArrayList<Long>(1);;
        cargoIds.add(1L);
        Collection<Long> grupoIds = new ArrayList<Long>(1);;
        grupoIds.add(1L);
        Collection<Long> areaIds = new ArrayList<Long>(1);;
        areaIds.add(1L);

        String filtro = "";

        colaboradorDao.expects(once()).method("findProjecaoSalarialByHistoricoColaborador").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colaboradors));
        areaOrganizacinoalManager.expects(once()).method("findAllList").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));
        areaOrganizacinoalManager.expects(once()).method("montaFamilia").with(ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));
        areaOrganizacinoalManager.expects(once()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING).will(returnValue(new AreaOrganizacional()));

        Collection<Colaborador> retorno = colaboradorManager.findProjecaoSalarial(tabelaReajusteColaborador.getId(), data, estabelecimentoIds, areaIds, grupoIds, cargoIds, filtro, empresa.getId());

        assertEquals("Sem TabelaReajusteColaborador", colaboradors.size(), retorno.size());

        tabelaReajusteColaborador.setId(1L);

        colaboradorDao.expects(once()).method("findProjecaoSalarialByHistoricoColaborador").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colaboradors));
        colaboradorDao.expects(once()).method("findProjecaoSalarialByTabelaReajusteColaborador").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colaboradors));
        areaOrganizacinoalManager.expects(once()).method("findAllList").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));
        areaOrganizacinoalManager.expects(once()).method("montaFamilia").with(ANYTHING).will(returnValue(new ArrayList<AreaOrganizacional>()));
        areaOrganizacinoalManager.expects(once()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING).will(returnValue(new AreaOrganizacional()));

        retorno = colaboradorManager.findProjecaoSalarial(tabelaReajusteColaborador.getId(), data, estabelecimentoIds, areaIds, grupoIds, cargoIds, filtro, empresa.getId());

        assertEquals("Com TabelaReajusteColaborador", colaboradors.size(), retorno.size());
    }

    public void testOrdenaPorEstabelecimentoAreaOrGrupoOcupacionalFiltro1() throws Exception
    {
        Empresa empresa = EmpresaFactory.getEmpresa(1L);

        AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
        areaOrganizacional.setEmpresa(empresa);

        Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
        areas.add(areaOrganizacional);

        Colaborador colaborador = ColaboradorFactory.getEntity(1L);
        colaborador.setAreaOrganizacional(areaOrganizacional);

        Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
        colaboradors.add(colaborador);

        areaOrganizacinoalManager.expects(once()).method("findAllList").with(ANYTHING, ANYTHING).will(returnValue(areas));

        areaOrganizacinoalManager.expects(once()).method("montaFamilia").with(ANYTHING).will(returnValue(areas));

        areaOrganizacinoalManager.expects(atLeastOnce()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING);

        Collection<Colaborador> retorno = colaboradorManager.ordenaPorEstabelecimentoArea(empresa.getId(), colaboradors);

        assertEquals(colaboradors.size(), retorno.size());
    }

    public void testOrdenaPorEstabelecimentoAreaOrGrupoOcupacionalFiltro2() throws Exception
    {
        Empresa empresa = EmpresaFactory.getEmpresa(1L);

        AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
        areaOrganizacional.setEmpresa(empresa);

        Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
        areas.add(areaOrganizacional);

        Colaborador colaborador = ColaboradorFactory.getEntity(1L);
        colaborador.setAreaOrganizacional(areaOrganizacional);

        Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
        colaboradors.add(colaborador);

        areaOrganizacinoalManager.expects(once()).method("findAllList").with(ANYTHING, ANYTHING).will(returnValue(areas));

        areaOrganizacinoalManager.expects(once()).method("montaFamilia").with(ANYTHING).will(returnValue(areas));

        areaOrganizacinoalManager.expects(atLeastOnce()).method("getAreaOrganizacional").with(ANYTHING, ANYTHING);

        Collection<Colaborador> retorno = colaboradorManager.ordenaPorEstabelecimentoArea(empresa.getId(), colaboradors);

        assertEquals(colaboradors.size(), retorno.size());
    }

    public void testRespondeuEntrevista()
    {
        Colaborador colaborador = ColaboradorFactory.getEntity(1L);

        colaboradorDao.expects(once()).method("setRespondeuEntrevista").with(eq(colaborador.getId()));

        colaboradorManager.respondeuEntrevista(colaborador.getId());
    }
    
    public void testPreparaRelatorioDinamico()
    {
    	Colaborador chico = ColaboradorFactory.getEntity(1L, "chico barroso", "Barroso", "99999");
    	Colaborador bruno = ColaboradorFactory.getEntity(2L, "Bruno", "Bruno", "888888");
    	Colaborador samuel = ColaboradorFactory.getEntity(3L, "Samuel", "Pinheiro", "5555555");

    	Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
    	colaboradores.add(chico);
    	colaboradores.add(bruno);
    	colaboradores.add(samuel);
    	
    	Collection<String> colunasMarcadas = new ArrayList<String>();
    	colunasMarcadas = Arrays.asList("nome", "matricula", "nomeComercial");
    	
    	Collection<DynaRecord> relatorio = colaboradorManager.preparaRelatorioDinamico(colaboradores, colunasMarcadas);
    	
    	assertEquals(3, relatorio.size());
    	
    	assertEquals("chico barroso", ((DynaRecord)relatorio.toArray()[0]).getCampo1());
    	assertEquals("99999", ((DynaRecord)relatorio.toArray()[0]).getCampo2());
    	assertEquals("Barroso", ((DynaRecord)relatorio.toArray()[0]).getCampo3());

    	assertEquals("Samuel", ((DynaRecord)relatorio.toArray()[2]).getCampo1());
    	assertEquals("Pinheiro", ((DynaRecord)relatorio.toArray()[2]).getCampo3());
    }

    public void testFindAllSelect()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	colaboradorDao.expects(once()).method("findAllSelect").with(eq(empresa.getId()), eq("nomeComercial")).will(returnValue(new ArrayList<Colaborador>()));

    	assertNotNull(colaboradorManager.findAllSelect(empresa.getId(), "nomeComercial"));
    }

    public void testFindByNomeCpfMatricula()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	colaboradorDao.expects(once()).method("findByNomeCpfMatricula").with(ANYTHING, eq(empresa.getId()), ANYTHING).will(returnValue(new ArrayList<Colaborador>()));

    	assertNotNull(colaboradorManager.findByNomeCpfMatricula(null, empresa.getId(), false));
    }

    public void testMigrarBairro()
    {
    	colaboradorDao.expects(once()).method("migrarBairro").with(eq("bairro"), eq("bairroDestino")).isVoid();

    	colaboradorManager.migrarBairro("bairro", "bairroDestino");
    }

    public void testFindListComHistoricoFuturo()
	{
    	Map parametros = new HashMap();
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);

		colaboradorDao.expects(once()).method("findList").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colaboradors));

		Collection<Colaborador> retorno = colaboradorManager.findListComHistoricoFuturo(1, 10, parametros);

		assertEquals(colaboradors.size(), retorno.size());
	}

    public void testGetCountComHistoricoFuturo()
	{
    	Map parametros = new HashMap();

    	colaboradorDao.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(2));

    	Integer count = colaboradorManager.getCountComHistoricoFuturo(parametros);

    	assertTrue(count.equals(2));
	}

    public void testGetNome()
    {
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	colaborador.setNome("Joao");

    	colaboradorDao.expects(once()).method("findColaboradorByIdProjection").with(eq(colaborador.getId())).will(returnValue(colaborador));

    	assertEquals("Joao", colaboradorManager.getNome(colaborador.getId()));
    }

    public void testGetNomeException()
    {
    	colaboradorDao.expects(once()).method("findColaboradorByIdProjection").with(ANYTHING).will(returnValue(null));

    	assertEquals("", colaboradorManager.getNome(null));
    }

    public void testUpdateEmpregado() throws Exception
    {
    	TEmpregado empregado = new TEmpregado();
    	empregado.setId(1);
    	empregado.setCidadeCodigoAC("1234");
    	empregado.setUfSigla("UF");
    	empregado.setSexo("M");
    	empregado.setDeficiencia("1");
    	empregado.setCtpsDV("");
    	empregado.setIdentidadeUF("UF");
    	empregado.setCtpsUFSigla("UF");
    	empregado.setCtpsDV("1");
    	empregado.setEscolaridade("Superior Completo");

    	Pessoal pessoal = new Pessoal();
    	pessoal.setEscolaridade("Especialização");
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	Estado estado = EstadoFactory.getEntity(1L);
    	Cidade cidade = CidadeFactory.getEntity();
    	cidade.setId(1L);
    	cidade.setUf(estado);
    	colaborador.setPessoal(pessoal);

    	cidadeManager.expects(once()).method("findByCodigoAC").with(ANYTHING, ANYTHING).will(returnValue(cidade));
    	estadoManager.expects(once()).method("findBySigla").with(ANYTHING).will(returnValue(estado));
    	estadoManager.expects(once()).method("findBySigla").with(ANYTHING).will(returnValue(estado));
    	colaboradorDao.expects(once()).method("findByIdComHistorico").with(ANYTHING,eq(null)).will(returnValue(colaborador));
    	colaboradorDao.expects(once()).method("update").with(eq(colaborador));

    	assertEquals(colaborador, colaboradorManager.updateEmpregado(empregado));
    }

    public void testGetCountAtivosByEstabelecimento()
    {
    	colaboradorDao.expects(once()).method("getCountAtivosByEstabelecimento").with(ANYTHING).will(returnValue(1));
    	assertEquals((Integer)1,colaboradorManager.getCountAtivosEstabelecimento(1L));
    }
    
    public void testFindByAreaOrganizacional()
    {
    	Collection<AreaOrganizacional> areas = Arrays.asList(new AreaOrganizacional[]{AreaOrganizacionalFactory.getEntity(1L)});
    	colaboradorDao.expects(once()).method("findByAreaOrganizacional").with(ANYTHING).will(returnValue(areas));
    	assertNotNull(colaboradorManager.findByAreaOrganizacional(new ArrayList<Long>()));
    }
    
    public void testFindEmailsDeColaboradoresByPerfis()
    {
    	Collection<Perfil> perfis = new ArrayList<Perfil>();
    	Perfil perfil = new Perfil();
    	perfil.setId(10L);
		perfis.add(perfil);
		Long empresaId=1L;
		
		colaboradorDao.expects(once()).method("findEmailsDeColaboradoresByPerfis").with(ANYTHING,ANYTHING).will(returnValue(new ArrayList<String>()));
		
		assertEquals(0, colaboradorManager.findEmailsDeColaboradoresByPerfis(perfis, empresaId).size());
    }
    public void testFindEmailsDeColaboradoresByPerfisVazio()
    {
    	Collection<Perfil> perfis = null;
    	Long empresaId=1L;
    	assertEquals(0, colaboradorManager.findEmailsDeColaboradoresByPerfis(perfis, empresaId).size());
    }
    
    public void testFindAdmitidosHaDias() throws Exception
    {
    	Integer dias=45;
		Empresa empresa= EmpresaFactory.getEmpresa(1L);
		Collection<AreaOrganizacional> areas = Arrays.asList(AreaOrganizacionalFactory.getEntity(1L));
		
		colaboradorDao.expects(once()).method("findAdmitidosHaDias").with(eq(dias),eq(empresa)).will(returnValue(new ArrayList<Colaborador>()));
		areaOrganizacinoalManager.expects(once()).method("findByEmpresasIds").with(ANYTHING, ANYTHING).will(returnValue(areas));
        areaOrganizacinoalManager.expects(once()).method("montaFamilia").with(ANYTHING).will(returnValue(areas));
		assertEquals(0, colaboradorManager.findAdmitidosHaDias(dias, empresa).size());
    }
    
    public void testFindAdmitidos() throws Exception
    {
    	Collection<AreaOrganizacional> areas = Arrays.asList(AreaOrganizacionalFactory.getEntity(1L));
    	Collection<Colaborador> admitidos = Arrays.asList(ColaboradorFactory.getEntity(1L));
    	
    	colaboradorDao.expects(once()).method("findAdmitidos").will(returnValue(admitidos));
    	areaOrganizacinoalManager.expects(once()).method("findByEmpresasIds").with(ANYTHING, ANYTHING).will(returnValue(areas));
        areaOrganizacinoalManager.expects(once()).method("montaFamilia").with(ANYTHING).will(returnValue(areas));
    	Long[] areasIds=new Long[]{1L};
		Long[] estabelecimentosIds=new Long[]{1L,2L};
		
		assertEquals(1, colaboradorManager.findAdmitidos(new Long[]{1L}, new Date(), new Date(), areasIds, estabelecimentosIds, false).size());
    }
    
    public void testFindAdmitidosException() 
    {
    	colaboradorDao.expects(once()).method("findAdmitidos").will(returnValue(new ArrayList<Colaborador>()));
    	
    	Long[] areasIds=new Long[]{1L};
    	Long[] estabelecimentosIds=new Long[]{1L,2L};
    	
    	Exception exception =null;
    	
    	try {
			colaboradorManager.findAdmitidos(new Long[]{1L}, new Date(), new Date(), areasIds, estabelecimentosIds, false);
		} catch (Exception e) {
			exception = e;
		}
		
		assertEquals("Não existem dados para o filtro informado.",exception.getMessage());
    }
    
    public void testVerificaColaboradorLogadoVerAreas() 
    {
    	assertEquals(new Long(1L), colaboradorManager.verificaColaboradorLogadoVerAreas());
    }
    
    public void testFindParticipantesByAvaliacaoDesempenho()
    {
    	colaboradorDao.expects(once()).method("findParticipantesDistinctByAvaliacaoDesempenho").with(eq(1L), eq(true), eq(null)).will(returnValue(new ArrayList<Colaborador>()));
    	assertNotNull(colaboradorManager.findParticipantesDistinctByAvaliacaoDesempenho(1L, true, null));
    }
    
  //TODO remprot
//    public void testValidaQtdCadastros()
//    {
//    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
//    	parametrosDoSistemaManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(parametrosDoSistema));
//    	colaboradorDao.expects(once()).method("getCount").will(returnValue(10));
//    	
//    	Exception exception = null;
//    	
//    	try
//		{
//			colaboradorManager.validaQtdCadastros();
//		} catch (Exception e)
//		{
//			exception = e;
//		}
//		
//		assertNotNull(exception);
//    }
}
