<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />

<html>
<head>
<@ww.head/>
	<title>Respostas das Minhas Avaliações</title>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
	</style>
</head>
<body>
	<#assign validarCampos="return validaFormulario('form', null, null)"/>

	<@ww.actionmessage />
	<@ww.actionerror />

	<@display.table name="colaboradorQuestionarios" id="colaboradorQuestionario" class="dados">
		<@display.column title="Ações" class="acao">
			<#if colaboradorQuestionario.avaliacao.tipoModeloAvaliacao == 'A'>
				<a href="javascript: executeLink('../avaliacaoExperiencia/prepareUpdateAvaliacaoExperiencia.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}&autoAvaliacao=true');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<#else>
				<a href="javascript: executeLink('../desempenho/prepareResponderAvaliacaoDesempenho.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}&autoAvaliacao=true');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			</#if>
		</@display.column>
		<@display.column property="respondidaEm" title="Data" format="{0,date,dd/MM/yyyy}" style="width: 100px; text-align: center;"/>
		<@display.column property="avaliacao.titulo" title="Avaliação" />
		<@display.column property="descricaoModeloAvaliacao" title="Tipo da Avaliação" />
	</@display.table>

</body>
</html>