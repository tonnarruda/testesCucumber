package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.sesmt.Pcmat;

public interface PcmatManager extends GenericManager<Pcmat>
{
	Collection<Pcmat> findByObra(Long obraId);
	void validaDataMaiorQueUltimoHistorico(Long pcmatId, Long obraId, Date aPartirDe) throws FortesException;
	public Pcmat findUltimoHistorico(Long pcmatId, Long obraId);
	void clonar(Long pcmatOrigemId, Date aPartirDe, Long obraId);
	XWPFDocument gerarDocumento(Long pcmatId) throws Exception;
}
