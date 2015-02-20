package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings({ "rawtypes", "serial" })
public class ErroFeedBackRemprot extends LinkedHashMap
{
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
	public static final String AnyError = "MaxWord";

	@SuppressWarnings("unchecked")
	public ErroFeedBackRemprot()
	{
		put(NoError,"");
		put(LocationInfoDiffers, "Erro na comparação do diretório de proteção");
		put(WinchesterIdDiffers, "Erro na comparação do nome e serial do HD");
		put(FileSystemDiffers, "Erro na comparação do sistema de arquivos");
		put(HDFormatDiffers, "Erro na comparação da formatação do HD");
		put(ProcessorIdDiffers, "Erro na comparação do identificador do processador");
		put(Reserved0,"");
		put(Reserved1,"");
		put(Reserved2,"");
		put(Reserved3,"");
		put(ApplicationDiffers, "O nome que está gravado no .inf difere do nome do arquivo");
		put(UserWantsToTransport, "O usuário deseja transportar a licença");
		put(FileAccessDenied,"");
		put(ProtectionNotSet,"");
		put(LicenseDataNotFound,"");
		put(ExecutionLimitExpired, "Data para habilitação expirou");
		put(ProtectionNotFound, "Dados da proteção não foram encontrados");
		put(AnyError,"");
	}
}

