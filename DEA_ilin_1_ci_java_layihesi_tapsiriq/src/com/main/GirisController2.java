package com.main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;

public class GirisController2 implements Initializable {

	@FXML
	Slider slayder;
	@FXML
	ProgressIndicator proses;

	@FXML
	ProgressBar proses2;

	@FXML
	Button basla1, basla2;

	@FXML
	ListView<String> list1, list2;

	@FXML
	public void basla1Basildi(ActionEvent event) {

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {

				double say = 0.0D;
				while (say <= 1.01) {
					proses.setProgress(say);
					;

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}

					say += 0.01;
				}

			}
		});

		t1.start();
	}

	@FXML

	public void basla2Basildi(ActionEvent event) {

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {

				double say = 0.0D;
				while (say <= 1.01) {

					proses2.setProgress(say);

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}

					say += 0.01;
				}

			}
		});

		t2.start();

	}

	@FXML
	MenuItem bagla;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		slayder.setMin(0);
		slayder.setMax(10);
		slayder.setValue(5);

		System.out.println(slayder.getValue());

		slayder.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> oV, Number kohneDeyer, Number yeniDeyer) {
				System.out.println((int) slayder.getValue());
			}
		});

	}

	@FXML
	public void surusdurulur(ActionEvent event) {
		System.out.println(slayder.getValue());
	}

	@FXML
	public void baglaBasildi(ActionEvent event) {
		System.exit(0);
	}
}
