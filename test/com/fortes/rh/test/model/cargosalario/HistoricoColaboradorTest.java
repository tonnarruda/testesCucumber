package com.fortes.rh.test.model.cargosalario;

import junit.framework.TestCase;

import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceHistoricoFactory;


public class HistoricoColaboradorTest extends TestCase
{
    public void testGetValor()
    {
        HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
        historicoColaborador.setTipoSalario(TipoAplicacaoIndice.VALOR);
        historicoColaborador.setSalario(5000.0);

        assertEquals(5000.0, historicoColaborador.getSalarioCalculado());
    }

    public void testGetValorIndice()
    {
    	IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);
    	indiceHistorico.setValor(100.0);

    	Indice indice = IndiceFactory.getEntity();
    	indice.setIndiceHistoricoAtual(indiceHistorico);

    	HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
    	historicoColaborador.setTipoSalario(TipoAplicacaoIndice.INDICE);
    	historicoColaborador.setQuantidadeIndice(2.0);
        historicoColaborador.setIndice(indice);

        assertEquals(200.0, historicoColaborador.getSalarioCalculado());
    }

    public void testGetValorCargoValor()
    {
    	FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
    	faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
    	faixaSalarialHistorico.setValor(300.0);

    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
    	faixaSalarial.setFaixaSalarialHistoricoAtual(faixaSalarialHistorico);

    	HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
    	historicoColaborador.setTipoSalario(TipoAplicacaoIndice.CARGO);
    	historicoColaborador.setFaixaSalarial(faixaSalarial);

    	assertEquals(300.0, historicoColaborador.getSalarioCalculado());
    }

    public void testGetValorCargoIndice()
    {
    	IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);
    	indiceHistorico.setValor(200.0);

    	Indice indice = IndiceFactory.getEntity(1L);
    	indice.setIndiceHistoricoAtual(indiceHistorico);

    	FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
    	faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.INDICE);
    	faixaSalarialHistorico.setQuantidade(3.0);
    	faixaSalarialHistorico.setIndice(indice);

    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
    	faixaSalarial.setFaixaSalarialHistoricoAtual(faixaSalarialHistorico);

    	HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
    	historicoColaborador.setTipoSalario(TipoAplicacaoIndice.CARGO);
    	historicoColaborador.setFaixaSalarial(faixaSalarial);

    	assertEquals(600.0, historicoColaborador.getSalarioCalculado());
    }
}
