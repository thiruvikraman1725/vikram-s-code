import java.io.*;
import java.util.*;

class Room implements Serializable {
    int roomNumber;
    String type;
    double price;
    boolean isAvailable;

    public Room(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.isAvailable = true;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + type + ") - $" + price + " - " + (isAvailable ? "Available" : "Booked");
    }
}

class Booking implements Serializable {
    String guestName;
    int roomNumber;
    Date date;
    boolean isPaid;

    public Booking(String guestName, int roomNumber, boolean isPaid) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.date = new Date();
        this.isPaid = isPaid;
    }

    @Override
    public String toString() {
        return "Booking: " + guestName + " | Room " + roomNumber + " | " + (isPaid ? "Paid" : "Pending");
    }
}

class Hotel implements Serializable {
    ArrayList<Room> rooms = new ArrayList<>();
    ArrayList<Booking> bookings = new ArrayList<>();

    // Initialize with some sample rooms
    public Hotel() {
        rooms.add(new Room(101, "Standard", 100));
        rooms.add(new Room(102, "Deluxe", 180));
        rooms.add(new Room(103, "Suite", 250));
    }

    public void showAvailableRooms() {
        System.out.println("\n=== Available Rooms ===");
        for (Room r : rooms) {
            if (r.isAvailable) System.out.println(r);
        }
    }

    public void bookRoom(String guestName, int roomNumber) {
        for (Room r : rooms) {
            if (r.roomNumber == roomNumber && r.isAvailable) {
                System.out.println("Room found! Price: $" + r.price);
                boolean payment = Payment.makePayment(r.price);
                if (payment) {
                    r.isAvailable = false;
                    Booking booking = new Booking(guestName, roomNumber, true);
                    bookings.add(booking);
                    System.out.println("✅ Booking successful!");
                    saveData();
                    return;
                } else {
                    System.out.println("❌ Payment failed. Booking canceled.");
                    return;
                }
            }
        }
        System.out.println("❌ Room not available or doesn't exist.");
    }

    public void cancelBooking(String guestName) {
        Iterator<Booking> iterator = bookings.iterator();
        while (iterator.hasNext()) {
            Booking b = iterator.next();
            if (b.guestName.equalsIgnoreCase(guestName)) {
                iterator.remove();
                for (Room r : rooms) {
                    if (r.roomNumber == b.roomNumber) r.isAvailable = true;
                }
                System.out.println("✅ Booking canceled for " + guestName);
                saveData();
                return;
            }
        }
        System.out.println("❌ No booking found for " + guestName);
    }

    public void showBookings() {
        System.out.println("\n=== Current Bookings ===");
        for (Booking b : bookings) System.out.println(b);
    }

    // File I/O for saving and loading data
    public void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("hotelData.ser"))) {
            out.writeObject(this);
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    public static Hotel loadData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("hotelData.ser"))) {
            return (Hotel) in.readObject();
        } catch (Exception e) {
            return new Hotel(); // default if file not found
        }
    }
}

class Payment {
    public static boolean makePayment(double amount) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter card number to pay $" + amount + ": ");
        String card = sc.nextLine();
        return !card.isEmpty(); // simulate success if user enters anything
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel hotel = Hotel.loadData();

        while (true) {
            System.out.println("\n==== HOTEL RESERVATION MENU ====");
            System.out.println("1. Show available rooms");
            System.out.println("2. Book a room");
            System.out.println("3. Cancel a booking");
            System.out.println("4. View all bookings");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> hotel.showAvailableRooms();
                case 2 -> {
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter room number to book: ");
                    int roomNum = sc.nextInt();
                    sc.nextLine();
                    hotel.bookRoom(name, roomNum);
                }
                case 3 -> {
                    System.out.print("Enter your name to cancel: ");
                    String name = sc.nextLine();
                    hotel.cancelBooking(name);
                }
                case 4 -> hotel.showBookings();
                case 5 -> {
                    System.out.println("Thank you! Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
