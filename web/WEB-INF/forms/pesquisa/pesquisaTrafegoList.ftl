<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>

	<title>Pesquisas</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#if colaborador?exists && colaborador.nome?exists>
		Motorista: ${colaborador.nome}<br>
	</#if>
	<#if colaborador?exists && colaborador.codigoAC?exists>
		Matrícula: ${colaborador.codigoAC}<br>
	</#if>
	<br>
	<#if colaborador?exists && colaborador.id?exists && questionarios?exists && 0 < questionarios?size>
		Pesquisa(s):<br><br>
		<#list questionarios as questionario>
			<a href="prepareResponderQuestionarioTrafeco.action?questionario.id=${questionario.id}&colaborador.id=${colaborador.id}&validarFormulario=true">
				<img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"> ${questionario.titulo}
			</a><br><br>
		</#list>
	</#if>
</body>
</html>