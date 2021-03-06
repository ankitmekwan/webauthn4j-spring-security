/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webauthn4j.springframework.security.webauthn.sample.app.api;

import com.webauthn4j.springframework.security.WebAuthnAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthController {


    @Autowired
    private AuthenticationTrustResolver trustResolver;

    @RequestMapping("/status")
    public AuthResponse status() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthStatus status;
        if (authentication == null || trustResolver.isAnonymous(authentication)) {
            status = AuthStatus.NOT_AUTHENTICATED;
        } else if (WebAuthnAuthenticationToken.class.isAssignableFrom(authentication.getClass()) ||
                authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("SINGLE_FACTOR_AUTHN_ALLOWED"))) {
            status = AuthStatus.AUTHENTICATED;
        } else {
            status = AuthStatus.AUTHENTICATING;
        }
        return new AuthResponse(status);
    }

}
