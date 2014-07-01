package com.fortes.rh.test.util.mockObjects;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import com.fortes.rh.model.dicionario.OpcaoImportacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.model.sesmt.Epi;

public class MockImportacaoCSVUtil {
	
	public static Collection<ColaboradorAfastamento> afastamentos = null;
	public static Collection<Colaborador> colaboradores = null;
	public static Collection<Epi> epis = null;
	
	public void importarCSV(File file, OpcaoImportacao opcao) throws IOException {
		
	}
	
	public void importarCSV(File file, OpcaoImportacao opcao, boolean pularTitulos) throws IOException {
		
	}
	
	public Collection<ColaboradorAfastamento> getAfastamentos() {
		return afastamentos;
	}
	
	public Collection<Colaborador> getColaboradores() {
		return colaboradores;
	}
	
	public Collection<Epi> getEpis() {
		return epis;
	}
}
