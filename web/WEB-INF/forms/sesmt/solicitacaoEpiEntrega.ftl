<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		input {
			border: 1px solid #BEBEBE !important;
		}
		
		.dados a
		{
			text-decoration: none;
			font-size: 10px !important;
		}
		.dados a:hover
		{
			text-decoration: none;
			color: #000;
		}

	</style>

	<#include "../ftl/mascarasImports.ftl" />
	<title>Entrega de EPIs</title>
	
	<#-- Não apague, da um erro que nem o babau achou, e ainda mais nem sempre acontece, logo não apague (erro no stack.setValue("#datepicker_js_included", true)) -->
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/webwork/jscalendar/" encode='false'/>calendar.js"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/webwork/jscalendar/lang/" encode='false'/>calendar-${parameters.language?default("en")}.js"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/webwork/jscalendar/" encode='false'/>calendar-setup.js"></script>
	<#-- Não apague, da um erro que nem o babau achou, e ainda mais nem sempre acontece, logo não apague (erro no stack.setValue("#datepicker_js_included", true)) -->
	
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

	<h4> Colaborador: ${solicitacaoEpi.colaborador.nomeDesligado}<br/>Data Solicitação: ${solicitacaoEpi.data?string('dd/MM/yyyy')}</h4>
	<@ww.hidden name="solicitacaoEpi.colaborador.id" />
	<div>EPIs:</div>

	<table class="dados">
		<thead>
			<tr>
				<th style="background:#7BA6D3;" colspan="2">Itens da Solicitação</th>
				<th style="background:#7BA6D3;" colspan="3">Histórico de Entregas</th>
			</tr>
			<tr>
				<th>Epi</th>
				<th>Qtd. Solicitada</th>
				<th width="50">Ações</th>
				<th width="100">Data</th>
				<th width="100">Qtd. Entregue</th>
			</tr>
		</thead>
		<tbody>
			<#assign i = 0/>
			<#list solicitacaoEpiItems as item>
				<tr class="<#if i%2 == 0>odd<#else>even</#if>">
					<td valign="top">
						${item.epi.nomeInativo}
					</td>
					<td valign="top" width="100" align="right">
						${item.qtdSolicitado}
					</td>
					<td colspan="3" align="center" style="padding:0px">
						<#if item.solicitacaoEpiItemEntregas?exists && 0 < item.solicitacaoEpiItemEntregas?size>
							<table style="margin:0px !important;">
								<tbody>
									<#list item.solicitacaoEpiItemEntregas as entrega>
										<tr>
											<td width="50" align="center">
												<a href="prepareUpdateEntrega.action?solicitacaoEpi.id=${solicitacaoEpi.id}&solicitacaoEpiItemEntrega.id=${entrega.id}&solicitacaoEpiItem.id=${item.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
												<a href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){window.location='deleteEntrega.action?solicitacaoEpi.id=${solicitacaoEpi.id}&solicitacaoEpiItemEntrega.id=${entrega.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
											</td>
											<td width="100" align="center">${entrega.dataEntrega}</td>
											<td width="100" align="right">${entrega.qtdEntregue}</td>
										</tr>
									</#list>
								</tbody>
							</table>
						</#if>
						
						<#if item.totalEntregue < item.qtdSolicitado>
							<a href="prepareInsertEntrega.action?solicitacaoEpi.id=${solicitacaoEpi.id}&solicitacaoEpiItem.id=${item.id}">
								<img title="Inserir entrega" src="<@ww.url includeParams="none" value="/imgs/add.png"/>" border="0" align="absMiddle" /> 
								Inserir entrega
							</a>
						<#else>
							<a>
								<img title="Todos os itens foram entregues" src="<@ww.url includeParams="none" value="/imgs/info.png"/>" border="0" align="absMiddle" /> 
								Todos os itens foram entregues
							</a>
						</#if>
					</td>
				<tr>
				<#assign i = i + 1/>
			</#list>
		</tbody>
	</table>
	
	<div class="buttonGroup">
		<button onclick="window.location='list.action'" class="btnVoltar"></button>
	</div>
</body>
</html>