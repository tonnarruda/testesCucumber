package com.fortes.rh.web.dwr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.sesmt.ComissaoManager;
import com.fortes.rh.business.sesmt.ComissaoReuniaoManager;
import com.fortes.rh.business.sesmt.ComissaoReuniaoPresencaManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.ComissaoReuniao;
import com.fortes.rh.model.sesmt.ComissaoReuniaoPresenca;
import com.fortes.rh.util.DateUtil;

@Component
public class ComissaoReuniaoDWR
{
	@Autowired
	private ComissaoReuniaoManager comissaoReuniaoManager;
	@Autowired
	private ComissaoReuniaoPresencaManager comissaoReuniaoPresencaManager;
	@Autowired
	private ComissaoManager comissaoManager;

	public Map<String,Object> prepareDadosReuniao(Long id) throws Exception
	{
		Map<String,Object> retorno = new HashMap<String, Object>();
		ComissaoReuniao comissaoReuniao = null;
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
	
	public Object findColaboradoresByDataReuniao(String dataReuniaoStr, Long comissaoId)
	{
		List<Object> retorno = new ArrayList<Object>();
		Map<String, String> colaborador;
		List<Colaborador> colaboradores = comissaoManager.findColaboradoresByDataReuniao(DateUtil.montaDataByString(dataReuniaoStr), comissaoId);
		
		for (Colaborador col : colaboradores) 
		{
			colaborador = new LinkedHashMap<String, String>();
			colaborador.put("id", col.getId().toString());
			colaborador.put("nome", col.getNome());
			colaborador.put("presente", "false");
			colaborador.put("justificativaFalta",null);
			retorno.add(colaborador);
		}
		
		return retorno;
	}
	
	public Object findPresencaColaboradoresByReuniao(Long comissaoReuniaoId, String dataReuniao)
	{
		List<Object> retorno = new ArrayList<Object>();
		Map<String, String> colaborador;
		List<ComissaoReuniaoPresenca> presencas = comissaoReuniaoPresencaManager.findPresencaColaboradoresByReuniao(comissaoReuniaoId, DateUtil.montaDataByString(dataReuniao));
		
		for (ComissaoReuniaoPresenca presenca : presencas) 
		{
			colaborador = new LinkedHashMap<String, String>();
			colaborador.put("id", presenca.getColaborador().getId().toString());
			colaborador.put("nome", presenca.getColaborador().getNome());
			colaborador.put("presente", String.valueOf(presenca.getPresente()));
			colaborador.put("justificativaFalta", presenca.getJustificativaFalta());
			retorno.add(colaborador);
		}
		
		return retorno;
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
