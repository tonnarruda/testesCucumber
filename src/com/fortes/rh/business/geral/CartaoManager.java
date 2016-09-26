package com.fortes.rh.business.geral;

import java.util.Collection;

import javax.activation.DataSource;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Cartao;
import com.fortes.rh.model.geral.Colaborador;

public interface CartaoManager extends GenericManager<Cartao>
{
	public Cartao findByEmpresaIdAndTipo(Long empresaId, String tipoCartao);
	public Collection<Cartao> findByEmpresaId(Long empresaId);
	public Cartao saveImagemCartao(Cartao cartao, String local);
	public DataSource[] geraCartao (Cartao cartao, Colaborador colaborador);
}