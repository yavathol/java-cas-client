/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/index.html
 */
package org.jasig.cas.client.tomcat.v6;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Request;
import org.jasig.cas.client.util.CommonUtils;

import java.util.regex.Pattern;

/**
 * Matches a number of urls (based on the regular expression) for handling
 * log out.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 3.1.12
 */
public final class RegExpBasedLogoutValue extends AbstractLogoutValve {

    private String regexpUri;

    private Pattern regexpUriPattern;

    private String redirectUrl;

    public void setRegexpUri(final String regexpUri) {
        this.regexpUri = regexpUri;
    }

    public void setRedirectUrl(final String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public void start() throws LifecycleException {
        super.start();

        try {
            CommonUtils.assertNotNull(this.regexpUri, "A Regular Expression must be provided.");

            this.regexpUriPattern = Pattern.compile(this.regexpUri);
        } catch (final Exception e) {
            throw new LifecycleException(e);
        }
    }

    protected boolean isLogoutRequest(final Request request) {
        return this.regexpUriPattern.matcher(request.getRequestURI()).matches();
    }

    protected String constructRedirectUrl(final Request request) {
        return this.redirectUrl;
    }
}
