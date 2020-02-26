package com.crisgon.apirest.model;

import java.io.Serializable;

/**
 * Clase que representa la caracteristica seleccionada por la CPU. No quería
 * precindir de esta clase pero he tenido dificultades para pasar Strings con el
 * Retrofit en Android.
 * 
 * @author Cristhian González.
 *
 */

public class CPUSelection implements Serializable {

	private String featureSelection;

	public CPUSelection(String featureSelection) {
		this.featureSelection = featureSelection;
	}

	public String getFeatureSelection() {
		return featureSelection;
	}

	public void setFeatureSelection(String featureSelection) {
		this.featureSelection = featureSelection;
	}

}
