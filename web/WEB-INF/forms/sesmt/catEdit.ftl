<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<#if cat.id?exists>
		<title>Editar Ficha de Investigação de Acidente (CAT)</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
		<#assign edicao=true>
	<#else>
		<title>Inserir Ficha de Investigação de Acidente (CAT)</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
	</#if>

	<#if cat?exists && cat.data?exists>
		<#assign data = cat.data/>
	<#else>
		<#assign data = "" />
	</#if>
	
	<#if cat?exists && cat.atestado?exists && cat.atestado.dataAtendimento?exists>
		<#assign dataAtendimento = cat.atestado.dataAtendimento/>
	<#else>
		<#assign dataAtendimento = "" />
	</#if>

	<#if cat?exists && cat.dataObito?exists>
		<#assign dataObito = cat.dataObito/>
		<#assign dataObitoDisabled = "false"/>
	<#else>
		<#assign dataObito = "" />
		<#assign dataObitoDisabled = "true"/>
	</#if>
	
	<#if cat?exists && cat.dataCatOrigem?exists>
		<#assign dataCatOrigem = cat.dataCatOrigem/>
	<#else>
		<#assign dataCatOrigem = "" />
	</#if>
	
	<#assign msgEsocial = "(Este campo será inabilitado devido a implementações do eSocial)"/>

	<#include "../ftl/mascarasImports.ftl" />
	
	<style type="text/css">
		@import url('<@ww.url value="/css/font-awesome.min.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');

		#ambiente, #funcao { font-weight: bold; }
		.testemunha {
			margin: 0px -10px 5px -7px;
			padding: 5px 10px;
			font-weight: bold;
			border-bottom: 1px solid #e7e7e7;
			background: #EBEBEB;
		}
		fieldset{
			background: #EFEFEF;
		}
		#wwgrp_cpfRegistardor{
			margin-left: 150px;
		}
	</style>

	<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/abas.css?version=${versao}"/>" media="screen" type="text/css">

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EnderecoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/BairroDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/UsuarioAjudaESocialDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/abas.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/SESMT/cat.js?version=${versao}"/>"></script>

	<script type='text/javascript'>
		$(function() {
			$("#emitiuCAT , #gerouAfastamento").click(function() {
				liberaCampo($(this));
			});
			
			$("#usavaEPI").click(function() {
				liberaMultiSelect();
			});
			
			liberaCampo($("#emitiuCAT"));
			liberaCampo($("#gerouAfastamento"));
			
			liberaMultiSelect();	
			
			$(".mascaraHora").blur(function() {
				$(this).val( $(this).val().replace(/ /g, '0') );
			});
			
			$('#registradorHelp').qtip({
				content: '<div style="text-align:justify">Opção habilitada caso tipo de registrador seja diferente de "empregador".</div>',
				style: { width: 300 }
			});
			
			$('#agenteCausadorHelp').qtip({
				content: '<div style="text-align:justify">Selecione pelo menos um "Acidente de Trabalho" ou "Situação Geradora de Doença Profissional".</div>',
				style: { width: 360 }
			});
			 
			$('#descricaoComplementarLesa').attr('maxLength','200');
			$('#diagnosticoProvavel').attr('maxLength','100');
			$('#observacaoAtestado').attr('maxLength','255');
			 
			addBuscaCEP('cep', 'ende', 'bairroNome', 'cidade', 'uf');
			addBuscaCEP('testemunha1Cep', 'testemunha1Logradouro', 'testemunha1Bairro', 'testemunha1Municipio', 'testemunha1UF');
			addBuscaCEP('testemunha2Cep', 'testemunha2Logradouro', 'testemunha2Bairro', 'testemunha2Municipio', 'testemunha2UF');
			 
			populaPartesAtingida();
			populaAgentesCausadoresAcidenteTrabalho();
			populaSituacoesGeradoraDoencaProfissional();
			
			<#if aderiuAoESocial>
				decisoes('*');
			<#else>
			 	decisoes('');
			</#if>

			<#if cat?exists && cat.obitoString?exists>
				if($('#tipo').val() != "3")
					$('#obito').removeAttr('disabled');
				else
					$('#obito').css('background', '#F6F6F6');				
			</#if>
			
			<#if cat?exists && cat.iniciatCAT?exists>
				$('#iniciatCAT').val(${cat.iniciatCAT});
			</#if>
			
			setAjudaESocial('Estamos nos adequando as exigências impostas pelo Governo Federal para atender as normas do eSocial.<br><br>'+
				'Desta forma, a partir da versão <strong>1.1.186.218</strong>, o cadastro de CAT passa a ter vários novos campos:<br><br>' + 
				'<strong>Tipo de risco eSocial:</strong> Classificação de riscos definida pelo eSocial na tabela 23 de seu leiaute.<br><br>'+
				'<strong>Fator de risco:</strong> Detalhamento dos riscos de acordo com a classificação do eSocial. Define todos os riscos que o colaborador ' + 
				'poderá estar exposto.', '<@ww.url value="/imgs/esocial.png"/>', 'imgAjudaEsocial');
		
			<#if exibeDialogAJuda>
				dialogAjudaESocial();
				UsuarioAjudaESocialDWR.saveUsuarioAjuda(${usuarioLogado.id}, "${telaAjuda?string}");
			</#if>
		});
		
		function populaPartesAtingida(){
			$('#formSelectDialogparteCorpoAtingida').live('dialogclose', function() {
			    adicionarLateralidade();
			    addRemover('parteCorpoAtingida');
			});
			
			<#if cat?exists && cat.partesAtingida?exists>
				contador = 0;
				id = 'parteCorpoAtingida';
				titulo = 'Partes do Corpo Atingida';
				<#list cat.partesAtingida as parteAtingida>
					setselectedSelectDialog(titulo, id, '${parteAtingida.parteCorpoAtingida.codigoDescricao}', '${parteAtingida.parteCorpoAtingida.id}', contador);
					adicionarLateralidade('divSelectDialogparteCorpoAtingida' + contador, '${parteAtingida.lateralidade}');
					adicionarItem(id, titulo);				
					contador++;
				</#list>
			</#if>
		}
		
		function populaAgentesCausadoresAcidenteTrabalho(){
			$('#formSelectDialogagenteCausadorAcidenteTrabalho').live('dialogclose', function() {
			    addRemover('agenteCausadorAcidenteTrabalho');
			});
			
			<#if cat?exists && cat.agentesCausadoresAcidenteTrabalho?exists>
				contador = 0;
				id = 'agenteCausadorAcidenteTrabalho';
				titulo = 'Agente Causador do Acidente de Trabalho';
				<#list cat.agentesCausadoresAcidenteTrabalho as agenteCausadorAcidenteTrabalho>
					setselectedSelectDialog(titulo, id, '${agenteCausadorAcidenteTrabalho.codigoDescricao}', '${agenteCausadorAcidenteTrabalho.id}', contador);
					adicionarItem(id, titulo);				
					contador++;
				</#list>
			</#if>
		}
		
		function populaSituacoesGeradoraDoencaProfissional(){
			$('#formSelectDialogsituacaoGeradoraDoencaProfissional').live('dialogclose', function() {
			    addRemover('situacaoGeradoraDoencaProfissional');
			});
			
			<#if cat?exists && cat.situacoesGeradoraDoencaProfissional?exists>
				contador = 0;
				id = 'situacaoGeradoraDoencaProfissional';
				titulo = 'Agente Causador/Situação Geradora de Doença Profissional';
				<#list cat.situacoesGeradoraDoencaProfissional as situacaoGeradoraDoencaProfissional>
					setselectedSelectDialog(titulo, id, '${situacaoGeradoraDoencaProfissional.codigoDescricao}', '${situacaoGeradoraDoencaProfissional.id}', contador);
					adicionarItem(id, titulo);				
					contador++;
				</#list>
			</#if>
		}
		
		contadorParteCorpoAtingida = 1;
		contadorAgenteCausadorAcidenteTrabalho = 1;
		contadorSituacaoGeradoraDoencaProfissional = 1;
		srcImg = '<@ww.url includeParams="none" value="/imgs/remove.png"/>';
		
		function adicionarItem(item, titulo){
		
			if(item == 'parteCorpoAtingida'){
				count = contadorParteCorpoAtingida
				contadorParteCorpoAtingida++;
			}else if(item == 'agenteCausadorAcidenteTrabalho'){
				count = contadorAgenteCausadorAcidenteTrabalho;
				contadorAgenteCausadorAcidenteTrabalho++;
			}else if(item == 'situacaoGeradoraDoencaProfissional'){
				count = contadorSituacaoGeradoraDoencaProfissional;
				contadorSituacaoGeradoraDoencaProfissional++; 
			}
			
			idDiv = "divSelectDialog" + item + count;
			$('#block' + item).append("<div id='" + idDiv + "' class='divSelectDialog' style='width: 600px;text-align: justify;border: 1px solid #BEBEBE;padding: 10px;margin-bottom: 4px;border-radius: 3px;background-color: #f3f3f3;'>" +
										"<span class='openSelectDialog' onclick=\"openSelectDialog('" + titulo + "','" + item + "','" + count + "');\" style='cursor: pointer; color: #1c96e8;'>" +
											"<i class='fa fa-plus-circle' aria-hidden='true' style='font-size: 16px;'></i>" +
											" Selecione um Ítem" +
										"</span>" +
									"</div>");
			
			addRemover(item);
		}
		
		function adicionarLateralidade(id, lateralidade){
			if(id == undefined){
				$('#blockparteCorpoAtingida').find('.selectDialog').each(function(){
					if($(this).parent().find('.lateralidade').length == 0)
						id = $(this).parent().attr('id');
				});
			}
			
			$("#" + id).find('.lateralidade').remove();
			$("#" + id).append("" +
				"<div class='lateralidade'>" +
					"</br><Label>Lateralidade:*</label>" +
					"<select name='lateralidadesSelecionadas' id='select" + id + "' class='selectLateralidade' style='width:590px;'>" +
					    "<option value=''>Selecione...</option>" +
					    <#list lateralidades?keys as keyLateralidade>
					    	"<option value='${keyLateralidade}'>${lateralidades.get(keyLateralidade)}</option>" +
					    </#list>
					"</select>" +
				"</div>"	
			);
			
			if(lateralidade != undefined)
				$('#select' + id).val(lateralidade);		
		}
		
		function addRemover(item){
			$('#block' + item).find('.fontOuser').remove();
			$('#block' + item).find('.openSelectDialog').parent().prepend("" +
						"<a class='fontOuser' onmouseover=\"$(this).find('i').addClass('fa-2x').css('color','#6965ec')\" onmouseout=\"$(this).find('i').removeClass('fa-2x').css('color','black')\"" +
						"style='cursor:pointer; float: right; margin: -10px -10px -80px 595px;' onclick='$(this).parent().find(\"input\").remove();$(this).parent().remove();checkSelecao(\"" + item + "\");' >" +
						"<i title='Remover' class='fa fa-times fa-lg' aria-hidden='true'></i>&nbsp;</a>");
		}
		
		function checkSelecao(item){
			if($('#block' + item).find('div').length == 0){
				if(item == 'parteCorpoAtingida'){
					titulo = 'Partes do Corpo Atingida';
				}else if(item == 'agenteCausadorAcidenteTrabalho'){
					titulo = 'Agente Causador do Acidente de Trabalho';
				}else if(item == 'situacaoGeradoraDoencaProfissional'){
					titulo = 'Agente Causador/Situação Geradora de Doença Profissional';
				}
				
				adicionarItem(item, titulo);
			}
		}
		
		function liberaMultiSelect(){
			if ($("#usavaEPI").attr('checked')){
				$(".listCheckBoxContainer [name='episChecked']").removeAttr('disabled');
				$(".listCheckBoxContainer").css('background-color', '#FFF');
			} else {
				$(".listCheckBoxContainer [name='episChecked']").attr('disabled', 'disabled');
				$(".listCheckBoxContainer").css('background-color', '#EFEFEF');
			}
		}
		
		function getFuncaoAmbiente(colaboradorId){
			ColaboradorDWR.findFuncaoAmbiente(colaboradorId, {
				callback: function(dados) {
					$('#ambiente').text(dados['ambienteNome']);
					$('#funcao').text(dados['funcaoNome']);
					
					$('#ambienteId').val(dados['ambienteId']);
					$('#funcaoId').val(dados['funcaoId']);
				}
			});
		}
		
		function submeterFormulario(){
			corCinza = '#F6F6F6';
			corAmarela = '#FFEEC2';
			enviarFormulario = true;
			exibeLabelDosCamposNaoPreenchidos = true;
			desmarcarAbas();
			
			$('.selectDialog').each(function(){
				if($(this).val() == '')
					$(this).remove();
			});

			camposObrigatorios = new Array('colaborador', 'data');
			camposValidos = new Array('data','dataCatOrigem','dataAtendimento','horario','cep','horasTrabalhadasAntesAcidente',
						'testemunha1Cep','testemunha2Cep','testemunha1Telefone','testemunha2Telefone');
			
			<#if aderiuAoESocial>
		 		if($('#blockagenteCausadorAcidenteTrabalho').find('.selectDialog').length == 0 && $('#blocksituacaoGeradoraDoencaProfissional').find('.selectDialog').length == 0){ 
		 			enviarFormulario = false;
		 			$('#blockagenteCausadorAcidenteTrabalho').find('div').css('background-color', corAmarela);
		 			$('#blocksituacaoGeradoraDoencaProfissional').find('div').css('background-color', corAmarela);
		 			camposNaoPreenchidos.push($('#blockagenteCausadorAcidenteTrabalho').find('label').text().replace(':','').replace('*','').replace('\n','').trim());
		 			camposNaoPreenchidos.push($('#blocksituacaoGeradoraDoencaProfissional').find('label').text().replace(':','').replace('*','').replace('\n','').trim());
		 			$('#aba6 a' ).css('color','red');
		 		}else{
		 			$('#blockagenteCausadorAcidenteTrabalho').find('div').css('background-color', corCinza);
		 			$('#blocksituacaoGeradoraDoencaProfissional').find('div').css('background-color', corCinza);
		 			$('#aba6 a' ).css('color','');
		 		}

		 		camposObrigatoriosEsocial = new Array('&codificacaoAcidenteTrabalho', '&parteCorpoAtingida', 'tipoRegistardor','horario',
		 									'horasTrabalhadasAntesAcidente','tipo', 'obito', 'comunicouPolicia', 'iniciatCAT', 'tipoLocal');
		 		
		 		if($('#tipoRegistardor').val() != '' &&  $('#tipoRegistardor').val() != 1)
		 			camposObrigatoriosEsocial.push('tipoInscricao');
		 			
		 			
		 		if($('#tipoInscricao').val() != '' &&  $('#tipoInscricao').val() == 1){
		 			camposObrigatoriosEsocial.push('cnpjRegistardor');
		 			camposValidos.push('cnpjRegistardor');
		 		}else if($('#tipoInscricao').val() != '' &&  $('#tipoInscricao').val() == 2){
		 			camposObrigatoriosEsocial.push('cpfRegistardor');
		 			camposValidos.push('cpfRegistardor');
		 		}
		 		
		 		if(($('#obito').val() != '' &&  $('#obito').val() == "true") || ($('#tipo').val() != '' && $('#tipo').val() == 3))
		 			camposObrigatoriosEsocial.push('dataObito');	
		 		
		 		if($('#emitiuCAT').is(':checked'))
		 			camposObrigatoriosEsocial.push('dataCatOrigem');	
		 		
		 		if($('#possuiAtestado').is(':checked')){
		 			camposAtestado = new Array('dataAtendimento','horaAtendimento','indicativoInternacao',
		 			    'duracaoTratamentoEmDias','indicativoAfastamento','codCID','medicoNome','orgaoDeClasse','numericoInscricao');
		 			
		 			camposObrigatoriosEsocial = camposObrigatoriosEsocial.concat(camposAtestado);	
		 		}
		 		
		 		if($('#possuiAtestado').is(':checked') && $('#blockdescricaoNaturezaLesao').find('.selectDialog').length == 0) 
		 			camposObrigatoriosEsocial.push('&descricaoNaturezaLesao');
		 		
		 		camposValidos.push('horaAtendimento');
		 		camposObrigatorios = camposObrigatorios.concat(camposObrigatoriosEsocial);
			</#if>
			
			formularioValidado = validaFormulario('form', camposObrigatorios, camposValidos, true);
			
			if(enviarFormulario && formularioValidado){
			
				$('#blockparteCorpoAtingida').find('.selectDialog').each(function(){
					if($(this).parent().find('.selectLateralidade').val() == ''){
						$(this).parent().css('background-color', corAmarela);
						$('#aba5 a' ).css('color','red');
						enviarFormulario = false;
						jAlert('Partes do Corpo Atingida necessita de Lateralidade');
						return;
					}else{
						$(this).parent().css('background-color', corCinza);
						$('#aba5 a' ).css('color','');
					}
				});
				
				if(enviarFormulario){
					$('#obito').removeAttr('disabled');
					
					$('#blockparteCorpoAtingida').find('.selectDialog').each(function(){
						$(this).val($(this).val() + "_" + $(this).parent().find('.selectLateralidade').val());
					});
					
					document.form.submit();
				}
			}
		}
		
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#if !edicao?exists>
		<@ww.form name="formFiltro" action="filtrarColaboradores.action" method="POST">
		<li>
			<@ww.div cssClass="divInfo" cssStyle="width: 490px;">
				<ul>
					<@ww.textfield label="Nome" name="colaborador.nome" id="nomeColaborador" cssStyle="width: 300px;"/>
					<@ww.textfield label="Matrícula" name="colaborador.matricula" id="matriculaBusca" liClass="liLeft" cssStyle="width: 60px;"/>
					
					<@ww.textfield label="CPF" name="colaborador.pessoal.cpf" id="cpfColaborador" cssClass="mascaraCpf"/>

					<div class="buttonGroup">
						<button onclick="validaFormulario('formFiltro', null, null);" class="btnPesquisar grayBGE"></button>
						<button onclick="document.formFiltro.action='list.action';document.formFiltro.submit();" class="btnVoltar grayBGE"></button>
					</div>
				</ul>
			</@ww.div>
		</li>
		</@ww.form>
	</#if>

	<#if (colaboradors?exists && colaboradors?size > 0) || (edicao?exists)>

		<@ww.form name="form" id="form" action="${formAction}" method="POST" onsubmit="submeterFormulario();" validate="true" enctype="multipart/form-data">

			<#if cat.colaborador?exists>
				<b>Colaborador: ${cat.colaborador.nome}<br>
					Ambiente: <#if cat.ambienteColaborador?exists>${cat.ambienteColaborador.nome}</#if><br>
					Função: <#if cat.funcaoColaborador?exists>${cat.funcaoColaborador.nome}</#if>
				</b>
				<p></p>
				<@ww.hidden id="colaborador" name="cat.colaborador.id" />
			</#if>

			<#if (colaboradors?exists && colaboradors?size > 0)>
				<@ww.select label="Colaborador" name="cat.colaborador.id" id="colaborador" required="true" list="colaboradors" listKey="id" listValue="nomeCpfMatricula" onchange="getFuncaoAmbiente(this.value)" headerKey="" headerValue="Selecione..." cssStyle="width:502px;"/>
				Ambiente atual do Colaborador: <span id="ambiente"></span><br />
				Função atual do Colaborador: <span id="funcao"></span><br /><br clear="all"/>
			</#if>
			</br>
			<div id="abas">
				<div id="aba1" class="abas"><a href="javascript: abas(1)">Registardor</a></div>
				<div id="aba2" class="abas"><a href="javascript: abas(2)">Dados do Acidente</a></div>
				<div id="aba3" class="abas"><a href="javascript: abas(3)">Dados Complementares</a></div>
				<div id="aba4" class="abas"><a href="javascript: abas(4)">Local do Acidente</a></div>
				<div id="aba5" class="abas"><a href="javascript: abas(5)">Parte Atingida</a></div>
				<div id="aba6" class="abas"><a href="javascript: abas(6)">Agente Causador</a></div>
				<div id="aba7" class="abas"><a href="javascript: abas(7)">Atestado</a></div>
				<div id="aba8" class="abas"><a href="javascript: abas(8)">Testemunhas</a></div>
			</div>
				
			<div id="content1" class="contents">
				<@ww.select label="Tipo de Registrador" name="cat.tipoRegistardor" required="${aderiuAoESocial?string}" id="tipoRegistardor" onchange="decideTipoInscricao();" list="tiposRegistradores" cssStyle="width:400px;"  headerKey="" headerValue="Selecione..."/>
				<@ww.select label="Tipo de Inscrição" name="cat.tipoInscricao" id="tipoInscricao" list="tiposInscricao" onchange="decideCpfOuCnpj()" cssStyle="width:400px;" headerKey="" headerValue="Selecione..."/>
				<@ww.textfield label="CNPJ" id="cnpjRegistardor" name="cat.cnpjRegistardor" cssClass="mascaraCnpj" liClass="liLeft" />
				<@ww.textfield label="CPF" id="cpfRegistardor" name="cat.cpfRegistardor" cssClass="mascaraCpf"/>
				<img id='registradorHelp' src="<@ww.url value='/imgs/help.gif'/>" width='16' height='16' style='margin-left: 405px;margin-top: -60px;vertical-align: top;' />
			</div>
			
			<div id="content2" class="contents" style="display: none;">
				<@ww.datepicker label="Data do Acidente" required="true" id="data" name="cat.data" value="${data}" cssClass="mascaraData"/>
				<@ww.textfield label="Hora do Acidente" required="${aderiuAoESocial?string}" id="horario" name="cat.horario" cssStyle="width:40px;" maxLength="5" cssClass="mascaraHora"/>
				<@ww.textfield label="Horas Trabalhadas Antes do Acidente" required="${aderiuAoESocial?string}" id="horasTrabalhadasAntesAcidente" name="cat.horasTrabalhadasAntesAcidente" cssStyle="width:40px;" maxLength="5" cssClass="mascaraHora"/>

				<#if cat.tipoAcidente?exists>
					<@ww.select label="Tipo de Acidente (Antigo)" name="cat.tipoAcidente" id="tipoAcidente" list="tipoAcidentes" cssStyle="width:910px;background-color:#F6F6F6"  headerKey="" headerValue="Selecione..." disabled="true"/>
					<@ww.hidden name="cat.tipoAcidente" />
				</#if>
				<@frt.selectDialog label="Tipo de Acidente de Trabalho" width="600px" id="codificacaoAcidenteTrabalho" name="cat.codificacaoAcidenteTrabalho.id" list="codificacoesAcidenteTrabalho" listKey="id" listValue="codigoDescricao" required="${aderiuAoESocial?string}" addRemover=true/>
				
				<@ww.select label="Tipo" name="cat.tipo" id="tipo" onchange="decideObito();" list="tiposCat" cssStyle="width:622px;"  headerKey="" headerValue="Selecione..." required="${aderiuAoESocial?string}"/>
				<@ww.select disabled="true" label="Houve Óbito?" name="cat.obitoString" id="obito" onchange="decideDataObito();" list=r"#{'true':'Sim','false':'Não'}" cssStyle="width:622px;" headerKey="" headerValue="Selecione..." required="${aderiuAoESocial?string}"/>
				<@ww.datepicker label="Data do Óbito" id="dataObito" name="cat.dataObito" value="${dataObito}" cssClass="mascaraData" disabled="${dataObitoDisabled}"/>
				
				<fieldset style="width:600px;">
					<legend>
						<@ww.checkbox id="emitiuCAT" onchange="decideNumero();" name="cat.emitiuCAT" theme="simple"/>
						Emitiu CAT?
					</legend>
					<@ww.datepicker label="Data da CAT Origem" id="dataCatOrigem" name="cat.dataCatOrigem" value="${dataCatOrigem}" cssClass="mascaraData" required="true"/>
					<@ww.textfield label="Número CAT" id = "numero" name="cat.numeroCat" cssStyle="width:152px;" maxLength="20"/>
				</fieldset></br>
				
				<@ww.select label="Houve comunicação à autoridade policial?" name="cat.comunicouPoliciaString" id="comunicouPolicia" list=r"#{'true':'Sim','false':'Não'}" cssStyle="width:622px;"  headerKey="" headerValue="Selecione..." required="${aderiuAoESocial?string}"/>
				<@ww.select label="Situação Geradora do Acidente de Trabalho" id="situacaoGeradoraAcidenteTrabalho" name="cat.situacaoGeradoraAcidenteTrabalho.id" list="situacoesGeradoraAcidenteTrabalho" cssStyle="width:622px;" listKey="id" listValue="codigoDescricao" headerKey="" headerValue="Selecione..."/>
				<@ww.select label="A CAT foi emitida por" name="cat.iniciatCAT" id="iniciatCAT" list="iniciativasCat" cssStyle="width:622px;"  headerKey="" headerValue="Selecione..." required="${aderiuAoESocial?string}"/>
				<@ww.textarea label="Observação" name="cat.observacao" cssStyle="width:622px;" />
			</div>
			
			<div id="content3" class="contents" style="display: none;">
				<#if cat.fotoUrl?exists >
					<div id="divFoto">
						<table style="border:none;">
							<tr>
								<td>
									<#if cat.id?exists && cat.fotoUrl != "">
										<a title="Clique para ver a foto no tamanho original" href="<@ww.url includeParams="none" value="showFoto.action?cat.fotoUrl=${cat.fotoUrl}"/>" target="_blank">
											<img src="<@ww.url includeParams="none" value="showFoto.action?cat.fotoUrl=${cat.fotoUrl}"/>" width="300" id="fotoImg"/>
										</a>
									</#if>
								</td>
								<td>
									<@ww.checkbox label="Manter foto do acidente" name="manterFoto" onclick="$('#divFotoUpload').toggle(!this.checked);" value="true" labelPosition="left" checked="checked"/>
									<div id="divFotoUpload" style="display:none;">
										<@ww.file label="Nova foto do acidente" name="fotoAcidente" id="fotoAcidente"/>
									</div>
								</td>
							</tr>
						</table>
					</div>
		        <#else>
					<@ww.file label="Foto do acidente" name="fotoAcidente" id="fotoAcidente" cssStyle="width:502px;"/>
		        </#if>
				
				<@ww.select label="Natureza da Lesão" name="cat.naturezaLesao.id" id="naturezaLesao" list="naturezaLesaos" listKey="id" listValue="descricao" headerKey="" headerValue="Selecione..." cssStyle="width:502px;"/>
				<div style="margin-top: 5px; margin-bottom: 5px;">Qtde de dias debitados: <@ww.textfield label="" id="qtdDiasDebitados" name="cat.qtdDiasDebitados" cssStyle="width:25px;" maxLength="3" onkeypress="return(somenteNumeros(event,''));" theme="simple"/></div>
				<@ww.checkbox label="Gerou afastamento?" id="gerouAfastamento" name="cat.gerouAfastamento" labelPosition="left" onchange="decideQtdDiasAfastado()"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Qtde de dias afastados:<@ww.textfield label="" id="qtdDiasAfastado" name="cat.qtdDiasAfastado" cssStyle="width:25px;" maxLength="3" theme="simple"  onkeypress="return(somenteNumeros(event,''));"/>
				<br />
				<@ww.checkbox label="Foi Treinado para a Função?" id="treinado" name="cat.foiTreinadoParaFuncao" labelPosition="left" />
				
				<@ww.checkbox label="Usava EPI?" id="usavaEPI" name="cat.usavaEPI" labelPosition="left"/>
				<@frt.checkListBox form="document.getElementById('form')" label="EPIs" name="episChecked" id="episChecked" list="episCheckList" filtro="true"/>
				
				<@ww.textarea label="Conclusão da Comissão" name="cat.conclusao" cssStyle="width:500px;" />
				
				<fieldset style="width:480px;">
					<@ww.checkbox label="Limitação funcional?" id="limitacaoFuncional" name="cat.limitacaoFuncional" labelPosition="left"/>
					<@ww.textarea label="Observação" name="cat.obsLimitacaoFuncional" cssStyle="width:477px;" />
				</fieldset>
			</div>
			
			<div id="content4" class="contents" style="display: none;">
				<#if !cat.local?exists>
					<@ww.textfield label="Local do Acidente ${msgEsocial}" id="local" name="cat.local" cssStyle="width:635px;background-color:#F6F6F6" maxLength="100" disabled="true"/>
					<@ww.hidden name="cat.local" />
				</#if>
				
				<@ww.select label="Tipo" name="cat.tipoLocal" id="tipoLocal" list="tiposLocalAcidente" cssStyle="width:635px;"  headerKey="" headerValue="Selecione..." required="${aderiuAoESocial?string}"/>
				<@ww.textfield label="CEP" name="cat.endereco.cep" id="cep" cssClass="mascaraCep" liClass="liLeft"/>
				<@ww.textfield label="Logradouro" name="cat.endereco.logradouro" id="ende" cssStyle="width: 300px;" liClass="liLeft" maxLength="80"/>
				<@ww.textfield label="Nº"  name="cat.endereco.numero" id="num" cssStyle="width:40px;" liClass="liLeft" maxLength="10"/>
				<@ww.textfield label="Complemento" name="cat.endereco.complemento" id="complemento" cssClass="campo-integrado" cssStyle="width: 205px;" maxLength="30"/>
				<@ww.select label="Estado" name="cat.endereco.uf.id" id="uf" list="estados" liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue=""/>
				<@ww.select label="Cidade" name="cat.endereco.cidade.id" id="cidade" list="cidades" liClass="liLeft" listKey="id" listValue="nome" cssStyle="width: 245px;" headerKey="" headerValue=""/>
				<@ww.textfield label="Bairro" name="cat.endereco.bairro" id="bairroNome" cssStyle="width: 330px;" maxLength="60"/>
				<@ww.div id="bairroContainer"/>
			</div>
			
			<div id="content5" class="contents" style="display: none;">
				<#if !cat.parteAtingida?exists>
					<@ww.textfield label="Parte do Corpo Atingida ${msgEsocial}" id="parteAtingida" name="cat.parteAtingida" cssStyle="width:620px;background-color:#F6F6F6" maxLength="100" disabled="true"/>
					<@ww.hidden name="cat.parteAtingida" />
				</#if>
				<@frt.selectDialog label="Partes do Corpo Atingida" width="600px" required="${aderiuAoESocial?string}" id="parteCorpoAtingida" name="partesCorpoAtingidaSelecionados" list="partesCorpoAtingida" listKey="id" listValue="codigoDescricao"/>
				
				</br>
				<a href="javascript:;" onclick="javascript:adicionarItem('parteCorpoAtingida', 'Partes do Corpo Atingida');" style="text-decoration: none;margin-left: 5px;">
					<img src='<@ww.url includeParams="none" value="/imgs/add.png"/>'/> 
					Adicionar parte do corpo atingida
				</a>
			</div>
			
			<div id="content6" class="contents" style="display: none;">
				<#if !cat.fonteLesao?exists>
					<@ww.textfield label="Fonte da Lesão ${msgEsocial}" id="fonteLesao" name="cat.fonteLesao" cssStyle="width:620px;background-color:#F6F6F6" maxLength="100" disabled="true"/>
					<@ww.hidden name="cat.fonteLesao" />
				</#if>
				
				<fieldset>
					<legend>Agente Causador:*</legend>
				<img id='agenteCausadorHelp' src="<@ww.url value='/imgs/help.gif'/>" width='16' height='16' style='margin-left: 128px;margin-top: -20px;vertical-align: top;' />
					<@frt.selectDialog label="Acidente de Trabalho" width="600px" id="agenteCausadorAcidenteTrabalho" name="agentesCausadoresAcidenteTrabalhoSelecionados" list="agentesCausadoresAcidenteTrabalho" listKey="id" listValue="codigoDescricao"/>
					</br>
					<a href="javascript:;" onclick="javascript:adicionarItem('agenteCausadorAcidenteTrabalho', 'Agente Causador do Acidente de Trabalho');" style="text-decoration: none;margin-left: 5px;">
						<img src='<@ww.url includeParams="none" value="/imgs/add.png"/>'/> 
						Adicionar agente causador do acidente de trabalho
					</a> 
					</br></br>
					<@frt.selectDialog label="Situação Geradora de Doença Profissional" width="600px" id="situacaoGeradoraDoencaProfissional" name="situacoesGeradoraDoencaProfissionalSelecionados" list="situacoesGeradorasDoencaProfissional" listKey="id" listValue="codigoDescricao" />
					</br>
					<a href="javascript:;" onclick="javascript:adicionarItem('situacaoGeradoraDoencaProfissional', 'Agente Causador/Situação Geradora de Doença Profissional');" style="text-decoration: none;margin-left: 5px;">
						<img src='<@ww.url includeParams="none" value="/imgs/add.png"/>'/> 
						Adicionar agente causador/situação geradora de doença profissional
					</a>
				</fieldset> 
			</div>
			
			<div id="content7" class="contents" style="display: none;">
				<fieldset>
					<legend>
						<@ww.checkbox id="possuiAtestado" onchange="decideAtestado();" name="cat.atestado.possuiAtestado" theme="simple"/>
						Possui Atestado?
					</legend>
					<@ww.textfield label="Cadastro Nacional de Estabelecimento de Saúde" id="codigoCNES" name="cat.atestado.codigoCNES" maxLength="7" cssStyle="width:320px;"/>
					<@ww.datepicker label="Data do Acidente" id="dataAtendimento" name="cat.atestado.dataAtendimento" value="${dataAtendimento}" cssClass="mascaraData atestado"/>
					<@ww.textfield label="Hora do Atendimento" id="horaAtendimento" name="cat.atestado.horaAtendimento" cssStyle="width:40px;" maxLength="5" cssClass="mascaraHora atestado"/>
					<@ww.select cssClass="atestado" label="Houve Internação" name="cat.atestado.indicativoInternacaoString" id="indicativoInternacao" list=r"#{'true':'Sim','false': 'Não'}" cssStyle="width:120px;"  headerKey="" headerValue="Selecione..."/>
					<@ww.textfield cssClass="atestado" label="Duração Estimada do Tratamento, em Dias" id="duracaoTratamentoEmDias" name="cat.atestado.duracaoTratamentoEmDias" cssStyle="width:100px;" maxLength="4"/>
					<@ww.select cssClass="atestado" label="Houve Afastamento" name="cat.atestado.indicativoAfastamentoString" id="indicativoAfastamento" list=r"#{'true':'Sim','false': 'Não'}" cssStyle="width:135px;"  headerKey="" headerValue="Selecione..."/>
					<@ww.textfield cssClass="atestado" label="CID" id="codCID" name="cat.atestado.codCID" cssStyle="width:100px;" maxLength="4"/>
					<@frt.selectDialog label="Descrição da natureza da lesão" width="600px" id="descricaoNaturezaLesao" name="cat.atestado.descricaoNaturezaLesao.id" list="descricoesNaturezaLesoes" listKey="id" listValue="codigoDescricao" addRemover=true/>
					<@ww.textarea label="Descrição Complementar da Lesão" id="descricaoComplementarLesa" name="cat.atestado.descricaoComplementarLesao" cssStyle="height:50px;width:620px;"/>
					<@ww.textarea label="Diagnóstico Provável" id="diagnosticoProvavel" name="cat.atestado.diagnosticoProvavel" cssStyle="height:50px;width:620px;"/>
					<@ww.textarea label="Observação" id="observacaoAtestado" name="cat.atestado.observacaoAtestado" cssStyle="height:60px;width:620px;"/>
	
					<fieldset style="width:600px;">
						<legend>Médico/Dentista que emitiu o atestado</legend>
						<@ww.textfield cssClass="atestado" label="Nome do Médico/Dentista" id="medicoNome" name="cat.atestado.medicoNome" cssStyle="width:500px;" maxLength="70"/>
						<@ww.select cssClass="atestado" label="Órgão de Classe" name="cat.atestado.orgaoDeClasse" id="orgaoDeClasse" list="orgaosDeClasse" cssStyle="width:500px;"  headerKey="" headerValue="Selecione..."/>
						<@ww.textfield cssClass="atestado" label="Número de Inscrição no Órgão de Classe" id="numericoInscricao" name="cat.atestado.numericoInscricao" cssStyle="width:300px;" maxLength="14"/>	
						<@ww.select label="Estado" name="cat.atestado.ufAtestado.id" id="ufAtestado" list="estados" cssStyle="width: 110px;" listKey="id" listValue="sigla" headerKey="" headerValue="Selecione..."/>
					</fieldset>
				</fieldset>
			</div>
			
			<div id="content8" class="contents" style="display: none;">
				<table>
					<td width="50%">
						<fieldset style="width:440px;">
							<div class="testemunha">1ª Testemunha</div>
							<@ww.textfield label="Nome" id="testemunha1Nome" name="cat.testemunha1.nome" cssStyle="width:440px;" maxLength="100" liClass="liLeft"/>
							<@ww.textfield label="CEP" id="testemunha1Cep" name="cat.testemunha1.cep" maxLength="8" cssStyle="width:75px;" liClass="liLeft" cssClass="mascaraCep"/>
							<@ww.textfield label="Logradouro" id="testemunha1Logradouro" name="cat.testemunha1.logradouro" cssStyle="width:357px;" maxLength="100"/>
							<@ww.textfield label="Núm." id="testemunha1Numero" name="cat.testemunha1.numero" cssStyle="width:47px;" maxLength="10" liClass="liLeft"/>
							<@ww.textfield label="Complemento" id="testemunha1Complemento" name="cat.testemunha1.complemento" cssStyle="width:100px;" maxLength="20" liClass="liLeft"/>
							<@ww.textfield label="Bairro" id="testemunha1Bairro" name="cat.testemunha1.bairro" cssStyle="width:277px;" maxLength="100"/>
							<@ww.textfield label="Município" id="testemunha1Municipio" name="cat.testemunha1.municipio" cssStyle="width:248px;" maxLength="100" liClass="liLeft"/>
							<@ww.textfield label="UF" id="testemunha1UF" name="cat.testemunha1.uf" cssStyle="width:29px;" maxLength="2" liClass="liLeft"/>
							<@ww.textfield label="DDD" id="testemunha1DDD" name="cat.testemunha1.ddd" cssStyle="width:29px;" maxLength="2" liClass="liLeft"/>
							<@ww.textfield label="Telefone" id="testemunha1Telefone" name="cat.testemunha1.telefone" cssStyle="width:105px;" maxLength="9"/>
						</fieldset>
					</td>
					<td width="50%">
						<fieldset style="width:440px;margin-left:10px">
						<div class="testemunha">2ª Testemunha</div>
							<@ww.textfield label="Nome" id="testemunha2Nome" name="cat.testemunha2.nome" cssStyle="width:440px;" maxLength="100" liClass="liLeft"/>
							<@ww.textfield label="CEP" id="testemunha2Cep" name="cat.testemunha2.cep" maxLength="8" cssStyle="width:75px;" liClass="liLeft" cssClass="mascaraCep"/>
							<@ww.textfield label="Logradouro" id="testemunha2Logradouro" name="cat.testemunha2.logradouro" cssStyle="width:357px;" maxLength="100"/>
							<@ww.textfield label="Núm." id="testemunha2Numero" name="cat.testemunha2.numero" cssStyle="width:47px;" maxLength="10" liClass="liLeft"/>
							<@ww.textfield label="Complemento" id="testemunha2Complemento" name="cat.testemunha2.complemento" cssStyle="width:100px;" maxLength="20" liClass="liLeft"/>
							<@ww.textfield label="Bairro" id="testemunha2Bairro" name="cat.testemunha2.bairro" cssStyle="width:277px;" maxLength="100"/>
							<@ww.textfield label="Município" id="testemunha2Municipio" name="cat.testemunha2.municipio" cssStyle="width:248px;" maxLength="100" liClass="liLeft"/>
							<@ww.textfield label="UF" id="testemunha2UF" name="cat.testemunha2.uf" cssStyle="width:29px;" maxLength="2" liClass="liLeft"/>
							<@ww.textfield label="DDD" id="testemunha1DDD" name="cat.testemunha2.ddd" cssStyle="width:29px;" maxLength="2" liClass="liLeft"/>
							<@ww.textfield label="Telefone" id="testemunha2Telefone" name="cat.testemunha2.telefone" cssStyle="width:105px;" maxLength="9"/>
						</fieldset>
					</td>
				</table>
			</div>
			
			<@ww.hidden name="cat.ambienteColaborador.id" id="ambienteId" />
			<@ww.hidden name="cat.funcaoColaborador.id" id="funcaoId" />
			<@ww.hidden name="cat.testemunha1.id" />
			<@ww.hidden name="cat.testemunha2.id" />
			<@ww.hidden name="cat.id" />
			<@ww.hidden name="cat.fotoUrl" />
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="submeterFormulario();" class="btnGravar" accesskey="${accessKey}"> </button>
			<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V"> </button>
		</div>
	</#if>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/forms/geral/bairros.js?version=${versao}"/>'></script>
</body>
</html>