package com.fortes.rh.business.geral;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.MensagemDao;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.Mensagem;

public class MensagemManagerImpl extends GenericManagerImpl<Mensagem, MensagemDao> implements MensagemManager
{
	@Override
	public Mensagem save(Mensagem mensagem)
	{
		mensagem.setData(new Date());
		return super.save(mensagem);
	}

	public String formataMensagemCancelamentoFaixaSalarialHistorico(String mensagem, FaixaSalarialHistorico faixaSalarialHistorico)
	{
		StringBuilder mensagemFinal = new StringBuilder();
		mensagemFinal.append("Cancelamento de Histórico da Faixa Salarial. ");
		mensagemFinal.append("\r\n\r\n");
		mensagemFinal.append("<b>Motivo do Cancelamento:</b> ");
		mensagemFinal.append("\r\n");
		mensagemFinal.append(mensagem);
		mensagemFinal.append("\r\n\r\n");
		mensagemFinal.append("<b>Dados do Histórico Cancelado:</b> ");
		mensagemFinal.append("\r\n");
		mensagemFinal.append("Cargo: "+faixaSalarialHistorico.getFaixaSalarial().getCargo().getNome());
		mensagemFinal.append("\r\n");
		mensagemFinal.append("Faixa Salarial: "+faixaSalarialHistorico.getFaixaSalarial().getNome());
		mensagemFinal.append("\r\n");
		mensagemFinal.append("Data: "+SimpleDateFormat.getDateInstance().format(faixaSalarialHistorico.getData()));
		mensagemFinal.append("\r\n");
		mensagemFinal.append("Tipo: "+TipoAplicacaoIndice.getDescricao(faixaSalarialHistorico.getTipo()));
		mensagemFinal.append("\r\n");

		DecimalFormat valor = new DecimalFormat("###,##0.00#");
		if(faixaSalarialHistorico.getTipo() == TipoAplicacaoIndice.INDICE)
		{
			mensagemFinal.append("Índice: "+faixaSalarialHistorico.getIndice().getNome());
			mensagemFinal.append("\r\n");
			mensagemFinal.append("Quantidade: "+valor.format(faixaSalarialHistorico.getQuantidade()));
		}
		else
		{
			mensagemFinal.append("Valor : "+valor.format(faixaSalarialHistorico.getValor()));
		}

		return mensagemFinal.toString();
	}

	public String formataMensagemCancelamentoHistoricoColaborador(String mensagem, HistoricoColaborador historicoColaborador)
	{
		StringBuilder mensagemFinal = new StringBuilder();
		mensagemFinal.append("Cancelamento de Situação do Colaborador. ");
		mensagemFinal.append("\r\n\r\n");
		mensagemFinal.append("<b>Motivo do Cancelamento:</b> ");
		mensagemFinal.append("\r\n");
		mensagemFinal.append(mensagem);
		mensagemFinal.append("\r\n\r\n");
		mensagemFinal.append("<b>Dados da Situação Cancelada:</b> ");
		mensagemFinal.append("\r\n");
		mensagemFinal.append("Nome: "+historicoColaborador.getColaborador().getNomeComercial());
		mensagemFinal.append("\r\n");
		mensagemFinal.append("Data: "+SimpleDateFormat.getDateInstance().format(historicoColaborador.getData()));
		mensagemFinal.append("\r\n");
		mensagemFinal.append("Estabelecimento: "+historicoColaborador.getEstabelecimento().getNome());
		mensagemFinal.append("\r\n");
		mensagemFinal.append("Área Organizacional: "+historicoColaborador.getAreaOrganizacional().getDescricao());
		mensagemFinal.append("\r\n");
		mensagemFinal.append("Cargo: "+historicoColaborador.getFaixaSalarial().getCargo().getNomeMercado());
		mensagemFinal.append("\r\n");
		mensagemFinal.append("Faixa Salarial: "+historicoColaborador.getFaixaSalarial().getNome());
		mensagemFinal.append("\r\n");
		mensagemFinal.append("Tipo do Salário: "+TipoAplicacaoIndice.getDescricao(historicoColaborador.getTipoSalario()));
		mensagemFinal.append("\r\n");

		DecimalFormat valor = new DecimalFormat("###,##0.00#");

		switch (historicoColaborador.getTipoSalario())
		{
			case TipoAplicacaoIndice.INDICE:
				mensagemFinal.append("Índice: "+historicoColaborador.getIndice().getNome());
				mensagemFinal.append("\r\n");
				mensagemFinal.append("Quantidade: "+valor.format(historicoColaborador.getQuantidadeIndice()));
				break;

			case TipoAplicacaoIndice.VALOR:
				mensagemFinal.append("Valor : "+valor.format(historicoColaborador.getSalario()));
				break;
		}

		return mensagemFinal.toString();
	}

}