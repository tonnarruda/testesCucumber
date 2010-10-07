package com.fortes.rh.test.util.mockObjects;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import com.fortes.rh.model.dicionario.OpcaoImportacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;

public class MockImportacaoCSVUtil {
	
	public static Collection<ColaboradorAfastamento> afastamentos = null;
	public static Collection<Colaborador> colaboradores = null;
	
	public void importarCSV(File file, OpcaoImportacao opcao) throws IOException {
		
	}
	
	public Collection<ColaboradorAfastamento> getAfastamentos() {
		return afastamentos;
	}
	
	public Collection<Colaborador> getColaboradores() {
		return colaboradores;
	}
}
