<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<title>Cartões para Acompanhamento do Período de Experiência</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript'>
		$(function() {
			populaColaboradores();
		});
	
		function populaColaboradores()
		{
			ColaboradorDWR.getColaboradoresByEstabelecimentoDataAdmissao(createListColaborador, $('#estabelecimentoId').val(), $('#data').val());
		}
		
		function createListColaborador(data)
		{
			addChecks('colaboradorsCheck',data);
		}
	</script> 

	<#assign validarCampos="return validaFormulario('form', new Array('data','@colaboradorsCheck'), new Array('data'))"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<#include "../ftl/mascarasImports.ftl" />

		<@ww.form name="form" action="imprimeCartoesAcompanhamentoPeriodoExperiencia.action" onsubmit="${validarCampos}" method="POST">
			<@ww.datepicker label="Admitidos a partir de" onchange="populaColaboradores();" id="data" name="dataReferencia" required="true" value="${dataDoDia}" cssClass="mascaraData"/>
			<@ww.select label="Estabelecimento" onchange="populaColaboradores();" name="estabelecimento.id" id="estabelecimentoId" listKey="id" listValue="nome" headerKey="-1" headerValue="Todos" list="estabelecimentos"/>
			<@frt.checkListBox name="colaboradorsCheck" label="Colaborador*" list="colaboradorsCheckList" />						
			<@frt.checkListBox name="periodoCheck" label="Período de Acompanhamento*" list="periodoCheckList" />	
			<@ww.textarea label="Observação" name="observacoes" cssStyle="width: 500px"/>				
		</@ww.form>

		<div class="buttonGroup">
			<button class="btnRelatorio" onclick="${validarCampos};" ></button>
		</div>
</body>
</html>