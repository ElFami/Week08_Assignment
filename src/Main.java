import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<Item> ListOfItems = new ArrayList<Item>();
    static ArrayList<Payment> ListOfPayments = new ArrayList<Payment>();
    static Scanner s = new Scanner(System.in);

    public static void seedData() {
        ListOfItems.add(new Item("Kulkas", "Electronic", 4800000));
        ListOfItems.add(new Item("TV", "Electronic", 1280000));
        ListOfItems.add(new Item("Laptop", "Computer", 6000000));
        ListOfItems.add(new Item("PC", "Computer", 12000000));
    }

    public static void printItem(Item item) {
        System.out.println("Nama   : " + item.getName());
        System.out.println("Tipe   : " + item.getType());
        System.out.println("Harga  : " + item.getPrice());
    }

    public static void main(String[] args) {
        int opt;
        seedData();

        do {
            System.out.println("----Program Toko Elektronik----");
            System.out.println("1. Pesan Barang");
            System.out.println("2. Lihat Pesanan");
            System.out.println("0. Keluar");
            System.out.print("Pilih : ");
            opt = s.nextInt();
            s.nextLine(); // clear newline

            if (opt == 1) {
                System.out.println("----Daftar Barang----");
                for (int i = 0; i < ListOfItems.size(); i++) {
                    System.out.println("No : " + (i + 1));
                    printItem(ListOfItems.get(i));
                    System.out.println("-------------------------------------");
                }

                System.out.print("Pilih : ");
                int pilih = s.nextInt();
                s.nextLine();

                if (pilih < 1 || pilih > ListOfItems.size()) {
                    System.out.println("Pilihan tidak valid.");
                    continue;
                }

                Item selectedItem = ListOfItems.get(pilih - 1);
                System.out.println("Nama : " + selectedItem.getName());
                System.out.println("Tipe : " + selectedItem.getType());
                System.out.println("Harga : " + selectedItem.getPrice());

                System.out.println("---Tipe Pembayaran---");
                System.out.println("1. Cash");
                System.out.println("2. Credit");
                System.out.print("Pilih : ");
                int tipe = s.nextInt();
                s.nextLine();

                if (tipe == 1) { // Cash
                    Payment payment = new Cash(selectedItem) {
                        @Override
                        public void processPayment() {
                            System.out.println("Processing cash payment...");
                        }
                    };

                    System.out.print("Bayar (Y/N): ");
                    String payNow = s.nextLine();

                    if (payNow.equalsIgnoreCase("Y")) {
                        int paid = payment.pay();
                        System.out.println("Harga Pembayaran : " + selectedItem.getPrice());
                        System.out.println("Bayar : " + paid);
                        System.out.println("Transaksi telah dibayar lunas");
                    } else {
                        System.out.println("Transaksi telah disimpan");
                    }

                    ListOfPayments.add(payment);

                } else if (tipe == 2) { // Credit
                    int cicilan = 0;
                    while (cicilan != 3 && cicilan != 6 && cicilan != 9 && cicilan != 12) {
                        System.out.print("Lama Cicilan (3/6/9/12): ");
                        cicilan = s.nextInt();
                        s.nextLine();
                    }

                    Payment payment = new Credit(selectedItem, cicilan) {
                        @Override
                        public void processPayment() {
                            System.out.println("Processing credit payment...");
                        }
                    };

                    int bayar = payment.pay();
                    System.out.println("Harga Pembayaran : " + bayar);
                    System.out.println("Bayar : " + bayar);
                    System.out.println("Transaksi telah dibayar");

                    ListOfPayments.add(payment);
                } else {
                    System.out.println("Tipe pembayaran tidak valid.");
                }

            } else if (opt == 2) {
                System.out.println("----Daftar Pesanan----");
                if (ListOfPayments.isEmpty()) {
                    System.out.println("Belum ada pesanan.");
                    continue;
                }

                for (int i = 0; i < ListOfPayments.size(); i++) {
                    Payment p = ListOfPayments.get(i);
                    System.out.println("No : " + (i + 1));
                    System.out.println("Nama : " + p.getItemName());
                    System.out.println("Tipe : " + p.getItem().getType());
                    System.out.println("Status : " + (p.isPaidOff() ? "FINISHED" : "ON PROGRESS"));
                    System.out.println("Sisa Pembayaran : " + p.getRemainingAmount());
                    System.out.println("---------------------------");
                }

                System.out.print("Pilih No Transaksi : ");
                int pilih = s.nextInt();
                s.nextLine();

                if (pilih < 1 || pilih > ListOfPayments.size()) {
                    System.out.println("Nomor transaksi tidak valid.");
                    continue;
                }

                Payment selectedPayment = ListOfPayments.get(pilih - 1);
                Item item = selectedPayment.getItem();

                System.out.println("Nama : " + item.getName());
                System.out.println("Tipe : " + item.getType());
                System.out.println("Status : " + (selectedPayment.isPaidOff() ? "FINISHED" : "ON PROGRESS"));
                System.out.println("Sisa Pembayaran : " + selectedPayment.getRemainingAmount());

                if (selectedPayment.isPaidOff()) {
                    System.out.println("Harga Pembayaran : 0");
                    System.out.println("Transaksi telah lunas");
                } else {
                    int bayar = selectedPayment.pay();
                    System.out.println("Harga Pembayaran : " + bayar);
                    System.out.println("Bayar : " + bayar);

                    if (selectedPayment.isPaidOff()) {
                        System.out.println("Transaksi telah dibayar lunas");
                    } else {
                        System.out.println("Transaksi telah dibayar sebagian");
                    }
                }

            } else if (opt == 0) {
                System.out.println("Terima Kasih");
            } else {
                System.out.println("Salah Input");
            }

        } while (opt != 0);
    }
}
