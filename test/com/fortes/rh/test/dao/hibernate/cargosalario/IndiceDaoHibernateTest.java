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

	public void testFindSemCodigoAC() {
		
		Indice indice1 = IndiceFactory.getEntity();
		indice1.setCodigoAC("1");
		indiceDao.save(indice1);
		
		Indice indice2 = IndiceFactory.getEntity();
		indice2.setCodigoAC("");
		indiceDao.save(indice2);
		
		Indice indice3 = IndiceFactory.getEntity();
		indice3.setCodigoAC(null);
		indiceDao.save(indice3);
		
		Collection<Indice> indices = indiceDao.findSemCodigoAC();
		
		assertFalse(indices.contains(indice1));		
		assertTrue(indices.contains(indice2));		
		assertTrue(indices.contains(indice3));		
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
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);

		Indice indice = IndiceFactory.getEntity();
		indice.setCodigoAC("123456");
		indiceDao.save(indice);
		
		Indice indice2 = IndiceFactory.getEntity();
		indice2.setCodigoAC("123457");
		indiceDao.save(indice2);
		
		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setIndice(indice);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistoricoDao.save(faixaSalarialHistorico);

		FaixaSalarialHistorico faixaSalarialHistorico2 = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico2.setIndice(indice2);
		faixaSalarialHistorico2.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistoricoDao.save(faixaSalarialHistorico2);
		
		assertEquals("", indiceDao.findCodigoACDuplicado(empresa.getId()));
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
		faixaSalarial.setCargo(cargo);
		faixaSalarialDao.save(faixaSalarial);
		
		Indice indice = IndiceFactory.getEntity();
		indice.setCodigoAC("123456");
		indiceDao.save(indice);
		
		Indice indice2 = IndiceFactory.getEntity();
		indice2.setCodigoAC("123456");
		indiceDao.save(indice2);

		Indice indice3 = IndiceFactory.getEntity();
		indice3.setCodigoAC("123457");
		indiceDao.save(indice3);
		
		Indice indice4 = IndiceFactory.getEntity();
		indice4.setCodigoAC("123457");
		indiceDao.save(indice4);
		
		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setIndice(indice);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistoricoDao.save(faixaSalarialHistorico);
		
		FaixaSalarialHistorico faixaSalarialHistorico2 = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico2.setIndice(indice2);
		faixaSalarialHistorico2.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistoricoDao.save(faixaSalarialHistorico2);
		
		FaixaSalarialHistorico faixaSalarialHistorico3 = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico3.setIndice(indice3);
		faixaSalarialHistorico3.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistoricoDao.save(faixaSalarialHistorico3);
		
		FaixaSalarialHistorico faixaSalarialHistorico4 = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico4.setIndice(indice4);
		faixaSalarialHistorico4.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistoricoDao.save(faixaSalarialHistorico4);
		
		assertEquals("123456,123457", indiceDao.findCodigoACDuplicado(empresa.getId()));
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