<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');
	</style>
	
	<#include "../ftl/mascarasImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@ww.head/>
	
	<#if historicoExtintor.id?exists>
		<title>Edição de Histórico do Extintor ${extintor.descricao}</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
	<#else>
		<title>Inserir Histórico para o Extintor ${extintor.descricao}</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
	</#if>
	<#assign validarCampos="return validaFormulario('form', new Array('dataHist','estabelecimento','localizacao','hora'), new Array('dataHist','hora'))"/>

	<#if historicoExtintor.data?exists>
		<#assign data = historicoExtintor.data>
	<#else>
		<#assign data = "">
	</#if>
	
	<#if troca>
		<#assign voltar="../historicoExtintor/list.action"/>
	<#else>
		<#assign voltar="../extintor/prepareUpdate.action?extintor.id=${extintor.id}"/>
	</#if>
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>
	<script type="text/javascript">
		var localizacoes = [${localizacoes}];

		$(function() {
			$("#localizacao").autocomplete(localizacoes);
		});
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.datepicker label="A partir de" name="historicoExtintor.data" id="dataHist" required="true" value="${data}" cssClass="mascaraData" liClass="liLeft"/>
		<@ww.textfield label="Hora" name="historicoExtintor.hora" id="hora" cssStyle="width: 38px;" cssClass="mascaraHora" required="true"/>
		<@ww.select label="Estabelecimento" name="historicoExtintor.estabelecimento.id" id="estabelecimento" required="true" list="estabelecimentos" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..."/>
		<@ww.textfield label="Localização" name="historicoExtintor.localizacao" id="localizacao" required="true" maxLength="50"/>
		
		<@ww.hidden name="historicoExtintor.id" />
		<@ww.hidden name="historicoExtintor.extintor.id" />
		<@ww.hidden name="extintor.id" />
		<@ww.hidden name="troca" />
		<@ww.hidden id="pagina" name="page"/>
	</@ww.form>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="form"/>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}"></button>
		<button onclick="javascript: executeLink('${voltar}');" class="btnCancelar" accesskey="V"></button>
	</div>
</body>
</html>