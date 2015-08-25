package se.inera.privatlakarportal.integration.terms.services.dto;

/**
 * Created by pebe on 2015-08-25.
 */
public class Terms {
    private String text;
    private int version;

    public Terms() {
    }

    public Terms(String text, Integer version) {
        this.text = text;
        this.version = version;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
