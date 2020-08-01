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

package com.webauthn4j.springframework.security.endpoint;

import com.webauthn4j.data.PublicKeyCredentialDescriptor;
import com.webauthn4j.data.PublicKeyCredentialParameters;
import com.webauthn4j.data.PublicKeyCredentialRpEntity;
import com.webauthn4j.data.PublicKeyCredentialUserEntity;
import com.webauthn4j.data.client.challenge.Challenge;
import com.webauthn4j.data.extension.client.AuthenticationExtensionsClientInputs;
import com.webauthn4j.data.extension.client.RegistrationExtensionClientInput;
import com.webauthn4j.util.CollectionUtil;

import java.util.List;

/**
 * Success response of {@link AttestationOptionsEndpointFilter}
 */
public class AttestationOptionsResponse implements Response {

    // ~ Instance fields
    // ================================================================================================
    private final PublicKeyCredentialRpEntity relyingParty;
    private final PublicKeyCredentialUserEntity user;
    private final Challenge challenge;
    private final List<PublicKeyCredentialParameters> pubKeyCredParams;
    private final Long timeout;
    private final List<PublicKeyCredentialDescriptor> credentials;
    private final AuthenticationExtensionsClientInputs<RegistrationExtensionClientInput> extensions;

    // ~ Constructors
    // ===================================================================================================

    @SuppressWarnings("squid:S00107")
    public AttestationOptionsResponse(
            PublicKeyCredentialRpEntity relyingParty,
            PublicKeyCredentialUserEntity user,
            Challenge challenge,
            List<PublicKeyCredentialParameters> pubKeyCredParams,
            Long timeout,
            List<PublicKeyCredentialDescriptor> credentials,
            AuthenticationExtensionsClientInputs<RegistrationExtensionClientInput> extensions) {
        super();

        this.relyingParty = relyingParty;
        this.user = user;
        this.challenge = challenge;
        this.pubKeyCredParams = CollectionUtil.unmodifiableList(pubKeyCredParams);
        this.timeout = timeout;
        this.credentials = CollectionUtil.unmodifiableList(credentials);
        this.extensions = extensions;
    }

    // ~ Methods
    // ========================================================================================================


    public PublicKeyCredentialRpEntity getRelyingParty() {
        return this.relyingParty;
    }

    public PublicKeyCredentialUserEntity getUser() {
        return this.user;
    }

    public Challenge getChallenge() {
        return this.challenge;
    }

    public List<PublicKeyCredentialParameters> getPubKeyCredParams() {
        return this.pubKeyCredParams;
    }

    public Long getTimeout() {
        return this.timeout;
    }

    public List<PublicKeyCredentialDescriptor> getCredentials() {
        return this.credentials;
    }

    public AuthenticationExtensionsClientInputs<RegistrationExtensionClientInput> getExtensions() {
        return this.extensions;
    }


    @Override
    public String getErrorMessage() {
        return null;
    }
}