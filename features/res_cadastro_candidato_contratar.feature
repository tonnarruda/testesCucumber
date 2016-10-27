# language: pt

Funcionalidade: Cadastrar Candidato

  @dev
  Cenário: Cadastro de Experiência do Candidato
    Dado que eu esteja logado com o usuário "SOS"
	Dado que exista o cargo "baba"
	
    Quando eu acesso o menu "R&S > Cadastros > Candidatos"
    Então eu devo ver o título "Candidatos"
    Quando eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Candidato"
    E eu clico na aba "Experiências"
    Quando eu clico no botão "Inserir"
    E eu devo ver "Inserir Experiência"
    Quando eu clico no botão "Gravar"
	Então eu devo ver o alert "Selecione um cargo." e clico no ok
	E eu seleciono "baba" de "Cargo"
    Quando eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
	E eu preencho "Empresa" com "veica"
    E eu preencho o campo (JS) "Data de Admissão" com "01/06/2011"
    Então eu clico no botão "Gravar"
    
    Então eu devo ver "Experiência Profissional"
    E eu devo ver "veica"
    Entao eu clico em editar "veica"
    E o campo "Empresa" deve conter "veica"
	E eu preencho "Empresa" com "sms"
    Então eu clico no botão "Gravar"
    
    Então eu devo ver "Experiência Profissional"
    E eu devo ver "sms"
    
    Então eu clico em excluir "sms"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu não devo ver "sms"
    
    