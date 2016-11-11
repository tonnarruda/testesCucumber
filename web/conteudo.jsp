<%@page import="com.fortes.rh.util.DateUtil"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix='ww' uri='webwork' %>
<%@taglib prefix='authz' uri='http://www.springframework.org/security/tags' %>
<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    
    String agora = DateUtil.formataDate(new java.util.Date(), "dd/MM/yyyy H:m");
%>
<html>
<head>
	<!--[if lte IE 8]><script type='text/javascript' src='<ww:url includeParams="none" value="/js/jQuery/excanvas.min.js"/>'></script><![endif]-->
	<style type="text/css">
		* { font-family: Arial, Helvetica, sans-serif; font-size: 11px; }
		h3 { font-size: 16px; }
		table.cabecalho { width: 100%; margin-bottom: 10px; }
		table.cabecalho tr td { vertical-align: top; }
		table.cabecalho tr td.dadosSistema { width: 180px; text-align: right; font-size: 12px; }

		table.dados { width: 100%; border-collapse: collapse; }
		table.dados tr td, table.dados tr th { border: 1px solid #000; padding: 3px; }
	</style>
</head>
<body>
	<table class="cabecalho">
		<tr>
			<td>
				<h3 id="popupTitulo"></h3>
			</td>
			<td class="dadosSistema">
				RH - <%= agora %><br />
				Impresso por <authz:authentication operation="nome"/><br />
				Licenciado para: <authz:authentication operation="empresaNome"/>
			</td>
		</tr>
	</table>

	<div id="popupConteudo"></div>	
</body>
</html>