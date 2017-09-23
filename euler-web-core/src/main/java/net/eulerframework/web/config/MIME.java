/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013-2017 cFrost.sun(孙宾, SUN BIN) 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 * For more information, please visit the following website
 * 
 * https://eulerproject.io
 * https://github.com/euler-form/web-form
 * https://cfrost.net
 */
package net.eulerframework.web.config;

import org.springframework.util.StringUtils;

import net.eulerframework.common.util.property.InvalidPropertyValueException;

/**
 * @author cFrost
 *
 */
public class MIME {
    private final static String SPLIT_CHAR = ";";
    
    private String contentType;
    private String contentDisposition;
    
    public MIME() {}
    
    public MIME(String configValue) {
        if(StringUtils.isEmpty(configValue) || configValue.indexOf(SPLIT_CHAR) < 0) {
            throw new InvalidPropertyValueException(configValue);
        }
        
        String[] configArray = configValue.split(SPLIT_CHAR);
        
        if(configArray.length < 2) {
            throw new InvalidPropertyValueException(configValue);
        }
        
        this.contentType = configArray[0];
        this.contentDisposition = configArray[1];
    }
    
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public String getContentDisposition() {
        return contentDisposition;
    }
    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }
    
    
}
