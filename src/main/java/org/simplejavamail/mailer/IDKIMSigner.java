package org.simplejavamail.mailer;

import org.simplejavamail.email.Email;

import javax.mail.internet.MimeMessage;

/**
 * This interface only serves to hide the DKIM implementation behind an easy-to-load-with-reflection class.
 */
public interface IDKIMSigner {

	MimeMessage signMessageWithDKIM(MimeMessage messageToSign, Email emailContainingSigningDetails);

}
