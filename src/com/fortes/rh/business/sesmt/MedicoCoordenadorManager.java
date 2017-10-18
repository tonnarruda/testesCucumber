package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.model.type.File;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.MedicoCoordenador;

public interface MedicoCoordenadorManager extends GenericManager<MedicoCoordenador>
{
	MedicoCoordenador findByDataEmpresa(Long empresaId, Date data);
	MedicoCoordenador findByIdProjection(Long medicoCoordenadorId);
	Collection<MedicoCoordenador> findByEmpresa(Long empresaId);
	File getAssinaturaDigital(Long id) throws Exception;
	Collection<MedicoCoordenador> findResponsaveisPorEstabelecimento(Date data, Colaborador colaborador);
	void insere(MedicoCoordenador medicoCoordenador, Long[] estabelecimentosIds) throws Exception;
	void atualiza(MedicoCoordenador medicoCoordenador, Long[] estabelecimentosIds, boolean manterAssinatura) throws Exception;
}