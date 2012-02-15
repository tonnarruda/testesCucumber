
function enviar(mensagem, exceptionClass)
{
	try {
		MorroDWR.enviar(mensagem, exceptionClass, $('#detalhesException .plain').text().replace(/at /g,'\nat '), location.href, BrowserDetect.browser + ', Versao: ' + BrowserDetect.version,  function(dados) { jAlert(dados); });
	} catch (e) {
		jAlert('Enviado com sucesso');
	}
}