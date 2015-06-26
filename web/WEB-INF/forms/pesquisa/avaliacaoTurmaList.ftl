<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		#formDialog { display: none; width: 600px; }
	</style>

	<script type='text/javascript'>
		function clonar(avaliacaoTurmaId, titulo)
		{
			$('#avaliacaoTurmaId').val(avaliacaoTurmaId);
			$('#formDialog').dialog({ modal: true, width: 530, title: 'Clonar: ' + titulo });
		}
	</script>

	<title>Modelos de Avaliação de Curso</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@display.table name="avaliacaoTurmas" id="avaliacaoTurma" class="dados">
		<@display.column title="Ações" style="width:155px">

			<#if avaliacaoTurma.questionario.aplicarPorAspecto>
				<a href="../questionario/prepareAplicarByAspecto.action?questionario.id=${avaliacaoTurma.questionario.id}&preview=true"><img border="0" title="Visualizar Questionário de Avaliação" src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>"></a>
			<#else>
				<a href="../questionario/prepareAplicar.action?questionario.id=${avaliacaoTurma.questionario.id}&preview=true"><img border="0" title="Visualizar Questionário de Avaliação" src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>"></a>
			</#if>

			<a href="prepareUpdate.action?avaliacaoTurma.id=${avaliacaoTurma.id}&quantidadeDeResposta=${avaliacaoTurma.questionario.quantidadeDeResposta}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>

			<#if 0 < avaliacaoTurma.questionario.quantidadeDeResposta>
				<img border="0" title="Questionário - Já existe resposta para este questionário, não é permitido editar" src="<@ww.url includeParams="none" value="/imgs/question.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				<img border="0" title="Aspectos - Já existe resposta para este questionário, não é permitido editar" src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			<#else>
				<a href="../pergunta/list.action?questionario.id=${avaliacaoTurma.questionario.id}"><img border="0" title="Questionário da avaliação" src="<@ww.url includeParams="none" value="/imgs/question.gif"/>"></a>
				<a href="../aspecto/list.action?questionario.id=${avaliacaoTurma.questionario.id}"><img border="0" title="Aspectos da avaliação" src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>"></a>
			</#if>

			<a href="javascript:;" onclick="javascript:clonar(${avaliacaoTurma.id}, '${avaliacaoTurma.questionario.titulo}');"><img border="0" title="Clonar" src="<@ww.url includeParams="none" value="/imgs/clonar.gif"/>"></a>
			
			<#if 0 < avaliacaoTurma.questionario.quantidadeDeResposta>
				<img border="0" title="Já existe resposta para este questionário, não é permitido excluir." src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			<#else>
				<a href="javascript:newConfirm('Confirma exclusão?', function(){window.location='delete.action?avaliacaoTurma.id=${avaliacaoTurma.id}&avaliacaoTurma.questionario.empresa.id=${avaliacaoTurma.questionario.empresa.id}&page=${page}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
			</#if>
			<a href="../questionario/imprimir.action?questionario.id=${avaliacaoTurma.questionario.id}"><img border="0" title="Imprimir" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
			<a href="../questionario/imprimir.action?questionario.id=${avaliacaoTurma.questionario.id}&imprimirFormaEconomica=true"><img border="0" title="Imprimir em formato econômico" src="<@ww.url includeParams="none" value="/imgs/iconPrint.gif"/>"></a>
		</@display.column>

		<@display.column property="questionario.titulo" title="Título" />
		<@display.column title="Ativa" style="width:30px">
			<#if avaliacaoTurma.ativa>
				<img border="0" title="Sim" src="<@ww.url includeParams="none" value="/imgs/flag_green.gif"/>">
			<#else>
				<img border="0" title="Não" src="<@ww.url includeParams="none" value="/imgs/flag_red.gif"/>">
			</#if>
		</@display.column>

	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
		</button>
	</div>
		
	<div id="formDialog">
		<@ww.form name="formModal" id="formModal" action="clonarAvaliacaoTurma.action" method="POST">
			<@frt.checkListBox label="Selecione as empresas para as quais deseja clonar este modelo de avaliação de curso" name="empresasCheck" list="empresasCheckList" form="document.getElementById('formModal')" filtro="true"/>
			* Caso nenhuma empresa seja selecionada, o modelo de avaliação de curso será clonado apenas para a empresa <@authz.authentication operation="empresaNome"/><br>
			<@ww.hidden name="avaliacaoTurma.id" id="avaliacaoTurmaId"/>
			<button class="btnClonar" type="submit"></button>
		</@ww.form>
	</div>
</body>
</html>