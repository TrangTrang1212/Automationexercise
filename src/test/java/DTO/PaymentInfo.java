package DTO;

public class PaymentInfo {
    private String nameCard;
    private String numberCard;
    private String cvc;
    private String expiration;
    private String year;
    public PaymentInfo(String nameCard, String numberCard, String cvc, String expiration, String year){

        this.nameCard = nameCard;
        this.numberCard = numberCard;
        this.cvc = cvc;
        this.expiration = expiration;
        this.year = year;
    }
    public String getNameCard() { return nameCard; }
    public void setNameCard(String nameCard) { this.nameCard = nameCard; }
    public String getNumberCard(){ return numberCard; }
    public void setNumberCard(String numberCard){ this.numberCard = numberCard; }
    public String getCvc(){
        return cvc;
    }
    public void setCvc(String cvc) {
        this.cvc = cvc;
    }
    public String getExpiration(){
        return expiration;
    }
    public void setExpiration(String expiration){
        this.expiration = expiration;
    }
    public String getYear(){
        return year;
    }
    public void setYear(){
        this.year = year;
    }
}
