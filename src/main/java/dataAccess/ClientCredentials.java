
package dataAccess;

public class ClientCredentials {
    static final String API_KEY = "AIzaSyBriPAQuXOgUzrBah8sbnAKv_LL0mhZ3x4";

  static void errorIfNotSpecified() {
    if (API_KEY.startsWith("Enter ")) {
      System.err.println(API_KEY);
      System.exit(1);
    }
  }
}
