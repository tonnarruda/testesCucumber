<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		
		.blue { color: blue; }
		.gray { color: #454C54; }
	</style>
		
	<#include "../ftl/mascarasImports.ftl" />
	<#include "../ftl/showFilterImports.ftl" />
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#if solicitacaoExame.id?exists>
		<title>Editar Solicitação/Atendimento Médico</title>
		<#assign formAction="update.action"/>
		<#assign edicao=true/>
	<#else>
		<title>Inserir Solicitação/Atendimento Médico</title>
		<#assign formAction="insert.action"/>
		<#assign edicao=false/>
	</#if>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/SolicitacaoExameDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

<#if exameAso?exists>
	<script>
		$(function() {
			<#if primeiraExecucao && vinculo?exists && (vinculo == "COLABORADOR" || vinculo == 'TODOS')>
				$('#examesPara').val('C');
				$('#nomeColaborador').val('${nomeBusca}');
				filtrarOpcao();
				$('#btnPesquisar').click();
			<#elseif primeiraExecucao &&  vinculo?exists && vinculo == "CANDIDATO">
				$('#examesPara').val('A');
				$('#nomeCandidato').val('${nomeBusca}');
				filtrarOpcao();
				$('#btnPesquisar').click();
			<#else>
				filtrarOpcao();
			</#if>
			
			<#if (listaExames?exists && listaExames?size > 0)>
				filtrarOpcao();
				configuraCampos();
			</#if>
			
			<#if !edicao && !primeiraExecucao>
				findProxOrdem();
			</#if>
		});
		
		function findProxOrdem()
		{
			var data = $('#data').val();
			
			SolicitacaoExameDWR.findProximaOrdem( data, function(ordem) { $('#ordem').val(ordem) } );
		}
	
		function filtrarOpcao()
		{
			value = document.getElementById('examesPara').value;
			if (value == 'A')
			{
				exibe('divCandidato');
				oculta('divColaborador');
			}
			else if (value == 'C')
			{
				exibe('divColaborador');
				oculta('divCandidato');
			}
		}
	
		function oculta(id_da_div)
		{
			document.getElementById(id_da_div).style.display = 'none';
		}
		
		function exibe(id_da_div)
		{
			document.getElementById(id_da_div).style.display = '';
		}
	
		function validaOrdem()
		{
			var data = $('#data').val();
			SolicitacaoExameDWR.findProximaOrdem(retornoValidaOrdem, data );
		}
	
		function retornoValidaOrdem(proximaOrdem)
		{
			if($('#ordem').val() > proximaOrdem){
				$('#ordem').css('background-color', '#FFEEC2');
				jAlert("Ordem deve ser menor ou igual a " + proximaOrdem + ".");
			}else{
				document.form.action = "${formAction}";
				validaform();
			}
		}
	
		function mudaAction(opcao)
		{
			if (opcao == 'gravar'){
				validaOrdem();
			}else{
				document.form.action = "imprimirSolicitacaoExames.action";
				validaform();
			}
		}
		
		function validaform(){
			return validaFormulario('form', new Array('data','ordem','motivoExame','medico'), new Array('data'));
		}
		
		function desabilitaPeriodicidade(elementoCheck)
		{
			elemMotivo = document.getElementById("motivoExame");
			
			var desabilitado = !elementoCheck.checked;
			
			if (elemMotivo.value == '${motivoDEMISSIONAL}') // quando eh demissional, nao temos periodicidade
			{
				desabilitado = true;
			}
			
			elemPeriodicidade = document.getElementById("periodicidadeId" + elementoCheck.value);
			elemPeriodicidade.disabled = desabilitado;
		}
	
		function marcarDesmarcar(frm)
		{
			var motivo = document.getElementById("motivoExame");
			var motivoExameEhAso = (motivo.value != '${motivoCONSULTA}' && motivo.value != '${motivoATESTADO}' && motivo.value != '${motivoSOLICITACAOEXAME}' && motivo.value != '');
			 
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
					if (elements[i].name == 'examesId' && elements[i].type == 'checkbox')
					{
						if (!motivoExameEhAso || (motivoExameEhAso && elements[i].value != ${exameAso.id}) || (motivoExameEhAso && elements[i].value == ${exameAso.id} && alterouMotivo(motivo.value)))
						{
							elements[i].checked = vMarcar;
							document.getElementById("selectClinica_" + elements[i].value).disabled = !vMarcar;
							desabilitaPeriodicidade(elements[i]);
						}
					}
				}
			}
		}
		
		function alterouMotivo(motivo)
		{
			<#if edicao>
				if (motivo == '${solicitacaoExame.motivo}')
					return false;
			</#if>
		
			return true;
		}
		
		function mudarClinica(elementCheck)
		{
			var id = "selectClinica_" + elementCheck.value;
			var elementSelect = document.getElementById(id);
	
			elementSelect.disabled = !elementSelect.disabled;
		}
		
		function acaoCheckExame(elementCheck)
		{
			var motivo = document.getElementById("motivoExame");
			var motivoExameEhAso = (motivo.value != '${motivoCONSULTA}' && motivo.value != '${motivoATESTADO}' && motivo.value != '${motivoSOLICITACAOEXAME}'&& motivo.value != '');
		 	
		 	// quando motivo nao for um dos ASOs, o exame ASO nao eh obrigatorio 
			if (elementCheck.value == ${exameAso.id} && motivoExameEhAso && !alterouMotivo(motivo.value))
			{
				elementCheck.checked = true;
				jAlert('O exame ASO é obrigatório.');
			}
			else
			{
				mudarClinica(elementCheck);
				desabilitaPeriodicidade(elementCheck);
			}
			
			if (elementCheck.checked == true)
				SolicitacaoExameDWR.verificaColaboradorExameDentroDoPrazo(alertaExameDentroDoPrazo, $("#colaborador").val(), $("#Candidato").val(), $("#solicitacaoId").val(), elementCheck.value);		
		}
		
		function alertaExameDentroDoPrazo(data)
		{
			if (data != "")
				jAlert(data);
		}
				
		function getMotivoExameEhAso()
		{
			var motivo = document.getElementById("motivoExame");
			var motivoExameEhAso = (motivo.value != '${motivoCONSULTA}' && motivo.value != '${motivoATESTADO}' && motivo.value != '${motivoSOLICITACAOEXAME}' && motivo.value != '');
			return motivoExameEhAso;
		}
	
		function configuraCampos()
		{
			if (document.forms.length == 1)
				return false;
			
			var id = "";
			
			var motivoExameEhAso = getMotivoExameEhAso();
			
			if (motivoExameEhAso)
			{
				var elementCheckExameAso = document.getElementById("exameId" + ${exameAso.id});
				elementCheckExameAso.checked = true;
			}
			
			for (var i = 0; i < document.forms[1].elements.length; i++)
			{
				var elementForm = document.forms[1].elements[i];
				if ((elementForm != null) && (elementForm.type == 'checkbox') && (elementForm.checked))
				{
					elementClinicaId = "selectClinica_" + elementForm.value;
	
					if(elementClinicaId != "selectClinica_on")
					{
						var elementSelect = document.getElementById(elementClinicaId);
						elementSelect.disabled = false;
						
						desabilitaPeriodicidade(elementForm);
					}
				}
			}
		}
		
		function listaExames(selecionadoId, tipo)
		{
			if (tipo == 'CO')
				$('#colaboradorId').val(selecionadoId);
			else if (tipo == 'CA')
				$('#candidatoId').val(selecionadoId);

			validaFormulario('formFiltro', null, null);
		}
		
		function pesquisar()
		{
			$('#colaboradorId, #candidatoId').val('');
			validaFormulario('formFiltro', null, null);
		}
	</script>
</#if>
	
	<style type="text/css">
	.dados td
	{
		padding: 2px 2px 2px 2px !important;
		margin-left:0px !important;
		margin-right:0px !important;
		
	}
	</style>

</head>
<body>

<@ww.actionerror />
<@ww.actionmessage />

<#if !exameAso?exists>
	<#assign idDaEmpresa><@authz.authentication operation="empresaId"/></#assign>
	<a style="font-size:13px; text-decoration: underline;" href="<@ww.url includeParams="none" value="/geral/empresa/prepareUpdate.action?empresa.id=${idDaEmpresa}"/>">
	Clique aqui para acessar o Cadastro da empresa e configurar o campo [Exame ASO].
	</a>
	
	<div class="buttonGroup">
		<button onclick="document.forms[0].action='list.action';document.forms[0].submit();" class="btnVoltar"> </button>
	</div>
<#else>

	<#include "../util/topFiltro.ftl" />
	<@ww.form name="formFiltro" action="filtroSolicitacaoExames.action" method="POST" >
		<@ww.select label="Exames para" name="examesPara" id="examesPara" list=r"#{'C':'Colaborador','A':'Candidato'}" onchange="filtrarOpcao();" />
		<span id="divCandidato" style="display:''">
			<@ww.textfield label="Nome" name="candidato.nome" id="nomeCandidato" cssStyle="width: 300px;"/>
			<@ww.textfield label="CPF" name="candidato.pessoal.cpf" id="cpfCandidato" cssClass="mascaraCpf"/>
			<@ww.hidden id="candidatoId" name="candidato.id" />
		</span>
		<span id="divColaborador" style="display:none">
			<@ww.textfield label="Nome" name="colaborador.nome" id="nomeColaborador" cssStyle="width: 300px;"/>
			<@ww.textfield label="Matrícula" name="colaborador.matricula" id="matriculaBusca" liClass="liLeft" cssStyle="width: 60px;"/>
			<@ww.textfield label="CPF" name="colaborador.pessoal.cpf" id="cpfColaborador" cssClass="mascaraCpf"/>
			<@ww.hidden id="colaboradorId" name="colaborador.id" />
		</span>
		<@ww.hidden id="nomeBusca" name="nomeBusca" />
		<@ww.hidden id="vinculo" name="vinculo" />
		<@ww.hidden id="primeiraExecucao" name="primeiraExecucao" value="false"/>
		
		<div class="buttonGroup">
			<button id="btnPesquisar" onclick="pesquisar();" class="btnPesquisar grayBGE"> </button>
			<button onclick="document.forms[0].action='list.action';document.forms[0].submit();" class="btnVoltar grayBGE"> </button>
		</div>
		<br/>
		
		<#if solicitacaoExame?exists && solicitacaoExame.data?exists>
			<#assign data = solicitacaoExame.data?date/>
		<#else>
			<#assign data = ""/>
		</#if>
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	
	<#if (candidatos?exists && candidatos?size > 0) || (colaboradors?exists && colaboradors?size > 0) || (edicao?exists)>
	
		<@ww.form name="form" action="${formAction}" method="post">
			<@ww.hidden id = "solicitacaoId" name="solicitacaoExame.id" />
			<@ww.hidden name="examesPara" />
			<#if solicitacaoExame.candidato?exists && solicitacaoExame.candidato.id?exists>
				<h4> Candidato: ${solicitacaoExame.candidato.nome} </h4>
				<@ww.hidden name="solicitacaoExame.candidato.id" />
			</#if>
			<#if solicitacaoExame.colaborador?exists && solicitacaoExame.colaborador.id?exists>
				<h4> Colaborador: ${solicitacaoExame.colaborador.nomeDesligado} </h4>
				<@ww.hidden name="solicitacaoExame.colaborador.id" />
			</#if>
			<@ww.hidden name="solicitacaoExame.empresa.id" />
			<@ww.hidden name="gravarEImprimir" value="true" />
	
			<#if (candidatos?exists && candidatos?size > 0)>
				<@ww.select label="Candidato" name="candidato.id" id="candidato" required="true" list="candidatos" headerKey="" headerValue="Selecione..." listKey="id" listValue="nomeECpf" onchange="listaExames(this.value, 'CA')" cssStyle="width:600px;"/>
			</#if>
	
			<#if (colaboradors?exists && colaboradors?size > 0)>
				<@ww.select label="Colaborador" name="colaborador.id" id="colaborador" required="true" list="colaboradors" headerKey="" headerValue="Selecione..." listKey="id" listValue="nomeCpfMatricula" onchange="listaExames(this.value, 'CO')" cssStyle="width:600px;"/>
			</#if>
	
			<#if (listaExames?exists && listaExames?size > 0)>
				<@ww.datepicker label="Data" id="data" name="solicitacaoExame.data" required="true" cssClass="mascaraData" value="${data}" liClass="liLeft" onchange="findProxOrdem()" onblur="findProxOrdem()"/>
				<@ww.textfield label="Ordem de Atendimento" name="solicitacaoExame.ordem" id="ordem" maxLength="2" size="3" onkeypress="return somenteNumeros(event,'')" required="true" cssStyle="text-align:right;"/>
					<@ww.select label="Motivo do Atendimento" onchange="configuraCampos();" name="solicitacaoExame.motivo" id="motivoExame" list="motivos" headerKey="" headerValue="Selecione..." required="true" cssStyle="width:300px;"/>
				 	<@ww.select label="Médico Coordenador" name="solicitacaoExame.medicoCoordenador.id" id="medico" list="medicoCoordenadors" required="true" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." cssStyle="width:300px;" />
			
					<@ww.textfield label="Observação" name="solicitacaoExame.observacao" id="observacao" maxLength="100" cssClass="inputNome"/>
					
					<@ww.hidden name="ordemAnterior" id="ordemAnterior"/>
					<@ww.hidden name="dataAnterior" id="dataAnterior"/>
				
				<div id="legendas" align="right"><span style="background-color:blue;">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Exames indicados para a função</div>
				
				<div>Exames:</div>
		
				<#assign i = 0/>
				<@display.table name="listaExames" id="lista" class="dados" >
					<#if lista[0].relacionadoAoColaborador>
						<#assign class="blue"/>
					<#else>
						<#assign class="gray"/>
					</#if>
		
					<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(document.forms[1]);' />" style="width: 30px; text-align: center;">
						<#assign checked=""/>
						<#if examesId?exists>
							<#list examesId as exameId>
								<#if (lista[0].id?string == exameId)>
									<#assign checked="checked"/>
								</#if>
							</#list>
						</#if>
						<input type="checkbox" value="${lista[0].id}" id="exameId${lista[0].id}" name="examesId" onclick="acaoCheckExame(this);" ${checked}/>
					</@display.column>
		
					<@display.column title="Exame">
						<label for="check${lista[0].id}" class="${class}">${lista[0].nome}</label>
					</@display.column>
		
					<#assign selected=""/>
		
					<@display.column title="Clínica / Médico" >
		
						<select name="selectClinicas" id="selectClinica_${lista[0].id}" disabled style="width:350px;" >
		
							<option value="">Nenhuma clínica</option>
							<#list lista[1] as clinica>
								<#if checked == "checked" && selected == "">
									<#if selectClinicas?exists && selectClinicas[i]?exists>
										<#if clinica.id?string == selectClinicas[i]?string>
											<#assign selected="selected"/>
											<#assign i = i + 1/>
										</#if>
									<#else>
										<#if exameSolicitacaoExames?exists >
											<#list exameSolicitacaoExames as exameSolicitacaoExame>
												<#if lista[0].id == exameSolicitacaoExame.exame.id && exameSolicitacaoExame.clinicaAutorizada.id?exists && clinica.id == exameSolicitacaoExame.clinicaAutorizada.id>
													<#assign selected="selected"/>
													<#break/>
												</#if>
											</#list>
										</#if>
									</#if>
								<#else>
									<#assign selected=""/>
								</#if>
		
								<#if clinica.id?exists>
							  		<option value="${clinica.id}" ${selected}>${clinica.nome}</option>
								</#if>
		
							</#list>
		
						</select>
					</@display.column>
					
					<@display.column title="Periodicidade (meses)">
					
						<#assign periodo = lista[2] />
						
						<#if exameSolicitacaoExames?exists >
							<#list exameSolicitacaoExames as exameSolicitacaoExame>
								<#if lista[0].id == exameSolicitacaoExame.exame.id>
									<#assign periodo = exameSolicitacaoExame.periodicidade />
									<#break/>
								</#if>
							</#list>
						</#if>
					
						<@ww.textfield name="periodicidades" value="${periodo}" theme="simple" disabled="true" maxlength="10" id="periodicidadeId${lista[0].id}" cssStyle="width:30px;border:1px solid #7E9DB9; text-align:right;" maxLength="4" onkeypress="return(somenteNumeros(event,''));"/>
					</@display.column>
				</@display.table>
				<@ww.token/>
			</#if>
		</@ww.form>
	
		<#if (listaExames?exists && listaExames?size > 0)>
			<div class="buttonGroup">
				<button onclick="return mudaAction('gravar');" class="btnGravar"> </button>
				<#-- TODO ver solução para gravar/visualizar. problemas no Inserir (gravando várias vezes)  
				<button onclick="return mudaAction('gravarVisualizar');" class="btnGravarVisualizar"> </button>
				-->
				<#if solicitacaoExame.id?exists>
					<button onclick="document.forms[0].action='list.action';document.forms[0].submit();" class="btnVoltar" accesskey="V"> </button>
				</#if>
			</div>
		</#if>
	</#if>
</#if>

</body>
</html>