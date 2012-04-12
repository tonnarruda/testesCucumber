<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		.label{
			 padding: 4px;
			 margin-right: 4px; 
			 cursor: pointer; 
		}
		
	</style>
	<script type="text/javascript">
		$(function(){
			var labels = ${labels};
			$(labels).each(function() {
				$("#containerLabels").append('<span class="label"><span style="background-color: #'+this.color+';">&nbsp&nbsp</span> ' + this.name + '</span> ');
			});
		}); 
	</script>
	
	<title>Cartões</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	<div id="containerLabels"></div>
	<br>
	<@display.table name="issues" id="issue" class="dados">
		<@display.column title="Ações" class="acao" style="width:30px;text-align:center;vertical-align:top;">
			<a href="prepareUpdate.action?issue.number=${issue.number}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
		</@display.column>
		<@display.column property="number" title="Número" style="width:30px;text-align:center;vertical-align:top;"/>
		<@display.column property="created_at_date" title="Data" format="{0,date,dd/MM/yyyy}" style="width:60px;text-align:center;vertical-align:top;"/>
		<@display.column property="title" title="Titulo" style="width:200px;vertical-align:top;"/>
		<@display.column property="body" title="Descrição" style="width:500px;"/>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'"></button>
	</div>
</body>
</html>
