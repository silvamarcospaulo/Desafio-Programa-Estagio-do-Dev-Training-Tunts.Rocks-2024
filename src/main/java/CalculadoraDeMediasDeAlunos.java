import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class CalculadoraDeMediasDeAlunos{
  private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static final String TOKENS_DIRECTORY_PATH = "tokens";
  private static final List<String> SCOPES =
      Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
  private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
      throws IOException {
    // Load client secrets.
    InputStream in = CalculadoraDeMediasDeAlunos.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
    if (in == null) {
      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
    }
    GoogleClientSecrets clientSecrets =
        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,
            Arrays.asList(SheetsScopes.SPREADSHEETS))
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build();

    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
  }

  public static void main(String... args) throws IOException, GeneralSecurityException {
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    final String spreadsheetId = "1ds4Y2QuyetY5u2jRjS8QKJ5hgUies1yqTEMKJBpSstk";
    final String range = "A4:H27";
    Sheets service =
        new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build();
    ValueRange response = service.spreadsheets().values()
        .get(spreadsheetId, range)
        .execute();

    List<List<Object>> values = response.getValues();

    if (values == null || values.isEmpty()) {
      System.out.println("No data found.");
    } else {
      String situation = "";
      List<List<Object>> updatedValues = new ArrayList<>();

      for (List row : values) {
        if((((Integer.valueOf((String) row.get(2))) * 100)/60) > 25){
          situation = "Reprovado por Falta";
        } else if((((Integer.valueOf((String) row.get(3))) + (Integer.valueOf((String) row.get(4))) + (Integer.valueOf((String) row.get(5))))/30) >= 7){
          situation = "Aprovado";
        }else if((((Integer.valueOf((String) row.get(3))) + (Integer.valueOf((String) row.get(4))) + (Integer.valueOf((String) row.get(5))))/30) >= 5){
          situation = "Exame Final";
        }else{
          situation = "Reprovado por Nota";
        }

        List<Object> updatedRow = null;

        if ("Exame Final".equals(situation)){
          int naf = (int) Math.ceil(10 - (((Double.valueOf((String) row.get(3))) + (Double.valueOf((String) row.get(4))) + (Double.valueOf((String) row.get(5))))/30));
          updatedRow = Arrays.asList(
                  situation,
                  naf
          );

        }else{
          updatedRow = Arrays.asList(situation, 0);
        }

        updatedValues.add(updatedRow);
      }
      writeResultsToSheet(service, spreadsheetId, updatedValues);
    }
  }

  private static void writeResultsToSheet(Sheets service, String spreadsheetId, List<List<Object>> values) throws IOException {
    ValueRange body = new ValueRange()
            .setValues(values);

    UpdateValuesResponse result = service.spreadsheets().values()
            .update(spreadsheetId, "G4:H27", body)
            .setValueInputOption("RAW")
            .execute();
  }
}