
package home.stanislavpoliakov.meet12_practice;


interface SharedPrefInterface{
    String getFirstName();
    String getLastName();
    oneway void putFirstName(in String firstName);
    oneway void putLastName(in String lastName);
}
