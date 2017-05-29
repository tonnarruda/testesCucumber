<style type="text/css">
	@import url('<@ww.url value="/css/font-awesome.min.css"/>');
</style>
<script type="text/javascript">
	$(function() {
		if($('.info').height())
			overFlow('info');
		else if($('.warning').height())
			overFlow('warning');
		else if($('.success').height())
			overFlow('success');
		else if($('.error').height())
			overFlow('error');
	});
	
	function overFlow(identificador){
		var curHeight = 0;
		curHeight = $('.' + identificador).height();
		if(curHeight > 75){
			$('.' + identificador).append('<div id="expandir'  + identificador + '" style="text-align:center;"><i class="fa fa-chevron-circle-down down fa-lg" aria-hidden="true"></i></div>');
			$('.' + identificador).css({ overflow: "hidden", height: "75px" });
			$('#' + identificador + 'Msg').css({ overflow: "hidden", height: "54px" });
			
			$('#expandir' + identificador).click(function() {
				$('#' + identificador +  'Msg').height(curHeight).animate({height: $("#" + identificador + "Msg")[0].scrollHeight}, 2000);
				$('.' + identificador).height(curHeight).animate({height: $("#" + identificador + "Msg")[0].scrollHeight}, 2000);
			});
		}
	}
</script>

<#if (actionMessages?exists && actionMessages?size > 0)>
	<div class="info">
		<div style="float:right;"><a title="Ocultar" href="javascript:;" onclick="$(this).parent().parent().remove();">x</a></div>
		<div id="infoMsg">
			<ul>
				<#list actionMessages as msg>
					<li>${msg}</li>
				</#list>
			</ul>
		</div>
	</div>
</#if>

<#if (actionWarnings?exists && actionWarnings?size > 0)>
	<div class="warning">
		<div style="float:right;"><a title="Ocultar" href="javascript:;" onclick="$(this).parent().parent().remove();">x</a></div>
		<div id="warningMsg">
			<ul>
				<#list actionWarnings as msg>
					<li>${msg}</li>
				</#list>
			</ul>
		</div>
	</div>
</#if>

<#if (actionSuccess?exists && actionSuccess?size > 0)>
	<div class="success">
		<div style="float:right;"><a title="Ocultar" href="javascript:;" onclick="$(this).parent().parent().remove();">x</a></div>
		<div id="successMsg">
			<ul>
				<#list actionSuccess as msg>
					<li>${msg}</li>
				</#list>
			</ul>
		</div>
	</div>
</#if>