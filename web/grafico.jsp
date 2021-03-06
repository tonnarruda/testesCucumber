<%@page import="com.fortes.rh.util.DateUtil"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix='ww' uri='webwork' %>
<%@taglib prefix='authz' uri='http://acegisecurity.org/authz' %>
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
		.legendColorBox {width: 20px;}
		* { font-family: Arial, Helvetica, sans-serif; }
		h3 { font-size: 16px; }
		table { width: 100%; }
		.legend table{ width: auto !important;}
		table tr td { vertical-align: top; }
		table tr td.dadosSistema { width: 180px; text-align: right; font-size: 12px; }
		#popupGrafico { width: 800px; height: 300px; }
		#popupGraficoLegenda { width: 800px; max-height: 300px; text-align: left}
		#popupObs { width: 600px; float:left; text-align: left; font-size: 13px;}
		.tabelaResultadoImpressao {	background-color: #FFF ;border: 1px solid #666;	width: 60%;	margin: 100px 0px 0px 80px;	border:1px solid #BEBEBE;}
		.tabelaResultadoImpressao td{padding: 5px 5px;}
		.tabelaResultadoImpressao th{padding: 5px 0;font-weight: normal;}
		.tabelaResultadoImpressao td{color: #454C54;font-size: 12px;}
		.tabelaResultadoImpressao thead tr {background-color: #999999;font-family: Arial, Helvetica, sans-serif;font-size: 12px;color: #FFF;}
		.tabelaResultadoImpressao tr.even {	background-color: #EFEFEF;}
		.tabelaResultadoImpressao tr.odd {	background-color: #FFF;}
	
	</style>
</head>
<body>
	<table>
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

	<div id="info"></div>
	<div id="popupGrafico"></div>
	<div id="popupGraficoLegenda"></div>
	<div id="popupObs"></div>
</body>
</html>