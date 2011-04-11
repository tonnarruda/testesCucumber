package com.fortes.rh.test.model.captacao;

import junit.framework.TestCase;

import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;

public class CandidatoSolicitacaoTest extends TestCase
{
    public void testGetNomeArea()
    {
    	CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
    	candidatoSolicitacao.setSolicitacao(null);
    	assertEquals("", candidatoSolicitacao.getNomeArea());

    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
    	areaOrganizacional.setNome("nome");
    	
    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
    	solicitacao.setAreaOrganizacional(areaOrganizacional);
    	
    	candidatoSolicitacao.setSolicitacao(solicitacao);
    	assertEquals("nome", candidatoSolicitacao.getNomeArea());
    	
    }

    public void testGetNomeCargo()
    {
    	CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
    	candidatoSolicitacao.setSolicitacao(null);
    	assertEquals("", candidatoSolicitacao.getNomeCargo());
    	
    	Cargo cargo = CargoFactory.getEntity();
    	cargo.setNome("cargo");
    	FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
    	faixaSalarial.setCargo(cargo);
    	
    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
    	solicitacao.setFaixaSalarial(faixaSalarial);
    	
    	candidatoSolicitacao.setSolicitacao(solicitacao);
    	assertEquals("cargo", candidatoSolicitacao.getNomeCargo());
    }
    public void testGetNomeSolicitante()
    {
    	CandidatoSolicitacao candidatoSolicitacao = CandidatoSolicitacaoFactory.getEntity();
    	candidatoSolicitacao.setSolicitacao(null);
    	assertEquals("", candidatoSolicitacao.getNomeSolicitante());
    	
    	Usuario solicitante = UsuarioFactory.getEntity();
    	solicitante.setNome("Joao");
    	
    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
    	solicitacao.setSolicitante(solicitante);
    	
    	candidatoSolicitacao.setSolicitacao(solicitacao);
    	assertEquals("Joao", candidatoSolicitacao.getNomeSolicitante());
    	
    }

}
