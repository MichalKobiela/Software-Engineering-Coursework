package uk.ac.ed.bikerental;

public class Customer {
    private String firstName;
    private String lastName;
    private String address;
    private String postCode;
    private String phoneNumber;
    private String email;
    private String password;
    
    public Customer(String firstName, String lastName, String address, String postCode, String phoneNumber,
            String email, String password) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postCode = postCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }
    
    
    
    
}
