package counterfeiters.models;

/**
 * The model containing the rules.
 *
 * @author Robin van den Berg
 */

public class Rules {

    String page;

    public Rules(){}

    public String switchPage(int page)
    {

        switch(page) {
            case 1:
                this.page = "/background/rulePage1.jpg";
                break;
            case 2:
                this.page = "/background/rulePage2.jpg";
                break;
        }

        return this.page;

    }
}
