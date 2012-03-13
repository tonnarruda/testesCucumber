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

		<table class="dados">
			<thead>
				<tr>
					<th>Epi</th>
					<th>Entregas</th>
				</tr>
			</thead>
			<tbody>
				<#list solicitacaoEpiItems as item>
					<tr>
						<td>${item.epi.nome}</td>
						<td width="300" style="padding:0px">
							<table class="dados" style="margin:0px !important;">
								<thead>
									<tr>
										<th style="background:#7BA6D3;">Ações</th>
										<th style="background:#7BA6D3;">Data</th>
										<th style="background:#7BA6D3;">Quantidade</th>
									</tr>
								</thead>
								<tbody>
									<#list item.solicitacaoEpiItemEntregas as entrega>
										<tr>
											<td width="20%" align="center"># X</td>
											<td width="40%" align="center">${entrega.dataEntrega}</td>
											<td width="40%" align="center">${entrega.qtdEntregue}</td>
										</tr>
									</#list>
								</tbody>
							</table>
						</td>
					<tr>
				</#list>
			</tbody>
		</table>
		
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