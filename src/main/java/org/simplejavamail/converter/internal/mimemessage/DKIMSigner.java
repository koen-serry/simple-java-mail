package org.simplejavamail.converter.internal.mimemessage;

import net.markenwerk.utils.mail.dkim.DkimMessage;
import net.markenwerk.utils.mail.dkim.DkimSigner;
import org.simplejavamail.email.Email;
import org.simplejavamail.mailer.IDKIMSigner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * This class only serves to hide the DKIM implementation behind an easy-to-load-with-reflection class.
 */
@SuppressWarnings("unused") // it is ued through reflection
public class DKIMSigner implements IDKIMSigner {

	private final DkimSigner dkimSigner;

	public DKIMSigner(String signingDomain, String selector, File derFile) {
		try{
			this.dkimSigner = new DkimSigner(signingDomain,selector,derFile);
		}
		catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e){
			throw new RuntimeException(MimeMessageParseException.ERROR_LOADING_DKIM_LIBRARY, e);
		}
	}

	public DKIMSigner(String signingDomain, String selector, InputStream derStream)  {
		try{
			this.dkimSigner = new DkimSigner(signingDomain,selector,derStream);
		}
		catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e){
			throw new RuntimeException(MimeMessageParseException.ERROR_LOADING_DKIM_LIBRARY, e);
		}
	}

	public DKIMSigner(DkimSigner dkimSigner){
		this.dkimSigner = dkimSigner;
	}
	
	/**
	 * @see MimeMessageHelper#signMessageWithDKIM(MimeMessage, Email)
	 */
	public MimeMessage signMessageWithDKIM(final MimeMessage messageToSign, final Email emailContainingSigningDetails) {
		try {
			return new DkimMessage(messageToSign, dkimSigner);
		} catch (MessagingException e) {
			throw new MimeMessageParseException(MimeMessageParseException.ERROR_SIGNING_DKIM_INVALID_DOMAINKEY, e);
		}
	}
}
