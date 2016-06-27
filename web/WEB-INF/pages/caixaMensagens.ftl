<#if action.getDescricaoTipo(tipo)?exists>
	<div class="portlet">
		<input type="hidden" name="caixa" value="${tipo}"/>
		<div class="portlet-header portlet-header-${tipo}">
			${action.getDescricaoTipo(tipo)} (${action.getTotalNaoLidas(tipo)} não lidas) 
			<a href="mensagens.action?tipo=${tipo}" class="verTodas">&bull; ver todas</a>
		</div>
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
							<#assign status><img border="0" title="Não lida" src="<@ww.url value="imgs/mail.png"/>"/></#assign>
						<#else>
							<#assign style="color: #949494;"/>
							<#assign status><img border="0" title="Lida" src="<@ww.url value="imgs/mail-open.png"/>"/></#assign>
						</#if>
					
						<tr class="${class}">
							<td width="40" align="center">
								<#if msg.usuarioMensagemId?exists>
									<a href="javascript: popup('geral/usuarioMensagem/leituraUsuarioMensagemPopup.action?internalToken=${internalToken}&amp;usuarioMensagem.empresa.id=${empresaId}&amp;usuarioMensagem.id=${msg.usuarioMensagemId}&amp;tipo=${tipo}', 400, 500)"><img border="0" title="Visualizar mensagem"  src="<@ww.url value="/imgs/olho.jpg"/>"/> </a>
									<a href="javascript: newConfirm('Confirma exclusão?', function(){window.location='geral/usuarioMensagem/delete.action?internalToken=${internalToken}&amp;usuarioMensagem.id=${msg.usuarioMensagemId}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"/></a>
								</#if>
							</td>
							<td>
								<#if msg.remetente?exists && msg.data?exists>
									${status}&nbsp;&nbsp;<span class="remetenteHora">${msg.remetente} - ${msg.data?string("dd/MM/yyyy HH:mm")}</span><br />
								</#if>
								
								<div class="tituloMensagem">
									<#if msg.link?exists && msg.link != "">
										<a href="${msg.link}" <#if msg.usuarioMensagemId?exists> onclick="marcarMensagemLida(${msg.usuarioMensagemId});" </#if> style="text-decoration:underline; ${style}" <#if msg.anexo>download title="Disponível apenas durante o período de realização do curso."<#else>title="${msg.textoAbreviado}"</#if>>
											${msg.textoAbreviado}
										</a>
									<#else>
										<a style="${style}">${msg.textoAbreviado}</a>
									</#if>
								</div>
							</td>
						</tr>
						<#assign j=j+1/>
						
						<#if j==10><#break></#if>
					</#list>
					
					<#if (action.getMensagens(tipo)?size < 1)>
						<tr>
							<td>Não há mensagens a serem exibidas</td>
						</tr>
					</#if>
				</tbody>
			</table>
		</div>
	</div>
</#if>