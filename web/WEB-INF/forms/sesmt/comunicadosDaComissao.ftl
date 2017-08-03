<html>
<head>
	<@ww.head/>
  <style type="text/css">
    #menuComissao a.ativaDocumentos{border-bottom: 2px solid #5292C0;}
  </style>
  <script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>

  <title>CIPA</title>
  
  <style type="text/css">
		#tt {position:absolute; display:block;}
		#tttop {display:block; height:5px; margin-left:5px; overflow:hidden}
		#ttcont {display:block; padding:2px 12px 3px 7px; margin-left:5px; background:#666; color:#FFF}
		#ttbot {display:block; height:5px; margin-left:5px; overflow:hidden}
	</style>
  
  <script type="text/javascript">

	$(document).ready(function() {
		$('#help_ata').qtip({
			content: 'Utilize a expressão #NOMEPRESIDENTE# <br/>onde deve aparecer o nome do Presidente da Comissão;<br/> Utilize a expressão #NOMEVICEPRESIDENTE#<br/> onde deve aparecer o nome do Vice-Presidente.'
			, style: { width: '100px' }
		});
	});

  </script>
	
</head>
<body>
	<#include "../ftl/mascarasImports.ftl" />
  	<#include "comissaoLinks.ftl" />
  	

	<@ww.form name="form" action="imprimirAtaPosse.action" method="POST">

	<h3>Texto para Ata de Instalação e Posse:</h3>
	<@ww.textarea label="Parte 1" name="comissao.ataPosseTexto1" cssStyle="width:600px;"/>
	
	<span style="position: relative">
		<img style="position: absolute; top: 12px; _top: 6px; left: 70px;" id="help_ata" src='<@ww.url value='/imgs/help.gif'/>'/>
		<@ww.textarea label="Parte 2" name="comissao.ataPosseTexto2" cssStyle="width:600px;"/>
	</span>
	
	<div class="buttonGroup">
		<button onclick="document.form.submit();" class="btnImprimirAtaInstalacao"></button>
	</div>

	<@ww.hidden name="comissao.id"/>
  	
	</@ww.form>

</body>
</html>