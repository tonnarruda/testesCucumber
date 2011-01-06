<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css"/>');
</style>
<#if cargo.id?exists>
	<title>Editar Cargo</title>
	<#assign formAction="update.action"/>
<#else>
	<title>Inserir Cargo</title>
	<#assign formAction="insert.action"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('nome','nomeMercado','@areasCheck'), null)"/>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ConhecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/HabilidadeDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AtitudeDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CodigoCBODWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
	<style type="text/css">
    @import url('<@ww.url includeParams="none" value="/css/fortes.css"/>');
    @import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
    </style>
	
	<style type="text/css">@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');</style>
	
	
	<script language='javascript'>

		function populaCHA(frm, nameCheck)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var areasIds = getArrayCheckeds(frm, nameCheck);

			ConhecimentoDWR.getConhecimentos(createListConhecimentos, areasIds, <@authz.authentication operation="empresaId"/>);
			HabilidadeDWR.getHabilidades(createListHabilidades, areasIds, <@authz.authentication operation="empresaId"/>);
			AtitudeDWR.getAtitudes(createListAtitudes, areasIds, <@authz.authentication operation="empresaId"/>);
		}

		function createListConhecimentos(data)
		{
			addChecks('conhecimentosCheck',data)
		}

		function createListHabilidades(data)
		{
			addChecks('habilidadesCheck',data)
		}

		function createListAtitudes(data)
		{
			addChecks('atitudesCheck',data)
		}
	
		jQuery(document).ready(function() {
			jQuery("#codigoCBO").keyup(function() 
			{
				var codigo = jQuery(this).val();
				if (jQuery(this).val().length == 2)
				{
					var CodigosCBOArray = new Array();
			
					CodigoCBODWR.getCodigosCBO(function(data) {
						CodigosCBOArray = data;
						jQuery("#codigoCBO").autocomplete(CodigosCBOArray);
						setTimeout(function () {
							jQuery("#codigoCBO").trigger('change');
						}, 1000);
					}, codigo);
					
				}
			});
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

	<@ww.textfield label="Nomenclatura" name="cargo.nome" id="nome" required="true" cssStyle="width:180px;" maxLength="30"/>
	<@ww.checkbox labelPosition="right" label="Exibir no modulo externo" name="cargo.exibirModuloExterno" />
	<@ww.textfield label="Nomenclatura de Mercado" name="cargo.nomeMercado" id="nomeMercado" required="true" cssStyle="width:180px;" maxLength="24"/>
	
	
	<@ww.textfield label="Código CBO" name="cargo.cboCodigo" id="codigoCBO" cssStyle="width:70px;" maxLength="6"  />
	
	
	
	<@ww.select label="Ativo" name="cargo.ativo" list=r"#{true:'Sim',false:'Não'}"/>
	<@ww.select label="Grupo Ocupacional" name="cargo.grupoOcupacional.id" list="grupoOcupacionals" emptyOption="true" listKey="id" listValue="nome" headerKey="-1"/>
	<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais Relacionadas*" list="areasCheckList" onClick="populaCHA(document.forms[0],'areasCheck');" />
	<@ww.textarea label="Missão do Cargo" name="cargo.missao" cssStyle="width:500px;height:60px;"/>
	<@ww.textarea label="Fontes de Recrutamento" name="cargo.recrutamento" cssStyle="width:500px;height:30px"/>
	<@frt.checkListBox name="etapaCheck" id="etapaCheck" label="Etapas Seletivas" list="etapaSeletivaCheckList" />
	<@ww.textarea label="Responsabilidades Correlatas" name="cargo.responsabilidades" id="resp" cssStyle="width:500px;height:45px;"/>
	
	<li>
		<fieldset class="fieldsetPadrao" style="width:510px;">
			<ul>
				<legend>Conhecimento, Habilidades e Atitudes (CHA)</legend>
				<@frt.checkListBox name="conhecimentosCheck" id="conhecimentosCheck" label="Conhecimentos" list="conhecimentosCheckList" />
				
				<@frt.checkListBox name="habilidadesCheck" id="habilidadesCheck" label="Habilidades" list="habilidadesCheckList" />
				<@ww.textarea label="Complemento das Habilidades" name="cargo.competencias" cssStyle="width:500px;height:30px;"/>
				
				<@frt.checkListBox name="atitudesCheck" id="atitudesCheck" label="Atitudes" list="atitudesCheckList" />
				<@ww.textarea label="Complemento das Atitudes" name="cargo.atitude" cssStyle="width:500px;height:45px;"/>
				
				<@ww.select label="Escolaridade" name="cargo.escolaridade" list="escolaridades"  headerKey="" headerValue=""/>
				<@frt.checkListBox name="areasFormacaoCheck" id="areasFormacaoCheck" label="Áreas de Formação Relacionadas" list="areasFormacaoCheckList"/>
				
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
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>
	<!--<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/forms/pesquisa/CodigoCBO.js"/>'></script>-->
</body>
</html>