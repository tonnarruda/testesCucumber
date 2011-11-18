<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css"/>');
</style>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/TurmaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
<script type="text/javascript">
		function enviarEmail(turmaId)
		{
			DWRUtil.useLoadingMessage('Enviando...');
			DWREngine.setErrorHandler(errorMsg);
			TurmaDWR.enviarAviso(enviarAviso, turmaId, <@authz.authentication operation="empresaId"/>);
		}

		function enviarAviso(data)
		{
			alert(data);
		}

		function errorMsg(msg)
		{
			jAlert("Envio de email falhou.");
		}
	</script>
<title>Turmas - ${curso.nome}</title>
</head>
<body>

	<#-- form utilizado pela paginação -->
	<@ww.form name="form" id="form" action="list.action" validate="true" method="POST">
		<@ww.hidden name="curso.id" />
		
		<@ww.hidden id="pagina" name="page"/>
	</@ww.form>

	<@ww.actionmessage />
	<@ww.actionerror />
	<@display.table name="turmas" id="turma" class="dados" >
		<@display.column title="Ações" style="width:170px">
			<@ww.hidden name="turma.id" value="" />
			<a href="../colaboradorTurma/list.action?turma.id=${turma.id}"><img border="0" title="Colaboradores Inscritos" src="<@ww.url includeParams="none" value="/imgs/usuarios.gif"/>"></a>
			<a href="prepareUpdate.action?turma.id=${turma.id}&curso.id=${curso.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="enviarEmail(${turma.id});" ><img border="0" title="Enviar aviso por email" src="<@ww.url value="/imgs/icon_email.gif"/>"></a>
			<a href="prepareAproveitamento.action?turma.id=${turma.id}&curso.id=${curso.id}"><img border="0" title="Avaliações dos Alunos" src="<@ww.url value="/imgs/favourites.gif"/>"></a>
			<a href="preparePresenca.action?turma.id=${turma.id}&curso.id=${curso.id}&voltarPara=list.action"><img border="0" title="Lista de Frequência" src="<@ww.url value="/imgs/check.gif"/>"></a>
			<a href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?turma.id=${turma.id}&turma.empresa.id=${turma.empresa.id}&curso.id=${curso.id}&curso.nome=${curso.nome}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			<a href="imprimirConfirmacaoCertificado.action?turma.id=${turma.id}&curso.id=${curso.id}"><img border="0" title="Relatório de Realização de Curso" src="<@ww.url value="/imgs/report.gif"/>"></a>
			
			<#-- 
			<#if turma.avaliacaoTurma.questionario.id?exists>
				<a href="../../pesquisa/questionario/imprimir.action?questionario.id=${turma.avaliacaoTurma.questionario.id}&filtroQuestionario=${turma.id?string?replace(".", "")?replace(",","")}"><img border="0" title="Imprimir Avaliação" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
				<a href="../../pesquisa/questionario/prepareResultado.action?questionario.id=${turma.avaliacaoTurma.questionario.id}&turmaId=${turma.id}&cursoId=${curso.id}"><img border="0" title="Relatório da Avaliação" src="<@ww.url includeParams="none" value="/imgs/grafico_pizza.gif"/>"></a>
			</#if >
			-->
		</@display.column>
		<@display.column property="descricao" title="Descrição"/>

		<@display.column title="Período" style="text-align:center; width:140px">
			${turma.periodoFormatado}
		</@display.column>
		<@display.column property="instrutor" title="Instrutor" style="text-align:center; width:180px" />

		<@display.column title="Realizada" style="text-align:center; width:50px">
			<#if turma.realizada>
			Sim
			<#else>
			Não
			</#if>
		</@display.column>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="form"/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action?curso.id=${curso.id}'" accesskey="N">
		</button>
		<button onclick="window.location='../curso/list.action'" class="btnVoltar" accesskey="V">
		</button>
	</div>

</body>
</html>