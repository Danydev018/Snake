package culebra;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class HighScoresPanel extends JPanel {
	private JFrame parentFrame;
	private JButton closeButton;
	private JButton refreshButton;
	private JList<String> scoresList;
	private DefaultListModel<String> listModel;

	public HighScoresPanel(JFrame parentFrame) {
		this.parentFrame = parentFrame;
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		setFocusable(true);

		JLabel titleLabel = new JLabel("Mejores Jugadores", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Sans serif", Font.BOLD, 24));
		titleLabel.setForeground(Color.BLACK);
		add(titleLabel, BorderLayout.NORTH);

		listModel = new DefaultListModel<>();
		scoresList = new JList<>(listModel);
		scoresList.setFont(new Font("Sans serif", Font.PLAIN, 18));
		scoresList.setBackground(Color.WHITE);
		scoresList.setForeground(Color.DARK_GRAY);
		add(new JScrollPane(scoresList), BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		closeButton = new JButton("Cerrar");
		refreshButton = new JButton("Actualizar");
		buttonPanel.add(refreshButton);
		buttonPanel.add(closeButton);
		add(buttonPanel, BorderLayout.SOUTH);

		closeButton.addActionListener(e -> parentFrame.dispose());
		refreshButton.addActionListener(e -> updateScores());

		updateScores();
	}

	private void updateScores() {
		listModel.clear();
		List<Score> scores = HighScores.getScores();
		if (scores.isEmpty()) {
			listModel.addElement("No hay puntajes registrados.");
		} else {
			for (Score score : scores) {
				listModel.addElement(score.getName() + " - " + score.getScore());
			}
		}
	}
}
