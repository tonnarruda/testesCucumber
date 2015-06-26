<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Cartões para Acompanhamento do Período de Experiência</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript'>
		$(function() {
			populaColaboradores();
			$('#wwctrl_periodoCheck * span').eq(0).removeAttr('onclick').css('color', '#6E7B8B').css('cursor', 'default');
		});
	
		function populaColaboradores()
		{
			ColaboradorDWR.getColaboradoresByEstabelecimentoDataAdmissao(createListColaborador, $('#estabelecimentoId').val(), $('#data').val(), <@authz.authentication operation="empresaId"/>);
		}
		
		function createListColaborador(data)
		{
			addChecks('colaboradorsCheck',data);
		}
		
		function validaQtd()
		{
		    if($("input[name='periodoCheck']:checked").size() >= 6)
		        $("input[name='periodoCheck']").not(':checked').attr('disabled','disabled').parent().css('color', '#DEDEDE');
		    else
		        $("input[name='periodoCheck']").removeAttr('disabled').parent().css('color', '#5C5C5A');
		}
	</script> 

	<#assign validarCampos="return validaFormulario('form', new Array('data','@colaboradorsCheck','@periodoCheck'), new Array('data'))"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<#include "../ftl/mascarasImports.ftl" />

		<@ww.form name="form" action="imprimeCartoesAcompanhamentoPeriodoExperiencia.action" onsubmit="${validarCampos}" method="POST">
			<@ww.datepicker label="Admitidos a partir de" onchange="populaColaboradores();" id="data" name="dataReferencia" required="true" value="${dataDoDia}" cssClass="mascaraData"/>
			<@ww.select label="Estabelecimento" onchange="populaColaboradores();" name="estabelecimento.id" id="estabelecimentoId" listKey="id" listValue="nome" headerKey="-1" headerValue="Todos" list="estabelecimentos"/>
			<@frt.checkListBox name="colaboradorsCheck" label="Colaborador*" list="colaboradorsCheckList" filtro="true" />						
			<@frt.checkListBox name="periodoCheck" label="Período de Acompanhamento (máx. 6 opções) *" list="periodoCheckList" onClick="validaQtd();" filtro="true"/>	
			<@ww.textarea label="Observação" name="observacoes" cssStyle="width: 500px"/>				
		</@ww.form>

		<div class="buttonGroup">
			<button class="btnRelatorio" onclick="${validarCampos};" ></button>
		</div>
</body>
</html>