package com.ideaas.web.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.client.util.store.FileDataStoreFactory;
//import com.google.api.services.drive.Drive;
//import com.google.api.services.drive.DriveScopes;
//import com.google.api.services.drive.model.File;
//import com.google.api.services.drive.model.FileList;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.script.Script;
import com.google.api.services.script.model.*;
import com.ideaas.web.services.interfaces.GoogleFormService;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class GoogleFormServiceImpl implements GoogleFormService {

    private static final String APPLICATION_NAME = "Apps Script API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved credentials folder at /secret.
     */
    private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/script.projects");
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
//     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GoogleFormServiceImpl.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }


    @Override
    public void main() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Script service = new Script.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        Script.Projects projects = service.projects();

        // Creates a new script project.
        Project createOp = projects.create(new CreateProjectRequest().setTitle("My Script")).execute();

        // Uploads two files to the project.
        File file1 = new File()
                .setName("hello")
                .setType("SERVER_JS")
                .setSource("function helloWorld() {\n  console.log(\"Hello, world!\");\n}");
        File file2 = new File()
                .setName("appsscript")
                .setType("JSON")
                .setSource("{\"timeZone\":\"America/New_York\",\"exceptionLogging\":\"CLOUD\"}");
        Content content = new Content().setFiles(Arrays.asList(file1, file2));
        Content updatedContent = projects.updateContent(createOp.getScriptId(), content).execute();

        // Logs the project URL.
        System.out.printf("https://script.google.com/d/%s/edit\n", updatedContent.getScriptId());


    }

    /**
     * Create a HttpRequestInitializer from the given one, except set
     * the HTTP read timeout to be longer than the default (to allow
     * called scripts time to execute).
     *
     * @param {HttpRequestInitializer} requestInitializer the initializer
     *     to copy and adjust; typically a Credential object.
     * @return an initializer with an extended read timeout.
     */
    private static HttpRequestInitializer setHttpTimeout(
            final HttpRequestInitializer requestInitializer) {
        return new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {
                requestInitializer.initialize(httpRequest);
                // This allows the API to call (and avoid timing out on)
                // functions that take up to 6 minutes to complete (the maximum
                // allowed script run time), plus a little overhead.
                httpRequest.setReadTimeout(380000);
            }
        };
    }

    /**
     * Build and return an authorized Script client service.
     *
     * @param {Credential} credential an authorized Credential object
     * @return an authorized Script client service
     */
    public static Script getScriptService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = getCredentials(HTTP_TRANSPORT);
        return new Script.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, setHttpTimeout(credential))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Interpret an error response returned by the API and return a String
     * summary.
     *
     * @param {Operation} op the Operation returning an error response
     * @return summary of error response, or null if Operation returned no
     *     error
     */
    public static String getScriptError(Operation op) {
        if (op.getError() == null) {
            return null;
        }

        // Extract the first (and only) set of error details and cast as a Map.
        // The values of this map are the script's 'errorMessage' and
        // 'errorType', and an array of stack trace elements (which also need to
        // be cast as Maps).
        Map<String, Object> detail = op.getError().getDetails().get(0);
        List<Map<String, Object>> stacktrace =
                (List<Map<String, Object>>)detail.get("scriptStackTraceElements");

        java.lang.StringBuilder sb =
                new StringBuilder("\nScript error message: ");
        sb.append(detail.get("errorMessage"));
        sb.append("\nScript error type: ");
        sb.append(detail.get("errorType"));

        if (stacktrace != null) {
            // There may not be a stacktrace if the script didn't start
            // executing.
            sb.append("\nScript error stacktrace:");
            for (Map<String, Object> elem : stacktrace) {
                sb.append("\n  ");
                sb.append(elem.get("function"));
                sb.append(":");
                sb.append(elem.get("lineNumber"));
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public void executeCreateForm()  throws IOException, GeneralSecurityException {
        // ID of the script to call. Acquire this from the Apps Script editor,
        // under Publish > Deploy as API executable.
        String scriptId = "16XWj2Gux8_wVvxDpRMI_NA0XyoIIKP04dwZzf8EzqBgNtRjeSiErvjKf";
        Script service = getScriptService();

        // Create an execution request object.
        ExecutionRequest request = new ExecutionRequest()
                .setFunction("createForm");

        try {
            // Make the API request.
            Operation op =
                    service.scripts().run(scriptId, request).execute();

            // Print results of request.
            if (op.getError() != null) {
                // The API executed, but the script returned an error.
                System.out.println(getScriptError(op));
            } else {
                String mylist = (String)(op.getResponse().get("result"));
                System.out.println(mylist);

//                Map<String, String> folderSet = (Map<String, String>)(op.getResponse().get("result"));
//                if (folderSet.size() == 0) {
//                    System.out.println("No folders returned!");
//                } else {
//                    System.out.println("Folders under your root folder:");
//                    for (String id: folderSet.keySet()) {
//                        System.out.printf(
//                                "\t%s (%s)\n", folderSet.get(id), id);
//                    }
//                }
            }
        } catch (GoogleJsonResponseException e) {
            // The API encountered a problem before the script was called.
            e.printStackTrace(System.out);
        }
    }
}
