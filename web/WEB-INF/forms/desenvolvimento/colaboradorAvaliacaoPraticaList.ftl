<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>

	<title>ColaboradorAvaliacaoPratica</title>
</head>
<body>
	<#assign validarCampos="return validaFormulario('form', null, null)"/>

	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" action="buscaColaboradores.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<li>
			<@ww.div cssClass="divInfo" cssStyle="width: 950px;">
				<ul>
					<@ww.select label="Certificações" name="certificacao.id" id="colab" list="certificacoes" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." onchange="document.form.submit();" />
					<@ww.select label="Colaborador" name="colaborador.id" id="colab" list="colaboradores" listKey="id" listValue="nomeCpf" headerKey="" headerValue="Selecione..." onchange="document.form.submit();" />
					<br><br>
				</ul>
			</@ww.div>
		</li>
	</@ww.form>
	
	<#if colaborador?exists && colaborador.id?exists>

		<br/>		
		<@display.table name="colaboradorAvaliacaoPraticas" id="colaboradorAvaliacaoPratica" class="dados">
			<@display.column property="avaliacaoPratica.titulo" title="Título" style="width: 300px;"/>
			<@display.column property="avaliacaoPratica.notaMinima" title="Nota Mínima" />
			<@display.column title="Nota" style="width: 300px;text-align: center;">
				<@ww.textfield id="" name="nota" value="${nota}" maxLength="5" cssStyle="text-align: right;width: 40px;border:1px solid #BEBEBE;" onkeypress = "return(somenteNumeros(event,'.,,'));" onfocus="setValor(this.value);" onchange="verificaValor(this.value);"/>
			</@display.column>
		</@display.table>
		
		<br/>
		
		<@display.table name="colaboradorTurmas" id="colaboradorTurma" class="dados">
			<@display.column property="curso.nome" title="Curso" style="width: 300px;"/>
			<@display.column property="turma.descricao" title="Turma" />
			<@display.column title="Período" style="text-align:center; width:180px">
				${colaboradorTurma.turma.dataPrevIni?string("dd'/'MM'/'yyyy")} - ${colaboradorTurma.turma.dataPrevFim?string("dd'/'MM'/'yyyy")}
			</@display.column>
			<@display.column property="turma.realizadaFormatada" title="Realizada" style="width: 50px;" />
			<@display.column property="aprovadoMaisNota" title="Aprovado" style="width: 50px;" />
		</@display.table>
	
		<div class="buttonGroup">
			<button class="btnInserir" onclick="window.location='prepareInsertAvaliacaoExperiencia.action?colaboradorQuestionario.colaborador.id=${colaborador.id}'"></button>
		</div>
	</#if>	
</body>
</html>
