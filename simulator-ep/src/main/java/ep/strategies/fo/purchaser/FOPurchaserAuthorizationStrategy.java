package ep.strategies.fo.purchaser;

import model.component.ComponentIO;
import model.component.IOutput;
import model.factory.MediatorFactory;
import model.mediator.Mediator;
import model.response.IResponse;
import model.strategies.IStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ep.strategies.fo.FOStrategy;

import simulator.Context;
import simulator.exception.ContextException;

public class FOPurchaserAuthorizationStrategy implements IStrategy<ComponentIO> {

	private static Logger log = LoggerFactory.getLogger(FOStrategy.class);

	@Override
	public void init(IOutput _this, Context ctx) {
	}

	@Override
	public IResponse processMessage(ComponentIO FOPurchaserAuthorization, Mediator m, String data) {
		
		
		try {
			Mediator mediateurAUtiliser = Context.getInstance().getFirstMediator(FOPurchaserAuthorization, "IssuerAuthorization");
			return mediateurAUtiliser.send(FOPurchaserAuthorization, data);
		}
		catch (ContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	
	}

	@Override
	public void processEvent(ComponentIO _this, String event) {

	}

	@Override
	public String toString() {
		return "FOIssuer";
	}

}
