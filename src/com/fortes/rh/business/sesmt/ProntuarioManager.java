package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.relatorio.ProntuarioRelatorio;
import com.fortes.rh.model.sesmt.Prontuario;

public interface ProntuarioManager extends GenericManager<Prontuario>
{
	Collection<Prontuario> findByColaborador(Colaborador colaborador);

	ProntuarioRelatorio findRelatorioProntuario(Empresa empresa, Colaborador colaborador) throws ColecaoVaziaException;

	Integer findQtdByEmpresa(Long empresaId, Date dataIni, Date dataFim);
}