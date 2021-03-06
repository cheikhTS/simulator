package ep.strategies.fo;

import org.jpos.iso.ISOMsg;

import fr.ensicaen.simulator.model.component.ComponentIO;
import fr.ensicaen.simulator.model.factory.MediatorFactory;
import fr.ensicaen.simulator.model.mediator.Mediator;

public class FOUnitTest {
	private static ISOMsg authorizationRequest;

	private static ComponentIO ept;
	private static Mediator m_ept_fo;
	private static Mediator m_fo_foIssuer;
	private static Mediator m_fo_foAcquirer;
	private static Mediator m_foIssuer_FoIssuerAuthorization;
	private static Mediator m_foAcquirer_FoAcquirerAuthorization;
	private static Mediator m_foAcquirerAuthorization_foIssuerAuthorization;
	MediatorFactory factory = MediatorFactory.getInstance();

	private static ComponentIO frontOffice;

	/* Les trois grandes fonctions d'un FO */
	private static ComponentIO issuer;
	private static ComponentIO acceptor;
	private static ComponentIO acquirer;

	/* Différents modules de la fonction émetteur */

	private static ComponentIO issuerAuthorization;
	private static ComponentIO controlesSecurite;
	private static ComponentIO controlesCarte;
	private static ComponentIO traitementsAutorisation;
	private static ComponentIO gestionDroitsCarte;
	private static ComponentIO gestionSoldeCompte;

	private static ComponentIO gestionDeLaFraude;

	/* Différents modules de la fonction accepteur */

	private static ComponentIO systemeEncaissement;
	private static ComponentIO gestionEncaissementsMultiples;
	private static ComponentIO gestionRolesDeCaisse;
	private static ComponentIO gestionRemises;
	private static ComponentIO gestionTickets;
	private static ComponentIO gestionPeripheriques;
	private static ComponentIO editionDeFactures;

	private static ComponentIO concentrateurMonetique;
	private static ComponentIO gestionLigneDeCaisse;
	private static ComponentIO gestionTerminauxDePaiementGrappes;

	private static ComponentIO telePaiement;
	private static ComponentIO passerelleTelepaiement;
	private static ComponentIO acceptationPaiementPubliphone;
	private static ComponentIO acceptationPaiementParInternet;
	private static ComponentIO acceptationPaiementParGSM;

	private static ComponentIO gestionnaireTelepaiement;
	private static ComponentIO delivrancePaiement;
	private static ComponentIO gestionDesRemises;
	private static ComponentIO gestionDonneesFonctionnement;

	/* Différents modules de la fonction acquéreur */

	private static ComponentIO acquirerAuthorization;

	private static ComponentIO GABHandler;

	private static ComponentIO retrait;
	private static ComponentIO libreServiceBancaire;
	private static ComponentIO retraitAutoCompte;
	private static ComponentIO depot;
	private static ComponentIO virement;
	private static ComponentIO commandeDeChequier;
	private static ComponentIO demandeDeRIB;
	private static ComponentIO demandeDeSolde;
	private static ComponentIO historiqueOperations;

	private static ComponentIO telecollection;
	private static ComponentIO gestionCBPRCB2A;

	private static ComponentIO paymentAcquirer;

	private static ComponentIO paiementDeProximite;
	private static ComponentIO preAutorisation;
	private static ComponentIO venteADistance;
	private static ComponentIO paiementSurAutomate;
	private static ComponentIO telePaiementGSM;
	private static ComponentIO paiementVocal;
	private static ComponentIO paiementTelevise;
	private static ComponentIO quasiCash;
	private static ComponentIO cashAdvance;

	private static ComponentIO compensationSingleMessage;

	// @Before
	// public void init() throws Exception {
	// Context.getInstance().autoRegistrationMode();
	// frontOffice = new ComponentIO("Front Office",
	// ComponentEP.FRONT_OFFICE.ordinal());
	//
	// issuer = new ComponentIO("Issuer", ComponentEP.FO_ISSUER.ordinal());
	// acceptor = new ComponentIO("Acceptor");
	// acquirer = new ComponentIO("Acquirer",
	// ComponentEP.FO_ACQUIRER.ordinal());
	//
	// /* Ajout des trois grandes fonctions du front Office */
	// frontOffice.addChild(issuer);
	// frontOffice.addChild(acceptor);
	// frontOffice.addChild(acquirer);
	//
	// /* Ajout des modules émetteur */
	// issuerAuthorization = new ComponentIO("IssuerAuthorization",
	// ComponentEP.FO_ISSUER_AUTHORIZATION.ordinal());
	// gestionDeLaFraude = new ComponentIO("GestionDeLaFraude");
	// issuer.addChild(issuerAuthorization);
	// issuer.addChild(gestionDeLaFraude);
	//
	// /* Ajout des composants du module Autorisation */
	// controlesCarte = new ComponentIO("controlesCarte");
	// traitementsAutorisation = new ComponentIO("traitemntsAutorisation");
	// issuerAuthorization.addChild(controlesCarte);
	// issuerAuthorization.addChild(traitementsAutorisation);
	//
	// gestionDroitsCarte = new ComponentIO("gestionDroitsCarte");
	// gestionSoldeCompte = new ComponentIO("gestionSoldeCompte");
	// traitementsAutorisation.addChild(gestionDroitsCarte);
	// traitementsAutorisation.addChild(gestionSoldeCompte);
	//
	// /* Ajout des modules accepteur */
	// systemeEncaissement = new ComponentIO("systemeEncaissement");
	// concentrateurMonetique = new ComponentIO("concentrateurMonetique");
	// telePaiement = new ComponentIO("telePaiement");
	// acceptor.addChild(systemeEncaissement);
	// acceptor.addChild(concentrateurMonetique);
	// acceptor.addChild(telePaiement);
	//
	// /* Ajout des composants du module systemeEncaissement */
	// gestionEncaissementsMultiples = new
	// ComponentIO("gestionEncaissementsMultiples");
	// gestionRolesDeCaisse = new ComponentIO("gestionRolesDeCaisse");
	// gestionTickets = new ComponentIO("gestionTickets");
	// gestionPeripheriques = new ComponentIO("gestionPeripheriques");
	// editionDeFactures = new ComponentIO("editionDeFactures");
	// systemeEncaissement.addChild(gestionEncaissementsMultiples);
	// systemeEncaissement.addChild(gestionRolesDeCaisse);
	// systemeEncaissement.addChild(gestionTickets);
	// systemeEncaissement.addChild(gestionPeripheriques);
	// systemeEncaissement.addChild(editionDeFactures);
	//
	// /* Ajout des composants du module concentrateur Monétique */
	// gestionLigneDeCaisse = new ComponentIO("gestionLigneDeCaisse");
	// gestionTerminauxDePaiementGrappes = new
	// ComponentIO("gestionTerminauxDePaiementGrappes");
	// gestionRolesDeCaisse.addChild(gestionLigneDeCaisse);
	// gestionRolesDeCaisse.addChild(gestionTerminauxDePaiementGrappes);
	//
	// /* Ajout des composants du module télépaiement */
	// passerelleTelepaiement = new ComponentIO("passerelleTelepaiement");
	// gestionnaireTelepaiement = new ComponentIO("gestionnaireTelepaiement");
	// telePaiement.addChild(passerelleTelepaiement);
	// telePaiement.addChild(gestionnaireTelepaiement);
	//
	// acceptationPaiementPubliphone = new
	// ComponentIO("acceptationPaiementPubliphone");
	// acceptationPaiementParInternet = new
	// ComponentIO("acceptationPaiementParInternet");
	// acceptationPaiementParGSM = new ComponentIO("acceptationPaiementParGSM");
	// passerelleTelepaiement.addChild(acceptationPaiementPubliphone);
	// passerelleTelepaiement.addChild(acceptationPaiementParInternet);
	// passerelleTelepaiement.addChild(acceptationPaiementParGSM);
	//
	// delivrancePaiement = new ComponentIO("delivrancePaiement");
	// gestionDesRemises = new ComponentIO("gestionDesRemises");
	// gestionDonneesFonctionnement = new
	// ComponentIO("gestionDonneesFonctionnement");
	// gestionnaireTelepaiement.addChild(delivrancePaiement);
	// gestionnaireTelepaiement.addChild(gestionDesRemises);
	// gestionnaireTelepaiement.addChild(gestionDonneesFonctionnement);
	//
	// /* Ajout des modules acquéreur */
	// acquirerAuthorization = new ComponentIO("AcquirerAuthorization",
	// ComponentEP.FO_ACQUIRER_AUTHORIZATION.ordinal());
	// GABHandler = new ComponentIO("GABHandler");
	// telecollection = new ComponentIO("telecollection");
	// paymentAcquirer = new ComponentIO("paymentAcquirer");
	// compensationSingleMessage = new ComponentIO("compensationSingleMessage");
	// acquirer.addChild(acquirerAuthorization);
	// acquirer.addChild(GABHandler);
	// acquirer.addChild(telecollection);
	// acquirer.addChild(paymentAcquirer);
	// acquirer.addChild(compensationSingleMessage);
	//
	// /* Ajout des composants du module GABHandler" */
	// retrait = new ComponentIO("retrait");
	// libreServiceBancaire = new ComponentIO("libreServiceBancaire");
	// GABHandler.addChild(retrait);
	// GABHandler.addChild(libreServiceBancaire);
	//
	// retraitAutoCompte = new ComponentIO("retraitAutoCompte");
	// depot = new ComponentIO("depot");
	// virement = new ComponentIO("virement");
	// commandeDeChequier = new ComponentIO("commandeDeChequier");
	// demandeDeRIB = new ComponentIO("demandeDeRIB");
	// demandeDeSolde = new ComponentIO("demandeDeSolde");
	// historiqueOperations = new ComponentIO("historiqueOperations");
	// libreServiceBancaire.addChild(retraitAutoCompte);
	// libreServiceBancaire.addChild(depot);
	// libreServiceBancaire.addChild(virement);
	// libreServiceBancaire.addChild(commandeDeChequier);
	// libreServiceBancaire.addChild(demandeDeRIB);
	// libreServiceBancaire.addChild(demandeDeSolde);
	// libreServiceBancaire.addChild(historiqueOperations);
	//
	// /* Ajout des composants du module telecollection */
	// gestionCBPRCB2A = new ComponentIO("gestionCBPRCB2A");
	// telecollection.addChild(gestionCBPRCB2A);
	//
	// /* Ajout des composants du module paymentAcquirer */
	// paiementDeProximite = new ComponentIO("paiementDeProximite");
	// preAutorisation = new ComponentIO("preAutorisation");
	// venteADistance = new ComponentIO("venteADistance");
	// telePaiementGSM = new ComponentIO("telePaiementGSM");
	// paiementVocal = new ComponentIO("paiementVocal");
	// paiementTelevise = new ComponentIO("paiementTelevise");
	// quasiCash = new ComponentIO("quasiCash");
	// cashAdvance = new ComponentIO("cashAdvance");
	// paymentAcquirer.addChild(paiementDeProximite);
	// paymentAcquirer.addChild(preAutorisation);
	// paymentAcquirer.addChild(venteADistance);
	// paymentAcquirer.addChild(telePaiementGSM);
	// paymentAcquirer.addChild(paiementVocal);
	// paymentAcquirer.addChild(paiementTelevise);
	// paymentAcquirer.addChild(quasiCash);
	// paymentAcquirer.addChild(cashAdvance);
	//
	// // front office
	// frontOffice.setStrategy(new FOStrategy());
	//
	// // module acquéreur
	// acquirer.setStrategy(new FOAcquirerStrategy());
	// acquirerAuthorization.setStrategy(new FOAcquirerAuthorizationStrategy());
	//
	// // module émetteur
	// issuer.setStrategy(new FOIssuerStrategy());
	// issuerAuthorization.setStrategy(new FOIssuerAuthorizationStrategy());
	//
	// ept = new ComponentIO("Electronic Payment Terminal",
	// ComponentEP.ELETRONIC_TERMINAL_PAYMENT.ordinal());
	//
	// m_ept_fo = MediatorFactory.getInstance().getMediator(frontOffice, ept,
	// EMediator.HALFDUPLEX);
	//
	// generateMsg();
	//
	// }
	//
	// @After
	// public void clean() throws Exception {
	// Context.getInstance().reset();
	// }
	//
	// /**
	// * generation des messages du EPT
	// *
	// * @throws ISOException
	// */
	// public static void generateMsg() throws ISOException {
	// authorizationRequest = ISO8583Tools.create();
	// authorizationRequest.setMTI("0100");
	// authorizationRequest.set(2, "0123456789123456"); // PAN
	// authorizationRequest.set(3, "000101"); // Type of Auth +
	// // accounts
	// authorizationRequest.set(4, "100"); // 100€
	// authorizationRequest.set(7,
	// ISO7816Tools.writeDATETIME(Calendar.getInstance().getTime())); // date
	// // :
	// // MMDDhhmmss
	// authorizationRequest.set(38, "123456"); // Approval Code
	// authorizationRequest.set(42, "623598"); // Acceptor's ID
	// authorizationRequest.set(123, "21151168"); // POS Data Code
	// }
	//
	// /**
	// * Le FO repond positevement a la requete => acceptence 00
	// */
	// @Test
	// public void testCallAuth_OK() {
	// // propriete d'acceptance
	// issuerAuthorization.getProperties().put("acceptance", "00");
	// // fake issuer authorization module as router
	// issuerAuthorization.setName("Router");
	// MediatorFactory.getInstance().getMediator(issuerAuthorization,
	// acquirerAuthorization);
	//
	// ept.setStrategy(new IStrategy<ComponentIO>() {
	//
	// private static final long serialVersionUID = 2626955719622250036L;
	//
	// @Override
	// public IResponse processMessage(ComponentIO _this, Mediator mediator,
	// String data) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public void processEvent(ComponentIO _this, String event) {
	//
	// ISOMsg authorizationAnswer = ISO8583Tools.create();
	// try {
	// authorizationAnswer.unpack(((DataResponse) m_ept_fo.send(_this,
	// new String(authorizationRequest.pack()))).getData().getBytes());
	// Assert.assertTrue(authorizationAnswer.getValue(39).equals("00"));
	// Assert.assertTrue(authorizationAnswer.getValue(38) != null);
	// }
	// catch (ISOException e) {
	// e.printStackTrace();
	// Assert.assertTrue(false);
	// }
	//
	// }
	//
	// @Override
	// public void init(IOutput _this, Context ctx) {
	// ctx.subscribeEvent(_this, "AUTHORIZATION_CALL");
	// }
	//
	// @Override
	// public List<PropertyDefinition> getPropertyDefinitions() {
	// return new ArrayList<PropertyDefinition>();
	// }
	// });
	//
	// // on insert la carte dans le tpe, le tpe envoie des donnees a la carte
	// Context.getInstance().addStartPoint(new Date(), "AUTHORIZATION_CALL");
	//
	// // execute simulation.
	// try {
	// SimulatorFactory.getSimulator().resume();
	// SimulatorFactory.getSimulator().start();
	// }
	// catch (SimulatorException e) {
	// e.printStackTrace();
	// Assert.assertFalse(true);
	// }
	//
	// }
	//
	// /**
	// * Le FO repond negativement a la requete => acceptance 01
	// */
	// @Test
	// public void testCallAuth_NOK() {
	// // propriete d'acceptance
	// issuerAuthorization.getProperties().put("acceptance", "01");
	// // fake issuer authorization module as router
	// issuerAuthorization.setName("Router");
	// MediatorFactory.getInstance().getMediator(issuerAuthorization,
	// acquirerAuthorization);
	//
	// ept.setStrategy(new IStrategy<ComponentIO>() {
	//
	// private static final long serialVersionUID = 2626955719622250036L;
	//
	// @Override
	// public IResponse processMessage(ComponentIO _this, Mediator mediator,
	// String data) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public void processEvent(ComponentIO _this, String event) {
	//
	// ISOMsg authorizationAnswer = ISO8583Tools.create();
	// try {
	// authorizationAnswer.unpack(((DataResponse) m_ept_fo.send(_this,
	// new String(authorizationRequest.pack()))).getData().getBytes());
	// Assert.assertTrue(authorizationAnswer.getValue(39).equals("01"));
	// }
	// catch (ISOException e) {
	// e.printStackTrace();
	// Assert.assertTrue(false);
	// }
	//
	// }
	//
	// @Override
	// public void init(IOutput _this, Context ctx) {
	// ctx.subscribeEvent(_this, "AUTHORIZATION_CALL");
	// }
	//
	// @Override
	// public List<PropertyDefinition> getPropertyDefinitions() {
	// return new ArrayList<PropertyDefinition>();
	// }
	// });
	//
	// // on insert la carte dans le tpe, le tpe envoie des donnees a la carte
	// Context.getInstance().addStartPoint(new Date(), "AUTHORIZATION_CALL");
	//
	// // execute simulation.
	// try {
	// SimulatorFactory.getSimulator().resume();
	// SimulatorFactory.getSimulator().start();
	// }
	// catch (SimulatorException e) {
	// e.printStackTrace();
	// Assert.assertFalse(true);
	// }
	//
	// }
}
