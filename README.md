# ElasticSearchExample #

## Project ##

This project is a sample for [Elasticsearch] with Java. The application parses the [Quality Controlled Local Climatological Data (QCLCD)] 
from March 2015 and bulk inserts it into a Elasticsearch instance. It doesn't deal with Clustering and only inserts on a single node.

I have used [Kibana] to create some visualizations and [JTinyCsvParser] to parse the CSV data.

Please see the blog post for more information on how to work with the code:

* https://bytefish.de/blog/elasticsearch_java/

## Dataset ##

The data is the [Quality Controlled Local Climatological Data (QCLCD)]: 

> Quality Controlled Local Climatological Data (QCLCD) consist of hourly, daily, and monthly summaries for approximately 
> 1,600 U.S. locations. Daily Summary forms are not available for all stations. Data are available beginning January 1, 2005 
> and continue to the present. Please note, there may be a 48-hour lag in the availability of the most recent data.

The data is available at:

* [http://www.ncdc.noaa.gov/orders/qclcd/](http://www.ncdc.noaa.gov/orders/qclcd/)

## Result ##

<a href="https://raw.githubusercontent.com/bytefish/JavaElasticSearchExperiment/master/JavaElasticSearchExperiment/img/kibana.jpg">
	<img src="https://raw.githubusercontent.com/bytefish/JavaElasticSearchExperiment/master/JavaElasticSearchExperiment/img/kibana.jpg" width="50%" height="50%" alt="Kibana Visualization of the Average US Temperature in March 2015" />
</a>


[JTinyCsvParser]: https://github.com/bytefish/JTinyCsvParser
[Elasticsearch]: https://www.elastic.co/products/elasticsearch
[Kibana]: https://www.elastic.co/products/kibana
[Quality Controlled Local Climatological Data (QCLCD)]: https://www.ncdc.noaa.gov/data-access/land-based-station-data/land-based-datasets/quality-controlled-local-climatological-data-qclcd
