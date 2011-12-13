package com.fortes.rh.test.dao.hibernate.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialHistoricoDao;
import com.fortes.rh.dao.cargosalario.IndiceDao;
import com.fortes.rh.dao.cargosalario.IndiceHistoricoDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceHistoricoFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.GrupoACFactory;

public class IndiceDaoHibernateTest extends GenericDaoHibernateTest<Indice>
{
	private IndiceDao indiceDao;
	private IndiceHistoricoDao indiceHistoricoDao;
	private GrupoACDao grupoACDao;
	private EmpresaDao empresaDao;
	private CargoDao cargoDao;
	private FaixaSalarialDao faixaSalarialDao;
	private FaixaSalarialHistoricoDao faixaSalarialHistoricoDao;

	public Indice getEntity()
	{
		return IndiceFactory.getEntity();
	}

	public GenericDao<Indice> getGenericDao()
	{
		return indiceDao;
	}

	public void testFindByIdProjection()
	{
		Indice indice = IndiceFactory.getEntity();
		indice = indiceDao.save(indice);

		Indice indiceRetorno = indiceDao.findByIdProjection(indice.getId());
		assertEquals(indice, indiceRetorno);
	}

	public void testFindByCodigo()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Indice indice = IndiceFactory.getEntity();
		indice.setCodigoAC("0013254765");
		indice.setGrupoAC(grupoAC.getCodigo());
		indice = indiceDao.save(indice);

		Indice indiceRetorno = indiceDao.findByCodigo("0013254765", "XXX");
		assertEquals(indice, indiceRetorno);
	}

	public void testRemoveByCodigo()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Indice indice = IndiceFactory.getEntity();
		indice.setCodigoAC("0013254765");
		indice.setGrupoAC(grupoAC.getCodigo());
		indice = indiceDao.save(indice);

		assertTrue(indiceDao.remove("0013254765", "XXX"));
		assertNull(indiceDao.findByCodigo("0013254765", "XXX"));
	}

	public void testFindHistoricoAtual()
	{
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity();
		indiceHistorico = indiceHistoricoDao.save(indiceHistorico);

		Indice indice = IndiceFactory.getEntity();
		indice = indiceDao.save(indice);

		indiceHistorico.setIndice(indice);

		Indice retorno = indiceDao.findHistoricoAtual(indice.getId(), null);

		assertEquals(indice.getId(), retorno.getId());
	}

	public void testFindIndiceByCodigoAc()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Indice indice = IndiceFactory.getEntity();
		indice.setCodigoAC("010203");
		indice.setGrupoAC(grupoAC.getCodigo());
		indice = indiceDao.save(indice);
		
		Indice indiceRetorno = indiceDao.findIndiceByCodigoAc(indice.getCodigoAC(), "XXX");
		
		assertEquals(indice, indiceRetorno);
	}

	public void testfindSemCodigoAC()
	{
		GrupoAC grupoAC1 = GrupoACFactory.getEntity();
		grupoAC1.setCodigo("998");
		grupoACDao.save(grupoAC1);

		GrupoAC grupoAC2 = GrupoACFactory.getEntity();
		grupoAC2.setCodigo("999");
		grupoACDao.save(grupoAC2);
		
		Empresa ente = EmpresaFactory.getEmpresa();
		ente.setCodigoAC("000225");
		ente.setGrupoAC(grupoAC1.getCodigo());
		empresaDao.save(ente);
		
		Indice indice = IndiceFactory.getEntity();
		indice.setCodigoAC("");
		indice.setGrupoAC(grupoAC1.getCodigo());
		indiceDao.save(indice);

		Indice indice2Repetido = IndiceFactory.getEntity();
		indice2Repetido.setCodigoAC("0001");
		indice2Repetido.setGrupoAC(grupoAC1.getCodigo());
		indiceDao.save(indice2Repetido);

		Indice indice3 = IndiceFactory.getEntity();
		indice3.setCodigoAC("0002");
		indice3.setGrupoAC(grupoAC1.getCodigo());
		indiceDao.save(indice3);
		
		Indice indice4 = IndiceFactory.getEntity();
		indice4.setCodigoAC("0001");
		indice4.setGrupoAC(grupoAC2.getCodigo());
		indiceDao.save(indice4);
		
		Indice indice5 = IndiceFactory.getEntity();
		indice5.setCodigoAC("");
		indice5.setGrupoAC(grupoAC2.getCodigo());
		indiceDao.save(indice5);
		
		assertEquals(1, indiceDao.findSemCodigoAC(ente).size());
	}
	
	public void testFindSemCodigoACVazio()
	{
		GrupoAC grupoAC1 = GrupoACFactory.getEntity();
		grupoAC1.setCodigo("998");
		grupoACDao.save(grupoAC1);
		
		GrupoAC grupoAC2 = GrupoACFactory.getEntity();
		grupoAC2.setCodigo("999");
		grupoACDao.save(grupoAC2);
		
		Empresa ente = EmpresaFactory.getEmpresa();
		ente.setCodigoAC("000225");
		ente.setGrupoAC(grupoAC1.getCodigo());
		empresaDao.save(ente);
		
		Indice indice = IndiceFactory.getEntity();
		indice.setCodigoAC("0001");
		indice.setGrupoAC(grupoAC1.getCodigo());
		indiceDao.save(indice);
		
		Indice indice2Repetido = IndiceFactory.getEntity();
		indice2Repetido.setCodigoAC("0001");
		indice2Repetido.setGrupoAC(grupoAC1.getCodigo());
		indiceDao.save(indice2Repetido);
		
		Indice indice3 = IndiceFactory.getEntity();
		indice3.setCodigoAC("0002");
		indice3.setGrupoAC(grupoAC1.getCodigo());
		indiceDao.save(indice3);
		
		Indice indice4 = IndiceFactory.getEntity();
		indice4.setCodigoAC("0001");
		indice4.setGrupoAC(grupoAC2.getCodigo());
		indiceDao.save(indice4);
		
		Indice indice5 = IndiceFactory.getEntity();
		indice5.setCodigoAC("0001");
		indice5.setGrupoAC(grupoAC2.getCodigo());
		indiceDao.save(indice5);
		
		assertEquals(0, indiceDao.findSemCodigoAC(ente).size());
	}

	public void testFindCodigoACDuplicadoVazio()
	{
		GrupoAC grupoAC1 = GrupoACFactory.getEntity();
		grupoAC1.setCodigo("998");
		grupoACDao.save(grupoAC1);
		
		GrupoAC grupoAC2 = GrupoACFactory.getEntity();
		grupoAC2.setCodigo("999");
		grupoACDao.save(grupoAC2);
		
		Empresa ente = EmpresaFactory.getEmpresa();
		ente.setCodigoAC("000225");
		ente.setGrupoAC(grupoAC1.getCodigo());
		empresaDao.save(ente);
		
		Indice indice = IndiceFactory.getEntity();
		indice.setCodigoAC("0001");
		indice.setGrupoAC(grupoAC1.getCodigo());
		indiceDao.save(indice);
		
		Indice indice2Repetido = IndiceFactory.getEntity();
		indice2Repetido.setCodigoAC("0001");
		indice2Repetido.setGrupoAC(grupoAC1.getCodigo());
		indiceDao.save(indice2Repetido);
		
		Indice indice3 = IndiceFactory.getEntity();
		indice3.setCodigoAC("0002");
		indice3.setGrupoAC(grupoAC1.getCodigo());
		indiceDao.save(indice3);
		
		Indice indice4 = IndiceFactory.getEntity();
		indice4.setCodigoAC("0005");
		indice4.setGrupoAC(grupoAC2.getCodigo());
		indiceDao.save(indice4);
		
		Indice indice5 = IndiceFactory.getEntity();
		indice5.setCodigoAC("0005");
		indice5.setGrupoAC(grupoAC2.getCodigo());
		indiceDao.save(indice5);
		
		assertEquals("0001", indiceDao.findCodigoACDuplicado(ente));
	}
	
	public void setIndiceDao(IndiceDao indiceDao)
	{
		this.indiceDao = indiceDao;
	}

	public void setIndiceHistoricoDao(IndiceHistoricoDao indiceHistoricoDao)
	{
		this.indiceHistoricoDao = indiceHistoricoDao;
	}

	public void setGrupoACDao(GrupoACDao grupoACDao) {
		this.grupoACDao = grupoACDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public void setCargoDao(CargoDao cargoDao) {
		this.cargoDao = cargoDao;
	}

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao) {
		this.faixaSalarialDao = faixaSalarialDao;
	}

	public void setFaixaSalarialHistoricoDao(FaixaSalarialHistoricoDao faixaSalarialHistoricoDao) {
		this.faixaSalarialHistoricoDao = faixaSalarialHistoricoDao;
	}
}