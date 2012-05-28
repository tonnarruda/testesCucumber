package com.fortes.model.type;

public enum FileStoreType
{	
	SAVE_ON_DATABASE, 
	SAVE_ON_DISK,
	SAVE_ON_DISK_SECURED	
	
	//TODO: Devem existir 3 casos de armazenamento de arquivos:
	//
	// 1- Salvar no banco (ideal para arquivos pequenos que serío pouco 
	//    vizualizados. Ex: foto do funcionário na Intranet).
	//
	// 2- Salvar em disco com acesso via url (ideal para arquivos que não 
	//    precisam de segurança e são muito vizualizados. Ex: imagens de notícias de um portal)
	//
	// 3- Salvar em disco mas sem acesso via url, o download é feito via streamming (ideal para arquivos 
	//    grandes ou muito visualizados mas que precisam de autenticação do usuário para serem visualizados.
	//    Ex: download de atualizações dos sistemas da Fortes).
	//
	// Podemos criar uma anotação para definir estes 3 tipos. Ex:
	// 
	// @FileStore(FileStoreType.SAVE_ON_DISK)
	// private File foto;
	// ...
	// 
	// public static final int SAVE_ON_DATABASE = 1;
	// public static final int SAVE_ON_DISK = 2;
	// public static final int SAVE_ON_DISK_SECURED = 3;	
}
