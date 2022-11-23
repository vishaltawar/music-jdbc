package com.jspider.musicplayer.player;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

import com.jspider.musicplayer.operation.SongOperation;

public class Player {

	public static Connection connection;
	private static PreparedStatement preparedStatement;
	private static Properties properties = new Properties();
	private static FileReader fileReader;
	private static String filePath = "F:\\Vishal Tawar\\WEJE-2\\music\\resource\\db_info.properties";
	private static SongOperation operation = new SongOperation();
	private static Scanner scanner;
	static boolean loop = true;

//********** getter & Setter **********************
	public static Connection getConnection() {
		return connection;
	}

	public static void setConnection(Connection connection) {
		Player.connection = connection;
	}

	public static PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}

	public static void setPreparedStatement(PreparedStatement preparedStatement) {
		Player.preparedStatement = preparedStatement;
	}

	public static Properties getProperties() {
		return properties;
	}

	public static void setProperties(Properties properties) {
		Player.properties = properties;
	}

	// ******************************* main() Method *****************************
	public static void main(String[] args) {
		Player player = new Player();

		while (loop) {
			try {
				player.player();
				System.out.println();

			} catch (Exception e) {
				System.out.println("Wrong Input...");
				player.player();
			}
		}
	}

	// player method
	public void player() {
// *******************************JDBC Conection Start*********************************************		
		try {
			fileReader = new FileReader(filePath);
			properties.load(fileReader);
			Class.forName(properties.getProperty("driverPath"));
			connection = DriverManager.getConnection(properties.getProperty("dburl"), properties);

// ********************************JDBC Conection End********************************************		
			scanner = new Scanner(System.in);
			System.out.println(
					"Please chose following operation :-\n1 :- Add/Remove Songs\n2 :- Play a Songs\n3 :- Edit a Songs\n4 :- View Songs\n5 :- Exist ");
			int choise = scanner.nextInt();
			switch (choise) {
			case 1:// for Add/Remove songs
				System.out.println("Choose  :-\n 1 :- Add Songs\n 2 :- Remove a Songs\n 3 :- Back ");
				choise = scanner.nextInt();
				switch (choise) {
				case 1:// add Songs
					System.out.print("How many Songs you want you to add :- ");
					choise = scanner.nextInt();
					while (choise > 0) {
						try {
							operation.addSongs();
						} catch (InputMismatchException e) {
							System.out.println("Wrong Input...\nPlease Check Data Type\n");
							break;
						}
						// call add method

						System.out.println();
						choise--;
					}
					operation.viewAllSongs();

					loop = true;
					break;
				case 2:// for Remove Songs
					operation.viewAllSongs();
					// System.out.println(operation.viewAllSongs());
					System.out.print("Which song you want to Delete Please Choose a No. :- ");
					choise = scanner.nextInt();
					operation.deleteSongs(choise);// delete method call by passing choise number
					break;
				case 3:// for Back Home
					player();
					break;
				default:
					System.out.println("Invalid Input ");
					break;
				}

				break;
			case 2:// for Play a Songs

				System.out.println(
						"\nchoose following Operation :- \n1 :- Play All Song\n2 :- Play Random Songs\n3 :- Select Song to Play\n4 :- Back");
				choise = scanner.nextInt();
				switch (choise) {
				case 1:// PlayAll songs

					operation.playAllSongs();

					break;
				case 2:// Play Random Songs
					operation.playRandomSongs();

					break;
				case 3:// select Song to Play
					operation.viewAllSongs();
					System.out.println("Choose Song :- ");
					choise = scanner.nextInt();
					operation.selectSong(choise);
					break;
				case 4:
					player();
					break;

				default:
					System.out.println("Invalid choise...");
					loop = true;
					break;
				}

				break;
			case 3:// Edit a Songs
					// operation.viewAllSongs();
				try {
					operation.editSong();

				} catch (InputMismatchException e) {
					System.out.println("Wrong Input..");
				}
				break;
			case 4:
				operation.viewAllSongs();
				break;
			case 5:
				loop = false;
				System.out.println("Thank You.");
				break;
			default:
				System.out.println("Invalid choise...");
				break;
			}

		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			//scanner.close();
		}

	}

}
