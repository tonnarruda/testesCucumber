package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;

import com.fortes.rh.dao.geral.CodigoCBODao;
import com.fortes.rh.model.geral.CodigoCBO;
import com.fortes.rh.test.dao.BaseDaoHibernateTest;

public class CodigoCBODaoHibernateTest extends BaseDaoHibernateTest
{
	private CodigoCBODao codigoCBODao;

	public void testBuscandoCBOsPorCodigoEDescricao() {
		
		Collection<CodigoCBO> cbos = codigoCBODao.buscaCodigosCBO("7", "Acabador");
		
		assertEquals("registros encontrados", 3, cbos.size());
	}
	
	public void testBuscandoDescricaoPorCodigo() {
		
		String descricao = codigoCBODao.findDescricaoByCodigo("764305");
		
		assertEquals("descricao", "Acabador de calçados", descricao);
	}
	
	public void setCodigoCBODao(CodigoCBODao codigoCBODao){
		this.codigoCBODao = codigoCBODao;
	}
}
