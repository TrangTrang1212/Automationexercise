package DTO;

public class AccountInfo {
    private String value;
    private String password;
    private String day;
    private String month;
    private String year;
    private String newsletter;
    private String optin;
    private String nameF;
    private String nameL;
    private String address;
    private String country;
    private String state;
    private String city;
    private String zipcode;
    private String phone;
    public AccountInfo(String value, String password, String day, String month, String year, String newsletter, String optin, String nameF, String nameL, String address, String country, String state, String city, String zipcode, String phone){
        this.value = value;
        this.password = password;
        this.day = day;
        this.month = month;
        this.year = year;
        this.newsletter = newsletter;
        this.optin = optin;
        this.nameF = nameF;
        this.nameL = nameL;
        this.address = address;
        this.country = country;
        this.state = state;
        this.city = city;
        this.zipcode = zipcode;
        this.phone = phone;
    }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }
    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }
    public String getNewsletter() { return newsletter; }
    public void setNewsletter(String newsletter) { this.newsletter = newsletter; }
    public String getOptin() { return optin; }
    public void setOptin(String optin) { this.optin = optin; }
    public String getFirstName() { return nameF; }
    public void setFirstName(String nameF) { this.nameF = nameF; }
    public String getLastName() { return nameL; }
    public void setLastName(String nameL) { this.nameL = nameL; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getZipcode() { return zipcode; }
    public void setZipcode(String zipcode) { this.zipcode = zipcode; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}

