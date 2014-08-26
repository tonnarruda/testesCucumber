<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />

<html>
<head>
<@ww.head/>
	<title>Atualizar Modelo de Avaliação</title>

</head>
<body>
	<@ww.actionerror />
	
	<@ww.form id="form" action="atualizarModeloAvaliacao" name="form" method="POST">
		<div>
			<fieldset>
				<legend>Colaborador</legend>
				<#assign i = 0 />
				<@display.table name="periodoExperiencias" id="periodoExperiencia" class="dados">
					<@display.column title="Dias" property="dias" style="width:80px;" />
					<@display.column title="Modelo do Acompanhamento do Período de Experiência">
						<@ww.hidden name="colaboradorAvaliacoes[${i}].periodoExperiencia.id" value="${periodoExperiencia.id}" />
						<@ww.select theme="simple" id="modeloPeriodo${periodoExperiencia.id}" list="colaboradorAvaliacoes" listValue="avaliacao.titulo" headerKey="" headerValue="Selecione" cssStyle="width: 750px;"/>
					</@display.column>
					
					<#assign i = i + 1 />
				</@display.table>
			</fieldset>

			<br />
<!--
			<fieldset>
				<legend>Gestor</legend>
				<#assign i = 0 />
				<@display.table name="periodoExperiencias" id="periodoExperiencia" class="dados">
					<@display.column title="Dias" property="dias" style="width:80px;" />
					<@display.column title="Modelo do Acompanhamento do Período de Experiência">
						<@ww.hidden name="colaboradorAvaliacoesGestor[${i}].periodoExperiencia.id" value="${periodoExperiencia.id}" />
						<@ww.select theme="simple" name="colaboradorAvaliacoesGestor[${i}].avaliacao.id" id="modeloPeriodoGestor${periodoExperiencia.id}" headerKey="" headerValue="Selecione" cssStyle="width: 750px;"/>
					</@display.column>
					
					<#assign i = i + 1 />
				</@display.table>
			</fieldset>
-->
	    </div>
	</@ww.form>
</body>
</html>