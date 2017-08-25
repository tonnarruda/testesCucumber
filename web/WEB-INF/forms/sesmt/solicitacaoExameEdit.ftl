<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		
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

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/SolicitacaoExameDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

	<script>
		var examesAsoPadrao = ${action.getExameAsosJson()};
		const COLABORADOR = 'C';
		
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
			
			$('#observacaoTooltipHelp').qtip({content: 'A observação inserida será apresentada ao "Imprimir a Solicitação de Exames".'});
		});
		
		function findProxOrdem()
		{
			var data = $('#data').val();
			SolicitacaoExameDWR.findProximaOrdem( data, function(ordem) { $('#ordem').val(ordem) } );
		}
		
		function verificaDataSolicitacaoMenorQueDataAdmissao(){
	    	DWREngine.setAsync(false);
            var retorno = false;
            var data = $('#data').val();
            
            if(data){
	            ColaboradorDWR.getColaboradorById($('#colaboradorId').val(),{
	                callback : function(colaborador){
	                    var partesDataAdmissao  =colaborador.dataAdmissaoFormatada.split('/');
						var dataAdmissao = new Date(partesDataAdmissao[2],partesDataAdmissao[1]-1,partesDataAdmissao[0]);
	                    
	                    var partesDataSolicitacao  = data.split('/');
						var dataSolicitacao = new Date(partesDataSolicitacao[2],partesDataSolicitacao[1]-1,partesDataSolicitacao[0]);
	                    
	                    if(colaborador.dataAdmissaoFormatada &&  dataSolicitacao.getTime() < dataAdmissao.getTime())
	                        jAlert("Data solicitação não pode ser menor que a data de admissão. \n Data admissão:  " + colaborador.dataAdmissaoFormatada);
	                    else
	                        retorno = true;
	                }
	            });
	        }
            
            return retorno;
		} 
	
		function validaDatasColaborador() {
	 	 if(!getMotivoExameEhAsoAdmissional()){
		    <#if edicao>
	        	<#if solicitacaoExame.colaborador?exists && solicitacaoExame.colaborador.id?exists>
	        		return verificaDataSolicitacaoMenorQueDataAdmissao();
	        	<#else>
	        		return true;
	        	</#if>
			<#else>
				if($('#examesPara').val() == COLABORADOR)
		        	return verificaDataSolicitacaoMenorQueDataAdmissao();
		        else
					return true;
			</#if>
			}
		 else{
			return true;
			}
		}
		
		function filtrarOpcao()
		{
			value = document.getElementById('examesPara').value;
			
			if (value == 'A'){
				exibe('divCandidato');
				oculta('divColaborador');
			}else if (value == 'C')	{
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

		function retornoValidaOrdem(proximaOrdem)
		{
			var ordemAtual = $('#ordem').val();
			
			<#if edicao>
				if(proximaOrdem > 1)
					proximaOrdem = proximaOrdem - 1;
			</#if>
			 
			if(ordemAtual == 0 || ordemAtual > proximaOrdem ){
				$('#ordem').css('background-color', '#FFEEC2');
				jAlert("Ordem deve ser maior que 0(zero) e menor ou igual a " + proximaOrdem + ".");
			}else{
				document.form.action = "${formAction}";
				validaform();
			}
		}
	
		function submeter(){
			if(validaDatasColaborador())
				SolicitacaoExameDWR.findProximaOrdem(retornoValidaOrdem, $('#data').val() );
		}
		
		function validaform(){
			return validaFormulario('form', new Array('data','ordem','motivoExame','medico'), new Array('data'));
		}

		function desabilitaPeriodicidade(value,checked)
		{
			var desabilitado = !checked;
			if ($('#motivoExame').val() == '${motivoDEMISSIONAL}') // quando eh demissional, nao temos periodicidade
				desabilitado = true;
			
			$("#periodicidadeId" + value).attr('disabled', desabilitado);
		}
	
	
		function marcarDesmarcar(frm)
		{
			$('.checks').each(function(){
	        	$("#exameId" + this.value).attr('checked', $('#md').attr('checked'));
	        	$("#selectClinica_" + this.value).attr('disabled', !$('#md').attr('checked'));
				desabilitaPeriodicidade(this.value, this.checked);
			});
			
			if (getMotivoExameEhAso())
			{
			    $.each(examesAsoPadrao, function(i, item) {
			        $("#exameId" + examesAsoPadrao[i].id).attr('checked', true);
		        	$("#selectClinica_" + examesAsoPadrao[i].id).removeAttr('disabled');
					desabilitaPeriodicidade(examesAsoPadrao[i].id, true);
				});
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
			var motivoExameEhAso = getMotivoExameEhAso();
			mudarClinica(elementCheck);
			desabilitaPeriodicidade(elementCheck.value, elementCheck.checked);
			
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
		function getMotivoExameEhAsoAdmissional()
		{
			return $('#motivoExame').val() === 'ADMISSIONAL';
		}
	
		function configuraCampos()
		{
			if (document.forms.length == 1)
				return false;
			
			if (getMotivoExameEhAso())
			{
			    $.each(examesAsoPadrao, function(i, item) {
					$('.aso').each(function(){
					    if (this.htmlFor == "check" + examesAsoPadrao[i].id)
					        $("#exameId" + examesAsoPadrao[i].id).attr('checked', true).attr('disabled','disabled');
	   				});
				});
			}
			else
			{
				$('.checks').each(function(){
			        $("#exameId" + this.value).removeAttr('disabled');
				});
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

						desabilitaPeriodicidade(elementForm.value, elementForm.checked);
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
				<@ww.datepicker label="Data" id="data" name="solicitacaoExame.data" required="true" cssClass="mascaraData" value="${data}" liClass="liLeft" onchange="findProxOrdem(),validaDatasColaborador()" onblur="findProxOrdem(),validaDatasColaborador()"/>
				<@ww.textfield label="Ordem de Atendimento" name="solicitacaoExame.ordem" id="ordem" maxLength="2" size="3" onkeypress="return somenteNumeros(event,'')" required="true" cssStyle="text-align:right;"/>
					<@ww.select label="Motivo do Atendimento" name="solicitacaoExame.motivo" id="motivoExame" list="motivos" headerKey="" headerValue="Selecione..." required="true" cssStyle="width:300px;" onchange="validaDatasColaborador();configuraCampos();" onblur="validaDatasColaborador();"/>
				 	<@ww.select label="Médico Coordenador" name="solicitacaoExame.medicoCoordenador.id" id="medico" list="medicoCoordenadors" required="true" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." cssStyle="width:300px;" />
			
					<@ww.textfield label="Observação" name="solicitacaoExame.observacao" id="observacao" maxLength="100" cssClass="inputNome"/>
					<img id="observacaoTooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin:-40px 0 35px 85px;"/>
					
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
						<input type="checkbox" value="${lista[0].id}" id="exameId${lista[0].id}" name="examesId" class="checks" onclick="acaoCheckExame(this);" ${checked}/>
					</@display.column>
		
					<@display.column title="Exame">
						<label for="check${lista[0].id}" class="${class} aso">${lista[0].nome}</label>
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
					
						<@ww.textfield name="periodicidades" value="${periodo}" theme="simple" disabled="true" maxlength="10" id="periodicidadeId${lista[0].id}" cssStyle="width:30px;border:1px solid #BEBEBE; text-align:right;" maxLength="4" onkeypress="return(somenteNumeros(event,''));"/>
					</@display.column>
				</@display.table>
				<@ww.token/>
			</#if>
		</@ww.form>
	
		<#if (listaExames?exists && listaExames?size > 0)>
			<div class="buttonGroup">
				<button  onclick="submeter();" class="btnGravar"> </button>
				<#if solicitacaoExame.id?exists>
					<button onclick="document.forms[0].action='list.action';document.forms[0].submit();" class="btnVoltar" accesskey="V"> </button>
				</#if>
			</div>
		</#if>
	</#if>

</body>
</html>