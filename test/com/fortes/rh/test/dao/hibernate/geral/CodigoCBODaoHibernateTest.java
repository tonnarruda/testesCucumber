package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;

import com.fortes.rh.dao.geral.CodigoCBODao;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.test.dao.BaseDaoHibernateTest;

/**
 * Os códigos CBOs são inseridos na instalação do FortesRH desde a versão 1.1.37.27.<br/>
 * Veja o arquivo update.sql para mais detalhes.
 */
public class CodigoCBODaoHibernateTest extends BaseDaoHibernateTest
{
	private CodigoCBODao codigoCBODao;

	public void testBuscandoCBOsPorCodigoEDescricao() {
		
		Collection<AutoCompleteVO> cbos = codigoCBODao.buscaCodigosCBO("Programador");
		
		assertEquals("registros encontrados", 5, cbos.size());
	}
	
	public void testFindDescricaoByCodigo() {
		
		String descricao = codigoCBODao.findDescricaoByCodigo("519805");
		
		assertEquals("descricao do codigo", "Profissional do sexo", descricao);
	}
	
	public void setCodigoCBODao(CodigoCBODao codigoCBODao){
		this.codigoCBODao = codigoCBODao;
	}
}
