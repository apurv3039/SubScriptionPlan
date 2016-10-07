package com.braintreeimplementation.braintreeimplementation;

/**
 * Created by User on 06/10/2016.
 */
public class SubscriptionData {

    String amount;
    String period;
    String plan;
    String discription;
    String active;
    String signinexpire;
    String buynow;
    String isRecurring;
    String transactionId;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getisRecurring() {
        return isRecurring;
    }

    public void setisRecurring(String isRecurring) {
        this.isRecurring = isRecurring;
    }
    public String gettransactionId() {
        return transactionId;
    }

    public void settransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getSigninexpire() {
        return signinexpire;
    }

    public void setSigninexpire(String signinexpire) {
        this.signinexpire = signinexpire;
    }

    public String getBuynow() {
        return buynow;
    }

    public void setBuynow(String buynow) {
        this.buynow = buynow;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    String currency;

    public String getSubscriptionID() {
        return subscriptionID;
    }

    public void setSubscriptionID(String subscriptionID) {
        this.subscriptionID = subscriptionID;
    }

    String subscriptionID;
}
