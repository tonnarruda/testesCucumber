<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<#if historicoColaborador?exists && historicoColaborador.id?exists>
		<title>Editar Situação do Colaborador - ${historicoColaborador.colaborador.nome}</title>
		<#assign formAction="update.action"/>
	<#else>
		<title>Inserir Situação do Colaborador - ${historicoColaborador.colaborador.nome}</title>
		<#assign formAction="insert.action"/>
		<#assign data = historicoColaborador.data?date/>
	</#if>

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/interface/FuncaoDWR.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/interface/AmbienteDWR.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ReajusteDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/engine.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/util.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/indice.js"/>"></script>

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/formataValores.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/areaOrganizacional.js"/>"></script>
	
	<script type="text/javascript">
		function populaFuncao(faixaId, funcaoId)
		{
			funcId = funcaoId;
			if(faixaId != "null" && faixaId != "")
			{
				DWRUtil.useLoadingMessage('Carregando...');
				FuncaoDWR.getFuncaoByFaixaSalarial(function(data){createListFuncao(data, funcaoId);
											}, faixaId, funcaoId);
			}
		}

		function createListFuncao(data, funcaoId)
		{
			DWRUtil.removeAllOptions("funcao");
			DWRUtil.addOptions("funcao", data);

			if(funcaoId != null)
				document.getElementById('funcao').value = funcaoId;
		}

		function populaAmbiente(estabelecimentoId, ambienteId)
		{
			if(estabelecimentoId != "null")
			{
				DWRUtil.useLoadingMessage('Carregando...');
				AmbienteDWR.getAmbienteByEstabelecimento(function(data){createListAmbiente(data, ambienteId);
															}, estabelecimentoId, ambienteId);
			}
		}

		function createListAmbiente(data, ambId)
		{
			DWRUtil.removeAllOptions("ambiente");
			DWRUtil.addOptions("ambiente", data);

			if(ambId != null)
				document.getElementById('ambiente').value = ambId;
		}

		var tipoSalario;
		var  faixaSalarialId;
		var indiceId;
		var quantidade;
		var salario;

		function calculaSalario()
		{
			var dataHistorico = document.getElementById("data").value;

			if(dataHistorico != "" && dataHistorico != "  /  /    " && dataHistorico.length == 10)
			{
				DWRUtil.useLoadingMessage('Carregando...');
				document.getElementById("salarioCalculado").value = "0,00";
	
				tipoSalario = document.getElementById("tipoSalario").value;
				faixaSalarialId = document.getElementById("faixa").value;
				indiceId = document.getElementById("indice").value;
				quantidade = document.getElementById("quantidade").value;
				salario = document.getElementById("salario").value;
	
				DWREngine.setErrorHandler(error);
				ReajusteDWR.calculaSalarioHistorico(setSalarioCalculado, tipoSalario, faixaSalarialId, indiceId, quantidade, salario, dataHistorico);
			}
			else
			{
				jAlert("Preencha o campo Data.");
				return false;
			}			
		}

		function setSalarioCalculado(data)
		{
			if (tipoSalario == ${tipoAplicacaoIndice.getValor()})
				document.getElementById("salario").value = data;
			else
				document.getElementById("salarioCalculado").value = data;
		}

		function error(msg)
  		{
    		jAlert(msg);
    		document.getElementById("tipoSalario").value = '';
			document.getElementById("salarioCalculado").value = '0,00';
  		}

		function enviaForm()
		{
			tipoSalario = Number(document.getElementById('tipoSalario').value);

			<#if historicoColaborador?exists && historicoColaborador.id?exists>
				switch(tipoSalario)
				{
					case ${tipoAplicacaoIndice.getCargo()}:
						return validaFormulario('form', new Array('estabelecimento', 'areaOrganizacional', 'faixa', 'tipoSalario'), new Array('salario'));
					case ${tipoAplicacaoIndice.getIndice()}:
						return validaFormulario('form', new Array('estabelecimento', 'areaOrganizacional', 'faixa', 'tipoSalario','indice','quantidade'), new Array('salario'));
					default:
						return validaFormulario('form', new Array('estabelecimento', 'areaOrganizacional', 'faixa', 'tipoSalario', 'salario'), new Array('salario'));
				}
			<#else>
				switch(tipoSalario)
				{
					case ${tipoAplicacaoIndice.getCargo()}:
						return validaFormulario('form', new Array('data','estabelecimento', 'areaOrganizacional', 'faixa', 'tipoSalario'), new Array('data','salario'));
					case ${tipoAplicacaoIndice.getIndice()}:
						return validaFormulario('form', new Array('data','estabelecimento', 'areaOrganizacional', 'faixa', 'tipoSalario','indice','quantidade'), new Array('data','salario'));
					default:
						return validaFormulario('form', new Array('data','estabelecimento', 'areaOrganizacional', 'faixa', 'tipoSalario', 'salario'), new Array('data','salario'));
				}
			</#if>
		}
	</script>

	<#if folhaProcessada>
		<#assign somenteLeitura="true"/>
	<#else>
		<#assign somenteLeitura="false"/>
	</#if>
	
	<#include "../ftl/mascarasImports.ftl" />
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" onsubmit="enviaForm();"  validate="true" method="POST">
		
		<#if historicoColaborador?exists && historicoColaborador.id?exists>
			<@ww.hidden name="historicoColaborador.data" id="data" value="${historicoColaborador.data}"/>
		<#else>
			<@ww.datepicker label="Data" id="data" name="historicoColaborador.data" required="true" cssClass="mascaraData" value="${data}"/>
		</#if>

		<#assign funcaoEstabelecimento="populaAmbiente(this.value);"/>
		<@authz.authorize ifNotGranted="ROLE_COMPROU_SESMT">
			<#assign funcaoEstabelecimento=""/>
		</@authz.authorize>
		
		<@ww.select label="Estabelecimento" name="historicoColaborador.estabelecimento.id" id="estabelecimento" list="estabelecimentos" required="true" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." cssStyle="width: 355px;" onchange="${funcaoEstabelecimento}" disabled="${somenteLeitura}"/>
		<@ww.select label="Área Organizacional" name="historicoColaborador.areaOrganizacional.id" id="areaOrganizacional" list="areaOrganizacionals" required="true" listKey="id" listValue="descricao" headerKey="" headerValue="Selecione..." cssStyle="width: 355px;" onchange="verificaMaternidade(this.value, 'areaOrganizacional');" disabled="${somenteLeitura}"/>

		<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
			<@ww.select label="Ambiente" name="historicoColaborador.ambiente.id" id="ambiente" list="ambientes" listKey="id" listValue="nome" headerKey="" headerValue="Nenhum" cssStyle="width: 355px;"/>
			<@ww.select label="Cargo/Faixa" name="historicoColaborador.faixaSalarial.id" id="faixa" list="faixaSalarials" listKey="id" listValue="descricao" required="true" headerKey="" headerValue="Selecione..." onchange="populaFuncao(this.value);calculaSalario();" cssStyle="width: 355px;" disabled="${somenteLeitura}"/>
			<@ww.select label="Função" name="historicoColaborador.funcao.id" id="funcao" list="funcaos" listKey="id" listValue="nome" headerValue="Nenhum" headerKey="-1" />
		</@authz.authorize>
		<@authz.authorize ifNotGranted="ROLE_COMPROU_SESMT">
			<@ww.select label="Cargo/Faixa" name="historicoColaborador.faixaSalarial.id" id="faixa" list="faixaSalarials" listKey="id" listValue="descricao" required="true" headerKey="" headerValue="Selecione..." onchange="calculaSalario();" cssStyle="width: 355px;" disabled="${somenteLeitura}"/>
		</@authz.authorize>


		<@ww.select label="Exposição a Agentes Nocivos (Código GFIP)" name="historicoColaborador.gfip" id="gfip" list="codigosGFIP" headerKey="" headerValue="Selecione..." cssStyle="width: 355px;" disabled="${somenteLeitura}"/>
		<@ww.select label="Tipo de Salário" name="historicoColaborador.tipoSalario" id="tipoSalario" required="true" list="tiposSalarios"  headerValue="Selecione..." headerKey="" onchange="alteraTipoSalario(this.value);calculaSalario();" disabled="${somenteLeitura}"/>

		<div id="valorDiv" style="display:none; _margin-top: 10px;">
			<ul>
				<@ww.textfield label="Valor" name="historicoColaborador.salario" required="true" id="salario" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12" disabled="${somenteLeitura}"/>
			</ul>
		</div>
		<div id="indiceDiv" style="display:none; _margin-top: 10px;">
			<ul>
				<@ww.select label="Índice"  name="historicoColaborador.indice.id" id="indice" list="indices" listKey="id" listValue="nome" required="true" headerValue="Selecione..." headerKey="" onchange="calculaSalario();" disabled="${somenteLeitura}"/>
				<@ww.textfield label="Quantidade" name="historicoColaborador.quantidadeIndice" id="quantidade" required="true" onkeypress = "return(somenteNumeros(event,'{,}'));" cssStyle="width: 45px;text-align:right;" maxLength="6" onchange="calculaSalario();" disabled="${somenteLeitura}"/>
			</ul>
		</div>
		<div id="valorCalculadoDiv" style="_margin-top: 10px;">
			<ul>
				<@ww.textfield label="Valor" name="salarioProcessado" id="salarioCalculado" cssStyle="width:85px; text-align:right;" disabled= "true" disabled="true"/>
			</ul>
		</div>

		<@ww.hidden name="historicoColaborador.id" />
		<@ww.hidden name="historicoColaborador.colaborador.id" />
		<@ww.hidden name="colaborador.id" />
	<@ww.token/>
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="enviaForm();" class="btnGravar"></button>
		<button onclick="window.location='historicoColaboradorList.action?colaborador.id=${historicoColaborador.colaborador.id}'" class="btnCancelar"></button>
	</div>

	<script>
		alteraTipoSalario(document.getElementById("tipoSalario").value);
	</script>
</body>
</html>