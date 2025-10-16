package com.amaris.pages;


import com.amaris.base.BasePage;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

public class CreditCardPage extends BasePage {
    private final Locator CardNumber;
    private final Locator CardExpiring;
    private final Locator CardCVV;
    private final Locator PayButton;

    public CreditCardPage(Page page) {
        super(page);
    
        CardNumber = page.locator("//*[@id='card-number']");
        CardExpiring = page.locator("//*[@id=\"card-expiration\"]") ;
        CardCVV = page.locator("//*[@id=\"card-cvv\"]");
        PayButton = page.locator("//*[@id=\"divImgAceptar\"]");
    }

    public void Pay(String CardN, String ExpiringD, String CVV){
        Locator payWithCardTitle = page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Pagar con Tarjeta"));
        payWithCardTitle.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        CardExpiring.fill(ExpiringD);
        CardCVV.fill(CVV);
        CardNumber.fill(CardN);
        
    }

    public boolean isAcceptButtonEnabled() {
    PayButton.waitFor(new Locator.WaitForOptions().setTimeout(5000));
    return PayButton.isEnabled();
}

}
