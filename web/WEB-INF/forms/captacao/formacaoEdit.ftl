<meta http-equiv="Cache-Control" content="no-cache, no-store" />
<meta http-equiv="Pragma" content="no-cache, no-store" />
<meta http-equiv="Expires" content="0" />
	<#if formacao.id?exists>
		<#assign formActionFormacao="update.action"/>
		<#assign labelFormacao="Atualizar Formação"/>
	<#else>
		<#assign formActionFormacao="insert.action"/>
		<#assign labelFormacao="Inserir Formação"/>
	</#if>
	
	<script type='text/javascript'>
		function voltarFormacao()
	    {
	    	$("#formacao").load('<@ww.url includeParams="none" value="/captacao/formacao/list.action"/>');
	    }
	</script>
	
	<@ww.actionerror />

	<div style="width:99%; display:block; border:1px solid #CCCCCC;">
		<h3 style="padding:10px;">${labelFormacao}</h3>
		<@ww.form id="frmFormacao" name="frmFormacao" action="/captacao/formacao/${formActionFormacao}" method="POST" cssStyle="width:99%; padding:5px;">
	
			<@ww.select label="Área de Formação" name="formacao.areaFormacao.id" id="formacaoArea" list="areaFormacaos" listKey="id" listValue="nome" theme="css_xhtml" cssStyle="width: 380px"/>
			<@ww.textfield label="Curso" name="formacao.curso" theme="css_xhtml" id="formacaoCurso" cssStyle="width: 300px" maxLength="100" required="true" />
			<@ww.textfield label="Instituição de ensino" name="formacao.local" theme="css_xhtml" id="formacaoLocal" cssStyle="width: 282px" maxLength="100" liClass="liLeft" required="true" />
			<@ww.select label="Tipo" name="formacao.tipo" id="formacaoTipo" list="tiposFormacao" required="true"  theme="css_xhtml" liClass="liLeft" />
			<@ww.select label="Situação" name="formacao.situacao" id="formacaoSituacao" list="situacoesFormacao" required="true" theme="css_xhtml" liClass="liLeft" />
			<@ww.textfield label="Conclusão" name="formacao.conclusao" id="formacaoConclusao" maxLength="20" theme="css_xhtml"/>
			<@ww.hidden label="Id" name="formacao.id"  theme="css_xhtml"/>
			<@ww.hidden name="internalToken"/>
	
			<@ww.submit value=" "  cssClass="btnGravar grayBG" cssStyle="float:left; display:block;" />
			<input type="button" class="btnCancelar grayBG" onclick="voltarFormacao();" />
		</@ww.form>
	</div>
	<br>