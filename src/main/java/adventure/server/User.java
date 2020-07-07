package adventure.server;

public class User {
    private String username;
    private String password;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
