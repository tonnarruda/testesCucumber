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

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/interface/FuncaoDWR.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/interface/AmbienteDWR.js?version=${versao}"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ReajusteDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/indice.js?version=${versao}"/>"></script>

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/formataValores.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/areaOrganizacional.js?version=${versao}"/>"></script>
	
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
			var tipoSalario = Number(document.getElementById('tipoSalario').value);
			var camposObrigatorios = new Array('estabelecimento', 'areaOrganizacional', 'faixa', 'tipoSalario');
			var camposValidos = new Array('salario');

			if (tipoSalario == ${tipoAplicacaoIndice.getIndice()})
			{
				camposObrigatorios.push('indice','quantidade');
			}
			else if (tipoSalario == ${tipoAplicacaoIndice.getValor()})
				camposObrigatorios.push('salario');

			<#if !historicoColaborador?exists || !historicoColaborador.id?exists>
				camposObrigatorios.push('data');
				camposValidos.push('data');
			</#if>
			
			<#if obrigarAmbienteFuncao>
				camposObrigatorios.push('ambiente','funcao');
			</#if>

			return validaFormulario('form', camposObrigatorios, camposValidos);
		}
			
		$(document).ready(function($)
		{
			<#if historicoColaborador.motivo?exists && historicoColaborador.motivo == "C">
				$("#motivo option[value!='C']").attr("disabled","disabled");
				$("#motivo option[value!='C']").css("background-color", "#DEDEDE");
			<#else>
				$("#motivo option[value='C']").attr("disabled","disabled");
				$("#motivo option[value='C']").css("background-color", "#DEDEDE");
			</#if>
			
			$('#data').blur(function(){validaDataPrimeiroHist()});
			
		});
		
		function strToDate(str)
		{
			var arr = str.match(/(\d\d)\/(\d\d)\/(\d\d\d\d)/);
			if (arr)
			    return new Date(arr[3], arr[2]-1, arr[1]);
			else
			    return undefined;		
		}
		
		<#if dataPrimeiroHist?exists>
			function validaDataPrimeiroHist()
			{
				if(strToDate($('#data').val()) <= strToDate('${dataPrimeiroHist}'))
				{
					$("#motivo option[value='C']").removeAttr("disabled");
					$("#motivo option[value='C']").css("background-color", "#FFF");
					$("#motivo option[value!='C']").attr("disabled","disabled");
					$("#motivo option[value!='C']").css("background-color", "#DEDEDE");
					$("#motivo").val('C');
				}
				else
				{
					$("#motivo option[value!='C']").removeAttr("disabled");
					$("#motivo option[value!='C']").css("background-color", "#FFF");
					$("#motivo option[value='C']").attr("disabled","disabled");
					$("#motivo option[value='C']").css("background-color", "#DEDEDE");
					$("#motivo").val('P');			
				}
			}
		</#if>			
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
			<@ww.datepicker label="Data" id="data" name="historicoColaborador.data" required="true" cssClass="mascaraData" value="${data}" eventOnUpdate="function(){validaDataPrimeiroHist()}"/>
		</#if>

		<#assign funcaoEstabelecimento="populaAmbiente(this.value);"/>
		<@authz.authorize ifNotGranted="ROLE_COMPROU_SESMT">
			<#assign funcaoEstabelecimento=""/>
		</@authz.authorize>
		
		<@ww.select label="Estabelecimento" name="historicoColaborador.estabelecimento.id" id="estabelecimento" list="estabelecimentos" required="true" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." cssStyle="width: 355px;" onchange="${funcaoEstabelecimento}" disabled="${somenteLeitura}"/>
		<@ww.select label="Área Organizacional" name="historicoColaborador.areaOrganizacional.id" id="areaOrganizacional" list="areaOrganizacionals" required="true" listKey="id" listValue="descricao" headerKey="" headerValue="Selecione..." cssStyle="width: 355px;" onchange="verificaMaternidade(this.value, 'areaOrganizacional');" disabled="${somenteLeitura}"/>

		<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
			<@ww.select label="Ambiente" name="historicoColaborador.ambiente.id" id="ambiente" required="${obrigarAmbienteFuncao?string}" list="ambientes" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." cssStyle="width: 355px;"/>
			<@ww.select label="Cargo/Faixa" name="historicoColaborador.faixaSalarial.id" id="faixa" list="faixaSalarials" listKey="id" listValue="descricao" required="true" headerKey="" headerValue="Selecione..." onchange="populaFuncao(this.value);calculaSalario();" cssStyle="width: 355px;" disabled="${somenteLeitura}"/>
			<@ww.select label="Função" name="historicoColaborador.funcao.id" id="funcao" required="${obrigarAmbienteFuncao?string}" list="funcaos" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="-1" cssStyle="width: 355px;"/>
		</@authz.authorize>
		<@authz.authorize ifNotGranted="ROLE_COMPROU_SESMT">
			<@ww.select label="Cargo/Faixa" name="historicoColaborador.faixaSalarial.id" id="faixa" list="faixaSalarials" listKey="id" listValue="descricao" required="true" headerKey="" headerValue="Selecione..." onchange="calculaSalario();" cssStyle="width: 355px;" disabled="${somenteLeitura}"/>
		</@authz.authorize>


		<@ww.select label="Exposição a Agentes Nocivos (Código GFIP)" name="historicoColaborador.gfip" id="gfip" list="codigosGFIP" headerKey="" headerValue="Selecione..." cssStyle="width: 355px;" disabled="${somenteLeitura}"/>
		<@ww.select label="Tipo de Salário" name="historicoColaborador.tipoSalario" id="tipoSalario" required="true" list="tiposSalarios"  headerValue="Selecione..." headerKey="" onchange="alteraTipoSalario(this.value);calculaSalario();" disabled="${somenteLeitura}"/>

		<div id="valorDiv" style="display:none; _margin-top: 10px;">
			<ul>
				<@ww.textfield label="Valor" name="historicoColaborador.salario" required="true" id="salario" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12" disabled="${somenteLeitura}" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
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
		
		<#if historicoColaborador?exists && historicoColaborador.id?exists>
			<#if historicoColaborador.motivo != 'I'>
				<@ww.select label="Motivo do reajuste" name="historicoColaborador.motivo" id="motivo" list=r'#{"C":"Contratado", "D":"Dissídio", "P":"Promoção", "S":"Sem Motivo"}'/>
			<#else>
				<@ww.hidden name="historicoColaborador.motivo" />
				Importado do Fortes Pessoal
			</#if>
		<#else>
			<#if historicoColaborador.motivo != 'I'>
				<@ww.select label="Motivo do reajuste" name="historicoColaborador.motivo" id="motivo" list=r'#{"C":"Contratado", "D":"Dissídio", "P":"Promoção", "S":"Sem Motivo"}'/>
			<#else>
				<@ww.select label="Motivo do reajuste" name="historicoColaborador.motivo" id="motivo" list=r'#{"D":"Dissídio", "P":"Promoção", "S":"Sem Motivo"}'/>
			</#if>
		</#if>	
		
		<#if integraAc && !historicoColaborador.colaborador.naoIntegraAc>
			<@ww.textfield label="Observação para o Setor Pessoal" name="historicoColaborador.obsACPessoal" id="obsACPessoal" cssStyle="width:355px;" maxLength="100"/>
		</#if>

		<@ww.hidden name="historicoColaborador.id" />
		<@ww.hidden name="historicoColaborador.colaborador.id" />
		<@ww.hidden name="historicoColaborador.colaborador.naoIntegraAc" />
		<@ww.hidden name="colaborador.id" />
		<@ww.hidden name="candidatoSolicitacaoId" />
		<@ww.hidden name="solicitacao.id" />
		<@ww.hidden name="encerrarSolicitacao" />
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