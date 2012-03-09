<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		input {
			border: 1px solid #7E9DB9 !important;
		}
	</style>

	<#include "../ftl/mascarasImports.ftl" />
	<title>Entrega de EPIs</title>
	
	<!-- Não apague, da um erro que nem o babau achou, e ainda mais nem sempre acontece, logo não apague (erro no stack.setValue("#datepicker_js_included", true)) -->
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/webwork/jscalendar/" encode='false'/>calendar.js"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/webwork/jscalendar/lang/" encode='false'/>calendar-${parameters.language?default("en")}.js"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/webwork/jscalendar/" encode='false'/>calendar-setup.js"></script>
	<!-- Não apague, da um erro que nem o babau achou, e ainda mais nem sempre acontece, logo não apague (erro no stack.setValue("#datepicker_js_included", true)) -->

	<script type='text/javascript'>
		$(function() {
			$('.mascaraData').attr('disabled', true);
			$('.qtdSolicitacao').attr('disabled', true);
			$('a[id^="selectDataSolicitado_"]').hide();
			
			$('.checkItem').change(function() {
				$(this).parent().parent().find('.mascaraData , .qtdSolicitacao').attr('disabled', !$(this).attr('checked'));
				$(this).parent().parent().find('a').toggle();
			});
	
			$('#md').change(function() {
				var valueCheck = $(this).attr('checked')
				$('.checkItem').attr('checked', valueCheck);
				
				$('.mascaraData').attr('disabled', !valueCheck);
				$('.qtdSolicitacao').attr('disabled', !valueCheck);
				
				if(valueCheck)
					$('a[id^="selectDataSolicitado_"]').show();
				else
					$('a[id^="selectDataSolicitado_"]').hide();
			});
		});
	</script>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" action="entrega.action" method="POST">
		<h4> Colaborador: ${solicitacaoEpi.colaborador.nome}<br/>Data Solicitação: ${solicitacaoEpi.data?string('dd/MM/yyyy')}</h4>
		<@ww.hidden name="solicitacaoEpi.colaborador.id" />
		<div>EPIs:</div>

		<#assign i = 0/>
		<@display.table name="listaEpis" id="lista" class="dados" defaultsort=2 sort="list">

			<@display.column title="<input type='checkbox' id='md' />" style="width: 30px; text-align: center;">
				<input type="checkbox" value="${lista[0].id}" name="epiIds" class="checkItem" id="${lista[0].id}"/>
			</@display.column>

			<@display.column title="EPI" style="width:500px;">
				${lista[0].nome}
				
				<br />
				
				<#list lista[1].solicitacaoEpiItemEntregas as entrega>
					${entrega.qtdEntregue} - ${entrega.dataEntrega}
				</#list>
			</@display.column>

		</@display.table>
		
		<@ww.hidden name="solicitacaoEpi.id" />
		<@ww.hidden name="solicitacaoEpi.empresa.id" />
		<@ww.hidden name="solicitacaoEpi.data" />
		<@ww.hidden name="solicitacaoEpi.cargo.id" />
		<@ww.hidden name="solicitacaoEpi.entregue" />
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="document.forms[0].submit();" class="btnGravar"></button>
		<button onclick="window.location='list.action'" class="btnVoltar"></button>
	</div>
</body>
</html>