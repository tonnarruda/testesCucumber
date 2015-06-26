<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		
		#alert { display: none; }
		.popup { display: none; }
		.popup ul { list-style: none; }
		.popup ul li { margin: 5px; }
		.popup ul li a { text-decoration: none; }
	</style>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/TurmaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
		
	<script type="text/javascript">
		var evt;
	
		function enviarEmail(turmaId)
		{
			DWRUtil.useLoadingMessage('Enviando...');
			DWREngine.setErrorHandler(errorMsg);
			TurmaDWR.enviarAviso(enviarAviso, turmaId, <@authz.authentication operation="empresaId"/>);
		}

		function enviarAviso(data)
		{
			jAlert(data);
		}

		function errorMsg(msg)
		{
			jAlert("Envio de email falhou.");
		}
		
		function exibeResultados(turmaId, questionarioId)
		{
			window.location = "../../pesquisa/questionario/prepareResultado.action?questionario.id=" + questionarioId + "&turmaId=" + turmaId + "&cursoId=" + ${curso.id};
		}

		function imprimirAvaliacao(turmaId, questionarioId)
		{
			window.location = "../../pesquisa/questionario/imprimir.action?questionario.id=" + questionarioId + "&filtroQuestionario=" + turmaId;
		}
		
		function liberarAvaliacaoTurma(turmaId, avaliacaoTurmaId)
		{
			DWRUtil.useLoadingMessage('Liberando...');
			TurmaDWR.liberar(retorno, turmaId, avaliacaoTurmaId, <@authz.authentication operation="empresaId"/>);
		}

		function bloquearAvaliacaoTurma(turmaId, avaliacaoTurmaId)
		{
			DWRUtil.useLoadingMessage('Bloqueando...');
			TurmaDWR.bloquear(retorno, turmaId, avaliacaoTurmaId, <@authz.authentication operation="empresaId"/>);
		}
		
		function retorno(turmaId)
		{
			getMenuAvaliacoes(evt, turmaId, ${curso.id});
		}

		function getMenuAvaliacoes(event, turmaId, cursoId)
		{
			evt = event;
			
			$('.popup').dialog({modal: true, 
								width: 320, 
								position: [event.pageX, event.pageY],
								create: function (event, ui) {
							        $(".ui-dialog-titlebar").hide();
							    }
							}).load('<@ww.url includeParams="none" value="/desenvolvimento/turma/popUpTurmaAvaliacaoAcao.action"/>', { 'turma.id': turmaId });
		}
	</script>
<title>Turmas do curso ${curso.nome}</title>
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
			
			<#if turma.status == 'P'>
				<#assign iconeStatus><@ww.url includeParams="none" value="/imgs/cadeado_yellow.gif"/></#assign>
				<#assign textoStatus>Parcialmente liberado</#assign>
			<#elseif turma.status == 'L'>
				<#assign iconeStatus><@ww.url includeParams="none" value="/imgs/cadeado_green.gif"/></#assign>
				<#assign textoStatus>Liberado</#assign>
			<#else>
				<#assign iconeStatus><@ww.url includeParams="none" value="/imgs/cadeado_red.gif"/></#assign>
				<#assign textoStatus>Bloqueado</#assign>
			</#if>
			
			<a href="../colaboradorTurma/list.action?turma.id=${turma.id}&page=1"><img border="0" title="Colaboradores Inscritos" src="<@ww.url includeParams="none" value="/imgs/usuarios.gif"/>"></a>
			<a href="prepareUpdate.action?turma.id=${turma.id}&curso.id=${curso.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="enviarEmail(${turma.id});" ><img border="0" title="Enviar aviso por email" src="<@ww.url value="/imgs/icon_email.gif"/>"></a>
			
			<#if avaliacaoCursos?exists && 0 < avaliacaoCursos?size>
				<a href="prepareAproveitamento.action?turma.id=${turma.id}&curso.id=${curso.id}"><img border="0" title="Avaliações dos Alunos" src="<@ww.url value="/imgs/favourites.gif"/>"></a>
			<#else>
				<a href="javascript:;"><img border="0" title="Não existem avaliações definidas para este curso" src="<@ww.url value="/imgs/favourites.gif"/>" style="opacity:0.3;filter:alpha(opacity=40);"/></a>
			</#if>
			
			<a href="preparePresenca.action?turma.id=${turma.id}&curso.id=${curso.id}&voltarPara=list.action"><img border="0" title="Lista de Frequência" src="<@ww.url value="/imgs/check.gif"/>"></a>
			
			<#if turma.empresa.id == empresaSistema.id>
				<a href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?turma.id=${turma.id}&turma.empresa.id=${turma.empresa.id}&curso.id=${curso.id}&curso.nome=${curso.nome}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			<#else>
				<img border="0" title="Turma compartilhada" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			</#if>
			
			<a href="imprimirConfirmacaoCertificado.action?turma.id=${turma.id}&curso.id=${curso.id}"><img border="0" title="Relatório de Realização de Curso" src="<@ww.url value="/imgs/report.gif"/>"></a>
			
			<#if turma.qtdAvaliacoes?exists && 0 < turma.qtdAvaliacoes>
				<a href="javascript:;" onclick="getMenuAvaliacoes(event, ${turma.id}, ${curso.id})"><img border="0" title="Avaliações da Turma" src="<@ww.url includeParams="none" value="/imgs/form.gif"/>"></a>
			<#else>
				<a href="javascript:;"><img border="0" title="Não há avaliações nessa turma" src="<@ww.url includeParams="none" value="/imgs/form.gif"/>" style="opacity:0.3;filter:alpha(opacity=40);"></a>
			</#if>
			
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

	<div class='popup'></div>
	<div id='alert'>
		<span id="texto"></span>
	</div>
</body>
</html>