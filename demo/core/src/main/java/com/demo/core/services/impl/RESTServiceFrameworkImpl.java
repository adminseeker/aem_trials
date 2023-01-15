package com.demo.core.services.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.demo.core.bo.RequestModel;
import com.demo.core.services.RESTServiceFramework;
import com.demo.core.services.impl.ApiRestConfigImpl;
import com.demo.core.utils.CommonsUtil;

@Component(immediate = true, service = RESTServiceFramework.class)
public class RESTServiceFrameworkImpl implements RESTServiceFramework {
	
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(RESTServiceFrameworkImpl.class);
	
	@Reference
	private ApiRestConfigImpl config;

	@Override
	public String makeGetWSCall(String endPointUrl, Map<String, String> requestParams, Map<String, String> headers, RequestModel requestModel) {

		try (final CloseableHttpClient client = HttpClients.createDefault()) {
			final long startTime = System.nanoTime();
			if (null != requestParams && !requestParams.isEmpty()) {
				StringBuilder parmsBuilder = buildStringBuilderFromRequestParams(requestParams);
				StringBuilder endPointBuilder = new StringBuilder(endPointUrl);
				endPointUrl = endPointUrl.contains("?") ? endPointBuilder.append(parmsBuilder.toString().replace(" ", "+")).toString() : endPointBuilder.append("?").append(parmsBuilder.toString().replaceAll(" ", "+")).toString();
			}
			final HttpRequestBase request = new HttpGet(endPointUrl);

			if (null != headers && !headers.isEmpty()) {
				for (final Entry<String, String> header : headers.entrySet()) {
					request.addHeader(header.getKey(), header.getValue());
				}
			}
			CloseableHttpResponse response = client.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			log.info("GET Endpoint : {}, status code :  {} ", printUrl(endPointUrl), statusCode);
			
			final HttpEntity entity = response.getEntity();
			String resp = EntityUtils.toString(entity, "UTF-8");
			
			final long endTime = System.nanoTime();
			final long duration = endTime - startTime;
			final double timeLapsedInSeconds = duration / 1000000000.0;
			log.info("*********Execution time seconds for {} ******* {}", printUrl(endPointUrl), timeLapsedInSeconds);
			log.debug("GET Endpoint : {}, response :  {}", printUrl(endPointUrl), printUrl(resp));
			return resp;
		} catch (final SocketTimeoutException exception) {
			log.error("SocketTimeoutException exception occured in makeGetWSCall...{}", exception);
		} catch (final IOException exception) {
			log.error("IOException exception occured in makeGetWSCall...{}", exception);
		} catch (final Exception e) {
			log.error("Exception occured in makeGetWSCall method of RESTServiceFrameworkImpl: {}", e);
		}
		return "";
	}

	private String printUrl(String endPointUrl) {
		return StringEscapeUtils.escapeJava(endPointUrl);
	}



	private StringBuilder buildStringBuilderFromRequestParams(Map<String, String> requestParams) throws UnsupportedEncodingException {
		StringBuilder parmsBuilder = new StringBuilder();
		final int size = requestParams.size();
		int index = 0;
		for (final Entry<String, String> entry : requestParams.entrySet()) {
			String param = entry.getKey();
			if (index++ == size - 1) {
				if (StringUtils.isBlank(requestParams.get(param))) {
					parmsBuilder.append(param).append("=").append(URLEncoder.encode("\"\"", "UTF-8"));
				} else {
					parmsBuilder.append(param).append("=").append(requestParams.get(param));
				}
			} else {
				if (StringUtils.isBlank(requestParams.get(param))) {
					parmsBuilder.append(param).append("=").append(URLEncoder.encode("\"\"", "UTF-8")).append("&");
				} else {
					parmsBuilder.append(param).append("=").append(requestParams.get(param)).append("&");
				}
			}
		}
		return parmsBuilder;
	}

	/**
	 * Make post WS call.
	 *
	 * @param endPointUrl   the end point url
	 * @param requestBody   the request body
	 * @param requestParams the request params
	 * @param headers       the headers
	 * @return the string
	 */
	@Override
	public String makePostWSCall(final String endPointUrl, final String requestBody, final Map<String, String> requestParams, final Map<String, String> headers, RequestModel requestModel) {
		try (final CloseableHttpClient client = HttpClients.createDefault()) {
			final long startTime = System.nanoTime();
			final HttpPost post = new HttpPost(endPointUrl);
			if (null != headers && !headers.isEmpty()) {
				for (final Entry<String, String> header : headers.entrySet()) {
					post.addHeader(header.getKey(), header.getValue());
				}
			}

			log.debug("POST Endpoint : {}, request :  {} ", printUrl(endPointUrl), printUrl(requestBody));

			if (StringUtils.isNotEmpty(requestBody)) {
				final StringEntity stringEntity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
				post.setEntity(stringEntity);
			} else if (null != requestParams && !requestParams.isEmpty()) {
				final List<BasicNameValuePair> parametersBody = getParametersBody(requestParams);
				post.setEntity(new UrlEncodedFormEntity(parametersBody));
			}
			CloseableHttpResponse response = client.execute(post);
			int statusCode = response.getStatusLine().getStatusCode();
			log.info("POST Endpoint : {}, status code :  {} ", printUrl(endPointUrl), statusCode);
			
			if (response.getEntity() == null) {
				return null;
			}
			final HttpEntity entity = response.getEntity();
			final String resp = EntityUtils.toString(entity, "UTF-8");
			final long endTime = System.nanoTime();
			final long duration = endTime - startTime;
			final double timeLapsedInSeconds = duration / 1000000000.0;
			log.info("*********Execution time seconds for POST Endpoint {} ******* {}", printUrl(endPointUrl), timeLapsedInSeconds);
			log.debug("POST Endpoint : {}, response :  {}", printUrl(endPointUrl), printUrl(resp));
			return resp;
		} catch (final SocketTimeoutException exception) {
			log.error("SocketTimeoutException exception occured in makePostWSCall...{}", exception);
		} catch (final IOException exception) {
			log.error("IOException exception occured in makePostWSCall...{}", exception);
		} catch (final Exception e) {
			log.error("Exception occured in makePostWSCall method of RESTServiceFrameworkImpl: {}", e);
		}
		return null;
	}


	/**
	 * Gets the parameters body.
	 *
	 * @param requestParams the request params
	 * @return the parameters body
	 */
	private List<BasicNameValuePair> getParametersBody(final Map<String, String> requestParams) {
		return requestParams.entrySet().parallelStream().map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue())).collect(Collectors.toList());

	}

	/**
	 * Make put WS call.
	 *
	 * @param endPointUrl   the end point url
	 * @param requestBody   the request body
	 * @param requestParams the request params
	 * @param headers       the headers
	 * @return the string
	 */
	@Override
	public String makePutWSCall(final String endPointUrl, final String requestBody, final Map<String, String> requestParams, final Map<String, String> headers, RequestModel requestModel) {
		try (final CloseableHttpClient client = HttpClients.createDefault()) {
			final long startTime = System.nanoTime();
			final HttpPut put = new HttpPut(endPointUrl);
			if (null != headers && !headers.isEmpty()) {
				for (final Entry<String, String> header : headers.entrySet()) {
					put.addHeader(header.getKey(), header.getValue());
				}
			}

			log.debug("PUT Endpoint : {}, request :  {} ", printUrl(endPointUrl), printUrl(requestBody));

			if (StringUtils.isNotEmpty(requestBody)) {
				final StringEntity stringEntity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
				put.setEntity(stringEntity);
			} else if (null != requestParams && !requestParams.isEmpty()) {
				final List<BasicNameValuePair> parametersBody = getParametersBody(requestParams);
				put.setEntity(new UrlEncodedFormEntity(parametersBody));
			}
			CloseableHttpResponse response = client.execute(put);
			int statusCode = response.getStatusLine().getStatusCode();
			log.info("PUT Endpoint : {}, status code :  {} ", printUrl(endPointUrl), statusCode);
			
			if (statusCode == 204) {
				return "{ \r\n" + "\"status\":\"success/nocontent\"\r\n" + "}";
			}

			if (response.getEntity() == null) {
				return null;
			}
			final HttpEntity entity = response.getEntity();
			final String resp = EntityUtils.toString(entity, "UTF-8");
			final long endTime = System.nanoTime();
			final long duration = endTime - startTime;
			final double timeLapsedInSeconds = duration / 1000000000.0;
			log.info("*********Execution time seconds for PUT Endpoint : {} ******* {}", printUrl(endPointUrl), timeLapsedInSeconds);
			log.debug("PUT Endpoint : {}, response :  {}", printUrl(endPointUrl), printUrl(resp));
			return resp;

		} catch (final SocketTimeoutException exception) {
			log.error("SocketTimeoutException exception occured in makePutWSCall...{}", exception);
		} catch (final IOException exception) {
			log.error("IOException exception occured in makePutWSCall...{}", exception);
		} catch (final Exception e) {
			log.error("Exception occured in makePutWSCall method of RESTServiceFrameworkImpl : {}", e);
		}
		return null;
	}

	/**
	 * Make delete WS call.
	 *
	 * @param endPointUrl the end point url
	 * @param headers     the headers
	 * @return the string
	 */
	@Override
	public String makeDeleteWSCall(final String endPointUrl, final Map<String, String> headers, RequestModel requestModel) {
		try (final CloseableHttpClient client = HttpClients.createDefault()) {
			final long startTime = System.nanoTime();
			final HttpDelete delete = new HttpDelete(endPointUrl);
			if (null != headers && !headers.isEmpty()) {
				for (final Entry<String, String> header : headers.entrySet()) {
					delete.addHeader(header.getKey(), header.getValue());
				}
			}
			CloseableHttpResponse response = client.execute(delete);
			final int statusCode = response.getStatusLine().getStatusCode();
			
			final long endTime = System.nanoTime();
			final long duration = endTime - startTime;
			final double timeLapsedInSeconds = duration / 1000000000.0;
			log.info("*********Execution time seconds for DELETE Endpoint {}******* {}", printUrl(endPointUrl), timeLapsedInSeconds );
			log.info("*********Status Code for DELETE Endpoint {} ******** {}",printUrl(endPointUrl),statusCode);
			final HttpEntity entity = response.getEntity();
			return null != entity ? EntityUtils.toString(entity, "UTF-8") : response.getStatusLine().toString();

		} catch (final SocketTimeoutException exception) {
			log.error("SocketTimeoutException exception occured in makeDeleteWSCall...{}", exception);
		} catch (final IOException exception) {
			log.error("IOException exception occured in makeDeleteWSCall...{}", exception);
		} catch (final Exception e) {
			log.error("Exception occured in makeDeleteWSCall method of RESTServiceFrameworkImpl:{} ", e);
		}
		return null;
	}
}
