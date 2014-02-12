package fr.ensicaen.gui_simulator.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.mxgraph.examples.swing.editor.BasicGraphEditor;
import com.mxgraph.util.mxResources;

import fr.ensicaen.gui_simulator.gui.bridge.StartPointJTableBridge;
import fr.ensicaen.gui_simulator.gui.editor.DateTimeCellEditor;
import fr.ensicaen.gui_simulator.gui.renderer.DateTimeCellRenderer;
import fr.ensicaen.simulator.simulator.AsyncSimulator;
import fr.ensicaen.simulator.simulator.Context;
import fr.ensicaen.simulator.simulator.SimulatorFactory;
import fr.ensicaen.simulator.simulator.exception.SimulatorException;

public class SimulatorPanel extends JTabbedPane implements
		ListSelectionListener {

	private JTable startPointTable;
	private StartPointJTableBridge startPointModelTable;
	private List<JButton> buttons = new ArrayList<>(2);

	public SimulatorPanel(BasicGraphEditor frame) {
		// tab
		addTab(mxResources.get("start_points"), new JScrollPane(
				initTab_startPointTable()));
		addTab(mxResources.get("simulator"), new JScrollPane(
				initTab_simulatorPanel()));
	}

	private JPanel initTab_startPointTable() {
		JPanel startPointTableTab = new JPanel();
		startPointTableTab.setLayout(new BorderLayout());

		// model
		Context ctx = Context.getInstance();
		// ctx.addStartPoint(new Date(), "COUCOU");
		// ctx.addStartPoint(new Date(System.currentTimeMillis() + 3600 * 24 *
		// 5), "LOL");
		startPointModelTable = new StartPointJTableBridge(ctx.getStartPoints());

		// view
		startPointTable = new JTable(startPointModelTable);
		// propertiesTable.setBackground(null);
		startPointTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		startPointTable.setRowHeight(20);
		startPointTable.getSelectionModel().addListSelectionListener(this);

		TableColumn col = startPointTable.getColumnModel().getColumn(0);
		col.setCellEditor(new DateTimeCellEditor());
		col.setCellRenderer(new DateTimeCellRenderer());
		col.setMinWidth(100);

		// view header
		JTableHeader header = startPointTable.getTableHeader();
		header.setResizingAllowed(true);
		header.setReorderingAllowed(false);

		// sub panel for button
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Color.WHITE);
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		buttonsPanel.add(initButton(
				"/com/mxgraph/examples/swing/images/new.gif",
				new AddEntryAction()));
		buttonsPanel.add(initButton(
				"/com/mxgraph/examples/swing/images/delete.gif",
				new DeleteEntryAction()));
		setButtonsState(0, true);

		// add in parent layout
		startPointTableTab.add(header, BorderLayout.NORTH);
		startPointTableTab.add(startPointTable, BorderLayout.CENTER);
		startPointTableTab.add(buttonsPanel, BorderLayout.SOUTH);

		return startPointTableTab;
	}

	private JPanel initTab_simulatorPanel() {
		JPanel simulatorPanel = new JPanel();

		JButton btnLaunch = new JButton("Launch");
		btnLaunch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AsyncSimulator sim = SimulatorFactory.getAsyncSimulator();
				try {
					sim.start();
				} catch (SimulatorException e1) {
					e1.printStackTrace();
				}
			}
		});

		simulatorPanel.add(btnLaunch);
		return simulatorPanel;
	}

	private JButton initButton(String iconPath, ActionListener action) {
		JButton button = new JButton(new ImageIcon(
				SimulatorPanel.class.getResource(iconPath)));
		button.addActionListener(action);
		button.setEnabled(false);

		// register
		buttons.add(button);

		return button;
	}

	/**
	 * 
	 * @param index
	 *            Numéro de l'index ou -1 pour tous.
	 * @param enable
	 */
	private void setButtonsState(int index, boolean enable) {
		if (index == -1) {
			for (JButton btn : buttons) {
				btn.setEnabled(enable);
			}
		} else {
			buttons.get(index).setEnabled(enable);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (startPointTable.getSelectedRow() != -1) {
			setButtonsState(1, true);
		} else {
			setButtonsState(1, false);
		}

		setButtonsState(0, !startPointModelTable.isAddRowEnabled());
	}

	private class AddEntryAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			startPointModelTable.enableAddRow();
			setButtonsState(0, false);
		}

	}

	private class DeleteEntryAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int rep = JOptionPane.showConfirmDialog(SimulatorPanel.this,
					mxResources.get("delete_confirmation"),
					mxResources.get("delete_confirmation_title"),
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (rep == JOptionPane.YES_OPTION) {
				startPointModelTable
						.deleteRow(startPointTable.getSelectedRow());
				setButtonsState(0, true);
			}
		}
	}

	public void refresh() {
		startPointModelTable.fireTableDataChanged();
	}

}
