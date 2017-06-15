/*
 * Copyright 2017 John Boyer
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package me.johnboyer.img.aws.es;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.LazyInitializer;
import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

/**
 * Search service class
 * @author John Boyer
 *
 */
public final class ElasticsearchService {
	
	/**
	 * Lazy initialization class
	 * @author john Boyer
	 *
	 */
	private static class ServiceIntializer extends LazyInitializer<ElasticsearchService> {

		@Override
		protected ElasticsearchService initialize() throws ConcurrentException {
			return new ElasticsearchService();
		}
	}
	/**
	 * Log object
	 */
	private static final Logger LOG = Logger.getLogger(ElasticsearchService.class);
	
	/**
	 * @return A single instance of the service object
	 * @throws ConcurrentException if concurrency error occurs
	 */
	public static ElasticsearchService getInstance() throws ConcurrentException {
		return new ServiceIntializer().get();
	}
	
	/**
	 * Elasticsearch REST client
	 */
	private RestClient client;
	
	/**
	 * CTOR
	 */
	private ElasticsearchService() {
		client = RestClient.builder(new HttpHost("search-movies-lghaxmaw3m4vwsupglbcuijyui.us-west-2.es.amazonaws.com", 80, "http")).build();
	}
	
	/**
	 * Search the Elasticsearch service and returns the JSON response
	 * @param query Search query
	 * @return A JSON string
	 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/5.3/_the_search_api.html">https://www.elastic.co/guide/en/elasticsearch/reference/5.3/_the_search_api.html</a>
	 * @throws IOException if an I/O error occurs
	 */
	public String search(String query) throws IOException {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("q", "title:" + query);
		paramMap.put("pretty", "true");
		                               
		Response response = client.performRequest("GET", "/flickr/_search",
		                                                           paramMap);
		final String entityString = EntityUtils.toString(response.getEntity());
		LOG.debug(entityString);
		LOG.debug("Host -" + response.getHost() );
		LOG.debug("RequestLine -"+ response.getRequestLine() );
		return entityString;
	}

}
