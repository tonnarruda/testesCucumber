require 'selenium-webdriver'
require 'cucumber/formatter/unicode' # Remove this line if you don't want Cucumber Unicode support
require 'capybara'
require 'capybara/dsl'
require 'capybara/session'
require 'ruby-debug'
require 'pg'
require './features/support/reload_db.rb'

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

$db_name = "fortesrh"
$data = File.read("./web/WEB-INF/metadata/create_data.sql").gsub(/-- BEGIN_TEST_IGNORE.*?-- END_TEST_IGNORE/m, '')

Before do
  reload_db
end

def exec_sql sql
  begin
    conn = PGconn.connect( :dbname => $db_name, :user => 'postgres', :host => 'localhost')
    conn.exec(sql)
  ensure
    conn.finish if conn
  end
end

class Insert
	instance_methods.each { |m| undef_method m unless m =~ /^__|instance_eval|object_id/ }

	def initialize(table, options)
    options[:sem_id]  ||= false

		@table = table
    if options[:sem_id] == true
      @properties = {}
    else
      @properties = {:id => "nextval('#{@table}_sequence')"}
    end
	end

	def method_missing(method, *args, &block)
    column = method
    if args[0].is_a? Symbol
      table = args[0]
      value = args[1]
    else
      table = method
      value = args[0]
    end

    if (value.class == Hash)
			k, v = value.each_pair.first
      value = "(select id from #{table} where #{k} = #{v.to_sql_param})"
      column = "#{column}_id"
    else
			value = value.to_sql_param
		end
    @properties[column] = value
	end

  def to_sql
    "insert into #{@table}(" + @properties.keys.join(', ') + ") values (" + @properties.values.join(', ') + ")"
  end

end

def insert(table, options={}, &block)
	ins = Insert.new(table, options)
	ins.instance_eval(&block)
  exec_sql ins.to_sql
end

def create(table, properties)
  exec_sql "insert into #{table} (" + properties.keys.join(', ') + ") values (" + properties.values.join(', ') + ")"
end

class Object
	def to_sql_param
		to_s
	end
end

class String
	def to_sql_param
    "'#{self}'"
	end
end