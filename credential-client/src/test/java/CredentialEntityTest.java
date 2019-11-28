import com.github.qq120011676.credential.common.entity.CredentialEntity;
import com.google.gson.Gson;

import java.time.Duration;

public class CredentialEntityTest {
    public static void main(String[] args) {
        CredentialEntity credential = new CredentialEntity();
        credential.setToken("1");
        credential.setTimeout(Duration.ofSeconds(5));
        Gson gson = new Gson();
        String json = gson.toJson(credential);
        System.out.println(json);
        CredentialEntity c = gson.fromJson(json, CredentialEntity.class);
        System.out.println(c);
    }
}
