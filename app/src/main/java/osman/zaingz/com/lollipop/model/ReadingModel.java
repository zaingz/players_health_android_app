package osman.zaingz.com.lollipop.model;

/**
 * Created by Zain on 10/30/2014.
 */
public class ReadingModel {
    String key;
    String value;

    public ReadingModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
