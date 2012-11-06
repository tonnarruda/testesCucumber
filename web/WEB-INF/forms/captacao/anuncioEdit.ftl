<html>
<head>
<@ww.head/>
<#if anuncio.id?exists>
	<title>Editar Anúncio de Solicitação de Pessoal</title>
	<#assign formAction="update.action"/>
<#else>
	<title>Inserir Anúncio de Solicitação de Pessoal</title>
	<#assign formAction="insert.action"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('titulo','cabecalho'), null)"/>

<script language='javascript'>
	$(function(){
		$('#exibirModuloExterno').change(function(){
				$('#responderAvaliacaoModuloExterno').attr('disabled', $(this).val()=='false');
		});
		
		$('#exibirModuloExterno').change();
		
	});
	
	function anunciar(param)
	{
		document.getElementById('acao').value = param;
		${validarCampos};
	}
</script>

</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield label="Título" required="true" name="anuncio.titulo" id="titulo" cssStyle="width: 600px;" maxLength="100"/>
		<@ww.textarea label="Descrição" required="true" name="anuncio.cabecalho" id="cabecalho" cssStyle="width: 600px;"/>
		<@ww.select label="Exibir no módulo externo" id="exibirModuloExterno" name="anuncio.exibirModuloExterno" list=r"#{true:'Sim',false:'Não'}" required="true"/>

<#-- 
		<#if anuncio.solicitacao.avaliacao?exists && anuncio.solicitacao.avaliacao.id?exists>
			<@ww.checkbox label="Permitir que o candidato responda o modelo de avaliação no modulo externo ao se candidatar." id="responderAvaliacaoModuloExterno" name="anuncio.responderAvaliacaoModuloExterno" labelPosition="left"/>
		</#if>
-->
		<@ww.hidden name="anuncio.id" />
		<@ww.hidden name="anuncio.solicitacao.id" />
		<@ww.hidden name="solicitacao.id" />
		<@ww.hidden name="acao" id="acao"/>

	</@ww.form>



	<div class="buttonGroup">
		<button onclick="anunciar('I')" class="btnGravar" accesskey="g">
		</button>
		<button onclick="anunciar('E')" class="btnEnviarPorEmail" accesskey="e">
		</button>
		<button onclick="window.location='../solicitacao/list.action'" class="btnVoltar" accesskey="V">
		</button>
	</div>
</body>
</html>