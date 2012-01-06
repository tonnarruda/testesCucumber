<meta http-equiv="Cache-Control" content="no-cache, no-store" />
<meta http-equiv="Pragma" content="no-cache, no-store" />
<meta http-equiv="Expires" content="0" />
	
	<script type='text/javascript'>
	    function removeFormacao(id)
	    {
			$("#formacao").load('<@ww.url includeParams="none" value="/captacao/formacao/delete.action?formacao.id="/>' + id, function (){
				$("#formacao").load('<@ww.url includeParams="none" value="/captacao/formacao/list.action"/>'); 
	    	});
	    }
	    
	    function prepareUpdateFormacao(id)
	    {
			$("#formacao").load('<@ww.url includeParams="none" value="/captacao/formacao/prepareUpdate.action?formacao.id="/>' + id, function (){
			    $("#frmFormacao").ajaxForm({
			    	beforeSubmit: function() { 
	    	   	    	return validaFormulario('frmFormacao', new Array('formacaoArea','formacaoCurso','formacaoLocal','formacaoSituacao'), null, true); 
			    	},
			    	target: "#formacao"
			    }); 
	    	});
	    }
	    
	    function prepareInsertFormacao()
	    {
	    	$("#formacao").load('<@ww.url includeParams="none" value="/captacao/formacao/prepareInsert.action"/>', function (){
			    $("#frmFormacao").ajaxForm({
			    	beforeSubmit: function() { 
	    	   	    	return validaFormulario('frmFormacao', new Array('formacaoArea','formacaoCurso','formacaoLocal','formacaoSituacao'), null, true); 
			    	},
			    	target: "#formacao"
			    }); 
	    	});
	    }
	    
	</script>
	
	<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
	<h3>Formação Escolar</h3>
	
	<@display.table name="formacaos" id="formacao" class="dados" defaultsort=6 defaultorder="descending">
		<@display.column title="Ações" media="html" class="acao">
			<a href="javascript:prepareUpdateFormacao(${formacao.id})"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<a href="javascript:newConfirm('Confirma exclusão?', function(){removeFormacao(${formacao.id})});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="curso" title="Curso"/>
		<#switch formacao.tipo>
			<#case 'T'>
				<#assign tipoLabel="Técnico"/>
			<#break>
			<#case 'G'>
				<#assign tipoLabel="Graduação"/>
			<#break>
			<#case 'M'>
				<#assign tipoLabel="Mestrado"/>
			<#break>
			<#case 'E'>
				<#assign tipoLabel="Especialização"/>
			<#break>
			<#case 'D'>
				<#assign tipoLabel="Doutorado"/>
			<#break>
			<#case 'P'>
				<#assign tipoLabel="Pós-doutorado"/>
			<#break>
		</#switch>
		<@display.column title="Formação">
			${tipoLabel}
		</@display.column>
		<@display.column property="local" title="Instituição de ensino"/>
		<#switch formacao.situacao>
			<#case 'C'>
				<#assign situacaoLabel="Concluído"/>
			<#break>
			<#case 'A'>
				<#assign situacaoLabel="Em andamento"/>
			<#break>
			<#case 'I'>
				<#assign situacaoLabel="Incompleto"/>
			<#break>
		</#switch>
		<@display.column title="Situação">
			${situacaoLabel}
		</@display.column>
		<@display.column property="conclusao" title="Conclusão"/>
	</@display.table>

	<div class="buttonGroup">
		<button class="btnInserir grayBG" id="inserirFormacao" onclick="prepareInsertFormacao()">
		</button>
	</div>
<br>