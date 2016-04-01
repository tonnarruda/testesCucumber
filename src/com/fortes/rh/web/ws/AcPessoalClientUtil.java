package com.fortes.rh.web.ws;

import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TItemTabelaEmpregados;
import com.fortes.rh.util.DateUtil;

public class AcPessoalClientUtil {

	public static void montaParametrosTItemTabelaEmpregado(Empresa empresa,HistoricoColaborador historico, TItemTabelaEmpregados item){
		item.setCargo(historico.getFaixaSalarial().getCodigoAC());
		item.setCodigo(historico.getColaborador().getCodigoAC());
		item.setData(DateUtil.formataDiaMesAno(historico.getData()));
		item.setEmpresa(empresa.getCodigoAC());
		item.setLotacao(historico.getAreaOrganizacional().getCodigoAC());
		item.setEstabelecimento(historico.getEstabelecimento().getCodigoAC());
		item.setSaltipo(String.valueOf(TipoAplicacaoIndice.getCodigoAC(historico.getTipoSalario())));
		item.setExpAgenteNocivo(historico.getGfip());

		switch (historico.getTipoSalario()){
			case TipoAplicacaoIndice.CARGO:
				item.setIndcodigosalario("");
				item.setIndqtde(0.0);
				item.setValor(0.0);
				break;
			case TipoAplicacaoIndice.INDICE:
				item.setIndcodigosalario(historico.getIndice().getCodigoAC());
				item.setIndqtde(historico.getQuantidadeIndice());
				item.setValor(0.0);
				break;
			case TipoAplicacaoIndice.VALOR:
				item.setIndcodigosalario("");
				item.setIndqtde(0.0);
				item.setValor(historico.getSalarioCalculado());
				break;
		}
	}
}
