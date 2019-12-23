package com.github.vazmin.manage.support.web.filter;

import com.github.vazmin.manage.component.config.ManageProperties;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * This filter is used in production, to put HTTP cache headers with a long (4 years) expiration time.
 */
public class CachingHttpHeadersFilter implements Filter {

    /**
     * Constant <code>DEFAULT_DAYS_TO_LIVE=1461</code>
     */
    public static final int DEFAULT_DAYS_TO_LIVE = 1461; // 4 years
    /**
     * Constant <code>DEFAULT_SECONDS_TO_LIVE=TimeUnit.DAYS.toMillis(DEFAULT_DAYS_TO_LIVE)</code>
     */
    public static final long DEFAULT_SECONDS_TO_LIVE = TimeUnit.DAYS.toMillis(DEFAULT_DAYS_TO_LIVE);

    private long cacheTimeToLive = DEFAULT_SECONDS_TO_LIVE;

    private ManageProperties manageProperties;

    /**
     * <p>Constructor for CachingHttpHeadersFilter.</p>
     *
     * @param manageProperties a {@link ManageProperties} object.
     */
    public CachingHttpHeadersFilter(ManageProperties manageProperties) {
        this.manageProperties = manageProperties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        cacheTimeToLive = TimeUnit.DAYS.toMillis(manageProperties.getHttp().getCache().getTimeToLiveInDays());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        // Nothing to destroy
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.setHeader("Cache-Control", "max-age=" + cacheTimeToLive + ", public");
        httpResponse.setHeader("Pragma", "cache");

        // Setting Expires header, for proxy caching
        httpResponse.setDateHeader("Expires", cacheTimeToLive + System.currentTimeMillis());

        chain.doFilter(request, response);
    }
}
