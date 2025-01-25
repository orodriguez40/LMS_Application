//Patron Class:
// Used to define what a kind of information a patron will contain.
//It will be called by the PatronManaging class to store, view, or remove patrons.

class Patron {

    //Attributes
    private int id;
    private String name;
    private String address;
    private double amountOwed;

    //Constructor
    public Patron(int id, String name, String address, double amountOwed) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.amountOwed = amountOwed;
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getAmountOwed() {
        return amountOwed;
    }


    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public void setAmountOwed(double amountOwed) {
        this.amountOwed = amountOwed;
    }

    //toString method is overwritten to display patron information.
    @Override
    public String toString() {
        return "\nID: " + id + "\nName: " + name + "\nAddress: " + address + "\nAmount Owed: " + amountOwed;
    }
}



