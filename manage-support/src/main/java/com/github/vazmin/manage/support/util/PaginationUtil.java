package com.github.vazmin.manage.support.util;

import com.github.vazmin.framework.core.service.Pagination;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Utility class for handling pagination.
 *
 * <p>
 * Pagination uses the same principles as the <a href="https://developer.github.com/v3/#pagination">GitHub API</a>,
 * and follow <a href="http://tools.ietf.org/html/rfc5988">RFC 5988 (Link header)</a>.
 */
public final class PaginationUtil {

    private PaginationUtil() {
    }

    public static HttpHeaders generatePaginationHttpHeaders(Pagination page, String baseUrl) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(page.getTotalCount()));
        String link = "";
        if ((page.getPageNumber() + 1) < page.getTotalPage()) {
            link = "<" + generateUri(baseUrl, page.getPageNumber() + 1, page.getPageSize()) + ">; rel=\"next\",";
        }
        // prev link
        if ((page.getPageNumber()) > 0) {
            link += "<" + generateUri(baseUrl, page.getPageNumber() - 1, page.getPageSize()) + ">; rel=\"prev\",";
        }
        // last and first link
        int lastPage = 0;
        if (page.getTotalPage() > 0) {
            lastPage = page.getTotalPage() - 1;
        }
        link += "<" + generateUri(baseUrl, lastPage, page.getPageSize()) + ">; rel=\"last\",";
        link += "<" + generateUri(baseUrl, 0, page.getPageSize()) + ">; rel=\"first\"";
        headers.add(HttpHeaders.LINK, link);
        return headers;
    }

    private static String generateUri(String baseUrl, int page, int size) {
        return UriComponentsBuilder.fromUriString(baseUrl).queryParam("page", page).queryParam("size", size).toUriString();
    }
}
