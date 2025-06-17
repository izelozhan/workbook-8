public class Shipper {
    private int shipperId;
    private String companyName;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;

    public Shipper(int shipperId, String companyName, String phone) {
        this.phone = phone;
        this.companyName = companyName;
        this.shipperId = shipperId;
    }

    public int getShipperId() {
        return shipperId;
    }

    public String getPhone() {
        return phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    @Override
    public String toString(){
        return "Shipper ID: " + this.shipperId + "\tCompany Name: " + this.companyName + "\tPhone: " + this.phone;
    }

}
