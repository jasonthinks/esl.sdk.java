package com.silanis.esl.sdk.examples;

import com.silanis.esl.sdk.DocumentPackage;
import com.silanis.esl.sdk.EslClient;
import com.silanis.esl.sdk.PackageId;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static com.silanis.esl.sdk.builder.DocumentBuilder.newDocumentWithName;
import static com.silanis.esl.sdk.builder.PackageBuilder.newPackageNamed;
import static com.silanis.esl.sdk.builder.SignatureBuilder.signatureFor;
import static com.silanis.esl.sdk.builder.SignerBuilder.newSignerWithEmail;
/**
 * 
 * Example of how to configure the SMS authentication method for a signer
 *
 */

public class SignerSMSAuthenticationExample {

    private static final Properties props = Props.get();
    public static final String API_KEY = props.getProperty( "api.key" );
    public static final String API_URL = props.getProperty( "api.url" );

    private static final SimpleDateFormat format = new SimpleDateFormat( "HH:mm:ss" );

    public static void main( String... args ) {
        EslClient eslClient = new EslClient( API_KEY, API_URL );
        DocumentPackage smsExamplePackage = newPackageNamed("Policy " + format.format(new Date()))
                .describedAs("This is a SMS authentication example")
                .withSigner(newSignerWithEmail(props.getProperty("1.email"))
                        .withFirstName("John")
                        .withLastName("Smith")
                        .withSmsSentTo(props.getProperty("1.sms")))
                .withDocument(newDocumentWithName("First Document")
                        .fromFile("src/main/resources/document.pdf")
                        .withSignature(signatureFor(props.getProperty("1.email"))
                                .onPage(0)
                                .atPosition(100, 100)))
                .build();

        PackageId packageId = eslClient.createPackage( smsExamplePackage );

        eslClient.sendPackage( packageId );
    }
}