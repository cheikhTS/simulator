package fr.ensicaen.simulator_ep.simulator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ensicaen.simulator.model.component.Component;
import fr.ensicaen.simulator.model.component.ComponentI;
import fr.ensicaen.simulator.model.component.ComponentIO;
import fr.ensicaen.simulator.model.component.ComponentO;
import fr.ensicaen.simulator.model.factory.MediatorFactory;
import fr.ensicaen.simulator.model.factory.MediatorFactory.EMediator;
import fr.ensicaen.simulator.model.mediator.Mediator;
import fr.ensicaen.simulator.model.mediator.explicit.HalfDuplexMediator;
import fr.ensicaen.simulator_ep.ep.strategies.card.CardStrategy;
import fr.ensicaen.simulator_ep.ep.strategies.ept.EPTStrategy;

public class Main {

	private static Logger log = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		log.info("Creation d'une carte bancaire");
		ComponentIO card = new ComponentIO("cb");
		ComponentIO chip = new ComponentIO("puce");
		ComponentIO magstrippe = new ComponentIO("piste magnetique");
		chip.getProperties().put("pan", "1111111111111111111111111");
		chip.getProperties().put("bccs", "12421874");
		card.getProperties().put("cipher", "RSA2048");
		card.getProperties().put("protocol", "ISO7816");
		card.getProperties().put("pan", "1111111111111111111111111");
		card.getProperties().put("icvv", "000");
		card.getProperties().put("genre", "M");
		card.getProperties().put("nom porteur", "Florent Moisson");
		card.getProperties().put("date expiration", "09/15");
		magstrippe.getProperties().put("piste iso2", "59859595985888648468454684");
		card.addChild(magstrippe);
		card.addChild(chip);
		log.info(card.toString());
		//Comportement de la carte
		//card.setStrategy(new CardStrategy());
		
		
		log.info("Creation d'un TPE");
		ComponentIO tpe = new ComponentIO("tpe");
		Component magstrippeReader = new ComponentI("lecteur piste magnetique");
		Component pinpad = new ComponentI("pinpad");
		Component chipReader = new ComponentIO("lecteur carte a puce");
		ComponentIO core = new ComponentIO("logiciel integre");
		core.getProperties().put("banque acquereur", "bnp");
		tpe.addChild(magstrippeReader);
		tpe.addChild(pinpad);
		tpe.addChild(chipReader);
		tpe.addChild(core);
		log.info(tpe.toString());
		//Comportement de la tpe
		//tpe.setStrategy(new EPTStrategy());
		
		//edition des liens
		Mediator m = MediatorFactory.getInstance().getMediator(card, tpe, EMediator.HALFDUPLEX);
		Mediator m1 = MediatorFactory.getInstance().getMediator(card, chip, EMediator.HALFDUPLEX);
		Mediator m2 = MediatorFactory.getInstance().getMediator(card, magstrippe, EMediator.HALFDUPLEX);
		
		//TPE->CARTE : demande de secure channel
		//tpe.output(m, "content-type:iso7816;type:rq;msg:initco;protocols:B0',CB2A;ciphersetting:none,RSA2048");
		
	}

}
