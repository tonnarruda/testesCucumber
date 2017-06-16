# language: pt

Funcionalidade: Atualizar Dados do Colaborador

  Cenário: Alterar dados de Usuário sem Colaborador Associado
     Dado que eu esteja logado com o usuário "SOS"
    Então eu acesso o menu "Info. Funcionais > Cadastros > Atualizar meus dados"
        E eu devo ver "Sua conta de usuário não está vinculada à nenhum colaborador"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Alterar Dados de Colaborador
     Dado que exista um colaborador "Theresa May", da area "Financeiro", com o cargo "Contador" e a faixa salarial "I"
     Dado que eu esteja logado com o usuário "SOS"
    Então eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
        E eu clico na linha "Theresa May" da imagem "Criar Acesso ao Sistema"
        E eu preencho "Nome" com "Theresa May"
        E eu preencho "Login" com "may"
        E eu preencho "Senha" com "1234"
        E eu preencho "Confirmar Senha" com "1234"
        E eu seleciono "Administrador" de "selectPerfil_1"
        E eu clico no botão "Gravar"
     Dado que eu esteja deslogado
        E eu preencho "username" com "may"
        E eu preencho "password" com "1234"
        E eu clico em "Entrar"
    Então eu acesso o menu "Info. Funcionais > Cadastros > Atualizar meus dados"
        E eu preencho "E-mail" com "email@teste.com.br"
    Então eu clico na aba "FORMAÇÃO ESCOLAR"
        E eu clico no botão de Id "inserirIdioma"
        E eu seleciono "Inglês" de "Idioma"
        E eu seleciono "Avançado" de "Nível"
        E eu clico no botão de Id "frmIdioma_0"
        E eu preencho "Curso" com "Contabilidade Geral"
        E eu clico no botão "Gravar"
    Então eu seleciono a opçao de valor "R" do rádio de id "tipoAlteracao"
        E eu clico no botão com o texto "Gravar"
        E eu devo ver "Dados atualizado com sucesso."
    Então eu devo deslogar do sistema