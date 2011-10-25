require 'rubygems'
require 'pg'

$db_name = "fortesrh"
$data = File.read("./web/WEB-INF/metadata/create_data.sql").gsub(/-- BEGIN_TEST_IGNORE.*?-- END_TEST_IGNORE/m, '')

def reload_db
  puts "Limpando Banco de Dados, apagando todos os registros"
  begin
	conn = PGconn.connect( :dbname => $db_name, :user => 'postgres')
    conn.exec("select alter_trigger(table_name, 'DISABLE') FROM information_schema.constraint_column_usage  where table_schema='public'  and table_catalog='#{$db_name}' group by table_name;")

    tables = conn.exec("SELECT table_name FROM information_schema.tables WHERE table_type = 'BASE TABLE' AND table_schema NOT IN ('pg_catalog', 'information_schema');")
    delete_tables = tables.map {|table| "delete from #{table['table_name']};"}.join()

    conn.exec delete_tables
    conn.exec("select alter_trigger(table_name, 'ENABLE') FROM information_schema.constraint_column_usage  where table_schema='public'  and table_catalog='#{$db_name}' group by table_name;")
    
    puts "Populando Banco de Dados, dados iniciais..."
    
    conn.exec($data)
    puts "Banco de Dados populado com sucesso."
  ensure
    conn.finish if conn
  end
end

if ARGV[0] == '--start'
  reload_db
end