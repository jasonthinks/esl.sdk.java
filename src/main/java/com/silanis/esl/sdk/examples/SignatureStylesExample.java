package com.silanis.esl.sdk.examples;

import com.silanis.esl.sdk.DocumentPackage;
import com.silanis.esl.sdk.EslClient;
import com.silanis.esl.sdk.PackageId;

import java.util.Properties;

import static com.silanis.esl.sdk.builder.DocumentBuilder.newDocumentWithName;
import static com.silanis.esl.sdk.builder.PackageBuilder.newPackageNamed;
import static com.silanis.esl.sdk.builder.SignatureBuilder.captureFor;
import static com.silanis.esl.sdk.builder.SignatureBuilder.initialsFor;
import static com.silanis.esl.sdk.builder.SignatureBuilder.signatureFor;
import static com.silanis.esl.sdk.builder.SignerBuilder.newSignerWithEmail;
/**
 * Demonstrating the 3 options a signer can have when signing a document
 */
public class SignatureStylesExample {

    private static final Properties props = Props.get();
    public static final String API_KEY = props.getProperty( "api.key" );
    public static final String API_URL = props.getProperty( "api.url" );

    public static void main( String... args ) {
        EslClient eslClient = new EslClient( API_KEY, API_URL );

        DocumentPackage superDuperPackage = newPackageNamed( "Sample Insurance policy" )
                .withSigner(newSignerWithEmail(props.getProperty("1.email"))
                        .withFirstName("John")
                        .withLastName("Smith"))
                .withDocument(newDocumentWithName("First Document")
                        .fromFile("src/main/resources/document.pdf")
                        .withSignature(signatureFor(props.getProperty("1.email"))
                                .onPage(0)
                                .atPosition(500, 100))
                        .withSignature(initialsFor(props.getProperty("1.email"))
                                .onPage(0)
                                .atPosition(500, 300))
                        .withSignature(captureFor(props.getProperty("1.email"))
                                .onPage(0)
                                .atPosition(500, 500)))
                .build();

        PackageId packageId = eslClient.createPackage( superDuperPackage );
        eslClient.sendPackage( packageId );
    }
}
