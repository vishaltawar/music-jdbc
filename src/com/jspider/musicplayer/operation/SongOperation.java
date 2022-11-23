package com.jspider.musicplayer.operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.jspider.musicplayer.player.Player;
import com.jspider.musicplayer.song.Songs;

public class SongOperation {

	public static Connection connection = Player.getConnection();
	public static PreparedStatement preparedStatement;
	private static ResultSet resultSet;
	private static int result;
	private static Scanner scanner = new Scanner(System.in);

	ArrayList<Songs> playList = new ArrayList<Songs>();

	// Add Songs
	public void addSongs() {
		try {
			// Step:- 1 Load the Driver class
			Class.forName("com.mysql.cj.jdbc.Driver");
			try {
				// Step:- 2 Open the connection
				connection = Player.getConnection();
				preparedStatement = connection.prepareStatement("insert into song values(?,?,?,?,?,?,?)");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Songs songs = new Songs();
		// Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Song Id : ");
		int id = scanner.nextInt();
		try {
			preparedStatement.setInt(1, id);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// songs.setInt(preparedStatement.setInt(1, id));

		System.out.print("Enter Song Name : ");
		String name = scanner.next();
		try {
			preparedStatement.setString(2, name);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		System.out.print("Enter Song Movie/Album : ");
		String movie = scanner.next();
		try {
			preparedStatement.setString(3, movie);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		System.out.print("Enter Song Length : ");
		float length = scanner.nextFloat();
		try {
			preparedStatement.setFloat(4, length);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		System.out.print("Enter  Singer Name : ");
		String singer = scanner.next();
		try {
			preparedStatement.setString(5, singer);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		System.out.print("Enter Song Composer Name : ");
		String composer = scanner.next();
		try {
			preparedStatement.setString(6, composer);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		System.out.print("Enter Song lyrics Creater Name : ");
		String lyrics = scanner.next();
		try {
			preparedStatement.setString(7, lyrics);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		try {
			result = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(result + " Added Successfull.");

	}

	// View All Songs
	public void viewAllSongs() {
		// if (playList.isEmpty()) {
//			System.out.println("Sorry Song is Not Available in Playlist.");
//		}
		// else {
		System.out.println("List Of All Songs :-");
		try {
			// Class.forName("com.mysql.cj.jdbc.Driver");
			connection = Player.getConnection();

			preparedStatement = connection.prepareStatement("select * from song");

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1) + " | " + resultSet.getString(2) + " | "
						+ resultSet.getString(3) + " | " + resultSet.getFloat(4) + " | " + resultSet.getString(5)
						+ " | " + resultSet.getString(6) + " | " + resultSet.getString(7));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Play All Songs
	public void playAllSongs() {

//		if (playList.isEmpty()) {
//			System.out.println("Sorry Song is Not Available in Playlist.");
//		} else {
		try {

			connection = Player.getConnection();
			preparedStatement = connection.prepareStatement("select * from song");

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println("Now " + resultSet.getString(2) + " Song Playing....");

				try {
					System.out.println("Zingg...");
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	// }

	// Play Random Songs
	public void playRandomSongs() {
		int random = 0;
		try {
			connection = Player.getConnection();
			for (int i = 0; i <= 15; i++) {
				preparedStatement = connection.prepareStatement("select name from song where id=?");//
				random = (int) (Math.random() * 10);

				preparedStatement.setInt(1, random);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					System.out.println("Now " + resultSet.getString(1) + " Song Playing....");

					System.out.println("Zingg...");
					Thread.sleep(2000);
				}
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	// Select Song
	public void selectSong(int choise) {
		try {
			preparedStatement = connection.prepareStatement("select name from song where id=?");
			preparedStatement.setInt(1, choise);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				System.out.println("Now " + resultSet.getString(1) + " Song Playing....");

				System.out.println("Zingg...");
				Thread.sleep(4000);
			}
		} catch (InterruptedException e) {

			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

//**************************DELETE**********************
	// Delete Song
	public void deleteSongs(int choise) {
		try {

			connection = Player.getConnection();
			preparedStatement = connection.prepareStatement("delete from song where id=?");
			preparedStatement.setInt(1, choise);

			result = preparedStatement.executeUpdate();

			System.out.println(result + " row(s) Deleted");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Edit Song
	public void editSong() {
//		if (playList.isEmpty()) {
//			System.out.println("Sorry Song is Not Available in Playlist.");
//		} else {

		try {

			connection = Player.getConnection();
			preparedStatement = connection.prepareStatement("select * from song");

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1) + " | " + resultSet.getString(2) + " | "
						+ resultSet.getString(3) + " | " + resultSet.getFloat(4) + " | " + resultSet.getString(5)
						+ " | " + resultSet.getString(6) + " | " + resultSet.getString(7));

			}
			// *********************************************************************
			preparedStatement = connection.prepareStatement("select * from song where id=?");

			System.out.print("Which song you want to Edit Choose No. :- ");
			int songNo = scanner.nextInt();
			preparedStatement.setInt(1, songNo);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1) + " | " + resultSet.getString(2) + " | "
						+ resultSet.getString(3) + " | " + resultSet.getFloat(4) + " | " + resultSet.getString(5)
						+ " | " + resultSet.getString(6) + " | " + resultSet.getString(7));

			}
			System.out.println("What you want to Edit :-");
			System.out.println(
					"\n 1 : id\n 2 : Song name\n 3 : Movie/Album name \n 4 : Length\n 5 : Singer \n 6 : Composer\n 7 : Lyrics Creater\n 8 : Back");

			int choise = scanner.nextInt();

			switch (choise) {
			case 1:
				System.out.print("Enter Song Id : ");
				preparedStatement = connection.prepareStatement("update song set id=? where id=? ");
				preparedStatement.setInt(1, scanner.nextInt());
				preparedStatement.setInt(2, songNo);
				preparedStatement.executeUpdate();
				System.out.println(result + " row(s) Update Successfull.");

				break;
			case 2:
				System.out.print("Enter Song Name : ");
				preparedStatement = connection.prepareStatement("update song set name=? where id=? ");
				preparedStatement.setString(1, scanner.next());
				preparedStatement.setInt(2, songNo);
				result = preparedStatement.executeUpdate();
				System.out.println(result + " row(s) Update Successfull.");

				break;
			case 3:
				System.out.print("Enter Song Movie/Album : ");
				preparedStatement = connection.prepareStatement("update song set movie=? where id=? ");
				preparedStatement.setString(1, scanner.next());
				preparedStatement.setInt(2, songNo);
				result = preparedStatement.executeUpdate();
				System.out.println(result + " row(s) Update Successfull.");

				break;
			case 4:
				System.out.print("Enter Song Length : ");
				preparedStatement = connection.prepareStatement("update song set length=? where id=? ");
				preparedStatement.setFloat(1, scanner.nextFloat());
				preparedStatement.setInt(2, songNo);
				result = preparedStatement.executeUpdate();
				System.out.println(result + " row(s) Update Successfull.");

				break;
			case 5:
				System.out.print("Enter Song Singer Name : ");
				preparedStatement = connection.prepareStatement("update song set singer=? where id=? ");
				preparedStatement.setString(1, scanner.next());
				preparedStatement.setInt(2, songNo);
				result = preparedStatement.executeUpdate();
				System.out.println(result + " row(s) Update Successfull.");
				

				break;
			case 6:
				System.out.print("Enter Song Composer Name : ");
				preparedStatement = connection.prepareStatement("update song set composer=? where id=? ");
				preparedStatement.setString(1, scanner.next());
				preparedStatement.setInt(2, songNo);
				result = preparedStatement.executeUpdate();
				System.out.println(result + " row(s) Update Successfull.");
				

				break;
			case 7:
				System.out.print("Enter Song lyrics Creater Name : ");
				preparedStatement = connection.prepareStatement("update song set lyrics=? where id=? ");
				preparedStatement.setString(1, scanner.next());
				preparedStatement.setInt(2, songNo);
				result = preparedStatement.executeUpdate();
				System.out.println(result + " row(s) Update Successfull.");
				break;

			default:
				System.out.println("Invalid choise...");
				break;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
