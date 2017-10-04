<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<@ww.head/>
		<style type="text/css">
			.divTitulo{
				width: 960px;
			}
			.titulo{
				font-weight: bold; 
				font-size: 13px;
			}
			.wwctrl textarea{
				width: 960px;
				height:150px;
				border: 1px solid;
				border-color: #D9D9D9;
			}
			#dadosColab{
				border-bottom: 1pt solid black;
				border-color: #D9D9D9;
				width: 966px;
			}
			#wwgrp_descricaoCBOCargo
		    {
				float: left;
		    	background-color: #E9E9E9;
				width: 310px;
				padding-left: 4px;
			}
			#wwgrp_descricaoCBOFuncao
		    {
				float: left;
		    	background-color: #E9E9E9;
				width: 310px;
				padding-left: 4px;
			}
			.desc{
				font-weight: bold 
			}
			.no-data {
				color: #9D9D9D;
				font-size: 11px;
				margin-left: 5px;
			}
		</style>
		
		<#include "../ftl/mascarasImports.ftl" />
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/OrdemDeServicoDWR.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/autoCompleteFortes.js?version=${versao}"/>'></script>
		
		<script type="text/javascript">
			var ultimaDataValida = "";

			var urlFind = "<@ww.url includeParams="none" value="/geral/codigoCBO/find.action"/>";

			$(function(){
				ultimaDataValida = $('#dataOS').val();
				
				$('#descricaoCBOCargo').focus(function() {
				    $(this).select();
				});
				
				$('#descricaoCBOFuncao').focus(function() {
				    $(this).select();
				});
			});

			function repopularOrdemDeServico()
			{
				var dataValida =  validaDate($('#dataOS')[0]) && $('#dataOS').val() != "  /  /    " &&  $('#dataOS').val() != "";
				if(dataValida){
					DWREngine.setErrorHandler(errorRecarregaDadosOrdemDeServico);
					OrdemDeServicoDWR.recarregaDadosOrdemDeServico(repopularOrdemDeServicoByDados, ${ordemDeServico.colaborador.id}, ${empresaSistema.id}, $('#dataOS').val());
				}
				else
					jAlert("Informe uma data válida");
			}
			
			function repopularOrdemDeServicoByDados(dados){
				ultimaDataValida = $('#dataOS').val();
				repopularDadosCabecalho(dados);
				repopularDadosFormulario(dados);
			}
			
			function repopularDadosCabecalho(dados) {
				$('#nomeColaboradorOS').text(dados["nomeColaborador"]);
				$('#dataAdmissaoFormatadaOS').text(dados["dataAdmisaoColaboradorFormatada"]);
				$('#nomeCargoOS').text(dados["nomeCargo"]);
				$('input[name=ordemDeServico.nomeCargo]').val(dados["nomeCargo"]);
				$('#nomeFuncaoOS').text(dados["nomeFuncao"]);
				$('input[name=ordemDeServico.nomeFuncao]').val(dados["nomeFuncao"]);
				$('input[name=ordemDeServico.nomeEstabelecimento]').val(dados["nomeEstabelecimento"]);
				$('input[name=ordemDeServico.estabelecimentoComplementoCnpj]').val(dados["estabelecimentoComplementoCnpj"]);
				$('input[name=ordemDeServico.estabelecimentoEndereco]').val(dados["estabelecimentoEndereco"]);
				
				montaCampoCBO(dados["codigoCBOCargo"],$("#cboCargo"), "Cargo");
				montaCampoCBO(dados["codigoCBOFuncao"],$("#cboFuncao"), "Funcao");
			}
			
			function repopularDadosFormulario(dados) {
				$('#atividadesOS').text(dados["atividades"] == null ? "" :  dados["atividades"]);
				$('#riscosOS').text(dados["riscos"] == null ? "" : dados["riscos"]);
				$('#episOS').text(dados["epis"] == null ? "" : dados["epis"]);
				$('#medidasPreventivasOS').text(dados["medidasPreventivas"] == null ? "" : dados["medidasPreventivas"]);
				$('#treinamentosOS').text(dados["treinamentos"] == null ? "" : dados["treinamentos"]);
				$('#normasInternasOS').text(dados["normasInternas"] == null ? "" : dados["normasInternas"]);
				$('#procedimentoEmCasoDeAcidenteOS').text(dados["procedimentoEmCasoDeAcidente"] == null ? "" : dados["procedimentoEmCasoDeAcidente"]);
				$('#termoDeResponsabilidadeOS').text(dados["termoDeResponsabilidade"] == null ? "" : dados["termoDeResponsabilidade"]);
				$('#informacoesAdicionaisOS').text(dados["informacoesAdicionais"] == null ? "" : dados["informacoesAdicionais"]);
			}
			
			function errorRecarregaDadosOrdemDeServico(msg){
				$('#dataOS').val(ultimaDataValida);
				jAlert(msg);
			}
			
			function carregaDadosOrdemDeServicoAnterior(){
				DWRUtil.useLoadingMessage('Carregando...');
				OrdemDeServicoDWR.carregaUltimaOrdemDeServicoByColaborador(repopularDadosFormulario, ${ordemDeServico.colaborador.id});
			}
			
			function montaCampoCBO(valorCBO, elementCBO, tipoElement){
				$(elementCBO).html("");
				if ( valorCBO == "" || valorCBO == null ) {
					$(elementCBO).html('<li id="wwgrp_codigoCBO'+tipoElement+'" class="liLeft">'+  
										'<div id="wwlbl_codigoCBO'+tipoElement+'" class="wwlbl">'+
											'<label for="codigoCBO'+tipoElement+'" class="desc" style="font-weight: bold"> Cód. CBO:<span class="req">* </span></label>'+
										'</div>'+ 
										'<div id="wwctrl_codigoCBO'+tipoElement+'" class="wwctrl">'+
											'<input type="text" name="ordemDeServico.codigoCBO'+tipoElement+'" size="6" maxlength="6" value="" id="codigoCBO'+tipoElement+'OS" onkeypress="return(somenteNumeros(event,\'\'));">'+
										'</div>'+ 
									'</li>'+
									'<li id="wwgrp_descricaoCBO'+tipoElement+'" class="wwgrp">'+  
										'<div id="wwlbl_descricaoCBO'+tipoElement+'" class="wwlbl">'+
											'<label for="descricaoCBO'+tipoElement+'" class="desc" style="font-weight: bold"> Busca CBO (Código ou Descrição):</label>'+       
										'</div>'+
										'<div id="wwctrl_descricaoCBO'+tipoElement+'" class="wwctrl">'+
											'<input type="text" name="descricaoCBO'+tipoElement+'" value="" id="descricaoCBO'+tipoElement+'" style="width: 300px;">'+
										'</div>'+
										'<div style="clear:both"></div>'+ 
									'</li>');
						
					$("#descricaoCBO"+tipoElement).autocomplete({
						source: ajaxData(urlFind),				 
						minLength: 2,
						select: function( event, ui ) { 
							$("#codigoCBO"+tipoElement+"OS").val(ui.item.id);
						}
					}).data( "autocomplete" )._renderItem = renderData;
				} else {
					$(elementCBO).html("");
					$(elementCBO).html('<span style="font-weight: bold;">Código CBO:</span><span>'+valorCBO+'</span>'+
								   '<input type="hidden" name="ordemDeServico.codigoCBO'+tipoElement+'" value="'+valorCBO+'" id="codigoCBO'+tipoElement+'OS">');
				}
			}
			
			function submit_form(){
				var array =  new Array('dataOS', 'atividadesOS','riscosOS','episOS','medidasPreventivasOS','treinamentosOS','normasInternasOS','procedimentoEmCasoDeAcidenteOS','termoDeResponsabilidadeOS');
				
				var validateCBOCargo = validaCBO($("#codigoCBOCargoOS"));
				var validateCBOFuncao = validaCBO($("#codigoCBOFuncaoOS"));
				
				if(!validateCBOCargo && !validateCBOFuncao){
					jAlert('Preencha o CBO do cargo e o CBO da função');
				}
				else if(!validateCBOCargo){
					jAlert('Preencha o CBO do cargo');
				}
				else if(!validateCBOFuncao){
					jAlert('Preencha o CBO da função');
				}
				return validaFormulario('form', array, new Array('dataOS'), true) && validateCBOCargo && validateCBOFuncao;
			}
			
			function validaCBO(campoCBO){
				var validado = true;
				if ( $(campoCBO).length == 1 && ($(campoCBO).val() == "" || $(campoCBO).val() == null)) {
					validado = false;
					$(campoCBO).css("background", "rgb(255, 238, 194)");
				} else{
					$(campoCBO).css("background", "white");
					validado = true;
				}
				return validado;
			}
			
		</script>	
			
		<#if ordemDeServico.id?exists>
			<title>Editar Ordem de Serviço</title>
			<#assign formAction="update.action"/>
			<#assign dataOS = ordemDeServico.data?date/>
			<#assign nomeCargo = ordemDeServico.nomeCargo>
			<#assign nomeFuncao = ordemDeServico.nomeFuncao>
			<#assign codigoCBOCargo = ordemDeServico.codigoCBOCargo>
			<#assign codigoCBOFuncao = ordemDeServico.codigoCBOFuncao>
		<#else>
			<title>Inserir Ordem de Serviço</title>
			<#assign formAction="insert.action"/>
			<#assign dataOS = "">
			<#assign nomeCargo = "<span class='no-data'>[Preencha a data para carregar essa informação]</span>">
			<#assign nomeFuncao = "<span class='no-data'>[Preencha a data para carregar essa informação]</span>">
			<#assign codigoCBOCargo = "<span class='no-data'>[Preencha a data para carregar essa informação]</span>">
			<#assign codigoCBOFuncao = "<span class='no-data'>[Preencha a data para carregar essa informação]</span>">
		</#if>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form id="form" name="form" action="${formAction}" onsubmit="return submit_form();" validate="true" method="POST">
			<@ww.hidden name="colaborador.id" value="${ordemDeServico.colaborador.id}" />
			<@ww.hidden name="ordemDeServico.id" />
			<@ww.hidden name="ordemDeServico.colaborador.id" value="${ordemDeServico.colaborador.id}" />
			<@ww.hidden name="ordemDeServico.nomeColaborador" />
			<@ww.hidden name="ordemDeServico.dataAdmisaoColaborador" />
			<@ww.hidden name="ordemDeServico.nomeFuncao" />
			<@ww.hidden name="ordemDeServico.nomeEmpresa" />
			<@ww.hidden name="ordemDeServico.nomeCargo" />
			<@ww.hidden name="ordemDeServico.empresaCnpj" />
			<@ww.hidden name="ordemDeServico.nomeEstabelecimento" />
			<@ww.hidden name="ordemDeServico.estabelecimentoComplementoCnpj" />
			<@ww.hidden name="ordemDeServico.estabelecimentoEndereco" />
			<@ww.hidden name="ordemDeServico.revisao" value="${revisao}"/>
			<@ww.token/>
			
			<table id="dadosColab">
				<tr>
					<td width="480px">
						<@ww.datepicker label="Data da Ordem de Serviço" name="ordemDeServico.data" value="${dataOS}" id="dataOS" required="true" cssClass="mascaraData" onchange="repopularOrdemDeServico();" onblur="repopularOrdemDeServico();"/>
					</td>
				</tr>
				<tr>	
					<td> <span style="font-weight: bold;">Revisão: </span> <span style="margin-left: 4px;">${revisao}</span> </td>
				</tr>
				<tr>
					<td> <span style="font-weight: bold;">Colaborador:</span> <span id="nomeColaboradorOS">${ordemDeServico.nomeColaborador}</span> </td>
					<td> <span style="font-weight: bold;">Data de Admissão:</span> <span id="dataAdmissaoFormatadaOS">${ordemDeServico.dataAdmisaoColaboradorFormatada}</span> </td>
				</tr>
				<tr>
					<td> <span style="font-weight: bold;">Cargo:</span> <span id="nomeCargoOS">${nomeCargo}</span> </td>
					<td width="480" id="cboCargo"> 
							<span style="font-weight: bold;">Código CBO:</span><span>${codigoCBOCargo}</span>
							<@ww.hidden name="ordemDeServico.codigoCBOCargo"/> 
					</td>
				</tr>
				<tr>
					<td> <span style="font-weight: bold;">Função:</span> <span id="nomeFuncaoOS">${nomeFuncao}</span> </td>
					<td width="480" id="cboFuncao"> 
							<span style="font-weight: bold;">Código CBO:</span><span>${codigoCBOFuncao}</span>
							<@ww.hidden name="ordemDeServico.codigoCBOFuncao"/> 
					</td>
				</tr>
			</table>
			
			<#if !ordemDeServico.id?exists && revisao != 1>
				<div class="buttonGroup">
					<button type="button" onclick="carregaDadosOrdemDeServicoAnterior()" class="btnCarregarOrdemDeServico"></button>
				</div>
			</#if>

			</br><div class="divTitulo" align="center"><span class="titulo">ATIVIDADES DESENVOLVIDAS*</span></div>
			<@ww.textarea name="ordemDeServico.atividades" id="atividadesOS" required="true"/>

			</br><div class="divTitulo" align="center"><span class="titulo">RISCO DA OPERAÇÃO*</span></div>
			<@ww.textarea name="ordemDeServico.riscos" id="riscosOS" required="true"/>
			
			</br><div class="divTitulo" align="center"><span class="titulo">EQUIPAMENTOS DE PROTEÇAO INDIVIDUAL (EPI'S) - USO OBRIGATÓRIO*</span></div>
			<@ww.textarea name="ordemDeServico.epis" id="episOS" required="true"/>
			
			</br><div class="divTitulo" align="center"><span class="titulo">MEDIDAS PREVENTIVAS*</span></div>
			<@ww.textarea name="ordemDeServico.medidasPreventivas" required="true" id="medidasPreventivasOS"/>
			
			</br><div class="divTitulo" align="center"><span class="titulo">TREINAMENTO(S) NECESSÁRIO(S)*</span></div>
			<@ww.textarea name="ordemDeServico.treinamentos" required="true" id="treinamentosOS"/>
			
			</br><div class="divTitulo" align="center"><span class="titulo">NORMAS INTERNAS*</span></div>
			<@ww.textarea name="ordemDeServico.normasInternas" id="normasInternasOS" required="true"/>
			
			</br><div class="divTitulo" align="center"><span class="titulo">PROCEDIMENTO EM CASO DE ACIDENTE DE TRABALHO*</span></div>
			<@ww.textarea name="ordemDeServico.procedimentoEmCasoDeAcidente" id="procedimentoEmCasoDeAcidenteOS" required="true"/>
			
			</br><div class="divTitulo" align="center"><span class="titulo">INFORMAÇÕES ADICIONAIS</span></div>
			<@ww.textarea name="ordemDeServico.informacoesAdicionais" id="informacoesAdicionaisOS"/>
			
			</br><div class="divTitulo" align="center"><span class="titulo">TERMO DE RESPONSABILIDADE*</span></div>
			<@ww.textarea name="ordemDeServico.termoDeResponsabilidade" id="termoDeResponsabilidadeOS" required="true"/>
			
			<div class="buttonGroup">
				<button type="submit" class="btnGravar"></button>
				<button onclick="window.location='list.action?colaborador.id=${ordemDeServico.colaborador.id}'" class="btnVoltar"></button>
			</div>
		</@ww.form>
	
	</body>
</html>
