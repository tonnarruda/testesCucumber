package com.fortes.rh.web.dwr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fortes.rh.business.sesmt.ComissaoManager;
import com.fortes.rh.business.sesmt.ComissaoReuniaoManager;
import com.fortes.rh.business.sesmt.ComissaoReuniaoPresencaManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ComissaoReuniao;
import com.fortes.rh.model.sesmt.ComissaoReuniaoPresenca;
import com.fortes.rh.util.DateUtil;

public class ComissaoReuniaoDWR
{
	private ComissaoReuniaoManager comissaoReuniaoManager;
	private ComissaoReuniaoPresencaManager comissaoReuniaoPresencaManager;
	private ComissaoManager comissaoManager;

	public Map<String,Object> prepareDadosReuniao(Long id) throws Exception
	{
		Map<String,Object> retorno = new HashMap<String, Object>();
		ComissaoReuniao comissaoReuniao = null;
		Collection<ComissaoReuniaoPresenca> presencas = comissaoReuniaoPresencaManager.findByReuniao(id);
		try
		{
			comissaoReuniao = comissaoReuniaoManager.findByIdProjection(id);

			if (comissaoReuniao == null)
				throw new Exception();

			retorno.put("comissaoReuniaoId", comissaoReuniao.getId());
			retorno.put("reuniaoData", comissaoReuniao.getDataFormatada());
			retorno.put("reuniaoDesc", comissaoReuniao.getDescricao());
			retorno.put("reuniaoHorario", comissaoReuniao.getHorario());
			retorno.put("reuniaoLocal", comissaoReuniao.getLocalizacao());
			retorno.put("reuniaoTipo", comissaoReuniao.getTipo());
			retorno.put("reuniaoAta", comissaoReuniao.getAta());
			retorno.put("reuniaoObsAnterior", comissaoReuniao.getObsReuniaoAnterior());

			for (ComissaoReuniaoPresenca comissaoReuniaoPresenca : presencas )
			{
				String colaboradorId = comissaoReuniaoPresenca.getColaborador().getId().toString();
				retorno.put("id" + colaboradorId, colaboradorId);
				retorno.put("check" + colaboradorId, comissaoReuniaoPresenca.getPresente());
				retorno.put("justificativaId" + colaboradorId, comissaoReuniaoPresenca.getJustificativaFalta());
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return retorno;
	}
	
	public boolean validaDataNoPeriodoDaComissao(String dataReuniaoStr, Long comissaoId)
	{
		Date dataReuniao = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try
		{
			dataReuniao = sdf.parse(dataReuniaoStr);
		} 
		catch (ParseException e) {
			return false;
		}
		
		return comissaoManager.validaData(dataReuniao, comissaoId);
	}
	
	public List<Colaborador> findColaboradoresByDataReuniao(String dataReuniaoStr, Long comissaoId)
	{
		return comissaoManager.findColaboradoresByDataReuniao(DateUtil.criarDataMesAno(dataReuniaoStr), comissaoId);
	}

	public void setComissaoReuniaoManager(ComissaoReuniaoManager comissaoReuniaoManager)
	{
		this.comissaoReuniaoManager = comissaoReuniaoManager;
	}

	public void setComissaoReuniaoPresencaManager(ComissaoReuniaoPresencaManager comissaoReuniaoPresencaManager)
	{
		this.comissaoReuniaoPresencaManager = comissaoReuniaoPresencaManager;
	}

	public void setComissaoManager(ComissaoManager comissaoManager) 
	{
		this.comissaoManager = comissaoManager;
	}
}
