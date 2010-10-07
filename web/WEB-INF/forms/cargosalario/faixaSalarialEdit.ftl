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
			<@ww.textfield label="Descrição no AC Pessoal" name="faixaSalarialAux.nomeACPessoal" id="nomeACPessoal" cssStyle="width: 220px;"  maxLength="30" required="true"/>
		<#else>
			<@ww.hidden	name="faixaSalarialAux.nomeACPessoal" />
		</#if>
		
        <@frt.checkListBox label="Certificações" name="certificacaosCheck" list="certificacaosCheckList" />

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
		<@display.table name="faixaSalarialsHistoricos" id="faixaSalarialHistorico" class="dados" style="width: 800px;">
			<@display.column title="Ações" class="acao">
				<a href="../faixaSalarialHistorico/prepareUpdate.action?faixaSalarialHistorico.id=${faixaSalarialHistorico.id}&faixaSalarialAux.id=${faixaSalarialAux.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<#if (faixaSalarialsHistoricos?size > 1)>
					<a href="#" onclick="if (confirm('Confirma exclusão?')) window.location='../faixaSalarialHistorico/delete.action?faixaSalarialHistorico.id=${faixaSalarialHistorico.id}&faixaSalarialAux.id=${faixaSalarialAux.id}'"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url value="/imgs/delete.gif"/>"></a>
				<#else>
					<img border="0" title="Não é possível excluir o último histórico." src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				</#if>
			</@display.column>
			<@display.column title="A partir de" style="width: 70px;text-align: center;">
				${faixaSalarialHistorico.data?date}
			</@display.column>
			<@display.column title="Tipo" style="width: 600px;">
				Por ${tipoAplicacaoIndice.getDescricao(faixaSalarialHistorico.tipo)}
				<#if faixaSalarialHistorico.tipo == 2>
					(${faixaSalarialHistorico.quantidade} X ${faixaSalarialHistorico.indice.nome})
				</#if>
			</@display.column>
			<@display.column title="Valor" style="width: 80px;text-align: right;">
				<#if faixaSalarialHistorico.valor?exists>
					${faixaSalarialHistorico.valor?string(",##0.00")}
				<#else>
					-
				</#if>
			</@display.column>
			<#if integradoAC>
				<@display.column title="Status no AC" style="width: 50px;text-align: center;">
					<img border="0" title="${statusRetornoAC.getDescricao(faixaSalarialHistorico.status)}"
					src="<@ww.url includeParams="none" value="/imgs/"/>${statusRetornoAC.getImg(faixaSalarialHistorico.status)}">
				</@display.column>
			</#if>
		</@display.table>

		<div class="buttonGroup">
			<button onclick="window.location='../faixaSalarialHistorico/prepareInsert.action?faixaSalarialAux.id=${faixaSalarialAux.id}'" class="btnInserir" accesskey="I"></button>
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