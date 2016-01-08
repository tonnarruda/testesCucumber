<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<#if conhecimento.id?exists>
	<title>Editar Conhecimento</title>
	<#assign formAction="update.action"/>
<#else>
	<title>Inserir Conhecimento</title>
	<#assign formAction="insert.action"/>
</#if>

<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/NivelCompetenciaDWR.js"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/CHA.js?version=${versao}"/>"></script>
	
<#assign validarCampos="return validaFormulario('form', new Array('nome','@areasCheck'), null)"/>

	<script type='text/javascript'>
		$(function(){
			<#if conhecimento.id?exists && conhecimento.criteriosAvaliacaoCompetencia?exists && (conhecimento.criteriosAvaliacaoCompetencia?size > 0) >
				<#list 0..(conhecimento.criteriosAvaliacaoCompetencia?size-1) as i>
					addCriterio("conhecimento", "${conhecimento.criteriosAvaliacaoCompetencia[i].descricao}", ${conhecimento.criteriosAvaliacaoCompetencia[i].id});
				</#list>
			</#if>
			createEventClickAddCriterio('conhecimento');
		});
	</script>
</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />
<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">

	<@ww.textfield label="Nome" name="conhecimento.nome" id="nome" required="true" cssClass="inputNome" maxLength="100" cssStyle="width:500px;"/>

	<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais *" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
	<@frt.checkListBox name="cursosCheck" id="cursosCheck" label="Cursos/Treinamentos Sugeridos" list="cursosCheckList" filtro="true"/>
	
	<li id="wwgrp_criterios" class="wwgrp" style="margin: 10px 0;">
		<div id="wwlbl_criterios" class="wwlbl">
			<label for="criterios" class="desc"> Comportamentos observados para a competência:</label>
		</div> 
		<div id="wwctrl_criterios" class="wwctrl" style="border: 1px solid #BEBEBE; padding: 10px 5px; width: 490px;">
			<ul id="criterios"></ul>
			<a title="Adicionar comportamento" href="javascript:;" onclick="validaAddCriterio(${empresaSistema.id}, 'conhecimento');">
				<img src="<@ww.url includeParams="none" value="/imgs/add.png"/>" border="0" align="absMiddle" /> 
				Adicionar comportamento 
			</a>
		</div>
	</li>	
	
	<@ww.textarea label="Observação" name="conhecimento.observacao" id="observacao" cssStyle="width:500px;"/>

	<@ww.hidden label="Id" name="conhecimento.id" />

<@ww.token/>
</@ww.form>


<div class="buttonGroup">
	<button onclick="${validarCampos};" class="btnGravar" ></button>
	<button onclick="window.location='list.action'" class="btnCancelar" ></button>
</div>
</body>
</html>