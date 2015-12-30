function colabNaOutraTurma(msg, altura, largura, titulo, submit)
{
	if (msg != ""){
		$('<div>' + msg + '</div>').dialog({title: titulo,
											modal: true, 
											height: altura,
											width: largura,
											buttons: [
											    {
											        text: "Sim",
											        click: function() { document.formColab.submit(); }
											    },
											    {
											        text: "Não",
											        click: function() { $(this).dialog("close"); }
											    }
											] 
											});
	}else if (submit){
		document.formColab.submit();
	}
}