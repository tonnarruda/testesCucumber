package com.fortes.rh.model.dicionario;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings({ "rawtypes", "serial" })
public class ErroFeedBackRemprot extends LinkedHashMap
{
	public static final int Error = -1;
	public static final int NoError = 0;
	public static final int LocationInfoDiffers = 1;
	public static final int WinchesterIdDiffers = 2;
	public static final int FileSystemDiffers = 4;
	public static final int HDFormatDiffers = 8;
	public static final int ProcessorIdDiffers = 16;
	public static final int Reserved0 = 32;
	public static final int Reserved1 = 64;
	public static final int Reserved2 = 128;
	public static final int Reserved3 = 256;
	public static final int ApplicationDiffers = 512;
	public static final int UserWantsToTransport = 1024;
	public static final int FileAccessDenied = 2048;
	public static final int ProtectionNotSet = 4096;
	public static final int LicenseDataNotFound = 8192;
	public static final int ExecutionLimitExpired = 16384;
	public static final int ProtectionNotFound = 32768;
	public static final int ServiceRemprotError = 65535;
	private static Map<Integer, String> mensagens = new HashMap<Integer, String>();

	public ErroFeedBackRemprot()
	{
		mensagens.put(Error,"Erro inesperado.");
		mensagens.put(NoError,"NoErro.");
		mensagens.put(LocationInfoDiffers, "Problema na comparação do diretório de proteção.");
		mensagens.put(WinchesterIdDiffers, "Problema na comparação do nome e serial do HD.");
		mensagens.put(FileSystemDiffers, "Problema na comparação do sistema de arquivos.");
		mensagens.put(HDFormatDiffers, "Problema na comparação da formatação do HD.");
		mensagens.put(ProcessorIdDiffers, "Problema na comparação do identificador do processador.");
		mensagens.put(Reserved0,"Erro Reserved0.");
		mensagens.put(Reserved1,"Erro Reserved1.");
		mensagens.put(Reserved2,"Erro Reserved2.");
		mensagens.put(Reserved3,"Erro Reserved3.");
		mensagens.put(ApplicationDiffers, "O nome que está gravado no \".inf\" difere do nome do arquivo.");
		mensagens.put(UserWantsToTransport, "O usuário deseja transportar a licença.");
		mensagens.put(FileAccessDenied,"Acesso negado (FileAccessDenied).");
		mensagens.put(ProtectionNotSet,"Dados da proteção não foram inseridos.");
		mensagens.put(LicenseDataNotFound,"Sem data de licença (LicenseDataNotFound).");
		mensagens.put(ExecutionLimitExpired, "Data para habilitação expirou.");
		mensagens.put(ProtectionNotFound, "Dados da proteção não foram encontrados.");
		mensagens.put(ServiceRemprotError, "Não foi possível conectar com servidor do Remprot.");
	}
	
	public String getMensagem(Integer tipo)
	{
		return mensagens.get(tipo) == null?"":mensagens.get(tipo);
	}
}