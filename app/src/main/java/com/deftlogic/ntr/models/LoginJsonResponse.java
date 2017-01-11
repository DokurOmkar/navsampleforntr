package com.deftlogic.ntr.models;

import java.util.List;

/**
 * Created by omkardokur on 2/10/16.
 */
public class LoginJsonResponse {
    /**
     * customer_id : 1581
     * firstname : Manavendranadh Roy
     * lastname : Ancha
     * phone : 9703467894
     * email : manu@deftlogic.com
     * image_url : null
     * addresses : [{"address_id":1541,"address_type":"DELIVERY","address1":"Viveka college of education, vaddesangam","address2":"Chirala","city":"Chirala","state":"AP","zip":523157}]
     */

    private int customer_id;
    private String firstname;
    private String lastname;
    private long phone;
    private String email;
    private Object image_url;
    /**
     * address_id : 1541
     * address_type : DELIVERY
     * address1 : Viveka college of education, vaddesangam
     * address2 : Chirala
     * city : Chirala
     * state : AP
     * zip : 523157
     */

    private List<AddressesEntity> addresses;

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImage_url(Object image_url) {
        this.image_url = image_url;
    }

    public void setAddresses(List<AddressesEntity> addresses) {
        this.addresses = addresses;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public long getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Object getImage_url() {
        return image_url;
    }

    public List<AddressesEntity> getAddresses() {
        return addresses;
    }

    public static class AddressesEntity {
        private int address_id;
        private String address_type;
        private String address1;
        private String address2;
        private String city;
        private String state;
        private int zip;

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }

        public void setAddress_type(String address_type) {
            this.address_type = address_type;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setZip(int zip) {
            this.zip = zip;
        }

        public int getAddress_id() {
            return address_id;
        }

        public String getAddress_type() {
            return address_type;
        }

        public String getAddress1() {
            return address1;
        }

        public String getAddress2() {
            return address2;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public int getZip() {
            return zip;
        }
    }
}
