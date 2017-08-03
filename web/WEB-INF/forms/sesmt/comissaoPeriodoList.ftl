<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">#menuComissao a.ativaComissao{border-bottom: 2px solid #5292C0;}</style>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	<title>CIPA</title>
</head>
<body>
	<#include "comissaoLinks.ftl" />

	<#list comissaoPeriodos as comissaoPeriodo>
		<@ww.div cssClass="divInfo" cssStyle="width: 950px;border-width:2px;">
			<table>
				<tr>
				<td style="width:882px;font-weight:bold;">Comissão de ${comissaoPeriodo.periodoFormatado}</td>
				<td>
					<a href="prepareUpdate.action?comissaoPeriodo.id=${comissaoPeriodo.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif?comissao.id=51"/>"></a>
					
					<#if comissaoPeriodo.permitirExcluir>
						<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?comissaoPeriodo.id=${comissaoPeriodo.id}&comissao.id=${comissao.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
					<#else>
						<img border="0" title="Não é possível excluir essa comissão, pois a mesma já possui membros que já participaram de uma reunião." src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
					</#if>
					
					<a href="clonar.action?comissaoPeriodo.id=${comissaoPeriodo.id}&comissao.id=${comissao.id}"><img border="0" title="Clonar" src="<@ww.url includeParams="none" value="/imgs/clonar.gif"/>"></a>
				 </td>
				</tr>
			</table>

			<#if comissaoPeriodo.comissaoMembros?exists >

				<#assign comissaoMembros = comissaoPeriodo.comissaoMembros />

				<table class="dados" id="comissaoPeriodo">
					<thead>
						<tr><th>Nome</th>
						<th>Função</th>
						<th>Tipo</th>
					</thead>
					<tbody>
						<#assign i=0/>
						<#list comissaoMembros as comissaoMembro>
							<#if i%2 == 0>
								<#assign clazz="class=odd">
							<#else>
								<#assign clazz="class=even">
							</#if>
							<tr ${clazz}>
								<td>${comissaoMembro.colaborador.nome}</td>
								<td>${comissaoMembro.funcaoDic}</td>
								<td>${comissaoMembro.tipoDic}</td>
							</tr>
						<#assign i = i+1/>
						</#list>
					</tbody>
				</table>
			</#if>
		</@ww.div>
		<br/>
	</#list>
</body>
</html>