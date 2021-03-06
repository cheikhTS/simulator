package fr.ensicaen.simulator.model.component;

import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.ensicaen.simulator.simulator.Context;

@XmlRootElement
public class ComponentO extends Component implements IOutput {

	private static Logger log = LoggerFactory.getLogger(ComponentO.class);

	public ComponentO() {
		super();
	}

	public ComponentO(String _name) {
		super(_name);
	}

	public ComponentO(String _name, int type) {
		super(_name, type);
	}

	@Override
	public void notifyEvent(String event) {
		this.strategy.processEvent(this, event);
	}

	@Override
	public String toString() {
		return "C[Output - " + this.name + "]";
	}

	@Override
	public void init(Context ctx) {
		if (this.strategy != null) {
			this.strategy.init(this, ctx);
		}
	}

	@Override
	public boolean isOutput() {
		return true;
	}

	@Override
	public boolean isInput() {
		return false;
	}
}
