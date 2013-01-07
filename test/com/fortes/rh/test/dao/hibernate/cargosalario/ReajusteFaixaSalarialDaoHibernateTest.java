package com.fortes.rh.test.dao.hibernate.cargosalario;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.ReajusteFaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.TabelaReajusteColaboradorDao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.ReajusteFaixaSalarial;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;

public class ReajusteFaixaSalarialDaoHibernateTest extends GenericDaoHibernateTest<ReajusteFaixaSalarial>
{
    private ReajusteFaixaSalarialDao reajusteFaixaSalarialDao;
    private TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao;
    private FaixaSalarialDao faixaSalarialDao;

    public ReajusteFaixaSalarial getEntity()
    {
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
    	faixaSalarialDao.save(faixaSalarial);
    	
    	TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
    	tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);
    	
    	ReajusteFaixaSalarial reajusteFaixaSalarial = new ReajusteFaixaSalarial();

    	reajusteFaixaSalarial.setId(null);
    	reajusteFaixaSalarial.setTabelaReajusteColaborador(tabelaReajusteColaborador);
    	reajusteFaixaSalarial.setFaixaSalarial(faixaSalarial);
    	reajusteFaixaSalarial.setTipoAtual(TipoAplicacaoIndice.VALOR);
    	reajusteFaixaSalarial.setTipoProposto(TipoAplicacaoIndice.VALOR);
    	reajusteFaixaSalarial.setValorAtual(1000.00);
    	reajusteFaixaSalarial.setValorProposto(1200.00);

        return reajusteFaixaSalarial;
    }
    
    public void testFindByTabelaReajusteColaboradorId()
    {
    	FaixaSalarial faixa = FaixaSalarialFactory.getEntity();
    	faixaSalarialDao.save(faixa);
    	
    	TabelaReajusteColaborador tabela1 = TabelaReajusteColaboradorFactory.getEntity();
    	tabelaReajusteColaboradorDao.save(tabela1);
    
    	ReajusteFaixaSalarial reajuste1 = getEntity();
    	reajuste1.setTabelaReajusteColaborador(tabela1);
    	reajuste1.setFaixaSalarial(faixa);
    	reajusteFaixaSalarialDao.save(reajuste1);
    	
    	TabelaReajusteColaborador tabela2 = TabelaReajusteColaboradorFactory.getEntity();
    	tabelaReajusteColaboradorDao.save(tabela2);    
    	
    	ReajusteFaixaSalarial reajuste2 = getEntity();
    	reajuste2.setTabelaReajusteColaborador(tabela2);
    	reajuste2.setFaixaSalarial(faixa);
    	reajusteFaixaSalarialDao.save(reajuste2);

    	ReajusteFaixaSalarial reajuste3 = getEntity();
    	reajuste3.setTabelaReajusteColaborador(tabela2);
    	reajuste3.setFaixaSalarial(faixa);
    	reajusteFaixaSalarialDao.save(reajuste3);
    	
    	assertEquals(1, reajusteFaixaSalarialDao.findByTabelaReajusteColaboradorId(tabela1.getId()).size());
    	assertEquals(2, reajusteFaixaSalarialDao.findByTabelaReajusteColaboradorId(tabela2.getId()).size());
    }
    
    public void testUpdateValorProposto()
    {
    	ReajusteFaixaSalarial reajuste1 = getEntity();
    	reajuste1.setValorAtual(100.0);
    	reajuste1.setValorProposto(120.0);
    	reajusteFaixaSalarialDao.save(reajuste1);

    	ReajusteFaixaSalarial reajuste2 = getEntity();
    	reajuste2.setValorAtual(190.0);
    	reajuste2.setValorProposto(230.0);
    	reajusteFaixaSalarialDao.save(reajuste2);
    	
    	reajusteFaixaSalarialDao.updateValorProposto(reajuste1.getId(), 150.0);
    	
    	ReajusteFaixaSalarial retorno1 = reajusteFaixaSalarialDao.findEntidadeComAtributosSimplesById(reajuste1.getId());
    	ReajusteFaixaSalarial retorno2 = reajusteFaixaSalarialDao.findEntidadeComAtributosSimplesById(reajuste2.getId());
    	
    	assertEquals(150.0, retorno1.getValorProposto());
    	assertEquals(230.0, retorno2.getValorProposto());
    }

	public void setReajusteFaixaSalarialDao(ReajusteFaixaSalarialDao reajusteFaixaSalarialDao) 
	{
		this.reajusteFaixaSalarialDao = reajusteFaixaSalarialDao;
	}

	@Override
	public GenericDao<ReajusteFaixaSalarial> getGenericDao() 
	{
		return reajusteFaixaSalarialDao;
	}

	public void setTabelaReajusteColaboradorDao(
			TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao) {
		this.tabelaReajusteColaboradorDao = tabelaReajusteColaboradorDao;
	}

	public void setFaixaSalarialDao(FaixaSalarialDao faixaSalarialDao) {
		this.faixaSalarialDao = faixaSalarialDao;
	}
}