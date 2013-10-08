package simulator;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import model.component.Component;
import model.component.ComponentIO;
import model.component.IOutput;
import model.mediator.HalfDuplexMediator;
import model.mediator.Mediator;
import model.mediator.SimplexMediator;
import model.strategies.IStrategy;
import model.strategies.NullStrategy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SimulatorTest {

	@Before
	public void beforeTest() {
		System.out.println("---------NEW TEST---------");
	}
	
	@Test(expected = SimulatorException.class)
	public void testWithNoStartPoint() throws SimulatorException {
		Simulator simulator = SimulatorFactory.getSimulator();
		simulator.start();
	}
	
	@Test
	public void testAsyncWithNoStartPoint() throws SimulatorException {
		AsyncSimulator simulator = SimulatorFactory.getAsyncSimulator();
		simulator.start();
		
		try {
			simulator.waitUntilEnd();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		Assert.assertNotNull(simulator.getException());
	}
	
	@Test
	public void testWithOneStartPoint() throws SimulatorException {
		// setting simple model
		ComponentIO florent = new ComponentIO("personne");
		florent.getProperties().put("nom", "moisson");
		florent.getProperties().put("prenom", "florent");
		florent.getProperties().put("age", "22");
		florent.getProperties().put("adresse", "...........");
		florent.setStrategy(new NullStrategy());
		
		ComponentIO bank = new ComponentIO("banque");
		bank.getProperties().put("marque", "bnp");
		bank.setStrategy(new NullStrategy());
		
		ComponentIO account = new ComponentIO("compte");
		account.getProperties().put("porteur", "florent moisson");
		account.getProperties().put("montant", "1500");
		account.getProperties().put("plafond", "9000");
		bank.getComponents().add(account);
		account.setStrategy(new NullStrategy());
		
		// TODO : va surement poser pb si on doit passer les types hérités
		// A l'instanciation, je vois plus passer Component mais on sait pas si c'est
		// IOuput ou IInput ... control dans le mediator ?
		Mediator mediator = new SimplexMediator(florent, bank);
		
		// setting context
		Context ctx = Context.getInstance();
		short contextIndex = ctx.currentCounter();
		ctx.addStartPoint(new Date(), florent, mediator, "TEST DATA");
		
		// running simulation
		Simulator simulator = SimulatorFactory.getSimulator();
		simulator.start();
		
		Assert.assertEquals(ctx.currentCounter(), contextIndex + 1);
	}
	
	@Test
	public void testAsyncWithOneStartPoint() throws SimulatorException {
		// setting simple model
		ComponentIO florent = new ComponentIO("personne");
		florent.getProperties().put("nom", "moisson");
		florent.getProperties().put("prenom", "florent");
		florent.getProperties().put("age", "22");
		florent.getProperties().put("adresse", "...........");
		
		ComponentIO bank = new ComponentIO("banque");
		bank.getProperties().put("marque", "bnp");
		
		ComponentIO account = new ComponentIO("compte");
		account.getProperties().put("porteur", "florent moisson");
		account.getProperties().put("montant", "1500");
		account.getProperties().put("plafond", "9000");
		bank.getComponents().add(account);
		
		// TODO : va surement poser pb si on doit passer les types hérités
		// A l'instanciation, je vois plus passer Component mais on sait pas si c'est
		// IOuput ou IInput ... control dans le mediator ?
		Mediator mediator = new SimplexMediator(florent, bank);
		
		// setting context
		Context ctx = Context.getInstance();
		short contextIndex = ctx.currentCounter();
		ctx.addStartPoint(new Date(), florent, mediator, "TEST DATA");
		
		// running simulation
		AsyncSimulator simulator = SimulatorFactory.getAsyncSimulator();
		simulator.start();
		
		try {
			simulator.waitUntilEnd();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		Assert.assertNull(simulator.getException());
		Assert.assertEquals(ctx.currentCounter(), contextIndex + 1);
	}
	
	@Test
	public void testWithDynamicStartPoint() throws SimulatorException {
		// setting simple model
		ComponentIO florent = new ComponentIO("personne");
		florent.getProperties().put("nom", "moisson");
		florent.getProperties().put("prenom", "florent");
		florent.getProperties().put("age", "22");
		florent.getProperties().put("adresse", "...........");
		florent.setStrategy(new NullStrategy());
		
		final ComponentIO bank = new ComponentIO("banque");
		bank.getProperties().put("marque", "bnp");
		bank.setStrategy(new NullStrategy());
		bank.setStrategy(new IStrategy() {
			
			@Override
			public void process(Mediator m, String data) {
				Context ctx = Context.getInstance();
				ctx.addStartPoint(new Date(System.currentTimeMillis() - 3600 * 4 * 1000), bank, m, "TEST CTX 3");
				ctx.addStartPoint(new Date(System.currentTimeMillis() + 3600 * 4 * 1000), bank, m, "TEST CTX 4");
			}
		});
		
		ComponentIO account = new ComponentIO("compte");
		account.getProperties().put("porteur", "florent moisson");
		account.getProperties().put("montant", "1500");
		account.getProperties().put("plafond", "9000");
		bank.getComponents().add(account);
		account.setStrategy(new NullStrategy());
		
		// TODO : va surement poser pb si on doit passer les types hérités
		// A l'instanciation, je vois plus passer Component mais on sait pas si c'est
		// IOuput ou IInput ... control dans le mediator ?
		Mediator mediator1 = new HalfDuplexMediator(florent, bank);
		Mediator mediator2 = new SimplexMediator(bank, account);
		
		
		// setting context
		Context ctx = Context.getInstance();
		short contextIndex = ctx.currentCounter();
		ctx.addStartPoint(new Date(), florent, mediator1, "TEST CTX 1");
		ctx.addStartPoint(new Date(), bank, mediator2, "TEST CTX 2");
		//ajout dynamique en strategy
		//ctx.addStartPoint(new Date(), bank, mediator1, "TEST CTX 3");
		
		// running simulation
		Simulator simulator = SimulatorFactory.getSimulator();
		simulator.start();
		
		Assert.assertEquals(ctx.currentCounter(), contextIndex + 4);
	}
}