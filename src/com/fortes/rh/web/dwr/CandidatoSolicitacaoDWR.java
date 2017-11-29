package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.TipoPessoa;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.DateUtil;

public class CandidatoSolicitacaoDWR {

	private ColaboradorManager colaboradorManager;
	private FaixaSalarialManager faixaSalarialManager;
	

	public Collection<FaixaSalarial> getFaixas(String tipoPessoaChave,String dataSolicitacaoExame,Long colaboradorOuCandidatoId,Long empresaId ){
		Date data = DateUtil.criarDataDiaMesAno(dataSolicitacaoExame);
		TipoPessoa tipoPessoa = TipoPessoa.valueOf(tipoPessoaChave);
		Collection<FaixaSalarial> faixas = new ArrayList<FaixaSalarial>();
		
		if(tipoPessoa.equals(TipoPessoa.COLABORADOR)){
			Colaborador colaborador;
			if(data!=null)
				colaborador = colaboradorManager.findByData(colaboradorOuCandidatoId, data);
			else
				colaborador = colaboradorManager.findByData(colaboradorOuCandidatoId, new Date());
			
			faixas.add(colaborador.getFaixaSalarial());
		}
		else{
			if(empresaId!=null)
				faixas = faixaSalarialManager.findFaixas(new Empresa(empresaId), Cargo.ATIVO, null);
		}		
		return faixas;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager) {
		this.faixaSalarialManager = faixaSalarialManager;
	}
}
