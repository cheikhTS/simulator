package fr.ensicaen.simulator.model.mediator.listener;

import fr.ensicaen.simulator.model.mediator.Mediator;

public interface MediatorListener {

	/**
	 * Invoquée lorsque le mediator est utilisé pour envoyer des donnees.
	 */
	public void onSendData(Mediator m, boolean response, String data);

}
