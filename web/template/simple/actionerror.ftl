<#if (actionErrors?exists && actionErrors?size > 0)>
	<div class="error">
		<div style="float:right;"><a title="Ocultar" href="javascript:;" onclick="$(this).parent().parent().remove();">x</a></div>
		<div id="errorMsg">
			<ul>
				<#list actionErrors as msg>
					<#if msg?exists>
						<li>${msg}</li>
					<#else>
						<li>Ocorreu uma inconsistência inesperada.</li>
					</#if>
				</#list>
			</ul>
		</div>
	</div>
</#if>