	<#if empresaId?exists>
		<#assign empresaIdTmp = empresaId />
	<#else>
		<#assign empresaIdTmp = ""/>
	</#if>
	
	<script type='text/javascript'>
	    function removeExperiencia(id)
	    {
			$("#expProfissional").load('<@ww.url includeParams="none" value="/captacao/experiencia/delete.action?experiencia.id="/>' + id + '&empresaId=${empresaIdTmp}' + '&internalToken=${internalToken}', function (){
				target: "#expProfissional" 
	    	});
	    }

	    function prepareUpdateExperiencia(id)
	    {
			$("#expProfissional").load('<@ww.url includeParams="none" value="/captacao/experiencia/prepareUpdate.action?experiencia.id="/>' + id + '&empresaId=${empresaIdTmp}' + '&internalToken=${internalToken}', function (){
			    $("#frmExperiencia").ajaxForm({ 
	    	   	    beforeSubmit: function() { 
	    	   	    	return validarFormExperiencia(); 
			    	},
			    	target: "#expProfissional"
			     }); 
	    	});
	    }
	    
	    function prepareInsertExperiencia()
	    {
	    	$("#expProfissional").load('<@ww.url includeParams="none" value="/captacao/experiencia/prepareInsert.action?empresaId=${empresaIdTmp}"/>' + '&internalToken=${internalToken}', function (){
			    $("#frmExperiencia").ajaxForm({ 
	    	   	   	beforeSubmit: function() { 
	    	   	    	return validarFormExperiencia(); 
			    	},
			    	target: "#expProfissional"
			     }); 
	    	});
	    } 
	</script>
	
	<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
	<h3>Experiência Profissional</h3>
	<@display.table name="experiencias" id="exp" class="dados campo" defaultsort=4 defaultorder="descending">
		<@display.column title="Ações" media="html" class="acao">
			<a href="javascript:prepareUpdateExperiencia(${exp.id?string?replace(".", "")?replace(",","")})"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<a href="javascript:newConfirm('Confirma exclusão?', function(){removeExperiencia(${exp.id?string?replace(".", "")?replace(",","")})});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="empresa" title="Empresa"/>
		<@display.column property="nomeFuncao" title="Cargo/Função"/>
		<@display.column property="dataAdmissao" title="Admissão" format="{0,date,dd/MM/yyyy}" style="text-align: center"/>
		<@display.column property="dataDesligamento" title="Desligamento" format="{0,date,dd/MM/yyyy}" style="text-align: center"/>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir grayBG" onclick="prepareInsertExperiencia();"></button>
	</div>
<br>