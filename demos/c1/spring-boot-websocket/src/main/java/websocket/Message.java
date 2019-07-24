package websocket;

public class Message {

    private String name;

    public Message() {
    }

    public Message(String text) {
        this.name = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
