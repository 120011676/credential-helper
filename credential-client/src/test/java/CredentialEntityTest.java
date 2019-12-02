import com.github.qq120011676.credential.common.entity.CredentialViewEntity;
import com.google.gson.Gson;

public class CredentialEntityTest {
    public static void main(String[] args) {
        CredentialViewEntity credentialView = new CredentialViewEntity();
        credentialView.setToken("1");
        Gson gson = new Gson();
        String json = gson.toJson(credentialView);
        System.out.println(json);
        CredentialViewEntity c = gson.fromJson(json, CredentialViewEntity.class);
        System.out.println(c);
    }
}
