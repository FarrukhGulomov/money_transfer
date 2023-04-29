package dev.farruh;

import dev.farruh.model.DatabaseService;
import dev.farruh.model.User;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int c = 0;
        System.out.println("0=>Ro'yxatdan o'tish\n1=>Balans\n2=>Balansni to'ldirish\n3=>Kartadan kartaga o'tkazma\n" +
                "4=>chiqish");
        User user = new User();
        DatabaseService dbService = new DatabaseService();
        while (c != 4) {
            c = input.nextInt();
            switch (c) {
                case 0:
                    input = new Scanner(System.in);
                    System.out.println("Ism familyangizni kiriting:");
                    String name = input.nextLine();
                    System.out.println("Karta raqamingizni kiriting:");
                    String cardNumber = input.nextLine();
                    System.out.println("Kartangiz amal qilish muddatini kiriting:");
                    String cardExpDate = input.nextLine();
                    System.out.println("Kartangiz pin kodini kiriting:");
                    String cardCode = input.nextLine();
                    user.setName(name);
                    user.setCardNumber(cardNumber);
                    user.setCardExpDate(cardExpDate);
                    user.setCardCode(cardCode);
                    System.out.println(dbService.addUser(user));
                    break;
                case 1:
                    input = new Scanner(System.in);
                    System.out.println("Karta raqamingizni kiriting:");
                    cardNumber = input.nextLine();
                    System.out.println("Kartangiz pin kodini kiriting:");
                    cardCode = input.nextLine();
                    user.setCardNumber(cardNumber);
                    user.setCardCode(cardCode);
                    if(dbService.getBalance(user)==-1){
                        System.out.println("Noto'g'ri ma'lumot kiritildi!");
                        break;
                    }
                    System.out.println("Sizning hisobingizda: " + dbService.getBalance(user) + " so'm mavjud!");
                    break;
                case 2:
                    input = new Scanner(System.in);
                    System.out.println("Karta raqamingizni kiriting:");
                    cardNumber = input.nextLine();

                    System.out.println("Mablag'ni kiriting:");
                    int deposit = input.nextInt();
                    user.setCardNumber(cardNumber);
                    if ((dbService.addBalance(user, deposit) == 1)) {
                        System.out.println("Balansingiz muvaffaqiyatli to'ldirildi!");
                        break;
                    }
                    System.out.println("Karta raqami topilmadi!");
                    break;
                case 3:
                    User sender = new User();
                    User receiver = new User();
                    input = new Scanner(System.in);
                    System.out.println("Karta raqamingizni kiriting:");
                    cardNumber = input.nextLine();
                    System.out.println("Kartangiz pin kodini kiriting:");
                    cardCode = input.nextLine();
                    sender.setCardNumber(cardNumber);
                    sender.setCardCode(cardCode);
                    System.out.println("Mablag' qabul qiluvchi karta raqamini kiriting");
                    String receiverCardNumber = input.nextLine();
                    receiver.setCardNumber(receiverCardNumber);
                    System.out.println("Mablag'ni kiriting:");
                    int amount = input.nextInt();
                    if (!(dbService.isCardNumber(sender))) {
                        System.out.println("Karta raqamingizni noto'g'ri kiritdingiz!");
                        return;
                    }
                    if (!(dbService.isCardNumber(receiver))) {
                        System.out.println("Oluvchi karta raqamini noto'g'ri kiritdingiz!");
                        return;
                    }
                    if (cardNumber.equals(receiverCardNumber)) {
                        System.out.println("Noto'g'ri amaliyot bajarilmoqda!");
                        break;
                    }
                    if (dbService.getBalance(sender) >= amount) {
                        dbService.withdrawalBalance(sender, amount);
                        dbService.addBalance(receiver, amount);
                        System.out.println(amount + " so'm mablag' oluvchi hisobiga muvaffaqiyatli o'tkazildi!");
                        System.out.println("Balansingizda: " + dbService.getBalance(sender) + " so'm mablag' qoldi!");
                        break;
                    }
                    System.out.println("Hisobingizda yetarli mablag' mavjud emas!");
                    break;
                default:
                    System.out.println("Noto'g'ri buyruq kiritildi!");
                    break;

            }
        }
    }
}
