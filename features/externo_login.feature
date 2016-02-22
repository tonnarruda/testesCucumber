# language: pt

Funcionalidade: Login no modulo externo
 
  @dev
  Cenário: logar
    Quando eu acesso "externo/prepareLogin.action?empresaId=1"
    E eu clico "Quero me cadastrar"
    Então eu devo ver "Prezado Candidato"
    
    Quando eu clico na aba "CURRÍCULO"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert "Senha obrigatória para acesso ao modulo exteno." e clico no ok
    Quando eu clico na aba "DADOS PESSOAIS"
    E eu preencho "Senha" com "123"
    E eu preencho "Confirmar Senha" com "123"
    E eu clico na aba "CURRÍCULO"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert "Os seguintes campos são obrigatórios:" e clico no ok
    
    Quando eu clico na aba "DADOS PESSOAIS"
    E eu preencho "nome" com "pedro"
    E eu preencho o campo (JS) "Nascimento" com "01/01/1987"
    E eu preencho "Naturalidade" com "fortaleza"
    E eu preencho o campo (JS) "CPF" com "881.028.771-11"
    E eu seleciono "Analfabeto, inclusive o que, embora tenha recebido instrução, não se alfabetizou" de "Escolaridade"
    E eu preencho "Logradouro" com "Eretides"
    E eu preencho "num" com "11"
    E eu preencho "Complemento" com "apto"
    E eu seleciono "CE" de "Estado"
    E eu seleciono "Fortaleza" de "cidade"
    E eu preencho "Bairro" com "Nossa Senhora da Apresentação"
    E eu saio do campo "Bairro"
    E eu preencho "E-mail" com "franciscobarroso@grupofortes.com.br"
    E eu preencho "DDD" com "85"
    E eu preencho "Telefone" com "88438383"
    E eu preencho "Celular" com "88438309"
    E eu preencho "Contato" com "Maria"
    E eu preencho "Parentes/Amigos na empresa" com "Pedro"
    E eu clico na aba "CURRÍCULO"
    Então eu clico no botão "Gravar"
    
    Quando eu acesso "externo/prepareLogin.action?empresaId=1"
    E eu clico no botão "Entrar"
    Então eu devo ver o alert do valida campos e clico no ok
    
    E eu preencho o campo (JS) "cpfRH" com "881.028.771-11"
    E eu preencho "senhaRH" com "123"
    E eu clico no botão "Entrar"
    Então eu devo ver "Bem vindo(a)"
    
    Quando eu clico "Anexar Documentos"
    E eu devo ver "Documentos Anexos"
    E eu clico no botão "Inserir"
    E eu clico no botão "Gravar"
    E eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    E eu devo ver "Documentos Anexos"
	Então eu clico "Vagas Abertas"
	E eu devo ver "Vagas Abertas"

    E eu clico "Sair"
