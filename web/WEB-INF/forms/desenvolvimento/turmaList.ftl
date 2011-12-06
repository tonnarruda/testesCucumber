<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/jquery-ui/jquery-ui-1.8.9.custom.css"/>');
		
		.popup { display: none; }
		.popup ul { list-style: none; }
		.popup ul li { margin: 5px; }
		.popup ul li a { text-decoration: none; }
	</style>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/TurmaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
		
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
		
		function getMenuAvaliacoes(event, turmaId, cursoId, acao)
		{
			TurmaDWR.getAvaliacaoTurmas(turmaId, {
				callback: function(dados) {
					$('.popup').empty();
					
					var link, urlIcone;
					var onClick = "";
					var popupAvaliacoes = "<ul>";
					
					$(dados).each(function(i, avaliacaoTurma) {
						if (acao == 'imprimir')
						{
							link = "../../pesquisa/questionario/imprimir.action?questionario.id=" + avaliacaoTurma.questionario.id + "&filtroQuestionario=" + avaliacaoTurma.turma.id;
							urlIcone = "<@ww.url includeParams="none" value="/imgs/printer.gif"/>";
						}
						else
						{
							if(avaliacaoTurma.qtdColaboradorQuestionario > 0){
								link = "../../pesquisa/questionario/prepareResultado.action?questionario.id=" + avaliacaoTurma.questionario.id + "&turmaId=" + avaliacaoTurma.turma.id + "&cursoId=" + cursoId;
							} else {
								link = "#";
								onClick = 'jAlert("Não existe questionário respondido para esta avaliação.")';
							}
							urlIcone = "<@ww.url includeParams="none" value="/imgs/grafico_pizza.gif"/>";
						}
					
						popupAvaliacoes += "<li>";
						popupAvaliacoes += "<a href='" + link + "' onclick='" + onClick+ " ' >";
						popupAvaliacoes += "<img src='" + urlIcone + "' align='absmiddle'/>&nbsp; " + avaliacaoTurma.questionarioTitulo;
						popupAvaliacoes += "</a>";
						popupAvaliacoes += "</li>\n";
					});
					
					popupAvaliacoes += "</ul>";
					
					$('.popup').html(popupAvaliacoes);
					
					$('.popup').dialog({ modal: true, width: 600, position: [event.pageX, event.pageY] });
				}
			});
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
			
			<#if avaliacaoCursos?exists && 0 < avaliacaoCursos?size>
				<a href="prepareAproveitamento.action?turma.id=${turma.id}&curso.id=${curso.id}"><img border="0" title="Avaliações dos Alunos" src="<@ww.url value="/imgs/favourites.gif"/>"></a>
			<#else>
				<a href="javascript:;"><img border="0" title="Não existem avaliações definidas para este curso" src="<@ww.url value="/imgs/favourites.gif"/>" style="opacity:0.3;filter:alpha(opacity=40);"/></a>
			</#if>
			
			<a href="preparePresenca.action?turma.id=${turma.id}&curso.id=${curso.id}&voltarPara=list.action"><img border="0" title="Lista de Frequência" src="<@ww.url value="/imgs/check.gif"/>"></a>
			<a href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?turma.id=${turma.id}&turma.empresa.id=${turma.empresa.id}&curso.id=${curso.id}&curso.nome=${curso.nome}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			<a href="imprimirConfirmacaoCertificado.action?turma.id=${turma.id}&curso.id=${curso.id}"><img border="0" title="Relatório de Realização de Curso" src="<@ww.url value="/imgs/report.gif"/>"></a>
			
			<#if turma.qtdAvaliacoes?exists && 0 < turma.qtdAvaliacoes>
				<a href="javascript:;" onclick="getMenuAvaliacoes(event, ${turma.id}, ${curso.id}, 'imprimir')"><img border="0" title="Imprimir Avaliações" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
				<a href="javascript:;" onclick="getMenuAvaliacoes(event, ${turma.id}, ${curso.id}, 'relatorio')"><img border="0" title="Relatório das Avaliações" src="<@ww.url includeParams="none" value="/imgs/grafico_pizza.gif"/>"></a>
			<#else>
				<a href="javascript:;"><img border="0" title="Não há avaliações a serem impressas nessa turma" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" style="opacity:0.3;filter:alpha(opacity=40);"></a>
				<a href="javascript:;"><img border="0" title="Não há avaliações para relatório nessa turma" src="<@ww.url includeParams="none" value="/imgs/grafico_pizza.gif"/>" style="opacity:0.3;filter:alpha(opacity=40);"></a>
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

	<div class='popup' title='Avaliações'>></div>
</body>
</html>