require 'selenium-webdriver'
require 'cucumber/formatter/unicode' # Remove this line if you don't want Cucumber Unicode support
#require 'cucumber/web/tableish'
require 'capybara/cucumber'
require 'capybara/session'
#require 'cucumber/rails/capybara_javascript_emulation' # Lets you click links with onclick javascript handlers without using @culerity or @javascript
require 'ruby-debug'
#set autoeval on

#Selenium::WebDriver.for :firefox
Capybara.default_driver = :selenium
Capybara.default_wait_time = 5

# This way we are using Selenium-RC

#VER!!!! http://selenium.googlecode.com/svn/trunk/docs/api/rb/Selenium/WebDriver/Firefox/Profile.html

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
