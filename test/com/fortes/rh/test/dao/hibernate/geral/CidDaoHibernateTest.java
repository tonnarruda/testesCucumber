package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;

import com.fortes.rh.dao.geral.CidDao;
import com.fortes.rh.model.geral.Cid;
import com.fortes.rh.test.dao.BaseDaoHibernateTest;

/**
 * Os códigos CBOs são inseridos na instalação do FortesRH desde a versão 1.1.37.27.<br/>
 * Veja o arquivo update.sql para mais detalhes.
 */
public class CidDaoHibernateTest extends BaseDaoHibernateTest
{
	private CidDao cidDao;

	public void testBuscandoCidsEDescricao() {
		
		Collection<Cid> cids = cidDao.buscaCids("A05", "Intoxica");
		
		assertEquals("registros encontrados", 6, cids.size());
	}
	
	public void testBuscandoDescricaoPorCodigo() {
		
		String descricao = cidDao.findDescricaoByCodigo("A010");
		
		assertEquals("descricao", "Febre tifóide", descricao);
	}
	
	public void setCidDao(CidDao cidDao){
		this.cidDao = cidDao;
	}
}
