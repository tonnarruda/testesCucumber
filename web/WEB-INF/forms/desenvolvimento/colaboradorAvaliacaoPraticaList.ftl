<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>

	<title>ColaboradorAvaliacaoPratica</title>
</head>
<body>
	<#assign validarCampos="return validaFormulario('form', null, null)"/>

	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" action="buscaColaboradores.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<li>
			<@ww.div cssClass="divInfo" cssStyle="width: 950px;">
				<ul>
					<@ww.select label="Certificações" name="certificacao.id" id="colab" list="certificacoes" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." onchange="document.form.submit();" />
					<@ww.select label="Colaborador" name="colaborador.id" id="colab" list="colaboradores" listKey="id" listValue="nomeCpf" headerKey="" headerValue="Selecione..." onchange="document.form.submit();" />
					<br><br>
				</ul>
			</@ww.div>
		</li>
	</@ww.form>
	
	<#if colaborador?exists && colaborador.id?exists>
		<br/>
		<b>Colaborador: ${colaborador.nome} ${nomeComercialEntreParentese}</b> <br/>
		<b>Estabelecimento: ${colaborador.estabelecimento.nome}</b> <br/>
		<b>Cargo: ${colaborador.faixaSalarial.descricao}</b> <br/>
		<b>Área Organizacional: ${colaborador.areaOrganizacional.descricao}</b> <br/>
		<b>Data de Admissão: ${colaborador.dataAdmissaoFormatada}</b> <br/>
		<b>Telefone : ${colaborador.contato.foneContatoFormatado}</b> <br/><br/>

		<@display.table name="colaboradorQuestionarios" id="colaboradorQuestionario" class="dados">
			<@display.column title="Ações" class="acao">
				<a href="prepareUpdateAvaliacaoExperiencia.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='deleteAvaliacaoExperiencia.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
				<a href="#" onclick="escolheTipoAgrupamento('${colaboradorQuestionario.avaliacao.id}', '${colaboradorQuestionario.avaliacao.titulo}','${colaboradorQuestionario.id}', false)"><img border="0" title="Imprimir avaliação" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
				<a href="imprimirAvaliacaoRespondida.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}&modoEconomico=true"><img border="0" title="Imprimir avaliação no formato econômico" src="<@ww.url includeParams="none" value="/imgs/iconPrint.gif"/>"></a>
			</@display.column>
			<@display.column property="dataMaisTempoPeriodoExperiencia" title="Data" style="width: 140px;"/>
			<@display.column property="avaliacao.titulo" title="Avaliação" />
			<@display.column property="performanceFormatada" title="Performance" style="width: 100px;" />
			<@display.column title="Obs." style="text-align: center;width: 50px">
				<#if colaboradorQuestionario.observacao?exists && colaboradorQuestionario.observacao?trim != "">
					<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${colaboradorQuestionario.observacao?j_string}');return false">...</span>
				</#if>
			</@display.column>
		</@display.table>
	
		<div class="buttonGroup">
			<button class="btnInserir" onclick="window.location='prepareInsertAvaliacaoExperiencia.action?colaboradorQuestionario.colaborador.id=${colaborador.id}'"></button>
		</div>
	</#if>	
</body>
</html>
