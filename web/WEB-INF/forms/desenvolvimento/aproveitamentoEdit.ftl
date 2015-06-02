<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>
	<title>Avaliações dos Alunos</title>

	<script type='text/javascript'>
		var alterouCampo = false;
		var valorCampo = "";

		function verificaEdicao()
		{
			if (alterouCampo)
				newConfirm('Você alterou alguns campos, deseja mudar de avaliação sem gravar?', function(){document.formFiltro.submit();});
			else
				document.formFiltro.submit();
		}

		function setValor(valor)
		{
			valorCampo = valor;
		}

		function verificaValor(valor)
		{
			alterouCampo = valorCampo != valor;
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="formFiltro" action="prepareAproveitamento.action" onsubmit="javascript:verificaEdicao();" method="POST">
		<@ww.select label="Avaliação" name="avaliacaoCurso.id" list="avaliacaoCursos" id="avaliacao" listKey="id" listValue="titulo"  headerKey="-1" headerValue="Selecione..." onchange="javascript:verificaEdicao();"/>
		<@ww.hidden name="turma.id"/>
		<@ww.hidden name="curso.id"/>
	</@ww.form>

	<#if colaboradoresTurma?exists && 0 < colaboradoresTurma?size>
		<@ww.form name="form" action="saveAproveitamentoCurso.action" onsubmit="" method="POST">
			<@display.table name="colaboradoresTurma" id="colaboradorTurma" class="dados">
				<@display.column property="colaborador.nome" title="Nome" style="width: 400px;"/>
				<@display.column property="colaborador.matricula" title="Matrícula" style="width: 80px;"/>

				<@display.column title="${avaliacaoCurso.titulo}" style="width: 300px;text-align: center;">
					<#assign valorNota = "" />

					<#if avaliacaoCurso.tipo == 'a'>
						<a href="../avaliacaoCurso/prepareResponderAvaliacaoAluno.action?colaborador.id=${colaboradorTurma.colaborador.id}&avaliacaoCurso.avaliacao.id=${avaliacaoCurso.avaliacao.id}&modeloAvaliacao=L&turma.id=${turma.id}&curso.id=${curso.id}&avaliacaoCurso.id=${avaliacaoCurso.id}">
							<#if colaboradorTurma.respondeuAvaliacaoTurma>
								<img border="0" title="Editar respostas" src="<@ww.url value="/imgs/page_edit.gif"/>">
							<#else>
								<img border="0" title="Responder" src="<@ww.url value="/imgs/page_new.gif"/>">
							</#if>
						</a>
					<#else>
						<#list aproveitamentos as aproveitamento>
							<#if aproveitamento.colaboradorTurma.id == colaboradorTurma.id>
								<#assign valorNota = aproveitamento.valor />
							</#if>
						</#list>
					
						<@ww.textfield id="" name="notas" value="${valorNota}" maxLength="5" cssStyle="text-align: right;width: 40px;border:1px solid #BEBEBE;" onkeypress = "return(somenteNumeros(event,'.,,'));" onfocus="setValor(this.value);" onchange="verificaValor(this.value);"/>
					</#if>
					<@ww.hidden name="colaboradorTurmaIds" value="${colaboradorTurma.id}"/>
				</@display.column>
			</@display.table>

			<@ww.hidden name="turma.id"/>
			<@ww.hidden name="curso.id"/>
			<@ww.hidden name="avaliacaoCurso.id"/>
		</@ww.form>
	</#if>

	<div class="buttonGroup">
		<#if colaboradoresTurma?exists && 0 < colaboradoresTurma?size && avaliacaoCurso?exists && avaliacaoCurso.tipo != 'a'>
			<button class="btnGravar" onclick="document.form.submit();"></button>
		</#if>
		<button class="btnVoltar" onclick="window.location='list.action?curso.id=${curso.id}'"></button>
	</div>
</body>
</html>