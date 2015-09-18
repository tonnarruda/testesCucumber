package com.fortes.rh.test.dao.hibernate.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialHistoricoDao;
import com.fortes.rh.dao.cargosalario.GrupoOcupacionalDao;
import com.fortes.rh.dao.cargosalario.IndiceDao;
import com.fortes.rh.dao.cargosalario.ReajusteFaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.TabelaReajusteColaboradorDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistoricoVO;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.ReajusteFaixaSalarial;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.GrupoOcupacionalFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;
import com.fortes.rh.util.DateUtil;

public class FaixaSalarialHistoricoDaoHibernateTest extends GenericDaoHibernateTest<FaixaSalarialHistorico>
{
	private FaixaSalarialHistoricoDao faixaSalarialHistoricoDao;
	private FaixaSalarialDao faixaSalarialDao;
	private IndiceDao indiceDao;
	private GrupoOcupacionalDao grupoOcupacionalDao;
	private CargoDao cargoDao;
	private EmpresaDao empresaDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao;
	private ReajusteFaixaSalarialDao reajusteFaixaSalarialDao;

	private FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
	
	public FaixaSalarialHistorico getEntity()
	{
		return FaixaSalarialHistoricoFactory.getEntity();
	}

	public void setFaixaSalarialHistoricoDao(FaixaSalarialHistoricoDao faixaSalarialHistoricoDao)
	{
		this.faixaSalarialHistoricoDao = faixaSalarialHistoricoDao;
	}

	public void testFindAllSelect()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = faixaSalarialHistoricoDao.findAllSelect(faixaSalarial.getId());

		assertEquals(1, faixaSalarialHistoricos.size());
	}
	
	public void testFindAllComHistoricoIndice()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		Collection<FaixaSalarialHistoricoVO> faixaSalarialHistoricoVOs = faixaSalarialHistoricoDao.findAllComHistoricoIndice(faixaSalarialHistorico.getFaixaSalarial().getId());

		// como a consulta utiliza SQLQuery (sessão diferente), o teste verifica somente quanto ao lançamento de exceções
		assertTrue(faixaSalarialHistoricoVOs.size() >= 0);
	}
	
	public void testFindIdByDataFaixa()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);
		
		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico.setData(new Date());
		faixaSalarialHistoricoDao.save(faixaSalarialHistorico);
		
		assertEquals(faixaSalarialHistorico.getId(), faixaSalarialHistoricoDao.findIdByDataFaixa(faixaSalarialHistorico));
	}

	
	public void testDeleteByFaixa() {
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixaSalarial);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistoricoDao.save(faixaSalarialHistorico);
		
		Exception exception = null;
		try {
			faixaSalarialHistoricoDao.deleteByFaixaSalarial(new Long[] {faixaSalarial.getId()});
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}

	public void testFindByTabelaReajusteId() {
		
		FaixaSalarial faixa1 = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixa1);

		FaixaSalarial faixa2 = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixa2);
		
		FaixaSalarial faixa3 = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixa3);

		TabelaReajusteColaborador tabela1 = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaboradorDao.save(tabela1);

		TabelaReajusteColaborador tabela2 = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaboradorDao.save(tabela2);
		
		ReajusteFaixaSalarial reajuste1 = new ReajusteFaixaSalarial();
		reajuste1.setTabelaReajusteColaborador(tabela1);
		reajuste1.setFaixaSalarial(faixa1);
		reajuste1.setValorAtual(1000.00);
		reajuste1.setValorProposto(1200.00);
		reajusteFaixaSalarialDao.save(reajuste1);
		
		ReajusteFaixaSalarial reajuste2 = new ReajusteFaixaSalarial();
		reajuste2.setTabelaReajusteColaborador(tabela1);
		reajuste2.setFaixaSalarial(faixa2);
		reajuste2.setValorAtual(1000.00);
		reajuste2.setValorProposto(1200.00);
		reajusteFaixaSalarialDao.save(reajuste2);
		
		ReajusteFaixaSalarial reajuste3 = new ReajusteFaixaSalarial();
		reajuste3.setTabelaReajusteColaborador(tabela2);
		reajuste3.setFaixaSalarial(faixa3);
		reajuste3.setValorAtual(1000.00);
		reajuste3.setValorProposto(1200.00);
		reajusteFaixaSalarialDao.save(reajuste3);
		
		FaixaSalarialHistorico historico1 = FaixaSalarialHistoricoFactory.getEntity();
		historico1.setFaixaSalarial(faixa1);
		historico1.setReajusteFaixaSalarial(reajuste1);
		faixaSalarialHistoricoDao.save(historico1);
		
		FaixaSalarialHistorico historico2 = FaixaSalarialHistoricoFactory.getEntity();
		historico2.setFaixaSalarial(faixa2);
		historico2.setReajusteFaixaSalarial(reajuste2);
		faixaSalarialHistoricoDao.save(historico2);
		
		FaixaSalarialHistorico historico3 = FaixaSalarialHistoricoFactory.getEntity();
		historico3.setFaixaSalarial(faixa3);
		historico3.setReajusteFaixaSalarial(reajuste3);
		faixaSalarialHistoricoDao.save(historico3);
		
		assertEquals(2, faixaSalarialHistoricoDao.findByTabelaReajusteId(tabela1.getId()).size());
		assertEquals(1, faixaSalarialHistoricoDao.findByTabelaReajusteId(tabela2.getId()).size());
	}

	public void testFindReajusteFaixaSalarial() 
	{
		FaixaSalarial faixa1 = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixa1);
		
		FaixaSalarial faixa2 = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixa2);
		
		TabelaReajusteColaborador tabela1 = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaboradorDao.save(tabela1);

		ReajusteFaixaSalarial reajuste1 = new ReajusteFaixaSalarial();
		reajuste1.setFaixaSalarial(faixa1);
		reajuste1.setValorAtual(1000.00);
		reajuste1.setValorProposto(1200.00);
		reajuste1.setTabelaReajusteColaborador(tabela1);
		reajusteFaixaSalarialDao.save(reajuste1);
		
		ReajusteFaixaSalarial reajuste2 = new ReajusteFaixaSalarial();
		reajuste2.setFaixaSalarial(faixa2);
		reajuste2.setValorAtual(1000.00);
		reajuste2.setValorProposto(1200.00);
		reajuste2.setTabelaReajusteColaborador(tabela1);
		reajusteFaixaSalarialDao.save(reajuste2);
		
		Date data = DateUtil.criarDataMesAno(01, 01, 2010);
		
		FaixaSalarialHistorico historico1 = FaixaSalarialHistoricoFactory.getEntity();
		historico1.setFaixaSalarial(faixa1);
		historico1.setReajusteFaixaSalarial(reajuste1);
		historico1.setData(data);
		faixaSalarialHistoricoDao.save(historico1);
		
		FaixaSalarialHistorico historico2 = FaixaSalarialHistoricoFactory.getEntity();
		historico2.setFaixaSalarial(faixa2);
		historico2.setReajusteFaixaSalarial(reajuste2);
		historico2.setData(data);
		faixaSalarialHistoricoDao.save(historico2);
		
		assertEquals(reajuste1.getId(), ((ReajusteFaixaSalarial) faixaSalarialHistoricoDao.findReajusteFaixaSalarial(data, faixa1.getId())).getId());
	}
	
	public void testfindHistoricoAtual() {
		
		FaixaSalarial faixa1 = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixa1);
		
		FaixaSalarial faixa2 = FaixaSalarialFactory.getEntity();
		faixaSalarialDao.save(faixa2);
		
		Date data = DateUtil.criarDataMesAno(01, 01, 2010);
		Date data2 = DateUtil.incrementaMes(data, 5);
		
		FaixaSalarialHistorico historico1 = FaixaSalarialHistoricoFactory.getEntity();
		historico1.setFaixaSalarial(faixa1);
		historico1.setData(data);
		historico1.setStatus(1);
		faixaSalarialHistoricoDao.save(historico1);
		
		FaixaSalarialHistorico historico2 = FaixaSalarialHistoricoFactory.getEntity();
		historico2.setFaixaSalarial(faixa2);
		historico2.setData(data);
		historico2.setStatus(1);
		faixaSalarialHistoricoDao.save(historico2);

		FaixaSalarialHistorico historico3 = FaixaSalarialHistoricoFactory.getEntity();
		historico3.setFaixaSalarial(faixa2);
		historico3.setData(data2);
		historico3.setStatus(1);
		faixaSalarialHistoricoDao.save(historico3);
		
		assertEquals(historico3.getId(), ((FaixaSalarialHistorico) faixaSalarialHistoricoDao.findHistoricoAtual(faixa2.getId())).getId());
	}
	
	public void testFindByIdProjection()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		FaixaSalarialHistorico faixaSalarialHistoricoRetorno = faixaSalarialHistoricoDao.findByIdProjection(faixaSalarialHistorico.getId());
		assertEquals(faixaSalarialHistorico, faixaSalarialHistoricoRetorno);
		assertEquals(faixaSalarial, faixaSalarialHistoricoRetorno.getFaixaSalarial());
	}

	public void testVerifyData()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		Date data = new Date();

		assertEquals(false, faixaSalarialHistoricoDao.verifyData(null, data, faixaSalarial.getId()));
	}

	public void testVerifyDataUpdate()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		Date data = new Date();

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico.setData(data);
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		assertEquals(false, faixaSalarialHistoricoDao.verifyData(faixaSalarialHistorico.getId(), data, faixaSalarial.getId()));
	}

	public void testVerifyDataInsert()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		Date data = new Date();

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico.setData(data);
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		assertEquals(true, faixaSalarialHistoricoDao.verifyData(null, data, faixaSalarial.getId()));
	}

	public void testSetStatus()
	{
		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setStatus(StatusRetornoAC.AGUARDANDO);
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		assertEquals(true, faixaSalarialHistoricoDao.setStatus(faixaSalarialHistorico.getId(), true));

		FaixaSalarialHistorico faixaSalarialHistoricoRetorno = faixaSalarialHistoricoDao.findByIdProjection(faixaSalarialHistorico.getId());
		assertEquals(StatusRetornoAC.CONFIRMADO, faixaSalarialHistoricoRetorno.getStatus().intValue());
	}

	public void testFindByFaixaSalarialId()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		Indice indice = IndiceFactory.getEntity();
		indice = indiceDao.save(indice);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico.setIndice(indice);
		faixaSalarialHistorico.setData(new Date());
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		FaixaSalarialHistorico retorno = faixaSalarialHistoricoDao.findByFaixaSalarialId(faixaSalarial.getId());

		assertEquals(faixaSalarialHistorico.getId(), retorno.getId());
	}
	
	public void testFindHistoricosByFaixaSalarialId()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);
		
		Indice indice = IndiceFactory.getEntity();
		indice = indiceDao.save(indice);
		
		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico.setIndice(indice);
		faixaSalarialHistorico.setData(new Date());
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);
		
		Collection<FaixaSalarialHistorico> retorno = faixaSalarialHistoricoDao.findHistoricosByFaixaSalarialId(faixaSalarial.getId(), null);
		
		assertEquals(1, retorno.size());
	}

	public void testFindByPeriodo()
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		Indice indice = IndiceFactory.getEntity();
		indice = indiceDao.save(indice);

		FaixaSalarialHistorico faixaSalarialHistorico1 = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico1.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico1.setData(DateUtil.criarDataMesAno(01, 01, 2100));
		faixaSalarialHistorico1 = faixaSalarialHistoricoDao.save(faixaSalarialHistorico1);

		FaixaSalarialHistorico faixaSalarialHistorico2 = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico2.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico2.setData(DateUtil.criarDataMesAno(01, 02, 2100));
		faixaSalarialHistorico2 = faixaSalarialHistoricoDao.save(faixaSalarialHistorico2);

		FaixaSalarialHistorico faixaSalarialHistorico3 = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico3.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico3.setData(DateUtil.criarDataMesAno(01, 03, 2100));
		faixaSalarialHistorico3 = faixaSalarialHistoricoDao.save(faixaSalarialHistorico3);

		Collection<FaixaSalarialHistorico> retorno = faixaSalarialHistoricoDao.findByPeriodo(faixaSalarial.getId(), DateUtil.criarDataMesAno(01, 02, 2100));

		assertEquals(2, retorno.size());
	}

	public void testRemoveByFaixas()
	{
		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1 = faixaSalarialDao.save(faixaSalarial1);

		FaixaSalarial faixaSalarial2 = FaixaSalarialFactory.getEntity();
		faixaSalarial2 = faixaSalarialDao.save(faixaSalarial2);

		FaixaSalarialHistorico faixaSalarialHistorico1 = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico1.setFaixaSalarial(faixaSalarial1);
		faixaSalarialHistorico1 = faixaSalarialHistoricoDao.save(faixaSalarialHistorico1);

		FaixaSalarialHistorico faixaSalarialHistorico2 = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico2.setFaixaSalarial(faixaSalarial2);
		faixaSalarialHistorico2 = faixaSalarialHistoricoDao.save(faixaSalarialHistorico2);

		Long[] faixaSalarialIds = new Long[]{faixaSalarial1.getId(), faixaSalarial2.getId()};

		faixaSalarialHistoricoDao.removeByFaixas(faixaSalarialIds);

		assertNull(faixaSalarialHistoricoDao.findById(faixaSalarialHistorico1.getId(), null));
		assertNull(faixaSalarialHistoricoDao.findById(faixaSalarialHistorico2.getId(), null));
	}

	public void testFindByGrupoCargoAreaDataApenasGrupo()
	{
		Date data1 = DateUtil.criarDataMesAno(01, 11, 2008);
		Date data2 = DateUtil.criarDataMesAno(01, 12, 2008);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		GrupoOcupacional grupoOcupacional1 = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional1 = grupoOcupacionalDao.save(grupoOcupacional1);

		GrupoOcupacional grupoOcupacional2 = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional2 = grupoOcupacionalDao.save(grupoOcupacional2);

		Collection<Long> grupoIds = new ArrayList<Long>();
		grupoIds.add(grupoOcupacional1.getId());

		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setGrupoOcupacional(grupoOcupacional1);
		cargo1.setEmpresa(empresa);
		cargo1 = cargoDao.save(cargo1);

		Cargo cargo2Fora = CargoFactory.getEntity();
		cargo2Fora.setGrupoOcupacional(grupoOcupacional2);
		cargo2Fora.setEmpresa(empresa);
		cargo2Fora = cargoDao.save(cargo2Fora);

		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setCargo(cargo1);
		faixaSalarial1 = faixaSalarialDao.save(faixaSalarial1);

		FaixaSalarial faixaSalarial2Fora = FaixaSalarialFactory.getEntity();
		faixaSalarial2Fora.setCargo(cargo2Fora);
		faixaSalarial2Fora = faixaSalarialDao.save(faixaSalarial2Fora);

		Indice indice = IndiceFactory.getEntity();
		indice = indiceDao.save(indice);

		FaixaSalarialHistorico faixaSalarialHistorico1 = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico1.setFaixaSalarial(faixaSalarial1);
		faixaSalarialHistorico1.setData(data1);
		faixaSalarialHistorico1.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico1.setValor(200.0);
		faixaSalarialHistorico1 = faixaSalarialHistoricoDao.save(faixaSalarialHistorico1);

		FaixaSalarialHistorico faixaSalarialHistorico2 = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico2.setFaixaSalarial(faixaSalarial1);
		faixaSalarialHistorico2.setData(data2);
		faixaSalarialHistorico2.setTipo(TipoAplicacaoIndice.INDICE);
		faixaSalarialHistorico2.setIndice(indice);
		faixaSalarialHistorico2 = faixaSalarialHistoricoDao.save(faixaSalarialHistorico2);

		FaixaSalarialHistorico faixaSalarialHistorico3 = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico3.setFaixaSalarial(faixaSalarial2Fora);
		faixaSalarialHistorico3.setData(data1);
		faixaSalarialHistorico3.setTipo(TipoAplicacaoIndice.INDICE);
		faixaSalarialHistorico3.setIndice(indice);
		faixaSalarialHistorico3 = faixaSalarialHistoricoDao.save(faixaSalarialHistorico3);

		Collection<FaixaSalarialHistorico> retorno = faixaSalarialHistoricoDao.findByGrupoCargoAreaData(grupoIds, new ArrayList<Long>(), null, data1, Boolean.FALSE, empresa.getId(), null);

		assertEquals(1, retorno.size());
	}
	
	public void testFindByGrupoCargoAreaData()
	{
		Date data1 = DateUtil.criarDataMesAno(01, 11, 2008);
		Date data2 = DateUtil.criarDataMesAno(01, 12, 2008);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional1.setEmpresa(empresa);
		areaOrganizacionalDao.save(areaOrganizacional1);
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional2.setEmpresa(empresa);
		areaOrganizacionalDao.save(areaOrganizacional2);
		AreaOrganizacional areaOrganizacional3 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional3.setEmpresa(empresa);
		areaOrganizacionalDao.save(areaOrganizacional3);

		GrupoOcupacional grupoOcupacional1 = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional1 = grupoOcupacionalDao.save(grupoOcupacional1);

		GrupoOcupacional grupoOcupacional2 = GrupoOcupacionalFactory.getGrupoOcupacional();
		grupoOcupacional2 = grupoOcupacionalDao.save(grupoOcupacional2);

		Collection<Long> grupoIds = new ArrayList<Long>();
		grupoIds.add(grupoOcupacional1.getId());

		Cargo cargo1 = CargoFactory.getEntity();
		cargo1.setEmpresa(empresa);
		cargo1.setAreasOrganizacionais(new ArrayList<AreaOrganizacional>());
		cargo1.getAreasOrganizacionais().add(areaOrganizacional1);
		cargo1.getAreasOrganizacionais().add(areaOrganizacional2);
		
		cargo1.setGrupoOcupacional(grupoOcupacional1);
		cargo1 = cargoDao.save(cargo1);

		Cargo cargo2Fora = CargoFactory.getEntity();
		cargo2Fora.setEmpresa(empresa);
		cargo2Fora.setGrupoOcupacional(grupoOcupacional2);
		cargo2Fora = cargoDao.save(cargo2Fora);

		FaixaSalarial faixaSalarial1 = FaixaSalarialFactory.getEntity();
		faixaSalarial1.setCargo(cargo1);
		faixaSalarial1 = faixaSalarialDao.save(faixaSalarial1);

		FaixaSalarial faixaSalarial2Fora = FaixaSalarialFactory.getEntity();
		faixaSalarial2Fora.setCargo(cargo2Fora);
		faixaSalarial2Fora = faixaSalarialDao.save(faixaSalarial2Fora);

		Indice indice = IndiceFactory.getEntity();
		indice = indiceDao.save(indice);

		FaixaSalarialHistorico faixaSalarialHistorico1 = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico1.setFaixaSalarial(faixaSalarial1);
		faixaSalarialHistorico1.setData(data1);
		faixaSalarialHistorico1.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico1.setValor(200.0);
		faixaSalarialHistorico1 = faixaSalarialHistoricoDao.save(faixaSalarialHistorico1);

		FaixaSalarialHistorico faixaSalarialHistorico2 = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico2.setFaixaSalarial(faixaSalarial1);
		faixaSalarialHistorico2.setData(data2);
		faixaSalarialHistorico2.setTipo(TipoAplicacaoIndice.INDICE);
		faixaSalarialHistorico2.setIndice(indice);
		faixaSalarialHistorico2 = faixaSalarialHistoricoDao.save(faixaSalarialHistorico2);

		FaixaSalarialHistorico faixaSalarialHistorico3 = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico3.setFaixaSalarial(faixaSalarial2Fora);
		faixaSalarialHistorico3.setData(data1);
		faixaSalarialHistorico3.setTipo(TipoAplicacaoIndice.INDICE);
		faixaSalarialHistorico3.setIndice(indice);
		faixaSalarialHistorico3 = faixaSalarialHistoricoDao.save(faixaSalarialHistorico3);
		
		Collection<Long> areaIds = new ArrayList<Long>();
		areaIds.add(areaOrganizacional1.getId());
		areaIds.add(areaOrganizacional2.getId());
		
		Collection<FaixaSalarialHistorico> retorno = faixaSalarialHistoricoDao.findByGrupoCargoAreaData(grupoIds, new ArrayList<Long>(), areaIds, data1, Boolean.FALSE, empresa.getId(), null);

		assertEquals(1, retorno.size());
	}

	public void testFindPendenciasByFaixaSalarialHistorico()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Cargo cargo = CargoFactory.getEntity();
		cargo.setEmpresa(empresa);
		cargo = cargoDao.save(cargo);

		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setCargo(cargo);
		faixaSalarial = faixaSalarialDao.save(faixaSalarial);

		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico.setStatus(StatusRetornoAC.AGUARDANDO);
		faixaSalarialHistorico = faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		Collection<FaixaSalarialHistorico> retorno = faixaSalarialHistoricoDao.findPendenciasByFaixaSalarialHistorico(empresa.getId());

		assertTrue(retorno.size() >= 1);
	}
	
	public void testFindByTabelaReajusteIdData()
	{
		TabelaReajusteColaborador tabela1 = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaboradorDao.save(tabela1);
		
		TabelaReajusteColaborador tabela2 = TabelaReajusteColaboradorFactory.getEntity();
		tabelaReajusteColaboradorDao.save(tabela2);
		
		Cargo cargo = CargoFactory.getEntity();
		cargoDao.save(cargo);
		
		FaixaSalarial faixa1 = FaixaSalarialFactory.getEntity();
		faixa1.setCargo(cargo);
		faixaSalarialDao.save(faixa1);
		
		FaixaSalarial faixa2 = FaixaSalarialFactory.getEntity();
		faixa2.setCargo(cargo);
		faixaSalarialDao.save(faixa2);
		
		FaixaSalarial faixa3 = FaixaSalarialFactory.getEntity();
		faixa3.setCargo(cargo);
		faixaSalarialDao.save(faixa3);
		
		ReajusteFaixaSalarial reajuste1 = new ReajusteFaixaSalarial();
		reajuste1.setFaixaSalarial(faixa1);
		reajuste1.setTabelaReajusteColaborador(tabela1);
		reajuste1.setValorAtual(100.00);
		reajuste1.setValorProposto(200.00);
		reajusteFaixaSalarialDao.save(reajuste1);
		
		ReajusteFaixaSalarial reajuste2 = new ReajusteFaixaSalarial();
		reajuste2.setFaixaSalarial(faixa2);
		reajuste2.setTabelaReajusteColaborador(tabela1);
		reajuste2.setValorAtual(100.00);
		reajuste2.setValorProposto(200.00);
		reajusteFaixaSalarialDao.save(reajuste2);
		
		ReajusteFaixaSalarial reajuste3 = new ReajusteFaixaSalarial();
		reajuste3.setFaixaSalarial(faixa3);
		reajuste3.setTabelaReajusteColaborador(tabela2);
		reajuste3.setValorAtual(100.00);
		reajuste3.setValorProposto(200.00);
		reajusteFaixaSalarialDao.save(reajuste3);
		
		Date data = DateUtil.criarDataMesAno(01, 01, 2010);
		
		FaixaSalarialHistorico historico1 = FaixaSalarialHistoricoFactory.getEntity();
		historico1.setFaixaSalarial(faixa1);
		historico1.setReajusteFaixaSalarial(reajuste1);
		historico1.setData(data);
		faixaSalarialHistoricoDao.save(historico1);
		
		FaixaSalarialHistorico historico2 = FaixaSalarialHistoricoFactory.getEntity();
		historico2.setFaixaSalarial(faixa2);
		historico2.setReajusteFaixaSalarial(reajuste2);
		historico2.setData(data);
		faixaSalarialHistoricoDao.save(historico2);
		
		FaixaSalarialHistorico historico3 = FaixaSalarialHistoricoFactory.getEntity();
		historico3.setFaixaSalarial(faixa3);
		historico3.setReajusteFaixaSalarial(reajuste3);
		historico3.setData(data);
		faixaSalarialHistoricoDao.save(historico3);
		
		assertEquals("Tabela Reajuste 1", 2, faixaSalarialHistoricoDao.findByTabelaReajusteIdData(tabela1.getId(), data).size());
		assertEquals("Tabela Reajuste 2", 1, faixaSalarialHistoricoDao.findByTabelaReajusteIdData(tabela2.getId(), data).size());
	}
	
	public void testExisteHistoricoPorIndice()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setEmpresa(empresa);
		cargoDao.save(cargo);
		
		FaixaSalarial faixa = FaixaSalarialFactory.getEntity();
		faixa.setCargo(cargo);
		faixaSalarialDao.save(faixa);

		assertFalse(faixaSalarialHistoricoDao.existeHistoricoPorIndice(empresa.getId()));
		
		FaixaSalarialHistorico h1 = FaixaSalarialHistoricoFactory.getEntity();
		h1.setFaixaSalarial(faixa);
		h1.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistoricoDao.save(h1);
		
		assertFalse(faixaSalarialHistoricoDao.existeHistoricoPorIndice(empresa.getId()));
		
		FaixaSalarialHistorico h2 = FaixaSalarialHistoricoFactory.getEntity();
		h2.setFaixaSalarial(faixa);
		h2.setTipo(TipoAplicacaoIndice.INDICE);
		faixaSalarialHistoricoDao.save(h2);
		
		assertTrue(faixaSalarialHistoricoDao.existeHistoricoPorIndice(empresa.getId()));
	}
	
	public void testExisteDependenciaComHistoricoIndiceExistindoUmHistoricoIndice()
	{
		Indice indice = IndiceFactory.getEntity();
		indiceDao.save(indice);
		
		Date dataHistoricoIndice = DateUtil.criarDataDiaMesAno("01/01/2015");
		Date dataHistoricoFaixaSalarial1 = DateUtil.criarDataDiaMesAno("01/01/2015");
		Date dataHistoricoFaixaSalarial2 = DateUtil.criarDataDiaMesAno("02/01/2015");
		
		criaHistoricoFaixaSalarial(indice, dataHistoricoFaixaSalarial1, TipoAplicacaoIndice.INDICE, StatusRetornoAC.CONFIRMADO, false);
		boolean existeDependencia = faixaSalarialHistoricoDao.existeDependenciaComHistoricoIndice(dataHistoricoIndice, null, indice.getId());
		
		assertTrue("Histórico do colaborador na mesma data que histórico do índice", existeDependencia);
		
		criaHistoricoFaixaSalarial(indice, dataHistoricoFaixaSalarial2, TipoAplicacaoIndice.INDICE, StatusRetornoAC.AGUARDANDO, false);
		existeDependencia = faixaSalarialHistoricoDao.existeDependenciaComHistoricoIndice(dataHistoricoIndice, null, indice.getId());
		
		assertTrue("Histórico da faixa salarial com data posterior à data do histórico do índice", existeDependencia);
	}
	
	public void testExisteDependenciaComHistoricoIndiceExistindoMaisDeUmHistoricoIndice()
	{
		Indice indice = IndiceFactory.getEntity();
		indiceDao.save(indice);
		
		Date dataHistoricoIndice1 = DateUtil.criarDataDiaMesAno("01/01/2015");
		Date dataHistoricoIndice2 = DateUtil.criarDataDiaMesAno("01/02/2015");
		Date dataHistoricoFaixaSalarial1 = DateUtil.criarDataDiaMesAno("01/01/2015");
		Date dataHistoricoFaixaSalarial2 = DateUtil.criarDataDiaMesAno("02/01/2015");
		
		criaHistoricoFaixaSalarial(indice, dataHistoricoFaixaSalarial1, TipoAplicacaoIndice.INDICE, StatusRetornoAC.CONFIRMADO, false);
		boolean existeDependencia = faixaSalarialHistoricoDao.existeDependenciaComHistoricoIndice(dataHistoricoIndice1, dataHistoricoFaixaSalarial2, indice.getId());
		
		assertTrue("Histórico da faixa salarial na mesma data que histórico do índice", existeDependencia);
		
		criaHistoricoFaixaSalarial(indice, dataHistoricoFaixaSalarial2, TipoAplicacaoIndice.INDICE, StatusRetornoAC.AGUARDANDO, false);
		existeDependencia = faixaSalarialHistoricoDao.existeDependenciaComHistoricoIndice(dataHistoricoIndice1, dataHistoricoIndice2, indice.getId());
		
		assertTrue("Histórico da faixa salarial com data posterior à data do histórico do índice", existeDependencia);
	}

	public void testExisteDependenciaComHistoricoIndiceComCondicoesInsatisfatorias()
	{
		Indice indice1 = IndiceFactory.getEntity();
		indiceDao.save(indice1);

		Indice indice2 = IndiceFactory.getEntity();
		indiceDao.save(indice2);

		Date dataHistoricoIndice1 = DateUtil.criarDataDiaMesAno("01/01/2015");
		Date dataHistoricoIndice2 = DateUtil.criarDataDiaMesAno("01/02/2015");
		Date dataHistoricoFaixaSalarial1 = DateUtil.criarDataDiaMesAno("01/01/2015");
		Date dataHistoricoFaixaSalarial2 = DateUtil.criarDataDiaMesAno("02/01/2015");

		criaHistoricoFaixaSalarial(indice1, dataHistoricoFaixaSalarial1, TipoAplicacaoIndice.INDICE, StatusRetornoAC.CANCELADO, false);
		boolean existeDependencia = faixaSalarialHistoricoDao.existeDependenciaComHistoricoIndice(dataHistoricoIndice1, null, indice1.getId());

		assertFalse("Histórico da faixa salarial cancelado", existeDependencia);

		criaHistoricoFaixaSalarial(indice1, dataHistoricoFaixaSalarial1, TipoAplicacaoIndice.VALOR, StatusRetornoAC.AGUARDANDO, false);
		existeDependencia = faixaSalarialHistoricoDao.existeDependenciaComHistoricoIndice(dataHistoricoIndice1, dataHistoricoIndice2, indice1.getId());
		
		assertFalse("Histórico da faixa salarial por valor", existeDependencia);
		
		criaHistoricoFaixaSalarial(indice1, dataHistoricoFaixaSalarial2, TipoAplicacaoIndice.INDICE, StatusRetornoAC.CONFIRMADO, false);
		existeDependencia = faixaSalarialHistoricoDao.existeDependenciaComHistoricoIndice(dataHistoricoIndice1, dataHistoricoIndice2, indice2.getId());
		
		assertFalse("Histórico da faixa salarial com outro índice", existeDependencia);
	}
	
	private void criaHistoricoFaixaSalarial(Indice indice, Date dataPrimeiroHistoricoIndice, int tipo, int status, boolean criaNovoHistorico)
	{
		FaixaSalarialHistorico faixaSalarialHistorico = null;
		if(criaNovoHistorico)
			faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		else
			faixaSalarialHistorico = this.faixaSalarialHistorico;
		
		faixaSalarialHistorico.setData(dataPrimeiroHistoricoIndice);
		faixaSalarialHistorico.setTipo(tipo);
		faixaSalarialHistorico.setIndice(indice);
		faixaSalarialHistorico.setStatus(status);
		faixaSalarialHistoricoDao.save(faixaSalarialHistorico);
	}

	public void testFindByEmpresaIdAndStatus() 
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setEmpresa(empresa);
		cargoDao.save(cargo);
		
		FaixaSalarial faixa1 = FaixaSalarialFactory.getEntity();
		faixa1.setCargo(cargo);
		faixaSalarialDao.save(faixa1);
		
		FaixaSalarial faixa2 = FaixaSalarialFactory.getEntity();
		faixa2.setCargo(cargo);
		faixaSalarialDao.save(faixa2);
		
		FaixaSalarial faixa3 = FaixaSalarialFactory.getEntity();
		faixa3.setCargo(cargo);
		faixaSalarialDao.save(faixa3);
		
		FaixaSalarialHistorico historico1 = FaixaSalarialHistoricoFactory.getEntity();
		historico1.setFaixaSalarial(faixa1);
		historico1.setData(DateUtil.criarDataMesAno(01, 01, 2010));
		historico1.setStatus(StatusRetornoAC.CONFIRMADO);
		faixaSalarialHistoricoDao.save(historico1);
		
		FaixaSalarialHistorico historico2 = FaixaSalarialHistoricoFactory.getEntity();
		historico2.setFaixaSalarial(faixa2);
		historico2.setStatus(StatusRetornoAC.CONFIRMADO);
		historico2.setData(DateUtil.criarDataMesAno(01, 01, 2010));
		faixaSalarialHistoricoDao.save(historico2);
		
		FaixaSalarialHistorico historico3 = FaixaSalarialHistoricoFactory.getEntity();
		historico3.setFaixaSalarial(faixa2);
		historico3.setStatus(StatusRetornoAC.AGUARDANDO);
		historico3.setData(DateUtil.criarDataMesAno(1,1, 2011));
		faixaSalarialHistoricoDao.save(historico3);
		
		FaixaSalarialHistorico historico4 = FaixaSalarialHistoricoFactory.getEntity();
		historico4.setFaixaSalarial(faixa3);
		historico4.setStatus(StatusRetornoAC.AGUARDANDO);
		historico4.setData(DateUtil.criarDataMesAno(1,1, 2011));
		faixaSalarialHistoricoDao.save(historico4);
		
		assertEquals(2, faixaSalarialHistoricoDao.findByEmpresaIdAndStatus(empresa.getId(), StatusRetornoAC.AGUARDANDO).size());
	}
	
	public GenericDao<FaixaSalarialHistorico> getGenericDao()
	{
		return faixaSalarialHistoricoDao;
	}

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao)
	{
		this.faixaSalarialDao = faixaSalarialDao;
	}

	public void setIndiceDao(IndiceDao indiceDao)
	{
		this.indiceDao = indiceDao;
	}

	public void setGrupoOcupacionalDao(GrupoOcupacionalDao grupoOcupacionalDao)
	{
		this.grupoOcupacionalDao = grupoOcupacionalDao;
	}

	public void setCargoDao(CargoDao cargoDao)
	{
		this.cargoDao = cargoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
	{
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setTabelaReajusteColaboradorDao(TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao)
	{
		this.tabelaReajusteColaboradorDao = tabelaReajusteColaboradorDao;
	}

	public void setReajusteFaixaSalarialDao(ReajusteFaixaSalarialDao reajusteFaixaSalarialDao)
	{
		this.reajusteFaixaSalarialDao = reajusteFaixaSalarialDao;
	}
}
