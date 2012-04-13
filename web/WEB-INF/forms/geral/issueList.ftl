<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		.label { padding: 3px 4px 3px 12px; cursor: pointer; border-radius: 3px; }
		.label:hover, .marcado { 
			background-color: #DEDEDE;  
			background-image: url(<@ww.url value="/imgs/menu-bar-right-arrow.gif"/>); 
			background-position: 4px 8px;
			background-repeat: no-repeat; 
		}
		.lbl { padding: 2px 4px; font-size: 9px; font-weight: bold; color: #FFF; border-radius: 3px; }
	</style>
	<script type="text/javascript">
		$(function(){
			var labels = ${labels};
			$(labels).each(function() {
				$("#containerLabels").append('<span class="label"><span class="lbl" style="background-color: #'+this.color+';">' + this.name + '</span></span> ');
			});
			
			// ajusta os contrastes
			contraste('lbl');
			
			<#if (label?exists && label != '')>
				$(".label:contains('${label}')").addClass('marcado');
			<#elseif closed?exists && closed == true>
				$(".lblFechados").addClass('marcado');
			<#else>
				$(".lblAbertos").addClass('marcado');
			</#if>
			
			$(".label").click(function(){
				var buscaLabel = $(this).text().trim();
				
				if(buscaLabel == "todos os fechados")
				{
					window.location="list.action?closed=true";
					return false;			
				}				
				
				if(buscaLabel == "todos os abertos")
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
	<span class="label lblAbertos"><span class="lbl" style="background-color: #6CC644;">todos os abertos</span></span>  
	<span class="label lblFechados"><span class="lbl" style="background-color: #666666;">todos os fechados</span></span>  
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
