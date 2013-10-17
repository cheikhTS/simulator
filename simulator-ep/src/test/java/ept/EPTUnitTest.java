package ept;

import java.util.Date;

import model.component.ComponentIO;
import model.component.ComponentO;
import model.component.IOutput;
import model.mediator.Mediator;
import model.response.IResponse;
import model.response.VoidResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import simulator.Context;
import simulator.SimulatorFactory;
import simulator.exception.SimulatorException;
import ep.strategies.ept.EPTChipsetStrategy;
import ep.strategies.ept.EPTStrategy;

/**
 * Electronic Payment Terminal (EPT)
 * 
 * Terminal de Paiement Electronique |- Chipset |- Lecteur carte à piste |-
 * Lecteur carte à puce |- Lecteur carte NFC [*] |- Emplacement SAM |-
 * Emplacement SIM [*] |- Imprimante |- Pinpad sécurisé |- Interface réseau
 * acquéreur (Modem, Ethernet, GPRS, ...) |- Interface caisse (Serial, Ethernet,
 * ...) [*]
 * 
 * [*] : facultatif
 * 
 * Source : http://fr.wikipedia.org/wiki/Terminal_de_paiement_%C3%A9lectronique
 * 
 * @author Flo
 */
public class EPTUnitTest {

	private static ComponentO testPerformer;

	private static ComponentIO paymentTerminal;
	private static ComponentIO smartCardReader;
	private static ComponentIO chipset;
	private static ComponentIO printer;
	private static ComponentIO securePinPad;
	private static ComponentIO networkInterface;

	@Before
	public void init() throws Exception {
		testPerformer = new ComponentO("Test performer");

		paymentTerminal = new ComponentIO("Payment terminal");
		paymentTerminal.setStrategy(new EPTStrategy());

		smartCardReader = new ComponentIO("Smart card reader");
		paymentTerminal.getComponents().add(smartCardReader);

		chipset = new ComponentIO("Chipset");
		chipset.setStrategy(new EPTChipsetStrategy());
		chipset.getProperties().put("pos_id", "0000623598");
		chipset.getProperties().put("protocol_list", "ISO7816 ISO8583 CB2A-T");
		chipset.getProperties().put("protocol_prefered", "ISO7816");
		paymentTerminal.getComponents().add(chipset);

		printer = new ComponentIO("Printer");
		paymentTerminal.getComponents().add(printer);

		securePinPad = new ComponentIO("Secure pin pad");
		paymentTerminal.getComponents().add(securePinPad);

		networkInterface = new ComponentIO("Network interface");
		paymentTerminal.getComponents().add(networkInterface);
	}

	@After
	public void clean() throws Exception {
	}

	@Test
	public void chipsetForwardTest() throws SimulatorException {
		final String testData = "FORWARD:OK";

		// forward test
		chipset.setStrategy(new EPTChipsetStrategy() {
			@Override
			public IResponse processMessage(ComponentIO component, Mediator c, String data) {
				Assert.assertEquals(data, testData);

				return VoidResponse.build();
			}
		});

		simulate(paymentTerminal, testData);
	}

	@Test
	public void secureChannelTest() {
		chipset.notifyEvent("CARD_INSERTED");
	}

	public void simulate(IOutput dst, String event) throws SimulatorException {
		// add start point for the simulator
		Context.getInstance().addStartPoint(new Date(), dst, event);

		// execute simulation.
		SimulatorFactory.getSimulator().start();
	}
}
