# img-es-search-app

## Overview
The **Image Search Java Web App** is a sample application that queries the AWS Elasticsearch (ES) Flickr index generated by the [Image Indexer](https://github.com/johnboyer/img-indexer) app and returns the results in JSON response.

## Demo
The demo app is offline. If it were online, you'd perform a full-text search by entering the search parameter in the query string. For example, to find photos with the caption `coffee`, click or enter the following URL in your browser: http://example.com/es?search=coffee

The `coffee` query returns the following pretty printed JSON:

	    {
        "took" : 4,
        "timed_out" : false,
        "_shards" : {
            "total" : 5,
            "successful" : 5,
            "failed" : 0
        },
        "hits" : {
            "total" : 41,
            "max_score" : 10.166786,
            "hits" : [
            {
                "_index" : "flickr",
                "_type" : "photo",
                "_id" : "AVyeum9FMMtxOkRnPObc",
                "_score" : 10.166786,
                "_source" : {
                "smallUrl" : "https://farm5.static.flickr.com/4218/34396802284_eb405e7305_n.jpg",
                "width" : "320",
                "title" : "Coffee",
                "height" : "240"
                }
            },
            ....


## Usage
First, update the AWS ES endpoint to the same domain used in the [Image Indexer](https://github.com/johnboyer/img-indexer) project. In the constructor of the `me.johnboyer.img.aws.es.ElasticsearchService` class, change the host name to your AWS ES endpoint:

	private ElasticsearchService() {
		client = RestClient.builder(new HttpHost("<MY-AWS-ES-DOMAIN>", 80, "http")).build();
	}

 
Build the project and upload it to a Java application server.

	#Generates a WAR file in dist/
	ant -buildfile deploy.xml dist
	

For more information about AWS ES see the [Getting Started with AWS ES Service Domains](http://docs.aws.amazon.com/elasticsearch-service/latest/developerguide/es-gsg-configure-access.html) tutorial.

## License
This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
