<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
<@ww.head/>
<#if anexo.id?exists>
	<title>Editar Anexo ${anexoDestino}</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Anexo ${anexoDestino}</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<#if anexo.id?exists>
	<#assign validarCampos="return validaFormulario('form', new Array('nome','observacao'), null)"/>
<#else>
	<#assign validarCampos="return validaFormulario('form', new Array('nome','observacao','anexo'), null)"/>
</#if>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" validate="true" method="POST" onsubmit="${validarCampos}" enctype="multipart/form-data">
		<@ww.textfield label="Nome" name="anexo.nome" id="nome"  cssClass="inputNome" maxLength="100" required="true"/>
		<@ww.textarea label="Observação" name="anexo.observacao" id="observacao"  cssClass="inputNome" required="true"/>
		<#if !anexo.id?exists>
			<@ww.file label="Anexo" name="arquivo" id="anexo"  required="true"/>
		</#if>

		<@ww.hidden name="anexo.origemId" />
		<@ww.hidden name="anexo.origem" />
		<@ww.hidden name="anexo.id" />
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}">
		</button>
		<#if anexo.id?exists>
			<button onclick="window.location='../../sesmt/anexo/prepareInsert.action?anexo.origemId=${anexo.origemId}&anexo.origem=${anexo.origem}'" class="btnVoltar" accesskey="V">
			</button>
		<#else>
			<button onclick="window.location='voltarList.action?anexo.origem=${anexo.origem}'" class="btnVoltar" accesskey="V">
			</button>
		</#if>
	</div>

	<p></p>
	<p></p>
	<p></p>

	<#if anexo?exists && anexos?size != 0 >
		<p><b>Anexos ${anexoDestino}</b></p>
		<@display.table name="anexos" id="anexo" pagesize=10 class="dados" defaultsort=2 sort="list">
			<@display.column title="Ações" class="acao">
				<a href="prepareUpdate.action?anexo.id=${anexo.id}&anexo.origemId=${anexo.origemId}">
					<img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>">
				</a>
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?anexo.id=${anexo.id}&anexo.origemId=${anexo.origemId}&anexo.origem=${anexo.origem}'});">
					<img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>">
				</a>
				<#if anexo.url?exists>
					<a  href="#"  onclick = "window.location='showArquivo.action?anexo.id=${anexo.id}'" >
						<img border="0" title="Baixar arquivo (${anexo.url})" src="<@ww.url value="/imgs/arrow_down2.gif"/>">
					</a>
				<#else>
					<img border="0" title="Não é possível baixar o arquivo." src="<@ww.url includeParams="none" value="/imgs/arrow_down2.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				</#if>
			</@display.column>
			<@display.column property="nome" title="Nome" style="width:150px"/>
			<@display.column property="observacao" title="Observação" />
		</@display.table>
	</#if>
</body>
</html>