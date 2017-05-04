<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<#if cargo.id?exists>
	<title>Editar Cargo</title>
	<#assign formAction="update.action"/>
<#else>
	<title>Inserir Cargo</title>
	<#assign formAction="insert.action"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('nome','nomeMercado','@areasCheck'), null)"/>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ConhecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/HabilidadeDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AtitudeDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/autoCompleteFortes.js?version=${versao}"/>'></script>
	<script src='<@ww.url includeParams="none" value="/js/fortes.js?version=${versao}"/>'></script>
	<script src='<@ww.url includeParams="none" value="/js/functions.js?version=${versao}"/>'></script>
	
	<style type="text/css">
	    @import url('<@ww.url includeParams="none" value="/css/fortes.css?version=${versao}"/>');
	    @import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
	    
	    #wwgrp_descricaoCBO
	    {
			float: left;
	    	background-color: #E9E9E9;
			width: 420px;
			padding-left: 4px;
		}
    </style>
	
	<script language='javascript'>
		function populaCHA(frm, nameCheck)
		{
			DWRUtil.useLoadingMessage('Carregando...');

			var areasIds = getArrayCheckeds(frm, nameCheck);
			var conhecimentosIds = getArrayCheckeds(frm,'conhecimentosCheck');
			var habilidadesIds = getArrayCheckeds(frm,'habilidadesCheck');
			var atitudesIds = getArrayCheckeds(frm,'atitudesCheck');

			ConhecimentoDWR.getConhecimentos(areasIds, <@authz.authentication operation="empresaId"/>, function(data){
																											addChecks('conhecimentosCheck',data);
																											marcarListCheckBox(frm, 'conhecimentosCheck', conhecimentosIds);			
																										});
			
			HabilidadeDWR.getHabilidades(areasIds, <@authz.authentication operation="empresaId"/>, function(data){
																									addChecks('habilidadesCheck',data);
																									marcarListCheckBox(frm, 'habilidadesCheck', habilidadesIds);			
																								});

			AtitudeDWR.getAtitudes(areasIds, <@authz.authentication operation="empresaId"/>, function(data){
																								addChecks('atitudesCheck',data);
																								marcarListCheckBox(frm, 'atitudesCheck', atitudesIds);			
																							});
		}

		$(document).ready(function() {
			var urlFind = "<@ww.url includeParams="none" value="/geral/codigoCBO/find.action"/>";
			
			
			<#if empresaSistema.acIntegra>
				$('#codigoCBO, #descricaoCBO').attr('readOnly','readOnly')
												.attr('title','Não é possível alterar o CBO quando integrado com o Fortes Pessoal.')
												.css('background-color','#F2F2F2');
				
			<#else>
				$("#descricaoCBO").autocomplete({
					source: ajaxData(urlFind),				 
					minLength: 2,
					select: function( event, ui ) { 
						$("#codigoCBO").val(ui.item.id);
					}
				}).data( "autocomplete" )._renderItem = renderData;
	
				$('#descricaoCBO').focus(function() {
				    $(this).select();
				});
			</#if>
		});
	</script>

</head>
<body> 
<@ww.actionerror />
<@ww.actionmessage />
<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">

	<@ww.hidden label="Id" name="cargo.id" />
	<@ww.hidden name="cargo.codigoAC" />
	<@ww.hidden name="page" />

	<@ww.textfield label="Nomenclatura" name="cargo.nome" id="nome" required="true" cssStyle="width:500px;" maxLength="100"/>
	<@ww.checkbox labelPosition="right" label="Exibir no modulo externo" name="cargo.exibirModuloExterno" />
	<@ww.textfield label="Nomenclatura de Mercado" name="cargo.nomeMercado" id="nomeMercado" required="true" cssStyle="width:500px;" maxLength="100"/>
	
	<@ww.textfield label="Cód. CBO" name="cargo.cboCodigo" id="codigoCBO" onkeypress="return(somenteNumeros(event,''));" size="6"  maxLength="6" liClass="liLeft"/>
	<@ww.textfield label="Busca CBO (Código ou Descrição)" name="descricaoCBO" id="descricaoCBO" cssStyle="width: 414px;"/>
	<div style="clear:both"></div>

	<@ww.select label="Ativo" name="cargo.ativo" list=r"#{true:'Sim',false:'Não'}"/>
	<@ww.select label="Grupo Ocupacional" name="cargo.grupoOcupacional.id" list="grupoOcupacionals" emptyOption="true" listKey="id" listValue="nome" headerKey="-1"/>
	<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais Relacionadas*" list="areasCheckList" onClick="populaCHA(document.forms[0],'areasCheck');" filtro="true" selectAtivoInativo="true"/>
	<@ww.textarea label="Missão do Cargo" name="cargo.missao" cssStyle="width:500px;height:60px;"/>
	<@ww.textarea label="Fontes de Recrutamento" name="cargo.recrutamento" cssStyle="width:500px;height:30px"/>
	<@frt.checkListBox name="etapaCheck" id="etapaCheck" label="Etapas Seletivas" list="etapaSeletivaCheckList" filtro="true"/>
	<@ww.textarea label="Responsabilidades Correlatas" name="cargo.responsabilidades" id="resp" cssStyle="width:500px;height:45px;"/>
	
	<li>
		<fieldset class="fieldsetPadrao" style="width:510px;">
			<ul>
				<legend>Conhecimento, Habilidades e Atitudes (CHA)</legend>
				<@frt.checkListBox name="conhecimentosCheck" id="conhecimentosCheck" label="Conhecimentos" list="conhecimentosCheckList" filtro="true"/>
				<@ww.textarea label="Complemento dos Conhecimentos" name="cargo.complementoConhecimento" cssStyle="width:500px;height:45px;"/>
				
				<@frt.checkListBox name="habilidadesCheck" id="habilidadesCheck" label="Habilidades" list="habilidadesCheckList" filtro="true"/>
				<@ww.textarea label="Complemento das Habilidades" name="cargo.competencias" cssStyle="width:500px;height:45px;"/>
				
				<@frt.checkListBox name="atitudesCheck" id="atitudesCheck" label="Atitudes" list="atitudesCheckList" filtro="true"/>
				<@ww.textarea label="Complemento das Atitudes" name="cargo.atitude" cssStyle="width:500px;height:45px;"/>
				
				<@ww.select label="Escolaridade" name="cargo.escolaridade" list="escolaridades"  headerKey="" headerValue=""/>
				<@frt.checkListBox name="areasFormacaoCheck" id="areasFormacaoCheck" label="Áreas de Formação Relacionadas" list="areasFormacaoCheckList" filtro="true"/>
				
				<@ww.textarea label="Experiência Desejada" name="cargo.experiencia" cssStyle="width:500px;height:45px;"/>
				<@ww.textarea label="Observações" name="cargo.observacao" cssStyle="width:500px;height:45px;"/>
			</ul>
		</fieldset>
	</li>

<@ww.token/>
</@ww.form>


<div class="buttonGroup">
	<button class="btnGravar" onclick="${validarCampos};" accesskey="I">
	</button>
	<button class="btnCancelar" onclick="window.location='list.action?page=${page}'" accesskey="V">
	</button>
</div>
</body>
</html>