<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>

	<script type="text/javascript">
		$(function(){
			mostraEstabelecimentos();
			
			$('#estabelecimentoResponsavel').change(function() {
				mostraEstabelecimentos();
			});
		})
	
		function mostraEstabelecimentos() 
		{
			var exibeEstabelecimentos = $('#estabelecimentoResponsavel').val();
			$('#divEstabelecimento').toggle(exibeEstabelecimentos == '${tipoEstabelecimentoResponsavelAlguns}') ;
		}
		
		function submeter() 
		{
			if($('#estabelecimentoResponsavel').val() == '${tipoEstabelecimentoResponsavelAlguns}') {
				return validaFormularioEPeriodo('form', new Array('nome','inicio','crm','@estabelecimentosCheck'), new Array('inicio','fim'));
			} else {
				return validaFormularioEPeriodo('form', new Array('nome','inicio','crm'), new Array('inicio','fim'));
			}
		}
	</script>
	
	<#if engenheiroResponsavel.id?exists>
		<title>Editar Engenheiro do Trabalho</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
	<#else>
		<title>Inserir Engenheiro do Trabalho</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
	</#if>

	<#assign inicio = ""/>
	<#if engenheiroResponsavel?exists && engenheiroResponsavel.inicio?exists>
		<#assign inicio = engenheiroResponsavel.inicio/>
	</#if>
	
	<#assign fim = ""/>
	<#if engenheiroResponsavel?exists && engenheiroResponsavel.fim?exists>
		<#assign fim = engenheiroResponsavel.fim/>
	</#if>
		
	<#include "../ftl/mascarasImports.ftl" />
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" validate="true" method="POST">
		<@ww.textfield id="nome" label="Nome" name="engenheiroResponsavel.nome" cssClass="inputNome" maxLength="100" required="true"/>
		
		Período:<br>
		<@ww.datepicker id="inicio" theme="simple" label="" name="engenheiroResponsavel.inicio" required="true" value="${inicio}" cssClass="mascaraData validaDataIni"/>
		a
		<@ww.datepicker id="fim" theme="simple" label="" name="engenheiroResponsavel.fim" value="${fim}" cssClass="mascaraData validaDataFim"/>
		<br>
		<@ww.textfield id="crea" label="CREA" name="engenheiroResponsavel.crea" maxLength="20" required="true" />
		<@ww.textfield id="nit" label="NIT" name="engenheiroResponsavel.nit" maxLength="15" />

		<fieldset style="padding: 5px 0px 5px 5px; width: 593px;">
			<legend>Estabelecimentos pelo qual é responsável</legend>
			<@ww.select label="Responsável por" name="engenheiroResponsavel.estabelecimentoResponsavel" id="estabelecimentoResponsavel" list="tipoEstabelecimentoResponsavel" />
			<div id="divEstabelecimento">
				<@frt.checkListBox label="Estabelecimentos" id="estabelecimentosCheck" name="estabelecimentosCheck" list="estabelecimentosCheckList" width="586" height="180" filtro="true"/>
			</div>
		</fieldset>
		
		<@ww.hidden name="engenheiroResponsavel.id"/>
		<@ww.token/>
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="submeter();" class="btnGravar" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
		</button>
	</div>
</body>
</html>