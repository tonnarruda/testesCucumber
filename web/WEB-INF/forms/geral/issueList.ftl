<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		.label { padding: 4px; margin-right: 4px; cursor: pointer; border-radius: 3px; }
		.label:hover { background-color: #DEDEDE; }
		.lbl { padding: 2px 4px; margin: 2px; font-size: 9px; font-weight: bold; color: #FFF; border-radius: 3px; }
	</style>
	<script type="text/javascript">
		$(function(){
			var labels = ${labels};
			$(labels).each(function() {
				$("#containerLabels").append('<span class="label"><span class="lbl" style="background-color: #'+this.color+';">&nbsp&nbsp</span> ' + this.name + '</span> ');
			});
			
			<#if (label?exists && label != '')>
				$(".label:contains('${label}')").css('background-color', '#DEDEDE');
			<#elseif closed?exists && closed == true>
				$(".lblFechados").css('background-color', '#DEDEDE');
			<#else>
				$(".lblAbertos").css('background-color', '#DEDEDE');
			</#if>
			
			$(".label").click(function(){
				var buscaLabel = $(this).text().trim();
				
				if(buscaLabel == "Todos os Fechados")
				{
					window.location="list.action?closed=true";
					return false;			
				}				
				
				if(buscaLabel == "Todos os Abertos")
					buscaLabel = "";
				
				window.location="list.action?label=" + buscaLabel;
			});
		}); 
	</script>
	
	<title>Cartões</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	<div id="containerLabels">
	Filtro:
	<span class="label lblAbertos"><span class="lbl" style="background-color: #00BFFF;">&nbsp&nbsp</span> Todos os Abertos</span>  
	<span class="label lblFechados"><span class="lbl" style="background-color: #8B8682;">&nbsp&nbsp</span> Todos os Fechados</span>  
	</div>
	<br>
	<@display.table name="issues" id="issue" class="dados">
		<@display.column title="Ações" class="acao" style="width:30px;text-align:center;vertical-align:top;">
			<#if issue.user.id == '1630543'>
				<a href="prepareUpdate.action?issue.number=${issue.number}"><img border="0" align='absMiddle' title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			</#if>
		</@display.column>
		<@display.column property="number" title="Número" style="width:30px;text-align:center;vertical-align:top;"/>
		<@display.column property="created_at_date" title="Data" format="{0,date,dd/MM/yyyy}" style="width:60px;text-align:center;vertical-align:top;"/>
		<@display.column title="Titulo" style="width:200px;vertical-align:top;">
			${issue.title}
			<#list issue.labels as label>
				<span class="lbl" style="background-color:#${label.color};">${label.name}</span>
			</#list>
		</@display.column>
		<@display.column property="body" title="Descrição" style="width:500px;"/>
		<@display.column property="user.login" title="Criado por" style="width:100px;"/>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'"></button>
	</div>
</body>
</html>
