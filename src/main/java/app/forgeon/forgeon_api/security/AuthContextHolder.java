package app.forgeon.forgeon_api.security;

public class AuthContextHolder {

    private static final ThreadLocal<AuthContext> CONTEXT = new ThreadLocal<>();

    public static void set(AuthContext auth) {
        CONTEXT.set(auth);
    }

    public static AuthContext get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
