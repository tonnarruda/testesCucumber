# language: pt

Funcionalidade: Atualizar Dados do Colaborador

  Cenário: Alterar dados de Usuário sem Colaborador Associado
     Dado que eu esteja logado com o usuário "SOS"
    Então eu acesso o menu "Info. Funcionais > Cadastros > Atualizar meus dados"
        E eu devo ver "Sua conta de usuário não está vinculada à nenhum colaborador"
    

#------------------------------------------------------------------------------------------------------------------------
@teste
  Cenário: Alterar Dados de Colaborador
     Dado que exista um usuario "admin" associado a um empregado
     Dado que eu esteja deslogado
     Dado que eu esteja logado com o usuário "admin"
    Então eu acesso o menu "Info. Funcionais > Cadastros > Atualizar meus dados"
        E eu espero 10 segundos
        E eu preencho "E-mail" com "email@teste.com.br"
        E eu preencho o campo pelo name "colaborador.contato.ddd" com "85"       
        E eu preencho "Telefone" com "88438383"
    Então eu clico na aba "FORMAÇÃO ESCOLAR"
        E eu clico no botão de Id "inserirIdioma"
        E eu seleciono "Inglês" de "Idioma"
        E eu seleciono "Avançado" de "Nível"
        E eu clico no botão de Id "frmIdioma_0"
        E eu preencho "Curso" com "Contabilidade Geral"
        E eu clico no botão "Gravar"
        E eu devo ver "Dados atualizado com sucesso."
    Então eu devo deslogar do sistema



    