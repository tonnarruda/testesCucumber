8<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />

<html>
<head>
<@ww.head/>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/FatorDeRiscoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/UsuarioAjudaESocialDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formModal.js?version=${versao}"/>'></script>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url value="/css/formModal.css?version=${versao}"/>');
		@import url('<@ww.url value="/css/font-awesome.min.css?version=${versao}"/>');
		@import url('<@ww.url value="/css/buttons.css"/>');
	</style>
	
	<#if risco.id?exists>
		<title>Editar Risco</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
	<#else>
		<title>Inserir Risco</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
	</#if>

	<#assign validarCampos="return validaFormulario('form', new Array('descricao','grupoRisco', 'grupoRiscoESocial', 'hidenFatorDeRisco'), null)"/>

	<script type="text/javascript">
		var grupoRiscoESocial = "";
		$(function(){
			populaFatoresDeRisco();
			
			<#if risco.grupoRiscoESocial?exists>
				grupoRiscoESocial = '${risco.grupoRiscoESocial}'; 
			</#if>
			
			populaFatoresDeRisco();
		});
		
		function populaFatoresDeRisco(){
			DWREngine.setAsync(true);
			DWRUtil.useLoadingMessage('Carregando...');
			FatorDeRiscoDWR.findByGrupoRiscoESocial($('#grupoRiscoESocial').val(), populaListFatorDeRisco);
		}		
	
		function populaListFatorDeRisco(fatores) {
			$("#fatorDeRisco-list").html("");			
			for (var countFator in fatores){
				$("#fatorDeRisco-list").append('<li class="ui-widget-content fatoresDeRiscoLi">' +
												'<input type="hidden" class="fatorDeRisco" value="'+fatores[countFator].id+'"/>'+
												'<div class="nome">'+fatores[countFator].descricaoComCodigo+'</div>'+
												'<div style="clear:both;float: none;"></div>'+
												'</li>');
												
				if($('#fatorDeRisco0').val() != '' && $('#fatorDeRisco0').val() == fatores[countFator].id){
					$('#itemSelecionadoDescricaofatorDeRisco').text(fatores[countFator].descricaoComCodigo);
				}
			}
		}

		function onchangeGrupoRiscoESocial(valor){
			if($('#fatorDeRisco').val() != '' && grupoRiscoESocial != valor){
				$("#divSelectDialogfatorDeRisco").html("");
				$("#divSelectDialogfatorDeRisco").append("<span onclick=\"openSelectDialog('Tipo de Risco eSocial','fatorDeRisco');populaFatoresDeRisco();\" style='cursor: pointer; color: #1c96e8;'>" +
													"<i class='fa fa-plus-circle' aria-hidden='true' style='font-size: 16px;'></i> Selecione um Ítem" + 
												"</span>");
				$('#fatorDeRisco').val('');
			}
		}
				
		function submitForm(){
			validaFormulario('form', new Array('descricao','grupoRisco', 'grupoRiscoESocial', 'fatorDeRisco'));
		}
	</script>	
</head>
<body>
	<@ww.actionerror />

	<@ww.form name="form" id="form" action="${formAction}" method="POST">
		<@ww.textfield label="Nome" name="risco.descricao" id="descricao" cssClass="inputNome" maxLength="100" cssStyle="width: 502px;" required="true"/>
		<@ww.select label="Tipo de Risco" name="risco.grupoRisco" id="grupoRisco" list="grupoRiscos" cssStyle="width: 502px;" headerKey="-1" headerValue="[Selecione...]" required="true"/>
		<@ww.select label="Tipo de Risco eSocial" name="risco.grupoRiscoESocial" id="grupoRiscoESocial" list="grupoRiscosESocial" cssStyle="width: 502px;" headerKey="" headerValue="[Selecione...]" required="true"  onchange="onchangeGrupoRiscoESocial(this.value);"/>
		<@frt.selectDialog label="Fator de Risco" id="fatorDeRisco" required="true" onclick="populaFatoresDeRisco();" name="risco.fatorDeRisco.id" list="riscos" listKey="id" listValue="descricao"/>
		<@frt.checkListBox label="Equipamentos Obrigatórios (EPIs)" name="episCheck" list="episCheckList" filtro="true"/>
		<@ww.hidden label="Id" name="risco.id" />
		<@ww.token />
	</@ww.form>

	<div class="buttonGroup">
		<button type="button" onclick="submitForm()" accesskey="${accessKey}">Gravar</button>
		<button type="button" onclick="window.location='list.action'" accesskey="V">Cancelar</button>
	</div>
</body>
</html>