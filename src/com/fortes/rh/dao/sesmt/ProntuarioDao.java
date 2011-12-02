package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Prontuario;

public interface ProntuarioDao extends GenericDao<Prontuario> 
{
	Collection<Prontuario> findByColaborador(Colaborador colaborador);
	Integer findQtdByEmpresa(Long empresaId, Date dataIni, Date dataFim);
}