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
package net.eulerframework.web.module.authentication.util;

import net.eulerframework.web.module.authentication.conf.SecurityConfig;
import net.eulerframework.web.module.authentication.exception.UserInfoCheckWebException;
import net.eulerframework.web.module.authentication.exception.UserNotFoundException;
import net.eulerframework.web.module.authentication.service.EulerUserEntityService;

/**
 * @author cFrost
 *
 */
public abstract class UserDataValidator {

    private static EulerUserEntityService eulerUserEntityService;
    
    public static void setUserDetailsServicel(EulerUserEntityService eulerUserEntityService) {
        UserDataValidator.eulerUserEntityService = eulerUserEntityService;
    }
    
    public static void validUsername(String username) throws UserInfoCheckWebException {
        if (username == null) {
            throw new UserInfoCheckWebException("_USERNAME_IS_NULL");
        }
        if (!(username.matches(SecurityConfig.getUsernameFormat()))) {
            throw new UserInfoCheckWebException("_INCORRECT_USERNAME_FORMAT");
        }
        
        try {
            eulerUserEntityService.loadUserByUsername(username);
            /*
             * Program running here means this username has already been used!
             */
            throw new UserInfoCheckWebException("_USERNAME_ALREADY_BE_USED");
        } catch (UserNotFoundException e) {
            /*
             * User not found means this username has not been used!
             */
        }
    }

    public static void validEmail(String email) throws UserInfoCheckWebException {
        if (email == null) {
            throw new UserInfoCheckWebException("_EMAIL_IS_NULL");
        }
        if (!(email.matches(SecurityConfig.getEmailFormat()))) {
            throw new UserInfoCheckWebException("_INCORRECT_EMAIL_FORMAT");
        }
        try {
            eulerUserEntityService.loadUserByEmail(email);
            /*
             * Program running here means this email has already been used!
             */
            throw new UserInfoCheckWebException("_EMAIL_ALREADY_BE_USED");
        } catch (UserNotFoundException e) {
            /*
             * User not found means this email has not been used!
             */
        }
    }

    public static void validMobile(String mobile) throws UserInfoCheckWebException {
        if (mobile != null) {
            try {
                eulerUserEntityService.loadUserByMobile(mobile);
                /*
                 * Program running here means this mobile has already been used!
                 */
                throw new UserInfoCheckWebException("_MOBILE_ALREADY_BE_USED");
            } catch (UserNotFoundException e) {
                /*
                 * User not found means this mobile has not been used!
                 */
            }
        }
    }

    public static void validPassword(String password) throws UserInfoCheckWebException {
        if (password == null) {
            throw new UserInfoCheckWebException("_PASSWORD_IS_NULL");
        }
        if (!password.matches(SecurityConfig.getPasswordFormat())) {
            throw new UserInfoCheckWebException("_INCORRECT_PASSWORD_FORMAT");
        }
        if (!(password.length() >= SecurityConfig.getMinPasswordLength()
                && password.length() <= SecurityConfig.getMaxPasswordLength())) {
            throw new UserInfoCheckWebException("_INCORRECT_PASSWORD_LENGTH");
        }
    }

}
