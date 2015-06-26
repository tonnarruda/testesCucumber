<html>
<head>
<@ww.head/>
<#if perfil.id?exists>
	<title>Editar Perfil</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Perfil</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
  	</style>

	<#assign validarCampos="return validaFormulario('form', new Array('nome'), null)"/>
	<script src='<@ww.url includeParams="none" value="/js/arvoreCheck.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	<script type="text/javascript">
		$(function() {
			<#list papeisComHelp as papel>
				$('#help_' + '${papel.idExibir}').qtip({
					content: '${papel.help}'
					,
	         		position: {
	                  corner: {
	                     tooltip: 'leftMiddle', 
	                     target: 'rightMiddle' 
	                  }
	                }
	                ,
					hide: {
						fixed: true
					}
					,
			        style: {
			        	padding: '5px 10px' 
			        }
				});
        	</#list>
		});	
	</script>

</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield label="Nome" name="perfil.nome" id="nome" cssClass="inputNome" maxLength="100" required="true"/>
		<@ww.hidden name="perfil.id" />
		<div>Permissões</div>
		<div class="listaOpcoes">
		<ul>
			${exibirPerfil}
		</ul>
		</div>
	<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
		</button>
	</div>
</body>
</html>