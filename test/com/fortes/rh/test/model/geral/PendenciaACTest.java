package com.fortes.rh.test.model.geral;

import junit.framework.TestCase;

import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.PendenciaAC;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.util.DateUtil;

public class PendenciaACTest extends TestCase
{
    public void testMontarDetalhes()
    {
    	Colaborador colaborador = ColaboradorFactory.getEntity(100L);
    	colaborador.setNomeComercial("Zé da Velha");
    	colaborador.setDataAdmissao(DateUtil.criarDataMesAno(20,2,2010));
    	HistoricoColaborador historicoColaborador  = HistoricoColaboradorFactory.getEntity(1000L);
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
    	Cargo cargo = CargoFactory.getEntity(1L);
    	cargo.setNome("Músico");
    	cargo.setNomeMercado("Músico");
		faixaSalarial.setCargo(cargo);
		
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		
    	PendenciaAC pendenciaAC = new PendenciaAC(historicoColaborador);
    	pendenciaAC.montarDetalhes();
    	
    	String esperado = "Cadastro do colaborador Zé da Velha. Admissão: 20/02/2010. Cargo: Músico";
    	
    	assertEquals(esperado,pendenciaAC.getDetalhes());
    }

   

}
