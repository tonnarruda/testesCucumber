<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
<#if solicitacao.id?exists>
	<title>Editar Solicitação de Pessoal</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
	<#assign DataSolicitacao = "${solicitacao.dataFormatada}"/>
	<#assign DataPrevisaoEncerramento = "${solicitacao.dataPrevisaoEncerramentoFormatada}"/>
<#else>
	<title>Inserir Solicitação de Pessoal</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
	<#assign DataSolicitacao = "${dataDoDia}"/>
	<#assign DataPrevisaoEncerramento = ""/>
</#if>

<#if solicitacao.dataStatus?exists>
	<#assign DataStatusSolicitacao = solicitacao.dataStatus?date/>
<#else>
	<#assign DataStatusSolicitacao = "${dataDoDia}"/>
</#if>

<#if visualizar?exists && cargo?exists>
	<#assign formAction = formAction + "?visualizar=${visualizar}&cargo.id=${cargo.id}"/>
</#if>


	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ComissaoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/BairroDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ReajusteDWR.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/interface/AmbienteDWR.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/interface/SolicitacaoDWR.js?version=${versao}"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/FaixaSalarialDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formataValores.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<#assign empresaId><@authz.authentication operation="empresaId"/></#assign>
	
	
	<script type="text/javascript">
		function populaEmails(id)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getEmailsResponsaveis(createListEmails, id, ${empresaId});
		}
	
		function createListEmails(data)
		{
			addChecks("emailsCheck", data);
		}
		
		function populaCidades()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			if(document.getElementById('estado').value == "")
			{
				DWRUtil.useLoadingMessage('Carregando...');
				DWRUtil.removeAllOptions("cidade");
			}else{
				CidadeDWR.getCidades(createListCidades, document.getElementById('estado').value);
			}
		}
	
		function createListCidades(data)
		{
			DWRUtil.removeAllOptions("cidade");
			DWRUtil.addOptions("cidade", data);
		}
	
		function populaBairros()
		{
			if(document.getElementById('cidade').value == "")
			{
				DWRUtil.useLoadingMessage('Carregando...');
				DWRUtil.removeAllOptions("bairrosCheck");
			}else{
				BairroDWR.getBairrosMap(createListBairros, document.getElementById('cidade').value);
			}
		}
	
		function createListBairros(data)
		{
			addChecks("bairrosCheck",data)
		}
	
		var bairrosMarcados = new Array();
	
		var retorno = new Array();
	
		function calculaSalario()
		{
			tipoSalario     = 1;// CARGO
			faixaSalarialId = document.getElementById("faixa").value;
			IndiceId        = 0;
			quantidade      = 0;
			salario         = 0;
	
			ReajusteDWR.calculaSalario(setSalarioCalculado, tipoSalario, faixaSalarialId, IndiceId, quantidade, salario);
		}
	
		function setSalarioCalculado(data)
		{
			document.getElementById("remuneracao").value = data;
		}
	
		function validacaoFormulario()
		{
			if($('#dataPrevisaoEncerramento').val() == "  /  /    ")
				$('#dataPrevisaoEncerramento').val(null);
			
			var camposObg = new Array('descricao','horarioComercial','estabelecimento','area','dataSol','faixa','quantidade','motivoSolicitacaoId');
		
			<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
				<#if obrigarAmbienteFuncao>
					camposObg.push('ambiente','funcao');
				</#if>
			</@authz.authorize>

			<#if obrigaDadosComplementares>
				camposObg.push('vinculo', 'escolaridade', 'sexo', 'dataPrevIni', 'dataPrevFim', 'estado', 'cidade', '@bairrosCheck', 'infoComplementares');
				
				<#if exibeSalario>
					camposObg.push('remuneracao');
				</#if>
			</#if>
		
			if(validaFormulario('form', camposObg, new Array ('dataSol')))
			{
				$('#gravar').attr('disabled', true);
				
				$('.colaboradorSubstituido').each(function() {
					if ($(this).val() == '')
						$(this).parent().remove();
				});
				
				return true;
			} 

			return false;
		}
		
		function populaAmbiente(estabelecimentoId)
		{
			if(estabelecimentoId != "null")
			{
				DWRUtil.useLoadingMessage('Carregando...');
				AmbienteDWR.getAmbienteByEstabelecimento(createListAmbiente, estabelecimentoId);
			}
		}

		function createListAmbiente(data)
		{
			addOptionsByMap('ambiente', data);
		}
			
		var contador = 0;
		function adicionarCampoColaboradorSubstituto(nome) 
		{
			if (!nome) nome = '';
			var campo = "<li>";
			campo += "<input type='text' id='colaboradorSubstituido" + contador + "' name='colaboradoresSusbstituidos' class='colaboradorSubstituido' size='40' value='" + nome + "'  maxLength='100' cssClass='inputNome' liClass='liLeft' style='margin-top: 5px;'/>";
			campo += "<img title='Remover' src='<@ww.url includeParams="none" value="/imgs/delete.gif"/>' onclick='javascript:$(this).parent().remove();' style='cursor:pointer;'/>";
			campo += "</li>";
			$('ul#camposSubstituto').append(campo);
			inicializaAutocompleteColabSubstituto(contador);
			contador++;
		}
		
		function inicializaAutocompleteColabSubstituto(contador)
		{
			var colabSub = "colaboradorSubstituido" + contador; 
			$('#'+ colabSub).autocomplete({
				minLength: 3,
				source: function( request, response ) {
					DWRUtil.useLoadingMessage('Carregando...');
					ColaboradorDWR.findByNome(request.term, ${empresaId}, $('#incluirColaboradoresDesligados').is(":checked"), function(dados) {
						
						var resultado = new Array();
						var nomesInseridos = new Array();
						$('.colaboradorSubstituido').each(function(){
							nomesInseridos.push($(this).val());
						});
						
						$.each(dados, function(index, obj){
							if (nomesInseridos.indexOf(obj.nome) == -1 )
								resultado.push(obj);
						});
						
						response( resultado );
					});
				},
				focus: function( event, ui ) {
					return false;
				},
				select: function( event, ui ) {
					DWRUtil.useLoadingMessage('Carregando...');
					ComissaoDWR.dataEstabilidade(ui.item.value, 
												function(data) 
												{
													if (data)
													{
														var msg = 'O Colaborador '+ ui.item.nome +', faz parte <br>da CIPA e possui estabilidade até o dia ' + data + '.' +
																	'<br /> Deseja realmente substituí-lo?';

														$('<div>'+ msg +'</div>').dialog({	title: 'Alerta!',
																							modal: true, 
																							height: 150,
																							width: 500,
																								buttons: [ 	{ text: "Sim", click: function() { $('#'+ colabSub).val(ui.item.nome); $(this).dialog("close"); } },
																							    		{ text: "Não", click: function() { $('#'+ colabSub).val(""); $(this).dialog("close"); } } ] 
																						});
														
													}
													else
													{
														$('#'+ colabSub).val(ui.item.nome);
													}
												});
					return false;
				}
			}).data( "autocomplete" )._renderItem = function( ul, item ) {
				return $( "<li></li>" )
					.data( "item.autocomplete", item )
					.append( "<a>" + item.label.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + $.ui.autocomplete.escapeRegex( $('#'+ colabSub).val() ) + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<strong>$1</strong>" ) + "</a>" )
					.appendTo( ul );
			};		
		}
		
		$(function() {
			<@authz.authorize ifAllGranted="ROLE_LIBERA_SOLICITACAO">
				$('#statusSolicitcao').removeAttr('disabled');
				$('#obsAprova').removeAttr('disabled');
			</@authz.authorize>
			
			<#if exibeColaboradorSubstituido>
				<#list colaboradoresSusbstituidos as colaboradorSusbstituido>
					adicionarCampoColaboradorSubstituto("${colaboradorSusbstituido}");
				</#list>
			
				if ($('.colaboradorSubstituido').size() == 0)
					adicionarCampoColaboradorSubstituto();
		
				$('#tooltipHelp').qtip({
					content: 'Digite ao lado o nome do colaborador que será substituído, para que o sistema valide se o mesmo tem estabilidade ou não na CIPA.'
				});
			</#if>
			
			$('#dataHelp').qtip({
				content: 'A data da solicitação não pode ser editada, pois existem competências para os candidatos.'
			});
		});
		
		function getCargos(){
			DWRUtil.useLoadingMessage('Carregando...');
			FaixaSalarialDWR.getCargosFaixasByArea(optionsCargos, $("#area").val(), ${empresaId});
		}

		function optionsCargos(data){
			$('#faixa').find('option').remove();
			if(data != "" && Object.keys(data).length > 0)
				addOptionsByMap('faixa', data, 'Selecione...');
			else
				$('#faixa').append('<option value="" selected="selected">Não existem cargos vinculados a área organizacional selecionada.</option>');
		}
		
		function checaQtdLimiteColaboradorPorCargo(){
			if($('#motivoSolicitacaoId').val() && $('#faixa').val())
				SolicitacaoDWR.checaQtdLimiteColaboradorPorCargo(dialogQtdLimiteColaboradorParaOCargoCargo, ${empresaSistema.id}, $('#area').val(), $('#faixa').val(), $('#motivoSolicitacaoId').val(), $('#quantidade').val());
		}
		
		function dialogQtdLimiteColaboradorParaOCargoCargo(data){
			if(data)
				$('#tipoAgrupamentoDialog').html(data).dialog({ title: "Aviso", 
																modal: true, 
																width: 550, 
																height: 270,
																buttons: 
																[
																    {
																        text: "Continuar",
																        click: function() { 
																			$('#tipoAgrupamentoDialog').dialog('close');							        
																        }
																    }
																]
															});
				

				
		}
	</script>

	<#assign validarCampos="return validacaoFormulario(); "/>
</head>
<body>
	<@ww.actionmessage/>
	<@ww.actionerror/>

	<#include "../ftl/mascarasImports.ftl" />
	
	<@ww.form name="form" id="form" action="${formAction}" validate="true" onsubmit="${validarCampos}" method="POST">
		<#if somenteLeitura>
			<@ww.textfield readonly="true" label="Data" name="solicitacao.data" id="dataSol" value="${DataSolicitacao}" cssClass="mascaraData" cssStyle="background: #EBEBEB;"/>
			<@ww.textfield readonly="true" label="Prazo de previsão para encerramento" name="solicitacao.dataPrevisaoEncerramento" id="dataPrevisaoEncerramento" value="${DataPrevisaoEncerramento}" cssClass="mascaraData" cssStyle="background: #EBEBEB;"/>
			<@ww.textfield readonly="true" label="Descrição" name="solicitacao.descricao" id="descricao" cssClass="inputNome" cssStyle="background: #EBEBEB;" />
			<@ww.textfield readonly="true" label="Horário de Trabalho" name="solicitacao.horarioComercial" id="horarioComercial" cssClass="inputNome" cssStyle="background: #EBEBEB;" />
		<#else>
			<#if existeCompetenciaRespondida>
				<@ww.textfield readonly="true" label="Data" name="solicitacao.data" id="dataSol" value="${DataSolicitacao}" cssClass="mascaraData" cssStyle="background: #EBEBEB;" liClass='liLeft'/>
				<img id="dataHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-top: 17px" /></br></br>
			<#else>
				<@authz.authorize ifAllGranted="ROLE_EDITA_DATA_SOLICITACAO">
					<@ww.datepicker label="Data" name="solicitacao.data" required="true" id="dataSol" value="${DataSolicitacao}" cssClass="mascaraData"/>
				</@authz.authorize>
				<@authz.authorize ifNotGranted="ROLE_EDITA_DATA_SOLICITACAO">
					<@ww.textfield readonly="true" label="Data" name="solicitacao.data" id="dataSol" value="${DataSolicitacao}" cssClass="mascaraData" cssStyle="background: #EBEBEB;"/>
				</@authz.authorize>
			</#if>	
			
			<@ww.datepicker label="Prazo de previsão para encerramento" name="solicitacao.dataPrevisaoEncerramento" required="false" id="dataPrevisaoEncerramento" value="${DataPrevisaoEncerramento}" cssClass="mascaraData"/>
			<@ww.textfield label="Descrição" name="solicitacao.descricao" id="descricao" cssClass="inputNome" maxlength="67" required="true"/>
			<@ww.textfield label="Horário de Trabalho" name="solicitacao.horarioComercial" id="horarioComercial" cssClass="inputNome" maxlength="255" required="true"/>
		</#if>

		<#if !clone && possuiCandidatoSolicitacao && solicitacao.estabelecimento?exists && solicitacao.estabelecimento.id?exists || somenteLeitura>
			<@ww.textfield readonly="true" label="Estabelecimento" name="solicitacao.estabelecimento.nome" id="estabelecimento" cssStyle="width: 447px;background: #EBEBEB;"/>
			<@ww.hidden name="solicitacao.estabelecimento.id"/>
		<#else>
			<#assign funcaoEstabelecimento="populaAmbiente(this.value);"/>
			<@authz.authorize ifNotGranted="ROLE_COMPROU_SESMT">
					<#assign funcaoEstabelecimento=""/>
			</@authz.authorize>
			<@ww.select label="Estabelecimento" name="solicitacao.estabelecimento.id" id="estabelecimento" list="estabelecimentos" onchange="${funcaoEstabelecimento}" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." required="true" cssStyle="width: 447px;"/>
		</#if>

		<#if !clone && possuiCandidatoSolicitacao && solicitacao.areaOrganizacional?exists && solicitacao.areaOrganizacional.id?exists || somenteLeitura>
			<@ww.textfield readonly="true" label="Área Organizacional" name="solicitacao.areaOrganizacional.nome" cssStyle="width: 447px;background: #EBEBEB;"/>
			<@ww.hidden name="solicitacao.areaOrganizacional.id" id="area"/>
		<#else>
			<#if solicitacao.id?exists>
				<#assign funcaoPopulaEmails=""/>
			<#else>
				<#assign funcaoPopulaEmails="javascript:populaEmails(this.value);"/>
			</#if>
			<@ww.select label="Área Organizacional" name="solicitacao.areaOrganizacional.id" id="area" list="areas" listKey="id" listValue="descricao" headerKey="" headerValue="Selecione..." required="true" cssStyle="width: 447px;" onchange="getCargos();${funcaoPopulaEmails}" />
		</#if>

		<#if !clone && possuiCandidatoSolicitacao && solicitacao.faixaSalarial?exists && solicitacao.faixaSalarial.id?exists || somenteLeitura>
			<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
				<@ww.textfield readonly="true" label="Ambiente" name="solicitacao.ambiente.nome" id="ambiente" cssStyle="width: 447px;background: #EBEBEB;"/>
				<@ww.textfield readonly="true" label="Cargo/Faixa" name="solicitacao.faixaSalarial.descricao" cssStyle="width: 447px;background: #EBEBEB;"/>
				<@ww.textfield readonly="true" label="Função" name="solicitacao.funcao.nome" id="funcao" cssStyle="width: 447px;background: #EBEBEB;"/>
			</@authz.authorize>
			<@authz.authorize ifNotGranted="ROLE_COMPROU_SESMT">
				<@ww.textfield readonly="true" label="Cargo/Faixa" name="solicitacao.faixaSalarial.descricao" cssStyle="width: 447px;background: #EBEBEB;"/>
				<@ww.hidden name="solicitacao.ambiente.id" id="ambiente"/>
				<@ww.hidden name="solicitacao.funcao.id" id="funcao"/>
			</@authz.authorize>
			<@ww.hidden name="solicitacao.faixaSalarial.id" id="faixa"/>
		<#else>
			<#if !faixaSalarials?exists || faixaSalarials?size == 0>
				<#assign headerValueCargo="Selecione uma área organizacional..."/>
			<#else>
				<#assign headerValueCargo="Selecione..."/>
			</#if>
			<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
				<@ww.select label="Ambiente" name="solicitacao.ambiente.id" id="ambiente" required="${obrigarAmbienteFuncao?string}" list="ambientes" listKey="id" listValue="nome" headerKey="" headerValue="Nenhum" cssStyle="width: 447px;"/>
				<@ww.select label="Cargo/Faixa" name="solicitacao.faixaSalarial.id" onchange="javascript:calculaSalario();populaFuncao(this.value);checaQtdLimiteColaboradorPorCargo();" list="faixaSalarials" id="faixa" listKey="id" headerKey="" headerValue="${headerValueCargo}" listValue="descricao" required="true" cssStyle="width: 447px;"/>
				<@ww.select label="Função" name="solicitacao.funcao.id" id="funcao" required="${obrigarAmbienteFuncao?string}" list="funcoes" listKey="id" listValue="nome" headerValue="Nenhuma" headerKey="" cssStyle="width: 447px;"/>
			</@authz.authorize>
			<@authz.authorize ifNotGranted="ROLE_COMPROU_SESMT">
				<@ww.select label="Cargo/Faixa" name="solicitacao.faixaSalarial.id" onchange="javascript:calculaSalario();checaQtdLimiteColaboradorPorCargo()" list="faixaSalarials" id="faixa" listKey="id" headerKey="" headerValue="${headerValueCargo}" listValue="descricao" required="true" cssStyle="width: 447px;"/>
				<@ww.hidden name="solicitacao.ambiente.id" id="ambiente"/>
				<@ww.hidden name="solicitacao.funcao.id" id="funcao"/>
			</@authz.authorize>
		</#if>
		
		<#if !clone && possuiCandidatoSolicitacao && (qtdAvaliacoesRespondidas > 0) || somenteLeitura>
			<@frt.checkListBox name="avaliacoesCheck" id="avaliacoesCheck" label="Avaliações" list="avaliacoesCheckList" readonly=true filtro="true" />
		<#else>
			<@frt.checkListBox name="avaliacoesCheck" id="avaliacoesCheck" label="Avaliações" list="avaliacoesCheckList" readonly=false filtro="true"/>
		</#if>
		
		<#if somenteLeitura>
			<@ww.textfield readonly="true" label="Nº Vagas" id="quantidade" name="solicitacao.quantidade" cssStyle="width:35px; text-align:right; background: #EBEBEB;"/>
			<@ww.textfield readonly="true" label="Motivo da Solicitação" name="solicitacao.motivoSolicitacao.descricao" id="motivoSolicitacaoId" cssClass="inputNome" cssStyle="width: 250px; background: #EBEBEB;" onchange="checaQtdLimiteColaboradorPorCargo()"/>
			<@authz.authorize ifAllGranted="ROLE_MOV_SOLICITACAO_BLOQUEAR_VISUALIZACAO_GESTOR">
				<@ww.checkbox label="Definir solicitação de pessoal como invisível para o gestor da área organizacional" name="solicitacao.invisivelParaGestor" id="invisivelparagestor" labelPosition="left" disabled="true" />
			</@authz.authorize>
			<@authz.authorize ifNotGranted="ROLE_MOV_SOLICITACAO_BLOQUEAR_VISUALIZACAO_GESTOR">
				<@ww.hidden name="solicitacao.invisivelParaGestor" id="invisivelparagestor"/>
			</@authz.authorize>
		<#else>
			<@authz.authorize ifAllGranted="ROLE_MOV_SOLICITACAO_BLOQUEAR_VISUALIZACAO_GESTOR">
				<@ww.checkbox label="Definir solicitação de pessoal como invisível para o gestor da área organizacional" name="solicitacao.invisivelParaGestor" id="invisivelparagestor" labelPosition="left"/>
			</@authz.authorize>
			<@authz.authorize ifNotGranted="ROLE_MOV_SOLICITACAO_BLOQUEAR_VISUALIZACAO_GESTOR">
				<@ww.hidden name="solicitacao.invisivelParaGestor" id="invisivelparagestor"/>
			</@authz.authorize>
			<@ww.textfield label="Nº Vagas" id="quantidade" name="solicitacao.quantidade" onkeypress = "return(somenteNumeros(event,''));" required="true" cssStyle="width:35px; text-align:right;" maxLength="4" onchange="checaQtdLimiteColaboradorPorCargo()" onblur="checaQtdLimiteColaboradorPorCargo()" />
			<@ww.select  id="motivoSolicitacaoId" label="Motivo da Solicitação" name="solicitacao.motivoSolicitacao.id" list="motivoSolicitacaos"  required="true" cssStyle="width: 250px;" listKey="id" listValue="descricao"  headerKey="" headerValue="" onchange="checaQtdLimiteColaboradorPorCargo()"/>
		</#if>
		
		<#if exibeColaboradorSubstituido>
			<div>
				<label>Colaborador Substituído:</label>
				<img id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-top:20px;" />
				<@ww.checkbox label="Possibilitar substituir colaboradores desligados" name="incluirColaboradoresDesligados" id="incluirColaboradoresDesligados" labelPosition="left"/>
				<ul id="camposSubstituto"></ul>
				<a href="javascript:;" onclick="javascript:adicionarCampoColaboradorSubstituto();" style="text-decoration: none;">
					<img src='<@ww.url includeParams="none" value="/imgs/mais.gif"/>'/> 
					Inserir mais um colaborador substituído
				</a>
				<br clear="all"/>
			</div>
			<br />
		</#if>
		
		<#if !solicitacao.id?exists>
			<@frt.checkListBox name="emailsCheck" id="emailsCheck" label="Comunicar responsáveis da Área Organizacional" list="emailsCheckList" filtro="true" />
		</#if>
		<br/>
		<#if somenteLeitura>
			<@ww.textarea label="Experiências" id = "experiencias" name="solicitacao.experiencias" cssStyle="width:445px;" cssStyle="width: 695px; background: #EBEBEB;" readonly=true/>
		<#else>
			<@ww.textarea label="Experiências" id = "experiencias" name="solicitacao.experiencias" cssStyle="width:445px;" cssStyle="width: 695px;"/>
		</#if>
		
		<li>
			<@ww.div id="complementares" cssClass="divInfo">
				<ul>
					<#if obrigaDadosComplementares>
						<#assign asterisco="*"/>
					<#else>
						<#assign asterisco=""/>
					</#if>
				
					<#if somenteLeitura>
						<@ww.textfield readonly="true" label="Vínculo" name="solicitacao.vinculoDescricao" id="vinculo" cssStyle="width: 85px; background: #EBEBEB;" />
						
						<#if exibeSalario>
							<@ww.textfield readonly="true" label="Remuneração (R$)" id="remuneracao" name="solicitacao.remuneracao" cssClass="currency" cssStyle="width:85px; text-align:right; background: #EBEBEB;" />
						</#if>
						
						<@ww.select label="Escolaridade mínima" id="escolaridade" name="solicitacao.escolaridade" list="escolaridades" cssStyle="width: 256px; background: #EBEBEB;"  headerKey="" headerValue="" disabled="true" />
						<@ww.select label="Sexo" id="sexo" name="solicitacao.sexo" list="sexos" liClass="liLeft" cssStyle="background: #EBEBEB;" disabled="true" />
						
						<li>
						<span>Idade Preferencial:</span>
						</li>
						<@ww.textfield name="solicitacao.idadeMinima" id="dataPrevIni" liClass="liLeft" cssStyle="width: 30px;text-align: right;background: #EBEBEB;" maxLength="3" onkeypress = "return(somenteNumeros(event,''));" readonly="true" />
						<@ww.label value="a" liClass="liLeft"/>
						<@ww.textfield name="solicitacao.idadeMaxima" id="dataPrevFim" cssStyle="width: 30px;text-align: right;background: #EBEBEB;" maxLength="3" onkeypress = "return(somenteNumeros(event,''));" liClass="liLeft" readonly="true" />
						<@ww.label value="anos"/><div style="clear: both"></div>
						
						<@ww.select label="Estado"  id="estado" name="estado.id" list="estados" listKey="id" listValue="sigla" liClass="liLeft" cssStyle="background: #EBEBEB;" headerKey="" headerValue="" disabled="true" />
						<@ww.select label="Cidade"  id="cidade" name="solicitacao.cidade.id" list="cidades" listKey="id" listValue="nome" cssStyle="width: 250px; background: #EBEBEB;" headerKey="" headerValue="" disabled="true" />

						<@frt.checkListBox name="bairrosCheck" id="bairrosCheck" label="Bairros${asterisco}" list="bairrosCheckList" width="695" filtro="true" readonly=true/>
						<@ww.textarea label="Informações Complementares" id = "infoComplementares" name="solicitacao.infoComplementares" cssStyle="width:445px;" cssStyle="width: 695px; background: #EBEBEB;" readonly=true/>

					<#else>
						<@ww.select label="Vínculo" name="solicitacao.vinculo" id="vinculo" list=r"vinculos" cssStyle="width: 85px;" required=obrigaDadosComplementares/>
						<#if exibeSalario>
							<@ww.textfield label="Remuneração (R$)" id="remuneracao" name="solicitacao.remuneracao" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12" required=obrigaDadosComplementares/>
						<#else>
							<@ww.hidden name="solicitacao.remuneracao"  id="remuneracao"/>
						</#if>
						<@ww.select label="Escolaridade mínima" id="escolaridade" name="solicitacao.escolaridade" list="escolaridades" cssStyle="width: 256px;"  headerKey="" headerValue="" required=obrigaDadosComplementares/>
						<@ww.select label="Sexo" id="sexo" name="solicitacao.sexo" list="sexos" liClass="liLeft" required=obrigaDadosComplementares/>
						
						<li>
						<span>Idade Preferencial:${asterisco}</span>
						</li>
						<@ww.textfield name="solicitacao.idadeMinima" id="dataPrevIni" liClass="liLeft" cssStyle="width: 30px;text-align: right;" maxLength="3" onkeypress = "return(somenteNumeros(event,''));" required=obrigaDadosComplementares/>
						<@ww.label value="a" liClass="liLeft"/>
						<@ww.textfield name="solicitacao.idadeMaxima" id="dataPrevFim" cssStyle="width: 30px;text-align: right;" maxLength="3" onkeypress = "return(somenteNumeros(event,''));" liClass="liLeft" required=obrigaDadosComplementares/>
						<@ww.label value="anos"/><div style="clear: both"></div>
						
						<@ww.select label="Estado"  id="estado" name="estado.id" list="estados" listKey="id" listValue="sigla" liClass="liLeft" headerKey="" headerValue="" onchange="javascript:populaCidades()" required=obrigaDadosComplementares/>
						<@ww.select label="Cidade"  id="cidade" name="solicitacao.cidade.id" list="cidades" listKey="id" listValue="nome" cssStyle="width: 250px;" headerKey="" headerValue="" onchange="javascript:populaBairros()" required=obrigaDadosComplementares/>
						<@frt.checkListBox name="bairrosCheck" id="bairrosCheck" label="Bairros${asterisco}" list="bairrosCheckList" width="695" filtro="true"/>
						<@ww.textarea label="Informações Complementares" id = "infoComplementares" name="solicitacao.infoComplementares" cssStyle="width:445px;" cssStyle="width: 695px;" required=obrigaDadosComplementares/>
						
						<@ww.hidden name="solicitacao.liberador.id"  />
					</#if>
					
					<@ww.div id="divcomplementar"/>
				</ul>
			</@ww.div>
		</li>
		<br/>

		<li>
			<@ww.div cssClass="divInfo">
				<ul>
					<#if nomeLiberador?exists>
						Atualizado por: ${nomeLiberador} 
						<br><br>					
					</#if>	
					<@ww.select label="Status da solicitação (Inicia o processo de seleção de pessoal)" name="solicitacao.status"  list="status" id="statusSolicitcao" disabled="true"/>
					
					<#if somenteLeitura>
						<@ww.textfield readonly="true" label="Data" name="solicitacao.dataStatus" value="${DataStatusSolicitacao}" cssClass="mascaraData" cssStyle="background: #EBEBEB;"/>
					<#else>
						
						<#if dataStatusSomenteLeitura>
							<@ww.textfield disabled="true" readonly="true" label="Data" name="solicitacao.dataStatus" id="dataStatusSol" value="${DataStatusSolicitacao}" cssClass="mascaraData" cssStyle="background: #EBEBEB;"/>
						<#else>
							<@ww.datepicker label="Data" name="solicitacao.dataStatus" value="${DataStatusSolicitacao}" cssClass="mascaraData" />
						</#if>
						
					</#if>
					
					<@ww.textarea label="Observação" name="solicitacao.observacaoLiberador" id="obsAprova" disabled="true" />
					
					<@ww.hidden name="statusSolicitacaoAnterior" id="statusSolicitacaoAnterior" />
					<@ww.hidden name="dataStatusSolicitacaoAnterior" id="dataStatusSolicitacaoAnterior" />
					<@ww.hidden name="obsStatusSolicitacaoAnterior" id="obsStatusSolicitacaoAnterior" />
					
				</ul>
			</@ww.div>
		</li>

		<@ww.hidden name="solicitacao.id" />
		<@ww.hidden name="solicitacao.solicitante.id" />
		<@ww.hidden name="solicitacao.empresa.id" />
		
		<@authz.authorize ifNotGranted="ROLE_LIBERA_SOLICITACAO">
			<@ww.hidden name="solicitacao.status" />
			<@ww.hidden name="solicitacao.observacaoLiberador" />
		</@authz.authorize>

	<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<#if !somenteLeitura>
			<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}" id="gravar" />
		<#else>
			<@authz.authorize ifAllGranted="ROLE_LIBERA_SOLICITACAO">
				<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}" id="gravar" />
			</@authz.authorize>
		</#if>
				
		<#if visualizar?exists && cargo?exists>
			<button onclick="window.location='list.action?visualizar=${visualizar}&cargo.id=${cargo.id}'" class="btnCancelar" accesskey="V" ></button>
		<#else>
			<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V" ></button>
		</#if>
	</div>
	
	<div id="tipoAgrupamentoDialog" />
</body>
</html>