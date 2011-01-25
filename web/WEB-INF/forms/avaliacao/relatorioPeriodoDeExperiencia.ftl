<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<title>Acompanhamento do Período de Experiência</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<#assign DataAtual = "${dataDoDia}"/>
	<script type='text/javascript'>
		function compl()
		{
			var compl = document.getElementById("complementares");
			var img = document.getElementById("imgCompl");
			if(compl.style.display == "none")
			{
				compl.style.display = "";
				img.src = "<@ww.url includeParams="none" value="/imgs/arrow_up.gif"/>"
			}
			else
			{
				compl.style.display = "none";
				img.src = "<@ww.url includeParams="none" value="/imgs/arrow_down.gif"/>"
			}
		}
	</script>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<#include "../ftl/mascarasImports.ftl" />

		<@ww.form name="form" action="imprimeRelatorioPeriodoDeAcompanhamentoDeExperiencia.action" method="POST">
		
			<@ww.datepicker label="Data" name="dataReferencia" required="true" id="dataReferencia" value="${DataAtual}" cssClass="mascaraData"/>
			<br>
			Desconsiderar colaboradores com menos de  
			<@ww.textfield theme="simple" name="diasDeAcompanhamento" id="diasDeAcompanhamento" cssStyle="width:60px; text-align:right;" maxLength="8" onkeypress = "return(somenteNumeros(event,''));"/> 
			dias de empresa.
			<br><br>  
			<!-- <@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas"/> -->
			<@frt.checkListBox label="Estabelecimento" name="estabelecimentoCheck" id="estabelecimentoCheck" list="estabelecimentoCheckList"/>						
			<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areasCheck" list="areasCheckList"/>
			
		</@ww.form>

		<div class="buttonGroup">
			<button class="btnRelatorio" onclick='jQuery("#imprimeRelatorioPeriodoDeAcompanhamentoDeExperiencia").submit();'></button>
		</div>
</body>
</html>