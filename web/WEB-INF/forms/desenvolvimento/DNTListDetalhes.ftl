<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<#assign showFilter = true/>
	<#include "../ftl/showFilterImports.ftl" />
	<#assign validarCampos="return validaFormulario('frmFiltro', new Array('estabelecimento','area'), null)"/>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<title>DNT - Colaborador X Curso X Prioridade</title>
</head>

<body>
	<@ww.actionerror/>
	<@ww.actionmessage/>

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="frmFiltro" onsubmit="${validarCampos}" action="listDetalhes.action" method="POST">
			<@ww.hidden name="dnt.id"/>
			<@ww.hidden name="gestor"/>
			<@ww.select label="Estabelecimento" name="estabelecimento.id" id="estabelecimento"  required="true" list="estabelecimentos" headerKey="" headerValue="[Selecione...]" listKey="id" listValue="nome" />
			<@ww.select label="Área Organizacional" name="areaFiltro.id" id="area" list="areas"  required="true" headerKey="" headerValue="[Selecione...]" listKey="id" listValue="descricao" cssStyle="width: 450px;"/>
			<button class="btnPesquisar grayBGE" onclick="${validarCampos};" accesskey="P">
			</button>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>

	<#if colaboradors?exists && 0 < colaboradors?size>
		<#list colaboradors as colab>
			<b>${colab.nomeComercial}</b>
			<@display.table name="getColabTurmas(${colab.id?string?replace('.', '')?replace(',','')})" id="ct" class="dados">
				<@display.column title="Ações" class="acao" style="width:60px;">
					<a href="../colaboradorTurma/prepareUpdateDnt.action?colaboradorTurma.dnt.id=${dnt.id}&areaFiltroId=${areaFiltro.id}&colaboradorTurma.id=${ct.id}&gestor=${gestor?string}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
					<a href="javascript:newConfirm('Confirma exclusão?', function(){window.location='../colaboradorTurma/delete.action?dntId=${dnt.id}&areaFiltroId=${areaFiltro.id}&colaboradorTurma.id=${ct.id}&gestor=${gestor?string}&estabelecimento.id=${estabelecimento.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
				</@display.column>
				<@display.column property="curso.nome" title="Curso" style="width:220px;"/>
				<@display.column property="turma.descricao" title="Turma" style="width:180px;"/>
				<@display.column property="prioridadeTreinamento.descricao" title="Prioridade" style="width:180px;"/>
			</@display.table>
		</#list>

	<#elseif areaFiltro?exists>
	<b>Não há resultado para o filtro informado.</b>
	<#else>
	<b>Selecione Área Organizacional e Estabelecimento.</b>
	</#if>


	<div class="buttonGroup">
		<#if colaboradors?exists>
			<button class="btnInserir" onclick="window.location = '../colaboradorTurma/prepareInsertDnt.action?colaboradorTurma.dnt.id=${dnt.id}&areaFiltroId=${areaFiltro.id}&gestor=${gestor?string}'" accesskey="I">
			</button>
		</#if>
		<#if !gestor>
			<button class="btnCancelar" onclick="window.location='list.action'" accesskey="V">
			</button>
		</#if>
	</div>

</body>
</html>