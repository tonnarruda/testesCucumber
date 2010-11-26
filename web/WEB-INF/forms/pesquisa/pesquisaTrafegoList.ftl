<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<html>
<head>
	<@ww.head/>

	<title>Pesquisas</title>
	
	<link rel="stylesheet" href="${request.contextPath}/externo/layout?tipo=trafego" />
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
			<a href="prepareResponderQuestionarioTrafego.action?questionario.id=${questionario.id}&colaborador.id=${colaborador.id}&validarFormulario=true&empresaCodigo=${empresaCodigo}&matricula=${matricula}">
				<img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"> ${questionario.titulo}
			</a><br><br>
		</#list>
	</#if>
</body>
</html>