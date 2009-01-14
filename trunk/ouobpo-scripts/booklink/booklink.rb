#!/usr/bin/env ruby

if ARGV.length == 0
  puts <<END
Enter ASIN as parameter.
> #{$0} <ASIN>
END
  exit
end

require 'net/http'
require 'rexml/document'

$asin = ARGV[0]
$aws_url = 'ecs.amazonaws.jp'
$aws_path_and_params = "/onca/xml?Service=AWSECommerceService&AWSAccessKeyId=041WR28EQVEF1112J6G2&Operation=ItemLookup&ItemId=#{$asin}&ResponseGroup=Medium"

Net::HTTP.version_1_2
Net::HTTP.start($aws_url, 80) { |http|
  response = http.get($aws_path_and_params)

  $doc = REXML::Document.new response.body
  $img = REXML::XPath.first($doc, '//ItemLookupResponse/Items/Item/MediumImage/URL/text()')

  puts <<END
<a href="http://www.amazon.co.jp/gp/product/#{$asin}?tag=ouobpo-22" target="_blank">
<img src="#{$img}" border="0" height="80"/>
</a>
END
}
