<div class="portlet">
	<input type="hidden" name="caixa" value="${tipo}"/>
	<div class="portlet-header portlet-header-${tipo}">${action.getDescricaoTipo(tipo)}(${action.getTotalNaoLidas(tipo)})</div>
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
						<#assign style="font-weight: bold;"/>
						<#assign status="Não lida">
					<#else>
						<#assign style=""/>
						<#assign status="Lida">
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
								<span class="remetenteHora">${msg.remetente} - ${msg.data?string("dd/MM/yyyy HH:mm")} - ${status}</span><br />
							</#if>
							
							<#if msg.link?exists && msg.link != "">
								<a href="${msg.link}" title="${msg.textoAbreviado}" <#if msg.usuarioMensagemId?exists> onclick="marcarMensagemLida(${msg.usuarioMensagemId});" </#if> style="${style}">
									${msg.textoAbreviado}
								</a>
							<#else>
								<span style="${style}">${msg.textoAbreviado}</span>
							</#if>
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
</div>