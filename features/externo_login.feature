# language: pt

Funcionalidade: Login no modulo externo
 
  @dev
  Cenário: logar
    Quando eu acesso "externo/prepareLogin.action?empresaId=1"
    E eu clico "Clique aqui para se cadastrar"
    Então eu devo ver "Prezado Candidato"
    Quando eu acesso "externo/prepareLogin.action?empresaId=1"
    E eu clico no botão "Entrar"
    Então eu devo ver o alert do valida campos e clico no ok
    
    E eu preencho o campo (JS) "CPF" com "137.322.372-30"
    E eu preencho "senhaRH" com "123"
    E eu clico no botão "Entrar"
