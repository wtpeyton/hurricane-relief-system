package com.location;


public class Address {
    private String street;
    private String city;
    private String state;
    private String zip;

    public Address(String street, String city, String state, String zip){
        this.street = street;
        this. city = city;
        this.state = state;
        this.zip = zip;
    }

    /**
     * Builds an Address from a single line such as
     * "701 Sumter Street, Columbia, SC 29208" (a comma may also separate the
     * state and ZIP code).
     * @param text the address text, separated by commas
     * @return the parsed Address
     * @throws IllegalArgumentException if the text is not in that format
     */
    public static Address parse(String text) {
        String format = "Enter the address as: Street, City, State ZIP";
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(format);
        }

        String[] parts = text.split(",");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        String street = parts[0];
        String city;
        String state;
        String zip;

        if (parts.length == 3) {
            String[] stateAndZip = parts[2].split("[ \\t]+");
            if (stateAndZip.length != 2) {
                throw new IllegalArgumentException(format);
            }
            city = parts[1];
            state = stateAndZip[0];
            zip = stateAndZip[1];
        } else if (parts.length == 4) {
            city = parts[1];
            state = parts[2];
            zip = parts[3];
        } else {
            throw new IllegalArgumentException(format);
        }

        if (street.isEmpty() || city.isEmpty() || state.isEmpty() || zip.isEmpty()) {
            throw new IllegalArgumentException(format);
        }
        return new Address(street, city, state, zip);
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public Coordinates getCoordinates() {
        try {
            return MapTools.getInstance().getCoordinates(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Address)) {
            return false;
        }
        return toString().equalsIgnoreCase(other.toString());
    }

    @Override
    public int hashCode() {
        return toString().toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s %s", street, city, state, zip);
    }
}