<meta http-equiv="Cache-Control" content="no-cache, no-store" />
<meta http-equiv="Pragma" content="no-cache, no-store" />
<meta http-equiv="Expires" content="0" />

<script type='text/javascript'>
    function removeIdioma(id)
    {
		$("#idioma").load('<@ww.url includeParams="none" value="/captacao/idioma/delete.action?candidatoIdioma.id="/>' + id, function (){
			target: "#idioma" 
    	});
    }
    
    function prepareUpdateIdioma(id)
    {
		$("#idioma").load('<@ww.url includeParams="none" value="/captacao/idioma/prepareUpdate.action?candidatoIdioma.id="/>' + id, function (){
		    $("#frmIdioma").ajaxForm({ 
    	   	    target: "#idioma" 
		    }); 
    	});
    }
    
    function prepareInsertIdioma()
    {
    	$("#idioma").load('<@ww.url includeParams="none" value="/captacao/idioma/prepareInsert.action"/>', function (){
		    $("#frmIdioma").ajaxForm({ 
    	   	    target: "#idioma" 
		    }); 
    	});
    }
</script>

<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
	<h3>Idiomas</h3>
	<@display.table name="idiomasCandidato" id="idiomaTable" class="dados" defaultsort=2 >
		<@display.column title="Ações" media="html" class="acao">
			<a href="javascript:prepareUpdateIdioma(${idiomaTable.id})"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<a href="javascript:newConfirm('Confirma exclusão?', function(){removeIdioma(${idiomaTable.id})});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="idioma.nome" title="Nome"/>
		<@display.column title="Nível">
			<#if idiomaTable.nivel == 'B'>
				Básico
			<#elseif idiomaTable.nivel == 'I'>
				Intermediário
			<#elseif idiomaTable.nivel == 'A'>
				Avançado
			</#if>
		</@display.column>
	</@display.table>
	
	<div class="buttonGroup">
		<#if (idiomasCandidato?size >= idiomas?size)> 
			<button class="btnInserirDesabilitado grayBG" id="inserirIdiomaDesabilitado" disabled></button>
		<#else>
			<button class="btnInserir grayBG" id="inserirIdioma" onclick="prepareInsertIdioma()" accesskey="I"></button>
		</#if>
	</div>

<br>