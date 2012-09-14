<div class="portlet">
	<input type="hidden" name="caixa" value="${tipo}"/>
	<div class="portlet-header portlet-header-${tipo}">${action.getDescricaoTipo(tipo)} (${action.getTotalNaoLidas(tipo)})</div>
	<div class="portlet-content">
		<table width="100%" class="dados" style="border:none;">
			<tbody>
				<#assign j=0/>
				<#list action.getMensagens(tipo) as msg>
					<#if j%2==0>
						<#assign class="odd"/>
					<#else>
						<#assign class="even"/>
					</#if>

					<#if !msg.lida>
						<#assign style=""/>
						<#assign status><img border="0" title="Não lida" src="/fortesrh/imgs/mail.png"/></#assign>
					<#else>
						<#assign style="color: #949494;"/>
						<#assign status><img border="0" title="Lida" src="/fortesrh/imgs/mail-open.png"/></#assign>
					</#if>
				
					<tr class="${class}">
						<#if msg.usuarioMensagemId?exists>
							<td width="40" align="center">
								<a href="javascript: popup('geral/usuarioMensagem/leituraUsuarioMensagemPopup.action?usuarioMensagem.empresa.id=${empresaId}&amp;usuarioMensagem.id=${msg.usuarioMensagemId}', 400, 500)"><img border="0" title="Visualizar mensagem"  src="/fortesrh/imgs/olho.jpg"></a>
								<a href="javascript: newConfirm('Confirma exclusão?', function(){window.location='geral/usuarioMensagem/delete.action?usuarioMensagem.id=${msg.usuarioMensagemId}'});"><img border="0" title="Excluir" src="/fortesrh/imgs/delete.gif"/></a>
							</td>
						</#if>
						<td>
							<#if msg.remetente?exists && msg.data?exists>
								${status}&nbsp;&nbsp;<span class="remetenteHora">${msg.remetente} - ${msg.data?string("dd/MM/yyyy HH:mm")}</span><br />
							</#if>
							
							<div class="tituloMensagem">
								<#if msg.link?exists && msg.link != "">
									<a href="${msg.link}" title="${msg.textoAbreviado}" <#if msg.usuarioMensagemId?exists> onclick="marcarMensagemLida(${msg.usuarioMensagemId});" </#if> style="text-decoration:underline; ${style}">
										${msg.textoAbreviado}
									</a>
								<#else>
									<a style="${style}">${msg.textoAbreviado}</a>
								</#if>
							</div>
						</td>
					</tr>
					<#assign j=j+1/>
				</#list>
				
				<#if (action.getMensagens(tipo)?size < 1)>
					<tr>
						<td>Não há mensagens disponíveis</td>
					</tr>
				</#if>
			</tbody>
		</table>
	</div>
	<div class="verTodas">
		<#if (j > 0)>
			<img border="0" title="Ver todas" src="/fortesrh/imgs/add.png"/>
			<a href="mensagens.action?tipo=${tipo}">Ver todas</a>
		<#else>
			&nbsp;
		</#if>
	</div>
</div>