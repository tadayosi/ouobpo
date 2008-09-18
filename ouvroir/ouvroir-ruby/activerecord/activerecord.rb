require 'rubygems'
require 'active_record'
require 'logger'

logger = Logger.new STDOUT
logger.level = Logger::DEBUG
ActiveRecord::Base.logger = logger

db = "#{ENV['TMPDIR']}ouvroir.sqlite3"
logger.info db

class Tables < ActiveRecord::Migration
  def self.up
    create_table(:samples) { |t|
      t.column :value, :string
    }
  end

  def self.down
    drop_table :samples
  end
end

class Sample < ActiveRecord::Base
  def to_s
    "#{id} : #{value}"
  end
end

################################################################################

ActiveRecord::Base.establish_connection(
  :adapter => "sqlite3",
  :database => db
)

Tables.migrate :up unless File.exist? db

sample = Sample.new
sample.value = Time.now.strftime "%H%M%S"
sample.save

samples = Sample.find :all
samples.each { |s|
  puts s
}
