require 'rubygems'
require 'pg'

$db_name = "fortesrh"
$conn = PGconn.connect( :dbname => $db_name, :user => 'postgres')

def reload_db
  puts "Limpando Banco de Dados, apagando todos os registros"
  begin
    $conn.exec("select alter_trigger(table_name, 'DISABLE') FROM information_schema.constraint_column_usage  where table_schema='public'  and table_catalog='#{$db_name}' group by table_name;")
    
    tables = $conn.exec("SELECT table_name FROM information_schema.tables WHERE table_type = 'BASE TABLE' AND table_schema NOT IN ('pg_catalog', 'information_schema');")
    delete_tables = tables.map {|table| "delete from #{table['table_name']};"}.join()
    
    $conn.exec delete_tables
    $conn.exec("select alter_trigger(table_name, 'ENABLE') FROM information_schema.constraint_column_usage  where table_schema='public'  and table_catalog='#{$db_name}' group by table_name;")
    
    puts "Populando Banco de Dados, dados iniciais..."
    popula_db
    puts "Banco de Dados populado com sucesso."
  ensure
    $conn.finish if $conn
  end
end

def popula_db
    i = 0
    File.readlines("./web/WEB-INF/metadata/create_data.sql").each do |linha|
      if linha =~ /^(set|select)/i
        $conn.exec(linha)
      elsif linha =~ /( cid | codigoCBO | cidade )/i
        if linha =~ /^insert into/i and i <= 6
          $conn.exec(linha)
          i+=1
        elsif linha =~ /^alter table/i
          $conn.exec(linha)
          i = 0          
        end
      elsif linha =~ /^(alter table|insert into)/i
          $conn.exec(linha)
      end
    end
end

if ARGV[0] == '--start'
  reload_db
end