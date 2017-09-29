# language: pt

Funcionalidade: Campos Extras para Candidato

  @dev
  Cenário: Configurar Campos Padrões
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Utilitários > Configurações > Configurar Cadastro de Colaborador e Candidato > Campos padrões do sistema"
    Entao eu clico na aba candidato de campo extra
    E eu marco "indicado" da aba "candidato"
    E eu marco "estadoCivil" da aba "candidato"    
    E eu marco "profMae" da aba "candidato"
    E eu clico no botão "Gravar"
    E eu devo ver "Campos do candidato gravados com sucesso"
    
@teste
  Cenário: Configurar Todos os Campos Padrões
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Utilitários > Configurações > Configurar Cadastro de Colaborador e Candidato > Campos padrões do sistema"
    Entao eu clico na aba candidato de campo extra
    E eu marco todos os itens padrões
    E eu clico no botão "Gravar"
    E eu devo ver "Campos do candidato gravados com sucesso"    
