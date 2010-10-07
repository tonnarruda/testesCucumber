<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Inserir Colaborador + Nota</title>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>
	
	<#assign validarCampos="return validaFormulario('form', new Array('colaborador'), null)"/>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
	<script type='text/javascript'>
		function populaColaborador()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			ColaboradorDWR.find(createListColaborador, jQuery('#nome').val(), "", jQuery('#matricula').val(), <@authz.authentication operation="empresaId"/>, true);
		}
		function createListColaborador(data)
		{
			DWRUtil.removeAllOptions('colaborador');
			jQuery('#colaborador').append('<option>Selecione...</option>');
			DWRUtil.addOptions("colaborador", data);
		}
	</script>
</head>
<body>

	<@ww.actionmessage />
	<@ww.actionerror />
	<@ww.form name="form" action="insertColaboradorNota.action" onsubmit="${validarCampos}" validate="true" method="POST">
	
	<div class="divFiltro">
		<@ww.textfield label="Nome do Colaborador" id="nome" name="colaborador.nome" maxLength="100" cssStyle="width: 500px;" />
		<@ww.textfield label="Matrícula do Colaborador" id="matricula" name="colaborador.matricula" maxLength="20" cssStyle="width: 170px;"/>

		<a href="#" onclick="populaColaborador();"><img border="0" src="<@ww.url value="/imgs/btnPesquisar.gif"/>"></a><br><br>
	</div>
	
	
		<@ww.select label="Colaborador (Nome - CPF - Matrícula)" name="colaborador.id" id="colaborador" list="colaboradors" listKey="id" listValue="nomeCpfMatricula" required="true" cssStyle="width: 500px;" headerKey="" headerValue="Utilize o filtro..."/><br>
		
		<#if avaliacaoCursos?exists && 0 < avaliacaoCursos?size>
			<@display.table name="avaliacaoCursos" id="avaliacaoCurso" class="dados">
				<@display.column property="titulo" title="Avaliação" style="width: 400px;"/>

				<@display.column title="Nota" style="width: 60px;text-align: center;">
					<@ww.textfield id="" name="notas" value="" maxLength="5" cssStyle="text-align: right;width: 40px;border:1px solid #7E9DB9;" onkeypress="return(somenteNumeros(event,'.,,'));"/>
					<@ww.hidden name="avaliacaoCursoIds" value="${avaliacaoCurso.id}"/>
				</@display.column>
			</@display.table>
		</#if>
		
		<@ww.hidden name="planoTreinamento" />
		<@ww.hidden name="turma.id" />
		<@ww.hidden name="turma.curso.id" />
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="${validarCampos};"> </button>
		<button onclick="window.location='list.action?turma.id=${turma.id}&curso.id=${turma.curso.id}&planoTreinamento=${planoTreinamento?string}'" class="btnVoltar"></button>
	</div>
</body>
</html>