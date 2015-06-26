<html>
<head>
<@ww.head/>
<#if questionario.id?exists>
	<#assign complementoTitulo=""/>

	<#if questionario.anonimo>
		<#assign complementoTitulo="(${tipoQuestionario.getDescricaoMaisc(questionario.tipo)} anônima)"/>
	</#if>

	<title>Responder ${tipoQuestionario.getDescricaoMaisc(questionario.tipo)} - ${questionario.titulo} ${complementoTitulo}</title>
	<#assign formAction="salvaRespostaPesquisa.action"/>
	<#assign retorno="../../"/>

	<#if voltarPara?exists>
		<#assign retorno=voltarPara/>
	</#if>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/perguntasAvaliacao.js?version=${versao}"/>'></script>
	<script type='text/javascript'>
		function exibirQuestionario() {
			var colaboradorId = $('#colaborador').val();
			
			if (colaboradorId != '') {
				var link = "prepareResponderQuestionarioPorOutroUsuario.action?questionario.id=${questionario.id}&colaborador.id=" + colaboradorId + "&validarFormulario=true&voltarPara=../colaboradorResposta/prepareResponderQuestionarioEmLote.action?questionario.id=${questionario.id}&tela=lote";
				window.location.href = link;
			
			} else {
				jAlert('Selecione um colaborador');
				$('#colaborador').focus();
			}
		}
	</script>
</#if>

</head>
<body>
	<@ww.actionmessage />
	<@ww.form>
		<@ww.select label="Colaborador com pesquisa não respondida" id="colaborador" listKey="id" listValue="nome" list="colaboradors"/>
	</@ww.form>

	<button onclick="exibirQuestionario()" class="btnAvancar"></button>
	<button onclick="window.location='../colaboradorQuestionario/list.action?questionario.id=${questionario.id}'" class="btnVoltar"></button>
</body>
</html>