<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>

	<#include "../ftl/mascarasImports.ftl" />
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<title>Mudança de Função</title>

	<#if historicoColaborador?exists && historicoColaborador.data?exists>
		<#assign data = historicoColaborador.data>
	<#else>
		<#assign data = "">
	</#if>

	<#if areaBusca?exists && areaBusca.id?exists>
		<#assign areaId = areaBusca.id>
	<#else>
		<#assign areaId = "">
	</#if>

	<#if nomeBusca?exists>
		<#assign nomeBuscado = nomeBusca>
	<#else>
		<#assign nomeBuscado = "">
	</#if>

	<#if page?exists>
		<#assign pagina = page>
	<#else>
		<#assign pagina = "">
	</#if>


	<#assign validarCampos="return validaFormulario('form', new Array('data','ambiente','funcao'), new Array('data'))"/>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	<@ww.form name="form" action="mudaFuncao.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield  label="Nome"   name="colaborador.nome" id="nome" disabled="true" cssStyle="width: 450px;"/>
		<@ww.datepicker label="Data"   name="historicoColaborador.data" id="data" required="true" value="${data?date}" cssClass="mascaraData"/>
	  	<@ww.select     label="Função" name="historicoColaborador.funcao.id" required="true" id="funcao" list="funcaos" headerKey="" headerValue="[Selecione...]" listKey="id" listValue="nome" cssStyle="width: 450px;"/>
		<@ww.textfield  label="GFIP"   name="historicoColaborador.gfip"   id="gfip" maxlength="2" cssStyle="width: 50px;"/>
	  	<@ww.select     label="Ambiente" required="true" id="ambiente" name="historicoColaborador.ambiente.id" list="ambientes" headerKey="" headerValue="[Selecione...]" listKey="id" listValue="nome" cssStyle="width: 450px;"/>
		<@ww.hidden name="colaborador.id" />
		<@ww.hidden name="page" value="${pagina}" />
		<@ww.hidden name="nomeBusca" value="${nomeBuscado}"/>
		<@ww.hidden name="areaBusca.id"  value="${areaId}"/>

	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};"  class="btnGravar" accesskey="g">
		</button>
		<button onclick="window.location.href='mudancaFuncaoFiltro.action?areaBusca.id=${areaId}&nomeBusca=${nomeBuscado}&page=${pagina}'" class="btnCancelar">
		</button
	</div>
</body>
</html>