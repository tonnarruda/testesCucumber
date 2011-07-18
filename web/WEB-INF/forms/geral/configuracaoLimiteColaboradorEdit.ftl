<html>
	<head>
		<@ww.head/>
		<#if configuracaoLimiteColaborador.id?exists>
			<title>Editar limite de Colaboradores por Cargo</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Configurar limite de Colaboradores por Cargo</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
	<script type="text/javascript">
			
		function getCargos()
		{
			var areaOrganizacionalId = $("#areaOrganizacionalId").val();
			
			DWRUtil.useLoadingMessage('Carregando...');
			$("#listaCargos").empty();
			CargoDWR.getCargosByArea(areaOrganizacionalId, ${empresaId}, createListCargos);
		}

		function createListCargos(data)
		{
			$(data).each(function() {
				addCargo(this.id, this.nomeMercado);
			});
		}
		
		var i = 0;
		function addCargo(cargoId, cargoNomeMercado)
		{
			$("#listaCargos").append("<tr><td>" + cargoNomeMercado + "</td><td id='cargo_" + i + "'></td></tr>");
			
			$('<input>').attr({
			    type: 'text',
			    name: 'quantidadeLimiteColaboradoresPorCargos['+ i +'].limite',
			    size: 4,
			    maxlength: 3
			})
			.css('text-align', 'right')
			.keypress(function(event) {
				return somenteNumeros(event,"");  
			})
			.appendTo("#cargo_" + i);
			
			$('<input>').attr({
			    type: 'hidden',
			    name: 'quantidadeLimiteColaboradoresPorCargos['+ i +'].cargo.id'
			}).val(cargoId).appendTo("#cargo_" + i);
		
			i++;
		}
	</script>
	
	
	<#assign validarCampos="return validaFormulario('form', new Array())"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="configuracaoLimiteColaborador.id" />
			<@ww.token/>
			<@ww.textfield label="Descrição" name="configuracaoLimiteColaborador.descricao" id="descricao" required="true" cssClass="inputNome" maxLength="100"/>
			<@ww.select label="Área Organizacional" name="configuracaoLimiteColaborador.areaOrganizacional.id" id="areaOrganizacionalId" onchange="getCargos()" required="true" list="areaOrganizacionais" cssStyle="width: 447px;" listKey="id" listValue="descricao" headerKey="" headerValue="Selecione..." />
			
			CUIDADO com eesse $("#listaCargos").empty(); no JS
			<table id="listaCargos" cellspacing="5"></table>
			
			<a href="javascript:;" onclick="javascript:addCargo(222, 'novo Cargo ##');" style="text-decoration: none;">
				<img src='<@ww.url includeParams="none" value="/imgs/mais.gif"/>'/> 
				Inserir cargos
			</a>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
