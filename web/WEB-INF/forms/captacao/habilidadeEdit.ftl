<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<#if habilidade.id?exists>
	<title>Editar Habilidade</title>
	<#assign formAction="update.action"/>
<#else>
	<title>Inserir Habilidade</title>
	<#assign formAction="insert.action"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('nome','@areasCheck'), null)"/>

	<script>
		$(function(){
			<#if habilidade.id?exists && habilidade.criteriosAvaliacaoCompetencia?exists && (habilidade.criteriosAvaliacaoCompetencia?size > 0) >
				<#list 0..(habilidade.criteriosAvaliacaoCompetencia?size-1) as i>
					addCriterio("${habilidade.criteriosAvaliacaoCompetencia[i].descricao}", ${habilidade.criteriosAvaliacaoCompetencia[i].id});
				</#list>
			</#if>
			createEventClickAddCriterio();
		});
	
		function createEventClickAddCriterio() {
			$(".inputCriterioDescricao").unbind();
			$(".inputCriterioDescricao").keypress(function(event){
				if ( event.which == 13 ) {
					addCriterio();
				}
			});
		}
		
		function reorganizeListaDeCriterios(addNew) {
			$("#criterios li").each(function(i){
				if( addNew && $(this).find(".inputCriterioDescricao").attr("name") == "" )
					$(this).find(".inputCriterioDescricao").focus();
					
				$(this).find(".inputCriterioId").attr("name", "habilidade.criteriosAvaliacaoCompetencia["+i+"].id");
				$(this).find(".inputCriterioDescricao").attr("name", "habilidade.criteriosAvaliacaoCompetencia["+i+"].descricao");
			});
			
		}
	
		function delCriterio(item)
		{
			$(item).parent().parent().remove();
			reorganizeListaDeCriterios();
		}
		
		function addCriterio(criterioDescricao, criterioId)
		{
			criterioId = criterioId != undefined ? criterioId : "";
			criterioDescricao = criterioDescricao != undefined ? criterioDescricao : "";
			
			var criterio = '<li style="margin: 2px 0;"><span>';
			criterio += '<img title="Remover critério" onclick="delCriterio(this)" src="<@ww.url includeParams="none" value="/imgs/remove.png"/>" border="0" align="absMiddle" style="cursor:pointer;" />&nbsp;';
			criterio += '<input type="hidden" value="' + criterioId + '" class="inputCriterioId" >';
			criterio += '<input type="text" maxlength="100" value="' + criterioDescricao + '" class="inputCriterioDescricao" style="width:468px;">';
			criterio += '</span></li>';
		
			$('#criterios').append(criterio);
			
			reorganizeListaDeCriterios(!criterioId);
			createEventClickAddCriterio();
		}
	</script>
</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />
<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">

	<@ww.textfield label="Nome" name="habilidade.nome" id="nome" required="true" cssClass="inputNome" maxLength="100" cssStyle="width:500px;"/>
	<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais *" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
	<@frt.checkListBox name="cursosCheck" id="cursosCheck" label="Cursos/Treinamentos Sugeridos" list="cursosCheckList" filtro="true"/>
	
	<li id="wwgrp_criterios" class="wwgrp" style="margin: 10px 0;">
		<div id="wwlbl_criterios" class="wwlbl">
			<label for="criterios" class="desc"> Critérios para avaliação da competência:</label>
		</div> 
		<div id="wwctrl_criterios" class="wwctrl" style="border: 1px solid #BEBEBE; padding: 10px 5px; width: 490px;">
			<ul id="criterios"></ul>
			<a title="Adicionar critério" href="javascript:;" onclick="addCriterio();">
				<img src="<@ww.url includeParams="none" value="/imgs/add.png"/>" border="0" align="absMiddle" /> 
				Adicionar critério 
			</a>
		</div>
	</li>	
	
	<@ww.textarea label="Observação" name="habilidade.observacao" id="observacao" cssStyle="width:500px;"/>
	<@ww.hidden label="Id" name="habilidade.id" />

<@ww.token/>
</@ww.form>


<div class="buttonGroup">
	<button onclick="${validarCampos};" class="btnGravar" ></button>
	<button onclick="window.location='list.action'" class="btnCancelar" ></button>
</div>
</body>
</html>