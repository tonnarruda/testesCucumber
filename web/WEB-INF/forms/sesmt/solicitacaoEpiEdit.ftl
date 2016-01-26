<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		
		.blue { color: blue; }
		.gray { color: #454C54; }
		
		option.inativo { background-color: rgb(236, 236, 236); }
	</style>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign showFilter = true />
	<#include "../ftl/showFilterImports.ftl" />

	<#if solicitacaoEpi.id?exists>
		<title>Editar Gerenciamento de EPIs</title>
		<#assign formAction="update.action"/>
		<#assign edicao=true/>
	<#else>
		<title>Inserir Gerenciamento de EPIs</title>
		
		<#assign formAction="insert.action"/>
	</#if>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

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
	
			check = validaCheck(document.getElementById('formInserir'), 'epiIds', "Selecione pelo menos um EPI.");
			if (check)
			{
				$(".inativo").removeAttr("disabled");
				
				idsQtdSolicitado = getIdsInputQtdSolicitado(document.getElementById('formInserir'));
				validaDatas = new Array('data');
							
				if($('#entregue').val() == 'true')
				{
					idsQtdSolicitado.push('dataEntrega');
					validaDatas.push('dataEntrega');
					
					var data = new Date($('#data').val());
					var dataEntrega = new Date($('#dataEntrega').val());
					
					if(dataEntrega.getTime() < data.getTime())
					{
						alert('Data da entrega inferior a data da solicitação.');
						return false;
					}				
				}
				
				var selectTamanhosAtivos = $('#formInserir').find("select[name=selectTamanhoEpi]").not(":disabled");
				
				$(selectTamanhosAtivos).each(function(){
					idsQtdSolicitado.push($(this).attr("id"));
				});
				
				validaForm = validaFormulario('form', idsQtdSolicitado, validaDatas);
			}
			
			if(!validaForm)
				$(".inativo").attr("disabled", "disabled");
			
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
	
		function marcarDesmarcar()
		{
			frm = document.getElementById('formInserir');
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
						motivo = document.getElementById("selectMotivoSolicitacaoEpi_" + elements[i].value);
						quantidade.disabled = !vMarcar;
						motivo.disabled = !vMarcar;
						if (vMarcar && quantidade.value == 0)
							quantidade.value = 1;
					}
				}
			}
		}
	
		function habilitaCampos(elementCheck)
		{
			var idCheckbox = "selectQtdSolicitado_" + elementCheck.value;
			var idSelect = "selectMotivoSolicitacaoEpi_" + elementCheck.value;
			var idTamanho = "selectTamanhoEpi_" + elementCheck.value;
			
			var quantidade = document.getElementById(idCheckbox);
			var motivo = document.getElementById(idSelect);
			var tamanho = document.getElementById(idTamanho);
	
			if(quantidade.disabled)
			{
				quantidade.disabled = false;
				if (quantidade.value == 'undefined' || quantidade.value == 0)
					quantidade.value = 1;
				
				quantidade.focus();
			}
			else
				quantidade.disabled = true;
			
			if(motivo.disabled)
				motivo.disabled = false;
			else
				motivo.disabled = true;
			
			if(tamanho.type != "hidden"){
				if(tamanho.disabled){
					tamanho.disabled = false;
				}
				else{
					tamanho.disabled = true;
				}
			}else if(tamanho.value == ''){
				if(elementCheck.checked)
					tamanho.setAttribute("name", "selectTamanhoEpi");
				if(!elementCheck.checked)
					tamanho.removeAttribute("name");
			}
		}
	
		function configuraCampos()
		{
			<#if edicao?exists>
			var id = "";
				for (var i = 0; i < document.getElementById('formInserir').elements.length; i++)
				{
					var elementForm =  document.getElementById('formInserir').elements[i];
					if ((elementForm != null) && (elementForm.type == 'checkbox') && (elementForm.checked))
					{
						var idCheck = elementForm.value;
						id1 = "selectQtdSolicitado_" + idCheck;
						id2 = "selectMotivoSolicitacaoEpi_" + idCheck;
						id3 = "selectTamanhoEpi_" + idCheck;
	
						if(id1 != "selectQtdSolicitado_on")
						{
							var elementSelect = document.getElementById(id1);
							elementSelect.disabled = false;
						}
						if(id2 != "selectMotivoSolicitacaoEpi_on")
						{
							var elementSelect = document.getElementById(id2);
							elementSelect.disabled = false;
						}
						
						if(id3 != "selectTamanhoEpi_on") {
							var elementSelect = document.getElementById(id3);
							elementSelect.disabled = false;
							if(elementSelect.value == '')
								elementSelect.setAttribute("name", "selectTamanhoEpi");
						}
					}
				}
			
		</#if>
		}
	
		function listaEpis(colaboradorId)
		{
			$('#colaboradorId').val(colaboradorId);
			validaFormulario('formFiltro', null, null);
		}
		
		function pesquisar()
		{
			$('#colaboradorId').val(''); 
			validaFormulario('formFiltro', null, null);
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
	
	<#if !edicao?exists>
		<#include "../util/topFiltro.ftl" />
		<@ww.form name="formFiltro" action="filtroColaboradores.action" method="POST">
			<@ww.textfield label="Nome" name="colaborador.nome" id="nome" cssClass="inputNome" maxLength="100" cssStyle="width: 400px;"/>
			<@ww.textfield label="CPF" id="cpf" name="colaborador.pessoal.cpf" liClass="liLeft" maxLength="11" cssClass="mascaraCpf" onkeypress="return(somenteNumeros(event,''));" />
			<@ww.textfield label="Matrícula" id="matricula" name="colaborador.matricula" cssStyle="width:60px;"  maxLength="20"/>
			<@ww.hidden id="colaboradorId" name="colaborador.id" />
			<button onclick="pesquisar();" class="btnPesquisar grayBGE"></button>
			<button onclick="document.forms[0].action='list.action';document.forms[0].submit();" class="btnVoltar grayBGE"></button>
		</@ww.form>
		<#include "../util/bottomFiltro.ftl" />
		<br/>
	</#if>

	<#if (colaboradors?exists && colaboradors?size > 0) || (edicao?exists)>

		<@ww.form name="form" id="formInserir" action="${formAction}" method="POST">
			<#if solicitacaoEpi.colaborador?exists>
				<h4> Colaborador: ${solicitacaoEpi.colaborador.nome} </h4>
				<@ww.hidden name="solicitacaoEpi.colaborador.id" />
			<#else>
				<@ww.select label="Colaborador" name="colaborador.id" id="colaborador" list="colaboradors" headerKey="" headerValue="Selecione..." listKey="id" listValue="NomeCpf" onchange="listaEpis(this.value)" />
			</#if>
			
			<#if listaEpis?exists && 0 < listaEpis?size>
				<@ww.datepicker label="Data" id="data" name="solicitacaoEpi.data" required="true" cssClass="mascaraData" value="${data}"/>
	
				<div id="legendas" align="right"><span style="background-color:blue;">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;EPIs indicados para a função</div>
	
				<div>EPIs:</div>
	
				<#assign i = 0/>
				<@display.table name="listaEpis" id="lista" class="dados" sort="list">
					<#if lista[0].relacionadoAoColaborador>
						<#assign class="blue"/>
					<#else>
						<#assign class="gray"/>
					</#if>
					
					<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar();' />" style="width: 25px; text-align: center;">
						<#assign checked=""/>
						<#if epiIds?exists>
							<#list epiIds as epiId>
								<#if (lista[0].id?string == epiId)>
									<#assign checked="checked"/>
								</#if>
							</#list>
						</#if>
						
						<#if lista[1].tamanhoEPI?exists && lista[1].tamanhoEPI.id?exists>
							<#assign tamanhoEPIId=lista[1].tamanhoEPI.id/>
						<#else>
							<#assign tamanhoEPIId=-1/>
						</#if>
						
						<@ww.hidden name="tipoEPI${lista[0].id}" value="${lista[0].tipoEPIId}" />
						<@ww.hidden name="tamanhoEPISelected${lista[0].id}" value="${tamanhoEPIId}" />
						<input type="checkbox" value="${lista[0].id}" id="check${lista[0].id}" name="epiIds" onclick="habilitaCampos(this);" ${checked}/>
					</@display.column>
	
					<@display.column title="EPI" style="width:250px;">
						<label for="check${lista[0].id}" class="${class}">${lista[0].nomeInativo}</label>
					</@display.column>
					
					<@display.column title="Tamanho" style="width:100px; text-align: center;">
						<#if (lista[0].tipoEPI.tamanhoEPIs.size() > 0) >
							<select name="selectTamanhoEpi" id="selectTamanhoEpi_${lista[0].id}" style="width:100px" disabled>
								<option value="" selected="selected" >Selecione...</option>
								<#list lista[0].tipoEPI.tamanhoEPIs as tipoTamanhoEPI>
									<#if lista[1].tamanhoEPI?exists && lista[1].tamanhoEPI.id?exists && tipoTamanhoEPI.tamanhoEPIs.id == lista[1].tamanhoEPI.id>
										<option value="${tipoTamanhoEPI.tamanhoEPIs.id}" selected="selected" <#if !tipoTamanhoEPI.ativo >disabled="disabled" class="inativo"</#if> >${tipoTamanhoEPI.tamanhoEPIs.descricao}</option>
									<#else>
										<option value="${tipoTamanhoEPI.tamanhoEPIs.id}" <#if !tipoTamanhoEPI.ativo >disabled="disabled" class="inativo"</#if>>${tipoTamanhoEPI.tamanhoEPIs.descricao}</option>
									</#if>
								</#list>
							</select>
						<#else>
							<@ww.hidden value="" id="selectTamanhoEpi_${lista[0].id}" name=""/>
						 	– 
						</#if>
					</@display.column>
					
	
					<@display.column title="Fabricante">
						<label for="check${lista[0].id}" class="${class}">${lista[0].fabricante}</label>
					</@display.column>
	
					<@display.column title="Número do CA">
						<label for="check${lista[0].id}" class="${class}">${lista[0].epiHistorico.CA}</label>
					</@display.column>
	
					<@display.column title="Quantidade" style="width:40px;">
						<#if lista[1].qtdSolicitado?exists>
							<#assign qtdSolicitado = lista[1].qtdSolicitado?string />
						<#else>
							<#assign qtdSolicitado = "" />
						</#if>
						<input type="text" name="selectQtdSolicitado" onkeypress="return somenteNumeros(event,'')" value="${qtdSolicitado}" id="selectQtdSolicitado_${lista[0].id}" disabled style="text-align:right; vertical-align:top; width: 80px;border:1px solid #BEBEBE;"/>
					</@display.column>
	
					<@display.column title="Motivo da Solicitação" style="width:150px">
						<select name="selectMotivoSolicitacaoEpi" id="selectMotivoSolicitacaoEpi_${lista[0].id}"  style="width:150px" disabled>
							<option value="" selected="selected" >Selecione...</option>
							<#list motivoSolicitacaoEpis as motivo>
								<#if lista[1].motivoSolicitacaoEpi?exists && lista[1].motivoSolicitacaoEpi.id?exists && motivo.id == lista[1].motivoSolicitacaoEpi.id>
									<option value="${motivo.id}" selected="selected" >${motivo.descricao}</option>
								<#else>
									<option value="${motivo.id}" >${motivo.descricao}</option>
								</#if>
							</#list>
						</select>
					</@display.column>
				</@display.table>
	
				<@ww.hidden name="solicitacaoEpi.id" />
				<@ww.hidden name="solicitacaoEpi.empresa.id" />
				<@ww.hidden name="solicitacaoEpi.cargo.id" />
				<@ww.hidden name="solicitacaoEpi.estabelecimento.id" />
				
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
			</#if>
		</@ww.form>

		<#if listaEpis?exists && 0 < listaEpis?size>
			<div class="buttonGroup">
				<button onclick="return enviaForm();" class="btnGravar"></button>
				<button onclick="document.forms[0].action='list.action';document.forms[0].submit();" class="btnVoltar" ></button>
			</div>
		</#if>
	</#if>

	<script type="text/javascript">
		configuraCampos();
	</script>
</body>
</html>