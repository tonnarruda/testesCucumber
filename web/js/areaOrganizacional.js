function verificaMaternidade(areaId, campoAreaId)
{
	if(areaId != "null" && areaId != "")
	{
		AreaOrganizacionalDWR.verificaMaternidade(areaId,
		{
			//function callback
			callback:function(data){
			},
			//function error
			errorHandler:function(msg, exception){
				document.getElementById(campoAreaId).value = "";			
				jAlert(msg);
			}
		});
	}
}