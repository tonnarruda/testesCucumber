package com.fortes.rh.test.dao.hibernate.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.IndiceDao;
import com.fortes.rh.dao.cargosalario.ReajusteIndiceDao;
import com.fortes.rh.dao.cargosalario.TabelaReajusteColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.ReajusteIndice;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;

public class ReajusteIndiceDaoHibernateTest extends GenericDaoHibernateTest<ReajusteIndice>
{
	private IndiceDao indiceDao;
	private EmpresaDao empresaDao;
	private ReajusteIndiceDao reajusteIndiceDao;
    private TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao;
	
    public ReajusteIndice getEntity()
    {
    	TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity();
    	tabelaReajusteColaboradorDao.save(tabelaReajusteColaborador);
    	
    	Indice indice = IndiceFactory.getEntity();
    	indiceDao.save(indice);
    	
    	ReajusteIndice reajusteIndice = new ReajusteIndice();

    	reajusteIndice.setId(null);
    	reajusteIndice.setTabelaReajusteColaborador(tabelaReajusteColaborador);
    	reajusteIndice.setIndice(indice);
    	reajusteIndice.setValorAtual(1000.00);
    	reajusteIndice.setValorProposto(1200.00);

        return reajusteIndice;
    }
    
    public void testFindPendentes()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setAcIntegra(false);
    	empresaDao.save(empresa);
    	
    	Indice indice1 = IndiceFactory.getEntity();
    	indice1.setNome("indice1");
    	indiceDao.save(indice1);
    	
    	Indice indice2 = IndiceFactory.getEntity();
    	indice2.setNome("indice2");
    	indiceDao.save(indice2);
    	
    	TabelaReajusteColaborador tabela1 = TabelaReajusteColaboradorFactory.getEntity();
    	tabela1.setAprovada(true);
    	tabelaReajusteColaboradorDao.save(tabela1);
    
    	ReajusteIndice reajuste1 = getEntity();
    	reajuste1.setTabelaReajusteColaborador(tabela1);
    	reajuste1.setIndice(indice1);
    	reajusteIndiceDao.save(reajuste1);
    	
    	TabelaReajusteColaborador tabela2 = TabelaReajusteColaboradorFactory.getEntity();
    	tabela2.setAprovada(false);
    	tabelaReajusteColaboradorDao.save(tabela2);    
    	
    	ReajusteIndice reajuste2 = getEntity();
    	reajuste2.setTabelaReajusteColaborador(tabela2);
    	reajuste2.setIndice(indice2);
    	reajusteIndiceDao.save(reajuste2);

    	assertTrue(reajusteIndiceDao.findPendentes(empresa).size() >= 1);
    }
    
    public void testFindByTabelaReajusteColaboradorId()
    {
    	Indice indice1 = IndiceFactory.getEntity();
    	indiceDao.save(indice1);
    	
    	Indice indice2 = IndiceFactory.getEntity();
    	indiceDao.save(indice2);
    	
    	TabelaReajusteColaborador tabela1 = TabelaReajusteColaboradorFactory.getEntity();
    	tabela1.setAprovada(true);
    	tabelaReajusteColaboradorDao.save(tabela1);
    
    	ReajusteIndice reajuste1 = getEntity();
    	reajuste1.setTabelaReajusteColaborador(tabela1);
    	reajuste1.setIndice(indice1);
    	reajusteIndiceDao.save(reajuste1);
    	
    	TabelaReajusteColaborador tabela2 = TabelaReajusteColaboradorFactory.getEntity();
    	tabela2.setAprovada(false);
    	tabelaReajusteColaboradorDao.save(tabela2);    
    	
    	ReajusteIndice reajuste2 = getEntity();
    	reajuste2.setTabelaReajusteColaborador(tabela2);
    	reajuste2.setIndice(indice2);
    	reajusteIndiceDao.save(reajuste2);

    	assertEquals(reajuste1.getId(), ((ReajusteIndice)reajusteIndiceDao.findByTabelaReajusteColaboradorId(tabela1.getId()).toArray()[0]).getId());
    	assertEquals(reajuste2.getId(), ((ReajusteIndice)reajusteIndiceDao.findByTabelaReajusteColaboradorId(tabela2.getId()).toArray()[0]).getId());
    }
    
    public void testFindByIdProjection()
    {
    	Indice indice = IndiceFactory.getEntity();
    	indice.setNome("salario minimo");
    	indiceDao.save(indice);
    	
    	TabelaReajusteColaborador tabela = TabelaReajusteColaboradorFactory.getEntity();
    	tabelaReajusteColaboradorDao.save(tabela);
    	
    	ReajusteIndice reajuste = getEntity();
    	reajuste.setTabelaReajusteColaborador(tabela);
    	reajuste.setIndice(indice);
    	reajuste.setValorProposto(10.25);
    	reajusteIndiceDao.save(reajuste);
    	
    	ReajusteIndice retorno = reajusteIndiceDao.findByIdProjection(reajuste.getId());
    	
    	assertEquals(reajuste.getValorProposto(), retorno.getValorProposto());
    	assertEquals(tabela.getId(), retorno.getTabelaReajusteColaborador().getId());
    	assertEquals(indice.getId(), retorno.getIndice().getId());
    	assertEquals(indice.getNome(), retorno.getIndice().getNome());
    }
    
    public void testUpdateValorProposto()
    {
    	Indice indice = IndiceFactory.getEntity();
    	indice.setNome("salario minimo");
    	indiceDao.save(indice);
    	
    	TabelaReajusteColaborador tabela = TabelaReajusteColaboradorFactory.getEntity();
    	tabelaReajusteColaboradorDao.save(tabela);
    	
    	ReajusteIndice reajuste = getEntity();
    	reajuste.setTabelaReajusteColaborador(tabela);
    	reajuste.setIndice(indice);
    	reajuste.setValorAtual(10.0);
    	reajuste.setValorProposto(11.00);
    	reajusteIndiceDao.save(reajuste);
    	
    	reajusteIndiceDao.updateValorProposto(reajuste.getId(), 12.00);
    	
    	ReajusteIndice retorno = reajusteIndiceDao.findByIdProjection(reajuste.getId());
    	
    	assertEquals(12.00, retorno.getValorProposto());
    }
    
    public void testFindByIndiceEstabelecimento()
    {
    	Indice indice = IndiceFactory.getEntity();
    	indice.setNome("salario minimo");
    	indiceDao.save(indice);
    	
    	TabelaReajusteColaborador tabela = TabelaReajusteColaboradorFactory.getEntity();
    	tabelaReajusteColaboradorDao.save(tabela);
    	
    	ReajusteIndice reajuste = getEntity();
    	reajuste.setTabelaReajusteColaborador(tabela);
    	reajuste.setIndice(indice);
    	reajuste.setValorAtual(10.0);
    	reajuste.setValorProposto(11.00);
    	reajusteIndiceDao.save(reajuste);
    	
    	Collection<Long> indiceIds = new ArrayList<Long>();
    	indiceIds.add(indice.getId());
    	
    	assertEquals(1, reajusteIndiceDao.findByTabelaReajusteIndice(tabela.getId(), indiceIds).size());
    }
    
	public void setTabelaReajusteColaboradorDao(TabelaReajusteColaboradorDao tabelaReajusteColaboradorDao) {
		this.tabelaReajusteColaboradorDao = tabelaReajusteColaboradorDao;
	}

	public void setReajusteIndiceDao(ReajusteIndiceDao reajusteIndiceDao) {
		this.reajusteIndiceDao = reajusteIndiceDao;
	}

	public void setIndiceDao(IndiceDao indiceDao) {
		this.indiceDao = indiceDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	@Override
	public GenericDao<ReajusteIndice> getGenericDao() {
		return reajusteIndiceDao;
	}
}