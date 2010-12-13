<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>

	<#if modeloAvaliacao?exists && modeloAvaliacao = tipoModeloAvaliacao.getSolicitacao()>
		<title>Modelos de Avaliação de Solicitação</title>
	<#else>
		<title>Modelos de Avaliação de Desempenho</title>
	</#if>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<div align="right">&nbsp;<span class="encerradaBkg">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Inativa(s)</div>
	<br />
	<@display.table name="avaliacaos" id="avaliacao" class="dados">

		<#assign classe=""/>
		<#if !avaliacao.ativo>
			<#assign classe="encerrada"/>
		</#if>

		<@display.column title="Ações" style = "width:160px;">
			<a href="visualizar.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}"><img border="0" title="Visualizar" src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>"></a>
			<a href="../perguntaAvaliacao/list.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}"><img border="0" title="Critérios" src="<@ww.url includeParams="none" value="/imgs/question.gif"/>"></a>
			<a href="../../pesquisa/aspecto/listAvaliacao.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}"><img border="0" title="Aspectos" src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>"></a>
			<a href="prepareUpdate.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="clonar.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}"><img border="0" title="Clonar" src="<@ww.url includeParams="none" value="/imgs/clonar.gif"/>"></a>
			<a href="imprimir.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}"><img border="0" title="Imprimir Modelo da Avaliação" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
			<a href="imprimir.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}&imprimirFormaEconomica=true"><img border="0" title="Imprimir Modelo da Avaliação em formato econômico" src="<@ww.url includeParams="none" value="/imgs/iconPrint.gif"/>"></a>
			<a href="#" onclick="if (confirm('Confirma exclusão?')) window.location='delete.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}'"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		
		<@display.column title="Avaliação" property="titulo" class="${classe}"/>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action?modeloAvaliacao=${modeloAvaliacao}'"></button>
	</div>
</body>
</html>
