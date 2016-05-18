<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<#if faixaSalarialAux.id?exists>
		<title>Editar Faixa Salarial</title>
		<#assign formAction="update.action"/>
		<#assign edicao=true/>
	<#else>
		<title>Inserir Faixa Salarial</title>
		<#assign formAction="insert.action"/>
		<#assign edicao=false/>
	</#if>

	<script type="text/javascript">
		function validaForm(indice, edicao)
		{
			<#if edicao>
				return validaFormulario('form', new Array('nome'), null);
			<#else>
				if(document.getElementById('tipo').value == indice)
				{
					return validaFormulario('form', new Array('nome','data','indice','quantidade'), new Array('data'));
				}
				else
				{
					return validaFormulario('form', new Array('nome','data','valor'), new Array('data', 'valor'));
				}
			</#if>
		}
		
		$(function() {
			var $imgs = $('.acao a img[title=Excluir]');
			if ($imgs.length==1)
			    $imgs.addClass('disabledImg').
			    	attr('title', 'Não é possível excluir o último histórico.').
			    	parent().removeAttr('onclick'); 
		});
			
	</script>

	<#include "faixaSalarialHistoricoCadastroHeadInclude.ftl" />
	
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" id="form" action="${formAction}" onsubmit="validaForm(${tipoAplicacaoIndice.getIndice()});" validate="true" method="POST">
		<b>Cargo:</b> ${cargoAux.nome}<br><br>

		<@ww.textfield label="Faixa" name="faixaSalarialAux.nome" id="nome" cssStyle="width: 220px;"  maxLength="30" required="true"/>
		<#if integradoAC>
			<@ww.textfield label="Descrição no Fortes Pessoal" name="faixaSalarialAux.nomeACPessoal" id="nomeACPessoal" cssStyle="width: 220px;"  maxLength="30" required="true"/>
		<#else>
			<@ww.hidden	name="faixaSalarialAux.nomeACPessoal" />
		</#if>
		
        <@frt.checkListBox label="Certificações" name="certificacaosCheck" list="certificacaosCheckList" filtro="true"/>

		<#if !edicao>
			<b><br>Primeiro Histórico da Faixa Salarial:</b><br>
			<#include "faixaSalarialHistoricoCadastroInclude.ftl" />
		</#if>

		<@ww.hidden	name="faixaSalarialAux.cargo.id" />
		<@ww.hidden name="faixaSalarialAux.codigoAC" />
		<@ww.hidden name="faixaSalarialAux.id" />
		<@ww.hidden name="cargoAux.id" />
	</@ww.form>
	<div class="buttonGroup">
		<button onclick="validaForm(${tipoAplicacaoIndice.getIndice()});" class="btnGravar"></button>

	<#if edicao>
		</div>
		<br>
		<@display.table name="faixaSalarialHistoricoVOs" id="faixaSalarialHistoricoVO" class="dados" style="width: 800px;">
			<@display.column title="Ações" class="acao">
				<#if (faixaSalarialHistoricoVO.editavel)>
					<a href="javascript: executeLink('../faixaSalarialHistorico/prepareUpdate.action?faixaSalarialHistorico.id=${faixaSalarialHistoricoVO.id}&faixaSalarialAux.id=${faixaSalarialAux.id}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
					<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('../faixaSalarialHistorico/delete.action?faixaSalarialHistorico.id=${faixaSalarialHistoricoVO.id}&faixaSalarialAux.id=${faixaSalarialAux.id}');});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url value="/imgs/delete.gif"/>"></a>
				<#else>
					<span title="Essa evolução do histórico da faixa foi devido ao reajuste do índice">(Índice)</span>
				</#if>
			</@display.column>
			<@display.column title="A partir de" style="width: 70px;text-align: center;">
				${faixaSalarialHistoricoVO.dataFaixa?date}
			</@display.column>
			<@display.column title="Tipo" style="width: 600px;">
				Por ${faixaSalarialHistoricoVO.nomeIndice}
			</@display.column>
			<@display.column title="Valor" style="width: 80px;text-align: right;">
				<#if faixaSalarialHistoricoVO.valorFaixa?exists>
					${faixaSalarialHistoricoVO.valorFaixa?string(",##0.00")}
				<#else>
					-
				</#if>
			</@display.column>
			<#if integradoAC>
				<@display.column title="Status no AC" style="width: 50px;text-align: center;">
					<img border="0" title="${statusRetornoAC.getDescricao(faixaSalarialHistoricoVO.status)}"
					src="<@ww.url includeParams="none" value="/imgs/"/>${statusRetornoAC.getImg(faixaSalarialHistoricoVO.status)}">
				</@display.column>
			</#if>
		</@display.table>

		<div class="buttonGroup">
			<button onclick="executeLink('../faixaSalarialHistorico/prepareInsert.action?faixaSalarialAux.id=${faixaSalarialAux.id}');" class="btnInserir" accesskey="I"></button>
	</#if>

		<button onclick="window.location='list.action?cargo.id=${cargoAux.id}'" class="btnCancelar"></button>
	</div>

	<script type="text/javascript">
		<#if !edicao>
			alteraTipo(document.getElementById('tipo').value, ${tipoAplicacaoIndice.getIndice()});
			calculaValor();
		</#if>
	</script>
</body>
</html>