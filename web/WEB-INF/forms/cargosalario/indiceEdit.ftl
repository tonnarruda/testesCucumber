<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>

	<#if indiceAux.id?exists>
		<title>Editar Índice</title>
		<#assign formAction="update.action"/>
		<#assign edicao=true/>
		<#assign validarCampos="return validaFormulario('form', new Array('nome'), null)"/>
	<#else>
		<title>Inserir Índice</title>
		<#assign formAction="insert.action"/>
		<#assign edicao=false/>
		<#assign validarCampos="return validaFormulario('form', new Array('nome','dataHist','valor'), new Array('dataHist'))"/>
	</#if>

	<#include "indiceHistoricoCadastroHeadInclude.ftl" />
	<#if !integradoAC>
		<#assign desabilita="false"/>
	<#else>
		<#assign desabilita="true"/>
	</#if>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield label="Nome" name="indiceAux.nome" id="nome" cssStyle="width: 300px;" maxLength="40" disabled="${desabilita}" required="true"/>

		<#if !edicao>
			<b><br>Primeiro Histórico do Índice:</b>
			<br/>
			<#include "indiceHistoricoCadastroInclude.ftl" />
		</#if>

		<@ww.hidden name="indiceAux.id" />
		<@ww.hidden name="indiceAux.codigoAC" />
	</@ww.form>

	<div class="buttonGroup">
		<#if !integradoAC>
			<button onclick="${validarCampos};" class="btnGravar">
			</button>
		</#if>

	<#if indiceAux.id?exists>
		</div>
		<br>
		<@display.table name="indicesHistoricos" id="indiceHistorico" class="dados" style="width: 300px;">
			<#if !integradoAC>
				<@display.column title="Ações" class="acao">
					<a href="javascript:;" onclick="javascript: executeLink('../indiceHistorico/prepareUpdate.action?indiceHistorico.id=${indiceHistorico.id}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
					<#if 1 < indicesHistoricos?size>
						<a href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){executeLink('../indiceHistorico/delete.action?indiceHistorico.id=${indiceHistorico.id}&indiceAux.id=${indiceHistorico.indice.id}');});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url value="/imgs/delete.gif"/>"></a>
					<#else>
						<img border="0" title="Não é possível excluir, um índice deve possuir pelo menos um histórico." src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
					</#if>
				</@display.column>
			</#if>
			<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width: 80px;text-align: center;"/>
			<@display.column property="valor" title="Valor" format="{0, number, #,##0.00}" style="text-align: right;"/>
		</@display.table>
	
		<div class="buttonGroup">
	</#if>
	
		<#if indiceAux.id?exists && !integradoAC>
			<button onclick="javascript: executeLink('../indiceHistorico/prepareInsert.action?indiceAux.id=${indiceAux.id}');" class="btnInserir"></button>
		</#if>
		<button onclick="window.location='list.action'" class="btnCancelar"></button>
	</div>
</body>
</html>