package com.fortes.rh.test.dicionario;

import java.util.Collection;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.web.tags.CheckBox;

public class MotivoSolicitacaoExameTest extends TestCase
{
	public void testMotivoSolicitacaoExame()
	{
		MotivoSolicitacaoExame motivo = MotivoSolicitacaoExame.getInstance();

		assertEquals(8, motivo.size());
		assertEquals("Consulta comum", motivo.get("CONSULTA"));
		assertEquals("ASO Admissional", motivo.get("ADMISSIONAL"));
		assertEquals("ASO Periódico", motivo.get("PERIODICO"));
		assertEquals("ASO Retorno ao Trabalho", motivo.get("RETORNO"));
		assertEquals("ASO Mudança de Função", motivo.get("MUDANCA"));
		assertEquals("ASO Demissional", motivo.get("DEMISSIONAL"));
		assertEquals("Apresentação de Atestado Externo", motivo.get("ATESTADO"));
		assertEquals("Solicitação de Exame", motivo.get("SOLICITACAOEXAME"));
	}

	public void testMontaCheckListBox()
	{
		Collection<CheckBox> checkBoxs = MotivoSolicitacaoExame.montaCheckListBox();
		assertEquals(8, checkBoxs.size());
	}

	public void testGetMarcados()
	{
		String[] values = MotivoSolicitacaoExame.getMarcados(null);
		assertEquals(0, values.length);
		
		values = MotivoSolicitacaoExame.getMarcados(new String[]{});
		assertEquals(0, values.length);
		
		String[] marcados = new String[]{"0", "4", "5", "6"};
		values = MotivoSolicitacaoExame.getMarcados(marcados);
		assertEquals(4, values.length);
		assertEquals("CONSULTA", values[0]);
		assertEquals("MUDANCA", values[1]);
		assertEquals("DEMISSIONAL", values[2]);
		assertEquals("ATESTADO", values[3]);
	}
}
