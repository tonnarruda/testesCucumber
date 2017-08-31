package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.relatorio.PppRelatorio;
public interface PppRelatorioManager
{
	Collection<PppRelatorio> populaRelatorioPpp(Colaborador colaborador, Empresa empresa, Date data, String nit, String cnae, String responsavel, String observacoes, String[] respostas, Long empresaId) throws Exception;
}