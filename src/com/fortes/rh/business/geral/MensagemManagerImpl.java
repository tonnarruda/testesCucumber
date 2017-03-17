package com.fortes.rh.business.geral;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.MensagemDao;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.MathUtil;

@Component
public class MensagemManagerImpl extends GenericManagerImpl<Mensagem, MensagemDao> implements MensagemManager
{
	@Autowired
	MensagemManagerImpl(MensagemDao dao) {
		setDao(dao);
	}
	
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
		mensagemFinal.append("Data: "+formataData(faixaSalarialHistorico.getData()));
		mensagemFinal.append("\r\n");
		mensagemFinal.append("Tipo: "+TipoAplicacaoIndice.getDescricao(faixaSalarialHistorico.getTipo()));
		mensagemFinal.append("\r\n");

		if(faixaSalarialHistorico.getTipo() == TipoAplicacaoIndice.INDICE)
		{
			mensagemFinal.append("Índice: "+faixaSalarialHistorico.getIndice().getNome());
			mensagemFinal.append("\r\n");
			mensagemFinal.append("Quantidade: " + MathUtil.formataValor(faixaSalarialHistorico.getQuantidade()));
		}
		else
		{
			mensagemFinal.append("Valor : " + MathUtil.formataValor(faixaSalarialHistorico.getValor()));
		}

		return mensagemFinal.toString();
	}

	private String formataData(Date data) {
		String dataFormatada = DateUtil.formataDate(data, "dd/MM/yyyy");
		return dataFormatada;
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
		mensagemFinal.append("Nome: " + (historicoColaborador.getColaborador().getNomeComercial() == null ? historicoColaborador.getColaborador().getNome() : historicoColaborador.getColaborador().getNomeComercial()));
		mensagemFinal.append("\r\n");
		mensagemFinal.append("Data: "+formataData(historicoColaborador.getData()));
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

		switch (historicoColaborador.getTipoSalario())
		{
			case TipoAplicacaoIndice.INDICE:
				mensagemFinal.append("Índice: "+historicoColaborador.getIndice().getNome());
				mensagemFinal.append("\r\n");
				mensagemFinal.append("Quantidade: " + MathUtil.formataValor(historicoColaborador.getQuantidadeIndice()));
				break;

			case TipoAplicacaoIndice.VALOR:
				mensagemFinal.append("Valor : " + MathUtil.formataValor(historicoColaborador.getSalario()));
				break;
		}

		return mensagemFinal.toString();
	}

	public void removeMensagensColaborador(Long colaboradorId, Character tipo) 
	{
		getDao().removeMensagensColaborador(colaboradorId, tipo);
	}

	public void removeByAvaliacaoId(Long avaliacaoId) 
	{
		getDao().removeByAvaliacaoId(avaliacaoId);
	}

	public void removerMensagensViculadasByColaborador(Long[] colaboradoresIds) {
		getDao().removerMensagensViculadasByColaborador(colaboradoresIds);
	}
}