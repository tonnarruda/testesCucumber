package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.dao.cargosalario.CargoDao;
import com.fortes.rh.dao.cargosalario.FaixaSalarialDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.dao.DaoHibernateAnnotationTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.util.DateUtil;

public class ColaboradorTurmaDaoHibernateTest_JUnit4 extends DaoHibernateAnnotationTest
{
    @Autowired
	private ColaboradorTurmaDao colaboradorTurmaDao;
    @Autowired
    private ColaboradorDao colaboradorDao;
    @Autowired
    private HistoricoColaboradorDao historicoColaboradorDao;
    @Autowired
    private FaixaSalarialDao faixaSalarialDao;
    @Autowired
    private CargoDao cargoDao;

    public ColaboradorTurma getEntity()
    {
        ColaboradorTurma colaboradorTurma = new ColaboradorTurma();
        colaboradorTurma.setOrigemDnt(true);
        colaboradorTurma.setId(null);

        return colaboradorTurma;
    }

    @Test
    public void testFindByColaboradoresForMatrizHistoricoTreinamento(){
    	Colaborador colaborador = ColaboradorFactory.getEntity();
    	colaborador.setNome("Fulano");
    	colaboradorDao.save(colaborador);

    	Cargo cargo = CargoFactory.getEntity();
    	cargoDao.save(cargo);
    	
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
    	faixaSalarial.setNome("FX colaboradorTurma");
    	faixaSalarial.setCargo(cargo);
    	faixaSalarialDao.save(faixaSalarial);
    	
    	HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
    	historicoColaborador.setData(DateUtil.criarDataMesAno(1, 1, 2017));
    	historicoColaborador.setColaborador(colaborador);
    	historicoColaborador.setStatus(StatusRetornoAC.CONFIRMADO);
    	historicoColaborador.setFaixaSalarial(faixaSalarial);
    	historicoColaboradorDao.save(historicoColaborador);
    	
    	Collection<ColaboradorTurma> colaboradoresTurmas = colaboradorTurmaDao.findByColaboradoresForMatrizHistoricoTreinamento(new Long[]{colaborador.getId()});
    	
    	assertEquals(1, colaboradoresTurmas.size());
    	assertEquals(colaborador.getNome(), ((ColaboradorTurma) colaboradoresTurmas.toArray()[0]).getColaborador().getNome());
    	assertEquals(faixaSalarial.getNome(), ((ColaboradorTurma) colaboradoresTurmas.toArray()[0]).getColaborador().getFaixaSalarial().getNome());
    }

}