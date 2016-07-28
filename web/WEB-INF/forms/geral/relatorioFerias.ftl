<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>

	<title>Férias</title>
	<#if dataIni?exists>
		<#assign valueDataIni = dataInicioGozo?date/>
	<#else>
		<#assign valueDataIni = "  /  /    "/>
	</#if>

	<#if dataFim?exists>
		<#assign valueDataFim = dataFimGozo?date/>
	<#else>
		<#assign valueDataFim = "  /  /    "/>
	</#if>

	<#include "../ftl/mascarasImports.ftl" />
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	<script type='text/javascript'>

		var areasIds = new Array();
		<#if areasIds?exists>
			<#list areasIds as areaId>
				areasIds.push(${areaId});
			</#list>
		</#if>
		
		function populaColaboradores()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var situacao = $('#situacao').val();
			
			ColaboradorDWR.getPermitidosPorResponsavelCoresponsavel(createListcolaborador, ${usuarioLogado.id}, areasIds, ${empresaSistema.id}, situacao, true);
		}

		function createListcolaborador(data)
		{
			addChecksByMap('colaboradoresCheck',data)
		}
	
		function validarCampos()
		{
			return validaFormularioEPeriodo('form', new Array('@colaboradoresCheck'), new Array('dataInicioGozo','dataFimGozo'));
		}
	
		function populaChecks()
		{
			DWREngine.setAsync(false);
			populaColaboradores();
		}
			
		$(function()
		{
			populaChecks();
			
			$('#imprimirFeriasGozadas').change(function() {
				var marcado = $(this).is(":checked");
				if(!marcado){
					$('#dataInicioGozo,#dataFimGozo').val('  /  /    ');
				}
				$('#periodoFeriasGozadas').toggle( marcado );
			});
			
			$('#periodoFeriasGozadas').hide();
			
			if(areasIds.length == 0){
				$('#wwlbl_situacao').hide();
				$('#situacao').hide();
			}
		});
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@ww.form name="form" action="imprimeRelatorioFerias.action" validate="true" method="POST">
		
		<fieldset style="padding: 5px 0px 5px 5px; width: 495px;">
			<legend>Colaboradores</legend>
			<@ww.select label="Situação" name="situacao" id="situacao" list="situacaos" onchange="populaColaboradores();"/>
			<@frt.checkListBox id="colaboradoresCheck" name="colaboradoresCheck" label="Colaboradores*" list="colaboradoresCheckList" width="585" height="180" filtro="true"/>
		</fieldset>
		
		<fieldset class="fieldsetFeriasGozadas" style="width:578px; margin-bottom: 10px;">
			<legend>Férias Gozadas</legend>
			
			<@ww.checkbox label="Imprimir férias já gozadas" name="imprimirFeriasGozadas" id="imprimirFeriasGozadas" labelPosition="left"/>

			<div id="periodoFeriasGozadas">
				<div>Período:</div>
				<@ww.datepicker name="dataInicioGozo" id="dataInicioGozo" liClass="liLeft" value="${valueDataIni}" cssClass="mascaraData validaDataIni"/>
				<@ww.label value="a" liClass="liLeft"/>
				<@ww.datepicker name="dataFimGozo" id="dataFimGozo" value="${valueDataFim}" cssClass="mascaraData validaDataFim"/>
			</div>

		</fieldset>
		<br />
	</@ww.form>

	<div class="buttonGroup">
		<button type="button" onclick="validarCampos()" class="btnRelatorio"></button>
	</div>
<br><br>

</body>
</html>