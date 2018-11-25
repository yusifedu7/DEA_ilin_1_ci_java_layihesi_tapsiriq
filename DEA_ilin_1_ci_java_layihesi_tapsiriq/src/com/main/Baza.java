package com.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Baza {
	static int secilenID;

	static String bazaYolu = "jdbc:mysql://127.0.0.1:3306/layihe_dea_java_3_cu_tapsiriq_proqrami_2018_06?user=yusif1&password=1234";

	public static String cedvelinSecilenSutunu;

	public static boolean iud(String sorgu) {
		boolean ugurluOldu = false;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection qosucu = DriverManager.getConnection(bazaYolu);
			Statement st = qosucu.createStatement();
			st.execute(sorgu);
			ugurluOldu = true;
			st.close();
			qosucu.close();
		} catch (Exception xeta) {
			ugurluOldu = false;
			xeta.printStackTrace();
		}

		return ugurluOldu;

	}

	public static ObservableList<Tapsiriq> tapsiriqlar(String sutun, String axtarilan) {
		ObservableList<Tapsiriq> yazilar = FXCollections.observableArrayList();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection qosucu = DriverManager.getConnection(bazaYolu);
			Statement st = qosucu.createStatement();
			ResultSet rs = st.executeQuery("select id,ad,tarix,gun,qalangun,kateqoriya,status from  tapsiriqlar where "
					+ sutun + " like '%" + axtarilan + "%'");

			while (rs.next()) {

				Tapsiriq t = new Tapsiriq(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
						rs.getString(6), rs.getString(7));

				int realGun = t.getGunSutun();
				String tarix = t.getTarixSutun();

				Date indiDate = new Date();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date tarixDate = sdf.parse(tarix);

				int aradakiGun = daysBetween(tarixDate, indiDate);

				int qalanGunun = realGun - aradakiGun;
				if (qalanGunun < 0) {
					qalanGunun = 0;
				}

				t.setQalangunSutun(qalanGunun);

				yazilar.add(t);

			}
			rs.close();
			st.close();
			qosucu.close();
		} catch (Exception xeta) {

			xeta.printStackTrace();
		}

		return yazilar;

	}

	public static int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

	public static ArrayList<String> cedveldenMelumatlariVer(String cedvelAdi, String sutun, String sert) {

		ArrayList<String> yazilar = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection qosucu = DriverManager.getConnection(bazaYolu);
			Statement st = qosucu.createStatement();
			ResultSet rs = st.executeQuery("select " + sutun + " from " + cedvelAdi + sert);
			while (rs.next()) {
				yazilar.add(rs.getString(1));

			}

			rs.close();
			st.close();
			qosucu.close();
		} catch (Exception xeta) {

			xeta.printStackTrace();
		}

		return yazilar;

	}

	public static String indikiTarix() {
		String tarix = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		tarix = sdf.format(d);

		return tarix;
	}

}
