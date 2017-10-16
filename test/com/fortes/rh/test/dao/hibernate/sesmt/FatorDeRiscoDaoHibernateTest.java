package com.fortes.rh.test.dao.hibernate.sesmt;

import static org.junit.Assert.*;
import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.FatorDeRiscoDao;
import com.fortes.rh.model.dicionario.GrupoRiscoESocial;
import com.fortes.rh.model.sesmt.FatorDeRisco;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;

public class FatorDeRiscoDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<FatorDeRisco> 
{
	
	@Autowired private FatorDeRiscoDao fatorDeRiscoDao;

	public GenericDao<FatorDeRisco> getGenericDao() {
		return fatorDeRiscoDao;
	}

	public FatorDeRisco getEntity() {
		return new FatorDeRisco();
	}
	
	@Test
	public void testFindByGrupoRiscoESocialFisico(){
		Collection<FatorDeRisco> fatoresDeRisco = fatorDeRiscoDao.findByGrupoRiscoESocial(GrupoRiscoESocial.FISICO);
		assertTrue(fatoresDeRisco.size() == 20);
	}
	
	@Test
	public void testFindByGrupoRiscoESocialQuimico(){
		Collection<FatorDeRisco> fatoresDeRisco = fatorDeRiscoDao.findByGrupoRiscoESocial(GrupoRiscoESocial.QUIMICO);
		assertTrue(fatoresDeRisco.size() == 842);
	}
	
	@Test
	public void testFindByGrupoRiscoESocialBiologico(){
		Collection<FatorDeRisco> fatoresDeRisco =  fatorDeRiscoDao.findByGrupoRiscoESocial(GrupoRiscoESocial.BIOLOGICO);
		assertTrue(fatoresDeRisco.size() == 15);
	}
	
	@Test
	public void testFindByGrupoRiscoESocialErgonomicoBiomecanico(){
		Collection<FatorDeRisco> fatoresDeRisco = fatorDeRiscoDao.findByGrupoRiscoESocial(GrupoRiscoESocial.ERGONOMICO_BIOMECANICO);
		assertTrue(fatoresDeRisco.size() == 10);
	}
	
	@Test
	public void testFindByGrupoRiscoESocialErgonomico(){
		Collection<FatorDeRisco> fatoresDeRisco = fatorDeRiscoDao.findByGrupoRiscoESocial(GrupoRiscoESocial.ERGONOMICO_MOBILIARIO_E_EQUIPAMENTO);
		assertTrue(fatoresDeRisco.size() == 3);
	}
	
	@Test
	public void testFindByGrupoRiscoESocialErgonomicoOrganizacional(){
		Collection<FatorDeRisco> fatoresDeRisco = fatorDeRiscoDao.findByGrupoRiscoESocial(GrupoRiscoESocial.ERGONOMICO_ORGANIZACIONAL);
		assertTrue(fatoresDeRisco.size() == 7);
	}
	
	@Test
	public void testFindByGrupoRiscoESocialErgonomicoPsicossocialCognitivo(){
		Collection<FatorDeRisco> fatoresDeRisco = fatorDeRiscoDao.findByGrupoRiscoESocial(GrupoRiscoESocial.ERGONOMICO_PSICOSSOCIAL_COGNITIVO);
		assertTrue(fatoresDeRisco.size() == 5);
	}
	
	@Test
	public void testFindByGrupoRiscoESocialAcidente(){
		Collection<FatorDeRisco> fatoresDeRisco = fatorDeRiscoDao.findByGrupoRiscoESocial(GrupoRiscoESocial.ACIDENTE);
		assertTrue(fatoresDeRisco.size() == 18);
	}
	
	@Test
	public void testFindByGrupoRiscoESocialPericuloso(){
		Collection<FatorDeRisco> fatoresDeRisco = fatorDeRiscoDao.findByGrupoRiscoESocial(GrupoRiscoESocial.PERICULOSO);
		assertTrue(fatoresDeRisco.size() == 7);
	}
	
	@Test
	public void testFindByGrupoRiscoESocialPenoso(){
		Collection<FatorDeRisco> fatoresDeRisco = fatorDeRiscoDao.findByGrupoRiscoESocial(GrupoRiscoESocial.PENOSO);
			assertTrue(fatoresDeRisco.size() == 4);
	}

	@Test
	public void testFindByGrupoRiscoESocialAssociacaoDeFatoresDeRisco(){
		Collection<FatorDeRisco> fatoresDeRisco = fatorDeRiscoDao.findByGrupoRiscoESocial(GrupoRiscoESocial.ASSOCIACAO_DE_FATORES_DE_RISCO);
		assertTrue(fatoresDeRisco.size() == 3);
	}

	@Test
	public void testFindByGrupoRiscoESocialAusenciaFatorDeRisco(){
		Collection<FatorDeRisco> fatoresDeRisco = fatorDeRiscoDao.findByGrupoRiscoESocial(GrupoRiscoESocial.AUSENCIA_FATOR_DE_RISCO);
		assertTrue(fatoresDeRisco.size() == 1);
	}
	
}