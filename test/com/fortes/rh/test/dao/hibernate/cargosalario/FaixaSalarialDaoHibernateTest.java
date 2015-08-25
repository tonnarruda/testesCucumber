package com.fortes.rh.test.dao.hibernate.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaDao;
import com.fortes.rh.dao.captacao.HabilidadeDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialHistoricoDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.cargosalario.IndiceDao;
import com.fortes.rh.dao.cargosalario.IndiceHistoricoDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.ws.TCargo;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceHistoricoFactory;
import com.fortes.rh.util.DateUtil;

public class FaixaSalarialDaoHibernateTest extends GenericDaoHibernateTest<FaixaSalarial>
{
    private FaixaSalarialDao faixaSalarialDao;
    private FaixaSalarialHistoricoDao faixaSalarialHistoricoDao;
    private IndiceDao indiceDao;
    private IndiceHistoricoDao indiceHistoricoDao;
    private CargoDao cargoDao;
    private EmpresaDao empresaDao;
    private EstabelecimentoDao estabelecimentoDao;
    private GrupoACDao grupoACDao;
    private HabilidadeDao habilidadeDao;
    private NivelCompetenciaDao nivelCompetenciaDao;
    private ConfiguracaoNivelCompetenciaDao configuracaoNivelCompetenciaDao;
    private ColaboradorDao colaboradorDao;
    private HistoricoColaboradorDao historicoColaboradorDao;
    private AreaOrganizacionalDao areaOrganizacionalDao;

    public FaixaSalarial getEntity()
    {
        FaixaSalarial faixaSalarial = new FaixaSalarial();

        faixaSalarial.setCargo(null);
        faixaSalarial.setCodigoAC("0");
        faixaSalarial.setId(null);
        faixaSalarial.setNome("faixa");

        return faixaSalarial;
    }

    public void testFindAll(){
        try
        {
            faixaSalarialDao.findAll();
            fail("A Faixa Salarial não pode ser listada sem algum filtro que permita identificar a empresa.");
        }
        catch (NoSuchMethodError e)
        {
            assertTrue(true);
        }
        catch (Exception e) {
            fail("A Faixa Salarial não pode ser listada sem algum filtro que permita identificar a empresa.");
        }
    }

    public void testFindAllComOrdenacao(){
        try
        {
            faixaSalarialDao.findAll(new String[]{"Não","pode", "funcionar"});
            fail("A Faixa Salarial não pode ser listada sem algum filtro que permita identificar a empresa.");
        }
        catch (NoSuchMethodError e)
        {
            assertTrue(true);
        }
        catch (Exception e) {
            fail("A Faixa Salarial não pode ser listada sem algum filtro que permita identificar a empresa.");
        }
    }

    public void testFindCodigoACById()
    {
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
    	faixaSalarial = faixaSalarialDao.save(faixaSalarial);

    	FaixaSalarial retorno = faixaSalarialDao.findCodigoACById(faixaSalarial.getId());

    	assertEquals(faixaSalarial.getId(), retorno.getId());
    }
    
    public void testGetFaixasAC()
    {
    	GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setCodigoAC("99999299");
    	empresa.setGrupoAC(grupoAC.getCodigo());
    	empresaDao.save(empresa);
    	
    	Cargo cargo = CargoFactory.getEntity();
    	cargo.setEmpresa(empresa);
    	cargoDao.save(cargo);
    	
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
    	faixaSalarial.setCargo(cargo);
    	faixaSalarial.setNome("TESTE_AC");
    	faixaSalarial.setCodigoAC("99999299");
    	faixaSalarialDao.save(faixaSalarial);
    	
    	TCargo[] tCargos = faixaSalarialDao.getFaixasAC();
    	
    	assertTrue(tCargos.length > 0);
    }
    
    public void testUpdateAC()
    {
    	Cargo cargo = CargoFactory.getEntity();
    	cargoDao.save(cargo);
    	
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
    	faixaSalarial.setNome("teste");
    	faixaSalarial.setNomeACPessoal("teste ac");
    	faixaSalarial.setCargo(cargo);
    	faixaSalarialDao.save(faixaSalarial);
    	
    	TCargo tCargo = new TCargo();
    	tCargo.setId(faixaSalarial.getId());
    	tCargo.setDescricao("abc");
    	tCargo.setDescricaoACPessoal("abc AC");
    	
    	faixaSalarialDao.updateAC(tCargo);
    	FaixaSalarial retorno = faixaSalarialDao.findByFaixaSalarialId(faixaSalarial.getId());
    	
    	assertEquals("abc", retorno.getNome());
    	assertEquals("abc AC", retorno.getNomeACPessoal());
    }

	public void testFindFaixaSalarialByCodigoAc()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("432423223");
		empresa.setGrupoAC(grupoAC.getCodigo());
		empresa = empresaDao.save(empresa);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setEmpresa(empresa);
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCodigoAC("010203");
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		FaixaSalarial faixaSalarialRetorno = faixaSalarialDao.findFaixaSalarialByCodigoAc(faixaSalarial.getCodigoAC(), empresa.getCodigoAC(), "XXX");

		assertEquals(faixaSalarial, faixaSalarialRetorno);
	}
	
	public void testFindByCargo()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setEmpresa(empresa);
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial1);
		
		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarial2.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial2);
		
		assertEquals(2, faixaSalarialDao.findByCargo(cargo.getId()).size());
	}
	
	
	public void testFindByCargoComCompetencia()
	{
		Habilidade habilidade = HabilidadeFactory.getEntity();
		habilidade.setNome("Habilidade 1");
		habilidadeDao.save(habilidade);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("Cargo 1");
		cargoDao.save(cargo);
		
		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setNome("I");
		faixaSalarial1.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial1);
		
		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarial2.setNome("II");
		faixaSalarial2.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial2);
		
		NivelCompetencia nivelCompetencia = new NivelCompetencia();
		nivelCompetencia.setDescricao("Bom");
		nivelCompetenciaDao.save(nivelCompetencia);
		
		ConfiguracaoNivelCompetencia configuracaoNivelCompetencia = new ConfiguracaoNivelCompetencia();
		configuracaoNivelCompetencia.setFaixaSalarial(faixaSalarial1);
		configuracaoNivelCompetencia.setCompetenciaId(habilidade.getId());
		configuracaoNivelCompetencia.setTipoCompetencia('H');
		configuracaoNivelCompetencia.setNivelCompetencia(nivelCompetencia);
		configuracaoNivelCompetenciaDao.save(configuracaoNivelCompetencia);
		
		faixaSalarialDao.getHibernateTemplateByGenericDao().flush();
		
		Collection<FaixaSalarial> faixaSalarials = faixaSalarialDao.findByCargoComCompetencia(cargo.getId());
		
		FaixaSalarial primeiraFaixaSalarial = (FaixaSalarial) faixaSalarials.toArray()[0];
		FaixaSalarial segundaFaixaSalarial = (FaixaSalarial) faixaSalarials.toArray()[1];
		
		assertEquals(2, faixaSalarials.size());
		assertEquals(faixaSalarial1.getNome(), primeiraFaixaSalarial.getNome());
		assertEquals(nivelCompetencia.getDescricao(), ((Competencia)primeiraFaixaSalarial.getCompetencias().toArray()[0]).getNivelCompetencia().getDescricao());		
		assertEquals(faixaSalarial2.getNome(), segundaFaixaSalarial.getNome());
		assertEquals(0, segundaFaixaSalarial.getCompetencias().size());		
	}
	
	public void testFindByCargos()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setEmpresa(empresa);
		cargoDao.save(cargo1);
		
		Cargo cargo2 = CargoFactory.getEntity();
		cargo2.setEmpresa(empresa);
		cargoDao.save(cargo2);
		
		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setCargo(cargo1);
		faixaSalarialDao.save(faixaSalarial1);
		
		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarial2.setCargo(cargo1);
		faixaSalarialDao.save(faixaSalarial2);
		
		FaixaSalarial faixaSalarial3 = FaixaSalarialFactory.getEntity();
		faixaSalarial3.setCargo(cargo2);
		faixaSalarialDao.save(faixaSalarial3);
		
		assertEquals(3, faixaSalarialDao.findByCargos(new Long[] {cargo1.getId(), cargo2.getId()}).size());
		assertEquals(2, faixaSalarialDao.findByCargos(new Long[] {cargo1.getId()}).size());
		assertEquals(1, faixaSalarialDao.findByCargos(new Long[] {cargo2.getId()}).size());
	}

    public void testVerifyExistsNomeByCargo()
    {
    	Cargo cargo = CargoFactory.getEntity();
    	cargo = cargoDao.save(cargo);

    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
    	faixaSalarial.setCargo(cargo);
    	faixaSalarial.setNome("TESTE");
    	faixaSalarial = faixaSalarialDao.save(faixaSalarial);

    	assertEquals(true, faixaSalarialDao.verifyExistsNomeByCargo(cargo.getId(), faixaSalarial.getNome()));
    	assertEquals(false, faixaSalarialDao.verifyExistsNomeByCargo(cargo.getId(), "BLA"));
    }

    public void testFindAllSelectByCargo()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa = empresaDao.save(empresa);

    	Cargo cargo = CargoFactory.getEntity();
    	cargo.setEmpresa(empresa);
    	cargo = cargoDao.save(cargo);

    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
    	faixaSalarial.setCargo(cargo);
    	faixaSalarial = faixaSalarialDao.save(faixaSalarial);

    	Collection<FaixaSalarial> faixaSalarials = new ArrayList<FaixaSalarial>();
    	faixaSalarials.add(faixaSalarial);

    	Collection<FaixaSalarial> retorno = faixaSalarialDao.findAllSelectByCargo(empresa.getId());

    	assertEquals(faixaSalarials.size(), retorno.size());
    }

    public void testFindByFaixaSalarialId()
    {
        Cargo cargo = CargoFactory.getEntity();
        cargo = cargoDao.save(cargo);

        FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
        faixaSalarial.setCargo(cargo);
        faixaSalarial = faixaSalarialDao.save(faixaSalarial);

        FaixaSalarial retorno = faixaSalarialDao.findByFaixaSalarialId(faixaSalarial.getId());

        assertEquals(faixaSalarial.getId(), retorno.getId());
    }

    public void testFindFaixaSalarialByCargo()
    {
        Cargo cargo = CargoFactory.getEntity();
        cargo = cargoDao.save(cargo);

        FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
        faixaSalarial.setCargo(cargo);
        faixaSalarial = faixaSalarialDao.save(faixaSalarial);

        Collection<FaixaSalarial> retornos = faixaSalarialDao.findFaixaSalarialByCargo(cargo.getId());

        assertEquals(1, retornos.size());
    }

    public void testFindHistoricoAtual()
    {
    	Date data = new Date();
        FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
        faixaSalarialHistorico.setData(DateUtil.retornaDataDiaAnterior(data));
        faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

        FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
        faixaSalarial = faixaSalarialDao.save(faixaSalarial);

        faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);

        FaixaSalarialHistorico faixaSalarialHistoricoAtualMasCancelado = FaixaSalarialHistoricoFactory.getEntity();
        faixaSalarialHistoricoAtualMasCancelado.setData(data);
        faixaSalarialHistoricoAtualMasCancelado.setStatus(StatusRetornoAC.CANCELADO);
        faixaSalarialHistoricoAtualMasCancelado = faixaSalarialHistoricoDao.save(faixaSalarialHistoricoAtualMasCancelado);

        faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
        faixaSalarialHistoricoAtualMasCancelado.setFaixaSalarial(faixaSalarial);

        FaixaSalarial retorno = faixaSalarialDao.findHistoricoAtual(faixaSalarial.getId(), null);

        assertEquals(faixaSalarial.getId(), retorno.getId());
    }
    
    public void testFindComHistoricoAtual()
    {
    	Date hoje = new Date();
    	
    	FaixaSalarial faixa1 = FaixaSalarialFactory.getEntity();
    	faixaSalarialDao.save(faixa1);

    	FaixaSalarialHistorico histFaixa1 = FaixaSalarialHistoricoFactory.getEntity();
    	histFaixa1.setFaixaSalarial(faixa1);
    	histFaixa1.setData(DateUtil.retornaDataDiaAnterior(hoje));
    	histFaixa1.setStatus(StatusRetornoAC.CONFIRMADO);
    	faixaSalarialHistoricoDao.save(histFaixa1);
    	
    	FaixaSalarialHistorico histFaixa1AtualMasCancelado = FaixaSalarialHistoricoFactory.getEntity();
    	histFaixa1AtualMasCancelado.setFaixaSalarial(faixa1);
    	histFaixa1AtualMasCancelado.setData(hoje);
    	histFaixa1AtualMasCancelado.setStatus(StatusRetornoAC.CANCELADO);
    	faixaSalarialHistoricoDao.save(histFaixa1AtualMasCancelado);

    	FaixaSalarial faixa2 = FaixaSalarialFactory.getEntity();
    	faixaSalarialDao.save(faixa2);
    	
    	Indice indice = IndiceFactory.getEntity();
    	indiceDao.save(indice);
    	
    	IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity();
    	indiceHistorico.setIndice(indice);
    	indiceHistorico.setValor(1.00);
    	indiceHistorico.setData(hoje);
    	indiceHistoricoDao.save(indiceHistorico);
    	
    	FaixaSalarialHistorico histFaixa2 = FaixaSalarialHistoricoFactory.getEntity();
    	histFaixa2.setFaixaSalarial(faixa2);
    	histFaixa2.setData(DateUtil.retornaDataDiaAnterior(hoje));
    	histFaixa2.setStatus(StatusRetornoAC.CONFIRMADO);
    	histFaixa2.setTipo(TipoAplicacaoIndice.INDICE);
    	histFaixa2.setIndice(indice);
    	faixaSalarialHistoricoDao.save(histFaixa2);
    	
    	Collection<FaixaSalarial> retorno = faixaSalarialDao.findComHistoricoAtual(new Long[] { faixa1.getId(), faixa2.getId() });
    	
    	assertEquals(2, retorno.size());
    	
    	FaixaSalarial faixa1retorno = (FaixaSalarial)retorno.toArray()[0];
    	FaixaSalarial faixa2retorno = (FaixaSalarial)retorno.toArray()[1];
    	
    	assertEquals(histFaixa1.getId(), faixa1retorno.getFaixaSalarialHistoricoAtual().getId());
    	assertEquals(histFaixa2.getId(), faixa2retorno.getFaixaSalarialHistoricoAtual().getId());
    	assertEquals(indiceHistorico.getValor(), faixa2retorno.getFaixaSalarialHistoricoAtual().getIndice().getIndiceHistoricoAtual().getValor());
    }

    public void testFindComHistoricoAtualByEmpresa()
    {
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("Cargo");
		cargo.setEmpresa(empresa);
		cargoDao.save(cargo);
		
		FaixaSalarial faixa = FaixaSalarialFactory.getEntity();
		faixa.setNome("I");
		faixa.setCargo(cargo);
		faixa.setCodigoAC(null);
		faixaSalarialDao.save(faixa);
		
		FaixaSalarialHistorico h1 = FaixaSalarialHistoricoFactory.getEntity();
		h1.setFaixaSalarial(faixa);
		h1.setData(new Date());
		h1.setTipo(TipoAplicacaoIndice.VALOR);
		h1.setStatus(StatusRetornoAC.CONFIRMADO);
		faixaSalarialHistoricoDao.save(h1);
		
		FaixaSalarialHistorico h2 = FaixaSalarialHistoricoFactory.getEntity();
		h2.setData(DateUtil.criarDataMesAno(1, 1, 2010));
		h2.setFaixaSalarial(faixa);
		h2.setTipo(TipoAplicacaoIndice.INDICE);
		h2.setStatus(StatusRetornoAC.CONFIRMADO);
		faixaSalarialHistoricoDao.save(h2);
		
		Collection<FaixaSalarial> faixaSalarials = faixaSalarialDao.findComHistoricoAtualByEmpresa(empresa.getId(), true);
		
		assertTrue(faixaSalarials.size() == 1);
	}
   	public void testFindFaixas()
   	{
        Empresa empresa = EmpresaFactory.getEmpresa();
        empresa = empresaDao.save(empresa);

        Cargo cargo = CargoFactory.getEntity();
        cargo.setEmpresa(empresa);
        cargo = cargoDao.save(cargo);

        FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
        faixaSalarial.setCargo(cargo);
        faixaSalarial = faixaSalarialDao.save(faixaSalarial);

        Collection<FaixaSalarial> retorno = faixaSalarialDao.findFaixas(empresa, Cargo.TODOS, null);

        assertTrue(retorno.size() > 0);
    }

    public void testFindFaixasAtivos()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa = empresaDao.save(empresa);

    	Cargo cargo = CargoFactory.getEntity();
    	cargo.setEmpresa(empresa);
    	cargo.setAtivo(true);
    	cargo = cargoDao.save(cargo);

    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
    	faixaSalarial.setCargo(cargo);
    	faixaSalarial = faixaSalarialDao.save(faixaSalarial);

    	Collection<FaixaSalarial> retorno = faixaSalarialDao.findFaixas(empresa, Cargo.ATIVO, null);

    	assertTrue(retorno.size() > 0);
    }

	public void testUpdateCodigoAC()
	{
		Cargo cargo = CargoFactory.getEntity();
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial.setCodigoAC(null);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		faixaSalarialDao.updateCodigoAC("0123", faixaSalarial.getId());

		assertEquals("0123", faixaSalarialDao.findByFaixaSalarialId(faixaSalarial.getId()).getCodigoAC());
	}

	public void testUpdateCargoENome()
	{
		Cargo cargoOrigem = CargoFactory.getEntity();
		cargoDao.save(cargoOrigem);

		Cargo cargoDestino = CargoFactory.getEntity();
		cargoDao.save(cargoDestino);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargoOrigem);
		faixaSalarial.setNome("I");
		faixaSalarial.setCodigoAC(null);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		faixaSalarialDao.updateNomeECargo(faixaSalarial.getId(), cargoDestino.getId(), "II");

		assertEquals(cargoDestino.getId(), faixaSalarialDao.findByFaixaSalarialId(faixaSalarial.getId()).getCargo().getId());

	}
	
	public void testFindSemCodigoAC() {
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresa1 = empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresa2 = empresaDao.save(empresa2);
		
		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setEmpresa(empresa1);
		cargoDao.save(cargo1);
		
		Cargo cargo2 = CargoFactory.getEntity();
		cargo2.setEmpresa(empresa2);
		cargoDao.save(cargo2);
		
		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setCargo(cargo1);
		faixaSalarial1.setCodigoAC("1");
		faixaSalarialDao.save(faixaSalarial1);
		
		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarial2.setCodigoAC(null);
		faixaSalarial2.setCargo(cargo1);
		faixaSalarialDao.save(faixaSalarial2);
		
		FaixaSalarial faixaSalarial3 = FaixaSalarialFactory.getEntity();
		faixaSalarial3.setCodigoAC(null);
		faixaSalarial3.setCargo(cargo1);
		faixaSalarialDao.save(faixaSalarial3);
		
		FaixaSalarial faixaSalarial4 = FaixaSalarialFactory.getEntity();
		faixaSalarial4.setCodigoAC("4");
		faixaSalarial4.setCargo(cargo2);
		faixaSalarialDao.save(faixaSalarial4);
		
		FaixaSalarial faixaSalarial5 = FaixaSalarialFactory.getEntity();
		faixaSalarial5.setCodigoAC(null);
		faixaSalarial5.setCargo(cargo2);
		faixaSalarialDao.save(faixaSalarial5);
		
		assertEquals(2, faixaSalarialDao.findSemCodigoAC(empresa1.getId()).size());
		assertEquals(1, faixaSalarialDao.findSemCodigoAC(empresa2.getId()).size());
		
	}
	
	public void testFindCodigoACDuplicadoVazio()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("24342333");
		empresaDao.save(empresa);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setEmpresa(empresa);
		cargoDao.save(cargo);	
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCodigoAC("123456");
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		assertEquals("",faixaSalarialDao.findCodigoACDuplicado(empresa.getId()));
	}

	public void testFindCodigoACDuplicado()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("24342333");
		empresaDao.save(empresa);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setEmpresa(empresa);
		cargoDao.save(cargo);	
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCodigoAC("123456");
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarial2.setCodigoAC("123456");
		faixaSalarial2.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial2);

		FaixaSalarial faixaSalarial3 = FaixaSalarialFactory.getEntity();
		faixaSalarial3.setCodigoAC("1234567");
		faixaSalarial3.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial3);
		
		assertEquals("123456",faixaSalarialDao.findCodigoACDuplicado(empresa.getId()));
	}
	
    public GenericDao<FaixaSalarial> getGenericDao()
    {
        return faixaSalarialDao;
    }

    public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao)
    {
        this.faixaSalarialDao = faixaSalarialDao;
    }

    public void setCargoDao(CargoDao cargoDao)
    {
        this.cargoDao = cargoDao;
    }

    public void setFaixaSalarialHistoricoDao(FaixaSalarialHistoricoDao faixaSalarialHistoricoDao)
    {
        this.faixaSalarialHistoricoDao = faixaSalarialHistoricoDao;
    }

	public void setGrupoACDao(GrupoACDao grupoACDao) {
		this.grupoACDao = grupoACDao;
	}
	
	public void setHabilidadeDao(HabilidadeDao habilidadeDao)
	{
		this.habilidadeDao = habilidadeDao;
	}
	
	public void setNivelCompetenciaDao(NivelCompetenciaDao nivelCompetenciaDao)
	{
		this.nivelCompetenciaDao = nivelCompetenciaDao;
	}

	
	public void setConfiguracaoNivelCompetenciaDao(ConfiguracaoNivelCompetenciaDao configuracaoNivelCompetenciaDao)
	{
		this.configuracaoNivelCompetenciaDao = configuracaoNivelCompetenciaDao;
	}

	public void setIndiceDao(IndiceDao indiceDao) {
		this.indiceDao = indiceDao;
	}

	public void setIndiceHistoricoDao(IndiceHistoricoDao indiceHistoricoDao) {
		this.indiceHistoricoDao = indiceHistoricoDao;
	}

	public void setHistoricoColaboradorDao(
			HistoricoColaboradorDao historicoColaboradorDao) {
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao) {
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao) {
		this.estabelecimentoDao = estabelecimentoDao;
	}
	
	public void setEmpresaDao(EmpresaDao empresaDao){
        this.empresaDao = empresaDao;
    }
}