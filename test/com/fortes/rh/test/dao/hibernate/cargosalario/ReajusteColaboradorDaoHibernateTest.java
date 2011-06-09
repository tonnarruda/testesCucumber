package com.fortes.rh.test.dao.hibernate.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.GrupoOcupacionalDao;
import com.fortes.rh.dao.cargosalario.IndiceDao;
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
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.AmbienteFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.GrupoOcupacionalFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.ReajusteColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.DateUtil;

public class ReajusteColaboradorDaoHibernateTest extends GenericDaoHibernateTest<ReajusteColaborador>
{
    private ReajusteColaboradorDao reajusteColaboradorDao;
    private EmpresaDao empresaDao;
    private ColaboradorDao colaboradorDao;
    private CargoDao cargoDao;
    private FaixaSalarialDao faixaSalarialDao;
    private GrupoOcupacionalDao grupoOcupacionalDao;
    private AreaOrganizacionalDao areaOrganizacionalDao;
    private TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao;
    private EstabelecimentoDao estabelecimentoDao;
    private IndiceDao indiceDao;
    private FuncaoDao funcaoDao;
    private AmbienteDao ambienteDao;

    public ReajusteColaborador getEntity()
    {
        ReajusteColaborador reajusteColaborador = new ReajusteColaborador();

        reajusteColaborador.setId(null);
        reajusteColaborador.setFaixaSalarialAtual(null);
        reajusteColaborador.setFaixaSalarialProposta(null);
        reajusteColaborador.setSalarioAtual(0D);
        reajusteColaborador.setSalarioProposto(0D);
        reajusteColaborador.setTabelaReajusteColaborador(null);

        return reajusteColaborador;
    }

    public void testFindByIdEstabelecimentoAreaGrupo()
    {
        Colaborador colaborador = ColaboradorFactory.getEntity();
        colaboradorDao.save(colaborador);
        
        Colaborador colaboradorDesligadoAntes = ColaboradorFactory.getEntity();
        colaboradorDesligadoAntes.setDesligado(true);
        colaboradorDesligadoAntes.setDataDesligamento(DateUtil.criarDataMesAno(10, 6, 2009));
        colaboradorDao.save(colaboradorDesligadoAntes);
        
        Colaborador colaboradorDesligadoDepois = ColaboradorFactory.getEntity();
        colaboradorDesligadoDepois.setDesligado(true);
        colaboradorDesligadoDepois.setDataDesligamento(DateUtil.criarDataMesAno(25, 6, 2009));
        colaboradorDao.save(colaboradorDesligadoDepois);

        Cargo cargoAtual = CargoFactory.getEntity();
        cargoDao.save(cargoAtual);

        FaixaSalarial faixaSalarialAtual = FaixaSalarialFactory.getEntity();
        faixaSalarialAtual.setCargo(cargoAtual);
        faixaSalarialDao.save(faixaSalarialAtual);

        GrupoOcupacional grupoOcupacionalProposto = GrupoOcupacionalFactory.getGrupoOcupacional();
        grupoOcupacionalDao.save(grupoOcupacionalProposto);

        Collection<Long> grupoIds = new ArrayList<Long>();
        grupoIds.add(grupoOcupacionalProposto.getId());

        Cargo cargoProposto = CargoFactory.getEntity();
        cargoProposto.setGrupoOcupacional(grupoOcupacionalProposto);
        cargoDao.save(cargoProposto);

        FaixaSalarial faixaSalarialProposto = FaixaSalarialFactory.getEntity();
        faixaSalarialProposto.setCargo(cargoProposto);
        faixaSalarialDao.save(faixaSalarialProposto);

        AreaOrganizacional areaOrganizacionalProposto = AreaOrganizacionalFactory.getEntity();
        areaOrganizacionalProposto.setAreasInteresse(null);
        areaOrganizacionalDao.save(areaOrganizacionalProposto);

        TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
        tabelaReajusteColaborador.setData(DateUtil.criarDataMesAno(20, 6, 2009));
        tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);

        Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity();
        estabelecimentoAtual.setNome("estabelecimento atual");
        estabelecimentoDao.save(estabelecimentoAtual);

        Estabelecimento estabelecimentoProposto = EstabelecimentoFactory.getEntity();
        estabelecimentoProposto.setNome("estabelecimento proposto");
        estabelecimentoDao.save(estabelecimentoProposto);

        ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador();
        reajusteColaborador.setAreaOrganizacionalProposta(areaOrganizacionalProposto);
        reajusteColaborador.setColaborador(colaborador);
        reajusteColaborador.setFaixaSalarialAtual(faixaSalarialAtual);
        reajusteColaborador.setFaixaSalarialProposta(faixaSalarialProposto);
        reajusteColaborador.setTabelaReajusteColaborador(tabelaReajusteColaborador);
        reajusteColaborador.setEstabelecimentoAtual(estabelecimentoAtual);
        reajusteColaborador.setEstabelecimentoProposto(estabelecimentoProposto);
        reajusteColaboradorDao.save(reajusteColaborador);

        ReajusteColaborador reajusteColaboradorDesligadoAntes = ReajusteColaboradorFactory.getReajusteColaborador();
        reajusteColaboradorDesligadoAntes.setAreaOrganizacionalProposta(areaOrganizacionalProposto);
        reajusteColaboradorDesligadoAntes.setColaborador(colaboradorDesligadoAntes);
        reajusteColaboradorDesligadoAntes.setFaixaSalarialAtual(faixaSalarialAtual);
        reajusteColaboradorDesligadoAntes.setFaixaSalarialProposta(faixaSalarialProposto);
        reajusteColaboradorDesligadoAntes.setTabelaReajusteColaborador(tabelaReajusteColaborador);
        reajusteColaboradorDesligadoAntes.setEstabelecimentoAtual(estabelecimentoAtual);
        reajusteColaboradorDesligadoAntes.setEstabelecimentoProposto(estabelecimentoProposto);
        reajusteColaboradorDao.save(reajusteColaboradorDesligadoAntes);
        
        ReajusteColaborador reajusteColaboradorDesligadoDepois = ReajusteColaboradorFactory.getReajusteColaborador();
        reajusteColaboradorDesligadoDepois.setAreaOrganizacionalProposta(areaOrganizacionalProposto);
        reajusteColaboradorDesligadoDepois.setColaborador(colaboradorDesligadoDepois);
        reajusteColaboradorDesligadoDepois.setFaixaSalarialAtual(faixaSalarialAtual);
        reajusteColaboradorDesligadoDepois.setFaixaSalarialProposta(faixaSalarialProposto);
        reajusteColaboradorDesligadoDepois.setTabelaReajusteColaborador(tabelaReajusteColaborador);
        reajusteColaboradorDesligadoDepois.setEstabelecimentoAtual(estabelecimentoAtual);
        reajusteColaboradorDesligadoDepois.setEstabelecimentoProposto(estabelecimentoProposto);
        reajusteColaboradorDao.save(reajusteColaboradorDesligadoDepois);
        
        Collection<Long> estabelecimentosIds = new ArrayList<Long>();
        estabelecimentosIds.add(estabelecimentoProposto.getId());
        
        Collection<Long> areasIds = new ArrayList<Long>();
        areasIds.add(areaOrganizacionalProposto.getId());
        
        Collection<ReajusteColaborador> reajusteColaboradors = reajusteColaboradorDao.findByIdEstabelecimentoAreaGrupo(tabelaReajusteColaborador.getId(), estabelecimentosIds, areasIds, grupoIds, 2);
        assertEquals(2, reajusteColaboradors.size());

        Collection<ReajusteColaborador> reajusteColaboradorsAreas = reajusteColaboradorDao.findByIdEstabelecimentoAreaGrupo(tabelaReajusteColaborador.getId(), estabelecimentosIds, areasIds, grupoIds, 1);
        assertEquals(2, reajusteColaboradorsAreas.size());
    }

    public void testDeleteByColaboradoresTabelaReajuste()
    {
        TabelaReajusteColaborador tabelaReajusteColaborador1 = TabelaReajusteColaboradorFactory.getEntity();
        tabelaReajusteColaborador1 = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador1);
        TabelaReajusteColaborador tabelaReajusteColaborador2 = TabelaReajusteColaboradorFactory.getEntity();
        tabelaReajusteColaborador2 = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador2);

        Colaborador colaborador1 = ColaboradorFactory.getEntity();
        colaborador1 = colaboradorDao.save(colaborador1);
        Colaborador colaborador2 = ColaboradorFactory.getEntity();
        colaborador2 = colaboradorDao.save(colaborador2);

        ReajusteColaborador reajusteColaborador1 = ReajusteColaboradorFactory.getReajusteColaborador();
        reajusteColaborador1.setTabelaReajusteColaborador(tabelaReajusteColaborador1);
        reajusteColaborador1.setColaborador(colaborador1);
        reajusteColaborador1 = reajusteColaboradorDao.save(reajusteColaborador1);

        ReajusteColaborador reajusteColaborador2 = ReajusteColaboradorFactory.getReajusteColaborador();
        reajusteColaborador2.setTabelaReajusteColaborador(tabelaReajusteColaborador2);
        reajusteColaborador2.setColaborador(colaborador2);
        reajusteColaborador2 = reajusteColaboradorDao.save(reajusteColaborador2);

        reajusteColaboradorDao.deleteByColaboradoresTabelaReajuste(new Long[]{colaborador1.getId(), colaborador2.getId()}, tabelaReajusteColaborador1.getId());
    }

    public void testDeleteByTabelaReajuste()
    {
        TabelaReajusteColaborador tabelaReajusteColaborador1 = TabelaReajusteColaboradorFactory.getEntity();
        tabelaReajusteColaborador1 = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador1);
        TabelaReajusteColaborador tabelaReajusteColaborador2 = TabelaReajusteColaboradorFactory.getEntity();
        tabelaReajusteColaborador2 = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador2);

        ReajusteColaborador reajusteColaborador1 = ReajusteColaboradorFactory.getReajusteColaborador();
        reajusteColaborador1.setTabelaReajusteColaborador(tabelaReajusteColaborador1);
        reajusteColaborador1 = reajusteColaboradorDao.save(reajusteColaborador1);

        ReajusteColaborador reajusteColaborador2 = ReajusteColaboradorFactory.getReajusteColaborador();
        reajusteColaborador2.setTabelaReajusteColaborador(tabelaReajusteColaborador2);
        reajusteColaborador2 = reajusteColaboradorDao.save(reajusteColaborador2);

        reajusteColaboradorDao.deleteByColaboradoresTabelaReajuste(null, tabelaReajusteColaborador1.getId());
    }

    public void testUpdateFromHistoricoColaborador()
    {
        HistoricoColaborador historicoColaborador = new HistoricoColaborador();

        Colaborador colaborador = ColaboradorFactory.getEntity();
        colaborador = colaboradorDao.save(colaborador);

        GrupoOcupacional grupoOcupacionalProposto = GrupoOcupacionalFactory.getGrupoOcupacional();
        grupoOcupacionalProposto = grupoOcupacionalDao.save(grupoOcupacionalProposto);

        Collection<Long> grupoIds = new ArrayList<Long>();
        grupoIds.add(grupoOcupacionalProposto.getId());

        Cargo cargoProposto = CargoFactory.getEntity();
        cargoProposto.setGrupoOcupacional(grupoOcupacionalProposto);
        cargoProposto = cargoDao.save(cargoProposto);

        FaixaSalarial faixaSalarialProposto = FaixaSalarialFactory.getEntity();
        faixaSalarialProposto.setCargo(cargoProposto);
        faixaSalarialProposto = faixaSalarialDao.save(faixaSalarialProposto);
        historicoColaborador.setFaixaSalarial(faixaSalarialProposto);

        AreaOrganizacional areaOrganizacionalProposto = AreaOrganizacionalFactory.getEntity();
        areaOrganizacionalProposto.setAreasInteresse(null);
        areaOrganizacionalProposto = areaOrganizacionalDao.save(areaOrganizacionalProposto);
        historicoColaborador.setAreaOrganizacional(areaOrganizacionalProposto);

        TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
        tabelaReajusteColaborador = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);

        Estabelecimento estabelecimentoProposto = EstabelecimentoFactory.getEntity();
        estabelecimentoProposto.setNome("estabelecimento proposto");
        estabelecimentoProposto = estabelecimentoDao.save(estabelecimentoProposto);
        historicoColaborador.setEstabelecimento(estabelecimentoProposto);

        ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador();
        reajusteColaborador.setAreaOrganizacionalProposta(areaOrganizacionalProposto);
        reajusteColaborador.setColaborador(colaborador);
        reajusteColaborador.setFaixaSalarialProposta(faixaSalarialProposto);
        reajusteColaborador.setTabelaReajusteColaborador(tabelaReajusteColaborador);
        reajusteColaborador.setEstabelecimentoProposto(estabelecimentoProposto);
        reajusteColaborador = reajusteColaboradorDao.save(reajusteColaborador);

        historicoColaborador.setTipoSalario(TipoAplicacaoIndice.VALOR);
        historicoColaborador.setReajusteColaborador(reajusteColaborador);
        historicoColaborador.setSalario(0.0);

        reajusteColaboradorDao.updateFromHistoricoColaborador(historicoColaborador);

        ReajusteColaborador reajusteColaboradorRetorno = reajusteColaboradorDao.findByIdProjection(reajusteColaborador.getId());
        assertEquals(reajusteColaborador.getId(), reajusteColaboradorRetorno.getId());
        assertEquals(historicoColaborador.getFaixaSalarial(), reajusteColaboradorRetorno.getFaixaSalarialProposta());
        assertEquals(historicoColaborador.getAreaOrganizacional(), reajusteColaboradorRetorno.getAreaOrganizacionalProposta());

        //testUpdateFromHistoricoColaborador quando colaborador Null
        reajusteColaborador.setColaborador(null);
        reajusteColaborador.setFaixaSalarialProposta(null);
        reajusteColaborador.setAreaOrganizacionalProposta(null);
        reajusteColaboradorDao.updateFromHistoricoColaborador(historicoColaborador);
        assertEquals(reajusteColaborador.getId(), reajusteColaboradorRetorno.getId());
        
        //teste do switch
        historicoColaborador.setTipoSalario(TipoAplicacaoIndice.CARGO);
        reajusteColaboradorDao.updateFromHistoricoColaborador(historicoColaborador);
        assertNull(reajusteColaborador.getSalarioProposto());
    }

    public void testUpdateFromHistoricoColaboradorIndice()
    {
        HistoricoColaborador historicoColaborador = new HistoricoColaborador();

        Colaborador colaborador = ColaboradorFactory.getEntity();
        colaborador = colaboradorDao.save(colaborador);

        GrupoOcupacional grupoOcupacionalProposto = GrupoOcupacionalFactory.getGrupoOcupacional();
        grupoOcupacionalProposto = grupoOcupacionalDao.save(grupoOcupacionalProposto);

        Collection<Long> grupoIds = new ArrayList<Long>();
        grupoIds.add(grupoOcupacionalProposto.getId());

        Cargo cargoProposto = CargoFactory.getEntity();
        cargoProposto.setGrupoOcupacional(grupoOcupacionalProposto);
        cargoProposto = cargoDao.save(cargoProposto);

        FaixaSalarial faixaSalarialProposto = FaixaSalarialFactory.getEntity();
        faixaSalarialProposto.setCargo(cargoProposto);
        faixaSalarialProposto = faixaSalarialDao.save(faixaSalarialProposto);
        historicoColaborador.setFaixaSalarial(faixaSalarialProposto);

        AreaOrganizacional areaOrganizacionalProposto = AreaOrganizacionalFactory.getEntity();
        areaOrganizacionalProposto.setAreasInteresse(null);
        areaOrganizacionalProposto = areaOrganizacionalDao.save(areaOrganizacionalProposto);
        historicoColaborador.setAreaOrganizacional(areaOrganizacionalProposto);

        TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
        tabelaReajusteColaborador = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);

        Estabelecimento estabelecimentoProposto = EstabelecimentoFactory.getEntity();
        estabelecimentoProposto.setNome("estabelecimento proposto");
        estabelecimentoProposto = estabelecimentoDao.save(estabelecimentoProposto);
        historicoColaborador.setEstabelecimento(estabelecimentoProposto);

        Funcao funcao = FuncaoFactory.getEntity();
        funcao = funcaoDao.save(funcao);
        
        Ambiente ambiente = AmbienteFactory.getEntity();
        ambiente = ambienteDao.save(ambiente);
        
        historicoColaborador.setAmbiente(ambiente);
        historicoColaborador.setFuncao(funcao);

        ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador();
        reajusteColaborador.setAreaOrganizacionalProposta(areaOrganizacionalProposto);
        reajusteColaborador.setColaborador(colaborador);
        reajusteColaborador.setFaixaSalarialProposta(faixaSalarialProposto);
        reajusteColaborador.setTabelaReajusteColaborador(tabelaReajusteColaborador);
        reajusteColaborador.setEstabelecimentoProposto(estabelecimentoProposto);
        reajusteColaborador = reajusteColaboradorDao.save(reajusteColaborador);

        historicoColaborador.setReajusteColaborador(reajusteColaborador);

        Indice indice = IndiceFactory.getEntity();
        indice = indiceDao.save(indice);

        historicoColaborador.setIndice(indice);
        historicoColaborador.setQuantidadeIndice(2.0);
        historicoColaborador.setTipoSalario(TipoAplicacaoIndice.INDICE);

        reajusteColaboradorDao.updateFromHistoricoColaborador(historicoColaborador);
        assertNull(reajusteColaborador.getSalarioProposto());
    }

    public void testFindByIdProjection()
    {
        Empresa empresa = EmpresaFactory.getEmpresa();
        empresa = empresaDao.save(empresa);

        Colaborador colaborador = ColaboradorFactory.getEntity();
        colaborador = colaboradorDao.save(colaborador);

        Cargo cargoAtual = CargoFactory.getEntity();
        cargoAtual = cargoDao.save(cargoAtual);

        FaixaSalarial faixaSalarialAtual = FaixaSalarialFactory.getEntity();
        faixaSalarialAtual.setCargo(cargoAtual);
        faixaSalarialAtual = faixaSalarialDao.save(faixaSalarialAtual);

        GrupoOcupacional grupoOcupacionalProposto = GrupoOcupacionalFactory.getGrupoOcupacional();
        grupoOcupacionalProposto = grupoOcupacionalDao.save(grupoOcupacionalProposto);

        Collection<Long> grupoIds = new ArrayList<Long>();
        grupoIds.add(grupoOcupacionalProposto.getId());

        Cargo cargoProposto = CargoFactory.getEntity();
        cargoProposto.setGrupoOcupacional(grupoOcupacionalProposto);
        cargoProposto = cargoDao.save(cargoProposto);

        FaixaSalarial faixaSalarialProposto = FaixaSalarialFactory.getEntity();
        faixaSalarialProposto.setCargo(cargoProposto);
        faixaSalarialProposto = faixaSalarialDao.save(faixaSalarialProposto);

        AreaOrganizacional areaOrganizacionalProposto = AreaOrganizacionalFactory.getEntity();
        areaOrganizacionalProposto.setAreasInteresse(null);
        areaOrganizacionalProposto = areaOrganizacionalDao.save(areaOrganizacionalProposto);

        Collection<Long> areaIds = new ArrayList<Long>();
        areaIds.add(areaOrganizacionalProposto.getId());

        TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
        tabelaReajusteColaborador.setEmpresa(empresa);
        tabelaReajusteColaborador = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);

        Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity();
        estabelecimentoAtual.setNome("estabelecimento atual");
        estabelecimentoAtual = estabelecimentoDao.save(estabelecimentoAtual);

        Estabelecimento estabelecimentoProposto = EstabelecimentoFactory.getEntity();
        estabelecimentoProposto.setNome("estabelecimento proposto");
        estabelecimentoProposto = estabelecimentoDao.save(estabelecimentoProposto);

        Collection<Long> estabelecimentoIds = new ArrayList<Long>();
        estabelecimentoIds.add(estabelecimentoProposto.getId());

        ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador();
        reajusteColaborador.setAreaOrganizacionalProposta(areaOrganizacionalProposto);
        reajusteColaborador.setColaborador(colaborador);
        reajusteColaborador.setFaixaSalarialAtual(faixaSalarialAtual);
        reajusteColaborador.setFaixaSalarialProposta(faixaSalarialProposto);
        reajusteColaborador.setTabelaReajusteColaborador(tabelaReajusteColaborador);
        reajusteColaborador.setEstabelecimentoAtual(estabelecimentoAtual);
        reajusteColaborador.setEstabelecimentoProposto(estabelecimentoProposto);
        reajusteColaborador = reajusteColaboradorDao.save(reajusteColaborador);

        ReajusteColaborador retorno = reajusteColaboradorDao.findByIdProjection(reajusteColaborador.getId());

        assertEquals(reajusteColaborador.getId(), retorno.getId());
    }

    public void testGetSituacaoReajusteColaborador()
    {
        Empresa empresa = EmpresaFactory.getEmpresa();
        empresa = empresaDao.save(empresa);

        Colaborador colaborador = ColaboradorFactory.getEntity();
        colaborador = colaboradorDao.save(colaborador);

        Cargo cargoAtual = CargoFactory.getEntity();
        cargoAtual = cargoDao.save(cargoAtual);

        FaixaSalarial faixaSalarialAtual = FaixaSalarialFactory.getEntity();
        faixaSalarialAtual.setCargo(cargoAtual);
        faixaSalarialAtual = faixaSalarialDao.save(faixaSalarialAtual);

        GrupoOcupacional grupoOcupacionalProposto = GrupoOcupacionalFactory.getGrupoOcupacional();
        grupoOcupacionalProposto = grupoOcupacionalDao.save(grupoOcupacionalProposto);

        Collection<Long> grupoIds = new ArrayList<Long>();
        grupoIds.add(grupoOcupacionalProposto.getId());

        Cargo cargoProposto = CargoFactory.getEntity();
        cargoProposto.setGrupoOcupacional(grupoOcupacionalProposto);
        cargoProposto = cargoDao.save(cargoProposto);

        FaixaSalarial faixaSalarialProposto = FaixaSalarialFactory.getEntity();
        faixaSalarialProposto.setCargo(cargoProposto);
        faixaSalarialProposto = faixaSalarialDao.save(faixaSalarialProposto);

        AreaOrganizacional areaOrganizacionalProposto = AreaOrganizacionalFactory.getEntity();
        areaOrganizacionalProposto.setAreasInteresse(null);
        areaOrganizacionalProposto = areaOrganizacionalDao.save(areaOrganizacionalProposto);

        Collection<Long> areaIds = new ArrayList<Long>();
        areaIds.add(areaOrganizacionalProposto.getId());

        TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
        tabelaReajusteColaborador.setEmpresa(empresa);
        tabelaReajusteColaborador = tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);

        Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity();
        estabelecimentoAtual.setNome("estabelecimento atual");
        estabelecimentoAtual = estabelecimentoDao.save(estabelecimentoAtual);

        Estabelecimento estabelecimentoProposto = EstabelecimentoFactory.getEntity();
        estabelecimentoProposto.setNome("estabelecimento proposto");
        estabelecimentoProposto = estabelecimentoDao.save(estabelecimentoProposto);

        Collection<Long> estabelecimentoIds = new ArrayList<Long>();
        estabelecimentoIds.add(estabelecimentoProposto.getId());

        ReajusteColaborador reajusteColaborador = ReajusteColaboradorFactory.getReajusteColaborador();
        reajusteColaborador.setAreaOrganizacionalProposta(areaOrganizacionalProposto);
        reajusteColaborador.setColaborador(colaborador);
        reajusteColaborador.setFaixaSalarialAtual(faixaSalarialAtual);
        reajusteColaborador.setFaixaSalarialProposta(faixaSalarialProposto);
        reajusteColaborador.setTabelaReajusteColaborador(tabelaReajusteColaborador);
        reajusteColaborador.setEstabelecimentoAtual(estabelecimentoAtual);
        reajusteColaborador.setEstabelecimentoProposto(estabelecimentoProposto);
        reajusteColaborador = reajusteColaboradorDao.save(reajusteColaborador);

        ReajusteColaborador retorno = reajusteColaboradorDao.getSituacaoReajusteColaborador(reajusteColaborador.getId());

        assertEquals(reajusteColaborador.getId(), retorno.getId());
    }

    public GenericDao<ReajusteColaborador> getGenericDao()
    {
        return reajusteColaboradorDao;
    }

    public void setEmpresaDao(EmpresaDao empresaDao)
    {
        this.empresaDao = empresaDao;
    }

    public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
    {
        this.areaOrganizacionalDao = areaOrganizacionalDao;
    }

    public void setCargoDao(CargoDao cargoDao)
    {
        this.cargoDao = cargoDao;
    }

    public void setColaboradorDao(ColaboradorDao colaboradorDao)
    {
        this.colaboradorDao = colaboradorDao;
    }

    public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao)
    {
        this.faixaSalarialDao = faixaSalarialDao;
    }

    public void setGrupoOcupacionalDao(GrupoOcupacionalDao grupoOcupacionalDao)
    {
        this.grupoOcupacionalDao = grupoOcupacionalDao;
    }

    public void setTabelaReajusteColaboradorDao(TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao)
    {
        this.tabelaReajusteColaboradorDao = tabelaReajusteColaboradorDao;
    }

    public void setReajusteColaboradorDao(ReajusteColaboradorDao reajusteColaboradorDao)
    {
        this.reajusteColaboradorDao = reajusteColaboradorDao;
    }

    public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao)
    {
        this.estabelecimentoDao = estabelecimentoDao;
    }

    public void setIndiceDao(IndiceDao indiceDao)
    {
        this.indiceDao = indiceDao;
    }

	public void setAmbienteDao(AmbienteDao ambienteDao)
	{
		this.ambienteDao = ambienteDao;
	}

	public void setFuncaoDao(FuncaoDao funcaoDao)
	{
		this.funcaoDao = funcaoDao;
	}

}