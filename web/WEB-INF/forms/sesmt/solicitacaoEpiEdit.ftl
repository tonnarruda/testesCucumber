<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign showFilter = true/>
	<#include "../ftl/showFilterImports.ftl" />

	<#if solicitacaoEpi.id?exists>
		<title>Editar Solicitação de EPIs</title>
		<#assign formAction="update.action"/>
		<#assign edicao=true/>
	<#else>
		<title>Inserir Solicitação de EPIs</title>
		
		<#assign formAction="insert.action"/>
	</#if>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

<script>
	$(function() {
		$('#entregue').change(function() {
			$('#dataEntrega').attr('disabled', $(this).val() == 'false');
			$('#dataEntrega_button').toggle($(this).val() == 'true');
		});
		$('#entregue').change();
	});
	
	function enviaForm()
	{
		check = validaCheck(document.forms[1], 'epiIds', "Selecione pelo menos um EPI.");
		if (check)
		{
			idsQtdSolicitado = getIdsInputQtdSolicitado(document.forms[1]);
			validaDatas = new Array('data');
						
			if($('#entregue').val() == 'true')
			{
				idsQtdSolicitado.push('dataEntrega');
				validaDatas.push('dataEntrega');				
			}

			validaForm = validaFormulario('form', idsQtdSolicitado, validaDatas);
		}

		return check && validaForm;
	}

	// Pega os ids dos input text (Quantidade)
	function getIdsInputQtdSolicitado(frm)
	{
		ids = new Array('data');
		j = 1;
		for (var i = 0; i < frm.elements.length; i++)
		{
			var elementForm = frm.elements[i];
			if ((elementForm != null) && (elementForm.type == 'checkbox') && (elementForm.checked))
			{
				id = "selectQtdSolicitado_" + elementForm.value;

				if(id != "selectQtdSolicitado_on")
				{
					ids[j] = id;

					// valor 0 é anulado, para forçar a validação
					var elementQtd = document.getElementById(id);
					if (elementQtd.value == "0")
					{
						elementQtd.value = "";
					}

					j++;
				}
			}
		}
		return ids;
	}

	function marcarDesmarcar(frm)
	{
		var vMarcar;

		if (document.getElementById('md').checked)
		{
			vMarcar = true;
		}
		else
		{
			vMarcar = false;
		}

		with(frm)
		{
			for(i = 0; i < elements.length; i++)
			{
				if(elements[i].name == 'epiIds' && elements[i].type == 'checkbox')
				{
					elements[i].checked = vMarcar;
					quantidade = document.getElementById("selectQtdSolicitado_" + elements[i].value);
					quantidade.disabled = !vMarcar;
					if (vMarcar && quantidade.value == 0)
						quantidade.value = 1;
				}
			}
		}
	}

	function mudarQtd(elementCheck)
	{
		var id = "selectQtdSolicitado_" + elementCheck.value;
		var quantidade = document.getElementById(id);

		if(quantidade.disabled)
		{
			quantidade.disabled = false;
			if (quantidade.value == 'undefined' || quantidade.value == 0)
				quantidade.value = 1;
			
			quantidade.focus();
		}
		else
			quantidade.disabled = true;
	}

	function configuraCampos()
	{
		var id = "";

		if (document.forms.length > 1)
		{
			for (var i = 0; i < document.forms[1].elements.length; i++)
			{
				var elementForm = document.forms[1].elements[i];
				if ((elementForm != null) && (elementForm.type == 'checkbox') && (elementForm.checked))
				{
					id = "selectQtdSolicitado_" + elementForm.value;

					if(id != "selectQtdSolicitado_on")
					{
						var elementSelect = document.getElementById(id);
						elementSelect.disabled = false;
					}
				}
			}
		}
	}

</script>

<#if solicitacaoEpi?exists && solicitacaoEpi.data?exists>
	<#assign data = solicitacaoEpi.data?date/>
<#else>
	<#assign data = ""/>
</#if>

</head>
<body>
<@ww.actionmessage />
<@ww.actionerror />
	
	<#include "../util/topFiltro.ftl" />
	<@ww.form name="formFiltro" action="filtroColaboradores.action" method="POST">
		<@ww.textfield label="Nome" name="colaborador.nome" id="nome" cssClass="inputNome" maxLength="100" cssStyle="width: 400px;"/>
		<@ww.textfield label="CPF" id="cpf" name="colaborador.pessoal.cpf" liClass="liLeft" maxLength="11" cssClass="mascaraCpf" onkeypress="return(somenteNumeros(event,''));" />
		<@ww.textfield label="Matrícula" id="matricula" name="colaborador.matricula" cssStyle="width:60px;"  maxLength="20"/>
		<button onclick="validaFormulario('formFiltro', null, null);" class="btnPesquisar grayBGE"></button>
		<button onclick="document.forms[0].action='list.action';document.forms[0].submit();" class="btnVoltar grayBGE"></button>
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br/>

	<#if (colaboradors?exists && colaboradors?size > 0) || (edicao?exists)>

		<@ww.form name="form" action="${formAction}" method="POST">
			<#if solicitacaoEpi.colaborador?exists>
				<h4> Colaborador: ${solicitacaoEpi.colaborador.nome} </h4>
				<@ww.hidden name="solicitacaoEpi.colaborador.id" />
			<#else>
				<@ww.select label="Colaborador" name="solicitacaoEpi.colaborador.id" id="colaborador" list="colaboradors" listKey="id" listValue="NomeCpf" />
			</#if>
			<@ww.datepicker label="Data" id="data" name="solicitacaoEpi.data" required="true" cssClass="mascaraData" value="${data}"/>

			<div>EPIs:</div>

			<#assign i = 0/>
			<@display.table name="listaEpis" id="lista" class="dados" sort="list">

				<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(document.forms[1]);' />" style="width: 30px; text-align: center;">
					<#assign checked=""/>
					<#if epiIds?exists>
						<#list epiIds as epiId>
							<#if (lista[0].id?string == epiId)>
								<#assign checked="checked"/>
							</#if>
						</#list>
					</#if>
					<input type="checkbox" value="${lista[0].id}" id="check${lista[0].id}" name="epiIds" onclick="mudarQtd(this);" ${checked}/>
				</@display.column>

				<@display.column title="EPI" style="width:400px;">
					<label for="check${lista[0].id}">${lista[0].nome}</label>
				</@display.column>

				<@display.column title="Fabricante">
					<label for="check${lista[0].id}">${lista[0].fabricante}</label>
				</@display.column>

				<@display.column title="Número do CA">
					<label for="check${lista[0].id}">${lista[0].epiHistorico.CA}</label>
				</@display.column>

				<@display.column title="Quantidade">
					<#if lista[1].qtdSolicitado?exists>
						<#assign qtdSolicitado = lista[1].qtdSolicitado?string />
					<#else>
						<#assign qtdSolicitado = "" />
					</#if>
					<input type="text" name="selectQtdSolicitado" onkeypress="return somenteNumeros(event,'')" value="${qtdSolicitado}" id="selectQtdSolicitado_${lista[0].id}" disabled style="text-align:right; vertical-align:top; width: 130px;border:1px solid #7E9DB9;"/>
				</@display.column>
			</@display.table>

			<@ww.hidden name="solicitacaoEpi.id" />
			<@ww.hidden name="solicitacaoEpi.empresa.id" />
			<@ww.hidden name="solicitacaoEpi.cargo.id" />
			
			<#if !solicitacaoEpi.id?exists>
				<li>
					<fieldset class="fieldsetPadrao">
						<ul>
							<legend>Considerar os EPIs acima como entregues:</legend>
							<@ww.select label="Entregues" name="entregue" id="entregue" list=r"#{true:'Sim',false:'Não'}" liClass="liLeft" />
							<@ww.datepicker label="Data" id="dataEntrega" name="dataEntrega" required="true" cssClass="mascaraData" value="${data}"/>
						</ul>
					</fieldset>
				</li>
			</#if>
			
			<@ww.token/>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="return enviaForm();" class="btnGravar"></button>
			<button onclick="document.forms[0].action='list.action';document.forms[0].submit();" class="btnVoltar" ></button>
		</div>
	</#if>

	<script type="text/javascript">
		configuraCampos();
	</script>
</body>
</html>