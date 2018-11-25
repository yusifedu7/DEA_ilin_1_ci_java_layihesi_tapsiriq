package com.main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class GirisController implements Initializable {
	@FXML
	Label setirSayi, xeberdarliqlarLabel;
	@FXML
	private TableColumn<Tapsiriq, String> tarixSutun;

	@FXML
	private CheckBox hamisiSecimi;

	@FXML
	private TableColumn<Tapsiriq, String> adSutun;

	@FXML
	private TableColumn<Tapsiriq, String> statusSutun;

	@FXML
	private TextField axtarisTF;

	@FXML
	private TableColumn<Tapsiriq, Integer> gunSutun, qalangunSutun;

	@FXML
	private TableColumn<Tapsiriq, Integer> idSutun;

	@FXML
	private TableView<Tapsiriq> tapsiriqlarCedveli;

	@FXML
	private RadioButton hamisiRB;

	@FXML
	private ToggleGroup statuslar;

	@FXML
	private RadioButton hellOlanlarRB;

	@FXML
	private TextField gunTF;

	@FXML
	private TableColumn<Tapsiriq, String> kateqoriyaSutun;

	@FXML
	private ComboBox<String> kateqoriyalar;

	@FXML
	private TextField adTF;

	@FXML
	private Button yeniTapsiriqButton, redakteButton, silButton, yeniKateqoriyaButton, hellEdildiButton;

	@FXML
	private AnchorPane panel;

	@FXML
	private RadioButton hellOlmayanlarRB;

	@FXML
	void tapsiriqlarCedveliSecilir(MouseEvent event) {
		Baza.secilenID = tapsiriqlarCedveli.getSelectionModel().getSelectedItem().getIdSutun();

		adTF.setText(tapsiriqlarCedveli.getSelectionModel().getSelectedItem().getAdSutun());

		gunTF.setText("" + tapsiriqlarCedveli.getSelectionModel().getSelectedItem().getGunSutun());
		kateqoriyalar.setValue(tapsiriqlarCedveli.getSelectionModel().getSelectedItem().getKateqoriyaSutun());

		@SuppressWarnings("rawtypes")
		TablePosition movqe = tapsiriqlarCedveli.getSelectionModel().getSelectedCells().get(0);
		String sutunIdSi = movqe.getTableColumn().getId();

		xeberdarliqlarLabel.setText(sutunIdSi + " - Seçildi");

		switch (sutunIdSi) {

		case "adSutun": {
			Baza.cedvelinSecilenSutunu = "ad";
		}
			;
			break;
		case "gunSutun": {
			Baza.cedvelinSecilenSutunu = "gun";
		}
			;
			break;
		case "kateqoriyaSutun": {
			Baza.cedvelinSecilenSutunu = "kateqoriya";
		}
			;
			break;

		case "idSutun": {
			Baza.cedvelinSecilenSutunu = "id";
		}
			;
			break;
		case "tarixSutun": {
			Baza.cedvelinSecilenSutunu = "tarix";
		}
			;
			break;

		default: {
			Baza.cedvelinSecilenSutunu = "ad";
			xeberdarliqlarLabel.setText("adSutun - Seçildi");
		}
			 
			break;
		}
	}

	@FXML
	void statusFiltirEdilir(ActionEvent event) {
		if (hamisiRB.isSelected()) {
			cedveliYenile("status", "");

		} else if (hellOlanlarRB.isSelected()) {

			cedveliYenile("status", "Həll edilib");

		} else if (hellOlmayanlarRB.isSelected()) {
			cedveliYenile("status", "Həll edilməyib");
		}
	}

	@FXML
	void yeniKateqoriyaBasildi(ActionEvent event) {
		String yeniKT = JOptionPane.showInputDialog("Yeni kateqoriyanın adını daxil edin").trim();
		if (!yeniKT.equals("")) {
			Baza.iud("insert into kateqoriyalar (ad) values ('" + yeniKT + "') ");
			kateqoriyalariYenile();
		} else {
			xeberdarliqlarLabel.setText("Adı boş qoymaq olmaz");
		}

	}

	@FXML
	void yeniTapsiriqBasildi(ActionEvent event) {
		String kateqoriya = kateqoriyalar.getSelectionModel().getSelectedItem();
		String ad = adTF.getText().trim();
		String gunString = gunTF.getText().trim();

		if (kateqoriya == null) {
			xeberdarliqlarLabel.setText("Kateqoriya seçilməyib");
		} else {

			if (ad.equals("")) {
				xeberdarliqlarLabel.setText("Ad boş ola bilməz");
			} else {

				if (gunString.equals("")) {
					xeberdarliqlarLabel.setText("Günü boş qoymaq olmaz");
				} else {

					int gun = Integer.parseInt(gunString);

					boolean netice = Baza.iud("insert into tapsiriqlar (ad,tarix,gun,kateqoriya,status) values ('" + ad
							+ "','" + Baza.indikiTarix() + "'," + gun + ",'" + kateqoriya + "','Həll edilməyib')");
					if (netice) {
						xeberdarliqlarLabel.setText("YENİ QEYDİYYAT UĞURLU");
						cedveliYenile("ad", "");
					} else {
						xeberdarliqlarLabel.setText("YENİ QEYDİYYAT UĞURSUZ");
					}
				}
			}
		}
	}

	@FXML
	void axtarisEdilir(KeyEvent event) {
		String axtarilan = axtarisTF.getText().trim();

		cedveliYenile(Baza.cedvelinSecilenSutunu, axtarilan);
	}

	@FXML
	void hellEdildiBasildi(ActionEvent event) {

		if (hamisiSecimi.isSelected()) {
			boolean netice = Baza.iud("update tapsiriqlar set status='Həll edilib'");

			if (netice) {
				xeberdarliqlarLabel.setText("STATUS UĞURLU DƏYİŞDİRİLDİ");
				cedveliYenile("ad", "");
			} else {
				xeberdarliqlarLabel.setText("STATUS UĞURSUZ DƏYİŞDİRİLDİ");
			}
		} else {
			boolean netice = Baza.iud("update tapsiriqlar set status='Həll edilib'  where id=" + Baza.secilenID + "");

			if (netice) {
				xeberdarliqlarLabel.setText("STATUS UĞURLU DƏYİŞDİRİLDİ");
				cedveliYenile("ad", "");
			} else {
				xeberdarliqlarLabel.setText("STATUS UĞURSUZ DƏYİŞDİRİLDİ");
			}
		}

	}

	@FXML
	void silBasildi(ActionEvent event) {

		if (hamisiSecimi.isSelected()) {
			boolean netice = Baza.iud("truncate table tapsiriqlar");
			if (netice) {
				xeberdarliqlarLabel.setText("HAMISINI SİL UĞURLU");
				cedveliYenile("ad", "");
			} else {
				xeberdarliqlarLabel.setText("HAMISINI SİL UĞURLU");

			}
		} else {
			boolean netice = Baza.iud("delete from tapsiriqlar where id=" + Baza.secilenID + "");
			if (netice) {
				xeberdarliqlarLabel.setText("SİL UĞURLU");
				cedveliYenile("ad", "");
			} else {
				xeberdarliqlarLabel.setText("SİL UĞURLU");

			}
		}
	}

	@FXML
	void redakteBasildi(ActionEvent event) {

		String kateqoriya = kateqoriyalar.getSelectionModel().getSelectedItem();
		String ad = adTF.getText().trim();
		String gunString = gunTF.getText().trim();

		if (kateqoriya == null) {
			xeberdarliqlarLabel.setText("Kateqoriya seçilməyib");
		} else {

			if (ad.equals("")) {
				xeberdarliqlarLabel.setText("Ad boş ola bilməz");
			} else {

				if (gunString.equals("")) {
					xeberdarliqlarLabel.setText("Günü boş qoymaq olmaz");
				} else {

					int gun = Integer.parseInt(gunString);

					boolean netice = Baza.iud("update tapsiriqlar set ad='" + ad + "',gun=" + gun + ",kateqoriya='"
							+ kateqoriya + "'  where id=" + Baza.secilenID + "");

					if (netice) {
						xeberdarliqlarLabel.setText("REDAKTƏ UĞURLU");
						cedveliYenile("ad", "");
					} else {
						xeberdarliqlarLabel.setText("REDAKTƏ UĞURSUZ");
					}
				}
			}
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Baza.cedvelinSecilenSutunu = "ad";
		kateqoriyalariYenile();

		cedveliYenile("ad", "");

		idSutun.setCellValueFactory(new PropertyValueFactory<>("idSutun"));
		adSutun.setCellValueFactory(new PropertyValueFactory<>("adSutun"));

		tarixSutun.setCellValueFactory(new PropertyValueFactory<>("tarixSutun"));
		gunSutun.setCellValueFactory(new PropertyValueFactory<>("gunSutun"));
		qalangunSutun.setCellValueFactory(new PropertyValueFactory<>("qalangunSutun"));
		kateqoriyaSutun.setCellValueFactory(new PropertyValueFactory<>("kateqoriyaSutun"));

		statusSutun.setCellValueFactory(new PropertyValueFactory<>("statusSutun"));

		gunTF.addEventHandler(KeyEvent.KEY_TYPED, reqemYoxlayici(5));
	}

	private void kateqoriyalariYenile() {
		ArrayList<String> kateqoriyalarList = Baza.cedveldenMelumatlariVer("kateqoriyalar", "ad", "");
		kateqoriyalar.getItems().clear();
		kateqoriyalar.getItems().addAll(kateqoriyalarList);
	}

	private void cedveliYenile(String sutun, String axtarilan) {
		tapsiriqlarCedveli.setItems(Baza.tapsiriqlar(sutun, axtarilan));

		setirSayi.setText("Sətir sayı - " + tapsiriqlarCedveli.getItems().size());

	}

	public EventHandler<KeyEvent> reqemYoxlayici(final Integer maksimumUzunluq) {
		return new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				TextField metin = (TextField) e.getSource();
				if (metin.getText().length() >= maksimumUzunluq) {
					e.consume();
				}
				if (e.getCharacter().matches("[0-9.]")) {
					if (metin.getText().contains(".") && e.getCharacter().matches("[.]")) {
						e.consume();
					} else if (metin.getText().length() == 0 && e.getCharacter().matches("[.]")) {
						e.consume();
					}
				} else {
					e.consume();
				}
			}
		};
	}

	public EventHandler<KeyEvent> herfYoxlayici(final Integer maksimumUzunluq) {
		return new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				TextField metin = (TextField) e.getSource();
				if (metin.getText().length() >= maksimumUzunluq) {
					e.consume();
				}
				if (e.getCharacter().matches("[A-Za-z]")) {
				} else {
					e.consume();
				}
			}
		};
	}
}
