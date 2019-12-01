package ariel.az.devcode20.Realm;

import io.realm.RealmObject;

public class LoginRealm extends RealmObject {
    private String email;
    private String token;
    private String password;

    public LoginRealm(){}

    public LoginRealm(String email, String token, String password) {
        this.email = email;
        this.token = token;
        this.password = password;
    }

}
