package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.dao.geral.CidDao;
import com.fortes.rh.model.geral.Cid;
import com.fortes.rh.test.dao.DaoHibernateAnnotationTest;

/**
 * Os códigos CBOs são inseridos na instalação do FortesRH desde a versão 1.1.37.27.<br/>
 * Veja o arquivo update.sql para mais detalhes.
 */
public class CidDaoHibernateTest extends DaoHibernateAnnotationTest
{
	@Autowired
	private CidDao cidDao;

	@Test
	public void testBuscandoCidsEDescricao() {
		
		Collection<Cid> cids = cidDao.buscaCids("A01", "Febre ti");
		
		Assert.assertEquals("registros encontrados", 1, cids.size());
	}
	
	@Test
	public void testBuscandoCidsEDescricaoComAcento() {
		
		Collection<Cid> cids = cidDao.buscaCids("", "Paratifóide A");
		
		Assert.assertEquals(1, cids.size());
	}
	
	@Test
	public void testBuscandoDescricaoPorCodigo() {
		
		String descricao = cidDao.findDescricaoByCodigo("A010");
		
		Assert.assertEquals("descricao", "Febre tifóide", descricao);
	}

}
