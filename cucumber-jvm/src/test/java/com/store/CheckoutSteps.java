package com.store;

import com.store.CashRegister;
import cucumber.api.java.en.*;

import static org.junit.Assert.assertEquals;

/**
 * Steps pertaining to checking out with a cash register.
 *
 * It's generally encouraged to make separate steps classes for each part of the domain.  For example if we added a
 * warehouse to our store, it would follow that we create a `WarehouseSteps` class for the various shipping, receiving,
 * and stock behaviors that are likely to take place.
 */
public class CheckoutSteps {
    //The class under test.  Making it here is fine for a small test in a small project, but consider making a
    //CashRegisterHelper class when cash registers get start getting used in multiple steps classes.
    //For example, if we added a CustomerServiceDesk and corresponding CustomerServiceSteps, that would be a good time
    //to extract anything about cash registers to CashRegisterHelper.
    private CashRegister cashRegister = new CashRegister();

    @Given("^the price of a \"([^\"]*)\" is (\\d+)c$")
    public void thePriceOfAIsC(String itemName, int priceInCents) throws Throwable {
        cashRegister.setPrice(itemName, priceInCents);
    }

    @When("^I checkout (\\d+) \"([^\"]*)\"$")
    public void iCheckout(int numberOf, String itemName) throws Throwable {
        cashRegister.checkout(numberOf, itemName);
    }

    @Then("^the total price should be (\\d+)c$")
    public void theTotalPriceShouldBeC(int totalInCents) throws Throwable {
        assertEquals(totalInCents, cashRegister.getTotalInCents());
    }
}
