<html>
	<head>
		<@ww.head/>
		<#assign edit=false/>
		<#if configuracaoLimiteColaborador.id?exists>
			<title>Editar limite de Colaboradores por Cargo</title>
			<#assign formAction="update.action"/>
			<#assign edit=true/>
		<#else>
			<title>Configurar limite de Colaboradores por Cargo</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
	<style type="text/css">
		.listaDeLimites 
		{
			background-color:#EEE;
			border:1px solid #BEBEBE;
			color:#5C5C5A;
			padding:5px;
			width: 580px;
		}
	</style>
	
	<script type="text/javascript">
		$(function() {
			<#if edit>
				<#list quantidadeLimiteColaboradoresPorCargos as limiteCargo>
					addCargo('${limiteCargo.cargo.id}', '${limiteCargo.cargo.nome}', '${limiteCargo.limite}');
				</#list>
			<#else>
				$('.listaDeLimites').hide();

				<#list idsFamiliasAreasJaConfiguradas as areaId>
					$("#areaOrganizacionalId option[value='${areaId}']").attr("disabled","disabled").css("background-color", "#DEDEDE");	
				</#list>
			</#if>
		});
			
		function getCargos()
		{
			var areaOrganizacionalId = $("#areaOrganizacionalId").val();
			
			DWRUtil.useLoadingMessage('Carregando...');
			$("#listaCargos").empty();
			CargoDWR.getCargosByArea(areaOrganizacionalId, ${empresaId}, createListCargos);
			
			$(".listaDeLimites").toggle(areaOrganizacionalId != '');
		}

		function createListCargos(data)
		{
			$(data).each(function() {
				addCargo(this.id, this.nomeMercado);
			});
		}
		
		var i = 0;
		function addCargo(cargoId, cargoNomeMercado, limite)
		{
			if(limite == undefined)
				limite = '';
			
			$("#listaCargos").append("<tr><td><img src='<@ww.url includeParams="none" value="/imgs/delete.gif"/>' style='cursor: pointer;' onclick='removerCargo(this)' title='Remover cargo'/></td><td id='cargo_" + i + "'></td><td>" + cargoNomeMercado + "</td></tr>");
			
			$("#cargo_" + i).append('<input type="text" name="' + 'quantidadeLimiteColaboradoresPorCargos['+ i +'].limite' + '" value="' + limite + '" style="text-align:right;" onkeypress="return somenteNumeros(event,\"\")" id="c_' + i + '" class="valida" size="4" maxlength="3"/>');
			$("#cargo_" + i).append('<input type="hidden" name="' + 'quantidadeLimiteColaboradoresPorCargos['+ i +'].cargo.id' + '" value="' + cargoId + '"/>');
		
			i++;
		}
		
		function removerCargo(obj)
		{
			$(obj).parent().parent().remove();
		}
		
		function novoCargo()
		{
			var cargoSelecionadoId = $('#cargoId option:selected').val();
			if (cargoSelecionadoId == '')
			{
				jAlert('Nenhum cargo selecionado.');
			}
			else
			{
				if($('#listaCargos').find(':hidden[value=' + cargoSelecionadoId + ']').size() == 0)
					addCargo(cargoSelecionadoId, $('#cargoId option:selected').text());
				else
					jAlert('Este Cargo já foi adicionado.');		
			}
		}
		
		function salvarLimites()
		{
			$('#listaCargos').find(":text[value!='']").css('background-color','#FFF');
			
			if(validaFormulario('form', $(".valida").map(function(){return $(this).attr('id');}), null, true))
			{
				if ($('#listaCargos').find(":text").size() == 0)
					jAlert('Nenhum Cargo configurado. Adicione cargo(s) à lista de limites de Colaborador por Cargo.');
				else
					$('#form').submit();				
			}
		}
		
	</script>
	
	</head>
	<body>
		<@ww.actionmessage />
		<@ww.actionerror />
		
		<@ww.form name="form" id="form" action="${formAction}" method="POST">
			<@ww.hidden name="configuracaoLimiteColaborador.id" />
			<@ww.token/>
			<@ww.textfield label="Contrato" name="configuracaoLimiteColaborador.descricao" id="descricao" required="true" cssClass="valida" maxLength="100" cssStyle="width: 591px;"/>
			
			<#if edit>
				<@ww.hidden name="configuracaoLimiteColaborador.areaOrganizacional.id" />
				Área Organizacional: ${configuracaoLimiteColaborador.areaOrganizacional.descricao}<br><br>
			<#else>
				<@ww.select label="Área Organizacional" name="configuracaoLimiteColaborador.areaOrganizacional.id" id="areaOrganizacionalId" cssClass="valida" onchange="getCargos()" required="true" list="areaOrganizacionais" cssStyle="width: 593px;" listKey="id" listValue="descricao" headerKey="" headerValue="Selecione..." />
			</#if>
			
			<div class="listaDeLimites">
				<table>
					<thead>
						<tr>
							<td> </td>
							<td></td>
							<td>Lista de Limites de Colaborador por Cargo</td>
						</tr>
					</thead>
					<tbody id="listaCargos"></tbody>
				</table>
				<br>
				<@ww.select name="cargo" id="cargoId" list="cargos" cssStyle="width: 452px;" listKey="id" listValue="nomeMercado" 
				headerKey="" headerValue="Selecione o cargo que você deseja adicionar à lista de limites..." liClass="liLeft" />
				
				<a href="javascript:;" onclick="javascript:novoCargo();" style="text-decoration: none;">
					<img src='<@ww.url includeParams="none" value="/imgs/mais.gif"/>'  align="absmiddle" border="0"/> 
					Adicionar Cargo
				</a>
			</div>
		</@ww.form>
		
		<div style="clear: both"></div>
		<div class="buttonGroup">
			<button onclick="salvarLimites();" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
