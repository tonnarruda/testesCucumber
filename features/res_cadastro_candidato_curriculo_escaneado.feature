# language: pt

Funcionalidade: Cadastrar Candidato

  @dev
  Cenário: Cadastro de Curriculo escaneado do Candidato
    Dado que eu esteja logado com o usuário "SOS"
	Dado que exista o cargo "baba"
	
    Quando eu acesso o menu "R&S > Cadastros > Candidatos"
    Então eu devo ver o título "Candidatos"
    Quando eu clico no botão "InserirCurriculoEscaneado"
    Então eu devo ver o título "Escanear Currículo"
    Quando eu clico no botão "SalvarArquivos"
	Então eu devo ver o alert "Preencha os campos indicados." e clico no ok
	E eu marco "baba"
    Quando eu clico no botão "SalvarArquivos"
    Então eu devo ver o alert do valida campos e clico no ok
    Quando eu clico no botão "Cancelar"
    Então eu devo ver o título "Candidatos"
    