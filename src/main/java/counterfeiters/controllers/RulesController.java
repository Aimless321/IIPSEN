package counterfeiters.controllers;

import counterfeiters.models.Rules;

/**
 * The link between the rules model and the rules view
 *
 * @author Robin van den Berg.
 */

public class RulesController {
    private ApplicationController app;
    public Rules rules;

    public RulesController(ApplicationController applicationController){
        this.app = applicationController;
        rules = new Rules();
    }

    public String nextButtonPressed() { return rules.switchPage(2); }


    public void closeButtonPressed()
    {

    }

    public String previousButtonPressed()
    {
        return rules.switchPage(1);
    }

}
