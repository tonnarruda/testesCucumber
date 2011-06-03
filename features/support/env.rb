require 'selenium-webdriver'
require 'cucumber/formatter/unicode' # Remove this line if you don't want Cucumber Unicode support
require 'capybara'
require 'capybara/dsl'
require 'capybara/session'
require 'ruby-debug'
require 'pg'

######################################################################################################################
# require 'capybara/cucumber' foi substituido pelo codigo abaixo. Motivo: nao matar a sessao entre
# os cenarios. O original se encontra em C:\Ruby192\lib\ruby\gems\1.9.1\gems\capybara-0.4.1.2\lib\capybara\cucumber.rb

World(Capybara)

Before('@javascript') do
  Capybara.current_driver = Capybara.javascript_driver
end

Before('@selenium') do
  Capybara.current_driver = :selenium
end

After do
  Capybara.use_default_driver
end
######################################################################################################################

Capybara.default_driver = :selenium
Capybara.default_wait_time = 5

Capybara.register_driver :selenium do |app|
  Capybara::Driver::Selenium.new(app,
    :browser => :remote,
    :url => "http://localhost:4444/wd/hub",
    :desired_capabilities => :firefox)
end
# Capybara defaults to XPath selectors rather than Webrat's default of CSS3. In
# order to ease the transition to Capybara we set the default here. If you'd
# prefer to use XPath just remove this line and adjust any selectors in your
# steps to use the XPath syntax.
Capybara.default_selector = :css

Before do
  puts "Limpando Banco de Dados, apagando todos os registros"
  db = "fortesrh"

  begin
    conn = PGconn.connect( :dbname => db, :user => 'postgres')
    conn.exec("select alter_trigger(table_name, 'DISABLE') FROM information_schema.constraint_column_usage  where table_schema='public'  and table_catalog='#{db}' group by table_name;")

    tables = conn.exec("select table_name FROM information_schema.constraint_column_usage  where table_schema='public'  and table_catalog='#{db}' group by table_name;")
    delete_tables = tables.map {|table| "delete from #{table['table_name']};"}.join()

    conn.exec delete_tables
    conn.exec("select alter_trigger(table_name, 'ENABLE') FROM information_schema.constraint_column_usage  where table_schema='public'  and table_catalog='#{db}' group by table_name;")
  ensure
    conn.finish if conn
  end

  puts "Populando Banco de Dados, dados iniciais..."
  `psql -U postgres #{db} < features/data/dataInicial.sql`
  puts "Banco de Dados populado com sucesso."

end

def exec_sql sql
  `echo #{sql} | psql -U postgres fortesrh`
end