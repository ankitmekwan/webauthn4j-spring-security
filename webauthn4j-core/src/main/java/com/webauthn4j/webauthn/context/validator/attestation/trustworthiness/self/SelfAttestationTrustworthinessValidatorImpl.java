/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webauthn4j.webauthn.context.validator.attestation.trustworthiness.self;

import com.webauthn4j.webauthn.attestation.statement.AttestationType;
import com.webauthn4j.webauthn.attestation.statement.WebAuthnAttestationStatement;
import com.webauthn4j.webauthn.exception.CertificateException;
import com.webauthn4j.webauthn.exception.SelfAttestationProhibitedException;

import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

/**
 * Validates {@link WebAuthnAttestationStatement} as self attestation
 */
public class SelfAttestationTrustworthinessValidatorImpl implements SelfAttestationTrustworthinessValidator {

    //~ Instance fields ================================================================================================
    private boolean isSelfAttestationAllowed = true;

    public void validate(WebAuthnAttestationStatement attestationStatement) {
        if (attestationStatement.getAttestationType() == AttestationType.Self) {
            throw new IllegalArgumentException("attestationStatement is not self attestedCredentialData");
        }
        if (isSelfAttestationAllowed()) {
            X509Certificate attestationCertificate = attestationStatement.getEndEntityCertificate();
            try {
                attestationCertificate.checkValidity();
            } catch (CertificateExpiredException e) {
                throw new CertificateException("Certificate expired", e);
            } catch (CertificateNotYetValidException e) {
                throw new CertificateException("Certificate not yet valid", e);
            }
        } else {
            throw new SelfAttestationProhibitedException("Self attestations is prohibited");
        }
    }

    public boolean isSelfAttestationAllowed() {
        return this.isSelfAttestationAllowed;
    }

    public void setSelfAttestationAllowed(boolean selfAttestationAllowed) {
        this.isSelfAttestationAllowed = selfAttestationAllowed;
    }
}