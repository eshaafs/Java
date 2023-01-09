import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.ArrayList;

class Menu{
	private String kodeMenu;
	private String namaMenu;
	private double harga;
	
	public Menu(String kodeMenu, String namaMenu, double harga) {
		this.kodeMenu = kodeMenu;
		this.namaMenu = namaMenu;
		this.harga = harga;
	}
	
	// Getter method
	public String getKodeMenu() {
		return this.kodeMenu;
	}
	
	public String getNamaMenu() {
		return this.namaMenu;
	}
	
	public double getHarga() {
		return this.harga;
	}
	
	// Setter method
	public void setKodeMenu(String kodeMenu) {
		this.kodeMenu = kodeMenu;
	}
	
	public void setNamaMenu(String namaMenu) {
		this.namaMenu = namaMenu;
	}
	
	public void setHarga(double harga) {
		this.harga = harga;
	}
}

class MenuSpecial extends Menu{
	private double discount;
	
	public MenuSpecial(String kodeMenu, String namaMenu, double harga, double discount) {
		super(kodeMenu, namaMenu, harga);
		this.discount = discount;
	}
	
	public double getDiscount() {
		return this.discount;
	}
	
	public void setDiscount(double discount) {
		this.discount = discount;
	}
}


public class Main {
	private static Scanner input;
	
	public static void main(String[] args) {
		int choice;
		ArrayList<Menu> regularMenu = new ArrayList<Menu>();
		ArrayList<MenuSpecial> specialMenu = new ArrayList<MenuSpecial>();
		String more;
		String codeMenu, nameMenu;
		double priceMenu, discountMenu;
		while(true) {
			choice = mainMenu();
			if(choice == 1) {
				more = "Y";
				while(more.charAt(0) == 'Y') {
					for(int i=0; i<20; i++) System.out.println();
					System.out.println("Add Regular Menu");
					System.out.println("================\n");
					while(true) {
						try {
							input = new Scanner(System.in);
							System.out.print("Input menu code [R...]: " );
							codeMenu = input.next();
							codeMenu.toUpperCase();
							// Validate menu code is 4 characters and start with R
							while(codeMenu.length() != 4 || codeMenu.charAt(0) != 'R') {
								System.err.println("Wrong format!");
								System.out.print("Input menu code [R...]: ");
								codeMenu = input.next();
							}
							break;
						} catch(Exception e) {
							System.err.println("Wrong input!");
						}
					}
					while(true) {
						try {
							input = new Scanner(System.in);
							System.out.print("Input menu name [5-20]: " );
							nameMenu = input.nextLine();
							// Validate menu name is 5 - 20 characters
							while(nameMenu.length() < 5 || nameMenu.length() > 20) {
								System.err.println("Wrong format!");
								System.out.print("Input menu name [5-20]: ");
								nameMenu = input.nextLine();
							}
							break;
						} catch(Exception e) {
							System.err.println("Wrong input!");
						}
					}
					while(true) {
						try {
							input = new Scanner(System.in);
							System.out.print("Input menu price [10000-100000]: " );
							priceMenu = input.nextDouble();
							// Validate menu price is 10000-100000
							while(priceMenu < 10000 || priceMenu > 100000) {
								System.err.println("Wrong format!");
								System.out.print("Input menu price [10000-100000]: ");
								priceMenu = input.nextDouble();
							}
							break;
						} catch(Exception e) {
							System.err.println("Wrong input!");
						}
					}
					regularMenu.add(new Menu(codeMenu, nameMenu, priceMenu));
					System.out.println("Input Success!\n");
					
					// Input More
					try {
						System.out.print("Input another menu [y|n]: ");
						more = input.next();
						more = more.toUpperCase();
						while(!more.equals("Y") && !more.equals("N")) {
							System.err.println("Wrong choice!");
							System.out.print("Input another menu [y|n]: ");
							more = input.next();
							more = more.toUpperCase();
						}
					} catch(Exception e) {
						System.err.println("Wrong input!");
					}
				}
			} else if (choice == 2) {
				more = "Y";
				while(more.charAt(0) == 'Y') {
					for(int i=0; i<20; i++) System.out.println();
					System.out.println("Add Special Menu");
					System.out.println("================\n");
					while(true) {
						try {
							input = new Scanner(System.in);
							System.out.print("Input menu code [S...]: " );
							codeMenu = input.next();
							// Validate menu code is 4 characters and start with S
							while(codeMenu.length() != 4 || codeMenu.charAt(0) != 'S') {
								System.err.println("Wrong format!");
								System.out.print("Input menu code [S...]: ");
								codeMenu = input.next();
							}
							codeMenu.toUpperCase();
							break;
						} catch(Exception e) {
							System.err.println("Wrong input!");
						}
					}
					while(true) {
						try {
							input = new Scanner(System.in);
							System.out.print("Input menu name [5-20]: " );
							nameMenu = input.nextLine();
							// Validate menu name is 5 - 20 characters
							while(nameMenu.length() < 5 || nameMenu.length() > 20) {
								System.err.println("Wrong format!");
								System.out.print("Input menu code [5-20]: ");
								nameMenu = input.nextLine();
							}
							break;
						} catch(Exception e) {
							System.err.println("Wrong input!");
						}
					}
					while(true) {
						try {
							input = new Scanner(System.in);
							System.out.print("Input menu price [10000-100000]: " );
							priceMenu = input.nextDouble();
							// Validate menu price is 10000-100000
							while(priceMenu < 10000 || priceMenu > 100000) {
								System.err.println("Wrong format!");
								System.out.print("Input menu price [10000-100000]: ");
								priceMenu = input.nextDouble();
							}
							break;
						} catch(Exception e) {
							System.err.println("Wrong input!");
						}
					}
					while(true) {
						try {
							input = new Scanner(System.in);
							System.out.print("Input menu dicount [10% | 25% | 50%]: " );
							discountMenu = input.nextDouble();
							// Validate menu price is 10000-100000
							while(discountMenu != 10 && discountMenu != 25 && discountMenu != 50) {
								System.err.println("Wrong format!");
								System.out.print("Input menu dicount [10% | 25% | 50%]: ");
								discountMenu = input.nextDouble();
							}
							break;
						} catch(Exception e) {
							System.err.println("Wrong input!");
						}
					}
					specialMenu.add(new MenuSpecial(codeMenu, nameMenu, priceMenu, discountMenu));
					System.out.println("Input Success!\n");
					
					// Input More
					try {
						System.out.print("Input another menu [y|n]: ");
						more = input.next();
						more = more.toUpperCase();
						while(!more.equals("Y") && !more.equals("N")) {
							System.err.println("Wrong choice!");
							System.out.print("Input another menu [y|n]: ");
							more = input.next();
							more = more.toUpperCase();
						}
					} catch(Exception e) {
						System.err.println("Wrong input!");
					}
				}
				
			} else if (choice == 3) {
				for(int i=0; i<20; i++) System.out.println();

				String regularMenuAlignFormat = "%-4d %-7s %-20s %-10s %n";
				String spesialMenuAlignFormat = "%-4d %-7s %-20s %-15s %-12s %n";
				int i = 0, j = 0; //Index loop
				
				System.out.println("Regular Menu");
				System.out.format("=========================================== %n");
				System.out.format("No.|  Kode  |        Nama       |   Harga  | %n");
				System.out.format("=========================================== %n");
				for(Menu menu:regularMenu) {
					i++;
					System.out.format(regularMenuAlignFormat, i, menu.getKodeMenu(), menu.getNamaMenu(), new DecimalFormat("#,###.00").format(menu.getHarga()));
				}
				System.out.println("\nSpecial Menu: ");
				System.out.format("==============================================================%n");
				System.out.format("No.|  Kode  |        Nama       |      Harga     |   Diskon  |%n");
				System.out.format("==============================================================%n");
				for(MenuSpecial menu:specialMenu) {
					j++;
					System.out.format(spesialMenuAlignFormat, j, menu.getKodeMenu(), menu.getNamaMenu(), new DecimalFormat("#,###.00").format(menu.getHarga()), (int)menu.getDiscount() + " %");
				}
				System.out.print("\nPress enter to return to the main menu...");
				try{
					System.in.read();
				} catch (Exception e) {
					System.err.println(e);
				}
			} else if (choice == 4) {
				more = "Y";
				while(more.charAt(0) == 'Y') {
					for(int i=0; i<20; i++) System.out.println();
					System.out.println("Delete Regular Menu");
					System.out.println("================\n");
					while(true) {
						try {
							boolean isFound = false;
							input = new Scanner(System.in);
							while(isFound == false) {
								int i = 0;
								System.out.print("Input menu code [R...]: " );
								codeMenu = input.next();
								// Validate menu code is 4 characters and start with R
								while(codeMenu.length() != 4 || codeMenu.charAt(0) != 'R') {
									System.err.println("Wrong format!");
									System.out.print("Input menu code [R...]2: ");
									codeMenu = input.next();
								}
								codeMenu.toUpperCase();
								for(Menu menu:regularMenu) {
									if(menu.getKodeMenu().equals(codeMenu)) {
										regularMenu.remove(i);
										isFound = true;
										System.out.println("Delete Success!\n");
										break;
									} else isFound = false;
									i++;
								}
								if(isFound == false) {
									System.err.println("Code is wrong!");
								} else if (isFound == true) break;
							}
							break;
						} catch(Exception e) {
							System.err.println("Wrong input!");
						}
					}

					// Delete More
					try {
						System.out.print("Delete another menu [y|n]: ");
						more = input.next();
						more = more.toUpperCase();
						while(!more.equals("Y") && !more.equals("N")) {
							System.err.println("Wrong choice!");
							System.out.print("Delete another menu [y|n]: ");
							more = input.next();
							more = more.toUpperCase();
						}
					} catch(Exception e) {
						System.err.println("Wrong input!");
					}
				}
			} else if (choice == 5) {
				more = "Y";
				while(more.charAt(0) == 'Y') {
					for(int i=0; i<20; i++) System.out.println();
					System.out.println("Delete Special Menu");
					System.out.println("================\n");
					while(true) {
						try {
							boolean isFound = false;
							input = new Scanner(System.in);
							while(isFound == false) {
								int i = 0;
								System.out.print("Input menu code [S...]: " );
								codeMenu = input.next();
								while(codeMenu.length() != 4 || codeMenu.charAt(0) != 'S') {
									System.err.println("Wrong format!");
									System.out.print("Input menu code [S...]2: ");
									codeMenu = input.next();
								}
								codeMenu.toUpperCase();
								for(Menu menu:specialMenu) {
									if(menu.getKodeMenu().equals(codeMenu)) {
										specialMenu.remove(i);
										isFound = true;
										System.out.println("Delete Success!\n");
										break;
									} else isFound = false;
									i++;
								}
								if(isFound == false) {
									System.err.println("Code is wrong!");
								} else if (isFound == true) break;
							}
							break;
						} catch(Exception e) {
							System.err.println("Wrong input!");
						}
					}

					// Delete More
					try {
						System.out.print("Delete another menu [y|n]: ");
						more = input.next();
						more = more.toUpperCase();
						while(!more.equals("Y") && !more.equals("N")) {
							System.err.println("Wrong choice!");
							System.out.print("Delete another menu [y|n]: ");
							more = input.next();
							more = more.toUpperCase();
						}
					} catch(Exception e) {
						System.err.println("Wrong input!");
					}
				}
			}else if(choice == 6){
				System.exit(0);
			}
		}
			
	}
	
	// Method to print menu
	public static int mainMenu() {
		for(int i=0; i<20; i++) System.out.println();
		System.out.println("Family Restaurant");
		for(int i=0; i<17; i++) System.out.print("=");
		System.out.println("\n");
		System.out.println("1. Add Regular Menu");
		System.out.println("2. Add Special Menu");
		System.out.println("3. Show All Menu");
		System.out.println("4. Delete Regular Menu");
		System.out.println("5. Delete Special Menu");
		System.out.println("6. Exit");
		
		int choice;
		while(true) {
			try {
				input = new Scanner(System.in);
				System.out.print("Choice [1-6]: ");
				choice = input.nextInt();
				while(choice < 1 || choice > 6) {
					System.err.println("Wrong choice!");
					System.out.print("Choice [1-6]: ");
					choice = input.nextInt();
				}
				break;
			} catch(Exception e) {
				System.err.println("Wrong input!");
			}
		}
		return choice;
	}

}
