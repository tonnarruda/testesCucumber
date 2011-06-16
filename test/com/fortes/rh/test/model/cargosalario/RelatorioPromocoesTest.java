package com.fortes.rh.test.model.cargosalario;

import junit.framework.TestCase;

import com.fortes.rh.model.cargosalario.relatorio.RelatorioPromocoes;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Estabelecimento;


public class RelatorioPromocoesTest extends TestCase
{

    public void testCompareTo()
    {
    	Estabelecimento estabelecimentoA = new Estabelecimento();
    	estabelecimentoA.setNome("AAA");
    	
    	AreaOrganizacional areaB = new AreaOrganizacional();
    	areaB.setNome("BB");

    	Estabelecimento estabelecimentoC = new Estabelecimento();
    	estabelecimentoC.setNome("CCC");
    	
    	AreaOrganizacional areaD = new AreaOrganizacional();
    	areaD.setNome("DD");
    	
    	RelatorioPromocoes relatorio1 = new RelatorioPromocoes();
    	relatorio1.setEstabelecimento(estabelecimentoA);
    	relatorio1.setArea(areaB);

    	RelatorioPromocoes relatorio2 = new RelatorioPromocoes();
    	relatorio2.setEstabelecimento(estabelecimentoC);
    	relatorio2.setArea(areaD);
    	
    	assertTrue(relatorio2.compareTo(relatorio1) > 0);
    }
}
