<#if !stack.findValue("#datepicker_js_included")?exists>
<#assign trash = stack.setValue("#datepicker_js_included", true)/>
<script type="text/javascript" src="<@ww.url includeParams="none" value="/webwork/jscalendar/" encode='false'/>calendar.js"></script>
<script type="text/javascript" src="<@ww.url includeParams="none" value="/webwork/jscalendar/lang/" encode='false'/>calendar-${parameters.language?default("en")}.js"></script>
<script type="text/javascript" src="<@ww.url includeParams="none" value="/webwork/jscalendar/" encode='false'/>calendar-setup.js"></script>
</#if>
<#include "/${parameters.templateDir}/simple/text.ftl" />
<a href="#" id="${parameters.id}_button"><img src="<@ww.url includeParams="none" value="/webwork/jscalendar/img.gif" encode='false'/>" width="16" height="16" border="0" title="Clique aqui para selecionar a data" align="absMiddle"></a>
<script type="text/javascript">
    Calendar.setup({
        inputField     :    "${parameters.id}",
<#if parameters.format?exists>
        ifFormat       :    "${parameters.format}",
</#if>
<#if parameters.showstime?exists>
        showsTime      :    "${parameters.showstime}",
</#if>
        button         :    "${parameters.id}_button",
<#if parameters.singleclick?exists>
        singleclick    :    ${parameters.singleclick},
</#if>
<#if parameters.eventOnUpdate?exists>
        onUpdate	:    ${parameters.eventOnUpdate},
</#if>
<#if parameters.eventOnSelect?exists>
        onSelect    :    ${parameters.eventOnSelect},
</#if>
        step           :    1
    });
</script>
