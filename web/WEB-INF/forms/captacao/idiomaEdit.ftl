<meta http-equiv="Cache-Control" content="no-cache, no-store" />
<meta http-equiv="Pragma" content="no-cache, no-store" />
<meta http-equiv="Expires" content="0" />

<script type='text/javascript'>
    function voltarIdioma()
    {
    	$("#idioma").load('<@ww.url includeParams="none" value="/captacao/idioma/list.action"/>');
    }
</script>

<#if candidatoIdioma.id?exists>
	<#assign formActionIdioma="update.action"/>
	<#assign labelIdioma="Atualizar Idioma"/>
<#else>
	<#assign formActionIdioma="insert.action"/>
	<#assign labelIdioma="Inserir Idioma"/>
</#if>
<@ww.actionerror />


<div style="width:99%; display:block; border:1px solid #CCCCCC;">
	<h3 style="padding:10px;">${labelIdioma}</h3>
	<@ww.form id="frmIdioma" name="frmIdioma" action="/captacao/idioma/${formActionIdioma}" method="POST" cssStyle="width:99%; padding:5px;">

		<@ww.select label="Idioma" name="candidatoIdioma.idioma.id" id="idiomaSelec" list="idiomas" listKey="id" listValue="nome" required="true" theme="css_xhtml" liClass="liLeft" />

		<@ww.select label="Nível" name="candidatoIdioma.nivel" id="nivelSelec" list="nivelIdioma" theme="css_xhtml" required="true" />

		<@ww.hidden name="candidatoIdioma.id" />

		<@ww.submit value=" " cssClass="btnGravar grayBG" liClass="liLeft" cssStyle="float:left; display:block;"/>
		<input type="button" class="btnCancelar grayBG" onclick="voltarIdioma();" />
	</@ww.form>
</div>
<br>