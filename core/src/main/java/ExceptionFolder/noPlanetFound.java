package ExceptionFolder;

public class noPlanetFound extends RuntimeException {
    public noPlanetFound(String message) {
        super(message);
    }
}
