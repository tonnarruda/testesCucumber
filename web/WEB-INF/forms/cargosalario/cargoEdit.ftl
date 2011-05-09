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
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
	
	<style type="text/css">
	    @import url('<@ww.url includeParams="none" value="/css/fortes.css"/>');
	    @import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
	    @import url('<@ww.url includeParams="none" value="/css/jquery-ui/redmond.css"/>');
    </style>
	
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

		function limpar(data)
		{
			if(data == '' || data.length < 6)
				$("#descricaoCBO").val('');
		}
	
		$(document).ready(function() {
			var urlFind = "<@ww.url includeParams="none" value="/geral/codigoCBO/find.action"/>";
			
			$("#codigoCBO").autocomplete({
				source: function( request, response ) {
					$.ajax({
						url: urlFind,
						dataType: "json",
						data: {
							descricao: '',
							codigo: request.term
						},
						success: function( data ) {
							response( $.map( data, function( item ) {
								return {
									label: item.codigo.replace(
										new RegExp(
											"(?![^&;]+;)(?!<[^<>]*)(" +
											$.ui.autocomplete.escapeRegex(request.term) +
											")(?![^<>]*>)(?![^&;]+;)", "gi"
										), "<strong>$1</strong>" ) + " - " + item.descricao ,
									value: item.codigo,
									descricao: item.descricao
								}
							}));
						}
					});
				},
				minLength: 2,
				select: function( event, ui ) {
					$("#descricaoCBO").val(ui.item.descricao);
				}
			}).data( "autocomplete" )._renderItem = function( ul, item ) {
					return $( "<li></li>" )
						.data( "item.autocomplete", item )
						.append( "<a>" + item.label + "</a>" )
						.appendTo( ul );
			};
			
			$("#descricaoCBO").autocomplete({
				source: function( request, response ) {
					$.ajax({
						url: urlFind,
						dataType: "json",
						data: {
							descricao: request.term,
							codigo: ''
						},
						success: function( data ) {
							response( $.map( data, function( item ) {
								return {
									label: item.descricao.replace(
											new RegExp(
												"(?![^&;]+;)(?!<[^<>]*)(" +
												$.ui.autocomplete.escapeRegex(request.term) +
												")(?![^<>]*>)(?![^&;]+;)", "gi"
											), "<strong>$1</strong>" ) + " - " + item.codigo ,
									
									value: item.descricao,
									codigo: item.codigo
								}
							}));
						}
					});
				},
				minLength: 2,
				select: function( event, ui ) {
					$("#codigoCBO").val(ui.item.codigo);
				},
				change: function(){
					alet()
				} 
			}).data( "autocomplete" )._renderItem = function( ul, item ) {
					return $( "<li></li>" )
						.data( "item.autocomplete", item )
						.append( "<a>" + item.label + "</a>" )
						.appendTo( ul );
			};
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
	
	<@ww.textfield label="CBO Cód." name="cargo.cboCodigo" id="codigoCBO" cssStyle="width:60px;" maxLength="6" onkeypress = "return(somenteNumeros(event,''));" liClass="liLeft" onblur="limpar(this.value);" />
	<@ww.textfield label="Descrição" name="descricaoCBO" id="descricaoCBO" cssStyle="width: 430px;" maxLength="200"  />

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
				<@ww.textarea label="Complemento dos Conhecimentos" name="cargo.complementoConhecimento" cssStyle="width:500px;height:45px;"/>
				
				<@frt.checkListBox name="habilidadesCheck" id="habilidadesCheck" label="Habilidades" list="habilidadesCheckList" />
				<@ww.textarea label="Complemento das Habilidades" name="cargo.competencias" cssStyle="width:500px;height:45px;"/>
				
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
</body>
</html>