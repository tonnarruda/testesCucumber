<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Inserir Colaborador + Nota</title>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	
	<#assign validarCampos="return validaFormulario('form', new Array('colaborador'), null)"/>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<script type='text/javascript'>
		var empresaIds = new Array();
		<#if empresas?exists>
			<#list empresas as empresa>
				empresaIds.push(${empresa.id});
			</#list>
		</#if>
		
		function populaColaborador()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			ColaboradorDWR.find(createListColaborador, $('#nome').val(), "", $('#matricula').val(), $('#empresaId').val(), true, empresaIds);
		}
		
		function createListColaborador(data)
		{
			DWRUtil.removeAllOptions('colaborador');
			$('#colaborador').append('<option value=\"\">Selecione...</option>');
			DWRUtil.addOptions("colaborador", data);
		}
		
		function populaNotas(colaboradorId)
		{
			$('input[id^="nota_"]').val('');
			
			var urlFind = "<@ww.url includeParams="none" value="/desenvolvimento/colaboradorTurma/findNotas.action"/>";
			
			$.getJSON(urlFind,
			{
	    		cursoId:"${turma.curso.id}",
	    		colaboradorId:colaboradorId
	  		},
	  		function(data) {
		  		$.each(data, function(i,item){
	            	$("#nota_" + item.avaliacaoCurso.id).val(item.valor);
	          	});
	  		});
		}
		
	</script>
</head>
<body>

	<@ww.actionmessage />
	<@ww.actionerror />
	<@ww.form name="form" action="insertColaboradorNota.action" onsubmit="${validarCampos}" validate="true" method="POST">
	
	<div class="divFiltro">
	    <@ww.select label="Empresa" name="empresaId" id="empresaId" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="-1" disabled="!compartilharColaboradores"/>
		<@ww.textfield label="Nome do Colaborador" id="nome" name="colaborador.nome" maxLength="100" cssStyle="width: 500px;" />
		<@ww.textfield label="Matrícula do Colaborador" id="matricula" name="colaborador.matricula" maxLength="20" cssStyle="width: 170px;"/>

		<a href="#" onclick="populaColaborador();"><img border="0" src="<@ww.url value="/imgs/btnPesquisar.gif"/>"></a><br><br>
	</div>
	
		<@ww.select label="Colaborador (Nome - CPF - Matrícula)" name="colaborador.id" id="colaborador" list="colaboradors" listKey="id" listValue="nomeCpfMatricula" required="true" cssStyle="width: 500px;" headerKey="" headerValue="Utilize o filtro..." onchange="javascript: populaNotas(this.value);"/><br>
		
		<#if avaliacaoCursos?exists && 0 < avaliacaoCursos?size>
			<@display.table name="avaliacaoCursos" id="avaliacaoCurso" class="dados">
				<@display.column property="titulo" title="Avaliação" style="width: 400px;"/>

				<@display.column title="Nota" style="width: 60px;text-align: center;">
					<@ww.textfield id="nota_${avaliacaoCurso.id}" name="notas" value="" maxLength="5" cssStyle="text-align: right;width: 40px;border:1px solid #BEBEBE;" onkeypress="return(somenteNumeros(event,'.,,'));"/>
					<@ww.hidden name="avaliacaoCursoIds" value="${avaliacaoCurso.id}"/>
				</@display.column>
			</@display.table>
		</#if>
		
		<@ww.hidden name="planoTreinamento" />
		<@ww.hidden name="turma.id" />
		<@ww.hidden name="turma.curso.id"/>
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="${validarCampos};"> </button>
		<button onclick="window.location='list.action?turma.id=${turma.id}&curso.id=${turma.curso.id}&planoTreinamento=${planoTreinamento?string}'" class="btnVoltar"></button>
	</div>
</body>
</html>